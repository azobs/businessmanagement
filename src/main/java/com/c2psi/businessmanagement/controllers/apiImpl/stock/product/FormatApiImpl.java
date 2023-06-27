package com.c2psi.businessmanagement.controllers.apiImpl.stock.product;

import com.c2psi.businessmanagement.controllers.api.stock.product.FormatApi;
import com.c2psi.businessmanagement.dtos.stock.product.FormatDto;
import com.c2psi.businessmanagement.services.contracts.stock.product.FormatService;
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
public class FormatApiImpl implements FormatApi {
    FormatService formatService;

    @Autowired
    public FormatApiImpl(FormatService formatService) {
        this.formatService = formatService;
    }

    @Override
    public ResponseEntity saveFormat(FormatDto formatDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", formatDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        FormatDto formatDtoSaved = formatService.saveFormat(formatDto);
        log.info("The formay of product saved successfully");
        //return new ResponseEntity(formatDtoSaved, HttpStatus.CREATED);
        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "Product Format created successfully ");
        map.put("data", formatDtoSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateFormat(FormatDto formatDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", formatDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        FormatDto formatDtoUpdated = formatService.updateFormat(formatDto);
        log.info("The formay of product updated successfully");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Product Format updated successfully ");
        map.put("data", formatDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findFormatById(Long formatId) {
        Map<String, Object> map = new LinkedHashMap<>();
        FormatDto formatDtoFound = formatService.findFormatById(formatId);
        log.info("Format found with the id {} is {}", formatId, formatDtoFound);
        //return ResponseEntity.ok(formatDtoFound);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Product Format found successfully ");
        map.put("data", formatDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findFormatInPointofsaleByFullcharacteristic(Long posId, String formatName,
                                                                      BigDecimal formatCapacity) {
        Map<String, Object> map = new LinkedHashMap<>();
        FormatDto formatDtoFound = formatService.findFormatInPointofsaleByFullcharacteristic(formatName, formatCapacity,
                posId);
        log.info("Format found with characteristic");
        //return ResponseEntity.ok(formatDtoFound);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Product Format found successfully ");
        map.put("data", formatDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllFormatInPos(Long posId) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<FormatDto> formatDtoList = formatService.findAllFormatInPos(posId);
        log.info("Format list of pos with id {} is found {}", posId, formatDtoList);
        //return ResponseEntity.ok(formatDtoList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Product Format list found successfully ");
        map.put("data", formatDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageFormatInPos(Long posId, Optional<Integer> optpagenum,
                                              Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Map<String, Object> map = new LinkedHashMap<>();

        Page<FormatDto> formatDtoPage = formatService.findPageFormatInPos(posId, pagenum, pagesize);
        log.info("Page of format is successfully found");
        //return ResponseEntity.ok(formatDtoPage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Product Format page found successfully ");
        map.put("data", formatDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteFormatById(Long formatId) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean delete = formatService.deleteFormatById(formatId);
        log.info("The method deleteFormatById is being executed");
        //return ResponseEntity.ok(delete);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Product Format deleted successfully ");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }
}
