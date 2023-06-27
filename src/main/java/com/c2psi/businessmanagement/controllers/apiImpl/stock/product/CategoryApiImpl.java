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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
        Map<String, Object> map = new LinkedHashMap<>();

        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", catDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        CategoryDto categoryDtoSaved = categoryService.saveCategory(catDto);
        log.info("The Category has been successfully registered in the DB {}", categoryDtoSaved);

        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "Category created successfully ");
        map.put("data", categoryDtoSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateCategory(CategoryDto catDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();

        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", catDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        CategoryDto categoryDtoUpdated = categoryService.updateCategory(catDto);
        log.info("The Category has been successfully updated in the DB {}", categoryDtoUpdated);

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Category updated successfully ");
        map.put("data", categoryDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findCategoryById(Long catId) {
        Map<String, Object> map = new LinkedHashMap<>();

        CategoryDto categoryDtoFound = categoryService.findCategoryById(catId);
        log.info("Entity Category found successfully {} with the ID {}", categoryDtoFound, catId);
        //return ResponseEntity.ok(categoryDtoFound);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Category found successfully ");
        map.put("data", categoryDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAscandantCategoryof(Long catId) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CategoryDto> categoryDtoList = categoryService.findAscandantCategoryof(catId);
        log.info("List of category parent of category with id {} successfully found ", catId);
        //return ResponseEntity.ok(categoryDtoList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Category list found successfully ");
        map.put("data", categoryDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findCategoryParentof(Long catId) {
        Map<String, Object> map = new LinkedHashMap<>();
        CategoryDto categoryDtoParent = categoryService.findCategoryParentof(catId);
        log.info("Category parent of category with id {} successfully found ", catId);
        //return ResponseEntity.ok(categoryDtoParent);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Category parent found successfully ");
        map.put("data", categoryDtoParent);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findChildCategoryOf(Long catId) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CategoryDto> categoryDtoList = categoryService.findChildCategoryOf(catId);
        log.info("List of category child of category with id {} successfully found ", catId);
        //return ResponseEntity.ok(categoryDtoList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Category child list found successfully ");
        map.put("data", categoryDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCategoryInPointofsale(Long posId) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CategoryDto> categoryDtoList = categoryService.findAllCategoryInPointofsale(posId);
        log.info("List of category in the pointofsale with id {} successfully found ", posId);
        //return ResponseEntity.ok(categoryDtoList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Category list of pointofsale found successfully ");
        map.put("data", categoryDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCategoryInPointofsale(Long posId, Optional<Integer> optpagenum,
                                                        Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Map<String, Object> map = new LinkedHashMap<>();
        Page<CategoryDto> categoryDtoPage = categoryService.findPageCategoryInPointofsale(posId, pagenum, pagesize);
        log.info("The Page of all the category in the system has been successfully found");
        //return ResponseEntity.ok(categoryDtoPage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Category page of pointofsale found successfully ");
        map.put("data", categoryDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageByCatNameInPosContaining(Long posId, String sample, Optional<Integer> optpagenum,
                                                          Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Map<String, Object> map = new LinkedHashMap<>();
        Page<CategoryDto> categoryDtoPage = categoryService.findPageByCatNameInPosContaining(posId, sample, pagenum,
                pagesize);
        log.info("The Page of all the category in the system has been successfully found");
        //return ResponseEntity.ok(categoryDtoPage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Category page that name contain key characters found successfully ");
        map.put("data", categoryDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteCategoryById(Long catId) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean delete = categoryService.deleteCategoryById(catId);
        log.info("Category deleted successfully with the Id {}", catId);
        //return ResponseEntity.ok(delete);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Category deleted successfully ");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteCategoryByCatCode(String catCode, Long posId) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean delete = categoryService.deleteCategoryByCatCode(catCode, posId);
        log.info("Category deleted successfully with the code {}", catCode);
        //return ResponseEntity.ok(delete);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Category deleted successfully ");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }
}
