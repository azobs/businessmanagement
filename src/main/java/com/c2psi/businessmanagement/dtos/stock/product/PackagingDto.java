package com.c2psi.businessmanagement.dtos.stock.product;

import com.c2psi.businessmanagement.dtos.pos.pos.EnterpriseDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;
import com.c2psi.businessmanagement.models.Packaging;
import com.c2psi.businessmanagement.models.Pointofsale;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Builder
public class PackagingDto {
    Long id;
    @NotNull
    @NotEmpty
    @Size(max = 30)
    String packLabel;
    String packDescription;
    @NotNull
    @NotEmpty
    String packFirstcolor;
    @PositiveOrZero
    BigDecimal packPrice;

    /******************************
     * Relation between entities  *
     * ****************************/
    //Many packaging must be produce by one provide
    @NotNull
    ProviderDto packProviderDto;
    /*@NotNull
    EnterpriseDto packEntDto;*/
    @NotNull
    PointofsaleDto packPosDto;
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
                .packPosDto(PointofsaleDto.fromEntity(packaging.getPackPos()))
                //.packEntDto(EnterpriseDto.fromEntity(packaging.getPackEnt()))
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
        packaging.setPackPos(PointofsaleDto.toEntity(packagingDto.getPackPosDto()));
        //packaging.setPackEnt(EnterpriseDto.toEntity(packagingDto.getPackEntDto()));
        return packaging;
    }
}
