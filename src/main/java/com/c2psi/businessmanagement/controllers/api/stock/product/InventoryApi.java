package com.c2psi.businessmanagement.controllers.api.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.InventoryDto;
import com.c2psi.businessmanagement.dtos.stock.product.InventoryLineDto;
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

import static com.c2psi.businessmanagement.utils.stock.product.InventoryApiConstant.*;

@Validated
@Api(INVENTORY_ENDPOINT)
public interface InventoryApi {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = CREATE_INVENTORY_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveInventory",
            notes = "This method is used to save an inventory in the DB",
            response = InventoryDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Inventory added successfully"),
            @ApiResponse(code=400, message="Object Inventory is not valid during the saving process")
    })
    ResponseEntity saveInventory(
            @ApiParam(name = "invDto", type = "InventoryDto", required = true,
                    value="The JSON object that represent the Inventory to save")
            @Valid @RequestBody InventoryDto invDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = UPDATE_INVENTORY_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "updateInventory",
            notes = "This method is used to update an inventory in the DB",
            response = InventoryDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Inventory updated successfully"),
            @ApiResponse(code=400, message="Object Inventory is not valid during the saving process")
    })
    ResponseEntity updateInventory(
            @ApiParam(name = "invDto", type = "InventoryDto", required = true,
                    value="The JSON object that represent the Inventory to update")
            @Valid @RequestBody InventoryDto invDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_INVENTORY_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findInventoryById", notes = "Search in the DB an inventory by its Id",
            response = InventoryDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The inventory with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findInventoryById(
            @ApiParam(name = "invId", type = "Long", required = true,
                    value="Id of the concerned Inventory", example = "1")
            @NotNull @PathVariable("invId") Long invId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_INVENTORY_BY_CODE_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findInventoryByCodeinPos", notes = "Search in the DB an inventory by its code in Pos",
            response = InventoryDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The inventory with the code precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findInventoryByCodeinPos(
            @ApiParam(name = "invCode", type = "String", required = true,
                    value="Code of the concerned Inventory", example = "code")
            @NotNull @PathVariable("invCode") String invCode,
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_INVENTORY_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllInventoryinPosBetween", notes = "Search in the DB an inventory list by between " +
            "02 dates in Pos",
            responseContainer = "List<InventoryDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The inventory list between 02 dates found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllInventoryinPosBetween(
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

    @GetMapping(value = FIND_PAGE_INVENTORY_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageInventoryinPosBetween", notes = "Search in the DB an inventory list by between " +
            "02 dates in Pos page by page",
            responseContainer = "Page<InventoryDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The inventory page between 02 dates found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageInventoryinPosBetween(
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

    @DeleteMapping(value = DELETE_INVENTORY_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deleteInventoryById",
            notes = "This method is used to delete the Inventory saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Inventory deleted successfully")
    })
    ResponseEntity deleteInventoryById(
            @ApiParam(name = "invId", type = "Long", required = true,
                    value="Id of the Inventory to delete", example = "1")
            @NotNull @PathVariable("invId") Long invId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = CREATE_INVENTORYLINE_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveInventoryLine",
            notes = "This method is used to save an inventory line in the DB",
            response = InventoryLineDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object InventoryLine added successfully"),
            @ApiResponse(code=400, message="Object InventoryLine is not valid during the saving process")
    })
    ResponseEntity saveInventoryLine(
            @ApiParam(name = "invLineDto", type = "InventoryLineDto", required = true,
                    value="The JSON object that represent the Inventory to save")
            @Valid @RequestBody InventoryLineDto invLineDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = UPDATE_INVENTORYLINE_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "updateInventoryLine",
            notes = "This method is used to update an inventory line in the DB",
            response = InventoryLineDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object InventoryLine updated successfully"),
            @ApiResponse(code=400, message="Object InventoryLine is not valid during the saving process")
    })
    ResponseEntity updateInventoryLine(
            @ApiParam(name = "invLineDto", type = "InventoryLineDto", required = true,
                    value="The JSON object that represent the Inventory to save")
            @Valid @RequestBody InventoryLineDto invLineDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_INVENTORYLINE_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findInventoryLineById", notes = "Search in the DB an inventory line by its Id",
            response = InventoryLineDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The inventoryLine with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findInventoryLineById(
            @ApiParam(name = "invLineId", type = "Long", required = true,
                    value="Id of the concerned InventoryLine", example = "1")
            @NotNull @PathVariable("invLineId") Long invLineId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_INVENTORYLINE_BY_ARTICLE_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findInventoryLineByArticleinInv", notes = "Search in the DB an inventory line of an article " +
            "in an inventory",
            response = InventoryLineDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The inventoryLine for the article precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findInventoryLineByArticleinInv(
            @ApiParam(name = "invId", type = "Long", required = true,
                    value="Id of the concerned Inventory", example = "1")
            @NotNull @PathVariable("invId") Long invId,
            @ApiParam(name = "artId", type = "Long", required = true,
                    value="Id of the concerned Article", example = "1")
            @NotNull @PathVariable("artId") Long artId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = DELETE_INVENTORYLINE_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deleteInventoryLineById",
            notes = "This method is used to delete the InventoryLine saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object InventoryLine deleted successfully")
    })
    ResponseEntity deleteInventoryLineById(
            @ApiParam(name = "invLineId", type = "Long", required = true,
                    value="Id of the InventoryLine to delete", example = "1")
            @NotNull @PathVariable("invLineId") Long invLineId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_INVENTORYLINE_OF_INVENTORY_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllInventorylineofInv", notes = "Search in the DB an inventory line list in an inventory ",
            responseContainer = "List<InventoryLineDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The inventory line list in an inventory found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllInventorylineofInv(
            @ApiParam(name = "invId", type = "Long", required = true,
                    value="Id of the concerned Inventory", example = "1")
            @NotNull @PathVariable("invId") Long invId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_INVENTORYLINE_OF_INVENTORY_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageInventorylineofInv", notes = "Search in the DB an inventory line list in an inventory " +
            "page by page",
            responseContainer = "Page<InventoryLineDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The inventory line page in an inventory found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageInventorylineofInv(
            @ApiParam(name = "invId", type = "Long", required = true,
                    value="Id of the concerned Inventory", example = "1")
            @NotNull @PathVariable("invId") Long invId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////




}
