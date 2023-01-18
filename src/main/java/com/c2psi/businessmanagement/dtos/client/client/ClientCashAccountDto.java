package com.c2psi.businessmanagement.dtos.client.client;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.models.ClientCashAccount;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
public class ClientCashAccountDto {
    Long id;

    @NotNull(message = "The account balance cannot be null")
    BigDecimal ccaBalance;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Each clientcashaccount must belonging to one pointofsale

    //PointofsaleDto ccaPointofsaleDto;

    //Each clientcapsuleaccount must belonging to one client

    /*@JsonIgnore
    ClientDto ccaClientDto;*/

    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static ClientCashAccountDto fromEntity(ClientCashAccount cca){
        if(cca == null){
            return null;
        }
        return ClientCashAccountDto.builder()
                .id(cca.getId())
                .ccaBalance(cca.getCcaBalance())
                .build();
    }

    public static ClientCashAccount toEntity(ClientCashAccountDto cca_dto){
        if(cca_dto == null){
            return null;
        }
        ClientCashAccount cca = new ClientCashAccount();
        cca.setId(cca_dto.getId());
        cca.setCcaBalance(cca_dto.getCcaBalance());
        return cca;
    }
}
