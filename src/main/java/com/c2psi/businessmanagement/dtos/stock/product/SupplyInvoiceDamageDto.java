package com.c2psi.businessmanagement.dtos.stock.product;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;
import com.c2psi.businessmanagement.models.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
public class SupplyInvoiceDamageDto {
    Long id;
    @NotNull(message = "The code cannot be null")
    @NotEmpty(message = "The code cannot be empty")
    @NotBlank(message = "The code cannot be blank")
    @Size(min = 3, max = 20, message = "The code size must be between 3 and 20 characters")
    String sidamCode;
    String sidamComment;
    String sidamPicture;
    @NotNull(message = "The delivery date cannot be null")
    @PastOrPresent(message = "The delivery date cannot ne be in the future")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    Instant sidamDeliveryDate;
    @NotNull(message = "The invoicing date cannot be null")
    @PastOrPresent(message = "The invoicing date cannot be in the future")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    Instant sidamInvoicingDate;
    @NotNull(message = "The total number of colis cannot be null")
    @Positive(message = "The total number of colis must be positive")
    BigDecimal sidamTotalcolis;
    @NotNull(message = "The total number of article to change cannot be null")
    @Positive(message = "The total number of article must be positive")
    BigDecimal sidamTotalDamToChange;
    @NotNull(message = "The total number of article changed cannot be null")
    @Positive(message = "The total number of article changed must be positive")
    BigDecimal sidamTotalDamChange;
    @NotNull(message = "The provider associated cannot be null")
    ProviderDto sidamProviderDto;
    @NotNull(message = "The userbm associated cannot be null")
    UserBMDto sidamUserbmDto;
    /*@NotNull(message = "The pointofsale associated cannot be null")
    PointofsaleDto sidamPosDto;*/
    Long sidamPosId;
    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static SupplyInvoiceDamageDto fromEntity(SupplyInvoiceDamage sid) {
        if (sid == null) {
            return null;
        }
        return SupplyInvoiceDamageDto.builder()
                .id(sid.getId())
                .sidamCode(sid.getSidamCode())
                .sidamComment(sid.getSidamComment())
                .sidamDeliveryDate(sid.getSidamDeliveryDate())
                .sidamInvoicingDate(sid.getSidamInvoicingDate())
                .sidamPicture(sid.getSidamPicture())
                //.sidamPosDto(PointofsaleDto.fromEntity(sid.getSidamPos()))
                .sidamPosId(sid.getSidamPosId())
                .sidamProviderDto(ProviderDto.fromEntity(sid.getSidamProvider()))
                .sidamTotalDamChange(sid.getSidamTotalDamChange())
                .sidamTotalDamToChange(sid.getSidamTotalDamToChange())
                .sidamTotalcolis(sid.getSidamTotalcolis())
                .sidamUserbmDto(UserBMDto.fromEntity(sid.getSidamUserbm()))
                .build();
    }

    public static SupplyInvoiceDamage toEntity(SupplyInvoiceDamageDto sidDto) {
        if (sidDto == null) {
            return null;
        }
        SupplyInvoiceDamage sid = new SupplyInvoiceDamage();
        sid.setId(sidDto.getId());
        sid.setSidamCode(sidDto.getSidamCode());
        sid.setSidamComment(sidDto.getSidamComment());
        sid.setSidamDeliveryDate(sidDto.getSidamDeliveryDate());
        sid.setSidamInvoicingDate(sidDto.getSidamInvoicingDate());
        sid.setSidamPicture(sidDto.getSidamPicture());
        //sid.setSidamPos(PointofsaleDto.toEntity(sidDto.getSidamPosDto()));
        sid.setSidamPosId(sidDto.getSidamPosId());
        sid.setSidamProvider(ProviderDto.toEntity(sidDto.getSidamProviderDto()));
        sid.setSidamTotalcolis(sidDto.getSidamTotalcolis());
        sid.setSidamUserbm(UserBMDto.toEntity(sidDto.getSidamUserbmDto()));
        sid.setSidamTotalDamChange(sidDto.getSidamTotalDamChange());
        sid.setSidamTotalDamToChange(sidDto.getSidamTotalDamToChange());
        return sid;
    }

}
