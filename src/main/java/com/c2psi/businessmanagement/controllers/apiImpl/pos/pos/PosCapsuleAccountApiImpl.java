package com.c2psi.businessmanagement.controllers.apiImpl.pos.pos;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.controllers.api.pos.pos.PosCapsuleAccountApi;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCapsuleAccountDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCapsuleOperationDto;
import com.c2psi.businessmanagement.services.contracts.pos.pos.PosCapsuleAccountService;
import com.c2psi.businessmanagement.services.contracts.pos.pos.PosCapsuleOperationService;
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
public class PosCapsuleAccountApiImpl implements PosCapsuleAccountApi {
    private PosCapsuleAccountService posCapsuleAccountService;
    private PosCapsuleOperationService posCapsuleOperationService;

    @Autowired
    public PosCapsuleAccountApiImpl(PosCapsuleAccountService posCapsuleAccountService,
                                    PosCapsuleOperationService posCapsuleOperationService) {
        this.posCapsuleAccountService = posCapsuleAccountService;
        this.posCapsuleOperationService = posCapsuleOperationService;
    }

    @Override
    public ResponseEntity savePosCapsuleAccount(PosCapsuleAccountDto pcapaccDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", pcapaccDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        PosCapsuleAccountDto pcapaccDtoSaved = posCapsuleAccountService.savePosCapsuleAccount(pcapaccDto);
        log.info("The method savePosCapsuleAccount is being executed");
        //return new ResponseEntity(pcapaccDtoSaved, HttpStatus.CREATED);
        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "Pos capsule account created successfully");
        map.put("data", pcapaccDtoSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity findPosCapsuleAccountById(Long pcapaccId) {
        Map<String, Object> map = new LinkedHashMap<>();
        PosCapsuleAccountDto pcapaccDtoFound = posCapsuleAccountService.findPosCapsuleAccountById(pcapaccId);
        log.info("The method findPosCapsuleAccountById is being executed");
        //return ResponseEntity.ok(pcapaccDtoFound);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos capsule account found successfully by Id");
        map.put("data", pcapaccDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPosCapsuleAccountofArticleInPos(Long artId, Long posId) {
        Map<String, Object> map = new LinkedHashMap<>();
        PosCapsuleAccountDto pcapaccDtoFound = posCapsuleAccountService.
                findPosCapsuleAccountofArticleInPos(artId, posId);
        log.info("The method findPosCapsuleAccountById is being executed");
        //return ResponseEntity.ok(pcapaccDtoFound);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos capsule account found successfully for an article in a pointofsale");
        map.put("data", pcapaccDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCapsuleAccountInPos(Long posId) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<PosCapsuleAccountDto> posCapsuleAccountDtoList = posCapsuleAccountService.findAllCapsuleAccountInPos(posId);
        log.info("The method findAllCapsuleAccountInPos is being executed");
        //return ResponseEntity.ok(posCapsuleAccountDtoList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos capsule account list found successfully");
        map.put("data", posCapsuleAccountDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCapsuleAccountInPos(Long posId, Optional<Integer> optpagenum,
                                                      Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();

        Page<PosCapsuleAccountDto> posCapsuleAccountDtoPage = posCapsuleAccountService.findPageCapsuleAccountInPos(
                posId, pagenum, pagesize);
        log.info("The method findPageCapsuleAccountInPos is being executed");
        //return ResponseEntity.ok(posCapsuleAccountDtoPage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos capsule account page found successfully");
        map.put("data", posCapsuleAccountDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCapsuleAccountofArticle(Long artId) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<PosCapsuleAccountDto> posCapsuleAccountDtoList = posCapsuleAccountService.findAllCapsuleAccountofArticle(artId);
        log.info("The method findAllCapsuleAccountInPos is being executed");
        //return ResponseEntity.ok(posCapsuleAccountDtoList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos capsule account list found successfully");
        map.put("data", posCapsuleAccountDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCapsuleAccountofArticle(Long artId, Optional<Integer> optpagenum,
                                                          Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();

        Page<PosCapsuleAccountDto> posCapsuleAccountDtoPage = posCapsuleAccountService.findPageCapsuleAccountofArticle(
                artId, pagenum, pagesize);
        log.info("The method findPageCapsuleAccountInPos is being executed");
        //return ResponseEntity.ok(posCapsuleAccountDtoPage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos capsule account page found successfully");
        map.put("data", posCapsuleAccountDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deletePosCapsuleAccountById(Long pcapaccId) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean delete = posCapsuleAccountService.deletePosCapsuleAccountById(pcapaccId);
        log.info("The method deletePosCapsuleAccountById is being executed");
        //return ResponseEntity.ok(delete);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos capsule account deleted successfully");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity saveCapsuleOperation(PosCapsuleAccountDto poscapaccDto, BindingResult bindingResult1,
                                               PosCapsuleOperationDto poscapopDto, BindingResult bindingResult2) {
        Map<String, Object> map = new LinkedHashMap<>();

        Boolean saveOperation = posCapsuleAccountService.saveCapsuleOperation(poscapaccDto, poscapopDto);
        log.info("The method saveCapsuleOperation is being executed");
        //return ResponseEntity.ok(saveOperation);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos capsule operation saved successfully");
        map.put("data", saveOperation);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity updatePosCapsuleOperation(PosCapsuleOperationDto pcapopDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", pcapopDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }
        PosCapsuleOperationDto pcapopDtoSaved = posCapsuleOperationService.updatePosCapsuleOperation(pcapopDto);
        log.info("The method updatePosCapsuleOperation is being executed");
        //return new ResponseEntity(pcapopDtoSaved, HttpStatus.OK);
        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "Pos capsule operation updated successfully");
        map.put("data", pcapopDtoSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity deletePosCapsuleOperationById(Long pcapopId) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean delete = posCapsuleOperationService.deletePosCapsuleOperationById(pcapopId);
        log.info("The method deletePosCapsuleOperationById is being executed");
        //return ResponseEntity.ok(delete);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos capsule operation deleted successfully");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllPosCapsuleOperation(Long pcapopId) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<PosCapsuleOperationDto> posCapsOpDtoList = posCapsuleOperationService.findAllPosCapsuleOperation(pcapopId);
        log.info("The method findAllPosCapsuleOperation is being executed");
        //return ResponseEntity.ok(posCapsOpDtoList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos capsule operation list found successfully");
        map.put("data", posCapsOpDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllPosCapsuleOperationofType(Long pcapopId, OperationType optype) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<PosCapsuleOperationDto> posCapsOpDtoList = posCapsuleOperationService.findAllPosCapsuleOperationofType(
                pcapopId, optype);
        log.info("The method findAllPosCapsuleOperationofType is being executed");
        //return ResponseEntity.ok(posCapsOpDtoList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos capsule operation list found successfully");
        map.put("data", posCapsOpDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPagePosCapsuleOperation(Long pcapopId, Optional<Integer> optpagenum,
                                                      Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();

        Page<PosCapsuleOperationDto> posCapsOpDtoPage = posCapsuleOperationService.findPagePosCapsuleOperation(
                pcapopId, pagenum, pagesize);
        log.info("The method findPagePosCapsuleOperation is being executed");
        //return ResponseEntity.ok(posCapsOpDtoPage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos capsule operation page found successfully");
        map.put("data", posCapsOpDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPagePosCapsuleOperationofType(Long pcapopId, OperationType optype,
                                                            Optional<Integer> optpagenum,
                                                            Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();

        Page<PosCapsuleOperationDto> posCapsOpDtoPage = posCapsuleOperationService.findPagePosCapsuleOperationofType(
                pcapopId, optype, pagenum, pagesize);
        log.info("The method findPagePosCapsuleOperation is being executed");
        //return ResponseEntity.ok(posCapsOpDtoPage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos capsule operation page found successfully");
        map.put("data", posCapsOpDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllPosCapsuleOperationBetween(Long pcapopId, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<PosCapsuleOperationDto> posCapOpDtoList = posCapsuleOperationService.findAllPosCapsuleOperationBetween(
                pcapopId, startDate, endDate);
        log.info("The method findAllPosCapsuleOperationBetween is being executed");
        //return ResponseEntity.ok(posCapOpDtoList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos capsule operation page found successfully");
        map.put("data", posCapOpDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPagePosCapsuleOperationBetween(Long pcapopId, Instant startDate, Instant endDate,
                                                             Optional<Integer> optpagenum,
                                                             Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();

        Page<PosCapsuleOperationDto> posCapOpDtoPage = posCapsuleOperationService.findPagePosCapsuleOperationBetween(
                pcapopId, startDate, endDate, pagenum, pagesize);
        log.info("The method findPagePosCapsuleOperationBetween is being executed");
        //return ResponseEntity.ok(posCapOpDtoPage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos capsule operation page found successfully");
        map.put("data", posCapOpDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllPosCapsuleOperationofTypeBetween(Long pcapopId, OperationType optype, Instant startDate,
                                                           Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<PosCapsuleOperationDto> posCapOpDtoList = posCapsuleOperationService.
                findAllPosCapsuleOperationofTypeBetween(pcapopId, optype, startDate, endDate);
        log.info("The method findAllPosCapsuleOperationBetween is being executed");
        //return ResponseEntity.ok(posCapOpDtoList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos capsule operation list found successfully");
        map.put("data", posCapOpDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPagePosCapsuleOperationofTypeBetween(Long pcapopId, OperationType optype, Instant startDate,
                                                            Instant endDate, Optional<Integer> optpagenum,
                                                            Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Page<PosCapsuleOperationDto> posCapOpDtoPage = posCapsuleOperationService.
                findPagePosCapsuleOperationofTypeBetween(pcapopId, optype, startDate, endDate, pagenum, pagesize);
        log.info("The method findPagePosCapsuleOperationBetween is being executed");
        //return ResponseEntity.ok(posCapOpDtoPage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Pos capsule operation page found successfully");
        map.put("data", posCapOpDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }
}
