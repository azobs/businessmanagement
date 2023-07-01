package com.c2psi.businessmanagement.controllers.apiImpl.stock.provider;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.controllers.api.stock.provider.ProviderCapsuleAccountApi;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCapsuleAccountDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCapsuleOperationDto;
import com.c2psi.businessmanagement.services.contracts.stock.provider.ProviderCapsuleAccountService;
import com.c2psi.businessmanagement.services.contracts.stock.provider.ProviderCapsuleOperationService;
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
public class ProviderCapsuleAccountApiImpl implements ProviderCapsuleAccountApi {

    private ProviderCapsuleAccountService providerCapsuleAccountService;
    private ProviderCapsuleOperationService providerCapsuleOperationService;

    @Autowired
    public ProviderCapsuleAccountApiImpl(ProviderCapsuleAccountService providerCapsuleAccountService,
                                         ProviderCapsuleOperationService providerCapsuleOperationService) {
        this.providerCapsuleAccountService = providerCapsuleAccountService;
        this.providerCapsuleOperationService = providerCapsuleOperationService;
    }

    @Override
    public ResponseEntity saveProviderCapsuleAccount(ProviderCapsuleAccountDto procapaccDto,
                                                     BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", procapaccDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        ProviderCapsuleAccountDto procapaccDtoSaved = providerCapsuleAccountService.
                saveProviderCapsuleAccount(procapaccDto);
        log.info("The method saveProviderCapsuleAccount is being executed");
        //return new ResponseEntity(procapaccDtoSaved, HttpStatus.CREATED);
        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "Provider capsule account created successfully ");
        map.put("data", procapaccDtoSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity findProviderCapsuleAccountById(Long procapaccId) {
        Map<String, Object> map = new LinkedHashMap<>();
        ProviderCapsuleAccountDto procapaccDtoFound = providerCapsuleAccountService.
                findProviderCapsuleAccountById(procapaccId);
        log.info("The method findProviderCapsuleAccountById is being executed");
        //return ResponseEntity.ok(procapaccDtoFound);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider capsule account found successfully ");
        map.put("data", procapaccDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findProviderCapsuleAccountofArticleinPos(Long providerId, Long artId) {
        Map<String, Object> map = new LinkedHashMap<>();
        ProviderCapsuleAccountDto procapaccDtoFound = providerCapsuleAccountService.
                findProviderCapsuleAccountofArticleinPos(providerId, artId);
        log.info("The method findProviderCapsuleAccountofArticleinPos is being executed");
        //return ResponseEntity.ok(procapaccDtoFound);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider capsule account found successfully ");
        map.put("data", procapaccDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllProviderCapsuleAccountinPos(Long providerId) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<ProviderCapsuleAccountDto> proCapsAccountDtoList = providerCapsuleAccountService.
                findAllProviderCapsuleAccountinPos(providerId);
        log.info("The method findAllProviderCapsuleAccountinPos is being executed");
        //return ResponseEntity.ok(proCapsAccountDtoList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider capsule account list found successfully ");
        map.put("data", proCapsAccountDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageProviderCapsuleAccountinPos(Long providerId, Optional<Integer> optpagenum,
                                                              Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();

        Page<ProviderCapsuleAccountDto> proCapsAccountDtoPage = providerCapsuleAccountService.
                findPageProviderCapsuleAccountinPos(providerId, pagenum, pagesize);
        log.info("The method findPageProviderCapsuleAccountinPos is being executed");
        //return ResponseEntity.ok(proCapsAccountDtoPage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider capsule account page found successfully ");
        map.put("data", proCapsAccountDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity saveCapsuleOperation(ProviderCapsuleOperationDto procapopDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", procapopDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        Boolean opSaved = providerCapsuleAccountService.saveCapsuleOperation(procapopDto);
        log.info("The method saveCapsuleOperation is being executed");
        //return ResponseEntity.ok(opSaved);
        map.put("status", HttpStatus.CREATED);
        map.put("message", "Provider capsule account operation created successfully ");
        map.put("data", opSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateProviderCapsuleOperation(ProviderCapsuleOperationDto procapopDto,
                                                         BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", procapopDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        ProviderCapsuleOperationDto procapopDtoUpdated = providerCapsuleOperationService.
                updateProviderCapsuleOperation(procapopDto);
        log.info("The method updateProviderCapsuleOperation is being executed");
        //return ResponseEntity.ok(procapopDtoUpdated);
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider capsule account operation updated successfully ");
        map.put("data", procapopDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteProviderCapsuleOperationById(Long procapopId) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean delete = providerCapsuleOperationService.deleteProviderCapsuleOperationById(procapopId);
        log.info("The method deleteProviderCapsuleOperationById is being executed");
        //return ResponseEntity.ok(delete);
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider capsule account operation deleted successfully ");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllProviderCapsuleOperation(Long procapaccId) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<ProviderCapsuleOperationDto> proCapsOperationDtoList = providerCapsuleOperationService.
                findAllProviderCapsuleOperation(procapaccId);
        log.info("The method findAllProviderCapsuleOperation is being executed");
        //return ResponseEntity.ok(proCapsOperationDtoList);
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider capsule account operation list deleted successfully ");
        map.put("data", proCapsOperationDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageProviderCapsuleOperation(Long procapaccId, Optional<Integer> optpagenum,
                                                           Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();

        Page<ProviderCapsuleOperationDto> proCapsOperationDtoPage = providerCapsuleOperationService.
                findPageProviderCapsuleOperation(procapaccId, pagenum, pagesize);
        log.info("The method findPageProviderCapsuleOperation is being executed");
        //return ResponseEntity.ok(proCapsOperationDtoPage);
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider capsule account operation page deleted successfully ");
        map.put("data", proCapsOperationDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllProviderCapsuleOperationofType(Long procapaccId, OperationType opType) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<ProviderCapsuleOperationDto> proCapsOperationDtoList = providerCapsuleOperationService.
                findAllProviderCapsuleOperationofType(procapaccId, opType);
        log.info("The method findAllProviderCapsuleOperationofType is being executed");
        //return ResponseEntity.ok(proCapsOperationDtoList);
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider capsule account operation list deleted successfully ");
        map.put("data", proCapsOperationDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageProviderCapsuleOperationofType(Long procapaccId, OperationType opType,
                                                                 Optional<Integer> optpagenum,
                                                                 Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();

        Page<ProviderCapsuleOperationDto> proCapsOperationDtoPage = providerCapsuleOperationService.
                findPageProviderCapsuleOperationofType(procapaccId, opType, pagenum, pagesize);
        log.info("The method findAllProviderCapsuleOperationofType is being executed");
        //return ResponseEntity.ok(proCapsOperationDtoPage);
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider capsule account operation page deleted successfully ");
        map.put("data", proCapsOperationDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllProviderCapsuleOperationBetween(Long procapaccId, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<ProviderCapsuleOperationDto> proCapsOperationDtoList = providerCapsuleOperationService.
                findAllProviderCapsuleOperationBetween(procapaccId, startDate, endDate);
        log.info("The method findAllProviderCapsuleOperationBetween is being executed");
        //return ResponseEntity.ok(proCapsOperationDtoList);
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider capsule account operation list deleted successfully ");
        map.put("data", proCapsOperationDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageProviderCapsuleOperationBetween(Long procapaccId, Instant startDate, Instant endDate,
                                                                  Optional<Integer> optpagenum,
                                                                  Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();

        Page<ProviderCapsuleOperationDto> proCapsOperationDtoPage = providerCapsuleOperationService.
                findPageProviderCapsuleOperationBetween(procapaccId, startDate, endDate, pagenum, pagesize);
        log.info("The method findPageProviderCapsuleOperationBetween is being executed");
        //return ResponseEntity.ok(proCapsOperationDtoPage);
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider capsule account operation list deleted successfully ");
        map.put("data", proCapsOperationDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllProviderCapsuleOperationofTypeBetween(Long procapaccId, OperationType opType,
                                                                       Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<ProviderCapsuleOperationDto> proCapsOperationDtoList = providerCapsuleOperationService.
                findAllProviderCapsuleOperationofTypeBetween(procapaccId, opType, startDate, endDate);
        log.info("The method findAllProviderCapsuleOperationofTypeBetween is being executed");
        //return ResponseEntity.ok(proCapsOperationDtoList);
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider capsule account operation list deleted successfully ");
        map.put("data", proCapsOperationDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageProviderCapsuleOperationofTypeBetween(Long procapaccId, OperationType opType,
                                                                        Instant startDate, Instant endDate,
                                                                        Optional<Integer> optpagenum,
                                                                        Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();

        Page<ProviderCapsuleOperationDto> proCapsOperationDtoPage = providerCapsuleOperationService.
                findPageProviderCapsuleOperationofTypeBetween(procapaccId, opType, startDate, endDate, pagenum, pagesize);
        log.info("The method findPageProviderCapsuleOperationofTypeBetween is being executed");
        //return ResponseEntity.ok(proCapsOperationDtoPage);
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider capsule account operation page deleted successfully ");
        map.put("data", proCapsOperationDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }
}
