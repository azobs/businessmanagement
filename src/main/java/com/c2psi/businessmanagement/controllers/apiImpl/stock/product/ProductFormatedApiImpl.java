package com.c2psi.businessmanagement.controllers.apiImpl.stock.product;

import com.c2psi.businessmanagement.controllers.api.stock.product.ProductFormatedApi;
import com.c2psi.businessmanagement.dtos.stock.product.ProductFormatedDto;
import com.c2psi.businessmanagement.services.contracts.stock.product.ProductFormatedService;
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
public class ProductFormatedApiImpl implements ProductFormatedApi {
    private ProductFormatedService pfService;

    @Autowired
    public ProductFormatedApiImpl(ProductFormatedService pfService) {
        this.pfService = pfService;
    }

    @Override
    public ResponseEntity saveProductFormated(ProductFormatedDto pfDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", pfDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        ProductFormatedDto productFormatedDtoSaved = pfService.saveProductFormated(pfDto);
        log.info("The productformated is saved with success");
        //return new ResponseEntity(productFormatedDtoSaved, HttpStatus.CREATED);
        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "Product formated created successfully ");
        map.put("data", productFormatedDtoSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateProductFormated(ProductFormatedDto pfDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", pfDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        ProductFormatedDto productFormatedDtoUpdated = pfService.updateProductFormated(pfDto);
        log.info("The productformated is updated with success");
        //return new ResponseEntity(productFormatedDtoUpdated, HttpStatus.OK);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Product formated updated successfully ");
        map.put("data", productFormatedDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findProductFormatedById(Long pfId) {
        Map<String, Object> map = new LinkedHashMap<>();
        ProductFormatedDto productFormatedDtoFound = pfService.findProductFormatedById(pfId);
        log.info("Productformated found by Id");
        //return ResponseEntity.ok(productFormatedDtoFound);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Product formated found successfully ");
        map.put("data", productFormatedDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findProductFormatedByProductIdAndFormatId(Long prodId, Long formatId) {
        Map<String, Object> map = new LinkedHashMap<>();
        ProductFormatedDto productFormatedDtoFound = pfService.findProductFormatedByProductIdAndFormatId(prodId,
                formatId);
        log.info("Productformated found by Id");
        //return ResponseEntity.ok(productFormatedDtoFound);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Product formated found successfully ");
        map.put("data", productFormatedDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllProductFormatedInPos(Long posId) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<ProductFormatedDto> productFormatedDtoList = pfService.findAllProductFormatedInPos(posId);
        log.info("Productformated list found in the DB");
        //return ResponseEntity.ok(productFormatedDtoList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Product formated list found successfully ");
        map.put("data", productFormatedDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageProductFormatedInPos(Long posId, Optional<Integer> optpagenum,
                                                       Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Map<String, Object> map = new LinkedHashMap<>();
        Page<ProductFormatedDto> productformatedDtoPage = pfService.findPageProductFormatedInPos(posId, pagenum,
                pagesize);
        log.info("Page of productformated is successfully found");
        //return ResponseEntity.ok(productformatedDtoPage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Product formated page found successfully ");
        map.put("data", productformatedDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteProductFormatedById(Long pfId) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean delete = pfService.deleteProductFormatedById(pfId);
        log.info("Productformated with id {} deleted", pfId);
        //return ResponseEntity.ok(delete);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Product formated deleted successfully ");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }
}
