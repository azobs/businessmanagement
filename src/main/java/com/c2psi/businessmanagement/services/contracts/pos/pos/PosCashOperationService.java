package com.c2psi.businessmanagement.services.contracts.pos.pos;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCashAccountDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCashOperationDto;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface PosCashOperationService {
    PosCashOperationDto savePosCashOperation(PosCashOperationDto pcopDto);

    Boolean isPosCashOperationDeleteable(Long pcopId);
    Boolean deletePosCashOperationById(Long pcopId);

    PosCashOperationDto findPosCashOperationById(Long pcopId);

    List<PosCashOperationDto> findAllPosCashOperation(
            Long pcaId);

    Page<PosCashOperationDto> findAllPosCashOperation(Long pcaId, int pagenum, int pagesize);

    List<PosCashOperationDto> findAllPosCashOperationBetween(
            Long pcaId, Date startDate, Date endDate);

    Page<PosCashOperationDto> findAllPosCashOperationBetween(Long pcaId, Date startDate, Date endDate,
                                                             int pagenum, int pagesize);

    List<PosCashOperationDto> findAllPosCashOperationBetween(
            Long pcaId, OperationType op_type, Date startDate, Date endDate);

    Page<PosCashOperationDto> findAllPosCashOperationBetween(Long pcaId, OperationType op_type,
                                                             Date startDate, Date endDate,
                                                             int pagenum, int pagesize);
}
