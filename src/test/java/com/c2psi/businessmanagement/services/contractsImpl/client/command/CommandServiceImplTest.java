package com.c2psi.businessmanagement.services.contractsImpl.client.command;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.Enumerations.CommandState;
import com.c2psi.businessmanagement.Enumerations.CommandStatus;
import com.c2psi.businessmanagement.Enumerations.CommandType;
import com.c2psi.businessmanagement.Enumerations.DeliveryState;
import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.dtos.client.client.DiversDto;
import com.c2psi.businessmanagement.dtos.client.command.CommandDto;
import com.c2psi.businessmanagement.dtos.client.command.SaleInvoiceCapsuleDto;
import com.c2psi.businessmanagement.dtos.client.command.SaleInvoiceCashDto;
import com.c2psi.businessmanagement.dtos.client.command.SaleInvoiceDamageDto;
import com.c2psi.businessmanagement.dtos.client.delivery.DeliveryDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.exceptions.DuplicateEntityException;
import com.c2psi.businessmanagement.exceptions.InvalidEntityException;
import com.c2psi.businessmanagement.services.contractsImpl.UsedForTestForAll;
import com.c2psi.businessmanagement.services.contractsImpl.client.UsedForTestForClient;
import com.c2psi.businessmanagement.services.contractsImpl.client.client.ClientServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.client.client.DiversCashAccountServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.client.client.DiversServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.client.delivery.DeliveryServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.pos.pos.EnterpriseServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.pos.pos.PointofsaleServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.pos.userbm.UserBMServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.price.CurrencyServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.product.UsedForTestForProduct;
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
public class CommandServiceImplTest {

    @Autowired
    DeliveryServiceImpl deliveryService;
    @Autowired
    PointofsaleServiceImpl pointofsaleService;
    @Autowired
    EnterpriseServiceImpl enterpriseService;
    @Autowired
    CurrencyServiceImpl currencyService;
    @Autowired
    ClientServiceImpl clientService;
    @Autowired
    UserBMServiceImpl userBMService;
    @Autowired
    SaleInvoiceCapsuleServiceImpl saleInvoiceCapsuleService;
    @Autowired
    SaleInvoiceCashServiceImpl saleInvoiceCashService;
    @Autowired
    SaleInvoiceDamageServiceImpl saleInvoiceDamageService;
    @Autowired
    CommandServiceImpl commandService;
    @Autowired
    UsedForTestForAll usedForTestForAll;
    @Autowired
    UsedForTestForClient usedForTestForClient;
    @Autowired
    UsedForTestForProduct usedForTestForProduct;
    @Autowired
    DiversCashAccountServiceImpl diversCashAccountService;
    @Autowired
    DiversServiceImpl diversService;

    @Test
    public void validate_SaveCommand() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        DiversDto diversDtoSaved = usedForTestForClient.saveDivers(5, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved);
        assertNotNull(diversDtoSaved.getDiversCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosDto());

        CommandDto commandDtoSaved = usedForTestForClient.saveCommand(-1, posDtoSaved, clientDtoSaved, null,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved);

        CommandDto commandDtoSaved1 = usedForTestForClient.saveCommand(-2, posDtoSaved, null, diversDtoSaved,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved1);

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveCommand_Invalid() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        DiversDto diversDtoSaved = usedForTestForClient.saveDivers(5, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved);
        assertNotNull(diversDtoSaved.getDiversCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosDto());

        CommandDto commandDtoSaved = usedForTestForClient.saveCommand_Invalid(-1, posDtoSaved, clientDtoSaved, null,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved);

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveCommand_PosIdNull() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        DiversDto diversDtoSaved = usedForTestForClient.saveDivers(5, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved);
        assertNotNull(diversDtoSaved.getDiversCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosDto());

        posDtoSaved.setId(null);

        CommandDto commandDtoSaved = usedForTestForClient.saveCommand(-1, posDtoSaved, clientDtoSaved, null,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved);

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveCommand_PosIdNotExist() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        DiversDto diversDtoSaved = usedForTestForClient.saveDivers(5, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved);
        assertNotNull(diversDtoSaved.getDiversCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosDto());

        posDtoSaved.setId(Long.valueOf(457821));

        CommandDto commandDtoSaved = usedForTestForClient.saveCommand(-1, posDtoSaved, clientDtoSaved, null,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved);

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveCommand_ClientorDiversNull() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        DiversDto diversDtoSaved = usedForTestForClient.saveDivers(5, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved);
        assertNotNull(diversDtoSaved.getDiversCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosDto());

        CommandDto commandDtoSaved = usedForTestForClient.saveCommand(-1, posDtoSaved, null, null,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved);

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveCommand_ClientorDiversNotNull() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        DiversDto diversDtoSaved = usedForTestForClient.saveDivers(5, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved);
        assertNotNull(diversDtoSaved.getDiversCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosDto());

        CommandDto commandDtoSaved = usedForTestForClient.saveCommand(-1, posDtoSaved, clientDtoSaved, diversDtoSaved,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved);

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveCommand_ClientNotExist() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        DiversDto diversDtoSaved = usedForTestForClient.saveDivers(5, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved);
        assertNotNull(diversDtoSaved.getDiversCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosDto());

        clientDtoSaved.setId(Long.valueOf(457896));

        CommandDto commandDtoSaved = usedForTestForClient.saveCommand(-1, posDtoSaved, clientDtoSaved, null,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved);

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveCommand_DiversNotExist() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        DiversDto diversDtoSaved = usedForTestForClient.saveDivers(5, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved);
        assertNotNull(diversDtoSaved.getDiversCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosDto());

        diversDtoSaved.setId(Long.valueOf(457896));

        CommandDto commandDtoSaved = usedForTestForClient.saveCommand(-1, posDtoSaved, null, diversDtoSaved,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved);

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveCommand_UserBMIdNull() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        DiversDto diversDtoSaved = usedForTestForClient.saveDivers(5, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved);
        assertNotNull(diversDtoSaved.getDiversCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosDto());

        userBMDtoSaved.setId(null);

        CommandDto commandDtoSaved = usedForTestForClient.saveCommand(-1, posDtoSaved, clientDtoSaved, null,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved);

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveCommand_UserBMNotNull() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        DiversDto diversDtoSaved = usedForTestForClient.saveDivers(5, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved);
        assertNotNull(diversDtoSaved.getDiversCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosDto());

        userBMDtoSaved.setId(Long.valueOf(748596));

        CommandDto commandDtoSaved = usedForTestForClient.saveCommand(-1, posDtoSaved, clientDtoSaved, null,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved);

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveCommand_UserBMPosNull() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        DiversDto diversDtoSaved = usedForTestForClient.saveDivers(5, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved);
        assertNotNull(diversDtoSaved.getDiversCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosDto());

        UserBMDto userBMDtoSaved1 = usedForTestForAll.saveUserBM(152, userBMService);
        assertNotNull(userBMDtoSaved1);

        CommandDto commandDtoSaved = usedForTestForClient.saveCommand(-1, posDtoSaved, clientDtoSaved, null,
                userBMDtoSaved1, commandService);
        assertNotNull(commandDtoSaved);

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveCommand_UserBMPosdifferentthanCmdPos() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        PointofsaleDto posDtoSaved1 = usedForTestForAll.savePointofsale(54, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved1);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        DiversDto diversDtoSaved = usedForTestForClient.saveDivers(5, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved);
        assertNotNull(diversDtoSaved.getDiversCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosDto());

        UserBMDto userBMDtoSaved1 = usedForTestForAll.saveUserBM(152, userBMService);
        assertNotNull(userBMDtoSaved1);

        CommandDto commandDtoSaved = usedForTestForClient.saveCommand(-1, posDtoSaved1, clientDtoSaved, null,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved);

    }

    @Test(expected = DuplicateEntityException.class)
    public void validate_SaveCommand_Duplicate() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        DiversDto diversDtoSaved = usedForTestForClient.saveDivers(5, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved);
        assertNotNull(diversDtoSaved.getDiversCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosDto());

        CommandDto commandDtoSaved = usedForTestForClient.saveCommand(-1, posDtoSaved, clientDtoSaved, null,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved);

        CommandDto commandDtoSaved1 = usedForTestForClient.saveCommand(-2, posDtoSaved, null, diversDtoSaved,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved1);

        CommandDto commandDtoSaved2 = usedForTestForClient.saveCommand(-2, posDtoSaved, null, diversDtoSaved,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved2);

    }


    @Test
    public void validate_UpdateCommand() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        ClientDto clientDtoSaved1 = usedForTestForClient.saveClient(7, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved1);
        assertNotNull(clientDtoSaved1.getClientCaDto().getId());

        DiversDto diversDtoSaved = usedForTestForClient.saveDivers(5, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved);
        assertNotNull(diversDtoSaved.getDiversCaDto().getId());

        DiversDto diversDtoSaved1 = usedForTestForClient.saveDivers(87, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved1);
        assertNotNull(diversDtoSaved1.getDiversCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosDto());

        CommandDto commandDtoSaved = usedForTestForClient.saveCommand(-1, posDtoSaved, clientDtoSaved, null,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved);
        assertEquals(CommandType.Standard, commandDtoSaved.getCmdType());

        CommandDto commandDtoSaved1 = usedForTestForClient.saveCommand(-2, posDtoSaved, null, diversDtoSaved,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved1);
        assertEquals(CommandType.Divers, commandDtoSaved1.getCmdType());

        CommandDto commandDtoFound = commandService.findCommandById(commandDtoSaved.getId());
        commandDtoFound.setCmdComment("comment");
        //On peut update le client
        commandDtoFound.setCmdClientDto(clientDtoSaved1);

        //On peut update le divers
        CommandDto commandDtoFound1 = commandService.findCommandByCodeinPos(commandDtoSaved1.getCmdCode(),
                commandDtoSaved1.getCmdPosDto().getId());
        commandDtoFound1.setCmdComment("comment divers");
        //On peut update le divers
        commandDtoFound1.setCmdDiversDto(diversDtoSaved1);
        commandDtoFound1.setCmdCode("newCode1154");

        CommandDto commandDtoUpdated = commandService.updateCommand(commandDtoFound);
        assertNotNull(commandDtoUpdated);
        assertEquals("comment", commandDtoUpdated.getCmdComment());
        assertEquals(CommandType.Standard, commandDtoUpdated.getCmdType());

        CommandDto commandDtoUpdated1 = commandService.updateCommand(commandDtoFound1);
        assertNotNull(commandDtoUpdated1);
        assertEquals("comment divers", commandDtoUpdated1.getCmdComment());
        assertEquals(CommandType.Divers, commandDtoUpdated1.getCmdType());

    }

    @Test(expected = DuplicateEntityException.class)
    public void validate_UpdateCommand_Duplicate() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        ClientDto clientDtoSaved1 = usedForTestForClient.saveClient(7, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved1);
        assertNotNull(clientDtoSaved1.getClientCaDto().getId());

        DiversDto diversDtoSaved = usedForTestForClient.saveDivers(5, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved);
        assertNotNull(diversDtoSaved.getDiversCaDto().getId());

        DiversDto diversDtoSaved1 = usedForTestForClient.saveDivers(87, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved1);
        assertNotNull(diversDtoSaved1.getDiversCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosDto());

        CommandDto commandDtoSaved = usedForTestForClient.saveCommand(-1, posDtoSaved, clientDtoSaved, null,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved);
        assertEquals(CommandType.Standard, commandDtoSaved.getCmdType());

        CommandDto commandDtoSaved1 = usedForTestForClient.saveCommand(-2, posDtoSaved, null, diversDtoSaved,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved1);
        assertEquals(CommandType.Divers, commandDtoSaved1.getCmdType());

        CommandDto commandDtoFound = commandService.findCommandById(commandDtoSaved.getId());
        commandDtoFound.setCmdComment("comment");
        //On peut update le client
        commandDtoFound.setCmdClientDto(clientDtoSaved1);

        //On peut update le divers
        CommandDto commandDtoFound1 = commandService.findCommandByCodeinPos(commandDtoSaved1.getCmdCode(),
                commandDtoSaved1.getCmdPosDto().getId());
        commandDtoFound1.setCmdComment("comment divers");
        //On peut update le divers
        commandDtoFound1.setCmdDiversDto(diversDtoSaved1);
        commandDtoFound1.setCmdCode("newCode1154");

        commandDtoFound.setCmdCode("newCode1154");

        CommandDto commandDtoUpdated = commandService.updateCommand(commandDtoFound);
        assertNotNull(commandDtoUpdated);
        assertEquals("comment", commandDtoUpdated.getCmdComment());

        CommandDto commandDtoUpdated1 = commandService.updateCommand(commandDtoFound1);
        assertNotNull(commandDtoUpdated1);
        assertEquals("comment divers", commandDtoUpdated1.getCmdComment());

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_UpdateCommand_DiversIdNull() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        ClientDto clientDtoSaved1 = usedForTestForClient.saveClient(7, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved1);
        assertNotNull(clientDtoSaved1.getClientCaDto().getId());

        DiversDto diversDtoSaved = usedForTestForClient.saveDivers(5, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved);
        assertNotNull(diversDtoSaved.getDiversCaDto().getId());

        DiversDto diversDtoSaved1 = usedForTestForClient.saveDivers(87, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved1);
        assertNotNull(diversDtoSaved1.getDiversCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosDto());

        CommandDto commandDtoSaved = usedForTestForClient.saveCommand(-1, posDtoSaved, clientDtoSaved, null,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved);
        assertEquals(CommandType.Standard, commandDtoSaved.getCmdType());

        CommandDto commandDtoSaved1 = usedForTestForClient.saveCommand(-2, posDtoSaved, null, diversDtoSaved,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved1);
        assertEquals(CommandType.Divers, commandDtoSaved1.getCmdType());

        CommandDto commandDtoFound = commandService.findCommandById(commandDtoSaved.getId());
        commandDtoFound.setCmdComment("comment");
        //On peut update le client
        commandDtoFound.setCmdClientDto(clientDtoSaved1);

        //On peut update le divers
        CommandDto commandDtoFound1 = commandService.findCommandByCodeinPos(commandDtoSaved1.getCmdCode(),
                commandDtoSaved1.getCmdPosDto().getId());
        commandDtoFound1.setCmdComment("comment divers");
        //On peut update le divers
        diversDtoSaved1.setId(null);
        commandDtoFound1.setCmdDiversDto(diversDtoSaved1);
        commandDtoFound1.setCmdCode("newCode1154");

        CommandDto commandDtoUpdated = commandService.updateCommand(commandDtoFound);
        assertNotNull(commandDtoUpdated);
        assertEquals("comment", commandDtoUpdated.getCmdComment());

        CommandDto commandDtoUpdated1 = commandService.updateCommand(commandDtoFound1);
        assertNotNull(commandDtoUpdated1);
        assertEquals("comment divers", commandDtoUpdated1.getCmdComment());

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_UpdateCommand_DiversNotExist() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        ClientDto clientDtoSaved1 = usedForTestForClient.saveClient(7, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved1);
        assertNotNull(clientDtoSaved1.getClientCaDto().getId());

        DiversDto diversDtoSaved = usedForTestForClient.saveDivers(5, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved);
        assertNotNull(diversDtoSaved.getDiversCaDto().getId());

        DiversDto diversDtoSaved1 = usedForTestForClient.saveDivers(87, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved1);
        assertNotNull(diversDtoSaved1.getDiversCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosDto());

        CommandDto commandDtoSaved = usedForTestForClient.saveCommand(-1, posDtoSaved, clientDtoSaved, null,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved);
        assertEquals(CommandType.Standard, commandDtoSaved.getCmdType());

        CommandDto commandDtoSaved1 = usedForTestForClient.saveCommand(-2, posDtoSaved, null, diversDtoSaved,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved1);
        assertEquals(CommandType.Divers, commandDtoSaved1.getCmdType());

        CommandDto commandDtoFound = commandService.findCommandById(commandDtoSaved.getId());
        commandDtoFound.setCmdComment("comment");
        //On peut update le client
        commandDtoFound.setCmdClientDto(clientDtoSaved1);

        //On peut update le divers
        CommandDto commandDtoFound1 = commandService.findCommandByCodeinPos(commandDtoSaved1.getCmdCode(),
                commandDtoSaved1.getCmdPosDto().getId());
        commandDtoFound1.setCmdComment("comment divers");
        //On peut update le divers
        diversDtoSaved1.setId(Long.valueOf(8574213));
        commandDtoFound1.setCmdDiversDto(diversDtoSaved1);
        commandDtoFound1.setCmdCode("newCode1154");

        CommandDto commandDtoUpdated = commandService.updateCommand(commandDtoFound);
        assertNotNull(commandDtoUpdated);
        assertEquals("comment", commandDtoUpdated.getCmdComment());

        CommandDto commandDtoUpdated1 = commandService.updateCommand(commandDtoFound1);
        assertNotNull(commandDtoUpdated1);
        assertEquals("comment divers", commandDtoUpdated1.getCmdComment());

    }


    @Test(expected = InvalidEntityException.class)
    public void validate_UpdateCommand_clientIdNull() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        ClientDto clientDtoSaved1 = usedForTestForClient.saveClient(7, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved1);
        assertNotNull(clientDtoSaved1.getClientCaDto().getId());

        DiversDto diversDtoSaved = usedForTestForClient.saveDivers(5, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved);
        assertNotNull(diversDtoSaved.getDiversCaDto().getId());

        DiversDto diversDtoSaved1 = usedForTestForClient.saveDivers(87, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved1);
        assertNotNull(diversDtoSaved1.getDiversCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosDto());

        CommandDto commandDtoSaved = usedForTestForClient.saveCommand(-1, posDtoSaved, clientDtoSaved, null,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved);
        assertEquals(CommandType.Standard, commandDtoSaved.getCmdType());

        CommandDto commandDtoSaved1 = usedForTestForClient.saveCommand(-2, posDtoSaved, null, diversDtoSaved,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved1);
        assertEquals(CommandType.Divers, commandDtoSaved1.getCmdType());

        CommandDto commandDtoFound = commandService.findCommandById(commandDtoSaved.getId());
        commandDtoFound.setCmdComment("comment");
        //On peut update le client
        clientDtoSaved1.setId(null);
        commandDtoFound.setCmdClientDto(clientDtoSaved1);

        //On peut update le divers
        CommandDto commandDtoFound1 = commandService.findCommandByCodeinPos(commandDtoSaved1.getCmdCode(),
                commandDtoSaved1.getCmdPosDto().getId());
        commandDtoFound1.setCmdComment("comment divers");
        //On peut update le divers
        commandDtoFound1.setCmdDiversDto(diversDtoSaved1);
        commandDtoFound1.setCmdCode("newCode1154");

        CommandDto commandDtoUpdated = commandService.updateCommand(commandDtoFound);
        assertNotNull(commandDtoUpdated);
        assertEquals("comment", commandDtoUpdated.getCmdComment());

        CommandDto commandDtoUpdated1 = commandService.updateCommand(commandDtoFound1);
        assertNotNull(commandDtoUpdated1);
        assertEquals("comment divers", commandDtoUpdated1.getCmdComment());

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_UpdateCommand_clientNotExist() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        ClientDto clientDtoSaved1 = usedForTestForClient.saveClient(7, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved1);
        assertNotNull(clientDtoSaved1.getClientCaDto().getId());

        DiversDto diversDtoSaved = usedForTestForClient.saveDivers(5, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved);
        assertNotNull(diversDtoSaved.getDiversCaDto().getId());

        DiversDto diversDtoSaved1 = usedForTestForClient.saveDivers(87, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved1);
        assertNotNull(diversDtoSaved1.getDiversCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosDto());

        CommandDto commandDtoSaved = usedForTestForClient.saveCommand(-1, posDtoSaved, clientDtoSaved, null,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved);
        assertEquals(CommandType.Standard, commandDtoSaved.getCmdType());

        CommandDto commandDtoSaved1 = usedForTestForClient.saveCommand(-2, posDtoSaved, null, diversDtoSaved,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved1);
        assertEquals(CommandType.Divers, commandDtoSaved1.getCmdType());

        CommandDto commandDtoFound = commandService.findCommandById(commandDtoSaved.getId());
        commandDtoFound.setCmdComment("comment");
        //On peut update le client
        clientDtoSaved1.setId(Long.valueOf(4125369));
        commandDtoFound.setCmdClientDto(clientDtoSaved1);

        //On peut update le divers
        CommandDto commandDtoFound1 = commandService.findCommandByCodeinPos(commandDtoSaved1.getCmdCode(),
                commandDtoSaved1.getCmdPosDto().getId());
        commandDtoFound1.setCmdComment("comment divers");
        //On peut update le divers
        commandDtoFound1.setCmdDiversDto(diversDtoSaved1);
        commandDtoFound1.setCmdCode("newCode1154");

        CommandDto commandDtoUpdated = commandService.updateCommand(commandDtoFound);
        assertNotNull(commandDtoUpdated);
        assertEquals("comment", commandDtoUpdated.getCmdComment());

        CommandDto commandDtoUpdated1 = commandService.updateCommand(commandDtoFound1);
        assertNotNull(commandDtoUpdated1);
        assertEquals("comment divers", commandDtoUpdated1.getCmdComment());

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_UpdateCommand_clientandDiversPrecised() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        ClientDto clientDtoSaved1 = usedForTestForClient.saveClient(7, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved1);
        assertNotNull(clientDtoSaved1.getClientCaDto().getId());

        DiversDto diversDtoSaved = usedForTestForClient.saveDivers(5, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved);
        assertNotNull(diversDtoSaved.getDiversCaDto().getId());

        DiversDto diversDtoSaved1 = usedForTestForClient.saveDivers(87, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved1);
        assertNotNull(diversDtoSaved1.getDiversCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosDto());

        CommandDto commandDtoSaved = usedForTestForClient.saveCommand(-1, posDtoSaved, clientDtoSaved, null,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved);
        assertEquals(CommandType.Standard, commandDtoSaved.getCmdType());

        CommandDto commandDtoSaved1 = usedForTestForClient.saveCommand(-2, posDtoSaved, null, diversDtoSaved,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved1);
        assertEquals(CommandType.Divers, commandDtoSaved1.getCmdType());

        CommandDto commandDtoFound = commandService.findCommandById(commandDtoSaved.getId());
        commandDtoFound.setCmdComment("comment");
        //On peut update le client
        commandDtoFound.setCmdClientDto(clientDtoSaved1);

        //On peut update le divers
        CommandDto commandDtoFound1 = commandService.findCommandByCodeinPos(commandDtoSaved1.getCmdCode(),
                commandDtoSaved1.getCmdPosDto().getId());
        commandDtoFound1.setCmdComment("comment divers");
        //On peut update le divers
        commandDtoFound1.setCmdDiversDto(diversDtoSaved1);
        commandDtoFound1.setCmdCode("newCode1154");
        commandDtoFound.setCmdDiversDto(diversDtoSaved);

        CommandDto commandDtoUpdated = commandService.updateCommand(commandDtoFound);
        assertNotNull(commandDtoUpdated);
        assertEquals("comment", commandDtoUpdated.getCmdComment());

        CommandDto commandDtoUpdated1 = commandService.updateCommand(commandDtoFound1);
        assertNotNull(commandDtoUpdated1);
        assertEquals("comment divers", commandDtoUpdated1.getCmdComment());

    }


    @Test
    public void validate_SetSaleInvoice() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        DiversDto diversDtoSaved = usedForTestForClient.saveDivers(5, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved);
        assertNotNull(diversDtoSaved.getDiversCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosDto());

        CommandDto commandDtoSaved = usedForTestForClient.saveCommand(-1, posDtoSaved, clientDtoSaved, null,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved);

        ///On va set le saleinvoicecapsule

        SaleInvoiceCapsuleDto saleInvoiceCapsuleDtoSaved = usedForTestForProduct.saveSaleInvoiceCapsuleDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceCapsuleService);
        assertNotNull(saleInvoiceCapsuleDtoSaved);
        assertNotNull(saleInvoiceCapsuleDtoSaved.getId());

        CommandDto commandDtoUpdated = commandService.setSaleInvoice(commandDtoSaved.getId(),
                saleInvoiceCapsuleDtoSaved.getId(), CommandStatus.Capsule);
        assertNotNull(commandDtoUpdated);
        assertNotNull(commandDtoUpdated.getCmdSaleicapsDto());

        CommandDto commandDtoSaved1 = usedForTestForClient.saveCommand(-2, posDtoSaved, null, diversDtoSaved,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved1);

        ///On va set le saleinvoicecash

        SaleInvoiceCashDto saleInvoiceCashDtoSaved = usedForTestForProduct.saveSaleInvoiceCashDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceCashService);
        assertNotNull(saleInvoiceCashDtoSaved);
        assertNotNull(saleInvoiceCashDtoSaved.getId());

        CommandDto commandDtoUpdated1 = commandService.setSaleInvoice(commandDtoSaved1.getId(),
                saleInvoiceCashDtoSaved.getId(), CommandStatus.Cash);
        assertNotNull(commandDtoUpdated1);
        assertNotNull(commandDtoUpdated1.getCmdSaleicashDto());

        CommandDto commandDtoSaved2 = usedForTestForClient.saveCommand(-5, posDtoSaved, clientDtoSaved, null,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved2);

        SaleInvoiceDamageDto saleInvoiceDamageDtoSaved = usedForTestForProduct.saveSaleInvoiceDamageDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceDamageService);
        assertNotNull(saleInvoiceDamageDtoSaved);
        assertNotNull(saleInvoiceDamageDtoSaved.getId());

        CommandDto commandDtoUpdated2 = commandService.setSaleInvoice(commandDtoSaved2.getId(),
                saleInvoiceDamageDtoSaved.getId(), CommandStatus.Damage);
        assertNotNull(commandDtoUpdated2);
        assertNotNull(commandDtoUpdated2.getCmdSaleidamDto());

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SetSaleInvoice_CmdNotFound() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        DiversDto diversDtoSaved = usedForTestForClient.saveDivers(5, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved);
        assertNotNull(diversDtoSaved.getDiversCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosDto());

        CommandDto commandDtoSaved = usedForTestForClient.saveCommand(-1, posDtoSaved, clientDtoSaved, null,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved);

        ///On va set le saleinvoicecapsule

        SaleInvoiceCapsuleDto saleInvoiceCapsuleDtoSaved = usedForTestForProduct.saveSaleInvoiceCapsuleDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceCapsuleService);
        assertNotNull(saleInvoiceCapsuleDtoSaved);
        assertNotNull(saleInvoiceCapsuleDtoSaved.getId());

        commandDtoSaved.setId(Long.valueOf(852147));

        CommandDto commandDtoUpdated = commandService.setSaleInvoice(commandDtoSaved.getId(),
                saleInvoiceCapsuleDtoSaved.getId(), CommandStatus.Capsule);
        assertNotNull(commandDtoUpdated);
        assertNotNull(commandDtoUpdated.getCmdSaleicapsDto());

        CommandDto commandDtoSaved1 = usedForTestForClient.saveCommand(-2, posDtoSaved, null, diversDtoSaved,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved1);

        ///On va set le saleinvoicecash

        SaleInvoiceCashDto saleInvoiceCashDtoSaved = usedForTestForProduct.saveSaleInvoiceCashDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceCashService);
        assertNotNull(saleInvoiceCashDtoSaved);
        assertNotNull(saleInvoiceCashDtoSaved.getId());

        CommandDto commandDtoUpdated1 = commandService.setSaleInvoice(commandDtoSaved1.getId(),
                saleInvoiceCashDtoSaved.getId(), CommandStatus.Cash);
        assertNotNull(commandDtoUpdated1);
        assertNotNull(commandDtoUpdated1.getCmdSaleicashDto());

        CommandDto commandDtoSaved2 = usedForTestForClient.saveCommand(-5, posDtoSaved, clientDtoSaved, null,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved2);

        SaleInvoiceDamageDto saleInvoiceDamageDtoSaved = usedForTestForProduct.saveSaleInvoiceDamageDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceDamageService);
        assertNotNull(saleInvoiceDamageDtoSaved);
        assertNotNull(saleInvoiceDamageDtoSaved.getId());

        CommandDto commandDtoUpdated2 = commandService.setSaleInvoice(commandDtoSaved2.getId(),
                saleInvoiceDamageDtoSaved.getId(), CommandStatus.Damage);
        assertNotNull(commandDtoUpdated2);
        assertNotNull(commandDtoUpdated2.getCmdSaleidamDto());

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SetSaleInvoice_MultipleStatus1() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        DiversDto diversDtoSaved = usedForTestForClient.saveDivers(5, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved);
        assertNotNull(diversDtoSaved.getDiversCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosDto());

        CommandDto commandDtoSaved = usedForTestForClient.saveCommand(-1, posDtoSaved, clientDtoSaved, null,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved);

        ///On va set le saleinvoicecapsule

        SaleInvoiceCapsuleDto saleInvoiceCapsuleDtoSaved = usedForTestForProduct.saveSaleInvoiceCapsuleDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceCapsuleService);
        assertNotNull(saleInvoiceCapsuleDtoSaved);
        assertNotNull(saleInvoiceCapsuleDtoSaved.getId());

        CommandDto commandDtoUpdated = commandService.setSaleInvoice(commandDtoSaved.getId(),
                saleInvoiceCapsuleDtoSaved.getId(), CommandStatus.Capsule);
        assertNotNull(commandDtoUpdated);
        assertNotNull(commandDtoUpdated.getCmdSaleicapsDto());

        CommandDto commandDtoSaved1 = usedForTestForClient.saveCommand(-2, posDtoSaved, null, diversDtoSaved,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved1);

        ///On va set le saleinvoicecash

        SaleInvoiceCashDto saleInvoiceCashDtoSaved = usedForTestForProduct.saveSaleInvoiceCashDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceCashService);
        assertNotNull(saleInvoiceCashDtoSaved);
        assertNotNull(saleInvoiceCashDtoSaved.getId());

        //On essaye de donner a une meme command(commandDtoSaved) 2 status (capsule et cash)
        CommandDto commandDtoUpdated1 = commandService.setSaleInvoice(commandDtoSaved.getId(),
                saleInvoiceCashDtoSaved.getId(), CommandStatus.Cash);
        assertNotNull(commandDtoUpdated1);
        assertNotNull(commandDtoUpdated1.getCmdSaleicashDto());

        CommandDto commandDtoSaved2 = usedForTestForClient.saveCommand(-5, posDtoSaved, clientDtoSaved, null,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved2);

        SaleInvoiceDamageDto saleInvoiceDamageDtoSaved = usedForTestForProduct.saveSaleInvoiceDamageDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceDamageService);
        assertNotNull(saleInvoiceDamageDtoSaved);
        assertNotNull(saleInvoiceDamageDtoSaved.getId());

        CommandDto commandDtoUpdated2 = commandService.setSaleInvoice(commandDtoSaved2.getId(),
                saleInvoiceDamageDtoSaved.getId(), CommandStatus.Damage);
        assertNotNull(commandDtoUpdated2);
        assertNotNull(commandDtoUpdated2.getCmdSaleidamDto());

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SetSaleInvoice_MultipleStatus2() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        DiversDto diversDtoSaved = usedForTestForClient.saveDivers(5, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved);
        assertNotNull(diversDtoSaved.getDiversCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosDto());

        CommandDto commandDtoSaved = usedForTestForClient.saveCommand(-1, posDtoSaved, clientDtoSaved, null,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved);

        ///On va set le saleinvoicecapsule

        SaleInvoiceCapsuleDto saleInvoiceCapsuleDtoSaved = usedForTestForProduct.saveSaleInvoiceCapsuleDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceCapsuleService);
        assertNotNull(saleInvoiceCapsuleDtoSaved);
        assertNotNull(saleInvoiceCapsuleDtoSaved.getId());

        CommandDto commandDtoUpdated = commandService.setSaleInvoice(commandDtoSaved.getId(),
                saleInvoiceCapsuleDtoSaved.getId(), CommandStatus.Capsule);
        assertNotNull(commandDtoUpdated);
        assertNotNull(commandDtoUpdated.getCmdSaleicapsDto());

        CommandDto commandDtoSaved1 = usedForTestForClient.saveCommand(-2, posDtoSaved, null, diversDtoSaved,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved1);

        ///On va set le saleinvoicecash

        SaleInvoiceCashDto saleInvoiceCashDtoSaved = usedForTestForProduct.saveSaleInvoiceCashDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceCashService);
        assertNotNull(saleInvoiceCashDtoSaved);
        assertNotNull(saleInvoiceCashDtoSaved.getId());

        //On essaye de donner a une meme command(commandDtoSaved) 2 status (capsule et cash)
        CommandDto commandDtoUpdated1 = commandService.setSaleInvoice(commandDtoSaved1.getId(),
                saleInvoiceCashDtoSaved.getId(), CommandStatus.Cash);
        assertNotNull(commandDtoUpdated1);
        assertNotNull(commandDtoUpdated1.getCmdSaleicashDto());

        CommandDto commandDtoSaved2 = usedForTestForClient.saveCommand(-5, posDtoSaved, clientDtoSaved, null,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved2);

        SaleInvoiceDamageDto saleInvoiceDamageDtoSaved = usedForTestForProduct.saveSaleInvoiceDamageDto(0, userBMDtoSaved,
                posDtoSaved, clientDtoSaved, saleInvoiceDamageService);
        assertNotNull(saleInvoiceDamageDtoSaved);
        assertNotNull(saleInvoiceDamageDtoSaved.getId());

        //On essaye de donner a une meme command(commandDtoSaved1) 2 status (capsule et damage)
        CommandDto commandDtoUpdated2 = commandService.setSaleInvoice(commandDtoSaved1.getId(),
                saleInvoiceDamageDtoSaved.getId(), CommandStatus.Damage);
        assertNotNull(commandDtoUpdated2);
        assertNotNull(commandDtoUpdated2.getCmdSaleidamDto());

    }


















    @Test
    public void validate_AssignCommandToDelivery() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        DiversDto diversDtoSaved = usedForTestForClient.saveDivers(5, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved);
        assertNotNull(diversDtoSaved.getDiversCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosDto());

        CommandDto commandDtoSaved = usedForTestForClient.saveCommand(-1, posDtoSaved, clientDtoSaved, null,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved);
        assertNull(commandDtoSaved.getCmdDeliveryDto());

        DeliveryDto deliveryDtoSaved = usedForTestForClient.saveDelivery(0, posDtoSaved, userBMDtoSaved,
                deliveryService);
        assertNotNull(deliveryDtoSaved);

        CommandDto commandDtoFound = commandService.switchCommandStateTo(commandDtoSaved.getId(), CommandState.Edited);
        assertNotNull(commandDtoFound);
        assertEquals(CommandState.Edited, commandDtoFound.getCmdState());

        CommandDto commandDtoUpdated = commandService.assignCommandToDelivery(commandDtoSaved.getId(),
                deliveryDtoSaved.getId());
        assertNotNull(commandDtoUpdated);
        assertNotNull(commandDtoUpdated.getCmdDeliveryDto());

        CommandDto commandDtoSaved1 = usedForTestForClient.saveCommand(-2, posDtoSaved, null, diversDtoSaved,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved1);

        DeliveryDto deliveryDtoSaved1 = usedForTestForClient.saveDelivery(-4, posDtoSaved, userBMDtoSaved,
                deliveryService);
        assertNotNull(deliveryDtoSaved1);

        CommandDto commandDtoFound1 = commandService.switchCommandStateTo(commandDtoSaved1.getId(), CommandState.Edited);
        assertNotNull(commandDtoFound1);
        assertEquals(CommandState.Edited, commandDtoFound1.getCmdState());

        CommandDto commandDtoUpdated1 = commandService.assignCommandToDelivery(commandDtoSaved1.getId(),
                deliveryDtoSaved1.getId());
        assertNotNull(commandDtoUpdated1);
        assertNotNull(commandDtoUpdated1.getCmdDeliveryDto());
        assertEquals(CommandState.PackedUp, commandDtoUpdated1.getCmdState());



    }

    @Test(expected = InvalidEntityException.class)
    public void validate_AssignCommandToDelivery_CommandStateNotEdited() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        DiversDto diversDtoSaved = usedForTestForClient.saveDivers(5, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved);
        assertNotNull(diversDtoSaved.getDiversCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosDto());

        CommandDto commandDtoSaved = usedForTestForClient.saveCommand(-1, posDtoSaved, clientDtoSaved, null,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved);
        assertNull(commandDtoSaved.getCmdDeliveryDto());

        DeliveryDto deliveryDtoSaved = usedForTestForClient.saveDelivery(0, posDtoSaved, userBMDtoSaved,
                deliveryService);
        assertNotNull(deliveryDtoSaved);

        CommandDto commandDtoFound = commandService.switchCommandStateTo(commandDtoSaved.getId(), CommandState.Edited);
        assertNotNull(commandDtoFound);
        assertEquals(CommandState.Edited, commandDtoFound.getCmdState());

        CommandDto commandDtoUpdated = commandService.assignCommandToDelivery(commandDtoSaved.getId(),
                deliveryDtoSaved.getId());
        assertNotNull(commandDtoUpdated);
        assertNotNull(commandDtoUpdated.getCmdDeliveryDto());

        CommandDto commandDtoSaved1 = usedForTestForClient.saveCommand(-2, posDtoSaved, null, diversDtoSaved,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved1);

        DeliveryDto deliveryDtoSaved1 = usedForTestForClient.saveDelivery(-4, posDtoSaved, userBMDtoSaved,
                deliveryService);
        assertNotNull(deliveryDtoSaved1);

        CommandDto commandDtoFound1 = commandService.switchCommandStateTo(commandDtoSaved1.getId(), CommandState.Edited);
        assertNotNull(commandDtoFound1);
        assertEquals(CommandState.Edited, commandDtoFound1.getCmdState());

        CommandDto commandDtoSaved2 = usedForTestForClient.saveCommand(-4, posDtoSaved, null, diversDtoSaved,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved2);

        CommandDto commandDtoUpdated1 = commandService.assignCommandToDelivery(commandDtoSaved2.getId(),
                deliveryDtoSaved1.getId());
        assertNotNull(commandDtoUpdated1);
        assertNotNull(commandDtoUpdated1.getCmdDeliveryDto());

    }

    @Test
    public void validate_ResetDeliveryofCommand() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        DiversDto diversDtoSaved = usedForTestForClient.saveDivers(5, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved);
        assertNotNull(diversDtoSaved.getDiversCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosDto());

        CommandDto commandDtoSaved = usedForTestForClient.saveCommand(-1, posDtoSaved, clientDtoSaved, null,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved);
        assertNull(commandDtoSaved.getCmdDeliveryDto());

        DeliveryDto deliveryDtoSaved = usedForTestForClient.saveDelivery(0, posDtoSaved, userBMDtoSaved,
                deliveryService);
        assertNotNull(deliveryDtoSaved);

        CommandDto commandDtoFound = commandService.switchCommandStateTo(commandDtoSaved.getId(), CommandState.Edited);
        assertNotNull(commandDtoFound);
        assertEquals(CommandState.Edited, commandDtoFound.getCmdState());

        CommandDto commandDtoUpdated = commandService.assignCommandToDelivery(commandDtoSaved.getId(),
                deliveryDtoSaved.getId());
        assertNotNull(commandDtoUpdated);
        assertNotNull(commandDtoUpdated.getCmdDeliveryDto());

        CommandDto commandDtoSaved1 = usedForTestForClient.saveCommand(-2, posDtoSaved, null, diversDtoSaved,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved1);

        DeliveryDto deliveryDtoSaved1 = usedForTestForClient.saveDelivery(-4, posDtoSaved, userBMDtoSaved,
                deliveryService);
        assertNotNull(deliveryDtoSaved1);

        CommandDto commandDtoFound1 = commandService.switchCommandStateTo(commandDtoSaved1.getId(), CommandState.Edited);
        assertNotNull(commandDtoFound1);
        assertEquals(CommandState.Edited, commandDtoFound1.getCmdState());

        CommandDto commandDtoUpdated1 = commandService.assignCommandToDelivery(commandDtoSaved1.getId(),
                deliveryDtoSaved1.getId());
        assertNotNull(commandDtoUpdated1);
        assertNotNull(commandDtoUpdated1.getCmdDeliveryDto());

        CommandDto commandDtoUpdatedReset = commandService.resetDeliveryofCommand(commandDtoSaved1.getId());
        assertNotNull(commandDtoUpdatedReset);
        assertEquals(CommandState.Edited, commandDtoUpdatedReset.getCmdState());



    }

    @Test
    public void validate_SwitchCommandStateTo_GoodSwitching() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        DiversDto diversDtoSaved = usedForTestForClient.saveDivers(5, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved);
        assertNotNull(diversDtoSaved.getDiversCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosDto());

        CommandDto commandDtoSaved = usedForTestForClient.saveCommand(-1, posDtoSaved, clientDtoSaved, null,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved);

        //From InEditing to Edited
        CommandDto commandDtoSwitch1 = commandService.switchCommandStateTo(commandDtoSaved.getId(), CommandState.Edited);
        assertNotNull(commandDtoSwitch1);
        assertEquals(CommandState.Edited, commandDtoSwitch1.getCmdState());

        //From Edited to InEditing
        CommandDto commandDtoSwitch2 = commandService.switchCommandStateTo(commandDtoSaved.getId(), CommandState.InEditing);
        assertNotNull(commandDtoSwitch2);
        assertEquals(CommandState.InEditing, commandDtoSwitch2.getCmdState());

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SwitchCommandStateTo_WrongSwitching1() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        DiversDto diversDtoSaved = usedForTestForClient.saveDivers(5, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved);
        assertNotNull(diversDtoSaved.getDiversCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosDto());

        CommandDto commandDtoSaved = usedForTestForClient.saveCommand(-1, posDtoSaved, clientDtoSaved, null,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved);

        //From InEditing to Edited
        CommandDto commandDtoSwitch1 = commandService.switchCommandStateTo(commandDtoSaved.getId(), CommandState.PackedUp);
        assertNotNull(commandDtoSwitch1);
        assertEquals(CommandState.Edited, commandDtoSwitch1.getCmdState());

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SwitchCommandStateTo_WrongSwitching2() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        DiversDto diversDtoSaved = usedForTestForClient.saveDivers(5, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved);
        assertNotNull(diversDtoSaved.getDiversCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosDto());

        CommandDto commandDtoSaved = usedForTestForClient.saveCommand(-1, posDtoSaved, clientDtoSaved, null,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved);

        //From InEditing to Edited
        CommandDto commandDtoSwitch1 = commandService.switchCommandStateTo(commandDtoSaved.getId(), CommandState.PackedUp);
        assertNotNull(commandDtoSwitch1);
        assertEquals(CommandState.Edited, commandDtoSwitch1.getCmdState());

        CommandDto commandDtoSaved1 = usedForTestForClient.saveCommand(-2, posDtoSaved, null, diversDtoSaved,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved1);

        DeliveryDto deliveryDtoSaved1 = usedForTestForClient.saveDelivery(-4, posDtoSaved, userBMDtoSaved,
                deliveryService);
        assertNotNull(deliveryDtoSaved1);

        CommandDto commandDtoFound1 = commandService.switchCommandStateTo(commandDtoSaved1.getId(), CommandState.Edited);
        assertNotNull(commandDtoFound1);
        assertEquals(CommandState.Edited, commandDtoFound1.getCmdState());

        CommandDto commandDtoUpdated1 = commandService.assignCommandToDelivery(commandDtoSaved1.getId(),
                deliveryDtoSaved1.getId());
        assertNotNull(commandDtoUpdated1);
        assertNotNull(commandDtoUpdated1.getCmdDeliveryDto());
        assertEquals(CommandState.PackedUp, commandDtoUpdated1.getCmdState());

        CommandDto commandDtoFound2 = commandService.switchCommandStateTo(commandDtoUpdated1.getId(), CommandState.Edited);

    }


    @Test(expected = InvalidEntityException.class)
    public void validate_SwitchCommandStateTo_WrongSwitching3() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        DiversDto diversDtoSaved = usedForTestForClient.saveDivers(5, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved);
        assertNotNull(diversDtoSaved.getDiversCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosDto());

        CommandDto commandDtoSaved = usedForTestForClient.saveCommand(-1, posDtoSaved, clientDtoSaved, null,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved);

        //From InEditing to Edited
        CommandDto commandDtoSwitch1 = commandService.switchCommandStateTo(commandDtoSaved.getId(), CommandState.Edited);
        assertNotNull(commandDtoSwitch1);
        assertEquals(CommandState.Edited, commandDtoSwitch1.getCmdState());

        //From Edited to InEditing
        CommandDto commandDtoSwitch2 = commandService.switchCommandStateTo(commandDtoSaved.getId(), CommandState.PackedUp);

    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SwitchCommandStateTo_WrongSwitching4() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        DiversDto diversDtoSaved = usedForTestForClient.saveDivers(5, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved);
        assertNotNull(diversDtoSaved.getDiversCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosDto());

        CommandDto commandDtoSaved = usedForTestForClient.saveCommand(-1, posDtoSaved, clientDtoSaved, null,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved);

        //From InEditing to Edited
        CommandDto commandDtoSwitch1 = commandService.switchCommandStateTo(commandDtoSaved.getId(), CommandState.PackedUp);

    }

    @Test
    public void validate_DeleteCommandDto() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        DiversDto diversDtoSaved = usedForTestForClient.saveDivers(5, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved);
        assertNotNull(diversDtoSaved.getDiversCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosDto());

        CommandDto commandDtoSaved = usedForTestForClient.saveCommand(-1, posDtoSaved, clientDtoSaved, null,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved);

        assertTrue(commandService.deleteCommandById(commandDtoSaved.getId()));


    }

    @Test
    public void validate_FindCommandDto() {
        Date startDate1 = new Date();
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        PointofsaleDto posDtoSaved1 = usedForTestForAll.savePointofsale(1, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        ClientDto clientDtoSaved1 = usedForTestForClient.saveClient(7, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved1);
        assertNotNull(clientDtoSaved1.getClientCaDto().getId());

        ClientDto clientDtoSavedPos1 = usedForTestForClient.saveClient(78, posDtoSaved1, clientService);
        assertNotNull(clientDtoSavedPos1);
        assertNotNull(clientDtoSavedPos1.getClientCaDto().getId());

        DiversDto diversDtoSaved = usedForTestForClient.saveDivers(5, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved);
        assertNotNull(diversDtoSaved.getDiversCaDto().getId());

        DiversDto diversDtoSaved1 = usedForTestForClient.saveDivers(8, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved1);
        assertNotNull(diversDtoSaved1.getDiversCaDto().getId());

        DiversDto diversDtoSavedPos1 = usedForTestForClient.saveDivers(8457, posDtoSaved1, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSavedPos1);
        assertNotNull(diversDtoSavedPos1.getDiversCaDto().getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosDto());

        UserBMDto userBMDtoSaved1 = usedForTestForAll.saveUserBM(12, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved1);
        assertNotNull(userBMDtoSaved1.getBmPosDto());

        UserBMDto userBMDtoSavedPos1 = usedForTestForAll.saveUserBM(145, posDtoSaved1, userBMService);
        assertNotNull(userBMDtoSaved1);
        assertNotNull(userBMDtoSaved1.getBmPosDto());

        //pos,client,user
        CommandDto commandDtoSaved = usedForTestForClient.saveCommand(-1, posDtoSaved, clientDtoSaved, null,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved);

        //pos,client1,user
        CommandDto commandDtoSaved1 = usedForTestForClient.saveCommand(-2, posDtoSaved, clientDtoSaved1, null,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved1);

        //pos,client,user1
        CommandDto commandDtoSaved2 = usedForTestForClient.saveCommand(-3, posDtoSaved, clientDtoSaved, null,
                userBMDtoSaved1, commandService);
        assertNotNull(commandDtoSaved2);

        //pos,divers,user
        CommandDto commandDtoSaved3 = usedForTestForClient.saveCommand(-4, posDtoSaved, null, diversDtoSaved,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved3);

        //pos,divers1,user
        CommandDto commandDtoSaved4 = usedForTestForClient.saveCommand(-5, posDtoSaved, null, diversDtoSaved1,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved4);

        //pos,divers,user1
        CommandDto commandDtoSaved5 = usedForTestForClient.saveCommand(-6, posDtoSaved, null, diversDtoSaved,
                userBMDtoSaved1, commandService);
        assertNotNull(commandDtoSaved5);
        CommandDto commandDtoUpdated1 = commandService.switchCommandStateTo(commandDtoSaved5.getId(),
                CommandState.Edited);
        assertNotNull(commandDtoUpdated1);

        //pos1,client,user
        CommandDto commandDtoSaved6 = usedForTestForClient.saveCommand(-1, posDtoSaved1, clientDtoSavedPos1, null,
                userBMDtoSavedPos1, commandService);
        assertNotNull(commandDtoSaved6);

        //pos1,divers,user
        CommandDto commandDtoSaved7 = usedForTestForClient.saveCommand(-2, posDtoSaved1, null, diversDtoSavedPos1,
                userBMDtoSavedPos1, commandService);
        assertNotNull(commandDtoSaved7);

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

            //findAll/PageCommandinPos
            List<CommandDto> commandDtoList = commandService.findAllCommandinPosBetween(posDtoSaved.getId(),
                    startDate.toInstant(), endDate.toInstant());
            assertNotNull(commandDtoList);
            assertEquals(6, commandDtoList.size());
            Page<CommandDto> commandDtoPage = commandService.findPageCommandinPosBetween(posDtoSaved.getId(),
                    startDate.toInstant(), endDate.toInstant(), 0, 4);
            assertNotNull(commandDtoPage);
            assertEquals(2, commandDtoPage.getTotalPages());
            //CommandinPosofcmdState
            List<CommandDto> commandDtoListState1 = commandService.findAllCommandinPosofcmdStateBetween(posDtoSaved.getId(),
                    CommandState.InEditing, startDate.toInstant(), endDate.toInstant());
            assertNotNull(commandDtoListState1);
            assertEquals(5, commandDtoListState1.size());
            List<CommandDto> commandDtoListState2 = commandService.findAllCommandinPosofcmdStateBetween(posDtoSaved.getId(),
                    CommandState.Edited, startDate.toInstant(), endDate.toInstant());
            assertNotNull(commandDtoListState2);
            assertEquals(1, commandDtoListState2.size());

            Page<CommandDto> commandDtoPageState1 = commandService.findPageCommandinPosofcmdStateBetween(posDtoSaved.getId(),
                    CommandState.InEditing, startDate.toInstant(), endDate.toInstant(),0,2);
            assertNotNull(commandDtoPageState1);
            assertEquals(3, commandDtoPageState1.getTotalPages());
            Page<CommandDto> commandDtoPageState2 = commandService.findPageCommandinPosofcmdStateBetween(posDtoSaved.getId(),
                    CommandState.Edited, startDate.toInstant(), endDate.toInstant(), 0, 2);
            assertNotNull(commandDtoPageState2);
            assertEquals(1, commandDtoPageState2.getTotalPages());
            //CommandinPosofcmdType
            //CommandinPosofcmdStatus
            //CommandinPosofcmdDeliveryState
            //CommandinPosofcmdStateAndcmdType
            //CommandinPosofcmdStateAndcmdStatus
            //CommandinPosofcmdStateAndcmdStatus
            //CommandinPosofcmdStateAndcmdDeliveryState
            //CommandinPosofcmdTypeAndcmdStatus
            //CommandinPosofcmdTypeAndcmdDeliveryState
            //CommandinPosofcmdStateAndcmdTypeAndcmdStatus
            //CommandinPosofcmdStateAndcmdTypeAndcmdDeliveryState
            //CommandinPosofcmdStateAndcmdStatusAndcmdDeliveryState
            //CommandinPosofcmdStateAndcmdTypeAndcmdStatusAndcmdDeliveryState
            //CommandinPosofClient
            //CommandinPosofClientAndcmdType
            //CommandinPosofClientAndcmdState
            //CommandinPosofClientAndcmdStatus
            //CommandinPosofClientAndcmdDeliveryState
            //CommandinPosofClientAndcmdTypeAndcmdState
            //CommandinPosofClientAndcmdTypeAndcmdStatus
            //CommandinPosofClientAndcmdTypeAndcmdDeliveryState
            //CommandinPosofClientAndcmdTypeAndcmdStateAndcmdStatus
            //CommandinPosofClientAndcmdTypeAndcmdStateAndcmdDeliveryState
            //CommandinPosofClientAndcmdTypeAndcmdStatusAndcmdDeliveryState
            //CommandinPosofClientAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryState
            //CommandinPosofUserbm
            //CommandinPosofUserbmAndcmdType
            //CommandinPosofUserbmAndcmdState
            //CommandinPosofUserbmAndcmdStatus
            //CommandinPosofUserbmAndcmdDeliveryState
            //CommandinPosofUserbmAndcmdTypeAndcmdState
            //CommandinPosofUserbmAndcmdTypeAndcmdStatus
            //CommandinPosofUserbmAndcmdTypeAndcmdStatus
            //CommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdStatus
            //CommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdDeliveryState
            //CommandinPosofUserbmAndcmdTypeAndcmdStatusAndcmdDeliveryState
            //CommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryState
            //CommandinPosofClientforUserbm
            //CommandinPosofClientAndUserbmAndcmdType
            //CommandinPosofClientAndUserbmAndcmdState
            //CommandinPosofClientAndUserbmAndcmdState
            //CommandinPosofClientAndUserbmAndcmdDeliveryState
            //CommandinPosofClientAndUserbmAndcmdTypeAndcmdState
            //CommandinPosofClientAndUserbmAndcmdTypeAndcmdStatus
            //CommandinPosofClientAndUserbmAndcmdTypeAndcmdStatus
            //CommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdStatus
            //CommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdDeliveryState
            //CommandinPosofClientAndUserbmAndcmdTypeAndcmdStatusAndcmdDeliveryState
            //CommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryState

        } catch (Exception e){
            System.err.println("There is an exception not manage in the program means a big problem appears");
            e.printStackTrace();
        }



    }


}