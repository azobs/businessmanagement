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

import java.util.List;

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
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", userBMRoleDto, bindingResult);
            return ResponseEntity.badRequest().body(bindingResult.toString());
        }

        UserBMRoleDto userBMRoleDtoSaved = userBMRoleService.saveUserBMRole(userBMRoleDto);
        log.info("Entity UserBMRole saved successfully {} ", userBMRoleDtoSaved);
        return new ResponseEntity(userBMRoleDtoSaved, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity findUserBMRoleById(Long userBMRoleId) {
        UserBMRoleDto userBMRoleDtoFound = userBMRoleService.findUserBMRoleById(userBMRoleId);
        log.info("Entity UserBMRole founded sucessfully with the id {}", userBMRoleId);
        return new ResponseEntity(userBMRoleDtoFound, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findByUserbmroleUserbmAndUserbmroleRole(Long userbmDtoId, Long roleDtoId) {
        UserBMRoleDto userBMRoleDtoFound = userBMRoleService.findByUserbmroleUserbmAndUserbmroleRole(userbmDtoId,
                roleDtoId);
        log.info("Entity UserBMRole founded sucessfully in the DB");
        return new ResponseEntity(userBMRoleDtoFound, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<UserBMRoleDto>> findAllUserBMRoleofPos(Long posId) {
        List<UserBMRoleDto> userBMRoleDtoList = userBMRoleService.findAllUserBMRoleofPointofsale(posId);
        log.info("List of Entity UserBMRole found successfully");
        return ResponseEntity.ok(userBMRoleDtoList);
    }

    @Override
    public ResponseEntity deleteRoleById(Long userbmroleId) {

        Boolean deleteUserBMRole = userBMRoleService.deleteUserBMRoleById(userbmroleId);
        log.info("UserBMRole deleted successfully");
        return ResponseEntity.ok(deleteUserBMRole);
    }
}
