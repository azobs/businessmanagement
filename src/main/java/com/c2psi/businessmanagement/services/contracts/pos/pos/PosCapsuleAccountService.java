package com.c2psi.businessmanagement.services.contracts.pos.pos;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCapsuleAccountDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCapsuleOperationDto;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface PosCapsuleAccountService {

    PosCapsuleAccountDto savePosCapsuleAccount(PosCapsuleAccountDto poscapsaccDto);

    PosCapsuleAccountDto findPosCapsuleAccountById(Long poscapsaccId);

    PosCapsuleAccountDto findPosCapsuleAccountofArticleInPos(Long artId, Long posId);

    List<PosCapsuleAccountDto> findAllCapsuleAccountInPos(Long posId);
    Page<PosCapsuleAccountDto> findPageCapsuleAccountInPos(Long posId, int pagenum, int pagesize);

    List<PosCapsuleAccountDto> findAllCapsuleAccountofArticle(Long artId);
    Page<PosCapsuleAccountDto> findPageCapsuleAccountofArticle(Long artId, int pagenum, int pagesize);

    Boolean deletePosCapsuleAccountById(Long poscapsaccId);

    Boolean saveCapsuleOperation(Long poscapsaccId, BigDecimal qte, OperationType operationType,
                                 Long userBMId, String opObject, String opDescription);

    Boolean saveCapsuleOperation(PosCapsuleAccountDto poscapaccDto, PosCapsuleOperationDto poscapopDto);
    Boolean saveCapsuleOperation(PosCapsuleOperationDto poscapopDto);


    Boolean isPosCapsuleAccountDeleteable(Long posCapsAccId);
}
