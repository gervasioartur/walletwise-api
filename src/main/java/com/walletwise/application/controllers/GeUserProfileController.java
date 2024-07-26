package com.walletwise.application.controllers;

import com.walletwise.application.http.Response;
import com.walletwise.domain.entities.exceptions.ForbiddenException;
import com.walletwise.domain.entities.models.Profile;
import com.walletwise.domain.useCases.auth.GetUserProfile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Authentication")
@RequestMapping("/auth/profile")
public class GeUserProfileController {
    private final GetUserProfile getUserProfile;

    public GeUserProfileController(GetUserProfile getUserProfile) {
        this.getUserProfile = getUserProfile;
    }

    @Operation(summary = "Get user profile")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns user Profile"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred."),
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Response> perform() {
        Response response;
        ResponseEntity<Response> responseEntity;

        try {
            Profile profile = this.getUserProfile.getUserProfile();
            response = Response.builder().body(profile).build();
            responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ForbiddenException ex) {
            response = Response.builder().body(ex.getMessage()).build();
            responseEntity = new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        } catch (Exception ex) {
            response = Response.builder()
                    .body("An unexpected error occurred. Please try again later.").build();
            responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }
}
