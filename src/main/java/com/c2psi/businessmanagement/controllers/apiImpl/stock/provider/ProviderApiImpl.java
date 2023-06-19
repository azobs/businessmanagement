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

import java.util.List;
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
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", providerDto, bindingResult);
            return ResponseEntity.badRequest().body(bindingResult.toString());
        }
        ProviderDto providerDtoSaved = providerService.saveProvider(providerDto);
        log.info("The method saveProvider is being executed");
        return new ResponseEntity(providerDtoSaved, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateProvider(ProviderDto providerDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", providerDto, bindingResult);
            return ResponseEntity.badRequest().body(bindingResult.toString());
        }
        ProviderDto providerDtoUpdated = providerService.updateProvider(providerDto);
        log.info("The method updateProvider is being executed");
        return new ResponseEntity(providerDtoUpdated, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findProviderById(Long providerId) {
        ProviderDto providerDtoFound = providerService.findProviderById(providerId);
        log.info("The method findProviderById is being executed");
        return ResponseEntity.ok(providerDtoFound);
    }

    @Override
    public ResponseEntity findProviderByNameofPos(String providerName, Long posId) {
        ProviderDto providerDtoFound = providerService.findProviderByNameofPos(providerName, posId);
        log.info("The method findProviderByNameofPos is being executed");
        return ResponseEntity.ok(providerDtoFound);
    }

    @Override
    public ResponseEntity findAllProviderofPos(Long posId) {
        List<ProviderDto> providerDtoList = providerService.findAllProviderofPos(posId);
        log.info("The method findAllProviderofPos is being executed");
        return ResponseEntity.ok(providerDtoList);
    }

    @Override
    public ResponseEntity findPageProviderofPos(Long posId, Optional<Integer> optpagenum,
                                                Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Page<ProviderDto> providerDtoPage = providerService.findPageProviderofPos(posId, pagenum, pagesize);
        log.info("The method findPageProviderofPos is being executed");
        return ResponseEntity.ok(providerDtoPage);
    }

    @Override
    public ResponseEntity deleteProviderById(Long providerId) {
        Boolean delete = providerService.deleteProviderById(providerId);
        log.info("The method deleteProviderById is being executed");
        return ResponseEntity.ok(delete);
    }

    @Override
    public ResponseEntity saveCashOperation(ProviderCashAccountDto providerCashAccountDto, BindingResult bindingResult1,
                                            ProviderCashOperationDto providerCashOperationDto, BindingResult bindingResult2) {
        if (bindingResult1.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", providerCashAccountDto, bindingResult1);
            return ResponseEntity.badRequest().body(bindingResult1.toString());
        }

        if (bindingResult2.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", providerCashOperationDto, bindingResult2);
            return ResponseEntity.badRequest().body(bindingResult2.toString());
        }

        Boolean opSaved = providerCashAccountService.saveCashOperation(providerCashAccountDto, providerCashOperationDto);
        log.info("The method saveCashOperation is being executed");
        return ResponseEntity.ok(opSaved);
    }

    @Override
    public ResponseEntity saveProviderCashAccount(ProviderCashAccountDto providerCashAccountDto,
                                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", providerCashAccountDto, bindingResult);
            return ResponseEntity.badRequest().body(bindingResult.toString());
        }
        ProviderCashAccountDto providerCashAccountDtoSaved = providerCashAccountService.
                saveProviderCashAccount(providerCashAccountDto);
        log.info("The method saveProviderCashAccount is being executed");
        return new ResponseEntity(providerCashAccountDtoSaved, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity findProviderCashAccountById(Long pcaId) {
        ProviderCashAccountDto providerCashAccountDtoFound = providerCashAccountService.findProviderCashAccountById(pcaId);
        log.info("The method findProviderCashAccountById is being executed");
        return ResponseEntity.ok(providerCashAccountDtoFound);
    }

    @Override
    public ResponseEntity deleteProviderCashAccountById(Long pcaId) {
        Boolean delete = providerCashAccountService.deleteProviderCashAccountById(pcaId);
        log.info("The method deleteProviderCashAccountById is being executed");
        return ResponseEntity.ok(delete);
    }
}
