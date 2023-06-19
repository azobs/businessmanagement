package com.c2psi.businessmanagement.services.contractsImpl.client.delivery;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.Enumerations.DeliveryState;
import com.c2psi.businessmanagement.dtos.client.delivery.DeliveryDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.exceptions.DuplicateEntityException;
import com.c2psi.businessmanagement.exceptions.InvalidEntityException;
import com.c2psi.businessmanagement.services.contractsImpl.UsedForTestForAll;
import com.c2psi.businessmanagement.services.contractsImpl.client.UsedForTestForClient;
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
public class DeliveryServiceImplTest {

    @Autowired
    UsedForTestForAll usedForTestForAll;
    @Autowired
    UsedForTestForClient usedForTestForClient;
    @Autowired
    PointofsaleServiceImpl pointofsaleService;
    @Autowired
    EnterpriseServiceImpl enterpriseService;
    @Autowired
    UserBMServiceImpl userBMService;
    @Autowired
    CurrencyServiceImpl currencyService;
    @Autowired
    DeliveryServiceImpl deliveryService;

    @Test
    public void validate_SaveDelivery(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosId());

        DeliveryDto deliveryDtoSaved = usedForTestForClient.saveDelivery(0, posDtoSaved, userBMDtoSaved,
                deliveryService);
        assertNotNull(deliveryDtoSaved);
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveDelivery_Invalid(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosId());

        DeliveryDto deliveryDtoSaved = usedForTestForClient.saveDelivery_Invalid(0, posDtoSaved, userBMDtoSaved,
                deliveryService);
        assertNotNull(deliveryDtoSaved);
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveDelivery_Posnull(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);
        posDtoSaved.setId(null);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosId());

        DeliveryDto deliveryDtoSaved = usedForTestForClient.saveDelivery_Invalid(0, posDtoSaved, userBMDtoSaved,
                deliveryService);
        assertNotNull(deliveryDtoSaved);
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveDelivery_PosNotexist(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);
        posDtoSaved.setId(Long.valueOf(124578));

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosId());

        DeliveryDto deliveryDtoSaved = usedForTestForClient.saveDelivery_Invalid(0, posDtoSaved, userBMDtoSaved,
                deliveryService);
        assertNotNull(deliveryDtoSaved);
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveDelivery_Userbmnull(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosId());
        userBMDtoSaved.setId(null);

        DeliveryDto deliveryDtoSaved = usedForTestForClient.saveDelivery_Invalid(0, posDtoSaved, userBMDtoSaved,
                deliveryService);
        assertNotNull(deliveryDtoSaved);
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveDelivery_UserbmnotExist(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosId());
        userBMDtoSaved.setId(Long.valueOf(457812));

        DeliveryDto deliveryDtoSaved = usedForTestForClient.saveDelivery_Invalid(0, posDtoSaved, userBMDtoSaved,
                deliveryService);
        assertNotNull(deliveryDtoSaved);
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveDelivery_PosDifferent(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        PointofsaleDto posDtoSaved1 = usedForTestForAll.savePointofsale(1, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved1);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosId());

        DeliveryDto deliveryDtoSaved = usedForTestForClient.saveDelivery_Invalid(0, posDtoSaved1, userBMDtoSaved,
                deliveryService);
        assertNotNull(deliveryDtoSaved);
    }

    @Test(expected = DuplicateEntityException.class)
    public void validate_SaveDelivery_Duplicated(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosId());

        DeliveryDto deliveryDtoSaved = usedForTestForClient.saveDelivery(0, posDtoSaved, userBMDtoSaved,
                deliveryService);
        assertNotNull(deliveryDtoSaved);

        DeliveryDto deliveryDtoSaved1 = usedForTestForClient.saveDelivery(0, posDtoSaved, userBMDtoSaved,
                deliveryService);
        assertNotNull(deliveryDtoSaved1);
    }

    @Test
    public void validate_UpdateDelivery(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosId());

        DeliveryDto deliveryDtoSaved = usedForTestForClient.saveDelivery(0, posDtoSaved, userBMDtoSaved,
                deliveryService);
        assertNotNull(deliveryDtoSaved);

        DeliveryDto deliveryDtoToUpdate = deliveryService.findDeliveryById(deliveryDtoSaved.getId());
        assertNotNull(deliveryDtoToUpdate);
        deliveryDtoToUpdate.setDeliveryCode("newCodeofDelivery");
        deliveryDtoToUpdate.setDeliveryComment("newComment");
        DeliveryDto deliveryDtoUpdated = deliveryService.updateDelivery(deliveryDtoToUpdate);
        assertNotNull(deliveryDtoUpdated);
        assertEquals("newCodeofDelivery", deliveryDtoUpdated.getDeliveryCode());
    }

    @Test
    public void validate_FindDeliveryByCodeinPos(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosId());

        DeliveryDto deliveryDtoSaved = usedForTestForClient.saveDelivery(0, posDtoSaved, userBMDtoSaved,
                deliveryService);
        assertNotNull(deliveryDtoSaved);

        DeliveryDto deliveryDtoFound = deliveryService.findDeliveryByCodeinPos(deliveryDtoSaved.getDeliveryCode(),
                posDtoSaved.getId());
        assertNotNull(deliveryDtoFound);

    }

    @Test
    public void validate_FindAllDeliveryinPosBetween(){
        Date startDate1 = new Date();
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosId());

        DeliveryDto deliveryDtoSaved = usedForTestForClient.saveDelivery(0, posDtoSaved, userBMDtoSaved,
                deliveryService);
        assertNotNull(deliveryDtoSaved);

        DeliveryDto deliveryDtoSaved1 = usedForTestForClient.saveDelivery(1, posDtoSaved, userBMDtoSaved,
                deliveryService);
        assertNotNull(deliveryDtoSaved1);

        DeliveryDto deliveryDtoSaved2 = usedForTestForClient.saveDelivery(2, posDtoSaved, userBMDtoSaved,
                deliveryService);
        assertNotNull(deliveryDtoSaved2);

        deliveryDtoSaved2.setDeliveryState(DeliveryState.Finish);
        DeliveryDto deliveryDtoUpdated = deliveryService.updateDelivery(deliveryDtoSaved2);
        assertNotNull(deliveryDtoUpdated);

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

            ///////////findAllDeliveryinPosBetween
            List<DeliveryDto> deliveryDtoList = deliveryService.findAllDeliveryinPosBetween(posDtoSaved.getId(),
                    startDate.toInstant(), endDate.toInstant());
            assertNotNull(deliveryDtoList);
            assertEquals(3, deliveryDtoList.size());

            /////////////findPageDeliveryinPosBetween
            Page<DeliveryDto> deliveryDtoPage = deliveryService.findPageDeliveryinPosBetween(posDtoSaved.getId(), startDate.toInstant(),
                    endDate.toInstant(), 0, 2);
            assertNotNull(deliveryDtoPage);
            assertEquals(2, deliveryDtoPage.getTotalPages());

            ///////////findAllDeliveryinPoswithStateBetween
            List<DeliveryDto> deliveryDtoList1 = deliveryService.findAllDeliveryinPoswithStateBetween(posDtoSaved.getId(),
                    DeliveryState.Finish, startDate.toInstant(), endDate.toInstant());
            assertNotNull(deliveryDtoList1);
            assertEquals(1, deliveryDtoList1.size());

            /////////////findPageDeliveryinPoswithStateBetween
            Page<DeliveryDto> deliveryDtoPage1 = deliveryService.findPageDeliveryinPoswithStateBetween(posDtoSaved.getId(),
                    DeliveryState.Finish, startDate.toInstant(), endDate.toInstant(), 0, 2);
            assertNotNull(deliveryDtoPage1);
            assertEquals(1, deliveryDtoPage1.getTotalPages());

            ///////////findAllDeliveryinPosforUserbmBetween
            List<DeliveryDto> deliveryDtoList2 = deliveryService.findAllDeliveryinPosforUserbmBetween(posDtoSaved.getId(),
                    userBMDtoSaved.getId(), startDate.toInstant(), endDate.toInstant());
            assertNotNull(deliveryDtoList2);
            assertEquals(3, deliveryDtoList2.size());

            /////////////findPageDeliveryinPosforUserbmBetween
            Page<DeliveryDto> deliveryDtoPage2 = deliveryService.findPageDeliveryinPosforUserbmBetween(posDtoSaved.getId(),
                    userBMDtoSaved.getId(), startDate.toInstant(), endDate.toInstant(), 0, 2);
            assertNotNull(deliveryDtoPage2);
            assertEquals(2, deliveryDtoPage2.getTotalPages());

            ///////////findAllDeliveryinPosforUserbmwithStateBetween
            List<DeliveryDto> deliveryDtoList3 = deliveryService.findAllDeliveryinPosforUserbmwithStateBetween(posDtoSaved.getId(),
                    userBMDtoSaved.getId(), DeliveryState.Delivery, startDate.toInstant(), endDate.toInstant());
            assertNotNull(deliveryDtoList3);
            assertEquals(2, deliveryDtoList3.size());

            /////////////findPageDeliveryinPosforUserbmwithStateBetween
            Page<DeliveryDto> deliveryDtoPage3 = deliveryService.findPageDeliveryinPosforUserbmwithStateBetween(posDtoSaved.getId(),
                    userBMDtoSaved.getId(), DeliveryState.Delivery, startDate.toInstant(), endDate.toInstant(), 0, 2);
            assertNotNull(deliveryDtoPage3);
            assertEquals(1, deliveryDtoPage3.getTotalPages());


        } catch (Exception e){
            System.err.println("There is an exception not manage in the program means a big problem appears|| "+e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void validate_DeleteDeliveryById(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosId());

        DeliveryDto deliveryDtoSaved = usedForTestForClient.saveDelivery(0, posDtoSaved, userBMDtoSaved,
                deliveryService);
        assertNotNull(deliveryDtoSaved);

        DeliveryDto deliveryDtoFound = deliveryService.findDeliveryByCodeinPos(deliveryDtoSaved.getDeliveryCode(),
                posDtoSaved.getId());
        assertNotNull(deliveryDtoFound);
        assertTrue(deliveryService.deleteDeliveryById(deliveryDtoFound.getId()));
    }

}