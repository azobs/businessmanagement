package com.c2psi.businessmanagement.services.contracts.pos.pos;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCapsuleAccountDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCapsuleOperationDto;

import java.util.Date;
import java.util.List;

public interface PosCapsuleOperationService {
    PosCapsuleOperationDto savePosCapsuleOperation(PosCapsuleOperationDto poscsopDto);

    PosCapsuleOperationDto findPosCapsuleOperationById(Long id);

    List<PosCapsuleOperationDto> findAllByPosCapsuleAccount(
            PosCapsuleAccountDto poscsaccDto);

    List<PosCapsuleOperationDto> findAllByPosCapsuleAccount(
            PosCapsuleAccountDto poscsaccDto, OperationType opType);

    List<PosCapsuleOperationDto> findAllByPosCapsuleAccountBetween(
            PosCapsuleAccountDto poscsaccDto, Date startDate, Date endDate);

    List<PosCapsuleOperationDto> findAllByPosCapsuleAccountBetween(
            PosCapsuleAccountDto poscsaccDto, Date startDate, Date endDate,
            OperationType opType);

    Boolean deletePosCapsuleOperationById(Long poscsopId);
}
