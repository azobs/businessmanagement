package com.c2psi.businessmanagement.validators.pos.loading;

import com.c2psi.businessmanagement.dtos.pos.loading.PackingDetailsDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class PackingDetailsValidator {
    /********************************************************************
     * *Le parametre a valider ne peut etre null
     * *Le nombre d'emballage utilisé ne peut etre negatif
     * *Le nombre d'emballage retourné ne peut etre negatif
     * *Le type d'emballage concerné ne peut etre null
     * @param pdDto
     * @return
     */
    public static List<String> validate(PackingDetailsDto pdDto) {
        List<String> errors = new ArrayList<>();
        Optional<PackingDetailsDto> optPdDto = Optional.ofNullable(pdDto);
        if(!optPdDto.isPresent()){
            errors.add("--Le parametre a valider ne saurait etre null--");
        }
        else{
            if(!Optional.ofNullable(pdDto.getPdPackagingDto()).isPresent()){
                errors.add("--Le type d'emballage lie a ce details de chargement ne peut etre null--");
            }
            if(Optional.ofNullable(pdDto.getPdNumberofpackageused()).isPresent()) {
                if (pdDto.getPdNumberofpackageused().intValue() < 0) {
                    errors.add("--Le nombre d'emballage utilise pour un type d'emballage ne peut etre negatif--");
                }
            }
            else{
                errors.add("--Le nombre d'emballage utilise ne peut etre null--");
            }
            if(Optional.ofNullable(pdDto.getPdNumberofpackagereturn()).isPresent()) {
                if (pdDto.getPdNumberofpackagereturn().intValue() < 0) {
                    errors.add("--Le nombre d'emballage retourné pour un type d'emballage ne peut etre negatif--");
                }
            }

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<PackingDetailsDto>> constraintViolations = validator.validate(pdDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<PackingDetailsDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }

        }
        return errors;
    }
}
