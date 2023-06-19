package com.c2psi.businessmanagement.dtos.pos.userbm;

import com.c2psi.businessmanagement.models.Address;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class AddressDto {
    @NotNull(message = "The phone number 1 cannot be null")
    @NotEmpty(message = "The phone number 1 cannot be empty value")
    @NotBlank(message = "The phone number 1 cannot be blank value")
    @ApiModelProperty(value = "numtel number1", name = "numtel1", dataType = "String",
            example = "678470262", required = true)
    String numtel1;
    @NotNull(message = "The phone number 2 cannot be null")
    @NotEmpty(message = "The phone number 2 cannot be empty value")
    @NotBlank(message = "The phone number 2 cannot be blank value")
    @ApiModelProperty(value = "numtel number2", name = "numtel2", dataType = "String",
            example = "695093228", required = true)
    String numtel2;
    @ApiModelProperty(value = "numtel number3", name = "numtel3", dataType = "String",
            example = "676170067")
    String numtel3;
    @ApiModelProperty(value = "Living quarter", name = "quartier", dataType = "String",
            example = "Babenga")
    String quartier;
    @ApiModelProperty(value = "Living country", name = "pays", dataType = "String",
            example = "Cameroon")
    String pays;
    @ApiModelProperty(value = "Living town", name = "ville", dataType = "String",
            example = "Douala")
    String ville;
    @ApiModelProperty(value = "Localisation plan", name = "localisation", dataType = "String",
            example = "localisation.png")
    String localisation;
    @Email(message = "The email when is precised should be a valid one")
    @ApiModelProperty(value = "The email of the user", name = "email", dataType = "String",
            example = "test_@gmail.com", required = true)
    String email;
    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static AddressDto fromEntity(Address address){
        if(address == null){
            return null;
        }
        return AddressDto.builder()
                .numtel1(address.getNumtel1())
                .numtel2(address.getNumtel2())
                .numtel3(address.getNumtel3())
                .quartier(address.getQuartier())
                .pays(address.getPays())
                .ville(address.getVille())
                .localisation(address.getLocalisation())
                .email(address.getEmail())
                .build();
    }
    public static Address toEntity(AddressDto address_dto){
        if(address_dto == null){
            return null;
        }
        Address address = new Address();
        address.setEmail(address_dto.getEmail());
        address.setLocalisation(address_dto.getLocalisation());
        address.setPays(address_dto.getPays());
        address.setVille(address_dto.getVille());
        address.setQuartier(address_dto.getQuartier());
        address.setNumtel1(address_dto.getNumtel1());
        address.setNumtel2(address_dto.getNumtel2());
        address.setNumtel3(address_dto.getNumtel3());
        return address;
    }
}
