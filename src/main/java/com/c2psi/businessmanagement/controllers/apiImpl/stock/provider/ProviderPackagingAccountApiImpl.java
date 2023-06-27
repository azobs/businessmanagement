package com.c2psi.businessmanagement.controllers.apiImpl.stock.provider;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.controllers.api.stock.provider.ProviderPackagingAccountApi;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderPackagingAccountDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderPackagingOperationDto;
import com.c2psi.businessmanagement.services.contracts.stock.provider.ProviderPackagingAccountService;
import com.c2psi.businessmanagement.services.contracts.stock.provider.ProviderPackagingOperationService;
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
public class ProviderPackagingAccountApiImpl implements ProviderPackagingAccountApi {

    private ProviderPackagingAccountService providerPackagingAccountService;
    private ProviderPackagingOperationService providerPackagingOperationService;

    @Autowired
    public ProviderPackagingAccountApiImpl(ProviderPackagingAccountService providerPackagingAccountService,
                                           ProviderPackagingOperationService providerPackagingOperationService) {
        this.providerPackagingAccountService = providerPackagingAccountService;
        this.providerPackagingOperationService = providerPackagingOperationService;
    }

    @Override
    public ResponseEntity saveProviderPackagingAccount(ProviderPackagingAccountDto propackaccDto,
                                                       BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", propackaccDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        ProviderPackagingAccountDto propackaccDtoSaved = providerPackagingAccountService.
                saveProviderPackagingAccount(propackaccDto);
        log.info("The method saveProviderPackagingAccount is being executed");
        //return new ResponseEntity(propackaccDtoSaved, HttpStatus.CREATED);
        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "Provider packaging account created successfully ");
        map.put("data", propackaccDtoSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity findProviderPackagingAccountById(Long propackaccId) {
        Map<String, Object> map = new LinkedHashMap<>();
        ProviderPackagingAccountDto propackaccDtoFound = providerPackagingAccountService.
                findProviderPackagingAccountById(propackaccId);
        log.info("The method findProviderPackagingAccountById is being executed");
        //return ResponseEntity.ok(propackaccDtoFound);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider packaging account found successfully ");
        map.put("data", propackaccDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findProviderPackagingAccount(Long packagingId, Long providerId) {
        Map<String, Object> map = new LinkedHashMap<>();
        ProviderPackagingAccountDto propackaccDtoFound = providerPackagingAccountService.
                findProviderPackagingAccount(packagingId, providerId);
        log.info("The method findProviderPackagingAccount is being executed");
        //return ResponseEntity.ok(propackaccDtoFound);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider packaging account found successfully ");
        map.put("data", propackaccDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllProviderPackagingAccountinPos(Long providerId) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<ProviderPackagingAccountDto> proPackAccDtoList = providerPackagingAccountService.
                findAllPackagingAccountofProvider(providerId);
        log.info("The method findAllProviderPackagingAccountinPos is being executed");
        //return ResponseEntity.ok(proPackAccDtoList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider packaging account list found successfully ");
        map.put("data", proPackAccDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageProviderPackagingAccountinPos(Long providerId, Optional<Integer> optpagenum,
                                                                Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();

        Page<ProviderPackagingAccountDto> proPackAccDtoPage = providerPackagingAccountService.
                findPagePackagingAccountofProvider(providerId, pagenum, pagesize);
        log.info("The method findPageProviderPackagingAccountinPos is being executed");
        //return ResponseEntity.ok(proPackAccDtoPage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider packaging account page found successfully ");
        map.put("data", proPackAccDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteProviderPackagingAccountById(Long propackId) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean delete = providerPackagingAccountService.deleteProviderPackagingAccountById(propackId);
        log.info("The method deleteProviderPackagingAccountById is being executed");
        //return ResponseEntity.ok(delete);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider packaging account deleted successfully ");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity savePackagingOperation(ProviderPackagingAccountDto propackaccDto, BindingResult bindingResult1,
                                                 ProviderPackagingOperationDto propackopDto, BindingResult bindingResult2) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult1.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", propackaccDto, bindingResult1);
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
                    "and the report errors are {}", propackopDto, bindingResult2);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult2);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        Boolean opSaved = providerPackagingAccountService.savePackagingOperationforProvider(propackaccDto, propackopDto);
        log.info("The method savePackagingOperation is being executed");
        //return ResponseEntity.ok(opSaved);
        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "Provider packaging operation created successfully ");
        map.put("data", opSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateProviderPackagingOperation(ProviderPackagingOperationDto propackopDto,
                                                           BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", propackopDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        ProviderPackagingOperationDto propackopDtoUpdated = providerPackagingOperationService.
                updateProviderPackagingOperation(propackopDto);
        log.info("The method updateProviderPackagingOperation is being executed");
        //return new ResponseEntity(propackopDtoUpdated, HttpStatus.OK);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider packaging operation updated successfully ");
        map.put("data", propackopDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteProviderPackagingOperationById(Long propackopId) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean delete = providerPackagingOperationService.deleteProviderPackagingOperationById(propackopId);
        log.info("The method deleteProviderPackagingOperationById is being executed");
        //return ResponseEntity.ok(delete);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider packaging operation deleted successfully ");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllProviderPackagingOperation(Long propackaccId) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<ProviderPackagingOperationDto> proPackOpDtoList = providerPackagingOperationService.
                findAllProviderPackagingOperation(propackaccId);
        log.info("The method findAllProviderPackagingOperation is being executed");
        //return ResponseEntity.ok(proPackOpDtoList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider packaging operation list successfully ");
        map.put("data", proPackOpDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageProviderPackagingOperation(Long propackaccId, Optional<Integer> optpagenum,
                                                             Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();

        Page<ProviderPackagingOperationDto> proPackOpDtoPage = providerPackagingOperationService.
                findPageProviderPackagingOperation(propackaccId, pagenum, pagesize);
        log.info("The method findPageProviderPackagingOperation is being executed");
        //return ResponseEntity.ok(proPackOpDtoPage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider packaging operation page successfully ");
        map.put("data", proPackOpDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllProviderPackagingOperationofType(Long propackaccId, OperationType opType) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<ProviderPackagingOperationDto> proPackOpDtoList = providerPackagingOperationService.
                findAllProviderPackagingOperationofType(propackaccId, opType);
        log.info("The method findAllProviderPackagingOperationofType is being executed");
        //return ResponseEntity.ok(proPackOpDtoList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider packaging operation list successfully ");
        map.put("data", proPackOpDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageProviderPackagingOperationofType(Long propackaccId, OperationType opType,
                                                                   Optional<Integer> optpagenum,
                                                                   Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();

        Page<ProviderPackagingOperationDto> proPackOpDtoPage = providerPackagingOperationService.
                findPageProviderPackagingOperationofType(propackaccId, opType, pagenum, pagesize);
        log.info("The method findPageProviderPackagingOperationofType is being executed");
        //return ResponseEntity.ok(proPackOpDtoPage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider packaging operation page successfully ");
        map.put("data", proPackOpDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllProviderPackagingOperationBetween(Long propackaccId, Instant startDate,
                                                                   Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<ProviderPackagingOperationDto> proPackOpDtoList = providerPackagingOperationService.
                findAllProviderPackagingOperationBetween(propackaccId, startDate, endDate);
        log.info("The method findAllProviderPackagingOperationBetween is being executed");
        //return ResponseEntity.ok(proPackOpDtoList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider packaging operation list successfully ");
        map.put("data", proPackOpDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageProviderPackagingOperationBetween(Long propackaccId, Instant startDate,
                                                                    Instant endDate, Optional<Integer> optpagenum,
                                                                    Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();

        Page<ProviderPackagingOperationDto> proPackOpDtoPage = providerPackagingOperationService.
                findPageProviderPackagingOperationBetween(propackaccId, startDate, endDate, pagenum, pagesize);
        log.info("The method findPageProviderPackagingOperationofType is being executed");
        //return ResponseEntity.ok(proPackOpDtoPage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider packaging operation page successfully ");
        map.put("data", proPackOpDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllProviderPackagingOperationofTypeBetween(Long propackaccId, OperationType opType,
                                                                         Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<ProviderPackagingOperationDto> proPackOpDtoList = providerPackagingOperationService.
                findAllProviderPackagingOperationofTypeBetween(propackaccId, opType, startDate, endDate);
        log.info("The method findAllProviderPackagingOperationBetween is being executed");
        //return ResponseEntity.ok(proPackOpDtoList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider packaging operation list successfully ");
        map.put("data", proPackOpDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageProviderPackagingOperationofTypeBetween(Long propackaccId, OperationType opType,
                                                                          Instant startDate, Instant endDate,
                                                                          Optional<Integer> optpagenum,
                                                                          Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();

        Page<ProviderPackagingOperationDto> proPackOpDtoPage = providerPackagingOperationService.
                findPageProviderPackagingOperationofTypeBetween(propackaccId, opType, startDate, endDate, pagenum, pagesize);
        log.info("The method findPageProviderPackagingOperationofTypeBetween is being executed");
        //return ResponseEntity.ok(proPackOpDtoPage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider packaging operation page successfully ");
        map.put("data", proPackOpDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }
}
