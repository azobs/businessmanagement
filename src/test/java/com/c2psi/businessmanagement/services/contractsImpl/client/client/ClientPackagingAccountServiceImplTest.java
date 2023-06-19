package com.c2psi.businessmanagement.services.contractsImpl.client.client;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientPackagingAccountDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientPackagingOperationDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.product.PackagingDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;
import com.c2psi.businessmanagement.exceptions.DuplicateEntityException;
import com.c2psi.businessmanagement.exceptions.InvalidEntityException;
import com.c2psi.businessmanagement.services.contractsImpl.UsedForTestForAll;
import com.c2psi.businessmanagement.services.contractsImpl.client.UsedForTestForClient;
import com.c2psi.businessmanagement.services.contractsImpl.pos.pos.EnterpriseServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.pos.pos.PointofsaleServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.pos.userbm.UserBMServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.price.CurrencyConversionServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.price.CurrencyServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.product.PackagingServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.product.UsedForTestForProduct;
import com.c2psi.businessmanagement.services.contractsImpl.stock.provider.ProviderServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.provider.UsedForTestForProvider;
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
public class ClientPackagingAccountServiceImplTest {

    @Autowired
    EnterpriseServiceImpl enterpriseService;
    @Autowired
    PointofsaleServiceImpl pointofsaleService;
    @Autowired
    UserBMServiceImpl userBMService;
    @Autowired
    CurrencyServiceImpl currencyService;
    @Autowired
    CurrencyConversionServiceImpl currencyConversionService;
    @Autowired
    ClientPackagingAccountServiceImpl clientPackagingAccountService;
    @Autowired
    ClientPackagingOperationServiceImpl clientPackagingOperationService;
    @Autowired
    PackagingServiceImpl packagingService;
    @Autowired
    ClientServiceImpl clientService;
    @Autowired
    ProviderServiceImpl providerService;
    @Autowired
    UsedForTestForAll usedForTestForAll;
    @Autowired
    UsedForTestForProduct usedForTestForProduct;
    @Autowired
    UsedForTestForClient usedForTestForClient;
    @Autowired
    UsedForTestForProvider usedForTestForProvider;

    @Test
    public void validate_saveClientPackagingAccount(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(0, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        PackagingDto packagingDtoSaved = usedForTestForProduct.savePackaging(0, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved);

        ClientPackagingAccountDto clientPackagingAccountDtoSaved = usedForTestForClient.saveClientPackagingAccount(0,
                clientDtoSaved, packagingDtoSaved, clientPackagingAccountService);
        assertNotNull(clientPackagingAccountDtoSaved);
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_saveClientPackagingAccount_Invalid(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(0, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        PackagingDto packagingDtoSaved = usedForTestForProduct.savePackaging(0, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved);

        ClientPackagingAccountDto clientPackagingAccountDtoSaved = usedForTestForClient.saveClientPackagingAccount_Invalid(0,
                clientDtoSaved, packagingDtoSaved, clientPackagingAccountService);
        assertNotNull(clientPackagingAccountDtoSaved);
    }

    @Test(expected = DuplicateEntityException.class)
    public void validate_saveClientPackagingAccount_Duplicated(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(0, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        PackagingDto packagingDtoSaved = usedForTestForProduct.savePackaging(0, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved);

        ClientPackagingAccountDto clientPackagingAccountDtoSaved = usedForTestForClient.saveClientPackagingAccount(0,
                clientDtoSaved, packagingDtoSaved, clientPackagingAccountService);
        assertNotNull(clientPackagingAccountDtoSaved);
        ClientPackagingAccountDto clientPackagingAccountDtoSaved1 = usedForTestForClient.saveClientPackagingAccount(10,
                clientDtoSaved, packagingDtoSaved, clientPackagingAccountService);
        //The above line is supposed to launch the expected exception

    }

    @Test
    public void validate_FindClientPackagingAccountById() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(0, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        PackagingDto packagingDtoSaved = usedForTestForProduct.savePackaging(0, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved);

        ClientPackagingAccountDto clientPackagingAccountDtoSaved = usedForTestForClient.saveClientPackagingAccount(0,
                clientDtoSaved, packagingDtoSaved, clientPackagingAccountService);
        assertNotNull(clientPackagingAccountDtoSaved);

        ClientPackagingAccountDto clientPackagingAccountDtoFound = clientPackagingAccountService.findClientPackagingAccountById(
                clientPackagingAccountDtoSaved.getId());
        assertNotNull(clientPackagingAccountDtoFound);
        assertEquals(clientPackagingAccountDtoSaved.getId(), clientPackagingAccountDtoFound.getId());
    }

    @Test
    public void validate_FindClientPackagingAccountInPos(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(0, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        PackagingDto packagingDtoSaved = usedForTestForProduct.savePackaging(0, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved);

        ClientPackagingAccountDto clientPackagingAccountDtoSaved = usedForTestForClient.saveClientPackagingAccount(0,
                clientDtoSaved, packagingDtoSaved, clientPackagingAccountService);
        assertNotNull(clientPackagingAccountDtoSaved);

        ClientPackagingAccountDto clientPackagingAccountDtoFound = clientPackagingAccountService.findClientPackagingAccount(
                packagingDtoSaved.getId(), clientDtoSaved.getId());
        assertNotNull(clientPackagingAccountDtoFound);
        assertEquals(clientPackagingAccountDtoSaved.getId(), clientPackagingAccountDtoFound.getId());
    }

    @Test
    public void validate_FindAllPackagingAccountInPos() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(0, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        PackagingDto packagingDtoSaved = usedForTestForProduct.savePackaging(0, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved);

        ClientPackagingAccountDto clientPackagingAccountDtoSaved = usedForTestForClient.saveClientPackagingAccount(0,
                clientDtoSaved, packagingDtoSaved, clientPackagingAccountService);
        assertNotNull(clientPackagingAccountDtoSaved);

        PackagingDto packagingDtoSaved1 = usedForTestForProduct.savePackaging(1, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved1);

        ClientPackagingAccountDto clientPackagingAccountDtoSaved1 = usedForTestForClient.saveClientPackagingAccount(10,
                clientDtoSaved, packagingDtoSaved1, clientPackagingAccountService);
        assertNotNull(clientPackagingAccountDtoSaved1);

        PackagingDto packagingDtoSaved2 = usedForTestForProduct.savePackaging(2, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved2);

        ClientPackagingAccountDto clientPackagingAccountDtoSaved2 = usedForTestForClient.saveClientPackagingAccount(10,
                clientDtoSaved, packagingDtoSaved2, clientPackagingAccountService);
        assertNotNull(clientPackagingAccountDtoSaved2);

        List<ClientPackagingAccountDto> clientPackagingAccountDtoList = clientPackagingAccountService.findAllPackagingAccountofClient(
                clientDtoSaved.getId());
        assertNotNull(clientPackagingAccountDtoList);
        assertEquals(3, clientPackagingAccountDtoList.size());
    }

    @Test
    public void validate_FindPagePackagingAccountInPos() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(0, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        PackagingDto packagingDtoSaved = usedForTestForProduct.savePackaging(0, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved);

        ClientPackagingAccountDto clientPackagingAccountDtoSaved = usedForTestForClient.saveClientPackagingAccount(0,
                clientDtoSaved, packagingDtoSaved, clientPackagingAccountService);
        assertNotNull(clientPackagingAccountDtoSaved);

        PackagingDto packagingDtoSaved1 = usedForTestForProduct.savePackaging(1, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved1);

        ClientPackagingAccountDto clientPackagingAccountDtoSaved1 = usedForTestForClient.saveClientPackagingAccount(10,
                clientDtoSaved, packagingDtoSaved1, clientPackagingAccountService);
        assertNotNull(clientPackagingAccountDtoSaved1);

        PackagingDto packagingDtoSaved2 = usedForTestForProduct.savePackaging(2, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved2);

        ClientPackagingAccountDto clientPackagingAccountDtoSaved2 = usedForTestForClient.saveClientPackagingAccount(10,
                clientDtoSaved, packagingDtoSaved2, clientPackagingAccountService);
        assertNotNull(clientPackagingAccountDtoSaved2);

        Page<ClientPackagingAccountDto> clientPackagingAccountDtoPage = clientPackagingAccountService.findPagePackagingAccountofClient(
                clientDtoSaved.getId(), 0, 2);
        assertNotNull(clientPackagingAccountDtoPage);
        assertEquals(2, clientPackagingAccountDtoPage.getTotalPages());
    }

    @Test
    public void validate_DeleteClientPackagingAccountById() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(0, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        PackagingDto packagingDtoSaved = usedForTestForProduct.savePackaging(0, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved);

        ClientPackagingAccountDto clientPackagingAccountDtoSaved = usedForTestForClient.saveClientPackagingAccount(0,
                clientDtoSaved, packagingDtoSaved, clientPackagingAccountService);
        assertNotNull(clientPackagingAccountDtoSaved);

        ClientPackagingAccountDto clientPackagingAccountDtoFound = clientPackagingAccountService.findClientPackagingAccountById(
                clientPackagingAccountDtoSaved.getId());
        assertNotNull(clientPackagingAccountDtoFound);
        assertEquals(clientPackagingAccountDtoSaved.getId(), clientPackagingAccountDtoFound.getId());

        assertTrue(clientPackagingAccountService.deleteClientPackagingAccountById(clientPackagingAccountDtoFound.getId()));
    }

    @Test
    public void validate_savePackagingOperationforClient(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(0, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        PackagingDto packagingDtoSaved = usedForTestForProduct.savePackaging(0, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved);

        ClientPackagingAccountDto clientPackagingAccountDtoSaved = usedForTestForClient.saveClientPackagingAccount(0,
                clientDtoSaved, packagingDtoSaved, clientPackagingAccountService);
        assertNotNull(clientPackagingAccountDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(12, userBMService);
        BigDecimal qteToSave = BigDecimal.valueOf(15);

        assertTrue(clientPackagingAccountService.savePackagingOperationforClient(clientPackagingAccountDtoSaved.getId(), qteToSave,
                OperationType.Credit, userBMDtoSaved.getId(), "credit ", "sdsds"));

        ClientPackagingAccountDto clientPackagingAccountDtoFound =clientPackagingAccountService.findClientPackagingAccountById(
               clientPackagingAccountDtoSaved.getId());
        System.err.println("solde == "+clientPackagingAccountDtoFound.getCpaNumber());
        assertTrue(BigDecimal.valueOf(15).compareTo(clientPackagingAccountDtoFound.getCpaNumber())==0);

        assertTrue(clientPackagingAccountService.savePackagingOperationforClient(clientPackagingAccountDtoSaved.getId(), qteToSave,
                OperationType.Withdrawal, userBMDtoSaved.getId(), "retrait ", "sdsds"));
        ClientPackagingAccountDto clientPackagingAccountDtoFound1 = clientPackagingAccountService.findClientPackagingAccountById(
                clientPackagingAccountDtoSaved.getId());
        System.err.println("solde == "+clientPackagingAccountDtoFound1.getCpaNumber());
        assertTrue(BigDecimal.valueOf(0).compareTo(clientPackagingAccountDtoFound1.getCpaNumber())==0);

        //On teste List<ClientPackagingOperationDto> findAllClientPackagingOperation(Long cltpamopId)
        List<ClientPackagingOperationDto> clientPackagingOperationDtoList = clientPackagingOperationService.findAllClientPackagingOperation(
                clientPackagingAccountDtoSaved.getId());
        assertNotNull(clientPackagingOperationDtoList);
        assertEquals(2, clientPackagingOperationDtoList.size());

        //On teste Page<ClientPackagingOperationDto> findPageClientPackagingOperation(Long pproopId, int pagenum, int pagesize)
        Page<ClientPackagingOperationDto> clientPackagingOperationDtoPage = clientPackagingOperationService.findPageClientPackagingOperation(
                clientPackagingAccountDtoSaved.getId(), 0, 2);
        assertNotNull(clientPackagingOperationDtoPage);
        assertEquals(1, clientPackagingOperationDtoPage.getTotalPages());

        //On teste List<ProviderCapsuleOperationDto> findAllProviderCapsuleOperationBetween(Long pproopId, Instant startDate,
        //                                                                          Instant endDate)
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

            List<ClientPackagingOperationDto> clientPackagingOperationDtoListBetween = clientPackagingOperationService.
                    findAllClientPackagingOperationBetween(clientPackagingAccountDtoSaved.getId(), startDate.toInstant(),
                            endDate.toInstant());
            assertNotNull(clientPackagingOperationDtoListBetween);
            assertEquals(2, clientPackagingOperationDtoListBetween.size());

            //Page<ClientPackagingOperationDto> findPageClientPackagingOperationBetween(Long cltpackopId, Instant startDate,
            //                                           Instant endDate, int pagenum, int pagesize)
            Page<ClientPackagingOperationDto> clientPackagingOperationDtoPageBetween = clientPackagingOperationService.
                    findPageClientPackagingOperationBetween(clientPackagingAccountDtoSaved.getId(), startDate.toInstant(),
                            endDate.toInstant(), 0, 2);
            assertNotNull(clientPackagingOperationDtoPageBetween);
            assertEquals(1, clientPackagingOperationDtoPageBetween.getTotalPages());

            //List<ClientPackagingOperationDto> findAllClientPackagingOperationBetween(Long cltpackopId, OperationType op_type,
            //                                                        Instant startDate, Instant endDate)
            List<ClientPackagingOperationDto> clientPackagingOperationDtoListBetweenoftype = clientPackagingOperationService.
                    findAllClientPackagingOperationBetween(clientPackagingAccountDtoSaved.getId(), OperationType.Credit,
                            startDate.toInstant(), endDate.toInstant());
            assertNotNull(clientPackagingOperationDtoListBetweenoftype);
            assertEquals(1, clientPackagingOperationDtoListBetweenoftype.size());

            //Page<ClientPackagingOperationDto> findPageClientPackagingOperationBetween(Long cltpackopId, OperationType op_type,
            //                                                                 Instant startDate, Instant endDate,
            //                                                                 int pagenum, int pagesize)
            Page<ClientPackagingOperationDto> clientPackagingOperationDtoPageBetweenoftype = clientPackagingOperationService.
                    findPageClientPackagingOperationBetween(clientPackagingAccountDtoSaved.getId(), OperationType.Credit,
                            startDate.toInstant(), endDate.toInstant(), 0, 2);
            assertNotNull(clientPackagingOperationDtoPageBetweenoftype);
            assertEquals(1, clientPackagingOperationDtoPageBetweenoftype.getTotalPages());

        }catch (Exception e){
            System.err.println("There is an exception not manage in the program means a big problem appears");
            e.printStackTrace();
        }

    }

}