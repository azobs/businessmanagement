package com.c2psi.businessmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="currency_conversion",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"currency_source_id", "currency_destination_id"})})
public class CurrencyConversion extends AbstractEntity{

    BigDecimal conversionFactor;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many unitconversion for 1 unit source
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "currency_source_id", nullable = false, referencedColumnName = "id")
    Currency currencySource;
    //Many unitconversion for 1 unit destination
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "currency_destination_id", nullable = false, referencedColumnName = "id")
    Currency currencyDestination;
}
