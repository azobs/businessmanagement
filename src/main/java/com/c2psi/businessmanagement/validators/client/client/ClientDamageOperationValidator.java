package com.c2psi.businessmanagement.validators.client.client;


import com.c2psi.businessmanagement.dtos.client.client.ClientDamageOperationDto;
import com.c2psi.businessmanagement.validators.pos.pos.OperationValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

public class ClientDamageOperationValidator {
    /*******************************************************************************************
     *  *Le paramètre operation a valider (cdopDto) ne doit pas etre null
     *  *Le nombre en mouvement doit toujours etre positif ou null
     *  *Le compte capsule associe ne doit pas etre null
     *  *L'operation associe doit etre valide
     * @param cdopDto
     * @return
     */
    public static List<String> validate(ClientDamageOperationDto cdopDto){
        List<String> errors = new ArrayList<>();
        if(cdopDto == null){
            errors.add("--Le parametre à valider ne peut etre null--");
        }
        else{
            if(Optional.ofNullable(cdopDto.getCltdoNumberinmvt()).isPresent()) {
                if (cdopDto.getCltdoNumberinmvt() <= 0) {
                    errors.add("--Le nombre d'article en mouvement ne saurait être négatif ou null--");
                }
            }
            else{
                errors.add("--Le nombre d'article en mvt ne peut etre null--");
            }

            if(!Optional.ofNullable(cdopDto.getCltdoCltDamageAccountDto()).isPresent()){
                errors.add("--Le compte qui sera affecte ne saurait etre null--");
            }

            if(!Optional.ofNullable(cdopDto.getCltdoUserbmDto()).isPresent()){
                errors.add("--L'utilisteur qui a effectue l'operation ne saurait etre null--");
            }

            List<String> opt_errors = OperationValidator.validate(
                    cdopDto.getCltdoOperationDto());
            if(opt_errors.size()>0){
                errors.addAll(opt_errors);
            }

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<ClientDamageOperationDto>> constraintViolations = validator.validate(cdopDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<ClientDamageOperationDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }

        }
        return errors;
    }
}
