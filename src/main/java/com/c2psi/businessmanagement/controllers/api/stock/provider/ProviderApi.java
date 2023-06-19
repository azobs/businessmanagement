package com.c2psi.businessmanagement.controllers.api.stock.provider;

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

import java.util.Optional;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

@Validated
@Api(APP_ROOT+"/provider")
public interface ProviderApi {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = APP_ROOT+"/provider/create",
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

    @PutMapping(value = APP_ROOT+"/provider/update",
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

    @GetMapping(value = APP_ROOT+"/provider/id/{providerId}",
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

    @GetMapping(value = APP_ROOT+"/provider/name/{providerName}/{posId}",
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

    @GetMapping(value = APP_ROOT+"/provider/all/{posId}",
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

    @GetMapping(value = APP_ROOT+"/provider/page/{posId}/{pagenum}/{pagesize}",
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

    @DeleteMapping(value = APP_ROOT+"/provider/delete/id/{providerId}",
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

    @PostMapping(value = APP_ROOT+"/provider/cash/operation/create",
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
            @ApiParam(name = "providerCashAccountDto", type = "ProviderCashAccountDto", required = true,
                    value="The JSON object that represent the ProviderCashAccountDto to save")
            @Valid @RequestBody ProviderCashAccountDto providerCashAccountDto, BindingResult bindingResult1,
            @ApiParam(name = "providerCashOperationDto", type = "ProviderCashOperationDto", required = true,
                    value="The JSON object that represent the ProviderCashOperationDto to save")
            @Valid @RequestBody ProviderCashOperationDto providerCashOperationDto, BindingResult bindingResult2);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = APP_ROOT+"/provider/cash/create",
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


    @GetMapping(value = APP_ROOT+"/provider/cash/id/{pcaId}",
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


    @DeleteMapping(value = APP_ROOT+"/provider/cash/delete/{pcaId}",
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













}
