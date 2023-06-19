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
import java.util.List;
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
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in agument {} " +
                    "and the report errors are {}", currencyDto, bindingResult);
            return ResponseEntity.badRequest().body(bindingResult.toString());
        }

        CurrencyDto currencyDtoSaved = currencyService.saveCurrency(currencyDto);

        log.info("Entity currencyDto saved successfully {} ", currencyDtoSaved);
        return new ResponseEntity(currencyDtoSaved, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateCurrency(CurrencyDto currencyDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in agument {} " +
                    "and the report errors are {}", currencyDto, bindingResult);
            return ResponseEntity.badRequest().body(bindingResult.toString());
        }
        CurrencyDto currencyDtoSaved = currencyService.updateCurrency(currencyDto);

        log.info("Entity currencyDto saved successfully {} ", currencyDtoSaved);
        return new ResponseEntity(currencyDtoSaved, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findCurrencyById(Long currencyId) {
        CurrencyDto currencyDtoFound = currencyService.findCurrencyById(currencyId);
        log.info("Currency successfully found in the system with the id precised {}", currencyId);
        return ResponseEntity.ok(currencyDtoFound);
    }

    @Override
    public ResponseEntity findCurrencyByFullname(String currencyName, String currencyShortname) {
        CurrencyDto currencyDtoFound = currencyService.findCurrencyByFullname(currencyName, currencyShortname);
        log.info("Currency successfully found in the system with the name precised {}", currencyName);
        return ResponseEntity.ok(currencyDtoFound);
    }

    @Override
    public ResponseEntity findAllCurrency() {
        List<CurrencyDto> currencyDtoList = currencyService.listofExistingCurrency();
        log.info("The list of all the currency in the system has been successfully found");
        return ResponseEntity.ok(currencyDtoList);
    }

    @Override
    public ResponseEntity findPageCurrency(Optional<Integer> optpagenum, Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Page<CurrencyDto> currencyDtoPage = currencyService.pageofExistingCurrency(pagenum, pagesize);
        log.info("The Page of all the currency in the system has been successfully found");
        return ResponseEntity.ok(currencyDtoPage);
    }

    @Override
    public ResponseEntity deleteCurrencyById(Long currencyId) {
        Boolean delete = currencyService.deleteCurrencyById(currencyId);
        log.info("The currency precised by its id has been suucessfully deleted");
        return ResponseEntity.ok(delete);
    }

    @Override
    public ResponseEntity saveCurrencyConversion(CurrencyConversionDto currencyconvDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in agument {} " +
                     "and the report errors are {}", currencyconvDto, bindingResult);
            return ResponseEntity.badRequest().body(bindingResult.toString());
        }

        CurrencyConversionDto currencyConversionDtoSaved = currconvService.saveCurrencyConversion(currencyconvDto);
        log.info("Entity currencyconversionDto saved successfully {} ", currencyConversionDtoSaved);

        return new ResponseEntity(currencyConversionDtoSaved, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateCurrencyConversion(CurrencyConversionDto currencyconvDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in agument {} " +
                    "and the report errors are {}", currencyconvDto, bindingResult);
            return ResponseEntity.badRequest().body(bindingResult.toString());
        }

        CurrencyConversionDto currencyConversionDtoSaved = currconvService.updateCurrencyConversion(currencyconvDto);
        log.info("Entity currencyconversionDto updated successfully {} ", currencyConversionDtoSaved);

        return new ResponseEntity(currencyConversionDtoSaved, HttpStatus.OK);
    }

    @Override
    public ResponseEntity convertTo(BigDecimal amountToConvert, Long currencySourceId, Long currencyDestinationId) {
        BigDecimal amountConvert = currconvService.convertTo(amountToConvert, currencySourceId, currencyDestinationId);
        log.info("The conversion has been done with success {}", amountConvert);
        return new ResponseEntity(amountConvert, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findCurrencyConversionById(Long currconvId) {
        CurrencyConversionDto currconvDto = currconvService.findCurrencyConversionById(currconvId);
        log.info("The currencyconversion with the id {} is sucessfully found", currconvId);
        return new ResponseEntity(currconvDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findConversionRuleBetween(Long currencySourceId, Long currencyDestinationId) {
        CurrencyConversionDto currconvDto = currconvService.findConversionRuleBetween(currencySourceId,
                currencyDestinationId);
        log.info("The currencyconversion that link the 02 currency is sucessfully found");
        return new ResponseEntity(currconvDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity listofConvertibleCurrencyWith(Long currencySourceId) {
        List<CurrencyDto> currencyDtoList = currconvService.listofConvertibleCurrencyWith(currencySourceId);
        log.info("The list of Currency in which the source currency can be converted");
        return new ResponseEntity(currencyDtoList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteCurrencyConversionById(Long currconvId) {
        log.info("The deletion of a currencyConversion by id");
        return new ResponseEntity(currconvService.deleteCurrencyConversionById(currconvId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteCurrencyConversionByCurrencyLink(Long currencySourceId, Long currencyDestinationId) {
        log.info("The deletion of a currencyConversion by argument");
        return new ResponseEntity(currconvService.deleteCurrencyConversionByCurrencyLink(currencySourceId, currencyDestinationId), HttpStatus.OK);
    }
}
