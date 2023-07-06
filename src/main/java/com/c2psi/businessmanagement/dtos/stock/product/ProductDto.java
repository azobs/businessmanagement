package com.c2psi.businessmanagement.dtos.stock.product;

import com.c2psi.businessmanagement.models.Product;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
public class ProductDto {
    @ApiModelProperty(value = "The id of the Product", name = "id", dataType = "Long")
    Long id;
    @NotNull(message = "The product code cannot be null")
    @NotEmpty(message = "The product code cannot be empty")
    @NotBlank(message = "The product code cannot be blank")
    @Size(min = 2, max = 25, message = "The product code size must be between 3 and 20 characters")
    @ApiModelProperty(value = "The code of the product in the pointofsale", name = "prodCode", dataType = "String",
            example = "code")
    String prodCode;
    @NotNull(message = "The product name cannot be null")
    @NotEmpty(message = "The product name cannot be empty")
    @NotBlank(message = "The product name cannot be blank")
    @Size(min = 3, max = 100, message = "The product name size must be between 3 and 100 characters")
    @ApiModelProperty(value = "The name of the product in the pointofsale", name = "prodName", dataType = "String",
            example = "name")
    String prodName;
    @Size(max = 300, message = "The product name size must be at most 300 characters")
    @ApiModelProperty(value = "The description of the product in the pointofsale", name = "prodDescription",
            dataType = "String", example = "details about the product")
    String prodDescription;
    @Size(max = 100, message = "The product alias size must at most 10 characters")
    @ApiModelProperty(value = "Another names for the product", name = "prodAlias", dataType = "String",
            example = "alias")
    String prodAlias;
    @NotNull(message = "The product must be perishable or not")
    @ApiModelProperty(value = "is perishable?", name = "prodPerishable", dataType = "Boolean",
            example = "false")
    Boolean prodPerishable;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many Product for one Category
    @NotNull(message = "The category associated to the product cannot be null")
    @ApiModelProperty(value = "The category owner of the product", name = "prodCatDto", dataType = "CategoryDto")
    CategoryDto prodCatDto;
    @NotNull(message = "The pointofsale associated to the product cannot be null")
    //PointofsaleDto prodPosDto;
    @ApiModelProperty(value = "The pointofsale in which the product is", name = "prodPosId", dataType = "Long",
            example = "1")
    Long prodPosId;
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
                .prodCode(product.getProdCode())
                .prodName(product.getProdName())
                .prodDescription(product.getProdDescription())
                .prodAlias(product.getProdAlias())
                .prodPerishable(product.getProdPerishable())
                .prodCatDto(CategoryDto.fromEntity(product.getProdCat()))
                //.prodPosDto(PointofsaleDto.fromEntity(product.getProdPos()))
                .prodPosId(product.getProdPosId())
                .build();
    }
    public static Product toEntity(ProductDto productDto){
        if(productDto == null){
            return null;
        }
        Product product = new Product();
        product.setId(productDto.getId());
        product.setProdCode(productDto.getProdCode());
        product.setProdAlias(productDto.getProdAlias());
        product.setProdName(productDto.getProdName());
        product.setProdDescription(productDto.getProdDescription());
        product.setProdPerishable(productDto.getProdPerishable());
        product.setProdCat(CategoryDto.toEntity(productDto.getProdCatDto()));
        //product.setProdPos(PointofsaleDto.toEntity(productDto.getProdPosDto()));
        product.setProdPosId(productDto.getProdPosId());
        return product;
    }
}
