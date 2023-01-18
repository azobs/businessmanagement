package com.c2psi.businessmanagement.dtos.client.client;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.AddressDto;
import com.c2psi.businessmanagement.models.Client;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
@Builder
public class ClientDto {
    Long id;

    @NotNull(message = "The client name cannot be null")
    @NotEmpty(message = "The client name cannot be empty")
    @NotBlank(message = "The client name cannot be blank value")
    String cltName;
    String cltOthername;
    String cltCni;
    /******************************
     * Relation between entities  *
     * ****************************/
    @NotNull(message = "The cash account of the client cannot be null")
    ClientCashAccountDto cltCaDto;
    @NotNull(message = "The client must be associated with a Pointofsale")
    PointofsaleDto cltPosDto;
    @Valid
    AddressDto cltAddressDto;
    //List of specialprice applied to a client
    /*@JsonIgnore
    List<ClientSpecialpriceDto> clientSpecialpriceDtoList;*/
    //List of clientcashaccount applied to a client one per pointofsale
    /*@JsonIgnore
    List<ClientCashAccountDto> clientCashAccountDtoList;*/
    //List of clientcapsuleaccount applied to a client one per pointofsale
    /*@JsonIgnore
    List<ClientCapsuleAccountDto> clientCapsuleAccountDtoList;*/
    //List of clientcapsuleaccount applied to a client one per pointofsale
    /*@JsonIgnore
    List<ClientPackagingAccountDto> clientPackagingAccountDtoList;*/
    //List of clientdamageaccount applied to a client one per pointofsale and one per article
    /*@JsonIgnore
    List<ClientDamageAccountDto> clientDamageAccountDtoList;
    @JsonIgnore
    List<CommandDto> commandDtoList;*/


    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static ClientDto fromEntity(Client clt){
        if(clt == null){
            return null;
        }
        return ClientDto.builder()
                .id(clt.getId())
                .cltName(clt.getCltName())
                .cltOthername(clt.getCltOthername())
                .cltCni(clt.getCltCni())
                .cltCaDto(ClientCashAccountDto.fromEntity(clt.getCltCa()))
                .cltPosDto(PointofsaleDto.fromEntity(clt.getCltPos()))
                /*.clientCapsuleAccountDtoList(clt.getClientCapsuleAccountList() != null ?
                        clt.getClientCapsuleAccountList().stream()
                        .map(ClientCapsuleAccountDto::fromEntity)
                        .collect(Collectors.toList()) : null)
                .clientPackagingAccountDtoList(clt.getClientPackagingAccountList() != null ?
                        clt.getClientPackagingAccountList().stream()
                                .map(ClientPackagingAccountDto::fromEntity)
                                .collect(Collectors.toList()) : null)
                .clientDamageAccountDtoList(clt.getClientDamageAccountList() != null ?
                        clt.getClientDamageAccountList().stream()
                                .map(ClientDamageAccountDto::fromEntity)
                                .collect(Collectors.toList()) : null)
                .commandDtoList(clt.getCommandList() != null ?
                        clt.getCommandList().stream()
                                .map(CommandDto::fromEntity)
                                .collect(Collectors.toList()) : null)*/
                .build();
    }

    public static Client toEntity(ClientDto clt_dto){
        if(clt_dto == null){
            return null;
        }
        Client client = new Client();
        client.setId(clt_dto.getId());
        client.setCltName(clt_dto.getCltName());
        client.setCltOthername(clt_dto.getCltOthername());
        client.setCltCni(clt_dto.getCltCni());
        client.setCltCa(ClientCashAccountDto.toEntity(clt_dto.getCltCaDto()));
        client.setCltPos(PointofsaleDto.toEntity(clt_dto.getCltPosDto()));
        /*client.setClientCapsuleAccountList(clt_dto.getClientCapsuleAccountDtoList() != null ?
                clt_dto.getClientCapsuleAccountDtoList().stream()
                        .map(ClientCapsuleAccountDto::toEntity)
                        .collect(Collectors.toList()) : null);
        client.setClientPackagingAccountList(clt_dto.getClientPackagingAccountDtoList() != null ?
                clt_dto.getClientPackagingAccountDtoList().stream()
                        .map(ClientPackagingAccountDto::toEntity)
                        .collect(Collectors.toList()) : null);
        client.setClientDamageAccountList(clt_dto.getClientDamageAccountDtoList() != null ?
                clt_dto.getClientDamageAccountDtoList().stream()
                        .map(ClientDamageAccountDto::toEntity)
                        .collect(Collectors.toList()) : null);
        client.setCommandList(clt_dto.getCommandDtoList() != null ?
                clt_dto.getCommandDtoList().stream()
                        .map(CommandDto::toEntity)
                        .collect(Collectors.toList()) : null);*/
        return client;
    }
}
