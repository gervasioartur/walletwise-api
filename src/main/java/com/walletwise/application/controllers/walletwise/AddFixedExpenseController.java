package com.walletwise.application.controllers.walletwise;

import com.walletwise.application.controllers.AbstractController;
import com.walletwise.application.http.AddFixedExpenseRequest;
import com.walletwise.application.http.Response;
import com.walletwise.application.validation.ValidationBuilder;
import com.walletwise.application.validation.contract.IValidator;
import com.walletwise.domain.entities.models.FixedExpense;
import com.walletwise.domain.entities.models.Profile;
import com.walletwise.domain.useCases.auth.GetUserProfile;
import com.walletwise.domain.useCases.expenses.AddFixedExpense;
import com.walletwise.infra.gateways.mappers.walletwise.FixedExpenseDTOMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Tag(name = "Fixed Expenses")
@RequestMapping("/fixed-expenses")
public class AddFixedExpenseController extends AbstractController<AddFixedExpenseRequest, Response> {
    private final GetUserProfile getUserProfile;
    private final FixedExpenseDTOMapper mapper;
    private final AddFixedExpense useCase;

    public AddFixedExpenseController(GetUserProfile getUserProfile,
                                     FixedExpenseDTOMapper mapper,
                                     AddFixedExpense useCase) {
        this.getUserProfile = getUserProfile;
        this.mapper = mapper;
        this.useCase = useCase;
    }

    @Override
    @PostMapping
    @Operation(summary = "Add fixed expense")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Returns successful message"),
            @ApiResponse(responseCode = "400", description = "Bad request happened"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred."),
    })
    public ResponseEntity<Response> perform(@RequestBody AddFixedExpenseRequest request) {
        Response response;
        ResponseEntity<Response> responseEntity;

        String error = this.validate(request);
        if (error != null) {
            response = Response.builder().body(error).build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            Profile profile = this.getUserProfile.getUserProfile();
            FixedExpense fixedExpense = this.mapper.toFixedExpenseDomainObj(profile.getUserId(), request);
            this.useCase.add(fixedExpense);
            response = Response.builder().body("Expense successful added.").build();
            responseEntity = new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception ex) {
            response = Response.builder()
                    .body("An unexpected error occurred. Please try again later.").build();
            responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return responseEntity;
    }

    @Override
    public List<IValidator> buildValidators(AddFixedExpenseRequest request) {
        List<IValidator> validators = new ArrayList<>();
        validators.addAll(ValidationBuilder.of("Description", request.description()).required().build());
        validators.addAll(ValidationBuilder.of("Amount", request.amount()).required().build());
        validators.addAll(ValidationBuilder.of("Category", request.category()).required().expenseCategory().build());
        validators.addAll(ValidationBuilder.of("Due day", request.dueDay()).required().dueDay().build());
        validators.addAll(ValidationBuilder.of("Start date", request.startDate()).required().build());
        validators.addAll(ValidationBuilder.of("End date", request.endDate()).required().endDate(request.startDate()).build());
        validators.addAll(ValidationBuilder.of("Payment frequency", request.paymentFrequency()).required().paymentMethod().build());
        return validators;
    }
}