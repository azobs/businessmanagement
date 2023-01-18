package com.c2psi.businessmanagement.validators.client.client;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.client.client.ClientDamageOperationDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientPackagingOperationDto;
import com.c2psi.businessmanagement.validators.pos.pos.OperationValidator;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.Instant;
import java.util.*;

public class ClientPackagingOperationValidator {
    /*****************************************************************************************
     *  *Le paramètre operation a valider (cpopDto) ne doit pas etre null
     *  *Le nombre en mouvement doit toujours etre positif ou null
     *  *Le compte packaging associe ne doit pas etre null
     *  *La date de l'operation doit etre antérieur ou egal a la date d'enregistrement
     *  *L'objet de l'operation doit être précise
     *  *Le type de l'opération est obligatoire. Il doit toujours etre précisé parmi ceux
     *      * admis par l'application
     * @param cpopDto
     * @return
     */
    public static List<String> validate(ClientPackagingOperationDto cpopDto){
        List<String> errors = new ArrayList<>();
        if(cpopDto == null){
            errors.add("--le parametre à valider ne peut etre null--");
        }
        else {
            if(cpopDto.getCltpoNumberinmvt() == null){
                errors.add("--le nombre de packaging en mouvement ne saurait null--");
            }
            else {
                if (cpopDto.getCltpoNumberinmvt() <= 0) {
                    errors.add("--le nombre de packaging en mouvement ne saurait être négatif ou null--");
                }
            }

            if(!Optional.ofNullable(cpopDto.getCltpoCltPackagingAccountDto()).isPresent()){
                errors.add("--Le compte qui sera affecte ne saurait etre null--");
            }

            if(!Optional.ofNullable(cpopDto.getCltpoUserbmDto()).isPresent()){
                errors.add("--L'utilisteur qui a effectue l'operation ne saurait etre null--");
            }

            List<String> opt_errors = OperationValidator.validate(
                    cpopDto.getCltpoOperationDto());
            if(opt_errors.size()>0){
                errors.addAll(opt_errors);
            }

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<ClientPackagingOperationDto>> constraintViolations = validator.validate(cpopDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<ClientPackagingOperationDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }

        }
        return errors;
    }
}
