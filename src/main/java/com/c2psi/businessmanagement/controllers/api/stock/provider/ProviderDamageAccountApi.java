package com.c2psi.businessmanagement.controllers.api.stock.provider;

import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDamageAccountDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDamageOperationDto;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

@Validated
@Api(APP_ROOT+"/prodamageaccount")
public interface ProviderDamageAccountApi {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = APP_ROOT+"/prodamageaccount/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveProviderDamageAccount",
            notes = "This method is used to save a damage account for a provider and an article in a pointofsale in the DB",
            response = ProviderDamageAccountDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object ProviderDamageAccount added successfully"),
            @ApiResponse(code=400, message="Object ProviderDamageAccount is not valid during the saving process")
    })
    ResponseEntity saveProviderDamageAccount(
            @ApiParam(name = "prodamaccDto", type = "ProviderDamageAccountDto", required = true,
                    value="The JSON object that represent the ProviderDamageAccountDto to save")
            @Valid @RequestBody ProviderDamageAccountDto prodamaccDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/prodamageaccount/id/{prodamaccId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findProviderDamageAccountById", notes = "Search in the DB a providerdamageaccount by its Id",
            response = ProviderDamageAccountDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The providerdamageaccount with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findProviderDamageAccountById(
            @ApiParam(name = "prodamaccId", type = "Long", required = true,
                    value="Id of the concerned ProviderDamageAccountDto", example = "1")
            @NotNull @PathVariable("prodamaccId") Long prodamaccId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/prodamageaccount/provider/article/{providerId}/{artId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findProviderDamageAccountofArticleinPos", notes = "Search in the DB a providerdamageaccount " +
            "for an article a provider in a DB",
            response = ProviderDamageAccountDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The providerdamageaccount for an article and a provider precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findProviderDamageAccountofArticleinPos(
            @ApiParam(name = "providerId", type = "Long", required = true,
                    value="Id of the concerned ProviderDto", example = "1")
            @NotNull @PathVariable("providerId") Long providerId,
            @ApiParam(name = "artId", type = "Long", required = true,
                    value="Id of the concerned ArticleDto", example = "1")
            @NotNull @PathVariable("artId") Long artId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/prodamageaccount/all/{providerId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllProviderDamageAccountinPos", notes = "Find all damage account in pos",
            responseContainer = "List<ProviderDamageAccountDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The posdamageaccount list in a pos for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllProviderDamageAccountinPos(
            @ApiParam(name = "providerId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("providerId") Long providerId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/prodamageaccount/page/{providerId}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageProviderDamageAccountinPos", notes = "Find all damage account in pos",
            responseContainer = "Page<ProviderDamageAccountDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The posdamageaccount list in a pos for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageProviderDamageAccountinPos(
            @ApiParam(name = "providerId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("providerId") Long providerId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = APP_ROOT+"/prodamageaccount/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveDamageOperation",
            notes = "This method is used to save a damage account operation for a provider and an article in a pointofsale in the DB",
            response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The operation added successfully"),
            @ApiResponse(code=400, message="The operation is not valid during the saving process")
    })
    ResponseEntity saveDamageOperation(
            @ApiParam(name = "prodamaccDto", type = "ProviderDamageAccountDto", required = true,
                    value="The JSON object that represent the ProviderDamageAccountDto to save")
            @Valid @RequestBody ProviderDamageAccountDto prodamaccDto, BindingResult bindingResult1,
            @ApiParam(name = "prodamopDto", type = "ProviderDamageOperationDto", required = true,
                    value="The JSON object that represent the ProviderDamageOperationDto to save")
            @Valid @RequestBody ProviderDamageOperationDto prodamopDto, BindingResult bindingResult2);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////











}
