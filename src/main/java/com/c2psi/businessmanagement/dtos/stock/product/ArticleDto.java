package com.c2psi.businessmanagement.dtos.stock.product;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.price.BasePriceDto;
import com.c2psi.businessmanagement.models.Article;
import com.c2psi.businessmanagement.models.InventoryLine;
import com.c2psi.businessmanagement.models.Pointofsale;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


@Data
@Builder
public class ArticleDto {
    Long id;
    @NotNull
    @NotEmpty
    String artCode;
    @NotNull
    @NotEmpty
    String artName;
    @NotNull
    @NotEmpty
    String artShortname;
    String artComment;
    @PositiveOrZero
    Integer artThreshold;
    @PositiveOrZero
    Integer artLowLimitWholesale;
    @PositiveOrZero
    Integer artLowLimitSemiWholesale;
    @PositiveOrZero
    Integer artQuantityinstock;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many article is associated to 1 ProductFormated
    @NotNull
    ProductFormatedDto artPfDto;
    //Many article must be related to 1 unit
    @NotNull
    UnitDto artUnitDto;
    //Many baseprice is for 1 article
    @NotNull
    BasePriceDto artBpDto;
    @NotNull
    PointofsaleDto artPosDto;

    /*@JsonIgnore
    List<InventoryLineDto> inventoryLineDtoList;*/
    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static ArticleDto fromEntity(Article article){
        if(article == null){
            return null;
        }
        return ArticleDto.builder()
                .id(article.getId())
                .artCode(article.getArtCode())
                .artName(article.getArtName())
                .artShortname(article.getArtShortname())
                .artComment(article.getArtComment())
                .artThreshold(article.getArtThreshold())
                .artLowLimitSemiWholesale(article.getArtLowLimitSemiWholesale())
                .artLowLimitWholesale(article.getArtLowLimitWholesale())
                .artQuantityinstock(article.getArtQuantityinstock())
                .artPfDto(ProductFormatedDto.fromEntity(article.getArtPf()))
                .artUnitDto(UnitDto.fromEntity(article.getArtUnit()))
                .artBpDto(BasePriceDto.fromEntity(article.getArtBp()))
                .artPosDto(PointofsaleDto.fromEntity(article.getArtPos()))
                /*.inventoryLineDtoList(article.getInventoryLineList() != null ?
                        article.getInventoryLineList().stream()
                        .map(InventoryLineDto::fromEntity)
                        .collect(Collectors.toList()) : null)*/
                .build();
    }
    public static Article toEntity(ArticleDto articleDto){
        if(articleDto == null){
            return null;
        }
        Article art = new Article();
        art.setId(articleDto.getId());
        art.setArtCode(articleDto.getArtCode());
        art.setArtComment(articleDto.getArtComment());
        art.setArtThreshold(articleDto.getArtThreshold());
        art.setArtLowLimitWholesale(articleDto.getArtLowLimitWholesale());
        art.setArtLowLimitSemiWholesale(articleDto.getArtLowLimitSemiWholesale());
        art.setArtQuantityinstock(articleDto.getArtQuantityinstock());
        art.setArtPf(ProductFormatedDto.toEntity(articleDto.getArtPfDto()));
        art.setArtUnit(UnitDto.toEntity(articleDto.getArtUnitDto()));
        art.setArtBp(BasePriceDto.toEntity(articleDto.getArtBpDto()));
        art.setArtPos(PointofsaleDto.toEntity(articleDto.getArtPosDto()));
        /*art.setInventoryLineList(articleDto.getInventoryLineDtoList() != null ?
                articleDto.getInventoryLineDtoList().stream()
                .map(InventoryLineDto::toEntity)
                .collect(Collectors.toList()) : null);*/
        return art;
    }
}
