package com.c2psi.businessmanagement.services.contractsImpl.pos.pos;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCashAccountDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCashOperationDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.services.contractsImpl.UsedForTestForAll;
import com.c2psi.businessmanagement.services.contractsImpl.pos.userbm.UserBMServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.price.BasePriceServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.price.CurrencyConversionServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.price.CurrencyServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.product.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusinessmanagementApplication.class)
public class PosCashAccountServiceImplTest {
    @Autowired
    EnterpriseServiceImpl enterpriseService;
    @Autowired
    PointofsaleServiceImpl pointofsaleService;
    @Autowired
    UserBMServiceImpl userBMService;
    @Autowired
    PosCashAccountServiceImpl posCashAccountService;
    @Autowired
    PosCashOperationServiceImpl posCashOperationService;
    @Autowired
    CurrencyServiceImpl currencyService;
    @Autowired
    CurrencyConversionServiceImpl currencyConversionService;
    @Autowired
    FormatServiceImpl formatService;
    @Autowired
    CategoryServiceImpl categoryService;
    @Autowired
    ProductServiceImpl productService;
    @Autowired
    ProductFormatedServiceImpl productFormatedService;
    @Autowired
    UnitServiceImpl unitService;
    @Autowired
    BasePriceServiceImpl basePriceService;
    @Autowired
    ArticleServiceImpl articleService;
    @Autowired
    UsedForTestForAll usedForTestForAll;
    @Autowired
    UsedForTestForProduct usedForTestForProduct;

    @Test
    public void validate_SaveCashOperation(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        PosCashAccountDto posCashAccountDtoSaved = posDtoSaved.getPosCashaccountDto();
        assertNotNull(posCashAccountDtoSaved);
        assertNotNull(posCashAccountDtoSaved.getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(9, userBMService);
        assertNotNull(userBMDtoSaved);

        BigDecimal qteToSave = BigDecimal.valueOf(15);

        Boolean b = posCashAccountService.saveCashOperation(posCashAccountDtoSaved.getId(), qteToSave,
                OperationType.Credit, userBMDtoSaved.getId(), "Creditation ", "description de l'operation");
        assertTrue(b);

    }

    @Test
    public void validate_FindAllPosCashOperationBetween(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        PosCashAccountDto posCashAccountDtoSaved = posDtoSaved.getPosCashaccountDto();
        assertNotNull(posCashAccountDtoSaved);
        assertNotNull(posCashAccountDtoSaved.getId());

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(9, userBMService);
        assertNotNull(userBMDtoSaved);

        BigDecimal qteToSave = BigDecimal.valueOf(15);

        Boolean b = posCashAccountService.saveCashOperation(posCashAccountDtoSaved.getId(), qteToSave,
                OperationType.Credit, userBMDtoSaved.getId(), "Creditation ", "description de l'operation");
        assertTrue(b);

        b = posCashAccountService.saveCashOperation(posCashAccountDtoSaved.getId(), qteToSave,
                OperationType.Credit, userBMDtoSaved.getId(), "Creditation ", "description de l'operation");
        assertTrue(b);

        PosCashAccountDto posCashAccountDtoFound = posCashAccountService.findPosCashAccountById(posCashAccountDtoSaved.getId());

        assertTrue(BigDecimal.valueOf(30).compareTo(posCashAccountDtoFound.getPcaBalance())==0);

        qteToSave = BigDecimal.valueOf(2);

        b = posCashAccountService.saveCashOperation(posCashAccountDtoSaved.getId(), qteToSave,
                OperationType.Withdrawal, userBMDtoSaved.getId(), "Debitation ", "description de l'operation");
        assertTrue(b);

        PosCashAccountDto posCashAccountDtoFound1 = posCashAccountService.findPosCashAccountById(posCashAccountDtoSaved.getId());

        assertTrue(BigDecimal.valueOf(28).compareTo(posCashAccountDtoFound1.getPcaBalance())==0);


        List<PosCashOperationDto> posCashOperationDtoListAll = posCashOperationService.findAllPosCashOperation(posCashAccountDtoFound.getId());
        assertEquals(3, posCashOperationDtoListAll.size());


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            Date startDate1 = new Date();
            String startDateString = sdf.format(startDate1);

            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate1);
            cal.add(Calendar.DAY_OF_MONTH, 3);
            Date endDate1 = cal.getTime();
            String endDateString = sdf.format(endDate1);

            Date startDate = sdf.parse(startDateString);
            Date endDate = sdf.parse(endDateString);

            /*System.err.println("dateafterInstant == "+startDate.toInstant().compareTo(endDate.toInstant()));
            System.err.println("startDateString == "+startDateString+"  endDateString == "+endDateString);*/
            /*Date startDate = sdf.parse("2023-04-19 00:00:00");
            Date endDate = sdf.parse("2023-04-20 00:00:00");*/

            List<PosCashOperationDto> posCashOperationDtoList = posCashOperationService.findAllPosCashOperationBetween(
                    posCashAccountDtoFound.getId(), startDate.toInstant(), endDate.toInstant()
            );

            /*System.err.println("startDate.toInstant() == "+startDate.toInstant()+" startDate == "+startDate);
            System.err.println("endDate.toInstant() == "+endDate.toInstant()+" endDate == "+endDate);*/

            assertNotNull(posCashOperationDtoList);
            assertEquals(3, posCashOperationDtoList.size());

            Page<PosCashOperationDto> posCashOperationDtoPage = posCashOperationService.findPagePosCashOperationBetween(
                    posCashAccountDtoFound.getId(), startDate.toInstant(), endDate.toInstant(), 0, 1
            );
            assertEquals(3, posCashOperationDtoPage.getTotalPages());

            List<PosCashOperationDto> posCashOperationCreditDtoList = posCashOperationService.findAllPosCashOperationBetween(
                    posCashAccountDtoFound.getId(), OperationType.Credit, startDate.toInstant(), endDate.toInstant()
            );
            assertNotNull(posCashOperationCreditDtoList);
            assertEquals(2, posCashOperationCreditDtoList.size());

            /************
             * On va tester la modification d'une operation
             */
            PosCashOperationDto posCashOperationDtoToUpdate = posCashOperationCreditDtoList.get(0);
            posCashOperationDtoToUpdate.setPoscoAmountinmvt(BigDecimal.valueOf(15));
            System.err.println("updatePosCashOperation "+posCashOperationDtoToUpdate.getPoscoOperationDto());
            PosCashOperationDto posCashOperationDtoUpdated =  posCashOperationService.updatePosCashOperation(posCashOperationDtoToUpdate);

            assertNotNull(posCashOperationDtoUpdated);
            assertTrue(BigDecimal.valueOf(15).compareTo(posCashOperationDtoUpdated.getPoscoAmountinmvt())==0);

            Page<PosCashOperationDto> posCashOperationCreditDtoPage = posCashOperationService.findPagePosCashOperationBetween(
                    posCashAccountDtoFound.getId(), OperationType.Credit, startDate.toInstant(), endDate.toInstant(), 0, 2
            );
            assertNotNull(posCashOperationCreditDtoPage);
            assertEquals(1, posCashOperationCreditDtoPage.getTotalPages());

            List<PosCashOperationDto> posCashOperationDebitDtoList = posCashOperationService.findAllPosCashOperationBetween(
                    posCashAccountDtoFound.getId(), OperationType.Withdrawal, startDate.toInstant(), endDate.toInstant()
            );
            assertNotNull(posCashOperationDebitDtoList);
            assertEquals(1, posCashOperationDebitDtoList.size());

            Page<PosCashOperationDto> posCashOperationDebitDtoPage = posCashOperationService.findPagePosCashOperationBetween(
                    posCashAccountDtoFound.getId(), OperationType.Withdrawal, startDate.toInstant(), endDate.toInstant(), 0, 2
            );
            assertNotNull(posCashOperationDebitDtoPage);
            assertEquals(1, posCashOperationDebitDtoPage.getTotalPages());
        }
        catch (Exception e){
            System.err.println("There is an exception not manage in the program means a big problem appears"+e.getMessage());
            e.printStackTrace();
        }

    }










}