package com.c2psi.businessmanagement.controllers.api.pos.pos;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.pos.pos.PosDamageAccountDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosDamageOperationDto;
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

import static com.c2psi.businessmanagement.utils.pos.pos.PosDamageAccountApiConstant.*;

@Validated
@Api(POS_DAMAGE_ACCOUNT_ENDPOINT)
public interface PosDamageAccountApi {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = CREATE_POS_DAMAGE_ACCOUNT_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "savePosDamageAccount",
            notes = "This method is used to save a damage account for an article in a pointofsale in the DB",
            response = PosDamageAccountDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object PosDamageAccount added successfully"),
            @ApiResponse(code=400, message="Object PosDamageAccount is not valid during the saving process")
    })
    ResponseEntity savePosDamageAccount(
            @ApiParam(name = "pdamaccDto", type = "PosDamageAccountDto", required = true,
                    value="The JSON object that represent the PosDamageAccountDto to save")
            @Valid @RequestBody PosDamageAccountDto pdamaccDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_POS_DAMAGE_ACCOUNT_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPosDamageAccountById", notes = "Search in the DB a poscapsuleaccount by its Id",
            response = PosDamageAccountDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The poscapsuleaccount with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPosDamageAccountById(
            @ApiParam(name = "pdamaccId", type = "Long", required = true,
                    value="Id of the concerned PosDamageAccountDto", example = "1")
            @NotNull @PathVariable("pdamaccId") Long pdamaccId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_POS_DAMAGE_ACCOUNT_OF_ARTICLE_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPosDamageAccountofArticleInPos", notes = "Search in the DB a posdamageaccount by its Id",
            response = PosDamageAccountDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The posdamageaccount with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPosDamageAccountofArticleInPos(
            @ApiParam(name = "artId", type = "Long", required = true,
                    value="Id of the concerned Article", example = "1")
            @NotNull @PathVariable("artId") Long artId,
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_DAMAGE_ACCOUNT_IN_POS_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllDamageAccountInPos", notes = "Find all damage account in pos",
            responseContainer = "List<PosDamageAccountDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The posdamageaccount list for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllDamageAccountInPos(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_DAMAGE_ACCOUNT_IN_POS_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageDamageAccountInPos", notes = "Find all damage account in pos page by page",
            responseContainer = "Page<PosDamageAccountDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The poscapsuleaccount page for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageDamageAccountInPos(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_DAMAGE_ACCOUNT_OF_ARTICLE_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllDamageAccountofArticle", notes = "Find all damage account of article",
            responseContainer = "List<PosDamageAccountDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The posdamageaccount list for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllDamageAccountofArticle(
            @ApiParam(name = "artId", type = "Long", required = true,
                    value="Id of the concerned Article", example = "1")
            @NotNull @PathVariable("artId") Long artId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_DAMAGE_ACCOUNT_OF_ARTICLE_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageDamageAccountofArticle", notes = "Find all damage account of article page by page",
            responseContainer = "Page<PosDamageAccountDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The posdamageaccount page for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageDamageAccountofArticle(
            @ApiParam(name = "artId", type = "Long", required = true,
                    value="Id of the concerned Article", example = "1")
            @NotNull @PathVariable("artId") Long artId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = DELETE_POS_DAMAGE_ACCOUNT_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deletePosDamageAccountById",
            notes = "This method is used to delete posdamageaccount saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object posdamageaccount deleted successfully")
    })
    ResponseEntity deletePosDamageAccountById(
            @ApiParam(name = "pdamaccId", type = "Long", required = true,
                    value="Id of the PosDamageAccount to delete", example = "1")
            @NotNull @PathVariable("pdamaccId") Long pdamaccId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = CREATE_POS_DAMAGE_OPERATION_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveDamageOperation",
            notes = "This method is used to save a damage operation for an article in a pointofsale in the DB",
            response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Operation PosDamageOperation added successfully"),
            @ApiResponse(code=400, message="Object PosDamageOperation is not valid during the saving process")
    })
    ResponseEntity saveDamageOperation(
            @ApiParam(name = "posdamopDto", type = "PosDamageOperationDto", required = true,
                    value="The JSON object that represent the PosDamageOperationDto to save")
            @Valid @RequestBody PosDamageOperationDto posdamopDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping (value = UPDATE_POS_DAMAGE_OPERATION_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "updatePosDamageOperation",
            notes = "This method is used to update a damage operation done in a pointofsale in the DB",
            response = PosDamageOperationDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object PosDamageAccount added successfully"),
            @ApiResponse(code=400, message="Object PosDamageAccount is not valid during the saving process")
    })
    ResponseEntity updatePosDamageOperation(
            @ApiParam(name = "pdamopDto", type = "PosDamageOperationDto", required = true,
                    value="The JSON object that represent the PosDamageOperationDto to save")
            @Valid @RequestBody PosDamageOperationDto pdamopDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = DELETE_POS_DAMAGE_OPERATION_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deletePosDamageOperationById",
            notes = "This method is used to delete posdamageoperation saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object PosDamageOperation deleted successfully")
    })
    ResponseEntity deletePosDamageOperationById(
            @ApiParam(name = "pdamopId", type = "Long", required = true,
                    value="Id of the PosDamageOperation to delete", example = "1")
            @NotNull @PathVariable("pdamopId") Long pdamopId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_POS_DAMAGE_OPERATION_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllPosDamageOperation", notes = "Find all damage operation on an account",
            responseContainer = "List<PosDamageOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The posdamageoperation list for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllPosDamageOperation(
            @ApiParam(name = "pdamopId", type = "Long", required = true,
                    value="Id of the concerned Damage operation", example = "1")
            @NotNull @PathVariable("pdamopId") Long pdamopId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_POS_DAMAGE_OPERATION_OF_TYPE_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllPosDamageOperationofType", notes = "Find all damage operation of type on an account",
            responseContainer = "List<PosDamageOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The posdamageoperation list of type for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllPosDamageOperationofType(
            @ApiParam(name = "pdamopId", type = "Long", required = true,
                    value="Id of the concerned Damage operation", example = "1")
            @NotNull @PathVariable("pdamopId") Long pdamopId,
            @ApiParam(name = "optype", type = "OperationType", required = true,
                    value="Operation type concerned", example = "Credit")
            @NotNull @PathVariable("optype") OperationType optype);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_POS_DAMAGE_OPERATION_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPagePosDamageOperation", notes = "Find all damage operation on an account page by page",
            responseContainer = "Page<PosDamageOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The posdamageoperation page for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPagePosDamageOperation(
            @ApiParam(name = "pdamopId", type = "Long", required = true,
                    value="Id of the concerned Damage operation", example = "1")
            @NotNull @PathVariable("pdamopId") Long pdamopId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_POS_DAMAGE_OPERATION_OF_TYPE_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPagePosDamageOperationofType", notes = "Find all damage operation of type on an account page by page",
            responseContainer = "Page<PosDamageOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The posdamageoperation page for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPagePosDamageOperationofType(
            @ApiParam(name = "pdamopId", type = "Long", required = true,
                    value="Id of the concerned Damage operation", example = "1")
            @NotNull @PathVariable("pdamopId") Long pdamopId,
            @ApiParam(name = "optype", type = "OperationType", required = true,
                    value="Operation type concerned", example = "Credit")
            @NotNull @PathVariable("optype") OperationType optype,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_POS_DAMAGE_OPERATION_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllPosDamageOperationBetween", notes = "Find all damage operation on an account between",
            responseContainer = "List<PosDamageOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The posdamageoperation list between for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllPosDamageOperationBetween(
            @ApiParam(name = "pdamopId", type = "Long", required = true,
                    value="Id of the concerned Damage operation", example = "1")
            @NotNull @PathVariable("pdamopId") Long pdamopId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_POS_DAMAGE_OPERATION_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPagePosDamageOperationBetween", notes = "Find all damage operation on an account page by page between",
            responseContainer = "Page<PosDamageOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The posdamageoperation page between for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPagePosDamageOperationBetween(
            @ApiParam(name = "pdamopId", type = "Long", required = true,
                    value="Id of the concerned Damage operation", example = "1")
            @NotNull @PathVariable("pdamopId") Long pdamopId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_POS_DAMAGE_OPERATION_OF_TYPE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllPosDamageOperationofTypeBetween", notes = "Find all damage operation of type on an account",
            responseContainer = "List<PosDamageOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The posdamageoperation list of type for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllPosDamageOperationofTypeBetween(
            @ApiParam(name = "pdamopId", type = "Long", required = true,
                    value="Id of the concerned Damage operation", example = "1")
            @NotNull @PathVariable("pdamopId") Long pdamopId,
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

    @GetMapping(value = FIND_PAGE_POS_DAMAGE_OPERATION_OF_TYPE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPagePosDamageOperationofType", notes = "Find all damage operation of type on an account page by page",
            responseContainer = "Page<PosDamageOperationDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The posdamageoperation page of type for pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPagePosDamageOperationofTypeBetween(
            @ApiParam(name = "pdamopId", type = "Long", required = true,
                    value="Id of the concerned Damage operation", example = "1")
            @NotNull @PathVariable("pdamopId") Long pdamopId,
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
