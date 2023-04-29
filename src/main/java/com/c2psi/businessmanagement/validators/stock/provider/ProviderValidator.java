package com.c2psi.businessmanagement.validators.stock.provider;

import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDamageOperationDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;
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

public class ProviderValidator {
    /********************************************************************************
     * *Le parametre a valider ne saurait etre null
     * *Le nom du provider (providerName) et son acronym (providerAcronym) ne peuveut
     * etre ni null ni vide
     * *L'adresse doit etre valide
     * *Le compte cash (providerCaDto) associe ne peut etre null
     * *Le point de vente associe au provider (providerPosDto) ne peut etre null
     * @param providerDto
     * @return
     */
    public static List<String> validate(ProviderDto providerDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(providerDto).isPresent()){
            errors.add("--le parametre a valider ne peut etre null--");
        }
        else{
            if(!Optional.ofNullable(providerDto.getProviderAddressDto()).isPresent()){
                errors.add("--L'address du provider ne peut etre null--");
            }
            else{
                List<String> errs = AddressValidator.validate(providerDto.getProviderAddressDto());
                if(errs.size()>0){
                    errors.addAll(errs);
                }
            }
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<ProviderDto>> constraintViolations = validator.validate(providerDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<ProviderDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }
        }
        return errors;
    }
}
