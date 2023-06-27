package com.c2psi.businessmanagement.controllers.api.client.client;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.client.client.ClientPackagingAccountDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientPackagingOperationDto;
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
@Api(APP_ROOT+"/clientpackagingaccount")
public interface ClientPackagingAccountApi {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = APP_ROOT+"/clientpackagingaccount/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveClientPackagingAccount",
            notes = "This method is used to save a packaging account for a client and an article in a pointofsale in the DB",
            response = ClientPackagingAccountDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object ClientPackagingAccount added successfully"),
            @ApiResponse(code=400, message="Object ClientPackagingAccount is not valid during the saving process")
    })
    ResponseEntity saveClientPackagingAccount(
            @ApiParam(name = "cltpackaccDto", type = "ClientPackagingAccountDto", required = true,
                    value="The JSON object that represent the ClientPackagingAccountDto to save")
            @Valid @RequestBody ClientPackagingAccountDto cltpackaccDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/clientpackagingaccount/id/{cltpackaccId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findClientPackagingAccountById", notes = "Search in the DB a clientpackagingaccount by its Id",
            response = ClientPackagingAccountDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The clientpackagingaccount with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findClientPackagingAccountById(
            @ApiParam(name = "cltpackaccId", type = "Long", required = true,
                    value="Id of the concerned ClientPackagingAccountDto", example = "1")
            @NotNull @PathVariable("cltpackaccId") Long cltpackaccId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/clientpackagingaccount/client/packaging/{clientId}/{packagingId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findClientPackagingAccountofArticleinPos", notes = "Search in the DB a clientpackagingaccount " +
            "for an article a client in a DB",
            response = ClientPackagingAccountDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The clientpackagingaccount for an article and a client precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findClientPackagingAccountByClientAndPackaging(
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned ClientDto", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "packagingId", type = "Long", required = true,
                    value="Id of the concerned PackagingDto", example = "1")
            @NotNull @PathVariable("packagingId") Long packagingId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/clientpackagingaccount/all/{posId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllClientPackagingAccountinPos", notes = "Find all packaging account in pos",
            responseContainer = "List<ClientPackagingAccountDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The clientpackagingaccount list in a pos for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllClientPackagingAccountinPos(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned pos", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/clientpackagingaccount/page/{posId}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageClientPackagingAccountinPos", notes = "Find page packaging account in pos",
            responseContainer = "Page<ClientPackagingAccountDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The clientpackagingaccount page in a pos for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageClientPackagingAccountinPos(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/clientpackagingaccount/all/{clientId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllClientPackagingAccountinPos", notes = "Find all packaging account of client",
            responseContainer = "List<ClientPackagingAccountDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The clientpackagingaccount list in a pos for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllPackagingAccountofClient(
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/clientpackagingaccount/page/{clientId}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageClientPackagingAccountinPos", notes = "Find all packaging account of client page by page",
            responseContainer = "Page<ClientPackagingAccountDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The clientpackagingaccount page in a pos for a client found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPagePackagingAccountofClient(
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = APP_ROOT+"/clientpackagingaccount/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "savePackagingOperation",
            notes = "This method is used to save a packaging account operation for a client and an article in a pointofsale in the DB",
            response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The operation added successfully"),
            @ApiResponse(code=400, message="The operation is not valid during the saving process")
    })
    ResponseEntity savePackagingOperationforClient(
            @ApiParam(name = "cltpackaccDto", type = "ClientPackagingAccountDto", required = true,
                    value="The JSON object that represent the ClientPackagingAccountDto to save")
            @Valid @RequestBody ClientPackagingAccountDto cltpackaccDto, BindingResult bindingResult1,
            @ApiParam(name = "cltpackopDto", type = "ClientPackagingOperationDto", required = true,
                    value="The JSON object that represent the ClientPackagingOperationDto to save")
            @Valid @RequestBody ClientPackagingOperationDto cltpackopDto, BindingResult bindingResult2);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = APP_ROOT+"/clientpackagingaccount/delete/{cltpackaccId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deleteClientPackagingAccountById",
            notes = "This method is used to delete a ClientPackagingAccount saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object ClientPackagingAccount deleted successfully")
    })
    ResponseEntity deleteClientPackagingAccountById(
            @ApiParam(name = "cltpackaccId", type = "Long", required = true,
                    value="Id of the ClientPackagingAccount to delete", example = "1")
            @NotNull @PathVariable("cltpackaccId") Long cltpackaccId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = APP_ROOT+"/clientpackagingaccount/operation/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "updateClientPackagingOperation",
            notes = "This method is used to update client operation in the DB",
            response = ClientPackagingOperationDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object ClientPackagingOperationDto updated successfully"),
            @ApiResponse(code=400, message="Object ClientPackagingOperationDto is not valid during the updating process")
    })
    ResponseEntity updateClientPackagingOperation(
            @ApiParam(name = "clientDto", type = "ClientDto", required = true,
                    value="The JSON object that represent the Client to update")
            @Valid @RequestBody ClientPackagingOperationDto cltpackopDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = APP_ROOT+"/clientpackagingaccount/operation/delete/{cltpackopId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deleteClientPackagingOperationById",
            notes = "This method is used to delete a ClientPackagingOperation saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object ClientPackagingOperation deleted successfully")
    })
    ResponseEntity deleteClientPackagingOperationById(
            @ApiParam(name = "cltpackopId", type = "Long", required = true,
                    value="Id of the ClientPackagingOperation to delete", example = "1")
            @NotNull @PathVariable("cltpackopId") Long cltpackopId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/clientpackagingaccount/operation/all/{cltpackaccId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllClientPackagingOperation", notes = "Find all packaging account operation in pos",
            responseContainer = "Page<ClientPackagingOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The clientpackagingoperation list in a pos for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllClientPackagingOperation(
            @ApiParam(name = "cltpackaccId", type = "Long", required = true,
                    value="Id of the concerned ClientPackagingAccount", example = "1")
            @NotNull @PathVariable("cltpackaccId") Long cltpackaccId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/clientpackagingaccount/operation/page/{cltpackaccId}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageClientPackagingOperation", notes = "Find all packaging account operation in pos page by page",
            responseContainer = "Page<ClientPackagingOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The cltpackagingoperation page in a pos for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageClientPackagingOperation(
            @ApiParam(name = "cltpackaccId", type = "Long", required = true,
                    value="Id of the concerned ClientPackagingAccount", example = "1")
            @NotNull @PathVariable("cltpackaccId") Long cltpackaccId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/clientpackagingaccount/operation/type/all/{cltpackaccId}/{optype}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllClientPackagingOperationofType", notes = "Find all packaging account operation in pos",
            responseContainer = "List<ClientPackagingOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The clientpackagingoperation list in a pos for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllClientPackagingOperationofType(
            @ApiParam(name = "cltpackaccId", type = "Long", required = true,
                    value="Id of the concerned ClientPackagingAccount", example = "1")
            @NotNull @PathVariable("cltpackaccId") Long cltpackaccId,
            @ApiParam(name = "opType", type = "OperationType", required = true,
                    value="The concerned Operation type", example = "Credit")
            @NotNull @PathVariable("opType") OperationType opType);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/clientpackagingaccount/operation/type/page/{cltpackaccId}/{optype}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageClientPackagingOperationofType", notes = "Find all packaging account operation in pos page by page",
            responseContainer = "Page<ClientPackagingOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The clientPackagingoperation page in a pos for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageClientPackagingOperationofType(
            @ApiParam(name = "cltcapaccId", type = "Long", required = true,
                    value="Id of the concerned clientPackagingAccount", example = "1")
            @NotNull @PathVariable("cltpackaccId") Long cltpackaccId,
            @ApiParam(name = "opType", type = "OperationType", required = true,
                    value="The concerned Operation type", example = "Credit")
            @NotNull @PathVariable("opType") OperationType opType,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/clientpackagingaccount/operation/all/between/{cltpackaccId}/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllClientPackagingOperationBetween", notes = "Find all Packaging account operation in pos between",
            responseContainer = "List<ClientPackagingOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The clientPackagingoperation list in a pos between 02 date found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllClientPackagingOperationBetween(
            @ApiParam(name = "cltpackaccId", type = "Long", required = true,
                    value="Id of the concerned ClientPackagingAccount", example = "1")
            @NotNull @PathVariable("cltpackaccId") Long cltpackaccId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/clientpackagingaccount/operation/page/between/{cltpackaccId}/{from}/{to}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageClientPackagingOperationBetween", notes = "Find page Packaging account operation in pos between",
            responseContainer = "Page<ClientPackagingOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The clientPackagingoperation page in a pos between 02 date found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageClientPackagingOperationBetween(
            @ApiParam(name = "cltpackaccId", type = "Long", required = true,
                    value="Id of the concerned ClientPackagingAccount", example = "1")
            @NotNull @PathVariable("cltpackaccId") Long cltpackaccId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/clientpackagingaccount/operation/type/all/between/{cltpackaccId}/{optype}/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllClientPackagingOperationofTypeBetween", notes = "Find all Packaging account operation in pos between",
            responseContainer = "List<ClientPackagingOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The clientPackagingoperation list in a pos between 02 date found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllClientPackagingOperationofTypeBetween(
            @ApiParam(name = "cltpackaccId", type = "Long", required = true,
                    value="Id of the concerned ClientPackagingAccount", example = "1")
            @NotNull @PathVariable("cltpackaccId") Long cltpackaccId,
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

    @GetMapping(value = APP_ROOT+"/clientpackagingaccount/operation/type/page/between/{cltpackaccId}/{optype}/{from}/{to}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageClientPackagingOperationofTypeBetween", notes = "Find all Packaging account operation page by page in pos between",
            responseContainer = "Page<ClientPackagingOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The clientPackagingoperation page in a pos between 02 date found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageClientPackagingOperationofTypeBetween(
            @ApiParam(name = "cltpackaccId", type = "Long", required = true,
                    value="Id of the concerned ClientPackagingAccount", example = "1")
            @NotNull @PathVariable("cltpackaccId") Long cltpackaccId,
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
