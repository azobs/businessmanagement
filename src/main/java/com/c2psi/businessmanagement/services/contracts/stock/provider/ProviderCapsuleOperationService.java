package com.c2psi.businessmanagement.services.contracts.stock.provider;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCapsuleOperationDto;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.List;

public interface ProviderCapsuleOperationService {

    ProviderCapsuleOperationDto findProviderCapsuleOperationById(Long proCapsOpId);
    ProviderCapsuleOperationDto updateProviderCapsuleOperation(ProviderCapsuleOperationDto procapopDto);

    Boolean isProviderCapsuleOperationDeleteable(Long procapopId);

    Boolean deleteProviderCapsuleOperationById(Long procapopId);

    List<ProviderCapsuleOperationDto> findAllProviderCapsuleOperation(Long procapaccId);

    Page<ProviderCapsuleOperationDto> findPageProviderCapsuleOperation(Long procapaccId, int pagenum, int pagesize);

    List<ProviderCapsuleOperationDto> findAllProviderCapsuleOperationofType(Long procapaccId, OperationType opType);

    Page<ProviderCapsuleOperationDto> findPageProviderCapsuleOperationofType(Long procapaccId, OperationType opType,
                                                                          int pagenum, int pagesize);
    List<ProviderCapsuleOperationDto> findAllProviderCapsuleOperationBetween(
            Long pcapsaccId, Instant startDate, Instant endDate);

    Page<ProviderCapsuleOperationDto> findPageProviderCapsuleOperationBetween(
            Long pcapsaccId, Instant startDate, Instant endDate, int pagenum, int pagesize);

    List<ProviderCapsuleOperationDto> findAllProviderCapsuleOperationBetween(
            Long pcapsaccId, OperationType opType, Instant startDate, Instant endDate);

    Page<ProviderCapsuleOperationDto> findPageProviderCapsuleOperationBetween(
            Long pcapsaccId, OperationType opType, Instant startDate, Instant endDate, int pagenum, int pagesize);


}
