package com.c2psi.businessmanagement.services.contracts.client.delivery;

import com.c2psi.businessmanagement.dtos.client.delivery.DeliveryDetailsDto;
import com.c2psi.businessmanagement.dtos.client.delivery.DeliveryDto;

import java.util.List;

public interface DeliveryDetailsService {
    DeliveryDetailsDto saveDeliveryDetails(DeliveryDetailsDto ddDto);
    DeliveryDetailsDto findDeliveryDetailsById(Long id);
    Boolean deleteDeliveryDetailsById(Long id);
    List<DeliveryDetailsDto> findAllDeliveryDetails(DeliveryDto ddDto);
    Boolean isDeliveryDetailsUniqueInDelivery(DeliveryDto ddDto);
}
