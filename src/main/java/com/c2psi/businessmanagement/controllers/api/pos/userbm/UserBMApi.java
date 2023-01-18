package com.c2psi.businessmanagement.controllers.api.pos.userbm;

import com.c2psi.businessmanagement.Enumerations.UserBMType;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.exceptions.BMException;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

@Api(APP_ROOT+"/userbm")
public interface UserBMApi {
    @PostMapping(value = APP_ROOT+"/userbm/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create or Persist a user BM in the DB",
            notes = "This method is used to save or modify a user BM in the DB",
            response = UserBMDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object UserBM add successfully"),
            @ApiResponse(code=400, message="The execution of the request encounter a problem")
    })
    ResponseEntity<UserBMDto> saveUserBM(@ApiParam(value="Accepted value for UserBMState are [Activated, Deactivated, " +
            "Connected, Disconnected]; for UserBMType are [AdminEnterprise, AdminBM, Employe]")
            @Valid @RequestBody UserBMDto userBMDto, BindingResult bindingResult)
            throws BMException;

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
    ResponseEntity<UserBMDto> updateUserBM(@ApiParam(value="Accepted value for UserBMState are [Activated, " +
            "Deactivated, Connected, Disconnected]; for UserBMType are [AdminEnterprise, AdminBM, " +
            "Employe]") @Valid @RequestBody UserBMDto userBMDto, BindingResult bindingResult)
            throws BMException;

    @GetMapping(value = {APP_ROOT+"/userbm/login/{bmLogin}"},
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find userbm by login",
            notes = "This method is used to find a userbm in the DB by its login",
            response = UserBMDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object UserBM found successfully"),
            @ApiResponse(code=404, message="No Object UserBM found with the login provided")
    })
    ResponseEntity<UserBMDto> findUserBMByLogin(@PathVariable("bmLogin") String bmLogin) throws BMException;

    @GetMapping(value = APP_ROOT+"/userbm/cni/{bmCni}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find userbm by cni number",
            notes = "This method is used to find a userbm in the DB by its cni number",
            response = UserBMDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object UserBM found successfully"),
            @ApiResponse(code=404, message="No Object UserBM found with the cni number provided")
    })
    ResponseEntity<UserBMDto> findUserBMByCni(@PathVariable("bmCni") String bmCni) throws BMException;

    @GetMapping(value = APP_ROOT+"/userbm/email/{bmEmail}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find userbm by email address",
            notes = "This method is used to find a userbm in the DB by its email address",
            response = UserBMDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object UserBM found successfully"),
            @ApiResponse(code=404, message="No Object UserBM found with the email address provided")
    })
    ResponseEntity<UserBMDto> findUserBMByEmail(@PathVariable("bmEmail") String bmEmail) throws BMException;

    @GetMapping(value = APP_ROOT+"/userbm/fullname/{bmName}/{bmSurname}/{bmDob}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find userbm by name, surname and date of birth",
            notes = "This method is used to find a userbm in the DB by its name, surname and date of birth",
            response = UserBMDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object UserBM found successfully"),
            @ApiResponse(code=404, message="No Object UserBM found with the name, surname and date of birth provided")
    })
    ResponseEntity<UserBMDto> findUserBMByFullNameAndDob(@PathVariable("bmName") String bmName,
                                         @PathVariable("bmSurname") String bmSurname,
                                         @PathVariable("bmDob") Date bmDob) throws BMException;

    @GetMapping(value = APP_ROOT+"/userbm/id/{bmId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find userbm by id ",
            notes = "This method is used to find a userbm in the DB by its id ",
            response = UserBMDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object UserBM found successfully"),
            @ApiResponse(code=404, message="No Object UserBM found with the id  provided")
    })
    ResponseEntity<UserBMDto> findUserBMById(@PathVariable("bmId") Long bmId) throws BMException;

    @GetMapping(value = APP_ROOT+"/userbm/type/{bmUsertype}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find all userbm of a precised type",
            notes = "This method is used to find all userbm in DB with a precised type",
            responseContainer = "List<UserBMDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="List of Object UserBM with a precised type found successfully " +
                    " or empty list")
    })
    ResponseEntity<List<UserBMDto>> findAllByUserBMType(@ApiParam(value="Accepted value for  UserBMType are [AdminEnterprise, AdminBM, Employe]")
                                        @PathVariable("bmUsertype") UserBMType bmUsertype) throws BMException;

    @GetMapping(value = APP_ROOT+"/userbm/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find all userbm registered",
            notes = "This method is used to find all userbm in DB",
            responseContainer = "List<UserBMDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="List of Object UserBM  found successfully " +
                    " or empty list")
    })
    ResponseEntity<List<UserBMDto>> findAllUserBM() throws BMException;

    @GetMapping(value = APP_ROOT+"/userbm/pos/{idPos}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find all userbm registered for a point of sale",
            notes = "This method is used to find all userbm in a point of sale",
            responseContainer = "List<UserBMDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="List of Object UserBM  found successfully " +
                    " or empty list")
    })
    ResponseEntity<List<UserBMDto>> findAllUserBMofPos(@PathVariable("idPos") Long idPos) throws BMException;

    @GetMapping(value = {APP_ROOT+"/userbm/page/pos/{idPos}", APP_ROOT+"/userbm/page/{idPos}/{sample}/{pagenum}/{pagesize}"},
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find a page of userbm registered in a point of sale that fullname contains",
            notes = "This method is used to find all userbm in a point of sale where fullname contains",
            responseContainer = "Page<UserBMDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="Page of Object UserBM  found successfully " +
                    " or empty Page")
    })
    ResponseEntity<Page<UserBMDto>> findAllUserBMofPos(
            @PathVariable("idPos") Long idPos,
            @PathVariable(name = "sample", required = false) Optional<String> optsample,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize)
            throws BMException;

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
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize) throws BMException;

    @DeleteMapping(value = APP_ROOT+"/userbm/delete/login/{bmLogin}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete a userbm in DB by login",
            notes = "This method is used to delete a userbm saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object UserBM deleted successfully")
    })
    ResponseEntity<Boolean> deleteUserBMByLogin(@PathVariable("bmLogin") String bmLogin) throws BMException;

    @DeleteMapping(value = APP_ROOT+"/userbm/delete/cni/{bmCni}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete a userbm in DB by cni number",
            notes = "This method is used to delete a userbm saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object UserBM deleted successfully")
    })
    ResponseEntity<Boolean> deleteUserBMByCni(@PathVariable("bmCni") String bmCni) throws BMException;

    @DeleteMapping(value = APP_ROOT+"/userbm/delete/fullname/{bmName}/{bmSurname}/{bmDob}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete a userbm in DB by Name, Surname and Date of birth",
            notes = "This method is used to delete a userbm saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object UserBM deleted successfully")
    })
    ResponseEntity<Boolean> deleteUserBMByFullNameAndDob(@PathVariable("bmName") String bmName,
                                         @PathVariable("bmSurname") String bmSurname,
                                         @PathVariable("bmDob") Date bmDob) throws BMException;

    @DeleteMapping(value = APP_ROOT+"/userbm/delete/id/{bmId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete a userbm in DB by id",
            notes = "This method is used to delete a userbm saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object UserBM deleted successfully")
    })
    ResponseEntity<Boolean> deleteUserBMById(@PathVariable("bmId") Long bmId) throws BMException;



}
