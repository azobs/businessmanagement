package com.c2psi.businessmanagement.controllers.api.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.SupplyInvoiceDamageDto;
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

import static com.c2psi.businessmanagement.utils.stock.product.SupplyInvoiceDamageApiConstant.*;

@Validated
@Api(SUPPLYINVOICEDAMAGE_ENDPOINT)
public interface SupplyInvoiceDamageApi {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = CREATE_SUPPLYINVOICEDAMAGE_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveSupplyInvoiceDamage",
            notes = "This method is used to save a supply invoice Damage in the DB",
            response = SupplyInvoiceDamageDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object SupplyInvoiceDamage added successfully"),
            @ApiResponse(code=400, message="Object SupplyInvoiceDamage is not valid during the saving process")
    })
    ResponseEntity saveSupplyInvoiceDamage(
            @ApiParam(name = "sidamDto", type = "SupplyInvoiceDamageDto", required = true,
                    value="The JSON object that represent the SupplyInvoiceDamageDto to save")
            @Valid @RequestBody SupplyInvoiceDamageDto sidamDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = UPDATE_SUPPLYINVOICEDAMAGE_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveSupplyInvoiceDamage",
            notes = "This method is used to update a supply invoice Damage in the DB",
            response = SupplyInvoiceDamageDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object SupplyInvoiceDamage updated successfully"),
            @ApiResponse(code=400, message="Object SupplyInvoiceDamage is not valid during the saving process")
    })
    ResponseEntity updateSupplyInvoiceDamage(
            @ApiParam(name = "sidamDto", type = "SupplyInvoiceDamageDto", required = true,
                    value="The JSON object that represent the SupplyInvoiceDamageDto to save")
            @Valid @RequestBody SupplyInvoiceDamageDto sidamDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = DELETE_SUPPLYINVOICEDAMAGE_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deleteSupplyInvoiceDamageById",
            notes = "This method is used to delete the supplyInvoiceDamage saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object supplyInvoiceDamage deleted successfully")
    })
    ResponseEntity deleteSupplyInvoiceDamageById(
            @ApiParam(name = "sidamId", type = "Long", required = true,
                    value="Id of the SupplyInvoiceDamage to delete", example = "1")
            @NotNull @PathVariable("sidamId") Long sidamId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_SUPPLYINVOICEDAMAGE_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findSupplyInvoiceDamageById", notes = "Search a supplyinvoiceDamage in a pointofsale",
            response = SupplyInvoiceDamageDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The supplyinvoiceDamage found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findSupplyInvoiceDamageById(
            @ApiParam(name = "sidamId", type = "Long", required = true,
                    value="Id of the concerned supplyinvoiceDamage", example = "1")
            @NotNull @PathVariable("sidamId") Long sidamId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_SUPPLYINVOICEDAMAGE_BY_CODE_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findSupplyInvoiceDamageByCode", notes = "Search a supplyinvoiceDamage in a pointofsale",
            response = SupplyInvoiceDamageDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The supplyinvoiceDamage found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findSupplyInvoiceDamageByCode(
            @ApiParam(name = "sidamCode", type = "String", required = true,
                    value="Code of the concerned supplyinvoiceDamage", example = "1")
            @NotNull @PathVariable("sidamCode") String sidamCode,
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_SUPPLYINVOICEDAMAGE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllSupplyInvoiceDamageBetween", notes = "Search all supplyinvoiceDamage in a pointofsale between 02 date",
            responseContainer = "List<SupplyInvoiceDamageDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The supplyinvoiceDamage list found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllSupplyInvoiceDamageBetween(
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

    @GetMapping(value = FIND_PAGE_SUPPLYINVOICEDAMAGE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageSupplyInvoiceDamageBetween", notes = "Search all supplyinvoiceDamage in a pointofsale " +
            "between 02 date page by page",
            responseContainer = "Page<SupplyInvoiceDamageDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The supplyinvoiceDamage page found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageSupplyInvoiceDamageBetween(
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

    @GetMapping(value = FIND_ALL_SUPPLYINVOICEDAMAGE_OF_USERBM_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllSupplyInvoiceDamageofUserbmBetween", notes = "Search all supplyinvoiceDamage in a pointofsale " +
            "between 02 date of a Userbm",
            responseContainer = "List<SupplyInvoiceDamageDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The supplyinvoiceDamage list found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllSupplyInvoiceDamageofUserbmBetween(
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

    @GetMapping(value = FIND_PAGE_SUPPLYINVOICEDAMAGE_OF_USERBM_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageSupplyInvoiceDamageofUserbmBetween", notes = "Search all supplyinvoiceDamage in a pointofsale " +
            "between 02 date of a Userbm page by page",
            responseContainer = "Page<SupplyInvoiceDamageDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The supplyinvoiceDamage page found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageSupplyInvoiceDamageofUserbmBetween(
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

    @GetMapping(value = FIND_ALL_SUPPLYINVOICEDAMAGE_OF_PROVIDER_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllSupplyInvoiceDamageofProviderBetween", notes = "Search all supplyinvoiceDamage in a pointofsale " +
            "between 02 date of a provider",
            responseContainer = "List<SupplyInvoiceDamageDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The supplyinvoiceDamage list found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllSupplyInvoiceDamageofProviderBetween(
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

    @GetMapping(value = FIND_PAGE_SUPPLYINVOICEDAMAGE_OF_PROVIDER_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageSupplyInvoiceDamageofProviderBetween", notes = "Search all supplyinvoiceDamage in a pointofsale " +
            "between 02 date of a provider page by page",
            responseContainer = "Page<SupplyInvoiceDamageDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The supplyinvoiceDamage page found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageSupplyInvoiceDamageofProviderBetween(
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

    @GetMapping(value = FIND_ALL_SUPPLYINVOICEDAMAGE_OF_PROVIDER_AND_USERBM_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllSupplyInvoiceDamageofProviderAndUserbmBetween", notes = "Search all supplyinvoiceDamage in a pointofsale " +
            "between 02 date of a provider saved by a userbm",
            responseContainer = "List<SupplyInvoiceDamageDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The supplyinvoiceDamage list found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllSupplyInvoiceDamageofProviderAndUserbmBetween(
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

    @GetMapping(value = FIND_PAGE_SUPPLYINVOICEDAMAGE_OF_PROVIDER_AND_USERBM_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageSupplyInvoiceDamageofProviderAndUserbmBetween", notes = "Search all supplyinvoiceDamage in a pointofsale " +
            "between 02 date of a provider saved by a userbm page by page",
            responseContainer = "List<SupplyInvoiceDamageDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The supplyinvoiceDamage page found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageSupplyInvoiceDamageofProviderAndUserbmBetween(
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
