package com.c2psi.businessmanagement.dtos.client.command;

import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.models.Client;
import com.c2psi.businessmanagement.models.Pointofsale;
import com.c2psi.businessmanagement.models.SaleInvoiceCash;
import com.c2psi.businessmanagement.models.UserBM;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
public class SaleInvoiceCashDto {
    Long id;
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 20)
    String saleicashCode;
    @NotNull
    @Positive
    BigDecimal saleicashAmountexpected;
    @NotNull
    @PositiveOrZero
    BigDecimal saleicashAmountpaid;
    @NotNull
    @PositiveOrZero
    BigDecimal saleicashAmountreimbourse;
    @PositiveOrZero
    Integer saleicashTotalcolis;
    @PastOrPresent
    Instant saleicashInvoicingDate;
    @FutureOrPresent
    Instant saleicashDeliveryDate;

    @NotNull
    PointofsaleDto saleicashPosDto;
    @NotNull
    ClientDto saleicashClientDto;
    @NotNull
    UserBMDto saleicashUserbmDto;
    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static SaleInvoiceCashDto fromEntity(SaleInvoiceCash sic){
        if(sic == null){
            return null;
        }
        return SaleInvoiceCashDto.builder()
                .id(sic.getId())
                .saleicashCode(sic.getSaleicashCode())
                .saleicashAmountexpected(sic.getSaleicashAmountexpected())
                .saleicashAmountpaid(sic.getSaleicashAmountpaid())
                .saleicashAmountreimbourse(sic.getSaleicashAmountreimbourse())
                .saleicashTotalcolis(sic.getSaleicashTotalcolis())
                .saleicashInvoicingDate(sic.getSaleicashInvoicingDate())
                .saleicashDeliveryDate(sic.getSaleicashDeliveryDate())
                .saleicashPosDto(PointofsaleDto.fromEntity(sic.getSaleicashPos()))
                .saleicashClientDto(ClientDto.fromEntity(sic.getSaleicashClient()))
                .saleicashUserbmDto(UserBMDto.fromEntity(sic.getSaleicashUserbm()))
                .build();
    }
    public static SaleInvoiceCash toEntity(SaleInvoiceCashDto sicDto){
        if(sicDto == null){
            return null;
        }
        SaleInvoiceCash sic = new SaleInvoiceCash();
        sic.setId(sicDto.getId());
        sic.setSaleicashCode(sicDto.getSaleicashCode());
        sic.setSaleicashAmountexpected(sicDto.getSaleicashAmountexpected());
        sic.setSaleicashAmountpaid(sicDto.getSaleicashAmountpaid());
        sic.setSaleicashAmountreimbourse(sicDto.getSaleicashAmountreimbourse());
        sic.setSaleicashTotalcolis(sicDto.getSaleicashTotalcolis());
        sic.setSaleicashDeliveryDate(sicDto.getSaleicashDeliveryDate());
        sic.setSaleicashInvoicingDate(sicDto.getSaleicashInvoicingDate());
        sic.setSaleicashPos(PointofsaleDto.toEntity(sicDto.getSaleicashPosDto()));
        sic.setSaleicashClient(ClientDto.toEntity(sicDto.getSaleicashClientDto()));
        sic.setSaleicashUserbm(UserBMDto.toEntity(sicDto.getSaleicashUserbmDto()));
        return sic;
    }
}
