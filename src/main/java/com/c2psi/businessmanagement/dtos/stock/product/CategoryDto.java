package com.c2psi.businessmanagement.dtos.stock.product;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.models.Category;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@ApiModel
public class CategoryDto {
    @ApiModelProperty(value = "The id of the Category", name = "id", dataType = "Long")
    Long id;
    @NotNull(message = "The category name cannot be null")
    @NotEmpty(message = "The category name cannot be empty")
    @NotBlank(message = "The category name cannot be blank")
    @Size(min = 3, max = 75, message = "The category name size must be between 3 and 100 characters")
    @ApiModelProperty(value = "The name of the category", name = "catName", dataType = "String", example = "Bierre")
    String catName;
    @NotEmpty(message = "The category shortname cannot be empty")
    @NotBlank(message = "The category shortname cannot be blank")
    @Size(min = 2, max = 30, message = "The category shortname size must be between 3 and 25 characters")
    @ApiModelProperty(value = "The short name of the category", name = "catShortname", dataType = "String", example = "Bierre")
    String catShortname;
    @NotNull(message = "The category code cannot be null")
    @NotEmpty(message = "The category code cannot be empty")
    @NotBlank(message = "The category code cannot be blank")
    @Size(min = 2, max = 25, message = "The category code size must be between 3 and 25 characters")
    @ApiModelProperty(value = "The code of the category", name = "catCode", dataType = "String", example = "Bierre")
    String catCode;
    @ApiModelProperty(value = "The description of the category", name = "catDescription", dataType = "String", example = "Produit a base de malt et mais fermente")
    String catDescription;
    /******************************
     * Relation between entities  *
     * ****************************/

    //Each category belongs to 1 pointofsale
    @NotNull(message = "The pointofsale associated to the category cannot be null")
    @ApiModelProperty(value = "The id of the pointofsale owner of the category", name = "catPosId", dataType = "Long")
    Long catPosId;
    //PointofsaleDto catPosDto;

    @ApiModelProperty(value = "The id of the parent category owner of the category", name = "categoryParentId",
            dataType = "Long")
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
                //.catPosDto(PointofsaleDto.fromEntity(cat.getCatPos()))
                .catPosId(cat.getCatPosId())
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
        //cat.setCatPos(PointofsaleDto.toEntity(catDto.getCatPosDto()));
        cat.setCatPosId(catDto.getCatPosId());

        return cat;
    }
}
