package com.c2psi.businessmanagement.dtos.client.command;

import com.c2psi.businessmanagement.Enumerations.CommandState;
import com.c2psi.businessmanagement.Enumerations.CommandStatus;
import com.c2psi.businessmanagement.Enumerations.CommandType;
import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.dtos.client.client.DiversDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.client.delivery.DeliveryDto;
import com.c2psi.businessmanagement.dtos.pos.loading.LoadingDto;
import com.c2psi.businessmanagement.models.Command;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.Instant;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
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

    LoadingDto cmdLoadingDto;

    @NotNull(message = "The concerning pointofsale cannot be null")
    //PointofsaleDto cmdPosDto;
    Long cmdPosId;

    /*@JsonIgnore
    List<SaleDto> saleDtoList;*/

    ClientDto cmdClientDto;

    DiversDto cmdDiversDto;

    @NotNull(message = "The userbm who register the command cannot be null")
    UserBMDto cmdUserbmDto;

    /******************************
     * Relation between entities  *
     * ****************************/

    SaleInvoiceCashDto cmdSaleicashDto;

    SaleInvoiceCapsuleDto cmdSaleicapsDto;

    SaleInvoiceDamageDto cmdSaleidamDto;

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
                .cmdDate(cmd.getCmdDate())
                .cmdState(cmd.getCmdState())
                .cmdComment(cmd.getCmdComment())
                .cmdType(cmd.getCmdType())
                .cmdStatus(cmd.getCmdStatus())
                .cmdDeliveryDto(DeliveryDto.fromEntity(cmd.getCmdDelivery()))
                .cmdClientDto(ClientDto.fromEntity(cmd.getCmdClient()))
                .cmdDiversDto(DiversDto.fromEntity(cmd.getCmdDivers()))
                .cmdLoadingDto(LoadingDto.fromEntity(cmd.getCmdLoading()))
                //.cmdPosDto(PointofsaleDto.fromEntity(cmd.getCommandPos()))
                .cmdPosId(cmd.getCommandPosId())
                .cmdUserbmDto(UserBMDto.fromEntity(cmd.getCmdUserbm()))
                .cmdSaleicashDto(SaleInvoiceCashDto.fromEntity(cmd.getCmdSaleicash()))
                .cmdSaleicapsDto(SaleInvoiceCapsuleDto.fromEntity(cmd.getCmdSaleicaps()))
                .cmdSaleidamDto(SaleInvoiceDamageDto.fromEntity(cmd.getCmdSaleidam()))

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
        cmd.setCmdLoading(LoadingDto.toEntity(cmdDto.getCmdLoadingDto()));
        //cmd.setCommandPos(PointofsaleDto.toEntity(cmdDto.getCmdPosDto()));
        cmd.setCommandPosId(cmdDto.getCmdPosId());
        cmd.setCmdClient(ClientDto.toEntity(cmdDto.getCmdClientDto()));
        cmd.setCmdDivers(DiversDto.toEntity(cmdDto.getCmdDiversDto()));
        cmd.setCmdUserbm(UserBMDto.toEntity(cmdDto.getCmdUserbmDto()));
        cmd.setCmdSaleicash(SaleInvoiceCashDto.toEntity(cmdDto.getCmdSaleicashDto()));
        cmd.setCmdSaleicaps(SaleInvoiceCapsuleDto.toEntity(cmdDto.getCmdSaleicapsDto()));
        cmd.setCmdSaleidam(SaleInvoiceDamageDto.toEntity(cmdDto.getCmdSaleidamDto()));

        return cmd;
    }
}
