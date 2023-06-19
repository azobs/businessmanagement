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

import java.util.List;
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
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", pfDto, bindingResult);
            return ResponseEntity.badRequest().body(bindingResult.toString());
        }
        ProductFormatedDto productFormatedDtoSaved = pfService.saveProductFormated(pfDto);
        log.info("The productformated is saved with success");
        return new ResponseEntity(productFormatedDtoSaved, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateProductFormated(ProductFormatedDto pfDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", pfDto, bindingResult);
            return ResponseEntity.badRequest().body(bindingResult.toString());
        }
        ProductFormatedDto productFormatedDtoUpdated = pfService.updateProductFormated(pfDto);
        log.info("The productformated is updated with success");
        return new ResponseEntity(productFormatedDtoUpdated, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findProductFormatedById(Long pfId) {
        ProductFormatedDto productFormatedDtoFound = pfService.findProductFormatedById(pfId);
        log.info("Productformated found by Id");
        return ResponseEntity.ok(productFormatedDtoFound);
    }

    @Override
    public ResponseEntity findProductFormatedByProductIdAndFormatId(Long prodId, Long formatId) {
        ProductFormatedDto productFormatedDtoFound = pfService.findProductFormatedByProductIdAndFormatId(prodId,
                formatId);
        log.info("Productformated found by Id");
        return ResponseEntity.ok(productFormatedDtoFound);
    }

    @Override
    public ResponseEntity findAllProductFormatedInPos(Long posId) {
        List<ProductFormatedDto> productFormatedDtoList = pfService.findAllProductFormatedInPos(posId);
        log.info("Productformated list found in the DB");
        return ResponseEntity.ok(productFormatedDtoList);
    }

    @Override
    public ResponseEntity findPageProductFormatedInPos(Long posId, Optional<Integer> optpagenum,
                                                       Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<ProductFormatedDto> productformatedDtoPage = pfService.findPageProductFormatedInPos(posId, pagenum,
                pagesize);
        log.info("Page of productformated is successfully found");
        return ResponseEntity.ok(productformatedDtoPage);
    }

    @Override
    public ResponseEntity deleteProductFormatedById(Long pfId) {
        Boolean delete = pfService.deleteProductFormatedById(pfId);
        log.info("Productformated with id {} deleted", pfId);
        return ResponseEntity.ok(delete);
    }
}
