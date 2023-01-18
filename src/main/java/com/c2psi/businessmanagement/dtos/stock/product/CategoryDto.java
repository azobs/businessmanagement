package com.c2psi.businessmanagement.dtos.stock.product;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.models.Category;
import com.c2psi.businessmanagement.models.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class CategoryDto {
    Long id;
    @NotNull
    @NotEmpty
    String catName;
    String catShortname;
    @NotNull
    @NotEmpty
    String catCode;
    String catDescription;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Each category can have 0 or 1 sup-category

    CategoryDto catParentDto;
    //Each category belongs to 1 pointofsale
    @NotNull
    PointofsaleDto catPosDto;

    /*@JsonIgnore
    List<ProductDto> productDtoList;*/
    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static CategoryDto fromEntity(Category cat){
        if(cat == null){
            return null;
        }
        return CategoryDto.builder()
                .id(cat.getId())
                .catName(cat.getCatName())
                .catShortname(cat.getCatShortname())
                .catCode(cat.getCatCode())
                .catDescription(cat.getCatDescription())
                .catParentDto(CategoryDto.fromEntity(cat.getCatParent()))
                .catPosDto(PointofsaleDto.fromEntity(cat.getCatPos()))
                /*.productDtoList(cat.getProductList() != null ?
                        cat.getProductList().stream()
                        .map(ProductDto::fromEntity)
                        .collect(Collectors.toList()) : null)*/
                .build();
    }
    public static Category toEntity(CategoryDto catDto){
        if(catDto == null){
            return null;
        }
        Category cat = new Category();
        cat.setCatCode(catDto.getCatCode());
        cat.setCatName(catDto.getCatName());
        cat.setCatShortname(catDto.getCatShortname());
        cat.setCatCode(catDto.getCatCode());
        cat.setCatDescription(catDto.getCatDescription());
        cat.setCatParent(CategoryDto.toEntity(catDto.getCatParentDto()));
        cat.setCatPos(PointofsaleDto.toEntity(catDto.getCatPosDto()));
        /*cat.setProductList(catDto.getProductDtoList() != null ?
                catDto.getProductDtoList().stream()
                .map(ProductDto::toEntity)
                .collect(Collectors.toList()) : null);*/
        return cat;
    }
}
