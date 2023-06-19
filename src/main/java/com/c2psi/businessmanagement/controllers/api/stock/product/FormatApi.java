package com.c2psi.businessmanagement.controllers.api.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.FormatDto;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Optional;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

@Validated
@Api(APP_ROOT+"/format")
public interface FormatApi {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = APP_ROOT+"/format/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveFormat",
            notes = "This method is used to save a format of a product in the DB",
            response = FormatDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Format added successfully"),
            @ApiResponse(code=400, message="Object Format is not valid during the saving process")
    })
    ResponseEntity saveFormat(
            @ApiParam(name = "formatDto", type = "FormatDto", required = true,
                    value="The JSON object that represent the format to save")
            @Valid @RequestBody FormatDto formatDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = APP_ROOT+"/format/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "updateFormat",
            notes = "This method is used to update a format of a product in the DB",
            response = FormatDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Format updated successfully"),
            @ApiResponse(code=400, message="Object Format is not valid during the updating process")
    })
    ResponseEntity updateFormat(
            @ApiParam(name = "formatDto", type = "FormatDto", required = true,
                    value="The JSON object that represent the format to save")
            @Valid @RequestBody FormatDto formatDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/format/{formatId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findFormatById", notes = "Search in the DB a format by its Id",
            response = FormatDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The Format with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the founding process")
    })
    ResponseEntity findFormatById(
            @ApiParam(name = "formatId", type = "Long", required = true,
                    value="Id of the concerned format", example = "1")
            @NotNull @PathVariable("formatId") Long formatId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/format/pos/{posId}/{formatName}/{formatCapacity}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findFormatInPointofsaleByFullcharacteristic", notes = "Search in the DB a format by its characteristics",
            response = FormatDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The Format with characteristics precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the founding process")
    })
    ResponseEntity findFormatInPointofsaleByFullcharacteristic(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "formatName", type = "String", required = true,
                    value="The format name", example = "name")
            @NotNull @NotEmpty @NotBlank @PathVariable("formatName") String formatName,
            @ApiParam(name = "formatCapacity", type = "BigDecimal", required = true,
                    value="The format capacity", example = "0")
            @NotNull @PathVariable("formatCapacity") BigDecimal formatCapacity);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/format/all/pos/{posId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllFormatInPos", notes = "Search all the format in a pointofsale",
            responseContainer = "List<FormatDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The list of format of pointofsale found successfully"),
            @ApiResponse(code=404, message="Error faced during the founding process")
    })
    ResponseEntity findAllFormatInPos(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the pointofsale of the format", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/format/page/pos/{posId}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageFormatInPos", notes = "Search all the format in a pointofsale",
            responseContainer = "Page<FormatDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The page of format of pointofsale found successfully"),
            @ApiResponse(code=404, message="Error faced during the founding process")
    })
    ResponseEntity findPageFormatInPos(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the pointofsale of the format", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



}
