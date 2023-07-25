package com.c2psi.businessmanagement.dtos.client.client;

import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import com.c2psi.businessmanagement.models.ClientDamageAccount;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


@Data
@Builder
public class ClientDamageAccountDto {
    Long id;
    @NotNull(message = "The number in the account cannot be null")
    BigDecimal cdaNumber;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Each clientdamageaccount must belonging to one pointofsale

    //PointofsaleDto cdaPointofsaleDto;

    //Each clientdamageaccount must belonging to one client
    @NotNull(message = "The client associated with the account cannot be null")
    ClientDto cdaClientDto;

    //Each ClientDamageAccount is for 1 article but not all article must have an ClientDamageAccount
    @NotNull(message = "The article concerned by the account cannot be null")
    ArticleDto cdaArticleDto;

    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static ClientDamageAccountDto fromEntity(ClientDamageAccount cda){
        if(cda == null){
            return null;
        }
        return ClientDamageAccountDto.builder()
                .id(cda.getId())
                .cdaNumber(cda.getCdaNumber())
                .cdaClientDto(ClientDto.fromEntity(cda.getCdaClient()))
                .cdaXArticleDto(ArticleDto.fromEntity(cda.getCdaArticle()))
                .build();
    }
    public static ClientDamageAccount toEntity(ClientDamageAccountDto cda_dto){
        if(cda_dto == null){
            return null;
        }
        ClientDamageAccount cda = new ClientDamageAccount();
        cda.setId(cda_dto.getId());
        cda.setCdaNumber(cda_dto.getCdaNumber());
        cda.setCdaClient(ClientDto.toEntity(cda_dto.getCdaClientDto()));
        cda.setCdaArticle(ArticleDto.toEntity(cda_dto.getCdaXArticleDto()));
        return cda;
    }
}
