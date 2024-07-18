package com.walletwise.infra.resource.controllers;

import com.walletwise.domain.useCases.Signup;
import com.walletwise.infra.resource.http.Response;
import com.walletwise.infra.resource.http.SignupRequest;
import com.walletwise.infra.resource.validation.ValidationBuilder;
import com.walletwise.infra.resource.validation.contract.IValidator;
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
@RequestMapping("/auth/signup")
public class SignupController extends AbstractController<SignupRequest, Response> {

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Singup")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Returns successful message"),
            @ApiResponse(responseCode = "400", description = "Bad request happened"),
            @ApiResponse(responseCode = "409", description = "Conflict with business rules"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred."),
    })
    public ResponseEntity<Response> perform(@RequestBody SignupRequest request) {
        Response response = null;
        ResponseEntity<Response> responseEntity = null;

        String error = this.validate(request);
        if(error != null){
            response =  Response.builder().body(error).build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }


        return null;
    }

    @Override
    public List<IValidator> buildValidators(SignupRequest request) {
        List<IValidator> validators = new ArrayList<>();
        validators.addAll(ValidationBuilder.of("Firstname", request.firstname()).required().build());
        validators.addAll(ValidationBuilder.of("Lastname", request.lastname()).required().build());
        return validators;
    }
}