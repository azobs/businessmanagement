package com.c2psi.businessmanagement.services.contractsImpl.pos.userbm;

import com.c2psi.businessmanagement.dtos.pos.pos.EnterpriseDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.RoleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMRoleDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.UserBMRole;
import com.c2psi.businessmanagement.repositories.pos.userbm.UserBMRoleRepository;
import com.c2psi.businessmanagement.services.contracts.pos.userbm.UserBMRoleService;
import com.c2psi.businessmanagement.validators.pos.userbm.UserBMRoleValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value="UserBMRoleService1")
@Slf4j
@Transactional
public class UserBMRoleServiceImpl implements UserBMRoleService {
    private UserBMRoleRepository userBMRoleRepository;

    @Autowired
    public UserBMRoleServiceImpl(UserBMRoleRepository userBMRoleRepository) {
        this.userBMRoleRepository = userBMRoleRepository;
    }

    @Override
    public UserBMRoleDto saveUserBMRole(UserBMRoleDto userbmRoleDto) {
        List<String> errors = UserBMRoleValidator.validate(userbmRoleDto);
        if(!errors.isEmpty()){
            log.error("Entity userBMrole not valid {}", userbmRoleDto);
            throw new InvalidEntityException("Le userBMRole passe en argument n'est pas valide",
                    ErrorCode.USERBMROLE_NOT_VALID, errors);
        }
        return UserBMRoleDto.fromEntity(
                userBMRoleRepository.save(
                        UserBMRoleDto.toEntity(userbmRoleDto)
                )
        );
    }

    @Override
    public UserBMRoleDto findUserBMRoleById(Long id) {
        if(id==null){
            log.error("UserBMRole ID is null");
            throw new NullArgumentException("L'id specifie est null");
        }
        Optional<UserBMRole> userBMRole = userBMRoleRepository.findById(id);

        if(!userBMRole.isPresent()){
            throw  new EntityNotFoundException("Aucun userBMRole avec l'id "+id
                    +" n'a ete trouve dans la BDD", ErrorCode.USERBM_NOT_FOUND);
        }
        return UserBMRoleDto.fromEntity(userBMRole.get());
    }

    @Override
    public UserBMRoleDto findUserBMRoleByUserBMandRole(UserBMDto userbmDto, RoleDto roleDto) {
       if(userbmDto == null || roleDto == null){
           log.error("Ni le userBM ni le role ne peut etre null lorsqu'on cherche le UserBMRole");
           throw new NullArgumentException("Les parametres de la recherche sont nulls");
       }

        Optional<UserBMRole> userBMRole = userBMRoleRepository.findByUserbmroleUserbmAndUserbmroleRole(
                UserBMDto.toEntity(userbmDto), RoleDto.toEntity(roleDto));

        if(!userBMRole.isPresent()){
            throw  new EntityNotFoundException("Aucun userBMRole du user de nom ="+userbmDto.getBmName()
                    +" Associe au role = "+roleDto.getRoleName()
                    +" n'a ete trouve dans la BDD ",
                    ErrorCode.USERBMROLE_NOT_FOUND);
        }
        return UserBMRoleDto.fromEntity(userBMRole.get());
    }

    @Override
    public List<UserBMRoleDto> findAllUserBMRoleofEnterprise(EnterpriseDto entDto) {
        return userBMRoleRepository.findAllUserBMRoleofEnterprise(entDto.getId()).stream()
                .map(UserBMRoleDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserBMRoleDto> findAllUserBMRoleofPointofsale(PointofsaleDto posDto) {
        return userBMRoleRepository.findAllUserBMRoleofPointofsale(posDto.getId()).stream()
                .map(UserBMRoleDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean isUserBMRoleDeleteable(Long id) {
        return true;
    }

    @Override
    public Boolean deleteUserBMRoleById(Long id) {
        System.out.println("long id delete = "+id);
        Optional<UserBMRole> optionalUserBMRole = Optional.of(UserBMRoleDto.toEntity(this.findUserBMRoleById(id)));
        if(optionalUserBMRole.isPresent()){
            if(isUserBMRoleDeleteable(id)){
                userBMRoleRepository.delete(optionalUserBMRole.get());
                return true;
            }
            log.error("The entity {} is not deleteable because it encompasses some other elements ", optionalUserBMRole.get());
            throw new EntityNotDeleteableException("Ce userrole ne peut etre supprime ",
                    ErrorCode.USERBMROLE_NOT_DELETEABLE);
        }
        throw new EntityNotFoundException("Aucun userrole n'existe avec l'id = "+id);
    }
}
