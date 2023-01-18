package com.c2psi.businessmanagement.services.contracts.stock.provider;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCashOperationDto;

import java.util.Date;
import java.util.List;

public interface ProviderCashOperationService {
    ProviderCashOperationDto saveProviderCashOperation(ProviderCashOperationDto pcopDto);
    Boolean deleteProviderCashOperationById(Long pcop_id);
    ProviderCashOperationDto findProviderCashOperationById(Long pcop_id);
    List<ProviderCashOperationDto> findAllProviderCashOperationBetween(Date startDate,
                                                                  Date endDate);
    List<ProviderCashOperationDto> findAllProviderCashOperationBetween(Date startDate,
                                                                  Date endDate,
                                                                  OperationType opType);
}
