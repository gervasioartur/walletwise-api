package com.walletwise.application.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.walletwise.application.http.PasswordRecoveryRequest;
import com.walletwise.domain.useCases.PasswordRecovery;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class PasswordRecoveryControllerTests {
    private final String URL = "/auth/password-recovery";
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @MockBean
    private PasswordRecovery passwordRecovery;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    @DisplayName("Should return badRequest if email is empty")
    void shouldReturnBadRequestIfEmailEmpty() throws Exception {
        PasswordRecoveryRequest requestParams = new PasswordRecoveryRequest("");


        String json = new ObjectMapper().writeValueAsString(requestParams);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("E-mail is required.")));
    }

    @Test
    @DisplayName("Should return badRequest Email is null")
    void shouldReturnBadRequestIfEmailNull() throws Exception {
        PasswordRecoveryRequest requestParams = new PasswordRecoveryRequest(null);

        String json = new ObjectMapper().writeValueAsString(requestParams);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("E-mail is required.")));
    }

    @Test
    @DisplayName("Should return badRequest Email is invalid")
    void shouldReturnBadRequestIfEmailInvalid() throws Exception {
        PasswordRecoveryRequest requestParams = new PasswordRecoveryRequest(Mocks.faker.name().name());

        String json = new ObjectMapper().writeValueAsString(requestParams);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("Invalid E-mail.")));
    }

    @Test
    @DisplayName("Should return if InternalServerError if Sign in throws unexpected error")
    void shouldReturnInternalServerErrorIfSignupThrowsUnexpectedError() throws Exception {
        PasswordRecoveryRequest requestParams = new PasswordRecoveryRequest(Mocks.faker.internet().emailAddress());

        BDDMockito.doThrow(HttpServerErrorException.InternalServerError.class)
                .when(this.passwordRecovery).recover(requestParams.email());

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
    @DisplayName("Should return ACCEPTED on success")
    void shouldReturnACCEPTEDOnSuccess() throws Exception {
        PasswordRecoveryRequest requestParams = new PasswordRecoveryRequest(Mocks.faker.internet().emailAddress());

        BDDMockito.doNothing().when(this.passwordRecovery).recover(requestParams.email());

        String json = new ObjectMapper().writeValueAsString(requestParams);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("body", Matchers.is("If an account with that email exists, " +
                        "we have sent you an email with the steps to recover your password.")));
    }
}
