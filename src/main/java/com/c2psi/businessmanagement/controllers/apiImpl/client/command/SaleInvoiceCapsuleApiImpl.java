package com.c2psi.businessmanagement.controllers.apiImpl.client.command;

import com.c2psi.businessmanagement.controllers.api.client.command.SaleInvoiceCapsuleApi;
import com.c2psi.businessmanagement.dtos.client.command.SaleInvoiceCapsuleDto;
import com.c2psi.businessmanagement.services.contracts.client.command.SaleInvoiceCapsuleService;
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
public class SaleInvoiceCapsuleApiImpl implements SaleInvoiceCapsuleApi {
    private SaleInvoiceCapsuleService saleInvoiceCapsuleService;

    @Autowired
    public SaleInvoiceCapsuleApiImpl(SaleInvoiceCapsuleService saleInvoiceCapsuleService) {
        this.saleInvoiceCapsuleService = saleInvoiceCapsuleService;
    }

    @Override
    public ResponseEntity saveSaleInvoiceCapsule(SaleInvoiceCapsuleDto saleiCapsuleDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", saleiCapsuleDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }
        SaleInvoiceCapsuleDto saleiCapsuleDtoSaved = saleInvoiceCapsuleService.saveSaleInvoiceCapsule(saleiCapsuleDto);
        log.info("The method saveSaleInvoiceCapsule is being executed");
        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "Sale Invoice Capsule created successfully ");
        map.put("data", saleiCapsuleDtoSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateSaleInvoiceCapsule(SaleInvoiceCapsuleDto saleiCapsuleDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", saleiCapsuleDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }
        SaleInvoiceCapsuleDto saleiCapsuleDtoUpdated = saleInvoiceCapsuleService.updateSaleInvoiceCapsule(saleiCapsuleDto);
        log.info("The method saveSaleInvoiceCapsule is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Capsule updated successfully ");
        map.put("data", saleiCapsuleDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteSaleInvoiceCapsuleById(Long saleiCapsuleId) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean delete = saleInvoiceCapsuleService.deleteSaleInvoiceCapsuleById(saleiCapsuleId);
        log.info("The method deleteSaleInvoiceCapsuleById is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Capsule deleted successfully ");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findSaleInvoiceCapsuleById(Long saleiCapsuleId) {
        Map<String, Object> map = new LinkedHashMap<>();
        SaleInvoiceCapsuleDto saleiCapsuleDtoFound = saleInvoiceCapsuleService.findSaleInvoiceCapsuleById(saleiCapsuleId);
        log.info("The method findSaleInvoiceCapsuleById is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Capsule found successfully ");
        map.put("data", saleiCapsuleDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findSaleInvoiceCapsuleByCode(String saleiCapsuleCode, Long posId) {
        Map<String, Object> map = new LinkedHashMap<>();
        SaleInvoiceCapsuleDto saleiCapsuleDtoFound = saleInvoiceCapsuleService.findSaleInvoiceCapsuleByCode(saleiCapsuleCode, posId);
        log.info("The method findSaleInvoiceCapsuleByCode is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Capsule found successfully ");
        map.put("data", saleiCapsuleDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllSaleiCapsuleBetween(Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<SaleInvoiceCapsuleDto> saleInvoiceCapsuleDtoList = saleInvoiceCapsuleService.findAllSaleiCapsuleBetween(
                startDate, endDate);
        log.info("The method findAllSaleiCapsuleBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Capsule list found successfully ");
        map.put("data", saleInvoiceCapsuleDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageSaleiCapsuleBetween(Instant startDate, Instant endDate, Optional<Integer> optpagenum,
                                                   Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();
        Page<SaleInvoiceCapsuleDto> saleInvoiceCapsuleDtoPage = saleInvoiceCapsuleService.findPageSaleiCapsuleBetween(
                startDate, endDate, pagenum, pagesize);
        log.info("The method findPageSaleiCapsuleBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Capsule page found successfully ");
        map.put("data", saleInvoiceCapsuleDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllSaleiCapsuleofClientBetween(Long clientId, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<SaleInvoiceCapsuleDto> saleInvoiceCapsuleDtoList = saleInvoiceCapsuleService.findAllSaleiCapsuleofClientBetween(
                clientId, startDate, endDate);
        log.info("The method findAllSaleiCapsuleofClientBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Capsule list found successfully ");
        map.put("data", saleInvoiceCapsuleDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageSaleiCapsuleofClientBetween(Long clientId, Instant startDate, Instant endDate,
                                                           Optional<Integer> optpagenum,
                                                           Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Map<String, Object> map = new LinkedHashMap<>();
        Page<SaleInvoiceCapsuleDto> saleInvoiceCapsuleDtoPage = saleInvoiceCapsuleService.findPageSaleiCapsuleofClientBetween(
                clientId, startDate, endDate, pagenum, pagesize);
        log.info("The method findPageSaleiCapsuleofClientBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Capsule page found successfully ");
        map.put("data", saleInvoiceCapsuleDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllSaleiCapsuleofUserbmBetween(Long userbmId, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<SaleInvoiceCapsuleDto> saleInvoiceCapsuleDtoList = saleInvoiceCapsuleService.findAllSaleiCapsuleofUserbmBetween(
                userbmId, startDate, endDate);
        log.info("The method findAllSaleiCapsuleofUserbmBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Capsule list found successfully ");
        map.put("data", saleInvoiceCapsuleDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageSaleiCapsuleofUserbmBetween(Long userbmId, Instant startDate, Instant endDate,
                                                           Optional<Integer> optpagenum,
                                                           Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Map<String, Object> map = new LinkedHashMap<>();
        Page<SaleInvoiceCapsuleDto> saleInvoiceCapsuleDtoPage = saleInvoiceCapsuleService.findPageSaleiCapsuleofUserbmBetween(
                userbmId, startDate, endDate, pagenum, pagesize);
        log.info("The method findPageSaleiCapsuleofUserbmBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Capsule page found successfully ");
        map.put("data", saleInvoiceCapsuleDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllSaleiCapsuleinPosBetween(Long posId, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<SaleInvoiceCapsuleDto> saleInvoiceCapsuleDtoList = saleInvoiceCapsuleService.findAllSaleiCapsuleinPosBetween(
                posId, startDate, endDate);
        log.info("The method findAllSaleiCapsuleinPosBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Capsule list found successfully ");
        map.put("data", saleInvoiceCapsuleDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageSaleiCapsuleinPosBetween(Long posId, Instant startDate, Instant endDate,
                                                        Optional<Integer> optpagenum, Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Map<String, Object> map = new LinkedHashMap<>();
        Page<SaleInvoiceCapsuleDto> saleInvoiceCapsuleDtoPage = saleInvoiceCapsuleService.findPageSaleiCapsuleinPosBetween(
                posId, startDate, endDate, pagenum, pagesize);
        log.info("The method findPageSaleiCapsuleinPosBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Capsule page found successfully ");
        map.put("data", saleInvoiceCapsuleDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllSaleiCapsuleofUserbminPosBetween(Long userbmId, Long posId, Instant startDate,
                                                               Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<SaleInvoiceCapsuleDto> saleInvoiceCapsuleDtoList = saleInvoiceCapsuleService.findAllSaleiCapsuleofUserbminPosBetween(
                userbmId, posId, startDate, endDate);
        log.info("The method findAllSaleiCapsuleofUserbminPosBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Capsule list found successfully ");
        map.put("data", saleInvoiceCapsuleDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageSaleiCapsuleofUserbminPosBetween(Long userbmId, Long posId, Instant startDate,
                                                                Instant endDate, Optional<Integer> optpagenum,
                                                                Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Map<String, Object> map = new LinkedHashMap<>();
        Page<SaleInvoiceCapsuleDto> saleInvoiceCapsuleDtoPage = saleInvoiceCapsuleService.findPageSaleiCapsuleofUserbminPosBetween(
                userbmId, posId, startDate, endDate, pagenum, pagesize);
        log.info("The method findPageSaleiCapsuleofUserbminPosBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Capsule page found successfully ");
        map.put("data", saleInvoiceCapsuleDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllSaleiCapsuleofClientinPosBetween(Long clientId, Long posId, Instant startDate,
                                                               Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<SaleInvoiceCapsuleDto> saleInvoiceCapsuleDtoList = saleInvoiceCapsuleService.findAllSaleiCapsuleofClientinPosBetween(
                clientId, posId, startDate, endDate);
        log.info("The method findAllSaleiCapsuleofClientinPosBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Capsule list found successfully ");
        map.put("data", saleInvoiceCapsuleDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageSaleiCapsuleofClientinPosBetween(Long clientId, Long posId, Instant startDate,
                                                                Instant endDate, Optional<Integer> optpagenum,
                                                                Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Map<String, Object> map = new LinkedHashMap<>();
        Page<SaleInvoiceCapsuleDto> saleInvoiceCapsuleDtoPage = saleInvoiceCapsuleService.findPageSaleiCapsuleofClientinPosBetween(
                clientId, posId, startDate, endDate, pagenum, pagesize);
        log.info("The method findPageSaleiCapsuleofClientinPosBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Capsule page found successfully ");
        map.put("data", saleInvoiceCapsuleDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllSaleiCapsuleofClientforUserbmBetween(Long clientId, Long userbmId, Instant startDate,
                                                                   Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<SaleInvoiceCapsuleDto> saleInvoiceCapsuleDtoList = saleInvoiceCapsuleService.findAllSaleiCapsuleofClientforUserbmBetween(
                clientId, userbmId, startDate, endDate);
        log.info("The method findAllSaleiCapsuleofClientforUserbmBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Capsule list found successfully ");
        map.put("data", saleInvoiceCapsuleDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageSaleiCapsuleofClientforUserbmBetween(Long clientId, Long userbmId, Instant startDate,
                                                                    Instant endDate, Optional<Integer> optpagenum,
                                                                    Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Map<String, Object> map = new LinkedHashMap<>();
        Page<SaleInvoiceCapsuleDto> saleInvoiceCapsuleDtoPage = saleInvoiceCapsuleService.findPageSaleiCapsuleofClientforUserbmBetween(
                clientId, userbmId, startDate, endDate, pagenum, pagesize);
        log.info("The method findPageSaleiCapsuleofClientforUserbmBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Capsule page found successfully ");
        map.put("data", saleInvoiceCapsuleDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllSaleiCapsuleofClientforUserbminPosBetween(Long clientId, Long userbmId, Long posId,
                                                                        Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<SaleInvoiceCapsuleDto> saleInvoiceCapsuleDtoList = saleInvoiceCapsuleService.
                findAllSaleiCapsuleofClientforUserbminPosBetween(clientId, userbmId, posId, startDate, endDate);
        log.info("The method findAllSaleiCapsuleofClientforUserbminPosBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Capsule list found successfully ");
        map.put("data", saleInvoiceCapsuleDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageSaleiCapsuleofClientforUserbminPosBetween(Long clientId, Long userbmId, Long posId,
                                                                         Instant startDate, Instant endDate,
                                                                         Optional<Integer> optpagenum,
                                                                         Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();
        Page<SaleInvoiceCapsuleDto> saleInvoiceCapsuleDtoPage = saleInvoiceCapsuleService.
                findPageSaleiCapsuleofClientforUserbminPosBetween(clientId, userbmId, posId, startDate, endDate, pagenum,
                        pagesize);
        log.info("The method findPageSaleiCapsuleofClientforUserbminPosBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Capsule page found successfully ");
        map.put("data", saleInvoiceCapsuleDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

}
