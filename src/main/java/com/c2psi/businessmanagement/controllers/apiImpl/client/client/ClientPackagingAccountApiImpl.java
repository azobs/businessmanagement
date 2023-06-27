package com.c2psi.businessmanagement.controllers.apiImpl.client.client;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.controllers.api.client.client.ClientPackagingAccountApi;
import com.c2psi.businessmanagement.dtos.client.client.ClientPackagingAccountDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientPackagingOperationDto;
import com.c2psi.businessmanagement.services.contracts.client.client.ClientPackagingAccountService;
import com.c2psi.businessmanagement.services.contracts.client.client.ClientPackagingOperationService;
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
public class ClientPackagingAccountApiImpl implements ClientPackagingAccountApi {

    private ClientPackagingAccountService clientPackagingAccountService;
    private ClientPackagingOperationService clientPackagingOperationService;

    @Autowired
    public ClientPackagingAccountApiImpl(ClientPackagingAccountService clientPackagingAccountService,
                                         ClientPackagingOperationService clientPackagingOperationService) {
        this.clientPackagingAccountService = clientPackagingAccountService;
        this.clientPackagingOperationService = clientPackagingOperationService;
    }

    @Override
    public ResponseEntity saveClientPackagingAccount(ClientPackagingAccountDto cltpackaccDto,
                                                     BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", cltpackaccDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }
        ClientPackagingAccountDto cltpackaccDtoSaved = clientPackagingAccountService.
                saveClientPackagingAccount(cltpackaccDto);
        log.info("The method saveClientPackagingAccount is executed successfully");
        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "ClientPackagingAccount created successfully");
        map.put("data", cltpackaccDtoSaved);
        map.put("cause", "Le compte packaging du client a ete cree avec success");
        return new ResponseEntity(map, HttpStatus.CREATED);
        //return null;
    }

    @Override
    public ResponseEntity findClientPackagingAccountById(Long cltpackaccId) {
        Map<String, Object> map = new LinkedHashMap<>();
        ClientPackagingAccountDto cltpackaccDtoFound = clientPackagingAccountService.
                findClientPackagingAccountById(cltpackaccId);
        log.info("The method findClientPackagingAccountById is executed successfully");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "ClientPackagingAccount found successfully");
        map.put("data", cltpackaccDtoFound);
        map.put("cause", "Le compte packaging du client a ete retrouve avec success a partir de son Id");
        return new ResponseEntity(map, HttpStatus.OK);
        //return null;
    }

    @Override
    public ResponseEntity findClientPackagingAccountByClientAndPackaging(Long clientId, Long packagingId) {
        Map<String, Object> map = new LinkedHashMap<>();
        ClientPackagingAccountDto cltpackaccDtoFound = clientPackagingAccountService.
                findClientPackagingAccountByClientAndPackaging(packagingId, clientId);
        log.info("The method findClientPackagingAccountByClientAndPackaging is executed successfully");
        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "ClientPackagingAccount found successfully");
        map.put("data", cltpackaccDtoFound);
        map.put("cause", "Le compte packaging du client a ete retrouve avec success a partir du packaging et " +
                "de son identifiant");
        return new ResponseEntity(map, HttpStatus.OK);
        //return null;
    }

    @Override
    public ResponseEntity findAllClientPackagingAccountinPos(Long posId) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<ClientPackagingAccountDto> cltpackaccDtoFoundList = clientPackagingAccountService.
                findAllClientPackagingAccountinPos(posId);
        log.info("The method findAllClientPackagingAccountinPos is executed successfully");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "List of ClientPackagingAccount found successfully");
        map.put("data", cltpackaccDtoFoundList);
        map.put("cause", "La liste des comptes client packaging dans le pointofsale a ete retrouve avec success");
        return new ResponseEntity(map, HttpStatus.OK);
        //return null;
    }

    @Override
    public ResponseEntity findPageClientPackagingAccountinPos(Long posId, Optional<Integer> optpagenum,
                                                              Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<ClientPackagingAccountDto> cltpackaccDtoFoundPage = clientPackagingAccountService.
                findPageClientPackagingAccountinPos(posId, pagenum, pagesize);
        log.info("The method findPageClientPackagingAccountinPos is executed successfully");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Page of ClientPackagingAccount found successfully");
        map.put("data", cltpackaccDtoFoundPage);
        map.put("cause", "La page des comptes client packaging dans le pointofsale a ete retrouve avec success");
        return new ResponseEntity(map, HttpStatus.OK);
        //return null;
    }

    @Override
    public ResponseEntity findAllPackagingAccountofClient(Long clientId) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<ClientPackagingAccountDto> cltpackaccDtoFoundList = clientPackagingAccountService.
                findAllPackagingAccountofClient(clientId);
        log.info("The method findAllPackagingAccountofClient is executed successfully");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "List of ClientPackagingAccount found successfully");
        map.put("data", cltpackaccDtoFoundList);
        map.put("cause", "La liste des comptes client packaging dans le pointofsale a ete retrouve avec success");
        return new ResponseEntity(map, HttpStatus.OK);
        //return null;
    }

    @Override
    public ResponseEntity findPagePackagingAccountofClient(Long clientId, Optional<Integer> optpagenum,
                                                                 Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<ClientPackagingAccountDto> cltpackaccDtoFoundPage = clientPackagingAccountService.
                findPagePackagingAccountofClient(clientId, pagenum, pagesize);
        log.info("The method findPagePackagingAccountofClient is executed successfully");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Page of ClientPackagingAccount found successfully");
        map.put("data", cltpackaccDtoFoundPage);
        map.put("cause", "La page des comptes packaging d'un client a ete retrouve avec success");
        return new ResponseEntity(map, HttpStatus.OK);
        //return null;
    }

    @Override
    public ResponseEntity savePackagingOperationforClient(ClientPackagingAccountDto cltpackaccDto, BindingResult bindingResult1,
                                                 ClientPackagingOperationDto cltpackopDto, BindingResult bindingResult2) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult1.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", cltpackaccDto, bindingResult1);
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
                    "and the report errors are {}", cltpackaccDto, bindingResult2);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult2);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }
        Boolean opSaved = clientPackagingAccountService.savePackagingOperationforClient(cltpackaccDto, cltpackopDto);
        log.info("The method savePackagingOperationforClient is executed successfully");
        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "Packaging operation created successfully");
        map.put("data", opSaved);
        map.put("cause", "Le packaging operation du client a ete enregistre avec success");
        return new ResponseEntity(map, HttpStatus.CREATED);
        //return null;
    }

    @Override
    public ResponseEntity deleteClientPackagingAccountById(Long cltpackaccId) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean delete = clientPackagingAccountService.deleteClientPackagingAccountById(cltpackaccId);
        log.info("The method deleteClientPackagingAccountById is executed successfully");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Packaging account deleted successfully");
        map.put("data", delete);
        map.put("cause", "Le packaging account du client a ete supprime avec success");
        return new ResponseEntity(map, HttpStatus.OK);
        //return null;
    }

    @Override
    public ResponseEntity updateClientPackagingOperation(ClientPackagingOperationDto cltpackopDto,
                                                         BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", cltpackopDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }
        ClientPackagingOperationDto cltpackopDtoUpdated = clientPackagingOperationService.
                updateClientPackagingOperation(cltpackopDto);
        log.info("The method updateClientPackagingOperation is executed successfully");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Packaging operation updated successfully");
        map.put("data", cltpackopDtoUpdated);
        map.put("cause", "Le packaging operation du client a ete mis a jour avec success");
        return new ResponseEntity(map, HttpStatus.OK);
        //return null;
    }

    @Override
    public ResponseEntity deleteClientPackagingOperationById(Long cltpackopId) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean delete = clientPackagingOperationService.deleteClientPackagingOperationById(cltpackopId);
        log.info("The method deleteClientPackagingOperationById is executed successfully");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Packaging operation deleted successfully");
        map.put("data", delete);
        map.put("cause", "Le packaging operation du client a ete supprime avec success");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllClientPackagingOperation(Long cltpackaccId) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<ClientPackagingOperationDto> cltpackopDtoFoundList = clientPackagingOperationService.
                findAllClientPackagingOperation(cltpackaccId);
        log.info("The method findAllClientPackagingOperation is executed successfully");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "List of ClientPackagingOperation found successfully");
        map.put("data", cltpackopDtoFoundList);
        map.put("cause", "La liste des operations sur le compte packaging dans le pointofsale a ete retrouve avec success");
        return new ResponseEntity(map, HttpStatus.OK);
        //return null;
    }

    @Override
    public ResponseEntity findPageClientPackagingOperation(Long cltpackaccId, Optional<Integer> optpagenum,
                                                           Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<ClientPackagingOperationDto> cltpackopDtoFoundPage = clientPackagingOperationService.
                findPageClientPackagingOperation(cltpackaccId, pagenum, pagesize);
        log.info("The method findPageClientPackagingOperation is executed successfully");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "List of ClientPackagingOperation found successfully");
        map.put("data", cltpackopDtoFoundPage);
        map.put("cause", "La liste des operations sur le compte packaging dans le pointofsale a ete retrouve avec success");
        return new ResponseEntity(map, HttpStatus.OK);
        //return null;
    }

    @Override
    public ResponseEntity findAllClientPackagingOperationofType(Long cltpackaccId, OperationType opType) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<ClientPackagingOperationDto> cltpackopDtoFoundList = clientPackagingOperationService.
                findAllClientPackagingOperationofType(cltpackaccId, opType);
        log.info("The method findAllClientPackagingOperationofType is executed successfully");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "List of ClientPackagingOperation of type found successfully");
        map.put("data", cltpackopDtoFoundList);
        map.put("cause", "La liste des operations sur le compte packaging dans le pointofsale a ete retrouve avec success");
        return new ResponseEntity(map, HttpStatus.OK);
        //return null;
    }

    @Override
    public ResponseEntity findPageClientPackagingOperationofType(Long cltpackaccId, OperationType opType,
                                                                 Optional<Integer> optpagenum,
                                                                 Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<ClientPackagingOperationDto> cltpackopDtoFoundPage = clientPackagingOperationService.
                findPageClientPackagingOperationofType(cltpackaccId, opType, pagenum, pagesize);
        log.info("The method findPageClientPackagingOperationofType is executed successfully");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "List of ClientPackagingOperation of type found successfully");
        map.put("data", cltpackopDtoFoundPage);
        map.put("cause", "La liste des operations sur le compte packaging dans le pointofsale a ete retrouve avec success");
        return new ResponseEntity(map, HttpStatus.OK);
        //return null;
    }

    @Override
    public ResponseEntity findAllClientPackagingOperationBetween(Long cltpackaccId, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<ClientPackagingOperationDto> cltpackopDtoFoundList = clientPackagingOperationService.
                findAllClientPackagingOperationBetween(cltpackaccId, startDate, endDate);
        log.info("The method findAllClientPackagingOperationBetween is executed successfully");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "List of ClientPackagingOperation of type found successfully");
        map.put("data", cltpackopDtoFoundList);
        map.put("cause", "La liste des operations sur le compte packaging dans le pointofsale a ete retrouve avec success");
        return new ResponseEntity(map, HttpStatus.OK);
        //return null;
    }

    @Override
    public ResponseEntity findPageClientPackagingOperationBetween(Long cltpackaccId, Instant startDate, Instant endDate,
                                                                  Optional<Integer> optpagenum,
                                                                  Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<ClientPackagingOperationDto> cltpackopDtoFoundPage = clientPackagingOperationService.
                findPageClientPackagingOperationBetween(cltpackaccId, startDate, endDate, pagenum, pagesize);
        log.info("The method findPageClientPackagingOperationBetween is executed successfully");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "List of ClientPackagingOperation of type found successfully");
        map.put("data", cltpackopDtoFoundPage);
        map.put("cause", "La liste des operations sur le compte packaging dans le pointofsale a ete retrouve avec success");
        return new ResponseEntity(map, HttpStatus.OK);
        //return null;
    }

    @Override
    public ResponseEntity findAllClientPackagingOperationofTypeBetween(Long cltpackaccId, OperationType opType,
                                                                       Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<ClientPackagingOperationDto> cltpackopDtoFoundList = clientPackagingOperationService.
                findAllClientPackagingOperationofTypeBetween(cltpackaccId, opType, startDate, endDate);
        log.info("The method findAllClientPackagingOperationofTypeBetween is executed successfully");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "List of ClientPackagingOperation of type found successfully");
        map.put("data", cltpackopDtoFoundList);
        map.put("cause", "La liste des operations sur le compte packaging dans le pointofsale a ete retrouve avec success");
        return new ResponseEntity(map, HttpStatus.OK);
        //return null;
    }

    @Override
    public ResponseEntity findPageClientPackagingOperationofTypeBetween(Long cltpackaccId, OperationType opType,
                                                                        Instant startDate, Instant endDate,
                                                                        Optional<Integer> optpagenum,
                                                                        Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<ClientPackagingOperationDto> cltpackopDtoFoundPage = clientPackagingOperationService.
                findPageClientPackagingOperationofTypeBetween(cltpackaccId, opType, startDate, endDate, pagenum,
                        pagesize);
        log.info("The method findPageClientPackagingOperationofTypeBetween is executed successfully");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "List of ClientPackagingOperation of type found successfully");
        map.put("data", cltpackopDtoFoundPage);
        map.put("cause", "La liste des operations sur le compte packaging dans le pointofsale a ete retrouve avec success");
        return new ResponseEntity(map, HttpStatus.OK);
        //return null;
    }
}
