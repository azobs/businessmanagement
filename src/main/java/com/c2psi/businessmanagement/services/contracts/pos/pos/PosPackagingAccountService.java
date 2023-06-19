package com.c2psi.businessmanagement.services.contracts.pos.pos;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.pos.pos.PosPackagingAccountDto;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface PosPackagingAccountService {
    PosPackagingAccountDto savePosPackagingAccount(PosPackagingAccountDto ppackaccDto);
    PosPackagingAccountDto findPosPackagingAccountById(Long ppackaccId);
    PosPackagingAccountDto findPosPackagingAccountInPos(Long packagingId, Long posId);
    List<PosPackagingAccountDto> findAllPackagingAccountInPos(Long posId);
    Page<PosPackagingAccountDto> findPagePackagingAccountInPos(Long posId, int pagenum, int pagesize);
    List<PosPackagingAccountDto> findAllPackagingAccount(Long packagingId);
    Page<PosPackagingAccountDto> findPagePackagingAccount(Long packagingId, int pagenum, int pagesize);
    Boolean deletePosPackagingAccountById(Long ppackaccId);
    Boolean isPosPackagingAccountDeleteable(Long ppackaccId);
    Boolean savePackagingOperation(Long ppackaccId, BigDecimal qte, OperationType operationType,
                                 Long userBMId, String opObject, String opDescription);
}
