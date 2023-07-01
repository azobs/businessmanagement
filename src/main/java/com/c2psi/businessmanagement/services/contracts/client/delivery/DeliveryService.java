package com.c2psi.businessmanagement.services.contracts.client.delivery;

import com.c2psi.businessmanagement.Enumerations.DeliveryState;
import com.c2psi.businessmanagement.dtos.client.delivery.DeliveryDto;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.List;

public interface DeliveryService {
    DeliveryDto saveDelivery(DeliveryDto deliveryDto);
    DeliveryDto updateDelivery(DeliveryDto deliveryDto);
    DeliveryDto switchDeliveryState(Long deliveryId, DeliveryState deliveryState);
    DeliveryDto switchDeliveryState(DeliveryDto deliveryDto);
    DeliveryDto findDeliveryById(Long deliveryId);
    DeliveryDto findDeliveryByCodeinPos(String deliveryCode, Long posId);
    Boolean deleteDeliveryById(Long deliveryId);
    Boolean isDeliveryUniqueinPos(String deliveryCode, Long posId);
    Boolean isDeliveryDeleteable(Long deliveryId);

    List<DeliveryDto> findAllDeliveryinPosBetween(Long posId, Instant startDate, Instant endDate);
    Page<DeliveryDto> findPageDeliveryinPosBetween(Long posId, Instant startDate, Instant endDate,
                                                int pagenum, int pagesize);

    List<DeliveryDto> findAllDeliveryinPoswithStateBetween(Long posId, DeliveryState deliveryState,
                                                        Instant startDate, Instant endDate);
    Page<DeliveryDto> findPageDeliveryinPoswithStateBetween(Long posId, DeliveryState deliveryState,
                                                         Instant startDate, Instant endDate,
                                                         int pagenum, int pagesize);

    List<DeliveryDto> findAllDeliveryinPosforUserbmBetween(Long posId, Long userbmId,
                                                        Instant startDate, Instant endDate);
    Page<DeliveryDto> findPageDeliveryinPosforUserbmBetween(Long posId, Long userbmId,
                                                        Instant startDate, Instant endDate,
                                                         int pagenum, int pagesize);

    List<DeliveryDto> findAllDeliveryinPosforUserbmwithStateBetween(Long posId, Long userbmId, DeliveryState deliveryState,
                                                                 Instant startDate, Instant endDate);
    Page<DeliveryDto> findPageDeliveryinPosforUserbmwithStateBetween(Long posId, Long userbmId, DeliveryState deliveryState,
                                                                 Instant startDate, Instant endDate,
                                                                 int pagenum, int pagesize);


}
