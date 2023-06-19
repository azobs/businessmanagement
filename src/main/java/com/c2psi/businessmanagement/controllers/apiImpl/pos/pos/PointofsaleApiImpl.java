package com.c2psi.businessmanagement.controllers.apiImpl.pos.pos;

import com.c2psi.businessmanagement.controllers.api.pos.pos.PointofsaleApi;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCashAccountDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCashOperationDto;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyDto;
import com.c2psi.businessmanagement.services.contracts.pos.pos.PointofsaleService;
import com.c2psi.businessmanagement.services.contracts.pos.pos.PosCashAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@RestController
@Slf4j
public class PointofsaleApiImpl implements PointofsaleApi {
    private PointofsaleService pointofsaleService;
    private PosCashAccountService posCashAccountService;

    @Autowired
    public PointofsaleApiImpl(PointofsaleService pointofsaleService, PosCashAccountService posCashAccountService) {
        this.pointofsaleService = pointofsaleService;
        this.posCashAccountService = posCashAccountService;
    }

    @Override
    public ResponseEntity getTurnover(Long posId, Instant startDate, Instant endDate) {
        return null;
    }

    @Override
    public ResponseEntity findAllEmployeofPos(Long posId) {
        return null;
    }

    @Override
    public ResponseEntity findAllProviderofPos(Long posId) {
        return null;
    }

    @Override
    public ResponseEntity getTotalCashofPos(Long posId) {
        BigDecimal cashAccountBalance = pointofsaleService.getTotalCashofPos(posId);
        log.info("The amount in the pointofsale cash account");
        return ResponseEntity.ok(cashAccountBalance);
    }

    @Override
    public ResponseEntity getNumberofDamageofPos(Long posId) {
        return null;
    }

    @Override
    public ResponseEntity getNumberofDamageofPos(Long posId, Long artId) {
        return null;
    }

    @Override
    public ResponseEntity getNumberofCapsuleofPos(Long posId) {
        return null;
    }

    @Override
    public ResponseEntity getNumberofCapsuleofPos(Long posId, Long artId) {
        return null;
    }

    @Override
    public ResponseEntity getNumberofPackagingofPos(Long posId) {
        return null;
    }

    @Override
    public ResponseEntity getNumberofPackagingofPos(Long posId, Long providerId) {
        return null;
    }

    @Override
    public ResponseEntity savePointofsale(PointofsaleDto posDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", posDto, bindingResult);
            return ResponseEntity.badRequest().body(bindingResult.toString());
        }

        /********************************************************************
         * L'adresse etant Embedded alors on n'a pas besoin de l'enregistre
         * de facon separer
         */

        /**********************************************************************************
         * Logiquement l'entreprise doit etre selectionne ou ouverte avant l'enregistrement
         * du  Pointofsale
         */

        /**********************************************************************************
         * Logiquement le Currency doit etre selectionne dans le formulaire d'enregistrement
         * du pointofsale
         */

        PointofsaleDto pointofsaleDtoSaved = pointofsaleService.savePointofsale(posDto);
        log.info("The Pointofsale has been successfully registered in the DB {}", pointofsaleDtoSaved);

        return new ResponseEntity(pointofsaleDtoSaved, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updatePointofsale(PointofsaleDto posDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", posDto, bindingResult);
            return ResponseEntity.badRequest().body(bindingResult.toString());
        }

        PointofsaleDto pointofsaleDtoUpdated = pointofsaleService.updatePointofsale(posDto);
        log.info("Entity Enterprise updated successfully {} ", pointofsaleDtoUpdated);
        return ResponseEntity.ok(pointofsaleDtoUpdated);
    }

    @Override
    public ResponseEntity findPointofsaleById(Long posId) {
        PointofsaleDto pointofsaleDtoFound = pointofsaleService.findPointofsaleById(posId);
        log.info("Entity Pointofsale found successfully {} with the ID {}", pointofsaleDtoFound, posId);
        return ResponseEntity.ok(pointofsaleDtoFound);
    }

    @Override
    public ResponseEntity deletePosById(Long posId) {
        Boolean delete = pointofsaleService.deletePosById(posId);
        log.info("Pointofsale deleted successfully {} with the Id {}", delete, posId);
        return ResponseEntity.ok(delete);
    }

    @Override
    public ResponseEntity deletePosInEnterpriseByName(String posName, Long entId) {
        Boolean delete = pointofsaleService.deletePosInEnterpriseByName(posName, entId);
        log.info("Pointofsale deleted successfully with the Id {}", delete);
        return ResponseEntity.ok(delete);
    }

    @Override
    public ResponseEntity listofConvertibleCurrency(Long posId) {
        List<CurrencyDto> currencyDtoList = pointofsaleService.listofConvertibleCurrency(posId);
        log.info("The convertible list of currency with the default currency of the posId is found ");
        return ResponseEntity.ok(currencyDtoList);
    }

    @Override
    public ResponseEntity getDefaultCurrency(Long posId) {
        CurrencyDto currencyDto = pointofsaleService.getDefaultCurrency(posId);
        log.info("The default currency of the pointofsale with id {}", posId);
        return ResponseEntity.ok(currencyDto);
    }

    @Override
    public ResponseEntity saveCashOperation(PosCashAccountDto posCashAccountDto, BindingResult bindingResult1,
                                            PosCashOperationDto posCashOperationDto, BindingResult bindingResult2) {

        if (bindingResult1.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", posCashAccountDto, bindingResult1);
            return ResponseEntity.badRequest().body(bindingResult1.toString());
        }
        if (bindingResult2.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", posCashOperationDto, bindingResult2);
            return ResponseEntity.badRequest().body(bindingResult2.toString());
        }
        Boolean opSaved = posCashAccountService.saveCashOperation(posCashAccountDto, posCashOperationDto);
        log.info("The method saveCashOperation is being executed");
        return ResponseEntity.ok(opSaved);
    }

    @Override
    public ResponseEntity deletePosCashAccountById(Long pcaId) {
        Boolean delete = posCashAccountService.deletePosCashAccountById(pcaId);
        log.info("The method deletePosCashAccountById is being executed");
        return ResponseEntity.ok(delete);
    }

    @Override
    public ResponseEntity findPosCashAccountById(Long pcaId) {
        PosCashAccountDto posCashAccountDtoFound = posCashAccountService.findPosCashAccountById(pcaId);
        log.info("The method findPosCashAccountById is being executed");
        return ResponseEntity.ok(posCashAccountDtoFound);
    }
}
