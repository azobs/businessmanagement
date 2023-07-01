package com.c2psi.businessmanagement.controllers.apiImpl.stock.product;

import com.c2psi.businessmanagement.controllers.api.stock.product.SupplyInvoiceCashApi;
import com.c2psi.businessmanagement.dtos.stock.product.SupplyInvoiceCashDto;
import com.c2psi.businessmanagement.services.contracts.stock.product.SupplyInvoiceCashService;
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
public class SupplyInvoiceCashApiImpl implements SupplyInvoiceCashApi {
    private SupplyInvoiceCashService supplyInvCashService;

    @Autowired
    public SupplyInvoiceCashApiImpl(SupplyInvoiceCashService supplyInvCashService) {
        this.supplyInvCashService = supplyInvCashService;
    }

    @Override
    public ResponseEntity saveSupplyInvoiceCash(SupplyInvoiceCashDto sicashDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", sicashDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        SupplyInvoiceCashDto sicashDtoSaved = supplyInvCashService.saveSupplyInvoiceCash(sicashDto);
        log.info("The method saveSupplyInvoiceCash is being executed");

        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "Supply Invoice Cash created successfully ");
        map.put("data", sicashDtoSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateSupplyInvoiceCash(SupplyInvoiceCashDto sicashDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", sicashDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        SupplyInvoiceCashDto sicashDtoUpdated = supplyInvCashService.updateSupplyInvoiceCash(sicashDto);
        log.info("The method saveSupplyInvoiceCash is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Supply Invoice Cash updated successfully ");
        map.put("data", sicashDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteSupplyInvoiceCashById(Long sicashId) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean delete = supplyInvCashService.deleteSupplyInvoiceCashById(sicashId);
        log.info("The method deleteSupplyInvoiceCashById is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Supply Invoice Cash deleted successfully ");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findSupplyInvoiceCashById(Long sicashId) {

        Map<String, Object> map = new LinkedHashMap<>();
        SupplyInvoiceCashDto sicashDtoFound = supplyInvCashService.findSupplyInvoiceCashById(sicashId);
        log.info("The method findSupplyInvoiceCashById is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Supply Invoice Cash found successfully ");
        map.put("data", sicashDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findSupplyInvoiceCashByCode(String sicashCode, Long posId) {

        Map<String, Object> map = new LinkedHashMap<>();
        SupplyInvoiceCashDto sicashDtoFound = supplyInvCashService.findSupplyInvoiceCashByCode(sicashCode, posId);
        log.info("The method findSupplyInvoiceCashByCode is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Supply Invoice Cash found successfully ");
        map.put("data", sicashDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllSupplyInvoiceCashBetween(Long posId, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<SupplyInvoiceCashDto> supplyInvoiceCashDtoList = supplyInvCashService.findAllSupplyInvoiceCashBetween(
                posId, startDate, endDate);
        log.info("The method findAllSupplyInvoiceCashBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Supply Invoice Cash list found successfully ");
        map.put("data", supplyInvoiceCashDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageSupplyInvoiceCashBetween(Long posId, Instant startDate, Instant endDate,
                                                           Optional<Integer> optpagenum,
                                                           Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();
        Page<SupplyInvoiceCashDto> supplyInvoiceCashDtoPage = supplyInvCashService.findPageSupplyInvoiceCashBetween(
                posId, startDate, endDate, pagenum, pagesize);
        log.info("The method findPageSupplyInvoiceCashBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Supply Invoice Cash page found successfully ");
        map.put("data", supplyInvoiceCashDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllSupplyInvoiceCashofUserbmBetween(Long posId, Long userbmId, Instant startDate,
                                                                  Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<SupplyInvoiceCashDto> supplyInvoiceCashDtoList = supplyInvCashService.
                findAllSupplyInvoiceCashofUserbmBetween(posId, userbmId, startDate, endDate);
        log.info("The method findAllSupplyInvoiceCashofUserbmBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Supply Invoice Cash list found successfully ");
        map.put("data", supplyInvoiceCashDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageSupplyInvoiceCashofUserbmBetween(Long posId, Long userbmId, Instant startDate,
                                                                   Instant endDate, Optional<Integer> optpagenum,
                                                                   Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();
        Page<SupplyInvoiceCashDto> supplyInvoiceCashDtoPage = supplyInvCashService.
                findPageSupplyInvoiceCashofUserbmBetween(posId, userbmId, startDate, endDate, pagenum, pagesize);
        log.info("The method findPageSupplyInvoiceCashofUserbmBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Supply Invoice Cash page found successfully ");
        map.put("data", supplyInvoiceCashDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllSupplyInvoiceCashofProviderBetween(Long posId, Long providerId, Instant startDate,
                                                                    Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<SupplyInvoiceCashDto> supplyInvoiceCashDtoList = supplyInvCashService.
                findAllSupplyInvoiceCashofProviderBetween(posId, providerId, startDate, endDate);
        log.info("The method findAllSupplyInvoiceCashofProviderBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Supply Invoice Cash list found successfully ");
        map.put("data", supplyInvoiceCashDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageSupplyInvoiceCashofProviderBetween(Long posId, Long providerId, Instant startDate,
                                                                     Instant endDate, Optional<Integer> optpagenum,
                                                                     Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();
        Page<SupplyInvoiceCashDto> supplyInvoiceCashDtoPage = supplyInvCashService.
                findPageSupplyInvoiceCashofProviderBetween(posId, providerId, startDate, endDate, pagenum, pagesize);
        log.info("The method findPageSupplyInvoiceCashofProviderBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Supply Invoice Cash page found successfully ");
        map.put("data", supplyInvoiceCashDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllSupplyInvoiceCashofProviderAndUserbmBetween(Long posId, Long providerId,
                                                                             Long userbmId, Instant startDate,
                                                                             Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<SupplyInvoiceCashDto> supplyInvoiceCashDtoList = supplyInvCashService.
                findAllSupplyInvoiceCashofProviderAndUserbmBetween(posId, providerId, userbmId, startDate, endDate);
        log.info("The method findAllSupplyInvoiceCashofProviderBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Supply Invoice Cash list found successfully ");
        map.put("data", supplyInvoiceCashDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageSupplyInvoiceCashofProviderAndUserbmBetween(Long posId, Long providerId, Long userbmId,
                                                                              Instant startDate, Instant endDate,
                                                                              Optional<Integer> optpagenum,
                                                                              Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();
        Page<SupplyInvoiceCashDto> supplyInvoiceCashDtoPage = supplyInvCashService.
                findPageSupplyInvoiceCashofProviderAndUserbmBetween(posId, providerId, userbmId, startDate, endDate,
                        pagenum, pagesize);
        log.info("The method findPageSupplyInvoiceCashofProviderAndUserbmBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Supply Invoice Cash page found successfully ");
        map.put("data", supplyInvoiceCashDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }
}
