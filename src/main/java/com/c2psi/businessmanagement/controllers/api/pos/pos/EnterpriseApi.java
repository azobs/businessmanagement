package com.c2psi.businessmanagement.controllers.api.pos.pos;

import com.c2psi.businessmanagement.dtos.pos.pos.EnterpriseDto;
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
import java.util.Date;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

@Validated
@Api(APP_ROOT+"/enterprise")
public interface EnterpriseApi {
    @PostMapping(value = APP_ROOT+"/enterprise/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create or Persist an enterprise in the DB",
            notes = "This method is used to save an enterprise in the DB",
            response = EnterpriseDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Enterprise add successfully"),
            @ApiResponse(code=400, message="Object Enterprise is not valid during the saving process")
    })
    ResponseEntity saveEnterprise(
            @ApiParam(name = "entDto", type = "UserBMDto", required = true,
                    value="The JSON object that represent the enterprise to save")
            @Valid @RequestBody EnterpriseDto entDto, BindingResult bindingResult);

    @PutMapping(value = APP_ROOT+"/enterprise/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update an enterprise in the DB",
            notes = "This method is used to update an enterprise in the DB",
            response = EnterpriseDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Enterprise modified successfully"),
            @ApiResponse(code=400, message="Object Enterprise is not valid during the updating process")
    })
    ResponseEntity updateEnterprise(
            @ApiParam(name = "entDto", type = "UserBMDto", required = true,
                    value="The JSON object that represent the updated version of the enterprise to update")
            @Valid @RequestBody EnterpriseDto entDto, BindingResult bindingResult);

    /*@PutMapping(value = APP_ROOT+"/enterprise/setAdmin",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Change the user admin of an enterprise",
            notes = "This method is used to change the admin of an enterprise in the DB",
            response = EnterpriseDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Admin of Enterprise modified successfully"),
            @ApiResponse(code=400, message="Object admin of enterprise is not valid during the setAdmin process")
    })
    ResponseEntity setAdminEnterprise(
            @ApiParam(name = "entId", type = "Long", required = true,
                    value="Id of the enterprise for which an admin will be set", example = "1")
            @NotNull @RequestBody Long entId,
            @ApiParam(name = "userBMId", type = "Long", required = true,
                    value="Id of the UserBM which will be set as admin of the enterprise", example = "1")
            @NotNull @RequestBody Long userBMAdminId);*/

    @PutMapping(value = APP_ROOT+"/enterprise/setAdmin",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Change the user admin of an enterprise",
            notes = "This method is used to change the admin of an enterprise in the DB",
            response = EnterpriseDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Admin of Enterprise modified successfully"),
            @ApiResponse(code=400, message="Object admin of enterprise is not valid during the setAdmin process")
    })
    ResponseEntity setAdminEnterprise(
            @ApiParam(name = "enterpriseDto", type = "Long", required = true,
                    value="Id's of enterprise and Userbm ")
            @Valid @RequestBody EnterpriseDto enterpriseDto, BindingResult bindingResult);

    @GetMapping(value = APP_ROOT+"/enterprise/{enterpriseId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find enterprise by id",
            notes = "This method is used to find an enterprise in the DB by its id",
            response = EnterpriseDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Enterprise found successfully"),
            @ApiResponse(code=404, message="No Object Enterprise found by Id in the findByEnterpriseId")
    })
    ResponseEntity findEnterpriseById(
            @ApiParam(name = "enterpriseId", type = "Long", required = true,
                    value="Id of the enterprise found", example = "1")
            @NotNull @PathVariable("enterpriseId") Long enterpriseId);

    @GetMapping(value = APP_ROOT+"/enterprise/name/{entName}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find enterprise by entName",
            notes = "This method is used to find an enterprise in the DB by its entName",
            response = EnterpriseDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Enterprise found successfully"),
            @ApiResponse(code=404, message="No Object Enterprise with the precised entName")
    })
    ResponseEntity findEnterpriseByName(
            @ApiParam(name = "entName", type = "String", required = true,
                    value="Name of the enterprise found", example = "Name")
            @NotNull @NotEmpty @NotBlank @PathVariable("entName") String entName);

    @GetMapping(value = APP_ROOT+"/enterprise/niu/{entNiu}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find enterprise by entNiu",
            notes = "This method is used to find an enterprise in the DB by its entNiu",
            response = EnterpriseDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Enterprise found successfully"),
            @ApiResponse(code=404, message="No Object Enterprise with the precised entNiu")
    })
    ResponseEntity findEnterpriseByNiu(
            @ApiParam(name = "entNiu", type = "String", required = true,
                    value="Niu of the enterprise found", example = "Niu")
            @NotNull @NotEmpty @NotBlank @PathVariable("entNiu") String entNiu);

    @DeleteMapping(value = APP_ROOT+"/enterprise/delete/{entId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete an enterprise in DB by id",
            notes = "This method is used to delete an enterprise saved in the DB",
            response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Enterprise deleted successfully")
    })
    ResponseEntity deleteEnterpriseById(
            @ApiParam(name = "entId", type = "Long", required = true,
                    value="Id of the enterprise which will be deleted", example = "1")
            @NotNull @PathVariable("entId") Long entId);

    @DeleteMapping(value = APP_ROOT+"/enterprise/delete/name/{entName}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete an enterprise in DB by Name",
            notes = "This method is used to delete an enterprise saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Enterprise deleted successfully")
    })
    ResponseEntity deleteEnterpriseByName(
            @ApiParam(name = "entName", type = "String", required = true,
                    value="Name of the enterprise which will be deleted", example = "Name")
            @NotNull @NotEmpty @NotBlank @PathVariable("entName") String entName);

    @DeleteMapping(value = APP_ROOT+"/enterprise/delete/niu/{entNiu}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete an enterprise in DB by Niu",
            notes = "This method is used to delete an enterprise saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Enterprise deleted successfully")
    })
    ResponseEntity deleteEnterpriseByNiu(
            @ApiParam(name = "entNiu", type = "String", required = true,
                    value="Niu of the enterprise which will be deleted", example = "Niu")
            @NotNull @NotEmpty @NotBlank @PathVariable("entNiu") String entNiu);

    @GetMapping(value = APP_ROOT+"/enterprise/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find all enterprise",
            notes = "This method is used to find all enterprise in DB",
            responseContainer = "List<EnterpriseDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="List of Object Enterprise found successfully or empty list")
    })
    ResponseEntity findAllEnterprise();

    @GetMapping(value = APP_ROOT+"/enterprise/allPointofsale/{entId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find all Point of sale of an enterprise",
            notes = "This method is used to find all point of sale of an enterprise in DB",
            responseContainer = "List<PointofsaleDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="List of Object Pointofsale of an  Enterprise found successfully or empty list")
    })
    ResponseEntity findAllPosofEnterprise(
            @ApiParam(name = "entId", type = "Long", required = true,
                    value="Id of the enterprise for which list of pointofsale is found", example = "1")
            @NotNull @PathVariable("entId") Long entId);

    @GetMapping(value = APP_ROOT+"/enterprise/getTurnover/{entId}/{startDate}/{endDate}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Return the turnover of an enterprise between 02 dates",
            notes = "This method is used to return the turnover realize in an enterprise between 02 dates",
            response = String.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The turnover of the  Enterprise calsulate successfully")
    })
    ResponseEntity getTurnover(
            @ApiParam(name = "entId", type = "Long", required = true,
                    value="Id of the enterprise for which the turnover is found", example = "1")
            @NotNull @PathVariable("entId") Long entId,
            @ApiParam(name = "startDate", type = "Date",
                    value="The minimum date from which the calculation going to start")
            @PathVariable("startDate") Date startDate,
            @ApiParam(name = "endDate", type = "Date",
                    value="The maximum date from which the calculation going to stop")
            @PathVariable("endDate") Date endDate);

    @GetMapping(value = APP_ROOT+"/enterprise/allEmploye/{entId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find all UserBM of an enterprise",
            notes = "This method is used to find all UserBM of an enterprise in DB",
            responseContainer = "List<UserBMDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="List of Object UserBM of an  Enterprise found successfully or empty list")
    })
    ResponseEntity findAllEmployeofEnterprise (
            @ApiParam(name = "entId", type = "Long", required = true,
                    value="Id of the enterprise for which the employe list is found", example = "1")
            @NotNull @PathVariable("entId") Long entId);

    /*@GetMapping(value = APP_ROOT+"/enterprise/allProvider/{entId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find all Provider of an enterprise",
            notes = "This method is used to find all provider of an enterprise in DB",
            responseContainer = "List<ProviderDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="List of Object Provider of an  Enterprise found successfully or empty list")
    })
    ResponseEntity<List<ProviderDto>> findAllProviderofEnterprise (@PathVariable("entId") Long entId);*/

    @GetMapping(value = APP_ROOT+"/enterprise/getTotalCash/{entId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Return the total cash of an enterprise between 02 dates",
            notes = "This method is used to return the total cash available in an enterprise",
            response = String.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The total cash of the  Enterprise calculate successfully")
    })
    ResponseEntity getTotalCash(
            @ApiParam(name = "entId", type = "Long", required = true,
                    value="Id of the enterprise for which the total cash is found", example = "1")
            @NotNull @PathVariable("entId") Long entId);

    @GetMapping(value = APP_ROOT+"/enterprise/getNumberofDamage/{entId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Return the total number of Damage product of an enterprise",
            notes = "This method is used to return the total number of damage product available in an enterprise",
            response = String.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The total number of damage of the  Enterprise calculate successfully")
    })
    ResponseEntity getNumberofDamage(
            @ApiParam(name = "entId", type = "Long", required = true,
                    value="Id of the enterprise for which the number of damage is found", example = "1")
            @NotNull @PathVariable("entId") Long entId);

    @GetMapping(value = APP_ROOT+"/enterprise/getNumberofDamage/{entId}/{artId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Return the total number of Damage product for a particular article of an enterprise",
            notes = "This method is used to return the total number of damage product of a particular article" +
                    " available in an enterprise",
            response = String.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The total number of damage of a particular article in an  " +
                    " Enterprise calculate successfully")
    })
    ResponseEntity getNumberofDamage(
            @ApiParam(name = "entId", type = "Long", required = true,
                    value="Id of the enterprise for which the number of damage is found", example = "1")
            @NotNull @PathVariable("entId") Long entId,
            @ApiParam(name = "entId", type = "Long", required = true,
                    value="Id of the article for which the number of damage is found", example = "1")
            @NotNull @PathVariable("artId") Long artId);

    @GetMapping(value = APP_ROOT+"/enterprise/getNumberofCover/{entId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Return the total number of cover product of an enterprise",
            notes = "This method is used to return the total number of cover product available in an enterprise",
            response = String.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The total number of cover of the  Enterprise calculate successfully")
    })
    ResponseEntity getNumberofCapsule(
            @ApiParam(name = "entId", type = "Long", required = true,
                    value="Id of the enterprise for which the number of cover is found", example = "1")
            @NotNull @PathVariable("entId") Long entId);

    @GetMapping(value = APP_ROOT+"/enterprise/getNumberofCover/{entId}/{artId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Return the total number of cover product for a particular article of an enterprise",
            notes = "This method is used to return the total number of cover product of a particular article" +
                    " available in an enterprise",
            response = String.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The total number of cover of a particular article in an  " +
                    " Enterprise calculate successfully")
    })
    ResponseEntity getNumberofCapsule(
            @ApiParam(name = "entId", type = "Long", required = true,
                    value="Id of the enterprise for which the number of cover is found", example = "1")
            @NotNull @PathVariable("entId") Long entId,
            @ApiParam(name = "entId", type = "Long", required = true,
                    value="Id of the article for which the number of cover is found", example = "1")
            @NotNull @PathVariable("artId") Long artId);

    @GetMapping(value = APP_ROOT+"/enterprise/getNumberofPackaging/{entId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Return the total number of packaging of an enterprise",
            notes = "This method is used to return the total number of packaging available in an enterprise",
            response = String.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The total number of packaging of the  Enterprise calculate successfully")
    })
    ResponseEntity getNumberofPackaging(
            @ApiParam(name = "entId", type = "Long", required = true,
                    value="Id of the enterprise for which the number of packaging is found", example = "1")
            @NotNull @PathVariable("entId") Long entId);

    @GetMapping(value = APP_ROOT+"/enterprise/getNumberofCover/{entId}/{providerId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Return the total number of packaging for a particular provider of an enterprise",
            notes = "This method is used to return the total number of packaging  of a particular provider" +
                    " available in an enterprise",
            response = String.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The total number of packaging of a particular provider in an  " +
                    " Enterprise calculate successfully")
    })
    ResponseEntity getNumberofPackaging(
            @ApiParam(name = "entId", type = "Long", required = true,
                    value="Id of the enterprise for which the number of packaging is found", example = "1")
            @NotNull @PathVariable("entId") Long entId,
            @ApiParam(name = "entId", type = "Long", required = true,
                    value="Id of the provider for which the number of packaging is found", example = "1")
            @NotNull @PathVariable("providerId") Long providerId);


}
