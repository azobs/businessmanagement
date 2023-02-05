package com.c2psi.businessmanagement.dtos.stock.price;

import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import com.c2psi.businessmanagement.models.Article;
import com.c2psi.businessmanagement.models.BasePrice;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@Builder
public class BasePriceDto {
    Long id;
    @NotNull(message = "The purchase price cannot be null")
    @Positive(message = "The purchase price cannot be null")
    BigDecimal bpPurchaseprice;
    @NotNull(message = "The sale whole price cannot be null")
    @Positive(message = "The sale whole price must be positive")
    BigDecimal bpWholesaleprice;
    @NotNull(message = "The sale details price cannot be null")
    @Positive(message = "The sale details price must be positive")
    BigDecimal bpDetailprice;
    @NotNull(message = "The sale semi whole price cannot be null")
    @Positive(message = "The sale semi whole price must be positive")
    BigDecimal bpSemiwholesaleprice;
    @NotNull(message = "The procompte value cannot be null")
    @PositiveOrZero(message = "The procompte value must be positive or null")
    BigDecimal bpPrecompte;
    @NotNull(message = "The ristourne value cannot be null")
    @PositiveOrZero(message = "The ristourne value must be positive or null")
    BigDecimal bpRistourne;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many baseprice is associated to 1 currency
    @NotNull(message = "The currency associated to the price cannot be null")
    CurrencyDto bpCurrencyDto;

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
