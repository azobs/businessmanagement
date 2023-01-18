package com.c2psi.businessmanagement.repositories.stock.provider;

import com.c2psi.businessmanagement.models.Pointofsale;
import com.c2psi.businessmanagement.models.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProviderRepository extends JpaRepository<Provider, Long> {
    List<Provider> findAllByProviderPos(Pointofsale pos);
}
