package com.walletwise.application.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.walletwise.application.http.ConfirmPasswordRecoveryRequest;
import com.walletwise.domain.entities.exceptions.BusinessException;
import com.walletwise.domain.entities.exceptions.NotFoundException;
import com.walletwise.domain.useCases.ConfirmPasswordRecovery;
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
public class ConfirmPasswordRecoveryControllerTests {
    private final String URL = "/auth/confirm-password-recovery";
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @MockBean
    private ConfirmPasswordRecovery confirmPasswordRecovery;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    @DisplayName("Should return badRequest if token is empty")
    void shouldReturnBadRequestIfTokenEmpty() throws Exception {
        ConfirmPasswordRecoveryRequest requestParams = new ConfirmPasswordRecoveryRequest
                ("", Mocks.faker.internet().password());

        String json = new ObjectMapper().writeValueAsString(requestParams);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("Token is required.")));
    }

    @Test
    @DisplayName("Should return badRequest token is null")
    void shouldReturnBadRequestIfEmailNull() throws Exception {
        ConfirmPasswordRecoveryRequest requestParams = new ConfirmPasswordRecoveryRequest
                ("", Mocks.faker.internet().password());

        String json = new ObjectMapper().writeValueAsString(requestParams);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("Token is required.")));
    }

    @Test
    @DisplayName("Should return badRequest if new password is empty")
    void shouldReturnBadRequestIfNewPasswordEmpty() throws Exception {
        ConfirmPasswordRecoveryRequest requestParams = new ConfirmPasswordRecoveryRequest
                (UUID.randomUUID().toString(), "");

        String json = new ObjectMapper().writeValueAsString(requestParams);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("New Password is required.")));
    }

    @Test
    @DisplayName("Should return badRequest password is null")
    void shouldReturnBadRequestIfNewPasswordNull() throws Exception {
        ConfirmPasswordRecoveryRequest requestParams = new ConfirmPasswordRecoveryRequest
                (UUID.randomUUID().toString(), "");

        String json = new ObjectMapper().writeValueAsString(requestParams);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("New Password is required.")));
    }

    @Test
    @DisplayName("Should return badRequest password is weak")
    void shouldReturnBadRequestIfNewPasswordIsWeak() throws Exception {
        ConfirmPasswordRecoveryRequest requestParams = new ConfirmPasswordRecoveryRequest
                (UUID.randomUUID().toString(), "123");

        String json = new ObjectMapper().writeValueAsString(requestParams);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers
                        .is("Password too weak! Must contain at least 8 characters, " +
                                "one uppercase letter, a special character and a number.")));
    }

    @Test
    @DisplayName("Should return if NotFound if ConfirmPasswordRecovery  throws NotFoundException")
    void shouldReturnNorFoundIfConfirmPasswordRecoveryThrowsNotFoundException() throws Exception {
        ConfirmPasswordRecoveryRequest requestParams = new ConfirmPasswordRecoveryRequest
                (UUID.randomUUID().toString(), "Pass@word01");

        BDDMockito.doThrow(new NotFoundException("User not found."))
                .when(this.confirmPasswordRecovery).confirm(requestParams.token(), requestParams.newPassword());

        String json = new ObjectMapper().writeValueAsString(requestParams);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("body", Matchers
                        .is("User not found.")));
    }

    @Test
    @DisplayName("Should return if badRequest if ConfirmPasswordRecovery  throws BusinessException")
    void shouldReturnBadRequestIfConfirmPasswordRecoveryThrowsBusinessException() throws Exception {
        ConfirmPasswordRecoveryRequest requestParams = new ConfirmPasswordRecoveryRequest
                (UUID.randomUUID().toString(), "Pass@word01");

        BDDMockito.doThrow(new BusinessException("Invalid or expired token."))
                .when(this.confirmPasswordRecovery).confirm(requestParams.token(), requestParams.newPassword());

        String json = new ObjectMapper().writeValueAsString(requestParams);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers
                        .is("Invalid or expired token.")));
    }

    @Test
    @DisplayName("Should return if InternalServerError if Recovery throws unexpected error")
    void shouldReturnInternalServerErrorIfSignupThrowsUnexpectedError() throws Exception {
        ConfirmPasswordRecoveryRequest requestParams = new ConfirmPasswordRecoveryRequest
                (UUID.randomUUID().toString(), "Pass@word01");

        BDDMockito.doThrow(HttpServerErrorException.InternalServerError.class)
                .when(this.confirmPasswordRecovery).confirm(requestParams.token(), requestParams.newPassword());

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
    @DisplayName("Should return OK on success")
    void shouldReturnOKOnSuccess() throws Exception {
        ConfirmPasswordRecoveryRequest requestParams = new ConfirmPasswordRecoveryRequest
                (UUID.randomUUID().toString(), "Pass@word01");

        BDDMockito.doNothing().when(this.confirmPasswordRecovery)
                .confirm(requestParams.token(), requestParams.newPassword());

        String json = new ObjectMapper().writeValueAsString(requestParams);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("body", Matchers.is("Password successfully reset.")));
    }
}
