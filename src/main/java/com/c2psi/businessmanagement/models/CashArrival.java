package com.c2psi.businessmanagement.models;

import com.c2psi.businessmanagement.Enumerations.CashArrivalType;
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
@Table(name="cash_arrival")
public class CashArrival extends AbstractEntity {

    @Column(nullable = false)
    Integer cashaDeliveryquantity;

    BigDecimal cashaUnitprice;

    CashArrivalType cashaArrivaltype;

    /******************************
     * Relation between entities  *
     * ****************************/
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "art_id", nullable = false, referencedColumnName = "id")
    Article cashaArt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sicash_id", nullable = false, referencedColumnName = "id")
    SupplyInvoiceCash cashaSicash;

    /*@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userbm_id", nullable = false, referencedColumnName = "id")
    UserBM casha_userbm;*/
}
