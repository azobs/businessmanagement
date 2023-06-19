package com.c2psi.businessmanagement.validators.pos.userbm;

import com.c2psi.businessmanagement.Enumerations.RoleType;
import com.c2psi.businessmanagement.dtos.pos.userbm.RoleDto;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class RoleValidator {
    /**************************************************************************
     * *Le parametre a valider ne peut etre null
     * *Le nom du role (roleName) ne peut etre ni vide ni null
     * *Le point de vente associe ne peut etre null
     * @param roleDto
     * @return
     */
    public static List<String> validate(RoleDto roleDto){
        List<String> errors = new ArrayList<>();

        if(!Optional.ofNullable(roleDto).isPresent()){
            errors.add("--Le parametre a valider ne peut etre null--");
        }
        else{
            if(!Optional.ofNullable(roleDto.getRoleEntDto()).isPresent()){
                errors.add("--Chaque role doit appartenir a une entreprise--");
            }
            if (Optional.ofNullable(roleDto.getRoleAlias()).isPresent()){
                if (!StringUtils.hasLength(roleDto.getRoleAlias())) {
                    errors.add("--L'alias du role ne peut etre vide--");
                }
            }
            int isnullable=0;
            if(!Optional.ofNullable(roleDto.getRoleName()).isPresent()){
                errors.add("--Le nom du role doit etre precise--");
                isnullable=1;
            }
            if(isnullable == 0){
                if(!roleDto.getRoleName().equals(RoleType.Deliver) &&
                        !roleDto.getRoleName().equals(RoleType.Dg) &&
                        !roleDto.getRoleName().equals(RoleType.Manager) &&
                        !roleDto.getRoleName().equals(RoleType.Pdg) &&
                        !roleDto.getRoleName().equals(RoleType.Saler) &&
                        !roleDto.getRoleName().equals(RoleType.Storekeeper)){
                    errors.add("--Le role specifie n'est pas pris en compte par le système--");
                }
            }
            //On teste les annotations
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<RoleDto>> constraintViolations = validator.validate(roleDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<RoleDto> contraintes : constraintViolations) {
                /*System.out.println(contraintes.getRootBeanClass().getSimpleName()+
                        "." + contraintes.getPropertyPath() + " " + contraintes.getMessage());*/
                    errors.add(contraintes.getMessage());
                }
            }
        }
        return errors;
    }
}
