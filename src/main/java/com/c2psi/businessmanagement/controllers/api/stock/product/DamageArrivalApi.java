package com.c2psi.businessmanagement.controllers.api.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.DamageArrivalDto;
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

import static com.c2psi.businessmanagement.utils.stock.product.DamageArrivalApiConstant.*;

@Validated
@Api(DAMAGEARRIVAL_ENDPOINT)
public interface DamageArrivalApi {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = CREATE_DAMAGEARRIVAL_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveDamageArrival",
            notes = "This method is used to save a Damage arrival in the DB",
            response = DamageArrivalDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object DamageArrivalDto added successfully"),
            @ApiResponse(code=400, message="Object DamageArrivalDto is not valid during the saving process")
    })
    ResponseEntity saveDamageArrival(
            @ApiParam(name = "damaDto", type = "DamageArrivalDto", required = true,
                    value="The JSON object that represent the DamageArrivalDto to save")
            @Valid @RequestBody DamageArrivalDto damaDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = UPDATE_DAMAGEARRIVAL_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "updateDamageArrival",
            notes = "This method is used to save a Damage arrival in the DB",
            response = DamageArrivalDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object DamageArrivalDto updated successfully"),
            @ApiResponse(code=400, message="Object DamageArrivalDto is not valid during the saving process")
    })
    ResponseEntity updateDamageArrival(
            @ApiParam(name = "damaDto", type = "DamageArrivalDto", required = true,
                    value="The JSON object that represent the DamageArrivalDto to save")
            @Valid @RequestBody DamageArrivalDto damaDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = DELETE_DAMAGEARRIVAL_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deleteDamageArrivalById",
            notes = "This method is used to delete the capsArrival saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object DamageArrival deleted successfully")
    })
    ResponseEntity deleteDamageArrivalById(
            @ApiParam(name = "damaId", type = "Long", required = true,
                    value="Id of the DamageArrival to delete", example = "1")
            @NotNull @PathVariable("damaId") Long damaId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_DAMAGEARRIVAL_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findDamageArrivalById", notes = "Search a DamageArrival in a pointofsale",
            response = DamageArrivalDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The DamageArrivalDto found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findDamageArrivalById(
            @ApiParam(name = "damaId", type = "Long", required = true,
                    value="Id of the concerned DamageArrival", example = "1")
            @NotNull @PathVariable("damaId") Long damaId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_DAMAGEARRIVAL_OF_ARTICLE_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findDamageArrivalofArticleinSiDam", notes = "Search a DamageArrival in a pointofsale",
            response = DamageArrivalDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The DamageArrivalDto found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findDamageArrivalofArticleinSidam(
            @ApiParam(name = "artId", type = "Long", required = true,
                    value="Id of the concerned Article", example = "1")
            @NotNull @PathVariable("artId") Long artId,
            @ApiParam(name = "sidamId", type = "Long", required = true,
                    value="Id of the concerned SupplyInvoiceDamage", example = "1")
            @NotNull @PathVariable("sidamId") Long sidamId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_DAMAGEERRIVAL_IN_SIDAM_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllDamageArrivalinSiDam", notes = "Search all DamageArrival in a pointofsale on a supply " +
            "invoice Damage",
            responseContainer = "List<DamageArrivalDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The DamageArrivalDto list found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllDamageArrivalinSidam(
            @ApiParam(name = "sidamId", type = "Long", required = true,
                    value="Id of the concerned SupplyInvoiceDamage", example = "1")
            @NotNull @PathVariable("sidamId") Long sidamId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_DAMAGEERRIVAL_IN_SIDAM_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllDamageArrivalinSidam", notes = "Search all DamageArrival in a pointofsale on a supply " +
            "invoice Damage page by page",
            responseContainer = "Page<DamageArrivalDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The DamageArrivalDto page found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageDamageArrivalinSidam(
            @ApiParam(name = "sidamId", type = "Long", required = true,
                    value="Id of the concerned SupplyInvoiceDamage", example = "1")
            @NotNull @PathVariable("sidamId") Long sidamId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_DAMAGEERRIVAL_IN_SIDAM_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllDamageArrivalinSiDamageBetween", notes = "Search all DamageArrival in a pointofsale on a supply " +
            "invoice Damage",
            responseContainer = "List<DamageArrivalDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The DamageArrivalDto list found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllDamageArrivalinSidamBetween(
            @ApiParam(name = "sidamId", type = "Long", required = true,
                    value="Id of the concerned SupplyInvoiceDamage", example = "1")
            @NotNull @PathVariable("sidamId") Long sidamId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_DAMAGEERRIVAL_IN_SIDAM_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageDamageArrivalinSiDamageBetween", notes = "Search all DamageArrival in a pointofsale on a supply " +
            "invoice Damage page by page",
            responseContainer = "Page<DamageArrivalDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The DamageArrivalDto page found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageDamageArrivalinSidamBetween(
            @ApiParam(name = "sidamId", type = "Long", required = true,
                    value="Id of the concerned SupplyInvoiceDamage", example = "1")
            @NotNull @PathVariable("sidamId") Long sidamId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_DAMAGEERRIVAL_IN_POS_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllDamageArrivalinPosBetween", notes = "Search all DamageArrival in a pointofsale  " +
            "between 02 dates",
            responseContainer = "List<DamageArrivalDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The DamageArrivalDto list found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllDamageArrivalinPosBetween(
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

    @GetMapping(value = FIND_PAGE_DAMAGEERRIVAL_IN_POS_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageDamageArrivalinSiPosBetween", notes = "Search all DamageArrival in a pointofsale  " +
            " page by page between 02 dates",
            responseContainer = "Page<DamageArrivalDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The DamageArrivalDto page found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageDamageArrivalinPosBetween(
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
    
    
}
