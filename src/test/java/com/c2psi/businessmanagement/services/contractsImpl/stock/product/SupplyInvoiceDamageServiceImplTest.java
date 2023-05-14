package com.c2psi.businessmanagement.services.contractsImpl.stock.product;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.product.SupplyInvoiceDamageDto;
import com.c2psi.businessmanagement.dtos.stock.product.SupplyInvoiceDamageDto;
import com.c2psi.businessmanagement.dtos.stock.product.SupplyInvoiceDamageDto;
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
public class SupplyInvoiceDamageServiceImplTest {

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
    SupplyInvoiceDamageServiceImpl supplyInvoiceDamageService;
    @Autowired
    UsedForTestForProvider usedForTestForProvider;
    @Autowired
    UsedForTestForAll usedForTestForAll;
    @Autowired
    UsedForTestForProduct usedForTestForProduct;

    @Test
    public void validate_SaveSupplyInvoiceDamage() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(1, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(2, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        SupplyInvoiceDamageDto supplyInvoiceDamageDtoSaved = usedForTestForProduct.saveSupplyInvoiceDamage(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceDamageService);
        assertNotNull(supplyInvoiceDamageDtoSaved);
    }

    @Test(expected = DuplicateEntityException.class)
    public void validate_SaveSupplyInvoiceDamage_Duplicated() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(1, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(2, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        SupplyInvoiceDamageDto SupplyInvoiceDamageDtoSaved = usedForTestForProduct.saveSupplyInvoiceDamage(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceDamageService);
        assertNotNull(SupplyInvoiceDamageDtoSaved);
        SupplyInvoiceDamageDto SupplyInvoiceDamageDtoSaved1 = usedForTestForProduct.saveSupplyInvoiceDamage(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceDamageService);
        //The above line is supposed to launch the expected exception
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_saveSupplyInvoiceDamage_Invalid() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(1, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(2, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        SupplyInvoiceDamageDto SupplyInvoiceDamageDtoSaved = usedForTestForProduct.saveSupplyInvoiceDamage_Invalid(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceDamageService);
        assertNotNull(SupplyInvoiceDamageDtoSaved);
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_saveSupplyInvoiceDamage_DifferentUserBMPos() {
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

        SupplyInvoiceDamageDto SupplyInvoiceDamageDtoSaved = usedForTestForProduct.saveSupplyInvoiceDamage(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceDamageService);
        assertNotNull(SupplyInvoiceDamageDtoSaved);
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_saveSupplyInvoiceDamage_DifferentUserBMProvider() {
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

        SupplyInvoiceDamageDto SupplyInvoiceDamageDtoSaved = usedForTestForProduct.saveSupplyInvoiceDamage(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceDamageService);
        assertNotNull(SupplyInvoiceDamageDtoSaved);
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_saveSupplyInvoiceDamage_DifferentPosSupplyInvoiceDamage() {
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

        SupplyInvoiceDamageDto SupplyInvoiceDamageDtoSaved = usedForTestForProduct.saveSupplyInvoiceDamage(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved1, supplyInvoiceDamageService);
        assertNotNull(SupplyInvoiceDamageDtoSaved);
    }

    @Test
    public void validate_UpdateSupplyInvoiceDamage() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(1, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(2, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        SupplyInvoiceDamageDto SupplyInvoiceDamageDtoSaved = usedForTestForProduct.saveSupplyInvoiceDamage(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceDamageService);
        assertNotNull(SupplyInvoiceDamageDtoSaved);

        SupplyInvoiceDamageDto SupplyInvoiceDamageDtoSavedFound = supplyInvoiceDamageService.findSupplyInvoiceDamageById(
                SupplyInvoiceDamageDtoSaved.getId());
        assertNotNull(SupplyInvoiceDamageDtoSavedFound);

        SupplyInvoiceDamageDto SupplyInvoiceDamageDtoSavedFound1 = supplyInvoiceDamageService.findSupplyInvoiceDamageByCode(
                SupplyInvoiceDamageDtoSaved.getSidamCode(), SupplyInvoiceDamageDtoSaved.getSidamPosDto().getId());
        assertNotNull(SupplyInvoiceDamageDtoSavedFound1);

        //On fait le update du code
        SupplyInvoiceDamageDtoSavedFound.setSidamCode("newCode");
        SupplyInvoiceDamageDto supplyInvoicedamDtoSavedUpdated = supplyInvoiceDamageService.updateSupplyInvoiceDamage(
                SupplyInvoiceDamageDtoSavedFound);
        assertNotNull(supplyInvoicedamDtoSavedUpdated);
        assertEquals(SupplyInvoiceDamageDtoSavedFound.getSidamCode(), supplyInvoicedamDtoSavedUpdated.getSidamCode());
    }

    @Test(expected = DuplicateEntityException.class)
    public void validate_UpdateSupplyInvoiceDamage_Duplicated() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(1, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(2, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        SupplyInvoiceDamageDto supplyInvoicedamDtoSaved = usedForTestForProduct.saveSupplyInvoiceDamage(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceDamageService);
        assertNotNull(supplyInvoicedamDtoSaved);

        SupplyInvoiceDamageDto supplyInvoicedamDtoSaved1 = usedForTestForProduct.saveSupplyInvoiceDamage(5,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceDamageService);
        assertNotNull(supplyInvoicedamDtoSaved1);

        SupplyInvoiceDamageDto supplyInvoicedamDtoSavedFound = supplyInvoiceDamageService.findSupplyInvoiceDamageById(
                supplyInvoicedamDtoSaved.getId());
        assertNotNull(supplyInvoicedamDtoSavedFound);

        SupplyInvoiceDamageDto supplyInvoicedamDtoSavedFound1 = supplyInvoiceDamageService.findSupplyInvoiceDamageByCode(
                supplyInvoicedamDtoSaved1.getSidamCode(), supplyInvoicedamDtoSaved1.getSidamPosDto().getId());
        assertNotNull(supplyInvoicedamDtoSavedFound1);

        //On fait le update du code
        supplyInvoicedamDtoSavedFound.setSidamCode("newCode");
        SupplyInvoiceDamageDto supplyInvoicedamDtoSavedUpdated = supplyInvoiceDamageService.updateSupplyInvoiceDamage(
                supplyInvoicedamDtoSavedFound);
        assertNotNull(supplyInvoicedamDtoSavedUpdated);
        assertEquals(supplyInvoicedamDtoSavedFound.getSidamCode(), supplyInvoicedamDtoSavedUpdated.getSidamCode());

        supplyInvoicedamDtoSavedFound1.setSidamCode("newCode");
        SupplyInvoiceDamageDto supplyInvoicedamDtoSavedUpdated1 = supplyInvoiceDamageService.updateSupplyInvoiceDamage(
                supplyInvoicedamDtoSavedFound1);
        //The above line is supposed to launch the expected exception
    }

    @Test
    public void validate_UpdateSupplyInvoiceDamage1() {
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

        SupplyInvoiceDamageDto SupplyInvoiceDamageDtoSaved = usedForTestForProduct.saveSupplyInvoiceDamage(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceDamageService);
        assertNotNull(SupplyInvoiceDamageDtoSaved);

        SupplyInvoiceDamageDto SupplyInvoiceDamageDtoSavedFound = supplyInvoiceDamageService.findSupplyInvoiceDamageById(
                SupplyInvoiceDamageDtoSaved.getId());
        assertNotNull(SupplyInvoiceDamageDtoSavedFound);

        SupplyInvoiceDamageDto SupplyInvoiceDamageDtoSavedFound1 = supplyInvoiceDamageService.findSupplyInvoiceDamageByCode(
                SupplyInvoiceDamageDtoSaved.getSidamCode(), SupplyInvoiceDamageDtoSaved.getSidamPosDto().getId());
        assertNotNull(SupplyInvoiceDamageDtoSavedFound1);

        //On fait le update du code
        SupplyInvoiceDamageDtoSavedFound.setSidamCode("newCode");
        SupplyInvoiceDamageDtoSavedFound.setSidamProviderDto(providerDtoSaved1);
        SupplyInvoiceDamageDto SupplyInvoiceDamageDtoSavedUpdated = supplyInvoiceDamageService.updateSupplyInvoiceDamage(
                SupplyInvoiceDamageDtoSavedFound);
        assertNotNull(SupplyInvoiceDamageDtoSavedUpdated);
        assertEquals(SupplyInvoiceDamageDtoSavedFound.getSidamCode(), SupplyInvoiceDamageDtoSavedUpdated.getSidamCode());
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_UpdateSupplyInvoiceDamage_UpdatePos() {
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

        SupplyInvoiceDamageDto SupplyInvoiceDamageDtoSaved = usedForTestForProduct.saveSupplyInvoiceDamage(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceDamageService);
        assertNotNull(SupplyInvoiceDamageDtoSaved);

        SupplyInvoiceDamageDto SupplyInvoiceDamageDtoSavedFound = supplyInvoiceDamageService.findSupplyInvoiceDamageById(
                SupplyInvoiceDamageDtoSaved.getId());
        assertNotNull(SupplyInvoiceDamageDtoSavedFound);

        SupplyInvoiceDamageDto SupplyInvoiceDamageDtoSavedFound1 = supplyInvoiceDamageService.findSupplyInvoiceDamageByCode(
                SupplyInvoiceDamageDtoSaved.getSidamCode(), SupplyInvoiceDamageDtoSaved.getSidamPosDto().getId());
        assertNotNull(SupplyInvoiceDamageDtoSavedFound1);

        //On fait le update du code
        SupplyInvoiceDamageDtoSavedFound.setSidamCode("newCode");
        SupplyInvoiceDamageDtoSavedFound.setSidamProviderDto(providerDtoSaved1);
        SupplyInvoiceDamageDtoSavedFound.setSidamPosDto(posDtoSaved1);
        SupplyInvoiceDamageDto SupplyInvoiceDamageDtoSavedUpdated = supplyInvoiceDamageService.updateSupplyInvoiceDamage(
                SupplyInvoiceDamageDtoSavedFound);
        assertNotNull(SupplyInvoiceDamageDtoSavedUpdated);
        assertEquals(SupplyInvoiceDamageDtoSavedFound.getSidamCode(), SupplyInvoiceDamageDtoSavedUpdated.getSidamCode());
    }

    @Test
    public void validate_findSupplyInvoiceCashBetween() {
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

        SupplyInvoiceDamageDto SupplyInvoiceDamageDtoSaved = usedForTestForProduct.saveSupplyInvoiceDamage(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceDamageService);
        assertNotNull(SupplyInvoiceDamageDtoSaved);

        SupplyInvoiceDamageDto SupplyInvoiceDamageDtoSaved01 = usedForTestForProduct.saveSupplyInvoiceDamage(43,
                userBMDtoSaved, providerDtoSaved1, posDtoSaved, supplyInvoiceDamageService);
        assertNotNull(SupplyInvoiceDamageDtoSaved01);

        SupplyInvoiceDamageDto SupplyInvoiceDamageDtoSaved1 = usedForTestForProduct.saveSupplyInvoiceDamage(10,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceDamageService);
        assertNotNull(SupplyInvoiceDamageDtoSaved1);

        SupplyInvoiceDamageDto SupplyInvoiceDamageDtoSaved2 = usedForTestForProduct.saveSupplyInvoiceDamage(11,
                userBMDtoSaved1, providerDtoSaved, posDtoSaved, supplyInvoiceDamageService);
        assertNotNull(SupplyInvoiceDamageDtoSaved2);

        SupplyInvoiceDamageDto SupplyInvoiceDamageDtoSaved3 = usedForTestForProduct.saveSupplyInvoiceDamage(17,
                userBMDtoSaved1, providerDtoSaved, posDtoSaved, supplyInvoiceDamageService);
        assertNotNull(SupplyInvoiceDamageDtoSaved3);

        SupplyInvoiceDamageDto SupplyInvoiceDamageDtoSaved31 = usedForTestForProduct.saveSupplyInvoiceDamage(28,
                userBMDtoSaved1, providerDtoSaved1, posDtoSaved, supplyInvoiceDamageService);
        assertNotNull(SupplyInvoiceDamageDtoSaved31);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date startDate1 = new Date();
            String startDateString = sdf.format(startDate1);

            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate1);
            cal.add(Calendar.DAY_OF_MONTH, 1);
            Date endDate1 = cal.getTime();
            String endDateString = sdf.format(endDate1);

            Date startDate = sdf.parse(startDateString);
            Date endDate = sdf.parse(endDateString);

            //findAllSupplyInvoiceDamageBetween
            List<SupplyInvoiceDamageDto> SupplyInvoiceDamageDtoList = supplyInvoiceDamageService.
                    findAllSupplyInvoiceDamageBetween(posDtoSaved.getId(), startDate.toInstant(), endDate.toInstant());
            assertNotNull(SupplyInvoiceDamageDtoList);
            assertEquals(6, SupplyInvoiceDamageDtoList.size());

            //findPageSupplyInvoiceDamageBetween
            Page<SupplyInvoiceDamageDto> SupplyInvoiceDamageDtoPage = supplyInvoiceDamageService.
                    findPageSupplyInvoiceDamageBetween(posDtoSaved.getId(), startDate.toInstant(), endDate.toInstant(), 0, 2);
            assertNotNull(SupplyInvoiceDamageDtoPage);
            assertEquals(3, SupplyInvoiceDamageDtoPage.getTotalPages());

            //findAllSupplyInvoiceDamageofUserbmBetween
            List<SupplyInvoiceDamageDto> SupplyInvoiceDamageDtoListuserbm = supplyInvoiceDamageService.
                    findAllSupplyInvoiceDamageofUserbmBetween(posDtoSaved.getId(), userBMDtoSaved.getId(), startDate.toInstant(), endDate.toInstant());
            assertNotNull(SupplyInvoiceDamageDtoListuserbm);
            assertEquals(3, SupplyInvoiceDamageDtoListuserbm.size());

            //findPageSupplyInvoiceDamageofUserbmBetween
            Page<SupplyInvoiceDamageDto> SupplyInvoiceDamageDtoPageuserbm = supplyInvoiceDamageService.
                    findPageSupplyInvoiceDamageofUserbmBetween(posDtoSaved.getId(), userBMDtoSaved.getId(), startDate.toInstant(),
                            endDate.toInstant(), 0, 2);
            assertNotNull(SupplyInvoiceDamageDtoPageuserbm);
            assertEquals(2, SupplyInvoiceDamageDtoPageuserbm.getTotalPages());

            //findAllSupplyInvoiceDamageofProviderBetween
            List<SupplyInvoiceDamageDto> SupplyInvoiceDamageDtoListprovider = supplyInvoiceDamageService.
                    findAllSupplyInvoiceDamageofProviderBetween(posDtoSaved.getId(), providerDtoSaved.getId(), startDate.toInstant(), endDate.toInstant());
            assertNotNull(SupplyInvoiceDamageDtoListprovider);
            assertEquals(4, SupplyInvoiceDamageDtoListprovider.size());

            //findPageSupplyInvoiceDamageofProviderBetween
            Page<SupplyInvoiceDamageDto> SupplyInvoiceDamageDtoPageprovider = supplyInvoiceDamageService.
                    findPageSupplyInvoiceDamageofProviderBetween(posDtoSaved.getId(), providerDtoSaved.getId(),
                            startDate.toInstant(), endDate.toInstant(), 0, 3);
            assertNotNull(SupplyInvoiceDamageDtoPageprovider);
            assertEquals(2, SupplyInvoiceDamageDtoPageprovider.getTotalPages());

            //findAllSupplyInvoiceDamageofProviderAndUserbmBetween
            List<SupplyInvoiceDamageDto> SupplyInvoiceDamageDtoListprovideruserbm = supplyInvoiceDamageService.
                    findAllSupplyInvoiceDamageofProviderAndUserbmBetween(posDtoSaved.getId(), providerDtoSaved.getId(),
                            userBMDtoSaved.getId(), startDate.toInstant(), endDate.toInstant());
            assertNotNull(SupplyInvoiceDamageDtoListprovideruserbm);
            assertEquals(2, SupplyInvoiceDamageDtoListprovideruserbm.size());

            //findPageSupplyInvoiceDamageofProviderAndUserbmBetween
            Page<SupplyInvoiceDamageDto> SupplyInvoiceDamageDtoPageprovideruserbm = supplyInvoiceDamageService.
                    findPageSupplyInvoiceDamageofProviderAndUserbmBetween(posDtoSaved.getId(), providerDtoSaved.getId(),
                            userBMDtoSaved.getId(), startDate.toInstant(), endDate.toInstant(), 0, 1);
            assertNotNull(SupplyInvoiceDamageDtoPageprovideruserbm);
            assertEquals(2, SupplyInvoiceDamageDtoPageprovideruserbm.getTotalPages());

        } catch (Exception e){
            System.err.println("There is an exception not manage in the program means a big problem appears");
            e.printStackTrace();
        }

    }


}