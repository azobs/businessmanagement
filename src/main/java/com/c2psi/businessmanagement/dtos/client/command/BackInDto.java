package com.c2psi.businessmanagement.dtos.client.command;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.models.BackIn;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@Builder
public class BackInDto {
    Long id;

    @NotNull(message = "The backin date cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    Instant biDate;
    String biComment;

    @NotNull(message = "The backin command cannot be null")
    CommandDto biCommandDto;
    @NotNull(message = "The backin userbm cannot be null")
    UserBMDto biUserbmDto;
    @NotNull(message = "The backin Pointofsale cannot be null")
    //PointofsaleDto biPosDto;
    Long biPosId;

    public static BackInDto fromEntity(BackIn backIn){
        if(backIn == null) {
            return null;
        }
        return BackInDto.builder()
                .id(backIn.getId())
                .biCommandDto(CommandDto.fromEntity(backIn.getBiCommand()))
                .biComment(backIn.getBiComment())
                .biDate(backIn.getBiDate())
                .biUserbmDto(UserBMDto.fromEntity(backIn.getBiUserbm()))
                //.biPosDto(PointofsaleDto.fromEntity(backIn.getBiPos()))
                .biPosId(backIn.getBiPosId())
                .build();
    }

    public static BackIn toEntity(BackInDto backInDto){
        if(backInDto == null) {
            return null;
        }
        BackIn backIn = new BackIn();
        backIn.setId(backInDto.getId());
        backIn.setBiComment(backIn.getBiComment());
        backIn.setBiDate(backInDto.getBiDate());
        backIn.setBiCommand(CommandDto.toEntity(backInDto.getBiCommandDto()));
        backIn.setBiUserbm(UserBMDto.toEntity(backInDto.getBiUserbmDto()));
        //backIn.setBiPos(PointofsaleDto.toEntity(backInDto.getBiPosDto()));
        backIn.setBiPosId(backInDto.getBiPosId());
        return backIn;
    }
}
