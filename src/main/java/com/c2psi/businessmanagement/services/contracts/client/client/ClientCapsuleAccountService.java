package com.c2psi.businessmanagement.services.contracts.client.client;

import com.c2psi.businessmanagement.dtos.client.client.ClientCapsuleAccountDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.dtos.client.command.SaleInvoiceCashDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;

import java.util.List;

public interface ClientCapsuleAccountService {
    /******************************************************************************************
     * Cette methode permettra d'enregistrer/Modifier un compte capsule lie a un client
     * d'un point de vente. Et en tant que compte capsule il est forcement lie a un article.
     * @param cltcsaccDto
     * @return
     * Elle retourne en sortie le compte capsule enregistre/modifie
     */
    ClientCapsuleAccountDto saveClientCapsuleAccount(ClientCapsuleAccountDto cltcsaccDto);

    /****************************************************************************************
     * Cette methode permettra de rechercher un compte capsule client connaissant son
     * identifiant dans la base de donnée
     * @param id
     * @return
     * Elle va retourner le compte capsule client retrouve
     */
    ClientCapsuleAccountDto findClientCapsuleAccountById(Long id);

    /****************************************************************************************
     * Cette methode permettra de rechercher le compte capsule lie a un client et un article
     * dans le point de vente precise
     * @param posDto
     * @param clientDto
     * @param artDto
     * @return
     * Elle va retourner le compte capsule retrouve
     */
    ClientCapsuleAccountDto findClientCapsuleAccountByPosClientArticle(
            PointofsaleDto posDto, ClientDto clientDto, ArticleDto artDto);

    /****************************************************************************************
     * Cette methode va retourner la liste des comptes capsules d'un client dans
     * un point de vente. En effet un client a autant de compte capsule que d'article dont
     * les capsules sont a considerer.
     * @param clientDto
     * @param posDto
     * @return
     */
    List<ClientCapsuleAccountDto> findAllCapsuleAccountClientPos(
            ClientDto clientDto, PointofsaleDto posDto);

    /****************************************************************************************
     * Cette methode va rechercher tous les compte capsule lie aux articles
     * d'un point de vente relatif à tous les clients du point de vente
     * @param artDto
     * @param posDto
     * @return
     */
    List<ClientCapsuleAccountDto> findAllCapsuleAccountArticlePos(
            ArticleDto artDto, PointofsaleDto posDto);

    /**************************************************************************************
     * Cette methode va permettre de supprimer un point de vente dont l'id est precise
     * @param cltcsa_id
     */
    Boolean deleteById(Long cltcsa_id);

    Boolean saveCapsuleDeposit(ClientCapsuleAccountDto cltcsaDto, Integer number,
                                UserBMDto userbmDto, ClientDto clientDto);
    Boolean saveCapsuleDeposit(ClientCapsuleAccountDto cltcsaDto, Integer number,
                               UserBMDto userbmDto, ClientDto clientDto,
                               SaleInvoiceCashDto saleicashDto);
    Boolean saveCapsuleWithdrawal(ClientCapsuleAccountDto cltcsaDto, Integer number,
                               UserBMDto userbmDto, ClientDto clientDto);


}
