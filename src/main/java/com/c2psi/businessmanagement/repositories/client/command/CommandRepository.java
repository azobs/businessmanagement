package com.c2psi.businessmanagement.repositories.client.command;

import com.c2psi.businessmanagement.Enumerations.CommandState;
import com.c2psi.businessmanagement.Enumerations.CommandStatus;
import com.c2psi.businessmanagement.Enumerations.CommandType;
import com.c2psi.businessmanagement.Enumerations.DeliveryState;
import com.c2psi.businessmanagement.models.Command;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface CommandRepository extends JpaRepository<Command, Long> {
    //Rechercher une command a partir de son id
    Optional<Command> findCommandById(Long cmdId);
    //Rechercher une command a partir de son code dans un pos
    @Query("SELECT cmd FROM Command cmd WHERE cmd.cmdCode=:cmdCode AND cmd.commandPosId=:posId")
    Optional<Command> findCommandByCodeinPos(@Param("cmdCode") String cmdCode, @Param("posId") Long posId);
    //Rechercher la liste des command d'un pos enregistrer entre 2 dates puis page par page
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosBetween(@Param("posId") Long posId,
                                                       @Param("startDate") Instant startDate,
                                                       @Param("endDate") Instant endDate);

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosBetween(@Param("posId") Long posId,
                                                        @Param("startDate") Instant startDate,
                                                        @Param("endDate") Instant endDate, Pageable pageable);
    //Rechercher la liste des command d'un pos dans un certain etat entre 2 dates puis page par page(A)
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdState=:cmdState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofcmdStateBetween(@Param("posId") Long posId,
                                                                 @Param("cmdState") CommandState cmdState,
                                                                 @Param("startDate") Instant startDate,
                                                                 @Param("endDate") Instant endDate);

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdState=:cmdState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofcmdStateBetween(@Param("posId") Long posId,
                                                                 @Param("cmdState") CommandState cmdState,
                                                                 @Param("startDate") Instant startDate,
                                                                 @Param("endDate") Instant endDate, Pageable pageable);
    //Rechercher la liste des command d'un pos d'un certain type entre 2 dates puis page par page(B)
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdType=:cmdType AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofcmdTypeBetween(@Param("posId") Long posId,
                                                                @Param("cmdType") CommandType cmdType,
                                                                @Param("startDate") Instant startDate,
                                                                @Param("endDate") Instant endDate);

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdType=:cmdType AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofcmdTypeBetween(@Param("posId") Long posId,
                                                                 @Param("cmdType") CommandType cmdType,
                                                                 @Param("startDate") Instant startDate,
                                                                 @Param("endDate") Instant endDate, Pageable pageable);
    //Rechercher la liste des command d'un pos d'un certain statut entre 2 dates puis page par page(C)
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdStatus=:cmdStatus AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofcmdStatusBetween(@Param("posId") Long posId,
                                                                 @Param("cmdStatus") CommandStatus cmdStatus,
                                                                 @Param("startDate") Instant startDate,
                                                                 @Param("endDate") Instant endDate);

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdStatus=:cmdStatus AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofcmdStatusBetween(@Param("posId") Long posId,
                                                                 @Param("cmdStatus") CommandStatus cmdStatus,
                                                                 @Param("startDate") Instant startDate,
                                                                 @Param("endDate") Instant endDate, Pageable pageable);
    //Rechercher la liste des command d'un pos d'un certain etat de livraison puis page par page(D)
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdDelivery.deliveryState=:deliveryState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofcmdDeliveryStateBetween(
            @Param("posId") Long posId,
            @Param("deliveryState") DeliveryState deliveryState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate);

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdDelivery.deliveryState=:deliveryState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofcmdDeliveryStateBetween(
            @Param("posId") Long posId,
            @Param("deliveryState") DeliveryState deliveryState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate,
            Pageable pageable);

    //Rechercher la liste des command dans un certain etat et un certain type (A,B)
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdState=:cmdState AND cmd.cmdType=:cmdType AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofcmdStateAndcmdTypeBetween(
            @Param("posId") Long posId,
            @Param("cmdState") CommandState cmdState,
            @Param("cmdType") CommandType cmdType,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate);

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdState=:cmdState AND cmd.cmdType=:cmdType AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofcmdStateAndcmdTypeBetween(
            @Param("posId") Long posId,
            @Param("cmdState") CommandState cmdState,
            @Param("cmdType") CommandType cmdType,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate,
            Pageable pageable);
    //Rechercher la liste des command dans un certain etat et un certain statut (A,C)
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdState=:cmdState AND cmd.cmdStatus=:cmdStatus AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofcmdStateAndcmdStatusBetween(
            @Param("posId") Long posId,
            @Param("cmdState") CommandState cmdState,
            @Param("cmdStatus") CommandStatus cmdStatus,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate);

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdState=:cmdState AND cmd.cmdStatus=:cmdStatus AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofcmdStateAndcmdStatusBetween(
            @Param("posId") Long posId,
            @Param("cmdState") CommandState cmdState,
            @Param("cmdStatus") CommandStatus cmdStatus,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate,
            Pageable pageable);
    //Rechercher la liste des command dans un certain etat et un certain etat de livraison (A,D)
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdState=:cmdState AND cmd.cmdDelivery.deliveryState=:deliveryState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofcmdStateAndcmdDeliveryStateBetween(
            @Param("posId") Long posId,
            @Param("cmdState") CommandState cmdState,
            @Param("deliveryState") DeliveryState deliveryState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate);

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdState=:cmdState AND cmd.cmdDelivery.deliveryState=:deliveryState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofcmdStateAndcmdDeliveryStateBetween(
            @Param("posId") Long posId,
            @Param("cmdState") CommandState cmdState,
            @Param("deliveryState") DeliveryState deliveryState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate,
            Pageable pageable);

    //Rechercher la liste des command d'un pos d'un certain type et un certains statut entre 2 dates puis page par page(B,C)
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdType=:cmdType AND cmd.cmdStatus=:cmdStatus AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofcmdTypeAndcmdStatusBetween(
            @Param("posId") Long posId,
            @Param("cmdType") CommandType cmdType,
            @Param("cmdStatus") CommandStatus cmdStatus,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate);

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdType=:cmdType AND cmd.cmdStatus=:cmdStatus AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofcmdTypeAndcmdStatusBetween(
            @Param("posId") Long posId,
            @Param("cmdType") CommandType cmdType,
            @Param("cmdStatus") CommandStatus cmdStatus,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate,
            Pageable pageable);
    //Rechercher la liste des command d'un pos d'un certain type et un certains etat de livraison entre
    // 2 dates puis page par page(B,D)
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdType=:cmdType AND cmd.cmdDelivery.deliveryState=:deliveryState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofcmdTypeAndcmdDeliveryStateBetween(
            @Param("posId") Long posId,
            @Param("cmdType") CommandType cmdType,
            @Param("deliveryState") DeliveryState deliveryState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate);//(B,D)

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdType=:cmdType AND cmd.cmdDelivery.deliveryState=:deliveryState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofcmdTypeAndcmdDeliveryStateBetween(
            @Param("posId") Long posId,
            @Param("cmdType") CommandType cmdType,
            @Param("deliveryState") DeliveryState deliveryState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate,
            Pageable pageable);//(B,D)
    //Rechercher la liste des command d'un pos d'un certain statut et un etat de livraison entre 2 dates
    // puis page par page(C,D)
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdType=:cmdType AND cmd.cmdDelivery.deliveryState=:deliveryState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofcmdStatusAndcmdDeliveryStateBetween(
            @Param("posId") Long posId,
            @Param("cmdType") CommandType cmdType,
            @Param("deliveryState") DeliveryState deliveryState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate);//(C,D)

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdType=:cmdType AND cmd.cmdDelivery.deliveryState=:deliveryState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofcmdStatusAndcmdDeliveryStateBetween(
            @Param("posId") Long posId,
            @Param("cmdType") CommandType cmdType,
            @Param("deliveryState") DeliveryState deliveryState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate,
            Pageable pageable);//(C,D)

    //Rechercher la liste des command d'un pos dans un certain etat, d'un certain type et d'un certain statut
    // entre 2 dates puis page par page(A,B,C)
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdState=:cmdState AND cmd.cmdType=:cmdType AND cmd.cmdStatus=:cmdStatus AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofcmdStateAndcmdTypeAndcmdStatusBetween(
            @Param("posId") Long posId,
            @Param("cmdState") CommandState cmdState,
            @Param("cmdType") CommandType cmdType,
            @Param("cmdStatus") CommandStatus cmdStatus,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate);//(A,B,C)

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdState=:cmdState AND cmd.cmdType=:cmdType AND cmd.cmdStatus=:cmdStatus AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofcmdStateAndcmdTypeAndcmdStatusBetween(
            @Param("posId") Long posId,
            @Param("cmdState") CommandState cmdState,
            @Param("cmdType") CommandType cmdType,
            @Param("cmdStatus") CommandStatus cmdStatus,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate,
            Pageable pageable);//(A,B,C)
    //Rechercher la liste des command d'un pos dans un certain etat, d'un certain type et d'un certain
    // etat de livraison entre 2 dates puis page par page(A,B,D)
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdState=:cmdState AND cmd.cmdType=:cmdType AND cmd.cmdDelivery.deliveryState=:deliveryState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofcmdStateAndcmdTypeAndcmdDeliveryStateBetween(
            @Param("posId") Long posId,
            @Param("cmdState") CommandState cmdState,
            @Param("cmdType") CommandType cmdType,
            @Param("deliveryState") DeliveryState deliveryState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate);

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdState=:cmdState AND cmd.cmdType=:cmdType AND cmd.cmdDelivery.deliveryState=:deliveryState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofcmdStateAndcmdTypeAndcmdDeliveryStateBetween(
            @Param("posId") Long posId,
            @Param("cmdState") CommandState cmdState,
            @Param("cmdType") CommandType cmdType,
            @Param("deliveryState") DeliveryState deliveryState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate,
            Pageable pageable);

    //Rechercher la liste des command d'un pos dans un certain etat, d'un certain statut et d'un etat
    // de livraison entre 2 dates puis page par page(A,C,D)
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdState=:cmdState AND cmd.cmdStatus=:cmdStatus AND cmd.cmdDelivery.deliveryState=:deliveryState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
            @Param("posId") Long posId,
            @Param("cmdState") CommandState cmdState,
            @Param("cmdStatus") CommandStatus cmdStatus,
            @Param("deliveryState") DeliveryState deliveryState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate);

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdState=:cmdState AND cmd.cmdStatus=:cmdStatus AND cmd.cmdDelivery.deliveryState=:deliveryState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
            @Param("posId") Long posId,
            @Param("cmdState") CommandState cmdState,
            @Param("cmdStatus") CommandStatus cmdStatus,
            @Param("deliveryState") DeliveryState deliveryState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate,
            Pageable pageable);

    //Rechercher la liste des command d'un pos dans un certain etat, d'un certain type, d'un certain
    // statut et d'un etat de livraison entre 2 dates puis page par page(A,B,C,D)
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdState=:cmdState AND cmd.cmdType=:cmdType AND cmd.cmdStatus=:cmdStatus AND cmd.cmdDelivery.deliveryState=:deliveryState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofcmdStateAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(
            @Param("posId") Long posId,
            @Param("cmdState") CommandState cmdState,
            @Param("cmdType") CommandType cmdType,
            @Param("cmdStatus") CommandStatus cmdStatus,
            @Param("deliveryState") DeliveryState deliveryState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate);

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdState=:cmdState AND cmd.cmdType=:cmdType AND cmd.cmdStatus=:cmdStatus AND cmd.cmdDelivery.deliveryState=:deliveryState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofcmdStateAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(
            @Param("posId") Long posId,
            @Param("cmdState") CommandState cmdState,
            @Param("cmdType") CommandType cmdType,
            @Param("cmdStatus") CommandStatus cmdStatus,
            @Param("deliveryState") DeliveryState deliveryState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate,
            Pageable pageable);











    //Rechercher la liste des command d'un pos d'un certain Client puis page par page
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdClient.id=:clientId AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofClientBetween(@Param("posId") Long posId,
                                                               @Param("clientId") Long clientId,
                                                               @Param("startDate") Instant startDate,
                                                               @Param("endDate") Instant endDate);

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdClient.id=:clientId AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofClientBetween(@Param("posId") Long posId,
                                                                @Param("clientId") Long clientId,
                                                                @Param("startDate") Instant startDate,
                                                                @Param("endDate") Instant endDate, Pageable pageable);

    //Rechercher la liste des command dans un pos d'un client et d'un certain type (E)
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdClient.id=:clientId AND cmd.cmdType=:cmdType AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofClientAndcmdTypeBetween(
            @Param("posId") Long posId,
            @Param("clientId") Long clientId,
            @Param("cmdType") CommandType cmdType,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate); //E

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdClient.id=:clientId AND cmd.cmdType=:cmdType AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofClientAndcmdTypeBetween(
            @Param("posId") Long posId,
            @Param("clientId") Long clientId,
            @Param("cmdType") CommandType cmdType,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate,
            Pageable pageable);//E

    //Rechercher la liste des command dans un pos d'un client et d'un certain etat (F)
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdClient.id=:clientId AND cmd.cmdState=:cmdState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofClientAndcmdStateBetween(
            @Param("posId") Long posId,
            @Param("clientId") Long clientId,
            @Param("cmdState") CommandState cmdState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate); //F

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdClient.id=:clientId AND cmd.cmdState=:cmdState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofClientAndcmdStateBetween(
            @Param("posId") Long posId,
            @Param("clientId") Long clientId,
            @Param("cmdState") CommandState cmdState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate,
            Pageable pageable); //F

    //Rechercher la liste des command dans un pos d'un client et d'un certain statut (G)
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdClient.id=:clientId AND cmd.cmdStatus=:cmdStatus AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofClientAndcmdStatusBetween(
            @Param("posId") Long posId,
            @Param("clientId") Long clientId,
            @Param("cmdStatus") CommandStatus cmdStatus,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate); //G

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdClient.id=:clientId AND cmd.cmdStatus=:cmdStatus AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofClientAndcmdStatusBetween(
            @Param("posId") Long posId,
            @Param("clientId") Long clientId,
            @Param("cmdStatus") CommandStatus cmdStatus,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate, Pageable pageable); //G

    //Rechercher la liste des command dans un pos d'un client et d'un certain etat de livraison (H)
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdClient.id=:clientId AND cmd.cmdDelivery.deliveryState=:deliveryState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofClientAndcmdDeliveryStateBetween(
            @Param("posId") Long posId,
            @Param("clientId") Long clientId,
            @Param("deliveryState") DeliveryState deliveryState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate); //H

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdClient.id=:clientId AND cmd.cmdDelivery.deliveryState=:deliveryState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofClientAndcmdDeliveryStateBetween(
            @Param("posId") Long posId,
            @Param("clientId") Long clientId,
            @Param("deliveryState") DeliveryState deliveryState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate, Pageable pageable); //H

    //Rechercher la liste des command dans un pos d'un client et d'un certain type et un certain etat (E,F)
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdClient.id=:clientId AND cmd.cmdType=:cmdType AND cmd.cmdState=:cmdState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofClientAndcmdTypeAndcmdStateBetween(
            @Param("posId") Long posId,
            @Param("clientId") Long clientId,
            @Param("cmdType") CommandType cmdType,
            @Param("cmdState") CommandState cmdState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate); //E,F

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdClient.id=:clientId AND cmd.cmdType=:cmdType AND cmd.cmdState=:cmdState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofClientAndcmdTypeAndcmdStateBetween(
            @Param("posId") Long posId,
            @Param("clientId") Long clientId,
            @Param("cmdType") CommandType cmdType,
            @Param("cmdState") CommandState cmdState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate,
            Pageable pageable); //E,F

    //Rechercher la liste des command dans un pos d'un client et d'un certain type et un certain statut (E,G)
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdClient.id=:clientId AND cmd.cmdType=:cmdType AND cmd.cmdStatus=:cmdStatus AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofClientAndcmdTypeAndcmdStatusBetween(
            @Param("posId") Long posId,
            @Param("clientId") Long clientId,
            @Param("cmdType") CommandType cmdType,
            @Param("cmdStatus") CommandStatus cmdStatus,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate); //E,G

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdClient.id=:clientId AND cmd.cmdType=:cmdType AND cmd.cmdStatus=:cmdStatus AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofClientAndcmdTypeAndcmdStatusBetween(
            @Param("posId") Long posId,
            @Param("clientId") Long clientId,
            @Param("cmdType") CommandType cmdType,
            @Param("cmdStatus") CommandStatus cmdStatus,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate,
            Pageable pageable); //E,G

    //Rechercher la liste des command dans un pos d'un client et d'un certain type et un certain
    // etat de livraison (E,H)
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdClient.id=:clientId AND cmd.cmdType=:cmdType AND cmd.cmdDelivery.deliveryState=:deliveryState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofClientAndcmdTypeAndcmdDeliveryStateBetween(
            @Param("posId") Long posId,
            @Param("clientId") Long clientId,
            @Param("cmdType") CommandType cmdType,
            @Param("deliveryState") DeliveryState deliveryState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate); //E,H

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdClient.id=:clientId AND cmd.cmdType=:cmdType AND cmd.cmdDelivery.deliveryState=:deliveryState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofClientAndcmdTypeAndcmdDeliveryStateBetween(
            @Param("posId") Long posId,
            @Param("clientId") Long clientId,
            @Param("cmdType") CommandType cmdType,
            @Param("deliveryState") DeliveryState deliveryState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate,
            Pageable pageable); //E,H

    //Rechercher la liste des command dans un pos d'un client d'un certain type, un certain etat et
    // d'un certain statut (E,F,G)
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdClient.id=:clientId AND cmd.cmdType=:cmdType AND cmd.cmdState=:cmdState AND cmd.cmdStatus=:cmdStatus AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofClientAndcmdTypeAndcmdStateAndcmdStatusBetween(
            @Param("posId") Long posId,
            @Param("clientId") Long clientId,
            @Param("cmdType") CommandType cmdType,
            @Param("cmdState") CommandState cmdState,
            @Param("cmdStatus") CommandStatus cmdStatus,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate); //E,F,G

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdClient.id=:clientId AND cmd.cmdType=:cmdType AND cmd.cmdState=:cmdState AND cmd.cmdStatus=:cmdStatus AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofClientAndcmdTypeAndcmdStateAndcmdStatusBetween(
            @Param("posId") Long posId,
            @Param("clientId") Long clientId,
            @Param("cmdType") CommandType cmdType,
            @Param("cmdState") CommandState cmdState,
            @Param("cmdStatus") CommandStatus cmdStatus,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate,
            Pageable pageable); //E,F,G
    //Rechercher la liste des command dans un pos d'un client d'un certain type, un certain etat et d'un certain
    // etat de livraison (E,F,H)
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdClient.id=:clientId AND cmd.cmdType=:cmdType AND cmd.cmdState=:cmdState AND cmd.cmdDelivery.deliveryState=:deliveryState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofClientAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween(
            @Param("posId") Long posId,
            @Param("clientId") Long clientId,
            @Param("cmdType") CommandType cmdType,
            @Param("cmdState") CommandState cmdState,
            @Param("deliveryState") DeliveryState deliveryState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate); //E,F,H

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdClient.id=:clientId AND cmd.cmdType=:cmdType AND cmd.cmdState=:cmdState AND cmd.cmdDelivery.deliveryState=:deliveryState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofClientAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween(
            @Param("posId") Long posId,
            @Param("clientId") Long clientId,
            @Param("cmdType") CommandType cmdType,
            @Param("cmdState") CommandState cmdState,
            @Param("deliveryState") DeliveryState deliveryState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate,
            Pageable pageable); //E,F,H

    //Rechercher la liste des command dans un pos d'un client et d'un certain type et un certain statut et un certain
    // etat de livraison (E,G,H)
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdClient.id=:clientId AND cmd.cmdType=:cmdType AND cmd.cmdStatus=:cmdStatus AND cmd.cmdDelivery.deliveryState=:deliveryState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofClientAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(
            @Param("posId") Long posId,
            @Param("clientId") Long clientId,
            @Param("cmdType") CommandType cmdType,
            @Param("cmdStatus") CommandStatus cmdStatus,
            @Param("deliveryState") DeliveryState deliveryState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate); //E,G,H

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdClient.id=:clientId AND cmd.cmdType=:cmdType AND cmd.cmdStatus=:cmdStatus AND cmd.cmdDelivery.deliveryState=:deliveryState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofClientAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(
            @Param("posId") Long posId,
            @Param("clientId") Long clientId,
            @Param("cmdType") CommandType cmdType,
            @Param("cmdStatus") CommandStatus cmdStatus,
            @Param("deliveryState") DeliveryState deliveryState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate,
            Pageable pageable); //E,G,H

    //Rechercher la liste des command dans un pos d'un client d'un certain type, un certain etat et d'un certain
    // statut et un certain etat de livraison (E,F,G,H)
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdClient.id=:clientId AND cmd.cmdType=:cmdType AND cmd.cmdState=:cmdState AND cmd.cmdStatus=:cmdStatus AND cmd.cmdDelivery.deliveryState=:deliveryState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofClientAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
            @Param("posId") Long posId,
            @Param("clientId") Long clientId,
            @Param("cmdType") CommandType cmdType,
            @Param("cmdState") CommandState cmdState,
            @Param("cmdStatus") CommandStatus cmdStatus,
            @Param("deliveryState") DeliveryState deliveryState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate); //E,F,G,H

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdClient.id=:clientId AND cmd.cmdType=:cmdType AND cmd.cmdState=:cmdState AND cmd.cmdStatus=:cmdStatus AND cmd.cmdDelivery.deliveryState=:deliveryState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofClientAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
            @Param("posId") Long posId,
            @Param("clientId") Long clientId,
            @Param("cmdType") CommandType cmdType,
            @Param("cmdState") CommandState cmdState,
            @Param("cmdStatus") CommandStatus cmdStatus,
            @Param("deliveryState") DeliveryState deliveryState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate, Pageable pageable); //E,F,G,H

    //Rechercher la liste des command d'un pos d'un certain UserBM puis page par page
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdUserbm.id=:userbmId AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofUserbmBetween(@Param("posId") Long posId,
                                                               @Param("userbmId") Long userbmId,
                                                               @Param("startDate") Instant startDate,
                                                               @Param("endDate") Instant endDate);

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdUserbm.id=:userbmId AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofUserbmBetween(@Param("posId") Long posId,
                                                                @Param("userbmId") Long userbmId,
                                                                @Param("startDate") Instant startDate,
                                                                @Param("endDate") Instant endDate,
                                                                Pageable pageable);





    //Rechercher la liste des command dans un pos d'un Userbm et d'un certain type (I)
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdUserbm.id=:userbmId AND cmd.cmdType=:cmdType AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofUserbmAndcmdTypeBetween(@Param("posId") Long posId,
                                                               @Param("userbmId") Long userbmId,
                                                               @Param("cmdType") CommandType cmdType,
                                                               @Param("startDate") Instant startDate,
                                                               @Param("endDate") Instant endDate);//I

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdUserbm.id=:userbmId AND cmd.cmdType=:cmdType AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofUserbmAndcmdTypeBetween(
            @Param("posId") Long posId,
            @Param("userbmId") Long userbmId,
            @Param("cmdType") CommandType cmdType,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate,
            Pageable pageable);//I

    //Rechercher la liste des command dans un pos d'un Userbm et d'un certain etat (J)
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdUserbm.id=:userbmId AND cmd.cmdState=:cmdState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofUserbmAndcmdStateBetween(@Param("posId") Long posId,
                                                                          @Param("userbmId") Long userbmId,
                                                                          @Param("cmdState") CommandState cmdState,
                                                                          @Param("startDate") Instant startDate,
                                                                          @Param("endDate") Instant endDate);//J

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdUserbm.id=:userbmId AND cmd.cmdState=:cmdState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofUserbmAndcmdStateBetween(@Param("posId") Long posId,
                                                                           @Param("userbmId") Long userbmId,
                                                                           @Param("cmdState") CommandState cmdState,
                                                                           @Param("startDate") Instant startDate,
                                                                           @Param("endDate") Instant endDate,
                                                                           Pageable pageable);//J

    //Rechercher la liste des command dans un pos d'un Userbm et d'un certain statut (K)
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdUserbm.id=:userbmId AND cmd.cmdStatus=:cmdStatus AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofUserbmAndcmdStatusBetween(@Param("posId") Long posId,
                                                                          @Param("userbmId") Long userbmId,
                                                                          @Param("cmdStatus") CommandState cmdStatus,
                                                                          @Param("startDate") Instant startDate,
                                                                          @Param("endDate") Instant endDate);//K

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdUserbm.id=:userbmId AND cmd.cmdStatus=:cmdStatus AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofUserbmAndcmdStatusBetween(@Param("posId") Long posId,
                                                                            @Param("userbmId") Long userbmId,
                                                                            @Param("cmdStatus") CommandState cmdStatus,
                                                                            @Param("startDate") Instant startDate,
                                                                            @Param("endDate") Instant endDate,
                                                                            Pageable pageable);//K

    //Rechercher la liste des command dans un pos d'un Userbm et d'un certain etat de livraison (L)
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdUserbm.id=:userbmId AND cmd.cmdDelivery.deliveryState=:deliveryState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofUserbmAndcmdDeliveryStateBetween(
            @Param("posId") Long posId,
            @Param("userbmId") Long userbmId,
            @Param("deliveryState") DeliveryState deliveryState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate);//L

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdUserbm.id=:userbmId AND cmd.cmdDelivery.deliveryState=:deliveryState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofUserbmAndcmdDeliveryStateBetween(
            @Param("posId") Long posId,
            @Param("userbmId") Long userbmId,
            @Param("deliveryState") DeliveryState deliveryState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate, Pageable pageable);//L

    //Rechercher la liste des command dans un pos d'un Userbm et d'un certain type et un certain etat (I,J)
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdUserbm.id=:userbmId AND cmd.cmdType=:cmdType AND cmd.cmdState=:cmdState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofUserbmAndcmdTypeAndcmdStateBetween(
            @Param("posId") Long posId,
            @Param("userbmId") Long userbmId,
            @Param("cmdType") CommandType cmdType,
            @Param("cmdState") CommandState cmdState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate);//I,J

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdUserbm.id=:userbmId AND cmd.cmdType=:cmdType AND cmd.cmdState=:cmdState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofUserbmAndcmdTypeAndcmdStateBetween(
            @Param("posId") Long posId,
            @Param("userbmId") Long userbmId,
            @Param("cmdType") CommandType cmdType,
            @Param("cmdState") CommandState cmdState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate,
            Pageable pageable);//I,J

    //Rechercher la liste des command dans un pos d'un Userbm et d'un certain type et un certain statut (I,K)
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdUserbm.id=:userbmId AND cmd.cmdType=:cmdType AND cmd.cmdStatus=:cmdStatus AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofUserbmAndcmdTypeAndcmdStatusBetween(
            @Param("posId") Long posId,
            @Param("userbmId") Long userbmId,
            @Param("cmdType") CommandType cmdType,
            @Param("cmdStatus") CommandStatus cmdStatus,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate);//I,K

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdUserbm.id=:userbmId AND cmd.cmdType=:cmdType AND cmd.cmdStatus=:cmdStatus AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofUserbmAndcmdTypeAndcmdStatusBetween(
            @Param("posId") Long posId,
            @Param("userbmId") Long userbmId,
            @Param("cmdType") CommandType cmdType,
            @Param("cmdStatus") CommandStatus cmdStatus,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate, Pageable pageable);//I,K

    //Rechercher la liste des command dans un pos d'un Userbm et d'un certain type et un certain
    // etat de livraison (I,L)
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdUserbm.id=:userbmId AND cmd.cmdType=:cmdType AND cmd.cmdDelivery.deliveryState=:deliveryState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofUserbmAndcmdTypeAndcmdStatusBetween(
            @Param("posId") Long posId,
            @Param("userbmId") Long userbmId,
            @Param("cmdType") CommandType cmdType,
            @Param("deliveryState") DeliveryState deliveryState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate);//I,L

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdUserbm.id=:userbmId AND cmd.cmdType=:cmdType AND cmd.cmdDelivery.deliveryState=:deliveryState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofUserbmAndcmdTypeAndcmdStatusBetween(
            @Param("posId") Long posId,
            @Param("userbmId") Long userbmId,
            @Param("cmdType") CommandType cmdType,
            @Param("deliveryState") DeliveryState deliveryState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate, Pageable pageable);//I,L

    //Rechercher la liste des command dans un pos d'un Userbm d'un certain type, un certain etat et
    // d'un certain statut (I,J,K)
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdUserbm.id=:userbmId AND cmd.cmdType=:cmdType AND cmd.cmdState=:cmdState AND cmd.cmdStatus=:cmdStatus AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdStatusBetween(
            @Param("posId") Long posId,
            @Param("userbmId") Long userbmId,
            @Param("cmdType") CommandType cmdType,
            @Param("cmdState") CommandState cmdState,
            @Param("cmdStatus") CommandStatus cmdStatus,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate);//I,J,K

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdUserbm.id=:userbmId AND cmd.cmdType=:cmdType AND cmd.cmdState=:cmdState AND cmd.cmdStatus=:cmdStatus AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdStatusBetween(
            @Param("posId") Long posId,
            @Param("userbmId") Long userbmId,
            @Param("cmdType") CommandType cmdType,
            @Param("cmdState") CommandState cmdState,
            @Param("cmdStatus") CommandStatus cmdStatus,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate, Pageable pageable);//I,J,K

    //Rechercher la liste des command dans un pos d'un Userbm d'un certain type, un certain etat et d'un certain
    // etat de livraison (I,J,L)
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdUserbm.id=:userbmId AND cmd.cmdType=:cmdType AND cmd.cmdState=:cmdState AND cmd.cmdDelivery.deliveryState=:deliveryState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween(
            @Param("posId") Long posId,
            @Param("userbmId") Long userbmId,
            @Param("cmdType") CommandType cmdType,
            @Param("cmdState") CommandState cmdState,
            @Param("deliveryState") DeliveryState deliveryState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate);//I,J,L

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdUserbm.id=:userbmId AND cmd.cmdType=:cmdType AND cmd.cmdState=:cmdState AND cmd.cmdDelivery.deliveryState=:deliveryState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween(
            @Param("posId") Long posId,
            @Param("userbmId") Long userbmId,
            @Param("cmdType") CommandType cmdType,
            @Param("cmdState") CommandState cmdState,
            @Param("deliveryState") DeliveryState deliveryState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate, Pageable pageable);//I,J,L

    //Rechercher la liste des command dans un pos d'un Userbm et d'un certain type et un certain statut et un certain
    // etat de livraison (I,K,L)
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdUserbm.id=:userbmId AND cmd.cmdType=:cmdType AND cmd.cmdStatus=:cmdStatus AND cmd.cmdDelivery.deliveryState=:deliveryState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofUserbmAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(
            @Param("posId") Long posId,
            @Param("userbmId") Long userbmId,
            @Param("cmdType") CommandType cmdType,
            @Param("cmdStatus") CommandStatus cmdStatus,
            @Param("deliveryState") DeliveryState deliveryState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate);//I,K,L

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdUserbm.id=:userbmId AND cmd.cmdType=:cmdType AND cmd.cmdStatus=:cmdStatus AND cmd.cmdDelivery.deliveryState=:deliveryState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofUserbmAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(
            @Param("posId") Long posId,
            @Param("userbmId") Long userbmId,
            @Param("cmdType") CommandType cmdType,
            @Param("cmdStatus") CommandStatus cmdStatus,
            @Param("deliveryState") DeliveryState deliveryState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate, Pageable pageable);//I,K,L

    //Rechercher la liste des command dans un pos d'un Userbm d'un certain type, un certain etat et d'un certain
    // statut et un certain etat de livraison (I,J,K,L)
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdUserbm.id=:userbmId AND cmd.cmdType=:cmdType AND cmd.cmdState=:cmdState AND cmd.cmdStatus=:cmdStatus AND cmd.cmdDelivery.deliveryState=:deliveryState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
            @Param("posId") Long posId,
            @Param("userbmId") Long userbmId,
            @Param("cmdType") CommandType cmdType,
            @Param("cmdState") CommandState cmdState,
            @Param("cmdStatus") CommandStatus cmdStatus,
            @Param("deliveryState") DeliveryState deliveryState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate);//I,J,K,L

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdUserbm.id=:userbmId AND cmd.cmdType=:cmdType AND cmd.cmdState=:cmdState AND cmd.cmdStatus=:cmdStatus AND cmd.cmdDelivery.deliveryState=:deliveryState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
            @Param("posId") Long posId,
            @Param("userbmId") Long userbmId,
            @Param("cmdType") CommandType cmdType,
            @Param("cmdState") CommandState cmdState,
            @Param("cmdStatus") CommandStatus cmdStatus,
            @Param("deliveryState") DeliveryState deliveryState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate, Pageable pageable);//I,J,K,L


    //Rechercher la liste des command dans un pos d'un certain UserBM pour un certain client puis page par page
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdUserbm.id=:userbmId AND cmd.cmdClient.id=:clientId AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofClientforUserbmBetween(
            @Param("posId") Long posId,
            @Param("clientId") Long clientId,
            @Param("userbmId") Long userbmId,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate);

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdUserbm.id=:userbmId AND cmd.cmdClient.id=:clientId AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofUserbmforClientBetween(
            @Param("posId") Long posId,
            @Param("userbmId") Long userbmId,
            @Param("clientId") Long clientId,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate,
            Pageable pageable);


    //Rechercher la liste des command dans un pos d'un Userbm pour un client et d'un certain type (M)
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdClient.id=:clientId AND cmd.cmdUserbm.id=:userbmId AND cmd.cmdType=:cmdType AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofClientAndUserbmAndcmdTypeBetween(
            @Param("posId") Long posId,
            @Param("clientId") Long clientId,
            @Param("userbmId") Long userbmId,
            @Param("cmdType") CommandType cmdType,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate); //M

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdClient.id=:clientId AND cmd.cmdUserbm.id=:userbmId AND cmd.cmdType=:cmdType AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofClientAndUserbmAndcmdTypeBetween(
            @Param("posId") Long posId,
            @Param("clientId") Long clientId,
            @Param("userbmId") Long userbmId,
            @Param("cmdType") CommandType cmdType,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate,
            Pageable pageable); //M

    //Rechercher la liste des command dans un pos d'un Userbm pour un client et d'un certain etat (N)
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdClient.id=:clientId AND cmd.cmdUserbm.id=:userbmId AND cmd.cmdState=:cmdState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofClientAndUserbmAndcmdStateBetween(
            @Param("posId") Long posId,
            @Param("clientId") Long clientId,
            @Param("userbmId") Long userbmId,
            @Param("cmdState") CommandState cmdState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate); //N

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdClient.id=:clientId AND cmd.cmdUserbm.id=:userbmId AND cmd.cmdState=:cmdState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofClientAndUserbmAndcmdStateBetween(
            @Param("posId") Long posId,
            @Param("clientId") Long clientId,
            @Param("userbmId") Long userbmId,
            @Param("cmdState") CommandState cmdState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate,
            Pageable pageable); //N

    //Rechercher la liste des command dans un pos d'un Userbm pour un client et d'un certain statut (O)
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdClient.id=:clientId AND cmd.cmdUserbm.id=:userbmId AND cmd.cmdStatus=:cmdStatus AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofClientAndUserbmAndcmdStateBetween(
            @Param("posId") Long posId,
            @Param("clientId") Long clientId,
            @Param("userbmId") Long userbmId,
            @Param("cmdStatus") CommandStatus cmdStatus,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate); //O

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdClient.id=:clientId AND cmd.cmdUserbm.id=:userbmId AND cmd.cmdStatus=:cmdStatus AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofClientAndUserbmAndcmdStateBetween(
            @Param("posId") Long posId,
            @Param("clientId") Long clientId,
            @Param("userbmId") Long userbmId,
            @Param("cmdStatus") CommandStatus cmdStatus,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate,
            Pageable pageable); //O

    //Rechercher la liste des command dans un pos d'un Userbm pour un client et d'un certain
    // etat de livraison (P)
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdClient.id=:clientId AND cmd.cmdUserbm.id=:userbmId AND cmd.cmdDelivery.deliveryState=:deliveryState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofClientAndUserbmAndcmdDeliveryStateBetween(
            @Param("posId") Long posId,
            @Param("clientId") Long clientId,
            @Param("userbmId") Long userbmId,
            @Param("deliveryState") DeliveryState deliveryState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate); //P

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdClient.id=:clientId AND cmd.cmdUserbm.id=:userbmId AND cmd.cmdDelivery.deliveryState=:deliveryState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofClientAndUserbmAndcmdDeliveryStateBetween(
            @Param("posId") Long posId,
            @Param("clientId") Long clientId,
            @Param("userbmId") Long userbmId,
            @Param("deliveryState") DeliveryState deliveryState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate,
            Pageable pageable); //P

    //Rechercher la liste des command dans un pos d'un Userbm pour un client et d'un certain type
    // et un certain etat (M,N)
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdClient.id=:clientId AND cmd.cmdUserbm.id=:userbmId AND cmd.cmdType=:cmdType AND cmd.cmdState=:cmdState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateBetween(
            @Param("posId") Long posId,
            @Param("clientId") Long clientId,
            @Param("userbmId") Long userbmId,
            @Param("cmdType") CommandType cmdType,
            @Param("cmdState") CommandState cmdState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate); //M,N

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdClient.id=:clientId AND cmd.cmdUserbm.id=:userbmId AND cmd.cmdType=:cmdType AND cmd.cmdState=:cmdState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateBetween(
            @Param("posId") Long posId,
            @Param("clientId") Long clientId,
            @Param("userbmId") Long userbmId,
            @Param("cmdType") CommandType cmdType,
            @Param("cmdState") CommandState cmdState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate,
            Pageable pageable); //M,N

    //Rechercher la liste des command dans un pos d'un Userbm pour un client et d'un certain type
    // et un certain statut (M,O)
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdClient.id=:clientId AND cmd.cmdUserbm.id=:userbmId AND cmd.cmdType=:cmdType AND cmd.cmdStatus=:cmdStatus AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStatusBetween(
            @Param("posId") Long posId,
            @Param("clientId") Long clientId,
            @Param("userbmId") Long userbmId,
            @Param("cmdType") CommandType cmdType,
            @Param("cmdStatus") CommandStatus cmdStatus,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate); //M,O

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdClient.id=:clientId AND cmd.cmdUserbm.id=:userbmId AND cmd.cmdType=:cmdType AND cmd.cmdStatus=:cmdStatus AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStatusBetween(
            @Param("posId") Long posId,
            @Param("clientId") Long clientId,
            @Param("userbmId") Long userbmId,
            @Param("cmdType") CommandType cmdType,
            @Param("cmdStatus") CommandStatus cmdStatus,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate,
            Pageable pageable); //M,O

    //Rechercher la liste des command dans un pos d'un Userbm pour un client et d'un certain type et un certain
    // etat de livraison (M,P)
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdClient.id=:clientId AND cmd.cmdUserbm.id=:userbmId AND cmd.cmdType=:cmdType AND cmd.cmdDelivery.deliveryState=:deliveryState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStatusBetween(
            @Param("posId") Long posId,
            @Param("clientId") Long clientId,
            @Param("userbmId") Long userbmId,
            @Param("cmdType") CommandType cmdType,
            @Param("deliveryState") DeliveryState deliveryState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate); //M,P

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdClient.id=:clientId AND cmd.cmdUserbm.id=:userbmId AND cmd.cmdType=:cmdType AND cmd.cmdDelivery.deliveryState=:deliveryState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStatusBetween(
            @Param("posId") Long posId,
            @Param("clientId") Long clientId,
            @Param("userbmId") Long userbmId,
            @Param("cmdType") CommandType cmdType,
            @Param("deliveryState") DeliveryState deliveryState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate,
            Pageable pageable); //M,P

    //Rechercher la liste des command dans un pos d'un Userbm pour un client d'un certain type, un certain etat et
    // d'un certain statut (M,N,O)
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdClient.id=:clientId AND cmd.cmdUserbm.id=:userbmId AND cmd.cmdType=:cmdType AND cmd.cmdState=:cmdState AND cmd.cmdStatus=:cmdStatus AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdStatusBetween(
            @Param("posId") Long posId,
            @Param("clientId") Long clientId,
            @Param("userbmId") Long userbmId,
            @Param("cmdType") CommandType cmdType,
            @Param("cmdState") CommandState cmdState,
            @Param("cmdStatus") CommandStatus cmdStatus,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate); //M,N,O

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdClient.id=:clientId AND cmd.cmdUserbm.id=:userbmId AND cmd.cmdType=:cmdType AND cmd.cmdState=:cmdState AND cmd.cmdStatus=:cmdStatus AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdStatusBetween(
            @Param("posId") Long posId,
            @Param("clientId") Long clientId,
            @Param("userbmId") Long userbmId,
            @Param("cmdType") CommandType cmdType,
            @Param("cmdState") CommandState cmdState,
            @Param("cmdStatus") CommandStatus cmdStatus,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate,
            Pageable pageable); //M,N,O

    //Rechercher la liste des command dans un pos d'un Userbm pour un client d'un certain type, un certain etat et d'un certain
    // etat de livraison (M,N,P)
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdClient.id=:clientId AND cmd.cmdUserbm.id=:userbmId AND cmd.cmdType=:cmdType AND cmd.cmdState=:cmdState AND cmd.cmdDelivery.deliveryState=:deliveryState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween(
            @Param("posId") Long posId,
            @Param("clientId") Long clientId,
            @Param("userbmId") Long userbmId,
            @Param("cmdType") CommandType cmdType,
            @Param("cmdState") CommandState cmdState,
            @Param("deliveryState") DeliveryState deliveryState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate); //M,N,O

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdClient.id=:clientId AND cmd.cmdUserbm.id=:userbmId AND cmd.cmdType=:cmdType AND cmd.cmdState=:cmdState AND cmd.cmdDelivery.deliveryState=:deliveryState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween(
            @Param("posId") Long posId,
            @Param("clientId") Long clientId,
            @Param("userbmId") Long userbmId,
            @Param("cmdType") CommandType cmdType,
            @Param("cmdState") CommandState cmdState,
            @Param("deliveryState") DeliveryState deliveryState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate,
            Pageable pageable); //M,N,O

    //Rechercher la liste des command dans un pos d'un Userbm pour un client et d'un certain type et un certain statut et un certain
    // etat de livraison (M,O,P)
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdClient.id=:clientId AND cmd.cmdUserbm.id=:userbmId AND cmd.cmdType=:cmdType AND cmd.cmdStatus=:cmdStatus AND cmd.cmdDelivery.deliveryState=:deliveryState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(
            @Param("posId") Long posId,
            @Param("clientId") Long clientId,
            @Param("userbmId") Long userbmId,
            @Param("cmdType") CommandType cmdType,
            @Param("cmdStatus") CommandStatus cmdStatus,
            @Param("deliveryState") DeliveryState deliveryState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate); //M,O,P

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdClient.id=:clientId AND cmd.cmdUserbm.id=:userbmId AND cmd.cmdType=:cmdType AND cmd.cmdStatus=:cmdStatus AND cmd.cmdDelivery.deliveryState=:deliveryState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(
            @Param("posId") Long posId,
            @Param("clientId") Long clientId,
            @Param("userbmId") Long userbmId,
            @Param("cmdType") CommandType cmdType,
            @Param("cmdStatus") CommandStatus cmdStatus,
            @Param("deliveryState") DeliveryState deliveryState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate,
            Pageable pageable); //M,O,P

    //Rechercher la liste des command dans un pos d'un Userbm pour un client d'un certain type, un certain etat et d'un certain
    // statut et un certain etat de livraison (M,N,O,P)
    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdClient.id=:clientId AND cmd.cmdUserbm.id=:userbmId AND cmd.cmdType=:cmdType AND cmd.cmdState=:cmdState AND cmd.cmdStatus=:cmdStatus AND cmd.cmdDelivery.deliveryState=:deliveryState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<List<Command>> findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
            @Param("posId") Long posId,
            @Param("clientId") Long clientId,
            @Param("userbmId") Long userbmId,
            @Param("cmdType") CommandType cmdType,
            @Param("cmdState") CommandState cmdState,
            @Param("cmdStatus") CommandStatus cmdStatus,
            @Param("deliveryState") DeliveryState deliveryState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate); //M,N,O,P

    @Query("SELECT cmd FROM Command cmd WHERE cmd.commandPosId=:posId AND cmd.cmdClient.id=:clientId AND cmd.cmdUserbm.id=:userbmId AND cmd.cmdType=:cmdType AND cmd.cmdState=:cmdState AND cmd.cmdStatus=:cmdStatus AND cmd.cmdDelivery.deliveryState=:deliveryState AND (cmd.cmdDate>=:startDate AND cmd.cmdDate<=:endDate) ORDER BY cmd.cmdDate ASC, cmd.cmdCode ASC")
    Optional<Page<Command>> findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
            @Param("posId") Long posId,
            @Param("clientId") Long clientId,
            @Param("userbmId") Long userbmId,
            @Param("cmdType") CommandType cmdType,
            @Param("cmdState") CommandState cmdState,
            @Param("cmdStatus") CommandStatus cmdStatus,
            @Param("deliveryState") DeliveryState deliveryState,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate,
            Pageable pageable); //M,N,O,P


    /************************************
     * A propos des Loading
     */
    //Liste des command d'un Loading puis page par page
    @Query("SELECT cmd FROM Command cmd WHERE cmd.cmdLoading.id=:loadingId AND cmd.commandPosId=:posId ORDER BY cmd.cmdDate ASC, cmd.cmdLoading.loadCode ASC")
    Optional<List<Command>> findAllCommandofLoadinginPos(Long loadingId, Long posId);

    @Query("SELECT cmd FROM Command cmd WHERE cmd.cmdLoading.id=:loadingId AND cmd.commandPosId=:posId ORDER BY cmd.cmdDate ASC, cmd.cmdLoading.loadCode ASC")
    Optional<Page<Command>> findPageCommandofLoadinginPos(Long loadingId, Long posId, Pageable pageable);

}
