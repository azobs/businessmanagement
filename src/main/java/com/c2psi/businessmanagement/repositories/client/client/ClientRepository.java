package com.c2psi.businessmanagement.repositories.client.client;

import com.c2psi.businessmanagement.models.Client;
import com.c2psi.businessmanagement.models.Pointofsale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findClientById(Long clientId);
    @Query("SELECT client FROM Client client WHERE client.clientName=:clientName AND client.clientOthername=:clientOthername AND client.clientPosId=:posId")
    Optional<Client> findClientByNameAndPos(@Param("clientName") String clientName,
                                            @Param("clientOthername") String clientOthername,
                                            @Param("posId") Long posId);
    @Query("SELECT client FROM Client client WHERE client.clientCni=:clientCni AND client.clientPosId=:posId")
    Optional<Client> findClientByCniAndPos(@Param("clientCni") String clientCni, @Param("posId") Long posId);
    @Query("SELECT client FROM Client client WHERE client.clientAddress.email=:clientEmail")
    Optional<Client> findClientByClientEmail(@Param("clientEmail") String clientEmail);
    @Query("SELECT client FROM Client client WHERE client.clientPosId=:posId")
    Optional<List<Client>> findAllClientofPos(Long posId);
    @Query("SELECT client FROM Client client WHERE client.clientPosId=:posId")
    Optional<Page<Client>> findPageClientofPos(Long posId, Pageable pageable);
}
