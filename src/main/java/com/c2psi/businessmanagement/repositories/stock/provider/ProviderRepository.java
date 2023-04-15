package com.c2psi.businessmanagement.repositories.stock.provider;

import com.c2psi.businessmanagement.models.Pointofsale;
import com.c2psi.businessmanagement.models.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProviderRepository extends JpaRepository<Provider, Long> {
    List<Provider> findAllByProviderPos(Pointofsale pos);
    Optional<Provider> findProviderById(Long providerId);
    @Query("SELECT prov FROM Provider prov WHERE prov.providerName=:providerName AND prov.providerPos.id=:posId")
    Optional<Provider> findProviderByNameAndPosid(@Param("providerName") String providerName, @Param("posId") Long posId);

    @Query("SELECT prov FROM Provider  prov WHERE prov.providerAddress.email=:providerEmail")
    Optional<Provider> findProviderByProviderEmail(@Param("providerEmail") String providerEmail);
}
