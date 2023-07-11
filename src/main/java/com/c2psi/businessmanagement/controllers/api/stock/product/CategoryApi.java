package com.c2psi.businessmanagement.controllers.api.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.CategoryDto;
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

import static com.c2psi.businessmanagement.utils.stock.product.CategoryApiConstant.*;

@Validated
@Api(CATEGORY_ENDPOINT)
public interface CategoryApi {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = CREATE_CATEGORY_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create or Persist a category of a Pos in the DB",
            notes = "This method is used to save a category of a pointofsale in the DB",
            response = CategoryDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Category added successfully"),
            @ApiResponse(code=400, message="Object Category is not valid during the saving process")
    })
    ResponseEntity saveCategory(
            @ApiParam(name = "catDto", type = "CategoryDto", required = true,
                    value="The JSON object that represent the category to save")
            @Valid @RequestBody CategoryDto catDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = UPDATE_CATEGORY_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update or Modify a category sent",
            notes = "This method is used to update a category of a pointofsale in the DB",
            response = CategoryDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Category updated successfully"),
            @ApiResponse(code=400, message="Object Category is not valid during the updating process")
    })
    ResponseEntity updateCategory(
            @ApiParam(name = "catDto", type = "CategoryDto", required = true,
                    value="The JSON object that represent the category to update")
            @Valid @RequestBody CategoryDto catDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_CATEGORY_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findCategoryById", notes = "Search in the DB a category by its Id",
            response = CategoryDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The Category with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the founding process")
    })
    ResponseEntity findCategoryById(
            @ApiParam(name = "catId", type = "Long", required = true,
                    value="Id of the concerned category", example = "1")
            @NotNull @PathVariable("catId") Long catId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ASCENDANT_CATEGORY_OF_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAscandantCategoryof", notes = "List of all category parents of the catageory sent by " +
            "its Id",
            responseContainer = "List<CategoryDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The list category parents found successfully"),
            @ApiResponse(code=404, message="Error faced during the founding process")
    })
    ResponseEntity findAscandantCategoryof(
            @ApiParam(name = "catId", type = "Long", required = true,
                    value="Id of the concerned category", example = "1")
            @NotNull @PathVariable("catId") Long catId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_CATEGORY_PARENT_OF_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findCategoryParentof", notes = "List of all category parents of the catageory sent by " +
            "its Id",
            response = CategoryDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The category parent found successfully"),
            @ApiResponse(code=404, message="Error faced during the founding process")
    })
    ResponseEntity findCategoryParentof(
            @ApiParam(name = "catId", type = "Long", required = true,
                    value="Id of the concerned category", example = "1")
            @NotNull @PathVariable("catId") Long catId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_CHILD_CATEGORY_OF_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findChildCategoryOf", notes = "List of all category child of the catageory sent by " +
            "its Id",
            responseContainer = "List<CategoryDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The list category parents found successfully"),
            @ApiResponse(code=404, message="Error faced during the founding process")
    })
    ResponseEntity findChildCategoryOf(
            @ApiParam(name = "catId", type = "Long", required = true,
                    value="Id of the concerned category", example = "1")
            @NotNull @PathVariable("catId") Long catId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_CATEGORY_IN_POS_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllCategoryInPointofsale", notes = "List of all category in the pointofsale sent by " +
            "its Id",
            responseContainer = "List<CategoryDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The list category in pointofsale found successfully"),
            @ApiResponse(code=404, message="Error faced during the founding process")
    })
    ResponseEntity findAllCategoryInPointofsale(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_CATEGORY_IN_POS_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCategoryInPointofsale", notes = "List of all category in the pointofsale sent by " +
            "its Id",
            responseContainer = "Page<CategoryDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The list category in pointofsale found successfully"),
            @ApiResponse(code=404, message="Error faced during the founding process")
    })
    ResponseEntity findPageCategoryInPointofsale(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_CATEGORY_CONTANING_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageCategoryInPointofsale", notes = "List of all category in the pointofsale sent by " +
            "its Id",
            responseContainer = "Page<CategoryDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The list category in pointofsale found successfully"),
            @ApiResponse(code=404, message="Error faced during the founding process")
    })
    ResponseEntity findPageByCatNameInPosContaining(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "sample", type = "String", value="char contain in the category name")
            @NotNull @PathVariable("sample") String sample,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = DELETE_CATEGORY_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deleteCategoryById",
            notes = "This method is used to delete a category saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Category deleted successfully")
    })
    ResponseEntity deleteCategoryById(
            @ApiParam(name = "catId", type = "Long", required = true,
                    value="Id of the Category to delete", example = "1")
            @NotNull @PathVariable("catId") Long catId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = DELETE_CATEGORY_BY_CODE_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deleteCategoryByCatCode",
            notes = "This method is used to delete a pointofsale saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Pointofsale deleted successfully")
    })
    ResponseEntity deleteCategoryByCatCode(
            @ApiParam(name = "catCode", type = "String", required = true,
                    value="Code of the Category which will be deleted", example = "Code")
            @NotNull @NotEmpty @NotBlank @PathVariable("catCode") String catCode,
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the Pointofsale in which will be found the category to delete", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////






}
