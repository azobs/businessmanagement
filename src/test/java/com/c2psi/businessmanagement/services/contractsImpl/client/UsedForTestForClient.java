package com.c2psi.businessmanagement.services.contractsImpl.client;

import com.c2psi.businessmanagement.Enumerations.*;
import com.c2psi.businessmanagement.dtos.client.client.*;
import com.c2psi.businessmanagement.dtos.client.command.BackInDetailsDto;
import com.c2psi.businessmanagement.dtos.client.command.BackInDto;
import com.c2psi.businessmanagement.dtos.client.command.CommandDto;
import com.c2psi.businessmanagement.dtos.client.command.SaleDto;
import com.c2psi.businessmanagement.dtos.client.delivery.DeliveryDetailsDto;
import com.c2psi.businessmanagement.dtos.client.delivery.DeliveryDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.AddressDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.price.SpecialPriceDto;
import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import com.c2psi.businessmanagement.dtos.stock.product.PackagingDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCapsuleAccountDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCashAccountDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderPackagingAccountDto;
import com.c2psi.businessmanagement.models.BackIn;
import com.c2psi.businessmanagement.services.contracts.client.client.*;
import com.c2psi.businessmanagement.services.contracts.client.command.BackInDetailsService;
import com.c2psi.businessmanagement.services.contracts.client.command.BackInService;
import com.c2psi.businessmanagement.services.contracts.client.command.CommandService;
import com.c2psi.businessmanagement.services.contracts.client.command.SaleService;
import com.c2psi.businessmanagement.services.contracts.client.delivery.DeliveryDetailsService;
import com.c2psi.businessmanagement.services.contracts.client.delivery.DeliveryService;
import com.c2psi.businessmanagement.services.contracts.stock.provider.ProviderCapsuleAccountService;
import com.c2psi.businessmanagement.services.contracts.stock.provider.ProviderCashAccountService;
import com.c2psi.businessmanagement.services.contracts.stock.provider.ProviderPackagingAccountService;
import com.c2psi.businessmanagement.services.contracts.stock.provider.ProviderService;
import com.c2psi.businessmanagement.services.contractsImpl.UsedForTestForAll;
import com.c2psi.businessmanagement.services.contractsImpl.client.command.CommandServiceImpl;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Service
public class UsedForTestForClient {
    @Autowired
    UsedForTestForAll usedForTestForAll;

    public ClientCashAccountDto saveClientCashAccount(int solde, ClientCashAccountService clientCashAccountService){
        Assert.assertNotNull(clientCashAccountService);
        ClientCashAccountDto clientCashAccountDtoToSave = ClientCashAccountDto.builder()
                .ccaBalance(BigDecimal.valueOf(solde))
                .build();
        ClientCashAccountDto clientCashAccountDtoSaved = clientCashAccountService.saveClientCashAccount(
                clientCashAccountDtoToSave);
        return clientCashAccountDtoSaved;
    }

    public DiversCashAccountDto saveDiversCashAccount(int solde, DiversCashAccountService diversCashAccountService){
        Assert.assertNotNull(diversCashAccountService);
        DiversCashAccountDto diversCashAccountDtoToSave = DiversCashAccountDto.builder()
                .dcaBalance(BigDecimal.valueOf(solde))
                .build();
        DiversCashAccountDto diversCashAccountDtoSaved = diversCashAccountService.saveDiversCashAccount(
                diversCashAccountDtoToSave);
        return diversCashAccountDtoSaved;
    }

    public ClientCashAccountDto saveClientCashAccount_Invalid(int num, ClientCashAccountService clientCashAccountService){
        Assert.assertNotNull(clientCashAccountService);
        ClientCashAccountDto clientCashAccountDtoToSave = ClientCashAccountDto.builder()
                .ccaBalance(null)
                .build();
        ClientCashAccountDto clientCashAccountDtoSaved = clientCashAccountService.saveClientCashAccount(
                clientCashAccountDtoToSave);
        return clientCashAccountDtoSaved;
    }

    public ClientCashAccountDto prepareClientCashAccount(double solde){
        ClientCashAccountDto clientCashAccountToSaved = ClientCashAccountDto.builder()
                .ccaBalance(BigDecimal.valueOf(solde))
                .build();
        return clientCashAccountToSaved;
    }

    public ClientDto saveClient(int num, PointofsaleDto pointofsaleDtoSaved, ClientService clientService){
        assertNotNull(clientService);
        assertNotNull(pointofsaleDtoSaved);

        AddressDto clientAddress = usedForTestForAll.getAddressDto(num+40);
        ClientCashAccountDto ccaDto = prepareClientCashAccount(0);
        ClientDto clientDtoToSave = ClientDto.builder()
                .clientCni("107235260_"+num)
                .clientName("Cedric_"+num)
                .clientOthername("Amour_"+num)
                .clientCaDto(ccaDto)
                .clientAddressDto(clientAddress)
                .clientPosDto(pointofsaleDtoSaved)
                .build();

       ClientDto clientDtoSaved = clientService.saveClient(clientDtoToSave);
       assertNotNull(clientDtoSaved);
       return clientDtoSaved;
    }

    public ClientDto saveClient_Invalid(int num, PointofsaleDto pointofsaleDtoSaved, ClientService clientService){
        assertNotNull(clientService);
        assertNotNull(pointofsaleDtoSaved);

        AddressDto clientAddress = usedForTestForAll.getAddressDto(num+40);
        ClientCashAccountDto ccaDto = prepareClientCashAccount(0);
        ClientDto clientDtoToSave = ClientDto.builder()
                .clientCni("107235260_"+num)
                .clientName("Cedric_"+num)
                .clientOthername("Amour_"+num)
                .clientCaDto(ccaDto)
                .clientAddressDto(null)
                .clientPosDto(pointofsaleDtoSaved)
                .build();

        ClientDto clientDtoSaved = clientService.saveClient(clientDtoToSave);
        assertNotNull(clientDtoSaved);
        return clientDtoSaved;
    }

    public ClientCapsuleAccountDto saveClientCapsuleAccount(int solde, ClientDto clientDtoSaved, ArticleDto articleDtoSaved,
                                                            ClientCapsuleAccountService clientCapsuleAccountService){
        Assert.assertNotNull(clientCapsuleAccountService);
        ClientCapsuleAccountDto clientCapsuleAccountDtoToSave = ClientCapsuleAccountDto.builder()
                .ccsaClientDto(clientDtoSaved)
                .ccsaArticleDto(articleDtoSaved)
                .ccsaNumber(BigDecimal.valueOf(solde))
                .build();
        ClientCapsuleAccountDto clientCapsuleAccountDtoSaved = clientCapsuleAccountService.saveClientCapsuleAccount(
                clientCapsuleAccountDtoToSave);
        return clientCapsuleAccountDtoSaved;
    }

    public ClientDamageAccountDto saveClientDamageAccount(int solde, ClientDto clientDtoSaved, ArticleDto articleDtoSaved,
                                                          ClientDamageAccountService clientDamageAccountService){
        Assert.assertNotNull(clientDamageAccountService);
        ClientDamageAccountDto clientDamageAccountDtoToSave = ClientDamageAccountDto.builder()
                .cdaClientDto(clientDtoSaved)
                .cdaArticleDto(articleDtoSaved)
                .cdaNumber(BigDecimal.valueOf(solde))
                .build();
        ClientDamageAccountDto clientDamageAccountDtoSaved = clientDamageAccountService.saveClientDamageAccount(
                clientDamageAccountDtoToSave);
        return clientDamageAccountDtoSaved;
    }

    public ClientPackagingAccountDto saveClientPackagingAccount(int solde, ClientDto clientDtoSaved, PackagingDto packagingDtoSaved,
                                                                ClientPackagingAccountService clientPackagingAccountService){
        Assert.assertNotNull(clientPackagingAccountService);
        ClientPackagingAccountDto clientPackagingAccountDtoToSave = ClientPackagingAccountDto.builder()
                .cpaClientDto(clientDtoSaved)
                .cpaPackagingDto(packagingDtoSaved)
                .cpaNumber(BigDecimal.valueOf(solde))
                .build();
        ClientPackagingAccountDto clientPackagingAccountDtoSaved = clientPackagingAccountService.saveClientPackagingAccount(
                clientPackagingAccountDtoToSave);
        return clientPackagingAccountDtoSaved;
    }

    public ClientPackagingAccountDto saveClientPackagingAccount_Invalid(int solde, ClientDto clientDtoSaved, PackagingDto packagingDtoSaved,
                                                                ClientPackagingAccountService clientPackagingAccountService){
        Assert.assertNotNull(clientPackagingAccountService);
        ClientPackagingAccountDto clientPackagingAccountDtoToSave = ClientPackagingAccountDto.builder()
                .cpaClientDto(clientDtoSaved)
                .cpaPackagingDto(packagingDtoSaved)
                .cpaNumber(null)
                .build();
        ClientPackagingAccountDto clientPackagingAccountDtoSaved = clientPackagingAccountService.saveClientPackagingAccount(
                clientPackagingAccountDtoToSave);
        return clientPackagingAccountDtoSaved;
    }

    public ClientSpecialpriceDto saveClientSpecialprice(int num, ClientDto clientDtoSaved, ArticleDto articleDtoSaved,
                                                        SpecialPriceDto specialPriceDtoSaved,
                                                        ClientSpecialpriceService clientSpecialpriceService){
        Assert.assertNotNull(clientSpecialpriceService);
        ClientSpecialpriceDto clientSpecialpriceDtoToSaved = ClientSpecialpriceDto.builder()
                .cltSpClientDto(clientDtoSaved)
                .cltSpArtDto(articleDtoSaved)
                .cltSpPDto(specialPriceDtoSaved)
                .cltSpApplieddate(new Date().toInstant())
                .build();
        ClientSpecialpriceDto clientSpecialpriceDtoSaved = clientSpecialpriceService.saveClientSpecialprice(
                clientSpecialpriceDtoToSaved);
        return clientSpecialpriceDtoSaved;
    }

    public ClientSpecialpriceDto saveClientSpecialprice_Invalid(int num, ClientDto clientDtoSaved, ArticleDto articleDtoSaved,
                                                        SpecialPriceDto specialPriceDtoSaved,
                                                        ClientSpecialpriceService clientSpecialpriceService){
        Assert.assertNotNull(clientSpecialpriceService);
        ClientSpecialpriceDto clientSpecialpriceDtoToSaved = ClientSpecialpriceDto.builder()
                .cltSpClientDto(clientDtoSaved)
                .cltSpArtDto(null)
                .cltSpPDto(specialPriceDtoSaved)
                .cltSpApplieddate(new Date().toInstant())
                .build();
        ClientSpecialpriceDto clientSpecialpriceDtoSaved = clientSpecialpriceService.saveClientSpecialprice(
                clientSpecialpriceDtoToSaved);
        return clientSpecialpriceDtoSaved;
    }

    public DeliveryDto saveDelivery(int num, PointofsaleDto pointofsaleDtoSaved, UserBMDto userBMDtoSaved,
                                              DeliveryService deliveryService){
        Assert.assertNotNull(deliveryService);
        Assert.assertNotNull(pointofsaleDtoSaved);
        Assert.assertNotNull(userBMDtoSaved);
        DeliveryDto deliveryDtoToSave = DeliveryDto.builder()
                .deliveryState(DeliveryState.Delivery)
                .deliveryDate(new Date().toInstant())
                .deliveryUserbmDto(userBMDtoSaved)
                .deliveryPosDto(pointofsaleDtoSaved)
                .deliveryComment("delivery comment")
                .deliveryCode("deliveryCode"+num)
                .build();

        DeliveryDto deliveryDtoSaved = deliveryService.saveDelivery(
                deliveryDtoToSave);
        return deliveryDtoSaved;
    }

    public DeliveryDto saveDelivery_Invalid(int num, PointofsaleDto pointofsaleDtoSaved, UserBMDto userBMDtoSaved,
                                    DeliveryService deliveryService){
        Assert.assertNotNull(deliveryService);
        Assert.assertNotNull(pointofsaleDtoSaved);
        Assert.assertNotNull(userBMDtoSaved);
        DeliveryDto deliveryDtoToSave = DeliveryDto.builder()
                .deliveryState(DeliveryState.Delivery)
                .deliveryDate(new Date().toInstant())
                .deliveryUserbmDto(null)
                .deliveryPosDto(pointofsaleDtoSaved)
                .deliveryComment("delivery comment")
                .deliveryCode("deliveryCode"+num)
                .build();

        DeliveryDto deliveryDtoSaved = deliveryService.saveDelivery(
                deliveryDtoToSave);
        return deliveryDtoSaved;
    }

    public DeliveryDetailsDto saveDeliveryDetails(int num, PointofsaleDto pointofsaleDtoSaved, PackagingDto packagingDtoSaved,
                                                 DeliveryDto deliveryDtoSaved, DeliveryDetailsService deliveryDetailsService){
        Assert.assertNotNull(deliveryDetailsService);
        Assert.assertNotNull(deliveryDtoSaved);
        Assert.assertNotNull(pointofsaleDtoSaved);
        Assert.assertNotNull(packagingDtoSaved);

        DeliveryDetailsDto deliveryDetailsDtoToSave = DeliveryDetailsDto.builder()
                .ddDeliveryDto(deliveryDtoSaved)
                .ddPackagingDto(packagingDtoSaved)
                .ddNumberofpackagereturn(BigDecimal.valueOf(10))
                .ddNumberofpackageused(BigDecimal.valueOf(10))
                .build();

        DeliveryDetailsDto deliverydetailsDtoSaved = deliveryDetailsService.saveDeliveryDetails(
                deliveryDetailsDtoToSave);
        return deliverydetailsDtoSaved;
    }

    public DeliveryDetailsDto saveDeliveryDetails_Invalid(int num, PointofsaleDto pointofsaleDtoSaved, PackagingDto packagingDtoSaved,
                                                 DeliveryDto deliveryDtoSaved, DeliveryDetailsService deliveryDetailsService){
        Assert.assertNotNull(deliveryDetailsService);
        Assert.assertNotNull(deliveryDtoSaved);
        Assert.assertNotNull(pointofsaleDtoSaved);
        Assert.assertNotNull(packagingDtoSaved);

        DeliveryDetailsDto deliveryDetailsDtoToSave = DeliveryDetailsDto.builder()
                .ddDeliveryDto(deliveryDtoSaved)
                .ddPackagingDto(null)
                .ddNumberofpackagereturn(BigDecimal.valueOf(10))
                .ddNumberofpackageused(BigDecimal.valueOf(10))
                .build();

        DeliveryDetailsDto deliverydetailsDtoSaved = deliveryDetailsService.saveDeliveryDetails(
                deliveryDetailsDtoToSave);
        return deliverydetailsDtoSaved;
    }

    public DiversDto saveDivers(int num, PointofsaleDto pointofsaleDtoSaved, DiversCashAccountService diversCashAccountService,
                                DiversService diversService){
        assertNotNull(diversService);
        assertNotNull(pointofsaleDtoSaved);
        assertNotNull(diversCashAccountService);

        AddressDto diversAddress = usedForTestForAll.getAddressDto(num+554);
        DiversCashAccountDto diversCashAccountDtoSaved = diversCashAccountService.findDiversCashAccountIfExistOrCreate();
        assertNotNull(diversCashAccountDtoSaved);
        DiversDto diversDtoToSave = DiversDto.builder()
                .diversAddressDto(diversAddress)
                .diversName("divers "+num)
                .diversPosDto(pointofsaleDtoSaved)
                .diversCaDto(diversCashAccountDtoSaved)
                .build();

        DiversDto diversDtoSaved = diversService.saveDivers(diversDtoToSave);
        assertNotNull(diversDtoSaved);
        return diversDtoSaved;
    }

    public DiversDto saveDivers_Invalid(int num, PointofsaleDto pointofsaleDtoSaved, DiversCashAccountService diversCashAccountService,
                                        DiversService diversService){
        AddressDto diversAddress = usedForTestForAll.getAddressDto(num+554);
        DiversCashAccountDto diversCashAccountDtoSaved = diversCashAccountService.findDiversCashAccountIfExistOrCreate();
        DiversDto diversDtoToSave = DiversDto.builder()
                .diversAddressDto(diversAddress)
                .diversCaDto(null)
                .diversName("divers "+num)
                .diversPosDto(pointofsaleDtoSaved)
                .build();

        DiversDto diversDtoSaved = diversService.saveDivers(diversDtoToSave);
        assertNotNull(diversDtoSaved);
        return diversDtoSaved;
    }


    public CommandDto saveCommand(int num, PointofsaleDto pointofsaleDtoSaved, ClientDto clientDtoSaved,
                                  DiversDto diversDtoSaved, UserBMDto userBMDtoSaved, CommandService commandService){

        assertNotNull(pointofsaleDtoSaved);
        //assertNotNull(clientDtoSaved);
        //assertNotNull(diversDtoSaved);
        assertNotNull(userBMDtoSaved);
        assertNotNull(commandService);

        CommandDto commandDtoToSave = CommandDto.builder()
                .cmdCode("CodeCmd__"+num)
                .cmdDate(new Date().toInstant())
                .cmdComment("Command comment "+num)
                .cmdState(CommandState.Edited)
                .cmdType(CommandType.Divers)
                .cmdStatus(CommandStatus.Cash)
                .cmdPosDto(pointofsaleDtoSaved)
                .cmdClientDto(clientDtoSaved)
                .cmdDiversDto(diversDtoSaved)
                .cmdUserbmDto(userBMDtoSaved)
                .build();

        CommandDto commandDtoSaved = commandService.saveCommand(commandDtoToSave);
        assertNotNull(commandDtoSaved);
        return commandDtoSaved;

    }


    public CommandDto saveCommand_Invalid(int num, PointofsaleDto pointofsaleDtoSaved, ClientDto clientDtoSaved,
                                  DiversDto diversDtoSaved, UserBMDto userBMDtoSaved, CommandService commandService){

        //assertNotNull(pointofsaleDtoSaved);
        //assertNotNull(clientDtoSaved);
        //assertNotNull(diversDtoSaved);
        //assertNotNull(userBMDtoSaved);
        assertNotNull(commandService);

        CommandDto commandDtoToSave = CommandDto.builder()
                .cmdCode("CodeCmd__"+num)
                .cmdDate(new Date().toInstant())
                .cmdComment("Command comment "+num)
                .cmdState(CommandState.Edited)
                .cmdType(CommandType.Divers)
                .cmdStatus(CommandStatus.Cash)
                .cmdPosDto(pointofsaleDtoSaved)
                .cmdClientDto(clientDtoSaved)
                .cmdDiversDto(diversDtoSaved)
                .cmdUserbmDto(null)
                .build();

        CommandDto commandDtoSaved = commandService.saveCommand(commandDtoToSave);
        assertNotNull(commandDtoSaved);
        return commandDtoSaved;

    }

    public SaleDto saveSale(int num, CommandDto cmdDtoSaved, ArticleDto artDtoSaved,
                            PointofsaleDto posDtoSaved, SaleService saleService){

        assertNotNull(saleService);
        assertNotNull(artDtoSaved);
        assertNotNull(cmdDtoSaved);
        assertNotNull(posDtoSaved);

        SaleDto saleDtoToSave = SaleDto.builder()
                .saleQuantity(BigDecimal.valueOf(12))
                .saleComment("Comment "+num)
                .saleFinalprice(BigDecimal.valueOf(1254))
                .saleType(SaleType.Whole)
                .saleCommandDto(cmdDtoSaved)
                .saleArticleDto(artDtoSaved)
                .salePosDto(posDtoSaved)
                .build();

        SaleDto saleDtoSaved = saleService.saveSale(saleDtoToSave);
        assertNotNull(saleDtoSaved);
        return saleDtoSaved;
    }

    public SaleDto saveSale_Invalid(int num, CommandDto cmdDtoSaved, ArticleDto artDtoSaved,
                            PointofsaleDto posDtoSaved, SaleService saleService){

        assertNotNull(saleService);
        assertNotNull(artDtoSaved);
        assertNotNull(cmdDtoSaved);
        assertNotNull(posDtoSaved);

        SaleDto saleDtoToSave = SaleDto.builder()
                .saleQuantity(BigDecimal.valueOf(12))
                .saleComment("Comment "+num)
                .saleFinalprice(BigDecimal.valueOf(1254))
                .saleType(SaleType.Whole)
                .saleCommandDto(cmdDtoSaved)
                .saleArticleDto(null)
                .salePosDto(posDtoSaved)
                .build();

        SaleDto saleDtoSaved = saleService.saveSale(saleDtoToSave);
        assertNotNull(saleDtoSaved);
        return saleDtoSaved;

    }

    public BackInDto saveBackIn(int num, CommandDto cmdDtoSaved, UserBMDto userbmDtoSaved,
                            PointofsaleDto posDtoSaved, BackInService backInService){

        assertNotNull(backInService);
        assertNotNull(userbmDtoSaved);
        assertNotNull(cmdDtoSaved);
        assertNotNull(posDtoSaved);


        BackInDto backInDtoToSave = BackInDto.builder()
                .biPosDto(posDtoSaved)
                .biUserbmDto(userbmDtoSaved)
                .biCommandDto(cmdDtoSaved)
                .biComment("comment "+num)
                .biDate(new Date().toInstant())
                .build();

        BackInDto backInDtoSaved = backInService.saveBackIn(backInDtoToSave);
        assertNotNull(backInDtoSaved);
        return backInDtoSaved;
    }

    public BackInDto saveBackIn_Invalid(int num, CommandDto cmdDtoSaved, UserBMDto userbmDtoSaved,
                                PointofsaleDto posDtoSaved, BackInService backInService){

        assertNotNull(backInService);
        assertNotNull(userbmDtoSaved);
        assertNotNull(cmdDtoSaved);
        assertNotNull(posDtoSaved);


        BackInDto backInDtoToSave = BackInDto.builder()
                .biPosDto(posDtoSaved)
                .biUserbmDto(userbmDtoSaved)
                .biCommandDto(cmdDtoSaved)
                .biComment("comment "+num)
                .biDate(null)
                .build();

        BackInDto backInDtoSaved = backInService.saveBackIn(backInDtoToSave);
        assertNotNull(backInDtoSaved);
        return backInDtoSaved;
    }

    public BackInDetailsDto saveBackInDetails(int num, ArticleDto articleDtoSaved, BackInDto backInDtoSaved,
                                       BackInDetailsService backInDetailsService){

        assertNotNull(backInDetailsService);
        assertNotNull(backInDtoSaved);
        assertNotNull(articleDtoSaved);

        BackInDetailsDto backInDetailsDtoToSave = BackInDetailsDto.builder()
                .bidQuantity(BigDecimal.valueOf(5))
                .bidComment("comment "+num)
                .bidArticleDto(articleDtoSaved)
                .bidbiDto(backInDtoSaved)
                .build();

        BackInDetailsDto backInDetailsDtoSaved = backInDetailsService.saveBackInDetails(backInDetailsDtoToSave);
        assertNotNull(backInDetailsDtoSaved);
        return backInDetailsDtoSaved;
    }






















}
