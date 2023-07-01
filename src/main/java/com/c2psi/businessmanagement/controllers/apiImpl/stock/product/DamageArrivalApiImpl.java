package com.c2psi.businessmanagement.controllers.apiImpl.stock.product;

import com.c2psi.businessmanagement.controllers.api.stock.product.DamageArrivalApi;
import com.c2psi.businessmanagement.dtos.stock.product.DamageArrivalDto;
import com.c2psi.businessmanagement.services.contracts.stock.product.DamageArrivalService;
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
public class DamageArrivalApiImpl implements DamageArrivalApi {
    private DamageArrivalService damageArrivalService;

    @Autowired
    public DamageArrivalApiImpl(DamageArrivalService damageArrivalService) {
        this.damageArrivalService = damageArrivalService;
    }

    @Override
    public ResponseEntity saveDamageArrival(DamageArrivalDto damaDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", damaDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        DamageArrivalDto damaDtoSaved = damageArrivalService.saveDamageArrival(damaDto);
        log.info("The method saveDamageArrival is being executed");

        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "Damage Arrival created successfully ");
        map.put("data", damaDtoSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateDamageArrival(DamageArrivalDto damaDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", damaDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        DamageArrivalDto damaDtoUpdated = damageArrivalService.updateDamageArrival(damaDto);
        log.info("The method updateDamageArrival is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Damage Arrival updated successfully ");
        map.put("data", damaDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteDamageArrivalById(Long damaId) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean delete = damageArrivalService.deleteDamageArrivalById(damaId);
        log.info("The method deleteDamageArrivalById is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Damage Arrival deleted successfully ");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findDamageArrivalById(Long damaId) {
        Map<String, Object> map = new LinkedHashMap<>();
        DamageArrivalDto damaDtoFound = damageArrivalService.findDamageArrivalById(damaId);
        log.info("The method findDamageArrivalById is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Damage Arrival found successfully ");
        map.put("data", damaDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findDamageArrivalofArticleinSidam(Long artId, Long sidamId) {
        Map<String, Object> map = new LinkedHashMap<>();
        DamageArrivalDto damaDtoFound = damageArrivalService.findDamageArrivalofArticleinSidam(artId, sidamId);
        log.info("The method findDamageArrivalofArticleinSiDam is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Damage Arrival found successfully ");
        map.put("data", damaDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllDamageArrivalinSidam(Long sidamId) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<DamageArrivalDto> damageArrivalDtoList = damageArrivalService.findAllDamageArrivalinSidam(sidamId);
        log.info("The method findAllDamageArrivalinSidam is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Damage Arrival list found successfully ");
        map.put("data", damageArrivalDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageDamageArrivalinSidam(Long sidamId, Optional<Integer> optpagenum,
                                                       Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();
        Page<DamageArrivalDto> damageArrivalDtoPage = damageArrivalService.findPageDamageArrivalinSidam(
                sidamId, pagenum, pagesize);
        log.info("The method findPageDamageArrivalinSidam is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Damage Arrival page found successfully ");
        map.put("data", damageArrivalDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllDamageArrivalinSidamBetween(Long sidamId, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<DamageArrivalDto> damageArrivalDtoList = damageArrivalService.findAllDamageArrivalinSidamBetween(
                sidamId, startDate, endDate);
        log.info("The method findAllDamageArrivalinSidamBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Damage Arrival list found successfully ");
        map.put("data", damageArrivalDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageDamageArrivalinSidamBetween(Long sidamId, Instant startDate, Instant endDate,
                                                              Optional<Integer> optpagenum,
                                                              Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();
        Page<DamageArrivalDto> damageArrivalDtoPage = damageArrivalService.findPageDamageArrivalinSidamBetween(
                sidamId, startDate, endDate, pagenum, pagesize);
        log.info("The method findPageDamageArrivalinSidamBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Damage Arrival page found successfully ");
        map.put("data", damageArrivalDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }
}
