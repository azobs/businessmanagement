package com.c2psi.businessmanagement.dtos.pos.pos;

import com.c2psi.businessmanagement.dtos.pos.userbm.AddressDto;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyDto;
import com.c2psi.businessmanagement.models.Pointofsale;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
public class PointofsaleDto {
    @ApiModelProperty(value = "The id of the Pointofsale", name = "id", dataType = "Long")
    Long id;
    @NotNull(message = "The pointofsale name cannot be null")
    @NotEmpty(message = "The pointofsale name cannot be empty value")
    @NotBlank(message = "The pointofsale name cannot be blank value")
    @Size(max = 30, message = "The pointofsale name cannot exceed 30 characters")
    @ApiModelProperty(value = "The name of the pointofsale", name = "posName", dataType = "String", example = "Depot")
    String posName;

    @NotNull(message = "The pointofsale acronym cannot be null")
    @NotEmpty(message = "The pointofsale acronym cannot be empty value")
    @NotBlank(message = "The pointofsale acronym cannot be blank value")
    @Size(max = 30, message = "The pointofsale acronym cannot exceed 30 characters")
    @ApiModelProperty(value = "The acronym of the pointofsale", name = "posAcronym", dataType = "String", example = "D")
    String posAcronym;
    @ApiModelProperty(value = "The description of the pointofsale", name = "posDescription", dataType = "String")
    String posDescription;

    @Valid
    AddressDto posAddressDto;

    /********************************
     * Relation between Dto entities*
     * ******************************/
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
    @ApiModelProperty(value = "The pointofsale cash account", name = "posCashaccountDto", dataType = "PosCashAccountDto")
    PosCashAccountDto posCashaccountDto;
    //Each pointofsale belongs to 1 enterprise

    @NotNull(message = "The main currency of a pointofsale cannot be null")
    @ApiModelProperty(value = "The currency of the pointofsale", name = "posCurrencyDto", dataType = "CurrencyDto")
    CurrencyDto posCurrencyDto;

    @NotNull(message = "The enterprise owner of a pointofsale cannot be null")
    @ApiModelProperty(value = "The enterprise of the pointofsale", name = "posEnterpriseDto", dataType = "EnterpriseDto")
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
                .posCurrencyDto(CurrencyDto.fromEntity(pos.getPosCurrency()))
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
        pos.setPosCurrency(CurrencyDto.toEntity(posDto.getPosCurrencyDto()));
        return pos;
    }
}
