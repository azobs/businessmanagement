package com.c2psi.businessmanagement.dtos.stock.product;

import com.c2psi.businessmanagement.models.Product;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
public class ProductDto {
    Long id;
    @NotNull(message = "The product name cannot be null")
    @NotEmpty(message = "The product name cannot be empty")
    @NotBlank(message = "The product name cannot be blank")
    @Size(min = 3, max = 20, message = "The product name size must be between 3 and 20 characters")
    String prodName;
    @Size(max = 300, message = "The product name size must be at most 300 characters")
    String prodDescription;
    @Size(max = 10, message = "The product alias size must at most 10 characters")
    String prodAlias;
    @NotNull(message = "The product must be perishable or not")
    Boolean prodPerishable;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many Product for one Category
    @NotNull(message = "The category associated to the product cannot be null")
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
