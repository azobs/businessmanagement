package com.c2psi.businessmanagement.validators.client.client;

import com.c2psi.businessmanagement.dtos.client.client.DiversDto;
import com.c2psi.businessmanagement.validators.pos.userbm.AddressValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class DiversValidator {
    public static List<String> validate(DiversDto diversDto){
        List<String> errors = new ArrayList<>();
        Optional<DiversDto> optionalDiversDto = Optional.ofNullable(diversDto);
        if(!optionalDiversDto.isPresent()){
            errors.add("--Le parametre à valider ne peut etre null--");
        }
        else{
            if(!Optional.ofNullable(diversDto.getDiversAddressDto()).isPresent()){
                errors.add("--L'address du divers ne peut etre null--");
            }
            else{
                List<String> errs = AddressValidator.validate(diversDto.getDiversAddressDto());
                if(errs.size()>0){
                    errors.addAll(errs);
                }
            }
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<DiversDto>> constraintViolations = validator.validate(diversDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<DiversDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }
        }
        return errors;
    }
}
