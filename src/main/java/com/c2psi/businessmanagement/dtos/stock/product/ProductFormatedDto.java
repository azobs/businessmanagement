package com.c2psi.businessmanagement.dtos.stock.product;

import com.c2psi.businessmanagement.models.ProductFormated;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
@Builder
@ApiModel
public class ProductFormatedDto {
    @ApiModelProperty(value = "The id of the product Formated", name = "id", dataType = "Long")
    Long id;
    @ApiModelProperty(value = "The fullname of a picture that describe the product in the corresponding format",
            name = "pfPicture", dataType = "String")
    String pfPicture;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Each productformated is associated to 1 Product
    @NotNull(message = "The product associated cannot be null")
    @ApiModelProperty(value = "The associated product", name = "pfProductDto", dataType = "ProductDto")
    ProductDto pfProductDto;
    //Each productformated is associated to 1 format
    @NotNull(message = "The format associated cannot be null")
    @ApiModelProperty(value = "The associated format", name = "pfFormatDto", dataType = "FormatDto")
    FormatDto pfFormatDto;
    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static ProductFormatedDto fromEntity(ProductFormated productFormated){
        if(productFormated == null){
            return null;
        }
        return ProductFormatedDto.builder()
                .id(productFormated.getId())
                .pfPicture(productFormated.getPfPicture())
                .pfProductDto(ProductDto.fromEntity(productFormated.getPfProduct()))
                .pfFormatDto(FormatDto.fromEntity(productFormated.getPfFormat()))
                .build();
    }
    public static ProductFormated toEntity(ProductFormatedDto productFormatedDto){
        if(productFormatedDto == null){
            return null;
        }
        ProductFormated pf = new ProductFormated();
        pf.setId(productFormatedDto.getId());
        pf.setPfPicture(productFormatedDto.getPfPicture());
        pf.setPfFormat(FormatDto.toEntity(productFormatedDto.getPfFormatDto()));
        pf.setPfProduct(ProductDto.toEntity(productFormatedDto.getPfProductDto()));
        return pf;
    }
}
