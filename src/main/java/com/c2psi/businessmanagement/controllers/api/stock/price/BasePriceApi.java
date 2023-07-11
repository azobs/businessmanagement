package com.c2psi.businessmanagement.controllers.api.stock.price;

import com.c2psi.businessmanagement.dtos.stock.price.BasePriceDto;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.c2psi.businessmanagement.utils.stock.price.BasePriceApiConstant.*;

@Validated
@Api(BASEPRICE_ENDPOINT)
public interface BasePriceApi {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = CREATE_BASEPRICE_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveBasePrice",
            notes = "This method is used to save a base price for articles in the DB",
            response = BasePriceDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object BasePrice added successfully"),
            @ApiResponse(code=400, message="Object BasePrice is not valid during the saving process")
    })
    ResponseEntity saveBasePrice(
            @ApiParam(name = "bpDto", type = "BasePriceDto", required = true,
                    value="The JSON object that represent the BasePrice to save")
            @Valid @RequestBody BasePriceDto bpDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = UPDATE_BASEPRICE_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "updateBasePrice",
            notes = "This method is used to update a base price for articles in the DB",
            response = BasePriceDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object BasePrice updated successfully"),
            @ApiResponse(code=400, message="Object BasePrice is not valid during the saving process")
    })
    ResponseEntity updateBasePrice(
            @ApiParam(name = "bpDto", type = "BasePriceDto", required = true,
                    value="The JSON object that represent the BasePrice to update")
            @Valid @RequestBody BasePriceDto bpDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_BASEPRICE_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findBasePriceById", notes = "Search in the DB a baseprice by its Id",
            response = BasePriceDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The baseprice with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findBasePriceById(
            @ApiParam(name = "bpId", type = "Long", required = true,
                    value="Id of the concerned baseprice", example = "1")
            @NotNull @PathVariable("bpId") Long bpId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = DELETE_BASEPRICE_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deleteBasePriceById",
            notes = "This method is used to delete a baseprice saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object BasePrice deleted successfully")
    })
    ResponseEntity deleteBasePriceById(
            @ApiParam(name = "bpId", type = "Long", required = true,
                    value="Id of the baseprice to delete", example = "1")
            @NotNull @PathVariable("bpId") Long bpId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
