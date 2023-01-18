package com.c2psi.businessmanagement.validators.pos.userbm;

import com.c2psi.businessmanagement.dtos.pos.userbm.AddressDto;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class AddressValidator {
    public static List<String> validate(AddressDto addressDto){
        List<String> errors = new ArrayList<>();

        if(!Optional.ofNullable(addressDto).isPresent()){
            errors.add("--Le parametre a valider ne saurait etre null--");
        }
        else {
            /**
             * L'adresse email peut être vide mais si elle existe il faut qu'elle soit bonne
             */
            if (Optional.ofNullable(addressDto.getEmail()).isPresent()){
                if (!StringUtils.hasLength(addressDto.getEmail())) {
                    errors.add("--L'email ne peut etre vide--");
                }
            }
            if(!StringUtils.hasLength(addressDto.getNumtel1())){
                errors.add("--Le premier numero de telephone ne peut etre vide--");
            }
            if(!StringUtils.hasLength(addressDto.getNumtel2())){
                errors.add("--Le second numero de telephone ne peut etre vide--");
            }

            //test des annotations de validation
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<AddressDto>> constraintViolations = validator.validate(addressDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<AddressDto> contraintes : constraintViolations) {
                /*System.out.println(contraintes.getRootBeanClass().getSimpleName()+
                        "." + contraintes.getPropertyPath() + " " + contraintes.getMessage());*/
                    errors.add(contraintes.getMessage());
                }
            }
        }
        return errors;
    }
}
