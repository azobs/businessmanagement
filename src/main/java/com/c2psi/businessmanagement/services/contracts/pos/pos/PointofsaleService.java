package com.c2psi.businessmanagement.services.contracts.pos.pos;

import com.c2psi.businessmanagement.dtos.pos.pos.EnterpriseDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyDto;
import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;

public interface PointofsaleService {
    /*****************************************************************************
     * Compute the turnover (chiffre d'affaire) of a Pointofsale between 02 dates
     * @param posId
     * @param startDate
     * @param endDate
     * @return
     */
    BigDecimal getTurnover(Long posId, Instant startDate, Instant endDate);

    /*****************************************************************************
     * Make the employee list of Pointofsale
     * @param posId
     * @return
     */
    List<UserBMDto> findAllEmployeofPos (Long posId);

    List<ProviderDto> findAllProviderofPos (Long posId);

    BigDecimal getTotalCashofPos(Long posId);

    BigDecimal getNumberofDamageofPos(Long posId);

    BigDecimal getNumberofDamageofPos(Long posId, Long artId);

    BigDecimal getNumberofCapsuleofPos(Long posId);

    BigDecimal getNumberofCapsuleofPos(Long posId, Long artId);

    BigDecimal getNumberofPackagingofPos(Long posId);

    BigDecimal getNumberofPackagingofPos(Long posId, Long providerId);

    /*******************************************************************************
     * Save method does both first saving and modification. If the id of the dto is
     * specified means the entity already exist and what we want to do is to
     * update or modify the entity. If not then it is the creation.
     * @param posDto
     * @return
     */
    PointofsaleDto savePointofsale(PointofsaleDto posDto);

    PointofsaleDto updatePointofsale(PointofsaleDto posDto);

    PointofsaleDto findPointofsaleById(Long posId);

    PointofsaleDto findPosInEnterpriseByName(String posName, EnterpriseDto entDto);
    Boolean isPosUnique(String posName, String posEmail, EnterpriseDto entDto);
    Boolean isPosUnique(String posEmail);

    Boolean isPointofsaleExistWithId(Long posId);
    Boolean isPointofsaleDeleteable(Long posId);
    Boolean deletePosById(Long posId);

    Boolean deletePosInEnterpriseByName (String posName, Long entId);

    /********************************************************************************
     * Return the list of currency that a conversion rules exist with the default
     * currency of the pointofsale. In such a way that for every operation in the
     * pointofsale, if the user don't want to use the default currency he can uses
     * a currency that is in relation with the default one.
     * @param posId
     * @return
     */
    List<CurrencyDto> listofConvertibleCurrency(Long posId);

    /******************************************************
     * Return the default currency of a pointofsale
     * @param posId
     * @return
     ******************************************************/
    CurrencyDto getDefaultCurrency(Long posId);
}
