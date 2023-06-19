package com.c2psi.businessmanagement.controllers.apiImpl.stock.price;

import com.c2psi.businessmanagement.controllers.api.stock.price.SpecialPriceApi;
import com.c2psi.businessmanagement.dtos.stock.price.SpecialPriceDto;
import com.c2psi.businessmanagement.services.contracts.stock.price.SpecialPriceService;
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
public class SpecialPriceApiImpl implements SpecialPriceApi {
    private SpecialPriceService specialPriceService;

    @Autowired
    public SpecialPriceApiImpl(SpecialPriceService specialPriceService) {
        this.specialPriceService = specialPriceService;
    }

    @Override
    public ResponseEntity saveSpecialPrice(SpecialPriceDto spDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", spDto, bindingResult);
            return ResponseEntity.badRequest().body(bindingResult.toString());
        }
        SpecialPriceDto specialPriceDtoSaved = specialPriceService.saveSpecialPrice(spDto);
        log.info("The methode saveSpecialPrice is being executed");
        return new ResponseEntity(specialPriceDtoSaved, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateSpecialPrice(SpecialPriceDto spDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", spDto, bindingResult);
            return ResponseEntity.badRequest().body(bindingResult.toString());
        }
        SpecialPriceDto specialPriceDtoUpdated = specialPriceService.updateSpecialPrice(spDto);
        log.info("The methode updateSpecialPrice is being executed");
        return new ResponseEntity(specialPriceDtoUpdated, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findSpecialPriceById(Long spId) {
        SpecialPriceDto specialPriceDtoFound = specialPriceService.findSpecialPriceById(spId);
        log.info("The method findSpecialPriceById is being executed");
        return ResponseEntity.ok(specialPriceDtoFound);
    }

    @Override
    public ResponseEntity findAllSpecialpriceofBaseprice(Long bpId) {
        List<SpecialPriceDto> specialPriceDtoList = specialPriceService.findAllSpecialpriceofBaseprice(bpId);
        log.info("The method findAllSpecialpriceofBaseprice is being executed");
        return ResponseEntity.ok(specialPriceDtoList);
    }

    @Override
    public ResponseEntity findPageSpecialpriceofBaseprice(Long bpId, Optional<Integer> optpagenum,
                                                          Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Page<SpecialPriceDto> specialPriceDtoPage = specialPriceService.findPageSpecialpriceofBaseprice(bpId,
                pagenum, pagesize);
        log.info("The method findPageSpecialpriceofBaseprice is being executed");
        return ResponseEntity.ok(specialPriceDtoPage);
    }

    @Override
    public ResponseEntity findAllSpecialpriceofArticle(Long artId) {
        List<SpecialPriceDto> specialPriceDtoList = specialPriceService.findAllSpecialpriceofArticle(artId);
        log.info("The method findAllSpecialpriceofArticle is being executed");
        return ResponseEntity.ok(specialPriceDtoList);
    }

    @Override
    public ResponseEntity findPageSpecialpriceofArticle(Long artId, Optional<Integer> optpagenum,
                                                        Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Page<SpecialPriceDto> specialPriceDtoPage = specialPriceService.findPageSpecialpriceofArticle(artId,
                pagenum, pagesize);
        log.info("The method findPageSpecialpriceofArticle is being executed");
        return ResponseEntity.ok(specialPriceDtoPage);
    }

    @Override
    public ResponseEntity deleteSpecialPriceById(Long spId) {
        Boolean delete = specialPriceService.deleteSpecialPriceById(spId);
        log.info("The method deleteSpecialPriceById is being executed");
        return ResponseEntity.ok(delete);
    }
}
