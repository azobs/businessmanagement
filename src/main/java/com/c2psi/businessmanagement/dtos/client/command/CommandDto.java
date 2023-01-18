package com.c2psi.businessmanagement.dtos.client.command;

import com.c2psi.businessmanagement.Enumerations.CommandState;
import com.c2psi.businessmanagement.Enumerations.CommandStatus;
import com.c2psi.businessmanagement.Enumerations.CommandType;
import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.dtos.client.delivery.DeliveryDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.models.Command;
import com.c2psi.businessmanagement.models.Pointofsale;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class CommandDto {
    Long id;
    @NotNull(message = "The command code cannot be null")
    @NotEmpty(message = "The command code cannot be empty")
    @NotBlank(message = "The command code cannot be blank value")
    @Size(min = 3, max = 20, message = "The command code size must be between 3 and 20 characters")
    String cmdCode;
    @NotNull(message = "The command date cannot be null")
    Instant cmdDate;
    @NotNull(message = "The command state cannot be null")
    CommandState cmdState;
    String cmdComment;
    @NotNull(message = "The command type cannot be null")
    CommandType cmdType;
    @NotNull(message = "The command status cannot be null")
    CommandStatus cmdStatus;

    /******************************
     * Relation between entities  *
     * ****************************/
    DeliveryDto cmdDeliveryDto;

    @NotNull(message = "The concerning pointofsale cannot be null")
    PointofsaleDto cmdPosDto;

    /*@JsonIgnore
    List<SaleDto> saleDtoList;*/

    @NotNull(message = "The concerning client cannot be null")
    ClientDto cmdClientDto;

    @NotNull(message = "The userbm who register the command cannot be null")
    UserBMDto cmdUserbmDto;

    /******************************
     * Relation between entities  *
     * ****************************/

    SaleInvoiceCashDto cmdSaleicashDto;

    SaleInvoiceCapsuleDto cmdSicapsDto;

    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static CommandDto fromEntity(Command cmd){
        if(cmd == null){
            return null;
        }
        return CommandDto.builder()
                .id(cmd.getId())
                .cmdCode(cmd.getCmdCode())
                .cmdState(cmd.getCmdState())
                .cmdComment(cmd.getCmdComment())
                .cmdType(cmd.getCmdType())
                .cmdStatus(cmd.getCmdStatus())
                .cmdDeliveryDto(DeliveryDto.fromEntity(cmd.getCmdDelivery()))
                .cmdClientDto(ClientDto.fromEntity(cmd.getCmdClient()))
                .cmdPosDto(PointofsaleDto.fromEntity(cmd.getCommandPos()))
                .cmdUserbmDto(UserBMDto.fromEntity(cmd.getCmdUserbm()))
                .cmdSaleicashDto(SaleInvoiceCashDto.fromEntity(cmd.getCmdSaleicash()))
                .cmdSicapsDto(SaleInvoiceCapsuleDto.fromEntity(cmd.getCmdSicaps()))
                /*.saleDtoList(cmd.getSaleList() != null ?
                        cmd.getSaleList().stream()
                        .map(SaleDto::fromEntity)
                        .collect(Collectors.toList()) : null)*/
                .build();
    }
    public static Command toEntity(CommandDto cmdDto){
        if(cmdDto == null){
            return null;
        }
        Command cmd = new Command();
        cmd.setId(cmdDto.getId());
        cmd.setCmdCode(cmdDto.getCmdCode());
        cmd.setCmdDate(cmdDto.getCmdDate());
        cmd.setCmdState(cmdDto.getCmdState());
        cmd.setCmdComment(cmdDto.getCmdComment());
        cmd.setCmdType(cmdDto.getCmdType());
        cmd.setCmdStatus(cmdDto.getCmdStatus());
        cmd.setCmdDelivery(DeliveryDto.toEntity(cmdDto.getCmdDeliveryDto()));
        cmd.setCommandPos(PointofsaleDto.toEntity(cmdDto.getCmdPosDto()));
        cmd.setCmdClient(ClientDto.toEntity(cmdDto.getCmdClientDto()));
        cmd.setCmdUserbm(UserBMDto.toEntity(cmdDto.getCmdUserbmDto()));
        cmd.setCmdSaleicash(SaleInvoiceCashDto.toEntity(cmdDto.getCmdSaleicashDto()));
        cmd.setCmdSicaps(SaleInvoiceCapsuleDto.toEntity(cmdDto.getCmdSicapsDto()));
        /*cmd.setSaleList(cmdDto.getSaleDtoList() != null ?
                cmdDto.getSaleDtoList().stream()
                .map(SaleDto::toEntity)
                .collect(Collectors.toList()) : null);*/
        return cmd;
    }
}
