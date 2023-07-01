package com.c2psi.businessmanagement.controllers.api.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.SupplyInvoiceCashDto;
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
@Api(APP_ROOT+"/supplyInvoiceCash")
public interface SupplyInvoiceCashApi {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = APP_ROOT+"/supplyInvoiceCash/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveSupplyInvoiceCash",
            notes = "This method is used to save a supply invoice cash in the DB",
            response = SupplyInvoiceCashDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object SupplyInvoiceCash added successfully"),
            @ApiResponse(code=400, message="Object SupplyInvoiceCash is not valid during the saving process")
    })
    ResponseEntity saveSupplyInvoiceCash(
            @ApiParam(name = "sicashDto", type = "SupplyInvoiceCashDto", required = true,
                    value="The JSON object that represent the SupplyInvoiceCashDto to save")
            @Valid @RequestBody SupplyInvoiceCashDto sicashDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = APP_ROOT+"/supplyInvoiceCash/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveSupplyInvoiceCash",
            notes = "This method is used to update a supply invoice cash in the DB",
            response = SupplyInvoiceCashDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object SupplyInvoiceCash updated successfully"),
            @ApiResponse(code=400, message="Object SupplyInvoiceCash is not valid during the saving process")
    })
    ResponseEntity updateSupplyInvoiceCash(
            @ApiParam(name = "sicashDto", type = "SupplyInvoiceCashDto", required = true,
                    value="The JSON object that represent the SupplyInvoiceCashDto to save")
            @Valid @RequestBody SupplyInvoiceCashDto sicashDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = APP_ROOT+"/supplyInvoiceCash/delete/id/{sicashId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deleteSupplyInvoiceCashById",
            notes = "This method is used to delete the supplyInvoiceCash saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object supplyInvoiceCash deleted successfully")
    })
    ResponseEntity deleteSupplyInvoiceCashById(
            @ApiParam(name = "sicashId", type = "Long", required = true,
                    value="Id of the SupplyInvoiceCash to delete", example = "1")
            @NotNull @PathVariable("sicashId") Long sicashId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/supplyInvoiceCash/id/{sicashId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findSupplyInvoiceCashById", notes = "Search a supplyinvoicecash in a pointofsale",
            response = SupplyInvoiceCashDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The supplyinvoicecash found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findSupplyInvoiceCashById(
            @ApiParam(name = "sicashId", type = "Long", required = true,
                    value="Id of the concerned supplyinvoicecash", example = "1")
            @NotNull @PathVariable("sicashId") Long sicashId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/supplyInvoiceCash/code/pos/{sicashCode}/{posId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findSupplyInvoiceCashByCode", notes = "Search a supplyinvoicecash in a pointofsale",
            response = SupplyInvoiceCashDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The supplyinvoicecash found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findSupplyInvoiceCashByCode(
            @ApiParam(name = "sicashCode", type = "String", required = true,
                    value="Code of the concerned supplyinvoicecash", example = "1")
            @NotNull @PathVariable("sicashCode") String sicashCode,
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/supplyInvoiceCash/pos/all/{posId}/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllSupplyInvoiceCashBetween", notes = "Search all supplyinvoicecash in a pointofsale between 02 date",
            responseContainer = "List<SupplyInvoiceCashDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The supplyinvoicecash list found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllSupplyInvoiceCashBetween(
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

    @GetMapping(value = APP_ROOT+"/supplyInvoiceCash/pos/page/{posId}/{from}/{to}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageSupplyInvoiceCashBetween", notes = "Search all supplyinvoicecash in a pointofsale " +
            "between 02 date page by page",
            responseContainer = "Page<SupplyInvoiceCashDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The supplyinvoicecash page found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageSupplyInvoiceCashBetween(
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

    @GetMapping(value = APP_ROOT+"/supplyInvoiceCash/pos/userbm/all/{posId}/{userbmId}/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllSupplyInvoiceCashofUserbmBetween", notes = "Search all supplyinvoicecash in a pointofsale " +
            "between 02 date of a Userbm",
            responseContainer = "List<SupplyInvoiceCashDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The supplyinvoicecash list found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllSupplyInvoiceCashofUserbmBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
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

    @GetMapping(value = APP_ROOT+"/supplyInvoiceCash/pos/userbm/page/{posId}/{userbmId}/{from}/{to}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageSupplyInvoiceCashofUserbmBetween", notes = "Search all supplyinvoicecash in a pointofsale " +
            "between 02 date of a Userbm page by page",
            responseContainer = "Page<SupplyInvoiceCashDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The supplyinvoicecash page found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageSupplyInvoiceCashofUserbmBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
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

    @GetMapping(value = APP_ROOT+"/supplyInvoiceCash/pos/provider/all/{posId}/{providerId}/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllSupplyInvoiceCashofProviderBetween", notes = "Search all supplyinvoicecash in a pointofsale " +
            "between 02 date of a provider",
            responseContainer = "List<SupplyInvoiceCashDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The supplyinvoicecash list found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllSupplyInvoiceCashofProviderBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "providerId", type = "Long", required = true,
                    value="Id of the concerned Provider", example = "1")
            @NotNull @PathVariable("providerId") Long providerId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/supplyInvoiceCash/pos/provider/page/{posId}/{providerId}/{from}/{to}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageSupplyInvoiceCashofProviderBetween", notes = "Search all supplyinvoicecash in a pointofsale " +
            "between 02 date of a provider page by page",
            responseContainer = "Page<SupplyInvoiceCashDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The supplyinvoicecash page found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageSupplyInvoiceCashofProviderBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "providerId", type = "Long", required = true,
                    value="Id of the concerned Provider", example = "1")
            @NotNull @PathVariable("providerId") Long providerId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/supplyInvoiceCash/pos/userbm/provider/all/{posId}/{providerId}/{userbmId}/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllSupplyInvoiceCashofProviderAndUserbmBetween", notes = "Search all supplyinvoicecash in a pointofsale " +
            "between 02 date of a provider saved by a userbm",
            responseContainer = "List<SupplyInvoiceCashDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The supplyinvoicecash list found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllSupplyInvoiceCashofProviderAndUserbmBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "providerId", type = "Long", required = true,
                    value="Id of the concerned Provider", example = "1")
            @NotNull @PathVariable("providerId") Long providerId,
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

    @GetMapping(value = APP_ROOT+"/supplyInvoiceCash/pos/userbm/provider/page/{posId}/{providerId}/{userbmId}/{from}/{to}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageSupplyInvoiceCashofProviderAndUserbmBetween", notes = "Search all supplyinvoicecash in a pointofsale " +
            "between 02 date of a provider saved by a userbm page by page",
            responseContainer = "List<SupplyInvoiceCashDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The supplyinvoicecash page found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageSupplyInvoiceCashofProviderAndUserbmBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "providerId", type = "Long", required = true,
                    value="Id of the concerned Provider", example = "1")
            @NotNull @PathVariable("providerId") Long providerId,
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




}
