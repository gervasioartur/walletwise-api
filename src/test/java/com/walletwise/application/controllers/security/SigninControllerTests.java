package com.walletwise.application.controllers.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.walletwise.application.dto.security.SigninRequest;
import com.walletwise.domain.entities.exceptions.UnauthorizedException;
import com.walletwise.domain.useCases.auth.Signin;
import com.walletwise.mocks.Mocks;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
public class SigninControllerTests {
    private final String URL = "/auth/signin";
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @MockBean
    private Signin signin;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    @DisplayName("Should return badRequest if Username or email is empty")
    void shouldReturnBadRequestIfUsernameOrEmailEmpty() throws Exception {
        SigninRequest requestParams = new SigninRequest("", Mocks.faker.internet().password());

        String json = new ObjectMapper().writeValueAsString(requestParams);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("Username or Email is required.")));
    }

    @Test
    @DisplayName("Should return badRequest if Username or Email is null")
    void shouldReturnBadRequestIfUsernameOrEmailNull() throws Exception {
        SigninRequest requestParams = new SigninRequest(null, Mocks.faker.internet().password());

        String json = new ObjectMapper().writeValueAsString(requestParams);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("Username or Email is required.")));
    }

    @Test
    @DisplayName("Should return badRequest if Password is empty")
    void shouldReturnBadRequestIfPasswordIsEmpty() throws Exception {
        SigninRequest requestParams = new SigninRequest(Mocks.faker.name().username(), "");

        String json = new ObjectMapper().writeValueAsString(requestParams);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("Password is required.")));
    }

    @Test
    @DisplayName("Should return badRequest if Password is null")
    void shouldReturnBadRequestIfPasswordIsNull() throws Exception {
        SigninRequest requestParams = new SigninRequest(Mocks.faker.name().username(), null);

        String json = new ObjectMapper().writeValueAsString(requestParams);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("Password is required.")));
    }


    @Test
    @DisplayName("Should return Unauthorized if Sing in throws UnauthorizedException")
    void shouldReturnUnauthorizedIfSignInThrowsUnauthorizedException() throws Exception {
        SigninRequest requestParams = new SigninRequest(Mocks.faker.name().username(),
                Mocks.faker.internet().password());

        BDDMockito.doThrow(UnauthorizedException.class)
                .when(this.signin).signin(requestParams.usernameOrEmail(), requestParams.password());

        String json = new ObjectMapper().writeValueAsString(requestParams);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Should return if InternalServerError if Sign in throws unexpected error")
    void shouldReturnInternalServerErrorIfSignupThrowsUnexpectedError() throws Exception {
        SigninRequest requestParams = new SigninRequest(Mocks.faker.name().username(),
                Mocks.faker.internet().password());

        BDDMockito.doThrow(HttpServerErrorException.InternalServerError.class)
                .when(this.signin).signin(requestParams.usernameOrEmail(), requestParams.password());

        String json = new ObjectMapper().writeValueAsString(requestParams);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("body", Matchers
                        .is("An unexpected error occurred. Please try again later.")));
    }

    @Test
    @DisplayName("Should return OK and access token on sign in success")
    void shouldReturnOkAndAccessTokenOnSignSuccess() throws Exception {
        SigninRequest requestParams = new SigninRequest(Mocks.faker.name().username(),
                Mocks.faker.internet().password());
        String accessToken = UUID.randomUUID().toString();

        BDDMockito.when(this.signin.signin(requestParams.usernameOrEmail(), requestParams.password())).thenReturn(accessToken);

        String json = new ObjectMapper().writeValueAsString(requestParams);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("body", Matchers.is(accessToken)));
    }
}
