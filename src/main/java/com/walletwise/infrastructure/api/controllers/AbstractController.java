package com.walletwise.infrastructure.api.controllers;

import com.walletwise.infrastructure.api.validation.ValidationComposite;
import com.walletwise.infrastructure.api.validation.validor.contract.IValidator;

import java.util.List;

public abstract class AbstractController<E> {
    public  List<IValidator> buildValidators(E request){
        return List.of();
    }

    protected String validate(E request) {
        List<IValidator> validators = this.buildValidators(request);
        return new ValidationComposite(validators).validate();
    }
}
