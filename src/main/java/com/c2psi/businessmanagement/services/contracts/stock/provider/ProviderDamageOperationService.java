package com.c2psi.businessmanagement.services.contracts.stock.provider;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCapsuleOperationDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDamageOperationDto;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.List;

public interface ProviderDamageOperationService {
    ProviderDamageOperationDto updateProviderDamageOperation(ProviderDamageOperationDto prodamopDto);
    Boolean isProviderDamageOperationDeleteable(Long prodamopId);
    Boolean deleteProviderDamageOperationById(Long prodamopId);
    List<ProviderDamageOperationDto> findAllProviderDamageOperation(Long prodamaccId);
    Page<ProviderDamageOperationDto> findPageProviderDamageOperation(Long prodamaccId, int pagenum, int pagesize);
    List<ProviderDamageOperationDto> findAllProviderDamageOperationofType(Long prodamaccId, OperationType opType);
    Page<ProviderDamageOperationDto> findPageProviderDamageOperationofType(Long prodamaccId, OperationType opType,
                                                                             int pagenum, int pagesize);
    List<ProviderDamageOperationDto> findAllProviderDamageOperationBetween(Long prodamaccId, Instant startDate,
                                                                           Instant endDate);
    Page<ProviderDamageOperationDto> findPageProviderDamageOperationBetween(Long prodamaccId, Instant startDate,
                                                                            Instant endDate, int pagenum, int pagesize);
    List<ProviderDamageOperationDto> findAllProviderDamageOperationBetween(Long prodamaccId, OperationType opType,
                                                                           Instant startDate, Instant endDate);
    Page<ProviderDamageOperationDto> findPageProviderDamageOperationBetween(Long prodamaccId, OperationType opType,
                                                                            Instant startDate, Instant endDate,
                                                                            int pagenum, int pagesize);
}
