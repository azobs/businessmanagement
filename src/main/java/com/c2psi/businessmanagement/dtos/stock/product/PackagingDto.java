package com.c2psi.businessmanagement.dtos.stock.product;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;
import com.c2psi.businessmanagement.models.Packaging;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
@Builder
public class PackagingDto {
    @ApiModelProperty(value = "The Id of the Packaging in the DB", name = "id", dataType = "Long")
    Long id;
    @NotNull(message = "The packaging label cannot be null")
    @NotEmpty(message = "The packaging label cannot be empty")
    @NotBlank(message = "The packaging label cannot be blank")
    @Size(min = 3, max = 20, message = "The packaging label size must be between 3 and 20 characters")
    @ApiModelProperty(value = "The name used to call the packaging", name = "packLabel", dataType = "String")
    String packLabel;
    @ApiModelProperty(value = "The description used to call the packaging", name = "packDescription", dataType = "String")
    String packDescription;
    @NotNull(message = "The packaging color cannot be null")
    @NotEmpty(message = "The packaging color cannot be empty")
    @NotBlank(message = "The packaging color cannot be blank")
    @Size(min = 3, max = 20, message = "The packaging color size must be between 3 and 20 characters")
    @ApiModelProperty(value = "The color name of the packaging", name = "packFirstcolor", dataType = "String")
    String packFirstcolor;
    @NotNull(message = "The packaging price cannot be null")
    @PositiveOrZero(message = "The packaging price must be positive or zero")
    @ApiModelProperty(value = "The price of the packaging", name = "packPrice", dataType = "BigDecimal")
    BigDecimal packPrice;

    /******************************
     * Relation between entities  *
     * ****************************/
    //Many packaging must be produce by one provide
    @NotNull(message = "The provider of a packaging cannot be null")
    @ApiModelProperty(value = "The provider owner of the packaging", name = "packProviderDto", dataType = "ProviderDto")
    ProviderDto packProviderDto;
    /*@NotNull
    EnterpriseDto packEntDto;*/
    @NotNull(message = "The point of sale associated with the packaging cannot be null")
    //PointofsaleDto packPosDto;
    @ApiModelProperty(value = "The id of the pointofsale link with the packaging", name = "packPosId", dataType = "Long")
    Long packPosId;
    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static PackagingDto fromEntity(Packaging packaging){
        if(packaging == null){
            return null;
        }
        return PackagingDto.builder()
                .id(packaging.getId())
                .packLabel(packaging.getPackLabel())
                .packDescription(packaging.getPackDescription())
                .packFirstcolor(packaging.getPackFirstcolor())
                .packPrice(packaging.getPackPrice())
                .packProviderDto(ProviderDto.fromEntity(packaging.getPackProvider()))
                //.packPosDto(PointofsaleDto.fromEntity(packaging.getPackPos()))
                .packPosId(packaging.getPackPosId())
                .build();
    }
    public static Packaging toEntity(PackagingDto packagingDto){
        if(packagingDto == null){
            return null;
        }
        Packaging packaging = new Packaging();
        packaging.setId(packagingDto.getId());
        packaging.setPackLabel(packagingDto.getPackLabel());
        packaging.setPackDescription(packagingDto.getPackDescription());
        packaging.setPackFirstcolor(packagingDto.getPackFirstcolor());
        packaging.setPackPrice(packagingDto.getPackPrice());
        packaging.setPackProvider(ProviderDto.toEntity(packagingDto.getPackProviderDto()));
        //packaging.setPackPos(PointofsaleDto.toEntity(packagingDto.getPackPosDto()));
        packaging.setPackPosId(packagingDto.getPackPosId());
        return packaging;
    }
}
