package com.c2psi.businessmanagement.services.contracts.client.client;

import com.c2psi.businessmanagement.dtos.client.client.ClientDamageAccountDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;

import java.util.List;

public interface ClientDamageAccountService {
    ClientDamageAccountDto saveClientDamageAccount(ClientDamageAccountDto cltdaccDto);
    ClientDamageAccountDto findClientDamageAccountById(Long id);
    ClientDamageAccountDto findByPosClientArticle(
            PointofsaleDto posDto, ClientDto clientDto, ArticleDto artDto);
    List<ClientDamageAccountDto> findAllDamageAccountClientPos(
            ClientDto clientDto, PointofsaleDto posDto);
    List<ClientDamageAccountDto> findAllDamageAccountArticlePos(
            ArticleDto artDto, PointofsaleDto posDto);
    Boolean deleteClientDamageAccountById(Long cltcsaId);

    Boolean saveDamageDeposit(ClientDamageAccountDto cltdaDto, Integer number,
                                UserBMDto userbmDto, ClientDto clientDto);

    Boolean saveDamageWithdrawal(ClientDamageAccountDto cltdaDto, Integer number,
                              UserBMDto userbmDto, ClientDto clientDto);


}
