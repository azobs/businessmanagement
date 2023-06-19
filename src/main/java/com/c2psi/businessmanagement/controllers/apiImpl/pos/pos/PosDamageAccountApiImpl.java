package com.c2psi.businessmanagement.controllers.apiImpl.pos.pos;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.controllers.api.pos.pos.PosDamageAccountApi;
import com.c2psi.businessmanagement.dtos.pos.pos.PosDamageAccountDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosDamageOperationDto;
import com.c2psi.businessmanagement.services.contracts.pos.pos.PosDamageAccountService;
import com.c2psi.businessmanagement.services.contracts.pos.pos.PosDamageOperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class PosDamageAccountApiImpl implements PosDamageAccountApi {

    private PosDamageAccountService posDamageAccountService;
    private PosDamageOperationService posDamageOperationService;

    @Autowired
    public PosDamageAccountApiImpl(PosDamageAccountService posDamageAccountService,
                                   PosDamageOperationService posDamageOperationService) {
        this.posDamageAccountService = posDamageAccountService;
        this.posDamageOperationService = posDamageOperationService;
    }

    @Override
    public ResponseEntity savePosDamageAccount(PosDamageAccountDto pdamaccDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", pdamaccDto, bindingResult);
            return ResponseEntity.badRequest().body(bindingResult.toString());
        }

        PosDamageAccountDto pdamaccDtoSaved = posDamageAccountService.savePosDamageAccount(pdamaccDto);
        log.info("The method savePosDamageAccount is being executed");
        return new ResponseEntity(pdamaccDtoSaved, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity findPosDamageAccountById(Long pdamaccId) {
        PosDamageAccountDto pdamaccDtoFound = posDamageAccountService.findPosDamageAccountById(pdamaccId);
        log.info("The method findPosDamageAccountById is being executed");
        return ResponseEntity.ok(pdamaccDtoFound);
    }

    @Override
    public ResponseEntity findPosDamageAccountofArticleInPos(Long artId, Long posId) {
        PosDamageAccountDto pdamaccDtoFound = posDamageAccountService.findPosDamageAccountofArticleInPos(artId, posId);
        log.info("The method findPosDamageAccountofArticleInPos is being executed");
        return ResponseEntity.ok(pdamaccDtoFound);
    }

    @Override
    public ResponseEntity findAllDamageAccountInPos(Long posId) {
        List<PosDamageAccountDto> pdamaccDtoList = posDamageAccountService.findAllDamageAccountInPos(posId);
        log.info("The method findAllDamageAccountInPos is being executed");
        return ResponseEntity.ok(pdamaccDtoList);
    }

    @Override
    public ResponseEntity findPageDamageAccountInPos(Long posId, Optional<Integer> optpagenum,
                                                     Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Page<PosDamageAccountDto> pdamaccDtoPage = posDamageAccountService.findPageDamageAccountInPos(posId,
                pagenum, pagesize);
        log.info("The method findPageDamageAccountInPos is being executed");
        return ResponseEntity.ok(pdamaccDtoPage);
    }

    @Override
    public ResponseEntity findAllDamageAccountofArticle(Long artId) {
        List<PosDamageAccountDto> pdamaccDtoList = posDamageAccountService.findAllDamageAccountofArticle(artId);
        log.info("The method findAllDamageAccountofArticle is being executed");
        return ResponseEntity.ok(pdamaccDtoList);
    }

    @Override
    public ResponseEntity findPageDamageAccountofArticle(Long artId, Optional<Integer> optpagenum,
                                                         Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Page<PosDamageAccountDto> pdamaccDtoPage = posDamageAccountService.findPageDamageAccountofArticle(artId,
                pagenum, pagesize);
        log.info("The method findPageDamageAccountofArticle is being executed");
        return ResponseEntity.ok(pdamaccDtoPage);
    }

    @Override
    public ResponseEntity deletePosDamageAccountById(Long pdamaccId) {
        Boolean delete = posDamageAccountService.deletePosDamageAccountById(pdamaccId);
        log.info("The method deletePosDamageAccountById is being executed");
        return ResponseEntity.ok(delete);
    }

    @Override
    public ResponseEntity saveDamageOperation(PosDamageAccountDto posdamaccDto, BindingResult bindingResult1,
                                              PosDamageOperationDto posdamopDto, BindingResult bindingResult2) {
        if (bindingResult1.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", posdamaccDto, bindingResult1);
            return ResponseEntity.badRequest().body(bindingResult1.toString());
        }
        if (bindingResult2.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", posdamopDto, bindingResult2);
            return ResponseEntity.badRequest().body(bindingResult2.toString());
        }
        Boolean saveOp = posDamageAccountService.saveDamageOperation(posdamaccDto, posdamopDto);
        log.info("The method saveDamageOperation is being executed");
        return ResponseEntity.ok(saveOp);
    }

    @Override
    public ResponseEntity updatePosDamageOperation(PosDamageOperationDto pdamopDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", pdamopDto, bindingResult);
            return ResponseEntity.badRequest().body(bindingResult.toString());
        }

        PosDamageOperationDto pdamopDtoUpdated = posDamageOperationService.updatePosDamageOperation(pdamopDto);
        log.info("The method updatePosDamageOperation is being executed");
        return new ResponseEntity(pdamopDtoUpdated, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deletePosDamageOperationById(Long pdamopId) {
        Boolean delete = posDamageOperationService.deletePosDamageOperationById(pdamopId);
        log.info("The method deletePosDamageOperationById is being executed");
        return ResponseEntity.ok(delete);
    }

    @Override
    public ResponseEntity findAllPosDamageOperation(Long pdamopId) {
        List<PosDamageOperationDto> posdamopList = posDamageOperationService.findAllPosDamageOperation(pdamopId);
        log.info("The method findAllPosDamageOperation is being executed");
        return ResponseEntity.ok(posdamopList);
    }

    @Override
    public ResponseEntity findAllPosDamageOperationofType(Long pdamopId, OperationType optype) {

        List<PosDamageOperationDto> posdamopList = posDamageOperationService.findAllPosDamageOperationofType(pdamopId,
                optype);
        log.info("The method findAllPosDamageOperationofType is being executed");
        return ResponseEntity.ok(posdamopList);
    }

    @Override
    public ResponseEntity findPagePosDamageOperation(Long pdamopId, Optional<Integer> optpagenum,
                                                     Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Page<PosDamageOperationDto> posdamopPage = posDamageOperationService.findPagePosDamageOperation(pdamopId,
                pagenum, pagesize);
        log.info("The method findPagePosDamageOperation is being executed");
        return ResponseEntity.ok(posdamopPage);
    }

    @Override
    public ResponseEntity findPagePosDamageOperationofType(Long pdamopId, OperationType optype,
                                                           Optional<Integer> optpagenum,
                                                           Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Page<PosDamageOperationDto> posdamopPage = posDamageOperationService.findPagePosDamageOperationofType(pdamopId,
                optype, pagenum, pagesize);
        log.info("The method findPagePosDamageOperationofType is being executed");
        return ResponseEntity.ok(posdamopPage);
    }

    @Override
    public ResponseEntity findAllPosDamageOperationBetween(Long pdamopId, Instant startDate, Instant endDate) {
        List<PosDamageOperationDto> posdamOpListBetween = posDamageOperationService.findAllPosDamageOperationBetween(
                pdamopId, startDate, endDate);
        log.info("The method findAllPosDamageOperationBetween is being executed");
        return ResponseEntity.ok(posdamOpListBetween);
    }

    @Override
    public ResponseEntity findPagePosDamageOperationBetween(Long pdamopId, Instant startDate, Instant endDate,
                                                            Optional<Integer> optpagenum,
                                                            Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<PosDamageOperationDto> posdamOpPageBetween = posDamageOperationService.findPagePosDamageOperationBetween(
                pdamopId, startDate, endDate, pagenum, pagesize);
        log.info("The method findPagePosDamageOperationBetween is being executed");
        return ResponseEntity.ok(posdamOpPageBetween);
    }

    @Override
    public ResponseEntity findAllPosDamageOperationofTypeBetween(Long pdamopId, OperationType optype, Instant startDate,
                                                          Instant endDate) {
        List<PosDamageOperationDto> posdamOpListBetween = posDamageOperationService.findAllPosDamageOperationofTypeBetween(
                pdamopId, optype, startDate, endDate);
        log.info("The method findAllPosDamageOperationofTypeBetween is being executed");
        return ResponseEntity.ok(posdamOpListBetween);
    }

    @Override
    public ResponseEntity findPagePosDamageOperationofTypeBetween(Long pdamopId, OperationType optype, Instant startDate,
                                                           Instant endDate, Optional<Integer> optpagenum,
                                                           Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<PosDamageOperationDto> posdamOpPageBetween = posDamageOperationService.findPagePosDamageOperationofTypeBetween(
                pdamopId, optype, startDate, endDate, pagenum, pagesize);
        log.info("The method findPagePosDamageOperationofTypeBetween is being executed");
        return ResponseEntity.ok(posdamOpPageBetween);
    }
}
