package com.c2psi.businessmanagement.controllers.api.pos.userbm;

import com.c2psi.businessmanagement.dtos.pos.userbm.RoleDto;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

@Api(APP_ROOT+"/role")
public interface RoleApi {
    @PostMapping(value = APP_ROOT+"/role/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create or Persist a role of an enterprise in the DB",
            notes = "This method is used to save or modify a role of an entreprise",
    response = RoleDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Role add/modify successfully"),
            @ApiResponse(code=400, message="Object Role is not valid")
    })
    RoleDto saveRole(@ApiParam(value="Accepted value for RoleType are [Manager, Saler, Deliver]")
                     @Valid @RequestBody RoleDto roleDto);

    @GetMapping(value = APP_ROOT+"/role/{idRole}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find role by id",
            notes = "This method is used to find a role in the DB by its id",
            response = RoleDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Role found successfully"),
            @ApiResponse(code=404, message="No Object Role found with the id provided")
    })
    RoleDto findRoleById(@PathVariable("idRole") Long id);

    @GetMapping(value = APP_ROOT+"/role/{roleName}/{entId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find role by roleName in an enterprise",
            notes = "This method is used to find a role in the DB by its roleName and enterprise",
            response = RoleDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Role found successfully"),
            @ApiResponse(code=404, message="No Object Role in the precised enterprise with the precised roleName")
    })
    RoleDto findRoleByRolename(@PathVariable("roleName") String roleName, @PathVariable("entId") Long entId);

    @DeleteMapping(value = APP_ROOT+"/role/delete/{roleId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete a role in DB by id",
            notes = "This method is used to delete a role saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Role deleted successfully")
    })
    Boolean deleteRoleById(@PathVariable("roleId") Long id);

    @DeleteMapping(value = APP_ROOT+"/role/delete/{roleName}/{entId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete a particular role in an enterprise",
            notes = "This method is used to delete a role saved for an enterprise", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Role deleted successfully")
    })
    Boolean deleteRoleByRolename(@PathVariable("roleName") String roleName, @PathVariable("entId") Long entId);

    @GetMapping(value = APP_ROOT+"/role/all/ent/{entId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find all role of an enterprise",
            notes = "This method is used to find all role saved for an enterprise",
            responseContainer = "List<RoleDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="List of Object Role found successfully or empty list")
    })
    List<RoleDto> findAllRoleOfEnterprise(@PathVariable("entId") Long entId);

    @GetMapping(value = APP_ROOT+"/role/all/userbm/{userbmId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find all role of a particular user",
            notes = "This method is used to find all role set to a particular user",
            responseContainer = "List<RoleDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="List of Object Role found successfully or empty list")
    })
    List<RoleDto> findAllRoleofUserBM(@PathVariable("userbmId") Long userbmId);
}
