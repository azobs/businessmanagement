package com.c2psi.businessmanagement.controllers.api.pos.userbm;

import com.c2psi.businessmanagement.Enumerations.UserBMType;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

@Validated
@Api(APP_ROOT+"/userbm")
public interface UserBMApi {
    @PostMapping(value = APP_ROOT+"/userbm/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create or Persist a user BM in the DB",
            notes = "This method is used to save or create a userBM in the DB",
            response = UserBMDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object UserBM added/created successfully"),
            @ApiResponse(code=400, message="The execution of the request encounter a problem")
    })
    ResponseEntity<UserBMDto> saveUserBM(
            @ApiParam(name = "userBMDto", type = "UserBMDto", required = true,
                    value="The JSON object that represent the UserBM to save")
            @Valid @RequestBody UserBMDto userBMDto, BindingResult bindingResult);
            //throws BMException;

    @PutMapping(value = APP_ROOT+"/userbm/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update a user BM in the DB",
            notes = "This method is used to modify a user BM in the DB",
            response = UserBMDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object UserBM modify successfully"),
            @ApiResponse(code=400, message="The execution of the request encounter a problem")
    })
    ResponseEntity<UserBMDto> updateUserBM(
            @ApiParam(name = "userBMDto", type = "UserBMDto", required = true,
                    value="The JSON object that represent the updated version of the UserBM")
            @Valid @RequestBody UserBMDto userBMDto, BindingResult bindingResult);
            //throws BMException;

    @PutMapping(value = APP_ROOT+"/userbm/switchUserBMState",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Switch the UserBM state to another State if the transition is possible",
            notes = "This method is used to update a user BM state in the DB",
            response = UserBMDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object UserBM updated successfully"),
            @ApiResponse(code=400, message="The execution of the request encounter a problem")
    })
    ResponseEntity<UserBMDto> switchUserBMState(
            @ApiParam(name = "userBMDto", type = "UserBMDto", required = true,
                    value="Any possible next state in the JSON object")
            @Valid @RequestBody UserBMDto userBMDto, BindingResult bindingResult);
            //throws BMException;

    @PutMapping(value = APP_ROOT+"/userbm/resetPassword",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Reset the UserBM password with security",
            notes = "This method is used to update a user BM password in the DB with security",
            response = UserBMDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object UserBM updated successfully"),
            @ApiResponse(code=400, message="The execution of the request encounter a problem")
    })
    ResponseEntity<UserBMDto> resetPassword(
            @ApiParam(name = "userBMDto", type = "UserBMDto", required = true,
                    value="Any possible new secure password in the JSON object")
            @Valid @RequestBody UserBMDto userBMDto, BindingResult bindingResult);
            //throws BMException;

    @GetMapping(value = {APP_ROOT+"/userbm/login/{bmLogin}"},
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find userbm by login",
            notes = "This method is used to find a userbm in the DB by its login",
            response = UserBMDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object UserBM found successfully"),
            @ApiResponse(code=404, message="No Object UserBM found with the login provided")
    })
    ResponseEntity<UserBMDto> findUserBMByLogin(
            @ApiParam(name = "bmLogin", type = "String", value = "The login of the user researched enterred during registration",
                    example = "login", required = true)
            @NotNull @NotEmpty @NotBlank @PathVariable("bmLogin") String bmLogin);// throws BMException;

    @GetMapping(value = APP_ROOT+"/userbm/cni/{bmCni}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find userbm by cni number",
            notes = "This method is used to find a userbm in the DB by its cni number",
            response = UserBMDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object UserBM found successfully"),
            @ApiResponse(code=404, message="No Object UserBM found with the cni number provided")
    })
    ResponseEntity<UserBMDto> findUserBMByCni(
            @ApiParam(name = "bmCni", type = "String", value = "The CNI number of the user researched enterred during registration",
                    example = "107235260", required = true)
            @NotNull @NotEmpty @NotBlank @PathVariable("bmCni") String bmCni);// throws BMException;

    @GetMapping(value = APP_ROOT+"/userbm/email/{bmEmail}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find userbm by email address",
            notes = "This method is used to find a userbm in the DB by its email address",
            response = UserBMDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object UserBM found successfully"),
            @ApiResponse(code=404, message="No Object UserBM found with the email address provided")
    })
    ResponseEntity<UserBMDto> findUserBMByEmail(
            @ApiParam(name = "bmEmail", type = "String", value = "The email of the user researched enterred during registration",
                    example = "test_@gmail.com", required = true)
            @NotNull @NotEmpty @NotBlank @PathVariable("bmEmail") String bmEmail);// throws BMException;

    @GetMapping(value = APP_ROOT+"/userbm/fullname/{bmName}/{bmSurname}/{bmDob}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find userbm by name, surname and date of birth",
            notes = "This method is used to find a userbm in the DB by its name, surname and date of birth",
            response = UserBMDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object UserBM found successfully"),
            @ApiResponse(code=404, message="No Object UserBM found with the name, surname and date of birth provided")
    })
    ResponseEntity<UserBMDto> findUserBMByFullNameAndDob(
            @ApiParam(name = "bmName", type = "String", value = "The first name of the user researched enterred during registration",
                    example = "first name", required = true)
            @NotNull @NotEmpty @NotBlank @PathVariable("bmName") String bmName,
            @ApiParam(name = "bmSurname", type = "String", value = "The surname of the user researched enterred during registration",
                    example = "surname")
            @PathVariable("bmSurname") String bmSurname,
            @ApiParam(name = "bmDob", type = "Date", value = "The date of birth of the user researched enterred during registration",
                    example = "1989-05-15", required = true)
            @NotNull @PastOrPresent @PathVariable("bmDob") Date bmDob);// throws BMException;

//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @GetMapping(value = APP_ROOT+"/userbm/id/{bmId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find userbm by id ",
            notes = "This method is used to find a userbm in the DB by its id ",
            response = UserBMDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object UserBM found successfully"),
            @ApiResponse(code=404, message="No Object UserBM found with the id  provided")
    })
    ResponseEntity<UserBMDto> findUserBMById(
            @ApiParam(name = "bmId", type = "Long", value = "The id of the UserBM researched", example = "1" , required = true)
            @NotNull @PathVariable("bmId") Long bmId);// throws BMException;

    @GetMapping(value = APP_ROOT+"/userbm/type/{bmUsertype}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find all userbm of a precised type",
            notes = "This method is used to find all userbm in DB with a precised type",
            responseContainer = "List<UserBMDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="List of Object UserBM with a precised type found successfully " +
                    " or empty list")
    })
    ResponseEntity<List<UserBMDto>> findAllByUserBMType(
            @ApiParam(name = "bmUsertype", type = "UserBMType", value="The type of Users researched",
                    allowableValues = "AdminEnterprise;AdminBM; Employe", required = true, example = "AdminBM")
            @NotNull @PathVariable("bmUsertype") UserBMType bmUsertype);// throws BMException;

    @GetMapping(value = APP_ROOT+"/userbm/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find all userbm registered",
            notes = "This method is used to find all userbm in DB",
            responseContainer = "List<UserBMDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="List of Object UserBM  found successfully " +
                    " or empty list")
    })
    ResponseEntity<List<UserBMDto>> findAllUserBM();// throws BMException;

    @GetMapping(value = APP_ROOT+"/userbm/pos/{posId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find all userbm registered for a point of sale",
            notes = "This method is used to find all userbm in a point of sale",
            responseContainer = "List<UserBMDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="List of Object UserBM  found successfully " +
                    " or empty list")
    })
    ResponseEntity<List<UserBMDto>> findAllUserBMofPos(
            @ApiParam(name = "idPos", type = "Long", value="The id of the pointofsale that Users are researched",
                    example = "1", required = true)
            @NotNull @PathVariable("posId") Long posId);// throws BMException;

    @GetMapping(value = {APP_ROOT+"/userbm/page/pos/{posId}", APP_ROOT+"/userbm/page/pos/{posId}/{sample}/{pagenum}/{pagesize}"},
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find a page of userbm registered in a point of sale that fullname contains",
            notes = "This method is used to find all userbm in a point of sale where fullname contains",
            responseContainer = "Page<UserBMDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="Page of Object UserBM  found successfully " +
                    " or empty Page")
    })
    ResponseEntity<Page<UserBMDto>> findPageUserBMofPos(
            @ApiParam(name = "idPos", type = "Long", value="The id of the pointofsale that Users are researched",
                    example = "1", required = true)
            @NotNull @PathVariable("posId") Long posId,
            @PathVariable(name = "sample", required = false) Optional<String> optsample,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);
            //throws BMException;

    @GetMapping(value = {APP_ROOT+"/userbm/page",
            APP_ROOT+"/userbm/page/{sample}", APP_ROOT+"/userbm/page/{sample}/{pagenum}/{pagesize}" },
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find a page of userbm registered in the DB that fullname contains",
            notes = "This method is used to find all userbm in the DB where fullname contains sample string",
            responseContainer = "Page<UserBMDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="Page of Object UserBM  found successfully " +
                    " or empty Page")
    })
    ResponseEntity<Page<UserBMDto>> findAllUserBMContaining(
            @PathVariable(name = "sample", required = false) Optional<String> optsample,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);// throws BMException;

    @DeleteMapping(value = APP_ROOT+"/userbm/delete/login/{bmLogin}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete a userbm in DB by login",
            notes = "This method is used to delete a userbm saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object UserBM deleted successfully")
    })
    ResponseEntity<Boolean> deleteUserBMByLogin(
            @ApiParam(name = "bmLogin", type = "String", value = "The login of the user researched enterred during registration",
                    example = "login", required = true)
            @NotNull @NotEmpty @NotBlank @PathVariable("bmLogin") String bmLogin);// throws BMException;

    @DeleteMapping(value = APP_ROOT+"/userbm/delete/cni/{bmCni}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete a userbm in DB by cni number",
            notes = "This method is used to delete a userbm saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object UserBM deleted successfully")
    })
    ResponseEntity<Boolean> deleteUserBMByCni(
            @ApiParam(name = "bmCni", type = "String", value = "The cni number of the user researched enterred during registration",
                    example = "107235260", required = true)
            @NotNull @NotEmpty @NotBlank @PathVariable("bmCni") String bmCni);// throws BMException;

    @DeleteMapping(value = APP_ROOT+"/userbm/delete/fullname/{bmName}/{bmSurname}/{bmDob}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete a userbm in DB by Name, Surname and Date of birth",
            notes = "This method is used to delete a userbm saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object UserBM deleted successfully")
    })
    ResponseEntity<Boolean> deleteUserBMByFullNameAndDob(
            @ApiParam(name = "bmName", type = "String", value = "The first names of the user researched enterred during registration",
                    example = "name_", required = true)
            @NotNull @NotEmpty @NotBlank @PathVariable("bmName") String bmName,
            @ApiParam(name = "bmSurname", type = "String",
                    value = "The last names of the user researched enterred during registration")
            @PathVariable("bmSurname") String bmSurname,
            @ApiParam(name = "bmDob", type = "Date", value = "The date of birth of the user researched enterred during registration",
                    example = "1989-05-15", required = true)
            @NotNull @PastOrPresent @PathVariable("bmDob") Date bmDob);// throws BMException;

    @DeleteMapping(value = APP_ROOT+"/userbm/delete/id/{bmId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete a userbm in DB by id",
            notes = "This method is used to delete a userbm saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object UserBM deleted successfully")
    })
    ResponseEntity<Boolean> deleteUserBMById(
            @ApiParam(name = "bmId", type = "Long", value = "The id of the UserBM researched", example = "1",
                    required = true)
            @NotNull @PathVariable("bmId") Long bmId);// throws BMException;



}
