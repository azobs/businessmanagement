package com.c2psi.businessmanagement.dtos.client.delivery;

import com.c2psi.businessmanagement.dtos.stock.product.PackagingDto;
import com.c2psi.businessmanagement.models.DeliveryDetails;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;


@Data
@Builder
public class DeliveryDetailsDto {
    Long id;
    @NotNull(message = "The number of package used for the delivery details cannot be null")
    @Positive(message = "The number of package used for the delivery details mut b positive")
    BigDecimal ddNumberofpackageused;
    @NotNull(message = "The number of package return after delivery cannot be null")
    @PositiveOrZero(message = "The number of package return after delivery must be positive or null")
    BigDecimal ddNumberofpackagereturn;

    /******************************
     * Relation between entities  *
     * ****************************/

    @NotNull(message = "The packaging type used for the delivery details cannot be null")
    PackagingDto ddPackagingDto;

    @NotNull(message = "The delivery associated with that delivery details cannot be null")
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
