package com.c2psi.businessmanagement.validators.client.client;

import com.c2psi.businessmanagement.dtos.client.client.ClientCapsuleAccountDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.dtos.pos.loading.LoadingDto;
import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ClientCapsuleAccountValidator {
    /******************************************************************************
     * Les conditions de validité d'un compte capsule client sont:
     * --le parametre compte capsule client (ccapsaDto) ne doit pas etre null
     * --Le compte doit être lié à un article
     * --Le compte doit être lié à un client
     * --Le compte doit être lié à un point de vente
     * --L'article doit être un article du point de vente auquel le compte est lié
     * --Le client doit être un client du point de vente auquel le compte est lié
     * @param ccapsaDto
     * @return
     */
    public static List<String> validate(ClientCapsuleAccountDto ccapsaDto){
        List<String> errors = new ArrayList<>();
        if(ccapsaDto == null){
            errors.add("--Le compte capsule client ne peut être null--");
        }
        else{
            if(ccapsaDto.getCcsaArticleDto() == null){
                errors.add("--L'article associé à ce compte capsule client est null--");
            }
            if(ccapsaDto.getCcsaClientDto() == null){
                errors.add("--Le client associé à ce compte capsule client est null--");
            }
            if(ccapsaDto.getCcsaNumber() == null){
                errors.add("--Le nombre de capsule dans le compte ne peuit etre null--");
            }

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<ClientCapsuleAccountDto>> constraintViolations = validator.validate(ccapsaDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<ClientCapsuleAccountDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }

            /*else {
                //Le client doit être un client du point de vente auquel le compte est lié
                int isClientofPos = 0;
                List<ClientDto> clientDtoList = ccapsaDto.getCcsaPointofsaleDto().getClientDtoList();
                for (ClientDto clientDto : clientDtoList) {
                    if (clientDto.getId().equals(ccapsaDto.getCcsaClientDto().getId())) {
                        isClientofPos = 1;
                    }
                }
                if (isClientofPos == 0) {
                    errors.add("--Le client specifié n'est pas un client du point de vente: "+errors);
                }
                //L'article doit etre un article du point de vente auquel le compte est lié
                int isArticleofPos = 0;
                List<ArticleDto> articleDtoList = ccapsaDto.getCcsaPointofsaleDto().getArticleDtoList();
                for (ArticleDto articleDto : articleDtoList) {
                    if (articleDto.getId().equals(ccapsaDto.getCcsaArticleDto().getId())) {
                        isArticleofPos = 1;
                    }
                }
                if (isArticleofPos == 0) {
                    errors.add("--L'article spécifié n'est pas un article du point de vente: "+errors);
                }
            }*/
        }
        return errors;
    }
}
