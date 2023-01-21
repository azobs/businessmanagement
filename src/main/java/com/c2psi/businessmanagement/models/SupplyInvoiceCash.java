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
import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="supply_invoice_cash",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"sicashCode", "pos_id"})})
public class SupplyInvoiceCash extends AbstractEntity {

    @Column(nullable = false)
    String sicashCode;
    String sicashComment;
    String sicashPicture;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(nullable = false)
    Instant sicashDeliveryDate;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(nullable = false)
    Instant sicashInvoicingDate;

    BigDecimal sicashAmountexpected;

    BigDecimal sicashAmountpaid;

    Integer sicashTotalcolis;

    /******************************
     * Relation between entities  *
     * ****************************/
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "provider_id", nullable = false, referencedColumnName = "id")
    Provider sicashProvider;

    @OneToMany(mappedBy = "cashaSicash", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<CashArrival> cashArrivalList;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userbm_id", nullable = false, referencedColumnName = "id")
    UserBM sicashUserbm;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pos_id", nullable = false, referencedColumnName = "id")
    Pointofsale sicashPos;

}