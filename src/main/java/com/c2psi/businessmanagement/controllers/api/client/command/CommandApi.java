package com.c2psi.businessmanagement.controllers.api.client.command;

import com.c2psi.businessmanagement.Enumerations.CommandState;
import com.c2psi.businessmanagement.Enumerations.CommandStatus;
import com.c2psi.businessmanagement.Enumerations.CommandType;
import com.c2psi.businessmanagement.Enumerations.DeliveryState;
import com.c2psi.businessmanagement.dtos.client.command.CommandDto;
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


import static com.c2psi.businessmanagement.utils.client.command.CommandApiConstant.*;

@Validated
@Api(COMMAND_ENDPOINT)
public interface CommandApi {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = CREATE_COMMAND_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveCommand",
            notes = "This method is used to save a command in the DB",
            response = CommandDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object CommandDto added successfully"),
            @ApiResponse(code=400, message="Object CommandDto is not valid during the saving process")
    })
    ResponseEntity saveCommand(
            @ApiParam(name = "cmdDto", type = "CommandDto", required = true,
                    value="The JSON object that represent the CommandDto to save")
            @Valid @RequestBody CommandDto cmdDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = UPDATE_COMMAND_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "updateCommand",
            notes = "This method is used to update a command in the DB",
            response = CommandDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object CommandDto updated successfully"),
            @ApiResponse(code=400, message="Object CommandDto is not valid during the updating process")
    })
    ResponseEntity updateCommand(
            @ApiParam(name = "cmdDto", type = "CommandDto", required = true,
                    value="The JSON object that represent the CommandDto to update")
            @Valid @RequestBody CommandDto cmdDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = SET_SALEINVOICE_FOR_COMMANDSTATUS_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "setSaleInvoice", notes = "Assign an invoice to command",
            response = CommandDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The sale invoice assign to a command successfully"),
            @ApiResponse(code=404, message="Error faced during the sale invoice assigning process")
    })
    ResponseEntity setSaleInvoice(
            @ApiParam(name = "cmdId", type = "Long", required = true,
                    value="Id of the concerned Command", example = "1")
            @NotNull @PathVariable("cmdId") Long cmdId,
            @ApiParam(name = "saleInvoiceId", type = "Long", required = true,
                    value="Id of the concerned Sale Invoice", example = "1")
            @NotNull @PathVariable("saleInvoiceId") Long saleInvoiceId,
            @ApiParam(name = "cmdStatus", type = "CommandStatus", required = true,
                    value="The corresponding status of the command", example = "Cash")
            @NotNull @PathVariable("cmdStatus") CommandStatus cmdStatus);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = ASSIGN_COMMAND_TO_DELIVERY_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "assignCommandToDelivery", notes = "Assign an invoice to command",
            response = CommandDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The sale invoice assign to a command successfully"),
            @ApiResponse(code=404, message="Error faced during the sale invoice assigning process")
    })
    ResponseEntity assignCommandToDelivery(
            @ApiParam(name = "cmdId", type = "Long", required = true,
                    value="Id of the concerned Command", example = "1")
            @NotNull @PathVariable("cmdId") Long cmdId,
            @ApiParam(name = "deliveryId", type = "Long", required = true,
                    value="Id of the concerned Delivery", example = "1")
            @NotNull @PathVariable("deliveryId") Long deliveryId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = RESET_DELIVERY_OF_COMMAND_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "resetDeliveryofCommand", notes = "Assign an invoice to command",
            response = CommandDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The sale invoice assign to a command successfully"),
            @ApiResponse(code=404, message="Error faced during the sale invoice assigning process")
    })
    ResponseEntity resetDeliveryofCommand(
            @ApiParam(name = "cmdId", type = "Long", required = true,
                    value="Id of the concerned Command", example = "1")
            @NotNull @PathVariable("cmdId") Long cmdId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = SWITCH_COMMANDSTATE_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "switchCommandStateTo", notes = "Switch command state",
            response = CommandDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command state of command changed successfully"),
            @ApiResponse(code=404, message="Error faced during the switching state process")
    })
    ResponseEntity switchCommandStateTo(
            @ApiParam(name = "cmdId", type = "Long", required = true,
                    value="Id of the concerned Command", example = "1")
            @NotNull @PathVariable("cmdId") Long cmdId,
            @ApiParam(name = "commandState", type = "CommandState", required = true,
                    value="concerned Command state", example = "InEditing")
            @NotNull @PathVariable("commandState") CommandState commandState);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_COMMAND_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findCommandById", notes = "Find a command by its id",
            response = CommandDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findCommandById(
            @ApiParam(name = "cmdId", type = "Long", required = true,
                    value="Id of the concerned Command", example = "1")
            @NotNull @PathVariable("cmdId") Long cmdId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_COMMAND_BY_CODE_IN_POS_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findCommandByCodeinPos", notes = "Find a command by its code in Pointofsale",
            response = CommandDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findCommandByCodeinPos(
            @ApiParam(name = "cmdCode", type = "String", required = true,
                    value="Code of the concerned Command", example = "code")
            @NotNull @PathVariable("cmdCode") String cmdCode,
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = DELETE_COMMAND_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deleteCommandById",
            notes = "This method is used to delete the command saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object command deleted successfully")
    })
    ResponseEntity deleteCommandById(
            @ApiParam(name = "cmdId", type = "Long", required = true,
                    value="Id of the Command to delete", example = "1")
            @NotNull @PathVariable("cmdId") Long cmdId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_COMMAND_IN_POS_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosBetween", notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_IN_POS_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosBetween", notes = "Find all command in a Pos page by page",
            responseContainer = "Page<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command page is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
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

    @GetMapping(value = FIND_ALL_COMMAND_IN_POS_OF_COMMANDSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosofcmdStateBetween", notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosofcmdStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "commandState", type = "CommandState", required = true,
                    value="concerned Command state", example = "InEditing")
            @NotNull @PathVariable("commandState") CommandState commandState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_IN_POS_OF_COMMANDSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosofcmdStateBetween", notes = "Find all command in a Pos page by page",
            responseContainer = "Page<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command page is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosofcmdStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "commandState", type = "CommandState", required = true,
                    value="concerned Command state", example = "InEditing")
            @NotNull @PathVariable("commandState") CommandState commandState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_COMMAND_IN_POS_OF_COMMANDTYPE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosofcmdTypeBetween", notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosofcmdTypeBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="concerned Command type", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_IN_POS_OF_COMMANDTYPE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosofcmdTypeBetween", notes = "Find all command in a Pos page by page",
            responseContainer = "Page<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command page is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosofcmdTypeBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="concerned Command type", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_COMMAND_IN_POS_OF_COMMANDSTATUS_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosofcmdTypeBetween", notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosofcmdStatusBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "commandStatus", type = "CommandStatus", required = true,
                    value="concerned Command status", example = "Cash")
            @NotNull @PathVariable("commandStatus") CommandStatus commandStatus,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_IN_POS_OF_COMMANDSTATUS_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosofcmdTypeBetween", notes = "Find all command in a Pos page by page",
            responseContainer = "Page<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command page is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosofcmdStatusBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "commandStatus", type = "CommandStatus", required = true,
                    value="concerned Command status", example = "Cash")
            @NotNull @PathVariable("commandStatus") CommandStatus commandStatus,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_COMMAND_IN_POS_OF_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosofcmdDeliveryStateBetween", notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosofcmdDeliveryStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "deliveryState", type = "DeliveryState", required = true,
                    value="concerned Command status", example = "PackedUp")
            @NotNull @PathVariable("deliveryState") DeliveryState deliveryState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_IN_POS_OF_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosofcmdDeliveryStateBetween", notes = "Find all command in a Pos page by page",
            responseContainer = "Page<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command page is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosofcmdDeliveryStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "deliveryState", type = "DeliveryState", required = true,
                    value="concerned Command status", example = "PackedUp")
            @NotNull @PathVariable("deliveryState") DeliveryState deliveryState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_COMMAND_IN_POS_OF_COMMANDSTATE_COMMANDTYPE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosofcmdStateAndcmdTypeBetween", notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosofcmdStateAndcmdTypeBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "commandState", type = "CommandState", required = true,
                    value="concerned Command state", example = "InEditing")
            @NotNull @PathVariable("commandState") CommandState commandState,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="concerned Command type", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_IN_POS_OF_COMMANDSTATE_COMMANDTYPE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosofcmdStateAndcmdTypeBetween", notes = "Find all command in a Pos page by page",
            responseContainer = "Page<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command page is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosofcmdStateAndcmdTypeBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "commandState", type = "CommandState", required = true,
                    value="concerned Command state", example = "InEditing")
            @NotNull @PathVariable("commandState") CommandState commandState,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="concerned Command type", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_COMMAND_IN_POS_OF_COMMANDSTATE_COMMANDSTATUS_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosofcmdStateAndcmdStatusBetween", notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosofcmdStateAndcmdStatusBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "commandState", type = "CommandState", required = true,
                    value="concerned Command state", example = "InEditing")
            @NotNull @PathVariable("commandState") CommandState commandState,
            @ApiParam(name = "commandStatus", type = "CommandStatus", required = true,
                    value="concerned Command status", example = "Cash")
            @NotNull @PathVariable("commandStatus") CommandStatus commandStatus,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_IN_POS_OF_COMMANDSTATE_COMMANDSTATUS_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosofcmdStateAndcmdStatusBetween", notes = "Find all command in a Pos page by page",
            responseContainer = "Page<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command page is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosofcmdStateAndcmdStatusBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "commandState", type = "CommandState", required = true,
                    value="concerned Command state", example = "InEditing")
            @NotNull @PathVariable("commandState") CommandState commandState,
            @ApiParam(name = "commandStatus", type = "CommandStatus", required = true,
                    value="concerned Command status", example = "Cash")
            @NotNull @PathVariable("commandStatus") CommandStatus commandStatus,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_COMMAND_IN_POS_OF_COMMANDSTATE_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosofcmdStateAndcmdDeliveryStateBetween", notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosofcmdStateAndcmdDeliveryStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "commandState", type = "CommandState", required = true,
                    value="concerned Command state", example = "InEditing")
            @NotNull @PathVariable("commandState") CommandState commandState,
            @ApiParam(name = "deliveryState", type = "DeliveryState", required = true,
                    value="concerned delivery State", example = "PackedUp")
            @NotNull @PathVariable("deliveryState") DeliveryState deliveryState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_IN_POS_OF_COMMANDSTATE_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosofcmdStateAndcmdDeliveryStateBetween", notes = "Find all command in a Pos",
            responseContainer = "Page<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command page is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosofcmdStateAndcmdDeliveryStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "commandState", type = "CommandState", required = true,
                    value="concerned Command state", example = "InEditing")
            @NotNull @PathVariable("commandState") CommandState commandState,
            @ApiParam(name = "deliveryState", type = "DeliveryState", required = true,
                    value="concerned delivery State", example = "PackedUp")
            @NotNull @PathVariable("deliveryState") DeliveryState deliveryState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_COMMAND_IN_POS_OF_COMMANDTYPE_COMMANDSTATUS_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosofcmdTypeAndcmdStatusBetween", notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosofcmdTypeAndcmdStatusBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="concerned Command type", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "commandStatus", type = "CommandStatus", required = true,
                    value="concerned Command status", example = "Cash")
            @NotNull @PathVariable("commandStatus") CommandStatus commandStatus,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_IN_POS_OF_COMMANDTYPE_COMMANDSTATUS_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosofcmdTypeAndcmdStatusBetween", notes = "Find all command in a Pos",
            responseContainer = "Page<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosofcmdTypeAndcmdStatusBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="concerned Command type", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "commandStatus", type = "CommandStatus", required = true,
                    value="concerned Command status", example = "Cash")
            @NotNull @PathVariable("commandStatus") CommandStatus commandStatus,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_COMMAND_IN_POS_OF_COMMANDTYPE_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosofcmdTypeAndcmdDeliveryStateBetween", notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosofcmdTypeAndcmdDeliveryStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="concerned Command type", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "deliveryState", type = "DeliveryState", required = true,
                    value="concerned deliveryState", example = "PackedUp")
            @NotNull @PathVariable("deliveryState") DeliveryState deliveryState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_IN_POS_OF_COMMANDTYPE_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosofcmdTypeAndcmdDeliveryStateBetween", notes = "Find all command in a Pos page by page",
            responseContainer = "Page<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command page is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosofcmdTypeAndcmdDeliveryStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="concerned Command type", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "deliveryState", type = "DeliveryState", required = true,
                    value="concerned deliveryState", example = "PackedUp")
            @NotNull @PathVariable("deliveryState") DeliveryState deliveryState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_COMMAND_IN_POS_OF_COMMANDSTATE_COMMANDTYPE_COMMANDSTATUS_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosofcmdStateAndcmdTypeAndcmdStatusBetween",
            notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosofcmdStateAndcmdTypeAndcmdStatusBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "commandState", type = "CommandState", required = true,
                    value="concerned Command state", example = "InEditing")
            @NotNull @PathVariable("commandState") CommandState commandState,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="concerned Command type", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "commandStatus", type = "CommandStatus", required = true,
                    value="concerned commandStatus", example = "Cash")
            @NotNull @PathVariable("commandStatus") CommandStatus commandStatus,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_IN_POS_OF_COMMANDSTATE_COMMANDTYPE_COMMANDSTATUS_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosofcmdStateAndcmdTypeAndcmdStatusBetween",
            notes = "Find all command in a Pos",
            responseContainer = "Page<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command page is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosofcmdStateAndcmdTypeAndcmdStatusBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "commandState", type = "CommandState", required = true,
                    value="concerned Command state", example = "InEditing")
            @NotNull @PathVariable("commandState") CommandState commandState,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="concerned Command type", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "commandStatus", type = "CommandStatus", required = true,
                    value="concerned commandStatus", example = "Cash")
            @NotNull @PathVariable("commandStatus") CommandStatus commandStatus,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_COMMAND_IN_POS_OF_COMMANDSTATE_COMMANDTYPE_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosofcmdStateAndcmdTypeAndcmdDeliveryStateBetween",
            notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosofcmdStateAndcmdTypeAndcmdDeliveryStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "commandState", type = "CommandState", required = true,
                    value="concerned Command state", example = "InEditing")
            @NotNull @PathVariable("commandState") CommandState commandState,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="concerned Command type", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "deliveryState", type = "DeliveryState", required = true,
                    value="concerned deliveryState", example = "PackedUp")
            @NotNull @PathVariable("deliveryState") DeliveryState deliveryState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_IN_POS_OF_COMMANDSTATE_COMMANDTYPE_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosofcmdStateAndcmdTypeAndcmdDeliveryStateBetween",
            notes = "Find all command in a Pos",
            responseContainer = "Page<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command page is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosofcmdStateAndcmdTypeAndcmdDeliveryStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "commandState", type = "CommandState", required = true,
                    value="concerned Command state", example = "InEditing")
            @NotNull @PathVariable("commandState") CommandState commandState,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="concerned Command type", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "deliveryState", type = "DeliveryState", required = true,
                    value="concerned deliveryState", example = "PackedUp")
            @NotNull @PathVariable("deliveryState") DeliveryState deliveryState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_COMMAND_IN_POS_OF_COMMANDSTATE_COMMANDSTATUS_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosofcmdStateAndcmdStatusAndcmdDeliveryStateBetween",
            notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosofcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "commandState", type = "CommandState", required = true,
                    value="concerned Command state", example = "InEditing")
            @NotNull @PathVariable("commandState") CommandState commandState,
            @ApiParam(name = "commandStatus", type = "CommandStatus", required = true,
                    value="concerned Command status", example = "Cash")
            @NotNull @PathVariable("commandStatus") CommandStatus commandStatus,
            @ApiParam(name = "deliveryState", type = "DeliveryState", required = true,
                    value="concerned deliveryState", example = "PackedUp")
            @NotNull @PathVariable("deliveryState") DeliveryState deliveryState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_IN_POS_OF_COMMANDSTATE_COMMANDSTATUS_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosofcmdStateAndcmdStatusAndcmdDeliveryStateBetween",
            notes = "Find all command in a Pos",
            responseContainer = "Page<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command page is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosofcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "commandState", type = "CommandState", required = true,
                    value="concerned Command state", example = "InEditing")
            @NotNull @PathVariable("commandState") CommandState commandState,
            @ApiParam(name = "commandStatus", type = "CommandStatus", required = true,
                    value="concerned Command status", example = "Cash")
            @NotNull @PathVariable("commandStatus") CommandStatus commandStatus,
            @ApiParam(name = "deliveryState", type = "DeliveryState", required = true,
                    value="concerned deliveryState", example = "PackedUp")
            @NotNull @PathVariable("deliveryState") DeliveryState deliveryState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_COMMAND_IN_POS_OF_COMMANDSTATE_COMMANDTYPE_COMMANDSTATUS_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosofcmdStateAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween",
            notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosofcmdStateAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "commandState", type = "CommandState", required = true,
                    value="concerned Command state", example = "InEditing")
            @NotNull @PathVariable("commandState") CommandState commandState,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="concerned Command type", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "commandStatus", type = "CommandStatus", required = true,
                    value="concerned Command status", example = "Cash")
            @NotNull @PathVariable("commandStatus") CommandStatus commandStatus,
            @ApiParam(name = "deliveryState", type = "DeliveryState", required = true,
                    value="concerned deliveryState", example = "PackedUp")
            @NotNull @PathVariable("deliveryState") DeliveryState deliveryState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_IN_POS_OF_COMMANDSTATE_COMMANDTYPE_COMMANDSTATUS_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosofcmdStateAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween",
            notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosofcmdStateAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "commandState", type = "CommandState", required = true,
                    value="concerned Command state", example = "InEditing")
            @NotNull @PathVariable("commandState") CommandState commandState,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="concerned Command type", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "commandStatus", type = "CommandStatus", required = true,
                    value="concerned Command status", example = "Cash")
            @NotNull @PathVariable("commandStatus") CommandStatus commandStatus,
            @ApiParam(name = "deliveryState", type = "DeliveryState", required = true,
                    value="concerned deliveryState", example = "PackedUp")
            @NotNull @PathVariable("deliveryState") DeliveryState deliveryState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_COMMAND_OF_CLIENT_IN_POS_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosofClientBetween",
            notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosofClientBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_OF_CLIENT_IN_POS_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosofClientBetween",
            notes = "Find all command in a Pos",
            responseContainer = "Page<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command page is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosofClientBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_COMMAND_OF_CLIENT_IN_POS_OF_COMMANDSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosofClientAndcmdStateBetween",
            notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosofClientAndcmdStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "commandState", type = "CommandState", required = true,
                    value="commandState concerned", example = "Standard")
            @NotNull @PathVariable("commandState") CommandState commandState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_OF_CLIENT_IN_POS_OF_COMMANDSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosofClientAndcmdStateBetween",
            notes = "Find all command in a Pos",
            responseContainer = "Page<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command page is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosofClientAndcmdStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "commandState", type = "CommandState", required = true,
                    value="commandState concerned", example = "Standard")
            @NotNull @PathVariable("commandState") CommandState commandState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @GetMapping(value = FIND_ALL_COMMAND_OF_CLIENT_IN_POS_OF_COMMANDTYPE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosofClientAndcmdTypeBetween",
            notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosofClientAndcmdTypeBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="commandType concerned", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_OF_CLIENT_IN_POS_OF_COMMANDTYPE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosofClientAndcmdTypeBetween",
            notes = "Find all command in a Pos",
            responseContainer = "Page<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command page is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosofClientAndcmdTypeBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="commandType concerned", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_COMMAND_OF_CLIENT_IN_POS_OF_COMMANDSTATUS_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosofClientAndcmdStatusBetween",
            notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosofClientAndcmdStatusBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "commandStatus", type = "CommandStatus", required = true,
                    value="commandStatus concerned", example = "Cash")
            @NotNull @PathVariable("commandStatus") CommandStatus commandStatus,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_OF_CLIENT_IN_POS_OF_COMMANDSTATUS_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosofClientAndcmdStatusBetween",
            notes = "Find all command in a Pos",
            responseContainer = "Page<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command Page is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosofClientAndcmdStatusBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "commandStatus", type = "CommandStatus", required = true,
                    value="commandStatus concerned", example = "Cash")
            @NotNull @PathVariable("commandStatus") CommandStatus commandStatus,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_COMMAND_OF_CLIENT_IN_POS_OF_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosofClientAndcmdDeliveryStateBetween",
            notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosofClientAndcmdDeliveryStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "deliveryState", type = "DeliveryState", required = true,
                    value="deliveryState concerned", example = "PackedUp")
            @NotNull @PathVariable("deliveryState") DeliveryState deliveryState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_OF_CLIENT_IN_POS_OF_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosofClientAndcmdDeliveryStateBetween",
            notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosofClientAndcmdDeliveryStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "deliveryState", type = "DeliveryState", required = true,
                    value="deliveryState concerned", example = "PackedUp")
            @NotNull @PathVariable("deliveryState") DeliveryState deliveryState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_COMMAND_OF_CLIENT_IN_POS_OF_COMMANDTYPE_COMMANDSTATUS_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosofClientAndcmdTypeAndcmdStatusBetween",
            notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosofClientAndcmdTypeAndcmdStatusBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="commandType concerned", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "commandStatus", type = "CommandStatus", required = true,
                    value="commandStatus concerned", example = "Cash")
            @NotNull @PathVariable("commandStatus") CommandStatus commandStatus,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_OF_CLIENT_IN_POS_OF_COMMANDTYPE_COMMANDSTATUS_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosofClientAndcmdTypeAndcmdStatusBetween",
            notes = "Find all command in a Pos",
            responseContainer = "Page<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command page is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosofClientAndcmdTypeAndcmdStatusBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="commandType concerned", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "commandStatus", type = "CommandStatus", required = true,
                    value="commandStatus concerned", example = "Cash")
            @NotNull @PathVariable("commandStatus") CommandStatus commandStatus,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_COMMAND_OF_CLIENT_IN_POS_OF_COMMANDTYPE_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosofClientAndcmdTypeAndcmdDeliveryStateBetween",
            notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosofClientAndcmdTypeAndcmdDeliveryStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="commandType concerned", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "deliveryState", type = "DeliveryState", required = true,
                    value="deliveryState concerned", example = "PackedUp")
            @NotNull @PathVariable("deliveryState") DeliveryState deliveryState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_OF_CLIENT_IN_POS_OF_COMMANDTYPE_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosofClientAndcmdTypeAndcmdDeliveryStateBetween",
            notes = "Find all command in a Pos",
            responseContainer = "Page<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command page is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosofClientAndcmdTypeAndcmdDeliveryStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="commandType concerned", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "deliveryState", type = "DeliveryState", required = true,
                    value="deliveryState concerned", example = "PackedUp")
            @NotNull @PathVariable("deliveryState") DeliveryState deliveryState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_COMMAND_OF_CLIENT_IN_POS_OF_COMMANDTYPE_COMMANDSTATE_COMMANDSTATUS_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosofClientAndcmdTypeAndcmdStateAndcmdStatusBetween",
            notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosofClientAndcmdTypeAndcmdStateAndcmdStatusBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="commandType concerned", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "commandState", type = "CommandState", required = true,
                    value="commandState concerned", example = "InEditing")
            @NotNull @PathVariable("commandState") CommandState commandState,
            @ApiParam(name = "commandStatus", type = "CommandStatus", required = true,
                    value="commandStatus concerned", example = "Cash")
            @NotNull @PathVariable("commandStatus") CommandStatus commandStatus,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_OF_CLIENT_IN_POS_OF_COMMANDTYPE_COMMANDSTATE_COMMANDSTATUS_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosofClientAndcmdTypeAndcmdStateAndcmdStatusBetween",
            notes = "Find all command in a Pos",
            responseContainer = "Page<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command page is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosofClientAndcmdTypeAndcmdStateAndcmdStatusBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="commandType concerned", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "commandState", type = "CommandState", required = true,
                    value="commandState concerned", example = "InEditing")
            @NotNull @PathVariable("commandState") CommandState commandState,
            @ApiParam(name = "commandStatus", type = "CommandStatus", required = true,
                    value="commandStatus concerned", example = "Cash")
            @NotNull @PathVariable("commandStatus") CommandStatus commandStatus,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_COMMAND_OF_CLIENT_IN_POS_OF_COMMANDTYPE_COMMANDSTATE_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosofClientAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween",
            notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosofClientAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="commandType concerned", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "commandState", type = "CommandState", required = true,
                    value="commandState concerned", example = "InEditing")
            @NotNull @PathVariable("commandState") CommandState commandState,
            @ApiParam(name = "deliveryState", type = "DeliveryState", required = true,
                    value="deliveryState concerned", example = "PackedUp")
            @NotNull @PathVariable("deliveryState") DeliveryState deliveryState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_OF_CLIENT_IN_POS_OF_COMMANDTYPE_COMMANDSTATE_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosofClientAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween",
            notes = "Find all command in a Pos",
            responseContainer = "Page<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command page is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosofClientAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="commandType concerned", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "commandState", type = "CommandState", required = true,
                    value="commandState concerned", example = "InEditing")
            @NotNull @PathVariable("commandState") CommandState commandState,
            @ApiParam(name = "deliveryState", type = "DeliveryState", required = true,
                    value="deliveryState concerned", example = "PackedUp")
            @NotNull @PathVariable("deliveryState") DeliveryState deliveryState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_COMMAND_OF_CLIENT_IN_POS_OF_COMMANDTYPE_COMMANDSTATE_COMMANDSTATUS_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosofClientAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween",
            notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosofClientAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="commandType concerned", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "commandState", type = "CommandState", required = true,
                    value="commandState concerned", example = "InEditing")
            @NotNull @PathVariable("commandState") CommandState commandState,
            @ApiParam(name = "commandStatus", type = "CommandStatus", required = true,
                    value="commandStatus concerned", example = "Cash")
            @NotNull @PathVariable("commandStatus") CommandStatus commandStatus,
            @ApiParam(name = "deliveryState", type = "DeliveryState", required = true,
                    value="deliveryState concerned", example = "PackedUp")
            @NotNull @PathVariable("deliveryState") DeliveryState deliveryState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_OF_CLIENT_IN_POS_OF_COMMANDTYPE_COMMANDSTATE_COMMANDSTATUS_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosofClientAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween",
            notes = "Find all command in a Pos",
            responseContainer = "Page<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command page is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosofClientAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="commandType concerned", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "commandState", type = "CommandState", required = true,
                    value="commandState concerned", example = "InEditing")
            @NotNull @PathVariable("commandState") CommandState commandState,
            @ApiParam(name = "commandStatus", type = "CommandStatus", required = true,
                    value="commandStatus concerned", example = "Cash")
            @NotNull @PathVariable("commandStatus") CommandStatus commandStatus,
            @ApiParam(name = "deliveryState", type = "DeliveryState", required = true,
                    value="deliveryState concerned", example = "PackedUp")
            @NotNull @PathVariable("deliveryState") DeliveryState deliveryState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_COMMAND_OF_USERBM_IN_POS_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosofUserbmBetween",
            notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosofUserbmBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_OF_USERBM_IN_POS_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosofUserbmBetween",
            notes = "Find all command in a Pos",
            responseContainer = "Page<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command page is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosofUserbmBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_COMMAND_OF_USERBM_IN_POS_OF_COMMANDTYPE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosofUserbmAndcmdTypeBetween",
            notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosofUserbmAndcmdTypeBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="commandType concerned", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_OF_USERBM_IN_POS_OF_COMMANDTYPE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosofUserbmAndcmdTypeBetween",
            notes = "Find all command in a Pos",
            responseContainer = "Page<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command page is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosofUserbmAndcmdTypeBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="commandType concerned", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_COMMAND_OF_USERBM_IN_POS_OF_COMMANDSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosofUserbmAndcmdStateBetween",
            notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosofUserbmAndcmdStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "commandState", type = "CommandState", required = true,
                    value="commandState concerned", example = "InEditing")
            @NotNull @PathVariable("commandState") CommandState commandState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_OF_USERBM_IN_POS_OF_COMMANDSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosofUserbmAndcmdStateBetween",
            notes = "Find all command in a Pos",
            responseContainer = "Page<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command Page is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosofUserbmAndcmdStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "commandState", type = "CommandState", required = true,
                    value="commandState concerned", example = "InEditing")
            @NotNull @PathVariable("commandState") CommandState commandState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_COMMAND_OF_USERBM_IN_POS_OF_COMMANDSTATUS_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosofUserbmAndcmdStatusBetween",
            notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosofUserbmAndcmdStatusBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "commandStatus", type = "CommandStatus", required = true,
                    value="commandStatus concerned", example = "Cash")
            @NotNull @PathVariable("commandStatus") CommandStatus commandStatus,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_OF_USERBM_IN_POS_OF_COMMANDSTATUS_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosofUserbmAndcmdStatusBetween",
            notes = "Find all command in a Pos",
            responseContainer = "Page<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command page is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosofUserbmAndcmdStatusBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "commandStatus", type = "CommandStatus", required = true,
                    value="commandStatus concerned", example = "Cash")
            @NotNull @PathVariable("commandStatus") CommandStatus commandStatus,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_COMMAND_OF_USERBM_IN_POS_OF_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosofUserbmAndcmdDeliveryStateBetween",
            notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosofUserbmAndcmdDeliveryStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "deliveryState", type = "DeliveryState", required = true,
                    value="deliveryState concerned", example = "PackedUp")
            @NotNull @PathVariable("deliveryState") DeliveryState deliveryState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_OF_USERBM_IN_POS_OF_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosofUserbmAndcmdDeliveryStateBetween",
            notes = "Find all command in a Pos",
            responseContainer = "Page<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command page is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosofUserbmAndcmdDeliveryStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "deliveryState", type = "DeliveryState", required = true,
                    value="deliveryState concerned", example = "PackedUp")
            @NotNull @PathVariable("deliveryState") DeliveryState deliveryState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_COMMAND_OF_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosofUserbmAndcmdTypeAndcmdStateBetween",
            notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosofUserbmAndcmdTypeAndcmdStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="commandType concerned", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "commandState", type = "CommandState", required = true,
                    value="commandState concerned", example = "InEditing")
            @NotNull @PathVariable("commandState") CommandState commandState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_OF_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosofUserbmAndcmdTypeAndcmdStateBetween",
            notes = "Find all command in a Pos",
            responseContainer = "Page<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command page is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosofUserbmAndcmdTypeAndcmdStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="commandType concerned", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "commandState", type = "CommandState", required = true,
                    value="commandState concerned", example = "InEditing")
            @NotNull @PathVariable("commandState") CommandState commandState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_COMMAND_OF_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATUS_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosofUserbmAndcmdTypeAndcmdStatusBetween",
            notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosofUserbmAndcmdTypeAndcmdStatusBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="commandType concerned", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "commandStatus", type = "CommandStatus", required = true,
                    value="commandStatus concerned", example = "Cash")
            @NotNull @PathVariable("commandStatus") CommandStatus commandStatus,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_OF_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATUS_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosofUserbmAndcmdTypeAndcmdStatusBetween",
            notes = "Find all command in a Pos",
            responseContainer = "Page<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command page is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosofUserbmAndcmdTypeAndcmdStatusBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="commandType concerned", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "commandStatus", type = "CommandStatus", required = true,
                    value="commandStatus concerned", example = "Cash")
            @NotNull @PathVariable("commandStatus") CommandStatus commandStatus,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_COMMAND_OF_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosofUserbmAndcmdTypeAndcmdDeliveryStateBetween",
            notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosofUserbmAndcmdTypeAndcmdDeliveryStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="commandType concerned", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "deliveryState", type = "DeliveryState", required = true,
                    value="deliveryState concerned", example = "PackedUp")
            @NotNull @PathVariable("deliveryState") DeliveryState deliveryState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_OF_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosofUserbmAndcmdTypeAndcmdDeliveryStateBetween",
            notes = "Find all command in a Pos",
            responseContainer = "Page<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command page is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosofUserbmAndcmdTypeAndcmdDeliveryStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="commandType concerned", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "deliveryState", type = "DeliveryState", required = true,
                    value="deliveryState concerned", example = "PackedUp")
            @NotNull @PathVariable("deliveryState") DeliveryState deliveryState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_COMMAND_OF_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATE_COMMANDSTATUS_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdStatusBetween",
            notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdStatusBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="commandType concerned", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "commandState", type = "CommandState", required = true,
                    value="commandState concerned", example = "InEditing")
            @NotNull @PathVariable("commandState") CommandState commandState,
            @ApiParam(name = "commandStatus", type = "CommandStatus", required = true,
                    value="commandStatus concerned", example = "Cash")
            @NotNull @PathVariable("commandStatus") CommandStatus commandStatus,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_OF_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATE_COMMANDSTATUS_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdStatusBetween",
            notes = "Find all command in a Pos",
            responseContainer = "Page<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command page is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdStatusBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="commandType concerned", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "commandState", type = "CommandState", required = true,
                    value="commandState concerned", example = "InEditing")
            @NotNull @PathVariable("commandState") CommandState commandState,
            @ApiParam(name = "commandStatus", type = "CommandStatus", required = true,
                    value="commandStatus concerned", example = "Cash")
            @NotNull @PathVariable("commandStatus") CommandStatus commandStatus,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_COMMAND_OF_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATE_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween",
            notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="commandType concerned", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "commandState", type = "CommandState", required = true,
                    value="commandState concerned", example = "InEditing")
            @NotNull @PathVariable("commandState") CommandState commandState,
            @ApiParam(name = "deliveryState", type = "DeliveryState", required = true,
                    value="deliveryState concerned", example = "PackedUp")
            @NotNull @PathVariable("deliveryState") DeliveryState deliveryState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_OF_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATE_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween",
            notes = "Find all command in a Pos",
            responseContainer = "Page<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command page is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="commandType concerned", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "commandState", type = "CommandState", required = true,
                    value="commandState concerned", example = "InEditing")
            @NotNull @PathVariable("commandState") CommandState commandState,
            @ApiParam(name = "deliveryState", type = "DeliveryState", required = true,
                    value="deliveryState concerned", example = "PackedUp")
            @NotNull @PathVariable("deliveryState") DeliveryState deliveryState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_COMMAND_OF_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATUS_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosofUserbmAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween",
            notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosofUserbmAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="commandType concerned", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "commandStatus", type = "CommandStatus", required = true,
                    value="commandStatus concerned", example = "Cash")
            @NotNull @PathVariable("commandStatus") CommandStatus commandStatus,
            @ApiParam(name = "deliveryState", type = "DeliveryState", required = true,
                    value="deliveryState concerned", example = "PackedUp")
            @NotNull @PathVariable("deliveryState") DeliveryState deliveryState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_OF_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATUS_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosofUserbmAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween",
            notes = "Find all command in a Pos",
            responseContainer = "Page<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command page is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosofUserbmAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="commandType concerned", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "commandStatus", type = "CommandStatus", required = true,
                    value="commandStatus concerned", example = "Cash")
            @NotNull @PathVariable("commandStatus") CommandStatus commandStatus,
            @ApiParam(name = "deliveryState", type = "DeliveryState", required = true,
                    value="deliveryState concerned", example = "PackedUp")
            @NotNull @PathVariable("deliveryState") DeliveryState deliveryState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_COMMAND_OF_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATE_COMMANDSTATUS_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween",
            notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="commandType concerned", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "commandState", type = "CommandState", required = true,
                    value="commandState concerned", example = "InEditing")
            @NotNull @PathVariable("commandState") CommandState commandState,
            @ApiParam(name = "commandStatus", type = "CommandStatus", required = true,
                    value="commandStatus concerned", example = "Cash")
            @NotNull @PathVariable("commandStatus") CommandStatus commandStatus,
            @ApiParam(name = "deliveryState", type = "DeliveryState", required = true,
                    value="deliveryState concerned", example = "PackedUp")
            @NotNull @PathVariable("deliveryState") DeliveryState deliveryState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_OF_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATE_COMMANDSTATUS_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween",
            notes = "Find all command in a Pos",
            responseContainer = "Page<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command page is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="commandType concerned", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "commandState", type = "CommandState", required = true,
                    value="commandState concerned", example = "InEditing")
            @NotNull @PathVariable("commandState") CommandState commandState,
            @ApiParam(name = "commandStatus", type = "CommandStatus", required = true,
                    value="commandStatus concerned", example = "Cash")
            @NotNull @PathVariable("commandStatus") CommandStatus commandStatus,
            @ApiParam(name = "deliveryState", type = "DeliveryState", required = true,
                    value="deliveryState concerned", example = "PackedUp")
            @NotNull @PathVariable("deliveryState") DeliveryState deliveryState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosofClientforUserbmBetween",
            notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosofClientforUserbmBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosofClientforUserbmBetween",
            notes = "Find all command in a Pos",
            responseContainer = "Page<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command page is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosofClientforUserbmBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_OF_COMMANDTYPE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosofClientAndUserbmAndcmdTypeBetween",
            notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosofClientAndUserbmAndcmdTypeBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="commandType concerned", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_OF_COMMANDTYPE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosofClientAndUserbmAndcmdTypeBetween",
            notes = "Find all command in a Pos",
            responseContainer = "Page<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command page is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosofClientAndUserbmAndcmdTypeBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="commandType concerned", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_OF_COMMANDSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosofClientAndUserbmAndcmdStateBetween",
            notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosofClientAndUserbmAndcmdStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "commandState", type = "CommandState", required = true,
                    value="commandState concerned", example = "InEditing")
            @NotNull @PathVariable("commandState") CommandState commandState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_OF_COMMANDSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosofClientAndUserbmAndcmdStateBetween",
            notes = "Find all command in a Pos",
            responseContainer = "Page<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command page is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosofClientAndUserbmAndcmdStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "commandState", type = "CommandState", required = true,
                    value="commandState concerned", example = "InEditing")
            @NotNull @PathVariable("commandState") CommandState commandState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_OF_COMMANDSTATUS_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosofClientAndUserbmAndcmdStatusBetween",
            notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosofClientAndUserbmAndcmdStatusBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "commandStatus", type = "CommandStatus", required = true,
                    value="commandStatus concerned", example = "Cash")
            @NotNull @PathVariable("commandStatus") CommandStatus commandStatus,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_OF_COMMANDSTATUS_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosofClientAndUserbmAndcmdStatusBetween",
            notes = "Find all command in a Pos",
            responseContainer = "Page<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command page is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosofClientAndUserbmAndcmdStatusBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "commandStatus", type = "CommandStatus", required = true,
                    value="commandStatus concerned", example = "Cash")
            @NotNull @PathVariable("commandStatus") CommandStatus commandStatus,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_OF_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosofClientAndUserbmAndcmdDeliveryStateBetween",
            notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosofClientAndUserbmAndcmdDeliveryStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "deliveryState", type = "DeliveryState", required = true,
                    value="deliveryState concerned", example = "PackedUp")
            @NotNull @PathVariable("deliveryState") DeliveryState deliveryState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_OF_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosofClientAndUserbmAndcmdDeliveryStateBetween",
            notes = "Find all command in a Pos",
            responseContainer = "Page<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command page is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosofClientAndUserbmAndcmdDeliveryStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "deliveryState", type = "DeliveryState", required = true,
                    value="deliveryState concerned", example = "PackedUp")
            @NotNull @PathVariable("deliveryState") DeliveryState deliveryState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateBetween",
            notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="commandType concerned", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "commandState", type = "CommandState", required = true,
                    value="commandState concerned", example = "InEditing")
            @NotNull @PathVariable("commandState") CommandState commandState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateBetween",
            notes = "Find all command in a Pos",
            responseContainer = "Page<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command page is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="commandType concerned", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "commandState", type = "CommandState", required = true,
                    value="commandState concerned", example = "InEditing")
            @NotNull @PathVariable("commandState") CommandState commandState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATUS_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStatusBetween",
            notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStatusBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="commandType concerned", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "commandStatus", type = "CommandStatus", required = true,
                    value="commandStatus concerned", example = "Cash")
            @NotNull @PathVariable("commandStatus") CommandStatus commandStatus,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATUS_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStatusBetween",
            notes = "Find all command in a Pos",
            responseContainer = "Page<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command page is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStatusBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="commandType concerned", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "commandStatus", type = "CommandStatus", required = true,
                    value="commandStatus concerned", example = "Cash")
            @NotNull @PathVariable("commandStatus") CommandStatus commandStatus,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdDeliveryStateBetween",
            notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdDeliveryStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="commandType concerned", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "deliveryState", type = "DeliveryState", required = true,
                    value="deliveryState concerned", example = "PackedUp")
            @NotNull @PathVariable("deliveryState") DeliveryState deliveryState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdDeliveryStateBetween",
            notes = "Find all command in a Pos",
            responseContainer = "Page<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command page is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdDeliveryStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="commandType concerned", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "deliveryState", type = "DeliveryState", required = true,
                    value="deliveryState concerned", example = "PackedUp")
            @NotNull @PathVariable("deliveryState") DeliveryState deliveryState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATE_COMMANDSTATUS_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdStatusBetween",
            notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdStatusBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="commandType concerned", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "commandState", type = "CommandState", required = true,
                    value="commandState concerned", example = "InEditing")
            @NotNull @PathVariable("commandState") CommandState commandState,
            @ApiParam(name = "commandStatus", type = "CommandStatus", required = true,
                    value="commandStatus concerned", example = "Cash")
            @NotNull @PathVariable("commandStatus") CommandStatus commandStatus,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATE_COMMANDSTATUS_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdStatusBetween",
            notes = "Find all command in a Pos",
            responseContainer = "Page<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command page is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdStatusBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="commandType concerned", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "commandState", type = "CommandState", required = true,
                    value="commandState concerned", example = "InEditing")
            @NotNull @PathVariable("commandState") CommandState commandState,
            @ApiParam(name = "commandStatus", type = "CommandStatus", required = true,
                    value="commandStatus concerned", example = "Cash")
            @NotNull @PathVariable("commandStatus") CommandStatus commandStatus,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATE_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween",
            notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="commandType concerned", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "commandState", type = "CommandState", required = true,
                    value="commandState concerned", example = "InEditing")
            @NotNull @PathVariable("commandState") CommandState commandState,
            @ApiParam(name = "deliveryState", type = "DeliveryState", required = true,
                    value="deliveryState concerned", example = "PackedUp")
            @NotNull @PathVariable("deliveryState") DeliveryState deliveryState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATE_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween",
            notes = "Find all command in a Pos",
            responseContainer = "Page<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command page is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="commandType concerned", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "commandState", type = "CommandState", required = true,
                    value="commandState concerned", example = "InEditing")
            @NotNull @PathVariable("commandState") CommandState commandState,
            @ApiParam(name = "deliveryState", type = "DeliveryState", required = true,
                    value="deliveryState concerned", example = "PackedUp")
            @NotNull @PathVariable("deliveryState") DeliveryState deliveryState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATUS_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween",
            notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="commandType concerned", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "commandStatus", type = "commandStatus", required = true,
                    value="commandStatus concerned", example = "Cash")
            @NotNull @PathVariable("commandStatus") CommandStatus commandStatus,
            @ApiParam(name = "deliveryState", type = "DeliveryState", required = true,
                    value="deliveryState concerned", example = "PackedUp")
            @NotNull @PathVariable("deliveryState") DeliveryState deliveryState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATUS_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween",
            notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command page is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="commandType concerned", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "commandStatus", type = "commandStatus", required = true,
                    value="commandStatus concerned", example = "Cash")
            @NotNull @PathVariable("commandStatus") CommandStatus commandStatus,
            @ApiParam(name = "deliveryState", type = "DeliveryState", required = true,
                    value="deliveryState concerned", example = "PackedUp")
            @NotNull @PathVariable("deliveryState") DeliveryState deliveryState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATE_COMMANDSTATUS_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween",
            notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="commandType concerned", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "commandState", type = "CommandState", required = true,
                    value="commandState concerned", example = "InEditing")
            @NotNull @PathVariable("commandState") CommandState commandState,
            @ApiParam(name = "commandStatus", type = "commandStatus", required = true,
                    value="commandStatus concerned", example = "Cash")
            @NotNull @PathVariable("commandStatus") CommandStatus commandStatus,
            @ApiParam(name = "deliveryState", type = "DeliveryState", required = true,
                    value="deliveryState concerned", example = "PackedUp")
            @NotNull @PathVariable("deliveryState") DeliveryState deliveryState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_OF_CLIENT_AND_USERBM_IN_POS_OF_COMMANDTYPE_COMMANDSTATE_COMMANDSTATUS_COMMANDDELIVERYSTATE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween",
            notes = "Find all command in a Pos",
            responseContainer = "Page<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command page is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned Userbm", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "commandType", type = "CommandType", required = true,
                    value="commandType concerned", example = "Standard")
            @NotNull @PathVariable("commandType") CommandType commandType,
            @ApiParam(name = "commandState", type = "CommandState", required = true,
                    value="commandState concerned", example = "InEditing")
            @NotNull @PathVariable("commandState") CommandState commandState,
            @ApiParam(name = "commandStatus", type = "commandStatus", required = true,
                    value="commandStatus concerned", example = "Cash")
            @NotNull @PathVariable("commandStatus") CommandStatus commandStatus,
            @ApiParam(name = "deliveryState", type = "DeliveryState", required = true,
                    value="deliveryState concerned", example = "PackedUp")
            @NotNull @PathVariable("deliveryState") DeliveryState deliveryState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_COMMAND_OF_LOADING_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCommandofLoadinginPos",
            notes = "Find all command in a Pos",
            responseContainer = "List<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command list is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCommandofLoadinginPos(
            @ApiParam(name = "loadingId", type = "Long", required = true,
                    value="Id of the concerned Loading", example = "1")
            @NotNull @PathVariable("loadingId") Long loadingId,
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_COMMAND_OF_LOADING_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCommandofLoadinginPos",
            notes = "Find all command in a Pos",
            responseContainer = "Page<CommandDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The command page is successfully found"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCommandofLoadinginPos(
            @ApiParam(name = "loadingId", type = "Long", required = true,
                    value="Id of the concerned Loading", example = "1")
            @NotNull @PathVariable("loadingId") Long loadingId,
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pos", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////




}
