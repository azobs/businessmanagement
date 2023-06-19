package com.c2psi.businessmanagement.dtos.stock.provider;

import com.c2psi.businessmanagement.dtos.stock.product.PackagingDto;
import com.c2psi.businessmanagement.models.ProviderPackagingAccount;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
public class ProviderPackagingAccountDto {
    Long id;
    @NotNull(message = "The number in the packaging account cannot ne null")
    BigDecimal ppaNumber;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Each providerpackagingaccount must belonging to one pointofsale

    //PointofsaleDto ppaPosDto;

    //Each providerpackagingaccount must belonging to one provider
    @NotNull(message = "The provider associated with the account cannot be null")
    ProviderDto ppaProviderDto;

    //Each ProviderPackagingAccount is for 1 packaging
    @NotNull(message = "The packaging associated with the account cannot be null")
    PackagingDto ppaPackagingDto;
    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static ProviderPackagingAccountDto fromEntity(ProviderPackagingAccount ppa){
        if(ppa == null){
            return null;
        }
        return ProviderPackagingAccountDto.builder()
                .id(ppa.getId())
                .ppaNumber(ppa.getPpaNumber())
                .ppaPackagingDto(PackagingDto.fromEntity(ppa.getPpaPackaging()))
                .ppaProviderDto(ProviderDto.fromEntity(ppa.getPpaProvider()))
                .build();
    }
    public static ProviderPackagingAccount toEntity(ProviderPackagingAccountDto ppaDto){
        if(ppaDto == null){
            return null;
        }
        ProviderPackagingAccount ppa = new ProviderPackagingAccount();
        ppa.setId(ppaDto.getId());
        ppa.setPpaNumber(ppaDto.getPpaNumber());
        ppa.setPpaPackaging(PackagingDto.toEntity(ppaDto.getPpaPackagingDto()));
        ppa.setPpaProvider(ProviderDto.toEntity(ppaDto.getPpaProviderDto()));
        return ppa;
    }
}
