package com.c2psi.businessmanagement.controllers.apiImpl.pos.pos;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.controllers.api.pos.pos.PosPackagingAccountApi;
import com.c2psi.businessmanagement.dtos.pos.pos.PosPackagingAccountDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosPackagingOperationDto;
import com.c2psi.businessmanagement.services.contracts.pos.pos.PosPackagingAccountService;
import com.c2psi.businessmanagement.services.contracts.pos.pos.PosPackagingOperationService;
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
public class PosPackagingAccountApiImpl implements PosPackagingAccountApi {

    private PosPackagingAccountService posPackagingAccountService;
    private PosPackagingOperationService posPackagingOperationService;

    @Autowired
    public PosPackagingAccountApiImpl(PosPackagingAccountService posPackagingAccountService,
                                      PosPackagingOperationService posPackagingOperationService) {
        this.posPackagingAccountService = posPackagingAccountService;
        this.posPackagingOperationService = posPackagingOperationService;
    }

    @Override
    public ResponseEntity savePosPackagingAccount(PosPackagingAccountDto pospackaccDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", pospackaccDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }


        PosPackagingAccountDto pospackaccDtoSaved = posPackagingAccountService.savePosPackagingAccount(pospackaccDto);
        log.info("The method savePosPackagingAccount is being executed");
        //return new ResponseEntity(pospackaccDtoSaved, HttpStatus.CREATED);
        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "Pos packaging account created successfully");
        map.put("data", pospackaccDtoSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity findPosPackagingAccountById(Long pospackaccId) {
        Map<String, Object> map = new LinkedHashMap<>();
        PosPackagingAccountDto pospackaccDtoFound = posPackagingAccountService.findPosPackagingAccountById(pospackaccId);
        log.info("The method findPosPackagingAccountById is being executed");
        //return ResponseEntity.ok(pospackaccDtoFound);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos packaging account found successfully");
        map.put("data", pospackaccDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPosPackagingAccountInPos(Long packagingId, Long posId) {
        Map<String, Object> map = new LinkedHashMap<>();

        PosPackagingAccountDto pospackaccDtoFound = posPackagingAccountService.
                findPosPackagingAccountInPos(packagingId, posId);
        log.info("The method findPosPackagingAccount is being executed");
        //return ResponseEntity.ok(pospackaccDtoFound);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos packaging account found successfully");
        map.put("data", pospackaccDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllPosPackagingAccountinPos(Long posId) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<PosPackagingAccountDto> posPackAccDtoList = posPackagingAccountService.findAllPackagingAccountInPos(posId);
        log.info("The method findAllPosPackagingAccountinPos is being executed");
        //return ResponseEntity.ok(posPackAccDtoList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos packaging account list found successfully");
        map.put("data", posPackAccDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPagePosPackagingAccountinPos(Long posId, Optional<Integer> optpagenum,
                                                           Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();

        Page<PosPackagingAccountDto> posPackAccDtoPage = posPackagingAccountService.
                findPagePackagingAccountInPos(posId, pagenum, pagesize);
        log.info("The method findPagePosPackagingAccountinPos is being executed");
        //return ResponseEntity.ok(posPackAccDtoPage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos packaging account page found successfully");
        map.put("data", posPackAccDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deletePosPackagingAccountById(Long pospackId) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean delete = posPackagingAccountService.deletePosPackagingAccountById(pospackId);
        log.info("The method deletePosPackagingAccountById is being executed");
        //return ResponseEntity.ok(delete);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos packaging account deleted successfully");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity savePackagingOperation(PosPackagingAccountDto pospackaccDto, BindingResult bindingResult1,
                                                 PosPackagingOperationDto pospackopDto, BindingResult bindingResult2) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult1.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", pospackaccDto, bindingResult1);
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
                    "and the report errors are {}", pospackaccDto, bindingResult2);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult2);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        Boolean opSaved = posPackagingAccountService.savePackagingOperation(pospackaccDto, pospackopDto);
        log.info("The method savePackagingOperation is being executed");
        //return ResponseEntity.ok(opSaved);
        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "Pos packaging account operation saved successfully");
        map.put("data", opSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updatePosPackagingOperation(PosPackagingOperationDto pospackopDto,
                                                      BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", pospackopDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        PosPackagingOperationDto pospackopDtoUpdated = posPackagingOperationService.
                updatePosPackagingOperation(pospackopDto);
        log.info("The method updatePosPackagingOperation is being executed");
        //return new ResponseEntity(pospackopDtoUpdated, HttpStatus.OK);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos packaging account operation updated successfully");
        map.put("data", pospackopDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deletePosPackagingOperationById(Long pospackopId) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean delete = posPackagingOperationService.deletePosPackagingOperationById(pospackopId);
        log.info("The method deletePosPackagingOperationById is being executed");
        //return ResponseEntity.ok(delete);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos packaging account operation deleted successfully");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllPosPackagingOperation(Long pospackaccId) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<PosPackagingOperationDto> posPackOpDtoList = posPackagingOperationService.
                findAllPosPackagingOperation(pospackaccId);
        log.info("The method findAllPosPackagingOperation is being executed");
        //return ResponseEntity.ok(posPackOpDtoList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos packaging account operation list found successfully");
        map.put("data", posPackOpDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPagePosPackagingOperation(Long pospackaccId, Optional<Integer> optpagenum,
                                                        Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();

        Page<PosPackagingOperationDto> posPackOpDtoPage = posPackagingOperationService.
                findPagePosPackagingOperation(pospackaccId, pagenum, pagesize);
        log.info("The method findPagePosPackagingOperation is being executed");
        //return ResponseEntity.ok(posPackOpDtoPage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos packaging account operation page found successfully");
        map.put("data", posPackOpDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllPosPackagingOperationofType(Long pospackaccId, OperationType opType) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<PosPackagingOperationDto> posPackOpDtoList = posPackagingOperationService.
                findAllPosPackagingOperationofType(pospackaccId, opType);
        log.info("The method findAllPosPackagingOperationofType is being executed");
        //return ResponseEntity.ok(posPackOpDtoList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos packaging account operation list found successfully");
        map.put("data", posPackOpDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPagePosPackagingOperationofType(Long pospackaccId, OperationType opType,
                                                              Optional<Integer> optpagenum,
                                                              Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();

        Page<PosPackagingOperationDto> posPackOpDtoPage = posPackagingOperationService.
                findPagePosPackagingOperationofType(pospackaccId, opType, pagenum, pagesize);
        log.info("The method findPagePosPackagingOperationofType is being executed");
        //return ResponseEntity.ok(posPackOpDtoPage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos packaging account operation page found successfully");
        map.put("data", posPackOpDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllPosPackagingOperationBetween(Long pospackaccId, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<PosPackagingOperationDto> posPackOpDtoList = posPackagingOperationService.
                findAllPosPackagingOperationBetween(pospackaccId, startDate, endDate);
        log.info("The method findAllPosPackagingOperationBetween is being executed");
        //return ResponseEntity.ok(posPackOpDtoList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos packaging account operation list found successfully");
        map.put("data", posPackOpDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPagePosPackagingOperationBetween(Long pospackaccId, Instant startDate, Instant endDate,
                                                               Optional<Integer> optpagenum,
                                                               Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();

        Page<PosPackagingOperationDto> posPackOpDtoPage = posPackagingOperationService.
                findPagePosPackagingOperationBetween(pospackaccId, startDate, endDate, pagenum, pagesize);
        log.info("The method findPagePosPackagingOperationBetween is being executed");
        //return ResponseEntity.ok(posPackOpDtoPage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos packaging account operation page found successfully");
        map.put("data", posPackOpDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllPosPackagingOperationofTypeBetween(Long pospackaccId, OperationType opType,
                                                                    Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<PosPackagingOperationDto> posPackOpDtoList = posPackagingOperationService.
                findAllPosPackagingOperationofTypeBetween(pospackaccId, opType, startDate, endDate);
        log.info("The method findAllPosPackagingOperationofTypeBetween is being executed");
        //return ResponseEntity.ok(posPackOpDtoList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos packaging account operation list found successfully");
        map.put("data", posPackOpDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPagePosPackagingOperationofTypeBetween(Long pospackaccId, OperationType opType,
                                                                     Instant startDate, Instant endDate,
                                                                     Optional<Integer> optpagenum,
                                                                     Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();

        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Page<PosPackagingOperationDto> posPackOpDtoPage = posPackagingOperationService.
                findPagePosPackagingOperationofTypeBetween(pospackaccId, opType, startDate, endDate, pagenum, pagesize);
        log.info("The method findPagePosPackagingOperationofTypeBetween is being executed");
        //return ResponseEntity.ok(posPackOpDtoPage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos packaging account operation page found successfully");
        map.put("data", posPackOpDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }
}
