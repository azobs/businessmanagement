package com.c2psi.businessmanagement.services.contractsImpl.client.command;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.dtos.client.command.SaleInvoiceDamageDto;
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
public class SaleInvoiceDamageServiceImplTest {
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
    SaleInvoiceDamageServiceImpl saleInvoiceDamageService;
    @Autowired
    UsedForTestForAll usedForTestForAll;
    @Autowired
    UsedForTestForClient usedForTestForClient;
    @Autowired
    UsedForTestForProduct usedForTestForProduct;

    @Test
    public void validate_SaveSaleInvoiceDamage(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosId());

        SaleInvoiceDamageDto saleInvoiceDamageDtoSaved = usedForTestForProduct.saveSaleInvoiceDamageDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceDamageService);
        assertNotNull(saleInvoiceDamageDtoSaved);
        assertNotNull(saleInvoiceDamageDtoSaved.getId());
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveSaleInvoiceDamage_Invalid(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosId());

        SaleInvoiceDamageDto saleInvoiceDamageDtoSaved = usedForTestForProduct.saveSaleInvoiceDamageDto_Invalid(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceDamageService);
        assertNotNull(saleInvoiceDamageDtoSaved);
        assertNotNull(saleInvoiceDamageDtoSaved.getId());
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveSaleInvoiceDamage_PointofsaleNull(){
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

        SaleInvoiceDamageDto saleInvoiceDamageDtoSaved = usedForTestForProduct.saveSaleInvoiceDamageDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceDamageService);
        assertNotNull(saleInvoiceDamageDtoSaved);
        assertNotNull(saleInvoiceDamageDtoSaved.getId());
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveSaleInvoiceDamage_PointofsaleNotExist(){
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

        SaleInvoiceDamageDto saleInvoiceDamageDtoSaved = usedForTestForProduct.saveSaleInvoiceDamageDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceDamageService);
        assertNotNull(saleInvoiceDamageDtoSaved);
        assertNotNull(saleInvoiceDamageDtoSaved.getId());
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveSaleInvoiceDamage_UserBMNull(){
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

        SaleInvoiceDamageDto saleInvoiceDamageDtoSaved = usedForTestForProduct.saveSaleInvoiceDamageDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceDamageService);
        assertNotNull(saleInvoiceDamageDtoSaved);
        assertNotNull(saleInvoiceDamageDtoSaved.getId());
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveSaleInvoiceDamage_UserBMNotExist(){
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

        SaleInvoiceDamageDto saleInvoiceDamageDtoSaved = usedForTestForProduct.saveSaleInvoiceDamageDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceDamageService);
        assertNotNull(saleInvoiceDamageDtoSaved);
        assertNotNull(saleInvoiceDamageDtoSaved.getId());
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveSaleInvoiceDamage_DifferentPos(){
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

        SaleInvoiceDamageDto saleInvoiceDamageDtoSaved = usedForTestForProduct.saveSaleInvoiceDamageDto(0, userBMDtoSaved1,
                posDtoSaved, clientDtoSaved, saleInvoiceDamageService);
        assertNotNull(saleInvoiceDamageDtoSaved);
        assertNotNull(saleInvoiceDamageDtoSaved.getId());
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveSaleInvoiceDamage_DifferentPos1(){
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

        SaleInvoiceDamageDto saleInvoiceDamageDtoSaved = usedForTestForProduct.saveSaleInvoiceDamageDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved1, saleInvoiceDamageService);
        assertNotNull(saleInvoiceDamageDtoSaved);
        assertNotNull(saleInvoiceDamageDtoSaved.getId());
    }

    @Test(expected = DuplicateEntityException.class)
    public void validate_SaveSaleInvoiceDamage_Duplicated(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosId());

        SaleInvoiceDamageDto saleInvoiceDamageDtoSaved = usedForTestForProduct.saveSaleInvoiceDamageDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceDamageService);
        assertNotNull(saleInvoiceDamageDtoSaved);
        assertNotNull(saleInvoiceDamageDtoSaved.getId());

        SaleInvoiceDamageDto saleInvoiceDamageDtoSaved1 = usedForTestForProduct.saveSaleInvoiceDamageDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceDamageService);
    }

    @Test
    public void validate_UpdateSaleInvoiceDamage(){
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

        SaleInvoiceDamageDto saleInvoiceDamageDtoSaved = usedForTestForProduct.saveSaleInvoiceDamageDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceDamageService);
        assertNotNull(saleInvoiceDamageDtoSaved);
        assertNotNull(saleInvoiceDamageDtoSaved.getId());

        SaleInvoiceDamageDto saleInvoiceDamageDtoFound = saleInvoiceDamageService.findSaleInvoiceDamageById(
                saleInvoiceDamageDtoSaved.getId());
        assertNotNull(saleInvoiceDamageDtoFound);
        assertEquals(saleInvoiceDamageDtoSaved.getId(), saleInvoiceDamageDtoFound.getId());

        saleInvoiceDamageDtoFound.setSaleidamCode("newCodeSale");
        saleInvoiceDamageDtoFound.setSaleidamNumberchanged(BigDecimal.valueOf(5));
        saleInvoiceDamageDtoFound.setSaleidamClientDto(clientDtoSaved1);

        SaleInvoiceDamageDto saleInvoiceDamageDtoUpdated = saleInvoiceDamageService.updateSaleInvoiceDamage(saleInvoiceDamageDtoFound);
        assertNotNull(saleInvoiceDamageDtoUpdated);
        assertEquals(saleInvoiceDamageDtoFound.getSaleidamCode(), saleInvoiceDamageDtoUpdated.getSaleidamCode());
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_UpdateSaleInvoiceDamage_UpdatePos(){
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

        SaleInvoiceDamageDto saleInvoiceDamageDtoSaved = usedForTestForProduct.saveSaleInvoiceDamageDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceDamageService);
        assertNotNull(saleInvoiceDamageDtoSaved);
        assertNotNull(saleInvoiceDamageDtoSaved.getId());

        SaleInvoiceDamageDto saleInvoiceDamageDtoFound = saleInvoiceDamageService.findSaleInvoiceDamageById(
                saleInvoiceDamageDtoSaved.getId());
        assertNotNull(saleInvoiceDamageDtoFound);
        assertEquals(saleInvoiceDamageDtoSaved.getId(), saleInvoiceDamageDtoFound.getId());

        saleInvoiceDamageDtoFound.setSaleidamCode("newCodeSale");
        saleInvoiceDamageDtoFound.setSaleidamNumberchanged(BigDecimal.valueOf(5));
        saleInvoiceDamageDtoFound.setSaleidamClientDto(clientDtoSaved1);

        PointofsaleDto posDtoSaved1 = usedForTestForAll.savePointofsale(45, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved1);

        saleInvoiceDamageDtoFound.setSaleidamPosId(posDtoSaved1.getId());

        SaleInvoiceDamageDto saleInvoiceDamageDtoUpdated = saleInvoiceDamageService.updateSaleInvoiceDamage(saleInvoiceDamageDtoFound);
        assertNotNull(saleInvoiceDamageDtoUpdated);
        assertEquals(saleInvoiceDamageDtoFound.getSaleidamCode(), saleInvoiceDamageDtoUpdated.getSaleidamCode());

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_UpdateSaleInvoiceDamage_UpdateUserbm(){
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

        SaleInvoiceDamageDto saleInvoiceDamageDtoSaved = usedForTestForProduct.saveSaleInvoiceDamageDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceDamageService);
        assertNotNull(saleInvoiceDamageDtoSaved);
        assertNotNull(saleInvoiceDamageDtoSaved.getId());

        SaleInvoiceDamageDto saleInvoiceDamageDtoFound = saleInvoiceDamageService.findSaleInvoiceDamageById(
                saleInvoiceDamageDtoSaved.getId());
        assertNotNull(saleInvoiceDamageDtoFound);
        assertEquals(saleInvoiceDamageDtoSaved.getId(), saleInvoiceDamageDtoFound.getId());

        saleInvoiceDamageDtoFound.setSaleidamCode("newCodeSale");
        saleInvoiceDamageDtoFound.setSaleidamNumberchanged(BigDecimal.valueOf(5));
        saleInvoiceDamageDtoFound.setSaleidamClientDto(clientDtoSaved1);

        UserBMDto userBMDtoSaved1 = usedForTestForAll.saveUserBM(16, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved1);
        assertNotNull(userBMDtoSaved1.getBmPosId());

        saleInvoiceDamageDtoFound.setSaleidamUserbmDto(userBMDtoSaved1);

        SaleInvoiceDamageDto saleInvoiceDamageDtoUpdated = saleInvoiceDamageService.updateSaleInvoiceDamage(saleInvoiceDamageDtoFound);
        assertNotNull(saleInvoiceDamageDtoUpdated);
        assertEquals(saleInvoiceDamageDtoFound.getSaleidamCode(), saleInvoiceDamageDtoUpdated.getSaleidamCode());

    }


    @Test
    public void validate_DeleteSaleInvoiceDamage(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosId());

        SaleInvoiceDamageDto saleInvoiceDamageDtoSaved = usedForTestForProduct.saveSaleInvoiceDamageDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceDamageService);
        assertNotNull(saleInvoiceDamageDtoSaved);
        assertNotNull(saleInvoiceDamageDtoSaved.getId());

        assertTrue(saleInvoiceDamageService.deleteSaleInvoiceDamageById(saleInvoiceDamageDtoSaved.getId()));
    }

    @Test
    public void validate_FindSaleInvoiceDamageByCode(){
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

        SaleInvoiceDamageDto saleInvoiceDamageDtoSaved = usedForTestForProduct.saveSaleInvoiceDamageDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceDamageService);
        assertNotNull(saleInvoiceDamageDtoSaved);
        assertNotNull(saleInvoiceDamageDtoSaved.getId());

        SaleInvoiceDamageDto saleInvoiceDamageDtoFound = saleInvoiceDamageService.
                findSaleInvoiceDamageByCode(saleInvoiceDamageDtoSaved.getSaleidamCode(), posDtoSaved.getId());
        assertNotNull(saleInvoiceDamageDtoFound);
        assertEquals(saleInvoiceDamageDtoSaved.getSaleidamCode(), saleInvoiceDamageDtoFound.getSaleidamCode());

        SaleInvoiceDamageDto saleInvoiceDamageDtoSaved1 = usedForTestForProduct.saveSaleInvoiceDamageDto(1, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceDamageService);
        assertNotNull(saleInvoiceDamageDtoSaved1);
        assertNotNull(saleInvoiceDamageDtoSaved1.getId());

        SaleInvoiceDamageDto saleInvoiceDamageDtoSaved2 = usedForTestForProduct.saveSaleInvoiceDamageDto(2, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceDamageService);
        assertNotNull(saleInvoiceDamageDtoSaved2);
        assertNotNull(saleInvoiceDamageDtoSaved2.getId());

        SaleInvoiceDamageDto saleInvoiceDamageDtoSaved3 = usedForTestForProduct.saveSaleInvoiceDamageDto(3, userBMDtoSaved1,
                posDtoSaved, clientDtoSaved, saleInvoiceDamageService);
        assertNotNull(saleInvoiceDamageDtoSaved3);
        assertNotNull(saleInvoiceDamageDtoSaved3.getId());

        SaleInvoiceDamageDto saleInvoiceDamageDtoSaved4 = usedForTestForProduct.saveSaleInvoiceDamageDto(4, userBMDtoSaved1,
                posDtoSaved, clientDtoSaved1, saleInvoiceDamageService);
        assertNotNull(saleInvoiceDamageDtoSaved4);
        assertNotNull(saleInvoiceDamageDtoSaved4.getId());

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

            ////////////////findAllSaleiDamageBetween

            List<SaleInvoiceDamageDto> saleInvoiceDamageDtoList = saleInvoiceDamageService.findAllSaleiDamageBetween(
                    startDate.toInstant(), endDate.toInstant());
            assertNotNull(saleInvoiceDamageDtoList);
            assertEquals(5,saleInvoiceDamageDtoList.size());

            ////////////////findPageSaleiDamageBetween

            Page<SaleInvoiceDamageDto> saleInvoiceDamageDtoPage = saleInvoiceDamageService.findPageSaleiDamageBetween(
                    startDate.toInstant(), endDate.toInstant(), 0, 2);
            assertNotNull(saleInvoiceDamageDtoList);
            assertEquals(3,saleInvoiceDamageDtoPage.getTotalPages());

            ////////////////findAllSaleiDamageofClientBetween

            List<SaleInvoiceDamageDto> saleInvoiceDamageDtoList1 = saleInvoiceDamageService.findAllSaleiDamageofClientBetween(
                    clientDtoSaved.getId(), startDate.toInstant(), endDate.toInstant());
            assertNotNull(saleInvoiceDamageDtoList1);
            assertEquals(4,saleInvoiceDamageDtoList1.size());

            ////////////////findPageSaleiDamageofClientBetween

            Page<SaleInvoiceDamageDto> saleInvoiceDamageDtoPage1 = saleInvoiceDamageService.findPageSaleiDamageofClientBetween(
                    clientDtoSaved.getId(), startDate.toInstant(), endDate.toInstant(), 0, 2);
            assertNotNull(saleInvoiceDamageDtoPage1);
            assertEquals(2,saleInvoiceDamageDtoPage1.getTotalPages());

            ////////////////findAllSaleiDamageofUserbmBetween

            List<SaleInvoiceDamageDto> saleInvoiceDamageDtoList2 = saleInvoiceDamageService.findAllSaleiDamageofUserbmBetween(
                    userBMDtoSaved.getId(), startDate.toInstant(), endDate.toInstant());
            assertNotNull(saleInvoiceDamageDtoList2);
            assertEquals(3,saleInvoiceDamageDtoList2.size());

            ////////////////findPageSaleiDamageofUserbmBetween

            Page<SaleInvoiceDamageDto> saleInvoiceDamageDtoPage2 = saleInvoiceDamageService.findPageSaleiDamageofUserbmBetween(
                    userBMDtoSaved.getId(), startDate.toInstant(), endDate.toInstant(), 0, 2);
            assertNotNull(saleInvoiceDamageDtoPage2);
            assertEquals(2,saleInvoiceDamageDtoPage2.getTotalPages());

            ////////////////findAllSaleiDamageofPosBetween

            List<SaleInvoiceDamageDto> saleInvoiceDamageDtoList3 = saleInvoiceDamageService.findAllSaleiDamageinPosBetween(
                    posDtoSaved.getId(), startDate.toInstant(), endDate.toInstant());
            assertNotNull(saleInvoiceDamageDtoList3);
            assertEquals(5,saleInvoiceDamageDtoList3.size());

            Page<SaleInvoiceDamageDto> saleInvoiceDamageDtoPage3 = saleInvoiceDamageService.findPageSaleiDamageinPosBetween(
                    posDtoSaved.getId(), startDate.toInstant(), endDate.toInstant(), 0, 2);
            assertNotNull(saleInvoiceDamageDtoPage3);
            assertEquals(3,saleInvoiceDamageDtoPage3.getTotalPages());

            ////////////////findAllSaleiDamageofUserbminPosBetween

            List<SaleInvoiceDamageDto> saleInvoiceDamageDtoList4 = saleInvoiceDamageService.findAllSaleiDamageofUserbminPosBetween(
                    userBMDtoSaved.getId(), posDtoSaved.getId(), startDate.toInstant(), endDate.toInstant());
            assertNotNull(saleInvoiceDamageDtoList4);
            assertEquals(3,saleInvoiceDamageDtoList4.size());

            Page<SaleInvoiceDamageDto> saleInvoiceDamageDtoPage4 = saleInvoiceDamageService.findPageSaleiDamageofUserbminPosBetween(
                    userBMDtoSaved.getId(), posDtoSaved.getId(), startDate.toInstant(), endDate.toInstant(), 0, 2);
            assertNotNull(saleInvoiceDamageDtoPage4);
            assertEquals(2,saleInvoiceDamageDtoPage4.getTotalPages());

            ////////////////findAllSaleiDamageofClientinPosBetween

            List<SaleInvoiceDamageDto> saleInvoiceDamageDtoList5 = saleInvoiceDamageService.findAllSaleiDamageofClientinPosBetween(
                    clientDtoSaved.getId(), posDtoSaved.getId(), startDate.toInstant(), endDate.toInstant());
            assertNotNull(saleInvoiceDamageDtoList5);
            assertEquals(4,saleInvoiceDamageDtoList5.size());

            Page<SaleInvoiceDamageDto> saleInvoiceDamageDtoPage5 = saleInvoiceDamageService.findPageSaleiDamageofClientinPosBetween(
                    clientDtoSaved.getId(), posDtoSaved.getId(), startDate.toInstant(), endDate.toInstant(), 0, 2);
            assertNotNull(saleInvoiceDamageDtoPage5);
            assertEquals(2,saleInvoiceDamageDtoPage5.getTotalPages());

            ////////////////findAllSaleiDamageofClientforUserbmBetween

            List<SaleInvoiceDamageDto> saleInvoiceDamageDtoList6 = saleInvoiceDamageService.findAllSaleiDamageofClientforUserbmBetween(
                    clientDtoSaved.getId(), userBMDtoSaved1.getId(), startDate.toInstant(), endDate.toInstant());
            assertNotNull(saleInvoiceDamageDtoList6);
            assertEquals(1,saleInvoiceDamageDtoList6.size());

            Page<SaleInvoiceDamageDto> saleInvoiceDamageDtoPage6 = saleInvoiceDamageService.findPageSaleiDamageofClientforUserbmBetween(
                    clientDtoSaved.getId(), userBMDtoSaved1.getId(), startDate.toInstant(), endDate.toInstant(), 0 , 2);
            assertNotNull(saleInvoiceDamageDtoPage6);
            assertEquals(1,saleInvoiceDamageDtoPage6.getTotalPages());

            ////////////////findAllSaleiDamageofClientforUserbminPosBetween

            List<SaleInvoiceDamageDto> saleInvoiceDamageDtoList7 = saleInvoiceDamageService.findAllSaleiDamageofClientforUserbminPosBetween(
                    clientDtoSaved1.getId(), userBMDtoSaved1.getId(), posDtoSaved.getId(), startDate.toInstant(), endDate.toInstant());
            assertNotNull(saleInvoiceDamageDtoList7);
            assertEquals(1,saleInvoiceDamageDtoList7.size());

            Page<SaleInvoiceDamageDto> saleInvoiceDamageDtoPage7 = saleInvoiceDamageService.findPageSaleiDamageofClientforUserbminPosBetween(
                    clientDtoSaved1.getId(), userBMDtoSaved1.getId(), posDtoSaved.getId(), startDate.toInstant(), endDate.toInstant(), 0, 2);
            assertNotNull(saleInvoiceDamageDtoPage7);
            assertEquals(1,saleInvoiceDamageDtoPage7.getTotalPages());




        } catch (Exception e){
            System.err.println("There is an exception not manage in the program means a big problem appears");
            e.printStackTrace();
        }

    }

}