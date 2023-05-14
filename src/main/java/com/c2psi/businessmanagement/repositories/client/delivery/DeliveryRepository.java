package com.c2psi.businessmanagement.repositories.client.delivery;

import com.c2psi.businessmanagement.Enumerations.DeliveryState;
import com.c2psi.businessmanagement.models.Delivery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    //rechercher un delivery par son id
    Optional<Delivery> findDeliveryById(Long deliveryId);
    //Rechercher un delivery par son code dans un pos
    @Query("SELECT delivery FROM Delivery delivery WHERE delivery.deliveryCode=:deliveryCode AND delivery.deliveryPos.id=:posId")
    Optional<Delivery> findDeliveryByCodeInPos(@Param("deliveryCode") String deliveryCode, @Param("posId") Long posId);
    //Rechercher la liste des delivery dans un pos entre 02 dates puis page par page
    @Query("SELECT delivery FROM Delivery delivery WHERE delivery.deliveryPos.id=:posId AND (delivery.deliveryDate>=:startDate AND delivery.deliveryDate<=:endDate) ORDER BY delivery.creationDate ASC")
    Optional<List<Delivery>> findAllDeliveryinPosBetween(@Param("posId") Long posId, @Param("startDate") Instant startDate, @Param("endDate") Instant endDate);
    @Query("SELECT delivery FROM Delivery delivery WHERE delivery.deliveryPos.id=:posId AND (delivery.deliveryDate>=:startDate AND delivery.deliveryDate<=:endDate) ORDER BY delivery.creationDate ASC")
    Optional<Page<Delivery>> findPageDeliveryinPosBetween(@Param("posId") Long posId, @Param("startDate") Instant startDate, @Param("endDate") Instant endDate, Pageable pageable);
    //Rechercher la liste des delivery d'un certain etat dans un pos entre 02 dates puis page par page
    @Query("SELECT delivery FROM Delivery delivery WHERE delivery.deliveryPos.id=:posId AND delivery.deliveryState=:deliveryState AND (delivery.deliveryDate>=:startDate AND delivery.deliveryDate<=:endDate) ORDER BY delivery.creationDate ASC")
    Optional<List<Delivery>> findAllDeliveryinPoswithStateBetween(@Param("posId") Long posId, @Param("deliveryState") DeliveryState deliveryState,
                                                                  @Param("startDate") Instant startDate,@Param("endDate")  Instant endDate);
    @Query("SELECT delivery FROM Delivery delivery WHERE delivery.deliveryPos.id=:posId AND delivery.deliveryState=:deliveryState AND (delivery.deliveryDate>=:startDate AND delivery.deliveryDate<=:endDate) ORDER BY delivery.creationDate ASC")
    Optional<Page<Delivery>> findPageDeliveryinPoswithStateBetween(@Param("posId") Long posId, @Param("deliveryState") DeliveryState deliveryState,
                                                                   @Param("startDate") Instant startDate, @Param("endDate") Instant endDate, Pageable pageable);
    //Rechercher la liste des delivery dans un pos enregistre par un userbm entre 02 date puis page par page
    @Query("SELECT delivery FROM Delivery delivery WHERE delivery.deliveryPos.id=:posId AND delivery.deliveryUserbm.id=:userbmId AND (delivery.deliveryDate>=:startDate AND delivery.deliveryDate<=:endDate) ORDER BY delivery.creationDate ASC")
    Optional<List<Delivery>> findAllDeliveryinPosforUserbmBetween(@Param("posId") Long posId, @Param("userbmId") Long userbmId,
                                                                  @Param("startDate") Instant startDate, @Param("endDate") Instant endDate);
    @Query("SELECT delivery FROM Delivery delivery WHERE delivery.deliveryPos.id=:posId AND delivery.deliveryUserbm.id=:userbmId AND (delivery.deliveryDate>=:startDate AND delivery.deliveryDate<=:endDate) ORDER BY delivery.creationDate ASC")
    Optional<Page<Delivery>> findPageDeliveryinPosforUserbmBetween(@Param("posId") Long posId, @Param("userbmId") Long userbmId,
                                                                   @Param("startDate") Instant startDate, @Param("endDate") Instant endDate, Pageable pageable);
    //Rechercher la liste des delivery dans un pos enregistre par un userbm et d'un certain etat entre 02 date puis page par page
    @Query("SELECT delivery FROM Delivery delivery WHERE delivery.deliveryPos.id=:posId AND delivery.deliveryUserbm.id=:userbmId AND delivery.deliveryState=:deliveryState AND (delivery.deliveryDate>=:startDate AND delivery.deliveryDate<=:endDate) ORDER BY delivery.creationDate ASC")
    Optional<List<Delivery>> findAllDeliveryinPosforUserbmwithStateBetween(@Param("posId") Long posId, @Param("userbmId") Long userbmId, DeliveryState deliveryState,
                                                                           @Param("startDate") Instant startDate, @Param("endDate") Instant endDate);
    @Query("SELECT delivery FROM Delivery delivery WHERE delivery.deliveryPos.id=:posId AND delivery.deliveryUserbm.id=:userbmId AND delivery.deliveryState=:deliveryState AND (delivery.deliveryDate>=:startDate AND delivery.deliveryDate<=:endDate) ORDER BY delivery.creationDate ASC")
    Optional<Page<Delivery>> findPageDeliveryinPosforUserbmwithStateBetween(@Param("posId") Long posId, @Param("userbmId") Long userbmId, DeliveryState deliveryState,
                                                                            @Param("startDate") Instant startDate, @Param("endDate") Instant endDate, Pageable pageable);

}
