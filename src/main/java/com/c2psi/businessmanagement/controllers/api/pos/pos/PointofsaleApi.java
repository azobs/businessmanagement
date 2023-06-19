package com.c2psi.businessmanagement.controllers.api.pos.pos;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCashAccountDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCashOperationDto;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyDto;
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
import java.time.Instant;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

@Validated
@Api(APP_ROOT+"/pos")
public interface PointofsaleApi {

    ///////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/pos/{posId}/{startDate}/{endDate}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "getTurnover", notes = "Compute the turnover of a pointofsale between 02 dates",
            response = BigDecimal.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The turnover computed successfully"),
            @ApiResponse(code=404, message="Error faced during computation of the turnover")
    })
    ResponseEntity getTurnover(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the pointofsale found", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "startDate", type = "Date", value="startDate from which the turnover will be computed")
            @NotNull @PathVariable("startDate") Instant startDate,
            @ApiParam(name = "endDate", type = "Date", value="endDate to which the turnover will be computed")
            @NotNull @PathVariable("endDate") Instant endDate);

    ///////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/pos/employes/{posId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllEmployeofPos", notes = "List of all the employe of a pointofsale",
            responseContainer = "List<UserBMDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The list of employe found successfully"),
            @ApiResponse(code=404, message="Error faced during the founding process")
    })
    ResponseEntity findAllEmployeofPos(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    //////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/pos/providers/{posId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllProviderofPos", notes = "List of all the provider of a pointofsale",
            responseContainer = "List<ProviderDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The list of provider found successfully"),
            @ApiResponse(code=404, message="Error faced during the founding process")
    })
    ResponseEntity findAllProviderofPos(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    //////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/pos/totalcash/{posId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "getTotalCashofPos", notes = "The total cash of a pointofsale",
            response = BigDecimal.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The total cash found successfully"),
            @ApiResponse(code=404, message="Error faced during the computing process")
    })
    ResponseEntity getTotalCashofPos(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    //////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/pos/damage/{posId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "getNumberofDamageofPos", notes = "The total damage for articles in a pointofsale",
            response = BigDecimal.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The total damage article found successfully"),
            @ApiResponse(code=404, message="Error faced during the computing process")
    })
    ResponseEntity getNumberofDamageofPos(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    //////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/pos/damage/{posId}/{artId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "getNumberofDamageofPos", notes = "The total damage of an article in a pointofsale",
            response = BigDecimal.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The total damage of an article found successfully"),
            @ApiResponse(code=404, message="Error faced during the computing process")
    })
    ResponseEntity getNumberofDamageofPos(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "artId", type = "Long", required = true,
                    value="Id of the concerned article", example = "1")
            @NotNull @PathVariable("artId") Long artId);

    //////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/pos/cover/{posId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "getNumberofCapsuleofPos", notes = "The total cover for articles in a pointofsale",
            response = BigDecimal.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The total cover article found successfully"),
            @ApiResponse(code=404, message="Error faced during the computing process")
    })
    ResponseEntity getNumberofCapsuleofPos(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    //////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/pos/cover/{posId}/{artId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "getNumberofCapsuleofPos", notes = "The total cover of an article in a pointofsale",
            response = BigDecimal.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The total cover of an article found successfully"),
            @ApiResponse(code=404, message="Error faced during the computing process")
    })
    ResponseEntity getNumberofCapsuleofPos(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "artId", type = "Long", required = true,
                    value="Id of the concerned article", example = "1")
            @NotNull @PathVariable("artId") Long artId);

    //////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/pos/packaging/{posId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "getNumberofPackagingofPos", notes = "The total packaging in a pointofsale",
            response = BigDecimal.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The total packaging found successfully"),
            @ApiResponse(code=404, message="Error faced during the computing process")
    })
    ResponseEntity getNumberofPackagingofPos(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    //////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/pos/packaging/{posId}/{providerId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "getNumberofPackagingofPos", notes = "The total packaging of a provider in a pointofsale",
            response = BigDecimal.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The total cover of a provider found successfully"),
            @ApiResponse(code=404, message="Error faced during the computing process")
    })
    ResponseEntity getNumberofPackagingofPos(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "providerId", type = "Long", required = true,
                    value="Id of the concerned provider", example = "1")
            @NotNull @PathVariable("providerId") Long providerId);

    //////////////////////////////////////////////////////////////////////////////////////////////////////


    @PostMapping(value = APP_ROOT+"/pos/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create or Persist a pointofsale in the DB",
            notes = "This method is used to save a pointofsale in the DB",
            response = PointofsaleDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Pointofsale added successfully"),
            @ApiResponse(code=400, message="Object Pointofsale is not valid during the saving process")
    })
    ResponseEntity savePointofsale(
            @ApiParam(name = "posDto", type = "PointofsaleDto", required = true,
                    value="The JSON object that represent the pointofsale to save")
            @Valid @RequestBody PointofsaleDto posDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = APP_ROOT+"/pos/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update or Modify a pointofsale in the DB",
            notes = "This method is used to update a pointofsale in the DB",
            response = PointofsaleDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Pointofsale updated successfully"),
            @ApiResponse(code=400, message="Object Pointofsale is not valid during the updating process")
    })
    ResponseEntity updatePointofsale(
            @ApiParam(name = "posDto", type = "PointofsaleDto", required = true,
                    value="The JSON object that represent the updated version of the pointofsale")
            @Valid @RequestBody PointofsaleDto posDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/pos/{posId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPointofsaleById", notes = "Search in the DB a pointofsale by its Id",
            response = PointofsaleDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The Pointofsale with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the founding process")
    })
    ResponseEntity findPointofsaleById(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = APP_ROOT+"/pos/delete/id/{posId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deletePosById",
            notes = "This method is used to delete a pointofsale saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Pointofsale deleted successfully")
    })
    ResponseEntity deletePosById(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the Pointofsale which will be deleted", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = APP_ROOT+"/pos/delete/name/{posName}/{entId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deletePosInEnterpriseByName",
            notes = "This method is used to delete a pointofsale saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Pointofsale deleted successfully")
    })
    ResponseEntity deletePosInEnterpriseByName(
            @ApiParam(name = "posName", type = "String", required = true,
                    value="Name of the Pointofsale which will be deleted", example = "Name")
            @NotNull @NotEmpty @NotBlank @PathVariable("posName") String posName,
            @ApiParam(name = "entId", type = "Long", required = true,
                    value="Id of the Enterprise in which will be found the pointofsale to delete", example = "1")
            @NotNull @PathVariable("entId") Long entId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/pos/currencies/{posId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "listofConvertibleCurrency", notes = "List of all convertible currency with the default " +
            "currency of the pointofsale",
            responseContainer = "List<CurrencyDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The list convertible currency found successfully"),
            @ApiResponse(code=404, message="Error faced during the founding process")
    })
    ResponseEntity listofConvertibleCurrency(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/pos/currency/{posId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "getDefaultCurrency", notes = "The default currency of the pointofsale",
            response = CurrencyDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The default currency found successfully"),
            @ApiResponse(code=404, message="Error faced during the founding process")
    })
    ResponseEntity getDefaultCurrency(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = APP_ROOT+"/pos/cash/operation/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveCashOperation",
            notes = "This method is used to save a cash operation on a pointofsale in the DB",
            response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object PointofsaleCashOperation added successfully"),
            @ApiResponse(code=400, message="Object PointofsaleCashOperation is not valid during the saving process")
    })
    ResponseEntity saveCashOperation(
            @ApiParam(name = "posCashAccountDto", type = "PosCashAccountDto", required = true,
                    value="The JSON object that represent the PosCashAccountDto to save")
            @Valid @RequestBody PosCashAccountDto posCashAccountDto, BindingResult bindingResult1,
            @ApiParam(name = "posCashOperationDto", type = "PosCashOperationDto", required = true,
                    value="The JSON object that represent the posCashOperationDto to save")
            @Valid @RequestBody PosCashOperationDto posCashOperationDto, BindingResult bindingResult2);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = APP_ROOT+"/pos/cash/delete/id/{pcaId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deletePosCashAccountById",
            notes = "This method is used to delete a PosCashAccount of a pointofsale saved in the DB",
            response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object PosCashAccount deleted successfully")
    })
    ResponseEntity deletePosCashAccountById(
            @ApiParam(name = "pcaId", type = "Long", required = true,
                    value="Id of PosCashAccount to delete", example = "1")
            @NotNull @PathVariable("pcaId") Long pcaId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/pos/cash/id/{pcaId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPosCashAccountById", notes = "Find the PosCashAccount by Id",
            response = PosCashAccountDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The default currency found successfully"),
            @ApiResponse(code=404, message="Error faced during the founding process")
    })
    ResponseEntity findPosCashAccountById(
            @ApiParam(name = "pcaId", type = "Long", required = true,
                    value="Id of the concerned poscashaccount", example = "1")
            @NotNull @PathVariable("pcaId") Long pcaId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
