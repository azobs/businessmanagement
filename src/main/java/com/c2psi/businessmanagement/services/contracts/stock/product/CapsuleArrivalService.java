package com.c2psi.businessmanagement.services.contracts.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.CapsuleArrivalDto;
import com.c2psi.businessmanagement.dtos.stock.product.SupplyInvoiceCapsuleDto;

import java.util.List;

public interface CapsuleArrivalService {
    CapsuleArrivalDto saveCapsuleArrival(CapsuleArrivalDto capsaDto);
    Boolean deleteCapsuleArrivalById(Long capsaId);
    CapsuleArrivalDto findCapsuleArrivalById(Long capsaId);
    List<CapsuleArrivalDto> findAllCapsuleArrival(SupplyInvoiceCapsuleDto sicapsDto);
}
