package com.c2psi.businessmanagement.dtos.client.command;

import com.c2psi.businessmanagement.Enumerations.CashSourceType;
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
    @NotNull(message = "The sale invoice cash code cannot be null")
    @NotEmpty(message = "The sale invoice cash code cannot be empty")
    @NotBlank(message = "The sale invoice cash code cannot be blank")
    @Size(min = 3, max = 20, message = "The sale invoice cash code size must be between 3 and 20 characters")
    String saleicashCode;
    @NotNull(message = "The expected amount of a sale invoice cash cannot be null")
    BigDecimal saleicashAmountexpected;
    @NotNull(message = "The current amount expected of a sale invoice cash cannot be null")
    @Positive(message = "The current amount expected of a sale invoice cash must be positive")
    BigDecimal saleicashCurrentAmountexpected;
    @NotNull(message = "The amount paid of a sale invoice cash cannot be null")
    @PositiveOrZero(message = "The amount paid of a sale invoice cash must be positive or zero")
    BigDecimal saleicashAmountpaid;
    @NotNull(message = "The amount reimbourse of a sale invoice cash cannot be null")
    @PositiveOrZero(message = "The amount reimbourse of a sale invoice cash must be positive or zero")
    BigDecimal saleicashAmountreimbourse;
    @NotNull(message = "The total colis of a sale invoice cash cannot be null")
    @Positive(message = "The total colis of a sale invoice cash must be positive")
    BigDecimal saleicashTotalcolis;
    @NotNull(message = "The source of cash of a sale invoice cash cannot be null")
    CashSourceType saleicashSourceofcash;
    @NotNull(message = "The invoicing date of a sale invoice cash cannot be null")
    @PastOrPresent(message = "The invoicing date of a sale invoice cash cannot be in the future")
    Instant saleicashInvoicingDate;
    Instant saleicashDeliveryDate;

    @NotNull(message = "The point of sale concerned by the sale invoice cash cannot be null")
    PointofsaleDto saleicashPosDto;
    @NotNull(message = "The client concerned by the sale invoice cash cannot be null")
    ClientDto saleicashClientDto;
    @NotNull(message = "The userbm concerned by the sale invoice cash cannot be null")
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
                .saleicashCurrentAmountexpected(sic.getSaleicashCurrentAmountexpected())
                .saleicashAmountexpected(sic.getSaleicashAmountexpected())
                .saleicashAmountpaid(sic.getSaleicashAmountpaid())
                .saleicashAmountreimbourse(sic.getSaleicashAmountreimbourse())
                .saleicashTotalcolis(sic.getSaleicashTotalcolis())
                .saleicashSourceofcash(sic.getSaleicashSourceofcash())
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
        sic.setSaleicashCurrentAmountexpected(sicDto.getSaleicashCurrentAmountexpected());
        sic.setSaleicashAmountexpected(sicDto.getSaleicashAmountexpected());
        sic.setSaleicashAmountpaid(sicDto.getSaleicashAmountpaid());
        sic.setSaleicashAmountreimbourse(sicDto.getSaleicashAmountreimbourse());
        sic.setSaleicashTotalcolis(sicDto.getSaleicashTotalcolis());
        sic.setSaleicashSourceofcash(sicDto.getSaleicashSourceofcash());
        sic.setSaleicashDeliveryDate(sicDto.getSaleicashDeliveryDate());
        sic.setSaleicashInvoicingDate(sicDto.getSaleicashInvoicingDate());
        sic.setSaleicashPos(PointofsaleDto.toEntity(sicDto.getSaleicashPosDto()));
        sic.setSaleicashClient(ClientDto.toEntity(sicDto.getSaleicashClientDto()));
        sic.setSaleicashUserbm(UserBMDto.toEntity(sicDto.getSaleicashUserbmDto()));
        return sic;
    }
}
