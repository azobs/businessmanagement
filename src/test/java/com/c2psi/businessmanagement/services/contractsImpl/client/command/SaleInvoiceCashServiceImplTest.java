package com.c2psi.businessmanagement.services.contractsImpl.client.command;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.dtos.client.command.SaleInvoiceCashDto;
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
public class SaleInvoiceCashServiceImplTest {

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
    SaleInvoiceCashServiceImpl saleInvoiceCashService;
    @Autowired
    UsedForTestForAll usedForTestForAll;
    @Autowired
    UsedForTestForClient usedForTestForClient;
    @Autowired
    UsedForTestForProduct usedForTestForProduct;

    @Test
    public void validate_SaveSaleInvoiceCash(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosId());

        SaleInvoiceCashDto saleInvoiceCashDtoSaved = usedForTestForProduct.saveSaleInvoiceCashDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceCashService);
        assertNotNull(saleInvoiceCashDtoSaved);
        assertNotNull(saleInvoiceCashDtoSaved.getId());
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveSaleInvoiceCash_Invalid(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosId());

        SaleInvoiceCashDto saleInvoiceCashDtoSaved = usedForTestForProduct.saveSaleInvoiceCashDto_Invalid(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceCashService);
        assertNotNull(saleInvoiceCashDtoSaved);
        assertNotNull(saleInvoiceCashDtoSaved.getId());
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveSaleInvoiceCash_PointofsaleNull(){
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

        SaleInvoiceCashDto saleInvoiceCashDtoSaved = usedForTestForProduct.saveSaleInvoiceCashDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceCashService);
        assertNotNull(saleInvoiceCashDtoSaved);
        assertNotNull(saleInvoiceCashDtoSaved.getId());
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveSaleInvoiceCash_PointofsaleNotExist(){
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

        SaleInvoiceCashDto saleInvoiceCashDtoSaved = usedForTestForProduct.saveSaleInvoiceCashDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceCashService);
        assertNotNull(saleInvoiceCashDtoSaved);
        assertNotNull(saleInvoiceCashDtoSaved.getId());
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveSaleInvoiceCash_UserBMNull(){
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

        SaleInvoiceCashDto saleInvoiceCashDtoSaved = usedForTestForProduct.saveSaleInvoiceCashDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceCashService);
        assertNotNull(saleInvoiceCashDtoSaved);
        assertNotNull(saleInvoiceCashDtoSaved.getId());
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveSaleInvoiceCash_UserBMNotExist(){
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

        SaleInvoiceCashDto saleInvoiceCashDtoSaved = usedForTestForProduct.saveSaleInvoiceCashDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceCashService);
        assertNotNull(saleInvoiceCashDtoSaved);
        assertNotNull(saleInvoiceCashDtoSaved.getId());
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveSaleInvoiceCash_DifferentPos(){
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

        SaleInvoiceCashDto saleInvoiceCashDtoSaved = usedForTestForProduct.saveSaleInvoiceCashDto(0, userBMDtoSaved1,
                posDtoSaved, clientDtoSaved, saleInvoiceCashService);
        assertNotNull(saleInvoiceCashDtoSaved);
        assertNotNull(saleInvoiceCashDtoSaved.getId());
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveSaleInvoiceCash_DifferentPos1(){
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

        SaleInvoiceCashDto saleInvoiceCashDtoSaved = usedForTestForProduct.saveSaleInvoiceCashDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved1, saleInvoiceCashService);
        assertNotNull(saleInvoiceCashDtoSaved);
        assertNotNull(saleInvoiceCashDtoSaved.getId());
    }

    @Test(expected = DuplicateEntityException.class)
    public void validate_SaveSaleInvoiceCash_Duplicated(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosId());

        SaleInvoiceCashDto saleInvoiceCashDtoSaved = usedForTestForProduct.saveSaleInvoiceCashDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceCashService);
        assertNotNull(saleInvoiceCashDtoSaved);
        assertNotNull(saleInvoiceCashDtoSaved.getId());

        SaleInvoiceCashDto saleInvoiceCashDtoSaved1 = usedForTestForProduct.saveSaleInvoiceCashDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceCashService);
    }

    @Test
    public void validate_UpdateSaleInvoiceCash(){
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

        SaleInvoiceCashDto saleInvoiceCashDtoSaved = usedForTestForProduct.saveSaleInvoiceCashDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceCashService);
        assertNotNull(saleInvoiceCashDtoSaved);
        assertNotNull(saleInvoiceCashDtoSaved.getId());

        SaleInvoiceCashDto saleInvoiceCashDtoFound = saleInvoiceCashService.findSaleInvoiceCashById(
                saleInvoiceCashDtoSaved.getId());
        assertNotNull(saleInvoiceCashDtoFound);
        assertEquals(saleInvoiceCashDtoSaved.getId(), saleInvoiceCashDtoFound.getId());

        saleInvoiceCashDtoFound.setSaleicashCode("newCodeSale");
        saleInvoiceCashDtoFound.setSaleicashAmountpaid(BigDecimal.valueOf(75000));
        saleInvoiceCashDtoFound.setSaleicashClientDto(clientDtoSaved1);

        SaleInvoiceCashDto saleInvoiceCashDtoUpdated = saleInvoiceCashService.updateSaleInvoiceCash(saleInvoiceCashDtoFound);
        assertNotNull(saleInvoiceCashDtoUpdated);
        assertEquals(saleInvoiceCashDtoFound.getSaleicashCode(), saleInvoiceCashDtoUpdated.getSaleicashCode());
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_UpdateSaleInvoiceCash_UpdatePos(){
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

        SaleInvoiceCashDto saleInvoiceCashDtoSaved = usedForTestForProduct.saveSaleInvoiceCashDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceCashService);
        assertNotNull(saleInvoiceCashDtoSaved);
        assertNotNull(saleInvoiceCashDtoSaved.getId());

        SaleInvoiceCashDto saleInvoiceCashDtoFound = saleInvoiceCashService.findSaleInvoiceCashById(
                saleInvoiceCashDtoSaved.getId());
        assertNotNull(saleInvoiceCashDtoFound);
        assertEquals(saleInvoiceCashDtoSaved.getId(), saleInvoiceCashDtoFound.getId());

        saleInvoiceCashDtoFound.setSaleicashCode("newCodeSale");
        saleInvoiceCashDtoFound.setSaleicashAmountpaid(BigDecimal.valueOf(75000));
        saleInvoiceCashDtoFound.setSaleicashClientDto(clientDtoSaved1);

        PointofsaleDto posDtoSaved1 = usedForTestForAll.savePointofsale(45, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved1);

        saleInvoiceCashDtoFound.setSaleicashPosId(posDtoSaved1.getId());

        SaleInvoiceCashDto saleInvoiceCashDtoUpdated = saleInvoiceCashService.updateSaleInvoiceCash(saleInvoiceCashDtoFound);
        assertNotNull(saleInvoiceCashDtoUpdated);
        assertEquals(saleInvoiceCashDtoFound.getSaleicashCode(), saleInvoiceCashDtoUpdated.getSaleicashCode());

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_UpdateSaleInvoiceCash_UpdateUserbm(){
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

        SaleInvoiceCashDto saleInvoiceCashDtoSaved = usedForTestForProduct.saveSaleInvoiceCashDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceCashService);
        assertNotNull(saleInvoiceCashDtoSaved);
        assertNotNull(saleInvoiceCashDtoSaved.getId());

        SaleInvoiceCashDto saleInvoiceCashDtoFound = saleInvoiceCashService.findSaleInvoiceCashById(
                saleInvoiceCashDtoSaved.getId());
        assertNotNull(saleInvoiceCashDtoFound);
        assertEquals(saleInvoiceCashDtoSaved.getId(), saleInvoiceCashDtoFound.getId());

        saleInvoiceCashDtoFound.setSaleicashCode("newCodeSale");
        saleInvoiceCashDtoFound.setSaleicashAmountpaid(BigDecimal.valueOf(75000));
        saleInvoiceCashDtoFound.setSaleicashClientDto(clientDtoSaved1);

        UserBMDto userBMDtoSaved1 = usedForTestForAll.saveUserBM(16, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved1);
        assertNotNull(userBMDtoSaved1.getBmPosId());

        saleInvoiceCashDtoFound.setSaleicashUserbmDto(userBMDtoSaved1);

        SaleInvoiceCashDto saleInvoiceCashDtoUpdated = saleInvoiceCashService.updateSaleInvoiceCash(saleInvoiceCashDtoFound);
        assertNotNull(saleInvoiceCashDtoUpdated);
        assertEquals(saleInvoiceCashDtoFound.getSaleicashCode(), saleInvoiceCashDtoUpdated.getSaleicashCode());

    }


    @Test
    public void validate_DeleteSaleInvoiceCash(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosId());

        SaleInvoiceCashDto saleInvoiceCashDtoSaved = usedForTestForProduct.saveSaleInvoiceCashDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceCashService);
        assertNotNull(saleInvoiceCashDtoSaved);
        assertNotNull(saleInvoiceCashDtoSaved.getId());

        assertTrue(saleInvoiceCashService.deleteSaleInvoiceCashById(saleInvoiceCashDtoSaved.getId()));
    }

    @Test
    public void validate_FindSaleInvoiceCashByCode(){
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

        SaleInvoiceCashDto saleInvoiceCashDtoSaved = usedForTestForProduct.saveSaleInvoiceCashDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceCashService);
        assertNotNull(saleInvoiceCashDtoSaved);
        assertNotNull(saleInvoiceCashDtoSaved.getId());

        SaleInvoiceCashDto saleInvoiceCashDtoFound = saleInvoiceCashService.
                findSaleInvoiceCashByCode(saleInvoiceCashDtoSaved.getSaleicashCode(), posDtoSaved.getId());
        assertNotNull(saleInvoiceCashDtoFound);
        assertEquals(saleInvoiceCashDtoSaved.getSaleicashCode(), saleInvoiceCashDtoFound.getSaleicashCode());

        SaleInvoiceCashDto saleInvoiceCashDtoSaved1 = usedForTestForProduct.saveSaleInvoiceCashDto(1, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceCashService);
        assertNotNull(saleInvoiceCashDtoSaved1);
        assertNotNull(saleInvoiceCashDtoSaved1.getId());

        SaleInvoiceCashDto saleInvoiceCashDtoSaved2 = usedForTestForProduct.saveSaleInvoiceCashDto(2, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceCashService);
        assertNotNull(saleInvoiceCashDtoSaved2);
        assertNotNull(saleInvoiceCashDtoSaved2.getId());

        SaleInvoiceCashDto saleInvoiceCashDtoSaved3 = usedForTestForProduct.saveSaleInvoiceCashDto(3, userBMDtoSaved1,
                posDtoSaved, clientDtoSaved, saleInvoiceCashService);
        assertNotNull(saleInvoiceCashDtoSaved3);
        assertNotNull(saleInvoiceCashDtoSaved3.getId());

        SaleInvoiceCashDto saleInvoiceCashDtoSaved4 = usedForTestForProduct.saveSaleInvoiceCashDto(4, userBMDtoSaved1,
                posDtoSaved, clientDtoSaved1, saleInvoiceCashService);
        assertNotNull(saleInvoiceCashDtoSaved4);
        assertNotNull(saleInvoiceCashDtoSaved4.getId());

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

            ////////////////findAllSaleicashBetween

            List<SaleInvoiceCashDto> saleInvoiceCashDtoList = saleInvoiceCashService.findAllSaleicashBetween(
                    startDate.toInstant(), endDate.toInstant());
            assertNotNull(saleInvoiceCashDtoList);
            assertEquals(5,saleInvoiceCashDtoList.size());

            ////////////////findPageSaleicashBetween

            Page<SaleInvoiceCashDto> saleInvoiceCashDtoPage = saleInvoiceCashService.findPageSaleicashBetween(
                    startDate.toInstant(), endDate.toInstant(), 0, 2);
            assertNotNull(saleInvoiceCashDtoList);
            assertEquals(3,saleInvoiceCashDtoPage.getTotalPages());

            ////////////////findAllSaleicashofClientBetween

            List<SaleInvoiceCashDto> saleInvoiceCashDtoList1 = saleInvoiceCashService.findAllSaleicashofClientBetween(
                    clientDtoSaved.getId(), startDate.toInstant(), endDate.toInstant());
            assertNotNull(saleInvoiceCashDtoList1);
            assertEquals(4,saleInvoiceCashDtoList1.size());

            ////////////////findPageSaleicashofClientBetween

            Page<SaleInvoiceCashDto> saleInvoiceCashDtoPage1 = saleInvoiceCashService.findPageSaleicashofClientBetween(
                    clientDtoSaved.getId(), startDate.toInstant(), endDate.toInstant(), 0, 2);
            assertNotNull(saleInvoiceCashDtoPage1);
            assertEquals(2,saleInvoiceCashDtoPage1.getTotalPages());

            ////////////////findAllSaleicashofUserbmBetween

            List<SaleInvoiceCashDto> saleInvoiceCashDtoList2 = saleInvoiceCashService.findAllSaleicashofUserbmBetween(
                    userBMDtoSaved.getId(), startDate.toInstant(), endDate.toInstant());
            assertNotNull(saleInvoiceCashDtoList2);
            assertEquals(3,saleInvoiceCashDtoList2.size());

            ////////////////findPageSaleicashofUserbmBetween

            Page<SaleInvoiceCashDto> saleInvoiceCashDtoPage2 = saleInvoiceCashService.findPageSaleicashofUserbmBetween(
                    userBMDtoSaved.getId(), startDate.toInstant(), endDate.toInstant(), 0, 2);
            assertNotNull(saleInvoiceCashDtoPage2);
            assertEquals(2,saleInvoiceCashDtoPage2.getTotalPages());

            ////////////////findAllSaleicashofPosBetween

            List<SaleInvoiceCashDto> saleInvoiceCashDtoList3 = saleInvoiceCashService.findAllSaleicashinPosBetween(
                    posDtoSaved.getId(), startDate.toInstant(), endDate.toInstant());
            assertNotNull(saleInvoiceCashDtoList3);
            assertEquals(5,saleInvoiceCashDtoList3.size());

            Page<SaleInvoiceCashDto> saleInvoiceCashDtoPage3 = saleInvoiceCashService.findPageSaleicashinPosBetween(
                    posDtoSaved.getId(), startDate.toInstant(), endDate.toInstant(), 0, 2);
            assertNotNull(saleInvoiceCashDtoPage3);
            assertEquals(3,saleInvoiceCashDtoPage3.getTotalPages());

            ////////////////findAllSaleicashofUserbminPosBetween

            List<SaleInvoiceCashDto> saleInvoiceCashDtoList4 = saleInvoiceCashService.findAllSaleicashofUserbminPosBetween(
                    userBMDtoSaved.getId(), posDtoSaved.getId(), startDate.toInstant(), endDate.toInstant());
            assertNotNull(saleInvoiceCashDtoList4);
            assertEquals(3,saleInvoiceCashDtoList4.size());

            Page<SaleInvoiceCashDto> saleInvoiceCashDtoPage4 = saleInvoiceCashService.findPageSaleicashofUserbminPosBetween(
                    userBMDtoSaved.getId(), posDtoSaved.getId(), startDate.toInstant(), endDate.toInstant(), 0, 2);
            assertNotNull(saleInvoiceCashDtoPage4);
            assertEquals(2,saleInvoiceCashDtoPage4.getTotalPages());

            ////////////////findAllSaleicashofClientinPosBetween

            List<SaleInvoiceCashDto> saleInvoiceCashDtoList5 = saleInvoiceCashService.findAllSaleicashofClientinPosBetween(
                    clientDtoSaved.getId(), posDtoSaved.getId(), startDate.toInstant(), endDate.toInstant());
            assertNotNull(saleInvoiceCashDtoList5);
            assertEquals(4,saleInvoiceCashDtoList5.size());

            Page<SaleInvoiceCashDto> saleInvoiceCashDtoPage5 = saleInvoiceCashService.findPageSaleicashofClientinPosBetween(
                    clientDtoSaved.getId(), posDtoSaved.getId(), startDate.toInstant(), endDate.toInstant(), 0, 2);
            assertNotNull(saleInvoiceCashDtoPage5);
            assertEquals(2,saleInvoiceCashDtoPage5.getTotalPages());

            ////////////////findAllSaleicashofClientforUserbmBetween

            List<SaleInvoiceCashDto> saleInvoiceCashDtoList6 = saleInvoiceCashService.findAllSaleicashofClientforUserbmBetween(
                    clientDtoSaved.getId(), userBMDtoSaved1.getId(), startDate.toInstant(), endDate.toInstant());
            assertNotNull(saleInvoiceCashDtoList6);
            assertEquals(1,saleInvoiceCashDtoList6.size());

            Page<SaleInvoiceCashDto> saleInvoiceCashDtoPage6 = saleInvoiceCashService.findPageSaleicashofClientforUserbmBetween(
                    clientDtoSaved.getId(), userBMDtoSaved1.getId(), startDate.toInstant(), endDate.toInstant(), 0 , 2);
            assertNotNull(saleInvoiceCashDtoPage6);
            assertEquals(1,saleInvoiceCashDtoPage6.getTotalPages());

            ////////////////findAllSaleicashofClientforUserbminPosBetween

            List<SaleInvoiceCashDto> saleInvoiceCashDtoList7 = saleInvoiceCashService.findAllSaleicashofClientforUserbminPosBetween(
                    clientDtoSaved1.getId(), userBMDtoSaved1.getId(), posDtoSaved.getId(), startDate.toInstant(), endDate.toInstant());
            assertNotNull(saleInvoiceCashDtoList7);
            assertEquals(1,saleInvoiceCashDtoList7.size());

            Page<SaleInvoiceCashDto> saleInvoiceCashDtoPage7 = saleInvoiceCashService.findPageSaleicashofClientforUserbminPosBetween(
                    clientDtoSaved1.getId(), userBMDtoSaved1.getId(), posDtoSaved.getId(), startDate.toInstant(), endDate.toInstant(), 0, 2);
            assertNotNull(saleInvoiceCashDtoPage7);
            assertEquals(1,saleInvoiceCashDtoPage7.getTotalPages());




        } catch (Exception e){
            System.err.println("There is an exception not manage in the program means a big problem appears");
            e.printStackTrace();
        }

    }


}