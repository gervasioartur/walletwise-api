package com.walletwise.main.config.security;

import com.walletwise.infrastructure.adapters.LoadUserAdapter;
import com.walletwise.infrastructure.gateways.token.GetUsernameFromToken;
import com.walletwise.infrastructure.gateways.token.IsValidToken;
import com.walletwise.infrastructure.persistence.entities.security.RoleEntity;
import com.walletwise.infrastructure.persistence.entities.security.UserEntity;
import com.walletwise.mocks.Mocks;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.util.JRLoader;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

@SpringBootTest
public class JwtAuthenticationFilterTests {
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @MockBean
    private LoadUserAdapter loadUserAdapter;
    @MockBean
    private GetUsernameFromToken getUsernameFromToken;
    @MockBean
    private IsValidToken isValidToken;
    @MockBean
    private HttpServletRequest request;
    @MockBean
    private HttpServletResponse response;
    @MockBean
    private FilterChain filterChain;

    @Test
    @DisplayName("Should not authenticate if authorization header is null")
    void shouldNotAuthenticateIfAuthorizationHeaderIsNull() throws ServletException, IOException {
        Mockito.when(request.getHeader("Authorization")).thenReturn(null);

        jwtAuthenticationFilter.doFilterInternal(request,response,filterChain);

        Assertions.assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
        verify(this.filterChain,times(1)).doFilter(request, response);
    }

    @Test
    @DisplayName("Should not authenticate if authorization header is empty")
    void shouldNotAuthenticateIfAuthorizationHeaderIsEmpty() throws ServletException, IOException {
        Mockito.when(request.getHeader("Authorization")).thenReturn("");

        jwtAuthenticationFilter.doFilterInternal(request,response,filterChain);

        Assertions.assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
        verify(this.filterChain,times(1)).doFilter(request, response);
    }

    @Test
    @DisplayName("Should not authenticate if authorization header is not Bearer")
    void shouldNotAuthenticateIfAuthorizationHeaderIsNotBearer() throws ServletException, IOException {
        String authHeader = "NotBearer " + UUID.randomUUID();

        Mockito.when(request.getHeader("Authorization")).thenReturn(authHeader);

        jwtAuthenticationFilter.doFilterInternal(request,response,filterChain);

        Assertions.assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
        verify(this.filterChain,times(1)).doFilter(request, response);
    }

    @Test
    @DisplayName("Should not authenticate if cant extract username on token")
    void shouldNotAuthenticateIfCantExtractUsernameOnToken() throws ServletException, IOException {
        String authHeader = "Bearer " + UUID.randomUUID();

        Mockito.when(request.getHeader("Authorization")).thenReturn(authHeader);

        jwtAuthenticationFilter.doFilterInternal(request,response,filterChain);

        Assertions.assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
        verify(this.filterChain,times(1)).doFilter(request, response);
    }

    @Test
    @DisplayName("Should not authenticate if security context if not null")
    void shouldNotAuthenticateIfSecurityContextIsNull() throws ServletException, IOException {
        String authHeader = "Bearer " + UUID.randomUUID();
        String token = authHeader.substring(7);
        String username = Mocks.faker.name().username();

        Mockito.when(request.getHeader("Authorization")).thenReturn(authHeader);
        Mockito.when(this.getUsernameFromToken.get(token)).thenReturn(username);

        try (MockedStatic<SecurityContextHolder> securityContextHolder = Mockito.mockStatic(SecurityContextHolder.class)) {
            SecurityContext securityContext = mock(SecurityContext.class);
            securityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication())
                    .thenReturn(new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList()));

            jwtAuthenticationFilter.doFilterInternal(request,response,filterChain);

            Assertions.assertThat(SecurityContextHolder.getContext().getAuthentication()).isNotNull();
            verify(this.filterChain,times(1)).doFilter(request, response);
        }
    }

    @Test
    @DisplayName("Should not authenticate if user does not exist by username")
    void shouldNotAuthenticateIfUserDoesNotExistByUsername() throws ServletException, IOException {
        String authHeader = "Bearer " + UUID.randomUUID();
        String token = authHeader.substring(7);
        String username = Mocks.faker.name().username();

        Mockito.when(request.getHeader("Authorization")).thenReturn(authHeader);
        Mockito.when(this.getUsernameFromToken.get(token)).thenReturn(username);

        try (MockedStatic<SecurityContextHolder> securityContextHolder = Mockito.mockStatic(SecurityContextHolder.class)) {
            SecurityContext securityContext = mock(SecurityContext.class);
            securityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(null);
            doThrow(UsernameNotFoundException.class).when(this.loadUserAdapter).loadUserByUsername(username);

           Throwable exception = Assertions
                   .catchThrowable(()->  jwtAuthenticationFilter.doFilterInternal(request,response,filterChain));

            Assertions.assertThat(exception).isInstanceOf(UsernameNotFoundException.class);
            Assertions.assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
            verify(this.loadUserAdapter,times(1)).loadUserByUsername(username);
            verify(this.filterChain,times(0)).doFilter(request, response);
        }
    }

    @Test
    @DisplayName("Should not authenticate if token is invalid")
    void shouldNotAuthenticateIfTokenIsInvalid() throws ServletException, IOException {
        String authHeader = "Bearer " + UUID.randomUUID();
        String token = authHeader.substring(7);
        String username = Mocks.faker.name().username();
        UserEntity userDetails = Mocks.savedUserEntityFactory();

        Mockito.when(request.getHeader("Authorization")).thenReturn(authHeader);
        Mockito.when(this.getUsernameFromToken.get(token)).thenReturn(username);

        try (MockedStatic<SecurityContextHolder> securityContextHolder = Mockito.mockStatic(SecurityContextHolder.class)) {
            SecurityContext securityContext = mock(SecurityContext.class);
            securityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(null);
            when(this.loadUserAdapter.loadUserByUsername(username)).thenReturn(userDetails);
            when(this.isValidToken.isValid(token,userDetails)).thenReturn(false);

             jwtAuthenticationFilter.doFilterInternal(request,response,filterChain);

            Assertions.assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
            verify(this.loadUserAdapter,times(1)).loadUserByUsername(username);
            verify(this.filterChain,times(1)).doFilter(request, response);
        }
    }

    @Test
    @DisplayName("Should authenticate")
    void shouldAuthenticate() throws ServletException, IOException {
        String authHeader = "Bearer " + UUID.randomUUID();
        String token = authHeader.substring(7);
        String username = Mocks.faker.name().username();
        UserEntity userDetails = Mocks.savedUserEntityFactory();
        userDetails.setRoles(List.of(Mocks.savedRoleEntityFactory()));

        Mockito.when(request.getHeader("Authorization")).thenReturn(authHeader);
        Mockito.when(this.getUsernameFromToken.get(token)).thenReturn(username);

        try (MockedStatic<SecurityContextHolder> securityContextHolder = Mockito.mockStatic(SecurityContextHolder.class)) {
            SecurityContext securityContext = mock(SecurityContext.class);
            securityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(null);
            when(this.loadUserAdapter.loadUserByUsername(username)).thenReturn(userDetails);
            when(this.isValidToken.isValid(token,userDetails)).thenReturn(true);
            securityContextHolder.when(SecurityContextHolder::createEmptyContext).thenReturn(securityContext);
            doNothing().when(securityContext).setAuthentication(any());

            jwtAuthenticationFilter.doFilterInternal(request,response,filterChain);

            Assertions.assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
            verify(this.loadUserAdapter,times(1)).loadUserByUsername(username);
            verify(this.filterChain,times(1)).doFilter(request, response);
        }
    }
}