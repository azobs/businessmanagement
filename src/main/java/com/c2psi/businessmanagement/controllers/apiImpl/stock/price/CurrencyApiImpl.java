package com.c2psi.businessmanagement.controllers.apiImpl.stock.price;

import com.c2psi.businessmanagement.controllers.api.stock.price.CurrencyApi;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyConversionDto;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyDto;
import com.c2psi.businessmanagement.services.contracts.stock.price.CurrencyConversionService;
import com.c2psi.businessmanagement.services.contracts.stock.price.CurrencyService;
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
public class CurrencyApiImpl implements CurrencyApi {

    private CurrencyService currencyService;
    private CurrencyConversionService currconvService;

    @Autowired
    public CurrencyApiImpl(CurrencyService currencyService, CurrencyConversionService currconvService) {

        this.currencyService = currencyService;
        this.currconvService = currconvService;
    }

    @Override
    public ResponseEntity saveCurrency(CurrencyDto currencyDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", currencyDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }


        CurrencyDto currencyDtoSaved = currencyService.saveCurrency(currencyDto);

        log.info("Entity currencyDto saved successfully {} ", currencyDtoSaved);
        //return new ResponseEntity(currencyDtoSaved, HttpStatus.CREATED);
        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "Currency created successfully ");
        map.put("data", currencyDtoSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateCurrency(CurrencyDto currencyDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", currencyDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        CurrencyDto currencyDtoUpdated = currencyService.updateCurrency(currencyDto);
        log.info("Entity currencyDto saved successfully {} ", currencyDtoUpdated);
        //return new ResponseEntity(currencyDtoUpdated, HttpStatus.OK);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Currency updated successfully ");
        map.put("data", currencyDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findCurrencyById(Long currencyId) {
        Map<String, Object> map = new LinkedHashMap<>();
        CurrencyDto currencyDtoFound = currencyService.findCurrencyById(currencyId);
        log.info("Currency successfully found in the system with the id precised {}", currencyId);
        //return ResponseEntity.ok(currencyDtoFound);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Currency found successfully ");
        map.put("data", currencyDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findCurrencyByFullname(String currencyName, String currencyShortname) {
        Map<String, Object> map = new LinkedHashMap<>();
        CurrencyDto currencyDtoFound = currencyService.findCurrencyByFullname(currencyName, currencyShortname);
        log.info("Currency successfully found in the system with the name precised {}", currencyName);
        //return ResponseEntity.ok(currencyDtoFound);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Currency found successfully ");
        map.put("data", currencyDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCurrency() {
        Map<String, Object> map = new LinkedHashMap<>();

        List<CurrencyDto> currencyDtoList = currencyService.listofExistingCurrency();
        log.info("The list of all the currency in the system has been successfully found");
        //return ResponseEntity.ok(currencyDtoList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Currency list found successfully ");
        map.put("data", currencyDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCurrency(Optional<Integer> optpagenum, Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();

        Page<CurrencyDto> currencyDtoPage = currencyService.pageofExistingCurrency(pagenum, pagesize);
        log.info("The Page of all the currency in the system has been successfully found");
        //return ResponseEntity.ok(currencyDtoPage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Currency page found successfully ");
        map.put("data", currencyDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteCurrencyById(Long currencyId) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean delete = currencyService.deleteCurrencyById(currencyId);
        log.info("The currency precised by its id has been suucessfully deleted");
        //return ResponseEntity.ok(delete);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Currency page deleted successfully ");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity saveCurrencyConversion(CurrencyConversionDto currencyconvDto, BindingResult bindingResult) {

        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", currencyconvDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        CurrencyConversionDto currencyConversionDtoSaved = currconvService.saveCurrencyConversion(currencyconvDto);
        log.info("Entity currencyconversionDto saved successfully {} ", currencyConversionDtoSaved);
        //return new ResponseEntity(currencyConversionDtoSaved, HttpStatus.CREATED);
        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "Currency conversion created successfully ");
        map.put("data", currencyConversionDtoSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateCurrencyConversion(CurrencyConversionDto currencyconvDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", currencyconvDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        CurrencyConversionDto currencyConversionDtoUpdated = currconvService.updateCurrencyConversion(currencyconvDto);
        log.info("Entity currencyconversionDto updated successfully {} ", currencyConversionDtoUpdated);
        //return new ResponseEntity(currencyConversionDtoSaved, HttpStatus.OK);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Currency conversion updated successfully ");
        map.put("data", currencyConversionDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity convertTo(BigDecimal amountToConvert, Long currencySourceId, Long currencyDestinationId) {
        Map<String, Object> map = new LinkedHashMap<>();

        BigDecimal amountConvert = currconvService.convertTo(amountToConvert, currencySourceId, currencyDestinationId);
        log.info("The conversion has been done with success {}", amountConvert);
        //return new ResponseEntity(amountConvert, HttpStatus.OK);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "conversion done successfully ");
        map.put("data", amountConvert);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findCurrencyConversionById(Long currconvId) {
        Map<String, Object> map = new LinkedHashMap<>();

        CurrencyConversionDto currconvDto = currconvService.findCurrencyConversionById(currconvId);
        log.info("The currencyconversion with the id {} is sucessfully found", currconvId);
        //return new ResponseEntity(currconvDto, HttpStatus.OK);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Currency conversion found successfully ");
        map.put("data", currconvDto);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findConversionRuleBetween(Long currencySourceId, Long currencyDestinationId) {
        Map<String, Object> map = new LinkedHashMap<>();

        CurrencyConversionDto currconvDto = currconvService.findConversionRuleBetween(currencySourceId,
                currencyDestinationId);
        log.info("The currencyconversion that link the 02 currency is sucessfully found");
        //return new ResponseEntity(currconvDto, HttpStatus.OK);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Currency conversion found successfully ");
        map.put("data", currconvDto);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity listofConvertibleCurrencyWith(Long currencySourceId) {
        Map<String, Object> map = new LinkedHashMap<>();

        List<CurrencyDto> currencyDtoList = currconvService.listofConvertibleCurrencyWith(currencySourceId);
        log.info("The list of Currency in which the source currency can be converted");
        //return new ResponseEntity(currencyDtoList, HttpStatus.OK);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Currency conversion list found successfully ");
        map.put("data", currencyDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteCurrencyConversionById(Long currconvId) {
        Map<String, Object> map = new LinkedHashMap<>();

        Boolean delete = currconvService.deleteCurrencyConversionById(currconvId);
        log.info("The deletion of a currencyConversion by id");
        //return new ResponseEntity(currconvService.deleteCurrencyConversionById(currconvId), HttpStatus.OK);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Currency conversion deleted successfully ");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteCurrencyConversionByCurrencyLink(Long currencySourceId, Long currencyDestinationId) {
        Map<String, Object> map = new LinkedHashMap<>();
        log.info("The deletion of a currencyConversion by argument");
        Boolean delete = currconvService.deleteCurrencyConversionByCurrencyLink(currencySourceId, currencyDestinationId);
        //return new ResponseEntity(currconvService.deleteCurrencyConversionByCurrencyLink(currencySourceId, currencyDestinationId), HttpStatus.OK);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Currency conversion deleted successfully ");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }
}
