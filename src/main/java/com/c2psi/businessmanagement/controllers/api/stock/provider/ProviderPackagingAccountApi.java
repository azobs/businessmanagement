package com.c2psi.businessmanagement.controllers.api.stock.provider;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderPackagingAccountDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderPackagingOperationDto;
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
@Api(APP_ROOT+"/propackagingaccount")
public interface ProviderPackagingAccountApi {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = APP_ROOT+"/propackagingaccount/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveProviderPackagingAccount",
            notes = "This method is used to save a packaging account for a provider in a pointofsale in the DB",
            response = ProviderPackagingAccountDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object ProviderPackagingAccount added successfully"),
            @ApiResponse(code=400, message="Object ProviderPackagingAccount is not valid during the saving process")
    })
    ResponseEntity saveProviderPackagingAccount(
            @ApiParam(name = "propackaccDto", type = "ProviderPackagingAccountDto", required = true,
                    value="The JSON object that represent the ProviderPackagingAccountDto to save")
            @Valid @RequestBody ProviderPackagingAccountDto propackaccDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/propackagingaccount/id/{propackaccId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findProviderPackagingAccountById", notes = "Search in the DB a providerpackagingaccount by its Id",
            response = ProviderPackagingAccountDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The providerpackagingaccount with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findProviderPackagingAccountById(
            @ApiParam(name = "propackaccId", type = "Long", required = true,
                    value="Id of the concerned ProviderPackagingAccountDto", example = "1")
            @NotNull @PathVariable("propackaccId") Long propackaccId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/propackagingaccount/packaging/provider/{packagingId}/{providerId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findProviderPackagingAccount", notes = "Search in the DB a providerpackagingaccount by its attributes",
            response = ProviderPackagingAccountDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The providerpackagingaccount with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findProviderPackagingAccount(
            @ApiParam(name = "packagingId", type = "Long", required = true,
                    value="Id of the concerned Packaging", example = "1")
            @NotNull @PathVariable("packagingId") Long packagingId,
            @ApiParam(name = "providerId", type = "Long", required = true,
                    value="Id of the concerned Provider", example = "1")
            @NotNull @PathVariable("providerId") Long providerId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/propackagingaccount/all/{providerId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllPackagingAccountofProvider", notes = "Find all packaging account in pos",
            responseContainer = "List<ProviderPackagingAccountDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The providerpackagingaccount list in a pos for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllProviderPackagingAccountinPos(
            @ApiParam(name = "providerId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("providerId") Long providerId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/propackagingaccount/page/{providerId}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPagePackagingAccountofProvider", notes = "Find all packaging account in pos page by page",
            responseContainer = "Page<ProviderPackagingAccountDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The providerpackagingaccount list in a pos for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageProviderPackagingAccountinPos(
            @ApiParam(name = "providerId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("providerId") Long providerId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = APP_ROOT+"/propackagingaccount/delete/{propackId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deleteProviderPackagingAccountById",
            notes = "This method is used to delete a ProviderPackagingOperation saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object ProviderPackagingAccount deleted successfully")
    })
    ResponseEntity deleteProviderPackagingAccountById(
            @ApiParam(name = "propackId", type = "Long", required = true,
                    value="Id of the ProviderPackagingAccount to delete", example = "1")
            @NotNull @PathVariable("propackId") Long propackId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = APP_ROOT+"/propackagingaccount/operation/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "savePackagingOperation",
            notes = "This method is used to save a packaging account operation for a provider in a pointofsale in the DB",
            response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The operation added successfully"),
            @ApiResponse(code=400, message="The operation is not valid during the saving process")
    })
    ResponseEntity savePackagingOperation(
            @ApiParam(name = "propackopDto", type = "ProviderPackagingOperationDto", required = true,
                    value="The JSON object that represent the ProviderPackagingOperationDto to save")
            @Valid @RequestBody ProviderPackagingOperationDto propackopDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = APP_ROOT+"/propackagingaccount/operation/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "updateProviderPackagingOperation",
            notes = "This method is used to update provider operation in the DB",
            response = ProviderPackagingOperationDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object ProviderPackagingOperationDto updated successfully"),
            @ApiResponse(code=400, message="Object ProviderPackagingOperationDto is not valid during the updating process")
    })
    ResponseEntity updateProviderPackagingOperation(
            @ApiParam(name = "propackopDto", type = "ProviderPackagingOperationDto", required = true,
                    value="The JSON object that represent the ProviderPackagingOperationDto to update")
            @Valid @RequestBody ProviderPackagingOperationDto propackopDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = APP_ROOT+"/propackagingaccount/operation/delete/{propackopId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deleteProviderPackagingOperationById",
            notes = "This method is used to delete a ProviderPackagingOperation saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object ProviderPackagingOperation deleted successfully")
    })
    ResponseEntity deleteProviderPackagingOperationById(
            @ApiParam(name = "propackopId", type = "Long", required = true,
                    value="Id of the ProviderPackagingOperation to delete", example = "1")
            @NotNull @PathVariable("propackopId") Long propackopId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/propackagingaccount/operation/all/{propackaccId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllProviderPackagingOperation", notes = "Find all packaging account operation in pos",
            responseContainer = "List<ProviderPackagingOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The propackagingoperation list in a pos for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllProviderPackagingOperation(
            @ApiParam(name = "propackaccId", type = "Long", required = true,
                    value="Id of the concerned ProviderPackagingAccount", example = "1")
            @NotNull @PathVariable("propackaccId") Long propackaccId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/propackagingaccount/operation/page/{propackaccId}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageProviderPackagingOperation", notes = "Find all packaging account operation in pos page by page",
            responseContainer = "Page<ProviderPackagingOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The propackagingoperation page in a pos for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageProviderPackagingOperation(
            @ApiParam(name = "propackaccId", type = "Long", required = true,
                    value="Id of the concerned ProviderPackagingAccount", example = "1")
            @NotNull @PathVariable("propackaccId") Long propackaccId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/propackagingaccount/operation/type/all/{propackaccId}/{opType}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllProviderPackagingOperationofType", notes = "Find all packaging account operation in pos for a " +
            "certain types",
            responseContainer = "List<ProviderPackagingOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The propackagingoperation list in a pos for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllProviderPackagingOperationofType(
            @ApiParam(name = "propackaccId", type = "Long", required = true,
                    value="Id of the concerned ProviderPackagingAccount", example = "1")
            @NotNull @PathVariable("propackaccId") Long propackaccId,
            @ApiParam(name = "opType", type = "OperationType", required = true,
                    value="The concerned Operation type", example = "Credit")
            @NotNull @PathVariable("opType") OperationType opType);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/propackagingaccount/operation/type/page/{propackaccId}/{opType}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageProviderPackagingOperationofType", notes = "Find all packaging account operation in pos for a " +
            "certain types page by page",
            responseContainer = "Page<ProviderPackagingOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The propackagingoperation page in a pos for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageProviderPackagingOperationofType(
            @ApiParam(name = "propackaccId", type = "Long", required = true,
                    value="Id of the concerned ProviderPackagingAccount", example = "1")
            @NotNull @PathVariable("propackaccId") Long propackaccId,
            @ApiParam(name = "opType", type = "OperationType", required = true,
                    value="The concerned Operation type", example = "Credit")
            @NotNull @PathVariable("opType") OperationType opType,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/propackagingaccount/operation/all/between/{propackaccId}/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllProviderPackagingOperationBetween", notes = "Find all packaging account operation in pos between",
            responseContainer = "List<ProviderPackagingOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The propackagingoperation list in a pos between 02 date found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllProviderPackagingOperationBetween(
            @ApiParam(name = "propackaccId", type = "Long", required = true,
                    value="Id of the concerned ProviderPackagingAccount", example = "1")
            @NotNull @PathVariable("propackaccId") Long propackaccId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/propackagingaccount/operation/all/between/{propackaccId}/{from}/{to}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageProviderPackagingOperationBetween", notes = "Find all packaging account operation in pos between",
            responseContainer = "Page<ProviderPackagingOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The propackagingoperation page in a pos between 02 date found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageProviderPackagingOperationBetween(
            @ApiParam(name = "propackaccId", type = "Long", required = true,
                    value="Id of the concerned ProviderPackagingAccount", example = "1")
            @NotNull @PathVariable("propackaccId") Long propackaccId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/propackagingaccount/operation/type/all/between/{propackaccId}/{optype}/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllProviderPackagingOperationofTypeBetween", notes = "Find all packaging account operation in pos between",
            responseContainer = "List<ProviderPackagingOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The propackagingoperation list in a pos between 02 date found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllProviderPackagingOperationofTypeBetween(
            @ApiParam(name = "propackaccId", type = "Long", required = true,
                    value="Id of the concerned ProviderPackagingAccount", example = "1")
            @NotNull @PathVariable("propackaccId") Long propackaccId,
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

    @GetMapping(value = APP_ROOT+"/propackagingaccount/operation/type/page/between/{propackaccId}/{optype}/{from}/{to}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageProviderPackagingOperationofTypeBetween", notes = "Find all packaging account operation in pos between",
            responseContainer = "Page<ProviderPackagingOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The propackagingoperation page in a pos between 02 date found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageProviderPackagingOperationofTypeBetween(
            @ApiParam(name = "propackaccId", type = "Long", required = true,
                    value="Id of the concerned ProviderPackagingAccount", example = "1")
            @NotNull @PathVariable("propackaccId") Long propackaccId,
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
