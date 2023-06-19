package com.c2psi.businessmanagement.repositories.pos.loading;

import com.c2psi.businessmanagement.models.LoadingDetails;
import com.c2psi.businessmanagement.models.PackingDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PackingDetailsRepository extends JpaRepository<PackingDetails, Long> {
    //rechercher un packingDetails par son id
    Optional<PackingDetails> findPackingDetailsById(Long packingDetailsId);
    //rechercher un packingdetails par l'article et le packing en relation
    @Query("SELECT pd FROM PackingDetails  pd WHERE pd.pdPackaging.id=:packagingId AND pd.pdLoading.id=:loadingId")
    Optional<PackingDetails> findPackingDetailsofPackaginginLoading(Long packagingId, Long loadingId);
    //Rechercher la liste des packingDetails dans un loading
    @Query("SELECT pd FROM PackingDetails  pd WHERE  pd.pdLoading.id=:loadingId ORDER BY pd.pdPackaging.packLabel ASC")
    Optional<List<LoadingDetails>> findAllLoadingDetailsofLoading(Long loadingId);
    @Query("SELECT pd FROM PackingDetails  pd WHERE  pd.pdLoading.id=:loadingId ORDER BY pd.pdPackaging.packLabel ASC")
    Optional<Page<LoadingDetails>> findPageLoadingDetailsofLoading(Long loadingId, Pageable pageable);
}
