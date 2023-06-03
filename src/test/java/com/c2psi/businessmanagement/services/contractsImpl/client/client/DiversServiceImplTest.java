package com.c2psi.businessmanagement.services.contractsImpl.client.client;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.dtos.client.client.DiversDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.exceptions.DuplicateEntityException;
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
import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusinessmanagementApplication.class)
public class DiversServiceImplTest {
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
    DiversServiceImpl diversService;
    @Autowired
    DiversCashAccountServiceImpl diversCashAccountService;
    @Autowired
    UsedForTestForAll usedForTestForAll;
    @Autowired
    UsedForTestForClient usedForTestForClient;

    @Test
    public void validate_SaveDivers() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        DiversDto diversDtoSaved = usedForTestForClient.saveDivers(5, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved);
        assertNotNull(diversDtoSaved.getDiversCaDto().getId());
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveDivers_Invalid() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        DiversDto diversDtoSaved = usedForTestForClient.saveDivers_Invalid(5, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved);
        assertNotNull(diversDtoSaved.getDiversCaDto().getId());
    }

    @Test(expected = DuplicateEntityException.class)
    public void validate_SaveDivers_UpdateAndDuplicate() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        DiversDto diversDtoSaved = usedForTestForClient.saveDivers(5, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved);
        assertNotNull(diversDtoSaved.getDiversCaDto().getId());

        DiversDto diversDtoSaved1 = usedForTestForClient.saveDivers(6, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved1);
        assertNotNull(diversDtoSaved1.getDiversCaDto().getId());

        assertEquals(diversDtoSaved1.getDiversCaDto().getId(), diversDtoSaved.getDiversCaDto().getId());

        diversDtoSaved1.getDiversAddressDto().setEmail("dsdsds@gmail.com");
        DiversDto diversDto1Updated = diversService.updateDivers(diversDtoSaved1);
        assertNotNull(diversDto1Updated);
        assertEquals(diversDtoSaved1.getId(), diversDto1Updated.getId());

        diversDtoSaved.getDiversAddressDto().setEmail("dsdsds@gmail.com");
        DiversDto diversDto1Updated1 = diversService.updateDivers(diversDtoSaved);
    }

    @Test
    public void validate_SaveDivers_FindDivers() {
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        DiversDto diversDtoSaved = usedForTestForClient.saveDivers(5, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved);
        assertNotNull(diversDtoSaved.getDiversCaDto().getId());

        DiversDto diversDtoSaved1 = usedForTestForClient.saveDivers(6, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved1);
        assertNotNull(diversDtoSaved1.getDiversCaDto().getId());

        assertEquals(diversDtoSaved1.getDiversCaDto().getId(), diversDtoSaved.getDiversCaDto().getId());

        diversDtoSaved1.getDiversAddressDto().setEmail("dsdsds@gmail.com");
        DiversDto diversDto1Updated = diversService.updateDivers(diversDtoSaved1);
        assertNotNull(diversDto1Updated);
        assertEquals(diversDtoSaved1.getId(), diversDto1Updated.getId());

        DiversDto diversDtoFound = diversService.findDiversByEmail("dsdsds@gmail.com");
        assertNotNull(diversDtoFound);

        DiversDto diversDtoSaved2 = usedForTestForClient.saveDivers(127, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved2);
        assertNotNull(diversDtoSaved2.getDiversCaDto().getId());
        diversDtoSaved2.setDiversName("cedric");
        DiversDto diversDto1Updated2 = diversService.updateDivers(diversDtoSaved2);
        assertNotNull(diversDto1Updated2);

        DiversDto diversDtoSaved3 = usedForTestForClient.saveDivers(148, posDtoSaved, diversCashAccountService,
                diversService);
        assertNotNull(diversDtoSaved3);
        assertNotNull(diversDtoSaved3.getDiversCaDto().getId());
        diversDtoSaved3.setDiversName("cedric");
        DiversDto diversDto1Updated3 = diversService.updateDivers(diversDtoSaved3);
        assertNotNull(diversDto1Updated3);

        assertEquals(4, diversService.findAllDiversofPos(posDtoSaved.getId()).size());
        assertEquals(2, diversService.findAllDiversByNameinPos("cedric", posDtoSaved.getId()).size());

    }

}