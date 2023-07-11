package com.c2psi.businessmanagement.controllers.api.pos.loading;

import com.c2psi.businessmanagement.dtos.pos.loading.LoadingDetailsDto;
import com.c2psi.businessmanagement.dtos.pos.loading.LoadingDto;
import com.c2psi.businessmanagement.dtos.pos.loading.PackingDetailsDto;
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

import static com.c2psi.businessmanagement.utils.pos.loading.LoadingApiConstant.*;

@Validated
@Api(LOADING_ENDPOINT)
public interface LoadingApi {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = CREATE_LOADING_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveLoading",
            notes = "This method is used to save a Loading in the DB",
            response = LoadingDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Loading added successfully"),
            @ApiResponse(code=400, message="Object Loading is not valid during the saving process")
    })
    ResponseEntity saveLoading(
            @ApiParam(name = "loadingDto", type = "LoadingDto", required = true,
                    value="The JSON object that represent the LoadingDto to save")
            @Valid @RequestBody LoadingDto loadingDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = UPDATE_LOADING_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "updateLoading",
            notes = "This method is used to update a Loading in the DB",
            response = LoadingDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Loading updated successfully"),
            @ApiResponse(code=400, message="Object Loading is not valid during the saving process")
    })
    ResponseEntity updateLoading(
            @ApiParam(name = "loadingDto", type = "LoadingDto", required = true,
                    value="The JSON object that represent the LoadingDto to save")
            @Valid @RequestBody LoadingDto loadingDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_LOADIND_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findLoadingById", notes = "Search a Loading by id",
            response = LoadingDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The Loading found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findLoadingById(
            @ApiParam(name = "loadingId", type = "Long", required = true,
                    value="Id of the concerned Loading", example = "1")
            @NotNull @PathVariable("loadingId") Long loadingId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_LOADING_BY_CODE_IN_POS_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findLoadingByCodeinPos", notes = "Search a Loading by code in Pos",
            response = LoadingDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The Loading found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findLoadingByCodeinPos(
            @ApiParam(name = "loadingCode", type = "String", required = true,
                    value="Code of the concerned Loading", example = "code")
            @NotNull @PathVariable("loadingCode") String loadingCode,
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_LOADING_IN_POS_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllLoadinginPosBetween", notes = "Search all Loading in Pos between",
            responseContainer = "List<LoadingDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The Loading list found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllLoadinginPosBetween(
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

    @GetMapping(value = FIND_PAGE_LOADING_IN_POS_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageLoadinginPosBetween", notes = "Search all Loading in Pos between page by page",
            responseContainer = "Page<LoadingDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The Loading page found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageLoadinginPosBetween(
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

    @GetMapping(value = FIND_ALL_LOADING_OF_USERBMMANAGER_IN_POS_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllLoadingofUserbmManagerinPosBetween", notes = "Search all Loading in Pos between",
            responseContainer = "List<LoadingDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The Loading list found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllLoadingofUserbmManagerinPosBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned UserBM Manager", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_LOADING_OF_USERBMMANAGER_IN_POS_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageLoadingofUserbmManagerinPosBetween", notes = "Search all Loading in Pos between",
            responseContainer = "Page<LoadingDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The Loading page found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageLoadingofUserbmManagerinPosBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned UserBM Manager", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_LOADING_OF_USERBMSALER_IN_POS_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllLoadingofUserbmSalerinPosBetween", notes = "Search all Loading in Pos between",
            responseContainer = "List<LoadingDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The Loading list found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllLoadingofUserbmSalerinPosBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned UserBM Saler", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_LOADING_OF_USERBMSALER_IN_POS_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageLoadingofUserbmSalerinPosBetween", notes = "Search all Loading in Pos between",
            responseContainer = "Page<LoadingDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The Loading page found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageLoadingofUserbmSalerinPosBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned UserBM Saler", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_LOADING_OF_USERBMMANAGER_AND_SALER_IN_POS_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllLoadingofUserbmManagerandSalerinPosBetween", notes = "Search all Loading in Pos between",
            responseContainer = "List<LoadingDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The Loading list found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllLoadingofUserbmManagerandSalerinPosBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "userbmId_m", type = "Long", required = true,
                    value="Id of the concerned UserBM Manager", example = "1")
            @NotNull @PathVariable("userbmId_m") Long userbmId_m,
            @ApiParam(name = "userbmId_s", type = "Long", required = true,
                    value="Id of the concerned UserBM Saler", example = "1")
            @NotNull @PathVariable("userbmId_s") Long userbmId_s,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_LOADING_OF_USERBMMANAGER_AND_SALER_IN_POS_BETWEEN_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageLoadingofUserbmManagerandSalerinPosBetween", notes = "Search all Loading in Pos between",
            responseContainer = "Page<LoadingDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The Loading page found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageLoadingofUserbmManagerandSalerinPosBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "userbmId_m", type = "Long", required = true,
                    value="Id of the concerned UserBM Manager", example = "1")
            @NotNull @PathVariable("userbmId_m") Long userbmId_m,
            @ApiParam(name = "userbmId_s", type = "Long", required = true,
                    value="Id of the concerned UserBM Saler", example = "1")
            @NotNull @PathVariable("userbmId_s") Long userbmId_s,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = DELETE_LOADING_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deleteLoadingById",
            notes = "This method is used to delete the loading saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Loading deleted successfully")
    })
    ResponseEntity deleteLoadingById(
            @ApiParam(name = "loadingId", type = "Long", required = true,
                    value="Id of the Loading to delete", example = "1")
            @NotNull @PathVariable("loadingId") Long loadingId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = CREATE_LOADINGDETAILS_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveLoadingDetails",
            notes = "This method is used to save a LoadingDetails in the DB",
            response = LoadingDetailsDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object LoadingDetails added successfully"),
            @ApiResponse(code=400, message="Object LoadingDetails is not valid during the saving process")
    })
    ResponseEntity saveLoadingDetails(
            @ApiParam(name = "ldDto", type = "LoadingDetailsDto", required = true,
                    value="The JSON object that represent the LoadingDetailsDto to save")
            @Valid @RequestBody LoadingDetailsDto ldDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = UPDATE_LOADINGDETAILS_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "updateLoadingDetails",
            notes = "This method is used to update a LoadingDetails in the DB",
            response = LoadingDetailsDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object LoadingDetails updated successfully"),
            @ApiResponse(code=400, message="Object LoadingDetails is not valid during the saving process")
    })
    ResponseEntity updateLoadingDetails(
            @ApiParam(name = "ldDto", type = "LoadingDetailsDto", required = true,
                    value="The JSON object that represent the LoadingDetailsDto to save")
            @Valid @RequestBody LoadingDetailsDto ldDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_LOADINGDETAILS_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findLoadingDetailsById", notes = "Search a LoadingDetails by id",
            response = LoadingDetailsDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The LoadingDetails found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findLoadingDetailsById(
            @ApiParam(name = "ldId", type = "Long", required = true,
                    value="Id of the concerned Loading Details", example = "1")
            @NotNull @PathVariable("ldId") Long ldId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_LOADINGDETAILS_OF_ARTICLE_IN_LOADING_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findLoadingDetailsofArticleinLoading", notes = "Search a LoadingDetails for article",
            response = LoadingDetailsDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The LoadingDetails found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findLoadingDetailsofArticleinLoading(
            @ApiParam(name = "artId", type = "Long", required = true,
                    value="Id of the concerned Article", example = "1")
            @NotNull @PathVariable("artId") Long artId,
            @ApiParam(name = "loadingId", type = "Long", required = true,
                    value="Id of the concerned Loading", example = "1")
            @NotNull @PathVariable("loadingId") Long loadingId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_LOADINGDETAILS_IN_LOADING_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllLoadingDetailsinLoading", notes = "Search a LoadingDetails list of loading ",
            responseContainer = "List<LoadingDetailsDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The LoadingDetails list found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllLoadingDetailsinLoading(
            @ApiParam(name = "loadingId", type = "Long", required = true,
                    value="Id of the concerned Loading", example = "1")
            @NotNull @PathVariable("loadingId") Long loadingId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_LOADINGDETAILS_IN_LOADING_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageLoadingDetailsinLoading", notes = "Search a LoadingDetails page of loading ",
            responseContainer = "Page<LoadingDetailsDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The LoadingDetails page found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageLoadingDetailsinLoading(
            @ApiParam(name = "loadingId", type = "Long", required = true,
                    value="Id of the concerned Loading", example = "1")
            @NotNull @PathVariable("loadingId") Long loadingId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = DELETE_LOADINGDETAILS_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deleteLoadingDetailsById",
            notes = "This method is used to delete the loading details saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Loading details deleted successfully")
    })
    ResponseEntity deleteLoadingDetailsById(
            @ApiParam(name = "ldId", type = "Long", required = true,
                    value="Id of the LoadingDetails to delete", example = "1")
            @NotNull @PathVariable("ldId") Long ldId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = CREATE_PACKINGDETAILS_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "savePackingDetails",
            notes = "This method is used to save a LoadingDetails in the DB",
            response = PackingDetailsDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object PackingDetailsDto added successfully"),
            @ApiResponse(code=400, message="Object PackingDetailsDto is not valid during the saving process")
    })
    ResponseEntity savePackingDetails(
            @ApiParam(name = "pdDto", type = "PackingDetailsDto", required = true,
                    value="The JSON object that represent the PackingDetailsDto to save")
            @Valid @RequestBody PackingDetailsDto pdDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = UPDATE_PACKINGDETAILS_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "updatePackingDetails",
            notes = "This method is used to update a LoadingDetails in the DB",
            response = PackingDetailsDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object PackingDetailsDto added successfully"),
            @ApiResponse(code=400, message="Object PackingDetailsDto is not valid during the saving process")
    })
    ResponseEntity updatePackingDetails(
            @ApiParam(name = "pdDto", type = "PackingDetailsDto", required = true,
                    value="The JSON object that represent the PackingDetailsDto to save")
            @Valid @RequestBody PackingDetailsDto pdDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PACKINGDETAILS_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPackingDetailsById", notes = "Search a PackingDetails by id",
            response = PackingDetailsDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The PackingDetails found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPackingDetailsById(
            @ApiParam(name = "pdId", type = "Long", required = true,
                    value="Id of the concerned Packing Details", example = "1")
            @NotNull @PathVariable("pdId") Long pdId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PACKINGDETAILS_OF_ARTICLE_IN_LOADING_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPackingDetailsofArticleinLoading", notes = "Search a PackingDetails by id",
            response = PackingDetailsDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The PackingDetails found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPackingDetailsofArticleinLoading(
            @ApiParam(name = "packagingId", type = "Long", required = true,
                    value="Id of the concerned Packaging", example = "1")
            @NotNull @PathVariable("packagingId") Long packagingId,
            @ApiParam(name = "loadingId", type = "Long", required = true,
                    value="Id of the concerned Loading", example = "1")
            @NotNull @PathVariable("loadingId") Long loadingId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_ALL_PACKINGDETAILS_IN_LOADING_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllPackingDetailsinLoading", notes = "Search a PackingDetails list of loading ",
            responseContainer = "List<PackingDetailsDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The PackingDetails list found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllPackingDetailsinLoading(
            @ApiParam(name = "loadingId", type = "Long", required = true,
                    value="Id of the concerned Loading", example = "1")
            @NotNull @PathVariable("loadingId") Long loadingId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = FIND_PAGE_PACKINGDETAILS_IN_LOADING_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPagePackingDetailsinLoading", notes = "Search a PackingDetails page of loading ",
            responseContainer = "Page<PackingDetailsDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The PackingDetails page found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPagePackingDetailsinLoading(
            @ApiParam(name = "loadingId", type = "Long", required = true,
                    value="Id of the concerned Loading", example = "1")
            @NotNull @PathVariable("loadingId") Long loadingId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = DELETE_PACKINGDETAILS_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deletePackingDetailsById",
            notes = "This method is used to delete the packing details saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Packing details deleted successfully")
    })
    ResponseEntity deletePackingDetailsById(
            @ApiParam(name = "pdId", type = "Long", required = true,
                    value="Id of the PackingDetails to delete", example = "1")
            @NotNull @PathVariable("pdId") Long pdId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



}
