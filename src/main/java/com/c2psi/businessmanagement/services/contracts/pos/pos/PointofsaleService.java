package com.c2psi.businessmanagement.services.contracts.pos.pos;

import com.c2psi.businessmanagement.dtos.pos.pos.*;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyDto;
import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import com.c2psi.businessmanagement.dtos.stock.product.CategoryDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;

import java.util.Date;
import java.util.List;

public interface PointofsaleService {
    /*****************************************************************************
     * Compute the turnover (chiffre d'affaire) of a Pointofsale between 02 dates
     * @param posDto
     * @param startDate
     * @param endDate
     * @return
     */
    Double getTurnover(PointofsaleDto posDto, Date startDate, Date endDate);

    /*****************************************************************************
     * Make the employee list of Pointofsale
     * @param posDto
     * @return
     */
    List<UserBMDto> findAllEmployeofPos (PointofsaleDto posDto);

    List<ProviderDto> findAllProviderofPos (PointofsaleDto posDto);

    Double getTotalCash(Long entId);

    Integer getNumberofDamage(PointofsaleDto posDto);

    Integer getNumberofDamage(PointofsaleDto posDto, ArticleDto artDto);

    Integer getNumberofCapsule(PointofsaleDto posDto);

    Integer getNumberofCapsule(PointofsaleDto posDto, ArticleDto artDto);

    Integer getNumberofPackaging(PointofsaleDto posDto);

    Integer getNumberofPackaging(PointofsaleDto posDto, ProviderDto providerDto);

    /*******************************************************************************
     * Save method does both first saving and modification. If the id of the dto is
     * specified means the entity already exist and what we want to do is to
     * update or modify the entity. If not then it is the creation.
     * @param posDto
     * @return
     */
    PointofsaleDto savePointofsale(PointofsaleDto posDto);

    PointofsaleDto findPosByNameInEnterprise(String posName, EnterpriseDto entDto);
    Boolean isPosUnique(String posName, EnterpriseDto entDto);

    boolean deletePosById(Long posId);

    boolean deletePosByNameInEnterprise (String posName, EnterpriseDto entDto);

    /*****************************************************************************
     * Return the list of currency that a conversion rules exist that link a
     * currency with the default currency of the pointofsale
     * @param posDto
     * @return
     */
    List<CurrencyDto> listofConvertibleCurrency(PointofsaleDto posDto);

    CurrencyDto findDefaultCurrency(PointofsaleDto posDto);
}
