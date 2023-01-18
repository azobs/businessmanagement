package com.c2psi.businessmanagement.services.contracts.client.client;

import com.c2psi.businessmanagement.dtos.client.client.ClientCapsuleAccountDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientPackagingAccountDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import com.c2psi.businessmanagement.dtos.stock.product.PackagingDto;

import java.util.List;

public interface ClientPackagingAccountService {
    ClientPackagingAccountDto saveClientPackagingAccount(ClientPackagingAccountDto cltpaccDto);

    ClientPackagingAccountDto findClientPackagingAccountById(Long id);

    ClientPackagingAccountDto findByPosClientPackaging(
            PointofsaleDto posDto, ClientDto clientDto, PackagingDto packagingDto);

    List<ClientPackagingAccountDto> findAllPackagingAccountClientPos(
            ClientDto clientDto, PointofsaleDto posDto);

    List<ClientPackagingAccountDto> findAllPackagingAccountPos(
            PackagingDto packagingDto, PointofsaleDto posDto);

    Boolean deleteClientPackagingAccountById(Long id);

    Boolean savePackagingDeposit(
            ClientPackagingAccountDto cltpaDto, Integer number,
            UserBMDto userbmDto, ClientDto clientDto);

    Boolean savePackagingWithdrawal(
            ClientPackagingAccountDto cltpaDto, Integer number,
                                 UserBMDto userbmDto, ClientDto clientDto);


}
