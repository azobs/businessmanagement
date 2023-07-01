package com.c2psi.businessmanagement.controllers.api.client.command;

import com.c2psi.businessmanagement.dtos.client.command.SaleInvoiceCashDto;
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
@Api(APP_ROOT+"/saleInvoiceCash")
public interface SaleInvoiceCashApi {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = APP_ROOT+"/saleInvoiceCash/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveSaleInvoiceCash",
            notes = "This method is used to save a sale invoice cash in the DB",
            response = SaleInvoiceCashDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object SaleInvoiceCashDto added successfully"),
            @ApiResponse(code=400, message="Object SaleInvoiceCashDto is not valid during the saving process")
    })
    ResponseEntity saveSaleInvoiceCash(
            @ApiParam(name = "saleicashDto", type = "SaleInvoiceCashDto", required = true,
                    value="The JSON object that represent the SaleInvoiceCashDto to save")
            @Valid @RequestBody SaleInvoiceCashDto saleicashDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = APP_ROOT+"/saleInvoiceCash/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "updateSaleInvoiceCash",
            notes = "This method is used to update a sale invoice cash in the DB",
            response = SaleInvoiceCashDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object SaleInvoiceCashDto updated successfully"),
            @ApiResponse(code=400, message="Object SaleInvoiceCashDto is not valid during the saving process")
    })
    ResponseEntity updateSaleInvoiceCash(
            @ApiParam(name = "saleicashDto", type = "SaleInvoiceCashDto", required = true,
                    value="The JSON object that represent the SaleInvoiceCashDto to save")
            @Valid @RequestBody SaleInvoiceCashDto saleicashDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = APP_ROOT+"/saleInvoiceCash/delete/id/{saleicashId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deleteSaleInvoiceCashById",
            notes = "This method is used to delete the saleInvoiceCash saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object saleInvoiceCash deleted successfully")
    })
    ResponseEntity deleteSaleInvoiceCashById(
            @ApiParam(name = "saleicashId", type = "Long", required = true,
                    value="Id of the SaleInvoiceCash to delete", example = "1")
            @NotNull @PathVariable("saleicashId") Long saleicashId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/saleInvoiceCash/id/{saleicashId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findSaleInvoiceCashById", notes = "Search a saleinvoicecash by id",
            response = SaleInvoiceCashDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoicecash found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findSaleInvoiceCashById(
            @ApiParam(name = "saleicashId", type = "Long", required = true,
                    value="Id of the concerned saleinvoicecash", example = "1")
            @NotNull @PathVariable("saleicashId") Long saleicashId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/saleInvoiceCash/pos/code/{saleicashCode}/{posId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findSaleInvoiceCashByCode", notes = "Search a saleinvoicecash by code in Pointofsale",
            response = SaleInvoiceCashDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoicecash found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findSaleInvoiceCashByCode(
            @ApiParam(name = "saleicashCode", type = "String", required = true,
                    value="Code of the concerned saleinvoicecash", example = "code")
            @NotNull @PathVariable("saleicashCode") String saleicashCode,
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/saleInvoiceCash/all/between/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllSaleicashBetween", notes = "Search a saleinvoicecash list between 02 dates",
            responseContainer = "List<SaleInvoiceCashDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoicecash list found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllSaleicashBetween(
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/saleInvoiceCash/page/between/{from}/{to}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageSaleicashBetween", notes = "Search a saleinvoicecash list between 02 dates page by page",
            responseContainer = "Page<SaleInvoiceCashDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoicecash page found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageSaleicashBetween(
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/saleInvoiceCash/client/all/between/{clientId}/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllSaleicashofClientBetween", notes = "Search a saleinvoicecash list for a client " +
            "between 02 dates",
            responseContainer = "List<SaleInvoiceCashDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoicecash list for a client found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllSaleicashofClientBetween(
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

    @GetMapping(value = APP_ROOT+"/saleInvoiceCash/client/page/between/{clientId}/{from}/{to}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageSaleicashofClientBetween", notes = "Search a saleinvoicecash page for a client " +
            "between 02 dates",
            responseContainer = "Page<SaleInvoiceCashDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoicecash page for a client found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageSaleicashofClientBetween(
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

    @GetMapping(value = APP_ROOT+"/saleInvoiceCash/userbm/all/between/{userbmId}/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllSaleicashofUserbmBetween", notes = "Search a saleinvoicecash list for a UserBM " +
            "between 02 dates",
            responseContainer = "List<SaleInvoiceCashDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoicecash list for a UserBM found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllSaleicashofUserbmBetween(
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

    @GetMapping(value = APP_ROOT+"/saleInvoiceCash/userbm/page/between/{userbmId}/{from}/{to}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageSaleicashofUserbmBetween", notes = "Search a saleinvoicecash page for a userbm " +
            "between 02 dates",
            responseContainer = "Page<SaleInvoiceCashDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoicecash page for a userbm found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageSaleicashofUserbmBetween(
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

    @GetMapping(value = APP_ROOT+"/saleInvoiceCash/pos/all/between/{posId}/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllSaleicashofUserbmBetween", notes = "Search a saleinvoicecash list in Pos" +
            "between 02 dates",
            responseContainer = "List<SaleInvoiceCashDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoicecash list in Pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllSaleicashinPosBetween(
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

    @GetMapping(value = APP_ROOT+"/saleInvoiceCash/pos/page/between/{posId}/{from}/{to}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageSaleicashinPosBetween", notes = "Search a saleinvoicecash page for a userbm " +
            "between 02 dates",
            responseContainer = "Page<SaleInvoiceCashDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoicecash page in pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageSaleicashinPosBetween(
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

    @GetMapping(value = APP_ROOT+"/saleInvoiceCash/pos/userbm/all/between/{userbmId}/{posId}/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllSaleicashofUserbminPosBetween", notes = "Search a saleinvoicecash list in Pos" +
            "between 02 dates for a UserBM",
            responseContainer = "List<SaleInvoiceCashDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoicecash list in Pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllSaleicashofUserbminPosBetween(
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

    @GetMapping(value = APP_ROOT+"/saleInvoiceCash/pos/userbm/page/between/{userbmId}/{posId}/{from}/{to}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageSaleicashofUserbminPosBetween", notes = "Search a saleinvoicecash page in Pos" +
            "between 02 dates for a UserBM",
            responseContainer = "Page<SaleInvoiceCashDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoicecash page in Pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageSaleicashofUserbminPosBetween(
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

    @GetMapping(value = APP_ROOT+"/saleInvoiceCash/pos/client/all/between/{clientId}/{posId}/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllSaleicashofClientinPosBetween", notes = "Search a saleinvoicecash list in Pos" +
            "between 02 dates for a client",
            responseContainer = "List<SaleInvoiceCashDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoicecash list in Pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllSaleicashofClientinPosBetween(
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

    @GetMapping(value = APP_ROOT+"/saleInvoiceCash/pos/client/page/between/{clientId}/{posId}/{from}/{to}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageSaleicashofClientinPosBetween", notes = "Search a saleinvoicecash page in Pos" +
            "between 02 dates for a client",
            responseContainer = "Page<SaleInvoiceCashDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoicecash page in Pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageSaleicashofClientinPosBetween(
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

    @GetMapping(value = APP_ROOT+"/saleInvoiceCash/client/userbm/all/between/{clientId}/{userbmId}/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllSaleicashofClientforUserbmBetween", notes = "Search a saleinvoicecash list for a client" +
            "between 02 dates saved by a user",
            responseContainer = "List<SaleInvoiceCashDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoicecash list in Pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllSaleicashofClientforUserbmBetween(
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

    @GetMapping(value = APP_ROOT+"/saleInvoiceCash/client/userbm/all/between/{clientId}/{userbmId}/{from}/{to}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageSaleicashofClientforUserbmBetween", notes = "Search a saleinvoicecash page for a client" +
            "between 02 dates saved by a user",
            responseContainer = "Page<SaleInvoiceCashDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoicecash page in Pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageSaleicashofClientforUserbmBetween(
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

    @GetMapping(value = APP_ROOT+"/saleInvoiceCash/pos/client/userbm/all/between/{clientId}/{userbmId}/{posId}/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllSaleicashofClientforUserbminPosBetween", notes = "Search a saleinvoicecash list for a client" +
            "between 02 dates saved by a user in a pointofsale",
            responseContainer = "List<SaleInvoiceCashDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoicecash list in Pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllSaleicashofClientforUserbminPosBetween(
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

    @GetMapping(value = APP_ROOT+"/saleInvoiceCash/pos/client/userbm/all/between/{clientId}/{userbmId}/{posId}/{from}/{to}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageSaleicashofClientforUserbminPosBetween", notes = "Search a saleinvoicecash page for a client" +
            "between 02 dates saved by a user in a pointofsale",
            responseContainer = "Page<SaleInvoiceCashDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The saleinvoicecash Page in Pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageSaleicashofClientforUserbminPosBetween(
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
