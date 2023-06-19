package com.c2psi.businessmanagement.services.contractsImpl.pos.pos;

import com.c2psi.businessmanagement.BusinessmanagementApplication;
import com.c2psi.businessmanagement.Enumerations.UserBMState;
import com.c2psi.businessmanagement.Enumerations.UserBMType;
import com.c2psi.businessmanagement.dtos.pos.pos.EnterpriseDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCashAccountDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.AddressDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyConversionDto;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyDto;
import com.c2psi.businessmanagement.exceptions.DuplicateEntityException;
import com.c2psi.businessmanagement.exceptions.EntityNotFoundException;
import com.c2psi.businessmanagement.exceptions.InvalidEntityException;
import com.c2psi.businessmanagement.exceptions.NullArgumentException;
import com.c2psi.businessmanagement.services.contractsImpl.UsedForTestForAll;
import com.c2psi.businessmanagement.services.contractsImpl.pos.userbm.UserBMServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.price.CurrencyConversionServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.stock.price.CurrencyServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusinessmanagementApplication.class)
public class PointofsaleServiceImplTest {
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
    CurrencyConversionServiceImpl currencyConversionService;
    @Autowired
    UsedForTestForAll usedForTestForAll;

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

    public UserBMDto saveUserBM(String login, String name, String email, String cni){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            UserBMDto userBMToSave = UserBMDto.builder()
                    .bmAddressDto(getAddressDto(email, "678470262"))
                    .bmCni("107235260")
                    .bmCni(cni)
                    .bmDob(sdf.parse("1942-05-15 00:00:00"))
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

    public EnterpriseDto saveEnterprise(){
        UserBMDto userBMSaved = saveUserBM("useradmin", "admin", "testemail1@gmail.com", "107230260");
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

    public PosCashAccountDto savePosCashAccount(double solde){
        PosCashAccountDto posCashAccountToSaved = PosCashAccountDto.builder()
                .pcaBalance(BigDecimal.valueOf(solde))
                .build();
        PosCashAccountDto posCashAccountSaved = posCashAccountService.savePosCashAccount(posCashAccountToSaved);
        return posCashAccountSaved;
    }

    public PosCashAccountDto preparePosCashAccount(double solde){
        PosCashAccountDto posCashAccountToSaved = PosCashAccountDto.builder()
                .pcaBalance(BigDecimal.valueOf(solde))
                .build();
        //PosCashAccountDto posCashAccountSaved = posCashAccountService.savePosCashAccount(posCashAccountToSaved);
        return posCashAccountToSaved;
    }

    public CurrencyDto saveCurrency(String curName, String curShortname){
        CurrencyDto currencyDtoToSaved = CurrencyDto.builder()
                .currencyShortname(curShortname)
                .currencyName(curName)
                .build();
        CurrencyDto currencyDtoSaved = currencyService.saveCurrency(currencyDtoToSaved);
        return currencyDtoSaved;
    }

    public CurrencyConversionDto saveCurrencyConversion(Double facteur, CurrencyDto curSource,
                                                        CurrencyDto curDestination){
        CurrencyConversionDto currencyConversionDtoToSaved = CurrencyConversionDto.builder()
                .currencySourceDto(curSource)
                .currencyDestinationDto(curDestination)
                .conversionFactor(BigDecimal.valueOf(facteur))
                .build();
        CurrencyConversionDto currencyConversionDtoSaved = currencyConversionService.saveCurrencyConversion(
                currencyConversionDtoToSaved);
        return currencyConversionDtoSaved;
    }

    public PointofsaleDto savePointofsale(String posName, String posAcronym, String posDescription,
                                             String posEmail, String posNumtel1, double accountBalance,
                                             String currencyName, String currencyShortname){
        AddressDto posAddress = getAddressDto(posEmail, posNumtel1);
        EnterpriseDto posEnt = saveEnterprise();
        PosCashAccountDto posPca = preparePosCashAccount(accountBalance);
        CurrencyDto posCurrency = saveCurrency(currencyName, currencyShortname);
        assertNotNull(posCurrency.getId());
        PointofsaleDto pointofsaleToSaved = PointofsaleDto.builder()
                .posAddressDto(posAddress)
                .posEnterpriseDto(posEnt)
                .posCashaccountDto(posPca)
                .posAcronym(posAcronym)
                .posDescription(posDescription)
                .posName(posName)
                .posCurrencyDto(posCurrency)
                .build();
        PointofsaleDto pointofsaleSaved = pointofsaleService.savePointofsale(pointofsaleToSaved);
        return pointofsaleSaved;
    }

    public PointofsaleDto savePointofsale(String posName, String posAcronym, String posDescription,
                                          String posEmail, String posNumtel1, double accountBalance,
                                          CurrencyDto currencyDto){
        AddressDto posAddress = getAddressDto(posEmail, posNumtel1);
        EnterpriseDto posEnt = saveEnterprise();
        PosCashAccountDto posPca = preparePosCashAccount(accountBalance);
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

    @Test
    public void savePointofsale_Valid(){
        assertNotNull(enterpriseService);
        assertNotNull(pointofsaleService);
        assertNotNull(userBMService);
        assertNotNull(posCashAccountService);
        assertNotNull(currencyService);

        PointofsaleDto pointofsaleSaved = usedForTestForAll.savePointofsale(0, pointofsaleService, enterpriseService,
                userBMService, currencyService);
        assertNotNull(pointofsaleSaved);
        assertNotNull(pointofsaleSaved.getId());
        /****
         *On se rassure que le compte cash du pointofsale a bel et bien ete cree avec son id
         *         pendant la creation du pointofsale
         */
        assertNotNull(pointofsaleSaved.getPosCashaccountDto().getId());

    }

    @Test(expected = DuplicateEntityException.class)
    public void savePointofsale_DuplicateEntity(){
        assertNotNull(enterpriseService);
        assertNotNull(pointofsaleService);
        assertNotNull(userBMService);
        assertNotNull(posCashAccountService);
        assertNotNull(currencyService);

        PointofsaleDto pointofsaleSaved1 = savePointofsale("depot foret bar", "D2D",
                "depot de boisson", "d2d@gmail.com", "676170067", 0.0,
                "Franc cfa", "F cfa");
        assertNotNull(pointofsaleSaved1);
        assertNotNull(pointofsaleSaved1.getId());
        PointofsaleDto pointofsaleSaved2 = savePointofsale("depot foret bar", "D2D",
                "depot de boisson", "d2d@gmail.com", "676170067", 0.0,
                "Franc cfa", "F cfa");
        //The above line will launch the expected exception

    }

    @Test(expected = InvalidEntityException.class)
    public void savePointofsale_Invalid(){
        assertNotNull(enterpriseService);
        assertNotNull(pointofsaleService);
        assertNotNull(userBMService);
        assertNotNull(posCashAccountService);
        assertNotNull(currencyService);

        PointofsaleDto pointofsaleSaved = savePointofsale(null, "D2D",
                "depot de boisson", "d2d@gmail.com", "676170067", 0.0,
                "Franc cfa", "F cfa");
        //The above line will launch the expected exception

    }

    public PointofsaleDto savePointofsaleWithEnterpriseNotFound(String posName, String posAcronym, String posDescription,
                                          String posEmail, String posNumtel1, double accountBalance,
                                          String currencyName, String currencyShortname){
        AddressDto posAddress = getAddressDto(posEmail, posNumtel1);
        EnterpriseDto posEnt = saveEnterprise();
        posEnt.setId(posEnt.getId()+125478);
        PosCashAccountDto posPca = preparePosCashAccount(accountBalance);
        CurrencyDto posCurrency = saveCurrency(currencyName, currencyShortname);
        assertNotNull(posCurrency.getId());
        PointofsaleDto pointofsaleToSaved = PointofsaleDto.builder()
                .posAddressDto(posAddress)
                .posEnterpriseDto(posEnt)
                .posCashaccountDto(posPca)
                .posAcronym(posAcronym)
                .posDescription(posDescription)
                .posName(posName)
                .posCurrencyDto(posCurrency)
                .build();
        PointofsaleDto pointofsaleSaved = pointofsaleService.savePointofsale(pointofsaleToSaved);
        return pointofsaleSaved;
    }


    @Test(expected = EntityNotFoundException.class)
    public void savePointofsale_EnterpriseNotFound(){
        assertNotNull(enterpriseService);
        assertNotNull(pointofsaleService);
        assertNotNull(userBMService);
        assertNotNull(posCashAccountService);
        assertNotNull(currencyService);

        PointofsaleDto pointofsaleSaved = savePointofsaleWithEnterpriseNotFound("depot foret bar", "D2D",
                "depot de boisson", "d2d@gmail.com", "676170067", 0.0,
                "Franc cfa", "F cfa");
        //The line above will launch the expected exceptionsavePointofsale WithoutEnterprise
    }

    @Test
    public void updatePointofsale_Valid(){
        assertNotNull(enterpriseService);
        assertNotNull(pointofsaleService);
        assertNotNull(userBMService);
        assertNotNull(posCashAccountService);
        assertNotNull(currencyService);

        PointofsaleDto pointofsaleSaved = savePointofsale("depot foret bar", "D2D",
                "depot de boisson", "d2d@gmail.com", "676170067", 0.0,
                "Franc cfa", "F cfa");
        assertNotNull(pointofsaleSaved);
        assertNotNull(pointofsaleSaved.getId());

        //On fait la recherche de celui quon va mettre a jour
        PointofsaleDto pointofsaleToUpdated = pointofsaleService.findPointofsaleById(pointofsaleSaved.getId());
        assertNotNull(pointofsaleToUpdated.getId());
        pointofsaleToUpdated.setPosAcronym("3FoisD");
        pointofsaleToUpdated.setPosName("Depot de Djoutsa");
        pointofsaleToUpdated.getPosAddressDto().setEmail("3foisD@gmail.com");

        PointofsaleDto pointofsaleUpdated = pointofsaleService.updatePointofsale(pointofsaleToUpdated);
        assertNotNull(pointofsaleUpdated);
        assertEquals(pointofsaleSaved.getId(), pointofsaleUpdated.getId());
        assertEquals("3foisD@gmail.com", pointofsaleUpdated.getPosAddressDto().getEmail());

    }

    @Test(expected = InvalidEntityException.class)
    public void updatePointofsale_Invalid(){
        assertNotNull(enterpriseService);
        assertNotNull(pointofsaleService);
        assertNotNull(userBMService);
        assertNotNull(posCashAccountService);
        assertNotNull(currencyService);

        PointofsaleDto pointofsaleSaved = savePointofsale("depot foret bar", "D2D",
                "depot de boisson", "d2d@gmail.com", "676170067", 0.0,
                "Franc cfa", "F cfa");
        assertNotNull(pointofsaleSaved);
        assertNotNull(pointofsaleSaved.getId());

        //On fait la recherche de celui quon va mettre a jour
        PointofsaleDto pointofsaleToUpdated = pointofsaleService.findPointofsaleById(pointofsaleSaved.getId());
        assertNotNull(pointofsaleToUpdated.getId());

        pointofsaleToUpdated.setPosName("");
        pointofsaleToUpdated.setPosAcronym("3FoisD");
        pointofsaleToUpdated.getPosAddressDto().setEmail("3foisD@gmail.com");

        PointofsaleDto pointofsaleUpdated = pointofsaleService.updatePointofsale(pointofsaleToUpdated);
        //The line above is supposed to launch the expected exception

    }

    @Test(expected = EntityNotFoundException.class)
    public void updatePointofsale_PointofsaleNotFound(){
        assertNotNull(enterpriseService);
        assertNotNull(pointofsaleService);
        assertNotNull(userBMService);
        assertNotNull(posCashAccountService);
        assertNotNull(currencyService);

        PointofsaleDto pointofsaleSaved = savePointofsale("depot foret bar", "D2D",
                "depot de boisson", "d2d@gmail.com", "676170067", 0.0,
                "Franc cfa", "F cfa");
        assertNotNull(pointofsaleSaved);
        assertNotNull(pointofsaleSaved.getId());

        //On fait la recherche de celui quon va mettre a jour
        PointofsaleDto pointofsaleToUpdated = pointofsaleService.findPointofsaleById(pointofsaleSaved.getId());
        assertNotNull(pointofsaleToUpdated.getId());

        pointofsaleToUpdated.setId(Long.valueOf(10002));
        pointofsaleToUpdated.setPosAcronym("3FoisD");
        pointofsaleToUpdated.getPosAddressDto().setEmail("3foisD@gmail.com");

        PointofsaleDto pointofsaleUpdated = pointofsaleService.updatePointofsale(pointofsaleToUpdated);
        //The line above is supposed to launch the expected exception

    }

    @Test(expected = DuplicateEntityException.class)
    public void updatePointofsale_PointofsaleDuplicated(){
        assertNotNull(enterpriseService);
        assertNotNull(pointofsaleService);
        assertNotNull(userBMService);
        assertNotNull(posCashAccountService);
        assertNotNull(currencyService);

        PointofsaleDto pointofsaleSaved1 = savePointofsale("depot foret bar", "D2D",
                "depot de boisson", "d2d@gmail.com", "676170067", 0.0,
                "Franc cfa", "F cfa");
        assertNotNull(pointofsaleSaved1);
        assertNotNull(pointofsaleSaved1.getId());

        PointofsaleDto pointofsaleSaved2 = savePointofsale("depot de djoutsa a foret bar", "D2D",
                "depot de boisson", "d2d@gmail.com", "676170067", 0.0,
                "Franc cfa", "F cfa");
        assertNotNull(pointofsaleSaved2);
        assertNotNull(pointofsaleSaved2.getId());

        //On fait la recherche de celui quon va mettre a jour
        PointofsaleDto pointofsaleToUpdated = pointofsaleService.findPointofsaleById(pointofsaleSaved1.getId());
        assertNotNull(pointofsaleToUpdated.getId());

        //On met donc a jour son nom pour creer une exception duplicate en lui donnant le meme nom que pointofsaleSaved2
        pointofsaleToUpdated.setPosName("depot de djoutsa a foret bar");
        pointofsaleToUpdated.setPosAcronym("3FoisD");

        PointofsaleDto pointofsaleUpdated = pointofsaleService.updatePointofsale(pointofsaleToUpdated);
        //The line above is supposed to launch the expected exception

    }

    @Test
    public void findPointofsaleById_Valid(){
        assertNotNull(enterpriseService);
        assertNotNull(pointofsaleService);
        assertNotNull(userBMService);
        assertNotNull(posCashAccountService);
        assertNotNull(currencyService);

        PointofsaleDto pointofsaleSaved = savePointofsale("depot foret bar", "D2D",
                "depot de boisson", "d2d@gmail.com", "676170067", 0.0,
                "Franc cfa", "F cfa");
        assertNotNull(pointofsaleSaved);
        assertNotNull(pointofsaleSaved.getId());

        PointofsaleDto pointofsaleDtoFound = pointofsaleService.findPointofsaleById(pointofsaleSaved.getId());
        assertNotNull(pointofsaleDtoFound);
        assertNotNull(pointofsaleDtoFound.getId());
        assertEquals(pointofsaleSaved.getId(), pointofsaleDtoFound.getId());

    }

    @Test
    public void deletePosById_ValidTrue(){
        assertNotNull(enterpriseService);
        assertNotNull(pointofsaleService);
        assertNotNull(userBMService);
        assertNotNull(posCashAccountService);
        assertNotNull(currencyService);

        PointofsaleDto pointofsaleSaved = savePointofsale("depot foret bar", "D2D",
                "depot de boisson", "d2d@gmail.com", "676170067", 0.0,
                "Franc cfa", "F cfa");
        assertNotNull(pointofsaleSaved);
        assertNotNull(pointofsaleSaved.getId());

        Boolean b = pointofsaleService.deletePosById(pointofsaleSaved.getId());
        assertTrue(b);
        assertFalse(pointofsaleService.deletePosById(pointofsaleSaved.getId()));

    }

    @Test
    public void deletePosById_ValidFalse(){
        assertNotNull(enterpriseService);
        assertNotNull(pointofsaleService);
        assertNotNull(userBMService);
        assertNotNull(posCashAccountService);
        assertNotNull(currencyService);

        PointofsaleDto pointofsaleSaved = savePointofsale("depot foret bar", "D2D",
                "depot de boisson", "d2d@gmail.com", "676170067", 0.0,
                "Franc cfa", "F cfa");
        assertNotNull(pointofsaleSaved);
        assertNotNull(pointofsaleSaved.getId());

        Boolean b = pointofsaleService.deletePosById(pointofsaleSaved.getId()+100);
        assertFalse(b);

    }

    @Test(expected = NullArgumentException.class)
    public void deletePosById_NullIdArgument(){
        assertNotNull(enterpriseService);
        assertNotNull(pointofsaleService);
        assertNotNull(userBMService);
        assertNotNull(posCashAccountService);
        assertNotNull(currencyService);

        PointofsaleDto pointofsaleSaved = savePointofsale("depot foret bar", "D2D",
                "depot de boisson", "d2d@gmail.com", "676170067", 0.0,
                "Franc cfa", "F cfa");
        assertNotNull(pointofsaleSaved);
        assertNotNull(pointofsaleSaved.getId());

        Boolean b = pointofsaleService.deletePosById(null);
        //The above line is supposed to launch the exception

    }

    @Test
    public void deletePosInEnterpriseByName_ValidTrue(){
        assertNotNull(enterpriseService);
        assertNotNull(pointofsaleService);
        assertNotNull(userBMService);
        assertNotNull(posCashAccountService);
        assertNotNull(currencyService);

        PointofsaleDto pointofsaleSaved = savePointofsale("depot foret bar", "D2D",
                "depot de boisson", "d2d@gmail.com", "676170067", 0.0,
                "Franc cfa", "F cfa");
        assertNotNull(pointofsaleSaved);
        assertNotNull(pointofsaleSaved.getId());

        Boolean b = pointofsaleService.deletePosInEnterpriseByName("depot foret bar",
                pointofsaleSaved.getPosEnterpriseDto().getId());
        assertTrue(b);
        assertFalse(pointofsaleService.deletePosInEnterpriseByName("depot foret bar",
                pointofsaleSaved.getPosEnterpriseDto().getId()));

    }

    @Test
    public void deletePosInEnterpriseByName_ValidFalse(){
        assertNotNull(enterpriseService);
        assertNotNull(pointofsaleService);
        assertNotNull(userBMService);
        assertNotNull(posCashAccountService);
        assertNotNull(currencyService);

        PointofsaleDto pointofsaleSaved = savePointofsale("depot foret bar", "D2D",
                "depot de boisson", "d2d@gmail.com", "676170067", 0.0,
                "Franc cfa", "F cfa");
        assertNotNull(pointofsaleSaved);
        assertNotNull(pointofsaleSaved.getId());

        Boolean b = pointofsaleService.deletePosInEnterpriseByName("depot foret barrrerre",
                pointofsaleSaved.getPosEnterpriseDto().getId());
        assertFalse(b);

    }

    @Test(expected = NullArgumentException.class)
    public void deletePosInEnterpriseByName_NullPosNameArgument(){
        assertNotNull(enterpriseService);
        assertNotNull(pointofsaleService);
        assertNotNull(userBMService);
        assertNotNull(posCashAccountService);
        assertNotNull(currencyService);

        PointofsaleDto pointofsaleSaved = savePointofsale("depot foret bar", "D2D",
                "depot de boisson", "d2d@gmail.com", "676170067", 0.0,
                "Franc cfa", "F cfa");
        assertNotNull(pointofsaleSaved);
        assertNotNull(pointofsaleSaved.getId());

        Boolean b = pointofsaleService.deletePosInEnterpriseByName(null,
                pointofsaleSaved.getPosEnterpriseDto().getId());
        //The above line is supposed to launch the exception

    }

    @Test(expected = NullArgumentException.class)
    public void deletePosInEnterpriseByName_NullEntDtoArgument(){
        assertNotNull(enterpriseService);
        assertNotNull(pointofsaleService);
        assertNotNull(userBMService);
        assertNotNull(posCashAccountService);
        assertNotNull(currencyService);

        PointofsaleDto pointofsaleSaved = savePointofsale("depot foret bar", "D2D",
                "depot de boisson", "d2d@gmail.com", "676170067", 0.0,
                "Franc cfa", "F cfa");
        assertNotNull(pointofsaleSaved);
        assertNotNull(pointofsaleSaved.getId());

        Boolean b = pointofsaleService.deletePosInEnterpriseByName("depot foret bar",
                null);
        //The above line is supposed to launch the exception

    }

    @Test
    public void listofConvertibleCurrency_Valid(){
        /*****
         * Il faut enregistrer 5 monnaies et lier 03 d'entre elles.
         * Il faut creer un pointofsale avec une des 03 monnaies liees
         * Enfin il faut faire la recherche des monnaies convertible avec la monnaie du point de vente enregistre
         * On doit obtenir exactement les 03 monnaies liees
         */
        assertNotNull(enterpriseService);
        assertNotNull(pointofsaleService);
        assertNotNull(userBMService);
        assertNotNull(posCashAccountService);
        assertNotNull(currencyService);
        assertNotNull(currencyConversionService);

        //Ajout des monnaies dans le systeme
        CurrencyDto currencyDto1 = this.saveCurrency("Franc cfa", "F cfa");
        CurrencyDto currencyDto2 = this.saveCurrency("Euro", "E");
        CurrencyDto currencyDto3 = this.saveCurrency("Dollard", "$");
        CurrencyDto currencyDto4 = this.saveCurrency("Dirham", "MAD");//59.55 cfa MAD
        CurrencyDto currencyDto5 = this.saveCurrency("Naira", "N");//1.39 cfa

        //On va lier 3 monnaies
        CurrencyConversionDto currencyConversionDto1 = this.saveCurrencyConversion(Double.valueOf(650.59),
                currencyDto2, currencyDto1);
        assertNotNull(currencyConversionDto1);
        CurrencyConversionDto currencyConversionDto2 = this.saveCurrencyConversion(Double.valueOf(655.55),
                currencyDto3, currencyDto1);
        assertNotNull(currencyConversionDto2);
        CurrencyConversionDto currencyConversionDto3 = this.saveCurrencyConversion(Double.valueOf(59.55),
                currencyDto4, currencyDto1);
        assertNotNull(currencyConversionDto3);

        //On va cree le Pointdevente
        PointofsaleDto pointofsaleDto = this.savePointofsale("depot foret bar", "D2D",
                "depot de boisson", "d2d@gmail.com", "676170067", 0.0,
                currencyDto1);

        List<CurrencyDto> currencyDtoList = pointofsaleService.listofConvertibleCurrency(pointofsaleDto.getId());
        //System.out.println("currencyDtoList ==> "+currencyDtoList.toString());
        assertNotNull(currencyDtoList);
        assertEquals(4,currencyDtoList.size());

    }




}