package com.c2psi.businessmanagement.controllers.api.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.CapsuleArrivalDto;
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
@Api(APP_ROOT+"/capsuleArrival")
public interface CapsuleArrivalApi {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = APP_ROOT+"/capsuleArrival/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveCapsuleArrival",
            notes = "This method is used to save a capsule arrival in the DB",
            response = CapsuleArrivalDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object CapsuleArrivalDto added successfully"),
            @ApiResponse(code=400, message="Object CapsuleArrivalDto is not valid during the saving process")
    })
    ResponseEntity saveCapsuleArrival(
            @ApiParam(name = "capsaDto", type = "CapsuleArrivalDto", required = true,
                    value="The JSON object that represent the CapsuleArrivalDto to save")
            @Valid @RequestBody CapsuleArrivalDto capsaDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = APP_ROOT+"/capsuleArrival/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "updateCapsuleArrival",
            notes = "This method is used to update a Capsule arrival in the DB",
            response = CapsuleArrivalDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object CapsuleArrivalDto updated successfully"),
            @ApiResponse(code=400, message="Object CapsuleArrivalDto is not valid during the saving process")
    })
    ResponseEntity updateCapsuleArrival(
            @ApiParam(name = "capsaDto", type = "CapsuleArrivalDto", required = true,
                    value="The JSON object that represent the CapsuleArrivalDto to save")
            @Valid @RequestBody CapsuleArrivalDto capsaDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = APP_ROOT+"/capsuleArrival/delete/id/{capsaId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deleteCapsuleArrivalById",
            notes = "This method is used to delete the capsArrival saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object capsuleArrival deleted successfully")
    })
    ResponseEntity deleteCapsuleArrivalById(
            @ApiParam(name = "capsaId", type = "Long", required = true,
                    value="Id of the CapsuleArrival to delete", example = "1")
            @NotNull @PathVariable("capsaId") Long capsaId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/capsuleArrival/id/{capsaId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findcapsuleArrivalById", notes = "Search a capsuleArrival in a pointofsale",
            response = CapsuleArrivalDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The capsuleArrivalDto found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findCapsuleArrivalById(
            @ApiParam(name = "capsaId", type = "Long", required = true,
                    value="Id of the concerned capsuleArrival", example = "1")
            @NotNull @PathVariable("capsaId") Long capsaId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/capsuleArrival/article/{artId}/{sicapsId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findCapsuleArrivalofArticleinSicapsule", notes = "Search a capsuleArrival in a pointofsale",
            response = CapsuleArrivalDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The capsuleArrivalDto found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findCapsuleArrivalofArticleinSicaps(
            @ApiParam(name = "artId", type = "Long", required = true,
                    value="Id of the concerned Article", example = "1")
            @NotNull @PathVariable("artId") Long artId,
            @ApiParam(name = "sicapsId", type = "Long", required = true,
                    value="Id of the concerned SupplyInvoicecapsule", example = "1")
            @NotNull @PathVariable("sicapsId") Long sicapsId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/capsuleArrival/all/{sicapsId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllcapsuleArrivalinSicapsule", notes = "Search all capsuleArrival in a pointofsale on a supply " +
            "invoice capsule",
            responseContainer = "List<capsuleArrivalDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The capsuleArrivalDto list found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCapsuleArrivalinSicaps(
            @ApiParam(name = "sicapsId", type = "Long", required = true,
                    value="Id of the concerned SupplyInvoicecapsule", example = "1")
            @NotNull @PathVariable("sicapsId") Long sicapsId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/capsuleArrival/all/{sicapsId}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCapsuleArrivalinSicapsule", notes = "Search all capsuleArrival in a pointofsale on a supply " +
            "invoice capsule page by page",
            responseContainer = "Page<CapsuleArrivalDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The capsuleArrivalDto page found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCapsuleArrivalinSicaps(
            @ApiParam(name = "sicapsId", type = "Long", required = true,
                    value="Id of the concerned SupplyInvoicecapsule", example = "1")
            @NotNull @PathVariable("sicapsId") Long sicapsId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/capsuleArrival/all/between/{sicapsId}/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCapsuleArrivalinSicapsuleBetween", notes = "Search all capsuleArrival in a pointofsale on a supply " +
            "invoice capsule",
            responseContainer = "List<CapsuleArrivalDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The capsuleArrivalDto list found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllCapsuleArrivalinSicapsBetween(
            @ApiParam(name = "sicapsId", type = "Long", required = true,
                    value="Id of the concerned SupplyInvoicecapsule", example = "1")
            @NotNull @PathVariable("sicapsId") Long sicapsId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/capsuleArrival/page/between/{sicapsId}/{from}/{to}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPagecapsuleArrivalinSicapsuleBetween", notes = "Search all capsuleArrival in a pointofsale on a supply " +
            "invoice capsule page by page",
            responseContainer = "Page<CapsuleArrivalDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The capsuleArrivalDto page found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageCapsuleArrivalinSicapsBetween(
            @ApiParam(name = "sicapsId", type = "Long", required = true,
                    value="Id of the concerned SupplyInvoicecapsule", example = "1")
            @NotNull @PathVariable("sicapsId") Long sicapsId,
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
