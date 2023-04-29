package com.c2psi.businessmanagement.services.contractsImpl.client;

import com.c2psi.businessmanagement.dtos.client.client.*;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.AddressDto;
import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import com.c2psi.businessmanagement.dtos.stock.product.PackagingDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCapsuleAccountDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCashAccountDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderPackagingAccountDto;
import com.c2psi.businessmanagement.services.contracts.client.client.*;
import com.c2psi.businessmanagement.services.contracts.stock.provider.ProviderCapsuleAccountService;
import com.c2psi.businessmanagement.services.contracts.stock.provider.ProviderCashAccountService;
import com.c2psi.businessmanagement.services.contracts.stock.provider.ProviderPackagingAccountService;
import com.c2psi.businessmanagement.services.contracts.stock.provider.ProviderService;
import com.c2psi.businessmanagement.services.contractsImpl.UsedForTestForAll;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Service
public class UsedForTestForClient {
    @Autowired
    UsedForTestForAll usedForTestForAll;

    public ClientCashAccountDto saveClientCashAccount(int solde, ClientCashAccountService clientCashAccountService){
        Assert.assertNotNull(clientCashAccountService);
        ClientCashAccountDto clientCashAccountDtoToSave = ClientCashAccountDto.builder()
                .ccaBalance(BigDecimal.valueOf(solde))
                .build();
        ClientCashAccountDto clientCashAccountDtoSaved = clientCashAccountService.saveClientCashAccount(
                clientCashAccountDtoToSave);
        return clientCashAccountDtoSaved;
    }

    public ClientCashAccountDto saveClientCashAccount_Invalid(int num, ClientCashAccountService clientCashAccountService){
        Assert.assertNotNull(clientCashAccountService);
        ClientCashAccountDto clientCashAccountDtoToSave = ClientCashAccountDto.builder()
                .ccaBalance(null)
                .build();
        ClientCashAccountDto clientCashAccountDtoSaved = clientCashAccountService.saveClientCashAccount(
                clientCashAccountDtoToSave);
        return clientCashAccountDtoSaved;
    }

    public ClientCashAccountDto prepareClientCashAccount(double solde){
        ClientCashAccountDto clientCashAccountToSaved = ClientCashAccountDto.builder()
                .ccaBalance(BigDecimal.valueOf(solde))
                .build();
        return clientCashAccountToSaved;
    }

    public ClientDto saveClient(int num, PointofsaleDto pointofsaleDtoSaved, ClientService clientService){
        assertNotNull(clientService);
        assertNotNull(pointofsaleDtoSaved);

        AddressDto clientAddress = usedForTestForAll.getAddressDto(num+40);
        ClientCashAccountDto ccaDto = prepareClientCashAccount(0);
        ClientDto clientDtoToSave = ClientDto.builder()
                .clientCni("107235260_"+num)
                .clientName("Cedric_"+num)
                .clientOthername("Amour_"+num)
                .clientCaDto(ccaDto)
                .clientAddressDto(clientAddress)
                .clientPosDto(pointofsaleDtoSaved)
                .build();

       ClientDto clientDtoSaved = clientService.saveClient(clientDtoToSave);
       assertNotNull(clientDtoSaved);
       return clientDtoSaved;
    }

    public ClientDto saveClient_Invalid(int num, PointofsaleDto pointofsaleDtoSaved, ClientService clientService){
        assertNotNull(clientService);
        assertNotNull(pointofsaleDtoSaved);

        AddressDto clientAddress = usedForTestForAll.getAddressDto(num+40);
        ClientCashAccountDto ccaDto = prepareClientCashAccount(0);
        ClientDto clientDtoToSave = ClientDto.builder()
                .clientCni("107235260_"+num)
                .clientName("Cedric_"+num)
                .clientOthername("Amour_"+num)
                .clientCaDto(ccaDto)
                .clientAddressDto(null)
                .clientPosDto(pointofsaleDtoSaved)
                .build();

        ClientDto clientDtoSaved = clientService.saveClient(clientDtoToSave);
        assertNotNull(clientDtoSaved);
        return clientDtoSaved;
    }

    public ClientCapsuleAccountDto saveClientCapsuleAccount(int solde, ClientDto clientDtoSaved, ArticleDto articleDtoSaved,
                                                            ClientCapsuleAccountService clientCapsuleAccountService){
        Assert.assertNotNull(clientCapsuleAccountService);
        ClientCapsuleAccountDto clientCapsuleAccountDtoToSave = ClientCapsuleAccountDto.builder()
                .ccsaClientDto(clientDtoSaved)
                .ccsaArticleDto(articleDtoSaved)
                .ccsaNumber(BigDecimal.valueOf(solde))
                .build();
        ClientCapsuleAccountDto clientCapsuleAccountDtoSaved = clientCapsuleAccountService.saveClientCapsuleAccount(
                clientCapsuleAccountDtoToSave);
        return clientCapsuleAccountDtoSaved;
    }

    public ClientDamageAccountDto saveClientDamageAccount(int solde, ClientDto clientDtoSaved, ArticleDto articleDtoSaved,
                                                          ClientDamageAccountService clientDamageAccountService){
        Assert.assertNotNull(clientDamageAccountService);
        ClientDamageAccountDto clientDamageAccountDtoToSave = ClientDamageAccountDto.builder()
                .cdaClientDto(clientDtoSaved)
                .cdaArticleDto(articleDtoSaved)
                .cdaNumber(BigDecimal.valueOf(solde))
                .build();
        ClientDamageAccountDto clientDamageAccountDtoSaved = clientDamageAccountService.saveClientDamageAccount(
                clientDamageAccountDtoToSave);
        return clientDamageAccountDtoSaved;
    }

    public ClientPackagingAccountDto saveClientPackagingAccount(int solde, ClientDto clientDtoSaved, PackagingDto packagingDtoSaved,
                                                                ClientPackagingAccountService clientPackagingAccountService){
        Assert.assertNotNull(clientPackagingAccountService);
        ClientPackagingAccountDto clientPackagingAccountDtoToSave = ClientPackagingAccountDto.builder()
                .cpaClientDto(clientDtoSaved)
                .cpaPackagingDto(packagingDtoSaved)
                .cpaNumber(BigDecimal.valueOf(solde))
                .build();
        ClientPackagingAccountDto clientPackagingAccountDtoSaved = clientPackagingAccountService.saveClientPackagingAccount(
                clientPackagingAccountDtoToSave);
        return clientPackagingAccountDtoSaved;
    }

    public ClientPackagingAccountDto saveClientPackagingAccount_Invalid(int solde, ClientDto clientDtoSaved, PackagingDto packagingDtoSaved,
                                                                ClientPackagingAccountService clientPackagingAccountService){
        Assert.assertNotNull(clientPackagingAccountService);
        ClientPackagingAccountDto clientPackagingAccountDtoToSave = ClientPackagingAccountDto.builder()
                .cpaClientDto(clientDtoSaved)
                .cpaPackagingDto(packagingDtoSaved)
                .cpaNumber(null)
                .build();
        ClientPackagingAccountDto clientPackagingAccountDtoSaved = clientPackagingAccountService.saveClientPackagingAccount(
                clientPackagingAccountDtoToSave);
        return clientPackagingAccountDtoSaved;
    }

}
