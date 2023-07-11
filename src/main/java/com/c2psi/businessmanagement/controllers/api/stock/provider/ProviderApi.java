package com.c2psi.businessmanagement.controllers.api.stock.provider;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.client.client.ClientCashOperationDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCashAccountDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCashOperationDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;
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


import static com.c2psi.businessmanagement.utils.stock.provider.ProviderApiConstant.*;

@Validated
@Api(PROVIDER_ENDPOINT)
public interface ProviderApi {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = CREATE_PROVIDER_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveProvider",
            notes = "This method is used to save a provider in the DB",
            response = ProviderDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Provider added successfully"),
            @ApiResponse(code=400, message="Object Provider is not valid during the saving process")
    })
    ResponseEntity saveProvider(
            @ApiParam(name = "providerDto", type = "ProviderDto", required = true,
                    value="The JSON object that represent the Provider to save")
            @Valid @RequestBody ProviderDto providerDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = UPDATE_PROVIDER_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "updateProvider",
            notes = "This method is used to update a provider in the DB",
            response = ProviderDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Provider updated successfully"),
            @ApiResponse(code=400, message="Object Provider is not valid during the updating process")
    })
    ResponseEntity updateProvider(
            @ApiParam(name = "providerDto", type = "ProviderDto", required = true,
                    value="The JSON object that represent the Provider to update")
            @Valid @RequestBody ProviderDto providerDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PROVIDER_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findProviderById", notes = "Search in the DB a provider by its Id",
            response = ProviderDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The provider with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findProviderById(
            @ApiParam(name = "providerId", type = "Long", required = true,
                    value="Id of the concerned Provider", example = "1")
            @NotNull @PathVariable("providerId") Long providerId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PROVIDER_BY_NAME_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findProviderByNameofPos", notes = "Search in the DB a provider by its name and pos",
            response = ProviderDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The provider with the name precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findProviderByNameofPos(
            @ApiParam(name = "providerName", type = "String", required = true,
                    value="Name of the concerned Provider", example = "name")
            @NotNull @PathVariable("providerName") String providerName,
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_PROVIDER_OF_POS_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllProviderofPos", notes = "Search the list of provider of a pointofsale",
            responseContainer = "List<ProviderDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The provider list in the pos precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllProviderofPos(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_PROVIDER_OF_POS_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageProviderofPos", notes = "Search the list of provider of a pointofsale page by page",
            responseContainer = "Page<ProviderDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The provider page in the pos precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageProviderofPos(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = DELETE_PROVIDER_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deleteProviderById",
            notes = "This method is used to delete a provider saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object provider deleted successfully")
    })
    ResponseEntity deleteProviderById(
            @ApiParam(name = "providerId", type = "Long", required = true,
                    value="Id of the provider to delete", example = "1")
            @NotNull @PathVariable("providerId") Long providerId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = CREATE_PROVIDER_CASH_OPERATION_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveCashOperation",
            notes = "This method is used to save a cash operation done on a providerCash account in the DB",
            response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object ProviderCashOperation added successfully"),
            @ApiResponse(code=400, message="Object ProviderCashOperation is not valid during the saving process")
    })
    ResponseEntity saveCashOperation(
            @ApiParam(name = "providerCashOperationDto", type = "ProviderCashOperationDto", required = true,
                    value="The JSON object that represent the ProviderCashOperationDto to save")
            @Valid @RequestBody ProviderCashOperationDto providerCashOperationDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = CREATE_PROVIDER_CASH_ACCOUNT_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveProviderCashAccount",
            notes = "This method is used to save provider cash account in the DB",
            response = ProviderCashAccountDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object ProviderCashAccount added successfully"),
            @ApiResponse(code=400, message="Object ProviderCashAccount is not valid during the saving process")
    })
    ResponseEntity saveProviderCashAccount(
            @ApiParam(name = "providerCashAccountDto", type = "ProviderCashAccountDto", required = true,
                    value="The JSON object that represent the ProviderCashAccountDto to save")
            @Valid @RequestBody ProviderCashAccountDto providerCashAccountDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @GetMapping(value = FIND_PROVIDER_CASH_ACCOUNT_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findProviderCashAccountById", notes = "Search in the DB a providercashaccount by its Id",
            response = ProviderCashAccountDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The providercashaccount with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findProviderCashAccountById(
            @ApiParam(name = "pcaId", type = "Long", required = true,
                    value="Id of the concerned ProviderCashAccount", example = "1")
            @NotNull @PathVariable("pcaId") Long pcaId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @DeleteMapping(value = DELETE_PROVIDER_CASH_ACCOUNT_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deleteProviderCashAccountById",
            notes = "This method is used to delete a providerCashAccount saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object providerCashAccount deleted successfully")
    })
    ResponseEntity deleteProviderCashAccountById(
            @ApiParam(name = "pcaId", type = "Long", required = true,
                    value="Id of the ProviderCashAccount to delete", example = "1")
            @NotNull @PathVariable("pcaId") Long pcaId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = UPDATE_PROVIDER_CASH_OPERATION_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "updateProviderCashOperation",
            notes = "This method is used to update a provider cash operation in the DB",
            response = ProviderCashOperationDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Provider cash operation updated successfully"),
            @ApiResponse(code=400, message="Object Provider cash operation is not valid during the updating process")
    })
    ResponseEntity updateProviderCashOperation(
            @ApiParam(name = "proCashOpDto", type = "ProviderCashOperationDto", required = true,
                    value="The JSON object that represent the Provider to update")
            @Valid @RequestBody ProviderCashOperationDto proCashOpDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = DELETE_PROVIDER_CASH_OPERATION_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deleteProviderCashOperationById",
            notes = "This method is used to delete a providerCashOperation saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object providerCashOperation deleted successfully")
    })
    ResponseEntity deleteProviderCashOperationById(
            @ApiParam(name = "procopId", type = "Long", required = true,
                    value="Id of the ProviderCashOperation to delete", example = "1")
            @NotNull @PathVariable("procopId") Long procopId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PROVIDER_CASH_OPERATION_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findProviderCashOperationById", notes = "Search in the DB a providercashoperation by its Id",
            response = ProviderCashOperationDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The providercashoperation with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findProviderCashOperationById(
            @ApiParam(name = "procopId", type = "Long", required = true,
                    value="Id of the concerned ProviderCashAccount", example = "1")
            @NotNull @PathVariable("procopId") Long procopId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_PROVIDER_CASH_OPERATION_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllProviderCashOperation", notes = "Search in the DB a providercashoperation the list " +
            "of operation",
            responseContainer = "List<ProviderCashOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The providercashoperation list with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllProviderCashOperation(
            @ApiParam(name = "procaId", type = "Long", required = true,
                    value="Id of the concerned ProviderCashAccount", example = "1")
            @NotNull @PathVariable("procaId") Long procaId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_PROVIDER_CASH_OPERATION_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageProviderCashOperation", notes = "Search in the DB a providercashoperation the list of " +
            "operation",
            responseContainer = "Page<ProviderCashOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The providercashoperation page with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageProviderCashOperation(
            @ApiParam(name = "procaId", type = "Long", required = true,
                    value="Id of the concerned ProviderCashAccount", example = "1")
            @NotNull @PathVariable("procaId") Long procaId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_PROVIDER_CASH_OPERATION_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllProviderCashOperationBetween", notes = "Search in the DB a providercashoperation " +
            "the list of operation",
            responseContainer = "List<ProviderCashOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The providercashoperation list with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllProviderCashOperationBetween(
            @ApiParam(name = "procaId", type = "Long", required = true,
                    value="Id of the concerned ProviderCashAccount", example = "1")
            @NotNull @PathVariable("procaId") Long procaId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_PROVIDER_CASH_OPERATION_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllProviderCashOperationBetween", notes = "Search in the DB a providercashoperation " +
            "the list of operation",
            responseContainer = "Page<ProviderCashOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The providercashoperation page with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageProviderCashOperationBetween(
            @ApiParam(name = "ccaId", type = "Long", required = true,
                    value="Id of the concerned ProviderCashAccount", example = "1")
            @NotNull @PathVariable("procaId") Long procaId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_PROVIDER_CASH_OPERATION_OFTYPE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllProviderCashOperationofTypeBetween", notes = "Search in the DB a providercashoperation the " +
            "list of operation",
            responseContainer = "List<ProviderCashOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The providercashoperation list with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllProviderCashOperationofTypeBetween(
            @ApiParam(name = "procaId", type = "Long", required = true,
                    value="Id of the concerned ProviderCashAccount", example = "1")
            @NotNull @PathVariable("procaId") Long procaId,
            @ApiParam(name = "opType", type = "OperationType", required = true,
                    value="The concerned Operation type", example = "Credit")
            @NotNull @PathVariable("opType") OperationType opType,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_PROVIDER_CASH_OPERATION_OFTYPE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageProviderCashOperationofTypeBetween", notes = "Search in the DB a providercashoperation the " +
            "list of operation",
            responseContainer = "Page<ProviderCashOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The providercashoperation page with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageProviderCashOperationofTypeBetween(
            @ApiParam(name = "procaId", type = "Long", required = true,
                    value="Id of the concerned ProviderCashAccount", example = "1")
            @NotNull @PathVariable("procaId") Long procaId,
            @ApiParam(name = "opType", type = "OperationType", required = true,
                    value="The concerned Operation type", example = "Credit")
            @NotNull @PathVariable("opType") OperationType opType,
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
