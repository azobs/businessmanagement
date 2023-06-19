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

import java.util.List;
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
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", prodDto, bindingResult);
            return ResponseEntity.badRequest().body(bindingResult.toString());
        }
        ProductDto productDtoSaved = productService.saveProduct(prodDto);
        log.info("The product has been successfully registered in the DB {}", productDtoSaved);
        return new ResponseEntity(productDtoSaved, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateProduct(ProductDto prodDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", prodDto, bindingResult);
            return ResponseEntity.badRequest().body(bindingResult.toString());
        }
        ProductDto productDtoUpdated = productService.updateProduct(prodDto);
        log.info("The product has been successfully updated in the DB {}", productDtoUpdated);
        return new ResponseEntity(productDtoUpdated, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findProductById(Long prodId) {
        ProductDto productDtoFound = productService.findProductById(prodId);
        log.info("Entity product found successfully {} with the Id {}",productDtoFound, prodId);
        return ResponseEntity.ok(productDtoFound);
    }

    @Override
    public ResponseEntity findProductByProductCodeInPos(String prodCode, Long posId) {
        ProductDto productDtoFound = productService.findProductByProductCodeInPos(prodCode, posId);
        log.info("Entity product found successfully {} with the Id {}",productDtoFound, prodCode);
        return ResponseEntity.ok(productDtoFound);
    }

    @Override
    public ResponseEntity findAllProductInPos(Long posId) {
        List<ProductDto> productDtoList = productService.findAllProductInPos(posId);
        log.info("List of product in a pointofsale found successfully");
        return ResponseEntity.ok(productDtoList);
    }

    @Override
    public ResponseEntity findAllProductOfCategory(Long catId) {
        List<ProductDto> productDtoList = productService.findAllProductOfCategory(catId);
        log.info("List of product in a pointofsale found successfully");
        return ResponseEntity.ok(productDtoList);
    }

    @Override
    public ResponseEntity findPageofProductInPos(Long posId, Optional<Integer> optpagenum,
                                                 Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<ProductDto> productDtoPage = productService.findPageofProductInPos(posId, pagenum, pagesize);
        log.info("Page of product is successfully found");
        return ResponseEntity.ok(productDtoPage);
    }

    @Override
    public ResponseEntity findPageOfProductOfCategory(Long catId, Optional<Integer> optpagenum,
                                                      Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<ProductDto> productDtoPage = productService.findPageOfProductOfCategory(catId, pagenum, pagesize);
        log.info("Page of product is successfully found");
        return ResponseEntity.ok(productDtoPage);
    }

    @Override
    public ResponseEntity findProductByProdCodeInPosContaining(Long posId, String sample, Optional<Integer> optpagenum,
                                                               Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<ProductDto> productDtoPage = productService.findProductByProdCodeInPosContaining(posId, sample,
                pagenum, pagesize);
        log.info("Page of product is successfully found");
        return ResponseEntity.ok(productDtoPage);
    }

    @Override
    public ResponseEntity deleteProductById(Long prodId) {
        Boolean delete = productService.deleteProductById(prodId);
        log.info("Page of product is successfully found");
        return ResponseEntity.ok(delete);
    }
}
