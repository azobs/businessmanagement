package com.c2psi.businessmanagement.dtos.stock.product;

import com.c2psi.businessmanagement.Enumerations.CashArrivalType;
import com.c2psi.businessmanagement.models.CashArrival;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
public class CashArrivalDto {
    Long id;
    @NotNull(message = "The delivery quantity cannot be null")
    @Positive(message = "The delivery quantity must be positive")
    BigDecimal cashaDeliveryquantity;
    @NotNull(message = "The final unit price cannot be null")
    @Positive(message = "The final unit price must be positive")
    BigDecimal cashaUnitprice;
    @NotNull(message = "The cash arrival type cannot be null")
    CashArrivalType cashaArrivaltype;

    @NotNull(message = "The entry date cannot be null")
    @PastOrPresent(message = "The entry date cannot be in the future")
    Instant cashaArrivalEntryDate;

    /******************************
     * Relation between entities  *
     * ****************************/
    @NotNull(message = "The article associated with the cash arrival cannot be null")
    ArticleDto cashaArtDto;

    SupplyInvoiceCashDto cashaSicashDto;

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
                .cashaArrivalEntryDate(cashArrival.getCashaArrivalEntryDate())
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
        ca.setCashaArrivalEntryDate(cashArrivalDto.getCashaArrivalEntryDate());
        return ca;
    }
}
