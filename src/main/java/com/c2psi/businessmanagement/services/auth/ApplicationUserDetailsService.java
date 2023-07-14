package com.c2psi.businessmanagement.services.auth;

import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.exceptions.ErrorCode;
import com.c2psi.businessmanagement.exceptions.InvalidEntityException;
import com.c2psi.businessmanagement.exceptions.ZeroRoleForUserBMException;
import com.c2psi.businessmanagement.models.UserBM;
import com.c2psi.businessmanagement.models.UserBMRole;
import com.c2psi.businessmanagement.models.auth.ExtendedUser;
import com.c2psi.businessmanagement.repositories.pos.userbm.UserBMRepository;
import com.c2psi.businessmanagement.repositories.pos.userbm.UserBMRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class ApplicationUserDetailsService implements UserDetailsService {

    private UserBMRepository userBMRepository;
    private UserBMRoleRepository userBMRoleRepository;

    @Autowired
    public ApplicationUserDetailsService(UserBMRepository userBMRepository, UserBMRoleRepository userBMRoleRepository) {
        this.userBMRepository = userBMRepository;
        this.userBMRoleRepository = userBMRoleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userToken) throws UsernameNotFoundException {

        /***************************************************************************************************
         * The argument userToken can be an Email, a login or username or also the cni number of the User.
         * All those listed parameter above can be used with the good password to be connected.
         */
        //Is userToken an Email? if so there is an user in the Data with email = userToken
        Optional<UserBM> optionalUserBMFoundByEmail = userBMRepository.findUserBMByBmEmail(userToken);
        log.info("On a lance la recherche par email avec la valeur {}", userToken);
        if(optionalUserBMFoundByEmail.isPresent()){
            UserBMDto userBMDtoFoundByEmail = UserBMDto.fromEntity(optionalUserBMFoundByEmail.get());
            /*List<UserBMRole> userBMRoleList = userBMRoleRepository.findAllByUserbmroleUserbm(
                    optionalUserBMFoundByEmail.get());

            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            userBMRoleList.forEach(userBMRole -> authorities.add(new SimpleGrantedAuthority(
                    userBMRole.getUserbmroleRole().getRoleName().name())));*/

            /////
            Optional<List<UserBMRole>> optionalUserBMRoleList = userBMRoleRepository.findAllUserBMRoleofUserbm(
                    userBMDtoFoundByEmail.getId());
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            if(!optionalUserBMRoleList.isPresent()){
                log.info("An Unknow error occur during execution of findAllUserBMRoleofUserbm on user with name {}",
                        userToken);
                throw new InvalidEntityException("Erreur lors de l'execution de findAllUserBMRoleofUserbm avec " +
                        "userToken = "+userToken, ErrorCode.AUTHENTICATION_ERROR);
            }

            List<UserBMRole> userBMRoleList = optionalUserBMRoleList.get();

            if(userBMRoleList.isEmpty()){
                log.info("The user is recognized but without any role assigned to him.");
                throw new ZeroRoleForUserBMException("Aucun role n'est encore assigne a l'utilisateur indique",
                        ErrorCode.ZEROROLE_ERROR);
            }

            userBMRoleList.forEach(userBMRole -> authorities.add(new SimpleGrantedAuthority(
                    userBMRole.getUserbmroleRole().getRoleName().name())));

            log.info("Le extended user trouve est {}", new ExtendedUser(userBMDtoFoundByEmail.getBmLogin(),
                    userBMDtoFoundByEmail.getBmPassword(), authorities, userBMDtoFoundByEmail.getBmUsertype(),
                    userBMDtoFoundByEmail.getBmEnterpriseId(), userBMDtoFoundByEmail.getBmPosId()));
            /////

            return new ExtendedUser(userBMDtoFoundByEmail.getBmLogin(), userBMDtoFoundByEmail.getBmPassword(),
                    authorities, userBMDtoFoundByEmail.getBmUsertype(), userBMDtoFoundByEmail.getBmEnterpriseId(),
                    userBMDtoFoundByEmail.getBmPosId());
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /*****************************************************************************************
         * Ici cela signifie que rien n'a ete trouve en considerant que la chaine de connexion
         * userToken est un email
         * On va continuer en supposant que cet un login
         ******************************************************************************************/
        Optional<UserBM> optionalUserBMFoundByLogin = userBMRepository.findUserBMByBmLogin(userToken);
        log.info("On a lance la recherche par login avec la valeur {}", userToken);
        if(optionalUserBMFoundByLogin.isPresent()){
            UserBMDto userBMDtoFoundByLogin = UserBMDto.fromEntity(optionalUserBMFoundByLogin.get());
            Optional<List<UserBMRole>> optionalUserBMRoleList = userBMRoleRepository.findAllUserBMRoleofUserbm(
                    optionalUserBMFoundByLogin.get().getId());
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            if(!optionalUserBMRoleList.isPresent()){
                log.info("An Unknow error occur during execution of findAllUserBMRoleofUserbm on user with name {}",
                        userToken);
                throw new InvalidEntityException("Erreur lors de l'execution de findAllUserBMRoleofUserbm avec " +
                        "userToken = "+userToken, ErrorCode.AUTHENTICATION_ERROR);
            }

            List<UserBMRole> userBMRoleList = optionalUserBMRoleList.get();

            if(userBMRoleList.isEmpty()){
                log.info("The user is recognized but without any role assigned to him.");
                throw new ZeroRoleForUserBMException("Aucun role n'est encore assigne a l'utilisateur indique",
                        ErrorCode.ZEROROLE_ERROR);
            }

            userBMRoleList.forEach(userBMRole -> authorities.add(new SimpleGrantedAuthority(
                    userBMRole.getUserbmroleRole().getRoleName().name())));

            log.info("Le extended user trouve est {}", new ExtendedUser(userBMDtoFoundByLogin.getBmLogin(),
                    userBMDtoFoundByLogin.getBmPassword(), authorities, userBMDtoFoundByLogin.getBmUsertype(),
                    userBMDtoFoundByLogin.getBmEnterpriseId(), userBMDtoFoundByLogin.getBmPosId()));

            return new ExtendedUser(userBMDtoFoundByLogin.getBmLogin(), userBMDtoFoundByLogin.getBmPassword(),
                    authorities, userBMDtoFoundByLogin.getBmUsertype(), userBMDtoFoundByLogin.getBmEnterpriseId(),
                    userBMDtoFoundByLogin.getBmPosId());
        }
        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        /******************
         * Ici cela signifie que rien n'a ete trouve en considerant que la chaine de connexion
         * userToken est un email ni un login
         * On va continuer en supposant que cet un cni number
         */
        Optional<UserBM> optionalUserBMFoundByCni = userBMRepository.findUserBMByBmCni(userToken);
        log.info("On a lance la recherche par cni avec la valeur {}", userToken);
        if(optionalUserBMFoundByCni.isPresent()){
            UserBMDto userBMDtoFoundByCni = UserBMDto.fromEntity(optionalUserBMFoundByCni.get());
            /*List<UserBMRole> userBMRoleList = userBMRoleRepository.findAllByUserbmroleUserbm(
                    optionalUserBMFoundByCni.get());
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            userBMRoleList.forEach(userBMRole -> authorities.add(new SimpleGrantedAuthority(
                    userBMRole.getUserbmroleRole().getRoleName().name())));*/

            //////
            Optional<List<UserBMRole>> optionalUserBMRoleList = userBMRoleRepository.findAllUserBMRoleofUserbm(
                    userBMDtoFoundByCni.getId());
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            if(!optionalUserBMRoleList.isPresent()){
                log.info("An Unknow error occur during execution of findAllUserBMRoleofUserbm on user with name {}",
                        userToken);
                throw new InvalidEntityException("Erreur lors de l'execution de findAllUserBMRoleofUserbm avec " +
                        "userToken = "+userToken, ErrorCode.AUTHENTICATION_ERROR);
            }

            List<UserBMRole> userBMRoleList = optionalUserBMRoleList.get();

            if(userBMRoleList.isEmpty()){
                log.info("The user is recognized but without any role assigned to him.");
                throw new ZeroRoleForUserBMException("Aucun role n'est encore assigne a l'utilisateur indique",
                        ErrorCode.ZEROROLE_ERROR);
            }

            userBMRoleList.forEach(userBMRole -> authorities.add(new SimpleGrantedAuthority(
                    userBMRole.getUserbmroleRole().getRoleName().name())));

            log.info("Le extended user trouve est {}", new ExtendedUser(userBMDtoFoundByCni.getBmLogin(),
                    userBMDtoFoundByCni.getBmPassword(), authorities, userBMDtoFoundByCni.getBmUsertype(),
                    userBMDtoFoundByCni.getBmEnterpriseId(), userBMDtoFoundByCni.getBmPosId()));
            //////

            return new ExtendedUser(userBMDtoFoundByCni.getBmLogin(), userBMDtoFoundByCni.getBmPassword(),
                    authorities, userBMDtoFoundByCni.getBmUsertype(), userBMDtoFoundByCni.getBmEnterpriseId(),
                    userBMDtoFoundByCni.getBmPosId());
        }

        throw new BadCredentialsException("Username and/or password invalid");
    }
}
