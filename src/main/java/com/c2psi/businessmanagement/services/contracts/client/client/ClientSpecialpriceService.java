package com.c2psi.businessmanagement.services.contracts.client.client;

import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientSpecialpriceDto;
import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import com.c2psi.businessmanagement.models.ClientSpecialprice;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface ClientSpecialpriceService {
    //Enregistrer un prix special d'un article pour un client
    ClientSpecialpriceDto saveClientSpecialprice(ClientSpecialpriceDto cltspepriceDto);
    //Update un prix special d'un article pour un client
    ClientSpecialpriceDto updateClientSpecialprice(ClientSpecialpriceDto cltspepriceDto);
    //Rechercher un prix special a partir de son id
    ClientSpecialpriceDto findClientSpecialpriceById(Long cltspepriceId);
    Boolean isClientSpecialpriceUnique(Long articleId, Long clientId);
    //Retrouver le prix special associe a un client pour un article donne
    ClientSpecialpriceDto findClientSpecialpriceofArticleforClient(Long articleId, Long clientId);
    //Faire la liste des prix speciaux d'un article puis page par page
    List<ClientSpecialpriceDto> findAllSpecialpriceofArticle(Long articleId);
    Page<ClientSpecialpriceDto> findPageSpecialpriceofArticle(Long articleId, int pagenum, int pagesize);
    //Faire la liste des prix speciaux associe a un client puis page par page
    List<ClientSpecialpriceDto> findAllSpecialpriceofClient(Long clientId);
    Page<ClientSpecialpriceDto> findPageSpecialpriceofClient(Long clientId, int pagenum, int pagesize);
    //Faire la liste de tous les clients qui sont soumis a un prix special particulier
    List<ClientDto> findAllClientAssociateWithSpecialprice(Long spepriceId);
    Page<ClientDto> findAllClientAssociateWithSpecialprice(Long spepriceId, int pagenum, int pagesize);
    //Faire la liste de tous les articles qui sont soumis a un prix special particulier
    List<ArticleDto> findAllArticleAssociateWithSpecialprice(Long spepriceId);
    Page<ArticleDto> findAllArticleAssociateWithSpecialprice(Long spepriceId, int pagenum, int pagesize);
    //Calculer le prix unitaire de facturation d'un article pour un client donne selon la quantite commadee
    BigDecimal getEffectivePriceToApplied(BigDecimal qteCommand, Long articleId, Long clientId);
    //Regarder si un prix special est supprimable?
    Boolean isClientSpecialpriceDeleteable(Long cltspepriceId);
    //Supprimer un prix special enregistrer a partir de son Id
    Boolean deleteClientSpecialprice(Long cltspepriceId);
}
