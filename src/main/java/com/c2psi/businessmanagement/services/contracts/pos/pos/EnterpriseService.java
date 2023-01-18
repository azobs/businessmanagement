package com.c2psi.businessmanagement.services.contracts.pos.pos;

import com.c2psi.businessmanagement.dtos.pos.pos.EnterpriseDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface EnterpriseService {
    /*******************************************
     * Save method does both first saving and modification. If the id of the dto is
     * specified means the entity already exist and what we want to do is to
     * update or modify the entity. If not then it is the creation.
     * @param entDto
     * @return
     */
    EnterpriseDto saveEnterprise(EnterpriseDto entDto);

    /****
     * La mise a jour d'une entrepriser ne concerne que les données de l'entreprise et non celle de
     * son administrateur.
     * Pour modifier l'administrateur d'une entreprise, une autre méthode est prevu pour cela
     * @param entDto
     * @return
     */
    EnterpriseDto updateEnterprise(EnterpriseDto entDto);

    EnterpriseDto setAdminEnterprise(Long entId, Long userBMAdminId);

    EnterpriseDto findEnterpriseByName(String entName);

    EnterpriseDto findEnterpriseByNiu (String entNiu);

    Boolean isEnterpriseUnique(String entName, String entNiu, String entEmail);

    EnterpriseDto findEnterpriseById (Long entId);

    UserBMDto findUserBMById (Long userBMId);

    boolean deleteEnterpriseByName(String entName);

    boolean deleteEnterpriseByNiu(String entNiu);

    boolean deleteEnterpriseById(Long entId);

    List<EnterpriseDto> findAllEnterprise();

    List<PointofsaleDto> findAllPosofEnterprise(Long entId);

    /***************************************************************************
     * Calculate the turnover of a company between 02 dates. It is the sum of
     * the turnover of all pointofsale belonging to the company
     * @param entId
     * @param startDate
     * @param endDate
     * @return
     */
    BigDecimal getTurnover(Long entId, Date startDate, Date endDate);

    List<UserBMDto> findAllEmployeofEnterprise (Long entId);

    List<ProviderDto> findAllProviderofEnterprise (Long entId);

    BigDecimal getTotalCash(Long entId);

    Integer getNumberofDamage(Long entId);

    Integer getNumberofDamage(Long entId, Long artId);

    Integer getNumberofCapsule(Long entId);

    Integer getNumberofCapsule(Long entId, Long artId);

    Integer getNumberofPackaging(Long entId);

    Integer getNumberofPackaging(Long entId, Long providerId);

}
