package com.c2psi.businessmanagement.controllers.api.pos.pos;

import com.c2psi.businessmanagement.dtos.pos.pos.EnterpriseDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

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
            @ApiResponse(code=400, message="Object Enterprise is not valid")
    })
    ResponseEntity<EnterpriseDto> saveEnterprise(@Valid @RequestBody EnterpriseDto entDto, BindingResult bindingResult);

    @PutMapping(value = APP_ROOT+"/enterprise/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update an enterprise in the DB",
            notes = "This method is used to update an enterprise in the DB",
            response = EnterpriseDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Enterprise modify successfully"),
            @ApiResponse(code=400, message="Object Enterprise is not valid")
    })
    ResponseEntity<EnterpriseDto> updateEnterprise(@Valid @RequestBody EnterpriseDto entDto, BindingResult bindingResult);

    @PutMapping(value = APP_ROOT+"/enterprise/setAdmin",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Change the user admin of an enterprise",
            notes = "This method is used to change the admin of an enterprise in the DB",
            response = EnterpriseDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Admin of Enterprise modify successfully"),
            @ApiResponse(code=400, message="Object admin of enterprise is not valid")
    })
    ResponseEntity<EnterpriseDto> setAdminEnterprise(@PathVariable("idEnterprise") Long entId,
                                     @PathVariable("idUserBM") Long userBMAdminId);

    @GetMapping(value = APP_ROOT+"/enterprise/{idEnterprise}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find enterprise by id",
            notes = "This method is used to find an enterprise in the DB by its id",
            response = EnterpriseDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Enterprise found successfully"),
            @ApiResponse(code=404, message="No Object Enterprise found with the id provided")
    })
    ResponseEntity<EnterpriseDto> findEnterpriseById(@PathVariable("idEnterprise") Long id);

    @GetMapping(value = APP_ROOT+"/enterprise/{entName}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find enterprise by entName",
            notes = "This method is used to find an enterprise in the DB by its entName",
            response = EnterpriseDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Enterprise found successfully"),
            @ApiResponse(code=404, message="No Object Enterprise with the precised entName")
    })
    ResponseEntity<EnterpriseDto> findEnterpriseByName(@PathVariable("entName") String entName);

    @GetMapping(value = APP_ROOT+"/enterprise/{entNiu}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find enterprise by entNiu",
            notes = "This method is used to find an enterprise in the DB by its entNiu",
            response = EnterpriseDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Enterprise found successfully"),
            @ApiResponse(code=404, message="No Object Enterprise with the precised entNiu")
    })
    ResponseEntity<EnterpriseDto> findEnterpriseByNiu(@PathVariable("entName") String entNiu);

    @DeleteMapping(value = APP_ROOT+"/enterprise/delete/{entId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete an enterprise in DB by id",
            notes = "This method is used to delete an enterprise saved in the DB",
            response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Enterprise deleted successfully")
    })
    ResponseEntity<Boolean> deleteEnterpriseById(@PathVariable("entId") Long id);

    @DeleteMapping(value = APP_ROOT+"/enterprise/delete/{entName}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete an enterprise in DB by Name",
            notes = "This method is used to delete an enterprise saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Enterprise deleted successfully")
    })
    ResponseEntity<Boolean> deleteEnterpriseByName(@PathVariable("entName") String entName);

    @DeleteMapping(value = APP_ROOT+"/enterprise/delete/{entNiu}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete an enterprise in DB by Niu",
            notes = "This method is used to delete an enterprise saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Enterprise deleted successfully")
    })
    ResponseEntity<Boolean> deleteEnterpriseByNiu(@PathVariable("entNiu") String entNiu);

    @GetMapping(value = APP_ROOT+"/enterprise/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find all enterprise",
            notes = "This method is used to find all enterprise in DB",
            responseContainer = "List<EnterpriseDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="List of Object Enterprise found successfully or empty list")
    })
    ResponseEntity<List<EnterpriseDto>> findAllEnterprise();

    @GetMapping(value = APP_ROOT+"/enterprise/allPointofsale/{entId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find all Point of sale of an enterprise",
            notes = "This method is used to find all point of sale of an enterprise in DB",
            responseContainer = "List<PointofsaleDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="List of Object Pointofsale of an  Enterprise found successfully or empty list")
    })
    ResponseEntity<List<PointofsaleDto>> findAllPosofEnterprise(@PathVariable("entId") Long entId);

    @GetMapping(value = APP_ROOT+"/enterprise/getTurnover/{entId}/{startDate}/{endDate}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Return the turnover of an enterprise between 02 dates",
            notes = "This method is used to return the turnover realize in an enterprise between 02 dates",
            response = BigDecimal.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The turnover of the  Enterprise calsulate successfully")
    })
    ResponseEntity<BigDecimal> getTurnover(@PathVariable("entId") Long entId, @PathVariable("startDate") Date startDate,
                           @PathVariable("endDate") Date endDate);

    @GetMapping(value = APP_ROOT+"/enterprise/allEmploye/{entId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find all UserBM of an enterprise",
            notes = "This method is used to find all UserBM of an enterprise in DB",
            responseContainer = "List<UserBMDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="List of Object UserBM of an  Enterprise found successfully or empty list")
    })
    ResponseEntity<List<UserBMDto>> findAllEmployeofEnterprise (@PathVariable("entId") Long entId);

    @GetMapping(value = APP_ROOT+"/enterprise/allProvider/{entId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find all Provider of an enterprise",
            notes = "This method is used to find all provider of an enterprise in DB",
            responseContainer = "List<ProviderDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="List of Object Provider of an  Enterprise found successfully or empty list")
    })
    ResponseEntity<List<ProviderDto>> findAllProviderofEnterprise (@PathVariable("entId") Long entId);

    @GetMapping(value = APP_ROOT+"/enterprise/getTotalCash/{entId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Return the total cash of an enterprise between 02 dates",
            notes = "This method is used to return the total cash available in an enterprise",
            response = BigDecimal.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The total cash of the  Enterprise calculate successfully")
    })
    ResponseEntity<BigDecimal> getTotalCash(@PathVariable("entId") Long entId);

    @GetMapping(value = APP_ROOT+"/enterprise/getNumberofDamage/{entId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Return the total number of Damage product of an enterprise",
            notes = "This method is used to return the total number of damage product available in an enterprise",
            response = Integer.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The total number of damage of the  Enterprise calculate successfully")
    })
    ResponseEntity<Integer> getNumberofDamage(@PathVariable("entId") Long entId);

    @GetMapping(value = APP_ROOT+"/enterprise/getNumberofDamage/{entId}/{artId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Return the total number of Damage product for a particular article of an enterprise",
            notes = "This method is used to return the total number of damage product of a particular article" +
                    " available in an enterprise",
            response = Integer.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The total number of damage of a particular article in an  " +
                    " Enterprise calculate successfully")
    })
    ResponseEntity<Integer> getNumberofDamage(@PathVariable("entId") Long entId, @PathVariable("artId") Long artId);

    @GetMapping(value = APP_ROOT+"/enterprise/getNumberofCover/{entId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Return the total number of cover product of an enterprise",
            notes = "This method is used to return the total number of cover product available in an enterprise",
            response = Integer.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The total number of cover of the  Enterprise calculate successfully")
    })
    ResponseEntity<Integer> getNumberofCapsule(@PathVariable("entId") Long entId);

    @GetMapping(value = APP_ROOT+"/enterprise/getNumberofCover/{entId}/{artId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Return the total number of cover product for a particular article of an enterprise",
            notes = "This method is used to return the total number of cover product of a particular article" +
                    " available in an enterprise",
            response = Integer.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The total number of cover of a particular article in an  " +
                    " Enterprise calculate successfully")
    })
    ResponseEntity<Integer> getNumberofCapsule(@PathVariable("entId") Long entId, @PathVariable("artId") Long artId);

    @GetMapping(value = APP_ROOT+"/enterprise/getNumberofPackaging/{entId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Return the total number of packaging of an enterprise",
            notes = "This method is used to return the total number of packaging available in an enterprise",
            response = Integer.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The total number of packaging of the  Enterprise calculate successfully")
    })
    ResponseEntity<Integer> getNumberofPackaging(@PathVariable("entId") Long entId);

    @GetMapping(value = APP_ROOT+"/enterprise/getNumberofCover/{entId}/{providerId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Return the total number of packaging for a particular provider of an enterprise",
            notes = "This method is used to return the total number of packaging  of a particular provider" +
                    " available in an enterprise",
            response = Integer.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The total number of packaging of a particular provider in an  " +
                    " Enterprise calculate successfully")
    })
    ResponseEntity<Integer> getNumberofPackaging(@PathVariable("entId") Long entId, @PathVariable("providerId") Long providerId);


}
