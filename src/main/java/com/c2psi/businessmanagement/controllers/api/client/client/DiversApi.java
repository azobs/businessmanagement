package com.c2psi.businessmanagement.controllers.api.client.client;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.client.client.DiversCashAccountDto;
import com.c2psi.businessmanagement.dtos.client.client.DiversCashOperationDto;
import com.c2psi.businessmanagement.dtos.client.client.DiversDto;
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
@Api(APP_ROOT+"/divers")
public interface DiversApi {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = APP_ROOT+"/divers/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveDivers",
            notes = "This method is used to save a divers in the DB",
            response = DiversDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Divers added successfully"),
            @ApiResponse(code=400, message="Object Divers is not valid during the saving process")
    })
    ResponseEntity saveDivers(
            @ApiParam(name = "diversDto", type = "DiversDto", required = true,
                    value="The JSON object that represent the Divers to save")
            @Valid @RequestBody DiversDto diversDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = APP_ROOT+"/divers/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "updateDivers",
            notes = "This method is used to update a divers in the DB",
            response = DiversDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Divers updated successfully"),
            @ApiResponse(code=400, message="Object Divers is not valid during the saving process")
    })
    ResponseEntity updateDivers(
            @ApiParam(name = "diversDto", type = "DiversDto", required = true,
                    value="The JSON object that represent the Divers to save")
            @Valid @RequestBody DiversDto diversDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/divers/id/{diversId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findDiversById", notes = "Search in the DB a divers by its Id",
            response = DiversDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The divers with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findDiversById(
            @ApiParam(name = "diversId", type = "Long", required = true,
                    value="Id of the concerned Divers", example = "1")
            @NotNull @PathVariable("diversId") Long diversId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/divers/email/{diversEmail}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findDiversById", notes = "Search in the DB a divers by its Id",
            response = DiversDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The divers with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findDiversByEmail(
            @ApiParam(name = "diversEmail", type = "String", required = true,
                    value="Email of the concerned Divers", example = "email@gmail.com")
            @NotNull @PathVariable("diversEmail") String diversEmail);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/divers/pos/all/{posId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllDiversofPos", notes = "Find all divers in a Pos",
            responseContainer = "List<DiversDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="List of divers of a pointofsale found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllDiversofPos(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/divers/pos/page/{posId}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageDiversofPos", notes = "Find all divers in a Pos page by page",
            responseContainer = "Page<DiversDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="Page of divers of a pointofsale found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageDiversofPos(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/divers/name/pos/all/{diversName}/{posId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllDiversByNameinPos", notes = "Find all divers in a Pos",
            responseContainer = "List<DiversDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="List of divers of a pointofsale found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllDiversByNameinPos(
            @ApiParam(name = "diversName", type = "String", required = true,
                    value="Name of the concerned divers", example = "1")
            @NotNull @PathVariable("diversName") String diversName,
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/divers/name/pos/page/{diversName}/{posId}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageDiversByNameinPos", notes = "Find all divers in a Pos page by page",
            responseContainer = "Page<DiversDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="Page of divers of a pointofsale found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageDiversByNameinPos(
            @ApiParam(name = "diversName", type = "String", required = true,
                    value="Name of the concerned divers", example = "1")
            @NotNull @PathVariable("diversName") String diversName,
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = APP_ROOT+"/divers/delete/id/{diversId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deleteDiversById",
            notes = "This method is used to delete a divers saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object divers deleted successfully")
    })
    ResponseEntity deleteDiversById(
            @ApiParam(name = "diversId", type = "Long", required = true,
                    value="Id of the divers to delete", example = "1")
            @NotNull @PathVariable("diversId") Long diversId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = APP_ROOT+"/divers/cash/operation/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveCashOperation",
            notes = "This method is used to save a divers cash operation in the DB",
            response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Divers cash operation added successfully"),
            @ApiResponse(code=400, message="Object Divers cash operation is not valid during the saving process")
    })
    ResponseEntity saveCashOperation(
            @ApiParam(name = "diversCashOperationDto", type = "DiversCashOperationDto", required = true,
                    value="The JSON object that represent the Divers cash operation to save")
            @Valid @RequestBody DiversCashOperationDto diversCashOperationDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = APP_ROOT+"/divers/cash/operation/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveDiversCashAccount",
            notes = "This method is used to save a divers cash account in the DB",
            response = DiversCashAccountDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Divers cash account added successfully"),
            @ApiResponse(code=400, message="Object Divers cash account is not valid during the saving process")
    })
    ResponseEntity saveDiversCashAccount(
            @ApiParam(name = "diversCashAccountDto", type = "DiversCashAccountDto", required = true,
                    value="The JSON object that represent the Divers cash account to save")
            @Valid @RequestBody DiversCashAccountDto diversCashAccountDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = APP_ROOT+"/diverscashaccount/delete/id/{dcaId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deleteDiversCashAccountById",
            notes = "This method is used to delete a divers cash account saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object divers cash account deleted successfully")
    })
    ResponseEntity deleteDiversCashAccountById(
            @ApiParam(name = "dcaId", type = "Long", required = true,
                    value="Id of the divers cash account to delete", example = "1")
            @NotNull @PathVariable("dcaId") Long dcaId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/diverscashaccount/id/{dcaId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findDiversCashAccountById", notes = "Find all divers in a Pos",
            responseContainer = "List<DiversDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="List of divers of a pointofsale found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findDiversCashAccountById(
            @ApiParam(name = "dcaId", type = "Long", required = true,
                    value="Id of the concerned DiversCashAccount", example = "1")
            @NotNull @PathVariable("dcaId") Long dcaId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/diverscashaccount/pos/{posId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findDiversCashAccountofPosIfExistOrCreate", notes = "Find divers cash account of pos",
            response = DiversCashAccountDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="List of divers of a pointofsale found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findDiversCashAccountofPosIfExistOrCreate(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = APP_ROOT+"/divers/cash/operation/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "updateDiversCashOperation",
            notes = "This method is used to update a divers cash operation in the DB",
            response = DiversCashOperationDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Divers cash operation updated successfully"),
            @ApiResponse(code=400, message="Object Divers cash operation is not valid during the saving process")
    })
    ResponseEntity updateDiversCashOperation(
            @ApiParam(name = "dcaopDto", type = "DiversCashOperationDto", required = true,
                    value="The JSON object that represent the DiversCashOperationDto to update")
            @Valid @RequestBody DiversCashOperationDto dcaopDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = APP_ROOT+"/divers/cash/operation/delete/id/{dcaopId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deleteDiversCashOperationById",
            notes = "This method is used to delete a divers cash operation saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object divers cash account deleted successfully")
    })
    ResponseEntity deleteDiversCashOperationById(
            @ApiParam(name = "dcaopId", type = "Long", required = true,
                    value="Id of the divers cash operation to delete", example = "1")
            @NotNull @PathVariable("dcaopId") Long dcaopId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/divers/cash/operation/id/{dcaopId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findDiversCashOperationById", notes = "Find divers cash account of pos",
            response = DiversCashOperationDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="List of divers of a pointofsale found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findDiversCashOperationById(
            @ApiParam(name = "dcaopId", type = "Long", required = true,
                    value="Id of the concerned diverscashoperation", example = "1")
            @NotNull @PathVariable("dcaopId") Long dcaopId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/divers/cash/operation/all/{dcaopId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllDiversCashOperation", notes = "Find all divers cash operation of pos",
            responseContainer = "List<DiversCashOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="List of divers cash operation of a pointofsale found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllDiversCashOperation(
            @ApiParam(name = "dcaopId", type = "Long", required = true,
                    value="Id of the concerned diverscashoperation", example = "1")
            @NotNull @PathVariable("dcaopId") Long dcaopId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/divers/cash/operation/page/{dcaopId}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageDiversCashOperation", notes = "Find all divers cash operation of pos page by page",
            responseContainer = "Page<DiversCashOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="Page of divers cash operation of a pointofsale found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageDiversCashOperation(
            @ApiParam(name = "dcaopId", type = "Long", required = true,
                    value="Id of the concerned diverscashoperation", example = "1")
            @NotNull @PathVariable("dcaopId") Long dcaopId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/divers/cash/operation/type/all/{dcaopId}/{opType}/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllDiversCashOperationofTypeBetween", notes = "Find all divers cash operation of pos",
            responseContainer = "List<DiversCashOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="List of divers cash operation of a pointofsale found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllDiversCashOperationofTypeBetween(
            @ApiParam(name = "dcaopId", type = "Long", required = true,
                    value="Id of the concerned diverscashoperation", example = "1")
            @NotNull @PathVariable("dcaopId") Long dcaopId,
            @ApiParam(name = "opType", type = "OperationType", required = true,
                    value="The concerned operation type", example = "Credit")
            @NotNull @PathVariable("opType")OperationType opType,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/divers/cash/operation/type/page/{dcaopId}/{opType}/{from}/{to}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageDiversCashOperationofTypeBetween", notes = "Find all divers cash operation of pos",
            responseContainer = "Page<DiversCashOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="Page of divers cash operation of a pointofsale found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageDiversCashOperationofTypeBetween(
            @ApiParam(name = "dcaopId", type = "Long", required = true,
                    value="Id of the concerned diverscashoperation", example = "1")
            @NotNull @PathVariable("dcaopId") Long dcaopId,
            @ApiParam(name = "opType", type = "OperationType", required = true,
                    value="The concerned operation type", example = "Credit")
            @NotNull @PathVariable("opType")OperationType opType,
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
