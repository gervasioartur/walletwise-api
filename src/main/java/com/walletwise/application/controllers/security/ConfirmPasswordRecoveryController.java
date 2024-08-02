package com.walletwise.application.controllers.security;

import com.walletwise.application.controllers.AbstractController;
import com.walletwise.application.dto.walletwise.ConfirmPasswordRecoveryRequest;
import com.walletwise.application.dto.Response;
import com.walletwise.application.validation.ValidationBuilder;
import com.walletwise.application.validation.contract.IValidator;
import com.walletwise.domain.entities.exceptions.BusinessException;
import com.walletwise.domain.entities.exceptions.NotFoundException;
import com.walletwise.domain.useCases.auth.ConfirmPasswordRecovery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Tag(name = "Authentication")
@RequestMapping("/auth/confirm-password-recovery")
public class ConfirmPasswordRecoveryController extends AbstractController<ConfirmPasswordRecoveryRequest, Response> {
    private final ConfirmPasswordRecovery confirmPasswordRecovery;

    public ConfirmPasswordRecoveryController(ConfirmPasswordRecovery confirmPasswordRecovery) {
        this.confirmPasswordRecovery = confirmPasswordRecovery;
    }

    @Override
    @Operation(summary = "Password recovery")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns successful message"),
            @ApiResponse(responseCode = "400", description = "Bad request happened"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred."),
    })
    public ResponseEntity<Response> perform(@RequestBody ConfirmPasswordRecoveryRequest request) {
        Response response;
        ResponseEntity<Response> responseEntity;

        String error = this.validate(request);
        if (error != null) {
            response = Response.builder().body(error).build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            this.confirmPasswordRecovery.confirm(request.token(), request.newPassword());
            response = Response.builder().body("Password successfully reset.").build();
            responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
        } catch (BusinessException ex) {
            response = Response.builder().body(ex.getMessage()).build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (NotFoundException ex) {
            response = Response.builder().body(ex.getMessage()).build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            response = Response.builder()
                    .body("An unexpected error occurred. Please try again later.").build();
            responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    @Override
    public List<IValidator> buildValidators(ConfirmPasswordRecoveryRequest request) {
        List<IValidator> validators = new ArrayList<>();
        validators.addAll(ValidationBuilder.of("Token", request.token()).required().build());
        validators.addAll(ValidationBuilder.of("New Password", request.newPassword()).required().password().build());
        return validators;
    }
}
