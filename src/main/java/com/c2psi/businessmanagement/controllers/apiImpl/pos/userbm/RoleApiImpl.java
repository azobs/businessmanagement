package com.c2psi.businessmanagement.controllers.apiImpl.pos.userbm;

import com.c2psi.businessmanagement.Enumerations.RoleType;
import com.c2psi.businessmanagement.controllers.api.pos.userbm.RoleApi;
import com.c2psi.businessmanagement.dtos.pos.userbm.RoleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.services.contracts.pos.userbm.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class RoleApiImpl implements RoleApi {
    private RoleService roleService;
    @Autowired
    public RoleApiImpl(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public ResponseEntity saveRole(RoleDto roleDto, BindingResult bindingResult) {
        //System.err.println("Nous voici dans le controller");
        Map<String, Object> map = new LinkedHashMap<>();

        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", roleDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        RoleDto roleDtoSaved = roleService.saveRole(roleDto);
        log.info("Entity Role saved successfully {} ", roleDtoSaved);
        //return new ResponseEntity(roleDtoSaved, HttpStatus.CREATED);
        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "Role created successfully");
        map.put("data", roleDtoSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity findRoleById(Long id) {
        Map<String, Object> map = new LinkedHashMap<>();
        RoleDto roleDtoFound = roleService.findRoleById(id);
        log.info("Entity Role found successfully with the id {}", id);
        //return new ResponseEntity(roleDtoFound, HttpStatus.OK);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Role found successfully");
        map.put("data", roleDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findRoleByRolename(String roleName, Long entId) {
        Map<String, Object> map = new LinkedHashMap<>();
        RoleType roleType = RoleType.Dg;
        switch (roleName){
            case "Deliver": roleType = RoleType.Deliver;
            case "Dg": roleType = RoleType.Dg;
            case "Manager": roleType = RoleType.Manager;
            case "Pdg": roleType = RoleType.Pdg;
            case "Saler": roleType = RoleType.Saler;
            case "Storekeeper": roleType = RoleType.Storekeeper;
        }

        RoleDto roleDtoFound = roleService.findRoleByRolename(roleType, entId);
        log.info("Entity Role found successfully with the name {} in the enterprise with id {}", roleName, entId);
        //return new ResponseEntity(roleDtoFound, HttpStatus.OK);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Role found successfully");
        map.put("data", roleDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);

    }

    @Override
    public ResponseEntity deleteRoleById(Long id) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean d= roleService.deleteRoleById(id);
        log.info("role deleted successfully with the ID {}", id);
        //return ResponseEntity.ok(d);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Role successfully deleted");
        map.put("data", d);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteRoleByRolename(String roleName, Long entId) {
        Map<String, Object> map = new LinkedHashMap<>();
        RoleType roleType = RoleType.Dg;
        switch (roleName){
            case "Deliver": roleType = RoleType.Deliver;
            case "Dg": roleType = RoleType.Dg;
            case "Manager": roleType = RoleType.Manager;
            case "Pdg": roleType = RoleType.Pdg;
            case "Saler": roleType = RoleType.Saler;
            case "Storekeeper": roleType = RoleType.Storekeeper;
        }
        Boolean d= roleService.deleteRoleByRolename(roleType, entId);
        log.info("role deleted successfully");
        //return ResponseEntity.ok(d);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Role successfully deleted");
        map.put("data", d);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllRoleOfEnterprise(Long entId) {
        Map<String, Object> map = new LinkedHashMap<>();
        log.info("Liste des roles  d'une entreeprise dont l'id est {} ", entId);
        List<RoleDto> roleDtoList =  roleService.findAllRoleOfEnterprise(entId);
        //return ResponseEntity.ok(roleDtoList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Role of enterprise list successfully found");
        map.put("data", roleDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllRoleofUserBM(Long userbmId) {
        Map<String, Object> map = new LinkedHashMap<>();
        log.info("Liste des roles  d'un user dont l'id est {} ", userbmId);
        List<RoleDto> roleDtoList = roleService.findAllRoleofUserBM(userbmId);
        //return ResponseEntity.ok(roleDtoList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Role of Userbm list successfully found");
        map.put("data", roleDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }
}
