package com.c2psi.businessmanagement.services.contractsImpl.client.command;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.dtos.client.command.SaleInvoiceCapsuleDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.exceptions.DuplicateEntityException;
import com.c2psi.businessmanagement.exceptions.InvalidEntityException;
import com.c2psi.businessmanagement.services.contractsImpl.UsedForTestForAll;
import com.c2psi.businessmanagement.services.contractsImpl.client.UsedForTestForClient;
import com.c2psi.businessmanagement.services.contractsImpl.client.client.ClientServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.pos.pos.EnterpriseServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.pos.pos.PointofsaleServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.pos.userbm.UserBMServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.price.CurrencyServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.product.ArticleServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.product.UsedForTestForProduct;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusinessmanagementApplication.class)
public class SaleInvoiceCapsuleServiceImplTest {

    @Autowired
    EnterpriseServiceImpl enterpriseService;
    @Autowired
    CurrencyServiceImpl currencyService;
    @Autowired
    PointofsaleServiceImpl pointofsaleService;
    @Autowired
    ArticleServiceImpl articleService;
    @Autowired
    UserBMServiceImpl userBMService;
    @Autowired
    ClientServiceImpl clientService;
    @Autowired
    SaleInvoiceCapsuleServiceImpl saleInvoiceCapsuleService;
    @Autowired
    UsedForTestForAll usedForTestForAll;
    @Autowired
    UsedForTestForClient usedForTestForClient;
    @Autowired
    UsedForTestForProduct usedForTestForProduct;

    @Test
    public void validate_SaveSaleInvoiceCapsule(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosId());

        SaleInvoiceCapsuleDto saleInvoiceCapsuleDtoSaved = usedForTestForProduct.saveSaleInvoiceCapsuleDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceCapsuleService);
        assertNotNull(saleInvoiceCapsuleDtoSaved);
        assertNotNull(saleInvoiceCapsuleDtoSaved.getId());
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveSaleInvoiceCapsule_Invalid(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosId());

        SaleInvoiceCapsuleDto saleInvoiceCapsuleDtoSaved = usedForTestForProduct.saveSaleInvoiceCapsuleDto_Invalid(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceCapsuleService);
        assertNotNull(saleInvoiceCapsuleDtoSaved);
        assertNotNull(saleInvoiceCapsuleDtoSaved.getId());
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveSaleInvoiceCapsule_PointofsaleNull(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosId());

        posDtoSaved.setId(null);

        SaleInvoiceCapsuleDto saleInvoiceCapsuleDtoSaved = usedForTestForProduct.saveSaleInvoiceCapsuleDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceCapsuleService);
        assertNotNull(saleInvoiceCapsuleDtoSaved);
        assertNotNull(saleInvoiceCapsuleDtoSaved.getId());
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveSaleInvoiceCapsule_PointofsaleNotExist(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosId());

        posDtoSaved.setId(Long.valueOf(458769));

        SaleInvoiceCapsuleDto saleInvoiceCapsuleDtoSaved = usedForTestForProduct.saveSaleInvoiceCapsuleDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceCapsuleService);
        assertNotNull(saleInvoiceCapsuleDtoSaved);
        assertNotNull(saleInvoiceCapsuleDtoSaved.getId());
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveSaleInvoiceCapsule_UserBMNull(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosId());

        userBMDtoSaved.setId(null);

        SaleInvoiceCapsuleDto saleInvoiceCapsuleDtoSaved = usedForTestForProduct.saveSaleInvoiceCapsuleDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceCapsuleService);
        assertNotNull(saleInvoiceCapsuleDtoSaved);
        assertNotNull(saleInvoiceCapsuleDtoSaved.getId());
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveSaleInvoiceCapsule_UserBMNotExist(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosId());

        userBMDtoSaved.setId(Long.valueOf(4581236));

        SaleInvoiceCapsuleDto saleInvoiceCapsuleDtoSaved = usedForTestForProduct.saveSaleInvoiceCapsuleDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceCapsuleService);
        assertNotNull(saleInvoiceCapsuleDtoSaved);
        assertNotNull(saleInvoiceCapsuleDtoSaved.getId());
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveSaleInvoiceCapsule_DifferentPos(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        PointofsaleDto posDtoSaved1 = usedForTestForAll.savePointofsale(1, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved1);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosId());

        UserBMDto userBMDtoSaved1 = usedForTestForAll.saveUserBM(145, posDtoSaved1, userBMService);
        assertNotNull(userBMDtoSaved1);
        assertNotNull(userBMDtoSaved1.getBmPosId());

        SaleInvoiceCapsuleDto saleInvoiceCapsuleDtoSaved = usedForTestForProduct.saveSaleInvoiceCapsuleDto(0, userBMDtoSaved1,
                posDtoSaved, clientDtoSaved, saleInvoiceCapsuleService);
        assertNotNull(saleInvoiceCapsuleDtoSaved);
        assertNotNull(saleInvoiceCapsuleDtoSaved.getId());
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveSaleInvoiceCapsule_DifferentPos1(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        PointofsaleDto posDtoSaved1 = usedForTestForAll.savePointofsale(1, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved1);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosId());

        ClientDto clientDtoSaved1 = usedForTestForClient.saveClient(9, posDtoSaved1, clientService);
        assertNotNull(clientDtoSaved1);
        assertNotNull(clientDtoSaved1.getClientCaDto().getId());

        SaleInvoiceCapsuleDto saleInvoiceCapsuleDtoSaved = usedForTestForProduct.saveSaleInvoiceCapsuleDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved1, saleInvoiceCapsuleService);
        assertNotNull(saleInvoiceCapsuleDtoSaved);
        assertNotNull(saleInvoiceCapsuleDtoSaved.getId());
    }

    @Test(expected = DuplicateEntityException.class)
    public void validate_SaveSaleInvoiceCapsule_Duplicated(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosId());

        SaleInvoiceCapsuleDto saleInvoiceCapsuleDtoSaved = usedForTestForProduct.saveSaleInvoiceCapsuleDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceCapsuleService);
        assertNotNull(saleInvoiceCapsuleDtoSaved);
        assertNotNull(saleInvoiceCapsuleDtoSaved.getId());

        SaleInvoiceCapsuleDto saleInvoiceCapsuleDtoSaved1 = usedForTestForProduct.saveSaleInvoiceCapsuleDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceCapsuleService);
    }

    @Test
    public void validate_UpdateSaleInvoiceCapsule(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        ClientDto clientDtoSaved1 = usedForTestForClient.saveClient(7, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved1);
        assertNotNull(clientDtoSaved1.getClientCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosId());

        SaleInvoiceCapsuleDto saleInvoiceCapsuleDtoSaved = usedForTestForProduct.saveSaleInvoiceCapsuleDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceCapsuleService);
        assertNotNull(saleInvoiceCapsuleDtoSaved);
        assertNotNull(saleInvoiceCapsuleDtoSaved.getId());

        SaleInvoiceCapsuleDto saleInvoiceCapsuleDtoFound = saleInvoiceCapsuleService.findSaleInvoiceCapsuleById(
                saleInvoiceCapsuleDtoSaved.getId());
        assertNotNull(saleInvoiceCapsuleDtoFound);
        assertEquals(saleInvoiceCapsuleDtoSaved.getId(), saleInvoiceCapsuleDtoFound.getId());

        saleInvoiceCapsuleDtoFound.setSaleicapsCode("newCodeSale");
        saleInvoiceCapsuleDtoFound.setSaleicapsNumberchanged(BigDecimal.valueOf(5));
        saleInvoiceCapsuleDtoFound.setSaleicapsClientDto(clientDtoSaved1);

        SaleInvoiceCapsuleDto saleInvoiceCapsuleDtoUpdated = saleInvoiceCapsuleService.updateSaleInvoiceCapsule(saleInvoiceCapsuleDtoFound);
        assertNotNull(saleInvoiceCapsuleDtoUpdated);
        assertEquals(saleInvoiceCapsuleDtoFound.getSaleicapsCode(), saleInvoiceCapsuleDtoUpdated.getSaleicapsCode());
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_UpdateSaleInvoiceCapsule_UpdatePos(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        ClientDto clientDtoSaved1 = usedForTestForClient.saveClient(7, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved1);
        assertNotNull(clientDtoSaved1.getClientCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosId());

        SaleInvoiceCapsuleDto saleInvoiceCapsuleDtoSaved = usedForTestForProduct.saveSaleInvoiceCapsuleDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceCapsuleService);
        assertNotNull(saleInvoiceCapsuleDtoSaved);
        assertNotNull(saleInvoiceCapsuleDtoSaved.getId());

        SaleInvoiceCapsuleDto saleInvoiceCapsuleDtoFound = saleInvoiceCapsuleService.findSaleInvoiceCapsuleById(
                saleInvoiceCapsuleDtoSaved.getId());
        assertNotNull(saleInvoiceCapsuleDtoFound);
        assertEquals(saleInvoiceCapsuleDtoSaved.getId(), saleInvoiceCapsuleDtoFound.getId());

        saleInvoiceCapsuleDtoFound.setSaleicapsCode("newCodeSale");
        saleInvoiceCapsuleDtoFound.setSaleicapsNumberchanged(BigDecimal.valueOf(5));
        saleInvoiceCapsuleDtoFound.setSaleicapsClientDto(clientDtoSaved1);

        PointofsaleDto posDtoSaved1 = usedForTestForAll.savePointofsale(45, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved1);

        saleInvoiceCapsuleDtoFound.setSaleicapsPosId(posDtoSaved1.getId());

        SaleInvoiceCapsuleDto saleInvoiceCapsuleDtoUpdated = saleInvoiceCapsuleService.updateSaleInvoiceCapsule(saleInvoiceCapsuleDtoFound);
        assertNotNull(saleInvoiceCapsuleDtoUpdated);
        assertEquals(saleInvoiceCapsuleDtoFound.getSaleicapsCode(), saleInvoiceCapsuleDtoUpdated.getSaleicapsCode());

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_UpdateSaleInvoiceCapsule_UpdateUserbm(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        ClientDto clientDtoSaved1 = usedForTestForClient.saveClient(7, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved1);
        assertNotNull(clientDtoSaved1.getClientCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosId());

        SaleInvoiceCapsuleDto saleInvoiceCapsuleDtoSaved = usedForTestForProduct.saveSaleInvoiceCapsuleDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceCapsuleService);
        assertNotNull(saleInvoiceCapsuleDtoSaved);
        assertNotNull(saleInvoiceCapsuleDtoSaved.getId());

        SaleInvoiceCapsuleDto saleInvoiceCapsuleDtoFound = saleInvoiceCapsuleService.findSaleInvoiceCapsuleById(
                saleInvoiceCapsuleDtoSaved.getId());
        assertNotNull(saleInvoiceCapsuleDtoFound);
        assertEquals(saleInvoiceCapsuleDtoSaved.getId(), saleInvoiceCapsuleDtoFound.getId());

        saleInvoiceCapsuleDtoFound.setSaleicapsCode("newCodeSale");
        saleInvoiceCapsuleDtoFound.setSaleicapsNumberchanged(BigDecimal.valueOf(5));
        saleInvoiceCapsuleDtoFound.setSaleicapsClientDto(clientDtoSaved1);

        UserBMDto userBMDtoSaved1 = usedForTestForAll.saveUserBM(16, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved1);
        assertNotNull(userBMDtoSaved1.getBmPosId());

        saleInvoiceCapsuleDtoFound.setSaleicapsUserbmDto(userBMDtoSaved1);

        SaleInvoiceCapsuleDto saleInvoiceCapsuleDtoUpdated = saleInvoiceCapsuleService.updateSaleInvoiceCapsule(saleInvoiceCapsuleDtoFound);
        assertNotNull(saleInvoiceCapsuleDtoUpdated);
        assertEquals(saleInvoiceCapsuleDtoFound.getSaleicapsCode(), saleInvoiceCapsuleDtoUpdated.getSaleicapsCode());

    }


    @Test
    public void validate_DeleteSaleInvoiceCapsule(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosId());

        SaleInvoiceCapsuleDto saleInvoiceCapsuleDtoSaved = usedForTestForProduct.saveSaleInvoiceCapsuleDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceCapsuleService);
        assertNotNull(saleInvoiceCapsuleDtoSaved);
        assertNotNull(saleInvoiceCapsuleDtoSaved.getId());

        assertTrue(saleInvoiceCapsuleService.deleteSaleInvoiceCapsuleById(saleInvoiceCapsuleDtoSaved.getId()));
    }

    @Test
    public void validate_FindSaleInvoiceCapsuleByCode(){
        Date startDate1 = new Date();
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        ClientDto clientDtoSaved1 = usedForTestForClient.saveClient(6, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved1);
        assertNotNull(clientDtoSaved1.getClientCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosId());

        UserBMDto userBMDtoSaved1 = usedForTestForAll.saveUserBM(189, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved1);
        assertNotNull(userBMDtoSaved1.getBmPosId());

        SaleInvoiceCapsuleDto saleInvoiceCapsuleDtoSaved = usedForTestForProduct.saveSaleInvoiceCapsuleDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceCapsuleService);
        assertNotNull(saleInvoiceCapsuleDtoSaved);
        assertNotNull(saleInvoiceCapsuleDtoSaved.getId());

        SaleInvoiceCapsuleDto saleInvoiceCapsuleDtoFound = saleInvoiceCapsuleService.
                findSaleInvoiceCapsuleByCode(saleInvoiceCapsuleDtoSaved.getSaleicapsCode(), posDtoSaved.getId());
        assertNotNull(saleInvoiceCapsuleDtoFound);
        assertEquals(saleInvoiceCapsuleDtoSaved.getSaleicapsCode(), saleInvoiceCapsuleDtoFound.getSaleicapsCode());

        SaleInvoiceCapsuleDto saleInvoiceCapsuleDtoSaved1 = usedForTestForProduct.saveSaleInvoiceCapsuleDto(1, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceCapsuleService);
        assertNotNull(saleInvoiceCapsuleDtoSaved1);
        assertNotNull(saleInvoiceCapsuleDtoSaved1.getId());

        SaleInvoiceCapsuleDto saleInvoiceCapsuleDtoSaved2 = usedForTestForProduct.saveSaleInvoiceCapsuleDto(2, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceCapsuleService);
        assertNotNull(saleInvoiceCapsuleDtoSaved2);
        assertNotNull(saleInvoiceCapsuleDtoSaved2.getId());

        SaleInvoiceCapsuleDto saleInvoiceCapsuleDtoSaved3 = usedForTestForProduct.saveSaleInvoiceCapsuleDto(3, userBMDtoSaved1,
                posDtoSaved, clientDtoSaved, saleInvoiceCapsuleService);
        assertNotNull(saleInvoiceCapsuleDtoSaved3);
        assertNotNull(saleInvoiceCapsuleDtoSaved3.getId());

        SaleInvoiceCapsuleDto saleInvoiceCapsuleDtoSaved4 = usedForTestForProduct.saveSaleInvoiceCapsuleDto(4, userBMDtoSaved1,
                posDtoSaved, clientDtoSaved1, saleInvoiceCapsuleService);
        assertNotNull(saleInvoiceCapsuleDtoSaved4);
        assertNotNull(saleInvoiceCapsuleDtoSaved4.getId());

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

            ////////////////findAllSaleiCapsuleBetween

            List<SaleInvoiceCapsuleDto> saleInvoiceCapsuleDtoList = saleInvoiceCapsuleService.findAllSaleiCapsuleBetween(
                    startDate.toInstant(), endDate.toInstant());
            assertNotNull(saleInvoiceCapsuleDtoList);
            assertEquals(5,saleInvoiceCapsuleDtoList.size());

            ////////////////findPageSaleiCapsuleBetween

            Page<SaleInvoiceCapsuleDto> saleInvoiceCapsuleDtoPage = saleInvoiceCapsuleService.findPageSaleiCapsuleBetween(
                    startDate.toInstant(), endDate.toInstant(), 0, 2);
            assertNotNull(saleInvoiceCapsuleDtoList);
            assertEquals(3,saleInvoiceCapsuleDtoPage.getTotalPages());

            ////////////////findAllSaleiCapsuleofClientBetween

            List<SaleInvoiceCapsuleDto> saleInvoiceCapsuleDtoList1 = saleInvoiceCapsuleService.findAllSaleiCapsuleofClientBetween(
                    clientDtoSaved.getId(), startDate.toInstant(), endDate.toInstant());
            assertNotNull(saleInvoiceCapsuleDtoList1);
            assertEquals(4,saleInvoiceCapsuleDtoList1.size());

            ////////////////findPageSaleiCapsuleofClientBetween

            Page<SaleInvoiceCapsuleDto> saleInvoiceCapsuleDtoPage1 = saleInvoiceCapsuleService.findPageSaleiCapsuleofClientBetween(
                    clientDtoSaved.getId(), startDate.toInstant(), endDate.toInstant(), 0, 2);
            assertNotNull(saleInvoiceCapsuleDtoPage1);
            assertEquals(2,saleInvoiceCapsuleDtoPage1.getTotalPages());

            ////////////////findAllSaleiCapsuleofUserbmBetween

            List<SaleInvoiceCapsuleDto> saleInvoiceCapsuleDtoList2 = saleInvoiceCapsuleService.findAllSaleiCapsuleofUserbmBetween(
                    userBMDtoSaved.getId(), startDate.toInstant(), endDate.toInstant());
            assertNotNull(saleInvoiceCapsuleDtoList2);
            assertEquals(3,saleInvoiceCapsuleDtoList2.size());

            ////////////////findPageSaleiCapsuleofUserbmBetween

            Page<SaleInvoiceCapsuleDto> saleInvoiceCapsuleDtoPage2 = saleInvoiceCapsuleService.findPageSaleiCapsuleofUserbmBetween(
                    userBMDtoSaved.getId(), startDate.toInstant(), endDate.toInstant(), 0, 2);
            assertNotNull(saleInvoiceCapsuleDtoPage2);
            assertEquals(2,saleInvoiceCapsuleDtoPage2.getTotalPages());

            ////////////////findAllSaleiCapsuleofPosBetween

            List<SaleInvoiceCapsuleDto> saleInvoiceCapsuleDtoList3 = saleInvoiceCapsuleService.findAllSaleiCapsuleinPosBetween(
                    posDtoSaved.getId(), startDate.toInstant(), endDate.toInstant());
            assertNotNull(saleInvoiceCapsuleDtoList3);
            assertEquals(5,saleInvoiceCapsuleDtoList3.size());

            Page<SaleInvoiceCapsuleDto> saleInvoiceCapsuleDtoPage3 = saleInvoiceCapsuleService.findPageSaleiCapsuleinPosBetween(
                    posDtoSaved.getId(), startDate.toInstant(), endDate.toInstant(), 0, 2);
            assertNotNull(saleInvoiceCapsuleDtoPage3);
            assertEquals(3,saleInvoiceCapsuleDtoPage3.getTotalPages());

            ////////////////findAllSaleiCapsuleofUserbminPosBetween

            List<SaleInvoiceCapsuleDto> saleInvoiceCapsuleDtoList4 = saleInvoiceCapsuleService.findAllSaleiCapsuleofUserbminPosBetween(
                    userBMDtoSaved.getId(), posDtoSaved.getId(), startDate.toInstant(), endDate.toInstant());
            assertNotNull(saleInvoiceCapsuleDtoList4);
            assertEquals(3,saleInvoiceCapsuleDtoList4.size());

            Page<SaleInvoiceCapsuleDto> saleInvoiceCapsuleDtoPage4 = saleInvoiceCapsuleService.findPageSaleiCapsuleofUserbminPosBetween(
                    userBMDtoSaved.getId(), posDtoSaved.getId(), startDate.toInstant(), endDate.toInstant(), 0, 2);
            assertNotNull(saleInvoiceCapsuleDtoPage4);
            assertEquals(2,saleInvoiceCapsuleDtoPage4.getTotalPages());

            ////////////////findAllSaleiCapsuleofClientinPosBetween

            List<SaleInvoiceCapsuleDto> saleInvoiceCapsuleDtoList5 = saleInvoiceCapsuleService.findAllSaleiCapsuleofClientinPosBetween(
                    clientDtoSaved.getId(), posDtoSaved.getId(), startDate.toInstant(), endDate.toInstant());
            assertNotNull(saleInvoiceCapsuleDtoList5);
            assertEquals(4,saleInvoiceCapsuleDtoList5.size());

            Page<SaleInvoiceCapsuleDto> saleInvoiceCapsuleDtoPage5 = saleInvoiceCapsuleService.findPageSaleiCapsuleofClientinPosBetween(
                    clientDtoSaved.getId(), posDtoSaved.getId(), startDate.toInstant(), endDate.toInstant(), 0, 2);
            assertNotNull(saleInvoiceCapsuleDtoPage5);
            assertEquals(2,saleInvoiceCapsuleDtoPage5.getTotalPages());

            ////////////////findAllSaleiCapsuleofClientforUserbmBetween

            List<SaleInvoiceCapsuleDto> saleInvoiceCapsuleDtoList6 = saleInvoiceCapsuleService.findAllSaleiCapsuleofClientforUserbmBetween(
                    clientDtoSaved.getId(), userBMDtoSaved1.getId(), startDate.toInstant(), endDate.toInstant());
            assertNotNull(saleInvoiceCapsuleDtoList6);
            assertEquals(1,saleInvoiceCapsuleDtoList6.size());

            Page<SaleInvoiceCapsuleDto> saleInvoiceCapsuleDtoPage6 = saleInvoiceCapsuleService.findPageSaleiCapsuleofClientforUserbmBetween(
                    clientDtoSaved.getId(), userBMDtoSaved1.getId(), startDate.toInstant(), endDate.toInstant(), 0 , 2);
            assertNotNull(saleInvoiceCapsuleDtoPage6);
            assertEquals(1,saleInvoiceCapsuleDtoPage6.getTotalPages());

            ////////////////findAllSaleiCapsuleofClientforUserbminPosBetween

            List<SaleInvoiceCapsuleDto> saleInvoiceCapsuleDtoList7 = saleInvoiceCapsuleService.findAllSaleiCapsuleofClientforUserbminPosBetween(
                    clientDtoSaved1.getId(), userBMDtoSaved1.getId(), posDtoSaved.getId(), startDate.toInstant(), endDate.toInstant());
            assertNotNull(saleInvoiceCapsuleDtoList7);
            assertEquals(1,saleInvoiceCapsuleDtoList7.size());

            Page<SaleInvoiceCapsuleDto> saleInvoiceCapsuleDtoPage7 = saleInvoiceCapsuleService.findPageSaleiCapsuleofClientforUserbminPosBetween(
                    clientDtoSaved1.getId(), userBMDtoSaved1.getId(), posDtoSaved.getId(), startDate.toInstant(), endDate.toInstant(), 0, 2);
            assertNotNull(saleInvoiceCapsuleDtoPage7);
            assertEquals(1,saleInvoiceCapsuleDtoPage7.getTotalPages());




        } catch (Exception e){
            System.err.println("There is an exception not manage in the program means a big problem appears");
            e.printStackTrace();
        }

    }


}