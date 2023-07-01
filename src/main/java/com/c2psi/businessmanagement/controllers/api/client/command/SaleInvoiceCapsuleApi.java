package com.c2psi.businessmanagement.controllers.api.client.command;

import com.c2psi.businessmanagement.dtos.client.command.SaleInvoiceCapsuleDto;
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
@Api(APP_ROOT+"/saleInvoiceCapsule")
public interface SaleInvoiceCapsuleApi {
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = APP_ROOT+"/saleInvoiceCapsule/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveSaleInvoiceCapsule",
            notes = "This method is used to save a sale invoice Capsule in the DB",
            response = SaleInvoiceCapsuleDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object SaleInvoiceCapsuleDto added successfully"),
            @ApiResponse(code=400, message="Object SaleInvoiceCapsuleDto is not valid during the saving process")
    })
    ResponseEntity saveSaleInvoiceCapsule(
            @ApiParam(name = "saleiCapsuleDto", type = "SaleInvoiceCapsuleDto", required = true,
                    value="The JSON object that represent the SaleInvoiceCapsuleDto to save")
            @Valid @RequestBody SaleInvoiceCapsuleDto saleiCapsuleDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = APP_ROOT+"/saleInvoiceCapsule/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "updateSaleInvoiceCapsule",
            notes = "This method is used to update a sale invoice Capsule in the DB",
            response = SaleInvoiceCapsuleDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object SaleInvoiceCapsuleDto updated successfully"),
            @ApiResponse(code=400, message="Object SaleInvoiceCapsuleDto is not valid during the saving process")
    })
    ResponseEntity updateSaleInvoiceCapsule(
            @ApiParam(name = "saleiCapsuleDto", type = "SaleInvoiceCapsuleDto", required = true,
                    value="The JSON object that represent the SaleInvoiceCapsuleDto to save")
            @Valid @RequestBody SaleInvoiceCapsuleDto saleiCapsuleDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = APP_ROOT+"/saleInvoiceCapsule/delete/id/{saleiCapsuleId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deleteSaleInvoiceCapsuleById",
            notes = "This method is used to delete the saleInvoiceCapsule saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object saleInvoiceCapsule deleted successfully")
    })
    ResponseEntity deleteSaleInvoiceCapsuleById(
            @ApiParam(name = "saleiCapsuleId", type = "Long", required = true,
                    value="Id of the SaleInvoiceCapsule to delete", example = "1")
            @NotNull @PathVariable("saleiCapsuleId") Long saleiCapsuleId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/saleInvoiceCapsule/id/{saleiCapsuleId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findSaleInvoiceCapsuleById", notes = "Search a saleinvoiceCapsule by id",
            response = SaleInvoiceCapsuleDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoiceCapsule found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findSaleInvoiceCapsuleById(
            @ApiParam(name = "saleiCapsuleId", type = "Long", required = true,
                    value="Id of the concerned saleinvoiceCapsule", example = "1")
            @NotNull @PathVariable("saleiCapsuleId") Long saleiCapsuleId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/saleInvoiceCapsule/pos/code/{saleiCapsuleCode}/{posId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findSaleInvoiceCapsuleByCode", notes = "Search a saleinvoiceCapsule by code in Pointofsale",
            response = SaleInvoiceCapsuleDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoiceCapsule found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findSaleInvoiceCapsuleByCode(
            @ApiParam(name = "saleiCapsuleCode", type = "String", required = true,
                    value="Code of the concerned saleinvoiceCapsule", example = "code")
            @NotNull @PathVariable("saleiCapsuleCode") String saleiCapsuleCode,
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/saleInvoiceCapsule/all/between/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllSaleiCapsuleBetween", notes = "Search a saleinvoiceCapsule list between 02 dates",
            responseContainer = "List<SaleInvoiceCapsuleDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoiceCapsule list found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllSaleiCapsuleBetween(
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/saleInvoiceCapsule/page/between/{from}/{to}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageSaleiCapsuleBetween", notes = "Search a saleinvoiceCapsule list between 02 dates page by page",
            responseContainer = "Page<SaleInvoiceCapsuleDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoiceCapsule page found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageSaleiCapsuleBetween(
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/saleInvoiceCapsule/client/all/between/{clientId}/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllSaleiCapsuleofClientBetween", notes = "Search a saleinvoiceCapsule list for a client " +
            "between 02 dates",
            responseContainer = "List<SaleInvoiceCapsuleDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoiceCapsule list for a client found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllSaleiCapsuleofClientBetween(
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

    @GetMapping(value = APP_ROOT+"/saleInvoiceCapsule/client/page/between/{clientId}/{from}/{to}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageSaleiCapsuleofClientBetween", notes = "Search a saleinvoiceCapsule page for a client " +
            "between 02 dates",
            responseContainer = "Page<SaleInvoiceCapsuleDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoiceCapsule page for a client found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageSaleiCapsuleofClientBetween(
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

    @GetMapping(value = APP_ROOT+"/saleInvoiceCapsule/userbm/all/between/{userbmId}/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllSaleiCapsuleofUserbmBetween", notes = "Search a saleinvoiceCapsule list for a UserBM " +
            "between 02 dates",
            responseContainer = "List<SaleInvoiceCapsuleDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoiceCapsule list for a UserBM found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllSaleiCapsuleofUserbmBetween(
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned UserBM", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/saleInvoiceCapsule/userbm/page/between/{userbmId}/{from}/{to}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageSaleiCapsuleofUserbmBetween", notes = "Search a saleinvoiceCapsule page for a userbm " +
            "between 02 dates",
            responseContainer = "Page<SaleInvoiceCapsuleDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoiceCapsule page for a userbm found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageSaleiCapsuleofUserbmBetween(
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned UserBM", example = "1")
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

    @GetMapping(value = APP_ROOT+"/saleInvoiceCapsule/pos/all/between/{posId}/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllSaleiCapsuleofUserbmBetween", notes = "Search a saleinvoiceCapsule list in Pos" +
            "between 02 dates",
            responseContainer = "List<SaleInvoiceCapsuleDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoiceCapsule list in Pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllSaleiCapsuleinPosBetween(
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

    @GetMapping(value = APP_ROOT+"/saleInvoiceCapsule/pos/page/between/{posId}/{from}/{to}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageSaleiCapsuleinPosBetween", notes = "Search a saleinvoiceCapsule page for a userbm " +
            "between 02 dates",
            responseContainer = "Page<SaleInvoiceCapsuleDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoiceCapsule page in pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageSaleiCapsuleinPosBetween(
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

    @GetMapping(value = APP_ROOT+"/saleInvoiceCapsule/pos/userbm/all/between/{userbmId}/{posId}/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllSaleiCapsuleofUserbminPosBetween", notes = "Search a saleinvoiceCapsule list in Pos" +
            "between 02 dates for a UserBM",
            responseContainer = "List<SaleInvoiceCapsuleDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoiceCapsule list in Pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllSaleiCapsuleofUserbminPosBetween(
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned UserBM", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
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

    @GetMapping(value = APP_ROOT+"/saleInvoiceCapsule/pos/userbm/page/between/{userbmId}/{posId}/{from}/{to}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageSaleiCapsuleofUserbminPosBetween", notes = "Search a saleinvoiceCapsule page in Pos" +
            "between 02 dates for a UserBM",
            responseContainer = "Page<SaleInvoiceCapsuleDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoiceCapsule page in Pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageSaleiCapsuleofUserbminPosBetween(
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned UserBM", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
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

    @GetMapping(value = APP_ROOT+"/saleInvoiceCapsule/pos/client/all/between/{clientId}/{posId}/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllSaleiCapsuleofClientinPosBetween", notes = "Search a saleinvoiceCapsule list in Pos" +
            "between 02 dates for a client",
            responseContainer = "List<SaleInvoiceCapsuleDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoiceCapsule list in Pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllSaleiCapsuleofClientinPosBetween(
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
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

    @GetMapping(value = APP_ROOT+"/saleInvoiceCapsule/pos/client/page/between/{clientId}/{posId}/{from}/{to}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageSaleiCapsuleofClientinPosBetween", notes = "Search a saleinvoiceCapsule page in Pos" +
            "between 02 dates for a client",
            responseContainer = "Page<SaleInvoiceCapsuleDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoiceCapsule page in Pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageSaleiCapsuleofClientinPosBetween(
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
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

    @GetMapping(value = APP_ROOT+"/saleInvoiceCapsule/client/userbm/all/between/{clientId}/{userbmId}/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllSaleiCapsuleofClientforUserbmBetween", notes = "Search a saleinvoiceCapsule list for a client" +
            "between 02 dates saved by a user",
            responseContainer = "List<SaleInvoiceCapsuleDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoiceCapsule list in Pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllSaleiCapsuleofClientforUserbmBetween(
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned UserBM", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/saleInvoiceCapsule/client/userbm/all/between/{clientId}/{userbmId}/{from}/{to}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageSaleiCapsuleofClientforUserbmBetween", notes = "Search a saleinvoiceCapsule page for a client" +
            "between 02 dates saved by a user",
            responseContainer = "Page<SaleInvoiceCapsuleDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoiceCapsule page in Pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageSaleiCapsuleofClientforUserbmBetween(
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned UserBM", example = "1")
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

    @GetMapping(value = APP_ROOT+"/saleInvoiceCapsule/pos/client/userbm/all/between/{clientId}/{userbmId}/{posId}/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllSaleiCapsuleofClientforUserbminPosBetween", notes = "Search a saleinvoiceCapsule list for a client" +
            "between 02 dates saved by a user in a pointofsale",
            responseContainer = "List<SaleInvoiceCapsuleDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoiceCapsule list in Pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllSaleiCapsuleofClientforUserbminPosBetween(
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned UserBM", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
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

    @GetMapping(value = APP_ROOT+"/saleInvoiceCapsule/pos/client/userbm/all/between/{clientId}/{userbmId}/{posId}/{from}/{to}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageSaleiCapsuleofClientforUserbminPosBetween", notes = "Search a saleinvoiceCapsule page for a client" +
            "between 02 dates saved by a user in a pointofsale",
            responseContainer = "Page<SaleInvoiceCapsuleDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoiceCapsule Page in Pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageSaleiCapsuleofClientforUserbminPosBetween(
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value="Id of the concerned Client", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned UserBM", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
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
}
