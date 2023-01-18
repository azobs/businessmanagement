package com.c2psi.businessmanagement.validators.pos.pos;

import com.c2psi.businessmanagement.dtos.pos.pos.PosDamageOperationDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosPackagingAccountDto;
import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import com.c2psi.businessmanagement.dtos.stock.product.PackagingDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class PosPackagingAccountValidator {
    /************************************************************************************
     * *Le parametre a valider ne peut etre null
     * *Le point de vente associe ne peut etre null
     * *Le packaging associe ne peut etre null
     * *Le packaging associe doit etre un packaging du point de vente specifie
     * @param pospackaccDto
     * @return
     */
    public static List<String> validate(PosPackagingAccountDto pospackaccDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(pospackaccDto).isPresent()){
            errors.add("--Le parametre a valider ne peut etre null--");
        }
        else{
            //int isnullable=0;
            if(!Optional.ofNullable(pospackaccDto.getPpaPackagingDto()).isPresent()){
                errors.add("--Le type d'emballage associe au compte d'emballage ne peut etre null--");
                //isnullable=1;
            }

            if(!Optional.ofNullable(pospackaccDto.getPpaPointofsaleDto()).isPresent()){
                errors.add("--Le point de vente associe au compte d'emballage ne peut etre null--");
                //isnullable=2;
            }

            if(!Optional.ofNullable(pospackaccDto.getPpaNumber()).isPresent()){
                errors.add("--Le nombre d'emballage dans le compte ne peut etre null--");
                //isnullable=2;
            }

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<PosPackagingAccountDto>> constraintViolations = validator.validate(pospackaccDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<PosPackagingAccountDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }

            /*if(isnullable == 0){

                int isPackagingofPos = 0;
                List<PackagingDto> packagingDtoList = pospackaccDto.getPpaPointofsaleDto().getPackagingDtoList();
                for (PackagingDto packagingDto : packagingDtoList) {
                    if (packagingDto.getId().equals(pospackaccDto.getPpaPackagingDto().getId())) {
                        isPackagingofPos = 1;
                    }
                }
                if (isPackagingofPos == 0) {
                    errors.add("--Le type d'emballage spécifié n'est pas un type d'emballage" +
                            " du point de vente: "+errors);
                }
            }*/
        }
        return errors;
    }
}
