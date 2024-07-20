package com.walletwise.main.beans.config.security;

import com.walletwise.infra.adapters.LoadUserAdapter;
import com.walletwise.infra.gateways.token.GetUsernameFromToken;
import com.walletwise.infra.gateways.token.IsValidToken;
import com.walletwise.infra.persistence.entities.RoleEntity;
import com.walletwise.infra.persistence.entities.UserEntity;
import com.walletwise.main.config.beans.security.JwtAuthenticationFilter;
import com.walletwise.mocks.Mocks;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.Collections;

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
    void doFilterInternal_withValidToken_shouldAuthenticateUser() throws ServletException, IOException {
        String token = "valid_token";

        String username = "any_user_name";

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(null);
        SecurityContextHolder.setContext(securityContext);

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(getUsernameFromToken.get(token)).thenReturn(username);
        RoleEntity savedRoleEntity = RoleEntity.builder().name("any_name").active(true).build();

        UserEntity userDetails = Mocks.savedUserEntityFactory();
        userDetails.setRoles(Collections.singletonList(savedRoleEntity));


        when(this.loadUserAdapter.loadUserByUsername(username)).thenReturn(userDetails);
        when(isValidToken.isValid(token, userDetails)).thenReturn(true);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(loadUserAdapter, times(1)).loadUserByUsername(username);
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void doFilterInternal_withValidToken_shouldAuthenticateUserTokenInvalid() throws ServletException, IOException {
        String token = "valid_token";
        String username = "user";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(getUsernameFromToken.get(token)).thenReturn(username);

        UserDetails userDetails = Mocks.savedUserEntityFactory();

        when(loadUserAdapter.loadUserByUsername(username)).thenReturn(userDetails);
        when(isValidToken.isValid(token, userDetails)).thenReturn(false);
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        verify(loadUserAdapter, times(1)).loadUserByUsername(username);
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void doFilterInternal_withInvalidToken_shouldNotAuthenticateUser() throws ServletException, IOException {
        String token = "invalid_token";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(getUsernameFromToken.get(token)).thenReturn(null);
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        verify(loadUserAdapter, never()).loadUserByUsername(anyString());
        verify(request, never()).setAttribute(anyString(), anyString());
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void doFilterInternal_withInvalidToken_shouldNotAuthenticateUserInvalid() throws ServletException, IOException {
        String token = "invalid_token";
        when(request.getHeader("Authorization")).thenReturn("NOT_Bearer " + token);
        when(getUsernameFromToken.get(token)).thenReturn(null);
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        verify(loadUserAdapter, never()).loadUserByUsername(anyString());
        verify(request, never()).setAttribute(anyString(), anyString());
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void doFilterInternal_withInvalidToken_shouldNotAuthenticateUserEmpty() throws ServletException, IOException {
        String token = "invalid_token";
        when(request.getHeader("Authorization")).thenReturn("");
        when(getUsernameFromToken.get(token)).thenReturn(null);
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        verify(loadUserAdapter, never()).loadUserByUsername(anyString());
        verify(request, never()).setAttribute(anyString(), anyString());
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void doFilterInternal_withInvalidToken_shouldNotAuthenticateUserFilterThrow() throws ServletException, IOException {
        String token = "invalid_token";
        when(request.getHeader("Authorization")).thenReturn("");
        when(getUsernameFromToken.get(token)).thenReturn(null);
        doThrow(ServletException.class).when(filterChain).doFilter(request, response);
        Throwable exception = Assertions.catchThrowable(() -> jwtAuthenticationFilter.doFilterInternal(request, response, filterChain));
        verify(loadUserAdapter, never()).loadUserByUsername(anyString());
        verify(request, never()).setAttribute(anyString(), anyString());
        verify(filterChain, times(1)).doFilter(request, response);
        Assertions.assertThat(exception).isInstanceOf(ServletException.class);
    }

    @Test
    void doFilterInternal_withInvalidToken_shouldNotAuthenticateUserFilterThrowIOException() throws ServletException, IOException {
        String token = "invalid_token";
        when(request.getHeader("Authorization")).thenReturn("");
        when(getUsernameFromToken.get(token)).thenReturn(null);
        doThrow(IOException.class).when(filterChain).doFilter(request, response);
        Throwable exception = Assertions.catchThrowable(() -> jwtAuthenticationFilter.doFilterInternal(request, response, filterChain));
        verify(loadUserAdapter, never()).loadUserByUsername(anyString());
        verify(request, never()).setAttribute(anyString(), anyString());
        verify(filterChain, times(1)).doFilter(request, response);
        Assertions.assertThat(exception).isInstanceOf(IOException.class);
    }
}
