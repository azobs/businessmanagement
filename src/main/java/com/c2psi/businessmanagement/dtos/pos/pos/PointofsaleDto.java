package com.c2psi.businessmanagement.dtos.pos.pos;

import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.AddressDto;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyDto;
import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import com.c2psi.businessmanagement.dtos.stock.product.PackagingDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;
import com.c2psi.businessmanagement.models.Article;
import com.c2psi.businessmanagement.models.Client;
import com.c2psi.businessmanagement.models.Packaging;
import com.c2psi.businessmanagement.models.Pointofsale;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class PointofsaleDto {
    Long id;
    @NotNull(message = "The pointofsale name cannot be null")
    @NotEmpty(message = "The pointofsale name cannot be empty value")
    @NotBlank(message = "The pointofsale name cannot be blank value")
    @Size(max = 30, message = "The pointofsale name cannot exceed 30 characters")
    String posName;

    @NotNull(message = "The pointofsale acronym cannot be null")
    @NotEmpty(message = "The pointofsale acronym cannot be empty value")
    @NotBlank(message = "The pointofsale acronym cannot be blank value")
    @Size(max = 30, message = "The pointofsale acronym cannot exceed 30 characters")
    String posAcronym;
    String posDescription;

    @Valid
    AddressDto posAddressDto;

    /******************************
     * Relation between Dto entities  *
     * ****************************/
    //Each pointofsale must be related to a list of packagingaccount (one per packaging)

    /*@JsonIgnore
    List<ClientDto> clientDtoList;*/
    //Each pointofsale must be related to a list of packagingaccount (one per packaging)

    /*@JsonIgnore
    List<ProviderDto> providerDtoList;

    @JsonIgnore
    List<PosPackagingAccountDto> posPackagingAccountDtoList;*/
    //Each pointofsale must be related to a list of capsuleaccount (one per article)

    /*@JsonIgnore
    List<PackagingDto> packagingDtoList;

    @JsonIgnore
    List<PosCapsuleAccountDto> posCapsuleAccountDtoList;*/
    //Each pointofsale must be related to a list of damageaccount (one per article)

    /*@JsonIgnore
    List<PosDamageAccountDto> posDamageAccountDtoList;*/
    //Each pointofsale must be related to exactly one cashaccount

    /*@JsonIgnore
    List<ArticleDto> articleDtoList;*/

    @NotNull(message = "The cash account of a pointofsale cannot be null")
    PosCashAccountDto posCashaccountDto;
    //Each pointofsale belongs to 1 enterprise

    @NotNull(message = "The main currency of a pointofsale cannot be null")
    CurrencyDto posCurrencyDto;

    @NotNull(message = "The enterprise owner of a pointofsale cannot be null")
    EnterpriseDto posEnterpriseDto;

    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static PointofsaleDto fromEntity(Pointofsale pos){
        if(pos == null){
            return null;
        }
        return PointofsaleDto.builder()
                .id(pos.getId())
                .posName(pos.getPosName())
                .posAcronym(pos.getPosAcronym())
                .posDescription(pos.getPosDescription())
                .posAddressDto(AddressDto.fromEntity(pos.getPosAddress()))
                .posCashaccountDto(PosCashAccountDto.fromEntity(pos.getPosCashaccount()))
                .posEnterpriseDto(EnterpriseDto.fromEntity(pos.getPosEnterprise()))
                /*.clientDtoList(pos.getClientList() != null ?
                        pos.getClientList().stream()
                        .map(ClientDto::fromEntity)
                        .collect(Collectors.toList()) : null)
                .providerDtoList(pos.getProviderList() != null ?
                        pos.getProviderList().stream()
                        .map(ProviderDto::fromEntity)
                        .collect(Collectors.toList()) : null)
                .posPackagingAccountDtoList(pos.getPosPackagingAccountList() != null ?
                        pos.getPosPackagingAccountList().stream()
                        .map(PosPackagingAccountDto::fromEntity)
                        .collect(Collectors.toList()) : null)
                .packagingDtoList(pos.getPackagingList() != null ?
                        pos.getPackagingList().stream()
                                .map(PackagingDto::fromEntity)
                                .collect(Collectors.toList()) : null)
                .posCapsuleAccountDtoList(pos.getPosCapsuleAccountList() != null ?
                        pos.getPosCapsuleAccountList().stream()
                                .map(PosCapsuleAccountDto::fromEntity)
                                .collect(Collectors.toList()) : null)
                .posDamageAccountDtoList(pos.getPosDamageAccountList() != null ?
                        pos.getPosDamageAccountList().stream()
                                .map(PosDamageAccountDto::fromEntity)
                                .collect(Collectors.toList()) : null)
                .articleDtoList(pos.getArticleList() != null ?
                        pos.getArticleList().stream()
                        .map(ArticleDto::fromEntity)
                        .collect(Collectors.toList()) : null)*/
                .build();
    }
    public static Pointofsale toEntity(PointofsaleDto posDto){
        if(posDto == null){
            return null;
        }
        Pointofsale pos = new Pointofsale();
        pos.setId(posDto.getId());
        pos.setPosName(posDto.getPosName());
        pos.setPosAcronym(posDto.getPosAcronym());
        pos.setPosDescription(posDto.getPosDescription());
        pos.setPosAddress(AddressDto.toEntity(posDto.getPosAddressDto()));
        pos.setPosCashaccount(PosCashAccountDto.toEntity(posDto.getPosCashaccountDto()));
        pos.setPosEnterprise(EnterpriseDto.toEntity(posDto.getPosEnterpriseDto()));
        /*pos.setClientList(posDto.getClientDtoList() != null ?
                posDto.getClientDtoList().stream()
                .map(ClientDto::toEntity)
                .collect(Collectors.toList()) : null);
        pos.setProviderList(posDto.getProviderDtoList() != null ?
                posDto.getProviderDtoList().stream()
                        .map(ProviderDto::toEntity)
                        .collect(Collectors.toList()) : null);
        pos.setPosPackagingAccountList(posDto.getPosPackagingAccountDtoList() != null ?
                posDto.getPosPackagingAccountDtoList().stream()
                        .map(PosPackagingAccountDto::toEntity)
                        .collect(Collectors.toList()) : null);
        pos.setPackagingList(posDto.getPackagingDtoList() != null ?
                posDto.getPackagingDtoList().stream()
                        .map(PackagingDto::toEntity)
                        .collect(Collectors.toList()) : null);
        pos.setPosCapsuleAccountList(posDto.getPosCapsuleAccountDtoList() != null ?
                posDto.getPosCapsuleAccountDtoList().stream()
                        .map(PosCapsuleAccountDto::toEntity)
                        .collect(Collectors.toList()) : null);
        pos.setPosDamageAccountList(posDto.getPosDamageAccountDtoList() != null ?
                posDto.getPosDamageAccountDtoList().stream()
                        .map(PosDamageAccountDto::toEntity)
                        .collect(Collectors.toList()) : null);
        pos.setArticleList(posDto.getArticleDtoList() != null ?
                posDto.getArticleDtoList().stream()
                .map(ArticleDto::toEntity)
                .collect(Collectors.toList()) : null);*/
        return pos;
    }
}
