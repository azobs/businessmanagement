package com.c2psi.businessmanagement.validators.pos.loading;

import com.c2psi.businessmanagement.dtos.pos.loading.LoadingDto;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

public class LoadingValidator {
    /****************************************************************************************
     * *Le parametre a valider ne peut etre null
     * *Le code du chargement (loadCode) ne peut etre ni null ni vide
     * *La date du chargement (loadDate) ne peut etre ni null ni vide
     * *L'etat du chargement (loadState) doit etre parmi les valeurs connues
     * *Le montant du chargement (loadTotalamountexpected) doit etre positif ou null
     * *Le montant du verse apres vente (loadTotalamountpaid) doit etre positif ou null
     * *L'utilisateur qui enregistre le chargement (loadUserbmManagerDto) ne doit pas
     * etre null et doit avoir un role dans le meme point de vente que celui ou le
     * chargement est effectué
     * *L'utilisateur qui va aller vendre le chargement (loadUserbmSalerDto) ne doit pas
     *      * etre null et doit avoir un role dans le meme point de vente ou le chargement
     *      est effectué
     * @param loadDto
     * @return
     */
    public static List<String> validate(LoadingDto loadDto) {
        List<String> errors = new ArrayList<>();
        Optional<LoadingDto> opt_loadDto = Optional.ofNullable(loadDto);
        if(!opt_loadDto.isPresent()){
            errors.add("--Le  parametre a valider ne saurait etre null--");
        }
        else{
            if(Optional.ofNullable(loadDto.getLoadCode()).isPresent()){
                if(!StringUtils.hasLength(loadDto.getLoadCode())){
                    errors.add("--le code du chargement ne peut etre vide--");
                }
            }
            else{
                errors.add("--Le code de la commande ne peut etre null--");
            }
            if(Optional.ofNullable(loadDto.getLoadDate()).isPresent()) {
                if (!loadDto.getLoadDate().isBefore(new Date().toInstant())) {
                    errors.add("--La date d'un chargement ne saurait etre antérieure a la date courante--");
                }
            }
            else{
                errors.add("--La date de chargement ne saurait etre vide--");
            }

            if(!Optional.ofNullable(loadDto.getLoadUserbmManagerDto()).isPresent()){
                errors.add("--L'utilisateur qui enregistre le chargement ne peut etre null--");
            }

            if(!Optional.ofNullable(loadDto.getLoadUserbmSalerDto()).isPresent()){
                errors.add("--L'utilisateur qui va vendre le chargement ne peut etre null--");
            }

            if(!Optional.ofNullable(loadDto.getLoadPosId()).isPresent()){
                errors.add("--Le point de vente du chargement ne peut etre null--");
            }

            /*if(Optional.ofNullable(loadDto.getLoadState()).isPresent()){
                if(!loadDto.getLoadState().equals(LoadingState.Edited) &&
                        !loadDto.getLoadState().equals(LoadingState.InEditing) &&
                        !loadDto.getLoadState().equals(LoadingState.PackedUp)){
                    errors.add("--Etat de chargement non reconnu par le système--");
                }
            }*/

            if(!Optional.ofNullable(loadDto.getLoadTotalamountexpected()).isPresent()){
                errors.add("--Le montant attendu ne peut etre non null--");
            }
            else {
                if (loadDto.getLoadTotalamountexpected().doubleValue() < 0) {
                    errors.add("--Le montant attendu du chargement ne saurait etre negatif--");
                }
                if(Optional.ofNullable(loadDto.getLoadTotalamountpaid()).isPresent()) {
                    if (loadDto.getLoadTotalamountpaid().doubleValue() < 0) {
                        errors.add("--Le montant versé du chargement ne saurait etre negatif--");
                    }
                }
            }
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<LoadingDto>> constraintViolations = validator.validate(loadDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<LoadingDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }
        }
        return errors;
    }
}
