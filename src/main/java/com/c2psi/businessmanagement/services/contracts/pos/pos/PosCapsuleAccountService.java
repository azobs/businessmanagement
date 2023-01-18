package com.c2psi.businessmanagement.services.contracts.pos.pos;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCapsuleAccountDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;

import java.util.List;

public interface PosCapsuleAccountService {
    /*********************************************************************************
     * Creer un compte capsule lie a un article pour un point de vente.
     * @param poscsaccDto
     * @return
     */
    PosCapsuleAccountDto savePosCapsuleAccount(PosCapsuleAccountDto poscsaccDto);

    PosCapsuleAccountDto findPosCapsuleAccountById(Long poscsaccId);

    PosCapsuleAccountDto findPosCapsuleAccountByPosArticle(PointofsaleDto posDto,
                                          ArticleDto artDto);

    List<PosCapsuleAccountDto> findAllCapsuleAccountPos(PointofsaleDto posDto);

    Boolean deletePosCapsuleAccountById(Long poscsaId);

    Boolean saveCapsuleDeposit(PosCapsuleAccountDto poscsaDto, Integer number,
                                UserBMDto userbmDto);

    Boolean saveCapsuleWithdrawal(PosCapsuleAccountDto poscsaDto, Integer number,
                               UserBMDto userbmDto);
}
