package com.c2psi.businessmanagement.controllers.api.client.client;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.client.client.ClientCapsuleAccountDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientCapsuleOperationDto;
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
@Api(APP_ROOT+"/clientcapsuleaccount")
public interface ClientCapsuleAccountApi {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = APP_ROOT+"/clientcapsuleaccount/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveClientCapsuleAccount",
            notes = "This method is used to save a capsule account for a client and an article in a pointofsale in the DB",
            response = ClientCapsuleAccountDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object ClientCapsuleAccount added successfully"),
            @ApiResponse(code=400, message="Object ClientCapsuleAccount is not valid during the saving process")
    })
    ResponseEntity saveClientCapsuleAccount(
            @ApiParam(name = "cltcapaccDto", type = "ClientCapsuleAccountDto", required = true,
                    value="The JSON object that represent the ClientCapsuleAccountDto to save")
            @Valid @RequestBody ClientCapsuleAccountDto cltcapaccDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/clientcapsuleaccount/id/{cltcapaccId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findClientCapsuleAccountById", notes = "Search in the DB a clientcapsuleaccount by its Id",
            response = ClientCapsuleAccountDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The clientcapsuleaccount with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findClientCapsuleAccountById(
            @ApiParam(name = "cltcapaccId", type = "Long", required = true,
                    value="Id of the concerned ClientCapsuleAccountDto", example = "1")
            @NotNull @PathVariable("cltcapaccId") Long cltcapaccId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/clientcapsuleaccount/client/article/{clientId}/{artId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findClientCapsuleAccountofArticleinPos", notes = "Search in the DB a clientcapsuleaccount " +
            "for an article a client in a DB",
            response = ClientCapsuleAccountDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The clientcapsuleaccount for an article and a client precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findClientCapsuleAccountofArticleinPos(
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned ClientDto", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "artId", type = "Long", required = true,
                    value="Id of the concerned ArticleDto", example = "1")
            @NotNull @PathVariable("artId") Long artId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/clientcapsuleaccount/all/{clientId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllClientCapsuleAccountinPos", notes = "Find all capsule account in pos",
            responseContainer = "List<ClientCapsuleAccountDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The clientcapsuleaccount list in a pos for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllClientCapsuleAccountinPos(
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/clientcapsuleaccount/page/{clientId}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageClientCapsuleAccountinPos", notes = "Find all capsule account in pos",
            responseContainer = "Page<ClientCapsuleAccountDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The clientcapsuleaccount list in a pos for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageClientCapsuleAccountinPos(
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = APP_ROOT+"/clientcapsuleaccount/operation/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveCapsuleOperation",
            notes = "This method is used to save a capsule account operation for a client and an article in a pointofsale in the DB",
            response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The operation added successfully"),
            @ApiResponse(code=400, message="The operation is not valid during the saving process")
    })
    ResponseEntity saveCapsuleOperation(
            @ApiParam(name = "cltcapopDto", type = "ClientCapsuleOperationDto", required = true,
                    value="The JSON object that represent the ClientCapsuleOperationDto to save")
            @Valid @RequestBody ClientCapsuleOperationDto cltcapopDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = APP_ROOT+"/clientcapsuleaccount/operation/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "updateClientCapsuleOperation",
            notes = "This method is used to update client operation in the DB",
            response = ClientCapsuleOperationDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object ClientCapsuleOperationDto updated successfully"),
            @ApiResponse(code=400, message="Object ClientCapsuleOperationDto is not valid during the updating process")
    })
    ResponseEntity updateClientCapsuleOperation(
            @ApiParam(name = "clientDto", type = "ClientDto", required = true,
                    value="The JSON object that represent the Client to update")
            @Valid @RequestBody ClientCapsuleOperationDto cltcapopDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = APP_ROOT+"/clientcapsuleaccount/operation/delete/{cltcapopId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deleteClientCapsuleOperationById",
            notes = "This method is used to delete a ClientCapsuleOperation saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object ClientCapsuleOperation deleted successfully")
    })
    ResponseEntity deleteClientCapsuleOperationById(
            @ApiParam(name = "cltcapopId", type = "Long", required = true,
                    value="Id of the ClientCapsuleOperation to delete", example = "1")
            @NotNull @PathVariable("cltcapopId") Long cltcapopId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/clientcapsuleaccount/operation/all/{cltcapaccId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllClientCapsuleOperation", notes = "Find all capsule account operation in pos",
            responseContainer = "Page<ClientCapsuleOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The clientcapsuleoperation list in a pos for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllClientCapsuleOperation(
            @ApiParam(name = "cltcapaccId", type = "Long", required = true,
                    value="Id of the concerned ClientCapsuleAccount", example = "1")
            @NotNull @PathVariable("cltcapaccId") Long cltcapaccId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/clientcapsuleaccount/operation/page/{cltcapaccId}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageClientCapsuleOperation", notes = "Find all capsule account operation in pos page by page",
            responseContainer = "Page<ClientCapsuleOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The cltcapsuleoperation page in a pos for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageClientCapsuleOperation(
            @ApiParam(name = "cltcapaccId", type = "Long", required = true,
                    value="Id of the concerned ClientCapsuleAccount", example = "1")
            @NotNull @PathVariable("cltcapaccId") Long cltcapaccId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/clientcapsuleaccount/operation/type/all/{cltcapaccId}/{optype}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllClientCapsuleOperationofType", notes = "Find all capsule account operation in pos",
            responseContainer = "List<ClientCapsuleOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The clientcapsuleoperation list in a pos for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllClientCapsuleOperationofType(
            @ApiParam(name = "cltcapaccId", type = "Long", required = true,
                    value="Id of the concerned ClientCapsuleAccount", example = "1")
            @NotNull @PathVariable("cltcapaccId") Long cltcapaccId,
            @ApiParam(name = "opType", type = "OperationType", required = true,
                    value="The concerned Operation type", example = "Credit")
            @NotNull @PathVariable("opType") OperationType opType);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/clientcapsuleaccount/operation/type/page/{cltcapaccId}/{optype}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageClientCapsuleOperationofType", notes = "Find all capsule account operation in pos page by page",
            responseContainer = "Page<ClientCapsuleOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The clientcapsuleoperation page in a pos for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageClientCapsuleOperationofType(
            @ApiParam(name = "cltcapaccId", type = "Long", required = true,
                    value="Id of the concerned clientCapsuleAccount", example = "1")
            @NotNull @PathVariable("cltcapaccId") Long cltcapaccId,
            @ApiParam(name = "opType", type = "OperationType", required = true,
                    value="The concerned Operation type", example = "Credit")
            @NotNull @PathVariable("opType") OperationType opType,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/clientcapsuleaccount/operation/all/between/{cltcapaccId}/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllClientCapsuleOperationBetween", notes = "Find all capsule account operation in pos between",
            responseContainer = "List<ClientCapsuleOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The clientcapsuleoperation list in a pos between 02 date found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllClientCapsuleOperationBetween(
            @ApiParam(name = "cltcapaccId", type = "Long", required = true,
                    value="Id of the concerned ClientCapsuleAccount", example = "1")
            @NotNull @PathVariable("cltcapaccId") Long cltcapaccId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/clientcapsuleaccount/operation/page/between/{cltcapaccId}/{from}/{to}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageClientCapsuleOperationBetween", notes = "Find page capsule account operation in pos between",
            responseContainer = "Page<ClientCapsuleOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The clientcapsuleoperation page in a pos between 02 date found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageClientCapsuleOperationBetween(
            @ApiParam(name = "cltcapaccId", type = "Long", required = true,
                    value="Id of the concerned ClientCapsuleAccount", example = "1")
            @NotNull @PathVariable("cltcapaccId") Long cltcapaccId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/clientcapsuleaccount/operation/type/all/between/{cltcapaccId}/{optype}/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllClientCapsuleOperationofTypeBetween", notes = "Find all capsule account operation in pos between",
            responseContainer = "List<ClientCapsuleOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The clientcapsuleoperation list in a pos between 02 date found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllClientCapsuleOperationofTypeBetween(
            @ApiParam(name = "cltcapaccId", type = "Long", required = true,
                    value="Id of the concerned ClientCapsuleAccount", example = "1")
            @NotNull @PathVariable("cltcapaccId") Long cltcapaccId,
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

    @GetMapping(value = APP_ROOT+"/clientcapsuleaccount/operation/type/page/between/{cltcapaccId}/{optype}/{from}/{to}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageClientCapsuleOperationofTypeBetween", notes = "Find all capsule account operation page by page in pos between",
            responseContainer = "Page<ClientCapsuleOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The clientcapsuleoperation page in a pos between 02 date found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageClientCapsuleOperationofTypeBetween(
            @ApiParam(name = "cltcapaccId", type = "Long", required = true,
                    value="Id of the concerned ClientCapsuleAccount", example = "1")
            @NotNull @PathVariable("cltcapaccId") Long cltcapaccId,
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
