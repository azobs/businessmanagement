package com.c2psi.businessmanagement.dtos.client.delivery;

import com.c2psi.businessmanagement.Enumerations.DeliveryState;
import com.c2psi.businessmanagement.dtos.client.command.CommandDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.models.Delivery;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class DeliveryDto {
    Long id;
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 20)
    String deliveryCode;
    @FutureOrPresent
    Instant deliveryDate;
    @NotNull
    @NotEmpty
    DeliveryState deliveryState;
    String deliveryComment;

    /******************************
     * Relation between entities  *
     * ****************************/

    /*@JsonIgnore
    List<DeliveryDetailsDto> deliveryDetailsDtoList;

    @JsonIgnore
    List<CommandDto> commandDtoList;*/

    @NotNull
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
                /*.deliveryDetailsDtoList(delivery.getDeliveryDetailsList() != null ?
                        delivery.getDeliveryDetailsList().stream()
                        .map(DeliveryDetailsDto::fromEntity)
                        .collect(Collectors.toList()) : null)
                .commandDtoList(delivery.getCommandList() != null ?
                        delivery.getCommandList().stream()
                                .map(CommandDto::fromEntity)
                                .collect(Collectors.toList()) : null)*/
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
