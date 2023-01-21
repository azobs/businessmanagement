package com.c2psi.businessmanagement.dtos.client.command;

import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.models.SaleInvoiceDamage;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class SaleInvoiceDamageDto {
    Long id;
    String saleidamCode;

    Integer saleidamNumbertochange;

    Integer saleidamNumberchanged;

    Integer saleidamTotalcolis;

    Instant saleidamDeliveryDate;

    Instant saleidamInvoicingDate;

    PointofsaleDto saleidamPosDto;

    ClientDto saleidamClientDto;

    UserBMDto saleidamUserbmDto;

    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static SaleInvoiceDamageDto fromEntity(SaleInvoiceDamage saleidam){
        if(saleidam == null){
            return null;
        }
        return SaleInvoiceDamageDto.builder()
                .id(saleidam.getId())
                .saleidamCode(saleidam.getSaleidamCode())
                .saleidamDeliveryDate(saleidam.getSaleidamDeliveryDate())
                .saleidamInvoicingDate(saleidam.getSaleidamInvoicingDate())
                .saleidamNumberchanged(saleidam.getSaleidamNumberchanged())
                .saleidamNumbertochange(saleidam.getSaleidamNumbertochange())
                .saleidamTotalcolis(saleidam.getSaleidamTotalcolis())
                .saleidamPosDto(PointofsaleDto.fromEntity(saleidam.getSaleidamPos()))
                .saleidamClientDto(ClientDto.fromEntity(saleidam.getSaleidamClient()))
                .saleidamUserbmDto(UserBMDto.fromEntity(saleidam.getSaleidamUserbm()))
                .build();
    }

    public static SaleInvoiceDamage toEntity(SaleInvoiceDamageDto saleidamDto){
        if(saleidamDto == null){
            return null;
        }
        SaleInvoiceDamage saleidam = new SaleInvoiceDamage();
        saleidam.setId(saleidamDto.getId());
        saleidam.setSaleidamCode(saleidamDto.getSaleidamCode());
        saleidam.setSaleidamClient(ClientDto.toEntity(saleidamDto.saleidamClientDto));
        saleidam.setSaleidamDeliveryDate(saleidamDto.getSaleidamDeliveryDate());
        saleidam.setSaleidamInvoicingDate(saleidamDto.getSaleidamInvoicingDate());
        saleidam.setSaleidamNumberchanged(saleidamDto.getSaleidamNumberchanged());
        saleidam.setSaleidamNumbertochange(saleidamDto.getSaleidamNumbertochange());
        saleidam.setSaleidamPos(PointofsaleDto.toEntity(saleidamDto.getSaleidamPosDto()));
        saleidam.setSaleidamTotalcolis(saleidamDto.saleidamTotalcolis);
        saleidam.setSaleidamUserbm(UserBMDto.toEntity(saleidamDto.getSaleidamUserbmDto()));
        return saleidam;
    }

}
