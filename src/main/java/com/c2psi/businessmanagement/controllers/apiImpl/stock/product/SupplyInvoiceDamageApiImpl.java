package com.c2psi.businessmanagement.controllers.apiImpl.stock.product;

import com.c2psi.businessmanagement.controllers.api.stock.product.SupplyInvoiceDamageApi;
import com.c2psi.businessmanagement.dtos.stock.product.SupplyInvoiceDamageDto;
import com.c2psi.businessmanagement.services.contracts.stock.product.SupplyInvoiceDamageService;
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
public class SupplyInvoiceDamageApiImpl implements SupplyInvoiceDamageApi {
    private SupplyInvoiceDamageService supplyInvDamageService;

    @Autowired
    public SupplyInvoiceDamageApiImpl(SupplyInvoiceDamageService supplyInvDamageService) {
        this.supplyInvDamageService = supplyInvDamageService;
    }

    @Override
    public ResponseEntity saveSupplyInvoiceDamage(SupplyInvoiceDamageDto sidamDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", sidamDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        SupplyInvoiceDamageDto sidamDtoSaved = supplyInvDamageService.saveSupplyInvoiceDamage(sidamDto);
        log.info("The method saveSupplyInvoiceDamage is being executed");

        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "Supply Invoice Damage created successfully ");
        map.put("data", sidamDtoSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateSupplyInvoiceDamage(SupplyInvoiceDamageDto sidamDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", sidamDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        SupplyInvoiceDamageDto sidamDtoUpdated = supplyInvDamageService.updateSupplyInvoiceDamage(sidamDto);
        log.info("The method updateSupplyInvoiceDamage is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Supply Invoice Damage updated successfully ");
        map.put("data", sidamDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteSupplyInvoiceDamageById(Long sidamId) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean delete = supplyInvDamageService.deleteSupplyInvoiceDamageById(sidamId);
        log.info("The method deleteSupplyInvoiceDamageById is being executed");
        //return null;
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Supply Invoice Damage deleted successfully ");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findSupplyInvoiceDamageById(Long sidamId) {
        Map<String, Object> map = new LinkedHashMap<>();
        SupplyInvoiceDamageDto sidamDtoFound = supplyInvDamageService.findSupplyInvoiceDamageById(sidamId);
        log.info("The method findSupplyInvoiceDamageById is being executed");
        //return null;
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Supply Invoice Damage found successfully ");
        map.put("data", sidamDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findSupplyInvoiceDamageByCode(String sidamCode, Long posId) {
        Map<String, Object> map = new LinkedHashMap<>();
        SupplyInvoiceDamageDto sidamDtoFound = supplyInvDamageService.findSupplyInvoiceDamageByCode(sidamCode, posId);
        log.info("The method findSupplyInvoiceDamageByCode is being executed");
        //return null;
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Supply Invoice Damage found successfully ");
        map.put("data", sidamDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllSupplyInvoiceDamageBetween(Long posId, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<SupplyInvoiceDamageDto> supplyInvoiceDamageDtoList = supplyInvDamageService.
                findAllSupplyInvoiceDamageBetween(posId, startDate, endDate);
        log.info("The method findAllSupplyInvoiceDamageBetween is being executed");
        //return null;
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Supply Invoice Damage list found successfully ");
        map.put("data", supplyInvoiceDamageDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageSupplyInvoiceDamageBetween(Long posId, Instant startDate, Instant endDate,
                                                             Optional<Integer> optpagenum,
                                                             Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();
        Page<SupplyInvoiceDamageDto> supplyInvoiceDamageDtoPage = supplyInvDamageService.
                findPageSupplyInvoiceDamageBetween(posId, startDate, endDate, pagenum, pagesize);
        log.info("The method findAllSupplyInvoiceDamageBetween is being executed");
        //return null;
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Supply Invoice Damage page found successfully ");
        map.put("data", supplyInvoiceDamageDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllSupplyInvoiceDamageofUserbmBetween(Long posId, Long userbmId, Instant startDate,
                                                                    Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<SupplyInvoiceDamageDto> supplyInvoiceDamageDtoList = supplyInvDamageService.
                findAllSupplyInvoiceDamageofUserbmBetween(posId, userbmId, startDate, endDate);
        log.info("The method findAllSupplyInvoiceDamageofUserbmBetween is being executed");
        //return null;
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Supply Invoice Damage list found successfully ");
        map.put("data", supplyInvoiceDamageDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageSupplyInvoiceDamageofUserbmBetween(Long posId, Long userbmId, Instant startDate,
                                                                     Instant endDate, Optional<Integer> optpagenum,
                                                                     Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();
        Page<SupplyInvoiceDamageDto> supplyInvoiceDamageDtoPage = supplyInvDamageService.
                findPageSupplyInvoiceDamageofUserbmBetween(posId, userbmId, startDate, endDate, pagenum, pagesize);
        log.info("The method findPageSupplyInvoiceDamageofUserbmBetween is being executed");
        //return null;
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Supply Invoice Damage page found successfully ");
        map.put("data", supplyInvoiceDamageDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllSupplyInvoiceDamageofProviderBetween(Long posId, Long providerId, Instant startDate,
                                                                      Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<SupplyInvoiceDamageDto> supplyInvoiceDamageDtoList = supplyInvDamageService.
                findAllSupplyInvoiceDamageofProviderBetween(posId, providerId, startDate, endDate);
        log.info("The method findAllSupplyInvoiceDamageofProviderBetween is being executed");
        //return null;
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Supply Invoice Damage list found successfully ");
        map.put("data", supplyInvoiceDamageDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageSupplyInvoiceDamageofProviderBetween(Long posId, Long providerId, Instant startDate,
                                                                       Instant endDate, Optional<Integer> optpagenum,
                                                                       Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();
        Page<SupplyInvoiceDamageDto> supplyInvoiceDamageDtoPage = supplyInvDamageService.
                findPageSupplyInvoiceDamageofProviderBetween(posId, providerId, startDate, endDate, pagenum, pagesize);
        log.info("The method findPageSupplyInvoiceDamageofProviderBetween is being executed");
        //return null;
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Supply Invoice Damage page found successfully ");
        map.put("data", supplyInvoiceDamageDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllSupplyInvoiceDamageofProviderAndUserbmBetween(Long posId, Long providerId,
                                                                               Long userbmId, Instant startDate,
                                                                               Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<SupplyInvoiceDamageDto> supplyInvoiceDamageDtoList = supplyInvDamageService.
                findAllSupplyInvoiceDamageofProviderAndUserbmBetween(posId, providerId, userbmId, startDate, endDate);
        log.info("The method findAllSupplyInvoiceDamageofProviderAndUserbmBetween is being executed");
        //return null;
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Supply Invoice Damage list found successfully ");
        map.put("data", supplyInvoiceDamageDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageSupplyInvoiceDamageofProviderAndUserbmBetween(Long posId, Long providerId,
                                                                                Long userbmId, Instant startDate,
                                                                                Instant endDate,
                                                                                Optional<Integer> optpagenum,
                                                                                Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();
        Page<SupplyInvoiceDamageDto> supplyInvoiceDamageDtoPage = supplyInvDamageService.
                findPageSupplyInvoiceDamageofProviderAndUserbmBetween(posId, providerId, userbmId, startDate, endDate,
                        pagenum, pagesize);
        log.info("The method findPageSupplyInvoiceDamageofProviderAndUserbmBetween is being executed");
        //return null;
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Supply Invoice Damage page found successfully ");
        map.put("data", supplyInvoiceDamageDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }
}
