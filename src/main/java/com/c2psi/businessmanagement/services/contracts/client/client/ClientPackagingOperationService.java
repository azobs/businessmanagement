package com.c2psi.businessmanagement.services.contracts.client.client;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.client.client.ClientPackagingAccountDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientPackagingOperationDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderPackagingOperationDto;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public interface ClientPackagingOperationService {
    ClientPackagingOperationDto updateClientPackagingOperation(ClientPackagingOperationDto cltpackopDto);
    Boolean isClientPackagingOperationDeleteable(Long cltpackopId);
    Boolean deleteClientPackagingOperationById(Long cltpackopId);
    List<ClientPackagingOperationDto> findAllClientPackagingOperation(Long cltpackopId);
    List<ClientPackagingOperationDto> findAllClientPackagingOperationofType(Long cltpackopId, OperationType opType);
    Page<ClientPackagingOperationDto> findPageClientPackagingOperation(Long cltpackopId, int pagenum, int pagesize);
    Page<ClientPackagingOperationDto> findPageClientPackagingOperationofType(Long cltpackopId, OperationType opType,
                                                                             int pagenum, int pagesize);
    List<ClientPackagingOperationDto> findAllClientPackagingOperationBetween(Long cltpackopId, Instant startDate,
                                                                             Instant endDate);
    Page<ClientPackagingOperationDto> findPageClientPackagingOperationBetween(Long cltpackopId, Instant startDate,
                                                                              Instant endDate, int pagenum, int pagesize);
    List<ClientPackagingOperationDto> findAllClientPackagingOperationBetween(Long cltpackopId, OperationType op_type,
                                                                             Instant startDate, Instant endDate);
    Page<ClientPackagingOperationDto> findPageClientPackagingOperationBetween(Long cltpackopId, OperationType op_type,
                                                                              Instant startDate, Instant endDate,
                                                                              int pagenum, int pagesize);
}
