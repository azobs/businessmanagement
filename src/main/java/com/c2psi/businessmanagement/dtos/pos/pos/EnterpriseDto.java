package com.c2psi.businessmanagement.dtos.pos.pos;

import com.c2psi.businessmanagement.dtos.pos.userbm.AddressDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.models.Enterprise;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class EnterpriseDto {
    @ApiModelProperty(value = "The Id of the Enterprise in the DB", name = "id", dataType = "Long")
    Long id;
    @NotEmpty(message = "The regime of an enterprise cannot be empty")
    @NotBlank(message = "The enterprise regime cannot be blank value")
    @ApiModelProperty(value = "The enterprise regime", name = "entRegime", dataType = "String", example = "SIMPLIFIE")
    String entRegime;
    @NotEmpty(message = "The social reason of an enterprise cannot be empty")
    @NotBlank(message = "The entreprise social reason cannot be blank value")
    @ApiModelProperty(value = "The social reason of the enterprise", name = "entSocialreason", dataType = "String",
            example = "COMMERCE GENERALE")
    String entSocialreason;
    @NotEmpty(message = "The enterprise description cannot be empty")
    @NotBlank(message = "The entreprise description cannot be blank value")
    @ApiModelProperty(value = "The enterprise description", name = "entDescription", dataType = "String")
    String entDescription;
    @NotEmpty(message = "The NIU of an enterprise cannot be empty")
    @NotBlank(message = "The entreprise NIU cannot be blank value")
    @ApiModelProperty(value = "The Unique Identification Number", name = "entNiu", dataType = "String")
    String entNiu;

    @NotNull(message = "The enterprise name of an enterprise cannot be null")
    @NotEmpty(message = "The enterprise name cannot be empty")
    @NotBlank(message = "The entreprise name cannot be blank value")
    @ApiModelProperty(value = "The enterprise name", name = "entName", dataType = "String",
            example = "C2PSI_Depot de BOISSON_", required = true)
    String entName;

    @NotEmpty(message = "The enterprise acronym cannot be empty")
    @NotBlank(message = "The entreprise acronym cannot be blank value")
    @ApiModelProperty(value = "The enterprise acronym", name = "entAcronym", dataType = "String", example = "C2PSI_DD",
            required = true)
    String entAcronym;
    @ApiModelProperty(value = "The enterprise logo", name = "entLogo", dataType = "String")
    String entLogo;

    @Valid
    AddressDto entAddressDto;

    @NotNull(message = "The user admin of enterprise cannot be null")
    @ApiModelProperty(value = "The UserBM that administrate the enterprise", name = "entAdminDto", dataType = "UserBMDto")
    UserBMDto entAdminDto;
    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static EnterpriseDto fromEntity(Enterprise ent){
        if(ent == null){
            return null;
        }
        return EnterpriseDto.builder()
                .id(ent.getId())
                .entRegime(ent.getEntRegime())
                .entSocialreason(ent.getEntSocialreason())
                .entDescription(ent.getEntDescription())
                .entNiu(ent.getEntNiu())
                .entName(ent.getEntName())
                .entAcronym(ent.getEntAcronym())
                .entLogo(ent.getEntLogo())
                .entAddressDto(AddressDto.fromEntity(ent.getEntAddress()))
                .entAdminDto(UserBMDto.fromEntity(ent.getEntAdmin()))
                .build();
    }
    public static Enterprise toEntity(EnterpriseDto entDto){
        if(entDto == null){
            return null;
        }
        Enterprise ent = new Enterprise();
        ent.setId(entDto.getId());
        ent.setEntRegime(entDto.getEntRegime());
        ent.setEntSocialreason(entDto.getEntSocialreason());
        ent.setEntDescription(entDto.getEntDescription());
        ent.setEntNiu(entDto.getEntNiu());
        ent.setEntName(entDto.getEntName());
        ent.setEntAcronym(entDto.getEntAcronym());
        ent.setEntLogo(entDto.getEntLogo());
        ent.setEntAddress(AddressDto.toEntity(entDto.getEntAddressDto()));
        ent.setEntAdmin(UserBMDto.toEntity(entDto.getEntAdminDto()));
        return ent;
    }
}
