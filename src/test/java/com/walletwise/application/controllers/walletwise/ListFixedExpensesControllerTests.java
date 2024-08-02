package com.walletwise.application.controllers.walletwise;

import com.walletwise.domain.entities.models.FixedExpense;
import com.walletwise.domain.entities.models.Profile;
import com.walletwise.domain.useCases.auth.GetUserProfile;
import com.walletwise.domain.useCases.expenses.ListFixedExpenses;
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

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ListFixedExpensesControllerTests {
    private final String URL = "/fixed-expenses";

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @MockBean
    private ListFixedExpenses useCase;
    @MockBean
    private GetUserProfile getUserProfile;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    @DisplayName("Should return if InternalServerError if use case throws unexpected error")
    void shouldReturnInternalServerErrorIfUseCaseThrowsUnexpectedError() throws Exception {
        Profile profile = Mocks.profileFactory();

        BDDMockito.when(this.getUserProfile.getUserProfile()).thenReturn(profile);
        BDDMockito.doThrow(HttpServerErrorException.InternalServerError.class).when(this.useCase)
                .list(profile.getUserId());

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
    @DisplayName("Should return lis of fixed expenses on success")
    void shouldReturnListOfFixedExpenseOnSuccess() throws Exception {
        Profile profile = Mocks.profileFactory();
        List<FixedExpense> fixedExpenseList = Mocks.fixedExpenseListFactory(profile.getUserId());

        BDDMockito.when(this.getUserProfile.getUserProfile()).thenReturn(profile);
        BDDMockito.when(this.useCase.list(profile.getUserId())).thenReturn(fixedExpenseList);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("body", Matchers.iterableWithSize(fixedExpenseList.size())));
    }

}
