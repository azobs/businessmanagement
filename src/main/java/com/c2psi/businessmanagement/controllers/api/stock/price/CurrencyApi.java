package com.c2psi.businessmanagement.controllers.api.stock.price;

import com.c2psi.businessmanagement.dtos.stock.price.CurrencyConversionDto;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyDto;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Optional;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

@Validated
@Api(APP_ROOT+"/currency")
public interface CurrencyApi {
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @PostMapping(value = APP_ROOT+"/currency/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create or Persist a currency in the DB for the whole system",
            notes = "This method is used to save a currency in the DB",
            response = CurrencyDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Currency added successfully"),
            @ApiResponse(code=400, message="Object Currency is not valid during the saving process")
    })
    ResponseEntity saveCurrency(
            @ApiParam(name = "currencyDto", type = "CurrencyDto", required = true,
                    value="The JSON object that represent the currency to save")
            @Valid @RequestBody CurrencyDto currencyDto, BindingResult bindingResult);

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = APP_ROOT+"/currency/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update a currency which is already saved in the DB",
            notes = "This method is used to update a currency in the DB",
            response = CurrencyDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Currency updated successfully"),
            @ApiResponse(code=400, message="Object Currency is not valid during the update process")
    })
    ResponseEntity updateCurrency(
            @ApiParam(name = "currencyDto", type = "CurrencyDto", required = true,
                    value="The JSON object that represent the currency to update")
            @Valid @RequestBody CurrencyDto currencyDto, BindingResult bindingResult);

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/currency/{currencyId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find currency by id",
            notes = "This method is used to find a currency in the DB by its id",
            response = CurrencyDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Currency founded successfully"),
            @ApiResponse(code=404, message="No Object Currency founded with the Id precised")
    })
    ResponseEntity findCurrencyById(
            @ApiParam(name = "currencyId", type = "Long", required = true,
                    value="The Id of the currency found", example = "1")
            @NotNull @PathVariable("currencyId") Long currencyId);

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/currency/fullname/{currencyName}/{currencyShortname}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find currency by id",
            notes = "This method is used to find a currency in the DB by its id",
            response = CurrencyDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Currency founded successfully"),
            @ApiResponse(code=404, message="No Object Currency founded with the Id precised")
    })
    ResponseEntity findCurrencyByFullname(
            @ApiParam(name = "currencyName", type = "String", required = true,
                    value="The name of the currency found", example = "Franc cfa")
            @NotNull @NotEmpty @NotBlank @PathVariable("currencyName") String currencyName,
            @ApiParam(name = "currencyShortname", type = "String", required = true,
                    value="Abbreviation of the currency found", example = "F cfa")
            @NotNull @NotEmpty @NotBlank @PathVariable("currencyShortname") String currencyShortname);

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/currency/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find all currency in the system",
            notes = "This method is used to find all currency in DB",
            responseContainer = "List<CurrencyDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="List of Object Currency found successfully or empty list")
    })
    ResponseEntity findAllCurrency();

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/currency/page/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find all currency in the system",
            notes = "This method is used to find all currency in DB",
            responseContainer = "Page<CurrencyDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="List of Object Currency found successfully or empty list")
    })
    ResponseEntity findPageCurrency(
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = APP_ROOT+"/currency/delete/id/{currencyId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete a Currency in DB by id",
            notes = "This method is used to delete a currency saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Currency deleted successfully")
    })
    ResponseEntity deleteCurrencyById(
            @ApiParam(name = "currencyId", type = "Long", value = "The id of the Currency researched", example = "1",
                    required = true)
            @NotNull @PathVariable("currencyId") Long currencyId);

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /***************************************************************
     * Consommation du service CurrencyConversionService
     */
    @PostMapping(value = APP_ROOT+"/currencyconversion/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create or Persist a currency conversion in the DB for the whole system",
            notes = "This method is used to save a conversion rule between 02 currencies in the DB",
            response = CurrencyConversionDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object CurrencyConversion added successfully"),
            @ApiResponse(code=400, message="Object CurrencyConversion is not valid during the saving process")
    })
    ResponseEntity saveCurrencyConversion(
            @ApiParam(name = "currencyconvDto", type = "CurrencyConversionDto", required = true,
                    value="The JSON object that represent the currency conversion to save")
            @Valid @RequestBody CurrencyConversionDto currencyconvDto, BindingResult bindingResult);

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = APP_ROOT+"/currencyconversion/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update or modify a currency conversion in the DB for the whole system",
            notes = "This method is used to update a conversion rule between 02 currencies in the DB",
            response = CurrencyConversionDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object CurrencyConversion updated successfully"),
            @ApiResponse(code=400, message="Object CurrencyConversion is not valid during the updating process")
    })
    ResponseEntity updateCurrencyConversion(
            @ApiParam(name = "currencyconvDto", type = "CurrencyConversionDto", required = true,
                    value="The JSON object that represent the currency conversion to save")
            @Valid @RequestBody CurrencyConversionDto currencyconvDto, BindingResult bindingResult);

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/currencyconversion/convertTo/{amount}/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Convert amount ",
            notes = "This method is used to convert an amount from one currency to another",
            response = BigDecimal.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Conversion successfully done"),
            @ApiResponse(code=404, message="Error during the conversion process")
    })
    ResponseEntity convertTo(
            @ApiParam(name = "amountToConvert", type = "BigDecimal", required = true,
                    value="The amount to convert", example = "0")
            @NotNull  @PathVariable("amount") BigDecimal amountToConvert,
            @ApiParam(name = "currencySourceId", type = "Long", required = true,
                    value="The currency source of the amount to convert", example = "1")
            @NotNull @PathVariable("from") Long currencySourceId,
            @ApiParam(name = "currencyDestinationId", type = "Long", required = true,
                    value="The currency destination of the amount to convert", example = "1")
            @NotNull @PathVariable("to") Long currencyDestinationId);

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/currencyconversion/id/{currconvId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find Currency Conversion by Id ",
            notes = "This method is used to find a currency conversion by id",
            response = CurrencyConversionDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Conversion rule successfully found"),
            @ApiResponse(code=404, message="Error during the finding process")
    })
    ResponseEntity findCurrencyConversionById(
            @ApiParam(name = "currconvId", type = "Long", required = true,
                    value="The currency conversion id found", example = "1")
            @NotNull  @PathVariable("currconvId") Long currconvId);

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/currencyconversion/link/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Convert amount ",
            notes = "This method is used to convert an amount from one currency to another",
            response = CurrencyConversionDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Conversion rule successfully found"),
            @ApiResponse(code=404, message="Error during the finding process")
    })
    ResponseEntity findConversionRuleBetween(
            @ApiParam(name = "currencySourceId", type = "Long", required = true,
                    value="The currency source of the amount to convert", example = "1")
            @NotNull @PathVariable("from") Long currencySourceId,
            @ApiParam(name = "currencyDestinationId", type = "Long", required = true,
                    value="The currency destination of the amount to convert", example = "1")
            @NotNull @PathVariable("to") Long currencyDestinationId);

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/currencyconversion/rules/{from}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find all currencyconversion in the system associate with",
            notes = "This method is used to find all currencyconversion in DB link with ",
            responseContainer = "List<CurrencyConversionDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="List of Object CurrencyConversion found successfully or empty list")
    })
    ResponseEntity listofConvertibleCurrencyWith(
            @ApiParam(name = "currencySourceId", type = "Long", required = true,
                    value="The currency source ", example = "1")
            @NotNull @PathVariable("from") Long currencySourceId);

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = APP_ROOT+"/currencyconversion/delete/id/{currconvId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete a CurrencyConversion in DB by id",
            notes = "This method is used to delete a conversion rule between 02 currencies saved in the DB",
            response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object CurrencyConversion deleted successfully")
    })
    ResponseEntity deleteCurrencyConversionById(
            @ApiParam(name = "currconvId", type = "Long", value = "The id of the CurrencyConversion researched", example = "1",
                    required = true)
            @NotNull @PathVariable("currconvId") Long currconvId);

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = APP_ROOT+"/currencyconversion/delete/link/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "delete a link between 02 currencies ",
            notes = "This method is used to delete a conversion rule betweem 02 currencies",
            response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Conversion rule successfully deleted"),
            @ApiResponse(code=404, message="Error during the deleting process")
    })
    ResponseEntity deleteCurrencyConversionByCurrencyLink(
            @ApiParam(name = "currencySourceId", type = "Long", required = true,
                    value="The currency source of the amount to convert", example = "1")
            @NotNull @PathVariable("from") Long currencySourceId,
            @ApiParam(name = "currencyDestinationId", type = "Long", required = true,
                    value="The currency destination of the amount to convert", example = "1")
            @NotNull @PathVariable("to") Long currencyDestinationId);

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
