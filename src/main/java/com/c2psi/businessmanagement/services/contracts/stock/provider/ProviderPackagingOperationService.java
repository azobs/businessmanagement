package com.c2psi.businessmanagement.services.contracts.stock.provider;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderPackagingOperationDto;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.List;

public interface ProviderPackagingOperationService {
    ProviderPackagingOperationDto updateProviderPackagingOperation(ProviderPackagingOperationDto propackopDto);
    Boolean isProviderPackagingOperationDeleteable(Long propackopId);
    Boolean deleteProviderPackagingOperationById(Long propackopId);
    List<ProviderPackagingOperationDto> findAllProviderPackagingOperation(Long propackopId);
    List<ProviderPackagingOperationDto> findAllProviderPackagingOperationofType(Long propackopId, OperationType opType);
    Page<ProviderPackagingOperationDto> findPageProviderPackagingOperation(Long propackopId, int pagenum, int pagesize);
    Page<ProviderPackagingOperationDto> findPageProviderPackagingOperationofType(Long propackopId, OperationType opType,
                                                                       int pagenum, int pagesize);
    List<ProviderPackagingOperationDto> findAllProviderPackagingOperationBetween(Long propackopId, Instant startDate,
                                                                       Instant endDate);
    Page<ProviderPackagingOperationDto> findPageProviderPackagingOperationBetween(Long propackopId, Instant startDate,
                                                                        Instant endDate, int pagenum, int pagesize);
    List<ProviderPackagingOperationDto> findAllProviderPackagingOperationBetween(Long propackopId, OperationType op_type,
                                                                       Instant startDate, Instant endDate);

    Page<ProviderPackagingOperationDto> findPageProviderPackagingOperationBetween(Long propackopId, OperationType op_type,
                                                                        Instant startDate, Instant endDate,
                                                                        int pagenum, int pagesize);
}
