package com.walletwise.infra.resource.controllers;

import com.walletwise.domain.entities.exceptions.ConflictException;
import com.walletwise.domain.entities.model.User;
import com.walletwise.domain.useCases.Signup;
import com.walletwise.infra.gateways.mappers.UserDTOMapper;
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
    private final Signup signup;
    private final UserDTOMapper mapper;

    public SignupController(Signup signup, UserDTOMapper mapper) {
        this.signup = signup;
        this.mapper = mapper;
    }

    @Override
    @Operation(summary = "Singup")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Returns successful message"),
            @ApiResponse(responseCode = "400", description = "Bad request happened"),
            @ApiResponse(responseCode = "409", description = "Conflict with business rules"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred."),
    })
    public ResponseEntity<Response> perform(@RequestBody SignupRequest request) {
        Response response;
        ResponseEntity<Response> responseEntity;

        String error = this.validate(request);
        if(error != null){
            response =  Response.builder().body(error).build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            User userDomainObject = this.mapper.toUserDomainObject(request);
            this.signup.signup(userDomainObject);
            response =  Response.builder().body("Sign-up successful").build();
            responseEntity = new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (ConflictException ex){
            response =  Response.builder().body(ex.getMessage()).build();
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }catch (Exception ex){
            response =  Response
                    .builder().body("An unexpected error occurred. Please try again later.").build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    @Override
    public List<IValidator> buildValidators(SignupRequest request) {
        List<IValidator> validators = new ArrayList<>();
        validators.addAll(ValidationBuilder.of("Firstname", request.firstname()).required().build());
        validators.addAll(ValidationBuilder.of("Lastname", request.lastname()).required().build());
        validators.addAll(ValidationBuilder.of("Username", request.username()).required().username().build());
        validators.addAll(ValidationBuilder.of("E-mail", request.email()).required().email().build());
        validators.addAll(ValidationBuilder.of("Password", request.password()).required().password().build());
        return validators;
    }
}