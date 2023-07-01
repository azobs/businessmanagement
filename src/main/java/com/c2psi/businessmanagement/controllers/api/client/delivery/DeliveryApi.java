package com.c2psi.businessmanagement.controllers.api.client.delivery;

import com.c2psi.businessmanagement.Enumerations.DeliveryState;
import com.c2psi.businessmanagement.dtos.client.delivery.DeliveryDetailsDto;
import com.c2psi.businessmanagement.dtos.client.delivery.DeliveryDto;
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

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

@Validated
@Api(APP_ROOT+"/delivery")
public interface DeliveryApi {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = APP_ROOT+"/delivery/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveDelivery",
            notes = "This method is used to save a delivery in the DB",
            response = DeliveryDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object DeliveryDto added successfully"),
            @ApiResponse(code=400, message="Object DeliveryDto is not valid during the saving process")
    })
    ResponseEntity saveDelivery(
            @ApiParam(name = "deliveryDto", type = "DeliveryDto", required = true,
                    value="The JSON object that represent the DeliveryDto to save")
            @Valid @RequestBody DeliveryDto deliveryDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = APP_ROOT+"/delivery/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "updateDelivery",
            notes = "This method is used to update a delivery in the DB",
            response = DeliveryDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object DeliveryDto updated successfully"),
            @ApiResponse(code=400, message="Object DeliveryDto is not valid during the saving process")
    })
    ResponseEntity updateDelivery(
            @ApiParam(name = "deliveryDto", type = "DeliveryDto", required = true,
                    value="The JSON object that represent the DeliveryDto to save")
            @Valid @RequestBody DeliveryDto deliveryDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/delivery/id/{deliveryId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findDeliveryById", notes = "Search a Delivery by id",
            response = DeliveryDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The Delivery found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findDeliveryById(
            @ApiParam(name = "deliveryId", type = "Long", required = true,
                    value="Id of the concerned Delivery", example = "1")
            @NotNull @PathVariable("deliveryId") Long deliveryId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/delivery/pos/code/{deliveryCode}/{posId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findDeliveryByCodeinPos", notes = "Search a Delivery by code in Pointofsale",
            response = DeliveryDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The Delivery found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findDeliveryByCodeinPos(
            @ApiParam(name = "deliveryCode", type = "String", required = true,
                    value="Id of the concerned Delivery", example = "code")
            @NotNull @PathVariable("deliveryCode") String deliveryCode,
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = APP_ROOT+"/delivery/delete/id/{deliveryId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deleteDeliveryById",
            notes = "This method is used to delete the delivery saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object delivery deleted successfully")
    })
    ResponseEntity deleteDeliveryById(
            @ApiParam(name = "deliveryId", type = "Long", required = true,
                    value="Id of the Delivery to delete", example = "1")
            @NotNull @PathVariable("deliveryId") Long deliveryId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/delivery/pos/all/{posId}/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllDeliveryinPosBetween", notes = "Search all Delivery by in Pointofsale between 02 dates",
            responseContainer = "List<DeliveryDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The Delivery list found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllDeliveryinPosBetween(
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

    @GetMapping(value = APP_ROOT+"/delivery/pos/page/{posId}/{from}/{to}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageDeliveryinPosBetween", notes = "Search all Delivery by in Pointofsale between 02 dates" +
            "page by page",
            responseContainer = "Page<DeliveryDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The Delivery page found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageDeliveryinPosBetween(
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

    @GetMapping(value = APP_ROOT+"/delivery/pos/state/all/{deliveryState}/{posId}/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllDeliveryinPoswithStateBetween", notes = "Search all Delivery by in Pointofsale between 02 dates",
            responseContainer = "List<DeliveryDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The Delivery list found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllDeliveryinPoswithStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "deliveryState", type = "DeliveryState", required = true,
                    value="DeliveryState found", example = "Delivery")
            @NotNull @PathVariable("deliveryState") DeliveryState deliveryState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/delivery/pos/state/page/{deliveryState}/{posId}/{from}/{to}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageDeliveryinPoswithStateBetween", notes = "Search all Delivery by in Pointofsale " +
            "between 02 dates page by page",
            responseContainer = "Page<DeliveryDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The Delivery page found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageDeliveryinPoswithStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "deliveryState", type = "DeliveryState", required = true,
                    value="DeliveryState found", example = "Delivery")
            @NotNull @PathVariable("deliveryState") DeliveryState deliveryState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/delivery/pos/userbm/all/{posId}/{userbmId}/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllDeliveryinPosforUserbmBetween", notes = "Search all Delivery by in Pointofsale " +
            "between 02 dates for UserBM",
            responseContainer = "List<DeliveryDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The Delivery list found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllDeliveryinPosforUserbmBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned UserBM", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/delivery/pos/userbm/page/{posId}/{userbmId}/{from}/{to}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageDeliveryinPosforUserbmBetween", notes = "Search all Delivery by in Pointofsale " +
            "between 02 dates for UserBM page by page",
            responseContainer = "Page<DeliveryDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The Delivery page found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageDeliveryinPosforUserbmBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned UserBM", example = "1")
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

    @GetMapping(value = APP_ROOT+"/delivery/pos/userbm/state/all/{deliveryState}/{posId}/{userbmId}/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllDeliveryinPosforUserbmwithStateBetween", notes = "Search all Delivery in Pointofsale " +
            "between 02 dates for UserBM with deliveryState",
            responseContainer = "List<DeliveryDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The Delivery list found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllDeliveryinPosforUserbmwithStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned UserBM", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "deliveryState", type = "DeliveryState", required = true,
                    value="DeliveryState found", example = "Delivery")
            @NotNull @PathVariable("deliveryState") DeliveryState deliveryState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/delivery/pos/userbm/state/page/{deliveryState}/{posId}/{userbmId}/{from}/{to}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageDeliveryinPosforUserbmwithStateBetween", notes = "Search all Delivery in Pointofsale " +
            "between 02 dates for UserBM with deliveryState page by page",
            responseContainer = "Page<DeliveryDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The Delivery page found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageDeliveryinPosforUserbmwithStateBetween(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "userbmId", type = "Long", required = true,
                    value="Id of the concerned UserBM", example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId,
            @ApiParam(name = "deliveryState", type = "DeliveryState", required = true,
                    value="DeliveryState found", example = "Delivery")
            @NotNull @PathVariable("deliveryState") DeliveryState deliveryState,
            @ApiParam(name = "from", type = "Instant", required = true,
                    value="The date from which to search")
            @NotNull @PathVariable("from") Instant startDate,
            @ApiParam(name = "to", type = "Instant", required = true,
                    value="The date to which to search")
            @NotNull @PathVariable("to") Instant endDate,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = APP_ROOT+"/delivery/details/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveDeliveryDetails",
            notes = "This method is used to save a delivery details in the DB",
            response = DeliveryDetailsDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object DeliveryDetailsDto added successfully"),
            @ApiResponse(code=400, message="Object DeliveryDto is not valid during the saving process")
    })
    ResponseEntity saveDeliveryDetails(
            @ApiParam(name = "ddDto", type = "DeliveryDetailsDto", required = true,
                    value="The JSON object that represent the DeliveryDto to save")
            @Valid @RequestBody DeliveryDetailsDto ddDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = APP_ROOT+"/delivery/details/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "updateDeliveryDetails",
            notes = "This method is used to update a delivery details in the DB",
            response = DeliveryDetailsDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object DeliveryDetailsDto updated successfully"),
            @ApiResponse(code=400, message="Object DeliveryDto is not valid during the saving process")
    })
    ResponseEntity updateDeliveryDetails(
            @ApiParam(name = "ddDto", type = "DeliveryDetailsDto", required = true,
                    value="The JSON object that represent the DeliveryDto to update")
            @Valid @RequestBody DeliveryDetailsDto ddDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/delivery/details/id/{ddId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findDeliveryDetailsById", notes = "Search a DeliveryDetails details by id",
            response = DeliveryDetailsDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The DeliveryDetails found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findDeliveryDetailsById(
            @ApiParam(name = "ddId", type = "Long", required = true,
                    value="Id of the concerned DeliveryDetails", example = "1")
            @NotNull @PathVariable("ddId") Long ddId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/delivery/details/packaging/pos/{packagingId}/{deliveryId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findDeliveryDetailsinDeliverywithPackaging", notes = "Search a DeliveryDetails details by details",
            response = DeliveryDetailsDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The DeliveryDetails found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findDeliveryDetailsinDeliverywithPackaging(
            @ApiParam(name = "packagingId", type = "Long", required = true,
                    value="Id of the concerned Packaging", example = "1")
            @NotNull @PathVariable("packagingId") Long packagingId,
            @ApiParam(name = "deliveryId", type = "Long", required = true,
                    value="Id of the concerned Delivery", example = "1")
            @NotNull @PathVariable("deliveryId") Long deliveryId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = APP_ROOT+"/delivery/details/delete/id/{ddId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deleteDeliveryDetailsById",
            notes = "This method is used to delete the delivery details saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object delivery details deleted successfully")
    })
    ResponseEntity deleteDeliveryDetailsById(
            @ApiParam(name = "ddId", type = "Long", required = true,
                    value="Id of the DeliveryDetails to delete", example = "1")
            @NotNull @PathVariable("ddId") Long ddId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/delivery/details/all/{deliveryId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllDeliveryDetailsinDelivery", notes = "Search a DeliveryDetails list in a delivery",
            responseContainer = "List<DeliveryDetailsDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The DeliveryDetails list found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllDeliveryDetailsinDelivery(
            @ApiParam(name = "deliveryId", type = "Long", required = true,
                    value="Id of the concerned Delivery", example = "1")
            @NotNull @PathVariable("deliveryId") Long deliveryId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/delivery/details/page/{deliveryId}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageDeliveryDetailsinDelivery", notes = "Search a DeliveryDetails page in a delivery",
            responseContainer = "Page<DeliveryDetailsDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The DeliveryDetails page found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageDeliveryDetailsinDelivery(
            @ApiParam(name = "deliveryId", type = "Long", required = true,
                    value="Id of the concerned Delivery", example = "1")
            @NotNull @PathVariable("deliveryId") Long deliveryId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


}
