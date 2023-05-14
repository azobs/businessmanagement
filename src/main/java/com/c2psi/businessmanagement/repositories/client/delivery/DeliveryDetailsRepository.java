package com.c2psi.businessmanagement.repositories.client.delivery;

import com.c2psi.businessmanagement.models.DeliveryDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DeliveryDetailsRepository
        extends JpaRepository<DeliveryDetails, Long> {
    //Rechercher un detailsdelivery by id
    Optional<DeliveryDetails> findDeliveryDetailsById(Long deliveryDetailsId);
    //Rechercher un detailsdelivery pour un package dans un delivery
    @Query("SELECT deliveryDetails FROM DeliveryDetails deliveryDetails WHERE deliveryDetails.ddPackaging.id=:packagingId AND deliveryDetails.ddDelivery.id=:deliveryId")
    Optional<DeliveryDetails> findDeliveryDetailsByPackagingAndDelivery(Long packagingId, Long deliveryId);
    //Rechercher la liste des detailsdelivery pour un delivery donnee puis page par page
    @Query("SELECT deliveryDetails FROM DeliveryDetails deliveryDetails WHERE deliveryDetails.ddDelivery.id=:deliveryId ORDER BY deliveryDetails.ddPackaging.packProvider.providerName")
    Optional<List<DeliveryDetails>> findAllDeliveryDetailsinDelivery(Long deliveryId);
    @Query("SELECT deliveryDetails FROM DeliveryDetails deliveryDetails WHERE deliveryDetails.ddDelivery.id=:deliveryId ORDER BY deliveryDetails.ddPackaging.packProvider.providerName")
    Optional<Page<DeliveryDetails>> findPageDeliveryDetailsinDelivery(Long deliveryId, Pageable pageable);
}
