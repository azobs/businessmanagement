package com.c2psi.businessmanagement.dtos.client.client;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import com.c2psi.businessmanagement.models.ClientCapsuleAccount;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


@Data
@Builder
public class ClientCapsuleAccountDto {
    Long id;

    @NotNull(message = "The number of capsule in the account cannot be null")
    BigDecimal ccsaNumber;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Each clientcapsuleaccount must belonging to one pointofsale

    //PointofsaleDto ccsaPointofsaleDto;

    //Each clientcapsuleaccount must belonging to one client

    @NotNull(message = "The client associated with the account cannot be null")
    ClientDto ccsaClientDto;
    //Long ccsaClientId;

    //Each ClientCapsuleAccount is for 1 article but not all article must have an ClientCapsuleAccount

    @NotNull(message = "The article concerned by the account cannot be null")
    ArticleDto ccsaArticleDto;
    //Long ccsaArticleId;

    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static ClientCapsuleAccountDto fromEntity(ClientCapsuleAccount ccsa){
        if(ccsa == null){
            return null;
        }
        return ClientCapsuleAccountDto.builder()
                .id(ccsa.getId())
                .ccsaArticleDto(ArticleDto.fromEntity(ccsa.getCcsaArticle()))
                .ccsaClientDto(ClientDto.fromEntity(ccsa.getCcsaClient()))
                .ccsaNumber(ccsa.getCcsaNumber())
                .build();
    }

    public static ClientCapsuleAccount toEntity(ClientCapsuleAccountDto ccsa_dto){
        if(ccsa_dto == null){
            return null;
        }
        ClientCapsuleAccount cca = new ClientCapsuleAccount();
        cca.setId(ccsa_dto.getId());
        cca.setCcsaArticle(ArticleDto.toEntity(ccsa_dto.getCcsaArticleDto()));
        cca.setCcsaClient(ClientDto.toEntity(ccsa_dto.getCcsaClientDto()));
        cca.setCcsaNumber(ccsa_dto.getCcsaNumber());
        return cca;
    }

}
