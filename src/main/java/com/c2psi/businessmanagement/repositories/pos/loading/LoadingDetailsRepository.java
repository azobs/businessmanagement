package com.c2psi.businessmanagement.repositories.pos.loading;

import com.c2psi.businessmanagement.models.LoadingDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LoadingDetailsRepository extends JpaRepository<LoadingDetails, Long> {
    //rechercher un loadingDetails par son id
    Optional<LoadingDetails> findLoadingDetailsById(Long loadingDetailsId);
    //rechercher un loadingdetails par l'article et le loading en relation
    @Query("SELECT ld FROM LoadingDetails  ld WHERE ld.ldArticle.id=:artId AND ld.ldLoading.id=:loadingId")
    Optional<LoadingDetails> findLoadingDetailsofArticleinLoading(Long artId, Long loadingId);
    //Rechercher la liste des loadingDetails dans un loading
    @Query("SELECT ld FROM LoadingDetails  ld WHERE  ld.ldLoading.id=:loadingId ORDER BY ld.ldArticle.artName ASC")
    Optional<List<LoadingDetails>> findAllLoadingDetailsofLoading(Long loadingId);
    @Query("SELECT ld FROM LoadingDetails  ld WHERE  ld.ldLoading.id=:loadingId ORDER BY ld.ldArticle.artName ASC")
    Optional<Page<LoadingDetails>> findPageLoadingDetailsofLoading(Long loadingId, Pageable pageable);

}
