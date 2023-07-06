package com.c2psi.businessmanagement.dtos.stock.provider;

import com.c2psi.businessmanagement.dtos.pos.pos.OperationDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.models.ProviderPackagingOperation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@Builder
public class ProviderPackagingOperationDto {
    @ApiModelProperty(value = "The Id of the provider packaging operation in the DB", name = "id", dataType = "Long")
    Long id;
    @NotNull(message = "The packaging operation cannot be null")
    @ApiModelProperty(value = "The Operation", name = "propoOperationDto", dataType = "OperationDto")
    OperationDto propoOperationDto;
    @NotNull(message = "The number of packaging in mvt cannot be null")
    @Positive(message = "The number of packaging in mvt must be positive")
    @ApiModelProperty(value = "The number of packaging in mouvement", name = "propoNumberinmvt", dataType = "String")
    BigDecimal propoNumberinmvt;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many ClientpackagingOperation for 1 ClientPackagingAccount
    @NotNull(message = "The provider packaging account cannot be null")
    @ApiModelProperty(value = "The account packaging link with the operation", name = "propoProPackagingAccountDto",
            dataType = "ProviderPackagingAccountDto")
    ProviderPackagingAccountDto propoProPackagingAccountDto;
    @NotNull(message = "The userbm associated with the operation cannot be null")
    @ApiModelProperty(value = "The UserBM that launch the operation", name = "propoUserbmDto", dataType = "UserBMDto")
    UserBMDto propoUserbmDto;
    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static ProviderPackagingOperationDto fromEntity(ProviderPackagingOperation ppop){
        if(ppop == null){
            return null;
        }
        return ProviderPackagingOperationDto.builder()
                .id(ppop.getId())
                .propoOperationDto(OperationDto.fromEntity(ppop.getPropoOperation()))
                .propoProPackagingAccountDto(ProviderPackagingAccountDto.fromEntity(ppop.getPropoProPackagingAccount()))
                .propoUserbmDto(UserBMDto.fromEntity(ppop.getPropoUserbm()))
                .propoNumberinmvt(ppop.getPropoNumberinmvt())
                .build();
    }
    public static ProviderPackagingOperation toEntity(ProviderPackagingOperationDto ppopDto){
        if(ppopDto == null){
            return null;
        }
        ProviderPackagingOperation ppop = new ProviderPackagingOperation();
        ppop.setId(ppopDto.getId());
        ppop.setPropoNumberinmvt(ppopDto.getPropoNumberinmvt());
        ppop.setPropoProPackagingAccount(ProviderPackagingAccountDto.toEntity(ppopDto.getPropoProPackagingAccountDto()));
        ppop.setPropoUserbm(UserBMDto.toEntity(ppopDto.getPropoUserbmDto()));
        ppop.setPropoOperation(OperationDto.toEntity(ppopDto.getPropoOperationDto()));
        return ppop;
    }
}
