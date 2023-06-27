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

import java.util.LinkedHashMap;
import java.util.Map;

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
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", bpDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        BasePriceDto basePriceDtoSaved = basePriceService.saveBasePrice(bpDto);
        log.info("The method saveBasePrice is being executed");
        //return new ResponseEntity(basePriceDtoSaved, HttpStatus.CREATED);
        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "Base price created successfully ");
        map.put("data", basePriceDtoSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateBasePrice(BasePriceDto bpDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", bpDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        BasePriceDto basePriceDtoUpdated = basePriceService.updateBasePrice(bpDto);
        log.info("The method updateBasePrice is being executed");
        //return new ResponseEntity(basePriceDtoUpdated, HttpStatus.OK);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Base price updated successfully ");
        map.put("data", basePriceDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findBasePriceById(Long bpId) {
        Map<String, Object> map = new LinkedHashMap<>();
        BasePriceDto basePriceDtoFound = basePriceService.findBasePriceById(bpId);
        log.info("The method basePriceDtoFound is being executed");
        //return ResponseEntity.ok(basePriceDtoFound);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Base price found successfully ");
        map.put("data", basePriceDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteBasePriceById(Long bpId) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean delete = basePriceService.deleteBasePriceById(bpId);
        log.info("The method deleteBasePriceById is being executed");
        //return ResponseEntity.ok(delete);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Base price deleted successfully ");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }


}
