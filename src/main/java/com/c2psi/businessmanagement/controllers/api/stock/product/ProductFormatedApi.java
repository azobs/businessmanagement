package com.c2psi.businessmanagement.controllers.api.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.ProductFormatedDto;
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
@Api(APP_ROOT+"/productformated")
public interface ProductFormatedApi {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = APP_ROOT+"/productformated/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveProductFormated",
            notes = "This method is used to save a productformated of a pointofsale in the DB",
            response = ProductFormatedDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object ProductFormated added successfully"),
            @ApiResponse(code=400, message="Object ProductFormated is not valid during the saving process")
    })
    ResponseEntity saveProductFormated(
            @ApiParam(name = "pfDto", type = "ProductFormatedDto", required = true,
                    value="The JSON object that represent the product formated to save")
            @Valid @RequestBody ProductFormatedDto pfDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = APP_ROOT+"/productformated/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "updateProductFormated",
            notes = "This method is used to update a productformated of a pointofsale in the DB",
            response = ProductFormatedDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object ProductFormated updated successfully"),
            @ApiResponse(code=400, message="Object ProductFormated is not valid during the updating process")
    })
    ResponseEntity updateProductFormated(
            @ApiParam(name = "pfDto", type = "ProductFormatedDto", required = true,
                    value="The JSON object that represent the product formated to save")
            @Valid @RequestBody ProductFormatedDto pfDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/productformated/id/{pfId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findProductFormatedById", notes = "Search in the DB a productformated by its Id",
            response = ProductFormatedDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The productformated with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the founding process")
    })
    ResponseEntity findProductFormatedById(
            @ApiParam(name = "pfId", type = "Long", required = true,
                    value="Id of the concerned productformated", example = "1")
            @NotNull @PathVariable("pfId") Long pfId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/productformated/pf/{productId}/{formatId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findProductFormatedByProductIdAndFormatId", notes = "Search in the DB a productformated by its Id",
            response = ProductFormatedDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The productformated with the characteristic precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the founding process")
    })
    ResponseEntity findProductFormatedByProductIdAndFormatId(
            @ApiParam(name = "prodId", type = "Long", required = true,
                    value="Id of the concerned product", example = "1")
            @NotNull @PathVariable("prodId") Long prodId,
            @ApiParam(name = "formatId", type = "Long", required = true,
                    value="Id of the concerned Format", example = "1")
            @NotNull @PathVariable("formatId") Long formatId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/productformated/all/{posId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllProductFormatedInPos", notes = "Search all productformated in pointofsale",
            responseContainer = "List<ProductFormatedDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The productformated list of pointofsale found successfully"),
            @ApiResponse(code=404, message="Error faced during the founding process")
    })
    ResponseEntity findAllProductFormatedInPos(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/productformated/page/{posId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageProductFormatedInPos", notes = "Search all productformated in pointofsale page by page",
            responseContainer = "Page<ProductFormatedDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The productformated page of pointofsale found successfully"),
            @ApiResponse(code=404, message="Error faced during the founding process")
    })
    ResponseEntity findPageProductFormatedInPos(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = APP_ROOT+"/productformated/delete/id/{pfId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deleteProductFormatedById",
            notes = "This method is used to delete a productformated saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object ProductFormated deleted successfully")
    })
    ResponseEntity deleteProductFormatedById(
            @ApiParam(name = "pfId", type = "Long", required = true,
                    value="Id of the productformated to delete", example = "1")
            @NotNull @PathVariable("pfId") Long pfId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////






}
