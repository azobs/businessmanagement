package com.c2psi.businessmanagement.validators.pos.userbm;

import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMRoleDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class UserBMRoleValidator {
    /***********************************************************************************
     * *Le parametre a valider ne peut etre null
     * *Le role associe au user ne peut etre null
     * *Le user associe au role ne peut etre null
     *
     * @param userBMRoleDto
     * @return
     */
    public static List<String> validate(UserBMRoleDto userBMRoleDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(userBMRoleDto).isPresent()){
            errors.add("--le parametre a valider ne peut etre null--");
        }
        else{
            if(!Optional.ofNullable(userBMRoleDto.getUserbmroleRoleDto()).isPresent()){
                errors.add("--Le role associe a un userBm ne peut etre null--");
            }
            if(!Optional.ofNullable(userBMRoleDto.getUserbmroleUserbmDto()).isPresent()){
                errors.add("--L'utilisateur auquel le role a ete associe ne peut etre null--");
            }
            if(!Optional.ofNullable(userBMRoleDto.getUserbmroleAttributionDate()).isPresent()){
                errors.add("--La date d'attribution du role au userbm ne peut etre null--");
            }
            else{
                //Il faut tester que la date d'attribution est antérieure ou la date courante
                Instant now = Instant.now();
                Instant attributionDate = userBMRoleDto.getUserbmroleAttributionDate();
                if(attributionDate.compareTo(now)>0){
                    errors.add("--La date d'attribution du role ne peut etre ulterieure a " +
                            "la date courante--");
                }
            }

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<UserBMRoleDto>> constraintViolations = validator.validate(userBMRoleDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<UserBMRoleDto> contraintes : constraintViolations) {
                /*System.out.println(contraintes.getRootBeanClass().getSimpleName()+
                        "." + contraintes.getPropertyPath() + " " + contraintes.getMessage());*/
                    errors.add(contraintes.getMessage());
                }
            }
        }
        return errors;
    }
}
