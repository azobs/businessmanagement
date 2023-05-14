package com.c2psi.businessmanagement.services.contractsImpl;

import com.c2psi.businessmanagement.Enumerations.UserBMState;
import com.c2psi.businessmanagement.Enumerations.UserBMType;
import com.c2psi.businessmanagement.dtos.pos.pos.*;
import com.c2psi.businessmanagement.dtos.pos.userbm.AddressDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.price.BasePriceDto;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyDto;
import com.c2psi.businessmanagement.dtos.stock.price.SpecialPriceDto;
import com.c2psi.businessmanagement.dtos.stock.product.*;
import com.c2psi.businessmanagement.services.contracts.pos.pos.*;
import com.c2psi.businessmanagement.services.contracts.pos.userbm.UserBMService;
import com.c2psi.businessmanagement.services.contracts.stock.price.BasePriceService;
import com.c2psi.businessmanagement.services.contracts.stock.price.CurrencyService;
import com.c2psi.businessmanagement.services.contracts.stock.price.SpecialPriceService;
import com.c2psi.businessmanagement.services.contracts.stock.product.*;
import org.junit.Assert;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@Service
public class UsedForTestForAll {
    public AddressDto getAddressDto(int num){
        return AddressDto.builder()
                .email("test_"+num+"@gmail.com")
                .localisation("")
                .numtel1("64785451"+num)
                .numtel2("69509322"+num)
                .numtel3("67617006"+num)
                .pays("Cameroun")
                .quartier("Foret bar")
                .ville("Douala")
                .build();
    }


    public CurrencyDto saveCurrency(int num, CurrencyService currencyService){
        CurrencyDto currencyDtoToSaved = CurrencyDto.builder()
                .currencyShortname("f cfa"+num)
                .currencyName("francs cfa"+num)
                .build();
        assertNull(currencyDtoToSaved.getId());
        CurrencyDto currencyDtoSaved = currencyService.saveCurrency(currencyDtoToSaved);
        return currencyDtoSaved;
    }

    public int justToTest(){
        return 15;
    }

    public UserBMDto saveUserBM(int num, UserBMService userBMService){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            UserBMDto userBMToSave = UserBMDto.builder()
                    .bmAddressDto(getAddressDto(num+1))
                    .bmCni("10723526"+num)
                    .bmDob(sdf.parse("1942-05-15 00:00:00"))
                    .bmLogin("login"+num)
                    .bmName("name"+num)
                    .bmPassword("password"+num)
                    .bmRepassword("password"+num)
                    .bmState(UserBMState.Activated)
                    .bmUsertype(UserBMType.AdminEnterprise)
                    .bmSurname("user"+num)
                    .build();
            UserBMDto userBMSaved = userBMService.saveUserBM(userBMToSave);
            return userBMSaved;
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public UserBMDto saveUserBM(int num, PointofsaleDto posDtoSaved, UserBMService userBMService){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            UserBMDto userBMToSave = UserBMDto.builder()
                    .bmAddressDto(getAddressDto(num+1))
                    .bmCni("10723526"+num)
                    .bmDob(sdf.parse("1942-05-15 00:00:00"))
                    .bmLogin("login"+num)
                    .bmName("name"+num)
                    .bmPassword("password"+num)
                    .bmRepassword("password"+num)
                    .bmState(UserBMState.Activated)
                    .bmUsertype(UserBMType.Employe)
                    .bmSurname("user"+num)
                    .bmPosDto(posDtoSaved)
                    .build();
            UserBMDto userBMSaved = userBMService.saveUserBM(userBMToSave);
            return userBMSaved;
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public EnterpriseDto saveEnterprise(int num, EnterpriseService enterpriseService, UserBMService userBMService){
        UserBMDto userBMSaved = saveUserBM(num, userBMService);
        EnterpriseDto enterpriseDtoToSave = EnterpriseDto.builder()
                .entAdminDto(userBMSaved)
                .entAddressDto(getAddressDto(num+2))
                .entNiu("P012546897"+num)
                .entAcronym("C2PSI"+num)
                .entDescription("vente de boisson"+num)
                .entName("Commerce general DJOUTSA"+num)
                .entRegime("Reel")
                .entSocialreason("Commerce general")
                .build();
        EnterpriseDto enterpriseDtoSaved = enterpriseService.saveEnterprise(enterpriseDtoToSave);
        return enterpriseDtoSaved;
    }

    public PosCashAccountDto preparePosCashAccount(double solde){
        PosCashAccountDto posCashAccountToSaved = PosCashAccountDto.builder()
                .pcaBalance(BigDecimal.valueOf(solde))
                .build();
        //PosCashAccountDto posCashAccountSaved = posCashAccountService.savePosCashAccount(posCashAccountToSaved);
        return posCashAccountToSaved;
    }

    public PointofsaleDto savePointofsale(int num, PointofsaleService pointofsaleService,
                                          EnterpriseService enterpriseService, UserBMService userBMService,
                                          CurrencyService currencyService){
        AddressDto posAddress = getAddressDto(num+3);
        EnterpriseDto posEnt = saveEnterprise(num, enterpriseService, userBMService);
        PosCashAccountDto posPca = preparePosCashAccount(0);
        CurrencyDto currencyDto = saveCurrency(num+1, currencyService);
        assertNotNull(currencyDto.getId());

        PointofsaleDto pointofsaleToSaved = PointofsaleDto.builder()
                .posAddressDto(posAddress)
                .posEnterpriseDto(posEnt)
                .posCashaccountDto(posPca)
                .posAcronym("posAcronym"+num)
                .posDescription("posDescription"+num)
                .posName("Depot Djoutsa"+num)
                .posCurrencyDto(currencyDto)
                .build();
        System.err.println("A ajouter: "+pointofsaleToSaved);
        PointofsaleDto pointofsaleSaved = pointofsaleService.savePointofsale(pointofsaleToSaved);
        return pointofsaleSaved;
    }


    public BasePriceDto saveBasePrice(int num, CurrencyDto currencyDtoSaved, BasePriceService basePriceService){
        Assert.assertNotNull(currencyDtoSaved);
        BasePriceDto basePriceDtoToSave = BasePriceDto.builder()
                .bpWholesaleprice(BigDecimal.valueOf(7100))
                .bpSemiwholesaleprice(BigDecimal.valueOf(7150))
                .bpDetailprice(BigDecimal.valueOf(7800))
                .bpPurchaseprice(BigDecimal.valueOf(7056))
                .bpRistourne(BigDecimal.valueOf(244))
                .bpPrecompte(BigDecimal.valueOf(100))
                .bpCurrencyDto(currencyDtoSaved)
                .build();
        BasePriceDto basePriceDtoSaved = basePriceService.saveBasePrice(basePriceDtoToSave);
        return basePriceDtoSaved;
    }

    public BasePriceDto saveBasePrice_NotValid(int num, CurrencyDto currencyDtoSaved, BasePriceService basePriceService){
        Assert.assertNotNull(currencyDtoSaved);
        BasePriceDto basePriceDtoToSave = BasePriceDto.builder()
                .bpWholesaleprice(BigDecimal.valueOf(7100))
                .bpSemiwholesaleprice(BigDecimal.valueOf(7150))
                .bpDetailprice(BigDecimal.valueOf(7800))
                .bpPurchaseprice(BigDecimal.valueOf(7056))
                .bpRistourne(BigDecimal.valueOf(244))
                .bpPrecompte(BigDecimal.valueOf(100))
                .bpCurrencyDto(null)
                .build();
        BasePriceDto basePriceDtoSaved = basePriceService.saveBasePrice(basePriceDtoToSave);
        return basePriceDtoSaved;
    }

    public SpecialPriceDto saveSpecialPrice(int num, BasePriceDto basePriceDtoSaved,
                                            SpecialPriceService specialPriceService){
        Assert.assertNotNull(basePriceDtoSaved);
        SpecialPriceDto specialPriceDtoToSave = SpecialPriceDto.builder()
                .spWholesaleprice(BigDecimal.valueOf(7100))
                .spSemiwholesaleprice(BigDecimal.valueOf(7150))
                .spDetailprice(BigDecimal.valueOf(7800))
                .spRistourne(BigDecimal.valueOf(244))
                .spPrecompte(BigDecimal.valueOf(100).add(BigDecimal.valueOf(Double.valueOf(""+num))))
                .spBasepriceDto(basePriceDtoSaved)
                .build();
        SpecialPriceDto specialPriceDtoSaved = specialPriceService.saveSpecialPrice(specialPriceDtoToSave);
        return specialPriceDtoSaved;
    }

    public SpecialPriceDto saveSpecialPrice_NotValid(int num, BasePriceDto basePriceDtoSaved,
                                                     SpecialPriceService specialPriceService){
        Assert.assertNotNull(basePriceDtoSaved);
        SpecialPriceDto specialPriceDtoToSave = SpecialPriceDto.builder()
                .spWholesaleprice(null)
                .spSemiwholesaleprice(BigDecimal.valueOf(7150))
                .spDetailprice(BigDecimal.valueOf(7800))
                .spRistourne(BigDecimal.valueOf(244))
                .spPrecompte(BigDecimal.valueOf(100).add(BigDecimal.valueOf(Double.valueOf(""+num))))
                .spBasepriceDto(basePriceDtoSaved)
                .build();
        SpecialPriceDto specialPriceDtoSaved = specialPriceService.saveSpecialPrice(specialPriceDtoToSave);
        return specialPriceDtoSaved;
    }

    public PosCapsuleAccountDto savePosCapsuleAccount(int num, ArticleDto articleDtoSaved, PointofsaleDto pointofsaleDtoSaved,
                                                      PosCapsuleAccountService posCapsuleAccountService){
        Assert.assertNotNull(articleDtoSaved);
        Assert.assertNotNull(pointofsaleDtoSaved);
        Assert.assertNotNull(posCapsuleAccountService);

        PosCapsuleAccountDto posCapsuleAccountDtoToSave = PosCapsuleAccountDto.builder()
                .pcsaArticleDto(articleDtoSaved)
                .pcsaPointofsaleDto(pointofsaleDtoSaved)
                .pcsaNumber(BigDecimal.valueOf(0))
                .build();
        PosCapsuleAccountDto posCapsuleAccountDtoSaved = posCapsuleAccountService.savePosCapsuleAccount(posCapsuleAccountDtoToSave);
        return posCapsuleAccountDtoSaved;
    }

    public PosCapsuleAccountDto savePosCapsuleAccount_Invalid(int num, ArticleDto articleDtoSaved, PointofsaleDto pointofsaleDtoSaved,
                                                      PosCapsuleAccountService posCapsuleAccountService){
        Assert.assertNotNull(articleDtoSaved);
        Assert.assertNotNull(pointofsaleDtoSaved);
        Assert.assertNotNull(posCapsuleAccountService);

        PosCapsuleAccountDto posCapsuleAccountDtoToSave = PosCapsuleAccountDto.builder()
                .pcsaArticleDto(articleDtoSaved)
                .pcsaPointofsaleDto(pointofsaleDtoSaved)
                .pcsaNumber(null)
                .build();
        PosCapsuleAccountDto posCapsuleAccountDtoSaved = posCapsuleAccountService.savePosCapsuleAccount(posCapsuleAccountDtoToSave);
        return posCapsuleAccountDtoSaved;
    }

    public PosDamageAccountDto savePosDamageAccount(int num, ArticleDto articleDtoSaved, PointofsaleDto pointofsaleDtoSaved,
                                                    PosDamageAccountService posDamageAccountService){
        Assert.assertNotNull(articleDtoSaved);
        Assert.assertNotNull(pointofsaleDtoSaved);
        Assert.assertNotNull(posDamageAccountService);

        PosDamageAccountDto posDamageAccountDtoToSave = PosDamageAccountDto.builder()
                .pdaArticleDto(articleDtoSaved)
                .pdaPointofsaleDto(pointofsaleDtoSaved)
                .pdaNumber(BigDecimal.valueOf(0))
                .build();
        PosDamageAccountDto posDamageAccountDtoSaved = posDamageAccountService.savePosDamageAccount(posDamageAccountDtoToSave);
        return posDamageAccountDtoSaved;
    }

    public PosDamageAccountDto savePosDamageAccount_Invalid(int num, ArticleDto articleDtoSaved, PointofsaleDto pointofsaleDtoSaved,
                                                    PosDamageAccountService posDamageAccountService){
        Assert.assertNotNull(articleDtoSaved);
        Assert.assertNotNull(pointofsaleDtoSaved);
        Assert.assertNotNull(posDamageAccountService);

        PosDamageAccountDto posDamageAccountDtoToSave = PosDamageAccountDto.builder()
                .pdaArticleDto(articleDtoSaved)
                .pdaPointofsaleDto(pointofsaleDtoSaved)
                .pdaNumber(null)
                .build();
        PosDamageAccountDto posDamageAccountDtoSaved = posDamageAccountService.savePosDamageAccount(posDamageAccountDtoToSave);
        return posDamageAccountDtoSaved;
    }

    public PosPackagingAccountDto savePosPackagingAccount(int num, PackagingDto packagingDtoSaved, PointofsaleDto pointofsaleDtoSaved,
                                                    PosPackagingAccountService posPackagingAccountService){
        Assert.assertNotNull(packagingDtoSaved);
        Assert.assertNotNull(pointofsaleDtoSaved);
        Assert.assertNotNull(posPackagingAccountService);

        PosPackagingAccountDto posPackagingAccountDtoToSave = PosPackagingAccountDto.builder()
                .ppaPackagingDto(packagingDtoSaved)
                .ppaPointofsaleDto(pointofsaleDtoSaved)
                .ppaNumber(BigDecimal.valueOf(0))
                .build();
        PosPackagingAccountDto posPackagingAccountDtoSaved = posPackagingAccountService.savePosPackagingAccount(posPackagingAccountDtoToSave);
        return posPackagingAccountDtoSaved;
    }

    public PosPackagingAccountDto savePosPackagingAccount_NotValid(int num, PackagingDto packagingDtoSaved, PointofsaleDto pointofsaleDtoSaved,
                                                          PosPackagingAccountService posPackagingAccountService){
        Assert.assertNotNull(packagingDtoSaved);
        Assert.assertNotNull(pointofsaleDtoSaved);
        Assert.assertNotNull(posPackagingAccountService);

        PosPackagingAccountDto posPackagingAccountDtoToSave = PosPackagingAccountDto.builder()
                .ppaPackagingDto(packagingDtoSaved)
                .ppaPointofsaleDto(pointofsaleDtoSaved)
                .ppaNumber(null)
                .build();
        PosPackagingAccountDto posPackagingAccountDtoSaved = posPackagingAccountService.savePosPackagingAccount(posPackagingAccountDtoToSave);
        return posPackagingAccountDtoSaved;
    }

}
