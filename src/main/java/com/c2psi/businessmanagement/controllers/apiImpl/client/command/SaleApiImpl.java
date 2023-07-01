package com.c2psi.businessmanagement.controllers.apiImpl.client.command;

import com.c2psi.businessmanagement.controllers.api.client.command.SaleApi;
import com.c2psi.businessmanagement.dtos.client.command.SaleDto;
import com.c2psi.businessmanagement.services.contracts.client.command.SaleService;
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
public class SaleApiImpl implements SaleApi {
    private SaleService saleService;

    @Autowired
    public SaleApiImpl(SaleService saleService) {
        this.saleService = saleService;
    }

    @Override
    public ResponseEntity saveSale(SaleDto saleDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", saleDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }
        SaleDto saleDtoSaved = saleService.saveSale(saleDto);
        log.info("The method saveSale is being executed");
        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "Sale of command created successfully ");
        map.put("data", saleDtoSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateSale(SaleDto saleDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", saleDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }
        SaleDto saleDtoUpdated = saleService.updateSale(saleDto);
        log.info("The method updateSale is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale of command updated successfully ");
        map.put("data", saleDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findSaleById(Long saleId) {
        Map<String, Object> map = new LinkedHashMap<>();

        SaleDto saleDtoFound = saleService.findSaleById(saleId);
        log.info("The method findSaleById is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale of command found successfully ");
        map.put("data", saleDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findSaleinCommandaboutArticle(Long cmdId, Long artId) {
        Map<String, Object> map = new LinkedHashMap<>();

        SaleDto saleDtoFound = saleService.findSaleinCommandaboutArticle(cmdId, artId);
        log.info("The method findSaleinCommandaboutArticle is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale of command found successfully ");
        map.put("data", saleDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteSaleById(Long saleId) {
        Map<String, Object> map = new LinkedHashMap<>();

        Boolean delete = saleService.deleteSaleById(saleId);
        log.info("The method deleteSaleById is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale of command deleted successfully ");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllSaleofCommand(Long cmdId) {
        Map<String, Object> map = new LinkedHashMap<>();

        List<SaleDto> saleDtoList = saleService.findAllSaleofCommand(cmdId);
        log.info("The method findAllSaleofCommand is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale of command list found successfully ");
        map.put("data", saleDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageSaleofCommand(Long cmdId, Optional<Integer> optpagenum,
                                                Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Map<String, Object> map = new LinkedHashMap<>();

        Page<SaleDto> saleDtoPage = saleService.findPageSaleofCommand(cmdId, pagenum, pagesize);
        log.info("The method findPageSaleofCommand is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale of command page found successfully ");
        map.put("data", saleDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllSaleonArticleBetween(Long artId, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();

        List<SaleDto> saleDtoList = saleService.findAllSaleonArticleBetween(artId, startDate, endDate);
        log.info("The method findAllSaleonArticleBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale of command list found successfully ");
        map.put("data", saleDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageSaleonArticleBetween(Long artId, Instant startDate, Instant endDate,
                                                       Optional<Integer> optpagenum, Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Map<String, Object> map = new LinkedHashMap<>();

        Page<SaleDto> saleDtoPage = saleService.findPageSaleonArticleBetween(artId, startDate, endDate,
                pagenum, pagesize);
        log.info("The method findPageSaleonArticleBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale of command page found successfully ");
        map.put("data", saleDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }
}
