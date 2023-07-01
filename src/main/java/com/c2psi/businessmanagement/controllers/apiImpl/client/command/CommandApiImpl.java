package com.c2psi.businessmanagement.controllers.apiImpl.client.command;

import com.c2psi.businessmanagement.Enumerations.CommandState;
import com.c2psi.businessmanagement.Enumerations.CommandStatus;
import com.c2psi.businessmanagement.Enumerations.CommandType;
import com.c2psi.businessmanagement.Enumerations.DeliveryState;
import com.c2psi.businessmanagement.controllers.api.client.command.CommandApi;
import com.c2psi.businessmanagement.dtos.client.command.CommandDto;
import com.c2psi.businessmanagement.services.contracts.client.command.CommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
public class CommandApiImpl implements CommandApi {
    private CommandService commandService;

    @Autowired
    public CommandApiImpl(CommandService commandService) {
        this.commandService = commandService;
    }

    @Override
    public ResponseEntity saveCommand(CommandDto cmdDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", cmdDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }
        CommandDto cmdDtoSaved = commandService.saveCommand(cmdDto);
        log.info("The method saveCommand is being executed");

        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "Command created successfully ");
        map.put("data", cmdDtoSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateCommand(CommandDto cmdDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", cmdDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }
        CommandDto cmdDtoUpdated = commandService.updateCommand(cmdDto);
        log.info("The method updatedCommand is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command updated successfully ");
        map.put("data", cmdDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity setSaleInvoice(Long cmdId, Long saleInvoiceId, CommandStatus cmdStatus) {
        Map<String, Object> map = new LinkedHashMap<>();
        CommandDto cmdDtoUpdated = commandService.setSaleInvoice(cmdId, saleInvoiceId, cmdStatus);
        log.info("The method setSaleInvoice is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "saleInvoice assigned  successfully ");
        map.put("data", cmdDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity assignCommandToDelivery(Long cmdId, Long deliveryId) {
        Map<String, Object> map = new LinkedHashMap<>();
        CommandDto cmdDtoUpdated = commandService.assignCommandToDelivery(cmdId, deliveryId);
        log.info("The method assignCommandToDelivery is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Delivery assigned  successfully ");
        map.put("data", cmdDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity resetDeliveryofCommand(Long cmdId) {
        Map<String, Object> map = new LinkedHashMap<>();
        CommandDto cmdDtoUpdated = commandService.resetDeliveryofCommand(cmdId);
        log.info("The method resetDeliveryofCommand is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Delivery reseting  successfully ");
        map.put("data", cmdDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity switchCommandStateTo(Long cmdId, CommandState commandState) {
        Map<String, Object> map = new LinkedHashMap<>();
        CommandDto cmdDtoUpdated = commandService.switchCommandStateTo(cmdId, commandState);
        log.info("The method switchCommandStateTo is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "CommandState switched  successfully ");
        map.put("data", cmdDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findCommandById(Long cmdId) {
        Map<String, Object> map = new LinkedHashMap<>();
        CommandDto cmdDtoFound = commandService.findCommandById(cmdId);
        log.info("The method findCommandById is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command found  successfully ");
        map.put("data", cmdDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findCommandByCodeinPos(String cmdCode, Long posId) {
        Map<String, Object> map = new LinkedHashMap<>();
        CommandDto cmdDtoFound = commandService.findCommandByCodeinPos(cmdCode, posId);
        log.info("The method findCommandByCodeinPos is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command found  successfully ");
        map.put("data", cmdDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteCommandById(Long cmdId) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean delete = commandService.deleteCommandById(cmdId);
        log.info("The method deleteCommandById is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command deleted  successfully ");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosBetween(Long posId, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.findAllCommandinPosBetween(posId, startDate, endDate);
        log.info("The method findCommandByCodeinPos is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosBetween(Long posId, Instant startDate, Instant endDate,
                                                      Optional<Integer> optpagenum,
                                                      Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.findPageCommandinPosBetween(posId, startDate, endDate,
                pagenum, pagesize);
        log.info("The method findCommandByCodeinPos is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosofcmdStateBetween(Long posId, CommandState commandState, Instant startDate,
                                                               Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.findAllCommandinPosofcmdStateBetween(posId, commandState,
                startDate, endDate);
        log.info("The method findAllCommandinPosofcmdStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosofcmdStateBetween(Long posId, CommandState commandState, Instant startDate,
                                                                Instant endDate, Optional<Integer> optpagenum,
                                                                Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.findPageCommandinPosofcmdStateBetween(
                posId, commandState,
                startDate, endDate,
                pagenum, pagesize);
        log.info("The method findPageCommandinPosofcmdStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosofcmdTypeBetween(Long posId, CommandType commandType, Instant startDate,
                                                              Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.findAllCommandinPosofcmdTypeBetween(posId, commandType,
                startDate, endDate);
        log.info("The method findAllCommandinPosofcmdTypeBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosofcmdTypeBetween(Long posId, CommandType commandType, Instant startDate,
                                                               Instant endDate, Optional<Integer> optpagenum,
                                                               Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.findPageCommandinPosofcmdTypeBetween(
                posId, commandType,
                startDate, endDate,
                pagenum, pagesize);
        log.info("The method findPageCommandinPosofcmdTypeBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosofcmdStatusBetween(Long posId, CommandStatus commandStatus,
                                                                Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.findAllCommandinPosofcmdStatusBetween(posId, commandStatus,
                startDate, endDate);
        log.info("The method findAllCommandinPosofcmdStatusBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosofcmdStatusBetween(Long posId, CommandStatus commandStatus,
                                                                 Instant startDate, Instant endDate,
                                                                 Optional<Integer> optpagenum,
                                                                 Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.findPageCommandinPosofcmdStatusBetween(
                posId, commandStatus,
                startDate, endDate,
                pagenum, pagesize);
        log.info("The method findPageCommandinPosofcmdStatusBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosofcmdDeliveryStateBetween(Long posId, DeliveryState deliveryState,
                                                                       Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.
                findAllCommandinPosofcmdDeliveryStateBetween(posId, deliveryState, startDate, endDate);
        log.info("The method findAllCommandinPosofcmdDeliveryStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosofcmdDeliveryStateBetween(Long posId, DeliveryState deliveryState,
                                                                        Instant startDate, Instant endDate,
                                                                        Optional<Integer> optpagenum,
                                                                        Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.findPageCommandinPosofcmdDeliveryStateBetween(
                posId, deliveryState,
                startDate, endDate,
                pagenum, pagesize);
        log.info("The method findPageCommandinPosofcmdDeliveryStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosofcmdStateAndcmdTypeBetween(Long posId, CommandState commandState,
                                                                         CommandType commandType, Instant startDate,
                                                                         Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.findAllCommandinPosofcmdStateAndcmdTypeBetween(
                posId, commandState, commandType, startDate, endDate);
        log.info("The method findAllCommandinPosofcmdStateAndcmdTypeBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosofcmdStateAndcmdTypeBetween(Long posId, CommandState commandState,
                                                                          CommandType commandType, Instant startDate,
                                                                          Instant endDate, Optional<Integer> optpagenum,
                                                                          Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.findPageCommandinPosofcmdStateAndcmdTypeBetween(
                posId, commandState, commandType,
                startDate, endDate,
                pagenum, pagesize);
        log.info("The method findPageCommandinPosofcmdStateAndcmdTypeBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosofcmdStateAndcmdStatusBetween(Long posId, CommandState commandState,
                                                                           CommandStatus commandStatus,
                                                                           Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.findAllCommandinPosofcmdStateAndcmdStatusBetween(
                posId, commandState, commandStatus, startDate, endDate);
        log.info("The method findAllCommandinPosofcmdStateAndcmdTypeBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosofcmdStateAndcmdStatusBetween(Long posId, CommandState commandState,
                                                                            CommandStatus commandStatus,
                                                                            Instant startDate, Instant endDate,
                                                                            Optional<Integer> optpagenum,
                                                                            Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.findPageCommandinPosofcmdStateAndcmdStatusBetween(
                posId, commandState, commandStatus,
                startDate, endDate,
                pagenum, pagesize);
        log.info("The method findPageCommandinPosofcmdStateAndcmdStatusBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosofcmdStateAndcmdDeliveryStateBetween(Long posId, CommandState commandState,
                                                                                  DeliveryState deliveryState,
                                                                                  Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.findAllCommandinPosofcmdStateAndcmdDeliveryStateBetween(
                posId, commandState, deliveryState, startDate, endDate);
        log.info("The method findAllCommandinPosofcmdStateAndcmdDeliveryStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosofcmdStateAndcmdDeliveryStateBetween(Long posId, CommandState commandState,
                                                                                   DeliveryState deliveryState,
                                                                                   Instant startDate, Instant endDate,
                                                                                   Optional<Integer> optpagenum,
                                                                                   Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.findPageCommandinPosofcmdStateAndcmdDeliveryStateBetween(
                posId, commandState, deliveryState,
                startDate, endDate,
                pagenum, pagesize);
        log.info("The method findPageCommandinPosofcmdStateAndcmdDeliveryStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosofcmdTypeAndcmdStatusBetween(Long posId, CommandType commandType,
                                                                          CommandStatus commandStatus, Instant startDate,
                                                                          Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.findAllCommandinPosofcmdTypeAndcmdStatusBetween(
                posId, commandType, commandStatus, startDate, endDate);
        log.info("The method findAllCommandinPosofcmdTypeAndcmdStatusBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosofcmdTypeAndcmdStatusBetween(Long posId, CommandType commandType,
                                                                           CommandStatus commandStatus,
                                                                           Instant startDate, Instant endDate,
                                                                           Optional<Integer> optpagenum,
                                                                           Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.findPageCommandinPosofcmdTypeAndcmdStatusBetween(
                posId, commandType, commandStatus,
                startDate, endDate,
                pagenum, pagesize);
        log.info("The method findPageCommandinPosofcmdTypeAndcmdStatusBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosofcmdTypeAndcmdDeliveryStateBetween(Long posId, CommandType commandType,
                                                                                 DeliveryState deliveryState,
                                                                                 Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.findAllCommandinPosofcmdTypeAndcmdDeliveryStateBetween(
                posId, commandType, deliveryState, startDate, endDate);
        log.info("The method findAllCommandinPosofcmdTypeAndcmdDeliveryStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosofcmdTypeAndcmdDeliveryStateBetween(Long posId, CommandType commandType,
                                                                                  DeliveryState deliveryState,
                                                                                  Instant startDate, Instant endDate,
                                                                                  Optional<Integer> optpagenum,
                                                                                  Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.findPageCommandinPosofcmdTypeAndcmdDeliveryStateBetween(
                posId, commandType, deliveryState,
                startDate, endDate,
                pagenum, pagesize);
        log.info("The method findPageCommandinPosofcmdTypeAndcmdDeliveryStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosofcmdStateAndcmdTypeAndcmdStatusBetween(
            Long posId, CommandState commandState, CommandType commandType, CommandStatus commandStatus,
            Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.findAllCommandinPosofcmdStateAndcmdTypeAndcmdStatusBetween(
                posId, commandState, commandType, commandStatus, startDate, endDate);
        log.info("The method findAllCommandinPosofcmdStateAndcmdTypeAndcmdStatusBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosofcmdStateAndcmdTypeAndcmdStatusBetween(
            Long posId, CommandState commandState, CommandType commandType, CommandStatus commandStatus,
            Instant startDate, Instant endDate, Optional<Integer> optpagenum, Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.findPageCommandinPosofcmdStateAndcmdTypeAndcmdStatusBetween(
                posId, commandState, commandType, commandStatus,
                startDate, endDate,
                pagenum, pagesize);
        log.info("The method findPageCommandinPosofcmdStateAndcmdTypeAndcmdStatusBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosofcmdStateAndcmdTypeAndcmdDeliveryStateBetween(
            Long posId, CommandState commandState, CommandType commandType, DeliveryState deliveryState,
            Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.
                findAllCommandinPosofcmdStateAndcmdTypeAndcmdDeliveryStateBetween(
                posId, commandState, commandType, deliveryState, startDate, endDate);
        log.info("The method findAllCommandinPosofcmdStateAndcmdTypeAndcmdDeliveryStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosofcmdStateAndcmdTypeAndcmdDeliveryStateBetween(
            Long posId, CommandState commandState, CommandType commandType, DeliveryState deliveryState,
            Instant startDate, Instant endDate, Optional<Integer> optpagenum, Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.
                findPageCommandinPosofcmdStateAndcmdTypeAndcmdDeliveryStateBetween(
                posId, commandState, commandType, deliveryState,
                startDate, endDate,
                pagenum, pagesize);
        log.info("The method findPageCommandinPosofcmdStateAndcmdTypeAndcmdDeliveryStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosofcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
            Long posId, CommandState commandState, CommandStatus commandStatus, DeliveryState deliveryState,
            Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.
                findAllCommandinPosofcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
                posId, commandState, commandStatus, deliveryState, startDate, endDate);
        log.info("The method findAllCommandinPosofcmdStateAndcmdStatusAndcmdDeliveryStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosofcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
            Long posId, CommandState commandState, CommandStatus commandStatus, DeliveryState deliveryState,
            Instant startDate, Instant endDate, Optional<Integer> optpagenum, Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.
                findPageCommandinPosofcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
                        posId, commandState, commandStatus, deliveryState,
                        startDate, endDate,
                        pagenum, pagesize);
        log.info("The method findPageCommandinPosofcmdStateAndcmdStatusAndcmdDeliveryStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosofcmdStateAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(
            Long posId, CommandState commandState, CommandType commandType, CommandStatus commandStatus,
            DeliveryState deliveryState, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.
                findAllCommandinPosofcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
                posId, commandState, commandStatus, deliveryState, startDate, endDate);
        log.info("The method findAllCommandinPosofcmdStateAndcmdStatusAndcmdDeliveryStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosofcmdStateAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(
            Long posId, CommandState commandState, CommandType commandType, CommandStatus commandStatus,
            DeliveryState deliveryState, Instant startDate, Instant endDate, Optional<Integer> optpagenum,
            Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.
                findPageCommandinPosofcmdStateAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(
                        posId, commandState, commandType, commandStatus, deliveryState,
                        startDate, endDate,
                        pagenum, pagesize);
        log.info("The method findPageCommandinPosofcmdStateAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosofClientBetween(Long posId, Long clientId, Instant startDate,
                                                             Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.findAllCommandinPosofClientBetween(
                posId, clientId, startDate, endDate);
        log.info("The method findAllCommandinPosofClientBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosofClientBetween(
            Long posId, Long clientId, Instant startDate, Instant endDate, Optional<Integer> optpagenum,
            Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.
                findPageCommandinPosofClientBetween(
                        posId, clientId,
                        startDate, endDate,
                        pagenum, pagesize);
        log.info("The method findPageCommandinPosofClientBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosofClientAndcmdTypeBetween(
            Long posId, Long clientId, CommandType commandType, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.findAllCommandinPosofClientAndcmdTypeBetween(
                posId, clientId, commandType, startDate, endDate);
        log.info("The method findAllCommandinPosofClientAndcmdTypeBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosofClientAndcmdTypeBetween(
            Long posId, Long clientId, CommandType commandType, Instant startDate, Instant endDate,
            Optional<Integer> optpagenum, Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.
                findPageCommandinPosofClientAndcmdTypeBetween(
                        posId, clientId, commandType,
                        startDate, endDate,
                        pagenum, pagesize);
        log.info("The method findPageCommandinPosofClientAndcmdTypeBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosofClientAndcmdStateBetween(
            Long posId, Long clientId, CommandState commandState, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.findAllCommandinPosofClientAndcmdStateBetween(
                posId, clientId, commandState, startDate, endDate);
        log.info("The method findAllCommandinPosofClientAndcmdStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosofClientAndcmdStateBetween(
            Long posId, Long clientId, CommandState commandState, Instant startDate, Instant endDate,
            Optional<Integer> optpagenum, Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.
                findPageCommandinPosofClientAndcmdStateBetween(
                        posId, clientId, commandState,
                        startDate, endDate,
                        pagenum, pagesize);
        log.info("The method findPageCommandinPosofClientAndcmdStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosofClientAndcmdStatusBetween(
            Long posId, Long clientId, CommandStatus commandStatus, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.findAllCommandinPosofClientAndcmdStatusBetween(
                posId, clientId, commandStatus, startDate, endDate);
        log.info("The method findAllCommandinPosofClientAndcmdStatusBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosofClientAndcmdStatusBetween(
            Long posId, Long clientId, CommandStatus commandStatus, Instant startDate, Instant endDate,
            Optional<Integer> optpagenum, Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.
                findPageCommandinPosofClientAndcmdStatusBetween(
                        posId, clientId, commandStatus,
                        startDate, endDate,
                        pagenum, pagesize);
        log.info("The method findPageCommandinPosofClientAndcmdStatusBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosofClientAndcmdDeliveryStateBetween(
            Long posId, Long clientId, DeliveryState deliveryState, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.findAllCommandinPosofClientAndcmdDeliveryStateBetween(
                posId, clientId, deliveryState, startDate, endDate);
        log.info("The method findAllCommandinPosofClientAndcmdDeliveryStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosofClientAndcmdDeliveryStateBetween(
            Long posId, Long clientId, DeliveryState deliveryState, Instant startDate, Instant endDate,
            Optional<Integer> optpagenum, Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.
                findPageCommandinPosofClientAndcmdDeliveryStateBetween(
                        posId, clientId, deliveryState,
                        startDate, endDate,
                        pagenum, pagesize);
        log.info("The method findPageCommandinPosofClientAndcmdDeliveryStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosofClientAndcmdTypeAndcmdStatusBetween(
            Long posId, Long clientId, CommandType commandType, CommandStatus commandStatus, Instant startDate,
            Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.findAllCommandinPosofClientAndcmdTypeAndcmdStatusBetween(
                posId, clientId, commandType, commandStatus, startDate, endDate);
        log.info("The method findAllCommandinPosofClientAndcmdTypeAndcmdStatusBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosofClientAndcmdTypeAndcmdStatusBetween(
            Long posId, Long clientId, CommandType commandType, CommandStatus commandStatus, Instant startDate,
            Instant endDate, Optional<Integer> optpagenum, Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.
                findPageCommandinPosofClientAndcmdTypeAndcmdStatusBetween(
                        posId, clientId, commandType, commandStatus,
                        startDate, endDate,
                        pagenum, pagesize);
        log.info("The method findPageCommandinPosofClientAndcmdTypeAndcmdStatusBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosofClientAndcmdTypeAndcmdDeliveryStateBetween(
            Long posId, Long clientId, CommandType commandType, DeliveryState deliveryState, Instant startDate,
            Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.findAllCommandinPosofClientAndcmdTypeAndcmdDeliveryStateBetween(
                posId, clientId, commandType, deliveryState, startDate, endDate);
        log.info("The method findAllCommandinPosofClientAndcmdTypeAndcmdDeliveryStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosofClientAndcmdTypeAndcmdDeliveryStateBetween(
            Long posId, Long clientId, CommandType commandType, DeliveryState deliveryState, Instant startDate,
            Instant endDate, Optional<Integer> optpagenum, Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.
                findPageCommandinPosofClientAndcmdTypeAndcmdDeliveryStateBetween(
                        posId, clientId, commandType, deliveryState,
                        startDate, endDate,
                        pagenum, pagesize);
        log.info("The method findPageCommandinPosofClientAndcmdTypeAndcmdDeliveryStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosofClientAndcmdTypeAndcmdStateAndcmdStatusBetween(
            Long posId, Long clientId, CommandType commandType, CommandState commandState, CommandStatus commandStatus,
            Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.
                findAllCommandinPosofClientAndcmdTypeAndcmdStateAndcmdStatusBetween(
                posId, clientId, commandType, commandState, commandStatus, startDate, endDate);
        log.info("The method findAllCommandinPosofClientAndcmdTypeAndcmdStateAndcmdStatusBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosofClientAndcmdTypeAndcmdStateAndcmdStatusBetween(
            Long posId, Long clientId, CommandType commandType, CommandState commandState, CommandStatus commandStatus,
            Instant startDate, Instant endDate, Optional<Integer> optpagenum, Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.
                findPageCommandinPosofClientAndcmdTypeAndcmdStateAndcmdStatusBetween(
                        posId, clientId, commandType, commandState, commandStatus,
                        startDate, endDate,
                        pagenum, pagesize);
        log.info("The method findPageCommandinPosofClientAndcmdTypeAndcmdStateAndcmdStatusBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosofClientAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween(
            Long posId, Long clientId, CommandType commandType, CommandState commandState, DeliveryState deliveryState,
            Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.
                findAllCommandinPosofClientAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween(
                posId, clientId, commandType, commandState, deliveryState, startDate, endDate);
        log.info("The method findAllCommandinPosofClientAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosofClientAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween(
            Long posId, Long clientId, CommandType commandType, CommandState commandState, DeliveryState deliveryState,
            Instant startDate, Instant endDate, Optional<Integer> optpagenum, Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.
                findPageCommandinPosofClientAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween(
                        posId, clientId, commandType, commandState, deliveryState,
                        startDate, endDate,
                        pagenum, pagesize);
        log.info("The method findPageCommandinPosofClientAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosofClientAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
            Long posId, Long clientId, CommandType commandType, CommandState commandState, CommandStatus commandStatus,
            DeliveryState deliveryState, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.
                findAllCommandinPosofClientAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
                posId, clientId, commandType, commandState, commandStatus, deliveryState, startDate, endDate);
        log.info("The method findAllCommandinPosofClientAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosofClientAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
            Long posId, Long clientId, CommandType commandType, CommandState commandState, CommandStatus commandStatus,
            DeliveryState deliveryState, Instant startDate, Instant endDate, Optional<Integer> optpagenum,
            Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.
                findPageCommandinPosofClientAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
                        posId, clientId, commandType, commandState, commandStatus, deliveryState,
                        startDate, endDate,
                        pagenum, pagesize);
        log.info("The method findAllCommandinPosofClientAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosofUserbmBetween(
            Long posId, Long userbmId, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.findAllCommandinPosofUserbmBetween(
                        posId, userbmId, startDate, endDate);
        log.info("The method findAllCommandinPosofUserbmBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosofUserbmBetween(
            Long posId, Long userbmId, Instant startDate, Instant endDate, Optional<Integer> optpagenum,
            Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.
                findPageCommandinPosofUserbmBetween(
                        posId, userbmId,
                        startDate, endDate,
                        pagenum, pagesize);
        log.info("The method findPageCommandinPosofUserbmBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosofUserbmAndcmdTypeBetween(
            Long posId, Long userbmId, CommandType commandType, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.findAllCommandinPosofUserbmAndcmdTypeBetween(
                posId, userbmId, commandType, startDate, endDate);
        log.info("The method findAllCommandinPosofUserbmAndcmdTypeBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosofUserbmAndcmdTypeBetween(
            Long posId, Long userbmId, CommandType commandType, Instant startDate, Instant endDate,
            Optional<Integer> optpagenum, Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.
                findPageCommandinPosofUserbmAndcmdTypeBetween(
                        posId, userbmId, commandType,
                        startDate, endDate,
                        pagenum, pagesize);
        log.info("The method findPageCommandinPosofUserbmAndcmdTypeBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosofUserbmAndcmdStateBetween(
            Long posId, Long userbmId, CommandState commandState, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.findAllCommandinPosofUserbmAndcmdStateBetween(
                posId, userbmId, commandState, startDate, endDate);
        log.info("The method findAllCommandinPosofUserbmAndcmdStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosofUserbmAndcmdStateBetween(
            Long posId, Long userbmId, CommandState commandState, Instant startDate, Instant endDate,
            Optional<Integer> optpagenum, Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.
                findPageCommandinPosofUserbmAndcmdStateBetween(
                        posId, userbmId, commandState,
                        startDate, endDate,
                        pagenum, pagesize);
        log.info("The method findPageCommandinPosofUserbmAndcmdStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosofUserbmAndcmdStatusBetween(
            Long posId, Long userbmId, CommandStatus commandStatus, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.findAllCommandinPosofUserbmAndcmdStatusBetween(
                posId, userbmId, commandStatus, startDate, endDate);
        log.info("The method findAllCommandinPosofUserbmAndcmdStatusBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosofUserbmAndcmdStatusBetween(
            Long posId, Long userbmId, CommandStatus commandStatus, Instant startDate, Instant endDate,
            Optional<Integer> optpagenum, Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.
                findPageCommandinPosofUserbmAndcmdStatusBetween(
                        posId, userbmId, commandStatus,
                        startDate, endDate,
                        pagenum, pagesize);
        log.info("The method findPageCommandinPosofUserbmAndcmdStatusBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosofUserbmAndcmdDeliveryStateBetween(
            Long posId, Long userbmId, DeliveryState deliveryState, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.findAllCommandinPosofUserbmAndcmdDeliveryStateBetween(
                posId, userbmId, deliveryState, startDate, endDate);
        log.info("The method findAllCommandinPosofUserbmAndcmdDeliveryStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosofUserbmAndcmdDeliveryStateBetween(
            Long posId, Long userbmId, DeliveryState deliveryState, Instant startDate, Instant endDate,
            Optional<Integer> optpagenum, Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.
                findPageCommandinPosofUserbmAndcmdDeliveryStateBetween(
                        posId, userbmId, deliveryState,
                        startDate, endDate,
                        pagenum, pagesize);
        log.info("The method findPageCommandinPosofUserbmAndcmdDeliveryStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosofUserbmAndcmdTypeAndcmdStateBetween(
            Long posId, Long userbmId, CommandType commandType, CommandState commandState, Instant startDate,
            Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.findAllCommandinPosofUserbmAndcmdTypeAndcmdStateBetween(
                posId, userbmId, commandType, commandState, startDate, endDate);
        log.info("The method findAllCommandinPosofUserbmAndcmdTypeAndcmdStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosofUserbmAndcmdTypeAndcmdStateBetween(
            Long posId, Long userbmId, CommandType commandType, CommandState commandState, Instant startDate,
            Instant endDate, Optional<Integer> optpagenum, Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.
                findPageCommandinPosofUserbmAndcmdTypeAndcmdStateBetween(
                        posId, userbmId, commandType, commandState,
                        startDate, endDate,
                        pagenum, pagesize);
        log.info("The method findPageCommandinPosofUserbmAndcmdTypeAndcmdStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosofUserbmAndcmdTypeAndcmdStatusBetween(
            Long posId, Long userbmId, CommandType commandType, CommandStatus commandStatus, Instant startDate,
            Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.findAllCommandinPosofUserbmAndcmdTypeAndcmdStatusBetween(
                posId, userbmId, commandType, commandStatus, startDate, endDate);
        log.info("The method findAllCommandinPosofUserbmAndcmdTypeAndcmdStatusBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosofUserbmAndcmdTypeAndcmdStatusBetween(
            Long posId, Long userbmId, CommandType commandType, CommandStatus commandStatus, Instant startDate,
            Instant endDate, Optional<Integer> optpagenum, Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.
                findPageCommandinPosofUserbmAndcmdTypeAndcmdStatusBetween(
                        posId, userbmId, commandType, commandStatus,
                        startDate, endDate,
                        pagenum, pagesize);
        log.info("The method findPageCommandinPosofUserbmAndcmdTypeAndcmdStatusBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosofUserbmAndcmdTypeAndcmdDeliveryStateBetween(
            Long posId, Long userbmId, CommandType commandType, DeliveryState deliveryState, Instant startDate,
            Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.
                findAllCommandinPosofUserbmAndcmdTypeAndcmdDeliveryStateBetween(
                posId, userbmId, commandType, deliveryState, startDate, endDate);
        log.info("The method findAllCommandinPosofUserbmAndcmdTypeAndcmdDeliveryStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosofUserbmAndcmdTypeAndcmdDeliveryStateBetween(
            Long posId, Long userbmId, CommandType commandType, DeliveryState deliveryState, Instant startDate,
            Instant endDate, Optional<Integer> optpagenum, Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.
                findPageCommandinPosofUserbmAndcmdTypeAndcmdDeliveryStateBetween(
                        posId, userbmId, commandType, deliveryState,
                        startDate, endDate,
                        pagenum, pagesize);
        log.info("The method findPageCommandinPosofUserbmAndcmdTypeAndcmdDeliveryStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdStatusBetween(
            Long posId, Long userbmId, CommandType commandType, CommandState commandState, CommandStatus commandStatus,
            Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.
                findAllCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdStatusBetween(
                posId, userbmId, commandType, commandState, commandStatus, startDate, endDate);
        log.info("The method findAllCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdStatusBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdStatusBetween(
            Long posId, Long userbmId, CommandType commandType, CommandState commandState, CommandStatus commandStatus,
            Instant startDate, Instant endDate, Optional<Integer> optpagenum, Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.
                findPageCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdStatusBetween(
                        posId, userbmId, commandType, commandState, commandStatus,
                        startDate, endDate,
                        pagenum, pagesize);
        log.info("The method findPageCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdStatusBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween(
            Long posId, Long userbmId, CommandType commandType, CommandState commandState, DeliveryState deliveryState,
            Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.
                findAllCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween(
                posId, userbmId, commandType, commandState, deliveryState, startDate, endDate);
        log.info("The method findAllCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween(
            Long posId, Long userbmId, CommandType commandType, CommandState commandState, DeliveryState deliveryState,
            Instant startDate, Instant endDate, Optional<Integer> optpagenum, Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.
                findPageCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween(
                        posId, userbmId, commandType, commandState, deliveryState,
                        startDate, endDate,
                        pagenum, pagesize);
        log.info("The method findPageCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosofUserbmAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(
            Long posId, Long userbmId, CommandType commandType, CommandStatus commandStatus,
            DeliveryState deliveryState, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.
                findAllCommandinPosofUserbmAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(
                posId, userbmId, commandType, commandStatus, deliveryState, startDate, endDate);
        log.info("The method findAllCommandinPosofUserbmAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosofUserbmAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(
            Long posId, Long userbmId, CommandType commandType, CommandStatus commandStatus, DeliveryState deliveryState,
            Instant startDate, Instant endDate, Optional<Integer> optpagenum, Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.
                findPageCommandinPosofUserbmAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(
                        posId, userbmId, commandType, commandStatus, deliveryState,
                        startDate, endDate,
                        pagenum, pagesize);
        log.info("The method findPageCommandinPosofUserbmAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
            Long posId, Long userbmId, CommandType commandType, CommandState commandState, CommandStatus commandStatus,
            DeliveryState deliveryState, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.
                findAllCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
                posId, userbmId, commandType, commandState, commandStatus, deliveryState, startDate, endDate);
        log.info("The method findAllCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
            Long posId, Long userbmId, CommandType commandType, CommandState commandState, CommandStatus commandStatus,
            DeliveryState deliveryState, Instant startDate, Instant endDate, Optional<Integer> optpagenum,
            Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.
                findPageCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
                        posId, userbmId, commandType, commandState, commandStatus, deliveryState,
                        startDate, endDate,
                        pagenum, pagesize);
        log.info("The method findPageCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosofClientforUserbmBetween(
            Long posId, Long clientId, Long userbmId, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.
                findAllCommandinPosofClientforUserbmBetween(
                        posId, clientId, userbmId, startDate, endDate);
        log.info("The method findAllCommandinPosofClientforUserbmBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosofClientforUserbmBetween(
            Long posId, Long clientId, Long userbmId, Instant startDate, Instant endDate, Optional<Integer> optpagenum,
            Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.
                findPageCommandinPosofClientforUserbmBetween(
                        posId, userbmId, clientId,
                        startDate, endDate,
                        pagenum, pagesize);
        log.info("The method findPageCommandinPosofClientforUserbmBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosofClientAndUserbmAndcmdTypeBetween(
            Long posId, Long clientId, Long userbmId, CommandType commandType, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.
                findAllCommandinPosofClientAndUserbmAndcmdTypeBetween(
                        posId, clientId, userbmId, commandType, startDate, endDate);
        log.info("The method findAllCommandinPosofClientAndUserbmAndcmdTypeBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosofClientAndUserbmAndcmdTypeBetween(
            Long posId, Long clientId, Long userbmId, CommandType commandType, Instant startDate, Instant endDate,
            Optional<Integer> optpagenum, Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.
                findPageCommandinPosofClientAndUserbmAndcmdTypeBetween(
                        posId, userbmId, clientId, commandType,
                        startDate, endDate,
                        pagenum, pagesize);
        log.info("The method findPageCommandinPosofClientAndUserbmAndcmdTypeBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosofClientAndUserbmAndcmdStateBetween(
            Long posId, Long clientId, Long userbmId, CommandState commandState, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.
                findAllCommandinPosofClientAndUserbmAndcmdStateBetween(
                        posId, clientId, userbmId, commandState, startDate, endDate);
        log.info("The method findAllCommandinPosofClientAndUserbmAndcmdStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosofClientAndUserbmAndcmdStateBetween(
            Long posId, Long clientId, Long userbmId, CommandState commandState, Instant startDate, Instant endDate,
            Optional<Integer> optpagenum, Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.
                findPageCommandinPosofClientAndUserbmAndcmdStateBetween(
                        posId, userbmId, clientId, commandState,
                        startDate, endDate,
                        pagenum, pagesize);
        log.info("The method findPageCommandinPosofClientAndUserbmAndcmdStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosofClientAndUserbmAndcmdStatusBetween(
            Long posId, Long clientId, Long userbmId, CommandStatus commandStatus, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.
                findAllCommandinPosofClientAndUserbmAndcmdStatusBetween(
                        posId, clientId, userbmId, commandStatus, startDate, endDate);
        log.info("The method findAllCommandinPosofClientAndUserbmAndcmdStatusBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosofClientAndUserbmAndcmdStatusBetween(
            Long posId, Long clientId, Long userbmId, CommandStatus commandStatus, Instant startDate, Instant endDate,
            Optional<Integer> optpagenum, Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.
                findPageCommandinPosofClientAndUserbmAndcmdStatusBetween(
                        posId, userbmId, clientId, commandStatus,
                        startDate, endDate,
                        pagenum, pagesize);
        log.info("The method findPageCommandinPosofClientAndUserbmAndcmdStatusBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosofClientAndUserbmAndcmdDeliveryStateBetween(
            Long posId, Long clientId, Long userbmId, DeliveryState deliveryState, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.
                findAllCommandinPosofClientAndUserbmAndcmdDeliveryStateBetween(
                        posId, clientId, userbmId, deliveryState, startDate, endDate);
        log.info("The method findAllCommandinPosofClientAndUserbmAndcmdDeliveryStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosofClientAndUserbmAndcmdDeliveryStateBetween(
            Long posId, Long clientId, Long userbmId, DeliveryState deliveryState, Instant startDate,
            Instant endDate, Optional<Integer> optpagenum, Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.
                findPageCommandinPosofClientAndUserbmAndcmdDeliveryStateBetween(
                        posId, userbmId, clientId, deliveryState,
                        startDate, endDate,
                        pagenum, pagesize);
        log.info("The method findPageCommandinPosofClientAndUserbmAndcmdDeliveryStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateBetween(
            Long posId, Long clientId, Long userbmId, CommandType commandType, CommandState commandState,
            Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.
                findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateBetween(
                        posId, clientId, userbmId, commandType, commandState, startDate, endDate);
        log.info("The method findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateBetween(
            Long posId, Long clientId, Long userbmId, CommandType commandType, CommandState commandState,
            Instant startDate, Instant endDate, Optional<Integer> optpagenum, Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.
                findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateBetween(
                        posId, userbmId, clientId, commandType, commandState,
                        startDate, endDate,
                        pagenum, pagesize);
        log.info("The method findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStatusBetween(
            Long posId, Long clientId, Long userbmId, CommandType commandType, CommandStatus commandStatus,
            Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.
                findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStatusBetween(
                        posId, clientId, userbmId, commandType, commandStatus, startDate, endDate);
        log.info("The method findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStatusBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStatusBetween(
            Long posId, Long clientId, Long userbmId, CommandType commandType, CommandStatus commandStatus,
            Instant startDate, Instant endDate, Optional<Integer> optpagenum, Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.
                findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStatusBetween(
                        posId, userbmId, clientId, commandType, commandStatus,
                        startDate, endDate,
                        pagenum, pagesize);
        log.info("The method findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStatusBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdDeliveryStateBetween(
            Long posId, Long clientId, Long userbmId, CommandType commandType, DeliveryState deliveryState,
            Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.
                findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdDeliveryStateBetween(
                        posId, clientId, userbmId, commandType, deliveryState, startDate, endDate);
        log.info("The method findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdDeliveryStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdDeliveryStateBetween(
            Long posId, Long clientId, Long userbmId, CommandType commandType, DeliveryState deliveryState,
            Instant startDate, Instant endDate, Optional<Integer> optpagenum, Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.
                findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdDeliveryStateBetween(
                        posId, userbmId, clientId, commandType, deliveryState,
                        startDate, endDate,
                        pagenum, pagesize);
        log.info("The method findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdDeliveryStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdStatusBetween(
            Long posId, Long clientId, Long userbmId, CommandType commandType, CommandState commandState,
            CommandStatus commandStatus, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.
                findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdStatusBetween(
                        posId, clientId, userbmId, commandType, commandState, commandStatus, startDate, endDate);
        log.info("The method findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdStatusBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdStatusBetween
            (Long posId, Long clientId, Long userbmId, CommandType commandType, CommandState commandState,
             CommandStatus commandStatus, Instant startDate, Instant endDate, Optional<Integer> optpagenum,
             Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.
                findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdStatusBetween(
                        posId, userbmId, clientId, commandType, commandState, commandStatus,
                        startDate, endDate,
                        pagenum, pagesize);
        log.info("The method findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdStatusBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween(
            Long posId, Long clientId, Long userbmId, CommandType commandType, CommandState commandState,
            DeliveryState deliveryState, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.
                findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween(
                        posId, clientId, userbmId, commandType, commandState, deliveryState, startDate, endDate);
        log.info("The method findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween(
            Long posId, Long clientId, Long userbmId, CommandType commandType, CommandState commandState,
            DeliveryState deliveryState, Instant startDate, Instant endDate, Optional<Integer> optpagenum,
            Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.
                findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween(
                        posId, userbmId, clientId, commandType, commandState, deliveryState,
                        startDate, endDate,
                        pagenum, pagesize);
        log.info("The method findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(
            Long posId, Long clientId, Long userbmId, CommandType commandType, CommandStatus commandStatus,
            DeliveryState deliveryState, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.
                findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(
                        posId, clientId, userbmId, commandType, commandStatus, deliveryState, startDate, endDate);
        log.info("The method findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(
            Long posId, Long clientId, Long userbmId, CommandType commandType, CommandStatus commandStatus,
            DeliveryState deliveryState, Instant startDate, Instant endDate, Optional<Integer> optpagenum,
            Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.
                findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(
                        posId, userbmId, clientId, commandType, commandStatus, deliveryState,
                        startDate, endDate,
                        pagenum, pagesize);
        log.info("The method findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
            Long posId, Long clientId, Long userbmId, CommandType commandType, CommandState commandState,
            CommandStatus commandStatus, DeliveryState deliveryState, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.
                findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
                        posId, clientId, userbmId, commandType, commandState, commandStatus, deliveryState, startDate, endDate);
        log.info("The method findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
            Long posId, Long clientId, Long userbmId, CommandType commandType, CommandState commandState,
            CommandStatus commandStatus, DeliveryState deliveryState, Instant startDate, Instant endDate,
            Optional<Integer> optpagenum, Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.
                findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
                        posId, userbmId, clientId, commandType, commandState, commandStatus, deliveryState,
                        startDate, endDate,
                        pagenum, pagesize);
        log.info("The method findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllCommandofLoadinginPos(Long loadingId, Long posId) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<CommandDto> commandDtoList = commandService.
                findAllCommandofLoadinginPos(
                        loadingId, posId);
        log.info("The method findAllCommandofLoadinginPos is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command list found  successfully ");
        map.put("data", commandDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageCommandofLoadinginPos(Long loadingId, Long posId, Optional<Integer> optpagenum,
                                                        Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<CommandDto> commandDtoPage = commandService.
                findPageCommandofLoadinginPos(
                        loadingId, posId,
                        pagenum, pagesize);
        log.info("The method findPageCommandofLoadinginPos is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Command page found  successfully ");
        map.put("data", commandDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }
}
