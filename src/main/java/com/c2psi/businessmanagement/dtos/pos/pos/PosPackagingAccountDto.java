package com.c2psi.businessmanagement.dtos.pos.pos;

import com.c2psi.businessmanagement.dtos.stock.product.PackagingDto;
import com.c2psi.businessmanagement.models.PosPackagingAccount;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class PosPackagingAccountDto {
    Long id;
    @NotNull(message = "The number of package cannot be null")
    Integer ppaNumber;


    /******************************
     * Relation between entities  *
     * ****************************/
    //Each packagingaccount must belonging to one pointofsale

    @NotNull(message = "The pointofsale associate with the account cannot be null")
    PointofsaleDto ppaPointofsaleDto;

    //Each packagingaccount must be related to exactly one packaging

    @NotNull(message = "The packaging associate with the account cannot be null")
    PackagingDto ppaPackagingDto;

    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static PosPackagingAccountDto fromEntity(PosPackagingAccount ppa){
        if(ppa == null){
            return null;
        }
        return PosPackagingAccountDto.builder()
                .id(ppa.getId())
                .ppaNumber(ppa.getPpaNumber())
                .ppaPointofsaleDto(PointofsaleDto.fromEntity(ppa.getPpaPointofsale()))
                .ppaPackagingDto(PackagingDto.fromEntity(ppa.getPpaPackaging()))
                .build();
    }
    public static PosPackagingAccount toEntity(PosPackagingAccountDto ppaDto){
        if(ppaDto == null){
            return null;
        }
        PosPackagingAccount ppa = new PosPackagingAccount();
        ppa.setId(ppaDto.getId());
        ppa.setPpaNumber(ppaDto.getPpaNumber());
        ppa.setPpaPackaging(PackagingDto.toEntity(ppaDto.getPpaPackagingDto()));
        ppa.setPpaPointofsale(PointofsaleDto.toEntity(ppaDto.getPpaPointofsaleDto()));
        return ppa;
    }
}
