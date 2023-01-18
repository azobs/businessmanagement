package com.c2psi.businessmanagement.validators.pos.userbm;

import com.c2psi.businessmanagement.Enumerations.UserBMState;
import com.c2psi.businessmanagement.Enumerations.UserBMType;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import org.springframework.util.StringUtils;

import javax.validation.*;
import java.time.Instant;
import java.util.*;

public class UserBMValidator {
    /***************************************************************************************
     * *Le parametre a valider ne peut etre null
     * *Le login du user (bmLogin) ne peut etre ni vide ni null
     * *Le nom du user (bmName) ne peut etre ni vide ni null
     * *La date de naissance du user (bmDob) ne peut etre ulterieure a la date courante
     * *L'etat du user (bmState) ne peut etre null et doit etre compris entre les valeurs
     * reconnu par le systeme
     * *Le type de user (bmUsertype) ne peut etre null et doit etre compris entre les valeurs
     * reconnu par le systeme
     * *L'adresse du user (bmAddress) doit etre valide
     * @param userBMDto
     * @return
     */
    public static List<String> validate(UserBMDto userBMDto){
        List<String> errors = new ArrayList<>();

        if(!Optional.ofNullable(userBMDto).isPresent()){
            errors.add("--Le parametre a valider ne peut etre null--");
        }
        else{
            if(!Optional.ofNullable(userBMDto.getBmAddressDto()).isPresent()){
                errors.add("--L'address du UserBM ne peut etre null--");
            }
            else{
                List<String> address_errors = AddressValidator.validate(
                        userBMDto.getBmAddressDto());
                if(address_errors.size()>0){
                    errors.addAll(address_errors);
                }
            }
            if(!StringUtils.hasLength(userBMDto.getBmLogin())){
                errors.add("--Le login du user ne peut etre vide--");
            }
            if(Optional.ofNullable(userBMDto.getBmCni()).isPresent()){
                if(!StringUtils.hasLength(userBMDto.getBmCni())){
                    errors.add("--Le Cni quand il est precise ne peut etre vide--");
                }
            }
            if(!StringUtils.hasLength(userBMDto.getBmName())){
                errors.add("--Le Name du user ne peut etre vide--");
            }
            if(!StringUtils.hasLength(userBMDto.getBmPassword())){
                errors.add("--Le password du user ne peut etre vide--");
            }
            Instant date_courante = new Date().toInstant();
            if(userBMDto.getBmDob() !=null) {
                if (date_courante.isBefore(userBMDto.getBmDob().toInstant())) {
                    errors.add("--La date de naissance du user ne saurait etre ultérieure a " +
                            "la date courante--");
                }
            }
            else{
                errors.add("--La date de naissance du user ne saurait etre null--");
            }
            UserBMState userBMState = userBMDto.getBmState();
            if(!Optional.ofNullable(userBMState).isPresent()){
                errors.add("--L'etat de l'utilisateur doit etre precise--");
            }
            else{
                if(!userBMState.equals(UserBMState.Activated) &&
                    !userBMState.equals(UserBMState.Connected) &&
                    !userBMState.equals(UserBMState.Deactivated) &&
                    !userBMState.equals(UserBMState.Disconnected)){
                    errors.add("--Etat du user non reconnu par le systeme--");
                }
            }
            UserBMType userBMType = userBMDto.getBmUsertype();
            if(!Optional.ofNullable(userBMType).isPresent()){
                errors.add("--Le type de l'utilisateur doit etre precise--");
            }
            else{
                if(!userBMType.equals(UserBMType.AdminBM) &&
                    !userBMType.equals(UserBMType.AdminEnterprise) &&
                    !userBMType.equals(UserBMType.Employe)){
                    errors.add("--Type d'utilisateur non reconnu par le systeme--");
                }
            }

            if(userBMDto.getBmPassword()!=null && userBMDto.getBmRepassword()!=null) {
                if (!userBMDto.getBmPassword().equalsIgnoreCase(userBMDto.getBmRepassword())) {
                    errors.add("--Le mot de passe doit être identique en tout point à sa confirmation--");
                }
            }

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<UserBMDto>> constraintViolations = validator.validate(userBMDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<UserBMDto> contraintes : constraintViolations) {
                /*System.out.println(contraintes.getRootBeanClass().getSimpleName()+
                        "." + contraintes.getPropertyPath() + " " + contraintes.getMessage());*/
                    errors.add(contraintes.getMessage());
                }
            }

        }
        return errors;
    }
}
