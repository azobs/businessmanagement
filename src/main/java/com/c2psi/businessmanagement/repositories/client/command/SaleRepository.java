package com.c2psi.businessmanagement.repositories.client.command;

import com.c2psi.businessmanagement.Enumerations.SaleType;
import com.c2psi.businessmanagement.models.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    //Rechercher une Sale a partir de son id
    Optional<Sale> findSaleById(Long saleId);
    //Rechercher un Sale a partir de la commande (cmdId) et de l'article (artId)
    @Query("SELECT sale FROM Sale sale WHERE sale.saleCommand.id=:cmdId AND sale.saleArticle.id=:artId")
    Optional<Sale> findSaleByCommandAndArticle(@Param("cmdId") Long cmdId, @Param("artId") Long artId);
    //Rechercher la liste des Sale d'une commande connaissant l'Id de la command puis page par page
    @Query("SELECT sale FROM Sale sale WHERE sale.saleCommand.id=:cmdId ORDER BY sale.saleArticle.artName ASC")
    Optional<List<Sale>> findAllSaleofCommand(@Param("cmdId") Long cmdId);
    @Query("SELECT sale FROM Sale sale WHERE sale.saleCommand.id=:cmdId ORDER BY sale.saleArticle.artName ASC")
    Optional<Page<Sale>> findPageSaleofCommand(@Param("cmdId") Long cmdId, Pageable pageable);
    //Rechercher la liste des Sale concernant un article
    @Query("SELECT sale FROM Sale sale WHERE sale.saleArticle.id=:artId ORDER BY sale.saleArticle.artName ASC")
    Optional<List<Sale>> findAllSaleonArticle(@Param("artId") Long artId);

    //Rechercher la liste des Sale concernant un article dans un intervalle de date
    @Query("SELECT sale FROM Sale sale WHERE sale.saleArticle.id=:artId AND (sale.saleCommand.cmdDate>=:startDate AND sale.saleCommand.cmdDate<=:endDate) ORDER BY sale.saleArticle.artName ASC")
    Optional<List<Sale>> findAllSaleonArticleBetween(@Param("artId") Long artId, @Param("startDate") Instant startDate,
                                              @Param("endDate") Instant endDate);

    @Query("SELECT sale FROM Sale sale WHERE sale.saleArticle.id=:artId AND (sale.saleCommand.cmdDate>=:startDate AND sale.saleCommand.cmdDate<=:endDate) ORDER BY sale.saleArticle.artName ASC")
    Optional<Page<Sale>> findPageSaleonArticleBetween(@Param("artId") Long artId, @Param("startDate") Instant startDate,
                                                     @Param("endDate") Instant endDate, Pageable pageable);

    @Query("SELECT sale FROM Sale sale WHERE sale.saleArticle.id=:artId ORDER BY sale.saleArticle.artName ASC")
    Optional<Page<Sale>> findPageSaleonArticle(@Param("artId") Long artId, Pageable pageable);
    //Rechercher la liste des Articles concernant un article et dont la vente a un certain type (si un article a deja ete vendu par permetation,
    // en details, en gros ou en semi-gros)
    @Query("SELECT sale FROM Sale sale WHERE sale.saleArticle.id=:artId AND sale.saleType=:saleType ORDER BY sale.saleArticle.artName ASC")
    Optional<List<Sale>> findAllSaleonArticlewithSaleType(@Param("artId") Long artId, @Param("saleType") Long saleType);
    @Query("SELECT sale FROM Sale sale WHERE sale.saleArticle.id=:artId AND sale.saleType=:saleType ORDER BY sale.saleArticle.artName ASC")
    Optional<Page<Sale>> findPageSaleonArticlewithSaleType(@Param("artId") Long artId, @Param("saleType") SaleType saleType, Pageable pageable);
}
