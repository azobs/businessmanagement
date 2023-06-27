package com.c2psi.businessmanagement.controllers.apiImpl.stock.provider;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.controllers.api.stock.provider.ProviderDamageAccountApi;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDamageAccountDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDamageOperationDto;
import com.c2psi.businessmanagement.services.contracts.stock.provider.ProviderDamageAccountService;
import com.c2psi.businessmanagement.services.contracts.stock.provider.ProviderDamageOperationService;
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
public class ProviderDamageAccountApiImpl implements ProviderDamageAccountApi {
    private ProviderDamageAccountService providerDamageAccountService;
    private ProviderDamageOperationService providerDamageOperationService;

    @Autowired
    public ProviderDamageAccountApiImpl(ProviderDamageAccountService providerDamageAccountService,
                                        ProviderDamageOperationService providerDamageOperationService) {
        this.providerDamageAccountService = providerDamageAccountService;
        this.providerDamageOperationService = providerDamageOperationService;
    }

    @Override
    public ResponseEntity saveProviderDamageAccount(ProviderDamageAccountDto prodamaccDto,
                                                    BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", prodamaccDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        ProviderDamageAccountDto prodamaccDtoSaved = providerDamageAccountService.saveProviderDamageAccount(prodamaccDto);
        log.info("The method saveProviderDamageAccount is being executed");
        //return new ResponseEntity(prodamaccDtoSaved, HttpStatus.CREATED);
        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "Provider damage account created successfully ");
        map.put("data", prodamaccDtoSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity findProviderDamageAccountById(Long prodamaccId) {
        Map<String, Object> map = new LinkedHashMap<>();
        ProviderDamageAccountDto prodamaccDtoFound = providerDamageAccountService.findProviderDamageAccountById(prodamaccId);
        log.info("The method findProviderDamageAccountById is being executed");
        //return ResponseEntity.ok(prodamaccDtoFound);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider damage account found successfully ");
        map.put("data", prodamaccDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findProviderDamageAccountofArticleinPos(Long providerId, Long artId) {
        Map<String, Object> map = new LinkedHashMap<>();
        ProviderDamageAccountDto prodamaccDtoFound = providerDamageAccountService.
                findProviderDamageAccountofArticleinPos(providerId, artId);
        log.info("The method findProviderDamageAccountofArticleinPos is being executed");
        //return ResponseEntity.ok(prodamaccDtoFound);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider damage account found successfully ");
        map.put("data", prodamaccDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllProviderDamageAccountinPos(Long providerId) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<ProviderDamageAccountDto> prodamaccDtoFoundList = providerDamageAccountService.
                findAllProviderDamageAccountinPos(providerId);
        log.info("The method findAllProviderDamageAccountinPos is being executed");
        //return ResponseEntity.ok(prodamaccDtoFoundList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider damage account list found successfully ");
        map.put("data", prodamaccDtoFoundList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageProviderDamageAccountinPos(Long providerId, Optional<Integer> optpagenum,
                                                             Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();

        Page<ProviderDamageAccountDto> prodamaccDtoFoundPage = providerDamageAccountService.
                findPageProviderDamageAccountinPos(providerId, pagenum, pagesize);
        log.info("The method findAllProviderDamageAccountinPos is being executed");
        //return ResponseEntity.ok(prodamaccDtoFoundPage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider damage account page found successfully ");
        map.put("data", prodamaccDtoFoundPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity saveDamageOperation(ProviderDamageAccountDto prodamaccDto, BindingResult bindingResult1,
                                              ProviderDamageOperationDto prodamopDto, BindingResult bindingResult2) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult1.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", prodamaccDto, bindingResult1);
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
                    "and the report errors are {}", prodamopDto, bindingResult2);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult2);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        Boolean opSaved = providerDamageAccountService.saveDamageOperation(prodamaccDto, prodamopDto);
        log.info("The method saveDamageOperation is being executed");
        //return ResponseEntity.ok(opSaved);
        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "Provider damage account operation created successfully ");
        map.put("data", opSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateProviderDamageOperation(ProviderDamageOperationDto prodamopDto,
                                                        BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", prodamopDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }
        ProviderDamageOperationDto prodamopDtoUpdated = providerDamageOperationService.
                updateProviderDamageOperation(prodamopDto);
        log.info("The method updateProviderDamageOperation is being executed");
        //return new ResponseEntity(prodamopDtoSaved, HttpStatus.OK);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider damage account operation updated successfully ");
        map.put("data", prodamopDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteProviderDamageOperationById(Long prodamopId) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean delete = providerDamageOperationService.deleteProviderDamageOperationById(prodamopId);
        log.info("The method deleteProviderDamageOperationById is being executed");
        //return ResponseEntity.ok(delete);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider damage account operation deleted successfully ");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllProviderDamageOperation(Long prodamaccId) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<ProviderDamageOperationDto> proDamageOpDtoList = providerDamageOperationService.
                findAllProviderDamageOperation(prodamaccId);
        log.info("The method findAllProviderDamageOperation is being executed");
        //return ResponseEntity.ok(proDamageOpDtoList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider damage account operation list found successfully ");
        map.put("data", proDamageOpDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageProviderDamageOperation(Long prodamaccId, Optional<Integer> optpagenum,
                                                          Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();

        Page<ProviderDamageOperationDto> proDamageOpDtoPage = providerDamageOperationService.
                findPageProviderDamageOperation(prodamaccId, pagenum, pagesize);
        log.info("The method findPageProviderDamageOperation is being executed");
        //return ResponseEntity.ok(proDamageOpDtoPage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider damage account operation page found successfully ");
        map.put("data", proDamageOpDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllProviderDamageOperationofType(Long prodamaccId, OperationType opType) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<ProviderDamageOperationDto> proDamageOpDtoList = providerDamageOperationService.
                findAllProviderDamageOperationofType(prodamaccId, opType);
        log.info("The method findAllProviderDamageOperationofType is being executed");
        //return ResponseEntity.ok(proDamageOpDtoList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider damage account operation list found successfully ");
        map.put("data", proDamageOpDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageProviderDamageOperationofType(Long prodamaccId, OperationType opType,
                                                                Optional<Integer> optpagenum,
                                                                Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();

        Page<ProviderDamageOperationDto> proDamageOpDtoPage = providerDamageOperationService.
                findPageProviderDamageOperationofType(prodamaccId, opType, pagenum, pagesize);
        log.info("The method findPageProviderDamageOperationofType is being executed");
        //return ResponseEntity.ok(proDamageOpDtoPage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider damage account operation page found successfully ");
        map.put("data", proDamageOpDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllProviderDamageOperationBetween(Long prodamaccId, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<ProviderDamageOperationDto> proDamageOpDtoList = providerDamageOperationService.
                findAllProviderDamageOperationBetween(prodamaccId, startDate, endDate);
        log.info("The method findAllProviderDamageOperationBetween is being executed");
        //return ResponseEntity.ok(proDamageOpDtoList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider damage account operation list found successfully ");
        map.put("data", proDamageOpDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageProviderDamageOperationBetween(Long prodamaccId, Instant startDate, Instant endDate,
                                                                 Optional<Integer> optpagenum,
                                                                 Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Page<ProviderDamageOperationDto> proDamageOpDtoPage = providerDamageOperationService.
                findPageProviderDamageOperationBetween(prodamaccId, startDate, endDate, pagenum, pagesize);
        log.info("The method findPageProviderDamageOperationBetween is being executed");
        //return ResponseEntity.ok(proDamageOpDtoPage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider damage account operation page found successfully ");
        map.put("data", proDamageOpDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllProviderDamageOperationofTypeBetween(Long prodamaccId, OperationType opType,
                                                                      Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<ProviderDamageOperationDto> proDamageOpDtoList = providerDamageOperationService.
                findAllProviderDamageOperationofTypeBetween(prodamaccId, opType, startDate, endDate);
        log.info("The method findAllProviderDamageOperationofTypeBetween is being executed");
        //return ResponseEntity.ok(proDamageOpDtoList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider damage account operation list found successfully ");
        map.put("data", proDamageOpDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageProviderDamageOperationofTypeBetween(Long prodamaccId, OperationType opType,
                                                                       Instant startDate, Instant endDate,
                                                                       Optional<Integer> optpagenum, Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();

        Page<ProviderDamageOperationDto> proDamageOpDtoPage = providerDamageOperationService.
                findPageProviderDamageOperationofTypeBetween(prodamaccId, opType, startDate, endDate, pagenum, pagesize);
        log.info("The method findPageProviderDamageOperationBetween is being executed");
        //return ResponseEntity.ok(proDamageOpDtoPage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider damage account operation page found successfully ");
        map.put("data", proDamageOpDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }


}
