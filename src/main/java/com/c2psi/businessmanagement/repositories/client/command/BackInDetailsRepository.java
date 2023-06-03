package com.c2psi.businessmanagement.repositories.client.command;

import com.c2psi.businessmanagement.models.BackInDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BackInDetailsRepository extends JpaRepository<BackInDetails, Long> {
    //Rechercher un backInDetails a partir de son Id
    Optional<BackInDetails> findBackInDetailsById(Long backInDetailsId);
    //Rechercher le backInDetails associe a un article et un BackIn
    @Query("SELECT backinDetails FROM BackInDetails backinDetails WHERE backinDetails.bidArticle.id=:artId AND backinDetails.bidbi.id=:backInId")
    Optional<BackInDetails> findBackInDetailsofArticleinBackIn(@Param("artId") Long artId, @Param("backInId") Long backInId);
    //Rechercher la liste des backInDetails associe a un BackIn puis page par page
    @Query("SELECT backinDetails FROM BackInDetails backinDetails WHERE backinDetails.bidbi.id=:backInId ORDER BY backinDetails.bidArticle.artName")
    Optional<List<BackInDetails>> findAllBackInDetailsofBackIn(@Param("backInId") Long backInId);
    @Query("SELECT backinDetails FROM BackInDetails backinDetails WHERE backinDetails.bidbi.id=:backInId ORDER BY backinDetails.bidArticle.artName")
    Optional<Page<BackInDetails>> findPageBackInDetailsofBackIn(@Param("backInId") Long backInId, Pageable pageable);
}
