package com.c2psi.businessmanagement.controllers.apiImpl.pos.userbm;

import com.c2psi.businessmanagement.controllers.api.pos.userbm.UserBMRoleApi;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMRoleDto;
import com.c2psi.businessmanagement.services.contracts.pos.userbm.UserBMRoleService;
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
public class UserBMRoleApiImpl implements UserBMRoleApi {
    private UserBMRoleService userBMRoleService;

    @Autowired
    public UserBMRoleApiImpl(UserBMRoleService userBMRoleService) {
        this.userBMRoleService = userBMRoleService;
    }

    @Override
    public ResponseEntity saveUserBMRole(UserBMRoleDto userBMRoleDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", userBMRoleDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        UserBMRoleDto userBMRoleDtoSaved = userBMRoleService.saveUserBMRole(userBMRoleDto);
        log.info("Entity UserBMRole saved successfully {} ", userBMRoleDtoSaved);
        //return new ResponseEntity(userBMRoleDtoSaved, HttpStatus.CREATED);
        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "UserBM created successfully ");
        map.put("data", userBMRoleDtoSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity findUserBMRoleById(Long userBMRoleId) {
        Map<String, Object> map = new LinkedHashMap<>();

        UserBMRoleDto userBMRoleDtoFound = userBMRoleService.findUserBMRoleById(userBMRoleId);
        log.info("Entity UserBMRole founded sucessfully with the id {}", userBMRoleId);
        //return new ResponseEntity(userBMRoleDtoFound, HttpStatus.OK);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "UserBM found successfully ");
        map.put("data", userBMRoleDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findByUserbmroleUserbmAndUserbmroleRole(Long userbmDtoId, Long roleDtoId) {
        Map<String, Object> map = new LinkedHashMap<>();
        UserBMRoleDto userBMRoleDtoFound = userBMRoleService.findByUserbmroleUserbmAndUserbmroleRole(userbmDtoId,
                roleDtoId);
        log.info("Entity UserBMRole founded sucessfully in the DB");
        //return new ResponseEntity(userBMRoleDtoFound, HttpStatus.OK);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "UserBM found successfully ");
        map.put("data", userBMRoleDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<UserBMRoleDto>> findAllUserBMRoleofPos(Long posId) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<UserBMRoleDto> userBMRoleDtoList = userBMRoleService.findAllUserBMRoleofPointofsale(posId);
        log.info("List of Entity UserBMRole found successfully");
        //return ResponseEntity.ok(userBMRoleDtoList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "UserBM list found successfully ");
        map.put("data", userBMRoleDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteRoleById(Long userbmroleId) {
        Map<String, Object> map = new LinkedHashMap<>();

        Boolean deleteUserBMRole = userBMRoleService.deleteUserBMRoleById(userbmroleId);
        log.info("UserBMRole deleted successfully");
        //return ResponseEntity.ok(deleteUserBMRole);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "UserBM deleted successfully ");
        map.put("data", deleteUserBMRole);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }
}
