package com.c2psi.businessmanagement.services.contracts.pos.pos;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.pos.pos.PosPackagingAccountDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosPackagingOperationDto;

import java.util.Date;
import java.util.List;

public interface PosPackagingOperationService {
    PosPackagingOperationDto savePosPackagingOperation(PosPackagingOperationDto pospopDto);

    PosPackagingOperationDto findPosPackagingOperationById(Long id);

    List<PosPackagingOperationDto> findAllByPosPackagingAccount(
            PosPackagingAccountDto pospaccDto);

    List<PosPackagingOperationDto> findAllByPosPackagingAccount(
            PosPackagingAccountDto pospaccDto, OperationType op_type);

    List<PosPackagingOperationDto> findAllByPosPackagingAccountBetween(
            PosPackagingAccountDto pospaccDto, Date startDate, Date endDate);

    List<PosPackagingOperationDto> findAllByPosPackagingAccountBetween(
            PosPackagingAccountDto pospaccDto, Date startDate, Date endDate,
            OperationType op_type);

    Boolean deletePosPackagingOperationById(Long pospopId);
}
