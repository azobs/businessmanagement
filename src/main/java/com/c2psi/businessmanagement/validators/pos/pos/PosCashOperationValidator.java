package com.c2psi.businessmanagement.validators.pos.pos;

import com.c2psi.businessmanagement.dtos.pos.pos.PosCashAccountDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCashOperationDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class PosCashOperationValidator {
    /**************************************************************************
     * *Le parametre a valider ne peut etre null
     * *L'operation associe doit etre valide
     * *Le montant en mouvement doit etre positif
     * *Le compte cash associe ne doit pas etre null
     * @param poscashopDto
     * @return
     */
    public static List<String> validate(PosCashOperationDto poscashopDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(poscashopDto).isPresent()){
            errors.add("--Le parametre a valider ne doit pas etre null--");
        }
        else{
            if(Optional.ofNullable(poscashopDto.getPoscoAmountinmvt()).isPresent()) {
                if (poscashopDto.getPoscoAmountinmvt().doubleValue() < 0) {
                    errors.add("--Le montant en mouvement ne doit pas etre negatif--");
                }
            }
            else{
                errors.add("--Le montant associe a l'operation ne peut etre null--");
            }

            List<String> errs = OperationValidator.validate(
                    poscashopDto.getPoscoOperationDto());
            if(errs.size()>0){
                errors.addAll(errs);
            }

            if(!Optional.ofNullable(poscashopDto.getPoscoPosCashAccountDto()).isPresent()){
                errors.add("--Le compte cash associe ne peut etre null--");
            }

            if(!Optional.ofNullable(poscashopDto.getPoscoUserbmDto()).isPresent()){
                errors.add("--L'utilisteur qui a effectue l'operation ne saurait etre null--");
            }

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<PosCashOperationDto>> constraintViolations = validator.validate(poscashopDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<PosCashOperationDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }

        }
        return errors;
    }
}
