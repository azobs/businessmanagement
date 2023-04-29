package com.c2psi.businessmanagement.dtos.client.client;

import com.c2psi.businessmanagement.dtos.stock.price.SpecialPriceDto;
import com.c2psi.businessmanagement.models.ClientSpecialprice;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.Instant;
import java.util.Date;

@Data
@Builder
public class ClientSpecialpriceDto {
    Long id;
    @NotNull(message = "The application date of special price to a client cannot be null")
    @PastOrPresent(message = "The application date cannot be in the future")
    Instant cltSpApplieddate;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many clientspecialprice for 1 Client

    @NotNull(message = "The client associated with the special price cannot be null")
    ClientDto cltSpClientDto;
    //Many clientspecialprice for 1 Specialprice

    @NotNull(message = "The special price associated cannot be null")
    SpecialPriceDto cltSpPDto;

    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static ClientSpecialpriceDto fromEntity(ClientSpecialprice csp){
        if(csp == null){
            return null;
        }
        return ClientSpecialpriceDto.builder()
                .id(csp.getId())
                .cltSpApplieddate(csp.getCltSpApplieddate())
                .cltSpClientDto(ClientDto.fromEntity(csp.getCltSpClient()))
                .cltSpPDto(SpecialPriceDto.fromEntity(csp.getCltSpSp()))
                .build();
    }
    public static ClientSpecialprice toEntity(ClientSpecialpriceDto cspDto){
        if(cspDto == null){
            return null;
        }
        ClientSpecialprice csp = new ClientSpecialprice();
        csp.setId(cspDto.getId());
        csp.setCltSpApplieddate(cspDto.getCltSpApplieddate());
        csp.setCltSpClient(ClientDto.toEntity(cspDto.getCltSpClientDto()));
        csp.setCltSpSp(SpecialPriceDto.toEntity(cspDto.getCltSpPDto()));
        return csp;
    }
}
