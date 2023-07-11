package com.c2psi.businessmanagement.controllers.api.pos.userbm;

import com.c2psi.businessmanagement.dtos.pos.userbm.RoleDto;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static com.c2psi.businessmanagement.utils.pos.userbm.RoleApiConstant.*;

@Validated
@Api(ROLE_ENDPOINT)
public interface RoleApi {
    @PostMapping(value = CREATE_ROLE_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create or Persist a role of an enterprise in the DB",
            notes = "This method is used to save or modify a role of an entreprise",
    response = RoleDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Role add/modify successfully"),
            @ApiResponse(code=400, message="Object Role is not valid")
    })
    ResponseEntity saveRole(
            @ApiParam(name = "roleDto", type = "RoleDto", value="The Json object that represent the role to save",
                    required = true, allowableValues = "Manager; Saler; Deliver", example = "Manager")
            @Valid @RequestBody RoleDto roleDto, BindingResult bindingResult);

    @GetMapping(value = FIND_ROLE_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find role by id",
            notes = "This method is used to find a role in the DB by its id",
            response = RoleDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Role found successfully"),
            @ApiResponse(code=404, message="No Object Role found with the id provided")
    })
    ResponseEntity findRoleById(
            @ApiParam(name = "idRole", type = "Long", value="The Id of the role", required = true, example = "1")
            @NotNull @PathVariable("idRole") Long id);

    @GetMapping(value = FIND_ROLE_BY_ROLENAME_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find role by roleName in an enterprise",
            notes = "This method is used to find a role in the DB by its roleName and enterprise",
            response = RoleDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Role found successfully"),
            @ApiResponse(code=404, message="No Object Role in the precised enterprise with the precised roleName")
    })
    ResponseEntity findRoleByRolename(
            @ApiParam(name = "roleName", type = "String", value="The name of the role", required = true, example = "Manager")
            @NotNull @PathVariable("roleName") String roleName,
            @ApiParam(name = "entId", type = "Long", value="The enterprise Id", required = true, example = "1")
            @NotNull @PathVariable("entId") Long entId);

    @DeleteMapping(value = DELETE_ROLE_BY_ID_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete a role in DB by id",
            notes = "This method is used to delete a role saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Role deleted successfully")
    })
    ResponseEntity deleteRoleById(@ApiParam(name = "roleId", type = "Long", value="The role Id", required = true, example = "1")
                           @NotNull @PathVariable("roleId") Long id);

    @DeleteMapping(value = DELETE_ROLE_BY_ROLENAME_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete a particular role in an enterprise",
            notes = "This method is used to delete a role saved for an enterprise", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Role deleted successfully")
    })
    ResponseEntity deleteRoleByRolename(
            @ApiParam(name = "roleName", type = "String", value="The name of the role", required = true, example = "Manager")
            @NotNull @PathVariable("roleName") String roleName,
            @ApiParam(name = "entId", type = "Long", value="The enterprise Id", required = true, example = "1")
            @NotNull @PathVariable("entId") Long entId);

    @GetMapping(value = FIND_ALL_ROLE_OF_ENTERPRISE_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find all role of an enterprise",
            notes = "This method is used to find all role saved for an enterprise",
            responseContainer = "List<RoleDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="List of Object Role found successfully or empty list")
    })
    ResponseEntity findAllRoleOfEnterprise(
            @ApiParam(name = "entId", type = "Long", value="The enterprise Id", required = true, example = "1")
            @NotNull @PathVariable("entId") Long entId);

    @GetMapping(value = FIND_ALL_ROLE_OF_USERBM_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find all role of a particular user",
            notes = "This method is used to find all role set to a particular user",
            responseContainer = "List<RoleDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="List of Object Role found successfully or empty list")
    })
    ResponseEntity findAllRoleofUserBM(
            @ApiParam(name = "userbmId", type = "Long", value="The userbm Id", required = true, example = "1")
            @NotNull @PathVariable("userbmId") Long userbmId);
}
