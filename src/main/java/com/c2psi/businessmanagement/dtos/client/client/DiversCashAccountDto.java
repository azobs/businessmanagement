package com.c2psi.businessmanagement.dtos.client.client;

import com.c2psi.businessmanagement.models.DiversCashAccount;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
@Data
@Builder
public class DiversCashAccountDto {
    Long id;
    BigDecimal dcaBalance;

    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static DiversCashAccountDto fromEntity(DiversCashAccount dca){
        if(dca == null){
            return null;
        }
        return DiversCashAccountDto.builder()
                .id(dca.getId())
                .dcaBalance(dca.getDcaBalance())
                .build();
    }

    public static DiversCashAccount toEntity(DiversCashAccountDto dca_dto){
        if(dca_dto == null){
            return null;
        }
        DiversCashAccount dca = new DiversCashAccount();
        dca.setId(dca_dto.getId());
        dca.setDcaBalance(dca_dto.getDcaBalance());
        return dca;
    }
}
