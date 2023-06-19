package com.c2psi.businessmanagement.controllers.apiImpl.stock.price;

import com.c2psi.businessmanagement.controllers.api.stock.price.BasePriceApi;
import com.c2psi.businessmanagement.dtos.stock.price.BasePriceDto;
import com.c2psi.businessmanagement.services.contracts.stock.price.BasePriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class BasePriceApiImpl implements BasePriceApi {
    private BasePriceService basePriceService;

    @Autowired
    public BasePriceApiImpl(BasePriceService basePriceService) {
        this.basePriceService = basePriceService;
    }

    @Override
    public ResponseEntity saveBasePrice(BasePriceDto bpDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", bpDto, bindingResult);
            return ResponseEntity.badRequest().body(bindingResult.toString());
        }
        BasePriceDto basePriceDtoSaved = basePriceService.saveBasePrice(bpDto);
        log.info("The method saveBasePrice is being executed");
        return new ResponseEntity(basePriceDtoSaved, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateBasePrice(BasePriceDto bpDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", bpDto, bindingResult);
            return ResponseEntity.badRequest().body(bindingResult.toString());
        }
        BasePriceDto basePriceDtoUpdated = basePriceService.updateBasePrice(bpDto);
        log.info("The method updateBasePrice is being executed");
        return new ResponseEntity(basePriceDtoUpdated, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findBasePriceById(Long bpId) {
        BasePriceDto basePriceDtoFound = basePriceService.findBasePriceById(bpId);
        log.info("The method basePriceDtoFound is being executed");
        return ResponseEntity.ok(basePriceDtoFound);
    }

    @Override
    public ResponseEntity deleteBasePriceById(Long bpId) {
        Boolean delete = basePriceService.deleteBasePriceById(bpId);
        log.info("The method deleteBasePriceById is being executed");
        return ResponseEntity.ok(delete);
    }


}
