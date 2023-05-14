package com.c2psi.businessmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="supply_invoice_capsule",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"sicapsCode", "pos_id"})})
public class SupplyInvoiceCapsule extends AbstractEntity {

    @Column(nullable = false)
    String sicapsCode;
    String sicapsComment;
    String sicapsPicture;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false)
    Instant sicapsDeliveryDate;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false)
    Instant sicapsInvoicingDate;

    BigDecimal sicapsTotalcolis;
    BigDecimal sicapsTotalCapsToChange;
    BigDecimal sicapsTotalCapsChange;
    /******************************
     * Relation between entities  *
     * ****************************/
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "provider_id", nullable = false, referencedColumnName = "id")
    Provider sicapsProvider;

    //List of specialprice applied to a client
    @OneToMany(mappedBy = "capsaSicaps", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<CapsuleArrival> capsuleArrivalList;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userbm_id", nullable = false, referencedColumnName = "id")
    UserBM sicapsUserbm;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pos_id", nullable = false, referencedColumnName = "id")
    Pointofsale sicapsPos;
}
