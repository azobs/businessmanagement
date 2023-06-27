package com.c2psi.businessmanagement.controllers.apiImpl.stock.provider;

import com.c2psi.businessmanagement.controllers.api.stock.provider.ProviderApi;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCashAccountDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCashOperationDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;
import com.c2psi.businessmanagement.services.contracts.stock.provider.ProviderCashAccountService;
import com.c2psi.businessmanagement.services.contracts.stock.provider.ProviderService;
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
public class ProviderApiImpl implements ProviderApi {
    private ProviderService providerService;
    private ProviderCashAccountService providerCashAccountService;

    @Autowired
    public ProviderApiImpl(ProviderService providerService, ProviderCashAccountService providerCashAccountService) {
        this.providerService = providerService;
        this.providerCashAccountService = providerCashAccountService;
    }

    @Override
    public ResponseEntity saveProvider(ProviderDto providerDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", providerDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        ProviderDto providerDtoSaved = providerService.saveProvider(providerDto);
        log.info("The method saveProvider is being executed");
        //return new ResponseEntity(providerDtoSaved, HttpStatus.CREATED);
        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "Provider created successfully ");
        map.put("data", providerDtoSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateProvider(ProviderDto providerDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", providerDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        ProviderDto providerDtoUpdated = providerService.updateProvider(providerDto);
        log.info("The method updateProvider is being executed");
        //return new ResponseEntity(providerDtoUpdated, HttpStatus.OK);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider updated successfully ");
        map.put("data", providerDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findProviderById(Long providerId) {
        Map<String, Object> map = new LinkedHashMap<>();
        ProviderDto providerDtoFound = providerService.findProviderById(providerId);
        log.info("The method findProviderById is being executed");
        //return ResponseEntity.ok(providerDtoFound);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider found successfully ");
        map.put("data", providerDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findProviderByNameofPos(String providerName, Long posId) {
        Map<String, Object> map = new LinkedHashMap<>();
        ProviderDto providerDtoFound = providerService.findProviderByNameofPos(providerName, posId);
        log.info("The method findProviderByNameofPos is being executed");
        //return ResponseEntity.ok(providerDtoFound);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider found successfully ");
        map.put("data", providerDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllProviderofPos(Long posId) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<ProviderDto> providerDtoList = providerService.findAllProviderofPos(posId);
        log.info("The method findAllProviderofPos is being executed");
        //return ResponseEntity.ok(providerDtoList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider list of pointofsale found successfully ");
        map.put("data", providerDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageProviderofPos(Long posId, Optional<Integer> optpagenum,
                                                Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();

        Page<ProviderDto> providerDtoPage = providerService.findPageProviderofPos(posId, pagenum, pagesize);
        log.info("The method findPageProviderofPos is being executed");
        //return ResponseEntity.ok(providerDtoPage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider page of pointofsale found successfully ");
        map.put("data", providerDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteProviderById(Long providerId) {
        Map<String, Object> map = new LinkedHashMap<>();
        /*****
         * Avant de delete un provider il faut d'abord delete son compte
         */
        Boolean delete = providerService.deleteProviderById(providerId);
        log.info("The method deleteProviderById is being executed");
        //return ResponseEntity.ok(delete);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider of pointofsale deleted successfully ");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity saveCashOperation(ProviderCashAccountDto providerCashAccountDto, BindingResult bindingResult1,
                                            ProviderCashOperationDto providerCashOperationDto, BindingResult bindingResult2) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult1.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", providerCashAccountDto, bindingResult1);
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
                    "and the report errors are {}", providerCashOperationDto, bindingResult2);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult2);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        Boolean opSaved = providerCashAccountService.saveCashOperation(providerCashAccountDto, providerCashOperationDto);
        log.info("The method saveCashOperation is being executed");
        //return ResponseEntity.ok(opSaved);
        map.put("status", HttpStatus.CREATED);
        map.put("message", "Provider cash operation created successfully ");
        map.put("data", opSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity saveProviderCashAccount(ProviderCashAccountDto providerCashAccountDto,
                                                  BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", providerCashAccountDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        ProviderCashAccountDto providerCashAccountDtoSaved = providerCashAccountService.
                saveProviderCashAccount(providerCashAccountDto);
        log.info("The method saveProviderCashAccount is being executed");
        //return new ResponseEntity(providerCashAccountDtoSaved, HttpStatus.CREATED);
        map.put("status", HttpStatus.CREATED);
        map.put("message", "Provider cash account created successfully ");
        map.put("data", providerCashAccountDtoSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity findProviderCashAccountById(Long pcaId) {
        Map<String, Object> map = new LinkedHashMap<>();
        ProviderCashAccountDto providerCashAccountDtoFound = providerCashAccountService.findProviderCashAccountById(pcaId);
        log.info("The method findProviderCashAccountById is being executed");
        //return ResponseEntity.ok(providerCashAccountDtoFound);
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider cash account found successfully ");
        map.put("data", providerCashAccountDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteProviderCashAccountById(Long pcaId) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean delete = providerCashAccountService.deleteProviderCashAccountById(pcaId);
        log.info("The method deleteProviderCashAccountById is being executed");
        //return ResponseEntity.ok(delete);
        map.put("status", HttpStatus.OK);
        map.put("message", "Provider cash account deleted successfully ");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }
}
