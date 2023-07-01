package com.c2psi.businessmanagement.controllers.api.stock.product;

import com.c2psi.businessmanagement.Enumerations.CashArrivalType;
import com.c2psi.businessmanagement.dtos.stock.product.CashArrivalDto;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.time.Instant;
import java.util.Optional;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

@Validated
@Api(APP_ROOT+"/cashArrival")
public interface CashArrivalApi {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = APP_ROOT+"/cashArrival/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveCashArrival",
            notes = "This method is used to save a cash arrival in the DB",
            response = CashArrivalDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object CashArrivalDto added successfully"),
            @ApiResponse(code=400, message="Object CashArrivalDto is not valid during the saving process")
    })
    ResponseEntity saveCashArrival(
            @ApiParam(name = "cashaDto", type = "CashArrivalDto", required = true,
                    value="The JSON object that represent the CashArrivalDto to save")
            @Valid @RequestBody CashArrivalDto cashaDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = APP_ROOT+"/cashArrival/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "updateCashArrival",
            notes = "This method is used to update a cash arrival in the DB",
            response = CashArrivalDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object CashArrivalDto updated successfully"),
            @ApiResponse(code=400, message="Object CashArrivalDto is not valid during the saving process")
    })
    ResponseEntity updateCashArrival(
            @ApiParam(name = "cashaDto", type = "CashArrivalDto", required = true,
                    value="The JSON object that represent the CashArrivalDto to save")
            @Valid @RequestBody CashArrivalDto cashaDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = APP_ROOT+"/cashArrival/delete/id/{cashaId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deleteCashArrivalById",
            notes = "This method is used to delete the cashArrival saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object cashArrival deleted successfully")
    })
    ResponseEntity deleteCashArrivalById(
            @ApiParam(name = "cashaId", type = "Long", required = true,
                    value="Id of the CashArrival to delete", example = "1")
            @NotNull @PathVariable("cashaId") Long cashaId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/cashArrival/id/{cashaId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findCashArrivalById", notes = "Search a cashArrival in a pointofsale",
            response = CashArrivalDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The CashArrivalDto found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findCashArrivalById(
            @ApiParam(name = "cashaId", type = "Long", required = true,
                    value="Id of the concerned CashArrival", example = "1")
            @NotNull @PathVariable("cashaId") Long cashaId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/cashArrival/article/{artId}/{sicashId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findCashArrivalofArticleinSicash", notes = "Search a cashArrival in a pointofsale",
            response = CashArrivalDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The CashArrivalDto found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findCashArrivalofArticleinSicash(
            @ApiParam(name = "artId", type = "Long", required = true,
                    value="Id of the concerned Article", example = "1")
            @NotNull @PathVariable("artId") Long artId,
            @ApiParam(name = "sicashId", type = "Long", required = true,
                    value="Id of the concerned SupplyInvoiceCash", example = "1")
            @NotNull @PathVariable("sicashId") Long sicashId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/cashArrival/all/{sicashId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCashArrivalinSicash", notes = "Search all cashArrival in a pointofsale on a supply " +
            "invoice cash",
            responseContainer = "List<CashArrivalDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The CashArrivalDto list found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCashArrivalinSicash(
            @ApiParam(name = "sicashId", type = "Long", required = true,
                    value="Id of the concerned SupplyInvoiceCash", example = "1")
            @NotNull @PathVariable("sicashId") Long sicashId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/cashArrival/all/{sicashId}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCashArrivalinSicash", notes = "Search all cashArrival in a pointofsale on a supply " +
            "invoice cash page by page",
            responseContainer = "Page<CashArrivalDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The CashArrivalDto page found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCashArrivalinSicash(
            @ApiParam(name = "sicashId", type = "Long", required = true,
                    value="Id of the concerned SupplyInvoiceCash", example = "1")
            @NotNull @PathVariable("sicashId") Long sicashId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/cashArrival/all/type/{arrivalType}/{sicashId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCashArrivalofCashArrivalTypeinSicash", notes = "Search all cashArrival in a pointofsale on a supply " +
            "invoice cash",
            responseContainer = "List<CashArrivalDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The CashArrivalDto list found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCashArrivalofCashArrivalTypeinSicash(
            @ApiParam(name = "arrivalType", type = "CashArrivalType", required = true,
                    value="Concerned CashArrivalType", example = "Standard")
            @NotNull @PathVariable("arrivalType") CashArrivalType arrivalType,
            @ApiParam(name = "sicashId", type = "Long", required = true,
                    value="Id of the concerned SupplyInvoiceCash", example = "1")
            @NotNull @PathVariable("sicashId") Long sicashId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/cashArrival/page/type/{arrivalType}/{sicashId}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCashArrivalofCashArrivalTypeinSicash", notes = "Search all cashArrival in a pointofsale on a supply " +
            "invoice cash page by page",
            responseContainer = "Page<CashArrivalDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The CashArrivalDto page found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCashArrivalofCashArrivalTypeinSicash(
            @ApiParam(name = "arrivalType", type = "CashArrivalType", required = true,
                    value="Concerned CashArrivalType", example = "Standard")
            @NotNull @PathVariable("arrivalType") CashArrivalType arrivalType,
            @ApiParam(name = "sicashId", type = "Long", required = true,
                    value="Id of the concerned SupplyInvoiceCash", example = "1")
            @NotNull @PathVariable("sicashId") Long sicashId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/cashArrival/all/between/{sicashId}/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCashArrivalinSicashBetween", notes = "Search all cashArrival in a pointofsale on a supply " +
            "invoice cash",
            responseContainer = "List<CashArrivalDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The CashArrivalDto list found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCashArrivalinSicashBetween(
            @ApiParam(name = "sicashId", type = "Long", required = true,
                    value="Id of the concerned SupplyInvoiceCash", example = "1")
            @NotNull @PathVariable("sicashId") Long sicashId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/cashArrival/page/between/{sicashId}/{from}/{to}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCashArrivalinSicashBetween", notes = "Search all cashArrival in a pointofsale on a supply " +
            "invoice cash page by page",
            responseContainer = "Page<CashArrivalDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The CashArrivalDto page found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCashArrivalinSicashBetween(
            @ApiParam(name = "sicashId", type = "Long", required = true,
                    value="Id of the concerned SupplyInvoiceCash", example = "1")
            @NotNull @PathVariable("sicashId") Long sicashId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/cashArrival/all/type/between/{arrivalType}/{sicashId}/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCashArrivalofCashArrivalTypeinSicashBetween", notes = "Search all cashArrival in a pointofsale on a supply " +
            "invoice cash",
            responseContainer = "List<CashArrivalDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The CashArrivalDto list found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCashArrivalofCashArrivalTypeinSicashBetween(
            @ApiParam(name = "arrivalType", type = "CashArrivalType", required = true,
                    value="Concerned CashArrivalType", example = "Standard")
            @NotNull @PathVariable("arrivalType") CashArrivalType arrivalType,
            @ApiParam(name = "sicashId", type = "Long", required = true,
                    value="Id of the concerned SupplyInvoiceCash", example = "1")
            @NotNull @PathVariable("sicashId") Long sicashId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/cashArrival/page/type/between/{arrivalType}/{sicashId}/{from}/{to}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCashArrivalofCashArrivalTypeinSicashBetween", notes = "Search all cashArrival in a pointofsale on a supply " +
            "invoice cash page by page",
            responseContainer = "Page<CashArrivalDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The CashArrivalDto page found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCashArrivalofCashArrivalTypeinSicashBetween(
            @ApiParam(name = "arrivalType", type = "CashArrivalType", required = true,
                    value="Concerned CashArrivalType", example = "Standard")
            @NotNull @PathVariable("arrivalType") CashArrivalType arrivalType,
            @ApiParam(name = "sicashId", type = "Long", required = true,
                    value="Id of the concerned SupplyInvoiceCash", example = "1")
            @NotNull @PathVariable("sicashId") Long sicashId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////




}
