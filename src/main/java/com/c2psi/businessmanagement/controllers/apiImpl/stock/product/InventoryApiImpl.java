package com.c2psi.businessmanagement.controllers.apiImpl.stock.product;

import com.c2psi.businessmanagement.controllers.api.stock.product.InventoryApi;
import com.c2psi.businessmanagement.dtos.stock.product.InventoryDto;
import com.c2psi.businessmanagement.dtos.stock.product.InventoryLineDto;
import com.c2psi.businessmanagement.services.contracts.stock.product.InventoryLineService;
import com.c2psi.businessmanagement.services.contracts.stock.product.InventoryService;
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
public class InventoryApiImpl implements InventoryApi {
    private InventoryService inventoryService;
    private InventoryLineService inventoryLineService;

    @Autowired
    public InventoryApiImpl(InventoryService inventoryService, InventoryLineService inventoryLineService) {
        this.inventoryService = inventoryService;
        this.inventoryLineService = inventoryLineService;
    }

    @Override
    public ResponseEntity saveInventory(InventoryDto invDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", invDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }
        InventoryDto invDtoSaved = inventoryService.saveInventory(invDto);
        log.info("The method saveInventory is being executed");

        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "Inventory created successfully ");
        map.put("data", invDtoSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateInventory(InventoryDto invDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", invDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }
        InventoryDto invDtoUpdated = inventoryService.updateInventory(invDto);
        log.info("The method updateInventory is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Inventory updated successfully ");
        map.put("data", invDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findInventoryById(Long invId) {
        Map<String, Object> map = new LinkedHashMap<>();

        InventoryDto invDtoFound = inventoryService.findInventoryById(invId);
        log.info("The method findInventoryById is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Inventory found successfully ");
        map.put("data", invDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findInventoryByCodeinPos(String invCode, Long posId) {
        Map<String, Object> map = new LinkedHashMap<>();

        InventoryDto invDtoFound = inventoryService.findInventoryByCodeinPos(invCode, posId);
        log.info("The method findInventoryByCodeinPos is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Inventory found successfully ");
        map.put("data", invDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllInventoryinPosBetween(Long posId, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();

        List<InventoryDto> inventoryDtoList = inventoryService.findAllInventoryinPosBetween(posId, startDate, endDate);
        log.info("The method findAllInventoryinPosBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Inventory list found successfully ");
        map.put("data", inventoryDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageInventoryinPosBetween(Long posId, Instant startDate, Instant endDate,
                                                        Optional<Integer> optpagenum, Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;


        Map<String, Object> map = new LinkedHashMap<>();

        Page<InventoryDto> inventoryDtoPage = inventoryService.findPageInventoryinPosBetween(posId, startDate, endDate,
                pagenum, pagesize);
        log.info("The method findPageInventoryinPosBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Inventory page found successfully ");
        map.put("data", inventoryDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteInventoryById(Long invId) {
        Map<String, Object> map = new LinkedHashMap<>();

        Boolean delete = inventoryService.deleteInventoryById(invId);
        log.info("The method deleteInventoryById is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Inventory deleted successfully ");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity saveInventoryLine(InventoryLineDto invLineDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", invLineDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }
        InventoryLineDto invLineDtoSaved = inventoryLineService.saveInventoryLine(invLineDto);
        log.info("The method saveInventoryLine is being executed");

        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "InventoryLine created successfully ");
        map.put("data", invLineDtoSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateInventoryLine(InventoryLineDto invLineDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", invLineDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }
        InventoryLineDto invLineDtoUpdated = inventoryLineService.updateInventoryLine(invLineDto);
        log.info("The method updateInventoryLine is being executed");

        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "InventoryLine updated successfully ");
        map.put("data", invLineDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity findInventoryLineById(Long invLineId) {
        Map<String, Object> map = new LinkedHashMap<>();

        InventoryLineDto invLineDtoFound = inventoryLineService.findInventoryLineById(invLineId);
        log.info("The method findInventoryById is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "InventoryLine found successfully ");
        map.put("data", invLineDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findInventoryLineByArticleinInv(Long invId, Long artId) {
        Map<String, Object> map = new LinkedHashMap<>();

        InventoryLineDto invLineDtoFound = inventoryLineService.findInventoryLineByArticleinInv(invId, artId);
        log.info("The method findInventoryLineByArticleinInv is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "InventoryLine found successfully ");
        map.put("data", invLineDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteInventoryLineById(Long invLineId) {
        Map<String, Object> map = new LinkedHashMap<>();

        Boolean delete = inventoryLineService.deleteInventoryLineById(invLineId);
        log.info("The method deleteInventoryLineById is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "InventoryLine deleted successfully ");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllInventorylineofInv(Long invId) {
        Map<String, Object> map = new LinkedHashMap<>();

        List<InventoryLineDto> inventoryLineDtoList = inventoryLineService.findAllInventorylineofInv(invId);
        log.info("The method findAllInventorylineofInv is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "InventoryLine list of an inventory found successfully ");
        map.put("data", inventoryLineDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageInventorylineofInv(Long invId, Optional<Integer> optpagenum,
                                                     Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Map<String, Object> map = new LinkedHashMap<>();

        Page<InventoryLineDto> inventoryLineDtoPage = inventoryLineService.findPageInventorylineofInv(invId,
                pagenum, pagesize);
        log.info("The method findPageInventorylineofInv is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "InventoryLine page of an inventory found successfully ");
        map.put("data", inventoryLineDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }
}
