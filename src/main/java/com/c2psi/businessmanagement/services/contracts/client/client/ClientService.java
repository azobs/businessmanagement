package com.c2psi.businessmanagement.services.contracts.client.client;

import com.c2psi.businessmanagement.dtos.client.client.*;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import com.c2psi.businessmanagement.dtos.stock.product.PackagingDto;

import java.util.List;

public interface ClientService {
    ClientDto saveClient(ClientDto cltDto);
    List<ClientCapsuleAccountDto> findAllCapsuleAccount(ClientDto cltDto);

    List<ClientPackagingAccountDto> findAllPackagingAccount(ClientDto cltDto);

    List<ClientDamageAccountDto> findAllDamageAccount(ClientDto cltDto);

    ClientCashAccountDto findCashAccount(ClientDto cltDto);

    List<ClientDto> findAllClientWithCapsuleAccountofArticle(
            PointofsaleDto posDto, ArticleDto artDto);

    List<ClientDto> findAllClientWithDamageAccountofArticle(
            PointofsaleDto posDto, ArticleDto artDto);

    List<ClientDto> findAllClientWithPackagingAccountofArticle(
            PointofsaleDto posDto, PackagingDto packDto);

    List<ClientDto> findAllClientOfPos(PointofsaleDto posDto);

    Integer getNumberofPackaging(PointofsaleDto posDto);

    Integer getNumberofPackaging(PointofsaleDto posDto, ClientDto cltDto);

    Boolean damageArticleChangedByPos(ArticleDto artDto, Integer qte_needed,
                                 Integer qte_changed);
    Boolean deleteClientById(Long id);

}
