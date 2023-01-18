package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.CategoryDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryValidator {
    /************************************************************************************
     * *le parametre a valider ne peut etre null
     * *Le nom de la categorie (catName) ne peut etre ni vide ni null
     * *Le code de la categorie (catCode) ne peut etre ni null ni vide
     * *le shortname de la categorie (catShortname) ne peut etre ni null ni vide et doit etre plus court
     * que le nom de la categorie
     * *Une categorie ne peut etre sa propre categorie parent
     * *le point de vente de la categorie ne peut etre null
     * @param catDto
     * @return
     */
    public static List<String> validate(CategoryDto catDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(catDto).isPresent()){
            errors.add("--Le parametre CategoryDto a valider ne peut etre null: "+errors);
        }
        else{
            if(!Optional.ofNullable(catDto.getCatPosDto()).isPresent()){
                errors.add("--Le point de vente de la categorie ne peut etre null: "+errors);
            }
            if(Optional.ofNullable(catDto.getId()).isPresent()){
                if(catDto.getId().equals(catDto.getCatParentDto().getId())){
                    errors.add("--Une categorie ne peut etre son propre parent: "+errors);
                }
            }
            if(!StringUtils.hasLength(catDto.getCatCode())){
                errors.add("--Le code de la categorie ne peut etre vide: "+errors);
            }
            if(!StringUtils.hasLength(catDto.getCatName())){
                errors.add("--Le nom de la categorie ne peut etre vide: "+errors);
            }
            if(!StringUtils.hasLength(catDto.getCatShortname())){
                errors.add("--Le shortname de la categorie ne peut etre vide: "+errors);
            }
            if(catDto.getCatName().length() < catDto.getCatShortname().length()){
                errors.add("--Le shortname de la categorie ne peut etre plus long que son nom: "+errors);
            }
        }
        return errors;
    }
}
