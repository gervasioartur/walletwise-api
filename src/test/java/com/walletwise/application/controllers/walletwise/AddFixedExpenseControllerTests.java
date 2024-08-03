package com.walletwise.application.controllers.walletwise;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.walletwise.application.dto.walletwise.AddFixedExpenseRequest;
import com.walletwise.domain.entities.enums.ExpenseCategoryEnum;
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
        BDDMockito.when(this.mapper.toFixedExpenseDomainObj(profile.getUserId(), requestParams)).thenReturn(fixedExpense);
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
                Date.from(now.plusDays(26).atZone(ZoneId.systemDefault()).toInstant()));


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
                Date.from(now.plusDays(26).atZone(ZoneId.systemDefault()).toInstant()));


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
                Date.from(now.plusDays(26).atZone(ZoneId.systemDefault()).toInstant()));


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

    @Test
    @DisplayName("Should return if badRequest if Category is empty")
    void shouldReturnBadRequestIfCategoryIsEmpty() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        AddFixedExpenseRequest requestParams = new AddFixedExpenseRequest(
                Mocks.faker.lorem().paragraph(),
                (double) Mocks.faker.number().randomNumber(),
                "",
                10,
                Date.from(now.atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(now.plusDays(26).atZone(ZoneId.systemDefault()).toInstant()));


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
                        .is("Category is required.")));
    }

    @Test
    @DisplayName("Should return if badRequest if Category is null")
    void shouldReturnBadRequestIfCategoryIsNull() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        AddFixedExpenseRequest requestParams = new AddFixedExpenseRequest(
                Mocks.faker.lorem().paragraph(),
                (double) Mocks.faker.number().randomNumber(),
                null,
                10,
                Date.from(now.atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(now.plusDays(26).atZone(ZoneId.systemDefault()).toInstant()));


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
                        .is("Category is required.")));
    }

    @Test
    @DisplayName("Should return if badRequest if Category is invalid")
    void shouldReturnBadRequestIfCategoryIsInvalid() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        AddFixedExpenseRequest requestParams = new AddFixedExpenseRequest(
                Mocks.faker.lorem().paragraph(),
                (double) Mocks.faker.number().randomNumber(),
                Mocks.faker.lorem().word(),
                10,
                Date.from(now.atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(now.plusDays(26).atZone(ZoneId.systemDefault()).toInstant()));


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
                        .is("Invalid category! You must choose a category between " +
                                "FOOD,TRANSPORT,RENT,ENTERTAINMENT,SCHOOL or OTHERS.")));
    }

    @Test
    @DisplayName("Should return if badRequest if Due day is empty")
    void shouldReturnBadRequestIfDueDayIsEmpty() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        AddFixedExpenseRequest requestParams = new AddFixedExpenseRequest(
                Mocks.faker.lorem().paragraph(),
                (double) Mocks.faker.number().randomNumber(),
                ExpenseCategoryEnum.SCHOOL.getValue(),
                0,
                Date.from(now.atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(now.plusDays(26).atZone(ZoneId.systemDefault()).toInstant()));


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
                        .is("Due day is required.")));
    }

    @Test
    @DisplayName("Should return if badRequest if Due day is invalid")
    void shouldReturnBadRequestIfDueDayIsInvalid() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        AddFixedExpenseRequest requestParams = new AddFixedExpenseRequest(
                Mocks.faker.lorem().paragraph(),
                (double) Mocks.faker.number().randomNumber(),
                ExpenseCategoryEnum.SCHOOL.getValue(),
                32,
                Date.from(now.atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(now.plusDays(26).atZone(ZoneId.systemDefault()).toInstant()));


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
                        .is("Invalid due day! Expiration day must be between 1 to 31.")));
    }

    @Test
    @DisplayName("Should return if badRequest if startDate is null")
    void shouldReturnBadRequestIfStartDateIsNull() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        AddFixedExpenseRequest requestParams = new AddFixedExpenseRequest(
                Mocks.faker.lorem().paragraph(),
                (double) Mocks.faker.number().randomNumber(),
                ExpenseCategoryEnum.SCHOOL.getValue(),
                31,
                null,
                Date.from(now.plusDays(26).atZone(ZoneId.systemDefault()).toInstant()));


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
                        .is("Start date is required.")));
    }

    @Test
    @DisplayName("Should return if badRequest if endDate is null")
    void shouldReturnBadRequestIfEndDateIsNull() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        AddFixedExpenseRequest requestParams = new AddFixedExpenseRequest(
                Mocks.faker.lorem().paragraph(),
                (double) Mocks.faker.number().randomNumber(),
                ExpenseCategoryEnum.SCHOOL.getValue(),
                31,
                Date.from(now.atZone(ZoneId.systemDefault()).toInstant()),
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
                .andExpect(jsonPath("body", Matchers
                        .is("End date is required.")));
    }

    @Test
    @DisplayName("Should return if badRequest if end date before start date")
    void shouldReturnBadRequestIfEndDateIsAfterStartDate() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        AddFixedExpenseRequest requestParams = new AddFixedExpenseRequest(
                Mocks.faker.lorem().paragraph(),
                (double) Mocks.faker.number().randomNumber(),
                ExpenseCategoryEnum.SCHOOL.getValue(),
                31,
                Date.from(now.plusDays(27).atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(now.plusDays(26).atZone(ZoneId.systemDefault()).toInstant()));

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
                        .is("The end date must be after start date.")));
    }

    @Test
    @DisplayName("Should return if Created on success")
    void shouldReturnCreatedOnSuccess() throws Exception {
        Profile profile = Mocks.profileFactory();
        AddFixedExpenseRequest requestParams = Mocks.addFixedExpenseRequest();
        FixedExpense fixedExpense = Mocks.formFixedExpenseRequestToObj(profile.getUserId(), requestParams);

        BDDMockito.when(this.getUserProfile.getUserProfile()).thenReturn(profile);
        BDDMockito.when(this.mapper.toFixedExpenseDomainObj(profile.getUserId(), requestParams)).thenReturn(fixedExpense);
        BDDMockito.doNothing().when(this.useCase).add(fixedExpense);

        String json = new ObjectMapper().writeValueAsString(requestParams);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("body", Matchers
                        .is("Expense successful added.")));
    }
}