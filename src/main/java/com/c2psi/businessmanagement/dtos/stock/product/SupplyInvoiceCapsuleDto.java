package com.c2psi.businessmanagement.dtos.stock.product;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;
import com.c2psi.businessmanagement.models.Pointofsale;
import com.c2psi.businessmanagement.models.SupplyInvoiceCapsule;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class SupplyInvoiceCapsuleDto {
    Long id;
    @NotNull(message = "The supply invoice code cannot be null")
    @NotEmpty(message = "The supply invoice code cannot be empty")
    @NotBlank(message = "The supply invoice code cannot be blank")
    @Size(min = 3, max = 20, message = "The supply invoice code size must be between 3 and 20 characters")
    String sicapsCode;
    String sicapsComment;
    String sicapsPicture;
    @NotNull(message = "The delivery date cannot be null")
    @PastOrPresent(message = "The delivery date cannot be in the future")
    Instant sicapsDeliveryDate;
    @NotNull(message = "The invoicing date cannot be null")
    @PastOrPresent(message = "The invoicing date cannot be in the future")
    Instant sicapsInvoicingDate;
    @NotNull(message = "The total colis delivery cannot be null")
    @Positive(message = "The total colis delivery must be positive")
    Integer sicapsTotalcolis;
    @NotNull(message = "The total capsule to change cannot be null")
    @Positive(message = "The total capsule to change must be positive")
    Integer sicapsTotalCapsToChange;
    @NotNull(message = "The total capsule changed cannot be null")
    @Positive(message = "The total capsule changed must be positive")
    Integer sicapsTotalCapsChange;
    /******************************
     * Relation between entities  *
     * ****************************/
    @NotNull(message = "The provider associated cannot be null")
    ProviderDto sicapsProviderDto;

    /*@JsonIgnore
    List<CapsuleArrivalDto> capsuleArrivalDtoList;*/
    @NotNull(message = "The userbm associated cannot be null")
    UserBMDto sicapsUserbmDto;
    @NotNull(message = "The point of sale associated cannot be null")
    PointofsaleDto sicapsPosDto;
    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static SupplyInvoiceCapsuleDto fromEntity(SupplyInvoiceCapsule sics){
        if(sics == null){
            return null;
        }
        return SupplyInvoiceCapsuleDto.builder()
                .id(sics.getId())
                .sicapsCode(sics.getSicapsCode())
                .sicapsComment(sics.getSicapsComment())
                .sicapsPicture(sics.getSicapsPicture())
                .sicapsDeliveryDate(sics.getSicapsDeliveryDate())
                .sicapsInvoicingDate(sics.getSicapsInvoicingDate())
                .sicapsTotalcolis(sics.getSicapsTotalcolis())
                .sicapsTotalCapsToChange(sics.getSicapsTotalCapsToChange())
                .sicapsTotalCapsChange(sics.getSicapsTotalCapsChange())
                .sicapsProviderDto(ProviderDto.fromEntity(sics.getSicapsProvider()))
                .sicapsUserbmDto(UserBMDto.fromEntity(sics.getSicapsUserbm()))
                .sicapsPosDto(PointofsaleDto.fromEntity(sics.getSicapsPos()))
                /*.capsuleArrivalDtoList(sics.getCapsuleArrivalList() != null ?
                        sics.getCapsuleArrivalList().stream()
                        .map(CapsuleArrivalDto::fromEntity)
                        .collect(Collectors.toList()) : null)*/
                .build();
    }
    public static SupplyInvoiceCapsule toEntity(SupplyInvoiceCapsuleDto sicsDto){
        if(sicsDto == null){
            return null;
        }
        SupplyInvoiceCapsule sics = new SupplyInvoiceCapsule();
        sics.setId(sicsDto.getId());
        sics.setSicapsCode(sicsDto.getSicapsCode());
        sics.setSicapsComment(sicsDto.getSicapsComment());
        sics.setSicapsDeliveryDate(sicsDto.getSicapsDeliveryDate());
        sics.setSicapsInvoicingDate(sicsDto.getSicapsInvoicingDate());
        sics.setSicapsPicture(sicsDto.getSicapsPicture());
        sics.setSicapsProvider(ProviderDto.toEntity(sicsDto.getSicapsProviderDto()));
        sics.setSicapsTotalcolis(sicsDto.getSicapsTotalcolis());
        sics.setSicapsTotalCapsToChange(sicsDto.getSicapsTotalCapsToChange());
        sics.setSicapsTotalCapsChange(sicsDto.getSicapsTotalCapsChange());
        sics.setSicapsUserbm(UserBMDto.toEntity(sicsDto.getSicapsUserbmDto()));
        sics.setSicapsPos(PointofsaleDto.toEntity(sicsDto.getSicapsPosDto()));
        /*sics.setCapsuleArrivalList(sicsDto.getCapsuleArrivalDtoList() != null ?
                sicsDto.getCapsuleArrivalDtoList().stream()
                .map(CapsuleArrivalDto::toEntity)
                .collect(Collectors.toList()) : null);*/
        return sics;
    }
}
