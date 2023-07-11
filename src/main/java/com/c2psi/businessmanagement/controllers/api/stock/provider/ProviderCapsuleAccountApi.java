package com.c2psi.businessmanagement.controllers.api.stock.provider;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCapsuleAccountDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCapsuleOperationDto;
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


import static com.c2psi.businessmanagement.utils.stock.provider.ProviderCapsuleAccountApiConstant.*;

@Validated
@Api(PROVIDERCAPSULEACCOUNT_ENDPOINT)
public interface ProviderCapsuleAccountApi {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = CREATE_PROVIDERCAPSULEACCOUNT_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveProviderCapsuleAccount",
            notes = "This method is used to save a capsule account for a provider and an article in a pointofsale in the DB",
            response = ProviderCapsuleAccountDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object ProviderCapsuleAccount added successfully"),
            @ApiResponse(code=400, message="Object ProviderCapsuleAccount is not valid during the saving process")
    })
    ResponseEntity saveProviderCapsuleAccount(
            @ApiParam(name = "procapaccDto", type = "ProviderCapsuleAccountDto", required = true,
                    value="The JSON object that represent the ProviderCapsuleAccountDto to save")
            @Valid @RequestBody ProviderCapsuleAccountDto procapaccDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PROVIDERCAPSULEACCOUNT_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findProviderCapsuleAccountById", notes = "Search in the DB a providercapsuleaccount by its Id",
            response = ProviderCapsuleAccountDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The providercapsuleaccount with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findProviderCapsuleAccountById(
            @ApiParam(name = "procapaccId", type = "Long", required = true,
                    value="Id of the concerned ProviderCapsuleAccountDto", example = "1")
            @NotNull @PathVariable("procapaccId") Long procapaccId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PROVIDERCAPSULEACCOUNT_OF_ARTICLE_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findProviderCapsuleAccountofArticleinPos", notes = "Search in the DB a providercapsuleaccount " +
            "for an article a provider in a DB",
            response = ProviderCapsuleAccountDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The providercapsuleaccount for an article and a provider precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findProviderCapsuleAccountofArticleinPos(
            @ApiParam(name = "providerId", type = "Long", required = true,
                    value="Id of the concerned ProviderDto", example = "1")
            @NotNull @PathVariable("providerId") Long providerId,
            @ApiParam(name = "artId", type = "Long", required = true,
                    value="Id of the concerned ArticleDto", example = "1")
            @NotNull @PathVariable("artId") Long artId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_PROVIDERCAPSULEACCOUNT_IN_POS_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllProviderCapsuleAccountinPos", notes = "Find all capsule account in pos",
            responseContainer = "List<ProviderCapsuleAccountDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The procapsuleaccount list in a pos for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllProviderCapsuleAccountinPos(
            @ApiParam(name = "providerId", type = "Long", required = true,
                    value="Id of the concerned Provider", example = "1")
            @NotNull @PathVariable("providerId") Long providerId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_PROVIDERCAPSULEACCOUNT_IN_POS_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageProviderCapsuleAccountinPos", notes = "Find all capsule account in pos",
            responseContainer = "Page<ProviderCapsuleAccountDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The procapsuleaccount list in a pos for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageProviderCapsuleAccountinPos(
            @ApiParam(name = "providerId", type = "Long", required = true,
                    value="Id of the concerned Provider", example = "1")
            @NotNull @PathVariable("providerId") Long providerId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = CREATE_PROVIDERCAPSULE_OPERATION_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveCapsuleOperation",
            notes = "This method is used to save a capsule account operation for a provider and an article in a pointofsale in the DB",
            response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The operation added successfully"),
            @ApiResponse(code=400, message="The operation is not valid during the saving process")
    })
    ResponseEntity saveCapsuleOperation(
            @ApiParam(name = "procapopDto", type = "ProviderCapsuleOperationDto", required = true,
                    value="The JSON object that represent the ProviderCapsuleOperationDto to save")
            @Valid @RequestBody ProviderCapsuleOperationDto procapopDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = UPDATE_PROVIDERCAPSULE_OPERATION_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "updateProviderCapsuleOperation",
            notes = "This method is used to update provider operation in the DB",
            response = ProviderCapsuleOperationDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object ProviderCapsuleOperationDto updated successfully"),
            @ApiResponse(code=400, message="Object ProviderCapsuleOperationDto is not valid during the updating process")
    })
    ResponseEntity updateProviderCapsuleOperation(
            @ApiParam(name = "providerDto", type = "ProviderDto", required = true,
                    value="The JSON object that represent the Provider to update")
            @Valid @RequestBody ProviderCapsuleOperationDto procapopDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = DELETE_PROVIDERCAPSULE_OPERATION_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deleteProviderCapsuleOperationById",
            notes = "This method is used to delete a ProviderCapsuleOperation saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object ProviderCapsuleOperation deleted successfully")
    })
    ResponseEntity deleteProviderCapsuleOperationById(
            @ApiParam(name = "procapopId", type = "Long", required = true,
                    value="Id of the ProviderCapsuleOperation to delete", example = "1")
            @NotNull @PathVariable("procapopId") Long procapopId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_PROVIDERCAPSULE_OPERATION_IN_POS_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllProviderCapsuleOperation", notes = "Find all capsule account operation in pos",
            responseContainer = "Page<ProviderCapsuleOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The procapsuleoperation list in a pos for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllProviderCapsuleOperation(
            @ApiParam(name = "procapaccId", type = "Long", required = true,
                    value="Id of the concerned ProviderCapsuleAccount", example = "1")
            @NotNull @PathVariable("procapaccId") Long procapaccId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_PROVIDERCAPSULE_OPERATION_IN_POS_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageProviderCapsuleOperation", notes = "Find all capsule account operation in pos page by page",
            responseContainer = "Page<ProviderCapsuleOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The procapsuleoperation page in a pos for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageProviderCapsuleOperation(
            @ApiParam(name = "procapaccId", type = "Long", required = true,
                    value="Id of the concerned ProviderCapsuleAccount", example = "1")
            @NotNull @PathVariable("procapaccId") Long procapaccId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_PROVIDERCAPSULE_OPERATION_OF_TYPE_IN_POS_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllProviderCapsuleOperationofType", notes = "Find all capsule account operation in pos",
            responseContainer = "List<ProviderCapsuleOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The procapsuleoperation list in a pos for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllProviderCapsuleOperationofType(
            @ApiParam(name = "procapaccId", type = "Long", required = true,
                    value="Id of the concerned ProviderCapsuleAccount", example = "1")
            @NotNull @PathVariable("procapaccId") Long procapaccId,
            @ApiParam(name = "opType", type = "OperationType", required = true,
                    value="The concerned Operation type", example = "Credit")
            @NotNull @PathVariable("opType") OperationType opType);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_PROVIDERCAPSULE_OPERATION_OF_TYPE_IN_POS_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageProviderCapsuleOperationofType", notes = "Find all capsule account operation in pos page by page",
            responseContainer = "Page<ProviderCapsuleOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The procapsuleoperation page in a pos for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageProviderCapsuleOperationofType(
            @ApiParam(name = "procapaccId", type = "Long", required = true,
                    value="Id of the concerned ProviderCapsuleAccount", example = "1")
            @NotNull @PathVariable("procapaccId") Long procapaccId,
            @ApiParam(name = "opType", type = "OperationType", required = true,
                    value="The concerned Operation type", example = "Credit")
            @NotNull @PathVariable("opType") OperationType opType,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_PROVIDERCAPSULE_OPERATION_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllProviderCapsuleOperationBetween", notes = "Find all capsule account operation in pos between",
            responseContainer = "List<ProviderCapsuleOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The procapsuleoperation list in a pos between 02 date found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllProviderCapsuleOperationBetween(
            @ApiParam(name = "procapaccId", type = "Long", required = true,
                    value="Id of the concerned ProviderCapsuleAccount", example = "1")
            @NotNull @PathVariable("procapaccId") Long procapaccId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_PROVIDERCAPSULE_OPERATION_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageProviderCapsuleOperationBetween", notes = "Find page capsule account operation in pos between",
            responseContainer = "Page<ProviderCapsuleOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The procapsuleoperation page in a pos between 02 date found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageProviderCapsuleOperationBetween(
            @ApiParam(name = "procapaccId", type = "Long", required = true,
                    value="Id of the concerned ProviderCapsuleAccount", example = "1")
            @NotNull @PathVariable("procapaccId") Long procapaccId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_PROVIDERCAPSULE_OPERATION_OF_TYPE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllProviderCapsuleOperationofTypeBetween", notes = "Find all capsule account operation in pos between",
            responseContainer = "List<ProviderCapsuleOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The procapsuleoperation list in a pos between 02 date found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllProviderCapsuleOperationofTypeBetween(
            @ApiParam(name = "procapaccId", type = "Long", required = true,
                    value="Id of the concerned ProviderCapsuleAccount", example = "1")
            @NotNull @PathVariable("procapaccId") Long procapaccId,
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

    @GetMapping(value = FIND_PAGE_PROVIDERCAPSULE_OPERATION_OF_TYPE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageProviderCapsuleOperationofTypeBetween", notes = "Find all capsule account operation page by page in pos between",
            responseContainer = "Page<ProviderCapsuleOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The procapsuleoperation page in a pos between 02 date found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageProviderCapsuleOperationofTypeBetween(
            @ApiParam(name = "procapaccId", type = "Long", required = true,
                    value="Id of the concerned ProviderCapsuleAccount", example = "1")
            @NotNull @PathVariable("procapaccId") Long procapaccId,
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
