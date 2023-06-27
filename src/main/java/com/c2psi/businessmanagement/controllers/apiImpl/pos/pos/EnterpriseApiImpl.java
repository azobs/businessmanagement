package com.c2psi.businessmanagement.controllers.apiImpl.pos.pos;


import com.c2psi.businessmanagement.Enumerations.RoleType;
import com.c2psi.businessmanagement.controllers.api.pos.pos.EnterpriseApi;
import com.c2psi.businessmanagement.dtos.pos.pos.EnterpriseDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.RoleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMRoleDto;
import com.c2psi.businessmanagement.exceptions.DuplicateEntityException;
import com.c2psi.businessmanagement.exceptions.EntityNotFoundException;
import com.c2psi.businessmanagement.services.contracts.pos.pos.EnterpriseService;
import com.c2psi.businessmanagement.services.contracts.pos.userbm.RoleService;
import com.c2psi.businessmanagement.services.contracts.pos.userbm.UserBMRoleService;
import com.c2psi.businessmanagement.services.contracts.pos.userbm.UserBMService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class EnterpriseApiImpl implements EnterpriseApi {
    private EnterpriseService entService;
    private UserBMService userBMService;
    private RoleService roleService;
    private UserBMRoleService userBMRoleService;

    @Autowired
    public EnterpriseApiImpl(EnterpriseService entService, UserBMService userBMService, RoleService roleService,
                             UserBMRoleService userBMRoleService){
        this.entService = entService;
        this.userBMService = userBMService;
        this.roleService = roleService;
        this.userBMRoleService = userBMRoleService;
    }


    @Override
    public ResponseEntity saveEnterprise(EnterpriseDto entDto, BindingResult bindingResult) {
        /************************************************************************************
         * Pour enregistrer une Enterprise, il faut le UserBM qui va l'administrer. Ce UserBM
         * doit etre enregistrer au prealable. Donc ce n'est pas l'enregistrement de
         * l'entreprise qui va provoquer celui de son administrateur
         */
        Map<String, Object> map = new LinkedHashMap<>();

        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", entDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        UserBMDto userBMDtoAdminofEntSave = userBMService.saveUserBM(entDto.getEntAdminDto());

        entDto.setEntAdminDto(userBMDtoAdminofEntSave);

        EnterpriseDto enterpriseDtoSaved = entService.saveEnterprise(entDto);

        /***********************************************************************
         * Une fois l'entreprise enregistre il faut donc ajouter le role PGD a
         * cette entreprise puis assigner ce role au userBM admin.
         * Mais avant il faut se rassurer que ce role n'est deja pas enregistre
         * et si cest le cas on recupere plutot le role enregistre
         */
        RoleDto roleDtoFound = roleService.findRoleByRolename(RoleType.Pdg, enterpriseDtoSaved.getId());
        RoleDto roleDtoSaved = null;
        if(roleDtoFound == null){
            RoleDto roleDtoToSave = RoleDto.builder()
                    .roleEntDto(enterpriseDtoSaved)
                    .roleName(RoleType.Pdg)
                    .roleAlias("Super Gerant")
                    .roleDescription("Le gerant de tous les points de vente de l'entreprise en question")
                    .build();

            roleDtoSaved = roleService.saveRole(roleDtoToSave);
        }
        else {
            roleDtoSaved = roleDtoFound;
        }

        /*************************************************************
         * Une fois ce role enregistre il faut l'attribuer au UserBM
         * admin de l'entreprise enregistre
         */
        UserBMRoleDto userBMRoleDtoToSave = UserBMRoleDto.builder()
                .userbmroleRoleDto(roleDtoSaved)
                .userbmroleUserbmDto(userBMDtoAdminofEntSave)
                .userbmroleAttributionDate(new Date().toInstant())
                .build();

        UserBMRoleDto userBMRoleDtoSaved = userBMRoleService.saveUserBMRole(userBMRoleDtoToSave);
        log.info("L'Admin de l'entreprise ainsi enregistre a deja le role PDG {}", userBMRoleDtoSaved);
        log.info("Entity entDto saved successfully {} ", enterpriseDtoSaved);
        //return new ResponseEntity(enterpriseDtoSaved, HttpStatus.CREATED);
        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "Enterprise created successfully with his administrator");
        map.put("data", enterpriseDtoSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity updateEnterprise(EnterpriseDto entDto, BindingResult bindingResult) {
        /****************************************************************
         * On update ici que les donnees de l'entreprise et non ceux
         * des objets lies
         */
        Map<String, Object> map = new LinkedHashMap<>();

        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", entDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        //log.info("Controller: ici apres le test de pre validation");

        EnterpriseDto entDtoUpdated = entService.updateEnterprise(entDto);
        log.info("Entity Enterprise updated successfully {} ", entDtoUpdated);
        //return ResponseEntity.ok(entDtoUpdated);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Enterprise updated successfully");
        map.put("data", entDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity setAdminEnterprise(EnterpriseDto enterpriseDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();

        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", enterpriseDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }
        /*********************************************************************
         * Il faut d'abord rechercher L'entreprise en question et si le role
         * Pdg n'est pas encore ajoute a cette entreprise on doit le faire
         */
        Long entId = enterpriseDto.getId();
        Long userBMAdminId = enterpriseDto.getEntAdminDto().getId();
        EnterpriseDto enterpriseDtoSaved = entService.findEnterpriseById(entId);
        RoleDto roleDtoSaved = null;
        try {
            roleDtoSaved = roleService.findRoleByRolename(RoleType.Pdg, entId);
        }
        catch (EntityNotFoundException e){
            RoleDto roleDtoToSave = RoleDto.builder()
                    .roleEntDto(enterpriseDtoSaved)
                    .roleName(RoleType.Pdg)
                    .roleAlias("Super Gerant")
                    .roleDescription("Le gerant de tous les points de vente de l'entreprise en question")
                    .build();

            roleDtoSaved = roleService.saveRole(roleDtoToSave);
        }

        /*********************************************************************
         * Il faut maintenant rechercher le UserBM indique pour l'attribuer le
         * role Pdg
         */
        UserBMDto userBMDtoSaved = userBMService.findUserBMById(userBMAdminId);

        EnterpriseDto entDtoUpdated = entService.setAdminEnterprise(entId, userBMAdminId);

        /**********************************************************************
         * Maintenant on attribue le role roleDtoSaved au user userBMDtoSaved
         * si il ne l'a pas encore
         */
        try{
            UserBMRoleDto userBMRoleDtoToSave = UserBMRoleDto.builder()
                    .userbmroleRoleDto(roleDtoSaved)
                    .userbmroleUserbmDto(userBMDtoSaved)
                    .userbmroleAttributionDate(new Date().toInstant())
                    .build();
            userBMRoleService.saveUserBMRole(userBMRoleDtoToSave);
        }
        catch (DuplicateEntityException e){
            log.warn("The precised UserBM has already the normal role");
            //Si il a deja ce role alors on affiche cet avertissement mais ce n'est pas une erreur
        }

        log.info("Admin of enterprise setted successfully {} ", entDtoUpdated);
        //return ResponseEntity.ok(entDtoUpdated);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Admin Enterprise updated successfully");
        map.put("data", entDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findEnterpriseById(Long entId) {
        Map<String, Object> map = new LinkedHashMap<>();
        EnterpriseDto entDtoFound = entService.findEnterpriseById(entId);
        log.info("Entity Enterprise found successfully {} with the ID {}", entDtoFound, entId);
        //return ResponseEntity.ok(entDto);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Enterprise found successfully by its Id");
        map.put("data", entDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);

    }

    @Override
    public ResponseEntity findEnterpriseByName(String entName) {
        Map<String, Object> map = new LinkedHashMap<>();
        EnterpriseDto entDtoFound = entService.findEnterpriseByName(entName);
        log.info("Entity Enterprise found successfully {} with the Name {}", entDtoFound, entName);
        //return ResponseEntity.ok(entDto);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Enterprise found successfully by its Name");
        map.put("data", entDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findEnterpriseByNiu(String entNiu) {
        Map<String, Object> map = new LinkedHashMap<>();
        EnterpriseDto entDtoFound = entService.findEnterpriseByNiu(entNiu);
        log.info("Entity Enterprise found successfully {} with the Niu {}", entDtoFound, entNiu);
        //return ResponseEntity.ok(entDto);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Enterprise found successfully by its Niu");
        map.put("data", entDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteEnterpriseById(Long entId) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean deleteEntById =  entService.deleteEnterpriseById(entId);
        log.info("Enterprise deleted successfully {} with the Id {}", deleteEntById, entId);
        //return ResponseEntity.ok(deleteEntById);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Enterprise deleted successfully by Id");
        map.put("data", deleteEntById);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteEnterpriseByName(String entName) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean deleteEntByName =  entService.deleteEnterpriseByName(entName);
        log.info("Enterprise deleted successfully {} with the Id {}", deleteEntByName, entName);
        //return ResponseEntity.ok(deleteEntByName);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Enterprise deleted successfully by Name");
        map.put("data", deleteEntByName);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteEnterpriseByNiu(String entNiu) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean deleteEntByNiu =  entService.deleteEnterpriseByNiu(entNiu);
        log.info("Enterprise deleted successfully {} with the Id {}", deleteEntByNiu, entNiu);
        //return ResponseEntity.ok(deleteEntByNiu);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Enterprise deleted successfully by Id");
        map.put("data", deleteEntByNiu);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllEnterprise() {
        Map<String, Object> map = new LinkedHashMap<>();
        List<EnterpriseDto> listofEnterpriseDtoFound =  entService.findAllEnterprise();
        log.info("List of Entity Enterprise found successfully: {}", listofEnterpriseDtoFound);
        //return ResponseEntity.ok(listofEnterpriseDtoFound);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "List of entreprise found successfully by Id");
        map.put("data", listofEnterpriseDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllPosofEnterprise(Long entId) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<PointofsaleDto> listofPointofsaleDtoFound =  entService.findAllPosofEnterprise(entId);
        log.info("List of Pointofsale of an enterprise found successfully: {}", listofPointofsaleDtoFound);
        //return ResponseEntity.ok(listofPointofsaleDtoFound);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "List of pointofsale of an enterprise found successfully by Id");
        map.put("data", listofPointofsaleDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity getTurnover(Long entId, Date startDate, Date endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        BigDecimal turnover =  entService.getTurnover(entId, startDate, endDate);
        log.info("Turnover of the enterprise compute successfully: {}", turnover);
        //return ResponseEntity.ok(turnover);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Enterprise turnover computed successfully by Id");
        map.put("data", turnover);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllEmployeofEnterprise(Long entId) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<UserBMDto> listofUserBMDtoFound =  entService.findAllEmployeofEnterprise(entId);
        log.info("List of UserBM of an enterprise found successfully: {}", listofUserBMDtoFound);
        //return ResponseEntity.ok(listofUserBMDtoFound);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "List of employe of an enterprise  found successfully by Id");
        map.put("data", listofUserBMDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    /*@Override
    public ResponseEntity<List<ProviderDto>> findAllProviderofEnterprise(Long entId) {
        try{
            List<ProviderDto> listofProviderDtoFound =  entService.findAllProviderofEnterprise(entId);
            log.info("List of Provider of an enterprise found successfully: {}", listofProviderDtoFound);
            return ResponseEntity.ok(listofProviderDtoFound);
        }
        catch(BMException bme){
            log.info("An error occured during the finding list process with the code {} and the message {} ",
                    bme.getErrorCode(), bme.getMessage());
            System.err.println("An error occured during the finding list process "+bme.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }*/

    @Override
    public ResponseEntity getTotalCash(Long entId) {
        Map<String, Object> map = new LinkedHashMap<>();
        BigDecimal totalcash =  entService.getTotalCash(entId);
        log.info("totaldamage of the enterprise compute successfully: {}", totalcash);
        //return ResponseEntity.ok(totalcash);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Total cash of enterprise  found successfully by Id");
        map.put("data", totalcash);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity getNumberofDamage(Long entId) {
        Map<String, Object> map = new LinkedHashMap<>();
        BigDecimal totaldamage =  entService.getNumberofDamage(entId);
        log.info("totaldamage of the enterprise compute successfully: {}", totaldamage);
        //return ResponseEntity.ok(totaldamage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Total damage of enterprise  found successfully by Id");
        map.put("data", totaldamage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity getNumberofDamage(Long entId, Long artId) {
        Map<String, Object> map = new LinkedHashMap<>();
        BigDecimal totaldamage =  entService.getNumberofDamage(entId, artId);
        log.info("totaldamage of the enterprise compute successfully: {}", totaldamage);
        //return ResponseEntity.ok(totaldamage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Total damage of enterprise  found successfully by Id");
        map.put("data", totaldamage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity getNumberofCapsule(Long entId) {
        Map<String, Object> map = new LinkedHashMap<>();
        BigDecimal totalcapsule =  entService.getNumberofCapsule(entId);
        log.info("totalcapsule of the enterprise compute successfully: {}", totalcapsule);
        //return ResponseEntity.ok(totalcapsule);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Total Capsule of enterprise  found successfully by Id");
        map.put("data", totalcapsule);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity getNumberofCapsule(Long entId, Long artId) {
        Map<String, Object> map = new LinkedHashMap<>();
        BigDecimal totalcapsule =  entService.getNumberofCapsule(entId, artId);
        log.info("totalcapsule of the enterprise compute successfully: {}", totalcapsule);
        //return ResponseEntity.ok(totalcapsule);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Total capsule of enterprise  found successfully by Id");
        map.put("data", totalcapsule);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);

    }

    @Override
    public ResponseEntity getNumberofPackaging(Long entId) {
        Map<String, Object> map = new LinkedHashMap<>();
        BigDecimal totalpackaging =  entService.getNumberofPackaging(entId);
        log.info("totalpackaging of the enterprise compute successfully: {}", totalpackaging);
        //return ResponseEntity.ok(totalpackaging);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Total method getNumberofPackaging found successfully by Id");
        map.put("data", totalpackaging);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity getNumberofPackaging(Long entId, Long providerId) {
        Map<String, Object> map = new LinkedHashMap<>();
        BigDecimal totalpackaging =  entService.getNumberofPackaging(entId, providerId);
        log.info("totalpackaging of the enterprise compute successfully: {}", totalpackaging);
        //return ResponseEntity.ok(totalpackaging);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Total number of packaging  found successfully by Id");
        map.put("data", totalpackaging);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }
}
