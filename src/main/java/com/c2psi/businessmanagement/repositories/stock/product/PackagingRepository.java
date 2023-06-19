package com.c2psi.businessmanagement.repositories.stock.product;

import com.c2psi.businessmanagement.models.Packaging;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PackagingRepository extends JpaRepository<Packaging, Long> {
    Optional<Packaging> findPackagingById(Long packId);
    @Query("SELECT pack FROM Packaging pack WHERE pack.packPosId=:posId AND pack.packProvider.id=:providerId AND pack.packFirstcolor=:packFirstcolor AND pack.packLabel=:packLabel")
    Optional<Packaging> findPackagingByAttribites(@Param("packLabel") String packLabel,
                                                  @Param("packFirstcolor") String packFirstcolor,
                                                  @Param("providerId") Long providerId, @Param("posId") Long posId);

    @Query("SELECT pack FROM Packaging pack WHERE pack.packPosId=:posId")
    Optional<List<Packaging>> findAllPackagingofPos(@Param("posId") Long posId);

    @Query("SELECT pack FROM Packaging pack WHERE pack.packPosId=:posId")
    Optional<Page<Packaging>> findPagePackagingofPos(@Param("posId") Long posId, Pageable pageable);

    @Query("SELECT pack FROM Packaging pack WHERE pack.packProvider.id=:providerId AND pack.packPosId=:posId")
    Optional<List<Packaging>> findAllPackagingofProviderinPos(@Param("providerId") Long providerId,
                                                              @Param("posId") Long posId);

    @Query("SELECT pack FROM Packaging pack WHERE pack.packProvider.id=:providerId AND pack.packPosId=:posId")
    Optional<Page<Packaging>> findPagePackagingofProviderinPos(@Param("providerId") Long providerId,
                                                               @Param("posId") Long posId,
                                                               Pageable pageable);

}
