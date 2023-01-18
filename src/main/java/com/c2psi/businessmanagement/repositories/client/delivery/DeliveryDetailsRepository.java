package com.c2psi.businessmanagement.repositories.client.delivery;

import com.c2psi.businessmanagement.models.DeliveryDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryDetailsRepository
        extends JpaRepository<DeliveryDetails, Long> {
}
