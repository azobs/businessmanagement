package com.c2psi.businessmanagement.validators.client.delivery;

import com.c2psi.businessmanagement.Enumerations.DeliveryState;
import com.c2psi.businessmanagement.dtos.client.delivery.DeliveryDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DeliveryValidator {
    /********************************************************************************
     * *Le parametre a valider ne peut etre null
     * *Le code de la livraison (deliveryCode) ne peut etre ni null ni vide
     * *L'etat de la livraison (deliveryState) ne doit pas etre null et doit une
     * valeur reconnu par le systeme
     * *L'utilisateur en charge de la livraison (deliveryUserbmDto) ne doit pas etre null
     * @param deliveryDto
     * @return
     */
    public static List<String> validate(DeliveryDto deliveryDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(deliveryDto).isPresent()){
            errors.add("--Le parametre deliveryDto a valider ne peut etre null: "+errors);
        }
        else{
            if(Optional.ofNullable(deliveryDto.getDeliveryCode()).isPresent()){
                if(!StringUtils.hasLength(deliveryDto.getDeliveryCode())){
                    errors.add("--le code de la livraison ne peut etre vide: "+errors);
                }
            }
            else{
                errors.add("--Le code de la livraison ne peut etre null: "+errors);
            }
            if(Optional.ofNullable(deliveryDto.getDeliveryState()).isPresent()){
                if(!deliveryDto.getDeliveryState().equals(DeliveryState.PackedUp) &&
                !deliveryDto.getDeliveryState().equals(DeliveryState.OutForDelivery) &&
                !deliveryDto.getDeliveryState().equals(DeliveryState.Delivery)&&
                !deliveryDto.getDeliveryState().equals(DeliveryState.Finish)){
                    errors.add("--Etat de livraison non pris en compte par le systeme: "+errors);
                }
            }
            else{
                errors.add("--L'etat de la livraison ne peut etre null: "+errors);
            }
            if(!Optional.ofNullable(deliveryDto.getDeliveryUserbmDto()).isPresent()){
                errors.add("--L'utilisateur lie a la livraison ne saurait etre null: "+errors);
            }
        }
        return errors;
    }
}
