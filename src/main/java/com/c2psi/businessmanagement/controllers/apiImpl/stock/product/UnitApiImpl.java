package com.c2psi.businessmanagement.controllers.apiImpl.stock.product;

import com.c2psi.businessmanagement.controllers.api.stock.product.UnitApi;
import com.c2psi.businessmanagement.dtos.stock.product.UnitConversionDto;
import com.c2psi.businessmanagement.dtos.stock.product.UnitDto;
import com.c2psi.businessmanagement.services.contracts.stock.product.UnitConversionService;
import com.c2psi.businessmanagement.services.contracts.stock.product.UnitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
public class UnitApiImpl implements UnitApi {
    private UnitService unitService;
    private UnitConversionService unitconvService;

    @Autowired
    public UnitApiImpl(UnitService unitService, UnitConversionService unitconvService) {
        this.unitService = unitService;
        this.unitconvService = unitconvService;
    }

    @Override
    public ResponseEntity saveUnit(UnitDto unitDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", unitDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        UnitDto unitDtoSaved = unitService.saveUnit(unitDto);
        log.info("The process of saving unit for product is launch");
        //return new ResponseEntity(unitDtoSaved, HttpStatus.CREATED);
        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "Unit created successfully ");
        map.put("data", unitDtoSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateUnit(UnitDto unitDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", unitDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        UnitDto unitDtoUpdated = unitService.updateUnit(unitDto);
        log.info("The process of updating unit for product is launch");
        //return new ResponseEntity(unitDtoUpdated, HttpStatus.OK);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Unit Updated successfully ");
        map.put("data", unitDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findUnitById(Long unitId) {
        Map<String, Object> map = new LinkedHashMap<>();
        UnitDto unitDtoFound = unitService.findUnitById(unitId);
        log.info("The method findUnitById is being executed");
        //return ResponseEntity.ok(unitDtoFound);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Unit found successfully ");
        map.put("data", unitDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findUnitByUnitnameAndPos(String unitName, Long posId) {
        Map<String, Object> map = new LinkedHashMap<>();
        UnitDto unitDtoFound = unitService.findUnitByUnitnameAndPos(unitName, posId);
        log.info("The method findUnitByUnitnameAndPos is being executed");
        //return ResponseEntity.ok(unitDtoFound);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Unit found successfully ");
        map.put("data", unitDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllUnitInPos(Long posId) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<UnitDto> unitDtoList = unitService.findAllUnitInPos(posId);
        log.info("The process of findAllUnitInPos is being executed");
        //return ResponseEntity.ok(unitDtoList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Unit list found successfully ");
        map.put("data", unitDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageUnitInPos(Long posId, Optional<Integer> optpagenum,
                                            Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Map<String, Object> map = new LinkedHashMap<>();
        Page<UnitDto> unitDtoPage = unitService.findPageUnitInPos(posId, pagenum, pagesize);
        log.info("The process of findPageUnitInPos is being executed");
        //return ResponseEntity.ok(unitDtoPage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Unit page found successfully ");
        map.put("data", unitDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteUnitById(Long unitId) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean delete = unitService.deleteUnitById(unitId);
        log.info("The process of deleteUnitById is being executed");
        //return ResponseEntity.ok(delete);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Unit deleted successfully ");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity saveUnitConversion(UnitConversionDto unitconvDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", unitconvDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        UnitConversionDto convRuleSaved = unitconvService.saveUnitConversion(unitconvDto);
        log.info("The process of saveUnitConversion is being executed");
        //return new ResponseEntity(convRuleSaved, HttpStatus.CREATED);
        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "Unit conversion created successfully ");
        map.put("data", convRuleSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateUnitConversion(UnitConversionDto unitconvDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", unitconvDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        UnitConversionDto convRuleUpdated = unitconvService.updateUnitConversion(unitconvDto);
        log.info("The process of updateUnitConversion is being executed");
        //return new ResponseEntity(convRuleUpdated, HttpStatus.OK);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Unit conversion updated successfully ");
        map.put("data", convRuleUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity convertTo(BigDecimal quantity, Long from, Long to) {
        Map<String, Object> map = new LinkedHashMap<>();
        BigDecimal qteConvert = unitconvService.convertTo(quantity, from, to);
        log.info("The unit conversion process is being executed in convertTo method");
        //return ResponseEntity.ok(qteConvert);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Conversion done successfully ");
        map.put("data", qteConvert);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findUnitConversionById(Long unitconvId) {
        Map<String, Object> map = new LinkedHashMap<>();
        UnitConversionDto convRule = unitconvService.findUnitConversionById(unitconvId);
        log.info("The method findUnitConversionById is being executed");
        //return ResponseEntity.ok(convRule);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Conversion rule found successfully ");
        map.put("data", convRule);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findConversionRuleBetween(Long from, Long to) {
        Map<String, Object> map = new LinkedHashMap<>();
        UnitConversionDto convRule = unitconvService.findConversionRuleBetween(from, to);
        log.info("The method findConversionRuleBetween is being executed");
        //return ResponseEntity.ok(convRule);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Conversion rule found successfully ");
        map.put("data", convRule);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity listofConvertibleUnitWith(Long from) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<UnitDto> convList = unitconvService.listofConvertibleUnitWith(from);
        log.info("The method listofConvertibleUnitWith is being executed");
        //return ResponseEntity.ok(convList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Conversion rule list found successfully ");
        map.put("data", convList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteUnitConversionById(Long unitconvId) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean delete = unitconvService.deleteUnitConversionById(unitconvId);
        log.info("The method deleteUnitConversionById is being executed");
        //return ResponseEntity.ok(delete);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Conversion rule deleted successfully ");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }
}
