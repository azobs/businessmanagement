package com.c2psi.businessmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="sale_invoice_damage",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"saleidamCode", "pos_id"})})
public class SaleInvoiceDamage extends AbstractEntity{
    @Column(nullable = false)
    String saleidamCode;

    Integer saleidamNumbertochange;

    Integer saleidamNumberchanged;

    Integer saleidamTotalcolis;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(nullable = false)
    Instant saleidamDeliveryDate;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(nullable = false)
    Instant saleidamInvoicingDate;

    /******************************
     * Relation between entities  *
     * ****************************/
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pos_id", nullable = false, referencedColumnName = "id")
    Pointofsale saleidamPos;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false, referencedColumnName = "id")
    Client saleidamClient;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userbm_id", nullable = false, referencedColumnName = "id")
    UserBM saleidamUserbm;
}
