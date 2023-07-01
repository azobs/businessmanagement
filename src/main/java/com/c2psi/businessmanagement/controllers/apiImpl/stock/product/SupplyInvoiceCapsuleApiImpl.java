package com.c2psi.businessmanagement.controllers.apiImpl.stock.product;

import com.c2psi.businessmanagement.controllers.api.stock.product.SupplyInvoiceCapsuleApi;
import com.c2psi.businessmanagement.dtos.stock.product.SupplyInvoiceCapsuleDto;
import com.c2psi.businessmanagement.services.contracts.stock.product.SupplyInvoiceCapsuleService;
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
public class SupplyInvoiceCapsuleApiImpl implements SupplyInvoiceCapsuleApi {
    private SupplyInvoiceCapsuleService supplyInvCapsService;

    @Autowired
    public SupplyInvoiceCapsuleApiImpl(SupplyInvoiceCapsuleService supplyInvCapsService) {
        this.supplyInvCapsService = supplyInvCapsService;
    }

    @Override
    public ResponseEntity saveSupplyInvoiceCapsule(SupplyInvoiceCapsuleDto sicapsDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", sicapsDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        SupplyInvoiceCapsuleDto sicapsDtoSaved = supplyInvCapsService.saveSupplyInvoiceCapsule(sicapsDto);
        log.info("The method saveSupplyInvoiceCapsule is being executed");

        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "Supply Invoice Capsule created successfully ");
        map.put("data", sicapsDtoSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateSupplyInvoiceCapsule(SupplyInvoiceCapsuleDto sicapsDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", sicapsDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        SupplyInvoiceCapsuleDto sicapsDtoUpdated = supplyInvCapsService.updateSupplyInvoiceCapsule(sicapsDto);
        log.info("The method saveSupplyInvoiceCapsule is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Supply Invoice Capsule updated successfully ");
        map.put("data", sicapsDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteSupplyInvoiceCapsuleById(Long sicapsId) {
        Map<String, Object> map = new LinkedHashMap<>();

        Boolean delete = supplyInvCapsService.deleteSupplyInvoiceCapsuleById(sicapsId);
        log.info("The method deleteSupplyInvoiceCapsuleById is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Supply Invoice Capsule deleted successfully ");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findSupplyInvoiceCapsuleById(Long sicapsId) {
        Map<String, Object> map = new LinkedHashMap<>();
        SupplyInvoiceCapsuleDto sicapsDtoFound = supplyInvCapsService.findSupplyInvoiceCapsuleById(sicapsId);
        log.info("The method findSupplyInvoiceCapsuleById is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Supply Invoice Capsule found successfully ");
        map.put("data", sicapsDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findSupplyInvoiceCapsuleByCode(String sicapsCode, Long posId) {
        Map<String, Object> map = new LinkedHashMap<>();
        SupplyInvoiceCapsuleDto sicapsDtoFound = supplyInvCapsService.findSupplyInvoiceCapsuleByCode(sicapsCode, posId);
        log.info("The method findSupplyInvoiceCapsuleByCode is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Supply Invoice Capsule found successfully ");
        map.put("data", sicapsDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllSupplyInvoiceCapsuleBetween(Long posId, Instant startDate, Instant endDate) {

        Map<String, Object> map = new LinkedHashMap<>();
        List<SupplyInvoiceCapsuleDto> supplyInvCapsDtoList = supplyInvCapsService.findAllSupplyInvoiceCapsuleBetween(posId
                , startDate, endDate);
        log.info("The method findAllSupplyInvoiceCapsuleBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Supply Invoice Capsule list found successfully ");
        map.put("data", supplyInvCapsDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageSupplyInvoiceCapsuleBetween(Long posId, Instant startDate, Instant endDate,
                                                              Optional<Integer> optpagenum,
                                                              Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();
        Page<SupplyInvoiceCapsuleDto> supplyInvCapsDtoPage = supplyInvCapsService.findPageSupplyInvoiceCapsuleBetween(posId
                , startDate, endDate, pagenum, pagesize);
        log.info("The method findAllSupplyInvoiceCapsuleBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Supply Invoice Capsule page found successfully ");
        map.put("data", supplyInvCapsDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllSupplyInvoiceCapsuleofUserbmBetween(Long posId, Long userbmId, Instant startDate,
                                                                     Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<SupplyInvoiceCapsuleDto> supplyInvCapsDtoList = supplyInvCapsService.
                findAllSupplyInvoiceCapsuleofUserbmBetween(posId, userbmId, startDate, endDate);
        log.info("The method findAllSupplyInvoiceCapsuleofUserbmBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Supply Invoice Capsule list found successfully ");
        map.put("data", supplyInvCapsDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageSupplyInvoiceCapsuleofUserbmBetween(Long posId, Long userbmId, Instant startDate,
                                                                      Instant endDate, Optional<Integer> optpagenum,
                                                                      Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();
        Page<SupplyInvoiceCapsuleDto> supplyInvCapsDtoPage = supplyInvCapsService.
                findPageSupplyInvoiceCapsuleofUserbmBetween(posId, userbmId, startDate, endDate, pagenum, pagesize);
        log.info("The method findPageSupplyInvoiceCapsuleofUserbmBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Supply Invoice Capsule page found successfully ");
        map.put("data", supplyInvCapsDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllSupplyInvoiceCapsuleofProviderBetween(Long posId, Long providerId, Instant startDate,
                                                                       Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<SupplyInvoiceCapsuleDto> supplyInvCapsDtoList = supplyInvCapsService.
                findAllSupplyInvoiceCapsuleofProviderBetween(posId, providerId, startDate, endDate);
        log.info("The method findAllSupplyInvoiceCapsuleofProviderBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Supply Invoice Capsule list found successfully ");
        map.put("data", supplyInvCapsDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageSupplyInvoiceCapsuleofProviderBetween(Long posId, Long providerId, Instant startDate,
                                                                        Instant endDate, Optional<Integer> optpagenum,
                                                                        Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();
        Page<SupplyInvoiceCapsuleDto> supplyInvCapsDtoPage = supplyInvCapsService.
                findPageSupplyInvoiceCapsuleofProviderBetween(posId, providerId, startDate, endDate, pagenum, pagesize);
        log.info("The method findPageSupplyInvoiceCapsuleofProviderBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Supply Invoice Capsule page found successfully ");
        map.put("data", supplyInvCapsDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllSupplyInvoiceCapsuleofProviderAndUserbmBetween(Long posId, Long providerId,
                                                                                Long userbmId, Instant startDate,
                                                                                Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<SupplyInvoiceCapsuleDto> supplyInvCapsDtoList = supplyInvCapsService.
                findAllSupplyInvoiceCapsuleofProviderAndUserbmBetween(posId, providerId, userbmId, startDate, endDate);
        log.info("The method findAllSupplyInvoiceCapsuleofProviderAndUserbmBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Supply Invoice Capsule list found successfully ");
        map.put("data", supplyInvCapsDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageSupplyInvoiceCapsuleofProviderAndUserbmBetween(Long posId, Long providerId,
                                                                                 Long userbmId, Instant startDate,
                                                                                 Instant endDate,
                                                                                 Optional<Integer> optpagenum,
                                                                                 Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();
        Page<SupplyInvoiceCapsuleDto> supplyInvCapsDtoPage = supplyInvCapsService.
                findPageSupplyInvoiceCapsuleofProviderAndUserbmBetween(posId, providerId, userbmId, startDate, endDate,
                        pagenum, pagesize);
        log.info("The method findPageSupplyInvoiceCapsuleofProviderAndUserbmBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Supply Invoice Capsule page found successfully ");
        map.put("data", supplyInvCapsDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }
}
