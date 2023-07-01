package com.c2psi.businessmanagement.services.contracts.client.command;

import com.c2psi.businessmanagement.Enumerations.CommandState;
import com.c2psi.businessmanagement.Enumerations.CommandStatus;
import com.c2psi.businessmanagement.Enumerations.CommandType;
import com.c2psi.businessmanagement.Enumerations.DeliveryState;
import com.c2psi.businessmanagement.dtos.client.command.CommandDto;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.List;

public interface CommandService {
    CommandDto saveCommand(CommandDto cmdDto);
    CommandDto updateCommand(CommandDto cmdDto);
    CommandDto setSaleInvoice(Long cmdId, Long saleInvoiceId, CommandStatus cmdStatus);
    CommandDto assignCommandToDelivery(Long cmdId, Long deliveryId);
    CommandDto resetDeliveryofCommand(Long cmdId);
    CommandDto switchCommandStateTo(Long cmdId, CommandState newCommandState);
    CommandDto findCommandById(Long cmdId);
    CommandDto findCommandByCodeinPos(String cmdCode, Long posId);
    Boolean isCommandUniqueinPos(String cmdCode, Long posId);
    Boolean isCommandDeleteable(Long cmdId);
    Boolean deleteCommandById(Long cmdId);

    List<CommandDto> findAllCommandinPosBetween(Long posId, Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosBetween(Long posId, Instant startDate, Instant endDate,
                                                        int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofcmdStateBetween(Long posId, CommandState cmdState,
                                                       Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosofcmdStateBetween(Long posId, CommandState cmdState,
                                                        Instant startDate, Instant endDate,
                                                        int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofcmdTypeBetween(Long posId, CommandType cmdType,
                                                      Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosofcmdTypeBetween(Long posId, CommandType cmdType,
                                                       Instant startDate, Instant endDate,
                                                       int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofcmdStatusBetween(Long posId, CommandStatus cmdStatus,
                                                       Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosofcmdStatusBetween(Long posId, CommandStatus cmdStatus,
                                                        Instant startDate, Instant endDate,
                                                        int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofcmdDeliveryStateBetween(Long posId, DeliveryState deliveryState,
                                                               Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosofcmdDeliveryStateBetween(Long posId, DeliveryState deliveryState,
                                                                Instant startDate, Instant endDate,
                                                                int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofcmdStateAndcmdTypeBetween(Long posId, CommandState cmdState,
                                                                 CommandType cmdType,
                                                                 Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosofcmdStateAndcmdTypeBetween(Long posId, CommandState cmdState,
                                                                  CommandType cmdType,
                                                                  Instant startDate, Instant endDate,
                                                                  int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofcmdStateAndcmdStatusBetween(Long posId, CommandState cmdState,
                                                                   CommandStatus cmdStatus,
                                                                   Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosofcmdStateAndcmdStatusBetween(Long posId, CommandState cmdState,
                                                                    CommandStatus cmdStatus,
                                                                    Instant startDate, Instant endDate,
                                                                    int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofcmdStateAndcmdDeliveryStateBetween(Long posId, CommandState cmdState,
                                                                          DeliveryState deliveryState,
                                                                          Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosofcmdStateAndcmdDeliveryStateBetween(Long posId, CommandState cmdState,
                                                                           DeliveryState deliveryState,
                                                                           Instant startDate, Instant endDate,
                                                                           int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofcmdTypeAndcmdStatusBetween(Long posId, CommandType cmdType,
                                                                 CommandStatus cmdStatus,
                                                                 Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosofcmdTypeAndcmdStatusBetween(Long posId, CommandType cmdType,
                                                                  CommandStatus cmdStatus,
                                                                  Instant startDate, Instant endDate,
                                                                  int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofcmdTypeAndcmdDeliveryStateBetween(Long posId, CommandType cmdType,
                                                                         DeliveryState deliveryState,
                                                                         Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosofcmdTypeAndcmdDeliveryStateBetween(Long posId, CommandType cmdType,
                                                                          DeliveryState deliveryState,
                                                                          Instant startDate, Instant endDate,
                                                                          int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofcmdStateAndcmdTypeAndcmdStatusBetween(Long posId, CommandState cmdState,
                                                                            CommandType cmdType, CommandStatus cmdStatus,
                                                                            Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosofcmdStateAndcmdTypeAndcmdStatusBetween(Long posId, CommandState cmdState,
                                                                             CommandType cmdType, CommandStatus cmdStatus,
                                                                             Instant startDate, Instant endDate,
                                                                             int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofcmdStateAndcmdTypeAndcmdDeliveryStateBetween(
            Long posId, CommandState cmdState,
            CommandType cmdType, DeliveryState deliveryState,
            Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosofcmdStateAndcmdTypeAndcmdDeliveryStateBetween(
            Long posId, CommandState cmdState,
            CommandType cmdType, DeliveryState deliveryState,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
           Long posId, CommandState cmdState,
           CommandStatus cmdStatus, DeliveryState deliveryState,
           Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosofcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
            Long posId, CommandState cmdState,
            CommandStatus cmdStatus, DeliveryState deliveryState,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofcmdStateAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(
            Long posId, CommandState cmdState,
            CommandType cmdType, CommandStatus cmdStatus,
            DeliveryState deliveryState,
            Instant startDate, Instant endDate);

    Page<CommandDto> findPageCommandinPosofcmdStateAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(
            Long posId, CommandState cmdState,
            CommandType cmdType, CommandStatus cmdStatus,
            DeliveryState deliveryState,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofClientBetween(Long posId, Long clientId,
                                                     Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosofClientBetween(Long posId, Long clientId,
                                                      Instant startDate, Instant endDate,
                                                      int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofClientAndcmdTypeBetween(Long posId, Long clientId,
                                                               CommandType cmdType,
                                                               Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosofClientAndcmdTypeBetween(Long posId, Long clientId,
                                                                CommandType cmdType,
                                                                Instant startDate, Instant endDate,
                                                                int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofClientAndcmdStateBetween(Long posId, Long clientId,
                                                                 CommandState cmdState,
                                                                 Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosofClientAndcmdStateBetween(Long posId, Long clientId,
                                                                 CommandState cmdState,
                                                                 Instant startDate, Instant endDate,
                                                                 int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofClientAndcmdStatusBetween(Long posId, Long clientId,
                                                                 CommandStatus cmdStatus,
                                                                 Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosofClientAndcmdStatusBetween(Long posId, Long clientId,
                                                                  CommandStatus cmdStatus,
                                                                  Instant startDate, Instant endDate,
                                                                  int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofClientAndcmdDeliveryStateBetween(Long posId, Long clientId,
                                                                        DeliveryState deliveryState,
                                                                        Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosofClientAndcmdDeliveryStateBetween(Long posId, Long clientId,
                                                                         DeliveryState deliveryState,
                                                                         Instant startDate, Instant endDate,
                                                                         int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofClientAndcmdTypeAndcmdStateBetween(Long posId, Long clientId,
                                                                          CommandType cmdType, CommandState cmdState,
                                                                          Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosofClientAndcmdTypeAndcmdStateBetween(Long posId, Long clientId,
                                                                           CommandType cmdType, CommandState cmdState,
                                                                           Instant startDate, Instant endDate,
                                                                           int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofClientAndcmdTypeAndcmdStatusBetween(
            Long posId, Long clientId,
            CommandType cmdType, CommandStatus cmdStatus,
            Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosofClientAndcmdTypeAndcmdStatusBetween(
            Long posId, Long clientId,
            CommandType cmdType, CommandStatus cmdStatus,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofClientAndcmdTypeAndcmdDeliveryStateBetween(
            Long posId, Long clientId,
            CommandType cmdType, DeliveryState deliveryState,
            Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosofClientAndcmdTypeAndcmdDeliveryStateBetween(
            Long posId, Long clientId,
            CommandType cmdType, DeliveryState deliveryState,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofClientAndcmdTypeAndcmdStateAndcmdStatusBetween(
            Long posId, Long clientId,
            CommandType cmdType, CommandState cmdState,
            CommandStatus cmdStatus,
            Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosofClientAndcmdTypeAndcmdStateAndcmdStatusBetween(
            Long posId, Long clientId,
            CommandType cmdType, CommandState cmdState,
            CommandStatus cmdStatus,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofClientAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween(
            Long posId, Long clientId,
            CommandType cmdType, CommandState cmdState,
            DeliveryState deliveryState,
            Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosofClientAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween(
            Long posId, Long clientId,
            CommandType cmdType, CommandState cmdState,
            DeliveryState deliveryState,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofClientAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(
            Long posId, Long clientId,
            CommandType cmdType, CommandStatus cmdStatus,
            DeliveryState deliveryState,
            Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosofClientAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(
            Long posId, Long clientId,
            CommandType cmdType, CommandStatus cmdStatus,
            DeliveryState deliveryState,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofClientAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
            Long posId, Long clientId,
            CommandType cmdType, CommandState cmdState,
            CommandStatus cmdStatus, DeliveryState deliveryState,
            Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosofClientAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
            Long posId, Long clientId,
            CommandType cmdType, CommandState cmdState,
            CommandStatus cmdStatus, DeliveryState deliveryState,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofUserbmBetween(Long posId, Long userbmId,
                                                     Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosofUserbmBetween(Long posId, Long userbmId,
                                                      Instant startDate, Instant endDate,
                                                      int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofUserbmAndcmdTypeBetween(Long posId, Long userbmId,
                                                                CommandType cmdType,
                                                                Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosofUserbmAndcmdTypeBetween(Long posId, Long userbmId,
                                                                CommandType cmdType,
                                                                Instant startDate, Instant endDate,
                                                                int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofUserbmAndcmdStateBetween(Long posId, Long userbmId,
                                                                CommandState cmdState,
                                                                Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosofUserbmAndcmdStateBetween(Long posId, Long userbmId,
                                                                 CommandState cmdState,
                                                                 Instant startDate, Instant endDate,
                                                                 int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofUserbmAndcmdStatusBetween(Long posId, Long userbmId,
                                                                  CommandStatus cmdStatus,
                                                                  Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosofUserbmAndcmdStatusBetween(Long posId, Long userbmId,
                                                                  CommandStatus cmdStatus,
                                                                  Instant startDate, Instant endDate,
                                                                  int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofUserbmAndcmdDeliveryStateBetween(Long posId, Long userbmId,
                                                                        DeliveryState deliveryState,
                                                                        Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosofUserbmAndcmdDeliveryStateBetween(Long posId, Long userbmId,
                                                                         DeliveryState deliveryState,
                                                                         Instant startDate, Instant endDate,
                                                                         int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofUserbmAndcmdTypeAndcmdStateBetween(Long posId, Long userbmId,
                                                                          CommandType cmdType, CommandState cmdState,
                                                                          Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosofUserbmAndcmdTypeAndcmdStateBetween(Long posId, Long userbmId,
                                                                           CommandType cmdType, CommandState cmdState,
                                                                           Instant startDate, Instant endDate,
                                                                           int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofUserbmAndcmdTypeAndcmdStatusBetween(Long posId, Long userbmId,
                                                                           CommandType cmdType, CommandStatus cmdStatus,
                                                                           Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosofUserbmAndcmdTypeAndcmdStatusBetween(Long posId, Long userbmId,
                                                                            CommandType cmdType, CommandStatus cmdStatus,
                                                                            Instant startDate, Instant endDate,
                                                                            int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofUserbmAndcmdTypeAndcmdDeliveryStateBetween(
            Long posId, Long userbmId,
            CommandType cmdType, DeliveryState deliveryState,
            Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosofUserbmAndcmdTypeAndcmdDeliveryStateBetween(
            Long posId, Long userbmId,
            CommandType cmdType, DeliveryState deliveryState,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdStatusBetween(
            Long posId, Long userbmId,
            CommandType cmdType, CommandState cmdState,
            CommandStatus cmdStatus,
            Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdStatusBetween(
            Long posId, Long userbmId,
            CommandType cmdType, CommandState cmdState,
            CommandStatus cmdStatus,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween(
            Long posId, Long userbmId,
            CommandType cmdType, CommandState cmdState,
            DeliveryState deliveryState,
            Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween(
            Long posId, Long userbmId,
            CommandType cmdType, CommandState cmdState,
            DeliveryState deliveryState,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofUserbmAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(
            Long posId, Long userbmId,
            CommandType cmdType, CommandStatus cmdStatus,
            DeliveryState deliveryState,
            Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosofUserbmAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(
            Long posId, Long userbmId,
            CommandType cmdType, CommandStatus cmdStatus,
            DeliveryState deliveryState,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
            Long posId, Long userbmId,
            CommandType cmdType, CommandState cmdState,
            CommandStatus cmdStatus, DeliveryState deliveryState,
            Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
            Long posId, Long userbmId,
            CommandType cmdType, CommandState cmdState,
            CommandStatus cmdStatus, DeliveryState deliveryState,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofClientforUserbmBetween(Long posId, Long clientId, Long userbmId,
                                                              Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosofClientforUserbmBetween(Long posId, Long userbmId,
                                                               Long clientId,
                                                               Instant startDate, Instant endDate,
                                                               int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofClientAndUserbmAndcmdTypeBetween(Long posId, Long clientId,
                                                                        Long userbmId, CommandType cmdType,
                                                                        Instant startDate, Instant endDate);

    Page<CommandDto> findPageCommandinPosofClientAndUserbmAndcmdTypeBetween(Long posId, Long clientId,
                                                                         Long userbmId, CommandType cmdType,
                                                                         Instant startDate, Instant endDate,
                                                                         int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofClientAndUserbmAndcmdStateBetween(
            Long posId, Long clientId,
            Long userbmId, CommandState cmdState,
            Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosofClientAndUserbmAndcmdStateBetween(
            Long posId, Long clientId,
            Long userbmId, CommandState cmdState,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofClientAndUserbmAndcmdStatusBetween(
            Long posId, Long clientId,
            Long userbmId, CommandStatus cmdStatus,
            Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosofClientAndUserbmAndcmdStatusBetween(
            Long posId, Long clientId,
            Long userbmId, CommandStatus cmdStatus,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofClientAndUserbmAndcmdDeliveryStateBetween(
            Long posId, Long clientId,
            Long userbmId, DeliveryState deliveryState,
            Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosofClientAndUserbmAndcmdDeliveryStateBetween(
            Long posId, Long clientId,
            Long userbmId, DeliveryState deliveryState,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateBetween(
            Long posId, Long clientId,
            Long userbmId, CommandType cmdType,
            CommandState cmdState,
            Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateBetween(
            Long posId, Long clientId,
            Long userbmId, CommandType cmdType,
            CommandState cmdState,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStatusBetween(
            Long posId, Long clientId,
            Long userbmId, CommandType cmdType,
            CommandStatus cmdStatus,
            Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStatusBetween(
            Long posId, Long clientId,
            Long userbmId, CommandType cmdType,
            CommandStatus cmdStatus,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdDeliveryStateBetween(
            Long posId, Long clientId,
            Long userbmId, CommandType cmdType,
            DeliveryState deliveryState,
            Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdDeliveryStateBetween(
            Long posId, Long clientId,
            Long userbmId, CommandType cmdType,
            DeliveryState deliveryState,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdStatusBetween(
            Long posId, Long clientId,
            Long userbmId, CommandType cmdType,
            CommandState cmdState, CommandStatus cmdStatus,
            Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdStatusBetween(
            Long posId, Long clientId,
            Long userbmId, CommandType cmdType,
            CommandState cmdState, CommandStatus cmdStatus,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween(
            Long posId, Long clientId,
            Long userbmId, CommandType cmdType,
            CommandState cmdState, DeliveryState deliveryState,
            Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween(
            Long posId, Long clientId,
            Long userbmId, CommandType cmdType,
            CommandState cmdState, DeliveryState deliveryState,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(
            Long posId, Long clientId,
            Long userbmId, CommandType cmdType,
            CommandStatus cmdStatus, DeliveryState deliveryState,
            Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(
            Long posId, Long clientId,
            Long userbmId, CommandType cmdType,
            CommandStatus cmdStatus, DeliveryState deliveryState,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize);

    List<CommandDto> findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
            Long posId, Long clientId,
            Long userbmId, CommandType cmdType,
            CommandState cmdState, CommandStatus cmdStatus,
            DeliveryState deliveryState,
            Instant startDate, Instant endDate);
    Page<CommandDto> findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
            Long posId, Long clientId,
            Long userbmId, CommandType cmdType,
            CommandState cmdState, CommandStatus cmdStatus,
            DeliveryState deliveryState,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize);


    /************************************
     * A propos des Loading
     */
    //liste des command d'un Loading dans un Pos puis page par page
    List<CommandDto> findAllCommandofLoadinginPos(Long loadingId, Long posId);
    Page<CommandDto> findPageCommandofLoadinginPos(Long loadingId, Long posId, int pagenum, int pagesize);


}
