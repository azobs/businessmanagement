package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.InventoryLineDto;
import com.c2psi.businessmanagement.dtos.stock.product.PackagingDto;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class PackagingValidator {
    /*************************************************************************************
     * *le parametre a valider ne peut etre null
     * *le label du packaging () ne peut etre ni vide ni null
     * *la couleur du packaging () ne peut etre ni null ni vide
     * *le prix du packaging () ne peut etre negative
     * *le provider du packaging ne peut etre null
     * @param packagingDto
     * @return
     */
    public static List<String> validate(PackagingDto packagingDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(packagingDto).isPresent()){
            errors.add("--Le parametre a valider ne peut etre null--");
        }
        else{
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<PackagingDto>> constraintViolations = validator.validate(packagingDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<PackagingDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }
        }
        return errors;
    }
}
