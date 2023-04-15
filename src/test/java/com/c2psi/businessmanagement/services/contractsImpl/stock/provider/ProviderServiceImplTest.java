package com.c2psi.businessmanagement.services.contractsImpl.stock.provider;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;
import com.c2psi.businessmanagement.exceptions.DuplicateEntityException;
import com.c2psi.businessmanagement.exceptions.EntityNotFoundException;
import com.c2psi.businessmanagement.exceptions.InvalidEntityException;
import com.c2psi.businessmanagement.services.contractsImpl.UsedForTestForAll;
import com.c2psi.businessmanagement.services.contractsImpl.pos.pos.EnterpriseServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.pos.pos.PointofsaleServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.pos.pos.PosCashAccountServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.pos.userbm.UserBMServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.price.CurrencyServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusinessmanagementApplication.class)
public class ProviderServiceImplTest {
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
    ProviderServiceImpl providerService;
    @Autowired
    UsedForTestForAll usedForTestForAll;
    @Autowired
    UsedForTestForProvider usedForTestForProvider;

    @Test
    public void validate_SaveProvider() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveProvider_Invalid() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider_Invalid(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveProvider_IdPointofsaleNull() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);
        posDtoSaved.setId(null);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());
    }

    @Test(expected = EntityNotFoundException.class)
    public void validate_SaveProvider_PointofsaleNotFound() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);
        posDtoSaved.setId(Long.valueOf(4578963));

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());
    }

    @Test(expected = DuplicateEntityException.class)
    public void validate_SaveProvider_Duplication() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());
        ProviderDto providerDtoSaved1 = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        //The above line is supposed to launch the expected exception

    }

    @Test
    public void validate_UpdateProvider() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        providerDtoSaved.setProviderName("double FSD");
        providerDtoSaved.getProviderAddressDto().setEmail("new_test@gmail.com");
        providerDtoSaved.getProviderAddressDto().setPays("Congo");

        ProviderDto providerDtoUpdated = providerService.updateProvider(providerDtoSaved);
        assertNotNull(providerDtoUpdated);
        assertEquals("Congo", providerDtoUpdated.getProviderAddressDto().getPays());
        assertEquals("double FSD", providerDtoUpdated.getProviderName());
    }

    @Test(expected = DuplicateEntityException.class)
    public void validate_UpdateProvider_DuplicatedEmail() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        ProviderDto providerDtoSaved1 = usedForTestForProvider.saveProvider(1, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved1);
        assertNotNull(providerDtoSaved1.getProviderCaDto().getId());

        providerDtoSaved.setProviderName("double FSD");
        providerDtoSaved.getProviderAddressDto().setEmail("new_test@gmail.com");
        providerDtoSaved.getProviderAddressDto().setPays("Congo");

        ProviderDto providerDtoUpdated = providerService.updateProvider(providerDtoSaved);
        assertNotNull(providerDtoUpdated);
        assertEquals("Congo", providerDtoUpdated.getProviderAddressDto().getPays());
        assertEquals("double FSD", providerDtoUpdated.getProviderName());

        providerDtoSaved1.getProviderAddressDto().setEmail("new_test@gmail.com");
        ProviderDto providerDtoUpdated1 = providerService.updateProvider(providerDtoSaved1);
        //The above line is supposed to launch the expected exception

    }

    @Test(expected = DuplicateEntityException.class)
    public void validate_UpdateProvider_DuplicatedName() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        ProviderDto providerDtoSaved1 = usedForTestForProvider.saveProvider(1, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved1);
        assertNotNull(providerDtoSaved1.getProviderCaDto().getId());

        providerDtoSaved.setProviderName("double FSD");
        providerDtoSaved.getProviderAddressDto().setEmail("new_test@gmail.com");
        providerDtoSaved.getProviderAddressDto().setPays("Congo");

        ProviderDto providerDtoUpdated = providerService.updateProvider(providerDtoSaved);
        assertNotNull(providerDtoUpdated);
        assertEquals("Congo", providerDtoUpdated.getProviderAddressDto().getPays());
        assertEquals("double FSD", providerDtoUpdated.getProviderName());

        providerDtoSaved1.setProviderName("double FSD");
        ProviderDto providerDtoUpdated1 = providerService.updateProvider(providerDtoSaved1);
        //The above line is supposed to launch the expected exception

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_UpdateProvider_Pos() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        providerDtoSaved.getProviderPosDto().setId(Long.valueOf(4587));

        ProviderDto providerDtoUpdated = providerService.updateProvider(providerDtoSaved);
        assertNotNull(providerDtoUpdated);
        assertEquals("Congo", providerDtoUpdated.getProviderAddressDto().getPays());
        assertEquals("double FSD", providerDtoUpdated.getProviderName());
    }

    @Test(expected = EntityNotFoundException.class)
    public void validate_UpdateProvider_NotFound() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        providerDtoSaved.setId(Long.valueOf(4578962));

        ProviderDto providerDtoUpdated = providerService.updateProvider(providerDtoSaved);
        assertNotNull(providerDtoUpdated);
        assertEquals("Congo", providerDtoUpdated.getProviderAddressDto().getPays());
        assertEquals("double FSD", providerDtoUpdated.getProviderName());
    }


}