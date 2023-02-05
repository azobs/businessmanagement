package com.c2psi.businessmanagement.dtos.client.command;

import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.models.SaleInvoiceDamage;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.Instant;

@Data
@Builder
public class SaleInvoiceDamageDto {
    Long id;
    @NotNull(message = "The sale invoice code cannot be null")
    @NotEmpty(message = "The sale invoice code cannot be empty")
    @NotBlank(message = "The sale invoice code cannot be blank")
    @Size(min = 3, max = 20, message = "The sale invoice code size must be between 3 and 20 characters")
    String saleidamCode;
    @NotNull(message = "The sale invoice number of damage to change cannot be null")
    @Positive(message = "The sale invoice number of damage to change must be positive")
    Integer saleidamNumbertochange;
    @NotNull(message = "The sale invoice number of damage change cannot be null")
    @Positive(message = "The sale invoice number of damage change must be positive")
    Integer saleidamNumberchanged;
    @NotNull(message = "The sale invoice number of colis cannot be null")
    @Positive(message = "The sale invoice number of colis must be positive")
    Integer saleidamTotalcolis;
    @NotNull(message = "The sale invoice delivery date changed cannot be null")
    Instant saleidamDeliveryDate;
    @NotNull(message = "The sale invoice delivery date changed cannot be null")
    Instant saleidamInvoicingDate;
    @NotNull(message = "The point of sale concerned by the sale invoice cannot be null")
    PointofsaleDto saleidamPosDto;
    @NotNull(message = "The client concerned by the sale invoice cannot be null")
    ClientDto saleidamClientDto;
    @NotNull(message = "The Userbm concerned by the sale invoice cannot be null")
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
