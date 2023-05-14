package com.c2psi.businessmanagement.models;

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
@Table(name="capsule_arrival")
public class CapsuleArrival extends AbstractEntity {

    @Column(nullable = false)
    BigDecimal capsaDeliveryquantity;

    @Column(nullable = false)
    BigDecimal capsaQuantitycapschanged;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false)
    Instant capsaArrivalEntryDate;
    /******************************
     * Relation between entities  *
     * ****************************/
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "art_id", nullable = false, referencedColumnName = "id")
    Article capsaArt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sicaps_id", referencedColumnName = "id")
    SupplyInvoiceCapsule capsaSicaps;

}
