package com.c2psi.businessmanagement.controllers.api.stock.price;

import com.c2psi.businessmanagement.dtos.stock.price.SpecialPriceDto;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.util.Optional;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

@Validated
@Api(APP_ROOT+"/article/bprice/special")
public interface SpecialPriceApi {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = APP_ROOT+"/article/bprice/special/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveSpecialPrice",
            notes = "This method is used to save a special price based on a base price for articles in the DB",
            response = SpecialPriceDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object SpecialPrice added successfully"),
            @ApiResponse(code=400, message="Object SpecialPrice is not valid during the saving process")
    })
    ResponseEntity saveSpecialPrice(
            @ApiParam(name = "spDto", type = "SpecialPriceDto", required = true,
                    value="The JSON object that represent the SpecialPrice to save")
            @Valid @RequestBody SpecialPriceDto spDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = APP_ROOT+"/article/bprice/special/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "updateSpecialPrice",
            notes = "This method is used to update a special price based on a base price for articles in the DB",
            response = SpecialPriceDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object SpecialPrice updated successfully"),
            @ApiResponse(code=400, message="Object SpecialPrice is not valid during the updating process")
    })
    ResponseEntity updateSpecialPrice(
            @ApiParam(name = "spDto", type = "SpecialPriceDto", required = true,
                    value="The JSON object that represent the SpecialPrice to update")
            @Valid @RequestBody SpecialPriceDto spDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/article/bprice/special/id/{spId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findSpecialPriceById", notes = "Search in the DB a specialprice by its Id",
            response = SpecialPriceDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The specialprice with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findSpecialPriceById(
            @ApiParam(name = "spId", type = "Long", required = true,
                    value="Id of the concerned specialprice", example = "1")
            @NotNull @PathVariable("spId") Long spId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/article/bprice/special/all/{bpId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllSpecialpriceofBaseprice", notes = "Search in the DB all specialprice associate to a baseprice",
            responseContainer = "List<SpecialPriceDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The specialprice list associate with the baseprice found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllSpecialpriceofBaseprice(
            @ApiParam(name = "bpId", type = "Long", required = true,
                    value="Id of the concerned baseprice", example = "1")
            @NotNull @PathVariable("bpId") Long bpId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/article/bprice/special/page/{bpId}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageSpecialpriceofBaseprice", notes = "Search in the DB all specialprice associate to a baseprice page by page",
            responseContainer = "Page<SpecialPriceDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The specialprice page associate with the baseprice found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageSpecialpriceofBaseprice(
            @ApiParam(name = "bpId", type = "Long", required = true,
                    value="Id of the concerned baseprice", example = "1")
            @NotNull @PathVariable("bpId") Long bpId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/article/bprice/special/article/all/{artId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllSpecialpriceofArticle", notes = "Search in the DB all specialprice associate to a article",
            responseContainer = "List<SpecialPriceDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The specialprice list associate with the article found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllSpecialpriceofArticle(
            @ApiParam(name = "artId", type = "Long", required = true,
                    value="Id of the concerned article", example = "1")
            @NotNull @PathVariable("artId") Long artId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/article/bprice/special/article/page/{artId}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageSpecialpriceofArticle", notes = "Search in the DB all specialprice associate to a article page by page",
            responseContainer = "Page<SpecialPriceDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The specialprice page associate with the article found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageSpecialpriceofArticle(
            @ApiParam(name = "artId", type = "Long", required = true,
                    value="Id of the concerned article", example = "1")
            @NotNull @PathVariable("artId") Long artId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = APP_ROOT+"/article/bprice/special/delete/id/{spId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deleteSpecialPriceById",
            notes = "This method is used to delete a specialprice saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object SpecialPrice deleted successfully")
    })
    ResponseEntity deleteSpecialPriceById(
            @ApiParam(name = "spId", type = "Long", required = true,
                    value="Id of the specialprice to delete", example = "1")
            @NotNull @PathVariable("spId") Long spId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////






}
