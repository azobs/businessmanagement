package com.c2psi.businessmanagement.controllers.apiImpl.pos.pos;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.controllers.api.pos.pos.PosDamageAccountApi;
import com.c2psi.businessmanagement.dtos.pos.pos.PosDamageAccountDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosDamageOperationDto;
import com.c2psi.businessmanagement.services.contracts.pos.pos.PosDamageAccountService;
import com.c2psi.businessmanagement.services.contracts.pos.pos.PosDamageOperationService;
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
public class PosDamageAccountApiImpl implements PosDamageAccountApi {

    private PosDamageAccountService posDamageAccountService;
    private PosDamageOperationService posDamageOperationService;

    @Autowired
    public PosDamageAccountApiImpl(PosDamageAccountService posDamageAccountService,
                                   PosDamageOperationService posDamageOperationService) {
        this.posDamageAccountService = posDamageAccountService;
        this.posDamageOperationService = posDamageOperationService;
    }

    @Override
    public ResponseEntity savePosDamageAccount(PosDamageAccountDto pdamaccDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", pdamaccDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        PosDamageAccountDto pdamaccDtoSaved = posDamageAccountService.savePosDamageAccount(pdamaccDto);
        log.info("The method savePosDamageAccount is being executed");
        //return new ResponseEntity(pdamaccDtoSaved, HttpStatus.CREATED);
        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "Pos damage account created successfully");
        map.put("data", pdamaccDtoSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity findPosDamageAccountById(Long pdamaccId) {
        Map<String, Object> map = new LinkedHashMap<>();
        PosDamageAccountDto pdamaccDtoFound = posDamageAccountService.findPosDamageAccountById(pdamaccId);
        log.info("The method findPosDamageAccountById is being executed");
        //return ResponseEntity.ok(pdamaccDtoFound);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos damage account found successfully");
        map.put("data", pdamaccDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPosDamageAccountofArticleInPos(Long artId, Long posId) {
        Map<String, Object> map = new LinkedHashMap<>();
        PosDamageAccountDto pdamaccDtoFound = posDamageAccountService.findPosDamageAccountofArticleInPos(artId, posId);
        log.info("The method findPosDamageAccountofArticleInPos is being executed");
        //return ResponseEntity.ok(pdamaccDtoFound);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos damage account found successfully");
        map.put("data", pdamaccDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllDamageAccountInPos(Long posId) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<PosDamageAccountDto> pdamaccDtoList = posDamageAccountService.findAllDamageAccountInPos(posId);
        log.info("The method findAllDamageAccountInPos is being executed");
        //return ResponseEntity.ok(pdamaccDtoList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos damage account list found successfully");
        map.put("data", pdamaccDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageDamageAccountInPos(Long posId, Optional<Integer> optpagenum,
                                                     Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();

        Page<PosDamageAccountDto> pdamaccDtoPage = posDamageAccountService.findPageDamageAccountInPos(posId,
                pagenum, pagesize);
        log.info("The method findPageDamageAccountInPos is being executed");
        //return ResponseEntity.ok(pdamaccDtoPage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos damage account page found successfully");
        map.put("data", pdamaccDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllDamageAccountofArticle(Long artId) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<PosDamageAccountDto> pdamaccDtoList = posDamageAccountService.findAllDamageAccountofArticle(artId);
        log.info("The method findAllDamageAccountofArticle is being executed");
        //return ResponseEntity.ok(pdamaccDtoList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos damage account list found successfully");
        map.put("data", pdamaccDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageDamageAccountofArticle(Long artId, Optional<Integer> optpagenum,
                                                         Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();

        Page<PosDamageAccountDto> pdamaccDtoPage = posDamageAccountService.findPageDamageAccountofArticle(artId,
                pagenum, pagesize);
        log.info("The method findPageDamageAccountofArticle is being executed");
        //return ResponseEntity.ok(pdamaccDtoPage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos damage account page found successfully");
        map.put("data", pdamaccDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deletePosDamageAccountById(Long pdamaccId) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean delete = posDamageAccountService.deletePosDamageAccountById(pdamaccId);
        log.info("The method deletePosDamageAccountById is being executed");
        //return ResponseEntity.ok(delete);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos damage account page deleted successfully");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity saveDamageOperation(PosDamageAccountDto posdamaccDto, BindingResult bindingResult1,
                                              PosDamageOperationDto posdamopDto, BindingResult bindingResult2) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult1.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", posdamaccDto, bindingResult1);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult1);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        if (bindingResult2.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", posdamopDto, bindingResult2);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult2);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }
        Boolean opSaved = posDamageAccountService.saveDamageOperation(posdamaccDto, posdamopDto);
        log.info("The method saveDamageOperation is being executed");
        //return ResponseEntity.ok(saveOp);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos damage account operation saved successfully");
        map.put("data", opSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity updatePosDamageOperation(PosDamageOperationDto pdamopDto, BindingResult bindingResult) {

        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", pdamopDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        PosDamageOperationDto pdamopDtoUpdated = posDamageOperationService.updatePosDamageOperation(pdamopDto);
        log.info("The method updatePosDamageOperation is being executed");
        //return new ResponseEntity(pdamopDtoUpdated, HttpStatus.OK);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos damage account operation updated successfully");
        map.put("data", pdamopDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deletePosDamageOperationById(Long pdamopId) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean delete = posDamageOperationService.deletePosDamageOperationById(pdamopId);
        log.info("The method deletePosDamageOperationById is being executed");
        //return ResponseEntity.ok(delete);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos damage account operation deleted successfully");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllPosDamageOperation(Long pdamopId) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<PosDamageOperationDto> posdamopList = posDamageOperationService.findAllPosDamageOperation(pdamopId);
        log.info("The method findAllPosDamageOperation is being executed");
        //return ResponseEntity.ok(posdamopList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos damage account operation list found successfully");
        map.put("data", posdamopList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllPosDamageOperationofType(Long pdamopId, OperationType optype) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<PosDamageOperationDto> posdamopList = posDamageOperationService.findAllPosDamageOperationofType(pdamopId,
                optype);
        log.info("The method findAllPosDamageOperationofType is being executed");
        //return ResponseEntity.ok(posdamopList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos damage account operation list found successfully");
        map.put("data", posdamopList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPagePosDamageOperation(Long pdamopId, Optional<Integer> optpagenum,
                                                     Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();

        Page<PosDamageOperationDto> posdamopPage = posDamageOperationService.findPagePosDamageOperation(pdamopId,
                pagenum, pagesize);
        log.info("The method findPagePosDamageOperation is being executed");
        //return ResponseEntity.ok(posdamopPage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos damage account operation page found successfully");
        map.put("data", posdamopPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPagePosDamageOperationofType(Long pdamopId, OperationType optype,
                                                           Optional<Integer> optpagenum,
                                                           Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();

        Page<PosDamageOperationDto> posdamopPage = posDamageOperationService.findPagePosDamageOperationofType(pdamopId,
                optype, pagenum, pagesize);
        log.info("The method findPagePosDamageOperationofType is being executed");
        //return ResponseEntity.ok(posdamopPage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos damage account operation page found successfully");
        map.put("data", posdamopPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllPosDamageOperationBetween(Long pdamopId, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<PosDamageOperationDto> posdamOpListBetween = posDamageOperationService.findAllPosDamageOperationBetween(
                pdamopId, startDate, endDate);
        log.info("The method findAllPosDamageOperationBetween is being executed");
        //return ResponseEntity.ok(posdamOpListBetween);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos damage account operation list found successfully");
        map.put("data", posdamOpListBetween);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPagePosDamageOperationBetween(Long pdamopId, Instant startDate, Instant endDate,
                                                            Optional<Integer> optpagenum,
                                                            Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();

        Page<PosDamageOperationDto> posdamOpPageBetween = posDamageOperationService.findPagePosDamageOperationBetween(
                pdamopId, startDate, endDate, pagenum, pagesize);
        log.info("The method findPagePosDamageOperationBetween is being executed");
        //return ResponseEntity.ok(posdamOpPageBetween);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos damage account operation page found successfully");
        map.put("data", posdamOpPageBetween);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllPosDamageOperationofTypeBetween(Long pdamopId, OperationType optype, Instant startDate,
                                                          Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();

        List<PosDamageOperationDto> posdamOpListBetween = posDamageOperationService.findAllPosDamageOperationofTypeBetween(
                pdamopId, optype, startDate, endDate);
        log.info("The method findAllPosDamageOperationofTypeBetween is being executed");
        //return ResponseEntity.ok(posdamOpListBetween);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos damage account operation list found successfully");
        map.put("data", posdamOpListBetween);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPagePosDamageOperationofTypeBetween(Long pdamopId, OperationType optype, Instant startDate,
                                                           Instant endDate, Optional<Integer> optpagenum,
                                                           Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();

        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Page<PosDamageOperationDto> posdamOpPageBetween = posDamageOperationService.findPagePosDamageOperationofTypeBetween(
                pdamopId, optype, startDate, endDate, pagenum, pagesize);
        log.info("The method findPagePosDamageOperationofTypeBetween is being executed");
        //return ResponseEntity.ok(posdamOpPageBetween);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos damage account operation list found successfully");
        map.put("data", posdamOpPageBetween);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }
}
