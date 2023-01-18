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
@Table(name="capsule_arrival")
public class CapsuleArrival extends AbstractEntity {

    @Column(nullable = false)
    Integer capsaDeliveryquantity;

    @Column(nullable = false)
    Integer capsaQuantitycapschanged;
    /******************************
     * Relation between entities  *
     * ****************************/
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "art_id", nullable = false, referencedColumnName = "id")
    Article capsaArt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sicaps_id", referencedColumnName = "id")
    SupplyInvoiceCapsule capsaSicaps;

}
