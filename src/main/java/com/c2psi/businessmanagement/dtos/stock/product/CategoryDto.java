package com.c2psi.businessmanagement.dtos.stock.product;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.models.Category;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
public class CategoryDto {

    Long id;
    @NotNull(message = "The category name cannot be null")
    @NotEmpty(message = "The category name cannot be empty")
    @NotBlank(message = "The category name cannot be blank")
    @Size(min = 3, max = 100, message = "The category name size must be between 3 and 100 characters")
    String catName;
    @NotEmpty(message = "The category shortname cannot be empty")
    @NotBlank(message = "The category shortname cannot be blank")
    @Size(min = 2, max = 25, message = "The category shortname size must be between 3 and 25 characters")
    String catShortname;
    @NotNull(message = "The category code cannot be null")
    @NotEmpty(message = "The category code cannot be empty")
    @NotBlank(message = "The category code cannot be blank")
    @Size(min = 2, max = 25, message = "The category code size must be between 3 and 25 characters")
    String catCode;
    String catDescription;
    /******************************
     * Relation between entities  *
     * ****************************/

    //Each category belongs to 1 pointofsale
    @NotNull(message = "The pointofsale associated to the category cannot be null")
    PointofsaleDto catPosDto;

    Long categoryParentId;

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
                .catPosDto(PointofsaleDto.fromEntity(cat.getCatPos()))
                .categoryParentId(cat.getCategoryParentId())
                .build();
    }
    public static Category toEntity(CategoryDto catDto){
        if(catDto == null){
            return null;
        }
        Category cat = new Category();
        cat.setId(catDto.getId());
        cat.setCatCode(catDto.getCatCode());
        cat.setCatName(catDto.getCatName());
        cat.setCatShortname(catDto.getCatShortname());
        cat.setCatCode(catDto.getCatCode());
        cat.setCatDescription(catDto.getCatDescription());
        cat.setCategoryParentId(catDto.getCategoryParentId());
        cat.setCatPos(PointofsaleDto.toEntity(catDto.getCatPosDto()));

        return cat;
    }
}
