package com.walletwise.application.controllers.walletwise;

import com.walletwise.domain.entities.exceptions.UnexpectedException;
import com.walletwise.domain.entities.models.Profile;
import com.walletwise.domain.useCases.auth.GetUserProfile;
import com.walletwise.domain.useCases.expenses.GenerateFixedExpensesReport;
import io.sentry.Sentry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.OutputStream;

@RestController
@Tag(name = "Fixed Expenses")
@RequestMapping("/fixed-expenses/report")
public class GenerateFixedExpensesReportController {
    private final GetUserProfile getUserProfile;
    private final GenerateFixedExpensesReport useCase;

    public GenerateFixedExpensesReportController(GetUserProfile getUserProfile,
                                                 GenerateFixedExpensesReport useCase) {
        this.getUserProfile = getUserProfile;
        this.useCase = useCase;
    }

    @GetMapping
    @Operation(summary = "Generate fixed expenses report")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns successful message"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred."),
    })
    @SecurityRequirement(name = "bearerAuth")
    public void perform(HttpServletResponse httpServletResponse) throws IOException {
        try {
            httpServletResponse.setContentType("application/pdf");
            httpServletResponse.setHeader("Content-Disposition", "attachment; filename=fixed-expenses.pdf");
            OutputStream outputStream = httpServletResponse.getOutputStream();
            Profile profile = this.getUserProfile.getUserProfile();
            this.useCase.generate(profile.getUserId(), outputStream);
        } catch (UnexpectedException | IOException ex) {
            Sentry.captureException(ex);
            httpServletResponse.setContentType("application/json");
            httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}