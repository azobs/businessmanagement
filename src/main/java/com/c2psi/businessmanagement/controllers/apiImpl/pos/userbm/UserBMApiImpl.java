package com.c2psi.businessmanagement.controllers.apiImpl.pos.userbm;

import com.c2psi.businessmanagement.Enumerations.RoleType;
import com.c2psi.businessmanagement.Enumerations.UserBMType;
import com.c2psi.businessmanagement.controllers.api.pos.userbm.UserBMApi;
import com.c2psi.businessmanagement.dtos.pos.pos.EnterpriseDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.RoleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMRoleDto;
import com.c2psi.businessmanagement.exceptions.EntityNotFoundException;
import com.c2psi.businessmanagement.services.contracts.pos.pos.EnterpriseService;
import com.c2psi.businessmanagement.services.contracts.pos.userbm.RoleService;
import com.c2psi.businessmanagement.services.contracts.pos.userbm.UserBMRoleService;
import com.c2psi.businessmanagement.services.contracts.pos.userbm.UserBMService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

@RestController
@Slf4j
public class UserBMApiImpl implements UserBMApi {
    private UserBMService userBMService;
    private RoleService roleService;
    private UserBMRoleService userBMRoleService;
    private EnterpriseService enterpriseService;

    @Autowired
    public UserBMApiImpl(UserBMService userBMService1, RoleService roleService, UserBMRoleService userBMRoleService,
                         EnterpriseService enterpriseService){
        this.userBMService = userBMService1;
        this.roleService = roleService;
        this.userBMRoleService = userBMRoleService;
        this.enterpriseService = enterpriseService;
    }

    @Override
    public ResponseEntity saveUserBM(UserBMDto userBMDto, BindingResult bindingResult){

        Map<String, Object> map = new LinkedHashMap<>();

        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", userBMDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        /********************************************************
         * Si le UserBM est un ADMIN ou un ADMIN_ENTERPRISE alors
         * il faut enregistrer les roles respectifs
         */
        Optional<RoleDto> optionalRoleDtoToAssign = Optional.ofNullable(null);
        if(userBMDto.getBmUsertype().equals(UserBMType.AdminBM)){
            /********************************************************
             * Alors il faut enregistrer ou recuperer le role ADMIN
             * car il sera attribue au UserBM qu'on va ajouter
             */
            try{
                optionalRoleDtoToAssign = Optional.ofNullable(roleService.findRoleByRolename(RoleType.ADMIN));
            }
            catch (EntityNotFoundException e){
                /*********************************************
                 * Si il n'existe pas alors il faut le creer *
                 *********************************************/
                RoleDto roleDtoToSave = RoleDto.builder()
                        .roleName(RoleType.ADMIN)
                        .roleAlias("ROLE_ADMINBM")
                        .roleDescription("Role permettant d'avoir le droit de configurer l'application et la gerer ")
                        .roleEntDto(null)
                        .build();

                optionalRoleDtoToAssign = Optional.ofNullable(roleService.saveRole(roleDtoToSave));
            }
        }
        else if(userBMDto.getBmUsertype().equals(UserBMType.AdminEnterprise)){
            /*******************************************************************
             * Alors il faut enregistrer ou recuperer le role ADMIN_ENTERPRISE
             * car il sera attribue au UserBM qu'on va ajouter
             */
            try{
                optionalRoleDtoToAssign = Optional.ofNullable(roleService.findRoleByRolename(RoleType.ADMIN_ENTERPRISE));
            }
            catch (EntityNotFoundException e) {
                /*********************************************
                 * Si il n'existe pas alors il faut le creer *
                 *********************************************/
                RoleDto roleDtoToSave = RoleDto.builder()
                        .roleName(RoleType.ADMIN_ENTERPRISE)
                        .roleAlias("ROLE_ADMIN_ENTERPRISE")
                        .roleDescription("Role permettant d'avoir le droit de gerer les employes et les taches dans une " +
                                "entreprise")
                        .roleEntDto(null)
                        .build();

                optionalRoleDtoToAssign = Optional.ofNullable(roleService.saveRole(roleDtoToSave));
            }
        }

        if(!optionalRoleDtoToAssign.isPresent()){
            map.clear();
            map.put("status", HttpStatus.SERVICE_UNAVAILABLE);
            map.put("message", "Service de creation des roles indisponible");
            map.put("data", "Erreur inconnue lors de la creation du role a assigner au UserBM");
            map.put("cause", "Service de creation des roles indisponible");
            return new ResponseEntity(map, HttpStatus.SERVICE_UNAVAILABLE);
        }
        /*****************************************************************************
         * Si on est a ce niveau alors le role qu'il faut au UserBM a bien ete cree.
         * On ajoute donc le UserBM en BD.
         */

        /**********************************************************
         * Un UserBM contient une fois son adresse donc pas besoin
         * d'enregistrer separrement l'adresse du UserBM.
         * Que ce soit le userBM d'un Pos ou pas
         */

        UserBMDto userBMDtoSaved = userBMService.saveUserBM(userBMDto);
        log.info("Entity UserBM saved successfully {} ", userBMDtoSaved);

        /****************************************************************
         * On doit donc l'associer le role qu'il lui faut
         */

        UserBMRoleDto userBMRoleDtoToSave = UserBMRoleDto.builder()
                .userbmroleRoleDto(optionalRoleDtoToAssign.get())
                .userbmroleUserbmDto(userBMDtoSaved)
                .userbmroleAttributionDate(new Date().toInstant())
                .build();

        UserBMRoleDto userBMRoleDtoSaved = userBMRoleService.saveUserBMRole(userBMRoleDtoToSave);

        if(Optional.ofNullable(userBMRoleDtoSaved).isPresent()){
            map.clear();
            map.put("status", HttpStatus.CREATED);
            map.put("message", "UserBM created successfully with his role");
            map.put("data", userBMDtoSaved);
            map.put("cause", "RAS");
            return new ResponseEntity(map, HttpStatus.CREATED);
        }

        if(userBMDtoSaved.getBmUsertype().equals(UserBMType.Employe)){
            map.clear();
            map.put("status", HttpStatus.CREATED);
            map.put("message", "UserBM created successfully without any role. An AdminEnterprise must assign him a role");
            map.put("data", userBMDtoSaved);
            map.put("cause", "RAS");
            return new ResponseEntity(map, HttpStatus.CREATED);
        }
        /****
         * Si on arrive ici et qu'on retourne ce null alors il y a eu probleme sur le
         * type de userbm qui n'a ete ni ADMINBM ni ADMIN_ENTERPRISE ni EMPLOYE
         */
        return null;
    }


    @Override
    public ResponseEntity updateUserBM(UserBMDto userBMDto, BindingResult bindingResult){
        Map<String, Object> map = new LinkedHashMap<>();

        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", userBMDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        UserBMDto userBMDtoUpdated = userBMService.updateUserBM(userBMDto);
        log.info("Entity UserBM updated successfully {} ", userBMDtoUpdated);
        //return new ResponseEntity(userBMDtoUpdated, HttpStatus.OK);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "UserBM updated successfully");
        map.put("data", userBMDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }


    @Override
    public ResponseEntity switchUserBMState(UserBMDto userBMDto, BindingResult bindingResult){
        Map<String, Object> map = new LinkedHashMap<>();

        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", userBMDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        UserBMDto userBMDtoUpdated = userBMService.switchUserBMState(userBMDto);
        log.info("switchUserBMState is normaly executed and the Entity UserBM updated successfully {} ", userBMDtoUpdated);
        //return new ResponseEntity(userBMDtoUpdated, HttpStatus.OK);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "UserBM updated successfully");
        map.put("data", userBMDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity resetPassword(UserBMDto userBMDto, BindingResult bindingResult){
        Map<String, Object> map = new LinkedHashMap<>();

        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", userBMDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        UserBMDto userBMDtoUpdated = userBMService.resetPassword(userBMDto);
        log.info("resetPassword is normaly executed and the Entity UserBM updated successfully {} ", userBMDtoUpdated);
        //return new ResponseEntity(userBMDtoUpdated, HttpStatus.OK);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Password of UserBM updated successfully");
        map.put("data", userBMDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }


    @Override
    public ResponseEntity findUserBMByLogin(String bmLogin){
        Map<String, Object> map = new LinkedHashMap<>();

        UserBMDto userBMDtoFound = userBMService.findUserBMByLogin(bmLogin);
        log.info("Entity UserBM found successfully {} with the login {}", userBMDtoFound, bmLogin);
        //return new ResponseEntity(userBMDtoFound, HttpStatus.OK);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "UserBM is found successfully");
        map.put("data", userBMDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findUserBMByCni(String bmCni){
        Map<String, Object> map = new LinkedHashMap<>();

        UserBMDto userBMDtoFound = userBMService.findUserBMByCni(bmCni);
        log.info("Entity UserBM found successfully {} with the cni number {}", userBMDtoFound, bmCni);
        //return new ResponseEntity(userBMDtoFound, HttpStatus.OK);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "UserBM is found successfully");
        map.put("data", userBMDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findUserBMByEmail(String bmEmail){
        Map<String, Object> map = new LinkedHashMap<>();

        UserBMDto userBMDtoFound = userBMService.findUserBMByEmail(bmEmail);
        log.info("Entity UserBM found successfully {} with the email address {}", userBMDtoFound, bmEmail);
        //return new ResponseEntity(userBMDtoFound, HttpStatus.OK);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "UserBM is found successfully");
        map.put("data", userBMDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findUserBMByFullNameAndDob(String bmName, String bmSurname,
                                                                Date bmDob){
        Map<String, Object> map = new LinkedHashMap<>();

        UserBMDto userBMDtoFound = userBMService.findUserBMByFullNameAndDob(bmName, bmSurname, bmDob);
        log.info("Entity UserBM found successfully {} with the name {} " +
                ", the surname {} and the dob {}", userBMDtoFound, bmName, bmSurname, bmDob);
        //return new ResponseEntity(userBMDtoFound, HttpStatus.OK);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "UserBM is found successfully");
        map.put("data", userBMDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }


    @Override
    public ResponseEntity findUserBMById(Long bmId){
        Map<String, Object> map = new LinkedHashMap<>();

        UserBMDto userBMDtoFound = userBMService.findUserBMById(bmId);
        log.info("Entity UserBM found successfully {} with the ID {}", userBMDtoFound, bmId);
        //return new ResponseEntity(userBMDtoFound, HttpStatus.OK);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "UserBM is found successfully");
        map.put("data", userBMDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllByUserBMType(UserBMType bmUsertype){
        Map<String, Object> map = new LinkedHashMap<>();

        List<UserBMDto> listofUserBMDtoFound = userBMService.findAllByUserBMType(bmUsertype);
        log.info("List of Entity UserBM found successfully {} with the type {}", listofUserBMDtoFound, bmUsertype);
        //return ResponseEntity.ok(listofUserBMDtoFound);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "UserBM list is found successfully");
        map.put("data", listofUserBMDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteUserBMByLogin(String bmLogin){
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean deleteUserBM = userBMService.deleteUserBMByLogin(bmLogin);
        log.info("UserBM deleted successfully {} with the login {}", deleteUserBM, bmLogin);
        //return ResponseEntity.ok(deleteUserBM);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "UserBM is deleted successfully");
        map.put("data", deleteUserBM);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteUserBMByCni(String bmCni){
        Map<String, Object> map = new LinkedHashMap<>();

        Boolean deleteUserBM = userBMService.deleteUserBMByCni(bmCni);
        log.info("UserBM deleted successfully {} with the Cni {}", deleteUserBM, bmCni);
        //return ResponseEntity.ok(deleteUserBM);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "UserBM is deleted successfully");
        map.put("data", deleteUserBM);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteUserBMByFullNameAndDob(String bmName, String bmSurname, Date bmDob){
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean deleteUserBM = userBMService.deleteUserBMByFullNameAndDob(bmName, bmSurname, bmDob);
        log.info("UserBM deleted successfully {} with the name {}, the surname {} and the dob {} ",
                deleteUserBM, bmName, bmSurname, bmDob);
        //return ResponseEntity.ok(deleteUserBM);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "UserBM is deleted successfully");
        map.put("data", deleteUserBM);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteUserBMById(Long bmId){
        Map<String, Object> map = new LinkedHashMap<>();

        Boolean deleteUserBM = userBMService.deleteUserBMById(bmId);
        log.info("UserBM deleted successfully {} with the ID {}", deleteUserBM, bmId);
        //return ResponseEntity.ok(deleteUserBM);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "UserBM is deleted successfully");
        map.put("data", deleteUserBM);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllUserBM(){
        Map<String, Object> map = new LinkedHashMap<>();

        List<UserBMDto> listofUserBMDtoFound = userBMService.findAllUserBM();
        log.info("List of Entity UserBM found successfully: {}", listofUserBMDtoFound);
        //return ResponseEntity.ok(listofUserBMDtoFound);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "UserBM list is found successfully");
        map.put("data", listofUserBMDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllUserBMofPos(Long idPos){
        Map<String, Object> map = new LinkedHashMap<>();

        List<UserBMDto> listofUserBMDtoFound = userBMService.findAllUserBMofPos(idPos);
        log.info("List of Entity UserBM in a pointofsale found successfully {}", listofUserBMDtoFound);
        //return ResponseEntity.ok(listofUserBMDtoFound);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "UserBM list is found successfully");
        map.put("data", listofUserBMDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageUserBMofPos(
            Long idPos, Optional<String> optsample, Optional<Integer> optpagenum, Optional<Integer> optpagesize){

        Map<String, Object> map = new LinkedHashMap<>();

        String sample = optsample.isPresent()?optsample.get():"";
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<UserBMDto> pageofUserBMDtoFound = userBMService.findAllUserBMofPos(idPos,
                sample, pagenum, pagesize);
        log.info("Page of Entity UserBM in a pointofsale containing {} found successfully {}", sample, pageofUserBMDtoFound);
        //return ResponseEntity.ok(pageofUserBMDtoFound);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "UserBM page is found successfully");
        map.put("data", pageofUserBMDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllUserBMContaining(
            Optional<String> optsample, Optional<Integer> optpagenum, Optional<Integer> optpagesize){

        Map<String, Object> map = new LinkedHashMap<>();

        String sample = optsample.isPresent()?optsample.get():"";
        sample = "%"+sample+"%";
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<UserBMDto> pageofUserBMDtoFound = userBMService.findAllUserBMContaining(sample,
                pagenum, pagesize);
        log.info("Page of Entity UserBM in a pointofsale containing {} found successfully {}", sample, pageofUserBMDtoFound);
        //return ResponseEntity.ok(pageofUserBMDtoFound);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "UserBM page is found successfully");
        map.put("data", pageofUserBMDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

}
