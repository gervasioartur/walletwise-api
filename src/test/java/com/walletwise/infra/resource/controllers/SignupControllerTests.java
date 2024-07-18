package com.walletwise.infra.resource.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.walletwise.infra.resource.http.SignupRequest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
public class SignupControllerTests {
    private final String URL = "/auth/signup";
    private final Faker faker = new Faker();
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }


    @Test
    @DisplayName("Should return badRequest if Firstname is empty")
    void shouldReturnBadRequestIfFirstnameIsEmpty() throws Exception {
        SignupRequest requestParams =  new SignupRequest(
                "",
                faker.name().lastName(),
                faker.name().username(),
                faker.internet().emailAddress(),
                faker.internet().password());

        String json =  new ObjectMapper().writeValueAsString(requestParams);
        MockHttpServletRequestBuilder request =  MockMvcRequestBuilders
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
        SignupRequest requestParams =  new SignupRequest(
                null,
                faker.name().lastName(),
                faker.name().username(),
                faker.internet().emailAddress(),
                faker.internet().password());

        String json =  new ObjectMapper().writeValueAsString(requestParams);
        MockHttpServletRequestBuilder request =  MockMvcRequestBuilders
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
        SignupRequest requestParams =  new SignupRequest(
                faker.name().firstName(),
                "",
                faker.name().username(),
                faker.internet().emailAddress(),
                faker.internet().password());

        String json =  new ObjectMapper().writeValueAsString(requestParams);
        MockHttpServletRequestBuilder request =  MockMvcRequestBuilders
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
        SignupRequest requestParams =  new SignupRequest(
                faker.name().firstName(),
                null,
                faker.name().username(),
                faker.internet().emailAddress(),
                faker.internet().password());

        String json =  new ObjectMapper().writeValueAsString(requestParams);
        MockHttpServletRequestBuilder request =  MockMvcRequestBuilders
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
        SignupRequest requestParams =  new SignupRequest(
                faker.name().firstName(),
                faker.name().lastName(),
                "",
                faker.internet().emailAddress(),
                faker.internet().password());

        String json =  new ObjectMapper().writeValueAsString(requestParams);
        MockHttpServletRequestBuilder request =  MockMvcRequestBuilders
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
        SignupRequest requestParams =  new SignupRequest(
                faker.name().firstName(),
                faker.name().lastName(),
                "",
                faker.internet().emailAddress(),
                faker.internet().password());

        String json =  new ObjectMapper().writeValueAsString(requestParams);
        MockHttpServletRequestBuilder request =  MockMvcRequestBuilders
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
        SignupRequest requestParams =  new SignupRequest(
                faker.name().firstName(),
                faker.name().lastName(),
                "@Username",
                faker.internet().emailAddress(),
                faker.internet().password());

        String json =  new ObjectMapper().writeValueAsString(requestParams);
        MockHttpServletRequestBuilder request =  MockMvcRequestBuilders
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
        SignupRequest requestParams =  new SignupRequest(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.name().username(),
                "",
                faker.internet().password());

        String json =  new ObjectMapper().writeValueAsString(requestParams);
        MockHttpServletRequestBuilder request =  MockMvcRequestBuilders
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
        SignupRequest requestParams =  new SignupRequest(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.name().username(),
                null,
                faker.internet().password());

        String json =  new ObjectMapper().writeValueAsString(requestParams);
        MockHttpServletRequestBuilder request =  MockMvcRequestBuilders
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
        SignupRequest requestParams =  new SignupRequest(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.name().username(),
                "invalidEmail",
                faker.internet().password());

        String json =  new ObjectMapper().writeValueAsString(requestParams);
        MockHttpServletRequestBuilder request =  MockMvcRequestBuilders
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
        SignupRequest requestParams =  new SignupRequest(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.name().username(),
                faker.internet().emailAddress(),
                "");

        String json =  new ObjectMapper().writeValueAsString(requestParams);
        MockHttpServletRequestBuilder request =  MockMvcRequestBuilders
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
        SignupRequest requestParams =  new SignupRequest(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.name().username(),
                faker.internet().emailAddress(),
                null);

        String json =  new ObjectMapper().writeValueAsString(requestParams);
        MockHttpServletRequestBuilder request =  MockMvcRequestBuilders
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
        SignupRequest requestParams =  new SignupRequest(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.name().username(),
                faker.internet().emailAddress(),
                "123");

        String json =  new ObjectMapper().writeValueAsString(requestParams);
        MockHttpServletRequestBuilder request =  MockMvcRequestBuilders
                .post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("Password too weak! Must contain at least 8 characters, one uppercase letter, a special character and a number.")));
    }
}
