package com.c2psi.businessmanagement.controllers.apiImpl.client.command;

import com.c2psi.businessmanagement.controllers.api.client.command.SaleInvoiceDamageApi;
import com.c2psi.businessmanagement.dtos.client.command.SaleInvoiceDamageDto;
import com.c2psi.businessmanagement.services.contracts.client.command.SaleInvoiceDamageService;
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
public class SaleInvoiceDamageApiImpl implements SaleInvoiceDamageApi {
    private SaleInvoiceDamageService saleInvoiceDamageService;

    @Autowired
    public SaleInvoiceDamageApiImpl(SaleInvoiceDamageService saleInvoiceDamageService) {
        this.saleInvoiceDamageService = saleInvoiceDamageService;
    }

    @Override
    public ResponseEntity saveSaleInvoiceDamage(SaleInvoiceDamageDto saleiDamageDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", saleiDamageDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }
        SaleInvoiceDamageDto saleiDamageDtoSaved = saleInvoiceDamageService.saveSaleInvoiceDamage(saleiDamageDto);
        log.info("The method saveSaleInvoiceDamage is being executed");
        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "Sale Invoice Damage created successfully ");
        map.put("data", saleiDamageDtoSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateSaleInvoiceDamage(SaleInvoiceDamageDto saleiDamageDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", saleiDamageDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }
        SaleInvoiceDamageDto saleiDamageDtoUpdated = saleInvoiceDamageService.updateSaleInvoiceDamage(saleiDamageDto);
        log.info("The method saveSaleInvoiceDamage is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Damage updated successfully ");
        map.put("data", saleiDamageDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteSaleInvoiceDamageById(Long saleiDamageId) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean delete = saleInvoiceDamageService.deleteSaleInvoiceDamageById(saleiDamageId);
        log.info("The method deleteSaleInvoiceDamageById is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Damage deleted successfully ");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findSaleInvoiceDamageById(Long saleiDamageId) {
        Map<String, Object> map = new LinkedHashMap<>();
        SaleInvoiceDamageDto saleiDamageDtoFound = saleInvoiceDamageService.findSaleInvoiceDamageById(saleiDamageId);
        log.info("The method findSaleInvoiceDamageById is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Damage found successfully ");
        map.put("data", saleiDamageDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findSaleInvoiceDamageByCode(String saleiDamageCode, Long posId) {
        Map<String, Object> map = new LinkedHashMap<>();
        SaleInvoiceDamageDto saleiDamageDtoFound = saleInvoiceDamageService.findSaleInvoiceDamageByCode(saleiDamageCode, posId);
        log.info("The method findSaleInvoiceDamageByCode is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Damage found successfully ");
        map.put("data", saleiDamageDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllSaleiDamageBetween(Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<SaleInvoiceDamageDto> saleInvoiceDamageDtoList = saleInvoiceDamageService.findAllSaleiDamageBetween(
                startDate, endDate);
        log.info("The method findAllSaleiDamageBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Damage list found successfully ");
        map.put("data", saleInvoiceDamageDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageSaleiDamageBetween(Instant startDate, Instant endDate, Optional<Integer> optpagenum,
                                                      Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Map<String, Object> map = new LinkedHashMap<>();
        Page<SaleInvoiceDamageDto> saleInvoiceDamageDtoPage = saleInvoiceDamageService.findPageSaleiDamageBetween(
                startDate, endDate, pagenum, pagesize);
        log.info("The method findPageSaleiDamageBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Damage page found successfully ");
        map.put("data", saleInvoiceDamageDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllSaleiDamageofClientBetween(Long clientId, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<SaleInvoiceDamageDto> saleInvoiceDamageDtoList = saleInvoiceDamageService.findAllSaleiDamageofClientBetween(
                clientId, startDate, endDate);
        log.info("The method findAllSaleiDamageofClientBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Damage list found successfully ");
        map.put("data", saleInvoiceDamageDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageSaleiDamageofClientBetween(Long clientId, Instant startDate, Instant endDate,
                                                              Optional<Integer> optpagenum,
                                                              Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Map<String, Object> map = new LinkedHashMap<>();
        Page<SaleInvoiceDamageDto> saleInvoiceDamageDtoPage = saleInvoiceDamageService.findPageSaleiDamageofClientBetween(
                clientId, startDate, endDate, pagenum, pagesize);
        log.info("The method findPageSaleiDamageofClientBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Damage page found successfully ");
        map.put("data", saleInvoiceDamageDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllSaleiDamageofUserbmBetween(Long userbmId, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<SaleInvoiceDamageDto> saleInvoiceDamageDtoList = saleInvoiceDamageService.findAllSaleiDamageofUserbmBetween(
                userbmId, startDate, endDate);
        log.info("The method findAllSaleiDamageofUserbmBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Damage list found successfully ");
        map.put("data", saleInvoiceDamageDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageSaleiDamageofUserbmBetween(Long userbmId, Instant startDate, Instant endDate,
                                                              Optional<Integer> optpagenum,
                                                              Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Map<String, Object> map = new LinkedHashMap<>();
        Page<SaleInvoiceDamageDto> saleInvoiceDamageDtoPage = saleInvoiceDamageService.findPageSaleiDamageofUserbmBetween(
                userbmId, startDate, endDate, pagenum, pagesize);
        log.info("The method findPageSaleiDamageofUserbmBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Damage page found successfully ");
        map.put("data", saleInvoiceDamageDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllSaleiDamageinPosBetween(Long posId, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<SaleInvoiceDamageDto> saleInvoiceDamageDtoList = saleInvoiceDamageService.findAllSaleiDamageinPosBetween(
                posId, startDate, endDate);
        log.info("The method findAllSaleiDamageinPosBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Damage list found successfully ");
        map.put("data", saleInvoiceDamageDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageSaleiDamageinPosBetween(Long posId, Instant startDate, Instant endDate,
                                                           Optional<Integer> optpagenum, Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Map<String, Object> map = new LinkedHashMap<>();
        Page<SaleInvoiceDamageDto> saleInvoiceDamageDtoPage = saleInvoiceDamageService.findPageSaleiDamageinPosBetween(
                posId, startDate, endDate, pagenum, pagesize);
        log.info("The method findPageSaleiDamageinPosBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Damage page found successfully ");
        map.put("data", saleInvoiceDamageDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllSaleiDamageofUserbminPosBetween(Long userbmId, Long posId, Instant startDate,
                                                                  Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<SaleInvoiceDamageDto> saleInvoiceDamageDtoList = saleInvoiceDamageService.findAllSaleiDamageofUserbminPosBetween(
                userbmId, posId, startDate, endDate);
        log.info("The method findAllSaleiDamageofUserbminPosBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Damage list found successfully ");
        map.put("data", saleInvoiceDamageDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageSaleiDamageofUserbminPosBetween(Long userbmId, Long posId, Instant startDate,
                                                                   Instant endDate, Optional<Integer> optpagenum,
                                                                   Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Map<String, Object> map = new LinkedHashMap<>();
        Page<SaleInvoiceDamageDto> saleInvoiceDamageDtoPage = saleInvoiceDamageService.findPageSaleiDamageofUserbminPosBetween(
                userbmId, posId, startDate, endDate, pagenum, pagesize);
        log.info("The method findPageSaleiDamageofUserbminPosBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Damage page found successfully ");
        map.put("data", saleInvoiceDamageDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllSaleiDamageofClientinPosBetween(Long clientId, Long posId, Instant startDate,
                                                                  Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<SaleInvoiceDamageDto> saleInvoiceDamageDtoList = saleInvoiceDamageService.findAllSaleiDamageofClientinPosBetween(
                clientId, posId, startDate, endDate);
        log.info("The method findAllSaleiDamageofClientinPosBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Damage list found successfully ");
        map.put("data", saleInvoiceDamageDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageSaleiDamageofClientinPosBetween(Long clientId, Long posId, Instant startDate,
                                                                   Instant endDate, Optional<Integer> optpagenum,
                                                                   Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Map<String, Object> map = new LinkedHashMap<>();
        Page<SaleInvoiceDamageDto> saleInvoiceDamageDtoPage = saleInvoiceDamageService.findPageSaleiDamageofClientinPosBetween(
                clientId, posId, startDate, endDate, pagenum, pagesize);
        log.info("The method findPageSaleiDamageofClientinPosBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Damage page found successfully ");
        map.put("data", saleInvoiceDamageDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllSaleiDamageofClientforUserbmBetween(Long clientId, Long userbmId, Instant startDate,
                                                                      Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<SaleInvoiceDamageDto> saleInvoiceDamageDtoList = saleInvoiceDamageService.findAllSaleiDamageofClientforUserbmBetween(
                clientId, userbmId, startDate, endDate);
        log.info("The method findAllSaleiDamageofClientforUserbmBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Damage list found successfully ");
        map.put("data", saleInvoiceDamageDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageSaleiDamageofClientforUserbmBetween(Long clientId, Long userbmId, Instant startDate,
                                                                       Instant endDate, Optional<Integer> optpagenum,
                                                                       Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Map<String, Object> map = new LinkedHashMap<>();
        Page<SaleInvoiceDamageDto> saleInvoiceDamageDtoPage = saleInvoiceDamageService.findPageSaleiDamageofClientforUserbmBetween(
                clientId, userbmId, startDate, endDate, pagenum, pagesize);
        log.info("The method findPageSaleiDamageofClientforUserbmBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Damage page found successfully ");
        map.put("data", saleInvoiceDamageDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllSaleiDamageofClientforUserbminPosBetween(Long clientId, Long userbmId, Long posId,
                                                                           Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<SaleInvoiceDamageDto> saleInvoiceDamageDtoList = saleInvoiceDamageService.
                findAllSaleiDamageofClientforUserbminPosBetween(clientId, userbmId, posId, startDate, endDate);
        log.info("The method findAllSaleiDamageofClientforUserbminPosBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Damage list found successfully ");
        map.put("data", saleInvoiceDamageDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageSaleiDamageofClientforUserbminPosBetween(Long clientId, Long userbmId, Long posId,
                                                                            Instant startDate, Instant endDate,
                                                                            Optional<Integer> optpagenum,
                                                                            Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();
        Page<SaleInvoiceDamageDto> saleInvoiceDamageDtoPage = saleInvoiceDamageService.
                findPageSaleiDamageofClientforUserbminPosBetween(clientId, userbmId, posId, startDate, endDate, pagenum,
                        pagesize);
        log.info("The method findPageSaleiDamageofClientforUserbminPosBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Damage page found successfully ");
        map.put("data", saleInvoiceDamageDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }
}
