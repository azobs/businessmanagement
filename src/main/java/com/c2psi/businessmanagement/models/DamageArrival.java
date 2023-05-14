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
@Table(name="damage_arrival")
public class DamageArrival extends AbstractEntity {

    @Column(nullable = false)
    BigDecimal damaDeliveryquantity;

    @Column(nullable = false)
    BigDecimal damaQuantityartchanged;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false)
    Instant damaArrivalEntryDate;

    /******************************
     * Relation between entities  *
     * ****************************/
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "art_id", nullable = false, referencedColumnName = "id")
    Article damaArt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sidam_id", referencedColumnName = "id")
    SupplyInvoiceDamage damaSidam;
}
