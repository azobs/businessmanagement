package com.c2psi.businessmanagement.repositories.stock.product;

import com.c2psi.businessmanagement.models.Format;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface FormatRepository extends JpaRepository<Format, Long> {
    Optional<Format> findFormatById(Long formatId);
    @Query("SELECT f FROM Format f WHERE f.formatPosId=:posId ")
    Optional<List<Format>> findAllFormatInPos(@Param("posId") Long posId);
    @Query("SELECT f FROM Format f WHERE f.formatPosId=:posId ")
    Optional<Page<Format>> findPageofFormatInPos(@Param("posId")Long posId, Pageable pageable);
    @Query("SELECT f FROM Format f WHERE f.formatName=:formatName AND f.formatCapacity=:formatCapacity AND f.formatPosId=:posId")
    Optional<Format> findFormatInPointofsaleByFullcharacteristic(@Param("formatName") String formatName,
                                                                 @Param("formatCapacity") BigDecimal formatCapacity,
                                                                 @Param("posId") Long posId);
}
