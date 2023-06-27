package com.c2psi.businessmanagement.controllers.apiImpl.client.client;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.controllers.api.client.client.ClientDamageAccountApi;
import com.c2psi.businessmanagement.dtos.client.client.ClientDamageAccountDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientDamageOperationDto;
import com.c2psi.businessmanagement.services.contracts.client.client.ClientDamageAccountService;
import com.c2psi.businessmanagement.services.contracts.client.client.ClientDamageOperationService;
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
public class ClientDamageAccountApiImpl implements ClientDamageAccountApi {

    private ClientDamageAccountService clientDamageAccountService;
    private ClientDamageOperationService clientDamageOperationService;

    @Autowired
    public ClientDamageAccountApiImpl(ClientDamageAccountService clientDamageAccountService,
                                      ClientDamageOperationService clientDamageOperationService) {
        this.clientDamageAccountService = clientDamageAccountService;
        this.clientDamageOperationService = clientDamageOperationService;
    }

    @Override
    public ResponseEntity saveClientDamageAccount(ClientDamageAccountDto cltdamaccDto, BindingResult bindingResult) {

        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", cltdamaccDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }
        ClientDamageAccountDto cltdamaccDtoSaved = clientDamageAccountService.saveClientDamageAccount(cltdamaccDto);
        log.info("The method saveClientDamageAccount is executed successfully");
        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "ClientDamageAccountDto created successfully");
        map.put("data", cltdamaccDtoSaved);
        map.put("cause", "Le compte damage du client a ete cree avec success");
        return new ResponseEntity(map, HttpStatus.CREATED);
        //return null;
    }

    @Override
    public ResponseEntity findClientDamageAccountById(Long cltdamaccId) {

        Map<String, Object> map = new LinkedHashMap<>();
        ClientDamageAccountDto cltdamaccDtoFound = clientDamageAccountService.findClientDamageAccountById(cltdamaccId);
        log.info("The method findClientDamageAccountById is executed successfully");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "ClientDamageAccountDto found successfully");
        map.put("data", cltdamaccDtoFound);
        map.put("cause", "Le compte damage du client a ete trouve avec success");
        return new ResponseEntity(map, HttpStatus.OK);
        //return ResponseEntity.ok(cltdamaccDtoFound);
    }

    @Override
    public ResponseEntity findClientDamageAccountofArticleinPos(Long clientId, Long artId) {
        Map<String, Object> map = new LinkedHashMap<>();
        ClientDamageAccountDto cltdamaccDtoFound = clientDamageAccountService.
                findClientDamageAccountofArticleinPos(clientId, artId);
        log.info("The method findClientDamageAccountofArticleinPos is executed successfully");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "ClientDamageAccountDto found successfully");
        map.put("data", cltdamaccDtoFound);
        map.put("cause", "Le compte damage du client a ete trouve avec success");
        return new ResponseEntity(map, HttpStatus.OK);

    }

    @Override
    public ResponseEntity findAllClientDamageAccountinPos(Long clientId) {

        Map<String, Object> map = new LinkedHashMap<>();
        List<ClientDamageAccountDto> cltdamaccDtoFoundList = clientDamageAccountService.
                findAllClientDamageAccountinPos(clientId);
        log.info("The method findAllClientDamageAccountinPos is executed successfully");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "ClientDamageAccountDto found successfully");
        map.put("data", cltdamaccDtoFoundList);
        map.put("cause", "Le compte damage du client a ete trouve avec success");
        return new ResponseEntity(map, HttpStatus.OK);

    }

    @Override
    public ResponseEntity findPageClientDamageAccountinPos(Long clientId, Optional<Integer> optpagenum,
                                                           Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();
        Page<ClientDamageAccountDto> cltdamaccDtoFoundPage = clientDamageAccountService.
                findPageClientDamageAccountinPos(clientId, pagenum, pagesize);
        log.info("The method findPageClientDamageAccountinPos is executed successfully");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "ClientDamageAccountDto found successfully");
        map.put("data", cltdamaccDtoFoundPage);
        map.put("cause", "Le compte damage du client a ete trouve avec success");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity saveDamageOperation(ClientDamageAccountDto cltdamaccDto, BindingResult bindingResult1,
                                              ClientDamageOperationDto cltdamopDto, BindingResult bindingResult2) {

        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult1.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", cltdamaccDto, bindingResult1);
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
                    "and the report errors are {}", cltdamopDto, bindingResult2);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult2);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        Boolean opSaved = clientDamageAccountService.saveDamageOperation(cltdamaccDto, cltdamopDto);
        log.info("The method saveDamageOperation is executed successfully");
        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "Damage operation created successfully");
        map.put("data", opSaved);
        map.put("cause", "Le compte damage du client a ete cree avec success");
        return new ResponseEntity(map, HttpStatus.CREATED);
        //return null;
    }

    @Override
    public ResponseEntity updateClientDamageOperation(ClientDamageOperationDto cltdamopDto,
                                                      BindingResult bindingResult) {

        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", cltdamopDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        ClientDamageOperationDto cltdamopDtoUpdated = clientDamageOperationService.
                updateClientDamageOperation(cltdamopDto);
        log.info("The method updateClientDamageOperation is executed successfully");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Damage operation created successfully");
        map.put("data", cltdamopDtoUpdated);
        map.put("cause", "Le compte damage du client a ete cree avec success");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteClientDamageOperationById(Long cltdamopId) {

        Map<String, Object> map = new LinkedHashMap<>();
        Boolean delete = clientDamageOperationService.deleteClientDamageOperationById(cltdamopId);
        log.info("The method deleteClientDamageOperationById is executed successfully");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Damage operation created successfully");
        map.put("data", delete);
        map.put("cause", "L'operation sur le compte damage du client a ete supprime avec success");
        return new ResponseEntity(map, HttpStatus.OK);

    }

    @Override
    public ResponseEntity findAllClientDamageOperation(Long cltdamaccId) {

        Map<String, Object> map = new LinkedHashMap<>();
        List<ClientDamageOperationDto> cltdamopDtoFoundList = clientDamageOperationService.
                findAllClientDamageOperation(cltdamaccId);
        log.info("The method findAllClientDamageOperation is executed successfully");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "ClientDamageAccountDto found successfully");
        map.put("data", cltdamopDtoFoundList);
        map.put("cause", "La liste des damage operation sur le compte a ete trouve avec success");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageClientDamageOperation(Long cltdamaccId, Optional<Integer> optpagenum,
                                                        Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();
        Page<ClientDamageOperationDto> cltdamopDtoFoundPage = clientDamageOperationService.
                findPageClientDamageOperation(cltdamaccId, pagenum, pagesize);
        log.info("The method findPageClientDamageOperation is executed successfully");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "ClientDamageAccountDto found successfully");
        map.put("data", cltdamopDtoFoundPage);
        map.put("cause", "La page des damage operation sur le compte a ete trouve avec success");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllClientDamageOperationofType(Long cltdamaccId, OperationType opType) {

        Map<String, Object> map = new LinkedHashMap<>();
        List<ClientDamageOperationDto> cltdamopDtoFoundList = clientDamageOperationService.
                findAllClientDamageOperationofType(cltdamaccId, opType);
        log.info("The method findAllClientDamageOperation is executed successfully");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "ClientDamageAccountDto found successfully");
        map.put("data", cltdamopDtoFoundList);
        map.put("cause", "La liste des damage operation sur le compte a ete trouve avec success");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageClientDamageOperationofType(Long cltdamaccId, OperationType opType,
                                                              Optional<Integer> optpagenum,
                                                              Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();
        Page<ClientDamageOperationDto> cltdamopDtoFoundPage = clientDamageOperationService.
                findPageClientDamageOperationofType(cltdamaccId, opType, pagenum, pagesize);
        log.info("The method findPageClientDamageOperation is executed successfully");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "ClientDamageAccountDto found successfully");
        map.put("data", cltdamopDtoFoundPage);
        map.put("cause", "La page des damage operation sur le compte a ete trouve avec success");
        return new ResponseEntity(map, HttpStatus.OK);
        /*Page<ClientDamageOperationDto> cltDamageOpDtoPage = clientDamageOperationService.
                findPageClientDamageOperationofType(cltdamaccId, opType, pagenum, pagesize);
        log.info("The method findPageClientDamageOperation is being executed");
        return ResponseEntity.ok(cltDamageOpDtoPage);*/
    }

    @Override
    public ResponseEntity findAllClientDamageOperationBetween(Long cltdamaccId, Instant startDate, Instant endDate) {

        Map<String, Object> map = new LinkedHashMap<>();
        List<ClientDamageOperationDto> cltdamopDtoFoundList = clientDamageOperationService.
                findAllClientDamageOperationBetween(cltdamaccId, startDate, endDate);
        log.info("The method findAllClientDamageOperationBetween is executed successfully");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "ClientDamageAccountDto found successfully");
        map.put("data", cltdamopDtoFoundList);
        map.put("cause", "La liste des damage operation sur le compte a ete trouve avec success");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageClientDamageOperationBetween(Long cltdamaccId, Instant startDate, Instant endDate,
                                                               Optional<Integer> optpagenum, Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();
        Page<ClientDamageOperationDto> cltdamopDtoFoundPage = clientDamageOperationService.
                findPageClientDamageOperationBetween(cltdamaccId, startDate, endDate, pagenum, pagesize);
        log.info("The method findPageClientDamageOperationBetween is executed successfully");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "ClientDamageAccountDto found successfully");
        map.put("data", cltdamopDtoFoundPage);
        map.put("cause", "La page des damage operation sur le compte a ete trouve avec success");
        return new ResponseEntity(map, HttpStatus.OK);

    }

    @Override
    public ResponseEntity findAllClientDamageOperationofTypeBetween(Long cltdamaccId, OperationType opType,
                                                                    Instant startDate, Instant endDate) {

        Map<String, Object> map = new LinkedHashMap<>();
        List<ClientDamageOperationDto> cltdamopDtoFoundList = clientDamageOperationService.
                findAllClientDamageOperationofTypeBetween(cltdamaccId, opType, startDate, endDate);
        log.info("The method findAllClientDamageOperationofTypeBetween is executed successfully");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "ClientDamageAccountDto found successfully");
        map.put("data", cltdamopDtoFoundList);
        map.put("cause", "La liste des damage operation sur le compte a ete trouve avec success");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageClientDamageOperationofTypeBetween(Long cltdamaccId, OperationType opType,
                                                                     Instant startDate, Instant endDate,
                                                                     Optional<Integer> optpagenum,
                                                                     Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();
        Page<ClientDamageOperationDto> cltdamopDtoFoundPage = clientDamageOperationService.
                findPageClientDamageOperationofTypeBetween(cltdamaccId, opType, startDate, endDate, pagenum, pagesize);
        log.info("The method findPageClientDamageOperationofTypeBetween is executed successfully");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "ClientDamageAccountDto found successfully");
        map.put("data", cltdamopDtoFoundPage);
        map.put("cause", "La page des damage operation sur le compte a ete trouve avec success");
        return new ResponseEntity(map, HttpStatus.OK);
    }
}
