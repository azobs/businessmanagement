package com.c2psi.businessmanagement.controllers.api.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.SupplyInvoiceCapsuleDto;
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

import static com.c2psi.businessmanagement.utils.stock.product.SupplyInvoiceCapsuleApiConstant.*;

@Validated
@Api(SUPPLYINVOICECAPSULE_ENDPOINT)
public interface SupplyInvoiceCapsuleApi {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = CREATE_SUPPLYINVOICECAPSULE_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveSupplyInvoiceCapsule",
            notes = "This method is used to save a supply invoice Capsule in the DB",
            response = SupplyInvoiceCapsuleDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object SupplyInvoiceCapsule added successfully"),
            @ApiResponse(code=400, message="Object SupplyInvoiceCapsule is not valid during the saving process")
    })
    ResponseEntity saveSupplyInvoiceCapsule(
            @ApiParam(name = "sicapsDto", type = "SupplyInvoiceCapsuleDto", required = true,
                    value="The JSON object that represent the SupplyInvoiceCapsuleDto to save")
            @Valid @RequestBody SupplyInvoiceCapsuleDto sicapsDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = UPDATE_SUPPLYINVOICECAPSULE_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveSupplyInvoiceCapsule",
            notes = "This method is used to update a supply invoice Capsule in the DB",
            response = SupplyInvoiceCapsuleDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object SupplyInvoiceCapsule updated successfully"),
            @ApiResponse(code=400, message="Object SupplyInvoiceCapsule is not valid during the saving process")
    })
    ResponseEntity updateSupplyInvoiceCapsule(
            @ApiParam(name = "sicapsDto", type = "SupplyInvoiceCapsuleDto", required = true,
                    value="The JSON object that represent the SupplyInvoiceCapsuleDto to save")
            @Valid @RequestBody SupplyInvoiceCapsuleDto sicapsDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = DELETE_SUPPLYINVOICECAPSULE_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deleteSupplyInvoiceCapsuleById",
            notes = "This method is used to delete the supplyInvoiceCapsule saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object supplyInvoiceCapsule deleted successfully")
    })
    ResponseEntity deleteSupplyInvoiceCapsuleById(
            @ApiParam(name = "sicapsId", type = "Long", required = true,
                    value="Id of the SupplyInvoiceCapsule to delete", example = "1")
            @NotNull @PathVariable("sicapsId") Long sicapsId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_SUPPLYINVOICECAPSULE_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findSupplyInvoiceCapsuleById", notes = "Search a supplyinvoiceCapsule in a pointofsale",
            response = SupplyInvoiceCapsuleDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The supplyinvoiceCapsule found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findSupplyInvoiceCapsuleById(
            @ApiParam(name = "sicapsId", type = "Long", required = true,
                    value="Id of the concerned supplyinvoiceCapsule", example = "1")
            @NotNull @PathVariable("sicapsId") Long sicapsId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_SUPPLYINVOICECAPSULE_BY_CODE_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findSupplyInvoiceCapsuleByCode", notes = "Search a supplyinvoiceCapsule in a pointofsale",
            response = SupplyInvoiceCapsuleDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The supplyinvoiceCapsule found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findSupplyInvoiceCapsuleByCode(
            @ApiParam(name = "sicapsCode", type = "String", required = true,
                    value="Code of the concerned supplyinvoiceCapsule", example = "1")
            @NotNull @PathVariable("sicapsCode") String sicapsCode,
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_SUPPLYINVOICECAPSULE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllSupplyInvoiceCapsuleBetween", notes = "Search all supplyinvoiceCapsule in a pointofsale between 02 date",
            responseContainer = "List<SupplyInvoiceCapsuleDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The supplyinvoiceCapsule list found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllSupplyInvoiceCapsuleBetween(
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

    @GetMapping(value = FIND_PAGE_SUPPLYINVOICECAPSULE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageSupplyInvoiceCapsuleBetween", notes = "Search all supplyinvoiceCapsule in a pointofsale " +
            "between 02 date page by page",
            responseContainer = "Page<SupplyInvoiceCapsuleDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The supplyinvoiceCapsule page found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageSupplyInvoiceCapsuleBetween(
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

    @GetMapping(value = FIND_ALL_SUPPLYINVOICECAPSULE_OF_USERBM_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllSupplyInvoiceCapsuleofUserbmBetween", notes = "Search all supplyinvoiceCapsule in a pointofsale " +
            "between 02 date of a Userbm",
            responseContainer = "List<SupplyInvoiceCapsuleDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The supplyinvoiceCapsule list found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllSupplyInvoiceCapsuleofUserbmBetween(
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

    @GetMapping(value = FIND_PAGE_SUPPLYINVOICECAPSULE_OF_USERBM_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageSupplyInvoiceCapsuleofUserbmBetween", notes = "Search all supplyinvoiceCapsule in a pointofsale " +
            "between 02 date of a Userbm page by page",
            responseContainer = "Page<SupplyInvoiceCapsuleDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The supplyinvoiceCapsule page found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageSupplyInvoiceCapsuleofUserbmBetween(
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

    @GetMapping(value = FIND_ALL_SUPPLYINVOICECAPSULE_OF_PROVIDER_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllSupplyInvoiceCapsuleofProviderBetween", notes = "Search all supplyinvoiceCapsule in " +
            "a pointofsale " +
            "between 02 date of a provider",
            responseContainer = "List<SupplyInvoiceCapsuleDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The supplyinvoiceCapsule list found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllSupplyInvoiceCapsuleofProviderBetween(
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

    @GetMapping(value = FIND_PAGE_SUPPLYINVOICECAPSULE_OF_PROVIDER_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageSupplyInvoiceCapsuleofProviderBetween", notes = "Search all supplyinvoiceCapsule " +
            "in a pointofsale " +
            "between 02 date of a provider page by page",
            responseContainer = "Page<SupplyInvoiceCapsuleDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The supplyinvoiceCapsule page found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageSupplyInvoiceCapsuleofProviderBetween(
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

    @GetMapping(value = FIND_ALL_SUPPLYINVOICECAPSULE_OF_PROVIDER_AND_USERBM_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllSupplyInvoiceCapsuleofProviderAndUserbmBetween", notes = "Search all supplyinvoiceCapsule in a pointofsale " +
            "between 02 date of a provider saved by a userbm",
            responseContainer = "List<SupplyInvoiceCapsuleDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The supplyinvoiceCapsule list found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllSupplyInvoiceCapsuleofProviderAndUserbmBetween(
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

    @GetMapping(value = FIND_PAGE_SUPPLYINVOICECAPSULE_OF_PROVIDER_AND_USERBM_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageSupplyInvoiceCapsuleofProviderAndUserbmBetween", notes = "Search all supplyinvoiceCapsule in a pointofsale " +
            "between 02 date of a provider saved by a userbm page by page",
            responseContainer = "List<SupplyInvoiceCapsuleDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The supplyinvoiceCapsule page found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageSupplyInvoiceCapsuleofProviderAndUserbmBetween(
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
