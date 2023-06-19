package com.c2psi.businessmanagement.validators.client.client;

import com.c2psi.businessmanagement.dtos.client.client.ClientSpecialpriceDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

public class ClientSpecialpriceValidator {
    /**************************************************************************************
     *  *la date d'application du prix special au client (cltSpApplieddate) doit etre
     *  anterieur ou egale a la date courante
     *  *Le client a qui on associe le prix special (cltSpClientDto) ne doit pas etre null
     *  *Le prix special qu'on veut associe au client (cltSpPDto) ne doit pas etre null
     *  *le prix special (cltSpPDto) doit etre associe a un prix de base (BasepriceDto)
     *  d'un article du meme point de vente (PointofsaleDto) que le client.
     * @param cltSpDto
     * @return
     */
    public static List<String> validate(ClientSpecialpriceDto cltSpDto){
        List<String> errors = new ArrayList<>();
        Optional<ClientSpecialpriceDto> optionalCltSpDto = Optional.ofNullable(cltSpDto);
        if(!optionalCltSpDto.isPresent()){
            errors.add("--Le parametre à valider ne peut etre null--");
        }
        else{

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<ClientSpecialpriceDto>> constraintViolations = validator.validate(cltSpDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<ClientSpecialpriceDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }

        }
        return errors;
    }
}
