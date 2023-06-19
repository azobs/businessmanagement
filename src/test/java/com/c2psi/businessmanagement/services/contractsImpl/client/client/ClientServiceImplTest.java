package com.c2psi.businessmanagement.services.contractsImpl.client.client;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.exceptions.DuplicateEntityException;
import com.c2psi.businessmanagement.exceptions.EntityNotFoundException;
import com.c2psi.businessmanagement.exceptions.InvalidEntityException;
import com.c2psi.businessmanagement.services.contractsImpl.UsedForTestForAll;
import com.c2psi.businessmanagement.services.contractsImpl.client.UsedForTestForClient;
import com.c2psi.businessmanagement.services.contractsImpl.pos.pos.EnterpriseServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.pos.pos.PointofsaleServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.pos.pos.PosCashAccountServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.pos.userbm.UserBMServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.price.CurrencyServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusinessmanagementApplication.class)
public class ClientServiceImplTest {
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
    ClientServiceImpl clientService;
    @Autowired
    UsedForTestForAll usedForTestForAll;
    @Autowired
    UsedForTestForClient usedForTestForClient;

    @Test
    public void validate_SaveClient() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveClient_Invalid() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient_Invalid(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveClient_IdPointofsaleNull() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        posDtoSaved.setId(null);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());
    }

    @Test(expected = EntityNotFoundException.class)
    public void validate_SaveClient_NotFoundPointofsaleNull() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        posDtoSaved.setId(Long.valueOf(548769));

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());
    }

    @Test(expected = DuplicateEntityException.class)
    public void validate_SaveClient_Duplicate() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        ClientDto clientDtoSaved1 = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        //The above line is supposed to launch the expected exception
    }

    @Test
    public void validate_UpdateClient() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        clientDtoSaved.setClientCni("458796312");
        clientDtoSaved.setClientOthername("Vidal");
        clientDtoSaved.getClientAddressDto().setEmail("testblondelllll@gmail.com");

        ClientDto clientDtoUpdated = clientService.updateClient(clientDtoSaved);
        assertNotNull(clientDtoUpdated);
        assertEquals("Vidal", clientDtoUpdated.getClientOthername());
        assertEquals("testblondelllll@gmail.com", clientDtoUpdated.getClientAddressDto().getEmail());
    }

    @Test(expected = DuplicateEntityException.class)
    public void validate_UpdateClient_DuplicateCni() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        clientDtoSaved.setClientCni("458796312");
        clientDtoSaved.setClientOthername("Vidal");
        clientDtoSaved.getClientAddressDto().setEmail("testblondelllll@gmail.com");

        ClientDto clientDtoUpdated = clientService.updateClient(clientDtoSaved);
        assertNotNull(clientDtoUpdated);
        assertEquals("Vidal", clientDtoUpdated.getClientOthername());
        assertEquals("testblondelllll@gmail.com", clientDtoUpdated.getClientAddressDto().getEmail());

        ClientDto clientDtoSaved1 = usedForTestForClient.saveClient(6, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved1);
        assertNotNull(clientDtoSaved1.getClientCaDto().getId());

        clientDtoSaved1.setClientCni("458796312");
        ClientDto clientDtoUpdated1 = clientService.updateClient(clientDtoSaved1);
    }

    @Test(expected = DuplicateEntityException.class)
    public void validate_UpdateClient_DuplicateFullname() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        clientDtoSaved.setClientCni("458796312");
        clientDtoSaved.setClientName("Vidal");
        clientDtoSaved.setClientOthername("Vidal");
        clientDtoSaved.getClientAddressDto().setEmail("testblondelllll@gmail.com");

        ClientDto clientDtoUpdated = clientService.updateClient(clientDtoSaved);
        assertNotNull(clientDtoUpdated);
        assertEquals("Vidal", clientDtoUpdated.getClientOthername());
        assertEquals("testblondelllll@gmail.com", clientDtoUpdated.getClientAddressDto().getEmail());

        ClientDto clientDtoSaved1 = usedForTestForClient.saveClient(6, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved1);
        assertNotNull(clientDtoSaved1.getClientCaDto().getId());

        clientDtoSaved1.setClientName("Vidal");
        clientDtoSaved1.setClientOthername("Vidal");
        ClientDto clientDtoUpdated1 = clientService.updateClient(clientDtoSaved1);
    }

    @Test(expected = DuplicateEntityException.class)
    public void validate_UpdateClient_DuplicateEmail() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        clientDtoSaved.setClientCni("4587963121");
        clientDtoSaved.setClientName("Vidal1");
        clientDtoSaved.setClientOthername("Vidal1");
        clientDtoSaved.getClientAddressDto().setEmail("testblondelllll@gmail.com");

        ClientDto clientDtoUpdated = clientService.updateClient(clientDtoSaved);
        assertNotNull(clientDtoUpdated);
        assertEquals("Vidal1", clientDtoUpdated.getClientOthername());
        assertEquals("testblondelllll@gmail.com", clientDtoUpdated.getClientAddressDto().getEmail());

        ClientDto clientDtoSaved1 = usedForTestForClient.saveClient(6, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved1);
        assertNotNull(clientDtoSaved1.getClientCaDto().getId());

        clientDtoSaved1.setClientCni("45879631211");
        clientDtoSaved1.setClientName("Vidal");
        clientDtoSaved1.setClientOthername("Vidal");
        clientDtoSaved1.getClientAddressDto().setEmail("testblondelllll@gmail.com");
        ClientDto clientDtoUpdated1 = clientService.updateClient(clientDtoSaved1);
    }

    @Test
    public void validate_FindClientByCniofPos() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        ClientDto clientDtoFound = clientService.findClientByCniofPos(clientDtoSaved.getClientCni(), posDtoSaved.getId());
        assertNotNull(clientDtoFound);
        assertEquals(clientDtoSaved.getClientName(), clientDtoFound.getClientName());
    }

    @Test
    public void validate_FindClientByNameofPos() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        ClientDto clientDtoFound = clientService.findClientByNameofPos(clientDtoSaved.getClientName(),
                clientDtoSaved.getClientOthername(), posDtoSaved.getId());
        assertNotNull(clientDtoFound);
        assertEquals(clientDtoSaved.getClientName(), clientDtoFound.getClientName());
    }

    @Test
    public void validate_FindClientByEmail() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        ClientDto clientDtoFound = clientService.findClientByEmail(clientDtoSaved.getClientAddressDto().getEmail());
        assertNotNull(clientDtoFound);
        assertEquals(clientDtoSaved.getClientName(), clientDtoFound.getClientName());
    }

    @Test
    public void validate_FindAllClientofPos() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        ClientDto clientDtoSaved1 = usedForTestForClient.saveClient(6, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved1);
        assertNotNull(clientDtoSaved1.getClientCaDto().getId());

        ClientDto clientDtoSaved2 = usedForTestForClient.saveClient(7, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved2);
        assertNotNull(clientDtoSaved2.getClientCaDto().getId());

        List<ClientDto> clientDtoList = clientService.findAllClientofPos(posDtoSaved.getId());
        assertNotNull(clientDtoList);
        assertEquals(3, clientDtoList.size());
    }

    @Test
    public void validate_FindPageClientofPos() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        ClientDto clientDtoSaved1 = usedForTestForClient.saveClient(6, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved1);
        assertNotNull(clientDtoSaved1.getClientCaDto().getId());

        ClientDto clientDtoSaved2 = usedForTestForClient.saveClient(7, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved2);
        assertNotNull(clientDtoSaved2.getClientCaDto().getId());

        Page<ClientDto> clientDtoPage = clientService.findPageClientofPos(posDtoSaved.getId(), 0, 2);
        assertNotNull(clientDtoPage);
        assertEquals(2, clientDtoPage.getTotalPages());
    }

    @Test
    public void validate_DeleteClient() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        ClientDto clientDtoSaved = usedForTestForClient.saveClient(5, posDtoSaved, clientService);
        assertNotNull(clientDtoSaved);
        assertNotNull(clientDtoSaved.getClientCaDto().getId());

        assertTrue(clientService.deleteClientById(clientDtoSaved.getId()));
    }

}