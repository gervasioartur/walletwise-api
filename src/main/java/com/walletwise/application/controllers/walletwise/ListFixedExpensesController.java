package com.walletwise.application.controllers.walletwise;

import com.walletwise.application.http.FixedExpenseResponse;
import com.walletwise.application.http.Response;
import com.walletwise.domain.entities.models.FixedExpense;
import com.walletwise.domain.entities.models.Profile;
import com.walletwise.domain.useCases.auth.GetUserProfile;
import com.walletwise.domain.useCases.expenses.ListFixedExpenses;
import com.walletwise.infra.gateways.mappers.walletwise.FixedExpenseDTOMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Fixed Expenses")
@RequestMapping("/fixed-expenses")
public class ListFixedExpensesController {
    private final GetUserProfile getUserProfile;
    private final FixedExpenseDTOMapper mapper;
    private final ListFixedExpenses useCase;

    public ListFixedExpensesController(GetUserProfile getUserProfile,
                                       FixedExpenseDTOMapper mapper,
                                       ListFixedExpenses useCase) {
        this.getUserProfile = getUserProfile;
        this.mapper = mapper;
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
            List<FixedExpense> fixedExpenseList = this.useCase.list(profile.getUserId());
            List<FixedExpenseResponse> result = this.mapper.toFixedExpenseListResponse(fixedExpenseList);
            response = Response.builder().body(result).build();
            responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            response = Response.builder()
                    .body("An unexpected error occurred. Please try again later.").build();
            responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}