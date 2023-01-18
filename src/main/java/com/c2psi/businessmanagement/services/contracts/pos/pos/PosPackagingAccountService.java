package com.c2psi.businessmanagement.services.contracts.pos.pos;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosPackagingAccountDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;

import java.util.List;

public interface PosPackagingAccountService {
    PosPackagingAccountDto savePosPackagingAccount(PosPackagingAccountDto posdaccDto);

    PosPackagingAccountDto findPosPackagingAccountById(Long posdacc_id);

    PosPackagingAccountDto findPosPackagingAccountByPosArticle(PointofsaleDto posDto,
                                          ArticleDto artDto);

    List<PosPackagingAccountDto> findAllPackagingAccountPos(PointofsaleDto posDto);

    Boolean deletePosPackagingAccountById(Long posda_id);

    Boolean savePackagingDeposit(PosPackagingAccountDto posdaDto, Integer number,
                               UserBMDto userbmDto);

    Boolean savePackagingWithdrawal(PosPackagingAccountDto posdaDto, Integer number,
                                  UserBMDto userbmDto);
}
