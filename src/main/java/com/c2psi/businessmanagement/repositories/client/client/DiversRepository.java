package com.c2psi.businessmanagement.repositories.client.client;

import com.c2psi.businessmanagement.models.Divers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DiversRepository extends JpaRepository<Divers, Long> {
    Optional<Divers> findDiversById(Long diversId);
    @Query("SELECT divers FROM Divers divers WHERE divers.diversAddress.email=:diversEmail")
    Optional<Divers> findDiversByEmail(@Param("diversEmail") String diversEmail);
    @Query("SELECT divers FROM Divers divers WHERE divers.diversName=:diversName AND divers.diversPos.id=:posId")
    Optional<List<Divers>> findAllDiversByNameinPos(@Param("diversName") String diversName, @Param("posId") Long posId);
    @Query("SELECT divers FROM Divers divers WHERE divers.diversName=:diversName AND divers.diversPos.id=:posId")
    Optional<Page<Divers>> findPageDiversByNameinPos(@Param("diversName") String diversName, @Param("posId") Long posId,
                                                     Pageable pageable);
    @Query("SELECT divers FROM Divers divers WHERE divers.diversPos.id=:diversId")
    Optional<List<Divers>> findAllDiversofPos(@Param("diversId") Long diversId);
    @Query("SELECT divers FROM Divers divers WHERE divers.diversPos.id=:diversId")
    Optional<Page<Divers>> findPageDiversofPos(@Param("diversId") Long diversId, Pageable pageable);
}
