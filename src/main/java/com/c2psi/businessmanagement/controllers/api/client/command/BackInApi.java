package com.c2psi.businessmanagement.controllers.api.client.command;

import com.c2psi.businessmanagement.dtos.client.command.BackInDetailsDto;
import com.c2psi.businessmanagement.dtos.client.command.BackInDto;
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
@Api(APP_ROOT+"/backin")
public interface BackInApi {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = APP_ROOT+"/backin/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveBackIn",
            notes = "This method is used to save a backin in the DB",
            response = BackInDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object BackInDto added successfully"),
            @ApiResponse(code=400, message="Object BackInDto is not valid during the saving process")
    })
    ResponseEntity saveBackIn(
            @ApiParam(name = "backInDto", type = "BackInDto", required = true,
                    value="The JSON object that represent the BackInDto to save")
            @Valid @RequestBody BackInDto backInDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = APP_ROOT+"/backin/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "updateBackIn",
            notes = "This method is used to update a backin in the DB",
            response = BackInDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object BackInDto updated successfully"),
            @ApiResponse(code=400, message="Object BackInDto is not valid during the saving process")
    })
    ResponseEntity updateBackIn(
            @ApiParam(name = "backInDto", type = "BackInDto", required = true,
                    value="The JSON object that represent the BackInDto to update")
            @Valid @RequestBody BackInDto backInDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/backin/id/{backinId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findBackInById", notes = "Search a Backin by id",
            response = BackInDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The BackIn found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findBackInById(
            @ApiParam(name = "backinId", type = "Long", required = true,
                    value="Id of the concerned BackIn", example = "1")
            @NotNull @PathVariable("backinId") Long backinId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/backin/command/{cmdId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findBackInofCommand", notes = "Search a Backin of command",
            response = BackInDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The BackIn of command found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findBackInofCommand(
            @ApiParam(name = "cmdId", type = "Long", required = true,
                    value="Id of the concerned Command", example = "1")
            @NotNull @PathVariable("cmdId") Long cmdId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/backin/pos/all/{posId}/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllBackIninPosBetween", notes = "find all Backin in pointofsale between 02 dates",
            responseContainer = "List<BackInDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The BackIn list in Pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllBackIninPosBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/backin/pos/page/{posId}/{from}/{to}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageBackIninPosBetween", notes = "find all Backin in pointofsale between 02 dates page by page",
            responseContainer = "Page<BackInDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The BackIn page in Pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageBackIninPosBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = APP_ROOT+"/backin/delete/id/{backinId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deleteBackInById",
            notes = "This method is used to delete the backIn saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object BackIn deleted successfully")
    })
    ResponseEntity deleteBackInById(
            @ApiParam(name = "backinId", type = "Long", required = true,
                    value="Id of the BackIn to delete", example = "1")
            @NotNull @PathVariable("backinId") Long backinId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = APP_ROOT+"/backin/details/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveBackInDetails",
            notes = "This method is used to save a BackInDetailsDto in the DB",
            response = BackInDetailsDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object BackInDetailsDto added successfully"),
            @ApiResponse(code=400, message="Object BackInDetailsDto is not valid during the saving process")
    })
    ResponseEntity saveBackInDetails(
            @ApiParam(name = "backInDetailsDto", type = "BackInDetailsDto", required = true,
                    value="The JSON object that represent the backInDetailsDto to save")
            @Valid @RequestBody BackInDetailsDto backInDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = APP_ROOT+"/backin/details/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "updateBackInDetails",
            notes = "This method is used to update a BackInDetailsDto in the DB",
            response = BackInDetailsDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object BackInDetailsDto updated successfully"),
            @ApiResponse(code=400, message="Object BackInDetailsDto is not valid during the saving process")
    })
    ResponseEntity updateBackInDetails(
            @ApiParam(name = "backInDetailsDto", type = "BackInDetailsDto", required = true,
                    value="The JSON object that represent the backInDetailsDto to update")
            @Valid @RequestBody BackInDetailsDto backInDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/backin/details/id/{backInDetailsId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findBackInDetailsById", notes = "Search a BackinDetails by id",
            response = BackInDetailsDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The BackIn found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findBackInDetailsById(
            @ApiParam(name = "backInDetailsId", type = "Long", required = true,
                    value="Id of the concerned BackInDetails", example = "1")
            @NotNull @PathVariable("backInDetailsId") Long backInDetailsId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/backin/details/article/{artId}/{backinId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findBackInDetailsofArticleinBackIn", notes = "Search a Backin details of command",
            response = BackInDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The BackInDetails of backin found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findBackInDetailsofArticleinBackIn(
            @ApiParam(name = "artId", type = "Long", required = true,
                    value="Id of the concerned Article", example = "1")
            @NotNull @PathVariable("artId") Long artId,
            @ApiParam(name = "backinId", type = "Long", required = true,
                    value="Id of the concerned BackIn", example = "1")
            @NotNull @PathVariable("backinId") Long backinId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/backin/details/all/{backInId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllBackInDetailsofBackIn", notes = "Search a BackinDetails by id",
            responseContainer = "List<BackInDetailsDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The BackIn list found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllBackInDetailsofBackIn(
            @ApiParam(name = "backInId", type = "Long", required = true,
                    value="Id of the concerned BackIn", example = "1")
            @NotNull @PathVariable("backInId") Long backInId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/backin/details/page/{backInId}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageBackInDetailsofBackIn", notes = "Search a BackinDetails by id",
            responseContainer = "Page<BackInDetailsDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The BackIn page found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageBackInDetailsofBackIn(
            @ApiParam(name = "backInId", type = "Long", required = true,
                    value="Id of the concerned BackIn", example = "1")
            @NotNull @PathVariable("backInId") Long backInId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = APP_ROOT+"/backin/details/delete/id/{backInDetailsId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deleteBackInDetailsById",
            notes = "This method is used to delete the backInDetails saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object BackInDetails deleted successfully")
    })
    ResponseEntity deleteBackInDetailsById(
            @ApiParam(name = "backInDetailsId", type = "Long", required = true,
                    value="Id of the BackInDetails to delete", example = "1")
            @NotNull @PathVariable("backInDetailsId") Long backInDetailsId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////






}
