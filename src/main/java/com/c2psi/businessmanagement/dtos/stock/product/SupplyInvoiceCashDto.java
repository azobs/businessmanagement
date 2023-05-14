package com.c2psi.businessmanagement.dtos.stock.product;

import com.c2psi.businessmanagement.Enumerations.CashSourceType;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
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
    @NotNull(message = "The supply invoice code cannot be null")
    @NotEmpty(message = "The supply invoice code cannot be empty")
    @NotBlank(message = "The supply invoice code cannot be blank")
    @Size(min = 3, max = 20, message = "The supply invoice code size must be between 3 and 20 characters")
    String sicashCode;
    String sicashComment;
    String sicashPicture;
    @NotNull(message = "The delivery date cannot be null")
    @PastOrPresent(message = "The delivery date cannot be in the future")
    Instant sicashDeliveryDate;
    @NotNull(message = "The invoicing date cannot be null")
    @PastOrPresent(message = "The invoicing date cannot be in the future")
    Instant sicashInvoicingDate;
    @NotNull(message = "The amount expected cannot be null")
    @Positive(message = "The amount expected must be positive")
    BigDecimal sicashAmountexpected;
    @NotNull(message = "The amount paid cannot be null")
    @PositiveOrZero(message = "The amount paid must be positive or zero")
    BigDecimal sicashAmountpaid;
    @NotNull(message = "The total number of colis cannot be null")
    @Positive(message = "The total number of colis must be positive")
    BigDecimal sicashTotalcolis;
    @NotNull(message = "The source of cash must be precised")
    CashSourceType sicashSourceofcash;

    /******************************
     * Relation between entities  *
     * ****************************/
    @NotNull(message = "The provider associated cannot be null")
    ProviderDto sicashProviderDto;

    @NotNull(message = "The point of sale associated cannot be null")
    PointofsaleDto sicashPosDto;

    /*@JsonIgnore
    List<CashArrivalDto> cashArrivalDtoList;*/
    @NotNull(message = "The userbm associated cannot be null")
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
                .sicashTotalcolis(sic.getSicashTotalcolis())
                .sicashDeliveryDate(sic.getSicashDeliveryDate())
                .sicashInvoicingDate(sic.getSicashInvoicingDate())
                .sicashPicture(sic.getSicashPicture())
                .sicashSourceofcash(sic.getSicashSourceofcash())
                .sicashProviderDto(ProviderDto.fromEntity(sic.getSicashProvider()))
                .sicashUserbmDto(UserBMDto.fromEntity(sic.getSicashUserbm()))
                .sicashPosDto(PointofsaleDto.fromEntity(sic.getSicashPos()))
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
        sic.setSicashTotalcolis(sicDto.getSicashTotalcolis());
        sic.setSicashCode(sicDto.getSicashCode());
        sic.setSicashComment(sicDto.getSicashComment());
        sic.setSicashDeliveryDate(sicDto.getSicashDeliveryDate());
        sic.setSicashInvoicingDate(sicDto.getSicashInvoicingDate());
        sic.setSicashPicture(sicDto.getSicashPicture());
        sic.setSicashSourceofcash(sicDto.getSicashSourceofcash());
        sic.setSicashProvider(ProviderDto.toEntity(sicDto.getSicashProviderDto()));
        sic.setSicashUserbm(UserBMDto.toEntity(sicDto.getSicashUserbmDto()));
        sic.setSicashPos(PointofsaleDto.toEntity(sicDto.getSicashPosDto()));

        return sic;
    }
}
