package com.c2psi.businessmanagement.validators.stock.product;

import com.c2psi.businessmanagement.Enumerations.CashArrivalType;
import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import com.c2psi.businessmanagement.dtos.stock.product.CashArrivalDto;
import com.c2psi.businessmanagement.dtos.stock.product.SupplyInvoiceCashDto;
import com.c2psi.businessmanagement.dtos.stock.product.UnitDto;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CashArrivalValidatorTest {

    @Test
    public void validate() {
        CashArrivalDto cashArrivalDto = CashArrivalDto.builder()
                .cashaArtDto(ArticleDto.builder().build())
                .cashaSicashDto(null)
                .cashaArrivaltype(CashArrivalType.Divers)
                .cashaUnitprice(BigDecimal.valueOf(100))
                .cashaDeliveryquantity(Integer.valueOf(10))
                .build();

        List<String> errors = CashArrivalValidator.validate(cashArrivalDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
    }

    @Test
    public void validateNull() {
        CashArrivalDto cashArrivalDto = CashArrivalDto.builder()
                .cashaArtDto(ArticleDto.builder().build())
                .cashaSicashDto(null)
                .cashaArrivaltype(CashArrivalType.Divers)
                .cashaUnitprice(BigDecimal.valueOf(100))
                .cashaDeliveryquantity(Integer.valueOf(10))
                .build();

        List<String> errors = CashArrivalValidator.validate(null);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--Le parametre a valider ne peut etre null--"));
    }

    @Test
    public void validateNullValue() {
        CashArrivalDto cashArrivalDto = CashArrivalDto.builder()
                .cashaArtDto(null)
                .cashaSicashDto(null)
                .cashaArrivaltype(null)
                .cashaUnitprice(null)
                .cashaDeliveryquantity(null)
                .build();

        List<String> errors = CashArrivalValidator.validate(cashArrivalDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(4, errors.size());
        assertTrue(errors.contains("The delivery quantity cannot be null"));
        assertTrue(errors.contains("The final unit price cannot be null"));
        assertTrue(errors.contains("The cash arrival type cannot be null"));
        assertTrue(errors.contains("The article associated with the cash arrival cannot be null"));
    }

    @Test
    public void validatePositiveValue() {
        CashArrivalDto cashArrivalDto = CashArrivalDto.builder()
                .cashaArtDto(ArticleDto.builder().build())
                .cashaSicashDto(null)
                .cashaArrivaltype(CashArrivalType.Divers)
                .cashaUnitprice(BigDecimal.valueOf(0))
                .cashaDeliveryquantity(Integer.valueOf(0))
                .build();

        List<String> errors = CashArrivalValidator.validate(cashArrivalDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertTrue(errors.contains("The delivery quantity must be positive"));
        assertTrue(errors.contains("The final unit price must be positive"));
    }

    @Test
    public void validateFactureStandardValue() {
        CashArrivalDto cashArrivalDto = CashArrivalDto.builder()
                .cashaArtDto(ArticleDto.builder().build())
                .cashaSicashDto(null)
                .cashaArrivaltype(CashArrivalType.Standard)
                .cashaUnitprice(BigDecimal.valueOf(10))
                .cashaDeliveryquantity(Integer.valueOf(10))
                .build();

        List<String> errors = CashArrivalValidator.validate(cashArrivalDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.contains("--La facture associe a l'arrivage ne peut etre null--"));
    }

    @Test
    public void validateFactureDiversValue() {
        CashArrivalDto cashArrivalDto = CashArrivalDto.builder()
                .cashaArtDto(ArticleDto.builder().build())
                .cashaSicashDto(SupplyInvoiceCashDto.builder().build())
                .cashaArrivaltype(CashArrivalType.Standard)
                .cashaUnitprice(BigDecimal.valueOf(10))
                .cashaDeliveryquantity(Integer.valueOf(10))
                .build();

        List<String> errors = CashArrivalValidator.validate(cashArrivalDto);
        System.out.println("errors == " + errors);
        assertNotNull(errors);
        assertEquals(0, errors.size());
    }


}