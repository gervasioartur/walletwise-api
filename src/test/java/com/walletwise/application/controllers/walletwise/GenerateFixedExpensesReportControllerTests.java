package com.walletwise.application.controllers.walletwise;

import com.walletwise.domain.entities.exceptions.UnexpectedException;
import com.walletwise.domain.entities.models.Profile;
import com.walletwise.domain.useCases.auth.GetUserProfile;
import com.walletwise.domain.useCases.expenses.GenerateFixedExpensesReport;
import com.walletwise.mocks.Mocks;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.OutputStream;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class GenerateFixedExpensesReportControllerTests {
    private final String URL = "/fixed-expenses/report";

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;
    @MockBean
    private GetUserProfile getUserProfile;
    @MockBean
    private GenerateFixedExpensesReport useCase;


    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    @DisplayName("Should return if InternalServerError if use case throws UnexpectedException ")
    void shouldReturnInternalServerErrorIfUseCaseThrowsUnexpectedException() throws Exception {
        Profile profile = Mocks.profileFactory();

        ServletOutputStream outputStream = mock(ServletOutputStream.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(response.getOutputStream()).thenReturn(outputStream);

        when(this.getUserProfile.getUserProfile()).thenReturn(profile);
        doThrow(new UnexpectedException("Simulated exception"))
                .when(useCase).generate(any(UUID.class), any(OutputStream.class));


        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(URL)
                .accept(MediaType.APPLICATION_PDF)
                .contentType(MediaType.APPLICATION_PDF);

        mvc
                .perform(request)
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Should generate report")
    void shouldGenerateReport() throws Exception {
        Profile profile = Mocks.profileFactory();

        ServletOutputStream outputStream = mock(ServletOutputStream.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(response.getOutputStream()).thenReturn(outputStream);

        when(this.getUserProfile.getUserProfile()).thenReturn(profile);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(URL)
                .accept(MediaType.APPLICATION_PDF)
                .contentType(MediaType.APPLICATION_PDF);

        mvc
                .perform(request)
                .andExpect(status().isOk());
    }
}
