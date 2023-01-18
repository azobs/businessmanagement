package com.c2psi.businessmanagement.dtos.stock.product;

import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;
import com.c2psi.businessmanagement.models.SupplyInvoiceCash;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class SupplyInvoiceCashDto {
    Long id;
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 20)
    String sicashCode;
    String sicashComment;
    String sicashPicture;
    @PastOrPresent
    Instant sicashDeliveryDate;
    @PastOrPresent
    Instant sicashInvoicingDate;
    @NotNull
    @PositiveOrZero
    BigDecimal sicashAmountexpected;
    @NotNull
    @PositiveOrZero
    BigDecimal sicashAmountpaid;
    @NotNull
    @PositiveOrZero
    Integer sicapsTotalcolis;

    /******************************
     * Relation between entities  *
     * ****************************/
    @NotNull
    ProviderDto sicashProviderDto;

    //List of specialprice applied to a client
    /*@JsonIgnore
    List<CashArrivalDto> cashArrivalDtoList;*/
    @NotNull
    UserBMDto sicashUserbmDto;
    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static SupplyInvoiceCashDto fromEntity(SupplyInvoiceCash sic){
        if(sic == null){
            return null;
        }
        return SupplyInvoiceCashDto.builder()
                .id(sic.getId())
                .sicashCode(sic.getSicashCode())
                .sicashComment(sic.getSicashComment())
                .sicashAmountexpected(sic.getSicashAmountexpected())
                .sicashAmountpaid(sic.getSicashAmountpaid())
                .sicapsTotalcolis(sic.getSicashTotalcolis())
                .sicashDeliveryDate(sic.getSicashDeliveryDate())
                .sicashInvoicingDate(sic.getSicashInvoicingDate())
                .sicashPicture(sic.getSicashPicture())
                .sicashProviderDto(ProviderDto.fromEntity(sic.getSicashProvider()))
                .sicashUserbmDto(UserBMDto.fromEntity(sic.getSicashUserbm()))
                /*.cashArrivalDtoList(sic.getCashArrivalList() != null ?
                        sic.getCashArrivalList().stream()
                                .map(CashArrivalDto::fromEntity)
                                .collect(Collectors.toList()) : null)*/
                .build();
    }
    public static SupplyInvoiceCash toEntity(SupplyInvoiceCashDto sicDto){
        if(sicDto == null){
            return null;
        }
        SupplyInvoiceCash sic = new SupplyInvoiceCash();
        sic.setId(sicDto.getId());
        sic.setSicashAmountexpected(sicDto.getSicashAmountexpected());
        sic.setSicashAmountpaid(sicDto.getSicashAmountpaid());
        sic.setSicashTotalcolis(sicDto.getSicapsTotalcolis());
        sic.setSicashCode(sicDto.getSicashCode());
        sic.setSicashComment(sicDto.getSicashComment());
        sic.setSicashDeliveryDate(sicDto.getSicashDeliveryDate());
        sic.setSicashInvoicingDate(sicDto.getSicashInvoicingDate());
        sic.setSicashPicture(sicDto.getSicashPicture());
        sic.setSicashProvider(ProviderDto.toEntity(sicDto.getSicashProviderDto()));
        sic.setSicashUserbm(UserBMDto.toEntity(sicDto.getSicashUserbmDto()));
        /*sic.setCashArrivalList(sicDto.getCashArrivalDtoList() != null ?
                sicDto.getCashArrivalDtoList().stream()
                        .map(CashArrivalDto::toEntity)
                        .collect(Collectors.toList()) : null);*/
        return sic;
    }
}
