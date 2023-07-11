package com.c2psi.businessmanagement.dtos.pos.loading;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.models.Loading;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.Instant;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    Instant loadDate;
    /*@NotNull(message = "The loading state cannot be null")
    LoadingState loadState;*/
    @NotNull(message = "The amount expected for a loading cannot be null")
    @Positive(message = "The total amount expected must be positive")
    BigDecimal loadTotalamountexpected;
    @PositiveOrZero(message = "The total amount really paid after selling must be positive or null")
    BigDecimal loadTotalamountpaid;
    @PositiveOrZero(message = "The total amount of remise must be positive or null")
    BigDecimal loadRemise;
    String loadSalereport;
    String loadComment;

    @NotNull(message = "The pointofsale in which the loading has been done cannot be null")
    //PointofsaleDto loadPosDto;
    Long loadPosId;

    @NotNull(message = "The user who fill the loading cannot be null")
    UserBMDto loadUserbmManagerDto;

    @NotNull(message = "The user saler responsible of the loading cannot be null")
    UserBMDto loadUserbmSalerDto;

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
                .loadDate(load.getLoadDate())
                //.loadState(load.getLoadState())
                .loadTotalamountexpected(load.getLoadTotalamountexpected())
                .loadTotalamountpaid(load.getLoadTotalamountpaid())
                .loadComment(load.getLoadComment())
                .loadSalereport(load.getLoadSalereport())
                //.loadPosDto(PointofsaleDto.fromEntity(load.getLoadPos()))
                .loadPosId(load.getLoadPosId())
                .loadUserbmManagerDto(UserBMDto.fromEntity(load.getLoadUserbmManager()))
                .loadUserbmSalerDto(UserBMDto.fromEntity(load.getLoadUserbmSaler()))

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
        //load.setLoadState(loadDto.getLoadState());
        load.setLoadComment(loadDto.getLoadComment());
        load.setLoadSalereport(loadDto.getLoadSalereport());
        load.setLoadTotalamountexpected(loadDto.getLoadTotalamountexpected());
        load.setLoadTotalamountpaid(loadDto.getLoadTotalamountpaid());

        load.setLoadUserbmManager(UserBMDto.toEntity(loadDto.getLoadUserbmManagerDto()));
        load.setLoadUserbmSaler(UserBMDto.toEntity(loadDto.getLoadUserbmSalerDto()));
        //load.setLoadPos(PointofsaleDto.toEntity(loadDto.getLoadPosDto()));
        load.setLoadPosId(loadDto.getLoadPosId());

        return load;
    }

}
