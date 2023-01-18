package com.c2psi.businessmanagement.services.contractsImpl.pos.userbm;

import com.c2psi.businessmanagement.dtos.pos.pos.EnterpriseDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.RoleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMRoleDto;
import com.c2psi.businessmanagement.exceptions.EntityNotFoundException;
import com.c2psi.businessmanagement.exceptions.ErrorCode;
import com.c2psi.businessmanagement.exceptions.InvalidEntityException;
import com.c2psi.businessmanagement.exceptions.NullArgumentException;
import com.c2psi.businessmanagement.models.UserBMRole;
import com.c2psi.businessmanagement.repositories.pos.userbm.UserBMRoleRepository;
import com.c2psi.businessmanagement.services.contracts.pos.userbm.UserBMRoleService;
import com.c2psi.businessmanagement.validators.pos.userbm.UserBMRoleValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value="UserBMRoleService1")
@Slf4j
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
            throw new InvalidEntityException("Le userBMRole passé en argument n'est pas valide",
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
        return Optional.of(UserBMRoleDto.fromEntity(userBMRole.get())).orElseThrow(()->
                new EntityNotFoundException("Aucun userBMRole avec l'id "+id
                        +" n'a été trouve dans la BDD", ErrorCode.USERBM_NOT_FOUND));
    }

    @Override
    public UserBMRoleDto findUserBMRoleByUserBMandRole(UserBMDto userbmDto, RoleDto roleDto) {
       if(userbmDto == null || roleDto == null){
           log.error("Ni le userBM ni le role ne peut etre null lorsqu'on cherche le UserBMRole");
           return null;
       }

        Optional<UserBMRole> userBMRole = userBMRoleRepository.findByUserbmroleUserbmAndUserbmroleRole(
                UserBMDto.toEntity(userbmDto), RoleDto.toEntity(roleDto));

        return Optional.of(UserBMRoleDto.fromEntity(userBMRole.get())).orElseThrow(()->
                new EntityNotFoundException("Aucun userBMRole du user de nom ="+userbmDto.getBmName()
                        +" Associe au role = "+roleDto.getRoleName()
                        +" n'a été trouve dans la BDD ",
                        ErrorCode.USERBMROLE_NOT_FOUND));
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
    public Boolean deleteUserBMRoleById(Long id) {
        Optional<UserBMRole> optionalUserBMRole = Optional.of(UserBMRoleDto.toEntity(this.findUserBMRoleById(id)));
        if(optionalUserBMRole.isPresent()){
            userBMRoleRepository.delete(optionalUserBMRole.get());
            return true;
        }
        return false;
    }
}
