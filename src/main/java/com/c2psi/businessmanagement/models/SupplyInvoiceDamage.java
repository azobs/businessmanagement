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
import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="supply_invoice_damage",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"sidamCode", "pos_id"})})
public class SupplyInvoiceDamage extends AbstractEntity {

    @Column(nullable = false)
    String sidamCode;
    String sidamComment;
    String sidamPicture;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(nullable = false)
    Instant sidamDeliveryDate;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(nullable = false)
    Instant sidamInvoicingDate;

    Integer sidamTotalcolis;
    Integer sidamTotalDamToChange;
    Integer sidamTotalDamChange;
    /******************************
     * Relation between entities  *
     * ****************************/
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "provider_id", nullable = false, referencedColumnName = "id")
    Provider sidamProvider;

    //List of specialprice applied to a client
    @OneToMany(mappedBy = "damaSidam", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<DamageArrival> damageArrivalList;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userbm_id", nullable = false, referencedColumnName = "id")
    UserBM sidamUserbm;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pos_id", nullable = false, referencedColumnName = "id")
    Pointofsale sidamPos;
}
