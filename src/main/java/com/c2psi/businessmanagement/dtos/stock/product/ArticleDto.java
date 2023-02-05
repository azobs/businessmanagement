package com.c2psi.businessmanagement.dtos.stock.product;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.price.BasePriceDto;
import com.c2psi.businessmanagement.models.Article;
import com.c2psi.businessmanagement.models.InventoryLine;
import com.c2psi.businessmanagement.models.Pointofsale;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


@Data
@Builder
public class ArticleDto {
    Long id;
    @NotNull(message = "The article code cannot be null")
    @NotEmpty(message = "The article code cannot be empty")
    @NotBlank(message = "The article code cannot be blank")
    @Size(min = 3, max = 20, message = "The article code size must be between 3 and 10 characters")
    String artCode;
    @NotNull(message = "The article name cannot be null")
    @NotEmpty(message = "The article name cannot be empty")
    @NotBlank(message = "The article name cannot be blank")
    @Size(min = 3, max = 20, message = "The article name size must be between 3 and 20 characters")
    String artName;
    @NotEmpty(message = "The article shortname cannot be empty")
    @NotBlank(message = "The article shortname cannot be blank")
    @Size(min = 3, max = 20, message = "The article shortname size must be between 3 and 10 characters")
    String artShortname;
    String artDescription;
    @NotNull(message = "The threshold value cannot be null")
    @PositiveOrZero(message = "The threshold value must be positive or zero")
    Integer artThreshold;
    /*****
     * A negative value means there the article cannot be selling in whole
     */
    @NotNull(message = "The low limit value to sell in whole value cannot be null")
    Integer artLowLimitWholesale;
    /*****
     * A negative value means there the article cannot be selling in semi whole
     * If an article cannot be selling in semi whole then it cannot be also selling in whole.
     */
    @NotNull(message = "The low limit value to sell in semi whole value cannot be null")
    Integer artLowLimitSemiWholesale;
    @NotNull(message = "The current quantity in stock cannot be null")
    @PositiveOrZero(message = "The current quantity in stock must be positive or zero")
    Integer artQuantityinstock;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many article is associated to 1 ProductFormated
    @NotNull(message = "The formated product associated cannot be null")
    ProductFormatedDto artPfDto;
    //Many article must be related to 1 unit(Unit represent conditionnement)
    @NotNull(message = "The unit associated cannot be null")
    UnitDto artUnitDto;
    //Many baseprice is for 1 article
    @NotNull(message = "The base price associated cannot be null")
    BasePriceDto artBpDto;
    @NotNull(message = "The point of sale associated cannot be null")
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
                .artDescription(article.getArtDescription())
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
        art.setArtDescription(articleDto.getArtDescription());
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
