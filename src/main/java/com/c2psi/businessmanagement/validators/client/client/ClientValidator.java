package com.c2psi.businessmanagement.validators.client.client;

import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientSpecialpriceDto;
import com.c2psi.businessmanagement.validators.pos.pos.OperationValidator;
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

public class ClientValidator {
    /************************************************************************************
     *  *Le parametre clientDto ne doit pas etre null
     *  *Le nom du client ne doit pas etre null
     *  *Le nom du client ne doit pas etre vide
     *  *le client doit avoir un compte cash qui lui est associe des son enregistrement
     *  *Le point de vente du client ne doit pas etre null
     *  *L'adresse doit etre valide
     * @param clientDto
     * @return
     */
    public static List<String> validate(ClientDto clientDto){
        List<String> errors = new ArrayList<>();
        Optional<ClientDto> optionalCltDto = Optional.ofNullable(clientDto);
        if(!optionalCltDto.isPresent()){
            errors.add("--Le parametre à valider ne peut etre null--");
        }
        else{
            if(clientDto.getCltCaDto() == null){
                errors.add("--Un compte cash doit etre associe au client des l'enregistrement--");
            }
            if(clientDto.getCltName() == null){
                errors.add("--Le nom du client ne peut etre null--");
            }
            else{
                if(!StringUtils.hasLength(clientDto.getCltName())){
                    errors.add("--Le nom du client ne doit pas etre vide--");
                }
                List<String> adr_errors = AddressValidator.validate(
                        clientDto.getCltAddressDto());
                if(adr_errors.size()>0){
                    errors.addAll(adr_errors);
                }
            }
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<ClientDto>> constraintViolations = validator.validate(clientDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<ClientDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }
        }
        return errors;
    }
}
