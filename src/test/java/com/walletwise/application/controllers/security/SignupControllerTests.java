package com.walletwise.application.controllers.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.walletwise.application.dto.security.SignupRequest;
import com.walletwise.domain.entities.exceptions.ConflictException;
import com.walletwise.domain.entities.models.security.User;
import com.walletwise.domain.useCases.auth.Signup;
import com.walletwise.infrastructure.gateways.mappers.security.UserDTOMapper;
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
public class SignupControllerTests {
    private final String URL = "/auth/signup";
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @MockBean
    private Signup signup;
    @MockBean
    private UserDTOMapper mapper;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }


    @Test
    @DisplayName("Should return badRequest if Firstname is empty")
    void shouldReturnBadRequestIfFirstnameIsEmpty() throws Exception {
        SignupRequest requestParams = new SignupRequest(
                "",
                Mocks.faker.name().lastName(),
                Mocks.faker.name().username(),
                Mocks.faker.internet().emailAddress(),
                Mocks.faker.internet().password());

        String json = new ObjectMapper().writeValueAsString(requestParams);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("Firstname is required.")));
    }

    @Test
    @DisplayName("Should return badRequest if Firstname is null")
    void shouldReturnBadRequestIfFirstnameIsNull() throws Exception {
        SignupRequest requestParams = new SignupRequest(
                null,
                Mocks.faker.name().lastName(),
                Mocks.faker.name().username(),
                Mocks.faker.internet().emailAddress(),
                Mocks.faker.internet().password());

        String json = new ObjectMapper().writeValueAsString(requestParams);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("Firstname is required.")));
    }

    @Test
    @DisplayName("Should return badRequest if Lastname is empty")
    void shouldReturnBadRequestIfLastnameIsEmpty() throws Exception {
        SignupRequest requestParams = new SignupRequest(
                Mocks.faker.name().firstName(),
                "",
                Mocks.faker.name().username(),
                Mocks.faker.internet().emailAddress(),
                Mocks.faker.internet().password());

        String json = new ObjectMapper().writeValueAsString(requestParams);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("Lastname is required.")));
    }

    @Test
    @DisplayName("Should return badRequest if Lastname is null")
    void shouldReturnBadRequestIfLastnameIsNull() throws Exception {
        SignupRequest requestParams = new SignupRequest(
                Mocks.faker.name().firstName(),
                null,
                Mocks.faker.name().username(),
                Mocks.faker.internet().emailAddress(),
                Mocks.faker.internet().password());

        String json = new ObjectMapper().writeValueAsString(requestParams);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("Lastname is required.")));
    }

    @Test
    @DisplayName("Should return badRequest if Username is empty")
    void shouldReturnBadRequestIfUsernameIsEmpty() throws Exception {
        SignupRequest requestParams = new SignupRequest(
                Mocks.faker.name().firstName(),
                Mocks.faker.name().lastName(),
                "",
                Mocks.faker.internet().emailAddress(),
                Mocks.faker.internet().password());

        String json = new ObjectMapper().writeValueAsString(requestParams);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("Username is required.")));
    }

    @Test
    @DisplayName("Should return badRequest if Username is null")
    void shouldReturnBadRequestIfUsernameIsNull() throws Exception {
        SignupRequest requestParams = new SignupRequest(
                Mocks.faker.name().firstName(),
                Mocks.faker.name().lastName(),
                "",
                Mocks.faker.internet().emailAddress(),
                Mocks.faker.internet().password());

        String json = new ObjectMapper().writeValueAsString(requestParams);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("Username is required.")));
    }

    @Test
    @DisplayName("Should return badRequest if Username is invalid")
    void shouldReturnBadRequestIfUsernameIsInvalid() throws Exception {
        SignupRequest requestParams = new SignupRequest(
                Mocks.faker.name().firstName(),
                Mocks.faker.name().lastName(),
                "@Username",
                Mocks.faker.internet().emailAddress(),
                Mocks.faker.internet().password());

        String json = new ObjectMapper().writeValueAsString(requestParams);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("Invalid Username! The username should not start with special character.")));
    }

    @Test
    @DisplayName("Should return badRequest if E-mail is empty")
    void shouldReturnBadRequestIfEmailIsEmpty() throws Exception {
        SignupRequest requestParams = new SignupRequest(
                Mocks.faker.name().firstName(),
                Mocks.faker.name().lastName(),
                Mocks.faker.name().username(),
                "",
                Mocks.faker.internet().password());

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
    @DisplayName("Should return badRequest if E-mail is null")
    void shouldReturnBadRequestIfEmailIsNull() throws Exception {
        SignupRequest requestParams = new SignupRequest(
                Mocks.faker.name().firstName(),
                Mocks.faker.name().lastName(),
                Mocks.faker.name().username(),
                null,
                Mocks.faker.internet().password());

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
    @DisplayName("Should return badRequest if E-mail is invalid")
    void shouldReturnBadRequestIfEmailIsInvalid() throws Exception {
        SignupRequest requestParams = new SignupRequest(
                Mocks.faker.name().firstName(),
                Mocks.faker.name().lastName(),
                Mocks.faker.name().username(),
                "invalidEmail",
                Mocks.faker.internet().password());

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
    @DisplayName("Should return badRequest if Password is empty")
    void shouldReturnBadRequestIfPasswordIsEmpty() throws Exception {
        SignupRequest requestParams = new SignupRequest(
                Mocks.faker.name().firstName(),
                Mocks.faker.name().lastName(),
                Mocks.faker.name().username(),
                Mocks.faker.internet().emailAddress(),
                "");

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
        SignupRequest requestParams = new SignupRequest(
                Mocks.faker.name().firstName(),
                Mocks.faker.name().lastName(),
                Mocks.faker.name().username(),
                Mocks.faker.internet().emailAddress(),
                null);

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
    @DisplayName("Should return badRequest if Password is weak")
    void shouldReturnBadRequestIfPasswordIsWeak() throws Exception {
        SignupRequest requestParams = new SignupRequest(
                Mocks.faker.name().firstName(),
                Mocks.faker.name().lastName(),
                Mocks.faker.name().username(),
                Mocks.faker.internet().emailAddress(),
                "123");

        String json = new ObjectMapper().writeValueAsString(requestParams);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("Password too weak! Must contain at least 8 characters, one uppercase letter, a special character and a number.")));
    }

    @Test
    @DisplayName("Should return conflict if username is already taken")
    void shouldReturnConflictIfUsernameIsAlreadyTaken() throws Exception {
        SignupRequest requestParams = Mocks.signupRequestToUserFactory();
        User userDomainObject = Mocks.fromSignupRequestToUserFactory(requestParams);

        BDDMockito.when(this.mapper.toUserDomainObject(requestParams)).thenReturn(userDomainObject);
        BDDMockito.doThrow(new ConflictException("Username already exists."))
                .when(this.signup).signup(userDomainObject);

        String json = new ObjectMapper().writeValueAsString(requestParams);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(status().isConflict())
                .andExpect(jsonPath("body", Matchers.is("Username already exists.")));
    }

    @Test
    @DisplayName("Should return conflict if E-mail is already in use")
    void shouldReturnConflictIfEmailIsAlreadyInUse() throws Exception {
        SignupRequest requestParams = Mocks.signupRequestToUserFactory();
        User userDomainObject = Mocks.fromSignupRequestToUserFactory(requestParams);

        BDDMockito.when(this.mapper.toUserDomainObject(requestParams)).thenReturn(userDomainObject);
        BDDMockito.doThrow(new ConflictException("E-mail already in use."))
                .when(this.signup).signup(userDomainObject);

        String json = new ObjectMapper().writeValueAsString(requestParams);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(status().isConflict())
                .andExpect(jsonPath("body", Matchers.is("E-mail already in use.")));
    }

    @Test
    @DisplayName("Should return if InternalServerError if Signup throws unexpected error")
    void shouldReturnInternalServerErrorIfSignupThrowsUnexpectedError() throws Exception {
        SignupRequest requestParams = Mocks.signupRequestToUserFactory();
        User userDomainObject = Mocks.fromSignupRequestToUserFactory(requestParams);

        BDDMockito.when(this.mapper.toUserDomainObject(requestParams)).thenReturn(userDomainObject);
        BDDMockito.doThrow(HttpServerErrorException.InternalServerError.class)
                .when(this.signup).signup(userDomainObject);

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
    @DisplayName("Should return Created on save success")
    void shouldReturnCreatedOnSaveSuccess() throws Exception {
        SignupRequest requestParams = Mocks.signupRequestToUserFactory();
        User userDomainObject = Mocks.fromSignupRequestToUserFactory(requestParams);

        BDDMockito.when(this.mapper.toUserDomainObject(requestParams)).thenReturn(userDomainObject);
        BDDMockito.doNothing().when(this.signup).signup(userDomainObject);

        String json = new ObjectMapper().writeValueAsString(requestParams);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("body", Matchers.is("Sign-up successful")));
    }
}
