package com.c2psi.businessmanagement.dtos.client.delivery;

import com.c2psi.businessmanagement.Enumerations.DeliveryState;
import com.c2psi.businessmanagement.dtos.client.command.CommandDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.models.Delivery;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class DeliveryDto {
    Long id;
    @NotNull(message = "The delivery code cannot be null")
    @NotEmpty(message = "The delivery code cannot be empty")
    @NotBlank(message = "The delivery code cannot be blank")
    @Size(min = 3, max = 20, message = "The delivery code size must be between 3 and 25 characters")
    String deliveryCode;
    @NotNull(message = "The delivery date cannot be null")
    Instant deliveryDate;
    @NotNull(message = "The delivery state cannot be null")
    DeliveryState deliveryState;
    String deliveryComment;

    /******************************
     * Relation between entities  *
     * ****************************/

    @NotNull(message = "The userbm associated with the delivery cannot be null")
    UserBMDto deliveryUserbmDto;
    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static DeliveryDto fromEntity(Delivery delivery){
        if(delivery == null){
            return null;
        }
        return DeliveryDto.builder()
                .id(delivery.getId())
                .deliveryCode(delivery.getDeliveryCode())
                .deliveryDate(delivery.getDeliveryDate())
                .deliveryState(delivery.getDeliveryState())
                .deliveryComment(delivery.getDeliveryComment())
                .deliveryUserbmDto(UserBMDto.fromEntity(delivery.getDeliveryUserbm()))
                .build();
    }
    public static Delivery toEntity(DeliveryDto deliveryDto){
        if(deliveryDto == null){
            return null;
        }
        Delivery delivery = new Delivery();
        delivery.setId(deliveryDto.getId());
        delivery.setDeliveryCode(deliveryDto.getDeliveryCode());
        delivery.setDeliveryDate(deliveryDto.getDeliveryDate());
        delivery.setDeliveryState(deliveryDto.getDeliveryState());
        delivery.setDeliveryComment(deliveryDto.getDeliveryComment());
        delivery.setDeliveryUserbm(UserBMDto.toEntity(deliveryDto.getDeliveryUserbmDto()));
        return delivery;
    }
}
