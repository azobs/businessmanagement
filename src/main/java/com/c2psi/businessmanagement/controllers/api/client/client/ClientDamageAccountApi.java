package com.c2psi.businessmanagement.controllers.api.client.client;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.client.client.*;
import com.c2psi.businessmanagement.dtos.client.client.ClientDamageAccountDto;
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

import static com.c2psi.businessmanagement.utils.client.client.ClientDamageAccountConstant.*;


@Validated
@Api(CLIENT_DAMAGE_ENDPOINT)
public interface ClientDamageAccountApi {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = CREATE_CLIENT_DAMAGE_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveClientDamageAccount",
            notes = "This method is used to save a Damage account for a client and an article in a pointofsale in the DB",
            response = ClientDamageAccountDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object ClientDamageAccount added successfully"),
            @ApiResponse(code=400, message="Object ClientDamageAccount is not valid during the saving process")
    })
    ResponseEntity saveClientDamageAccount(
            @ApiParam(name = "cltdamaccDto", type = "ClientDamageAccountDto", required = true,
                    value="The JSON object that represent the ClientDamageAccountDto to save")
            @Valid @RequestBody ClientDamageAccountDto cltdamaccDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_CLIENT_DAMAGE_ACCOUNT_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findClientDamageAccountById", notes = "Search in the DB a clientDamageaccount by its Id",
            response = ClientDamageAccountDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The clientDamageaccount with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findClientDamageAccountById(
            @ApiParam(name = "cltdamaccId", type = "Long", required = true,
                    value="Id of the concerned ClientDamageAccountDto", example = "1")
            @NotNull @PathVariable("cltdamaccId") Long cltdamaccId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_CLIENT_DAMAGE_ACCOUNT_OF_ARTICLE_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findClientDamageAccountofArticleinPos", notes = "Search in the DB a clientDamageaccount " +
            "for an article a client in a DB",
            response = ClientDamageAccountDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The clientDamageaccount for an article and a client precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findClientDamageAccountofArticleinPos(
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned ClientDto", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "artId", type = "Long", required = true,
                    value="Id of the concerned ArticleDto", example = "1")
            @NotNull @PathVariable("artId") Long artId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_CLIENT_DAMAGE_ACCOUNT_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllClientdamageAccountinPos", notes = "Find all damage account in pos",
            responseContainer = "List<ClientDamageAccountDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The clientdamageaccount list in a pos for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllClientDamageAccountinPos(
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_CLIENT_DAMAGE_ACCOUNT_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageClientDamageAccountinPos", notes = "Find all Damage account in pos",
            responseContainer = "Page<ClientDamageAccountDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The clientDamageaccount list in a pos for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageClientDamageAccountinPos(
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = CREATE_CLIENT_DAMAGE_ACCOUNT_OPERATION_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveDamageOperation",
            notes = "This method is used to save a Damage account operation for a client and an article in a pointofsale in the DB",
            response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The operation added successfully"),
            @ApiResponse(code=400, message="The operation is not valid during the saving process")
    })
    ResponseEntity saveDamageOperation(
            @ApiParam(name = "cltdamopDto", type = "ClientDamageOperationDto", required = true,
                    value="The JSON object that represent the ClientDamageOperationDto to save")
            @Valid @RequestBody ClientDamageOperationDto cltdamopDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = UPDATE_CLIENT_DAMAGE_ACCOUNT_OPERATION_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "updateClientDamageOperation",
            notes = "This method is used to update client operation in the DB",
            response = ClientDamageOperationDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object ClientDamageOperationDto updated successfully"),
            @ApiResponse(code=400, message="Object ClientDamageOperationDto is not valid during the updating process")
    })
    ResponseEntity updateClientDamageOperation(
            @ApiParam(name = "clientDto", type = "ClientDto", required = true,
                    value="The JSON object that represent the Client to update")
            @Valid @RequestBody ClientDamageOperationDto cltdamopDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = DELETE_CLIENT_DAMAGE_ACCOUNT_OPERATION_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deleteClientDamageOperationById",
            notes = "This method is used to delete a ClientDamageOperation saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object ClientDamageOperation deleted successfully")
    })
    ResponseEntity deleteClientDamageOperationById(
            @ApiParam(name = "cltdamopId", type = "Long", required = true,
                    value="Id of the ClientDamageOperation to delete", example = "1")
            @NotNull @PathVariable("cltdamopId") Long cltdamopId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_CLIENT_DAMAGE_ACCOUNT_OPERATION_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllClientDamageOperation", notes = "Find all Damage account operation in pos",
            responseContainer = "Page<ClientDamageOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The clientDamageoperation list in a pos for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllClientDamageOperation(
            @ApiParam(name = "cltdamaccId", type = "Long", required = true,
                    value="Id of the concerned ClientDamageAccount", example = "1")
            @NotNull @PathVariable("cltdamaccId") Long cltdamaccId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_CLIENT_DAMAGE_ACCOUNT_OPERATION_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageClientDamageOperation", notes = "Find all Damage account operation in pos page by page",
            responseContainer = "Page<ClientDamageOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The cltDamageoperation page in a pos for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageClientDamageOperation(
            @ApiParam(name = "cltdamaccId", type = "Long", required = true,
                    value="Id of the concerned ClientDamageAccount", example = "1")
            @NotNull @PathVariable("cltdamaccId") Long cltdamaccId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_CLIENT_DAMAGE_ACCOUNT_OPERATION_OF_TYPE_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllClientDamageOperationofType", notes = "Find all Damage account operation in pos",
            responseContainer = "List<ClientDamageOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The clientDamageoperation list in a pos for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllClientDamageOperationofType(
            @ApiParam(name = "cltdamaccId", type = "Long", required = true,
                    value="Id of the concerned ClientDamageAccount", example = "1")
            @NotNull @PathVariable("cltdamaccId") Long cltdamaccId,
            @ApiParam(name = "opType", type = "OperationType", required = true,
                    value="The concerned Operation type", example = "Credit")
            @NotNull @PathVariable("opType") OperationType opType);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_CLIENT_DAMAGE_ACCOUNT_OPERATION_OF_TYPE_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageClientDamageOperationofType", notes = "Find all Damage account operation in pos page by page",
            responseContainer = "Page<ClientDamageOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The clientDamageoperation page in a pos for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageClientDamageOperationofType(
            @ApiParam(name = "cltdamaccId", type = "Long", required = true,
                    value="Id of the concerned clientDamageAccount", example = "1")
            @NotNull @PathVariable("cltdamaccId") Long cltdamaccId,
            @ApiParam(name = "opType", type = "OperationType", required = true,
                    value="The concerned Operation type", example = "Credit")
            @NotNull @PathVariable("opType") OperationType opType,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_CLIENT_DAMAGE_ACCOUNT_OPERATION_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllClientDamageOperationBetween", notes = "Find all Damage account operation in pos between",
            responseContainer = "List<ClientDamageOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The clientDamageoperation list in a pos between 02 date found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllClientDamageOperationBetween(
            @ApiParam(name = "cltdamaccId", type = "Long", required = true,
                    value="Id of the concerned ClientDamageAccount", example = "1")
            @NotNull @PathVariable("cltdamaccId") Long cltdamaccId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_CLIENT_DAMAGE_ACCOUNT_OPERATION_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageClientDamageOperationBetween", notes = "Find page Damage account operation in pos between",
            responseContainer = "Page<ClientDamageOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The clientDamageoperation page in a pos between 02 date found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageClientDamageOperationBetween(
            @ApiParam(name = "cltdamaccId", type = "Long", required = true,
                    value="Id of the concerned ClientDamageAccount", example = "1")
            @NotNull @PathVariable("cltdamaccId") Long cltdamaccId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_CLIENT_DAMAGE_ACCOUNT_OPERATION_OF_TYPE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllClientDamageOperationofTypeBetween", notes = "Find all Damage account operation in pos between",
            responseContainer = "List<ClientDamageOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The clientDamageoperation list in a pos between 02 date found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllClientDamageOperationofTypeBetween(
            @ApiParam(name = "cltdamaccId", type = "Long", required = true,
                    value="Id of the concerned ClientDamageAccount", example = "1")
            @NotNull @PathVariable("cltdamaccId") Long cltdamaccId,
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

    @GetMapping(value = FIND_PAGE_CLIENT_DAMAGE_ACCOUNT_OPERATION_OF_TYPE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageClientDamageOperationofTypeBetween", notes = "Find all Damage account operation page by page in pos between",
            responseContainer = "Page<ClientDamageOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The clientDamageoperation page in a pos between 02 date found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageClientDamageOperationofTypeBetween(
            @ApiParam(name = "cltdamaccId", type = "Long", required = true,
                    value="Id of the concerned ClientDamageAccount", example = "1")
            @NotNull @PathVariable("cltdamaccId") Long cltdamaccId,
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
