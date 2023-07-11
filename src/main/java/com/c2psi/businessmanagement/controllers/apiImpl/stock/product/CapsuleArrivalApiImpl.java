package com.c2psi.businessmanagement.controllers.apiImpl.stock.product;

import com.c2psi.businessmanagement.controllers.api.stock.product.CapsuleArrivalApi;
import com.c2psi.businessmanagement.dtos.stock.product.CapsuleArrivalDto;
import com.c2psi.businessmanagement.services.contracts.stock.product.CapsuleArrivalService;
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
public class CapsuleArrivalApiImpl implements CapsuleArrivalApi {
    private CapsuleArrivalService capsuleArrivalService;

    @Autowired
    public CapsuleArrivalApiImpl(CapsuleArrivalService capsuleArrivalService) {
        this.capsuleArrivalService = capsuleArrivalService;
    }

    @Override
    public ResponseEntity saveCapsuleArrival(CapsuleArrivalDto capsaDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", capsaDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        CapsuleArrivalDto capsaDtoSaved = capsuleArrivalService.saveCapsuleArrival(capsaDto);
        log.info("The method saveCapsuleArrival is being executed");

        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "Capsule Arrival created successfully ");
        map.put("data", capsaDtoSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateCapsuleArrival(CapsuleArrivalDto capsaDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", capsaDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }
        //return null;
        CapsuleArrivalDto capsaDtoUpdated = capsuleArrivalService.updateCapsuleArrival(capsaDto);
        log.info("The method updateCapsuleArrival is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Capsule Arrival updated successfully ");
        map.put("data", capsaDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteCapsuleArrivalById(Long capsaId) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean delete = capsuleArrivalService.deleteCapsuleArrivalById(capsaId);
        log.info("The method deleteCapsuleArrivalById is being executed");
        //return null;
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Capsule Arrival deleted successfully ");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findCapsuleArrivalById(Long capsaId) {
        Map<String, Object> map = new LinkedHashMap<>();
        CapsuleArrivalDto capsaDtoFound = capsuleArrivalService.findCapsuleArrivalById(capsaId);
        log.info("The method findCapsuleArrivalById is being executed");
        //return null;
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Capsule Arrival found successfully ");
        map.put("data", capsaDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findCapsuleArrivalofArticleinSicaps(Long artId, Long sicapsId) {
        Map<String, Object> map = new LinkedHashMap<>();
        CapsuleArrivalDto capsaDtoFound = capsuleArrivalService.findCapsuleArrivalofArticleinSicaps(artId, sicapsId);
        log.info("The method findCapsuleArrivalofArticleinSicaps is being executed");
        //return null;
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Capsule Arrival found successfully ");
        map.put("data", capsaDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCapsuleArrivalinSicaps(Long sicapsId) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CapsuleArrivalDto> capsuleArrivalDtoList = capsuleArrivalService.findAllCapsuleArrivalinSicaps(sicapsId);
        log.info("The method findAllcapsuleArrivalinSicaps is being executed");
        //return null;
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Capsule Arrival list found successfully ");
        map.put("data", capsuleArrivalDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCapsuleArrivalinSicaps(Long sicapsId, Optional<Integer> optpagenum,
                                                         Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Page<CapsuleArrivalDto> capsuleArrivalDtoPage = capsuleArrivalService.findPageCapsuleArrivalinSicaps(sicapsId,
                pagenum, pagesize);
        log.info("The method findPageCapsuleArrivalinSicaps is being executed");
        //return null;
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Capsule Arrival page found successfully ");
        map.put("data", capsuleArrivalDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCapsuleArrivalinSicapsBetween(Long sicapsId, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CapsuleArrivalDto> capsuleArrivalDtoList = capsuleArrivalService.findAllCapsuleArrivalinSicapsBetween(
                sicapsId, startDate, endDate);
        log.info("The method findAllCapsuleArrivalinSicapsBetween is being executed");
        //return null;
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Capsule Arrival list found successfully ");
        map.put("data", capsuleArrivalDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCapsuleArrivalinSicapsBetween(Long sicapsId, Instant startDate, Instant endDate,
                                                                Optional<Integer> optpagenum,
                                                                Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Page<CapsuleArrivalDto> capsuleArrivalDtoPage = capsuleArrivalService.findPageCapsuleArrivalinSicapsBetween(
                sicapsId, startDate, endDate, pagenum, pagesize);
        log.info("The method findPageCapsuleArrivalinSicapsBetween is being executed");
        //return null;
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Capsule Arrival page found successfully ");
        map.put("data", capsuleArrivalDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCapsuleArrivalinPosBetween(Long posId, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CapsuleArrivalDto> capsuleArrivalDtoList = capsuleArrivalService.findAllCapsuleArrivalinPosBetween(
                posId, startDate, endDate);
        log.info("The method findAllCapsuleArrivalinPosBetween is being executed");
        //return null;
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Capsule Arrival list found successfully ");
        map.put("data", capsuleArrivalDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCapsuleArrivalinPosBetween(Long posId, Instant startDate, Instant endDate,
                                                             Optional<Integer> optpagenum,
                                                             Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Page<CapsuleArrivalDto> capsuleArrivalDtoPage = capsuleArrivalService.findPageCapsuleArrivalinPosBetween(
                posId, startDate, endDate, pagenum, pagesize);
        log.info("The method findPageCapsuleArrivalinPosBetween is being executed");
        //return null;
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Capsule Arrival page found successfully ");
        map.put("data", capsuleArrivalDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }
}
