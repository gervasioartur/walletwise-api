package com.walletwise.infra.resource.controllers;

import com.walletwise.infra.resource.http.Response;
import com.walletwise.infra.resource.validation.ValidationComposite;
import com.walletwise.infra.resource.validation.contract.IValidator;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

import java.util.List;

@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public abstract class AbstractController<E> {
    public abstract Response perform(E request);

    public List<IValidator> buildValidators(E request) {
        return List.of();
    }

    protected String validate(E request) {
        List<IValidator> validators = this.buildValidators(request);
        return new ValidationComposite(validators).validate();
    }
}
