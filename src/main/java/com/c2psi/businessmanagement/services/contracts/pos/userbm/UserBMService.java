package com.c2psi.businessmanagement.services.contracts.pos.userbm;

import com.c2psi.businessmanagement.Enumerations.UserBMType;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.models.Pointofsale;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface UserBMService {
    /****************
     * Save method does both first saving and modification. If the id of the dto is
     * specified means the entity already exist and what we want to do is to
     * update or modify the entity. If not then it is the creation.
     * @param userBMDto
     * @return
     */
    UserBMDto saveUserBM(UserBMDto userBMDto);
    UserBMDto updateUserBM(UserBMDto userBMDto);
    Boolean isUserBMUnique(String bmName, String bmSurname, Date bmDob, String bmLogin,
                           String bmCni, String bmEmail);
    Boolean isUserBMFullnameUnique(String bmName, String bmSurname, Date bmDob);
    Boolean isUserBMLoginUnique(String bmLogin);
    Boolean isUserBMCniUnique(String bmCni);
    Boolean isUserBMEmailUnique(String bmEmail);
    UserBMDto findUserBMByLogin(String bmLogin);
    UserBMDto findUserBMByEmail(String bmEmail);
    UserBMDto findUserBMByCni(String bmCni);
    UserBMDto findUserBMByFullNameAndDob(String bmName, String bmSurname, Date bmDob);
    UserBMDto findUserBMById(Long bmId);
    List<UserBMDto> findAllByUserBMType(UserBMType bmUsertype);
    Boolean isUserBMDeleteable(Long bmId);
    Boolean deleteUserBMByLogin(String bmLogin);
    Boolean deleteUserBMByCni(String bmCni);
    Boolean deleteUserBMByFullNameAndDob(String bmName, String bmSurname, Date bmDob);
    Boolean deleteUserBMById(Long bmId);
    List<UserBMDto> findAllUserBM();
    Page<UserBMDto> findAllUserBMContaining(String sample, int pagenum, int pagesize);
    List<UserBMDto> findAllUserBMofPos(Long idPos);
    Page<UserBMDto> findAllUserBMofPos(Long idPos, String sample, int pagenum, int pagesize);

}
