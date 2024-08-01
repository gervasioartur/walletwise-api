package com.walletwise.application.controllers.walletwise;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.walletwise.application.http.AddFixedExpenseRequest;
import com.walletwise.domain.entities.enums.ExpenseCategoryEnum;
import com.walletwise.domain.entities.enums.PaymentFrequencyEnum;
import com.walletwise.domain.entities.models.FixedExpense;
import com.walletwise.domain.entities.models.Profile;
import com.walletwise.domain.useCases.auth.GetUserProfile;
import com.walletwise.domain.useCases.expenses.AddFixedExpense;
import com.walletwise.infra.gateways.mappers.walletwise.FixedExpenseDTOMapper;
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

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AddFixedExpenseControllerTests {
    private final String URL = "/fixed-expenses";

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @MockBean
    private AddFixedExpense useCase;
    @MockBean
    private FixedExpenseDTOMapper mapper;
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
        AddFixedExpenseRequest requestParams = Mocks.addFixedExpenseRequest();
        FixedExpense fixedExpense = Mocks.formFixedExpenseRequestToObj(profile.getUserId(), requestParams);

        BDDMockito.when(this.getUserProfile.getUserProfile()).thenReturn(profile);
        BDDMockito.when(this.mapper.toFixedExpenseDomainObj(profile.getUserId(),requestParams)).thenReturn(fixedExpense);
        BDDMockito.doThrow(HttpServerErrorException.InternalServerError.class).when(this.useCase).add(fixedExpense);

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
    @DisplayName("Should return if badRequest if description is empty")
    void shouldReturnBadRequestIfDescriptionIsEmpty() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        AddFixedExpenseRequest requestParams = new AddFixedExpenseRequest(
                "",
                (double) Mocks.faker.number().randomNumber(),
                ExpenseCategoryEnum.SCHOOL.getValue(),
                10,
                Date.from(now.atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(now.plusDays(26).atZone(ZoneId.systemDefault()).toInstant()),
                PaymentFrequencyEnum.WEEKLY.getValue());


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
                        .is("Description is required.")));
    }

    @Test
    @DisplayName("Should return if badRequest if description is null")
    void shouldReturnBadRequestIfDescriptionIsNull() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        AddFixedExpenseRequest requestParams = new AddFixedExpenseRequest(
                null,
                (double) Mocks.faker.number().randomNumber(),
                ExpenseCategoryEnum.SCHOOL.getValue(),
                10,
                Date.from(now.atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(now.plusDays(26).atZone(ZoneId.systemDefault()).toInstant()),
                PaymentFrequencyEnum.WEEKLY.getValue());


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
                        .is("Description is required.")));
    }

    @Test
    @DisplayName("Should return if badRequest if Amount is empty")
    void shouldReturnBadRequestIfAmountIsEmpty() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        AddFixedExpenseRequest requestParams = new AddFixedExpenseRequest(
                Mocks.faker.lorem().paragraph(),
                (double) 0,
                ExpenseCategoryEnum.SCHOOL.getValue(),
                10,
                Date.from(now.atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(now.plusDays(26).atZone(ZoneId.systemDefault()).toInstant()),
                PaymentFrequencyEnum.WEEKLY.getValue());


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
                        .is("Amount is required.")));
    }
}