package com.c2psi.businessmanagement.validators.pos.loading;

import com.c2psi.businessmanagement.dtos.pos.loading.LoadingDetailsDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosPackagingOperationDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class LoadingDetailsValidator {
    public static List<String> validate(LoadingDetailsDto ldDto) {
        List<String> errors = new ArrayList<>();
        Optional<LoadingDetailsDto> opt_ldDto = Optional.ofNullable(ldDto);
        if(!opt_ldDto.isPresent()){
            errors.add("--Le  parametre a valider ne saurait etre null--");
        }
        else {
            if (!Optional.ofNullable(ldDto.getLdArticleDto()).isPresent()) {
                errors.add("--L'article lie a ce détails de chargement ne peut etre null--");
            }
            if (Optional.ofNullable(ldDto.getLdQuantityreturn()).isPresent() ||
                    Optional.ofNullable(ldDto.getLdQuantitytaken()).isPresent()){
                int sentinel=0;
                if (ldDto.getLdQuantityreturn().intValue() < 0) {
                    errors.add("--La quantité retourne d'un produit ne peut etre negative--");
                    sentinel=1;
                }
                if (ldDto.getLdQuantitytaken().intValue() <= 0) {
                    errors.add("--La quantité emporté d'un article dans le chargement doit etre positive--");
                    sentinel=1;
                }
                if(sentinel == 0) {
                    if (ldDto.getLdQuantityreturn().intValue() > ldDto.getLdQuantitytaken().intValue()) {
                        errors.add("--La quantité retourné ne peut etre superieur a la quantite emporte--");
                    }
                }
            }
            else{
                errors.add("--La quantite emporte ou retourne ne peut etre null--");
            }
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<LoadingDetailsDto>> constraintViolations = validator.validate(ldDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<LoadingDetailsDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }
        }
        return errors;
    }
}
