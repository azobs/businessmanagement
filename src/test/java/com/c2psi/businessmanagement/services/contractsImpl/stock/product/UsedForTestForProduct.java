package com.c2psi.businessmanagement.services.contractsImpl.stock.product;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.price.BasePriceDto;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyDto;
import com.c2psi.businessmanagement.dtos.stock.price.SpecialPriceDto;
import com.c2psi.businessmanagement.dtos.stock.product.*;
import com.c2psi.businessmanagement.services.contracts.stock.price.BasePriceService;
import com.c2psi.businessmanagement.services.contracts.stock.price.SpecialPriceService;
import com.c2psi.businessmanagement.services.contracts.stock.product.*;
import org.junit.Assert;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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


}
