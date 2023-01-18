package com.c2psi.businessmanagement.dtos.client.delivery;

import com.c2psi.businessmanagement.dtos.stock.product.PackagingDto;
import com.c2psi.businessmanagement.models.DeliveryDetails;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;


@Data
@Builder
public class DeliveryDetailsDto {
    Long id;
    @NotNull
    @PositiveOrZero
    Integer ddNumberofpackageused;
    @NotNull
    @PositiveOrZero
    Integer ddNumberofpackagereturn;

    /******************************
     * Relation between entities  *
     * ****************************/

    @NotNull
    PackagingDto ddPackagingDto;

    @NotNull
    DeliveryDto ddDeliveryDto;
    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static DeliveryDetailsDto fromEntity(DeliveryDetails deliveryDetails){
        if(deliveryDetails == null){
            return null;
        }
        return DeliveryDetailsDto.builder()
                .id(deliveryDetails.getId())
                .ddNumberofpackageused(deliveryDetails.getDdNumberofpackageused())
                .ddNumberofpackagereturn(deliveryDetails.getDdNumberofpackagereturn())
                .ddPackagingDto(PackagingDto.fromEntity(deliveryDetails.getDdPackaging()))
                .ddDeliveryDto(DeliveryDto.fromEntity(deliveryDetails.getDdDelivery()))
                .build();
    }
    public static DeliveryDetails toEntity(DeliveryDetailsDto deliverydetailsDto){
        if(deliverydetailsDto == null){
            return null;
        }
        DeliveryDetails deliveryDetails = new DeliveryDetails();
        deliveryDetails.setId(deliverydetailsDto.getId());
        deliveryDetails.setDdNumberofpackageused(deliverydetailsDto.getDdNumberofpackageused());
        deliveryDetails.setDdNumberofpackagereturn(deliverydetailsDto.getDdNumberofpackagereturn());
        deliveryDetails.setDdPackaging(PackagingDto.toEntity(deliverydetailsDto.getDdPackagingDto()));
        deliveryDetails.setDdDelivery(DeliveryDto.toEntity(deliverydetailsDto.getDdDeliveryDto()));
        return deliveryDetails;
    }
}
