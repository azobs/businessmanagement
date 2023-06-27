package com.c2psi.businessmanagement.controllers.apiImpl.stock.product;

import com.c2psi.businessmanagement.controllers.api.stock.product.ProductApi;
import com.c2psi.businessmanagement.dtos.stock.product.ProductDto;
import com.c2psi.businessmanagement.services.contracts.stock.product.ProductService;
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
public class ProductApiImpl implements ProductApi {

    private ProductService productService;

    @Autowired
    public ProductApiImpl(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public ResponseEntity saveProduct(ProductDto prodDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", prodDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        ProductDto productDtoSaved = productService.saveProduct(prodDto);
        log.info("The product has been successfully registered in the DB {}", productDtoSaved);
        //return new ResponseEntity(productDtoSaved, HttpStatus.CREATED);
        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "Product created successfully ");
        map.put("data", productDtoSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateProduct(ProductDto prodDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", prodDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        ProductDto productDtoUpdated = productService.updateProduct(prodDto);
        log.info("The product has been successfully updated in the DB {}", productDtoUpdated);
        //return new ResponseEntity(productDtoUpdated, HttpStatus.OK);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Product updated successfully ");
        map.put("data", productDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findProductById(Long prodId) {
        Map<String, Object> map = new LinkedHashMap<>();
        ProductDto productDtoFound = productService.findProductById(prodId);
        log.info("Entity product found successfully {} with the Id {}",productDtoFound, prodId);
        //return ResponseEntity.ok(productDtoFound);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Product found successfully ");
        map.put("data", productDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findProductByProductCodeInPos(String prodCode, Long posId) {
        Map<String, Object> map = new LinkedHashMap<>();
        ProductDto productDtoFound = productService.findProductByProductCodeInPos(prodCode, posId);
        log.info("Entity product found successfully {} with the Id {}",productDtoFound, prodCode);
        //return ResponseEntity.ok(productDtoFound);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Product found successfully ");
        map.put("data", productDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllProductInPos(Long posId) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<ProductDto> productDtoList = productService.findAllProductInPos(posId);
        log.info("List of product in a pointofsale found successfully");
        //return ResponseEntity.ok(productDtoList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Product list found successfully ");
        map.put("data", productDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllProductOfCategory(Long catId) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<ProductDto> productDtoList = productService.findAllProductOfCategory(catId);
        log.info("List of product in a pointofsale found successfully");
        //return ResponseEntity.ok(productDtoList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Product list found successfully ");
        map.put("data", productDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageofProductInPos(Long posId, Optional<Integer> optpagenum,
                                                 Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Map<String, Object> map = new LinkedHashMap<>();
        Page<ProductDto> productDtoPage = productService.findPageofProductInPos(posId, pagenum, pagesize);
        log.info("Page of product is successfully found");
        //return ResponseEntity.ok(productDtoPage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Product page found successfully ");
        map.put("data", productDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageOfProductOfCategory(Long catId, Optional<Integer> optpagenum,
                                                      Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Map<String, Object> map = new LinkedHashMap<>();
        Page<ProductDto> productDtoPage = productService.findPageOfProductOfCategory(catId, pagenum, pagesize);
        log.info("Page of product is successfully found");
        //return ResponseEntity.ok(productDtoPage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Product page found successfully ");
        map.put("data", productDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findProductByProdCodeInPosContaining(Long posId, String sample, Optional<Integer> optpagenum,
                                                               Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Map<String, Object> map = new LinkedHashMap<>();
        Page<ProductDto> productDtoPage = productService.findProductByProdCodeInPosContaining(posId, sample,
                pagenum, pagesize);
        log.info("Page of product is successfully found");
        //return ResponseEntity.ok(productDtoPage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Product page found successfully ");
        map.put("data", productDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteProductById(Long prodId) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean delete = productService.deleteProductById(prodId);
        log.info("Page of product is successfully found");
        //return ResponseEntity.ok(delete);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Product deleted successfully ");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }
}
