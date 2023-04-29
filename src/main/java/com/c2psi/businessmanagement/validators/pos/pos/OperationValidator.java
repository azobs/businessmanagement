package com.c2psi.businessmanagement.validators.pos.pos;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.pos.pos.EnterpriseDto;
import com.c2psi.businessmanagement.dtos.pos.pos.OperationDto;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.Instant;
import java.util.*;

public class OperationValidator {
    /*************************************************************************************
     * *Le parametre a valider ne peut etre null
     * *La date de l'operation ne peut etre ulterieure a la date courante
     * *L'objet de l'operation ne peut etre ni vide ni null
     * *Le type de l'operation ne peut etre null et doit avoir une valeur prevu par le systeme
     * @param optDto
     * @return
     */
    public static List<String> validate(OperationDto optDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(optDto).isPresent()){
            errors.add("--le parametre a valider ne saurait etre null--");
        }
        else {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<OperationDto>> constraintViolations = validator.validate(optDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<OperationDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }
        }
        return errors;
    }
}
