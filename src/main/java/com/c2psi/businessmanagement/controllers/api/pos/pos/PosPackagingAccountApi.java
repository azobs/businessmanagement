package com.c2psi.businessmanagement.controllers.api.pos.pos;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.pos.pos.PosPackagingAccountDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosPackagingOperationDto;
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

import static com.c2psi.businessmanagement.utils.pos.pos.PosPackagingAccountApiConstant.*;

@Validated
@Api(POS_PACKAGING_ACCOUNT_ENDPOINT)
public interface PosPackagingAccountApi {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = CREATE_POS_PACKAGING_ACCOUNT_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "savePosPackagingAccount",
            notes = "This method is used to save a packaging account for a provider in a pointofsale in the DB",
            response = PosPackagingAccountDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object PosPackagingAccount added successfully"),
            @ApiResponse(code=400, message="Object PosPackagingAccount is not valid during the saving process")
    })
    ResponseEntity savePosPackagingAccount(
            @ApiParam(name = "pospackaccDto", type = "PosPackagingAccountDto", required = true,
                    value="The JSON object that represent the PosPackagingAccountDto to save")
            @Valid @RequestBody PosPackagingAccountDto pospackaccDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_POS_PACKAGING_ACCOUNT_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPosPackagingAccountById", notes = "Search in the DB a pospackagingaccount by its Id",
            response = PosPackagingAccountDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The pospackagingaccount with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPosPackagingAccountById(
            @ApiParam(name = "pospackaccId", type = "Long", required = true,
                    value="Id of the concerned PosPackagingAccountDto", example = "1")
            @NotNull @PathVariable("pospackaccId") Long pospackaccId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_POS_PACKAGING_ACCOUNT_IN_POS_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPosPackagingAccount", notes = "Search in the DB a pospackagingaccount by its attributes",
            response = PosPackagingAccountDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The pospackagingaccount with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPosPackagingAccountInPos(
            @ApiParam(name = "packagingId", type = "Long", required = true,
                    value="Id of the concerned Packaging", example = "1")
            @NotNull @PathVariable("packagingId") Long packagingId,
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_PACKAGING_ACCOUNT_IN_POS_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllPackagingAccountofPos", notes = "Find all packaging account in pos",
            responseContainer = "List<PosPackagingAccountDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The pospackagingaccount list in a pos for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllPosPackagingAccountinPos(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_PACKAGING_ACCOUNT_IN_POS_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPagePackagingAccountofPos", notes = "Find all packaging account in pos page by page",
            responseContainer = "Page<PosPackagingAccountDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The pospackagingaccount list in a pos for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPagePosPackagingAccountinPos(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = DELETE_POS_PACKAGING_ACCOUNT_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deletePosPackagingAccountById",
            notes = "This method is used to delete a PosPackagingOperation saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object PosPackagingAccount deleted successfully")
    })
    ResponseEntity deletePosPackagingAccountById(
            @ApiParam(name = "pospackId", type = "Long", required = true,
                    value="Id of the PosPackagingAccount to delete", example = "1")
            @NotNull @PathVariable("pospackId") Long pospackId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = CREATE_POS_PACKAGING_OPERATION_ENDPOINT,
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
            @ApiParam(name = "pospackopDto", type = "PosPackagingOperationDto", required = true,
                    value="The JSON object that represent the PosPackagingOperationDto to save")
            @Valid @RequestBody PosPackagingOperationDto pospackopDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = UPDATE_POS_PACKAGING_OPERATION_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "updatePosPackagingOperation",
            notes = "This method is used to update pointofsale operation in the DB",
            response = ProviderPackagingOperationDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object PosPackagingOperationDto updated successfully"),
            @ApiResponse(code=400, message="Object PosPackagingOperationDto is not valid during the updating process")
    })
    ResponseEntity updatePosPackagingOperation(
            @ApiParam(name = "pospackopDto", type = "PosPackagingOperationDto", required = true,
                    value="The JSON object that represent the PosPackagingOperationDto to update")
            @Valid @RequestBody PosPackagingOperationDto pospackopDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = DELETE_POS_PACKAGING_OPERATION_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deletePosPackagingOperationById",
            notes = "This method is used to delete a PosPackagingOperation saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object PosPackagingOperation deleted successfully")
    })
    ResponseEntity deletePosPackagingOperationById(
            @ApiParam(name = "pospackopId", type = "Long", required = true,
                    value="Id of the PosPackagingOperation to delete", example = "1")
            @NotNull @PathVariable("pospackopId") Long pospackopId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_POS_PACKAGING_OPERATION_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllPosPackagingOperation", notes = "Find all packaging account operation in pos",
            responseContainer = "List<PosPackagingOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The pospackagingoperation list in a pos for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllPosPackagingOperation(
            @ApiParam(name = "pospackaccId", type = "Long", required = true,
                    value="Id of the concerned PosPackagingAccount", example = "1")
            @NotNull @PathVariable("pospackaccId") Long pospackaccId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_POS_PACKAGING_OPERATION_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPagePosPackagingOperation", notes = "Find all packaging account operation in pos page by page",
            responseContainer = "Page<PosPackagingOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The pospackagingoperation page in a pos for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPagePosPackagingOperation(
            @ApiParam(name = "pospackaccId", type = "Long", required = true,
                    value="Id of the concerned PosPackagingAccount", example = "1")
            @NotNull @PathVariable("pospackaccId") Long pospackaccId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_POS_PACKAGING_OPERATION_OF_TYPE_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllPosPackagingOperationofType", notes = "Find all packaging account operation in pos for a " +
            "certain types",
            responseContainer = "List<PosPackagingOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The pospackagingoperation list in a pos for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllPosPackagingOperationofType(
            @ApiParam(name = "pospackaccId", type = "Long", required = true,
                    value="Id of the concerned PosPackagingAccount", example = "1")
            @NotNull @PathVariable("pospackaccId") Long pospackaccId,
            @ApiParam(name = "opType", type = "OperationType", required = true,
                    value="The concerned Operation type", example = "Credit")
            @NotNull @PathVariable("opType") OperationType opType);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_POS_PACKAGING_OPERATION_OF_TYPE_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPagePosPackagingOperationofType", notes = "Find all packaging account operation in pos for a " +
            "certain types page by page",
            responseContainer = "Page<PosPackagingOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The pospackagingoperation page in a pos for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPagePosPackagingOperationofType(
            @ApiParam(name = "pospackaccId", type = "Long", required = true,
                    value="Id of the concerned PosPackagingAccount", example = "1")
            @NotNull @PathVariable("pospackaccId") Long pospackaccId,
            @ApiParam(name = "opType", type = "OperationType", required = true,
                    value="The concerned Operation type", example = "Credit")
            @NotNull @PathVariable("opType") OperationType opType,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_POS_PACKAGING_OPERATION_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllPosPackagingOperationBetween", notes = "Find all packaging account operation in pos between",
            responseContainer = "List<PosPackagingOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The pospackagingoperation list in a pos between 02 date found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllPosPackagingOperationBetween(
            @ApiParam(name = "pospackaccId", type = "Long", required = true,
                    value="Id of the concerned PosPackagingAccount", example = "1")
            @NotNull @PathVariable("pospackaccId") Long pospackaccId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_POS_PACKAGING_OPERATION_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPagePosPackagingOperationBetween", notes = "Find all packaging account operation in pos between",
            responseContainer = "Page<PosPackagingOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The pospackagingoperation page in a pos between 02 date found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPagePosPackagingOperationBetween(
            @ApiParam(name = "pospackaccId", type = "Long", required = true,
                    value="Id of the concerned PosPackagingAccount", example = "1")
            @NotNull @PathVariable("pospackaccId") Long pospackaccId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_POS_PACKAGING_OPERATION_OF_TYPE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllPosPackagingOperationofTypeBetween", notes = "Find all packaging account operation in pos between",
            responseContainer = "List<PosPackagingOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The pospackagingoperation list in a pos between 02 date found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllPosPackagingOperationofTypeBetween(
            @ApiParam(name = "pospackaccId", type = "Long", required = true,
                    value="Id of the concerned PosPackagingAccount", example = "1")
            @NotNull @PathVariable("pospackaccId") Long pospackaccId,
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

    @GetMapping(value = FIND_PAGE_POS_PACKAGING_OPERATION_OF_TYPE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPagePosPackagingOperationofTypeBetween", notes = "Find all packaging account operation in pos between",
            responseContainer = "Page<PosPackagingOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The pospackagingoperation page in a pos between 02 date found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPagePosPackagingOperationofTypeBetween(
            @ApiParam(name = "pospackaccId", type = "Long", required = true,
                    value="Id of the concerned PosPackagingAccount", example = "1")
            @NotNull @PathVariable("pospackaccId") Long pospackaccId,
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
