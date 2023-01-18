package com.c2psi.businessmanagement.dtos.stock.product;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;
import com.c2psi.businessmanagement.models.*;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.Instant;

@Data
@Builder
public class SupplyInvoiceDamageDto {
    Long id;
    @NotNull
    @NotEmpty
    String sidamCode;
    String sidamComment;
    String sidamPicture;
    @PastOrPresent
    Instant sidamDeliveryDate;
    @PastOrPresent
    Instant sidamInvoicingDate;
    @NotNull
    @PositiveOrZero
    Integer sidamTotalcolis;
    @NotNull
    @Positive
    Integer sidamTotalDamToChange;
    @NotNull
    @Positive
    Integer sidamTotalDamChange;
    @NotNull
    ProviderDto sidamProviderDto;
    @NotNull
    UserBMDto sidamUserbmDto;
    @NotNull
    PointofsaleDto sidamPosDto;
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
                .sidamPosDto(PointofsaleDto.fromEntity(sid.getSidamPos()))
                .sidamProviderDto(ProviderDto.fromEntity(sid.getSidamProvider()))
                .sidamTotalDamChange(sid.getSidamTotalDamChange())
                .sidamTotalDamToChange(sid.getSidamTotalDamToChange())
                .sidamUserbmDto(UserBMDto.fromEntity(sid.getSidamUserbm()))
                .build();
    }

    public static SupplyInvoiceDamage toEntity(SupplyInvoiceDamageDto sidDto) {
        if (sidDto == null) {
            return null;
        }
        SupplyInvoiceDamage sid = new SupplyInvoiceDamage();
        sid.setSidamCode(sidDto.getSidamCode());
        sid.setSidamComment(sidDto.getSidamComment());
        sid.setSidamDeliveryDate(sidDto.getSidamDeliveryDate());
        sid.setSidamInvoicingDate(sidDto.getSidamInvoicingDate());
        sid.setSidamPicture(sidDto.getSidamPicture());
        sid.setSidamPos(PointofsaleDto.toEntity(sidDto.getSidamPosDto()));
        sid.setSidamProvider(ProviderDto.toEntity(sidDto.getSidamProviderDto()));
        sid.setSidamTotalcolis(sidDto.getSidamTotalcolis());
        sid.setSidamUserbm(UserBMDto.toEntity(sidDto.getSidamUserbmDto()));
        sid.setSidamTotalDamChange(sidDto.getSidamTotalDamChange());
        sid.setSidamTotalDamToChange(sidDto.getSidamTotalDamToChange());
        return sid;
    }

}
