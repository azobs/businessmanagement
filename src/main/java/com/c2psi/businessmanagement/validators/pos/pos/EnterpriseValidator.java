package com.c2psi.businessmanagement.validators.pos.pos;

import com.c2psi.businessmanagement.dtos.pos.pos.EnterpriseDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.AddressDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.validators.pos.userbm.AddressValidator;
import com.c2psi.businessmanagement.validators.pos.userbm.UserBMValidator;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class EnterpriseValidator {
    /****************************************************************************************
     * *Le parametre a valider ne peut etre null
     * *Le regime de l'entreprise (entRegime) ne peut etre ni null ni vide
     * *La raison sociale (entSocialreason) de l'entreprise ne peut etre ni null ni vide
     * *Le nom de l'entreprise (entName) ne peut etre ni null ni vide
     * *L'adresse de l'entreprise (entAddressDto) ne peut être null
     * Dans l'adresse de l'entreprise au moins 2 numéros de telephone doivent etre
     * disponible (numtel1 et numtel2) et l'email aussi ne doit etre ni null ni vide
     * @param entDto
     * @return
     */
    public static List<String> validate(EnterpriseDto entDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(entDto).isPresent()){
            errors.add("--Le parametre a valider ne peut etre null--");
        }
        else{
            if(!Optional.ofNullable(entDto.getEntAddressDto()).isPresent()){
                errors.add("--L'address du UserBM ne peut etre null--");
            }
            else{
                //errors.addAll(AddressValidator.validate(entDto.getEntAddressDto()));
                List<String> address_errors = AddressValidator.validate(
                        entDto.getEntAddressDto());
                if(address_errors.size()>0){
                    errors.addAll(address_errors);
                }
            }

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<EnterpriseDto>> constraintViolations = validator.validate(entDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<EnterpriseDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }
        }
        return errors;
    }
}
