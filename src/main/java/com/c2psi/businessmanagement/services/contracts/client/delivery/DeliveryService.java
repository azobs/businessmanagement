package com.c2psi.businessmanagement.services.contracts.client.delivery;

import com.c2psi.businessmanagement.Enumerations.DeliveryState;
import com.c2psi.businessmanagement.dtos.client.command.CommandDto;
import com.c2psi.businessmanagement.dtos.client.delivery.DeliveryDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;

import java.util.Date;
import java.util.List;

public interface DeliveryService {
    DeliveryDto saveDelivery(DeliveryDto deliveryDto);
    DeliveryDto findDeliveryById(Long delivery_id);
    DeliveryDto findDeliveryByCode(String delivery_code);
    List<DeliveryDto> findAllDelivery(PointofsaleDto posDto, Date startDate,
                                      Date endDate);
    List<DeliveryDto> findAllDelivery(
            PointofsaleDto posDto, DeliveryState delivery_state, UserBMDto userbmDto,
                                      Date startDate, Date endDate);
    Boolean deleteDeliveryById(Long delivery_id);
    List<CommandDto> findAllCommandOfDelivery(DeliveryDto deliveryDto);

}
