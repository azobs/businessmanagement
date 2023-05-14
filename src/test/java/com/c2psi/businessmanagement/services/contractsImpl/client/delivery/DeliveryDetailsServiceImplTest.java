package com.c2psi.businessmanagement.services.contractsImpl.client.delivery;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.dtos.client.delivery.DeliveryDetailsDto;
import com.c2psi.businessmanagement.dtos.client.delivery.DeliveryDto;
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

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusinessmanagementApplication.class)
public class DeliveryDetailsServiceImplTest {

    @Autowired
    UsedForTestForAll usedForTestForAll;
    @Autowired
    UsedForTestForClient usedForTestForClient;
    @Autowired
    UsedForTestForProduct usedForTestForProduct;
    @Autowired
    UsedForTestForProvider usedForTestForProvider;
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
    @Autowired
    DeliveryDetailsServiceImpl deliveryDetailsService;
    @Autowired
    PackagingServiceImpl packagingService;
    @Autowired
    ProviderServiceImpl providerService;

    @Test
    public void validate_SaveDelivery(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosDto());

        DeliveryDto deliveryDtoSaved = usedForTestForClient.saveDelivery(0, posDtoSaved, userBMDtoSaved,
                deliveryService);
        assertNotNull(deliveryDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        PackagingDto packagingDtoSaved = usedForTestForProduct.savePackaging(0, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved);

        DeliveryDetailsDto deliveryDetailsDtoSaved = usedForTestForClient.saveDeliveryDetails(0, posDtoSaved,
                packagingDtoSaved, deliveryDtoSaved, deliveryDetailsService);
        assertNotNull(deliveryDetailsDtoSaved);
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveDelivery_Invalid(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosDto());

        DeliveryDto deliveryDtoSaved = usedForTestForClient.saveDelivery(0, posDtoSaved, userBMDtoSaved,
                deliveryService);
        assertNotNull(deliveryDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        PackagingDto packagingDtoSaved = usedForTestForProduct.savePackaging(0, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved);

        DeliveryDetailsDto deliveryDetailsDtoSaved = usedForTestForClient.saveDeliveryDetails_Invalid(0, posDtoSaved,
                packagingDtoSaved, deliveryDtoSaved, deliveryDetailsService);
        assertNotNull(deliveryDetailsDtoSaved);
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveDelivery_Packagingnull(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosDto());

        DeliveryDto deliveryDtoSaved = usedForTestForClient.saveDelivery(0, posDtoSaved, userBMDtoSaved,
                deliveryService);
        assertNotNull(deliveryDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        PackagingDto packagingDtoSaved = usedForTestForProduct.savePackaging(0, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved);
        packagingDtoSaved.setId(null);

        DeliveryDetailsDto deliveryDetailsDtoSaved = usedForTestForClient.saveDeliveryDetails(0, posDtoSaved,
                packagingDtoSaved, deliveryDtoSaved, deliveryDetailsService);
        assertNotNull(deliveryDetailsDtoSaved);
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveDelivery_Packagingnotexist(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosDto());

        DeliveryDto deliveryDtoSaved = usedForTestForClient.saveDelivery(0, posDtoSaved, userBMDtoSaved,
                deliveryService);
        assertNotNull(deliveryDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        PackagingDto packagingDtoSaved = usedForTestForProduct.savePackaging(0, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved);
        packagingDtoSaved.setId(Long.valueOf(45123));

        DeliveryDetailsDto deliveryDetailsDtoSaved = usedForTestForClient.saveDeliveryDetails(0, posDtoSaved,
                packagingDtoSaved, deliveryDtoSaved, deliveryDetailsService);
        assertNotNull(deliveryDetailsDtoSaved);
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveDelivery_Deliverynull(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosDto());

        DeliveryDto deliveryDtoSaved = usedForTestForClient.saveDelivery(0, posDtoSaved, userBMDtoSaved,
                deliveryService);
        assertNotNull(deliveryDtoSaved);
        deliveryDtoSaved.setId(null);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        PackagingDto packagingDtoSaved = usedForTestForProduct.savePackaging(0, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved);

        DeliveryDetailsDto deliveryDetailsDtoSaved = usedForTestForClient.saveDeliveryDetails(0, posDtoSaved,
                packagingDtoSaved, deliveryDtoSaved, deliveryDetailsService);
        assertNotNull(deliveryDetailsDtoSaved);
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_SaveDelivery_Deliverynotexist(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosDto());

        DeliveryDto deliveryDtoSaved = usedForTestForClient.saveDelivery(0, posDtoSaved, userBMDtoSaved,
                deliveryService);
        assertNotNull(deliveryDtoSaved);
        deliveryDtoSaved.setId(Long.valueOf(452136));

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        PackagingDto packagingDtoSaved = usedForTestForProduct.savePackaging(0, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved);

        DeliveryDetailsDto deliveryDetailsDtoSaved = usedForTestForClient.saveDeliveryDetails(0, posDtoSaved,
                packagingDtoSaved, deliveryDtoSaved, deliveryDetailsService);
        assertNotNull(deliveryDetailsDtoSaved);
    }

    @Test(expected = DuplicateEntityException.class)
    public void validate_SaveDelivery_Duplicate(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosDto());

        DeliveryDto deliveryDtoSaved = usedForTestForClient.saveDelivery(0, posDtoSaved, userBMDtoSaved,
                deliveryService);
        assertNotNull(deliveryDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        PackagingDto packagingDtoSaved = usedForTestForProduct.savePackaging(0, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved);

        DeliveryDetailsDto deliveryDetailsDtoSaved = usedForTestForClient.saveDeliveryDetails(0, posDtoSaved,
                packagingDtoSaved, deliveryDtoSaved, deliveryDetailsService);
        assertNotNull(deliveryDetailsDtoSaved);

        DeliveryDetailsDto deliveryDetailsDtoSaved1 = usedForTestForClient.saveDeliveryDetails(1, posDtoSaved,
                packagingDtoSaved, deliveryDtoSaved, deliveryDetailsService);
        assertNotNull(deliveryDetailsDtoSaved1);
    }

    @Test
    public void validate_UpdateDelivery(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosDto());

        DeliveryDto deliveryDtoSaved = usedForTestForClient.saveDelivery(0, posDtoSaved, userBMDtoSaved,
                deliveryService);
        assertNotNull(deliveryDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        PackagingDto packagingDtoSaved = usedForTestForProduct.savePackaging(0, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved);

        PackagingDto packagingDtoSaved1 = usedForTestForProduct.savePackaging(1, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved1);


        DeliveryDetailsDto deliveryDetailsDtoSaved = usedForTestForClient.saveDeliveryDetails(0, posDtoSaved,
                packagingDtoSaved, deliveryDtoSaved, deliveryDetailsService);
        assertNotNull(deliveryDetailsDtoSaved);

        DeliveryDetailsDto deliveryDetailsDtoFound = deliveryDetailsService.findDeliveryDetailsById(deliveryDetailsDtoSaved.getId());
        assertNotNull(deliveryDetailsDtoFound);
        deliveryDetailsDtoFound.setDdPackagingDto(packagingDtoSaved1);

        DeliveryDetailsDto deliveryDetailsDtoUpdated =  deliveryDetailsService.updateDeliveryDetails(deliveryDetailsDtoFound);
        assertNotNull(deliveryDetailsDtoUpdated);
    }

    @Test(expected = InvalidEntityException.class)
    public void validate_UpdateDelivery_UpdateDelivery(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosDto());

        DeliveryDto deliveryDtoSaved = usedForTestForClient.saveDelivery(0, posDtoSaved, userBMDtoSaved,
                deliveryService);
        assertNotNull(deliveryDtoSaved);

        DeliveryDto deliveryDtoSaved1 = usedForTestForClient.saveDelivery(1, posDtoSaved, userBMDtoSaved,
                deliveryService);
        assertNotNull(deliveryDtoSaved1);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        PackagingDto packagingDtoSaved = usedForTestForProduct.savePackaging(0, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved);

        PackagingDto packagingDtoSaved1 = usedForTestForProduct.savePackaging(1, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved1);


        DeliveryDetailsDto deliveryDetailsDtoSaved = usedForTestForClient.saveDeliveryDetails(0, posDtoSaved,
                packagingDtoSaved, deliveryDtoSaved, deliveryDetailsService);
        assertNotNull(deliveryDetailsDtoSaved);

        DeliveryDetailsDto deliveryDetailsDtoSaved1 = usedForTestForClient.saveDeliveryDetails(1, posDtoSaved,
                packagingDtoSaved, deliveryDtoSaved1, deliveryDetailsService);
        assertNotNull(deliveryDetailsDtoSaved1);

        DeliveryDetailsDto deliveryDetailsDtoFound = deliveryDetailsService.findDeliveryDetailsById(deliveryDetailsDtoSaved.getId());
        assertNotNull(deliveryDetailsDtoFound);
        deliveryDetailsDtoFound.setDdPackagingDto(packagingDtoSaved1);
        deliveryDetailsDtoFound.setDdDeliveryDto(deliveryDtoSaved1);

        DeliveryDetailsDto deliveryDetailsDtoUpdated =  deliveryDetailsService.updateDeliveryDetails(deliveryDetailsDtoFound);
        assertNotNull(deliveryDetailsDtoUpdated);
    }

    @Test
    public void validate_FindDeliveryDetailsinDeliverywithPackagingAndDelete(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosDto());

        DeliveryDto deliveryDtoSaved = usedForTestForClient.saveDelivery(0, posDtoSaved, userBMDtoSaved,
                deliveryService);
        assertNotNull(deliveryDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        PackagingDto packagingDtoSaved = usedForTestForProduct.savePackaging(0, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved);

        PackagingDto packagingDtoSaved1 = usedForTestForProduct.savePackaging(1, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved1);

        DeliveryDetailsDto deliveryDetailsDtoSaved = usedForTestForClient.saveDeliveryDetails(0, posDtoSaved,
                packagingDtoSaved, deliveryDtoSaved, deliveryDetailsService);
        assertNotNull(deliveryDetailsDtoSaved);

        DeliveryDetailsDto deliveryDetailsDtoSaved1 = usedForTestForClient.saveDeliveryDetails(1, posDtoSaved,
                packagingDtoSaved1, deliveryDtoSaved, deliveryDetailsService);
        assertNotNull(deliveryDetailsDtoSaved1);

        DeliveryDetailsDto deliveryDetailsDtoFound = deliveryDetailsService.findDeliveryDetailsinDeliverywithPackaging(
                packagingDtoSaved.getId(), deliveryDtoSaved.getId());
        assertNotNull(deliveryDetailsDtoFound);
        assertEquals(deliveryDetailsDtoSaved.getId(), deliveryDetailsDtoFound.getId());

        //On teste aussi le delete
        assertTrue(deliveryDetailsService.deleteDeliveryDetailsById(deliveryDetailsDtoFound.getId()));
    }

    @Test
    public void validate_FindAllDeliveryDetailsinDelivery(){
        PointofsaleDto posDtoSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(posDtoSaved);

        UserBMDto userBMDtoSaved = usedForTestForAll.saveUserBM(11, posDtoSaved, userBMService);
        assertNotNull(userBMDtoSaved);
        assertNotNull(userBMDtoSaved.getBmPosDto());

        DeliveryDto deliveryDtoSaved = usedForTestForClient.saveDelivery(0, posDtoSaved, userBMDtoSaved,
                deliveryService);
        assertNotNull(deliveryDtoSaved);

        ProviderDto providerDtoSaved = usedForTestForProvider.saveProvider(0, posDtoSaved, providerService);
        assertNotNull(providerDtoSaved);
        assertNotNull(providerDtoSaved.getProviderCaDto().getId());

        PackagingDto packagingDtoSaved = usedForTestForProduct.savePackaging(0, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved);

        PackagingDto packagingDtoSaved1 = usedForTestForProduct.savePackaging(1, providerDtoSaved, posDtoSaved,
                packagingService);
        assertNotNull(packagingDtoSaved1);

        DeliveryDetailsDto deliveryDetailsDtoSaved = usedForTestForClient.saveDeliveryDetails(0, posDtoSaved,
                packagingDtoSaved, deliveryDtoSaved, deliveryDetailsService);
        assertNotNull(deliveryDetailsDtoSaved);

        DeliveryDetailsDto deliveryDetailsDtoSaved1 = usedForTestForClient.saveDeliveryDetails(1, posDtoSaved,
                packagingDtoSaved1, deliveryDtoSaved, deliveryDetailsService);
        assertNotNull(deliveryDetailsDtoSaved1);

        DeliveryDetailsDto deliveryDetailsDtoFound = deliveryDetailsService.findDeliveryDetailsinDeliverywithPackaging(
                packagingDtoSaved.getId(), deliveryDtoSaved.getId());
        assertNotNull(deliveryDetailsDtoFound);
        assertEquals(deliveryDetailsDtoSaved.getId(), deliveryDetailsDtoFound.getId());

        List<DeliveryDetailsDto> deliveryDetailsDtoList = deliveryDetailsService.findAllDeliveryDetailsinDelivery(
                deliveryDtoSaved.getId());
        assertNotNull(deliveryDetailsDtoList);
        assertEquals(2, deliveryDetailsDtoList.size());

        Page<DeliveryDetailsDto> deliveryDetailsDtoPage = deliveryDetailsService.findPageDeliveryDetailsinDelivery(
                deliveryDtoSaved.getId(), 0, 1);
        assertNotNull(deliveryDetailsDtoPage);
        assertEquals(2, deliveryDetailsDtoPage.getTotalPages());

    }

}