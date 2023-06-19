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
import java.util.List;
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
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", unitDto, bindingResult);
            return ResponseEntity.badRequest().body(bindingResult.toString());
        }
        UnitDto unitDtoSaved = unitService.saveUnit(unitDto);
        log.info("The process of saving unit for product is launch");
        return new ResponseEntity(unitDtoSaved, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateUnit(UnitDto unitDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", unitDto, bindingResult);
            return ResponseEntity.badRequest().body(bindingResult.toString());
        }
        UnitDto unitDtoUpdated = unitService.updateUnit(unitDto);
        log.info("The process of updating unit for product is launch");
        return new ResponseEntity(unitDtoUpdated, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findUnitById(Long unitId) {
        UnitDto unitDtoFound = unitService.findUnitById(unitId);
        log.info("The method findUnitById is being executed");
        return ResponseEntity.ok(unitDtoFound);
    }

    @Override
    public ResponseEntity findUnitByUnitnameAndPos(String unitName, Long posId) {
        UnitDto unitDtoFound = unitService.findUnitByUnitnameAndPos(unitName, posId);
        log.info("The method findUnitByUnitnameAndPos is being executed");
        return ResponseEntity.ok(unitDtoFound);
    }

    @Override
    public ResponseEntity findAllUnitInPos(Long posId) {
        List<UnitDto> unitDtoList = unitService.findAllUnitInPos(posId);
        log.info("The process of findAllUnitInPos is being executed");
        return ResponseEntity.ok(unitDtoList);
    }

    @Override
    public ResponseEntity findPageUnitInPos(Long posId, Optional<Integer> optpagenum,
                                            Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<UnitDto> unitDtoPage = unitService.findPageUnitInPos(posId, pagenum, pagesize);
        log.info("The process of findPageUnitInPos is being executed");
        return ResponseEntity.ok(unitDtoPage);
    }

    @Override
    public ResponseEntity deleteUnitById(Long unitId) {
        Boolean delete = unitService.deleteUnitById(unitId);
        log.info("The process of deleteUnitById is being executed");
        return ResponseEntity.ok(delete);
    }

    @Override
    public ResponseEntity saveUnitConversion(UnitConversionDto unitconvDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", unitconvDto, bindingResult);
            return ResponseEntity.badRequest().body(bindingResult.toString());
        }
        UnitConversionDto convRuleSaved = unitconvService.saveUnitConversion(unitconvDto);
        log.info("The process of saveUnitConversion is being executed");
        return new ResponseEntity(convRuleSaved, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateUnitConversion(UnitConversionDto unitconvDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", unitconvDto, bindingResult);
            return ResponseEntity.badRequest().body(bindingResult.toString());
        }
        UnitConversionDto convRuleUpdated = unitconvService.updateUnitConversion(unitconvDto);
        log.info("The process of updateUnitConversion is being executed");
        return new ResponseEntity(convRuleUpdated, HttpStatus.OK);
    }

    @Override
    public ResponseEntity convertTo(BigDecimal quantity, Long from, Long to) {
        BigDecimal qteConvert = unitconvService.convertTo(quantity, from, to);
        log.info("The unit conversion process is being executed in convertTo method");
        return ResponseEntity.ok(qteConvert);
    }

    @Override
    public ResponseEntity findUnitConversionById(Long unitconvId) {
        UnitConversionDto convRule = unitconvService.findUnitConversionById(unitconvId);
        log.info("The method findUnitConversionById is being executed");
        return ResponseEntity.ok(convRule);
    }

    @Override
    public ResponseEntity findConversionRuleBetween(Long from, Long to) {
        UnitConversionDto convRule = unitconvService.findConversionRuleBetween(from, to);
        log.info("The method findConversionRuleBetween is being executed");
        return ResponseEntity.ok(convRule);
    }

    @Override
    public ResponseEntity listofConvertibleUnitWith(Long from) {
        List<UnitDto> convList = unitconvService.listofConvertibleUnitWith(from);
        log.info("The method listofConvertibleUnitWith is being executed");
        return ResponseEntity.ok(convList);
    }

    @Override
    public ResponseEntity deleteUnitConversionById(Long unitconvId) {
        Boolean delete = unitconvService.deleteUnitConversionById(unitconvId);
        log.info("The method deleteUnitConversionById is being executed");
        return ResponseEntity.ok(delete);
    }
}
