package com.c2psi.businessmanagement.services.contractsImpl.stock.product;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.product.SupplyInvoiceCashDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;
import com.c2psi.businessmanagement.exceptions.DuplicateEntityException;
import com.c2psi.businessmanagement.exceptions.InvalidEntityException;
import com.c2psi.businessmanagement.services.contractsImpl.UsedForTestForAll;
import com.c2psi.businessmanagement.services.contractsImpl.pos.pos.EnterpriseServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.pos.pos.PointofsaleServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.pos.userbm.UserBMServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.price.CurrencyServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.provider.ProviderServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.provider.UsedForTestForProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusinessmanagementApplication.class)
public class SupplyInvoiceCashServiceImplTest {

    @Autowired
    EnterpriseServiceImpl enterpriseService;
    @Autowired
    PointofsaleServiceImpl pointofsaleService;
    @Autowired
    UserBMServiceImpl userBMService;
    @Autowired
    ProviderServiceImpl providerService;
    @Autowired
    CurrencyServiceImpl currencyService;
    @Autowired
    SupplyInvoiceCashServiceImpl supplyInvoiceCashService;
    @Autowired
    UsedForTestForProvider usedForTestForProvider;
    @Autowired
    UsedForTestForAll usedForTestForAll;
    @Autowired
    UsedForTestForProduct usedForTestForProduct;

    @Test
    public void validate_SaveSupplyInvoiceCash() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(1, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(2, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        SupplyInvoiceCashDto supplyInvoiceCashDtoSaved = usedForTestForProduct.saveSupplyInvoiceCash(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCashService);
        assertNotNull(supplyInvoiceCashDtoSaved);
    }

    @Test(expected = DuplicateEntityException.class)
    public void validate_SaveSupplyInvoiceCash_Duplicated() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(1, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(2, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        SupplyInvoiceCashDto supplyInvoiceCashDtoSaved = usedForTestForProduct.saveSupplyInvoiceCash(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCashService);
        assertNotNull(supplyInvoiceCashDtoSaved);
        SupplyInvoiceCashDto supplyInvoiceCashDtoSaved1 = usedForTestForProduct.saveSupplyInvoiceCash(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCashService);
        //The above line is supposed to launch the expected exception
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveSupplyInvoiceCash_Invalid() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(1, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(2, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        SupplyInvoiceCashDto supplyInvoiceCashDtoSaved = usedForTestForProduct.saveSupplyInvoiceCash_Invalid(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCashService);
        assertNotNull(supplyInvoiceCashDtoSaved);
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveSupplyInvoiceCash_DifferentUserBMPos() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        PointofsaleDto posDtoSaved1 = usedForTestForAll.savePointofsale(1, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved1);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(100, posDtoSaved1, userBMService);
        assertNotNull(userBMDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(2, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        SupplyInvoiceCashDto supplyInvoiceCashDtoSaved = usedForTestForProduct.saveSupplyInvoiceCash(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCashService);
        assertNotNull(supplyInvoiceCashDtoSaved);
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveSupplyInvoiceCash_DifferentUserBMProvider() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        PointofsaleDto posDtoSaved1 = usedForTestForAll.savePointofsale(1, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved1);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(100, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(2, posDtoSaved1, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        SupplyInvoiceCashDto supplyInvoiceCashDtoSaved = usedForTestForProduct.saveSupplyInvoiceCash(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCashService);
        assertNotNull(supplyInvoiceCashDtoSaved);
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveSupplyInvoiceCash_DifferentPosSupplyInvoiceCash() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        PointofsaleDto posDtoSaved1 = usedForTestForAll.savePointofsale(1, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved1);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(100, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(2, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        SupplyInvoiceCashDto supplyInvoiceCashDtoSaved = usedForTestForProduct.saveSupplyInvoiceCash(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved1, supplyInvoiceCashService);
        assertNotNull(supplyInvoiceCashDtoSaved);
    }

    @Test
    public void validate_UpdateSupplyInvoiceCash() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(1, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(2, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        SupplyInvoiceCashDto supplyInvoiceCashDtoSaved = usedForTestForProduct.saveSupplyInvoiceCash(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCashService);
        assertNotNull(supplyInvoiceCashDtoSaved);

        SupplyInvoiceCashDto supplyInvoiceCashDtoSavedFound = supplyInvoiceCashService.findSupplyInvoiceCashById(
                supplyInvoiceCashDtoSaved.getId());
        assertNotNull(supplyInvoiceCashDtoSavedFound);

        SupplyInvoiceCashDto supplyInvoiceCashDtoSavedFound1 = supplyInvoiceCashService.findSupplyInvoiceCashByCode(
                supplyInvoiceCashDtoSaved.getSicashCode(), supplyInvoiceCashDtoSaved.getSicashPosId());
        assertNotNull(supplyInvoiceCashDtoSavedFound1);

        //On fait le update du code
        supplyInvoiceCashDtoSavedFound.setSicashCode("newCode");
        SupplyInvoiceCashDto supplyInvoiceCashDtoSavedUpdated = supplyInvoiceCashService.updateSupplyInvoiceCash(
                supplyInvoiceCashDtoSavedFound);
        assertNotNull(supplyInvoiceCashDtoSavedUpdated);
        assertEquals(supplyInvoiceCashDtoSavedFound.getSicashCode(), supplyInvoiceCashDtoSavedUpdated.getSicashCode());
    }

    @Test(expected = DuplicateEntityException.class)
    public void validate_UpdateSupplyInvoiceCash_Duplicated() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(1, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(2, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        SupplyInvoiceCashDto supplyInvoiceCashDtoSaved = usedForTestForProduct.saveSupplyInvoiceCash(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCashService);
        assertNotNull(supplyInvoiceCashDtoSaved);

        SupplyInvoiceCashDto supplyInvoiceCashDtoSaved1 = usedForTestForProduct.saveSupplyInvoiceCash(5,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCashService);
        assertNotNull(supplyInvoiceCashDtoSaved1);

        SupplyInvoiceCashDto supplyInvoiceCashDtoSavedFound = supplyInvoiceCashService.findSupplyInvoiceCashById(
                supplyInvoiceCashDtoSaved.getId());
        assertNotNull(supplyInvoiceCashDtoSavedFound);

        SupplyInvoiceCashDto supplyInvoiceCashDtoSavedFound1 = supplyInvoiceCashService.findSupplyInvoiceCashByCode(
                supplyInvoiceCashDtoSaved1.getSicashCode(), supplyInvoiceCashDtoSaved1.getSicashPosId());
        assertNotNull(supplyInvoiceCashDtoSavedFound1);

        //On fait le update du code
        supplyInvoiceCashDtoSavedFound.setSicashCode("newCode");
        SupplyInvoiceCashDto supplyInvoiceCashDtoSavedUpdated = supplyInvoiceCashService.updateSupplyInvoiceCash(
                supplyInvoiceCashDtoSavedFound);
        assertNotNull(supplyInvoiceCashDtoSavedUpdated);
        assertEquals(supplyInvoiceCashDtoSavedFound.getSicashCode(), supplyInvoiceCashDtoSavedUpdated.getSicashCode());

        supplyInvoiceCashDtoSavedFound1.setSicashCode("newCode");
        SupplyInvoiceCashDto supplyInvoiceCashDtoSavedUpdated1 = supplyInvoiceCashService.updateSupplyInvoiceCash(
                supplyInvoiceCashDtoSavedFound1);
        //The above line is supposed to launch the expected exception
    }

    @Test
    public void validate_UpdateSupplyInvoiceCash1() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(1, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(2, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        ProviderDto providerDtoSaved1 = usedForTestForProvider.saveProvider(7, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved1);
        assertNotNull(providerDtoSaved1.getProviderCaDto().getId());

        SupplyInvoiceCashDto supplyInvoiceCashDtoSaved = usedForTestForProduct.saveSupplyInvoiceCash(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCashService);
        assertNotNull(supplyInvoiceCashDtoSaved);

        SupplyInvoiceCashDto supplyInvoiceCashDtoSavedFound = supplyInvoiceCashService.findSupplyInvoiceCashById(
                supplyInvoiceCashDtoSaved.getId());
        assertNotNull(supplyInvoiceCashDtoSavedFound);

        SupplyInvoiceCashDto supplyInvoiceCashDtoSavedFound1 = supplyInvoiceCashService.findSupplyInvoiceCashByCode(
                supplyInvoiceCashDtoSaved.getSicashCode(), supplyInvoiceCashDtoSaved.getSicashPosId());
        assertNotNull(supplyInvoiceCashDtoSavedFound1);

        //On fait le update du code
        supplyInvoiceCashDtoSavedFound.setSicashCode("newCode");
        supplyInvoiceCashDtoSavedFound.setSicashProviderDto(providerDtoSaved1);
        SupplyInvoiceCashDto supplyInvoiceCashDtoSavedUpdated = supplyInvoiceCashService.updateSupplyInvoiceCash(
                supplyInvoiceCashDtoSavedFound);
        assertNotNull(supplyInvoiceCashDtoSavedUpdated);
        assertEquals(supplyInvoiceCashDtoSavedFound.getSicashCode(), supplyInvoiceCashDtoSavedUpdated.getSicashCode());
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_UpdateSupplyInvoiceCash_UpdatePos() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        PointofsaleDto posDtoSaved1 = usedForTestForAll.savePointofsale(8, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved1);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(1, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(2, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        ProviderDto providerDtoSaved1 = usedForTestForProvider.saveProvider(7, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved1);
        assertNotNull(providerDtoSaved1.getProviderCaDto().getId());

        SupplyInvoiceCashDto supplyInvoiceCashDtoSaved = usedForTestForProduct.saveSupplyInvoiceCash(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCashService);
        assertNotNull(supplyInvoiceCashDtoSaved);

        SupplyInvoiceCashDto supplyInvoiceCashDtoSavedFound = supplyInvoiceCashService.findSupplyInvoiceCashById(
                supplyInvoiceCashDtoSaved.getId());
        assertNotNull(supplyInvoiceCashDtoSavedFound);

        SupplyInvoiceCashDto supplyInvoiceCashDtoSavedFound1 = supplyInvoiceCashService.findSupplyInvoiceCashByCode(
                supplyInvoiceCashDtoSaved.getSicashCode(), supplyInvoiceCashDtoSaved.getSicashPosId());
        assertNotNull(supplyInvoiceCashDtoSavedFound1);

        //On fait le update du code
        supplyInvoiceCashDtoSavedFound.setSicashCode("newCode");
        supplyInvoiceCashDtoSavedFound.setSicashProviderDto(providerDtoSaved1);
        supplyInvoiceCashDtoSavedFound.setSicashPosId(posDtoSaved1.getId());
        SupplyInvoiceCashDto supplyInvoiceCashDtoSavedUpdated = supplyInvoiceCashService.updateSupplyInvoiceCash(
                supplyInvoiceCashDtoSavedFound);
        assertNotNull(supplyInvoiceCashDtoSavedUpdated);
        assertEquals(supplyInvoiceCashDtoSavedFound.getSicashCode(), supplyInvoiceCashDtoSavedUpdated.getSicashCode());
    }

    @Test
    public void validate_findSupplyInvoiceCashBetween() {
        Date startDate1 = new Date();
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(1, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);

        UserBMDto userBMDtoSaved1 = usedForTestForAll.saveUserBM(15, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved1);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(2, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        ProviderDto providerDtoSaved1 = usedForTestForProvider.saveProvider(30, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved1);
        assertNotNull(providerDtoSaved1.getProviderCaDto().getId());

        SupplyInvoiceCashDto supplyInvoiceCashDtoSaved = usedForTestForProduct.saveSupplyInvoiceCash(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCashService);
        assertNotNull(supplyInvoiceCashDtoSaved);

        SupplyInvoiceCashDto supplyInvoiceCashDtoSaved01 = usedForTestForProduct.saveSupplyInvoiceCash(43,
                userBMDtoSaved, providerDtoSaved1, posDtoSaved, supplyInvoiceCashService);
        assertNotNull(supplyInvoiceCashDtoSaved01);

        SupplyInvoiceCashDto supplyInvoiceCashDtoSaved1 = usedForTestForProduct.saveSupplyInvoiceCash(10,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCashService);
        assertNotNull(supplyInvoiceCashDtoSaved1);

        SupplyInvoiceCashDto supplyInvoiceCashDtoSaved2 = usedForTestForProduct.saveSupplyInvoiceCash(11,
                userBMDtoSaved1, providerDtoSaved, posDtoSaved, supplyInvoiceCashService);
        assertNotNull(supplyInvoiceCashDtoSaved2);

        SupplyInvoiceCashDto supplyInvoiceCashDtoSaved3 = usedForTestForProduct.saveSupplyInvoiceCash(17,
                userBMDtoSaved1, providerDtoSaved, posDtoSaved, supplyInvoiceCashService);
        assertNotNull(supplyInvoiceCashDtoSaved3);

        SupplyInvoiceCashDto supplyInvoiceCashDtoSaved4 = usedForTestForProduct.saveSupplyInvoiceCash(28,
                userBMDtoSaved1, providerDtoSaved1, posDtoSaved, supplyInvoiceCashService);
        assertNotNull(supplyInvoiceCashDtoSaved4);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {

            String startDateString = sdf.format(startDate1);

            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate1);
            cal.add(Calendar.DAY_OF_MONTH, 1);
            Date endDate1 = cal.getTime();
            String endDateString = sdf.format(endDate1);

            Date startDate = sdf.parse(startDateString);
            Date endDate = sdf.parse(endDateString);

            //findAllSupplyInvoiceCashBetween
            List<SupplyInvoiceCashDto> supplyInvoiceCashDtoList = supplyInvoiceCashService.
                    findAllSupplyInvoiceCashBetween(posDtoSaved.getId(), startDate.toInstant(), endDate.toInstant());
            assertNotNull(supplyInvoiceCashDtoList);
            assertEquals(6, supplyInvoiceCashDtoList.size());

            //findPageSupplyInvoiceCashBetween
            Page<SupplyInvoiceCashDto> supplyInvoiceCashDtoPage = supplyInvoiceCashService.
                    findPageSupplyInvoiceCashBetween(posDtoSaved.getId(), startDate.toInstant(), endDate.toInstant(), 0, 2);
            assertNotNull(supplyInvoiceCashDtoPage);
            assertEquals(3, supplyInvoiceCashDtoPage.getTotalPages());

            //findAllSupplyInvoiceCashofUserbmBetween
            List<SupplyInvoiceCashDto> supplyInvoiceCashDtoListuserbm = supplyInvoiceCashService.
                    findAllSupplyInvoiceCashofUserbmBetween(posDtoSaved.getId(), userBMDtoSaved.getId(), startDate.toInstant(), endDate.toInstant());
            assertNotNull(supplyInvoiceCashDtoListuserbm);
            assertEquals(3, supplyInvoiceCashDtoListuserbm.size());

            //findPageSupplyInvoiceCashofUserbmBetween
            Page<SupplyInvoiceCashDto> supplyInvoiceCashDtoPageuserbm = supplyInvoiceCashService.
                    findPageSupplyInvoiceCashofUserbmBetween(posDtoSaved.getId(), userBMDtoSaved.getId(), startDate.toInstant(),
                            endDate.toInstant(), 0, 2);
            assertNotNull(supplyInvoiceCashDtoPageuserbm);
            assertEquals(2, supplyInvoiceCashDtoPageuserbm.getTotalPages());

            //findAllSupplyInvoiceCashofProviderBetween
            List<SupplyInvoiceCashDto> supplyInvoiceCashDtoListprovider = supplyInvoiceCashService.
                    findAllSupplyInvoiceCashofProviderBetween(posDtoSaved.getId(), providerDtoSaved.getId(), startDate.toInstant(), endDate.toInstant());
            assertNotNull(supplyInvoiceCashDtoListprovider);
            assertEquals(4, supplyInvoiceCashDtoListprovider.size());

            //findPageSupplyInvoiceCashofProviderBetween
            Page<SupplyInvoiceCashDto> supplyInvoiceCashDtoPageprovider = supplyInvoiceCashService.
                    findPageSupplyInvoiceCashofProviderBetween(posDtoSaved.getId(), providerDtoSaved.getId(),
                            startDate.toInstant(), endDate.toInstant(), 0, 3);
            assertNotNull(supplyInvoiceCashDtoPageprovider);
            assertEquals(2, supplyInvoiceCashDtoPageprovider.getTotalPages());

            //findAllSupplyInvoiceCashofProviderAndUserbmBetween
            List<SupplyInvoiceCashDto> supplyInvoiceCashDtoListprovideruserbm = supplyInvoiceCashService.
                    findAllSupplyInvoiceCashofProviderAndUserbmBetween(posDtoSaved.getId(), providerDtoSaved.getId(),
                            userBMDtoSaved.getId(), startDate.toInstant(), endDate.toInstant());
            assertNotNull(supplyInvoiceCashDtoListprovideruserbm);
            assertEquals(2, supplyInvoiceCashDtoListprovideruserbm.size());

            //findPageSupplyInvoiceCashofProviderAndUserbmBetween
            Page<SupplyInvoiceCashDto> supplyInvoiceCashDtoPageprovideruserbm = supplyInvoiceCashService.
                    findPageSupplyInvoiceCashofProviderAndUserbmBetween(posDtoSaved.getId(), providerDtoSaved.getId(),
                            userBMDtoSaved.getId(), startDate.toInstant(), endDate.toInstant(), 0, 1);
            assertNotNull(supplyInvoiceCashDtoPageprovideruserbm);
            assertEquals(2, supplyInvoiceCashDtoPageprovideruserbm.getTotalPages());

        } catch (Exception e){
            System.err.println("There is an exception not manage in the program means a big problem appears");
            e.printStackTrace();
        }

    }

    @Test
    public void validate_DeleteSupplyInvoiceCash() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(1, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(2, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        SupplyInvoiceCashDto supplyInvoiceCashDtoSaved = usedForTestForProduct.saveSupplyInvoiceCash(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCashService);
        assertNotNull(supplyInvoiceCashDtoSaved);

        assertTrue(supplyInvoiceCashService.deleteSupplyInvoiceCashById(supplyInvoiceCashDtoSaved.getId()));
    }

}