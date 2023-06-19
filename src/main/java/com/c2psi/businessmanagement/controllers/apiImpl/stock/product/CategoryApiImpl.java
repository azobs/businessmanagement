package com.c2psi.businessmanagement.controllers.apiImpl.stock.product;

import com.c2psi.businessmanagement.controllers.api.stock.product.CategoryApi;
import com.c2psi.businessmanagement.dtos.stock.product.CategoryDto;
import com.c2psi.businessmanagement.services.contracts.stock.product.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class CategoryApiImpl implements CategoryApi {
    private CategoryService categoryService;

    @Autowired
    public CategoryApiImpl(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public ResponseEntity saveCategory(CategoryDto catDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", catDto, bindingResult);
            return ResponseEntity.badRequest().body(bindingResult.toString());
        }
        CategoryDto categoryDtoSaved = categoryService.saveCategory(catDto);
        log.info("The Category has been successfully registered in the DB {}", categoryDtoSaved);

        return new ResponseEntity(categoryDtoSaved, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateCategory(CategoryDto catDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", catDto, bindingResult);
            return ResponseEntity.badRequest().body(bindingResult.toString());
        }
        CategoryDto categoryDtoUpdated = categoryService.updateCategory(catDto);
        log.info("The Category has been successfully updated in the DB {}", categoryDtoUpdated);

        return new ResponseEntity(categoryDtoUpdated, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findCategoryById(Long catId) {
        CategoryDto categoryDtoFound = categoryService.findCategoryById(catId);
        log.info("Entity Category found successfully {} with the ID {}", categoryDtoFound, catId);
        return ResponseEntity.ok(categoryDtoFound);
    }

    @Override
    public ResponseEntity findAscandantCategoryof(Long catId) {
        List<CategoryDto> categoryDtoList = categoryService.findAscandantCategoryof(catId);
        log.info("List of category parent of category with id {} successfully found ", catId);
        return ResponseEntity.ok(categoryDtoList);
    }

    @Override
    public ResponseEntity findCategoryParentof(Long catId) {
        CategoryDto categoryDtoParent = categoryService.findCategoryParentof(catId);
        log.info("Category parent of category with id {} successfully found ", catId);
        return ResponseEntity.ok(categoryDtoParent);
    }

    @Override
    public ResponseEntity findChildCategoryOf(Long catId) {
        List<CategoryDto> categoryDtoList = categoryService.findChildCategoryOf(catId);
        log.info("List of category child of category with id {} successfully found ", catId);
        return ResponseEntity.ok(categoryDtoList);
    }

    @Override
    public ResponseEntity findAllCategoryInPointofsale(Long posId) {
        List<CategoryDto> categoryDtoList = categoryService.findAllCategoryInPointofsale(posId);
        log.info("List of category in the pointofsale with id {} successfully found ", posId);
        return ResponseEntity.ok(categoryDtoList);
    }

    @Override
    public ResponseEntity findPageCategoryInPointofsale(Long posId, Optional<Integer> optpagenum,
                                                        Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CategoryDto> categoryDtoPage = categoryService.findPageCategoryInPointofsale(posId, pagenum, pagesize);
        log.info("The Page of all the category in the system has been successfully found");
        return ResponseEntity.ok(categoryDtoPage);
    }

    @Override
    public ResponseEntity findPageByCatNameInPosContaining(Long posId, String sample, Optional<Integer> optpagenum,
                                                          Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CategoryDto> categoryDtoPage = categoryService.findPageByCatNameInPosContaining(posId, sample, pagenum,
                pagesize);
        log.info("The Page of all the category in the system has been successfully found");
        return ResponseEntity.ok(categoryDtoPage);
    }

    @Override
    public ResponseEntity deleteCategoryById(Long catId) {
        Boolean delete = categoryService.deleteCategoryById(catId);
        log.info("Category deleted successfully with the Id {}", catId);
        return ResponseEntity.ok(delete);
    }

    @Override
    public ResponseEntity deleteCategoryByCatCode(String catCode, Long posId) {
        Boolean delete = categoryService.deleteCategoryByCatCode(catCode, posId);
        log.info("Category deleted successfully with the code {}", catCode);
        return ResponseEntity.ok(delete);
    }
}
