package com.c2psi.businessmanagement.dtos.pos.loading;

import com.c2psi.businessmanagement.Enumerations.LoadingState;
import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.dtos.client.command.CommandDto;
import com.c2psi.businessmanagement.dtos.client.command.SaleDto;
import com.c2psi.businessmanagement.dtos.client.command.SaleInvoiceCapsuleDto;
import com.c2psi.businessmanagement.dtos.client.command.SaleInvoiceCashDto;
import com.c2psi.businessmanagement.dtos.client.delivery.DeliveryDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.models.Command;
import com.c2psi.businessmanagement.models.Loading;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class LoadingDto {
    Long id;
    @NotNull(message = "The loading code cannot be null")
    @NotEmpty(message = "The loading code cannot be empty")
    @NotBlank(message = "The loading code cannot be blank value")
    @Size(min = 3, max = 20, message = "The loading code must have at least 3 and at most 20 character")
    String loadCode;
    @NotNull(message = "The loading date cannot be null")
    @PastOrPresent(message = "The loading date cannot be in the future")
    Instant loadDate;
    @NotNull(message = "The loading state cannot be null")
    LoadingState loadState;
    @NotNull(message = "The amount expected for a loading cannot be null")
    @Positive(message = "The total amount expected must be positive")
    BigDecimal loadTotalamountexpected;
    @PositiveOrZero(message = "The total amount expected must be positive or null")
    BigDecimal loadTotalamountpaid;
    String loadSalereport;
    String loadComment;

    @NotNull(message = "The pointofsale in which the loading has been done cannot be null")
    PointofsaleDto loadPosDto;

    @NotNull(message = "The user who fill the loading cannot be null")
    UserBMDto loadUserbmManagerDto;

    @NotNull(message = "The user saler responsible of the loading cannot be null")
    UserBMDto loadUserbmSalerDto;

    /*@JsonIgnore
    List<LoadingDetailsDto> loadingDetailsDtoList;

    @JsonIgnore
    List<PackingDetailsDto> packingDetailsDtoList;*/

    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static LoadingDto fromEntity(Loading load) {
        if (load == null) {
            return null;
        }
        return LoadingDto.builder()
                .id(load.getId())
                .loadCode(load.getLoadCode())
                .loadState(load.getLoadState())
                .loadComment(load.getLoadComment())
                .loadSalereport(load.getLoadSalereport())
                .loadPosDto(PointofsaleDto.fromEntity(load.getLoadPos()))
                .loadUserbmManagerDto(UserBMDto.fromEntity(load.getLoadUserbmManager()))
                .loadUserbmSalerDto(UserBMDto.fromEntity(load.getLoadUserbmSaler()))
                /*.loadingDetailsDtoList(load.getLoadingDetailsList() != null ?
                        load.getLoadingDetailsList().stream()
                                .map(LoadingDetailsDto::fromEntity)
                                .collect(Collectors.toList()) : null)
                .packingDetailsDtoList(load.getPackingDetailsList() != null ?
                        load.getPackingDetailsList().stream()
                                .map(PackingDetailsDto::fromEntity)
                                .collect(Collectors.toList()) : null)*/
                .build();
    }

    public static Loading toEntity(LoadingDto loadDto) {
        if (loadDto == null) {
            return null;
        }
        Loading load = new Loading();
        load.setId(loadDto.getId());
        load.setLoadCode(loadDto.getLoadCode());
        load.setLoadDate(loadDto.getLoadDate());
        load.setLoadState(loadDto.getLoadState());
        load.setLoadComment(loadDto.getLoadComment());
        load.setLoadSalereport(loadDto.getLoadSalereport());
        load.setLoadTotalamountexpected(loadDto.getLoadTotalamountexpected());
        load.setLoadTotalamountpaid(loadDto.getLoadTotalamountpaid());

        load.setLoadUserbmManager(UserBMDto.toEntity(loadDto.getLoadUserbmManagerDto()));
        load.setLoadUserbmSaler(UserBMDto.toEntity(loadDto.getLoadUserbmSalerDto()));
        load.setLoadPos(PointofsaleDto.toEntity(loadDto.getLoadPosDto()));
        /*load.setLoadingDetailsList(loadDto.getLoadingDetailsDtoList() != null?
                loadDto.getLoadingDetailsDtoList().stream()
                .map(LoadingDetailsDto::toEntity)
                .collect(Collectors.toList()):null);
        load.setPackingDetailsList(loadDto.getPackingDetailsDtoList() != null?
                loadDto.getPackingDetailsDtoList().stream()
                        .map(PackingDetailsDto::toEntity)
                        .collect(Collectors.toList()):null);*/

        return load;
    }

}
