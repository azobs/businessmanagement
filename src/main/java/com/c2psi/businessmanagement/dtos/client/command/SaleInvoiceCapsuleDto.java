package com.c2psi.businessmanagement.dtos.client.command;

import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.models.SaleInvoiceCapsule;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
public class SaleInvoiceCapsuleDto {
    Long id;
    @NotNull(message = "The sale invoice code cannot be null")
    @NotEmpty(message = "The sale invoice code cannot be empty")
    @NotBlank(message = "The sale invoice code cannot be blank")
    @Size(min = 3, max = 20, message = "The sale invoice code size must be between 3 and 20 characters")
    String saleicapsCode;
    @NotNull(message = "The sale invoice number of capsule to change cannot be null")
    @Positive(message = "The sale invoice number of capsule to change must be positive")
    BigDecimal saleicapsNumbertochange;
    @NotNull(message = "The sale invoice number of capsule changed cannot be null")
    @Positive(message = "The sale invoice number of capsule changed must be positive")
    BigDecimal saleicapsNumberchanged;
    @NotNull(message = "The sale invoice total colis changed cannot be null")
    @Positive(message = "The sale invoice total colis changed must be positive")
    BigDecimal saleicapsTotalcolis;
    @NotNull(message = "The sale invoice delivery date changed cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    Instant saleicapsDeliveryDate;
    @NotNull(message = "The sale invoice invoicing date changed cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    Instant saleicapsInvoicingDate;

    @NotNull(message = "The point of sale concerned by the sale invoice cannot be null")
    //PointofsaleDto saleicapsPosDto;
    Long saleicapsPosId;
    @NotNull(message = "The client concerned by the sale invoice cannot be null")
    ClientDto saleicapsClientDto;
    @NotNull(message = "The Userbm concerned by the sale invoice cannot be null")
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
                //.saleicapsPosDto(PointofsaleDto.fromEntity(sics.getSaleicapsPos()))
                .saleicapsPosId(sics.getSaleicapsPosId())
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
        //sics.setSaleicapsPos(PointofsaleDto.toEntity(sicsDto.getSaleicapsPosDto()));
        sics.setSaleicapsPosId(sicsDto.getSaleicapsPosId());
        sics.setSaleicapsClient(ClientDto.toEntity(sicsDto.getSaleicapsClientDto()));
        sics.setSaleicapsUserbm(UserBMDto.toEntity(sicsDto.getSaleicapsUserbmDto()));
        return sics;
    }
}
