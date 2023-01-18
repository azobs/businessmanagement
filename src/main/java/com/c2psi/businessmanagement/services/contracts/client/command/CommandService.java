package com.c2psi.businessmanagement.services.contracts.client.command;

import com.c2psi.businessmanagement.Enumerations.CommandState;
import com.c2psi.businessmanagement.Enumerations.CommandStatus;
import com.c2psi.businessmanagement.Enumerations.CommandType;
import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.dtos.client.command.CommandDto;
import com.c2psi.businessmanagement.dtos.client.command.SaleInvoiceCapsuleDto;
import com.c2psi.businessmanagement.dtos.client.command.SaleInvoiceCashDto;
import com.c2psi.businessmanagement.dtos.client.delivery.DeliveryDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;

import java.util.Date;
import java.util.List;

public interface CommandService {
    CommandDto saveCommand(CommandDto cmdDto);
    CommandDto findCommandById(Long cmd_id);
    CommandDto findCommandByCode(String cmd_code, PointofsaleDto posDto);
    List<CommandDto> findAllCommand(PointofsaleDto posDto, Date startDate,
                                    Date endDate);
    List<CommandDto> findAllCommand(PointofsaleDto posDto, CommandState cmdState,
                                    CommandType cmdType, Date startDate,
                                    CommandStatus cmdStatus, Date endDate);
    List<CommandDto> findAllCommand(PointofsaleDto posDto, CommandState cmdState,
                                    CommandType cmdType, ClientDto cltDto,
                                    Date startDate, CommandStatus cmdStatus,
                                    Date endDate);
    List<CommandDto> findAllCommand(PointofsaleDto posDto, CommandState cmdState,
                                    CommandType cmdType, UserBMDto userbmDto,
                                    Date startDate, CommandStatus cmdStatus,
                                    Date endDate);
    Boolean deleteCommandById(Long cmd_id);

    DeliveryDto findDeliveryofCommand(CommandDto cmdDto);

    SaleInvoiceCashDto findSaleInvoiceCash(CommandDto cmdDto);
    SaleInvoiceCapsuleDto findSaleInvoiceCapsule(CommandDto cmdDto);
    Boolean isCommandDelivery(CommandDto cmdDto);
    CommandState findCommandState(CommandDto cmdDto);

}
