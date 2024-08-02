package com.walletwise.application.controllers.walletwise;

import com.walletwise.application.http.Response;
import com.walletwise.domain.entities.models.Profile;
import com.walletwise.domain.useCases.auth.GetUserProfile;
import com.walletwise.domain.useCases.expenses.ListFixedExpenses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Fixed Expenses")
@RequestMapping("/fixed-expenses")
public class ListFixedExpensesController {
    private final GetUserProfile getUserProfile;
    private final ListFixedExpenses useCase;

    public ListFixedExpensesController(GetUserProfile getUserProfile, ListFixedExpenses useCase) {
        this.getUserProfile = getUserProfile;
        this.useCase = useCase;
    }

    @GetMapping
    @Operation(summary = "Get fixed expenses")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns successful message"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred."),
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Response> perform() {
        Response response;
        ResponseEntity<Response> responseEntity;

        try {
            Profile profile = this.getUserProfile.getUserProfile();
            this.useCase.list(profile.getUserId());
            response = Response.builder().body("Expense successful added.").build();
            responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            response = Response.builder()
                    .body("An unexpected error occurred. Please try again later.").build();
            responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}