package com.c2psi.businessmanagement.controllers.apiImpl.stock.product;

import com.c2psi.businessmanagement.controllers.apiImpl.pos.pos.PosDtoConstructor;
import com.c2psi.businessmanagement.dtos.pos.pos.EnterpriseDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyDto;
import com.c2psi.businessmanagement.dtos.stock.product.CategoryDto;
import com.c2psi.businessmanagement.services.contracts.pos.pos.PointofsaleService;
import com.c2psi.businessmanagement.services.contracts.stock.product.CategoryService;

public class ProductDtoConstructor {
    public static CategoryDto getCategoryDtoToSave(int num, CategoryDto catParentDto, CurrencyDto currencyDtoSaved,
                                                   EnterpriseDto enterpriseDtoSaved, PointofsaleDto posDtoSaved){

        CategoryDto categoryDto = CategoryDto.builder()
                .catName("CatName")
                .catShortname("Short")
                .catCode("Code_"+num)
                .catDescription("Description of category")
                .catPosId(posDtoSaved.getId())
                .categoryParentId(catParentDto == null? null:catParentDto.getId())
                .build();
        return categoryDto;
    }

    public static CategoryDto getCategoryDtoSaved(int num, CategoryDto catParentDto, CurrencyDto currencyDtoSaved,
                                                  EnterpriseDto enterpriseDtoSaved, PointofsaleService posService,
                                                  CategoryService categoryService){
        PointofsaleDto posOwner = PosDtoConstructor.getPointofsaleDtoSaved(num+2, currencyDtoSaved,
                enterpriseDtoSaved, posService);

        CategoryDto categoryDto = CategoryDto.builder()
                .catName("CatName")
                .catShortname("Short")
                .catCode("Code_"+num)
                .catDescription("Description of category")
                .catPosId(posOwner.getId())
                .categoryParentId(catParentDto == null? null:catParentDto.getId())
                .build();
        return categoryService.saveCategory(categoryDto);
    }
}
