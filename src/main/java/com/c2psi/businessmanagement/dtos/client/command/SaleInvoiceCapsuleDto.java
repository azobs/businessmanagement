package com.c2psi.businessmanagement.dtos.client.command;

import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.models.Pointofsale;
import com.c2psi.businessmanagement.models.SaleInvoiceCapsule;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.Instant;

@Data
@Builder
public class SaleInvoiceCapsuleDto {
    Long id;
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 20)
    String saleicapsCode;
    @NotNull
    @PositiveOrZero
    Integer saleicapsNumbertochange;
    @NotNull
    @PositiveOrZero
    Integer saleicapsNumberchanged;
    @PositiveOrZero
    Integer saleicapsTotalcolis;
    @FutureOrPresent
    Instant saleicapsDeliveryDate;
    @PastOrPresent
    Instant saleicapsInvoicingDate;

    @NotNull
    PointofsaleDto saleicapsPosDto;
    @NotNull
    ClientDto saleicapsClientDto;
    @NotNull
    UserBMDto saleicapsUserbmDto;
    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static SaleInvoiceCapsuleDto fromEntity(SaleInvoiceCapsule sics){
        if(sics == null){
            return null;
        }
        return SaleInvoiceCapsuleDto.builder()
                .id(sics.getId())
                .saleicapsCode(sics.getSaleicapsCode())
                .saleicapsNumbertochange(sics.getSaleicapsNumbertochange())
                .saleicapsNumberchanged(sics.getSaleicapsNumberchanged())
                .saleicapsTotalcolis(sics.getSaleicapsTotalcolis())
                .saleicapsDeliveryDate(sics.getSaleicapsDeliveryDate())
                .saleicapsInvoicingDate(sics.getSaleicapsInvoicingDate())
                .saleicapsPosDto(PointofsaleDto.fromEntity(sics.getSaleicapsPos()))
                .saleicapsClientDto(ClientDto.fromEntity(sics.getSaleicapsClient()))
                .saleicapsUserbmDto(UserBMDto.fromEntity(sics.getSaleicapsUserbm()))
                .build();
    }
    public static SaleInvoiceCapsule toEntity(SaleInvoiceCapsuleDto sicsDto){
        if(sicsDto == null){
            return null;
        }
        SaleInvoiceCapsule sics = new SaleInvoiceCapsule();
        sics.setId(sicsDto.getId());
        sics.setSaleicapsCode(sicsDto.getSaleicapsCode());
        sics.setSaleicapsNumberchanged(sicsDto.getSaleicapsNumberchanged());
        sics.setSaleicapsNumbertochange(sicsDto.getSaleicapsNumbertochange());
        sics.setSaleicapsTotalcolis(sicsDto.getSaleicapsTotalcolis());
        sics.setSaleicapsDeliveryDate(sicsDto.getSaleicapsDeliveryDate());
        sics.setSaleicapsInvoicingDate(sicsDto.getSaleicapsInvoicingDate());
        sics.setSaleicapsPos(PointofsaleDto.toEntity(sicsDto.getSaleicapsPosDto()));
        sics.setSaleicapsClient(ClientDto.toEntity(sicsDto.getSaleicapsClientDto()));
        sics.setSaleicapsUserbm(UserBMDto.toEntity(sicsDto.getSaleicapsUserbmDto()));
        return sics;
    }
}
