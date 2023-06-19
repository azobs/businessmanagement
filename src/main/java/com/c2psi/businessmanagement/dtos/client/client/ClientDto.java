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
    String clientName;
    String clientOthername;
    String clientCni;
    /******************************
     * Relation between entities  *
     * ****************************/
    @NotNull(message = "The cash account of the client cannot be null")
    ClientCashAccountDto clientCaDto;
    @NotNull(message = "The client must be associated with a Pointofsale")
    //PointofsaleDto clientPosDto;
    Long clientPosId;
    @Valid
    AddressDto clientAddressDto;
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
    public static ClientDto fromEntity(Client client){
        if(client == null){
            return null;
        }
        return ClientDto.builder()
                .id(client.getId())
                .clientName(client.getClientName())
                .clientOthername(client.getClientOthername())
                .clientCni(client.getClientCni())
                .clientCaDto(ClientCashAccountDto.fromEntity(client.getClientCa()))
                //.clientPosDto(PointofsaleDto.fromEntity(client.getClientPos()))
                .clientPosId(client.getClientPosId())
                .clientAddressDto(AddressDto.fromEntity(client.getClientAddress()))
                .build();
    }

    public static Client toEntity(ClientDto client_dto){
        if(client_dto == null){
            return null;
        }
        Client client = new Client();
        client.setId(client_dto.getId());
        client.setClientName(client_dto.getClientName());
        client.setClientOthername(client_dto.getClientOthername());
        client.setClientCni(client_dto.getClientCni());
        client.setClientCa(ClientCashAccountDto.toEntity(client_dto.getClientCaDto()));
        //client.setClientPos(PointofsaleDto.toEntity(client_dto.getClientPosDto()));
        client.setClientPosId(client_dto.getClientPosId());
        client.setClientAddress(AddressDto.toEntity(client_dto.getClientAddressDto()));

        return client;
    }
}
