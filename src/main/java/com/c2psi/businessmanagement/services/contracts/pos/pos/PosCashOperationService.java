package com.c2psi.businessmanagement.services.contracts.pos.pos;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCashAccountDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCashOperationDto;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public interface PosCashOperationService {
    PosCashOperationDto updatePosCashOperation(PosCashOperationDto pcopDto);

    Boolean isPosCashOperationDeleteable(Long pcopId);
    Boolean deletePosCashOperationById(Long pcopId);

    PosCashOperationDto findPosCashOperationById(Long pcopId);

    List<PosCashOperationDto> findAllPosCashOperation(
            Long pcaId);

    Page<PosCashOperationDto> findPagePosCashOperation(Long pcaId, int pagenum, int pagesize);

    List<PosCashOperationDto> findAllPosCashOperationBetween(
            Long pcaId, Instant startDate, Instant endDate);

    Page<PosCashOperationDto> findPagePosCashOperationBetween(Long pcaId, Instant startDate, Instant endDate,
                                                             int pagenum, int pagesize);

    List<PosCashOperationDto> findAllPosCashOperationBetween(
            Long pcaId, OperationType op_type, Instant startDate, Instant endDate);

    Page<PosCashOperationDto> findPagePosCashOperationBetween(Long pcaId, OperationType op_type,
                                                             Instant startDate, Instant endDate,
                                                             int pagenum, int pagesize);
}
