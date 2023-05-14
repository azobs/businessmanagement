package com.c2psi.businessmanagement.models;

import com.c2psi.businessmanagement.Enumerations.CashArrivalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="cash_arrival", uniqueConstraints = {@UniqueConstraint(
        columnNames = {"art_id", "sicash_id"})})
public class CashArrival extends AbstractEntity {

    @Column(nullable = false)
    BigDecimal cashaDeliveryquantity;

    BigDecimal cashaUnitprice;

    CashArrivalType cashaArrivaltype;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false)
    Instant cashaArrivalEntryDate;

    /******************************
     * Relation between entities  *
     * ****************************/
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "art_id", nullable = false, referencedColumnName = "id")
    Article cashaArt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sicash_id", referencedColumnName = "id")
    SupplyInvoiceCash cashaSicash;

}
