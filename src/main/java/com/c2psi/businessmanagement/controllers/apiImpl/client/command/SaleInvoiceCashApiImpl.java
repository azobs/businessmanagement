package com.c2psi.businessmanagement.controllers.apiImpl.client.command;

import com.c2psi.businessmanagement.controllers.api.client.command.SaleInvoiceCashApi;
import com.c2psi.businessmanagement.dtos.client.command.SaleInvoiceCashDto;
import com.c2psi.businessmanagement.services.contracts.client.command.SaleInvoiceCashService;
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
public class SaleInvoiceCashApiImpl implements SaleInvoiceCashApi {
    private SaleInvoiceCashService saleInvoiceCashService;

    @Autowired
    public SaleInvoiceCashApiImpl(SaleInvoiceCashService saleInvoiceCashService) {
        this.saleInvoiceCashService = saleInvoiceCashService;
    }

    @Override
    public ResponseEntity saveSaleInvoiceCash(SaleInvoiceCashDto saleicashDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", saleicashDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }
        SaleInvoiceCashDto saleicashDtoSaved = saleInvoiceCashService.saveSaleInvoiceCash(saleicashDto);
        log.info("The method saveSaleInvoiceCash is being executed");
        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "Sale Invoice Cash created successfully ");
        map.put("data", saleicashDtoSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateSaleInvoiceCash(SaleInvoiceCashDto saleicashDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", saleicashDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }
        SaleInvoiceCashDto saleicashDtoUpdated = saleInvoiceCashService.updateSaleInvoiceCash(saleicashDto);
        log.info("The method saveSaleInvoiceCash is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Cash updated successfully ");
        map.put("data", saleicashDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteSaleInvoiceCashById(Long saleicashId) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean delete = saleInvoiceCashService.deleteSaleInvoiceCashById(saleicashId);
        log.info("The method deleteSaleInvoiceCashById is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Cash deleted successfully ");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findSaleInvoiceCashById(Long saleicashId) {
        Map<String, Object> map = new LinkedHashMap<>();
        SaleInvoiceCashDto saleicashDtoFound = saleInvoiceCashService.findSaleInvoiceCashById(saleicashId);
        log.info("The method findSaleInvoiceCashById is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Cash found successfully ");
        map.put("data", saleicashDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findSaleInvoiceCashByCode(String saleicashCode, Long posId) {
        Map<String, Object> map = new LinkedHashMap<>();
        SaleInvoiceCashDto saleicashDtoFound = saleInvoiceCashService.findSaleInvoiceCashByCode(saleicashCode, posId);
        log.info("The method findSaleInvoiceCashByCode is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Cash found successfully ");
        map.put("data", saleicashDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllSaleicashBetween(Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<SaleInvoiceCashDto> saleInvoiceCashDtoList = saleInvoiceCashService.findAllSaleicashBetween(
                startDate, endDate);
        log.info("The method findAllSaleicashBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Cash list found successfully ");
        map.put("data", saleInvoiceCashDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageSaleicashBetween(Instant startDate, Instant endDate, Optional<Integer> optpagenum,
                                                   Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Map<String, Object> map = new LinkedHashMap<>();
        Page<SaleInvoiceCashDto> saleInvoiceCashDtoPage = saleInvoiceCashService.findPageSaleicashBetween(
                startDate, endDate, pagenum, pagesize);
        log.info("The method findPageSaleicashBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Cash page found successfully ");
        map.put("data", saleInvoiceCashDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllSaleicashofClientBetween(Long clientId, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<SaleInvoiceCashDto> saleInvoiceCashDtoList = saleInvoiceCashService.findAllSaleicashofClientBetween(
                clientId, startDate, endDate);
        log.info("The method findAllSaleicashofClientBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Cash list found successfully ");
        map.put("data", saleInvoiceCashDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageSaleicashofClientBetween(Long clientId, Instant startDate, Instant endDate,
                                                           Optional<Integer> optpagenum,
                                                           Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Map<String, Object> map = new LinkedHashMap<>();
        Page<SaleInvoiceCashDto> saleInvoiceCashDtoPage = saleInvoiceCashService.findPageSaleicashofClientBetween(
                clientId, startDate, endDate, pagenum, pagesize);
        log.info("The method findPageSaleicashofClientBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Cash page found successfully ");
        map.put("data", saleInvoiceCashDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllSaleicashofUserbmBetween(Long userbmId, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<SaleInvoiceCashDto> saleInvoiceCashDtoList = saleInvoiceCashService.findAllSaleicashofUserbmBetween(
                userbmId, startDate, endDate);
        log.info("The method findAllSaleicashofUserbmBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Cash list found successfully ");
        map.put("data", saleInvoiceCashDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageSaleicashofUserbmBetween(Long userbmId, Instant startDate, Instant endDate,
                                                           Optional<Integer> optpagenum,
                                                           Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Map<String, Object> map = new LinkedHashMap<>();
        Page<SaleInvoiceCashDto> saleInvoiceCashDtoPage = saleInvoiceCashService.findPageSaleicashofUserbmBetween(
                userbmId, startDate, endDate, pagenum, pagesize);
        log.info("The method findPageSaleicashofUserbmBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Cash page found successfully ");
        map.put("data", saleInvoiceCashDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllSaleicashinPosBetween(Long posId, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<SaleInvoiceCashDto> saleInvoiceCashDtoList = saleInvoiceCashService.findAllSaleicashinPosBetween(
                posId, startDate, endDate);
        log.info("The method findAllSaleicashinPosBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Cash list found successfully ");
        map.put("data", saleInvoiceCashDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageSaleicashinPosBetween(Long posId, Instant startDate, Instant endDate,
                                                        Optional<Integer> optpagenum, Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Map<String, Object> map = new LinkedHashMap<>();
        Page<SaleInvoiceCashDto> saleInvoiceCashDtoPage = saleInvoiceCashService.findPageSaleicashinPosBetween(
                posId, startDate, endDate, pagenum, pagesize);
        log.info("The method findPageSaleicashinPosBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Cash page found successfully ");
        map.put("data", saleInvoiceCashDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllSaleicashofUserbminPosBetween(Long userbmId, Long posId, Instant startDate,
                                                               Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<SaleInvoiceCashDto> saleInvoiceCashDtoList = saleInvoiceCashService.findAllSaleicashofUserbminPosBetween(
                userbmId, posId, startDate, endDate);
        log.info("The method findAllSaleicashofUserbminPosBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Cash list found successfully ");
        map.put("data", saleInvoiceCashDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageSaleicashofUserbminPosBetween(Long userbmId, Long posId, Instant startDate,
                                                                Instant endDate, Optional<Integer> optpagenum,
                                                                Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Map<String, Object> map = new LinkedHashMap<>();
        Page<SaleInvoiceCashDto> saleInvoiceCashDtoPage = saleInvoiceCashService.findPageSaleicashofUserbminPosBetween(
                userbmId, posId, startDate, endDate, pagenum, pagesize);
        log.info("The method findPageSaleicashofUserbminPosBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Cash page found successfully ");
        map.put("data", saleInvoiceCashDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllSaleicashofClientinPosBetween(Long clientId, Long posId, Instant startDate,
                                                               Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<SaleInvoiceCashDto> saleInvoiceCashDtoList = saleInvoiceCashService.findAllSaleicashofClientinPosBetween(
                clientId, posId, startDate, endDate);
        log.info("The method findAllSaleicashofClientinPosBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Cash list found successfully ");
        map.put("data", saleInvoiceCashDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageSaleicashofClientinPosBetween(Long clientId, Long posId, Instant startDate,
                                                                Instant endDate, Optional<Integer> optpagenum,
                                                                Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Map<String, Object> map = new LinkedHashMap<>();
        Page<SaleInvoiceCashDto> saleInvoiceCashDtoPage = saleInvoiceCashService.findPageSaleicashofClientinPosBetween(
                clientId, posId, startDate, endDate, pagenum, pagesize);
        log.info("The method findPageSaleicashofClientinPosBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Cash page found successfully ");
        map.put("data", saleInvoiceCashDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllSaleicashofClientforUserbmBetween(Long clientId, Long userbmId, Instant startDate,
                                                                   Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<SaleInvoiceCashDto> saleInvoiceCashDtoList = saleInvoiceCashService.findAllSaleicashofClientforUserbmBetween(
                clientId, userbmId, startDate, endDate);
        log.info("The method findAllSaleicashofClientforUserbmBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Cash list found successfully ");
        map.put("data", saleInvoiceCashDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageSaleicashofClientforUserbmBetween(Long clientId, Long userbmId, Instant startDate,
                                                                    Instant endDate, Optional<Integer> optpagenum,
                                                                    Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Map<String, Object> map = new LinkedHashMap<>();
        Page<SaleInvoiceCashDto> saleInvoiceCashDtoPage = saleInvoiceCashService.findPageSaleicashofClientforUserbmBetween(
                clientId, userbmId, startDate, endDate, pagenum, pagesize);
        log.info("The method findPageSaleicashofClientforUserbmBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Cash page found successfully ");
        map.put("data", saleInvoiceCashDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllSaleicashofClientforUserbminPosBetween(Long clientId, Long userbmId, Long posId,
                                                                        Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<SaleInvoiceCashDto> saleInvoiceCashDtoList = saleInvoiceCashService.
                findAllSaleicashofClientforUserbminPosBetween(clientId, userbmId, posId, startDate, endDate);
        log.info("The method findAllSaleicashofClientforUserbminPosBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Cash list found successfully ");
        map.put("data", saleInvoiceCashDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageSaleicashofClientforUserbminPosBetween(Long clientId, Long userbmId, Long posId,
                                                                         Instant startDate, Instant endDate,
                                                                         Optional<Integer> optpagenum,
                                                                         Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();
        Page<SaleInvoiceCashDto> saleInvoiceCashDtoPage = saleInvoiceCashService.
                findPageSaleicashofClientforUserbminPosBetween(clientId, userbmId, posId, startDate, endDate, pagenum,
                        pagesize);
        log.info("The method findPageSaleicashofClientforUserbminPosBetween is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Sale Invoice Cash page found successfully ");
        map.put("data", saleInvoiceCashDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }


}
