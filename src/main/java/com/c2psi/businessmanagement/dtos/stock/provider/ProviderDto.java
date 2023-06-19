package com.c2psi.businessmanagement.dtos.stock.provider;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.AddressDto;
import com.c2psi.businessmanagement.models.Provider;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
public class ProviderDto {
    @ApiModelProperty(value = "The Id of the provider in the DB", name = "id", dataType = "Long")
    Long id;
    @NotNull(message = "The provider name cannot be null")
    @NotEmpty(message = "The provider name cannot be empty")
    @NotBlank(message = "The provider name cannot be blank")
    @Size(min = 3, max = 20, message = "The provider name size must be between 3 and 20 characters")
    @ApiModelProperty(value = "The provider name", name = "providerName", dataType = "String")
    String providerName;
    @NotNull(message = "The provider acronym cannot be null")
    @NotEmpty(message = "The provider acronym cannot be empty")
    @NotBlank(message = "The provider acronym cannot be blank")
    @Size(min = 3, max = 10, message = "The provider acronym size must be between 3 and 10 characters")
    @ApiModelProperty(value = "The provider acronym", name = "providerAcronym", dataType = "String")
    String providerAcronym;
    @ApiModelProperty(value = "The provider description", name = "providerDescription", dataType = "String")
    String providerDescription;
    @NotNull(message = "The address of provider cannot be null")
    @ApiModelProperty(value = "The provider address", name = "providerAddressDto", dataType = "AddressDto")
    AddressDto providerAddressDto;

    /******************************
     * Relation between entities  *
     * ****************************/
    @NotNull(message = "The provider cash account cannot be null")
    @ApiModelProperty(value = "The provider cash account", name = "providerCaDto", dataType = "ProviderCashAccountDto")
    ProviderCashAccountDto providerCaDto;
    //Each provider produce a list of packaging

    /*@JsonIgnore
    List<PackagingDto> packagingDtoList;*/
    //List of providercapsuleaccount applied to a provider one per pointofsale

    /*@JsonIgnore
    List<ProviderCapsuleAccountDto> providerCapsuleAccountDtoList;*/
    //List of providerdamageaccount applied to a provider one per pointofsale

    /*@JsonIgnore
    List<ProviderDamageAccountDto> providerDamageAccountDtoList;*/
    //List of providerpackageaccount applied to a client one per pointofsale

    /*@JsonIgnore
    List<ProviderPackagingAccountDto> providerPackagingAccountDtoList;*/
    //List of clientcashaccount applied to a client one per pointofsale

    /*@JsonIgnore
    List<ProviderCashAccountDto> providerCashAccountDtoList;*/

    @NotNull(message = "The pointofsale assoiciated with the provider cannot be null")
    @ApiModelProperty(value = "The id of the pointofsale owner of the provider", name = "providerPosId", dataType = "Long")
    Long providerPosId;
    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static ProviderDto fromEntity(Provider provider){
        if(provider == null){
            return null;
        }
        return ProviderDto.builder()
                .id(provider.getId())
                .providerAcronym(provider.getProviderAcronym())
                .providerName(provider.getProviderName())
                .providerDescription(provider.getProviderDescription())
                .providerCaDto(ProviderCashAccountDto.fromEntity(provider.getProviderCa()))
                .providerAddressDto(AddressDto.fromEntity(provider.getProviderAddress()))
                //.providerPosDto(PointofsaleDto.fromEntity(provider.getProviderPos()))
                .providerPosId(provider.getProviderPosId())

                .build();
    }
    public static Provider toEntity(ProviderDto  providerDto){
        if(providerDto == null){
            return null;
        }
        Provider p = new Provider();
        p.setId(providerDto.getId());
        p.setProviderAcronym(providerDto.getProviderAcronym());
        p.setProviderName(providerDto.getProviderName());
        p.setProviderDescription(providerDto.getProviderDescription());
        p.setProviderCa(ProviderCashAccountDto.toEntity(providerDto.getProviderCaDto()));
        p.setProviderAddress(AddressDto.toEntity(providerDto.getProviderAddressDto()));
        //p.setProviderPos(PointofsaleDto.toEntity(providerDto.providerPosDto));
        p.setProviderPosId(providerDto.getProviderPosId());

        return p;
    }
}
