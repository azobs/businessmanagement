package com.c2psi.businessmanagement.dtos.stock.provider;

import com.c2psi.businessmanagement.models.ProviderCashAccount;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
public class ProviderCashAccountDto {
    @ApiModelProperty(value = "The Id of the providercashaccount in the DB", name = "id", dataType = "Long")
    Long id;
    @NotNull(message = "The provider account balance cannot be null")
    @ApiModelProperty(value = "The provider cash account balance", name = "pcaBalance", dataType = "BigDecimal")
    BigDecimal pcaBalance;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Each providercashaccount must belonging to one pointofsale

    //PointofsaleDto pcaPointofsaleDto;

    //Each providercapsuleaccount must belonging to one provider

    //ProviderDto pcaProviderDto;
    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static ProviderCashAccountDto fromEntity(ProviderCashAccount pca){
        if(pca == null){
            return null;
        }
        return ProviderCashAccountDto.builder()
                .id(pca.getId())
                .pcaBalance(pca.getPcaBalance())
                .build();
    }
    public static ProviderCashAccount toEntity(ProviderCashAccountDto pcaDto){
        if(pcaDto == null){
            return null;
        }
        ProviderCashAccount pca = new ProviderCashAccount();
        pca.setId(pcaDto.getId());
        pca.setPcaBalance(pcaDto.getPcaBalance());
        return pca;
    }
}
