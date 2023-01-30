package com.c2psi.businessmanagement.dtos.client.command;

import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.models.BackIn;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Data
@Builder
public class BackInDto {
    Long id;
    @NotNull(message = "The backin code cannot be null")
    @NotEmpty(message = "The backin code cannot be empty")
    @NotBlank(message = "The backin code cannot be blank value")
    @Size(min = 3, max = 20, message = "The backin code size must be between 3 and 20 characters")
    String biCode;
    @NotNull(message = "The backin date cannot be null")
    Instant biDate;
    String biComment;

    @NotNull(message = "The backin command cannot be null")
    CommandDto biCommand;
    @NotNull(message = "The backin userbm cannot be null")
    UserBMDto biUserbm;

    public static BackInDto fromEntity(BackIn backIn){
        if(backIn == null) {
            return null;
        }
        return BackInDto.builder()
                .id(backIn.getId())
                .biCode(backIn.getBiCode())
                .biCommand(CommandDto.fromEntity(backIn.getBiCommand()))
                .biComment(backIn.getBiComment())
                .biDate(backIn.getBiDate())
                .biUserbm(UserBMDto.fromEntity(backIn.getBiUserbm()))
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
        backIn.setBiCode(backInDto.getBiCode());
        backIn.setBiCommand(CommandDto.toEntity(backInDto.getBiCommand()));
        backIn.setBiUserbm(UserBMDto.toEntity(backInDto.getBiUserbm()));
        return backIn;
    }
}
