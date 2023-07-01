package com.c2psi.businessmanagement.controllers.api.pos.pos;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCapsuleAccountDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCapsuleOperationDto;
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
@Api(APP_ROOT+"/poscapsuleaccount")
public interface PosCapsuleAccountApi {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = APP_ROOT+"/pcapacc/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "savePosCapsuleAccount",
            notes = "This method is used to save a capsule account for an article in a pointofsale in the DB",
            response = PosCapsuleAccountDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object PosCapsuleAccount added successfully"),
            @ApiResponse(code=400, message="Object PosCapsuleAccount is not valid during the saving process")
    })
    ResponseEntity savePosCapsuleAccount(
            @ApiParam(name = "pcapaccDto", type = "PosCapsuleAccountDto", required = true,
                    value="The JSON object that represent the PosCapsuleAccountDto to save")
            @Valid @RequestBody PosCapsuleAccountDto pcapaccDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/pcapacc/id/{pcapaccId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPosCapsuleAccountById", notes = "Search in the DB a poscapsuleaccount by its Id",
            response = PosCapsuleAccountDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The poscapsuleaccount with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPosCapsuleAccountById(
            @ApiParam(name = "pcapaccId", type = "Long", required = true,
                    value="Id of the concerned PosCapsuleAccount", example = "1")
            @NotNull @PathVariable("pcapaccId") Long pcapaccId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/pcapacc/art/pos/{artId}/{posId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPosCapsuleAccountofArticleInPos", notes = "Search in the DB a poscapsuleaccount by its data",
            response = PosCapsuleAccountDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The poscapsuleaccount with the data precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPosCapsuleAccountofArticleInPos(
            @ApiParam(name = "artId", type = "Long", required = true,
                    value="Id of the concerned Article", example = "1")
            @NotNull @PathVariable("artId") Long artId,
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/pcapacc/all/pos/{posId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCapsuleAccountInPos", notes = "Find all capsule account in pos",
            responseContainer = "List<PosCapsuleAccountDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The poscapsuleaccount list for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCapsuleAccountInPos(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/pcapacc/page/pos/{posId}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCapsuleAccountInPos", notes = "Find all capsule account in pos page by page",
            responseContainer = "Page<PosCapsuleAccountDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The poscapsuleaccount page for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCapsuleAccountInPos(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/pcapacc/all/art/{artId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCapsuleAccountofArticle", notes = "Find all capsule account of article",
            responseContainer = "List<PosCapsuleAccountDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The poscapsuleaccount list for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCapsuleAccountofArticle(
            @ApiParam(name = "artId", type = "Long", required = true,
                    value="Id of the concerned Article", example = "1")
            @NotNull @PathVariable("artId") Long artId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/pcapacc/all/art/{artId}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCapsuleAccountofArticle", notes = "Find all capsule account of article page by page",
            responseContainer = "Page<PosCapsuleAccountDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The poscapsuleaccount page for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCapsuleAccountofArticle(
            @ApiParam(name = "artId", type = "Long", required = true,
                    value="Id of the concerned Article", example = "1")
            @NotNull @PathVariable("artId") Long artId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = APP_ROOT+"/pcapacc/delete/id/{pcapaccId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deletePosCapsuleAccountById",
            notes = "This method is used to delete poscapsuleaccount saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object poscapsuleaccount deleted successfully")
    })
    ResponseEntity deletePosCapsuleAccountById(
            @ApiParam(name = "pcapaccId", type = "Long", required = true,
                    value="Id of the PosCapsuleAccount to delete", example = "1")
            @NotNull @PathVariable("pcapaccId") Long pcapaccId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = APP_ROOT+"/pcapacc/operation/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveCapsuleOperation",
            notes = "This method is used to save a capsule operation for an article in a pointofsale in the DB",
            response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Operation PosCapsuleOperation added successfully"),
            @ApiResponse(code=400, message="Object PosCapsuleOperation is not valid during the saving process")
    })
    ResponseEntity saveCapsuleOperation(
            @ApiParam(name = "poscapopDto", type = "PosCapsuleOperationDto", required = true,
                    value="The JSON object that represent the PosCapsuleOperationDto to save")
            @Valid @RequestBody PosCapsuleOperationDto poscapopDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping (value = APP_ROOT+"/pcapacc/operation/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "updatePosCapsuleOperation",
            notes = "This method is used to update a capsule operation done in a pointofsale in the DB",
            response = PosCapsuleOperationDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object PosCapsuleAccount added successfully"),
            @ApiResponse(code=400, message="Object PosCapsuleAccount is not valid during the saving process")
    })
    ResponseEntity updatePosCapsuleOperation(
            @ApiParam(name = "pcapopDto", type = "PosCapsuleOperationDto", required = true,
                    value="The JSON object that represent the PosCapsuleOperationDto to save")
            @Valid @RequestBody PosCapsuleOperationDto pcapopDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = APP_ROOT+"/pcapacc/operation/delete/id/{pcapopId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deletePosCapsuleOperationById",
            notes = "This method is used to delete poscapsuleoperation saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object PosCapsuleOperation deleted successfully")
    })
    ResponseEntity deletePosCapsuleOperationById(
            @ApiParam(name = "pcapopId", type = "Long", required = true,
                    value="Id of the PosCapsuleOperation to delete", example = "1")
            @NotNull @PathVariable("pcapopId") Long pcapopId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/pcapacc/operation/all/{pcapopId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllPosCapsuleOperation", notes = "Find all capsule operation on an account",
            responseContainer = "List<PosCapsuleOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The poscapsuleoperation list for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllPosCapsuleOperation(
            @ApiParam(name = "pcapopId", type = "Long", required = true,
                    value="Id of the concerned capsule operation", example = "1")
            @NotNull @PathVariable("pcapopId") Long pcapopId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/pcapacc/operation/type/{pcapopId}/{optype}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllPosCapsuleOperationofType", notes = "Find all capsule operation of type on an account",
            responseContainer = "List<PosCapsuleOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The poscapsuleoperation list of type for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllPosCapsuleOperationofType(
            @ApiParam(name = "pcapopId", type = "Long", required = true,
                    value="Id of the concerned Capsule operation", example = "1")
            @NotNull @PathVariable("pcapopId") Long pcapopId,
            @ApiParam(name = "optype", type = "OperationType", required = true,
                    value="Operation type concerned", example = "Credit")
            @NotNull @PathVariable("optype") OperationType optype);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/pcapacc/operation/page/{pcapopId}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPagePosCapsuleOperation", notes = "Find all capsule operation on an account page by page",
            responseContainer = "Page<PosCapsuleOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The poscapsuleoperation page for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPagePosCapsuleOperation(
            @ApiParam(name = "pcapopId", type = "Long", required = true,
                    value="Id of the concerned Capsule operation", example = "1")
            @NotNull @PathVariable("pcapopId") Long pcapopId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/pcapacc/operation/page/type/{pcapopId}/{optype}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPagePosCapsuleOperationofType", notes = "Find all capsule operation of type on an account page by page",
            responseContainer = "Page<PosCapsuleOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The poscapsuleoperation page for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPagePosCapsuleOperationofType(
            @ApiParam(name = "pcapopId", type = "Long", required = true,
                    value="Id of the concerned Capsule operation", example = "1")
            @NotNull @PathVariable("pcapopId") Long pcapopId,
            @ApiParam(name = "optype", type = "OperationType", required = true,
                    value="Operation type concerned", example = "Credit")
            @NotNull @PathVariable("optype") OperationType optype,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/pcapacc/operation/all/between/{pcapopId}/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllPosCapsuleOperationBetween", notes = "Find all capsule operation on an account between",
            responseContainer = "List<PosCapsuleOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The poscapsuleoperation list between for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllPosCapsuleOperationBetween(
            @ApiParam(name = "pcapopId", type = "Long", required = true,
                    value="Id of the concerned Capsule operation", example = "1")
            @NotNull @PathVariable("pcapopId") Long pcapopId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/pcapacc/operation/page/between/{pcapopId}/{from}/{to}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPagePosCapsuleOperationBetween", notes = "Find all capsule operation on an account page by page between",
            responseContainer = "Page<PosCapsuleOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The poscapsuleoperation page between for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPagePosCapsuleOperationBetween(
            @ApiParam(name = "pcapopId", type = "Long", required = true,
                    value="Id of the concerned Capsule operation", example = "1")
            @NotNull @PathVariable("pcapopId") Long pcapopId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/pcapacc/operation/type/between/{pcapopId}/{optype}/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllPosCapsuleOperationofTypeBetween", notes = "Find all capsule operation of type on an account",
            responseContainer = "List<PosCapsuleOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The poscapsuleoperation list of type for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllPosCapsuleOperationofTypeBetween(
            @ApiParam(name = "pcapopId", type = "Long", required = true,
                    value="Id of the concerned Capsule operation", example = "1")
            @NotNull @PathVariable("pcapopId") Long pcapopId,
            @ApiParam(name = "optype", type = "OperationType", required = true,
                    value="Operation type concerned", example = "Credit")
            @NotNull @PathVariable("optype") OperationType optype,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/pcapacc/operation/type/between/{pcapopId}/{optype}/{from}/{to}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPagePosCapsuleOperationofTypeBetween", notes = "Find all Capsule operation of type on an account page by page",
            responseContainer = "Page<PosCapsuleOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The poscapsuleoperation page of type for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPagePosCapsuleOperationofTypeBetween(
            @ApiParam(name = "pcapopId", type = "Long", required = true,
                    value="Id of the concerned Capsule operation", example = "1")
            @NotNull @PathVariable("pcapopId") Long pcapopId,
            @ApiParam(name = "optype", type = "OperationType", required = true,
                    value="Operation type concerned", example = "Credit")
            @NotNull @PathVariable("optype") OperationType optype,
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
