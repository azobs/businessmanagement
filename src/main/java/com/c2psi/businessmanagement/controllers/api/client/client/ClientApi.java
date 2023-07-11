package com.c2psi.businessmanagement.controllers.api.client.client;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.client.client.ClientCashAccountDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientCashOperationDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
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

import static com.c2psi.businessmanagement.utils.client.client.ClientApiConstant.*;

@Validated
@Api(CLIENT_ENDPOINT)
public interface ClientApi {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = CREATE_CLIENT_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveClient",
            notes = "This method is used to save a client in the DB",
            response = ClientDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Client added successfully"),
            @ApiResponse(code=400, message="Object Client is not valid during the saving process")
    })
    ResponseEntity saveClient(
            @ApiParam(name = "clientDto", type = "ClientDto", required = true,
                    value="The JSON object that represent the Client to save")
            @Valid @RequestBody ClientDto clientDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = UPDATE_CLIENT_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "updateClient",
            notes = "This method is used to update a client in the DB",
            response = ClientDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Client updated successfully"),
            @ApiResponse(code=400, message="Object Client is not valid during the updating process")
    })
    ResponseEntity updateClient(
            @ApiParam(name = "clientDto", type = "ClientDto", required = true,
                    value="The JSON object that represent the Client to update")
            @Valid @RequestBody ClientDto clientDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_CLIENT_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findClientById", notes = "Search in the DB a client by its Id",
            response = ClientDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The client with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findClientById(
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_CLIENT_BY_NAME_IN_POS_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findClientByNameofPos", notes = "Search in the DB a client by its name and pos",
            response = ClientDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The client with the name precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findClientByNameofPos(
            @ApiParam(name = "clientName", type = "String", required = true,
                    value="Name of the concerned Client", example = "name")
            @NotNull @PathVariable("clientName") String clientName,
            @ApiParam(name = "clientOthername", type = "String", required = true,
                    value="Other name of the concerned Client", example = "name")
            @NotNull @PathVariable("clientOthername") String clientOthername,
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_CLIENT_BY_CNI_IN_POS_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findClientByCniofPos", notes = "Search in the DB a client by its cni number and pos",
            response = ClientDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The client with the name precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findClientByCniofPos(
            @ApiParam(name = "clientCni", type = "String", required = true,
                    value="Cni number of the concerned Client", example = "cni")
            @NotNull @PathVariable("clientCni") String clientCni,
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_CLIENT_BY_EMAIL_IN_POS_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findClientByEmail", notes = "Search in the DB a client by its email address",
            response = ClientDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The client with the email address precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findClientByEmail(
            @ApiParam(name = "clientEmail", type = "String", required = true,
                    value="Cni number of the concerned Client", example = "email@gmail.com")
            @NotNull @PathVariable("clientEmail") String clientEmail);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_CLIENT_IN_POS_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllClientofPos", notes = "Search the list of client of a pointofsale",
            responseContainer = "List<ClientDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The client list in the pos precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllClientofPos(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_CLIENT_IN_POS_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageClientofPos", notes = "Search the list of client of a pointofsale page by page",
            responseContainer = "Page<ClientDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The client page in the pos precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageClientofPos(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = DELETE_CLIENT_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deleteClientById",
            notes = "This method is used to delete a client saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object client deleted successfully")
    })
    ResponseEntity deleteClientById(
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the client to delete", example = "1")
            @NotNull @PathVariable("clientId") Long clientId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = CREATE_CLIENT_CASH_OPERATION_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveCashOperation",
            notes = "This method is used to save a cash operation done on a client Cash account in the DB",
            response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object ClientCashOperation added successfully"),
            @ApiResponse(code=400, message="Object ClientCashOperation is not valid during the saving process")
    })
    ResponseEntity saveCashOperation(
            @ApiParam(name = "clientCashOperationDto", type = "ClientCashOperationDto", required = true,
                    value="The JSON object that represent the ClientCashOperationDto to save")
            @Valid @RequestBody ClientCashOperationDto clientCashOperationDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = CREATE_CLIENT_CASH_ACCOUNT_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveClientCashAccount",
            notes = "This method is used to save client cash account in the DB",
            response = ClientCashAccountDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object ClientCashAccount added successfully"),
            @ApiResponse(code=400, message="Object ClientCashAccount is not valid during the saving process")
    })
    ResponseEntity saveClientCashAccount(
            @ApiParam(name = "clientCashAccountDto", type = "ClientCashAccountDto", required = true,
                    value="The JSON object that represent the ClientCashAccountDto to save")
            @Valid @RequestBody ClientCashAccountDto clientCashAccountDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_CLIENT_CASH_ACCOUNT_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findClientCashAccountById", notes = "Search in the DB a clientcashaccount by its Id",
            response = ClientCashAccountDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The clientcashaccount with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findClientCashAccountById(
            @ApiParam(name = "ccaId", type = "Long", required = true,
                    value="Id of the concerned ClientCashAccount", example = "1")
            @NotNull @PathVariable("ccaId") Long ccaId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = DELETE_CLIENT_CASH_ACCOUNT_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deleteClientCashAccountById",
            notes = "This method is used to delete a clientCashAccount saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object clientCashAccount deleted successfully")
    })
    ResponseEntity deleteClientCashAccountById(
            @ApiParam(name = "ccaId", type = "Long", required = true,
                    value="Id of the ClientCashAccount to delete", example = "1")
            @NotNull @PathVariable("ccaId") Long ccaId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = UPDATE_CLIENT_CASH_OPERATION_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "updateClientCashOperation",
            notes = "This method is used to update a client cash operation in the DB",
            response = ClientCashOperationDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Client cash operation updated successfully"),
            @ApiResponse(code=400, message="Object Client cash operation is not valid during the updating process")
    })
    ResponseEntity updateClientCashOperation(
            @ApiParam(name = "cltCashOpDto", type = "ClientCashOperationDto", required = true,
                    value="The JSON object that represent the Client to update")
            @Valid @RequestBody ClientCashOperationDto cltCashOpDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = DELETE_CLIENT_CASH_OPERATION_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deleteClientCashOperationById",
            notes = "This method is used to delete a clientCashOperation saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object clientCashOperation deleted successfully")
    })
    ResponseEntity deleteClientCashOperationById(
            @ApiParam(name = "ccaopId", type = "Long", required = true,
                    value="Id of the ClientCashOperation to delete", example = "1")
            @NotNull @PathVariable("ccaopId") Long ccaopId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_CLIENT_CASH_OPERATION_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findClientCashOperationById", notes = "Search in the DB a clientcashoperation by its Id",
            response = ClientCashOperationDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The clientcashoperation with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findClientCashOperationById(
            @ApiParam(name = "ccaopId", type = "Long", required = true,
                    value="Id of the concerned ClientCashAccount", example = "1")
            @NotNull @PathVariable("ccaopId") Long ccaopId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_CLIENT_CASH_OPERATION_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllClientCashOperation", notes = "Search in the DB a clientcashoperation the list " +
            "of operation",
            responseContainer = "List<ClientCashOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The clientcashoperation list with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllClientCashOperation(
            @ApiParam(name = "ccaId", type = "Long", required = true,
                    value="Id of the concerned ClientCashAccount", example = "1")
            @NotNull @PathVariable("ccaId") Long ccaId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_CLIENT_CASH_OPERATION_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageClientCashOperation", notes = "Search in the DB a clientcashoperation the list of " +
            "operation",
            responseContainer = "Page<ClientCashOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The clientcashoperation page with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageClientCashOperation(
            @ApiParam(name = "ccaopId", type = "Long", required = true,
                    value="Id of the concerned ClientCashAccount", example = "1")
            @NotNull @PathVariable("ccaId") Long ccaId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_CLIENT_CASH_OPERATION_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllClientCashOperationBetween", notes = "Search in the DB a clientcashoperation " +
            "the list of operation",
            responseContainer = "List<ClientCashOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The clientcashoperation list with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllClientCashOperationBetween(
            @ApiParam(name = "ccaId", type = "Long", required = true,
                    value="Id of the concerned ClientCashAccount", example = "1")
            @NotNull @PathVariable("ccaId") Long ccaId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_CLIENT_CASH_OPERATION_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllClientCashOperationBetween", notes = "Search in the DB a clientcashoperation " +
            "the list of operation",
            responseContainer = "Page<ClientCashOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The clientcashoperation page with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageClientCashOperationBetween(
            @ApiParam(name = "ccaId", type = "Long", required = true,
                    value="Id of the concerned ClientCashAccount", example = "1")
            @NotNull @PathVariable("ccaId") Long ccaId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_CLIENT_CASH_OPERATION_OF_TYPE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllClientCashOperationofTypeBetween", notes = "Search in the DB a clientcashoperation the " +
            "list of operation",
            responseContainer = "List<ClientCashOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The clientcashoperation list with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllClientCashOperationofTypeBetween(
            @ApiParam(name = "ccaId", type = "Long", required = true,
                    value="Id of the concerned ClientCashAccount", example = "1")
            @NotNull @PathVariable("ccaId") Long ccaId,
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

    @GetMapping(value = FIND_PAGE_CLIENT_CASH_OPERATION_OF_TYPE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageClientCashOperationofTypeBetween", notes = "Search in the DB a clientcashoperation the " +
            "list of operation",
            responseContainer = "Page<ClientCashOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The clientcashoperation page with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageClientCashOperationofTypeBetween(
            @ApiParam(name = "ccaId", type = "Long", required = true,
                    value="Id of the concerned ClientCashAccount", example = "1")
            @NotNull @PathVariable("ccaId") Long ccaId,
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
