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
@Table(name="base_price")
public class BasePrice extends AbstractEntity {

    BigDecimal bpPurchaseprice;

    BigDecimal bpWholesaleprice;

    BigDecimal bpDetailprice;

    BigDecimal bpSemiwholesaleprice;

    BigDecimal bpPrecompte;

    BigDecimal bpRistourne;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many baseprice is associated to 1 currency
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "currency_id", nullable = false, referencedColumnName = "id")
    Currency bpCurrency;

    /*@OneToOne(mappedBy = "art_bp")
    Article bp_art;*/

}
