package com.c2psi.businessmanagement.controllers.apiImpl.stock.product;

import com.c2psi.businessmanagement.Enumerations.CashArrivalType;
import com.c2psi.businessmanagement.controllers.api.stock.product.CashArrivalApi;
import com.c2psi.businessmanagement.dtos.stock.product.CashArrivalDto;
import com.c2psi.businessmanagement.services.contracts.stock.product.CashArrivalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
public class CashArrivalApiImpl implements CashArrivalApi {
    private CashArrivalService cashArrivalService;

    @Autowired
    public CashArrivalApiImpl(CashArrivalService cashArrivalService) {
        this.cashArrivalService = cashArrivalService;
    }

    @Override
    public ResponseEntity saveCashArrival(CashArrivalDto cashaDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", cashaDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        CashArrivalDto cashaDtoSaved = cashArrivalService.saveCashArrival(cashaDto);
        log.info("The method saveCashArrival is being executed");

        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "Cash Arrival created successfully ");
        map.put("data", cashaDtoSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateCashArrival(CashArrivalDto cashaDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", cashaDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        CashArrivalDto cashaDtoUpdated = cashArrivalService.updateCashArrival(cashaDto);
        log.info("The method saveCashArrival is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Cash Arrival updated successfully ");
        map.put("data", cashaDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteCashArrivalById(Long cashaId) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean delete = cashArrivalService.deleteCashArrivalById(cashaId);
        log.info("The method deleteCashArrivalById is being executed");
        //return null;
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Cash Arrival deleted successfully ");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findCashArrivalById(Long cashaId) {
        Map<String, Object> map = new LinkedHashMap<>();
        CashArrivalDto cashaDtoFound = cashArrivalService.findCashArrivalById(cashaId);
        log.info("The method findCashArrivalById is being executed");
        //return null;
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Cash Arrival found successfully ");
        map.put("data", cashaDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findCashArrivalofArticleinSicash(Long artId, Long sicashId) {
        Map<String, Object> map = new LinkedHashMap<>();
        CashArrivalDto cashaDtoFound = cashArrivalService.findCashArrivalofArticleinSicash(artId, sicashId);
        log.info("The method findCashArrivalofArticleinSicash is being executed");
        //return null;
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Cash Arrival found successfully ");
        map.put("data", cashaDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCashArrivalinSicash(Long sicashId) {
        Map<String, Object> map = new LinkedHashMap<>();
        //return null;
        List<CashArrivalDto> cashArrivalDtoList = cashArrivalService.findAllCashArrivalinSicash(sicashId);
        log.info("The method findAllCashArrivalinSicash is being executed");
        //return null;
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Cash Arrival list found successfully ");
        map.put("data", cashArrivalDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCashArrivalinSicash(Long sicashId, Optional<Integer> optpagenum,
                                                      Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        //return null;
        Page<CashArrivalDto> cashArrivalDtoPage = cashArrivalService.findPageCashArrivalinSicash(sicashId,
                pagenum, pagesize);
        log.info("The method findPageCashArrivalinSicash is being executed");
        //return null;
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Cash Arrival page found successfully ");
        map.put("data", cashArrivalDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCashArrivalofCashArrivalTypeinSicash(CashArrivalType arrivalType, Long sicashId) {
        Map<String, Object> map = new LinkedHashMap<>();
        //return null;
        List<CashArrivalDto> cashArrivalDtoList = cashArrivalService.findAllCashArrivalofCashArrivalTypeinSicash(
                arrivalType, sicashId);
        log.info("The method findAllCashArrivalofCashArrivalTypeinSicash is being executed");
        //return null;
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Cash Arrival list found successfully ");
        map.put("data", cashArrivalDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCashArrivalofCashArrivalTypeinSicash(CashArrivalType arrivalType, Long sicashId,
                                                                       Optional<Integer> optpagenum,
                                                                       Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        //return null;
        Page<CashArrivalDto> cashArrivalDtoPage = cashArrivalService.findPageCashArrivalofCashArrivalTypeinSicash(
                arrivalType, sicashId, pagenum, pagesize);
        log.info("The method findPageCashArrivalofCashArrivalTypeinSicash is being executed");
        //return null;
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Cash Arrival page found successfully ");
        map.put("data", cashArrivalDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCashArrivalinSicashBetween(Long sicashId, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        //return null;
        List<CashArrivalDto> cashArrivalDtoList = cashArrivalService.findAllCashArrivalinSicashBetween(
                sicashId, startDate, endDate);
        log.info("The method findAllCashArrivalinSicashBetween is being executed");
        //return null;
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Cash Arrival list found successfully ");
        map.put("data", cashArrivalDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCashArrivalinSicashBetween(Long sicashId, Instant startDate, Instant endDate,
                                                             Optional<Integer> optpagenum,
                                                             Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        //return null;
        Page<CashArrivalDto> cashArrivalDtoPage = cashArrivalService.findPageCashArrivalinSicashBetween(sicashId,
                startDate, endDate, pagenum, pagesize);
        log.info("The method findPageCashArrivalinSicashBetween is being executed");
        //return null;
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Cash Arrival page found successfully ");
        map.put("data", cashArrivalDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCashArrivalofCashArrivalTypeinSicashBetween(CashArrivalType arrivalType, Long sicashId,
                                                                             Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        //return null;
        List<CashArrivalDto> cashArrivalDtoList = cashArrivalService.findAllCashArrivalofCashArrivalTypeinSicashBetween(
                arrivalType, sicashId, startDate, endDate);
        log.info("The method findAllCashArrivalofCashArrivalTypeinSicashBetween is being executed");
        //return null;
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Cash Arrival list found successfully ");
        map.put("data", cashArrivalDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCashArrivalofCashArrivalTypeinSicashBetween(CashArrivalType arrivalType, Long sicashId,
                                                                              Instant startDate, Instant endDate,
                                                                              Optional<Integer> optpagenum,
                                                                              Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        //return null;
        Page<CashArrivalDto> cashArrivalDtoPage = cashArrivalService.findPageCashArrivalofCashArrivalTypeinSicashBetween(
                arrivalType, sicashId, startDate, endDate, pagenum, pagesize);
        log.info("The method findPageCashArrivalofCashArrivalTypeinSicashBetween is being executed");
        //return null;
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Cash Arrival page found successfully ");
        map.put("data", cashArrivalDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCashArrivalinPosBetween(Long posId, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        //return null;
        List<CashArrivalDto> cashArrivalDtoList = cashArrivalService.findAllCashArrivalinPosBetween(
                posId, startDate, endDate);
        log.info("The method findAllCashArrivalinPosBetween is being executed");
        //return null;
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Cash Arrival list found successfully ");
        map.put("data", cashArrivalDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCashArrivalinPosBetween(Long posId, Instant startDate, Instant endDate,
                                                          Optional<Integer> optpagenum,
                                                          Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        //return null;
        Page<CashArrivalDto> cashArrivalDtoPage = cashArrivalService.findPageCashArrivalinPosBetween(posId,
                startDate, endDate, pagenum, pagesize);
        log.info("The method findPageCashArrivalinPosBetween is being executed");
        //return null;
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Cash Arrival page found successfully ");
        map.put("data", cashArrivalDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }
}
