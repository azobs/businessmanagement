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
@Table(name="special_price")
public class SpecialPrice extends AbstractEntity {
    BigDecimal spWholesaleprice;
    BigDecimal spDetailprice;
    BigDecimal spSemiwholesaleprice;
    BigDecimal spPrecompte;
    BigDecimal spRistourne;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many specialprice is based on 1 baseprice
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bp_id", nullable = false, referencedColumnName = "id")
    BasePrice spBaseprice;
}
