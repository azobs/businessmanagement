package com.c2psi.businessmanagement.controllers.apiImpl.stock.product;

import com.c2psi.businessmanagement.controllers.api.stock.product.PackagingApi;
import com.c2psi.businessmanagement.dtos.stock.product.PackagingDto;
import com.c2psi.businessmanagement.services.contracts.stock.product.PackagingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
public class PackagingApiImpl implements PackagingApi {
    private PackagingService packagingService;

    @Autowired
    public PackagingApiImpl(PackagingService packagingService) {
        this.packagingService = packagingService;
    }

    @Override
    public ResponseEntity savePackaging(PackagingDto packDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", packDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        PackagingDto packagingDtoSaved = packagingService.savePackaging(packDto);
        log.info("The method savePackaging is being executed");
        //return new ResponseEntity(packagingDtoSaved, HttpStatus.CREATED);
        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "Packaging created successfully ");
        map.put("data", packagingDtoSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updatePackaging(PackagingDto packDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", packDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        PackagingDto packagingDtoUpdated = packagingService.updatePackaging(packDto);
        log.info("The method updatePackaging is being executed");
        //return new ResponseEntity(packagingDtoUpdated, HttpStatus.OK);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Packaging updated successfully ");
        map.put("data", packagingDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPackagingById(Long packagingId) {
        Map<String, Object> map = new LinkedHashMap<>();
        PackagingDto packagingDtoFound = packagingService.findPackagingById(packagingId);
        log.info("The method findPackagingById is being executed");
        //return ResponseEntity.ok(packagingDtoFound);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Packaging found successfully ");
        map.put("data", packagingDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPackagingByAttributes(String packLabel, String packFirstColor,
                                                    Long providerId, Long posId) {
        Map<String, Object> map = new LinkedHashMap<>();
        PackagingDto packagingDtoFound = packagingService.findPackagingByAttributes(packLabel, packFirstColor,
                providerId, posId);
        log.info("The method findPackagingByAttributes is being executed");
        //return ResponseEntity.ok(packagingDtoFound);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Packaging found successfully ");
        map.put("data", packagingDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllPackagingofPos(Long posId) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<PackagingDto> packagingDtoListFound = packagingService.findAllPackagingofPos(posId);
        log.info("The method findAllPackagingofPos is being executed");
        //return ResponseEntity.ok(packagingDtoListFound);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Packaging list in pointofsale found successfully ");
        map.put("data", packagingDtoListFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPagePackagingofPos(Long posId, Optional<Integer> optpagenum, Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();

        Page<PackagingDto> packagingDtoPageFound = packagingService.findPagePackagingofPos(posId, pagenum, pagesize);
        log.info("The method findPagePackagingofPos is being executed");
        //return ResponseEntity.ok(packagingDtoPageFound);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Packaging page in pointofsale found successfully ");
        map.put("data", packagingDtoPageFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllPackagingofProviderinPos(Long providerId, Long posId) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<PackagingDto> packagingDtoList = packagingService.findAllPackagingofProviderinPos(providerId, posId);
        log.info("The method findAllPackagingofProviderinPos is being executed");
        //return ResponseEntity.ok(packagingDtoList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Packaging list of provider found successfully ");
        map.put("data", packagingDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPagePackagingofProviderinPos(Long providerId, Long posId, Optional<Integer> optpagenum,
                                                           Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();

        Page<PackagingDto> packagingDtoPage = packagingService.findPagePackagingofProviderinPos(providerId, posId,
                pagenum, pagesize);
        log.info("The method findPagePackagingofProviderinPos is being executed");
        //return ResponseEntity.ok(packagingDtoPage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Packaging page of provider found successfully ");
        map.put("data", packagingDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity convertCashToPackaging(Long packagingId, BigDecimal amountToConvert) {
        Map<String, Object> map = new LinkedHashMap<>();
        BigDecimal numberofPackaging = packagingService.convertCashToPackaging(packagingId, amountToConvert);
        log.info("The method convertCashToPackaging is being executed");
        //return ResponseEntity.ok(numberofPackaging);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Conversion of cash to packaging done successfully ");
        map.put("data", numberofPackaging);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity convertPackagingToCash(Long packagingId, BigDecimal numberToConvert) {
        Map<String, Object> map = new LinkedHashMap<>();
        BigDecimal amount = packagingService.convertPackagingToCash(packagingId, numberToConvert);
        log.info("The method convertPackagingToCash is being executed");
        //return ResponseEntity.ok(amount);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Conversion of packaging to cash done successfully ");
        map.put("data", amount);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deletePackagingById(Long packagingId) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean delete = packagingService.deletePackagingById(packagingId);
        log.info("The method deleteFormatById is being executed");
        //return ResponseEntity.ok(delete);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Packaging deleted successfully ");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }
}
