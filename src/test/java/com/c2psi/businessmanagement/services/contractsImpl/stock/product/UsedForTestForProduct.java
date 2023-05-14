package com.c2psi.businessmanagement.services.contractsImpl.stock.product;

import com.c2psi.businessmanagement.Enumerations.CashArrivalType;
import com.c2psi.businessmanagement.Enumerations.CashSourceType;
import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.dtos.client.command.SaleInvoiceCapsuleDto;
import com.c2psi.businessmanagement.dtos.client.command.SaleInvoiceCashDto;
import com.c2psi.businessmanagement.dtos.client.command.SaleInvoiceDamageDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.price.BasePriceDto;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyDto;
import com.c2psi.businessmanagement.dtos.stock.price.SpecialPriceDto;
import com.c2psi.businessmanagement.dtos.stock.product.*;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;
import com.c2psi.businessmanagement.services.contracts.stock.price.BasePriceService;
import com.c2psi.businessmanagement.services.contracts.stock.price.SpecialPriceService;
import com.c2psi.businessmanagement.services.contracts.stock.product.*;
import com.c2psi.businessmanagement.services.contractsImpl.client.command.SaleInvoiceCapsuleServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.client.command.SaleInvoiceCashServiceImpl;
import com.c2psi.businessmanagement.services.contractsImpl.client.command.SaleInvoiceDamageServiceImpl;
import org.junit.Assert;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class UsedForTestForProduct {


    public CategoryDto saveCategory(int num, Long catParentId, PointofsaleDto pointofsaleDtoSaved,
                                    CategoryService categoryService){

        Assert.assertNotNull(pointofsaleDtoSaved);

        CategoryDto categoryDtoToSaved = CategoryDto.builder()
                .catPosDto(pointofsaleDtoSaved)
                .categoryParentId(catParentId)
                .catDescription("Les produits SABC biere en bouteille"+num)
                .catCode("biere_001"+num)
                .catShortname("b_SABC"+num)
                .catName("bierre_SABC"+num)
                .build();

        CategoryDto categoryDtoSaved = categoryService.saveCategory(categoryDtoToSaved);
        return categoryDtoSaved;
    }

    public ProductDto saveProduct(int num, PointofsaleDto pointofsaleDtoSaved, CategoryDto catProdDto,
                                  ProductService productService){

        Assert.assertNotNull(pointofsaleDtoSaved);

        ProductDto productDtoToSaved = ProductDto.builder()
                .prodPosDto(pointofsaleDtoSaved)
                .prodCatDto(catProdDto)
                .prodCode("prod_001_"+num)
                .prodName("export"+num)
                .prodAlias("33exp"+num)
                .prodPerishable(true)
                .prodDescription("prodDescription"+num)
                .build();
        ProductDto productDtoSaved = productService.saveProduct(productDtoToSaved);
        return productDtoSaved;
    }
    public ProductDto saveProduct_NonValid(int num, PointofsaleDto pointofsaleDtoSaved, CategoryDto catProdDto,
                                           ProductService productService){

        Assert.assertNotNull(pointofsaleDtoSaved);

        ProductDto productDtoToSaved = ProductDto.builder()
                .prodPosDto(pointofsaleDtoSaved)
                .prodCatDto(catProdDto)
                .prodCode("")
                .prodName("prodName"+num)
                .prodAlias("prodAlias"+num)
                .prodPerishable(true)
                .prodDescription("prodDescription"+num)
                .build();
        ProductDto productDtoSaved = productService.saveProduct(productDtoToSaved);
        return productDtoSaved;
    }

    public FormatDto saveFormat(int num, PointofsaleDto pointofsaleDtoSaved, FormatService formatService){
        Assert.assertNotNull(pointofsaleDtoSaved);
        FormatDto formatDtoToSaved = FormatDto.builder()
                .formatName("petit_"+num)
                .formatCapacity(BigDecimal.valueOf(0.33))
                .formatPosDto(pointofsaleDtoSaved)
                .build();
        FormatDto formatDtoSaved = formatService.saveFormat(formatDtoToSaved);
        return formatDtoSaved;
    }

    public FormatDto saveFormat_NonValid(int num, PointofsaleDto pointofsaleDtoSaved, FormatService formatService){
        Assert.assertNotNull(pointofsaleDtoSaved);
        FormatDto formatDtoToSaved = FormatDto.builder()
                .formatName("formatName"+num)
                .formatCapacity(BigDecimal.valueOf(0.33))
                .formatPosDto(null)
                .build();
        FormatDto formatDtoSaved = formatService.saveFormat(formatDtoToSaved);
        return formatDtoSaved;
    }

    public ProductFormatedDto saveProductFormatedDto(int num, ProductDto productDtoSaved, FormatDto formatDtoSaved,
                                                     ProductFormatedService productFormatedService){
        Assert.assertNotNull(productDtoSaved);
        Assert.assertNotNull(formatDtoSaved);
        Assert.assertNotNull(productFormatedService);
        ProductFormatedDto productFormatedDtoToSaved = ProductFormatedDto.builder()
                .pfPicture("picture "+num)
                .pfProductDto(productDtoSaved)
                .pfFormatDto(formatDtoSaved)
                .build();
        ProductFormatedDto productFormatedDtoSaved = productFormatedService.saveProductFormated(productFormatedDtoToSaved);
        return productFormatedDtoSaved;
    }

    public ProductFormatedDto saveProductFormatedDto_NotValid(int num, ProductDto productDtoSaved, FormatDto formatDtoSaved,
                                                              ProductFormatedService productFormatedService){
        Assert.assertNotNull(productDtoSaved);
        Assert.assertNotNull(formatDtoSaved);
        Assert.assertNotNull(productFormatedService);
        ProductFormatedDto productFormatedDtoToSaved = ProductFormatedDto.builder()
                .pfPicture("picture "+num)
                .pfProductDto(null)
                .pfFormatDto(formatDtoSaved)
                .build();
        ProductFormatedDto productFormatedDtoSaved = productFormatedService.saveProductFormated(productFormatedDtoToSaved);
        return productFormatedDtoSaved;
    }

    public UnitDto saveUnit(int num, PointofsaleDto pointofsaleDtoSaved, UnitService unitService){
        Assert.assertNotNull(pointofsaleDtoSaved);
        UnitDto unitDtoToSaved = UnitDto.builder()
                .unitAbbreviation("cs"+num)
                .unitName("Casier"+num)
                .unitPosDto(pointofsaleDtoSaved)
                .build();
        UnitDto unitDtoSaved = unitService.saveUnit(unitDtoToSaved);
        return unitDtoSaved;
    }

    public UnitDto saveUnit_NotValid(int num, PointofsaleDto pointofsaleDtoSaved, UnitService unitService){
        Assert.assertNotNull(pointofsaleDtoSaved);
        UnitDto unitDtoToSaved = UnitDto.builder()
                .unitAbbreviation("cs"+num)
                .unitName("")
                .unitPosDto(pointofsaleDtoSaved)
                .build();
        UnitDto unitDtoSaved = unitService.saveUnit(unitDtoToSaved);
        return unitDtoSaved;
    }


    public UnitConversionDto saveUnitConversion(int num, UnitDto unitDtoSourceSaved, UnitDto unitDtoDestinationSaved,
                                                UnitConversionService unitconvService){
        Assert.assertNotNull(unitDtoSourceSaved);
        Assert.assertNotNull(unitDtoDestinationSaved);
        UnitConversionDto unitConversionDtoToSave = UnitConversionDto.builder()
                .conversionFactor(Double.valueOf(2)+num)
                .unitSourceDto(unitDtoSourceSaved)
                .unitDestinationDto(unitDtoDestinationSaved)
                .build();
        UnitConversionDto unitConversionDtoSaved = unitconvService.saveUnitConversion(unitConversionDtoToSave);
        return unitConversionDtoSaved;
    }

    public UnitConversionDto saveUnitConversion_NotValid(int num, UnitDto unitDtoSourceSaved, UnitDto unitDtoDestinationSaved,
                                                         UnitConversionService unitconvService){
        Assert.assertNotNull(unitDtoSourceSaved);
        Assert.assertNotNull(unitDtoDestinationSaved);
        UnitConversionDto unitConversionDtoToSave = UnitConversionDto.builder()
                .conversionFactor(Double.valueOf(2)+num)
                .unitSourceDto(null)
                .unitSourceDto(unitDtoDestinationSaved)
                .build();
        UnitConversionDto unitConversionDtoSaved = unitconvService.saveUnitConversion(unitConversionDtoToSave);
        return unitConversionDtoSaved;
    }



    public ArticleDto saveArticle(int num, ProductFormatedDto productFormatedDtoSaved, UnitDto unitDtoSaved,
                                  BasePriceDto basePriceDtoSaved, PointofsaleDto pointofsaleDtoSaved,
                                  ArticleService articleService){
        Assert.assertNotNull(productFormatedDtoSaved);
        Assert.assertNotNull(unitDtoSaved);
        Assert.assertNotNull(basePriceDtoSaved);
        Assert.assertNotNull(pointofsaleDtoSaved);
        Assert.assertNotNull(articleService);


        ArticleDto articleDtoToSave = ArticleDto.builder()
                .artCode("exp65_"+num)
                .artName("33 Export65_"+num)
                .artShortname("Export65_"+num)
                .artDescription("Le supporter numero 1 du football")
                .artThreshold(BigDecimal.valueOf(5))
                .artLowLimitSemiWholesale(BigDecimal.valueOf(7))
                .artLowLimitWholesale(BigDecimal.valueOf(10))
                .artQuantityinstock(BigDecimal.valueOf(20))
                .artPfDto(productFormatedDtoSaved)
                .artUnitDto(unitDtoSaved)
                .artBpDto(basePriceDtoSaved)
                .artPosDto(pointofsaleDtoSaved)
                .build();

        ArticleDto articleDtoSaved = articleService.saveArticle(articleDtoToSave);
        return articleDtoSaved;
    }

    public ArticleDto saveArticle_NotValid(int num, ProductFormatedDto productFormatedDtoSaved, UnitDto unitDtoSaved,
                                           BasePriceDto basePriceDtoSaved, PointofsaleDto pointofsaleDtoSaved,
                                           ArticleService articleService){
        Assert.assertNotNull(productFormatedDtoSaved);
        Assert.assertNotNull(unitDtoSaved);
        Assert.assertNotNull(basePriceDtoSaved);
        Assert.assertNotNull(pointofsaleDtoSaved);
        Assert.assertNotNull(articleService);


        ArticleDto articleDtoToSave = ArticleDto.builder()
                .artCode(null)
                .artName("33 Export65_"+num)
                .artShortname("Export65_"+num)
                .artDescription("Le supporter numero 1 du football")
                .artThreshold(BigDecimal.valueOf(5))
                .artLowLimitSemiWholesale(BigDecimal.valueOf(7))
                .artLowLimitWholesale(BigDecimal.valueOf(10))
                .artQuantityinstock(BigDecimal.valueOf(20))
                .artPfDto(productFormatedDtoSaved)
                .artUnitDto(unitDtoSaved)
                .artBpDto(basePriceDtoSaved)
                .artPosDto(pointofsaleDtoSaved)
                .build();

        ArticleDto articleDtoSaved = articleService.saveArticle(articleDtoToSave);
        return articleDtoSaved;
    }

    public PackagingDto savePackaging(int num, ProviderDto providerDtoSaved, PointofsaleDto pointofsaleDtoSaved,
                                    PackagingService packagingService){
        Assert.assertNotNull(providerDtoSaved);
        Assert.assertNotNull(pointofsaleDtoSaved);
        Assert.assertNotNull(packagingService);


        PackagingDto packagingDtoToSaved = PackagingDto.builder()
                .packLabel("casier_"+num)
                .packFirstcolor("rouge_"+num)
                .packDescription("Casier de 12_"+num)
                .packPrice(BigDecimal.valueOf(3600))
                .packProviderDto(providerDtoSaved)
                .packPosDto(pointofsaleDtoSaved)
                .build();

        PackagingDto packagingDtoSaved = packagingService.savePackaging(packagingDtoToSaved);
        return packagingDtoSaved;
    }

    public PackagingDto savePackaging_Invalid(int num, ProviderDto providerDtoSaved, PointofsaleDto pointofsaleDtoSaved,
                                      PackagingService packagingService){
        Assert.assertNotNull(providerDtoSaved);
        Assert.assertNotNull(pointofsaleDtoSaved);
        Assert.assertNotNull(packagingService);


        PackagingDto packagingDtoToSaved = PackagingDto.builder()
                .packLabel("casier_"+num)
                .packFirstcolor("rouge_"+num)
                .packDescription("Casier de 12_"+num)
                .packPrice(null)
                .packProviderDto(providerDtoSaved)
                .packPosDto(pointofsaleDtoSaved)
                .build();

        PackagingDto packagingDtoSaved = packagingService.savePackaging(packagingDtoToSaved);
        return packagingDtoSaved;
    }

    public SupplyInvoiceCashDto saveSupplyInvoiceCash(int num, UserBMDto userbmDtoSaved, ProviderDto providerDtoSaved,
                                                      PointofsaleDto pointofsaleDtoSaved,
                                                      SupplyInvoiceCashService supplyInvoiceCashService){
        Assert.assertNotNull(providerDtoSaved);
        Assert.assertNotNull(pointofsaleDtoSaved);
        Assert.assertNotNull(userbmDtoSaved);
        Assert.assertNotNull(supplyInvoiceCashService);

        SupplyInvoiceCashDto supplyInvoiceCashDtoToSaved = SupplyInvoiceCashDto.builder()
                .sicashAmountexpected(BigDecimal.valueOf(100000))
                .sicashAmountpaid(BigDecimal.valueOf(95600))
                .sicashPicture("cxxcx"+num)
                .sicashCode("codeFacture_"+num)
                .sicashComment("Commentaire sur la facture remis par le founisseur")
                .sicashTotalcolis(BigDecimal.valueOf(78))
                .sicashInvoicingDate(new Date().toInstant())
                .sicashDeliveryDate(new Date().toInstant())
                .sicashSourceofcash(CashSourceType.CASH)
                .sicashPosDto(pointofsaleDtoSaved)
                .sicashUserbmDto(userbmDtoSaved)
                .sicashProviderDto(providerDtoSaved)
                .build();

        SupplyInvoiceCashDto supplyInvoiceCashDtoSaved = supplyInvoiceCashService.
                saveSupplyInvoiceCash(supplyInvoiceCashDtoToSaved);
        return supplyInvoiceCashDtoSaved;
    }

    public SupplyInvoiceCashDto saveSupplyInvoiceCash_Invalid(int num, UserBMDto userbmDtoSaved, ProviderDto providerDtoSaved,
                                                      PointofsaleDto pointofsaleDtoSaved,
                                                      SupplyInvoiceCashService supplyInvoiceCashService){
        Assert.assertNotNull(providerDtoSaved);
        Assert.assertNotNull(pointofsaleDtoSaved);
        Assert.assertNotNull(userbmDtoSaved);
        Assert.assertNotNull(supplyInvoiceCashService);

        SupplyInvoiceCashDto supplyInvoiceCashDtoToSaved = SupplyInvoiceCashDto.builder()
                .sicashAmountexpected(BigDecimal.valueOf(100000))
                .sicashAmountpaid(BigDecimal.valueOf(95600))
                .sicashPicture("cxxcx"+num)
                .sicashCode("codeFacture_"+num)
                .sicashComment("Commentaire sur la facture remis par le founisseur")
                .sicashTotalcolis(BigDecimal.valueOf(78))
                .sicashInvoicingDate(new Date().toInstant())
                .sicashDeliveryDate(null)
                .sicashSourceofcash(CashSourceType.CASH)
                .sicashPosDto(pointofsaleDtoSaved)
                .sicashUserbmDto(userbmDtoSaved)
                .sicashProviderDto(providerDtoSaved)
                .build();

        SupplyInvoiceCashDto supplyInvoiceCashDtoSaved = supplyInvoiceCashService.
                saveSupplyInvoiceCash(supplyInvoiceCashDtoToSaved);
        return supplyInvoiceCashDtoSaved;
    }

    public SupplyInvoiceCapsuleDto saveSupplyInvoiceCapsule(int num, UserBMDto userbmDtoSaved, ProviderDto providerDtoSaved,
                                                            PointofsaleDto pointofsaleDtoSaved,
                                                            SupplyInvoiceCapsuleService supplyInvoiceCapsuleService){
        Assert.assertNotNull(providerDtoSaved);
        Assert.assertNotNull(pointofsaleDtoSaved);
        Assert.assertNotNull(userbmDtoSaved);
        Assert.assertNotNull(supplyInvoiceCapsuleService);

        SupplyInvoiceCapsuleDto supplyInvoiceCapsDtoToSaved = SupplyInvoiceCapsuleDto.builder()
                .sicapsTotalCapsToChange(BigDecimal.valueOf(10))
                .sicapsTotalCapsChange(BigDecimal.valueOf(9))
                .sicapsPicture("cxxcx"+num)
                .sicapsCode("codeFacture_"+num)
                .sicapsComment("Commentaire sur la facture remis par le founisseur")
                .sicapsTotalcolis(BigDecimal.valueOf(9))
                .sicapsInvoicingDate(new Date().toInstant())
                .sicapsDeliveryDate(new Date().toInstant())
                .sicapsPosDto(pointofsaleDtoSaved)
                .sicapsUserbmDto(userbmDtoSaved)
                .sicapsProviderDto(providerDtoSaved)
                .build();

        SupplyInvoiceCapsuleDto supplyInvoiceCapsDtoSaved = supplyInvoiceCapsuleService.
                saveSupplyInvoiceCapsule(supplyInvoiceCapsDtoToSaved);
        return supplyInvoiceCapsDtoSaved;
    }

    public SupplyInvoiceCapsuleDto saveSupplyInvoiceCapsule_Invalid(int num, UserBMDto userbmDtoSaved, ProviderDto providerDtoSaved,
                                                            PointofsaleDto pointofsaleDtoSaved,
                                                            SupplyInvoiceCapsuleService supplyInvoiceCapsuleService){
        Assert.assertNotNull(providerDtoSaved);
        Assert.assertNotNull(pointofsaleDtoSaved);
        Assert.assertNotNull(userbmDtoSaved);
        Assert.assertNotNull(supplyInvoiceCapsuleService);

        SupplyInvoiceCapsuleDto supplyInvoiceCapsDtoToSaved = SupplyInvoiceCapsuleDto.builder()
                .sicapsTotalCapsToChange(BigDecimal.valueOf(10))
                .sicapsTotalCapsChange(BigDecimal.valueOf(9))
                .sicapsPicture("cxxcx"+num)
                .sicapsCode(null)
                .sicapsComment("Commentaire sur la facture remis par le founisseur")
                .sicapsTotalcolis(BigDecimal.valueOf(9))
                .sicapsInvoicingDate(new Date().toInstant())
                .sicapsDeliveryDate(new Date().toInstant())
                .sicapsPosDto(pointofsaleDtoSaved)
                .sicapsUserbmDto(userbmDtoSaved)
                .sicapsProviderDto(providerDtoSaved)
                .build();

        SupplyInvoiceCapsuleDto supplyInvoiceCapsDtoSaved = supplyInvoiceCapsuleService.
                saveSupplyInvoiceCapsule(supplyInvoiceCapsDtoToSaved);
        return supplyInvoiceCapsDtoSaved;
    }

    public SupplyInvoiceDamageDto saveSupplyInvoiceDamage(int num, UserBMDto userbmDtoSaved, ProviderDto providerDtoSaved,
                                                            PointofsaleDto pointofsaleDtoSaved,
                                                            SupplyInvoiceDamageService supplyInvoiceDamageService){
        Assert.assertNotNull(providerDtoSaved);
        Assert.assertNotNull(pointofsaleDtoSaved);
        Assert.assertNotNull(userbmDtoSaved);
        Assert.assertNotNull(supplyInvoiceDamageService);

        SupplyInvoiceDamageDto supplyInvoiceDamDtoToSaved = SupplyInvoiceDamageDto.builder()
                .sidamTotalDamToChange(BigDecimal.valueOf(10))
                .sidamTotalDamChange(BigDecimal.valueOf(9))
                .sidamPicture("cxxcx"+num)
                .sidamCode("codeFacture_"+num)
                .sidamComment("Commentaire sur la facture remis par le founisseur")
                .sidamTotalcolis(BigDecimal.valueOf(9))
                .sidamInvoicingDate(new Date().toInstant())
                .sidamDeliveryDate(new Date().toInstant())
                .sidamPosDto(pointofsaleDtoSaved)
                .sidamUserbmDto(userbmDtoSaved)
                .sidamProviderDto(providerDtoSaved)
                .build();

        SupplyInvoiceDamageDto supplyInvoiceDamDtoSaved = supplyInvoiceDamageService.
                saveSupplyInvoiceDamage(supplyInvoiceDamDtoToSaved);
        return supplyInvoiceDamDtoSaved;
    }

    public SupplyInvoiceDamageDto saveSupplyInvoiceDamage_Invalid(int num, UserBMDto userbmDtoSaved, ProviderDto providerDtoSaved,
                                                          PointofsaleDto pointofsaleDtoSaved,
                                                          SupplyInvoiceDamageService supplyInvoiceDamageService){
        Assert.assertNotNull(providerDtoSaved);
        Assert.assertNotNull(pointofsaleDtoSaved);
        Assert.assertNotNull(userbmDtoSaved);
        Assert.assertNotNull(supplyInvoiceDamageService);

        SupplyInvoiceDamageDto supplyInvoiceDamDtoToSaved = SupplyInvoiceDamageDto.builder()
                .sidamTotalDamToChange(BigDecimal.valueOf(10))
                .sidamTotalDamChange(BigDecimal.valueOf(9))
                .sidamPicture("cxxcx"+num)
                .sidamCode(null)
                .sidamComment("Commentaire sur la facture remis par le founisseur")
                .sidamTotalcolis(BigDecimal.valueOf(9))
                .sidamInvoicingDate(new Date().toInstant())
                .sidamDeliveryDate(new Date().toInstant())
                .sidamPosDto(pointofsaleDtoSaved)
                .sidamUserbmDto(userbmDtoSaved)
                .sidamProviderDto(providerDtoSaved)
                .build();

        SupplyInvoiceDamageDto supplyInvoiceDamDtoSaved = supplyInvoiceDamageService.
                saveSupplyInvoiceDamage(supplyInvoiceDamDtoToSaved);
        return supplyInvoiceDamDtoSaved;
    }

    public CashArrivalDto saveCashArrival(int num, ArticleDto articleDtoSaved, SupplyInvoiceCashDto sicashDtoSaved,
                                          CashArrivalService cashArrivalService){

        //Assert.assertNotNull(sicashDtoSaved);
        Assert.assertNotNull(articleDtoSaved);
        Assert.assertNotNull(cashArrivalService);

        CashArrivalDto cashArrivalDtoToSave = CashArrivalDto.builder()
                .cashaUnitprice(BigDecimal.valueOf(6000))
                .cashaArrivaltype(CashArrivalType.Divers)
                .cashaDeliveryquantity(BigDecimal.valueOf(15))
                .cashaArrivalEntryDate(new Date().toInstant())
                .cashaArtDto(articleDtoSaved)
                .cashaSicashDto(sicashDtoSaved)
                .build();

        CashArrivalDto cashArrivalDtoSaved = cashArrivalService.saveCashArrival(cashArrivalDtoToSave);
        return cashArrivalDtoSaved;
    }

    public CashArrivalDto saveCashArrival_NullSicash(int num, ArticleDto articleDtoSaved, SupplyInvoiceCashDto sicashDtoSaved,
                                          CashArrivalService cashArrivalService){

        //Assert.assertNotNull(sicashDtoSaved);
        Assert.assertNotNull(articleDtoSaved);
        Assert.assertNotNull(cashArrivalService);

        CashArrivalDto cashArrivalDtoToSave = CashArrivalDto.builder()
                .cashaUnitprice(BigDecimal.valueOf(6000))
                .cashaArrivaltype(CashArrivalType.Divers)
                .cashaDeliveryquantity(BigDecimal.valueOf(15))
                .cashaArrivalEntryDate(new Date().toInstant())
                .cashaArtDto(articleDtoSaved)
                .cashaSicashDto(null)
                .build();

        CashArrivalDto cashArrivalDtoSaved = cashArrivalService.saveCashArrival(cashArrivalDtoToSave);
        return cashArrivalDtoSaved;
    }

    public CashArrivalDto saveCashArrival_Invalid(int num, ArticleDto articleDtoSaved, SupplyInvoiceCashDto sicashDtoSaved,
                                          CashArrivalService cashArrivalService){

        //Assert.assertNotNull(sicashDtoSaved);
        Assert.assertNotNull(articleDtoSaved);
        Assert.assertNotNull(cashArrivalService);

        CashArrivalDto cashArrivalDtoToSave = CashArrivalDto.builder()
                .cashaUnitprice(BigDecimal.valueOf(6000))
                .cashaArrivaltype(null)
                .cashaDeliveryquantity(BigDecimal.valueOf(15))
                .cashaArrivalEntryDate(new Date().toInstant())
                .cashaArtDto(articleDtoSaved)
                .cashaSicashDto(sicashDtoSaved)
                .build();

        CashArrivalDto cashArrivalDtoSaved = cashArrivalService.saveCashArrival(cashArrivalDtoToSave);
        return cashArrivalDtoSaved;
    }

    public DamageArrivalDto saveDamageArrival(int num, ArticleDto articleDtoSaved, SupplyInvoiceDamageDto sidamDtoSaved,
                                          DamageArrivalService damageArrivalService){

        //Assert.assertNotNull(sicashDtoSaved);
        Assert.assertNotNull(articleDtoSaved);
        Assert.assertNotNull(damageArrivalService);

        DamageArrivalDto damageArrivalDtoToSave = DamageArrivalDto.builder()
                .damaArtDto(articleDtoSaved)
                .damaSidamDto(sidamDtoSaved)
                .damaQuantityartchanged(BigDecimal.valueOf(10))
                .damaDeliveryquantity(BigDecimal.valueOf(10))
                .damaArrivalEntryDate(new Date().toInstant())
                .build();

        DamageArrivalDto damageArrivalDtoSaved = damageArrivalService.saveDamageArrival(damageArrivalDtoToSave);
        return damageArrivalDtoSaved;
    }

    public DamageArrivalDto saveDamArrival_NullSidam(int num, ArticleDto articleDtoSaved, SupplyInvoiceDamageDto sidamDtoSaved,
                                                     DamageArrivalService damageArrivalService){

        //Assert.assertNotNull(sicashDtoSaved);
        Assert.assertNotNull(articleDtoSaved);
        Assert.assertNotNull(damageArrivalService);

        DamageArrivalDto damageArrivalDtoToSave = DamageArrivalDto.builder()
                .damaArtDto(articleDtoSaved)
                .damaSidamDto(null)
                .damaQuantityartchanged(BigDecimal.valueOf(10))
                .damaDeliveryquantity(BigDecimal.valueOf(10))
                .damaArrivalEntryDate(new Date().toInstant())
                .build();

        DamageArrivalDto damageArrivalDtoSaved = damageArrivalService.saveDamageArrival(damageArrivalDtoToSave);
        return damageArrivalDtoSaved;
    }

    public DamageArrivalDto saveDamageArrival_Invalid(int num, ArticleDto articleDtoSaved, SupplyInvoiceDamageDto sidamDtoSaved,
                                                  DamageArrivalService damageArrivalService){

        //Assert.assertNotNull(sicashDtoSaved);
        Assert.assertNotNull(articleDtoSaved);
        Assert.assertNotNull(damageArrivalService);

        DamageArrivalDto damageArrivalDtoToSave = DamageArrivalDto.builder()
                .damaArtDto(articleDtoSaved)
                .damaSidamDto(null)
                .damaQuantityartchanged(null)
                .damaDeliveryquantity(BigDecimal.valueOf(10))
                .damaArrivalEntryDate(new Date().toInstant())
                .build();

        DamageArrivalDto damageArrivalDtoSaved = damageArrivalService.saveDamageArrival(damageArrivalDtoToSave);
        return damageArrivalDtoSaved;
    }

    public CapsuleArrivalDto saveCapsuleArrival(int num, ArticleDto articleDtoSaved, SupplyInvoiceCapsuleDto sicapsDtoSaved,
                                              CapsuleArrivalService capsuleArrivalService){

        //Assert.assertNotNull(sicashDtoSaved);
        Assert.assertNotNull(articleDtoSaved);
        Assert.assertNotNull(capsuleArrivalService);

        CapsuleArrivalDto capsuleArrivalDtoToSave = CapsuleArrivalDto.builder()
                .capsaArtDto(articleDtoSaved)
                .capsaSicapsDto(sicapsDtoSaved)
                .capsaDeliveryquantity(BigDecimal.valueOf(10))
                .capsaQuantitycapschanged(BigDecimal.valueOf(10))
                .capsaArrivalEntryDate(new Date().toInstant())
                .build();

        CapsuleArrivalDto capsuleArrivalDtoSaved = capsuleArrivalService.saveCapsuleArrival(capsuleArrivalDtoToSave);
        return capsuleArrivalDtoSaved;
    }

    public CapsuleArrivalDto saveCapsArrival_NullSiCaps(int num, ArticleDto articleDtoSaved, SupplyInvoiceCapsuleDto sicapsDtoSaved,
                                                     CapsuleArrivalService capsuleArrivalService){

        //Assert.assertNotNull(sicashDtoSaved);
        Assert.assertNotNull(articleDtoSaved);
        Assert.assertNotNull(capsuleArrivalService);

        CapsuleArrivalDto capsuleArrivalDtoToSave = CapsuleArrivalDto.builder()
                .capsaArtDto(articleDtoSaved)
                .capsaSicapsDto(null)
                .capsaDeliveryquantity(BigDecimal.valueOf(10))
                .capsaQuantitycapschanged(BigDecimal.valueOf(10))
                .capsaArrivalEntryDate(new Date().toInstant())
                .build();

        CapsuleArrivalDto capsuleArrivalDtoSaved = capsuleArrivalService.saveCapsuleArrival(capsuleArrivalDtoToSave);
        return capsuleArrivalDtoSaved;
    }

    public CapsuleArrivalDto saveCapsuleArrival_Invalid(int num, ArticleDto articleDtoSaved, SupplyInvoiceCapsuleDto siCapsDtoSaved,
                                                      CapsuleArrivalService capsuleArrivalService){

        //Assert.assertNotNull(sicashDtoSaved);
        Assert.assertNotNull(articleDtoSaved);
        Assert.assertNotNull(capsuleArrivalService);

        CapsuleArrivalDto capsuleArrivalDtoToSave = CapsuleArrivalDto.builder()
                .capsaArtDto(null)
                .capsaSicapsDto(null)
                .capsaDeliveryquantity(BigDecimal.valueOf(10))
                .capsaQuantitycapschanged(BigDecimal.valueOf(10))
                .capsaArrivalEntryDate(new Date().toInstant())
                .build();

        CapsuleArrivalDto capsuleArrivalDtoSaved = capsuleArrivalService.saveCapsuleArrival(capsuleArrivalDtoToSave);
        return capsuleArrivalDtoSaved;
    }

    public InventoryDto saveInventory(int num, PointofsaleDto pointofsaleDtoSaved, InventoryService inventoryService){

        Assert.assertNotNull(inventoryService);
        Assert.assertNotNull(pointofsaleDtoSaved);

        InventoryDto inventoryDtoToSave = InventoryDto.builder()
                .invCode("invCode"+num)
                .invComment("invComment"+num)
                .invDate(new Date().toInstant())
                .invPosDto(pointofsaleDtoSaved)
                .build();

        InventoryDto inventoryDtoSaved = inventoryService.saveInventory(inventoryDtoToSave);
        return inventoryDtoSaved;
    }

    public InventoryDto saveInventory_Invalid(int num, PointofsaleDto pointofsaleDtoSaved, InventoryService inventoryService){

        Assert.assertNotNull(inventoryService);
        Assert.assertNotNull(pointofsaleDtoSaved);

        InventoryDto inventoryDtoToSave = InventoryDto.builder()
                .invCode(null)
                .invComment("invComment"+num)
                .invDate(new Date().toInstant())
                .invPosDto(pointofsaleDtoSaved)
                .build();

        InventoryDto inventoryDtoSaved = inventoryService.saveInventory(inventoryDtoToSave);
        return inventoryDtoSaved;
    }

    public InventoryLineDto saveInventoryLine(int num, PointofsaleDto pointofsaleDtoSaved, ArticleDto articleDtoSaved,
                                              InventoryDto inventoryDtoSaved, InventoryLineService inventoryLineService){

        Assert.assertNotNull(inventoryLineService);
        Assert.assertNotNull(pointofsaleDtoSaved);
        Assert.assertNotNull(inventoryDtoSaved);

        InventoryLineDto inventoryLineDtoToSave = InventoryLineDto.builder()
                .invlineComment("invlineComment"+num)
                .invlineRealqteinstock(BigDecimal.valueOf(15))
                .invlineLogicqteinstock(BigDecimal.valueOf(16))
                .invlineInvDto(inventoryDtoSaved)
                .invlineArtDto(articleDtoSaved)
                .build();

        InventoryLineDto inventorylineDtoSaved = inventoryLineService.saveInventoryLine(inventoryLineDtoToSave);
        return inventorylineDtoSaved;
    }

    public InventoryLineDto saveInventoryLine_Invalid(int num, PointofsaleDto pointofsaleDtoSaved, ArticleDto articleDtoSaved,
                                              InventoryDto inventoryDtoSaved, InventoryLineService inventoryLineService){

        Assert.assertNotNull(inventoryLineService);
        Assert.assertNotNull(pointofsaleDtoSaved);
        Assert.assertNotNull(inventoryDtoSaved);

        InventoryLineDto inventoryLineDtoToSave = InventoryLineDto.builder()
                .invlineComment("invlineComment"+num)
                .invlineRealqteinstock(BigDecimal.valueOf(15))
                .invlineLogicqteinstock(BigDecimal.valueOf(16))
                .invlineInvDto(null)
                .invlineArtDto(articleDtoSaved)
                .build();

        InventoryLineDto inventorylineDtoSaved = inventoryLineService.saveInventoryLine(inventoryLineDtoToSave);
        return inventorylineDtoSaved;
    }


    public SaleInvoiceCashDto saveSaleInvoiceCashDto(int num, UserBMDto userBMDtoSaved, PointofsaleDto pointofsaleDtoSaved,
                                                     ClientDto clientDtoSaved, SaleInvoiceCashServiceImpl saleInvoiceCashService){

        Assert.assertNotNull(saleInvoiceCashService);
        Assert.assertNotNull(pointofsaleDtoSaved);
        Assert.assertNotNull(userBMDtoSaved);
        Assert.assertNotNull(clientDtoSaved);

        SaleInvoiceCashDto saleInvoiceCashDtoToUpdate = SaleInvoiceCashDto.builder()
                .saleicashCurrentAmountexpected(BigDecimal.valueOf(74000))
                .saleicashAmountexpected(BigDecimal.valueOf(74000))
                .saleicashAmountpaid(BigDecimal.valueOf(74000))
                .saleicashAmountreimbourse(BigDecimal.valueOf(0))
                .saleicashTotalcolis(BigDecimal.valueOf(50))
                .saleicashDeliveryDate(new Date().toInstant())
                .saleicashInvoicingDate(new Date().toInstant())
                .saleicashCode("salecashCode"+num)
                .saleicashUserbmDto(userBMDtoSaved)
                .saleicashPosDto(pointofsaleDtoSaved)
                .saleicashClientDto(clientDtoSaved)
                .saleicashSourceofcash(CashSourceType.CASH)
                .build();

        SaleInvoiceCashDto saleInvoiceCashDtoUpdated = saleInvoiceCashService.saveSaleInvoiceCash(saleInvoiceCashDtoToUpdate);
        return saleInvoiceCashDtoUpdated;
    }

    public SaleInvoiceCashDto saveSaleInvoiceCashDto_Invalid(int num, UserBMDto userBMDtoSaved, PointofsaleDto pointofsaleDtoSaved,
                                                     ClientDto clientDtoSaved, SaleInvoiceCashServiceImpl saleInvoiceCashService){

        Assert.assertNotNull(saleInvoiceCashService);
        Assert.assertNotNull(pointofsaleDtoSaved);
        Assert.assertNotNull(userBMDtoSaved);
        Assert.assertNotNull(clientDtoSaved);

        SaleInvoiceCashDto saleInvoiceCashDtoToUpdate = SaleInvoiceCashDto.builder()
                .saleicashCurrentAmountexpected(BigDecimal.valueOf(74000))
                .saleicashAmountexpected(BigDecimal.valueOf(74000))
                .saleicashAmountpaid(BigDecimal.valueOf(74000))
                .saleicashAmountreimbourse(BigDecimal.valueOf(0))
                .saleicashTotalcolis(BigDecimal.valueOf(50))
                .saleicashDeliveryDate(new Date().toInstant())
                .saleicashInvoicingDate(new Date().toInstant())
                .saleicashCode(null)
                .saleicashUserbmDto(userBMDtoSaved)
                .saleicashPosDto(pointofsaleDtoSaved)
                .saleicashClientDto(clientDtoSaved)
                .saleicashSourceofcash(CashSourceType.CASH)
                .build();

        SaleInvoiceCashDto saleInvoiceCashDtoUpdated = saleInvoiceCashService.saveSaleInvoiceCash(saleInvoiceCashDtoToUpdate);
        return saleInvoiceCashDtoUpdated;
    }

    public SaleInvoiceCapsuleDto saveSaleInvoiceCapsuleDto(int num, UserBMDto userBMDtoSaved, PointofsaleDto pointofsaleDtoSaved,
                                                           ClientDto clientDtoSaved, SaleInvoiceCapsuleServiceImpl saleInvoiceCapsuleService){

        Assert.assertNotNull(saleInvoiceCapsuleService);
        Assert.assertNotNull(pointofsaleDtoSaved);
        Assert.assertNotNull(userBMDtoSaved);
        Assert.assertNotNull(clientDtoSaved);

        SaleInvoiceCapsuleDto saleInvoiceCapsuleDtoToUpdate = SaleInvoiceCapsuleDto.builder()
                .saleicapsNumbertochange(BigDecimal.valueOf(5))
                .saleicapsNumberchanged(BigDecimal.valueOf(5))
                .saleicapsTotalcolis(BigDecimal.valueOf(5))
                .saleicapsDeliveryDate(new Date().toInstant())
                .saleicapsInvoicingDate(new Date().toInstant())
                .saleicapsCode("salecashCode"+num)
                .saleicapsUserbmDto(userBMDtoSaved)
                .saleicapsPosDto(pointofsaleDtoSaved)
                .saleicapsClientDto(clientDtoSaved)
                .build();

        SaleInvoiceCapsuleDto saleInvoiceCapsuleDtoUpdated = saleInvoiceCapsuleService.saveSaleInvoiceCapsule(saleInvoiceCapsuleDtoToUpdate);
        return saleInvoiceCapsuleDtoUpdated;
    }


    public SaleInvoiceCapsuleDto saveSaleInvoiceCapsuleDto_Invalid(int num, UserBMDto userBMDtoSaved, PointofsaleDto pointofsaleDtoSaved,
                                                           ClientDto clientDtoSaved, SaleInvoiceCapsuleServiceImpl saleInvoiceCapsuleService){

        Assert.assertNotNull(saleInvoiceCapsuleService);
        Assert.assertNotNull(pointofsaleDtoSaved);
        Assert.assertNotNull(userBMDtoSaved);
        Assert.assertNotNull(clientDtoSaved);

        SaleInvoiceCapsuleDto saleInvoiceCapsuleDtoToUpdate = SaleInvoiceCapsuleDto.builder()
                .saleicapsNumbertochange(BigDecimal.valueOf(5))
                .saleicapsNumberchanged(BigDecimal.valueOf(5))
                .saleicapsTotalcolis(BigDecimal.valueOf(5))
                .saleicapsDeliveryDate(new Date().toInstant())
                .saleicapsInvoicingDate(new Date().toInstant())
                .saleicapsCode(null)
                .saleicapsUserbmDto(userBMDtoSaved)
                .saleicapsPosDto(pointofsaleDtoSaved)
                .saleicapsClientDto(clientDtoSaved)
                .build();

        SaleInvoiceCapsuleDto saleInvoiceCapsuleDtoUpdated = saleInvoiceCapsuleService.saveSaleInvoiceCapsule(saleInvoiceCapsuleDtoToUpdate);
        return saleInvoiceCapsuleDtoUpdated;
    }


    public SaleInvoiceDamageDto saveSaleInvoiceDamageDto(int num, UserBMDto userBMDtoSaved, PointofsaleDto pointofsaleDtoSaved,
                                                         ClientDto clientDtoSaved, SaleInvoiceDamageServiceImpl saleInvoiceDamageService){

        Assert.assertNotNull(saleInvoiceDamageService);
        Assert.assertNotNull(pointofsaleDtoSaved);
        Assert.assertNotNull(userBMDtoSaved);
        Assert.assertNotNull(clientDtoSaved);

        SaleInvoiceDamageDto saleInvoiceDamageDtoToUpdate = SaleInvoiceDamageDto.builder()
                .saleidamNumbertochange(BigDecimal.valueOf(5))
                .saleidamNumberchanged(BigDecimal.valueOf(5))
                .saleidamTotalcolis(BigDecimal.valueOf(5))
                .saleidamDeliveryDate(new Date().toInstant())
                .saleidamInvoicingDate(new Date().toInstant())
                .saleidamCode("saledamCode"+num)
                .saleidamUserbmDto(userBMDtoSaved)
                .saleidamPosDto(pointofsaleDtoSaved)
                .saleidamClientDto(clientDtoSaved)
                .build();

        SaleInvoiceDamageDto saleInvoiceDamageDtoUpdated = saleInvoiceDamageService.saveSaleInvoiceDamage(saleInvoiceDamageDtoToUpdate);
        return saleInvoiceDamageDtoUpdated;
    }

    public SaleInvoiceDamageDto saveSaleInvoiceDamageDto_Invalid(int num, UserBMDto userBMDtoSaved, PointofsaleDto pointofsaleDtoSaved,
                                                         ClientDto clientDtoSaved, SaleInvoiceDamageServiceImpl saleInvoiceDamageService){

        Assert.assertNotNull(saleInvoiceDamageService);
        Assert.assertNotNull(pointofsaleDtoSaved);
        Assert.assertNotNull(userBMDtoSaved);
        Assert.assertNotNull(clientDtoSaved);

        SaleInvoiceDamageDto saleInvoiceDamageDtoToUpdate = SaleInvoiceDamageDto.builder()
                .saleidamNumbertochange(BigDecimal.valueOf(5))
                .saleidamNumberchanged(BigDecimal.valueOf(5))
                .saleidamTotalcolis(BigDecimal.valueOf(5))
                .saleidamDeliveryDate(new Date().toInstant())
                .saleidamInvoicingDate(new Date().toInstant())
                .saleidamCode(null)
                .saleidamUserbmDto(userBMDtoSaved)
                .saleidamPosDto(pointofsaleDtoSaved)
                .saleidamClientDto(clientDtoSaved)
                .build();

        SaleInvoiceDamageDto saleInvoiceDamageDtoUpdated = saleInvoiceDamageService.saveSaleInvoiceDamage(saleInvoiceDamageDtoToUpdate);
        return saleInvoiceDamageDtoUpdated;
    }



}
