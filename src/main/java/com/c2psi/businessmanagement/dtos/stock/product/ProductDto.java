package com.c2psi.businessmanagement.dtos.stock.product;

import com.c2psi.businessmanagement.models.Product;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class ProductDto {
    Long id;
    @NotNull
    @NotEmpty
    String prodName;
    String prodDescription;
    String prodAlias;
    Boolean prodPerishable;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many Product for one Category
    @NotNull
    CategoryDto prodCatDto;
    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static ProductDto fromEntity(Product product){
        if(product == null){
            return null;
        }
        return ProductDto.builder()
                .id(product.getId())
                .prodName(product.getProdName())
                .prodDescription(product.getProdDescription())
                .prodAlias(product.getProdAlias())
                .prodPerishable(product.getProdPerishable())
                .prodCatDto(CategoryDto.fromEntity(product.getProdCat()))
                .build();
    }
    public static Product toEntity(ProductDto productDto){
        if(productDto == null){
            return null;
        }
        Product product = new Product();
        product.setProdAlias(productDto.getProdAlias());
        product.setProdName(productDto.getProdName());
        product.setProdDescription(productDto.getProdDescription());
        product.setProdPerishable(productDto.getProdPerishable());
        product.setProdCat(CategoryDto.toEntity(productDto.getProdCatDto()));
        return product;
    }
}
