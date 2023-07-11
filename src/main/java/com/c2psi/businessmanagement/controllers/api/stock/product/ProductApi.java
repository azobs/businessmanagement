package com.c2psi.businessmanagement.controllers.api.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.ProductDto;
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

import java.util.Optional;

import static com.c2psi.businessmanagement.utils.stock.product.ProductApiConstant.*;

@Validated
@Api(PRODUCT_ENDPOINT)
public interface ProductApi {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = CREATE_PRODUCT_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveProduct",
            notes = "This method is used to save a product of a pointofsale in the DB",
            response = ProductDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Product added successfully"),
            @ApiResponse(code=400, message="Object Product is not valid during the saving process")
    })
    ResponseEntity saveProduct(
            @ApiParam(name = "prodDto", type = "ProductDto", required = true,
                    value="The JSON object that represent the product to save")
            @Valid @RequestBody ProductDto prodDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = UPDATE_PRODUCT_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "updateProduct",
            notes = "This method is used to update a product of a pointofsale in the DB",
            response = ProductDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Product added successfully"),
            @ApiResponse(code=400, message="Object Product is not valid during the saving process")
    })
    ResponseEntity updateProduct(
            @ApiParam(name = "prodDto", type = "ProductDto", required = true,
                    value="The JSON object that represent the product to update")
            @Valid @RequestBody ProductDto prodDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PRODUCT_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findProductById", notes = "Search in the DB a product by its Id",
            response = ProductDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The product with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the founding process")
    })
    ResponseEntity findProductById(
            @ApiParam(name = "prodId", type = "Long", required = true,
                    value="Id of the concerned product", example = "1")
            @NotNull @PathVariable("prodId") Long prodId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PRODUCT_BY_CODE_IN_POS_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findProductByProductCodeInPos", notes = "Search in the DB a category by its code and posId",
            response = ProductDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The Category with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the founding process")
    })
    ResponseEntity findProductByProductCodeInPos(
            @ApiParam(name = "prodCode", type = "String", required = true,
                    value="product code", example = "000")
            @NotNull @NotEmpty @NotBlank @PathVariable("prodCode") String prodCode,
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the pointofsale of the product", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_PRODUCT_IN_POS,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllProductInPos", notes = "Search all the product in a pointofsale",
            responseContainer = "List<ProductDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The list of product of pointofsale found successfully"),
            @ApiResponse(code=404, message="Error faced during the founding process")
    })
    ResponseEntity findAllProductInPos(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the pointofsale of the product", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_PRODUCT_OF_CATEGORY,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllProductOfCategory", notes = "Search all the product in a category",
            responseContainer = "List<ProductDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The list of product of category found successfully"),
            @ApiResponse(code=404, message="Error faced during the founding process")
    })
    ResponseEntity findAllProductOfCategory(
            @ApiParam(name = "catId", type = "Long", required = true,
                    value="Id of the category of the product", example = "1")
            @NotNull @PathVariable("catId") Long catId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_PRODUCT_IN_POS,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageofProductInPos", notes = "Search all the product in a pointofsale page by page",
            responseContainer = "Page<ProductDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The page of product of pointofsale found successfully"),
            @ApiResponse(code=404, message="Error faced during the founding process")
    })
    ResponseEntity findPageofProductInPos(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the pointofsale of the product", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_PRODUCT_OF_CATEGORY,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageOfProductOfCategory", notes = "Search all products in a category page by page",
            responseContainer = "Page<ProductDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The page of product of category found successfully"),
            @ApiResponse(code=404, message="Error faced during the founding process")
    })
    ResponseEntity findPageOfProductOfCategory(
            @ApiParam(name = "catId", type = "Long", required = true,
                    value="Id of the category of the product", example = "1")
            @NotNull @PathVariable("catId") Long catId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_PRODUCT_CONTAINING,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findProductByProdCodeInPosContaining", notes = "Search all products in a category page by page",
            responseContainer = "Page<ProductDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The page of product of category found successfully"),
            @ApiResponse(code=404, message="Error faced during the founding process")
    })
    ResponseEntity findProductByProdCodeInPosContaining(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the pointofsale of the product", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "sample", type = "String", value="char contain in the product name")
            @NotNull @PathVariable("sample") String sample,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = DELETE_PRODUCT_BY_ID,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deleteProductById",
            notes = "This method is used to delete a product saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Product deleted successfully")
    })
    ResponseEntity deleteProductById(
            @ApiParam(name = "prodId", type = "Long", required = true,
                    value="Id of the product to delete", example = "1")
            @NotNull @PathVariable("prodId") Long prodId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////









}
