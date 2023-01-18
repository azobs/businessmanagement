package com.c2psi.businessmanagement.dtos.stock.product;

import com.c2psi.businessmanagement.models.ProductFormated;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
@Builder
public class ProductFormatedDto {
    Long id;
    String pfPicture;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Each productformated is associated to 1 Product
    @NotNull
    ProductDto pfProductDto;
    //Each productformated is associated to 1 format
    @NotNull
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
