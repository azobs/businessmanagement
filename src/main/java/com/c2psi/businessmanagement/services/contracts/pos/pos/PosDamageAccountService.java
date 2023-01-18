package com.c2psi.businessmanagement.services.contracts.pos.pos;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCapsuleAccountDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosDamageAccountDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;

import java.util.List;

public interface PosDamageAccountService {
    PosDamageAccountDto savePosDamageAccount(PosDamageAccountDto posdaccDto);

    PosDamageAccountDto findPosDamageAccountById(Long id);

    PosDamageAccountDto findPosDamageAccountByPosArticle(PointofsaleDto posDto,
                                          ArticleDto artDto);

    List<PosDamageAccountDto> findAllDamageAccountPos(PointofsaleDto posDto);

    Boolean deletePosDamageAccountById(Long id);

    Boolean saveDamageDeposit(PosDamageAccountDto posdaDto, Integer number,
                               UserBMDto userbmDto);

    Boolean saveDamageWithdrawal(PosDamageAccountDto posdaDto, Integer number,
                                  UserBMDto userbmDto);
}
