package com.c2psi.businessmanagement.services.contracts.pos.pos;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.pos.pos.PosDamageAccountDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosDamageOperationDto;

import java.util.Date;
import java.util.List;

public interface PosDamageOperationService {
    PosDamageOperationDto savePosDamageOperation(PosDamageOperationDto posdopDto);

    PosDamageOperationDto findPosDamageOperationById(Long id);

    List<PosDamageOperationDto> findAllByPosDamageAccount(
            PosDamageAccountDto posdaccDto);

    List<PosDamageOperationDto> findAllByPosDamageAccount(
            PosDamageAccountDto posdaccDto, OperationType opType);

    List<PosDamageOperationDto> findAllByPosDamageAccountBetween(
            PosDamageAccountDto posdaccDto, Date startDate, Date endDate);

    List<PosDamageOperationDto> findAllByPosDamageAccountBetween(
            PosDamageAccountDto posdaccDto, Date startDate, Date endDate,
            OperationType opType);

    Boolean deletePosDamageOperationById(Long posdopId);
}
