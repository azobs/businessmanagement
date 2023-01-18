package com.c2psi.businessmanagement.dtos.stock.product;

import com.c2psi.businessmanagement.Enumerations.CashArrivalType;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.models.CashArrival;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@Builder
public class CashArrivalDto {
    Long id;
    @PositiveOrZero
    Integer cashaDeliveryquantity;
    @PositiveOrZero
    BigDecimal cashaUnitprice;
    @NotNull
    @PositiveOrZero
    CashArrivalType cashaArrivaltype;

    /******************************
     * Relation between entities  *
     * ****************************/
    @NotNull
    ArticleDto cashaArtDto;

    SupplyInvoiceCashDto cashaSicashDto;

    //UserBMDto cashaUserbmDto;
    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static CashArrivalDto fromEntity(CashArrival cashArrival){
        if(cashArrival == null){
            return null;
        }
        return CashArrivalDto.builder()
                .id(cashArrival.getId())
                .cashaDeliveryquantity(cashArrival.getCashaDeliveryquantity())
                .cashaUnitprice(cashArrival.getCashaUnitprice())
                .cashaArrivaltype(cashArrival.getCashaArrivaltype())
                .cashaArtDto(ArticleDto.fromEntity(cashArrival.getCashaArt()))
                .cashaSicashDto(SupplyInvoiceCashDto.fromEntity(cashArrival.getCashaSicash()))
                //.cashaUserbmDto(UserBMDto.fromEntity(cashArrival.getCashaUserbm()))
                .build();
    }
    public static CashArrival toEntity(CashArrivalDto cashArrivalDto){
        if(cashArrivalDto == null){
            return null;
        }
        CashArrival ca = new CashArrival();
        ca.setId(cashArrivalDto.getId());
        ca.setCashaDeliveryquantity(cashArrivalDto.getCashaDeliveryquantity());
        ca.setCashaUnitprice(cashArrivalDto.getCashaUnitprice());
        ca.setCashaArrivaltype(cashArrivalDto.getCashaArrivaltype());
        ca.setCashaArt(ArticleDto.toEntity(cashArrivalDto.getCashaArtDto()));
        ca.setCashaSicash(SupplyInvoiceCashDto.toEntity(cashArrivalDto.getCashaSicashDto()));
        //ca.setCashaUserbm(UserBMDto.toEntity(cashArrivalDto.getCashaUserbmDto()));
        return ca;
    }
}
