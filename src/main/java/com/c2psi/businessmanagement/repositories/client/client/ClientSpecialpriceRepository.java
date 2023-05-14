package com.c2psi.businessmanagement.repositories.client.client;

import com.c2psi.businessmanagement.models.ClientSpecialprice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClientSpecialpriceRepository
        extends JpaRepository<ClientSpecialprice, Long> {
    //Rechercher un prix special a partir de son id
    Optional<ClientSpecialprice> findClientSpecialpriceById(Long cltSpePriceId);
    //Retrouver le prix special associe a un client pour un article donne
    @Query("SELECT cltspeprice FROM ClientSpecialprice cltspeprice WHERE cltspeprice.cltSpClient.id=:clientId AND cltspeprice.cltSpArt.id=:articleId")
    Optional<ClientSpecialprice> findClientSpecialpriceofArticleforClient(Long clientId, Long articleId);
    //Faire la liste des prix speciaux d'un article puis page par page
    @Query("SELECT cltspeprice FROM ClientSpecialprice cltspeprice WHERE cltspeprice.cltSpArt.id=:articleId ORDER BY cltspeprice.cltSpClient.clientName")
    Optional<List<ClientSpecialprice>> findAllSpecialpriceofArticle(Long articleId);
    @Query("SELECT cltspeprice FROM ClientSpecialprice cltspeprice WHERE cltspeprice.cltSpArt.id=:articleId ORDER BY cltspeprice.cltSpClient.clientName")
    Optional<Page<ClientSpecialprice>> findPageSpecialpriceofArticle(Long articleId, Pageable pageable);
    //Faire la liste des prix speciaux associe a un client puis page par page
    @Query("SELECT cltspeprice FROM ClientSpecialprice cltspeprice WHERE cltspeprice.cltSpClient.id=:clientId ORDER BY cltspeprice.cltSpArt.artName")
    Optional<List<ClientSpecialprice>> findAllSpecialpriceofClient(Long clientId);
    @Query("SELECT cltspeprice FROM ClientSpecialprice cltspeprice WHERE cltspeprice.cltSpClient.id=:clientId ORDER BY cltspeprice.cltSpArt.artName")
    Optional<Page<ClientSpecialprice>> findPageSpecialpriceofClient(Long clientId, Pageable pageable);
    @Query("SELECT cltspeprice FROM ClientSpecialprice cltspeprice WHERE cltspeprice.cltSpSp.id=:spepriceId")
    Optional<List<ClientSpecialprice>> findAllClientSpecialpriceofSpecialprice(Long spepriceId);


}
