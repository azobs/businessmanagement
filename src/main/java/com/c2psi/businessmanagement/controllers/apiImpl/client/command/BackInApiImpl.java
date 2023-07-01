package com.c2psi.businessmanagement.controllers.apiImpl.client.command;

import com.c2psi.businessmanagement.controllers.api.client.command.BackInApi;
import com.c2psi.businessmanagement.dtos.client.command.BackInDetailsDto;
import com.c2psi.businessmanagement.dtos.client.command.BackInDto;
import com.c2psi.businessmanagement.services.contracts.client.command.BackInDetailsService;
import com.c2psi.businessmanagement.services.contracts.client.command.BackInService;
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
public class BackInApiImpl implements BackInApi {
    private BackInService backInService;
    private BackInDetailsService backInDetailsService;

    @Autowired
    public BackInApiImpl(BackInService backInService, BackInDetailsService backInDetailsService) {
        this.backInService = backInService;
        this.backInDetailsService = backInDetailsService;
    }

    @Override
    public ResponseEntity saveBackIn(BackInDto backInDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", backInDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        BackInDto backInDtoSaved = backInService.saveBackIn(backInDto);
        log.info("The method saveBackIn is being executed ");

        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "BackIn created successfully ");
        map.put("data", backInDtoSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateBackIn(BackInDto backInDto, BindingResult bindingResult) {

        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", backInDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        BackInDto backInDtoUpdated = backInService.updateBackIn(backInDto);
        log.info("The method saveBackIn is being executed ");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "BackIn updated successfully ");
        map.put("data", backInDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findBackInById(Long backinId) {
        Map<String, Object> map = new LinkedHashMap<>();

        BackInDto backInDtoFound = backInService.findBackInById(backinId);
        log.info("The method findBackInById is being executed ");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "BackIn found successfully ");
        map.put("data", backInDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findBackInofCommand(Long cmdId) {

        Map<String, Object> map = new LinkedHashMap<>();

        BackInDto backInDtoFound = backInService.findBackInofCommand(cmdId);
        log.info("The method findBackInofCommand is being executed ");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "BackIn found successfully ");
        map.put("data", backInDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllBackIninPosBetween(Long posId, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();

        List<BackInDto> backInDtoList = backInService.findAllBackIninPosBetween(posId, startDate, endDate);
        log.info("The method findAllBackIninPosBetween is being executed ");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "BackIn list in pos between found successfully ");
        map.put("data", backInDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageBackIninPosBetween(Long posId, Instant startDate, Instant endDate,
                                                     Optional<Integer> optpagenum, Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Page<BackInDto> backInDtoPage = backInService.findPageBackIninPosBetween(posId, startDate, endDate,
                pagenum, pagesize);
        log.info("The method findPageBackIninPosBetween is being executed ");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "BackIn page in pos between found successfully ");
        map.put("data", backInDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteBackInById(Long backinId) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean delete = backInService.deleteBackInById(backinId);
        log.info("The method deleteBackInById is being executed ");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "BackIn deleted successfully ");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity saveBackInDetails(BackInDetailsDto backInDetailsDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", backInDetailsDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        BackInDetailsDto backInDetailsDtoSaved = backInDetailsService.saveBackInDetails(backInDetailsDto);
        log.info("The method saveBackInDetails is being executed ");

        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "BackInDetails created successfully ");
        map.put("data", backInDetailsDtoSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateBackInDetails(BackInDetailsDto backInDetailsDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", backInDetailsDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        BackInDetailsDto backInDetailsDtoUpdated = backInDetailsService.updateBackInDetails(backInDetailsDto);
        log.info("The method saveBackInDetails is being executed ");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "BackInDetails updated successfully ");
        map.put("data", backInDetailsDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findBackInDetailsById(Long backInDetailsId) {
        Map<String, Object> map = new LinkedHashMap<>();
        BackInDetailsDto backInDetailsDtoFound = backInDetailsService.findBackInDetailsById(backInDetailsId);
        log.info("The method findBackInDetailsById is being executed ");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "BackInDetails found successfully ");
        map.put("data", backInDetailsDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findBackInDetailsofArticleinBackIn(Long artId, Long backinId) {
        Map<String, Object> map = new LinkedHashMap<>();
        BackInDetailsDto backInDetailsDtoFound = backInDetailsService.findBackInDetailsofArticleinBackIn(artId, backinId);
        log.info("The method findBackInDetailsofArticleinBackIn is being executed ");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "BackInDetails found successfully ");
        map.put("data", backInDetailsDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllBackInDetailsofBackIn(Long backInId) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<BackInDetailsDto> backInDetailsDtoList = backInDetailsService.findAllBackInDetailsofBackIn(backInId);
        log.info("The method findAllBackInDetailsofBackIn is being executed ");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "BackInDetails list found successfully ");
        map.put("data", backInDetailsDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageBackInDetailsofBackIn(Long backInId, Optional<Integer> optpagenum,
                                                        Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Page<BackInDetailsDto> backInDetailsDtoPage = backInDetailsService.findPageBackInDetailsofBackIn(backInId,
                pagenum, pagesize);
        log.info("The method findPageBackIninPosBetween is being executed ");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "BackInDetails page in pos between found successfully ");
        map.put("data", backInDetailsDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteBackInDetailsById(Long backInDetailsId) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean delete = backInDetailsService.deleteBackInDetailsById(backInDetailsId);
        log.info("The method deleteBackInDetailsById is being executed ");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "BackInDetails deleted successfully ");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }
}
