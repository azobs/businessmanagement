package com.c2psi.businessmanagement.services.contracts.client.delivery;

import com.c2psi.businessmanagement.dtos.client.delivery.DeliveryDetailsDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DeliveryDetailsService {
    DeliveryDetailsDto saveDeliveryDetails(DeliveryDetailsDto ddDto);
    DeliveryDetailsDto updateDeliveryDetails(DeliveryDetailsDto ddDto);
    DeliveryDetailsDto findDeliveryDetailsById(Long ddId);
    Boolean isDeliveryDetailsUniqueinDeliverywithPackaging(Long packagingId, Long deliveryId);
    DeliveryDetailsDto findDeliveryDetailsinDeliverywithPackaging(Long packagingId, Long deliveryId);
    Boolean deleteDeliveryDetailsById(Long ddId);
    Boolean isDeliveryDetailsDeleteable(Long ddId);
    List<DeliveryDetailsDto> findAllDeliveryDetailsinDelivery(Long deliveryId);
    Page<DeliveryDetailsDto> findPageDeliveryDetailsinDelivery(Long deliveryId, int pagenum, int pagesize);

}
