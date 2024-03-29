package com.c2psi.businessmanagement.controllers.apiImpl.pos.pos;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.controllers.api.pos.pos.PointofsaleApi;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCashAccountDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCashOperationDto;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyDto;
import com.c2psi.businessmanagement.services.contracts.pos.pos.PointofsaleService;
import com.c2psi.businessmanagement.services.contracts.pos.pos.PosCashAccountService;
import com.c2psi.businessmanagement.services.contracts.pos.pos.PosCashOperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
public class PointofsaleApiImpl implements PointofsaleApi {
    private PointofsaleService pointofsaleService;
    private PosCashAccountService posCashAccountService;
    private PosCashOperationService posCashOperationService;

    @Autowired
    public PointofsaleApiImpl(PointofsaleService pointofsaleService, PosCashAccountService posCashAccountService,
                              PosCashOperationService posCashOperationService) {
        this.pointofsaleService = pointofsaleService;
        this.posCashAccountService = posCashAccountService;
        this.posCashOperationService = posCashOperationService;
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
        Map<String, Object> map = new LinkedHashMap<>();
        BigDecimal cashAccountBalance = pointofsaleService.getTotalCashofPos(posId);
        log.info("The amount in the pointofsale cash account");
        //return ResponseEntity.ok(cashAccountBalance);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Total cash of a pointofsale successfully computed");
        map.put("data", cashAccountBalance);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
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

        Map<String, Object> map = new LinkedHashMap<>();



        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", posDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
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

        //return new ResponseEntity(pointofsaleDtoSaved, HttpStatus.CREATED);
        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "Pointofsale created successfully");
        map.put("data", pointofsaleDtoSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updatePointofsale(PointofsaleDto posDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", posDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        PointofsaleDto pointofsaleDtoUpdated = pointofsaleService.updatePointofsale(posDto);
        log.info("Entity Enterprise updated successfully {} ", pointofsaleDtoUpdated);
        //return ResponseEntity.ok(pointofsaleDtoUpdated);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pointofsale updated successfully");
        map.put("data", pointofsaleDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPointofsaleById(Long posId) {
        Map<String, Object> map = new LinkedHashMap<>();
        PointofsaleDto pointofsaleDtoFound = pointofsaleService.findPointofsaleById(posId);
        log.info("Entity Pointofsale found successfully {} with the ID {}", pointofsaleDtoFound, posId);
        //return ResponseEntity.ok(pointofsaleDtoFound);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pointofsale found successfully");
        map.put("data", pointofsaleDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deletePosById(Long posId) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean delete = pointofsaleService.deletePosById(posId);
        log.info("Pointofsale deleted successfully {} with the Id {}", delete, posId);
        //return ResponseEntity.ok(delete);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pointofsale successfully deleted");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deletePosInEnterpriseByName(String posName, Long entId) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean delete = pointofsaleService.deletePosInEnterpriseByName(posName, entId);
        log.info("Pointofsale deleted successfully with the Id {}", delete);
        //return ResponseEntity.ok(delete);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pointofsale successfully deleted by Name");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity listofConvertibleCurrency(Long posId) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CurrencyDto> currencyDtoList = pointofsaleService.listofConvertibleCurrency(posId);
        log.info("The convertible list of currency with the default currency of the posId is found ");
        //return ResponseEntity.ok(currencyDtoList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "List of Pointofsale currency successfully found");
        map.put("data", currencyDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity getDefaultCurrency(Long posId) {
        Map<String, Object> map = new LinkedHashMap<>();
        CurrencyDto currencyDto = pointofsaleService.getDefaultCurrency(posId);
        log.info("The default currency of the pointofsale with id {}", posId);
        //return ResponseEntity.ok(currencyDto);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Default currency of the pointofsale successfully found");
        map.put("data", currencyDto);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity saveCashOperation(PosCashOperationDto posCashOperationDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", posCashOperationDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        Boolean opSaved = posCashAccountService.saveCashOperation(posCashOperationDto);
        log.info("The method saveCashOperation is being executed");
        //return ResponseEntity.ok(opSaved);
        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "The cash operation is successfully created");
        map.put("data", opSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity deletePosCashAccountById(Long pcaId) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean delete = posCashAccountService.deletePosCashAccountById(pcaId);
        log.info("The method deletePosCashAccountById is being executed");
        //return ResponseEntity.ok(delete);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "The cash account is successfully deleted by Id");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPosCashAccountById(Long pcaId) {
        Map<String, Object> map = new LinkedHashMap<>();
        PosCashAccountDto posCashAccountDtoFound = posCashAccountService.findPosCashAccountById(pcaId);
        log.info("The method findPosCashAccountById is being executed");
        //return ResponseEntity.ok(posCashAccountDtoFound);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "The cash account of Pointofsale is successfully found");
        map.put("data", posCashAccountDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity updatePosCashOperation(PosCashOperationDto posCashOpDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", posCashOpDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        PosCashOperationDto posCashOpDtoUpdated = posCashOperationService.updatePosCashOperation(posCashOpDto);
        log.info("The method updatePosCashOperation is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "The cash account operation of Pointofsale is successfully updated");
        map.put("data", posCashOpDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deletePosCashOperationById(Long pcopId) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean delete = posCashOperationService.deletePosCashOperationById(pcopId);
        log.info("The method deletePosCashOperationById is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "The cash account operation of Pointofsale is successfully deleted");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPosCashOperationById(Long pcopId) {
        Map<String, Object> map = new LinkedHashMap<>();

        PosCashOperationDto posCashOpDtFound = posCashOperationService.findPosCashOperationById(pcopId);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "The cash account operation of Pointofsale is successfully found");
        map.put("data", posCashOpDtFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllPosCashOperation(Long pcaId) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<PosCashOperationDto> posCashOperationDtoList = posCashOperationService.findAllPosCashOperation(pcaId);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "The cash account operation list of Pointofsale is successfully found");
        map.put("data", posCashOperationDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPagePosCashOperation(Long pcaId, Optional<Integer> optpagenum,
                                                   Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();
        Page<PosCashOperationDto> posCashOperationDtoPage = posCashOperationService.findPagePosCashOperation(pcaId,
                pagenum, pagesize);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "The cash account operation page of Pointofsale is successfully found");
        map.put("data", posCashOperationDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllPosCashOperationBetween(Long pcaId, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<PosCashOperationDto> posCashOperationDtoList = posCashOperationService.findAllPosCashOperationBetween(pcaId,
                startDate, endDate);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "The cash account operation list of Pointofsale is successfully found");
        map.put("data", posCashOperationDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPagePosCashOperationBetween(Long pcaId, Instant startDate, Instant endDate,
                                                          Optional<Integer> optpagenum, Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();
        Page<PosCashOperationDto> posCashOperationDtoPage = posCashOperationService.findPagePosCashOperationBetween(pcaId,
                startDate, endDate, pagenum, pagesize);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "The cash account operation page of Pointofsale is successfully found");
        map.put("data", posCashOperationDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllPosCashOperationofTypeBetween(Long pcaId, OperationType opType, Instant startDate,
                                                               Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<PosCashOperationDto> posCashOperationDtoList = posCashOperationService.findAllPosCashOperationofTypeBetween(pcaId,
                opType, startDate, endDate);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "The cash account operation list of Pointofsale is successfully found");
        map.put("data", posCashOperationDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPagePosCashOperationofTypeBetween(Long pcaId, OperationType opType, Instant startDate,
                                                                Instant endDate, Optional<Integer> optpagenum,
                                                                Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();
        Page<PosCashOperationDto> posCashOperationDtoPage = posCashOperationService.findPagePosCashOperationofTypeBetween(pcaId,
                opType, startDate, endDate, pagenum, pagesize);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "The cash account operation page of Pointofsale is successfully found");
        map.put("data", posCashOperationDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }
}
