package com.c2psi.businessmanagement.dtos.stock.price;

import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import com.c2psi.businessmanagement.models.Article;
import com.c2psi.businessmanagement.models.BasePrice;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@Builder
public class BasePriceDto {
    Long id;
    @PositiveOrZero
    BigDecimal bpPurchaseprice;
    @PositiveOrZero
    BigDecimal bpWholesaleprice;
    @PositiveOrZero
    BigDecimal bpDetailprice;
    @PositiveOrZero
    BigDecimal bpSemiwholesaleprice;
    @PositiveOrZero
    BigDecimal bpPrecompte;
    @PositiveOrZero
    BigDecimal bpRistourne;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many baseprice is associated to 1 currency
    @NotNull
    CurrencyDto bpCurrencyDto;
    /*@JsonIgnore
    ArticleDto bp_artDto;*/
    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static BasePriceDto fromEntity(BasePrice bp){
        if(bp == null){
            return null;
        }
        return BasePriceDto.builder()
                .id(bp.getId())
                .bpPurchaseprice(bp.getBpPurchaseprice())
                .bpWholesaleprice(bp.getBpWholesaleprice())
                .bpDetailprice(bp.getBpDetailprice())
                .bpSemiwholesaleprice(bp.getBpSemiwholesaleprice())
                .bpPrecompte(bp.getBpPrecompte())
                .bpRistourne(bp.getBpRistourne())
                .bpCurrencyDto(CurrencyDto.fromEntity(bp.getBpCurrency()))
                /*.bp_artDto(ArticleDto.fromEntity(bp.getBp_art()))*/
                .build();
    }
    public static BasePrice toEntity(BasePriceDto bpDto){
        if(bpDto == null){
            return null;
        }
        BasePrice bp = new BasePrice();
        bp.setId(bpDto.getId());
        bp.setBpPurchaseprice(bpDto.getBpPurchaseprice());
        bp.setBpWholesaleprice(bpDto.getBpWholesaleprice());
        bp.setBpDetailprice(bpDto.getBpDetailprice());
        bp.setBpSemiwholesaleprice(bpDto.getBpSemiwholesaleprice());
        bp.setBpPrecompte(bpDto.getBpPrecompte());
        bp.setBpRistourne(bpDto.getBpRistourne());
        bp.setBpCurrency(CurrencyDto.toEntity(bpDto.getBpCurrencyDto()));
        /*bp.setBp_art(ArticleDto.toEntity(bpDto.getBp_artDto()));*/
        return bp;
    }
}
