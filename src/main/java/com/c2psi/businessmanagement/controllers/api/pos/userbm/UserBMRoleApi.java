package com.c2psi.businessmanagement.controllers.api.pos.userbm;

import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMRoleDto;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.util.List;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

@Validated
@Api(APP_ROOT+"/userbmrole")
public interface UserBMRoleApi {
    @PostMapping(value = APP_ROOT+"/userbmrole/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Assign a role to a userbm in the system",
            notes = "This method is used to save a userbmrole in the system",
            response = UserBMRoleDto.class)
    @ApiResponses(value={
            @ApiResponse(code=201, message="Object UserBMRole added successfully"),
            @ApiResponse(code=400, message="Object UserBMRole is not valid")
    })
    ResponseEntity saveUserBMRole(
            @ApiParam(name = "userBMRoleDto", type = "UserBMRoleDto", value="The Json object that represent the userbmrole to save",
                    required = true)
            @Valid @RequestBody UserBMRoleDto userBMRoleDto, BindingResult bindingResult);

    @GetMapping(value = APP_ROOT+"/userbmrole/{iduserBMRole}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find userbmrole by id",
            notes = "This method is used to find a userbmrole in the DB by its id",
            response = UserBMRoleDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object UserBMRole founded successfully"),
            @ApiResponse(code=404, message="No Object UserBMRole founded with the id provided")
    })
    ResponseEntity findUserBMRoleById(
            @ApiParam(name = "idUserBMRole", type = "Long", value="The Id of the userbmrole", required = true, example = "1")
            @NotNull @PathVariable("idUserBMRole") Long idUserBMRole);

    @GetMapping(value = APP_ROOT+"/userbmrole/{userbmDtoId}/{roleDtoId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find userbmrole by userbmid and roleid",
            notes = "This method is used to find a userbmrole in the DB by knowing the userbmid and the roleid",
            response = UserBMRoleDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object UserBMRole founded successfully"),
            @ApiResponse(code=404, message="No Object UserBMRole founded with the id provided")
    })
    ResponseEntity findByUserbmroleUserbmAndUserbmroleRole(
            @ApiParam(name = "userbmDtoId", type = "Long", value="The Id of the userbm", required = true, example = "1")
            @NotNull @PathVariable("userbmDtoId") Long userbmDtoId,
            @ApiParam(name = "roleDtoId", type = "Long", value="The Id of the role", required = true, example = "1")
            @NotNull @PathVariable("roleDtoId") Long roleDtoId);

    @GetMapping(value = APP_ROOT+"/userbmrole/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find all userbmrole registered",
            notes = "This method is used to find all userbmrole in DB",
            responseContainer = "List<UserBMRoleDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="List of Object UserBMRole  founded successfully " +
                    " or empty list")
    })
    ResponseEntity<List<UserBMRoleDto>> findAllUserBMRoleofPos(
            @ApiParam(name = "posId", type = "Long", value="The Id of the Pointofsale", required = true, example = "1")
            @NotNull @PathVariable("posId") Long posId);// throws BMException;

    @DeleteMapping(value = APP_ROOT+"/userbmrole/delete/{userbmroleId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete a role in DB by id",
            notes = "This method is used to delete a userbmrole saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object UserBMRole deleted successfully")
    })
    ResponseEntity deleteRoleById(@ApiParam(name = "userbmroleId", type = "Long", value="The userbm role Id", required = true, example = "1")
                                  @NotNull @PathVariable("userbmroleId") Long userbmroleId);
}
