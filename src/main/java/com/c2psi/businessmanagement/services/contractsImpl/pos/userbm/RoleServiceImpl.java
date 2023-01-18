package com.c2psi.businessmanagement.services.contractsImpl.pos.userbm;

import com.c2psi.businessmanagement.dtos.pos.pos.EnterpriseDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.RoleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMRoleDto;
import com.c2psi.businessmanagement.exceptions.EntityNotFoundException;
import com.c2psi.businessmanagement.exceptions.ErrorCode;
import com.c2psi.businessmanagement.exceptions.IllegalArgumentException;
import com.c2psi.businessmanagement.exceptions.InvalidEntityException;
import com.c2psi.businessmanagement.exceptions.NullArgumentException;
import com.c2psi.businessmanagement.models.Enterprise;
import com.c2psi.businessmanagement.models.Role;
import com.c2psi.businessmanagement.models.UserBM;
import com.c2psi.businessmanagement.repositories.pos.pos.EnterpriseRepository;
import com.c2psi.businessmanagement.repositories.pos.userbm.RoleRepository;
import com.c2psi.businessmanagement.repositories.pos.userbm.UserBMRepository;
import com.c2psi.businessmanagement.repositories.pos.userbm.UserBMRoleRepository;
import com.c2psi.businessmanagement.services.contracts.pos.userbm.RoleService;
import com.c2psi.businessmanagement.validators.pos.userbm.RoleValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value="RoleServiceV1")
@Slf4j
public class RoleServiceImpl  implements RoleService {

    private RoleRepository roleRepository;
    private UserBMRoleRepository userBMRoleRepository;
    private EnterpriseRepository entRepository;
    private UserBMRepository userBMRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository,
                           UserBMRoleRepository userBMRoleRepository,
                           EnterpriseRepository entRepository,
                           UserBMRepository userBMRepository)
    {
        this.roleRepository = roleRepository;
        this.userBMRoleRepository = userBMRoleRepository;
        this.entRepository = entRepository;
        this.userBMRepository = userBMRepository;
    }

    @Override
    public RoleDto saveRole(RoleDto role_dto) {
        List<String> errors = RoleValidator.validate(role_dto);
        if(!errors.isEmpty()){
            log.error("Entity role not valid {}", role_dto);
            throw new InvalidEntityException("Le role passé en argument n'est pas valide",
                    ErrorCode.ROLE_NOT_VALID, errors);
        }
        //Il faut d'abord verifier que le role sera unique dans l'entreprise pour lequel le role est ajoute dans la bd
        return RoleDto.fromEntity(
                roleRepository.save(
                        RoleDto.toEntity(role_dto)
                )
        );
    }

    @Override
    public RoleDto findRoleById(Long id) {
        if(id==null){
            log.error("Role ID is null");
            throw new NullArgumentException("L'id specifie est null");
        }
        Optional<Role> role = roleRepository.findById(id);
        return Optional.of(RoleDto.fromEntity(role.get())).orElseThrow(()->
                new EntityNotFoundException("Aucun role avec l'id "+id
                        +" n'a été trouve dans la BDD", ErrorCode.ROLE_NOT_FOUND));
    }

    @Override
    public RoleDto findRoleByRolename(String roleName, Long entId) {
        if(!StringUtils.hasLength(roleName) || entId == null){
            log.error("Role name is null");
            throw new NullArgumentException("L'un des parametres passe a la methode est null");
        }
        Optional<Enterprise> ent = entRepository.findEnterpriseById(entId);
        if(!ent.isPresent()){
            log.error("entId does not match with any enterprise in the database");
            throw new IllegalArgumentException("le entId precise ne match avec aucune entreprise dans la BD");
        }
        Optional<Role> optionalRole = roleRepository.findRoleByRoleNameAndRoleEnt(roleName,
                EnterpriseDto.toEntity(EnterpriseDto.fromEntity(ent.get())));

        return Optional.of(RoleDto.fromEntity(optionalRole.get())).orElseThrow(()->
                new EntityNotFoundException("Aucun role avec le nom ="+roleName
                        +" n'a été trouve dans la BDD pour l'entreprise "+
                        EnterpriseDto.toEntity(EnterpriseDto.fromEntity(ent.get())).getEntName(),
                        ErrorCode.ROLE_NOT_FOUND));
    }

    @Override
    public Boolean deleteRoleById(Long id) {
        if(id==null){
            log.error("Role ID is null");
            throw new NullArgumentException("L'id specifie est null");
        }
        Optional<Role> optionalRole = Optional.of(RoleDto.toEntity(this.findRoleById(id)));
        if(optionalRole.isPresent()){
            roleRepository.delete(optionalRole.get());
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteRoleByRolename(String roleName, Long entId) {
        if(!StringUtils.hasLength(roleName) || entId == null){
            log.error("Role name is null or entId is null");
            throw new NullArgumentException("Le roleName ou le enterprise Id specifie est null");
        }
        Optional<Role> optionalRole = Optional.of(RoleDto.toEntity(this.findRoleByRolename(roleName, entId)));
        if(optionalRole.isPresent()){
            roleRepository.delete(optionalRole.get());
            return true;
        }
        return false;
    }

    @Override
    public List<RoleDto> findAllRoleOfEnterprise(Long entId) {
        if(entId == null){
            log.error("enterprise id is null");
            throw new NullArgumentException("L'Id de enterprise passe a la methode est null");
        }
        Optional<Enterprise> ent = entRepository.findEnterpriseById(entId);
        if(!ent.isPresent()){
            log.error("entId does not match with any enterprise in the database");
            throw new IllegalArgumentException("le entId precise ne match avec aucune entreprise dans la BD");
        }
        return roleRepository.findAllByRoleEnt(ent.get()).stream()
                .map(RoleDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoleDto> findAllRoleofUserBM(Long userbmId) {

        if(userbmId == null){
            log.error("userbmId precised id is null");
            throw new NullArgumentException("L'Id du Userbm passe a la methode est null");
        }

        Optional<UserBM> userbm = userBMRepository.findUserBMById(userbmId);
        if(!userbm.isPresent()){
            log.error("userbmId does not match with any userbm in the database");
            throw new IllegalArgumentException("le userbmId precise ne match avec aucun userbm dans la BD");
        }

        List<UserBMRoleDto> listofUserBMRole = userBMRoleRepository
                .findAllByUserbmroleUserbm(userbm.get()).stream()
                .map(UserBMRoleDto::fromEntity)
                .collect(Collectors.toList());
        List<RoleDto> listofRoleDto = new ArrayList<>();
        for(UserBMRoleDto userbm_role : listofUserBMRole){
            if(userbm_role.getUserbmroleRoleDto() != null) {
                listofRoleDto.add(userbm_role.getUserbmroleRoleDto());
            }
        }
        return listofRoleDto;
    }
}
