package com.c2psi.businessmanagement.controllers.api.client.command;

import com.c2psi.businessmanagement.dtos.client.command.SaleDto;
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


import static com.c2psi.businessmanagement.utils.client.command.SaleApiConstant.*;

@Validated
@Api(SALE_ENDPOINT)
public interface SaleApi {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = CREATE_SALE_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveSale",
            notes = "This method is used to save a sale in the DB",
            response = SaleDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object SaleDto added successfully"),
            @ApiResponse(code=400, message="Object SaleDto is not valid during the saving process")
    })
    ResponseEntity saveSale(
            @ApiParam(name = "saleDto", type = "SaleDto", required = true,
                    value="The JSON object that represent the SaleDto to save")
            @Valid @RequestBody SaleDto saleDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = UPDATE_SALE_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "updateSale",
            notes = "This method is used to update a sale in the DB",
            response = SaleDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object SaleDto updated successfully"),
            @ApiResponse(code=400, message="Object SaleDto is not valid during the saving process")
    })
    ResponseEntity updateSale(
            @ApiParam(name = "updateSale", type = "SaleDto", required = true,
                    value="The JSON object that represent the SaleDto to update")
            @Valid @RequestBody SaleDto saleDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_SALE_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findSaleById", notes = "Search a Sale by id",
            response = SaleDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The Sale found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findSaleById(
            @ApiParam(name = "saleId", type = "Long", required = true,
                    value="Id of the concerned Sale", example = "1")
            @NotNull @PathVariable("saleId") Long saleId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_SALE_IN_COMMAND_FOR_ARTICLE_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findSaleinCommandaboutArticle", notes = "Search a Sale in Command about an article",
            response = SaleDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The Sale found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findSaleinCommandaboutArticle(
            @ApiParam(name = "cmdId", type = "Long", required = true,
                    value="Id of the concerned Command", example = "1")
            @NotNull @PathVariable("cmdId") Long cmdId,
            @ApiParam(name = "artId", type = "Long", required = true,
                    value="Id of the concerned Article", example = "1")
            @NotNull @PathVariable("artId") Long artId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = DELETE_SALE_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deleteSaleById",
            notes = "This method is used to delete the sale saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Sale deleted successfully")
    })
    ResponseEntity deleteSaleById(
            @ApiParam(name = "saleId", type = "Long", required = true,
                    value="Id of the Sale to delete", example = "1")
            @NotNull @PathVariable("saleId") Long saleId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_SALE_IN_COMMAND_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllSaleofCommand", notes = "find all sale in a command",
            responseContainer = "List<SaleDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The Sale list found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllSaleofCommand(
            @ApiParam(name = "cmdId", type = "Long", required = true,
                    value="Id of the concerned Command", example = "1")
            @NotNull @PathVariable("cmdId") Long cmdId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_SALE_IN_COMMAND_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageSaleofCommand", notes = "find all sale in a command page by page",
            responseContainer = "Page<SaleDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The Sale page found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageSaleofCommand(
            @ApiParam(name = "cmdId", type = "Long", required = true,
                    value="Id of the concerned Command", example = "1")
            @NotNull @PathVariable("cmdId") Long cmdId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_SALE_OF_ARTICLE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllSaleonArticleBetween", notes = "find all sale in a command",
            responseContainer = "List<SaleDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The Sale list found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllSaleonArticleBetween(
            @ApiParam(name = "artId", type = "Long", required = true,
                    value="Id of the concerned Article", example = "1")
            @NotNull @PathVariable("artId") Long artId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_SALE_OF_ARTICLE_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageSaleonArticleBetween", notes = "find all sale in a command page by page",
            responseContainer = "Page<SaleDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The Sale page found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageSaleonArticleBetween(
            @ApiParam(name = "artId", type = "Long", required = true,
                    value="Id of the concerned Article", example = "1")
            @NotNull @PathVariable("artId") Long artId,
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
