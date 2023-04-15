package com.c2psi.businessmanagement.services.contracts.pos.pos;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosDamageAccountDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosDamageAccountDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface PosDamageAccountService {
    PosDamageAccountDto savePosDamageAccount(PosDamageAccountDto poscapsaccDto);

    PosDamageAccountDto findPosDamageAccountById(Long posdamaccId);

    PosDamageAccountDto findPosDamageAccountofArticleInPos(Long artId, Long posId);

    List<PosDamageAccountDto> findAllDamageAccountInPos(Long posId);
    Page<PosDamageAccountDto> findPageDamageAccountInPos(Long posId, int pagenum, int pagesize);

    List<PosDamageAccountDto> findAllDamageAccountofArticle(Long artId);
    Page<PosDamageAccountDto> findPageDamageAccountofArticle(Long artId, int pagenum, int pagesize);

    Boolean deletePosDamageAccountById(Long posdamaccId);

    Boolean saveDamageOperation(Long posdamaccId, BigDecimal qte, OperationType operationType,
                                 Long userBMId, String opObject, String opDescription);
    Boolean isPosDamageAccountDeleteable(Long posdamAccId);
}
