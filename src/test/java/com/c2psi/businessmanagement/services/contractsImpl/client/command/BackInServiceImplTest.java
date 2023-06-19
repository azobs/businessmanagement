package com.c2psi.businessmanagement.services.contractsImpl.client.command;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.dtos.client.command.BackInDto;
import com.c2psi.businessmanagement.dtos.client.command.CommandDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.services.contractsImpl.UsedForTestForAll;
import com.c2psi.businessmanagement.services.contractsImpl.client.UsedForTestForClient;
import com.c2psi.businessmanagement.services.contractsImpl.client.client.ClientServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.pos.pos.EnterpriseServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.pos.pos.PointofsaleServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.pos.userbm.UserBMServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.price.CurrencyServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.product.UsedForTestForProduct;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusinessmanagementApplication.class)
public class BackInServiceImplTest {
    @Autowired
    UserBMServiceImpl userBMService;
    @Autowired
    CommandServiceImpl commandService;
    @Autowired
    PointofsaleServiceImpl pointofsaleService;
    @Autowired
    EnterpriseServiceImpl enterpriseService;
    @Autowired
    CurrencyServiceImpl currencyService;
    @Autowired
    ClientServiceImpl clientService;
    @Autowired
    BackInServiceImpl backInService;
    @Autowired
    UsedForTestForAll usedForTestForAll;
    @Autowired
    UsedForTestForClient usedForTestForClient;
    @Autowired
    UsedForTestForProduct usedForTestForProduct;


    @Test
    public void validate_SaveBackIn() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosId());

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        CommandDto commandDtoSaved = usedForTestForClient.saveCommand(-1, posDtoSaved, clientDtoSaved, null,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved);

        BackInDto backInDtoSaved = usedForTestForClient.saveBackIn(5, commandDtoSaved, userBMDtoSaved,
                posDtoSaved, backInService);
        assertNotNull(backInDtoSaved);
    }

    @Test
    public void validate_UpdateBackIn() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosId());

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        CommandDto commandDtoSaved = usedForTestForClient.saveCommand(-1, posDtoSaved, clientDtoSaved, null,
                userBMDtoSaved, commandService);
        assertNotNull(commandDtoSaved);

        BackInDto backInDtoSaved = usedForTestForClient.saveBackIn(5, commandDtoSaved, userBMDtoSaved,
                posDtoSaved, backInService);
        assertNotNull(backInDtoSaved);

        backInDtoSaved.setBiComment("new new Comment");

        BackInDto backInDtoUpdated = backInService.updateBackIn(backInDtoSaved);
        assertNotNull(backInDtoUpdated);
        assertEquals("new new Comment", backInDtoUpdated.getBiComment());

        BackInDto backInDtoFound = backInService.findBackInofCommand(commandDtoSaved.getId());
        backInDtoFound.setBiComment("new new new Comment");
        BackInDto backInDtoUpdated1 = backInService.updateBackIn(backInDtoFound);
        assertNotNull(backInDtoUpdated1);
        assertEquals("new new new Comment", backInDtoUpdated1.getBiComment());

    }
}