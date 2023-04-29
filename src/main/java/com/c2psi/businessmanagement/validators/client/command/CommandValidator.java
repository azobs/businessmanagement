package com.c2psi.businessmanagement.validators.client.command;

import com.c2psi.businessmanagement.Enumerations.CommandState;
import com.c2psi.businessmanagement.Enumerations.CommandStatus;
import com.c2psi.businessmanagement.Enumerations.CommandType;
import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.dtos.client.command.CommandDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMRoleDto;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

public class CommandValidator {
    /***************************************************************************
     *  *Le parametre a valider ne peut etre null
     *  *Le code de la commande (cmdCode) ne peut etre ni null ni vide
     *  *La date de la commande (cmdDate) doit etre ulterieure ou egale a la
     *  date courante
     *  *L'etat (cmdState), le statut (cmdStatus) et le type (cmdType) doivent etre non null et
     *  leur valeur choisi parmi celle prevu
     *  *Le client associe a la commande (cmdCltDto) ne doit pas etre null
     *  *L'utilisateur qui enregistre la commande (cmdUserbmDto) ne doit pas etre null
     *  et doit avoir un role dans le même pointofsale du client auquel la commande est
     *  associee
     *  *Le client et la commande doivent etre dans le même point de vente
     * @param commandDto
     * @return
     */
    public static List<String> validate(CommandDto commandDto){
       List<String> errors = new ArrayList<>();
        Optional<CommandDto> optCmdDto = Optional.ofNullable(commandDto);
        if(!optCmdDto.isPresent()){
            errors.add("--Le  parametre a valider ne saurait etre null--");
        }
        else{

            if(Optional.ofNullable(commandDto.getCmdState()).isPresent() &&
                    Optional.ofNullable(commandDto.getCmdType()).isPresent() &&
                    Optional.ofNullable(commandDto.getCmdStatus()).isPresent()){
                if(!commandDto.getCmdState().equals(CommandState.Edited) &&
                        !commandDto.getCmdState().equals(CommandState.InEditing) &&
                        !commandDto.getCmdState().equals(CommandState.PackedUp)){
                    errors.add("--Etat de commande non reconnu par le système--");
                }
                if(!commandDto.getCmdType().equals(CommandType.Divers) &&
                        !commandDto.getCmdType().equals(CommandType.Standard)){
                    errors.add("--Type de commande non reconnu par le système--");
                }
                if(!commandDto.getCmdStatus().equals(CommandStatus.Capsule) &&
                        !commandDto.getCmdStatus().equals(CommandStatus.Cash)){
                    errors.add("--Status de commande non reconnu par le système--");
                }
            }

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<CommandDto>> constraintViolations = validator.validate(commandDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<CommandDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }

        }
       return errors;
    }
}
