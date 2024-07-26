package com.walletwise.application.controllers;

import com.walletwise.application.http.PasswordRecoveryRequest;
import com.walletwise.application.http.Response;
import com.walletwise.application.validation.ValidationBuilder;
import com.walletwise.application.validation.contract.IValidator;
import com.walletwise.domain.useCases.auth.PasswordRecovery;
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
@RequestMapping("/auth/password-recovery")
public class PasswordRecoveryController extends AbstractController<PasswordRecoveryRequest, Response> {
    private final PasswordRecovery passwordRecovery;

    public PasswordRecoveryController(PasswordRecovery passwordRecovery) {
        this.passwordRecovery = passwordRecovery;
    }

    @Override
    @Operation(summary = "Password recovery")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Returns successful message"),
            @ApiResponse(responseCode = "400", description = "Bad request happened"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred."),
    })
    public ResponseEntity<Response> perform(@RequestBody PasswordRecoveryRequest request) {
        Response response;
        ResponseEntity<Response> responseEntity;

        String error = this.validate(request);
        if (error != null) {
            response = Response.builder().body(error).build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            this.passwordRecovery.recover(request.email());
            response = Response.builder()
                    .body("If an account with that email exists, " +
                            "we have sent you an email with the steps to recover your password.").build();
            responseEntity = new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            response = Response.builder()
                    .body("An unexpected error occurred. Please try again later.").build();
            responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    @Override
    public List<IValidator> buildValidators(PasswordRecoveryRequest request) {
        return new ArrayList<>(ValidationBuilder.of("E-mail", request.email()).required().email().build());
    }
}
