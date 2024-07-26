package com.walletwise.application.controllers;

import com.walletwise.domain.entities.exceptions.ForbiddenException;
import com.walletwise.domain.entities.models.Profile;
import com.walletwise.domain.useCases.auth.GetUserProfile;
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
public class GetUserProfileControllerTests {
    private final String URL = "/auth/profile";
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @MockBean
    private GetUserProfile getUserProfile;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    @DisplayName("Should return if Forbidden if getUserProfile throws ForbiddenException ")
    void shouldReturnForbiddenIfGetUserProfileThrowsForbiddenException() throws Exception {

        BDDMockito.doThrow(new ForbiddenException("Forbidden.")).when(this.getUserProfile).getUserProfile();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        mvc
                .perform(request)
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("body", Matchers.is("Forbidden.")));
    }

    @Test
    @DisplayName("Should return if InternalServerError if GetUserProfile  throws unexpected error")
    void shouldReturnInternalServerErrorIfGetUserProfileThrowsUnexpectedError() throws Exception {
        BDDMockito.doThrow(HttpServerErrorException.InternalServerError.class)
                .when(this.getUserProfile).getUserProfile();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mvc
                .perform(request)
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("body", Matchers
                        .is("An unexpected error occurred. Please try again later.")));
    }

    @Test
    @DisplayName("Should return OK on success")
    void shouldReturnOKOnSuccess() throws Exception {
        Profile profile = Mocks.profileFactory();

        BDDMockito.when(this.getUserProfile.getUserProfile())
                .thenReturn(profile);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("body.userId", Matchers.is(profile.getUserId().toString())))
                .andExpect(jsonPath("body.firstname", Matchers.is(profile.getFirstname())))
                .andExpect(jsonPath("body.lastname", Matchers.is(profile.getLastname())))
                .andExpect(jsonPath("body.username", Matchers.is(profile.getUsername())))
                .andExpect(jsonPath("body.email", Matchers.is(profile.getEmail())))
                .andExpect(jsonPath("body.image", Matchers.is(profile.getImage())))
                .andExpect(jsonPath("body.theme", Matchers.is(profile.getTheme())));
    }
}
