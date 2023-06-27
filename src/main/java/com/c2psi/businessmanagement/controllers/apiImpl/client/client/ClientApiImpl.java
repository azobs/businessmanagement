package com.c2psi.businessmanagement.controllers.apiImpl.client.client;

import com.c2psi.businessmanagement.controllers.api.client.client.ClientApi;
import com.c2psi.businessmanagement.dtos.client.client.ClientCashAccountDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientCashOperationDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.services.contracts.client.client.ClientCashAccountService;
import com.c2psi.businessmanagement.services.contracts.client.client.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
public class ClientApiImpl implements ClientApi {

    private ClientService clientService;
    private ClientCashAccountService clientCashAccountService;

    @Autowired
    public ClientApiImpl(ClientService clientService, ClientCashAccountService clientCashAccountService) {
        this.clientService = clientService;
        this.clientCashAccountService = clientCashAccountService;
    }

    @Override
    public ResponseEntity saveClient(ClientDto clientDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", clientDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }
        ClientDto clientDtoSaved = clientService.saveClient(clientDto);
        log.info("The method saveClient is being executed");
        //return new ResponseEntity(clientDtoSaved, HttpStatus.CREATED);
        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "Client created successfully");
        map.put("data", clientDtoSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity updateClient(ClientDto clientDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", clientDto, bindingResult);
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }
        ClientDto clientDtoUpdated = clientService.updateClient(clientDto);
        log.info("The method updateClient is being executed");
        //return new ResponseEntity(clientDtoUpdated, HttpStatus.OK);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Client updated successfully");
        map.put("data", clientDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findClientById(Long clientId) {
        ClientDto clientDtoFound = clientService.findClientById(clientId);
        log.info("The method findClientById is being executed");
        //return ResponseEntity.ok(clientDtoFound);
        Map<String, Object> map = new LinkedHashMap<>();
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Client found successfully");
        map.put("data", clientDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findClientByNameofPos(String clientName, String clientOthername, Long posId) {
        ClientDto clientDtoFound = clientService.findClientByNameofPos(clientName, clientOthername, posId);
        log.info("The method findClientByNameofPos is being executed");
        //return ResponseEntity.ok(clientDtoFound);
        Map<String, Object> map = new LinkedHashMap<>();
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Client found successfully");
        map.put("data", clientDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findClientByCniofPos(String clientCni, Long posId) {
        ClientDto clientDtoFound = clientService.findClientByCniofPos(clientCni, posId);
        log.info("The method findClientByCniofPos is being executed");
        //return ResponseEntity.ok(clientDtoFound);
        Map<String, Object> map = new LinkedHashMap<>();
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Client found successfully");
        map.put("data", clientDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findClientByEmail(String clientEmail) {
        ClientDto clientDtoFound = clientService.findClientByEmail(clientEmail);
        log.info("The method findClientByCniofPos is being executed");
        //return ResponseEntity.ok(clientDtoFound);
        Map<String, Object> map = new LinkedHashMap<>();
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Client found successfully");
        map.put("data", clientDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllClientofPos(Long posId) {
        List<ClientDto> clientDtoList = clientService.findAllClientofPos(posId);
        log.info("The method findAllClientofPos is being executed");
        //return ResponseEntity.ok(clientDtoList);
        Map<String, Object> map = new LinkedHashMap<>();
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "List of Client found successfully");
        map.put("data", clientDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageClientofPos(Long posId, Optional<Integer> optpagenum,
                                              Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Page<ClientDto> clientDtoPage = clientService.findPageClientofPos(posId, pagenum, pagesize);
        log.info("The method findPageClientofPos is being executed");
        //return ResponseEntity.ok(clientDtoPage);
        Map<String, Object> map = new LinkedHashMap<>();
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "page of Client found successfully");
        map.put("data", clientDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteClientById(Long clientId) {
        Boolean delete = clientService.deleteClientById(clientId);
        log.info("The method deleteClientById is being executed");
        //return ResponseEntity.ok(delete);
        Map<String, Object> map = new LinkedHashMap<>();
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Client deleted successfully");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity saveCashOperation(ClientCashAccountDto clientCashAccountDto, BindingResult bindingResult1,
                                            ClientCashOperationDto clientCashOperationDto, BindingResult bindingResult2) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult1.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", clientCashAccountDto, bindingResult1);
            //return ResponseEntity.badRequest().body(bindingResult1.toString());
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
                    "and the report errors are {}", clientCashOperationDto, bindingResult2);
            //return ResponseEntity.badRequest().body(bindingResult2.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult2);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }
        Boolean opSaved = clientCashAccountService.saveCashOperation(clientCashAccountDto, clientCashOperationDto);
        log.info("The method saveCashOperation is being executed");
        //return ResponseEntity.ok(opSaved);
        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "Client cash operation saved successfully");
        map.put("data", opSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity saveClientCashAccount(ClientCashAccountDto clientCashAccountDto,
                                                BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", clientCashAccountDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }
        ClientCashAccountDto clientCashAccountDtoSaved = clientCashAccountService.
                saveClientCashAccount(clientCashAccountDto);
        log.info("The method saveClientCashAccount is being executed");
        //return new ResponseEntity(clientCashAccountDtoSaved, HttpStatus.CREATED);
        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "Client cash account created successfully");
        map.put("data", clientCashAccountDtoSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity findClientCashAccountById(Long ccaId) {
        Map<String, Object> map = new LinkedHashMap<>();
        ClientCashAccountDto clientCashAccountDtoFound = clientCashAccountService.findClientCashAccountById(ccaId);
        log.info("The method findClientCashAccountById is being executed");
        //return ResponseEntity.ok(clientCashAccountDtoFound);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Client cash account found successfully");
        map.put("data", clientCashAccountDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteClientCashAccountById(Long ccaId) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean delete = clientCashAccountService.deleteClientCashAccountById(ccaId);
        log.info("The method deleteClientCashAccountById is being executed");
        //return ResponseEntity.ok(delete);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Client cash account found successfully");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }
}
