package com.c2psi.businessmanagement.controllers.apiImpl.client.client;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.controllers.api.client.client.ClientCapsuleAccountApi;
import com.c2psi.businessmanagement.dtos.client.client.ClientCapsuleAccountDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientCapsuleOperationDto;
import com.c2psi.businessmanagement.services.contracts.client.client.ClientCapsuleAccountService;
import com.c2psi.businessmanagement.services.contracts.client.client.ClientCapsuleOperationService;
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
public class ClientCapsuleAccountApiImpl implements ClientCapsuleAccountApi {

    private ClientCapsuleAccountService clientCapsuleAccountService;
    private ClientCapsuleOperationService clientCapsuleOperationService;

    @Autowired
    public ClientCapsuleAccountApiImpl(ClientCapsuleAccountService clientCapsuleAccountService,
                                       ClientCapsuleOperationService clientCapsuleOperationService) {
        this.clientCapsuleAccountService = clientCapsuleAccountService;
        this.clientCapsuleOperationService = clientCapsuleOperationService;
    }

    @Override
    public ResponseEntity saveClientCapsuleAccount(ClientCapsuleAccountDto cltcapaccDto, BindingResult bindingResult) {

        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", cltcapaccDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }
        ClientCapsuleAccountDto clientCapsuleAccountDtoSaved = clientCapsuleAccountService.
                saveClientCapsuleAccount(cltcapaccDto);
        log.info("The method saveClientCapsuleAccount is executed successfully");
        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "ClientCapsuleAccountDto created successfully");
        map.put("data", clientCapsuleAccountDtoSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity findClientCapsuleAccountById(Long cltcapaccId) {
        Map<String, Object> map = new LinkedHashMap<>();
        ClientCapsuleAccountDto clientCapsuleAccountDtoFound = clientCapsuleAccountService.
                findClientCapsuleAccountById(cltcapaccId);
        log.info("The method findClientCapsuleAccountById is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "ClientCapsuleAccountDto created successfully");
        map.put("data", clientCapsuleAccountDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findClientCapsuleAccountofArticleinPos(Long clientId, Long artId) {

        Map<String, Object> map = new LinkedHashMap<>();
        ClientCapsuleAccountDto clientCapsuleAccountDtoFound = clientCapsuleAccountService.
                findClientCapsuleAccountofArticleinPos(clientId, artId);
        log.info("The method findClientCapsuleAccountofArticleinPos is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "ClientCapsuleAccountDto created successfully");
        map.put("data", clientCapsuleAccountDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllClientCapsuleAccountinPos(Long clientId) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<ClientCapsuleAccountDto> clientCapsuleAccountDtoList = clientCapsuleAccountService.
                findAllClientCapsuleAccountinPos(clientId);
        log.info("The method findAllClientCapsuleAccountinPos is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "ClientCapsuleAccountDto created successfully");
        map.put("data", clientCapsuleAccountDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageClientCapsuleAccountinPos(Long clientId, Optional<Integer> optpagenum,
                                                            Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();

        Page<ClientCapsuleAccountDto> clientCapsuleAccountDtoPage = clientCapsuleAccountService.
                findPageClientCapsuleAccountinPos(clientId, pagenum, pagesize);
        log.info("The method findPageClientCapsuleAccountinPos is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "ClientCapsuleAccountDto created successfully");
        map.put("data", clientCapsuleAccountDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity saveCapsuleOperation(ClientCapsuleOperationDto cltcapopDto, BindingResult bindingResult) {

        Map<String, Object> map = new LinkedHashMap<>();

        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", cltcapopDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        Boolean opSaved = clientCapsuleAccountService.saveCapsuleOperation(cltcapopDto);
        log.info("The method saveCapsuleOperation is being executed");
        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "ClientCapsuleAccountDto created successfully");
        map.put("data", opSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateClientCapsuleOperation(ClientCapsuleOperationDto cltcapopDto,
                                                       BindingResult bindingResult) {

        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", cltcapopDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        ClientCapsuleOperationDto cltcapopDtoSaved = clientCapsuleOperationService.
                updateClientCapsuleOperation(cltcapopDto);
        log.info("The method updateClientCapsuleOperation is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "ClientCapsuleAccountDto updated successfully");
        map.put("data", cltcapopDtoSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteClientCapsuleOperationById(Long cltcapopId) {

        Map<String, Object> map = new LinkedHashMap<>();

        Boolean delete = clientCapsuleOperationService.deleteClientCapsuleOperationById(cltcapopId);
        log.info("The method deleteClientCapsuleOperationById is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "ClientCapsuleAccountDto deleted successfully");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllClientCapsuleOperation(Long cltcapaccId) {
        Map<String, Object> map = new LinkedHashMap<>();

        List<ClientCapsuleOperationDto> clientCapsOpDtoList = clientCapsuleOperationService.
                findAllClientCapsuleOperation(cltcapaccId);
        log.info("The method findAllClientCapsuleOperation is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "List of ClientCapsuleAccountDto found successfully");
        map.put("data", clientCapsOpDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageClientCapsuleOperation(Long cltcapaccId, Optional<Integer> optpagenum,
                                                         Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();
        Page<ClientCapsuleOperationDto> clientCapsOpDtoPage = clientCapsuleOperationService.
                findPageClientCapsuleOperation(cltcapaccId, pagenum, pagesize);
        log.info("The method findPageClientCapsuleOperation is being executed");
        //return ResponseEntity.ok(clientCapsOpDtoPage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Page of ClientCapsuleAccountDto found successfully");
        map.put("data", clientCapsOpDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllClientCapsuleOperationofType(Long cltcapaccId, OperationType opType) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<ClientCapsuleOperationDto> clientCapsOpDtoList = clientCapsuleOperationService.
                findAllClientCapsuleOperationofType(cltcapaccId, opType);
        log.info("The method findAllClientCapsuleOperationofType is being executed");
        //return ResponseEntity.ok(clientCapsOpDtoList);

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "List of ClientCapsuleOperationDto found successfully");
        map.put("data", clientCapsOpDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageClientCapsuleOperationofType(Long cltcapaccId, OperationType opType,
                                                               Optional<Integer> optpagenum,
                                                               Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();

        Page<ClientCapsuleOperationDto> clientCapsOpDtoPage = clientCapsuleOperationService.
                findPageClientCapsuleOperationofType(cltcapaccId, opType, pagenum, pagesize);
        log.info("The method findAllClientCapsuleOperation is being executed");
        //return ResponseEntity.ok(clientCapsOpDtoPage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Page of ClientCapsuleOperationDto found successfully");
        map.put("data", clientCapsOpDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllClientCapsuleOperationBetween(Long cltcapaccId, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<ClientCapsuleOperationDto> clientCapsOpDtoList = clientCapsuleOperationService.
                findAllClientCapsuleOperationBetween(cltcapaccId, startDate, endDate);
        log.info("The method findAllClientCapsuleOperationBetween is being executed");
        //return ResponseEntity.ok(clientCapsOpDtoList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "List of clientCapsuleOperation found successfully");
        map.put("data", clientCapsOpDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageClientCapsuleOperationBetween(Long cltcapaccId, Instant startDate, Instant endDate,
                                                                Optional<Integer> optpagenum,
                                                                Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Map<String, Object> map = new LinkedHashMap<>();

        Page<ClientCapsuleOperationDto> clientCapsOpDtoPage = clientCapsuleOperationService.
                findPageClientCapsuleOperationBetween(cltcapaccId, startDate, endDate, pagenum, pagesize);
        log.info("The method findPageClientCapsuleOperationBetween is being executed");
        //return ResponseEntity.ok(clientCapsOpDtoPage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Page of clientCapsuleOperation found successfully");
        map.put("data", clientCapsOpDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllClientCapsuleOperationofTypeBetween(Long cltcapaccId, OperationType opType,
                                                                     Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<ClientCapsuleOperationDto> clientCapsOpDtoList = clientCapsuleOperationService.
                findAllClientCapsuleOperationofTypeBetween(cltcapaccId, opType, startDate, endDate);
        log.info("The method findAllClientCapsuleOperationofTypeBetween is being executed");
        //return ResponseEntity.ok(clientCapsOpDtoList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "List of clientCapsuleOperation found successfully");
        map.put("data", clientCapsOpDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageClientCapsuleOperationofTypeBetween(Long cltcapaccId, OperationType opType,
                                                                      Instant startDate, Instant endDate,
                                                                      Optional<Integer> optpagenum,
                                                                      Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Map<String, Object> map = new LinkedHashMap<>();
        Page<ClientCapsuleOperationDto> clientCapsOpDtoPage = clientCapsuleOperationService.
                findPageClientCapsuleOperationofTypeBetween(cltcapaccId, opType, startDate, endDate, pagenum, pagesize);
        log.info("The method findPageClientCapsuleOperationofTypeBetween is being executed");
        //return ResponseEntity.ok(clientCapsOpDtoPage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Page of clientCapsuleOperation found successfully");
        map.put("data", clientCapsOpDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }
}
