package com.c2psi.businessmanagement.services.contractsImpl.pos.userbm;

import com.c2psi.businessmanagement.Enumerations.RoleType;
import com.c2psi.businessmanagement.Enumerations.UserBMType;
import com.c2psi.businessmanagement.dtos.pos.pos.EnterpriseDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.RoleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMRoleDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.Pointofsale;
import com.c2psi.businessmanagement.models.Role;
import com.c2psi.businessmanagement.models.UserBM;
import com.c2psi.businessmanagement.models.UserBMRole;
import com.c2psi.businessmanagement.repositories.pos.pos.PointofsaleRepository;
import com.c2psi.businessmanagement.repositories.pos.userbm.RoleRepository;
import com.c2psi.businessmanagement.repositories.pos.userbm.UserBMRepository;
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
    private UserBMRepository userBMRepository;
    private RoleRepository roleRepository;
    private PointofsaleRepository posRepository;

    @Autowired
    public UserBMRoleServiceImpl(UserBMRoleRepository userBMRoleRepository, UserBMRepository userBMRepository,
                                 RoleRepository roleRepository, PointofsaleRepository posRepository) {
        this.userBMRoleRepository = userBMRoleRepository;
        this.userBMRepository = userBMRepository;
        this.roleRepository = roleRepository;
        this.posRepository = posRepository;
    }

    @Override
    public UserBMRoleDto saveUserBMRole(UserBMRoleDto userbmRoleDto) {
        List<String> errors = UserBMRoleValidator.validate(userbmRoleDto);
        if(!errors.isEmpty()){
            log.error("Entity userBMrole not valid {}", userbmRoleDto);
            throw new InvalidEntityException("Le userBMRole passe en argument n'est pas valide",
                    ErrorCode.USERBMROLE_NOT_VALID, errors);
        }

        /***********************************************************************************
         * Il faut se rassurer que le UserBM associe ici est bel et bien existant dans la BD
         */
        if(userbmRoleDto.getUserbmroleUserbmDto() == null){
            log.error("The UserBM associated can't be null");
            throw new InvalidEntityException("Le userbm associe ne saurait etre null dans cette operation",
                    ErrorCode.USERBMROLE_NOT_VALID);
        }
        if(userbmRoleDto.getUserbmroleUserbmDto().getId() == null){
            log.error("The UserBMId associated can't be null");
            throw new InvalidEntityException("Le userbmId associe ne saurait etre null dans cette operation",
                    ErrorCode.USERBMROLE_NOT_VALID);
        }
        Optional<UserBM> optionalUserBM = userBMRepository.findUserBMById(userbmRoleDto.getUserbmroleUserbmDto().getId());
        if(!optionalUserBM.isPresent()){
            log.error("The userBM associated don't exist in the DataBase");
            throw new InvalidEntityException("Le userbm associe n'existe pas dans la BD ", ErrorCode.USERBM_NOT_FOUND);
        }

        UserBM userAssociate = optionalUserBM.get();

        if(userbmRoleDto.getUserbmroleRoleDto() == null){
            log.error("The role associated can't be null");
            throw new InvalidEntityException("Le role associe ne saurait etre null dans cette operation",
                    ErrorCode.USERBMROLE_NOT_VALID);
        }

        if(userbmRoleDto.getUserbmroleRoleDto().getId() == null){
            log.error("The roleId associated can't be null");
            throw new InvalidEntityException("Le roleId associe ne saurait etre null dans cette operation",
                    ErrorCode.USERBMROLE_NOT_VALID);
        }
        Optional<Role> optionalRole = roleRepository.findRoleById(userbmRoleDto.getUserbmroleRoleDto().getId());
        if(!optionalUserBM.isPresent()){
            log.error("The role associated don't exist in the DataBase");
            throw new InvalidEntityException("Le role associe n'existe pas dans la BD ", ErrorCode.ROLE_NOT_FOUND);
        }
        Role roleAssociate = optionalRole.get();

        if(userAssociate.getBmUsertype().equals(UserBMType.AdminBM)){
            if(!roleAssociate.getRoleName().equals(RoleType.ADMIN)){
                log.error("It is not possible to assign another role than ADMIN to an AdminBM");
                throw new InvalidEntityException("On ne peut associe a un AdminBM que le role ADMIN sur la plateforme"
                        , ErrorCode.USERBMROLE_NOT_VALID);
            }
        } else if (userAssociate.getBmUsertype().equals(UserBMType.AdminEnterprise)) {
            if(!roleAssociate.getRoleName().equals(RoleType.ADMIN_ENTERPRISE)){
                log.error("It is not possible to assign another role than ADMIN_ENTERPRISE to an AdminEnterprise");
                throw new InvalidEntityException("On ne peut associe a un AdminEnterprise que le role ADMIN_ENTERPRISE" +
                        " sur la plateforme", ErrorCode.USERBMROLE_NOT_VALID);
            }
        } else{
            /**********************************************************************************
             * Ceci signifie que le UserBM est forcement associe a un point de vente.
             * IL faut donc se rassurer que le role et le user sont dans la meme entreprise
             */
            if(userAssociate.getBmPosId() == null){
                log.error("The posId associate to the User can't be null because the user must be associate with " +
                        " a pointofsale to have this role");
                throw new InvalidEntityException("Il n'est pas possible d'avoir ce role sans etre associe a un point " +
                        "de vente ", ErrorCode.USERBMROLE_NOT_VALID);
            }
            Optional<Pointofsale> optionalPointofsale = posRepository.findPointofsaleById(userAssociate.getBmPosId());
            if(!optionalRole.isPresent()){
                log.error("There is any Pointofsale in the DB with the posId associate to the User");
                throw new InvalidEntityException("Le posId associe au User n'identifie aucun Pointofsale dans la BD ",
                        ErrorCode.USERBMROLE_NOT_VALID);
            }
            Pointofsale posAssociate = optionalPointofsale.get();
            if(!roleAssociate.getRoleEnt().getId().equals(posAssociate.getPosEnterprise().getId())){
                log.error("The role and the User must belong to the same enterprise");
                throw new InvalidEntityException("Le UserBM et le role dans cette operation doivent apparatenir a la " +
                        "meme entreprise", ErrorCode.USERBMROLE_NOT_VALID);
            }
        }

        /**********************************************************************************
         * On se rassure que ce user n'a pas deja ce role car on ne peut faire l'association
         * de UserBM et Role qu'une seule fois
         */
        if(!isUserBMRoleUnique(userAssociate.getId(), roleAssociate.getId())){
            log.error("The User precised has already the designated role");
            throw new DuplicateEntityException("L'utilisateur precise ici a deja le role demande ",
                    ErrorCode.USERBMROLE_DUPLICATED);
        }

        log.info("After all verification, the userBMRole {} can be saved normally", userbmRoleDto);
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
                    +" n'a ete trouve dans la BDD", ErrorCode.USERBMROLE_NOT_FOUND);
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
    public UserBMRoleDto findByUserbmroleUserbmAndUserbmroleRole(Long userbmDtoId, Long roleDtoId) {
        if(userbmDtoId == null || roleDtoId == null){
            log.error("Ni le userBMId ni le roleId ne peut etre null lorsqu'on cherche le UserBMRole");
            throw new NullArgumentException("Les parametres de la recherche sont nulls");
        }

        Optional<UserBMRole> optionalUserBMRole = userBMRoleRepository.findUserBMRoleByUserbmAndRole(userbmDtoId,
                roleDtoId);
        if(!optionalUserBMRole.isPresent()){
            log.error("There is no UserBMRole that associate the Userbm precised with the role precised");
            throw new EntityNotFoundException("Aucun UserBMRole n'associe le UserBM et le role dont les Id ont " +
                    "ete precise", ErrorCode.USERBMROLE_NOT_FOUND);
        }
        return UserBMRoleDto.fromEntity(optionalUserBMRole.get());
    }

    /*@Override
    public List<UserBMRoleDto> findAllUserBMRoleofEnterprise(EnterpriseDto entDto) {
        return userBMRoleRepository.findAllUserBMRoleofEnterprise(entDto.getId()).stream()
                .map(UserBMRoleDto::fromEntity)
                .collect(Collectors.toList());
    }*/

    @Override
    public List<UserBMRoleDto> findAllUserBMRoleofPointofsale(Long posId) {
        return userBMRoleRepository.findAllUserBMRoleofPointofsale(posId).stream()
                .map(UserBMRoleDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean isUserBMRoleUnique(Long userbmDtoId, Long roleDtoId) {
        if(userbmDtoId == null || roleDtoId == null){
            log.error("Ni le userBMId ni le roleId ne peut etre null lorsqu'on cherche le UserBMRole");
            throw new NullArgumentException("Les parametres de la recherche sont nulls");
        }
        Optional<UserBMRole> optionalUserBMRole = userBMRoleRepository.findUserBMRoleByUserbmAndRole(userbmDtoId, roleDtoId);

        return optionalUserBMRole.isEmpty();
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
