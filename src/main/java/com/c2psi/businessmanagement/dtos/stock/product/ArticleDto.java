package com.c2psi.businessmanagement.dtos.stock.product;

import com.c2psi.businessmanagement.dtos.stock.price.BasePriceDto;
import com.c2psi.businessmanagement.models.Article;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.math.MathContext;


@Data
@Builder
@ApiModel
public class ArticleDto {
    @ApiModelProperty(value = "The id of the article", name = "id", dataType = "Long")
    Long id;
    @NotNull(message = "The article code cannot be null")
    @NotEmpty(message = "The article code cannot be empty")
    @NotBlank(message = "The article code cannot be blank")
    @Size(min = 3, max = 30, message = "The article code size must be between 3 and 30 characters")
    @ApiModelProperty(value = "The code of the article", name = "artCode", dataType = "String", example = "Code")
    String artCode;
    @NotNull(message = "The article name cannot be null")
    @NotEmpty(message = "The article name cannot be empty")
    @NotBlank(message = "The article name cannot be blank")
    @Size(min = 3, max = 75, message = "The article name size must be between 3 and 75 characters")
    @ApiModelProperty(value = "The name of the article", name = "artName", dataType = "String", example = "Name")
    String artName;
    @NotEmpty(message = "The article shortname cannot be empty")
    @NotBlank(message = "The article shortname cannot be blank")
    @Size(min = 3, max = 50, message = "The article shortname size must be between 3 and 50 characters")
    @ApiModelProperty(value = "The shortname of the article", name = "artShortname", dataType = "String", example = "Shortname")
    String artShortname;
    @Size(max = 150, message = "The article shortname size must be at least 150 characters")
    @ApiModelProperty(value = "The description of the article", name = "artDescription", dataType = "String", example = "details about the article")
    String artDescription;
    @NotNull(message = "The threshold value cannot be null")
    @PositiveOrZero(message = "The threshold value must be positive or zero")
    //@ApiModelProperty(value = "The threshold of the article", name = "artThreshold", dataType = "BigDecimal", example = "5")
    //BigDecimal artThreshold;
    @ApiModelProperty(value = "The threshold of the article", name = "artThreshold", dataType = "Double", example = "5")
    Double artThreshold;
    /*****
     * A negative value means there the article cannot be selling in whole
     */
    @NotNull(message = "The low limit value to sell in whole value cannot be null")
//    @ApiModelProperty(value = "The low limit to sell in whole", name = "artLowLimitWholesale", dataType = "BigDecimal",
//            example = "100")
//    BigDecimal artLowLimitWholesale;
    @ApiModelProperty(value = "The low limit to sell in whole", name = "artLowLimitWholesale", dataType = "Double",
            example = "100")
    Double artLowLimitWholesale;
    /*****
     * A negative value means there the article cannot be selling in semi whole
     * If an article cannot be selling in semi whole then it cannot be also selling in whole.
     */
    @NotNull(message = "The low limit value to sell in semi whole value cannot be null")
//    @ApiModelProperty(value = "The low limit to sell in semi whole", name = "artLowLimitSemiWholesale", dataType = "BigDecimal",
//            example = "75")
//    BigDecimal artLowLimitSemiWholesale;
    @ApiModelProperty(value = "The low limit to sell in semi whole", name = "artLowLimitSemiWholesale", dataType = "Double",
            example = "75")
    Double artLowLimitSemiWholesale;
    @NotNull(message = "The current quantity in stock cannot be null")
    @PositiveOrZero(message = "The current quantity in stock must be positive or zero")
//    @ApiModelProperty(value = "The real quantity in stock", name = "artQuantityinstock", dataType = "BigDecimal",
//            example = "10")
//    BigDecimal artQuantityinstock;
    @ApiModelProperty(value = "The real quantity in stock", name = "artQuantityinstock", dataType = "Double",
            example = "10")
    Double artQuantityinstock;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many article is associated to 1 ProductFormated
    @NotNull(message = "The formated product associated cannot be null")
    @ApiModelProperty(value = "The productformated associate", name = "artPfDto", dataType = "ProductFormatedDto")
    ProductFormatedDto artPfDto;
    //Many article must be related to 1 unit(Unit represent conditionnement)
    @NotNull(message = "The unit associated cannot be null")
    @ApiModelProperty(value = "The unit associate", name = "artUnitDto", dataType = "UnitDto")
    UnitDto artUnitDto;
    //Many baseprice is for 1 article
    @NotNull(message = "The base price associated cannot be null")
    @ApiModelProperty(value = "The base price associate", name = "artBpDto", dataType = "BasePriceDto")
    BasePriceDto artBpDto;
    @NotNull(message = "The point of sale associated cannot be null")
    //PointofsaleDto artPosDto;
    @ApiModelProperty(value = "The id of the pointofsale which belong the article", name = "artPosDto", dataType = "Long")
    Long artPosId;

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
                .artThreshold(article.getArtThreshold().doubleValue())
                .artLowLimitSemiWholesale(article.getArtLowLimitSemiWholesale().doubleValue())
                .artLowLimitWholesale(article.getArtLowLimitWholesale().doubleValue())
                .artQuantityinstock(article.getArtQuantityinstock().doubleValue())
                .artPfDto(ProductFormatedDto.fromEntity(article.getArtPf()))
                .artUnitDto(UnitDto.fromEntity(article.getArtUnit()))
                .artBpDto(BasePriceDto.fromEntity(article.getArtBp()))
                //.artPosDto(PointofsaleDto.fromEntity(article.getArtPos()))
                .artPosId(article.getArtPosId())

                .build();
    }
    public static Article toEntity(ArticleDto ArticleDto){
        if(ArticleDto == null){
            return null;
        }
        Article art = new Article();
        art.setId(ArticleDto.getId());
        art.setArtCode(ArticleDto.getArtCode());
        art.setArtName(ArticleDto.getArtName());
        art.setArtShortname(ArticleDto.getArtShortname());
        art.setArtDescription(ArticleDto.getArtDescription());
//        art.setArtThreshold(articleDto.getArtThreshold());
//        art.setArtLowLimitWholesale(articleDto.getArtLowLimitWholesale());
//        art.setArtLowLimitSemiWholesale(articleDto.getArtLowLimitSemiWholesale());
//        art.setArtQuantityinstock(articleDto.getArtQuantityinstock());
        art.setArtThreshold(new BigDecimal(ArticleDto.getArtThreshold(), new MathContext(2)));
        art.setArtLowLimitWholesale(new BigDecimal(ArticleDto.getArtLowLimitWholesale(), new MathContext(2)));
        art.setArtLowLimitSemiWholesale(new BigDecimal(ArticleDto.getArtLowLimitSemiWholesale(), new MathContext(2)));
        art.setArtQuantityinstock(new BigDecimal(ArticleDto.getArtQuantityinstock(), new MathContext(2)));
        art.setArtPf(ProductFormatedDto.toEntity(ArticleDto.getArtPfDto()));
        art.setArtUnit(UnitDto.toEntity(ArticleDto.getArtUnitDto()));
        art.setArtBp(BasePriceDto.toEntity(ArticleDto.getArtBpDto()));
        //art.setArtPos(PointofsaleDto.toEntity(articleDto.getArtPosDto()));
        art.setArtPosId(ArticleDto.getArtPosId());

        return art;
    }
}
