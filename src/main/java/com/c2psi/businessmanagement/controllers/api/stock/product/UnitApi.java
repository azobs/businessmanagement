package com.c2psi.businessmanagement.controllers.api.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.UnitConversionDto;
import com.c2psi.businessmanagement.dtos.stock.product.UnitDto;
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
@Api(APP_ROOT+"/unit")
public interface UnitApi {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = APP_ROOT+"/unit/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveUnit",
            notes = "This method is used to save a unit in the DB",
            response = UnitDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Unit added successfully"),
            @ApiResponse(code=400, message="Object Unit is not valid during the saving process")
    })
    ResponseEntity saveUnit(
            @ApiParam(name = "unitDto", type = "UnitDto", required = true,
                    value="The JSON object that represent the unit to save")
            @Valid @RequestBody UnitDto unitDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = APP_ROOT+"/unit/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "updateUnit",
            notes = "This method is used to update a unit in the DB",
            response = UnitDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Unit updated successfully"),
            @ApiResponse(code=400, message="Object Unit is not valid during the saving process")
    })
    ResponseEntity updateUnit(
            @ApiParam(name = "unitDto", type = "UnitDto", required = true,
                    value="The JSON object that represent the unit to update")
            @Valid @RequestBody UnitDto unitDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/unit/id/{unitId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findUnitById", notes = "Search in the DB a Unit by its Id",
            response = UnitDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The Unit with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the founding process")
    })
    ResponseEntity findUnitById(
            @ApiParam(name = "unitId", type = "Long", required = true,
                    value="Id of the concerned unit", example = "1")
            @NotNull @PathVariable("unitId") Long unitId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/unit/name/{unitName}/{posId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findUnitByUnitnameAndPos", notes = "Search in the DB a Unit by its name",
            response = UnitDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The Unit with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the founding process")
    })
    ResponseEntity findUnitByUnitnameAndPos(
            @ApiParam(name = "unitName", type = "String", required = true,
                    value="The name of the finding unit", example = "name")
            @NotNull @NotEmpty @NotBlank @PathVariable("unitName") String unitName,
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/unit/all/pos/{posId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllUnitInPos", notes = "Search the unit list in the DB ",
            responseContainer = "List<UnitDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The list of Unit found successfully"),
            @ApiResponse(code=404, message="Error faced during the founding process")
    })
    ResponseEntity findAllUnitInPos(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/unit/page/pos/{posId}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageUnitInPos", notes = "Search the unit list in the DB ",
            responseContainer = "Page<UnitDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The page of Unit found successfully"),
            @ApiResponse(code=404, message="Error faced during the founding process")
    })
    ResponseEntity findPageUnitInPos(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = APP_ROOT+"/unit/delete/id/{unitId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deleteUnitById",
            notes = "This method is used to delete an unit saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Unit deleted successfully")
    })
    ResponseEntity deleteUnitById(
            @ApiParam(name = "unitId", type = "Long", required = true,
                    value="Id of the unit to delete", example = "1")
            @NotNull @PathVariable("unitId") Long unitId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = APP_ROOT+"/unitconversion/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveUnitConversion",
            notes = "This method is used to save a unit conversion rule in the DB",
            response = UnitConversionDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object UnitConversion added successfully"),
            @ApiResponse(code=400, message="Object UnitConversion is not valid during the saving process")
    })
    ResponseEntity saveUnitConversion(
            @ApiParam(name = "unitconvDto", type = "UnitConversionDto", required = true,
                    value="The JSON object that represent the unit conversion to save")
            @Valid @RequestBody UnitConversionDto unitconvDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = APP_ROOT+"/unitconversion/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "updateUnitConversion",
            notes = "This method is used to update a unit conversion rule in the DB",
            response = UnitConversionDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object UnitConversion updated successfully"),
            @ApiResponse(code=400, message="Object UnitConversion is not valid during the saving process")
    })
    ResponseEntity updateUnitConversion(
            @ApiParam(name = "unitconvDto", type = "UnitConversionDto", required = true,
                    value="The JSON object that represent the unit conversion to update")
            @Valid @RequestBody UnitConversionDto unitconvDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/unitconversion/convert/{quantity}/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "convertTo", notes = "convert a quantity from one unit to another",
            response = BigDecimal.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The quantity in the new Unit"),
            @ApiResponse(code=404, message="Error faced during the converting process")
    })
    ResponseEntity convertTo(
            @ApiParam(name = "quantity", type = "BigDecimal", required = true,
                    value="The quantity to convert", example = "1")
            @NotNull @PathVariable("quantity") BigDecimal quantity,
            @ApiParam(name = "from", type = "Long", required = true,
                    value="The id of the unit source")
            @NotNull @PathVariable("from") Long from,
            @ApiParam(name = "to", type = "Long", required = true,
                    value="The id of the unit destination")
            @NotNull @PathVariable("to") Long to);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/unitconversion/id/{unitconvId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findUnitConversionById", notes = "find a conversion rule by its id",
            response = UnitConversionDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The conversion rule in the new Unit"),
            @ApiResponse(code=404, message="Error faced during the converting process")
    })
    ResponseEntity findUnitConversionById(
            @ApiParam(name = "unitconvId", type = "Long", required = true,
                    value="The Unit conversion Id", example = "1")
            @NotNull @PathVariable("unitconvId") Long unitconvId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/unitconversion/rule/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findConversionRuleBetween", notes = "find a conversion rule ",
            response = UnitConversionDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The conversion rule in the new Unit"),
            @ApiResponse(code=404, message="Error faced during the converting process")
    })
    ResponseEntity findConversionRuleBetween(
            @ApiParam(name = "from", type = "Long", required = true,
                    value="The source Unit conversion", example = "1")
            @NotNull @PathVariable("from") Long from,
            @ApiParam(name = "to", type = "Long", required = true,
                    value="The source Unit conversion", example = "1")
            @NotNull @PathVariable("to") Long to);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/unitconversion/rules/{from}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "listofConvertibleUnitWith", notes = "Search the unit list in the DB ",
            responseContainer = "List<UnitDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The list of Unit found successfully"),
            @ApiResponse(code=404, message="Error faced during the founding process")
    })
    ResponseEntity listofConvertibleUnitWith(
            @ApiParam(name = "from", type = "Long", required = true,
                    value="Id of the concerned pointofsale", example = "1")
            @NotNull @PathVariable("from") Long from);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = APP_ROOT+"/unitconversion/delete/id/{unitconvId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deleteUnitById",
            notes = "This method is used to delete an unit saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Unit deleted successfully")
    })
    ResponseEntity deleteUnitConversionById(
            @ApiParam(name = "unitconvId", type = "Long", required = true,
                    value="Id of the unit to delete", example = "1")
            @NotNull @PathVariable("unitconvId") Long unitconvId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////




















}
