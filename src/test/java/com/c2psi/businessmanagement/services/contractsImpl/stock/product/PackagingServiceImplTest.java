package com.c2psi.businessmanagement.services.contractsImpl.stock.product;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.product.PackagingDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;
import com.c2psi.businessmanagement.exceptions.DuplicateEntityException;
import com.c2psi.businessmanagement.exceptions.InvalidEntityException;
import com.c2psi.businessmanagement.services.contractsImpl.UsedForTestForAll;
import com.c2psi.businessmanagement.services.contractsImpl.pos.pos.EnterpriseServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.pos.pos.PointofsaleServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.pos.pos.PosCashAccountServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.pos.userbm.UserBMServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.price.CurrencyConversionServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.price.CurrencyServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.provider.ProviderServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.provider.UsedForTestForProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusinessmanagementApplication.class)
public class PackagingServiceImplTest {
    @Autowired
    EnterpriseServiceImpl enterpriseService;
    @Autowired
    PointofsaleServiceImpl pointofsaleService;
    @Autowired
    UserBMServiceImpl userBMService;
    @Autowired
    PosCashAccountServiceImpl posCashAccountService;
    @Autowired
    CurrencyServiceImpl currencyService;
    @Autowired
    CurrencyConversionServiceImpl currencyConversionService;
    @Autowired
    UsedForTestForAll usedForTestForAll;
    @Autowired
    ProviderServiceImpl providerService;
    @Autowired
    PackagingServiceImpl packagingService;
    @Autowired
    UsedForTestForProvider usedForTestForProvider;
    @Autowired
    UsedForTestForProduct usedForTestForProduct;


    @Test
    public void validate_SavePackaging() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        PackagingDto packagingDtoSaved = usedForTestForProduct.savePackaging(0, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved);
        assertNotNull(packagingDtoSaved.getId());
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SavePackaging_Invalid() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        PackagingDto packagingDtoSaved = usedForTestForProduct.savePackaging_Invalid(0, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved);
        assertNotNull(packagingDtoSaved.getId());
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SavePackaging_InvalidPointofsale() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);


        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        posDtoSaved.setId(Long.valueOf(1425578));

        PackagingDto packagingDtoSaved = usedForTestForProduct.savePackaging(0, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved);
        assertNotNull(packagingDtoSaved.getId());
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SavePackaging_InvalidProvider() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        providerDtoSaved.setId(Long.valueOf(1458279));


        PackagingDto packagingDtoSaved = usedForTestForProduct.savePackaging(0, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved);
        assertNotNull(packagingDtoSaved.getId());
    }

    @Test(expected = DuplicateEntityException.class)
    public void validate_SavePackaging_Duplicated() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        PackagingDto packagingDtoSaved = usedForTestForProduct.savePackaging(0, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved);
        assertNotNull(packagingDtoSaved.getId());

        PackagingDto packagingDtoSaved1 = usedForTestForProduct.savePackaging(0, providerDtoSaved, posDtoSaved,
                packagingService);
    }

    @Test
    public void validate_UpdatePackaging() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        PackagingDto packagingDtoSaved = usedForTestForProduct.savePackaging(0, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved);
        assertNotNull(packagingDtoSaved.getId());

        packagingDtoSaved.setPackPrice(BigDecimal.valueOf(1200));
        packagingDtoSaved.setPackLabel("Le casier");

        PackagingDto packagingDtoUpdated = packagingService.updatePackaging(packagingDtoSaved);
        assertNotNull(packagingDtoUpdated);
        assertEquals(packagingDtoSaved.getPackDescription(), packagingDtoUpdated.getPackDescription());
        assertEquals("Le casier", packagingDtoUpdated.getPackLabel());

    }

    @Test(expected = DuplicateEntityException.class)
    public void validate_UpdatePackaging_Duplicata() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        PackagingDto packagingDtoSaved = usedForTestForProduct.savePackaging(0, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved);
        assertNotNull(packagingDtoSaved.getId());

        PackagingDto packagingDtoSaved1 = usedForTestForProduct.savePackaging(1, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved1);
        assertNotNull(packagingDtoSaved1.getId());

        packagingDtoSaved.setPackPrice(BigDecimal.valueOf(1200));
        packagingDtoSaved.setPackLabel("Le casier");
        packagingDtoSaved.setPackFirstcolor("bleu");


        PackagingDto packagingDtoUpdated = packagingService.updatePackaging(packagingDtoSaved);
        assertNotNull(packagingDtoUpdated);
        assertEquals(packagingDtoSaved.getPackDescription(), packagingDtoUpdated.getPackDescription());
        assertEquals("Le casier", packagingDtoUpdated.getPackLabel());


        packagingDtoSaved1.setPackPrice(BigDecimal.valueOf(1200));
        packagingDtoSaved1.setPackLabel("Le casier");
        packagingDtoSaved1.setPackFirstcolor("bleu");
        PackagingDto packagingDtoUpdated1 = packagingService.updatePackaging(packagingDtoSaved1);


    }



}