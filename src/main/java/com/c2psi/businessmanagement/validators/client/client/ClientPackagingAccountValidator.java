package com.c2psi.businessmanagement.validators.client.client;

import com.c2psi.businessmanagement.dtos.client.client.ClientDamageOperationDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientPackagingAccountDto;
import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import com.c2psi.businessmanagement.dtos.stock.product.PackagingDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ClientPackagingAccountValidator {
    /***************************************************************************************
     *  *Le parametre compte packaging client (cpaDto) ne doit pas etre null
     *  *Le compte doit être lié à un packaging
     *  *Le compte doit être lié à un client
     *  *Le compte doit être lié à un point de vente
     *  *Le packaging doit être un packaging du point de vente auquel le compte est lié
     *  *Le client doit être un client du point de vente auquel le compte est lié
     * @param cpaDto
     * @return
     */
    public static List<String> validate(ClientPackagingAccountDto cpaDto){
        List<String> errors = new ArrayList<>();
        if(cpaDto == null){
            errors.add("--Le compte packaging client ne peut être null--");
        }
        else{
            if(cpaDto.getCpaPackagingDto() == null){
                errors.add("--Le packaging associé à ce compte packaging client est null--");
            }
            if(cpaDto.getCpaClientDto() == null){
                errors.add("--Le client associé à ce compte packaging client est null--");
            }
            if(cpaDto.getCpaNumber() == null){
                errors.add("--Le nombre de packaging dans le compte ne peut etre null--");
            }

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<ClientPackagingAccountDto>> constraintViolations = validator.validate(cpaDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<ClientPackagingAccountDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }

            /*else {
                //Le client doit être un client du point de vente auquel le compte est lié
                int isClientofPos = 0;
                List<ClientDto> clientDtoList = cpaDto.getCpaPointofsaleDto().getClientDtoList();
                for (ClientDto clientDto : clientDtoList) {
                    if (clientDto.getId().equals(cpaDto.getCpaClientDto().getId())) {
                        isClientofPos = 1;
                    }
                }
                if (isClientofPos == 0) {
                    errors.add("--Le client specifié n'est pas un client du point de vente: "+errors);
                }
                //Le packaging doit etre un packaging du point de vente auquel le compte est lié
                int isPackagingofPos = 0;
                List<PackagingDto> packagingDtoList = cpaDto.getCpaPointofsaleDto().getPackagingDtoList();
                for (PackagingDto packagingDto : packagingDtoList) {
                    if (packagingDto.getId().equals(cpaDto.getCpaPackagingDto().getId())) {
                        isPackagingofPos = 1;
                    }
                }
                if (isPackagingofPos == 0) {
                    errors.add("--Le packaging spécifié n'est pas un packaging du point de vente: "+errors);
                }
            }*/
        }
        return errors;
    }
}
