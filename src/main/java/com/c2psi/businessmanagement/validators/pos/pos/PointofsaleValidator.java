package com.c2psi.businessmanagement.validators.pos.pos;

import com.c2psi.businessmanagement.dtos.pos.pos.OperationDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.validators.pos.userbm.AddressValidator;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class PointofsaleValidator {
    /***************************************************************************************
     * *Le parametre a valider ne doit pas etre null
     * *Le nom du point de vente ne doit etre ni null ni vide
     * *L'adresse du point de vente doit etre valide
     * *L'entreprise auquel appartient le point de vente ne doit pas etre null
     * *Le compte cash du point de vente ne doit pas etre null
     * @param posDto
     * @return
     */
    public static List<String> validate(PointofsaleDto posDto){
        List<String> errors = new ArrayList<String>();
        if(!Optional.ofNullable(posDto).isPresent()){
            errors.add("--Le parametre a valider ne doit pas etre null--");
        }
        else{
            if(!Optional.ofNullable(posDto.getPosAddressDto()).isPresent()){
                errors.add("--L'address du Pointofsale ne peut etre null--");
            }
            else{
                List<String> errs = AddressValidator.validate(posDto.getPosAddressDto());
                if(errs.size()>0){
                    errors.addAll(errs);
                }
            }

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<PointofsaleDto>> constraintViolations = validator.validate(posDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<PointofsaleDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }
        }
        return errors;
    }
}
