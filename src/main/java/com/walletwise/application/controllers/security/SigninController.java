package com.walletwise.application.controllers.security;

import com.walletwise.application.controllers.AbstractController;
import com.walletwise.application.http.Response;
import com.walletwise.application.http.SigninRequest;
import com.walletwise.application.validation.ValidationBuilder;
import com.walletwise.application.validation.contract.IValidator;
import com.walletwise.domain.entities.exceptions.UnauthorizedException;
import com.walletwise.domain.useCases.auth.Signin;
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
@RequestMapping("/auth/signin")
public class SigninController extends AbstractController<SigninRequest, Response> {
    private final Signin signin;

    public SigninController(Signin signin) {
        this.signin = signin;
    }

    @Override
    @Operation(summary = "Singin")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns successful message"),
            @ApiResponse(responseCode = "400", description = "Bad request happened"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred."),
    })
    public ResponseEntity<Response> perform(@RequestBody SigninRequest request) {
        Response response;
        ResponseEntity<Response> responseEntity;

        String error = this.validate(request);
        if (error != null) {
            response = Response.builder().body(error).build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            String accessToken = this.signin.signin(request.usernameOrEmail(), request.password());
            response = Response.builder().body(accessToken).build();
            responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UnauthorizedException ex) {
            response = Response.builder().body(ex.getMessage()).build();
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            response = Response.builder()
                    .body("An unexpected error occurred. Please try again later.").build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    @Override
    public List<IValidator> buildValidators(SigninRequest request) {
        List<IValidator> validators = new ArrayList<>();
        validators.addAll(ValidationBuilder.of("Username or Email", request.usernameOrEmail()).required().build());
        validators.addAll(ValidationBuilder.of("Password", request.password()).required().build());
        return validators;
    }
}