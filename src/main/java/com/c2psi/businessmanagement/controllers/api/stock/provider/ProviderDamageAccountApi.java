package com.c2psi.businessmanagement.controllers.api.stock.provider;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDamageAccountDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDamageOperationDto;
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

import static com.c2psi.businessmanagement.utils.stock.provider.ProviderDamageAccountApiConstant.*;

@Validated
@Api(PROVIDERDAMAGEACCOUNT_ENDPOINT)
public interface ProviderDamageAccountApi {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = CREATE_PROVIDERDAMAGEACCOUNT_ENDPOINT,
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

    @GetMapping(value = FIND_PROVIDERDAMAGEACCOUNT_BY_ID_ENDPOINT,
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

    @GetMapping(value = FIND_PROVIDERDAMAGEACCOUNT_OF_ARTICLE_ENDPOINT,
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

    @GetMapping(value = FIND_ALL_PROVIDERDAMAGEACCOUNT_IN_POS_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllProviderDamageAccountinPos", notes = "Find all damage account in pos",
            responseContainer = "List<ProviderDamageAccountDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The providerdamageaccount list in a pos for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllProviderDamageAccountinPos(
            @ApiParam(name = "providerId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("providerId") Long providerId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_PROVIDERDAMAGEACCOUNT_IN_POS_ENDPOINT,
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
            @NotNull @PathVariable("providerId") Long providerId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = CREATE_PROVIDERDAMAGE_OPERATION_ENDPOINT,
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
            @ApiParam(name = "prodamopDto", type = "ProviderDamageOperationDto", required = true,
                    value="The JSON object that represent the ProviderDamageOperationDto to save")
            @Valid @RequestBody ProviderDamageOperationDto prodamopDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = UPDATE_PROVIDERDAMAGE_OPERATION_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "updateProviderDamageOperation",
            notes = "This method is used to update provider operation in the DB",
            response = ProviderDamageOperationDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object ProviderDamageOperationDto updated successfully"),
            @ApiResponse(code=400, message="Object ProviderDamageOperationDto is not valid during the updating process")
    })
    ResponseEntity updateProviderDamageOperation(
            @ApiParam(name = "prodamopDto", type = "ProviderDamageOperationDto", required = true,
                    value="The JSON object that represent the Provider to update")
            @Valid @RequestBody ProviderDamageOperationDto prodamopDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = DELETE_PROVIDERDAMAGE_OPERATION_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deleteProviderDamageOperationById",
            notes = "This method is used to delete a ProviderDamageOperation saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object ProviderDamageOperation deleted successfully")
    })
    ResponseEntity deleteProviderDamageOperationById(
            @ApiParam(name = "prodamopId", type = "Long", required = true,
                    value="Id of the ProviderDamageOperation to delete", example = "1")
            @NotNull @PathVariable("prodamopId") Long prodamopId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_PROVIDERDAMAGE_OPERATION_IN_POS_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllProviderDamageOperation", notes = "Find all damage account operation in pos",
            responseContainer = "List<ProviderDamageOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The prodamageoperation list in a pos for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllProviderDamageOperation(
            @ApiParam(name = "prodamaccId", type = "Long", required = true,
                    value="Id of the concerned ProviderDamageAccount", example = "1")
            @NotNull @PathVariable("prodamaccId") Long prodamaccId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_PROVIDERDAMAGE_OPERATION_IN_POS_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageProviderDamageOperation", notes = "Find all damage account operation in pos page by page",
            responseContainer = "Page<ProviderDamageOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The posdamageoperation page in a pos for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageProviderDamageOperation(
            @ApiParam(name = "prodamaccId", type = "Long", required = true,
                    value="Id of the concerned ProviderDamageAccount", example = "1")
            @NotNull @PathVariable("prodamaccId") Long prodamaccId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_PROVIDERDAMAGE_OPERATION_OF_TYPE_IN_POS_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllProviderDamageOperationofType", notes = "Find all damage account operation in pos",
            responseContainer = "List<ProviderDamageOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The posdamageoperation list in a pos for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllProviderDamageOperationofType(
            @ApiParam(name = "prodamaccId", type = "Long", required = true,
                    value="Id of the concerned ProviderDamageAccount", example = "1")
            @NotNull @PathVariable("prodamaccId") Long prodamaccId,
            @ApiParam(name = "opType", type = "OperationType", required = true,
                    value="The concerned Operation type", example = "Credit")
            @NotNull @PathVariable("opType") OperationType opType);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_PROVIDERDAMAGE_OPERATION_OF_TYPE_IN_POS_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageProviderDamageOperationofType", notes = "Find all damage account operation in pos page by page",
            responseContainer = "Page<ProviderDamageOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The posdamageoperation page in a pos for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageProviderDamageOperationofType(
            @ApiParam(name = "prodamaccId", type = "Long", required = true,
                    value="Id of the concerned ProviderDamageAccount", example = "1")
            @NotNull @PathVariable("prodamaccId") Long prodamaccId,
            @ApiParam(name = "opType", type = "OperationType", required = true,
                    value="The concerned Operation type", example = "Credit")
            @NotNull @PathVariable("opType") OperationType opType,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_PROVIDERDAMAGE_OPERATION_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllProviderDamageOperationBetween", notes = "Find all damage account operation in pos between",
            responseContainer = "List<ProviderDamageOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The prodamageoperation list in a pos between 02 date found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllProviderDamageOperationBetween(
            @ApiParam(name = "prodamaccId", type = "Long", required = true,
                    value="Id of the concerned ProviderDamageAccount", example = "1")
            @NotNull @PathVariable("prodamaccId") Long prodamaccId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_PROVIDERDAMAGE_OPERATION_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageProviderDamageOperationBetween", notes = "Find page damage account operation in pos between",
            responseContainer = "Page<ProviderDamageOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The posdamageoperation page in a pos between 02 date found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageProviderDamageOperationBetween(
            @ApiParam(name = "prodamaccId", type = "Long", required = true,
                    value="Id of the concerned ProviderDamageAccount", example = "1")
            @NotNull @PathVariable("prodamaccId") Long prodamaccId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_PROVIDERDAMAGE_OPERATION_OF_TYPE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllProviderDamageOperationofTypeBetween", notes = "Find all damage account operation in pos between",
            responseContainer = "List<ProviderDamageOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The prodamageoperation list in a pos between 02 date found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllProviderDamageOperationofTypeBetween(
            @ApiParam(name = "prodamaccId", type = "Long", required = true,
                    value="Id of the concerned ProviderDamageAccount", example = "1")
            @NotNull @PathVariable("prodamaccId") Long prodamaccId,
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

    @GetMapping(value = FIND_PAGE_PROVIDERDAMAGE_OPERATION_OF_TYPE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageProviderDamageOperationofTypeBetween", notes = "Find all damage account operation page by page in pos between",
            responseContainer = "Page<ProviderDamageOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The posdamageoperation page in a pos between 02 date found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageProviderDamageOperationofTypeBetween(
            @ApiParam(name = "prodamaccId", type = "Long", required = true,
                    value="Id of the concerned ProviderDamageAccount", example = "1")
            @NotNull @PathVariable("prodamaccId") Long prodamaccId,
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
