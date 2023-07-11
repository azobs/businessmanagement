package com.c2psi.businessmanagement.controllers.api.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.FormatDto;
import com.c2psi.businessmanagement.dtos.stock.product.PackagingDto;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Optional;

import static com.c2psi.businessmanagement.utils.stock.product.PackagingApiConstant.*;

@Validated
@Api(PACKAGING_ENDPOINT)
public interface PackagingApi {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = CREATE_PACKAGING_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "savePackaging",
            notes = "This method is used to save packaging of a product in the DB",
            response = PackagingDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Packaging added successfully"),
            @ApiResponse(code=400, message="Object Packaging is not valid during the saving process")
    })
    ResponseEntity savePackaging(
            @ApiParam(name = "packDto", type = "PackagingDto", required = true,
                    value="The JSON object that represent the format to save")
            @Valid @RequestBody PackagingDto packDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = UPDATE_PACKAGING_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "updatePackaging",
            notes = "This method is used to update a Packaging of a product in the DB",
            response = PackagingDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Packaging updated successfully"),
            @ApiResponse(code=400, message="Object Packaging is not valid during the updating process")
    })
    ResponseEntity updatePackaging(
            @ApiParam(name = "packDto", type = "PackagingDto", required = true,
                    value="The JSON object that represent the packDto to update")
            @Valid @RequestBody PackagingDto packDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PACKAGING_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPackagingById", notes = "Search in the DB a packaging by its Id",
            response = FormatDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The Packaging with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the founding process")
    })
    ResponseEntity findPackagingById(
            @ApiParam(name = "packagingId", type = "Long", required = true,
                    value="Id of the concerned Packaging", example = "1")
            @NotNull @PathVariable("packagingId") Long packagingId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PACKAGING_BY_ATTRIBUTES_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPackagingByAttributes", notes = "Search in the DB a packaging by its parameters",
            response = PackagingDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The Packaging with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the founding process")
    })
    ResponseEntity findPackagingByAttributes(
            @ApiParam(name = "packLabel", type = "String", required = true,
                    value="name of the concerned Packaging", example = "Casier")
            @NotNull @PathVariable("packLabel") String packLabel,
            @ApiParam(name = "packFirstColor", type = "String", required = true,
                    value="name of the concerned Packaging", example = "Red")
            @NotNull @PathVariable("packFirstColor") String packFirstColor,
            @ApiParam(name = "providerId", type = "Long", required = true,
                    value="name of the provider owner of the Packaging", example = "1")
            @NotNull @PathVariable("providerId") Long providerId,
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="name of the provider owner of the Packaging", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_PACKAGING_OF_POS_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllPackagingofPos", notes = "Search all packaging in pos in the DB ",
            responseContainer = "List<PackagingDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The Packaging list with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the founding process")
    })
    ResponseEntity findAllPackagingofPos(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_PACKAGING_OF_POS_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPagePackagingofPos", notes = "Search all packaging in pos in the DB page by page",
            responseContainer = "Page<PackagingDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The Packaging page with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the founding process")
    })
    ResponseEntity findPagePackagingofPos(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_PACKAGING_OF_PROVIDER_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllPackagingofProviderinPos", notes = "Search all packaging in pos in the DB ",
            responseContainer = "List<PackagingDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The Packaging list of a provider in a pointofsale found successfully"),
            @ApiResponse(code=404, message="Error faced during the founding process")
    })
    ResponseEntity findAllPackagingofProviderinPos(
            @ApiParam(name = "providerId", type = "Long", required = true,
                    value="Id of the concerned Provider", example = "1")
            @NotNull @PathVariable("providerId") Long providerId,
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_PACKAGING_OF_PROVIDER_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPagePackagingofProviderinPos", notes = "Search all packaging in pos for a provider page by page",
            responseContainer = "Page<PackagingDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The Packaging Page of a provider in a pointofsale found successfully"),
            @ApiResponse(code=404, message="Error faced during the founding process")
    })
    ResponseEntity findPagePackagingofProviderinPos(
            @ApiParam(name = "providerId", type = "Long", required = true,
                    value="Id of the concerned Provider", example = "1")
            @NotNull @PathVariable("providerId") Long providerId,
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = CONVERT_CASH_TO_PACKAGING_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "convertCashToPackaging", notes = "Convert an amount of money in packaging",
            responseContainer = "BigDecimal")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The conversion done with success"),
            @ApiResponse(code=404, message="Error faced during the conversion process")
    })
    ResponseEntity convertCashToPackaging(
            @ApiParam(name = "packagingId", type = "Long", required = true,
                    value="Id of the concerned Packaging", example = "1")
            @NotNull @PathVariable("packagingId") Long packagingId,
            @ApiParam(name = "amountToConvert", type = "BigDecimal", required = true,
                    value="The amount to convert", example = "1")
            @NotNull @PathVariable("amountToConvert") BigDecimal amountToConvert);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = CONVERT_PACKAGING_TO_CASH_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "convertPackagingToCash", notes = "Convert a number of packaging to money",
            responseContainer = "BigDecimal")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The conversion done with success"),
            @ApiResponse(code=404, message="Error faced during the conversion process")
    })
    ResponseEntity convertPackagingToCash(
            @ApiParam(name = "packagingId", type = "Long", required = true,
                    value="Id of the concerned Packaging", example = "1")
            @NotNull @PathVariable("packagingId") Long packagingId,
            @ApiParam(name = "numberToConvert", type = "BigDecimal", required = true,
                    value="The number of packaging to convert", example = "1")
            @NotNull @PathVariable("numberToConvert") BigDecimal numberToConvert);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = DELETE_PACKAGING_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deletePackagingById",
            notes = "This method is used to delete a packaging saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Packaging deleted successfully")
    })
    ResponseEntity deletePackagingById(
            @ApiParam(name = "packagingId", type = "Long", required = true,
                    value="Id of the Format to delete", example = "1")
            @NotNull @PathVariable("packagingId") Long packagingId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////













}
