package com.c2psi.businessmanagement.dtos.stock.product;

import com.c2psi.businessmanagement.models.CategoryParentCategory;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class CategoryParentCategoryDto {
    Long id;
    @NotNull(message = "The Child category of the relation cannot be null")
    CategoryDto childCategoryDto;

    @NotNull(message = "The Parent category of the relation cannot be null")
    CategoryDto parentCategoryDto;

    public static CategoryParentCategoryDto fromEntity(CategoryParentCategory catPatCat){
        if(catPatCat == null){
            return null;
        }
        return CategoryParentCategoryDto.builder()
                .id(catPatCat.getId())
                .childCategoryDto(CategoryDto.fromEntity(catPatCat.getChildCategory()))
                .parentCategoryDto(CategoryDto.fromEntity(catPatCat.getParentCategory()))
                .build();
    }

    public static CategoryParentCategory toEntity(CategoryParentCategoryDto catPatCatDto){
        if(catPatCatDto == null){
            return null;
        }
        CategoryParentCategory catPatCat = new CategoryParentCategory();
        catPatCat.setId(catPatCatDto.getId());
        catPatCat.setParentCategory(CategoryDto.toEntity(catPatCatDto.getParentCategoryDto()));
        catPatCat.setChildCategory(CategoryDto.toEntity(catPatCatDto.getChildCategoryDto()));
        return catPatCat;
    }
}
