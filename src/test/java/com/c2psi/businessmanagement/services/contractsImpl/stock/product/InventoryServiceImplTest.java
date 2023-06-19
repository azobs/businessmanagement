package com.c2psi.businessmanagement.services.contractsImpl.stock.product;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.product.InventoryDto;
import com.c2psi.businessmanagement.exceptions.DuplicateEntityException;
import com.c2psi.businessmanagement.exceptions.EntityNotFoundException;
import com.c2psi.businessmanagement.exceptions.InvalidEntityException;
import com.c2psi.businessmanagement.services.contracts.stock.product.InventoryService;
import com.c2psi.businessmanagement.services.contractsImpl.UsedForTestForAll;
import com.c2psi.businessmanagement.services.contractsImpl.pos.pos.EnterpriseServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.pos.pos.PointofsaleServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.pos.userbm.UserBMServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.price.CurrencyServiceImpl;
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
public class InventoryServiceImplTest {
    @Autowired
    EnterpriseServiceImpl enterpriseService;
    @Autowired
    UserBMServiceImpl userBMService;
    @Autowired
    CurrencyServiceImpl currencyService;
    @Autowired
    PointofsaleServiceImpl pointofsaleService;
    @Autowired
    InventoryService inventoryService;
    @Autowired
    UsedForTestForAll usedForTestForAll;
    @Autowired
    UsedForTestForProduct usedForTestForProduct;

    @Test
    public void validate_SaveInventory(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        InventoryDto inventoryDtoSaved = usedForTestForProduct.saveInventory(0, posDtoSaved, inventoryService);
        assertNotNull(inventoryDtoSaved);
        assertEquals(posDtoSaved.getId(), inventoryDtoSaved.getInvPosId());
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveInventory_Invalid(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        InventoryDto inventoryDtoSaved = usedForTestForProduct.saveInventory_Invalid(0, posDtoSaved, inventoryService);
        assertNotNull(inventoryDtoSaved);
        assertEquals(posDtoSaved.getId(), inventoryDtoSaved.getInvPosId());
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveInventory_NullPos(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);
        posDtoSaved.setId(null);

        InventoryDto inventoryDtoSaved = usedForTestForProduct.saveInventory(0, posDtoSaved, inventoryService);
        assertNotNull(inventoryDtoSaved);
        assertEquals(posDtoSaved.getId(), inventoryDtoSaved.getInvPosId());
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveInventory_NotExistPos(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);
        posDtoSaved.setId(Long.valueOf(1458796));

        InventoryDto inventoryDtoSaved = usedForTestForProduct.saveInventory(0, posDtoSaved, inventoryService);
        assertNotNull(inventoryDtoSaved);
        assertEquals(posDtoSaved.getId(), inventoryDtoSaved.getInvPosId());
    }

    @Test(expected = DuplicateEntityException.class)
    public void validate_SaveInventory_Duplicated(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        InventoryDto inventoryDtoSaved = usedForTestForProduct.saveInventory(5, posDtoSaved, inventoryService);
        assertNotNull(inventoryDtoSaved);
        assertEquals(posDtoSaved.getId(), inventoryDtoSaved.getInvPosId());
        InventoryDto inventoryDtoSaved1 = usedForTestForProduct.saveInventory(5, posDtoSaved, inventoryService);
    }

    @Test
    public void validate_updateInventory(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        InventoryDto inventoryDtoSaved = usedForTestForProduct.saveInventory(0, posDtoSaved, inventoryService);
        assertNotNull(inventoryDtoSaved);
        assertEquals(posDtoSaved.getId(), inventoryDtoSaved.getInvPosId());
        inventoryDtoSaved.setInvComment("newCode");
        inventoryDtoSaved.setInvCode("newComment");
        InventoryDto inventoryDtoUpdated = inventoryService.updateInventory(inventoryDtoSaved);
        assertNotNull(inventoryDtoUpdated);
        assertEquals("newCode", inventoryDtoUpdated.getInvComment());
        assertEquals(inventoryDtoSaved.getId(), inventoryDtoUpdated.getId());
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_updateInventory_NullId(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        InventoryDto inventoryDtoSaved = usedForTestForProduct.saveInventory(0, posDtoSaved, inventoryService);
        assertNotNull(inventoryDtoSaved);
        assertEquals(posDtoSaved.getId(), inventoryDtoSaved.getInvPosId());
        inventoryDtoSaved.setId(null);
        inventoryDtoSaved.setInvComment("newCode");
        inventoryDtoSaved.setInvCode("newComment");
        InventoryDto inventoryDtoUpdated = inventoryService.updateInventory(inventoryDtoSaved);
        assertNotNull(inventoryDtoUpdated);
        assertEquals("newCode", inventoryDtoUpdated.getInvComment());
        assertEquals(inventoryDtoSaved.getId(), inventoryDtoUpdated.getId());
    }

    @Test(expected = EntityNotFoundException.class)
    public void validate_updateInventory_NotExist(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        InventoryDto inventoryDtoSaved = usedForTestForProduct.saveInventory(0, posDtoSaved, inventoryService);
        assertNotNull(inventoryDtoSaved);
        assertEquals(posDtoSaved.getId(), inventoryDtoSaved.getInvPosId());
        inventoryDtoSaved.setId(Long.valueOf(124587));
        inventoryDtoSaved.setInvComment("newCode");
        inventoryDtoSaved.setInvCode("newComment");
        InventoryDto inventoryDtoUpdated = inventoryService.updateInventory(inventoryDtoSaved);
        assertNotNull(inventoryDtoUpdated);
        assertEquals("newCode", inventoryDtoUpdated.getInvComment());
        assertEquals(inventoryDtoSaved.getId(), inventoryDtoUpdated.getId());
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_updateInventory_updatePos(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);
        PointofsaleDto posDtoSaved1 = usedForTestForAll.savePointofsale(1, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved1);

        InventoryDto inventoryDtoSaved = usedForTestForProduct.saveInventory(0, posDtoSaved, inventoryService);
        assertNotNull(inventoryDtoSaved);
        assertEquals(posDtoSaved.getId(), inventoryDtoSaved.getInvPosId());
        inventoryDtoSaved.setInvComment("newCode");
        inventoryDtoSaved.setInvCode("newComment");
        inventoryDtoSaved.setInvPosId(posDtoSaved1.getId());
        InventoryDto inventoryDtoUpdated = inventoryService.updateInventory(inventoryDtoSaved);
        assertNotNull(inventoryDtoUpdated);
        assertEquals("newCode", inventoryDtoUpdated.getInvComment());
        assertEquals(inventoryDtoSaved.getId(), inventoryDtoUpdated.getId());
    }

    @Test(expected = DuplicateEntityException.class)
    public void validate_updateInventory_Duplicated(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        InventoryDto inventoryDtoSaved = usedForTestForProduct.saveInventory(0, posDtoSaved, inventoryService);
        assertNotNull(inventoryDtoSaved);
        assertEquals(posDtoSaved.getId(), inventoryDtoSaved.getInvPosId());
        inventoryDtoSaved.setInvComment("newCode");
        inventoryDtoSaved.setInvCode("newComment");
        InventoryDto inventoryDtoUpdated = inventoryService.updateInventory(inventoryDtoSaved);
        assertNotNull(inventoryDtoUpdated);
        assertEquals("newCode", inventoryDtoUpdated.getInvComment());
        assertEquals(inventoryDtoSaved.getId(), inventoryDtoUpdated.getId());
        InventoryDto inventoryDtoSaved1 = usedForTestForProduct.saveInventory(3, posDtoSaved, inventoryService);
        inventoryDtoSaved1.setInvCode("newComment");
        InventoryDto inventoryDtoUpdated1 = inventoryService.updateInventory(inventoryDtoSaved1);

    }

    @Test
    public void validate_findInventoryByCodeinPos(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        InventoryDto inventoryDtoSaved = usedForTestForProduct.saveInventory(0, posDtoSaved, inventoryService);
        assertNotNull(inventoryDtoSaved);
        assertEquals(posDtoSaved.getId(), inventoryDtoSaved.getInvPosId());

        InventoryDto inventoryDtoFound = inventoryService.findInventoryByCodeinPos(inventoryDtoSaved.getInvCode(),
                posDtoSaved.getId());
        assertNotNull(inventoryDtoFound);
        assertEquals(inventoryDtoSaved.getId(), inventoryDtoFound.getId());
    }

    @Test
    public void validate_findAllInventoryinPosBetween(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate1 = new Date();
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        InventoryDto inventoryDtoSaved = usedForTestForProduct.saveInventory(0, posDtoSaved, inventoryService);
        assertNotNull(inventoryDtoSaved);
        assertEquals(posDtoSaved.getId(), inventoryDtoSaved.getInvPosId());
        InventoryDto inventoryDtoSaved1 = usedForTestForProduct.saveInventory(1, posDtoSaved, inventoryService);
        assertNotNull(inventoryDtoSaved1);
        InventoryDto inventoryDtoSaved2 = usedForTestForProduct.saveInventory(2, posDtoSaved, inventoryService);
        assertNotNull(inventoryDtoSaved2);

        try {
            String startDateString = sdf.format(startDate1);

            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate1);
            cal.add(Calendar.DAY_OF_MONTH, 1);
            Date endDate1 = cal.getTime();
            String endDateString = sdf.format(endDate1);

            Date startDate = sdf.parse(startDateString);
            Date endDate = sdf.parse(endDateString);

            //findAllInventoryinPosBetween
            List<InventoryDto> inventoryDtoList = inventoryService.
                    findAllInventoryinPosBetween(posDtoSaved.getId(), startDate.toInstant(), endDate.toInstant());
            assertNotNull(inventoryDtoList);
            assertEquals(3, inventoryDtoList.size());

            //findPageInventoryinPosBetween
            Page<InventoryDto> inventoryDtoPage = inventoryService.
                    findPageInventoryinPosBetween(posDtoSaved.getId(), startDate.toInstant(), endDate.toInstant(), 0, 2);
            assertNotNull(inventoryDtoPage);
            assertEquals(2, inventoryDtoPage.getTotalPages());

        }
        catch (Exception e){
            System.err.println("There is an exception not manage in the program means a big problem appears");
            e.printStackTrace();
        }
    }

    @Test
    public void validate_DeleteInventory(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        InventoryDto inventoryDtoSaved = usedForTestForProduct.saveInventory(0, posDtoSaved, inventoryService);
        assertNotNull(inventoryDtoSaved);
        assertEquals(posDtoSaved.getId(), inventoryDtoSaved.getInvPosId());

        assertTrue(inventoryService.deleteInventoryById(inventoryDtoSaved.getId()));
    }


}