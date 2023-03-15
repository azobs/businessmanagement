package com.c2psi.businessmanagement.services.contractsImpl;

import com.c2psi.businessmanagement.Enumerations.UserBMState;
import com.c2psi.businessmanagement.Enumerations.UserBMType;
import com.c2psi.businessmanagement.dtos.pos.pos.EnterpriseDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCashAccountDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.AddressDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyDto;
import com.c2psi.businessmanagement.dtos.stock.product.CategoryDto;
import com.c2psi.businessmanagement.services.contracts.pos.pos.EnterpriseService;
import com.c2psi.businessmanagement.services.contracts.pos.pos.PointofsaleService;
import com.c2psi.businessmanagement.services.contracts.pos.userbm.UserBMService;
import com.c2psi.businessmanagement.services.contracts.stock.price.CurrencyService;
import com.c2psi.businessmanagement.services.contracts.stock.product.CategoryService;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UsedForTest {
    public AddressDto getAddressDto(String email, String numtel1){
        return AddressDto.builder()
                .email(email)
                .localisation("")
                .numtel1(numtel1)
                .numtel2("695093228")
                .numtel3("676170067")
                .pays("Cameroun")
                .quartier("Foret bar")
                .ville("Douala")
                .build();
    }

    public CurrencyDto saveCurrency(String name, String shortName, CurrencyService currencyService){
        CurrencyDto currencyDtoToSaved = CurrencyDto.builder()
                .currencyShortname(shortName)
                .currencyName(name)
                .build();
        assertNull(currencyDtoToSaved.getId());
        CurrencyDto currencyDtoSaved = currencyService.saveCurrency(currencyDtoToSaved);
        return currencyDtoSaved;
    }

    public int justToTest(){
        return 15;
    }

    public UserBMDto saveUserBM(String login, String name, String email, String cni, UserBMService userBMService){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        try {
            UserBMDto userBMToSave = UserBMDto.builder()
                    .bmAddressDto(getAddressDto(email, "678470262"))
                    .bmCni("107235260")
                    .bmCni(cni)
                    .bmDob(sdf.parse("15-05-1942"))
                    .bmLogin(login)
                    .bmName(name)
                    .bmPassword("password")
                    .bmRepassword("password")
                    .bmState(UserBMState.Activated)
                    .bmUsertype(UserBMType.AdminEnterprise)
                    .bmSurname("user")
                    .build();
            UserBMDto userBMSaved = userBMService.saveUserBM(userBMToSave);
            return userBMSaved;
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public EnterpriseDto saveEnterprise(EnterpriseService enterpriseService, UserBMService userBMService){
        UserBMDto userBMSaved = saveUserBM("useradmin", "admin", "testemail1@gmail.com",
                "107230260", userBMService);
        EnterpriseDto enterpriseDtoToSave = EnterpriseDto.builder()
                .entAdminDto(userBMSaved)
                .entAddressDto(AddressDto.builder()
                        .email("testsaveent@gmail.com")
                        .localisation("")
                        .numtel1("678470262")
                        .numtel2("695093228")
                        .numtel3("676170067")
                        .pays("Cameroun")
                        .quartier("Foret bar")
                        .ville("Douala")
                        .build())
                .entNiu("P012546897")
                .entAcronym("C2PSI")
                .entDescription("vente de boisson")
                .entName("Commerce general DJOUTSA")
                .entRegime("Reel")
                .entSocialreason("Commerce general")
                .build();
        EnterpriseDto enterpriseDtoSaved = enterpriseService.saveEnterprise(enterpriseDtoToSave);
        return enterpriseDtoSaved;
    }

    public EnterpriseDto saveEnterprise(String niu, String acronym, String description, String name, String regime,
                                        String socialReason, EnterpriseService enterpriseService,
                                        UserBMService userBMService){
        UserBMDto userBMSaved = saveUserBM("useradmin", "admin", "testemail1@gmail.com",
                "107230260", userBMService);
        EnterpriseDto enterpriseDtoToSave = EnterpriseDto.builder()
                .entAdminDto(userBMSaved)
                .entAddressDto(AddressDto.builder()
                        .email("testsaveent@gmail.com")
                        .localisation("")
                        .numtel1("678470262")
                        .numtel2("695093228")
                        .numtel3("676170067")
                        .pays("Cameroun")
                        .quartier("Foret bar")
                        .ville("Douala")
                        .build())
                .entNiu(niu)
                .entAcronym(acronym)
                .entDescription(description)
                .entName(name)
                .entRegime(regime)
                .entSocialreason(socialReason)
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

    public PointofsaleDto savePointofsale(String posName, String posAcronym, String posDescription,
                                          String posEmail, String posNumtel1, double accountBalance,
                                          String currencyName, String currencyShortname, PointofsaleService pointofsaleService,
                                          EnterpriseService enterpriseService, UserBMService userBMService, CurrencyService currencyService){
        AddressDto posAddress = getAddressDto(posEmail, posNumtel1);
        EnterpriseDto posEnt = saveEnterprise(enterpriseService, userBMService);
        PosCashAccountDto posPca = preparePosCashAccount(accountBalance);
        CurrencyDto currencyDto = saveCurrency(currencyName, currencyShortname, currencyService);
        assertNotNull(currencyDto.getId());
        PointofsaleDto pointofsaleToSaved = PointofsaleDto.builder()
                .posAddressDto(posAddress)
                .posEnterpriseDto(posEnt)
                .posCashaccountDto(posPca)
                .posAcronym(posAcronym)
                .posDescription(posDescription)
                .posName(posName)
                .posCurrencyDto(currencyDto)
                .build();
        PointofsaleDto pointofsaleSaved = pointofsaleService.savePointofsale(pointofsaleToSaved);
        return pointofsaleSaved;
    }

    public PointofsaleDto savePointofsale(String posName, String posAcronym, String posDescription,
                           String posEmail, String posNumtel1, double accountBalance,
                           EnterpriseDto enterpriseDto, CurrencyDto currencyDto, PointofsaleService pointofsaleService){

        AddressDto posAddress = getAddressDto(posEmail, posNumtel1);
        PosCashAccountDto posPca = preparePosCashAccount(accountBalance);
        assertNotNull(currencyDto.getId());
        assertNotNull(enterpriseDto.getId());
        PointofsaleDto pointofsaleToSaved = PointofsaleDto.builder()
                .posAddressDto(posAddress)
                .posEnterpriseDto(enterpriseDto)
                .posCashaccountDto(posPca)
                .posAcronym(posAcronym)
                .posDescription(posDescription)
                .posName(posName)
                .posCurrencyDto(currencyDto)
                .build();
        PointofsaleDto pointofsaleSaved = pointofsaleService.savePointofsale(pointofsaleToSaved);
        return pointofsaleSaved;

    }

    public CategoryDto saveCategory(String catName, String catShortname, String catCode, String catDescription,
                                    PointofsaleDto posDto, CategoryService categoryService){

        CategoryDto categoryDtoToSaved = CategoryDto.builder()
                .catShortname(catShortname)
                .catPosDto(posDto)
                .catName(catName)
                .catDescription(catDescription)
                .catCode(catCode)
                .build();
        CategoryDto categoryDtoSaved = categoryService.saveCategory(categoryDtoToSaved);
        return categoryDtoSaved;
    }

    public CategoryDto  updateCategory(CategoryDto categoryDtoToUpdate, CategoryService categoryService){
        CategoryDto categoryDtoUpdated = categoryService.updateCategory(categoryDtoToUpdate);
        return categoryDtoUpdated;
    }

}
