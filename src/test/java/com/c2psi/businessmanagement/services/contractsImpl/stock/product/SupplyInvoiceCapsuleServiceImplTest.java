package com.c2psi.businessmanagement.services.contractsImpl.stock.product;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.product.SupplyInvoiceCapsuleDto;
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
public class SupplyInvoiceCapsuleServiceImplTest {
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
    SupplyInvoiceCapsuleServiceImpl supplyInvoiceCapsuleService;
    @Autowired
    UsedForTestForProvider usedForTestForProvider;
    @Autowired
    UsedForTestForAll usedForTestForAll;
    @Autowired
    UsedForTestForProduct usedForTestForProduct;


    @Test
    public void validate_SaveSupplyInvoiceCapsule() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(1, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(2, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        SupplyInvoiceCapsuleDto supplyInvoiceCapsuleDtoSaved = usedForTestForProduct.saveSupplyInvoiceCapsule(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCapsuleService);
        assertNotNull(supplyInvoiceCapsuleDtoSaved);
    }

    @Test(expected = DuplicateEntityException.class)
    public void validate_SaveSupplyInvoiceCapsule_Duplicated() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(1, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(2, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        SupplyInvoiceCapsuleDto supplyInvoiceCapsuleDtoSaved = usedForTestForProduct.saveSupplyInvoiceCapsule(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCapsuleService);
        assertNotNull(supplyInvoiceCapsuleDtoSaved);
        SupplyInvoiceCapsuleDto supplyInvoiceCapsuleDtoSaved1 = usedForTestForProduct.saveSupplyInvoiceCapsule(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCapsuleService);
        //The above line is supposed to launch the expected exception
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveSupplyInvoiceCapsule_Invalid() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(1, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(2, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        SupplyInvoiceCapsuleDto supplyInvoiceCapsuleDtoSaved = usedForTestForProduct.saveSupplyInvoiceCapsule_Invalid(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCapsuleService);
        assertNotNull(supplyInvoiceCapsuleDtoSaved);
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveSupplyInvoiceCapsule_DifferentUserBMPos() {
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

        SupplyInvoiceCapsuleDto supplyInvoiceCapsuleDtoSaved = usedForTestForProduct.saveSupplyInvoiceCapsule(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCapsuleService);
        assertNotNull(supplyInvoiceCapsuleDtoSaved);
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveSupplyInvoiceCapsule_DifferentUserBMProvider() {
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

        SupplyInvoiceCapsuleDto supplyInvoiceCapsuleDtoSaved = usedForTestForProduct.saveSupplyInvoiceCapsule(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCapsuleService);
        assertNotNull(supplyInvoiceCapsuleDtoSaved);
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveSupplyInvoiceCapsule_DifferentPosSupplyInvoiceCapsule() {
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

        SupplyInvoiceCapsuleDto supplyInvoiceCapsuleDtoSaved = usedForTestForProduct.saveSupplyInvoiceCapsule(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved1, supplyInvoiceCapsuleService);
        assertNotNull(supplyInvoiceCapsuleDtoSaved);
    }

    @Test
    public void validate_UpdateSupplyInvoiceCapsule() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(1, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(2, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        SupplyInvoiceCapsuleDto supplyInvoiceCapsuleDtoSaved = usedForTestForProduct.saveSupplyInvoiceCapsule(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCapsuleService);
        assertNotNull(supplyInvoiceCapsuleDtoSaved);

        SupplyInvoiceCapsuleDto supplyInvoiceCapsuleDtoSavedFound = supplyInvoiceCapsuleService.findSupplyInvoiceCapsuleById(
                supplyInvoiceCapsuleDtoSaved.getId());
        assertNotNull(supplyInvoiceCapsuleDtoSavedFound);

        SupplyInvoiceCapsuleDto supplyInvoiceCapsuleDtoSavedFound1 = supplyInvoiceCapsuleService.findSupplyInvoiceCapsuleByCode(
                supplyInvoiceCapsuleDtoSaved.getSicapsCode(), supplyInvoiceCapsuleDtoSaved.getSicapsPosId());
        assertNotNull(supplyInvoiceCapsuleDtoSavedFound1);

        //On fait le update du code
        supplyInvoiceCapsuleDtoSavedFound.setSicapsCode("newCode");
        SupplyInvoiceCapsuleDto supplyInvoiceCapsDtoSavedUpdated = supplyInvoiceCapsuleService.updateSupplyInvoiceCapsule(
                supplyInvoiceCapsuleDtoSavedFound);
        assertNotNull(supplyInvoiceCapsDtoSavedUpdated);
        assertEquals(supplyInvoiceCapsuleDtoSavedFound.getSicapsCode(), supplyInvoiceCapsDtoSavedUpdated.getSicapsCode());
    }

    @Test(expected = DuplicateEntityException.class)
    public void validate_UpdateSupplyInvoiceCapsule_Duplicated() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(1, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(2, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        SupplyInvoiceCapsuleDto supplyInvoiceCapsDtoSaved = usedForTestForProduct.saveSupplyInvoiceCapsule(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCapsuleService);
        assertNotNull(supplyInvoiceCapsDtoSaved);

        SupplyInvoiceCapsuleDto supplyInvoiceCapsDtoSaved1 = usedForTestForProduct.saveSupplyInvoiceCapsule(5,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCapsuleService);
        assertNotNull(supplyInvoiceCapsDtoSaved1);

        SupplyInvoiceCapsuleDto supplyInvoiceCapsDtoSavedFound = supplyInvoiceCapsuleService.findSupplyInvoiceCapsuleById(
                supplyInvoiceCapsDtoSaved.getId());
        assertNotNull(supplyInvoiceCapsDtoSavedFound);

        SupplyInvoiceCapsuleDto supplyInvoiceCapsDtoSavedFound1 = supplyInvoiceCapsuleService.findSupplyInvoiceCapsuleByCode(
                supplyInvoiceCapsDtoSaved1.getSicapsCode(), supplyInvoiceCapsDtoSaved1.getSicapsPosId());
        assertNotNull(supplyInvoiceCapsDtoSavedFound1);

        //On fait le update du code
        supplyInvoiceCapsDtoSavedFound.setSicapsCode("newCode");
        SupplyInvoiceCapsuleDto supplyInvoiceCapsDtoSavedUpdated = supplyInvoiceCapsuleService.updateSupplyInvoiceCapsule(
                supplyInvoiceCapsDtoSavedFound);
        assertNotNull(supplyInvoiceCapsDtoSavedUpdated);
        assertEquals(supplyInvoiceCapsDtoSavedFound.getSicapsCode(), supplyInvoiceCapsDtoSavedUpdated.getSicapsCode());

        supplyInvoiceCapsDtoSavedFound1.setSicapsCode("newCode");
        SupplyInvoiceCapsuleDto supplyInvoiceCapsDtoSavedUpdated1 = supplyInvoiceCapsuleService.updateSupplyInvoiceCapsule(
                supplyInvoiceCapsDtoSavedFound1);
        //The above line is supposed to launch the expected exception
    }

    @Test
    public void validate_UpdateSupplyInvoiceCapsule1() {
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

        SupplyInvoiceCapsuleDto supplyInvoiceCapsuleDtoSaved = usedForTestForProduct.saveSupplyInvoiceCapsule(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCapsuleService);
        assertNotNull(supplyInvoiceCapsuleDtoSaved);

        SupplyInvoiceCapsuleDto supplyInvoiceCapsuleDtoSavedFound = supplyInvoiceCapsuleService.findSupplyInvoiceCapsuleById(
                supplyInvoiceCapsuleDtoSaved.getId());
        assertNotNull(supplyInvoiceCapsuleDtoSavedFound);

        SupplyInvoiceCapsuleDto supplyInvoiceCapsuleDtoSavedFound1 = supplyInvoiceCapsuleService.findSupplyInvoiceCapsuleByCode(
                supplyInvoiceCapsuleDtoSaved.getSicapsCode(), supplyInvoiceCapsuleDtoSaved.getSicapsPosId());
        assertNotNull(supplyInvoiceCapsuleDtoSavedFound1);

        //On fait le update du code
        supplyInvoiceCapsuleDtoSavedFound.setSicapsCode("newCode");
        supplyInvoiceCapsuleDtoSavedFound.setSicapsProviderDto(providerDtoSaved1);
        SupplyInvoiceCapsuleDto supplyInvoiceCapsuleDtoSavedUpdated = supplyInvoiceCapsuleService.updateSupplyInvoiceCapsule(
                supplyInvoiceCapsuleDtoSavedFound);
        assertNotNull(supplyInvoiceCapsuleDtoSavedUpdated);
        assertEquals(supplyInvoiceCapsuleDtoSavedFound.getSicapsCode(), supplyInvoiceCapsuleDtoSavedUpdated.getSicapsCode());
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_UpdateSupplyInvoiceCapsule_UpdatePos() {
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

        SupplyInvoiceCapsuleDto supplyInvoiceCapsuleDtoSaved = usedForTestForProduct.saveSupplyInvoiceCapsule(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCapsuleService);
        assertNotNull(supplyInvoiceCapsuleDtoSaved);

        SupplyInvoiceCapsuleDto supplyInvoiceCapsuleDtoSavedFound = supplyInvoiceCapsuleService.findSupplyInvoiceCapsuleById(
                supplyInvoiceCapsuleDtoSaved.getId());
        assertNotNull(supplyInvoiceCapsuleDtoSavedFound);

        SupplyInvoiceCapsuleDto supplyInvoiceCapsuleDtoSavedFound1 = supplyInvoiceCapsuleService.findSupplyInvoiceCapsuleByCode(
                supplyInvoiceCapsuleDtoSaved.getSicapsCode(), supplyInvoiceCapsuleDtoSaved.getSicapsPosId());
        assertNotNull(supplyInvoiceCapsuleDtoSavedFound1);

        //On fait le update du code
        supplyInvoiceCapsuleDtoSavedFound.setSicapsCode("newCode");
        supplyInvoiceCapsuleDtoSavedFound.setSicapsProviderDto(providerDtoSaved1);
        supplyInvoiceCapsuleDtoSavedFound.setSicapsPosId(posDtoSaved1.getId());
        SupplyInvoiceCapsuleDto supplyInvoiceCapsuleDtoSavedUpdated = supplyInvoiceCapsuleService.updateSupplyInvoiceCapsule(
                supplyInvoiceCapsuleDtoSavedFound);
        assertNotNull(supplyInvoiceCapsuleDtoSavedUpdated);
        assertEquals(supplyInvoiceCapsuleDtoSavedFound.getSicapsCode(), supplyInvoiceCapsuleDtoSavedUpdated.getSicapsCode());
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

        SupplyInvoiceCapsuleDto SupplyInvoiceCapsuleDtoSaved = usedForTestForProduct.saveSupplyInvoiceCapsule(3,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCapsuleService);
        assertNotNull(SupplyInvoiceCapsuleDtoSaved);

        SupplyInvoiceCapsuleDto SupplyInvoiceCapsuleDtoSaved01 = usedForTestForProduct.saveSupplyInvoiceCapsule(43,
                userBMDtoSaved, providerDtoSaved1, posDtoSaved, supplyInvoiceCapsuleService);
        assertNotNull(SupplyInvoiceCapsuleDtoSaved01);

        SupplyInvoiceCapsuleDto SupplyInvoiceCapsuleDtoSaved1 = usedForTestForProduct.saveSupplyInvoiceCapsule(10,
                userBMDtoSaved, providerDtoSaved, posDtoSaved, supplyInvoiceCapsuleService);
        assertNotNull(SupplyInvoiceCapsuleDtoSaved1);

        SupplyInvoiceCapsuleDto SupplyInvoiceCapsuleDtoSaved2 = usedForTestForProduct.saveSupplyInvoiceCapsule(11,
                userBMDtoSaved1, providerDtoSaved, posDtoSaved, supplyInvoiceCapsuleService);
        assertNotNull(SupplyInvoiceCapsuleDtoSaved2);

        SupplyInvoiceCapsuleDto SupplyInvoiceCapsuleDtoSaved3 = usedForTestForProduct.saveSupplyInvoiceCapsule(17,
                userBMDtoSaved1, providerDtoSaved, posDtoSaved, supplyInvoiceCapsuleService);
        assertNotNull(SupplyInvoiceCapsuleDtoSaved3);

        SupplyInvoiceCapsuleDto SupplyInvoiceCapsuleDtoSaved31 = usedForTestForProduct.saveSupplyInvoiceCapsule(28,
                userBMDtoSaved1, providerDtoSaved1, posDtoSaved, supplyInvoiceCapsuleService);
        assertNotNull(SupplyInvoiceCapsuleDtoSaved31);

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

            //findAllSupplyInvoiceCapsuleBetween
            List<SupplyInvoiceCapsuleDto> SupplyInvoiceCapsuleDtoList = supplyInvoiceCapsuleService.
                    findAllSupplyInvoiceCapsuleBetween(posDtoSaved.getId(), startDate.toInstant(), endDate.toInstant());
            assertNotNull(SupplyInvoiceCapsuleDtoList);
            assertEquals(6, SupplyInvoiceCapsuleDtoList.size());

            //findPageSupplyInvoiceCapsuleBetween
            Page<SupplyInvoiceCapsuleDto> SupplyInvoiceCapsuleDtoPage = supplyInvoiceCapsuleService.
                    findPageSupplyInvoiceCapsuleBetween(posDtoSaved.getId(), startDate.toInstant(), endDate.toInstant(), 0, 2);
            assertNotNull(SupplyInvoiceCapsuleDtoPage);
            assertEquals(3, SupplyInvoiceCapsuleDtoPage.getTotalPages());

            //findAllSupplyInvoiceCapsuleofUserbmBetween
            List<SupplyInvoiceCapsuleDto> SupplyInvoiceCapsuleDtoListuserbm = supplyInvoiceCapsuleService.
                    findAllSupplyInvoiceCapsuleofUserbmBetween(posDtoSaved.getId(), userBMDtoSaved.getId(), startDate.toInstant(), endDate.toInstant());
            assertNotNull(SupplyInvoiceCapsuleDtoListuserbm);
            assertEquals(3, SupplyInvoiceCapsuleDtoListuserbm.size());

            //findPageSupplyInvoiceCapsuleofUserbmBetween
            Page<SupplyInvoiceCapsuleDto> SupplyInvoiceCapsuleDtoPageuserbm = supplyInvoiceCapsuleService.
                    findPageSupplyInvoiceCapsuleofUserbmBetween(posDtoSaved.getId(), userBMDtoSaved.getId(), startDate.toInstant(),
                            endDate.toInstant(), 0, 2);
            assertNotNull(SupplyInvoiceCapsuleDtoPageuserbm);
            assertEquals(2, SupplyInvoiceCapsuleDtoPageuserbm.getTotalPages());

            //findAllSupplyInvoiceCapsuleofProviderBetween
            List<SupplyInvoiceCapsuleDto> SupplyInvoiceCapsuleDtoListprovider = supplyInvoiceCapsuleService.
                    findAllSupplyInvoiceCapsuleofProviderBetween(posDtoSaved.getId(), providerDtoSaved.getId(), startDate.toInstant(), endDate.toInstant());
            assertNotNull(SupplyInvoiceCapsuleDtoListprovider);
            assertEquals(4, SupplyInvoiceCapsuleDtoListprovider.size());

            //findPageSupplyInvoiceCapsuleofProviderBetween
            Page<SupplyInvoiceCapsuleDto> SupplyInvoiceCapsuleDtoPageprovider = supplyInvoiceCapsuleService.
                    findPageSupplyInvoiceCapsuleofProviderBetween(posDtoSaved.getId(), providerDtoSaved.getId(),
                            startDate.toInstant(), endDate.toInstant(), 0, 3);
            assertNotNull(SupplyInvoiceCapsuleDtoPageprovider);
            assertEquals(2, SupplyInvoiceCapsuleDtoPageprovider.getTotalPages());

            //findAllSupplyInvoiceCapsuleofProviderAndUserbmBetween
            List<SupplyInvoiceCapsuleDto> SupplyInvoiceCapsuleDtoListprovideruserbm = supplyInvoiceCapsuleService.
                    findAllSupplyInvoiceCapsuleofProviderAndUserbmBetween(posDtoSaved.getId(), providerDtoSaved.getId(),
                            userBMDtoSaved.getId(), startDate.toInstant(), endDate.toInstant());
            assertNotNull(SupplyInvoiceCapsuleDtoListprovideruserbm);
            assertEquals(2, SupplyInvoiceCapsuleDtoListprovideruserbm.size());

            //findPageSupplyInvoiceCapsuleofProviderAndUserbmBetween
            Page<SupplyInvoiceCapsuleDto> SupplyInvoiceCapsuleDtoPageprovideruserbm = supplyInvoiceCapsuleService.
                    findPageSupplyInvoiceCapsuleofProviderAndUserbmBetween(posDtoSaved.getId(), providerDtoSaved.getId(),
                            userBMDtoSaved.getId(), startDate.toInstant(), endDate.toInstant(), 0, 1);
            assertNotNull(SupplyInvoiceCapsuleDtoPageprovideruserbm);
            assertEquals(2, SupplyInvoiceCapsuleDtoPageprovideruserbm.getTotalPages());

        } catch (Exception e){
            System.err.println("There is an exception not manage in the program means a big problem appears");
            e.printStackTrace();
        }

    }
    
    
    
    
    
    


}