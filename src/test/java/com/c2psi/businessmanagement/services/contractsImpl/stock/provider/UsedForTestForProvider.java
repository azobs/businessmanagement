package com.c2psi.businessmanagement.services.contractsImpl.stock.provider;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.AddressDto;
import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import com.c2psi.businessmanagement.dtos.stock.product.PackagingDto;
import com.c2psi.businessmanagement.dtos.stock.provider.*;
import com.c2psi.businessmanagement.services.contracts.stock.provider.*;
import com.c2psi.businessmanagement.services.contractsImpl.UsedForTestForAll;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Service
public class UsedForTestForProvider {

    @Autowired
    UsedForTestForAll usedForTestForAll;

    public ProviderCashAccountDto saveProviderCashAccount(int num, ProviderCashAccountService providerCashAccountService){
        Assert.assertNotNull(providerCashAccountService);
        ProviderCashAccountDto providerCashAccountDtoToSave = ProviderCashAccountDto.builder()
                .pcaBalance(BigDecimal.valueOf(4552))
                .build();
        ProviderCashAccountDto providerCashAccountDtoSaved = providerCashAccountService.saveProviderCashAccount(
                providerCashAccountDtoToSave);
        return providerCashAccountDtoSaved;
    }

    public ProviderCapsuleAccountDto saveProviderCapsuleAccount(int solde, ProviderDto providerDtoSaved, ArticleDto articleDtoSaved,
                                                                ProviderCapsuleAccountService providerCapsuleAccountService){
        Assert.assertNotNull(providerCapsuleAccountService);
        ProviderCapsuleAccountDto providerCapsuleAccountDtoToSave = ProviderCapsuleAccountDto.builder()
                .pcsaProviderDto(providerDtoSaved)
                .pcsaArticleDto(articleDtoSaved)
                .pcsaNumber(BigDecimal.valueOf(solde))
                .build();
        ProviderCapsuleAccountDto providerCapsuleAccountDtoSaved = providerCapsuleAccountService.saveProviderCapsuleAccount(
                providerCapsuleAccountDtoToSave);
        return providerCapsuleAccountDtoSaved;
    }

    public ProviderDamageAccountDto saveProviderDamageAccount(int solde, ProviderDto providerDtoSaved, ArticleDto articleDtoSaved,
                                                               ProviderDamageAccountService providerDamageAccountService){
        Assert.assertNotNull(providerDamageAccountService);
        ProviderDamageAccountDto providerDamageAccountDtoToSave = ProviderDamageAccountDto.builder()
                .pdaProviderDto(providerDtoSaved)
                .pdaArticleDto(articleDtoSaved)
                .pdaNumber(BigDecimal.valueOf(solde))
                .build();
        ProviderDamageAccountDto providerDamageAccountDtoSaved = providerDamageAccountService.saveProviderDamageAccount(
                providerDamageAccountDtoToSave);
        return providerDamageAccountDtoSaved;
    }

    public ProviderCashAccountDto saveProviderCashAccount_Invalid(int num, ProviderCashAccountService providerCashAccountService){
        Assert.assertNotNull(providerCashAccountService);
        ProviderCashAccountDto providerCashAccountDtoToSave = ProviderCashAccountDto.builder()
                .pcaBalance(null)
                .build();
        ProviderCashAccountDto providerCashAccountDtoSaved = providerCashAccountService.saveProviderCashAccount(
                providerCashAccountDtoToSave);
        return providerCashAccountDtoSaved;
    }

    public ProviderCashAccountDto prepareProviderCashAccount(double solde){
        ProviderCashAccountDto providerCashAccountToSaved = ProviderCashAccountDto.builder()
                .pcaBalance(BigDecimal.valueOf(solde))
                .build();
        //PosCashAccountDto posCashAccountSaved = posCashAccountService.savePosCashAccount(posCashAccountToSaved);
        return providerCashAccountToSaved;
    }

    public ProviderDto saveProvider(int num, PointofsaleDto pointofsaleDtoSaved, ProviderService providerService){
        assertNotNull(providerService);
        assertNotNull(pointofsaleDtoSaved);

        AddressDto providerAddress = usedForTestForAll.getAddressDto(num+31);
        ProviderCashAccountDto pcaDto = prepareProviderCashAccount(0);
        ProviderDto providerDtoToSave = ProviderDto.builder()
                .providerAcronym("FSD"+num)
                .providerCaDto(pcaDto)
                .providerAddressDto(providerAddress)
                .providerDescription("Description of FSD "+num)
                .providerName("First Step"+num)
                .providerPosDto(pointofsaleDtoSaved)
                .build();

        ProviderDto providerDtoSaved = providerService.saveProvider(providerDtoToSave);
        return providerDtoSaved;
    }

    public ProviderDto saveProvider_Invalid(int num, PointofsaleDto pointofsaleDtoSaved, ProviderService providerService){
        assertNotNull(providerService);
        assertNotNull(pointofsaleDtoSaved);

        AddressDto providerAddress = usedForTestForAll.getAddressDto(num+78);
        ProviderCashAccountDto pcaDto = prepareProviderCashAccount(0);
        ProviderDto providerDtoToSave = ProviderDto.builder()
                .providerAcronym("FSD"+num)
                .providerCaDto(null)
                .providerAddressDto(providerAddress)
                .providerDescription("Description of FSD "+num)
                .providerName("First Step"+num)
                .providerPosDto(pointofsaleDtoSaved)
                .build();

        ProviderDto providerDtoSaved = providerService.saveProvider(providerDtoToSave);
        return providerDtoSaved;
    }

    public ProviderPackagingAccountDto saveProviderPackagingAccount(int solde, ProviderDto providerDtoSaved, PackagingDto packagingDtoSaved,
                                                                 ProviderPackagingAccountService providerPackagingAccountService){
        Assert.assertNotNull(providerPackagingAccountService);
        ProviderPackagingAccountDto providerPackagingAccountDtoToSave = ProviderPackagingAccountDto.builder()
                .ppaProviderDto(providerDtoSaved)
                .ppaPackagingDto(packagingDtoSaved)
                .ppaNumber(BigDecimal.valueOf(solde))
                .build();
        ProviderPackagingAccountDto providerPackagingAccountDtoSaved = providerPackagingAccountService.saveProviderPackagingAccount(
                providerPackagingAccountDtoToSave);
        return providerPackagingAccountDtoSaved;
    }

    public ProviderPackagingAccountDto saveProviderPackagingAccount_Invalid(int solde, ProviderDto providerDtoSaved, PackagingDto packagingDtoSaved,
                                                                    ProviderPackagingAccountService providerPackagingAccountService){
        Assert.assertNotNull(providerPackagingAccountService);
        ProviderPackagingAccountDto providerPackagingAccountDtoToSave = ProviderPackagingAccountDto.builder()
                .ppaProviderDto(providerDtoSaved)
                .ppaPackagingDto(packagingDtoSaved)
                .ppaNumber(null)
                .build();
        ProviderPackagingAccountDto providerPackagingAccountDtoSaved = providerPackagingAccountService.saveProviderPackagingAccount(
                providerPackagingAccountDtoToSave);
        return providerPackagingAccountDtoSaved;
    }


}
