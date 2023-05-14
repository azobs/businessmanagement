package com.c2psi.businessmanagement.repositories.stock.product;

import com.c2psi.businessmanagement.models.InventoryLine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InventoryLineRepository extends JpaRepository<InventoryLine, Long> {

    //retourner une ligne d'inventaire selon son id
    Optional<InventoryLine> findInventoryLineById(Long invLineId);
    //retourner la ligne d'inventaire d'un article dans un inventaire donnee
    @Query("SELECT invline FROM InventoryLine invline WHERE invline.invlineArt.id=:articleId AND invline.invlineInv.id=:invId")
    Optional<InventoryLine> findInventoryLineByArticleinInv(@Param("articleId") Long articleId, @Param("invId") Long invId);
    //retourner la liste des lignes d'inventaire d'un inventaire puis page par page par ordre alphabetique croissant
    //sur le nom des articles
    @Query("SELECT invline FROM InventoryLine invline WHERE invline.invlineInv.id=:invId ORDER BY invline.invlineArt.artName ASC ")
    Optional<List<InventoryLine>> findAllInventorylineofInv(@Param("invId") Long invId);
    @Query("SELECT invline FROM InventoryLine invline WHERE invline.invlineInv.id=:invId ORDER BY invline.invlineArt.artName ASC ")
    Optional<Page<InventoryLine>> findPageInventorylineofInv(@Param("invId") Long invId, Pageable pageable);


}
