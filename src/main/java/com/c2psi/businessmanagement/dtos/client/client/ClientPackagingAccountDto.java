package com.c2psi.businessmanagement.dtos.client.client;

import com.c2psi.businessmanagement.dtos.stock.product.PackagingDto;
import com.c2psi.businessmanagement.models.ClientPackagingAccount;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
public class ClientPackagingAccountDto {
    Long id;
    @NotNull(message = "The number of packaging in the account cannot be null")
    BigDecimal cpaNumber;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Each clientcapsuleaccount must belonging to one pointofsale

    //PointofsaleDto cpaPointofsaleDto;

    //Each clientcapsuleaccount must belonging to one client

    @NotNull(message = "The client associated with the account cannot be null")
    ClientDto cpaClientDto;

    //Each ClientPackagingAccount is for 1 packaging
    @NotNull(message = "The packaging type associated with the account cannot be null")
    PackagingDto cpaPackagingDto;

    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static ClientPackagingAccountDto fromEntity(ClientPackagingAccount cpa){
        if(cpa == null){
            return null;
        }
        return ClientPackagingAccountDto.builder()
                .id(cpa.getId())
                .cpaNumber(cpa.getCpaNumber())
                .cpaClientDto(ClientDto.fromEntity(cpa.getCpaClient()))
                .cpaPackagingDto(PackagingDto.fromEntity(cpa.getCpaPackaging()))
                .build();
    }
    public static ClientPackagingAccount toEntity(ClientPackagingAccountDto cpaDto){
        if(cpaDto == null){
            return null;
        }
        ClientPackagingAccount cpa = new ClientPackagingAccount();
        cpa.setId(cpaDto.getId());
        cpa.setCpaNumber(cpaDto.getCpaNumber());
        cpa.setCpaClient(ClientDto.toEntity(cpaDto.getCpaClientDto()));
        cpa.setCpaPackaging(PackagingDto.toEntity(cpaDto.getCpaPackagingDto()));
        return cpa;
    }
}
