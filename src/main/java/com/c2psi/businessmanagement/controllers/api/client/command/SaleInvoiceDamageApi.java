package com.c2psi.businessmanagement.controllers.api.client.command;

import com.c2psi.businessmanagement.dtos.client.command.SaleInvoiceDamageDto;
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
@Api(APP_ROOT+"/saleInvoiceDamage")
public interface SaleInvoiceDamageApi {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = APP_ROOT+"/saleInvoiceDamage/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveSaleInvoiceDamage",
            notes = "This method is used to save a sale invoice Damage in the DB",
            response = SaleInvoiceDamageDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object SaleInvoiceDamageDto added successfully"),
            @ApiResponse(code=400, message="Object SaleInvoiceDamageDto is not valid during the saving process")
    })
    ResponseEntity saveSaleInvoiceDamage(
            @ApiParam(name = "saleiDamageDto", type = "SaleInvoiceDamageDto", required = true,
                    value="The JSON object that represent the SaleInvoiceDamageDto to save")
            @Valid @RequestBody SaleInvoiceDamageDto saleiDamageDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = APP_ROOT+"/saleInvoiceDamage/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "updateSaleInvoiceDamage",
            notes = "This method is used to update a sale invoice Damage in the DB",
            response = SaleInvoiceDamageDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object SaleInvoiceDamageDto updated successfully"),
            @ApiResponse(code=400, message="Object SaleInvoiceDamageDto is not valid during the saving process")
    })
    ResponseEntity updateSaleInvoiceDamage(
            @ApiParam(name = "saleiDamageDto", type = "SaleInvoiceDamageDto", required = true,
                    value="The JSON object that represent the SaleInvoiceDamageDto to save")
            @Valid @RequestBody SaleInvoiceDamageDto saleiDamageDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = APP_ROOT+"/saleInvoiceDamage/delete/id/{saleiDamageId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deleteSaleInvoiceDamageById",
            notes = "This method is used to delete the saleInvoiceDamage saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object saleInvoiceDamage deleted successfully")
    })
    ResponseEntity deleteSaleInvoiceDamageById(
            @ApiParam(name = "saleiDamageId", type = "Long", required = true,
                    value="Id of the SaleInvoiceDamage to delete", example = "1")
            @NotNull @PathVariable("saleiDamageId") Long saleiDamageId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/saleInvoiceDamage/id/{saleiDamageId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findSaleInvoiceDamageById", notes = "Search a saleinvoiceDamage by id",
            response = SaleInvoiceDamageDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoiceDamage found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findSaleInvoiceDamageById(
            @ApiParam(name = "saleiDamageId", type = "Long", required = true,
                    value="Id of the concerned saleinvoiceDamage", example = "1")
            @NotNull @PathVariable("saleiDamageId") Long saleiDamageId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/saleInvoiceDamage/pos/code/{saleiDamageCode}/{posId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findSaleInvoiceDamageByCode", notes = "Search a saleinvoiceDamage by code in Pointofsale",
            response = SaleInvoiceDamageDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoiceDamage found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findSaleInvoiceDamageByCode(
            @ApiParam(name = "saleiDamageCode", type = "String", required = true,
                    value="Code of the concerned saleinvoiceDamage", example = "code")
            @NotNull @PathVariable("saleiDamageCode") String saleiDamageCode,
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/saleInvoiceDamage/all/between/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllSaleiDamageBetween", notes = "Search a saleinvoiceDamage list between 02 dates",
            responseContainer = "List<SaleInvoiceDamageDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoiceDamage list found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllSaleiDamageBetween(
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/saleInvoiceDamage/page/between/{from}/{to}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageSaleiDamageBetween", notes = "Search a saleinvoiceDamage list between 02 dates page by page",
            responseContainer = "Page<SaleInvoiceDamageDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoiceDamage page found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageSaleiDamageBetween(
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/saleInvoiceDamage/client/all/between/{clientId}/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllSaleiDamageofClientBetween", notes = "Search a saleinvoiceDamage list for a client " +
            "between 02 dates",
            responseContainer = "List<SaleInvoiceDamageDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoiceDamage list for a client found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllSaleiDamageofClientBetween(
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

    @GetMapping(value = APP_ROOT+"/saleInvoiceDamage/client/page/between/{clientId}/{from}/{to}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageSaleiDamageofClientBetween", notes = "Search a saleinvoiceDamage page for a client " +
            "between 02 dates",
            responseContainer = "Page<SaleInvoiceDamageDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoiceDamage page for a client found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageSaleiDamageofClientBetween(
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

    @GetMapping(value = APP_ROOT+"/saleInvoiceDamage/userbm/all/between/{userbmId}/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllSaleiDamageofUserbmBetween", notes = "Search a saleinvoiceDamage list for a UserBM " +
            "between 02 dates",
            responseContainer = "List<SaleInvoiceDamageDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoiceDamage list for a UserBM found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllSaleiDamageofUserbmBetween(
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

    @GetMapping(value = APP_ROOT+"/saleInvoiceDamage/userbm/page/between/{userbmId}/{from}/{to}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageSaleiDamageofUserbmBetween", notes = "Search a saleinvoiceDamage page for a userbm " +
            "between 02 dates",
            responseContainer = "Page<SaleInvoiceDamageDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoiceDamage page for a userbm found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageSaleiDamageofUserbmBetween(
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

    @GetMapping(value = APP_ROOT+"/saleInvoiceDamage/pos/all/between/{posId}/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllSaleiDamageofUserbmBetween", notes = "Search a saleinvoiceDamage list in Pos" +
            "between 02 dates",
            responseContainer = "List<SaleInvoiceDamageDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoiceDamage list in Pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllSaleiDamageinPosBetween(
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

    @GetMapping(value = APP_ROOT+"/saleInvoiceDamage/pos/page/between/{posId}/{from}/{to}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageSaleiDamageinPosBetween", notes = "Search a saleinvoiceDamage page for a userbm " +
            "between 02 dates",
            responseContainer = "Page<SaleInvoiceDamageDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoiceDamage page in pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageSaleiDamageinPosBetween(
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

    @GetMapping(value = APP_ROOT+"/saleInvoiceDamage/pos/userbm/all/between/{userbmId}/{posId}/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllSaleiDamageofUserbminPosBetween", notes = "Search a saleinvoiceDamage list in Pos" +
            "between 02 dates for a UserBM",
            responseContainer = "List<SaleInvoiceDamageDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoiceDamage list in Pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllSaleiDamageofUserbminPosBetween(
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

    @GetMapping(value = APP_ROOT+"/saleInvoiceDamage/pos/userbm/page/between/{userbmId}/{posId}/{from}/{to}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageSaleiDamageofUserbminPosBetween", notes = "Search a saleinvoiceDamage page in Pos" +
            "between 02 dates for a UserBM",
            responseContainer = "Page<SaleInvoiceDamageDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoiceDamage page in Pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageSaleiDamageofUserbminPosBetween(
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

    @GetMapping(value = APP_ROOT+"/saleInvoiceDamage/pos/client/all/between/{clientId}/{posId}/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllSaleiDamageofClientinPosBetween", notes = "Search a saleinvoiceDamage list in Pos" +
            "between 02 dates for a client",
            responseContainer = "List<SaleInvoiceDamageDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoiceDamage list in Pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllSaleiDamageofClientinPosBetween(
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

    @GetMapping(value = APP_ROOT+"/saleInvoiceDamage/pos/client/page/between/{clientId}/{posId}/{from}/{to}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageSaleiDamageofClientinPosBetween", notes = "Search a saleinvoiceDamage page in Pos" +
            "between 02 dates for a client",
            responseContainer = "Page<SaleInvoiceDamageDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoiceDamage page in Pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageSaleiDamageofClientinPosBetween(
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

    @GetMapping(value = APP_ROOT+"/saleInvoiceDamage/client/userbm/all/between/{clientId}/{userbmId}/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllSaleiDamageofClientforUserbmBetween", notes = "Search a saleinvoiceDamage list for a client" +
            "between 02 dates saved by a user",
            responseContainer = "List<SaleInvoiceDamageDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoiceDamage list in Pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllSaleiDamageofClientforUserbmBetween(
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

    @GetMapping(value = APP_ROOT+"/saleInvoiceDamage/client/userbm/all/between/{clientId}/{userbmId}/{from}/{to}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageSaleiDamageofClientforUserbmBetween", notes = "Search a saleinvoiceDamage page for a client" +
            "between 02 dates saved by a user",
            responseContainer = "Page<SaleInvoiceDamageDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoiceDamage page in Pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageSaleiDamageofClientforUserbmBetween(
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

    @GetMapping(value = APP_ROOT+"/saleInvoiceDamage/pos/client/userbm/all/between/{clientId}/{userbmId}/{posId}/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllSaleiDamageofClientforUserbminPosBetween", notes = "Search a saleinvoiceDamage list for a client" +
            "between 02 dates saved by a user in a pointofsale",
            responseContainer = "List<SaleInvoiceDamageDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoiceDamage list in Pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllSaleiDamageofClientforUserbminPosBetween(
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

    @GetMapping(value = APP_ROOT+"/saleInvoiceDamage/pos/client/userbm/all/between/{clientId}/{userbmId}/{posId}/{from}/{to}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageSaleiDamageofClientforUserbminPosBetween", notes = "Search a saleinvoiceDamage page for a client" +
            "between 02 dates saved by a user in a pointofsale",
            responseContainer = "Page<SaleInvoiceDamageDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoiceDamage Page in Pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageSaleiDamageofClientforUserbminPosBetween(
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
