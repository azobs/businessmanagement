package com.c2psi.businessmanagement.dtos.stock.provider;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.product.PackagingDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.AddressDto;
import com.c2psi.businessmanagement.models.Provider;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class ProviderDto {
    Long id;
    @NotNull(message = "The provider name cannot be null")
    @NotEmpty(message = "The provider name cannot be empty")
    @NotBlank(message = "The provider name cannot be blank")
    @Size(min = 3, max = 20, message = "The provider name size must be between 3 and 20 characters")
    String providerName;
    @NotNull(message = "The provider acronym cannot be null")
    @NotEmpty(message = "The provider acronym cannot be empty")
    @NotBlank(message = "The provider acronym cannot be blank")
    @Size(min = 3, max = 10, message = "The provider acronym size must be between 3 and 10 characters")
    String providerAcronym;
    String providerDescription;
    @NotNull(message = "The address of provider cannot be null")
    AddressDto providerAddressDto;

    /******************************
     * Relation between entities  *
     * ****************************/
    @NotNull(message = "The provider cash account cannot be null")
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
    PointofsaleDto providerPosDto;
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
                .providerPosDto(PointofsaleDto.fromEntity(provider.getProviderPos()))
                /*.packagingDtoList(provider.getPackagingList() != null ?
                        provider.getPackagingList().stream()
                        .map(PackagingDto::fromEntity)
                        .collect(Collectors.toList()) : null)
                .providerCapsuleAccountDtoList(provider.getProviderCapsuleAccountList() != null ?
                        provider.getProviderCapsuleAccountList().stream()
                        .map(ProviderCapsuleAccountDto::fromEntity)
                        .collect(Collectors.toList()) : null)
                .providerPackagingAccountDtoList(provider.getProviderPackagingAccountList() != null ?
                        provider.getProviderPackagingAccountList().stream()
                                .map(ProviderPackagingAccountDto::fromEntity)
                                .collect(Collectors.toList()) : null)
                .providerDamageAccountDtoList(provider.getProviderDamageAccountList() != null ?
                        provider.getProviderDamageAccountList().stream()
                                .map(ProviderDamageAccountDto::fromEntity)
                                .collect(Collectors.toList()) : null)*/
                .build();
    }
    public static Provider toEntity(ProviderDto  providerDto){
        if(providerDto == null){
            return null;
        }
        Provider p = new Provider();
        p.setId(providerDto.getId());
        p.setProviderAcronym(providerDto.getProviderAcronym());
        p.setProviderAddress(AddressDto.toEntity(providerDto.getProviderAddressDto()));
        p.setProviderPos(PointofsaleDto.toEntity(providerDto.providerPosDto));
        p.setProviderDescription(providerDto.getProviderDescription());
        p.setProviderName(providerDto.getProviderName());
        /*p.setPackagingList(providerDto.getPackagingDtoList() != null ?
                providerDto.getPackagingDtoList().stream()
                .map(PackagingDto::toEntity)
                .collect(Collectors.toList()) : null);
        p.setProviderCapsuleAccountList(providerDto.getProviderCapsuleAccountDtoList() != null ?
                providerDto.getProviderCapsuleAccountDtoList().stream()
                        .map(ProviderCapsuleAccountDto::toEntity)
                        .collect(Collectors.toList()) : null);
        p.setProviderDamageAccountList(providerDto.getProviderDamageAccountDtoList() != null ?
                providerDto.getProviderDamageAccountDtoList().stream()
                        .map(ProviderDamageAccountDto::toEntity)
                        .collect(Collectors.toList()) : null);
        p.setProviderPackagingAccountList(providerDto.getProviderPackagingAccountDtoList() != null ?
                providerDto.getProviderPackagingAccountDtoList().stream()
                        .map(ProviderPackagingAccountDto::toEntity)
                        .collect(Collectors.toList()) : null);
        p.setProviderCa(ProviderCashAccountDto.toEntity(providerDto.getProviderCaDto()));*/
        return p;
    }
}
