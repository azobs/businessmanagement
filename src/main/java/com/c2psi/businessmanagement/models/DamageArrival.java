package com.c2psi.businessmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="damage_arrival")
public class DamageArrival extends AbstractEntity {

    @Column(nullable = false)
    Integer damaDeliveryquantity;

    @Column(nullable = false)
    Integer damaQuantityartchanged;
    /******************************
     * Relation between entities  *
     * ****************************/
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "art_id", nullable = false, referencedColumnName = "id")
    Article damaArt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sidam_id", referencedColumnName = "id")
    SupplyInvoiceDamage damaSidam;
}
