package com.c2psi.businessmanagement.dtos.pos.pos;

import com.c2psi.businessmanagement.models.PosCashAccount;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
public class PosCashAccountDto {
    @ApiModelProperty(value = "The id of the PosCashAccount", name = "id", dataType = "Long")
    Long id;
    @NotNull(message = "The account balance cannot be null")
    @ApiModelProperty(value = "The PosCashAccount balance", name = "pcaBalance", dataType = "BigDecimal")
    BigDecimal pcaBalance;
    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static PosCashAccountDto fromEntity(PosCashAccount pca){
        if(pca == null){
            return null;
        }
        return PosCashAccountDto.builder()
                .id(pca.getId())
                .pcaBalance(pca.getPcaBalance())
                .build();
    }
    public static PosCashAccount toEntity(PosCashAccountDto pcaDto){
        if(pcaDto == null){
            return null;
        }
        PosCashAccount pca = new PosCashAccount();
        pca.setId(pcaDto.getId());
        pca.setPcaBalance(pcaDto.getPcaBalance());
        return pca;
    }
}
