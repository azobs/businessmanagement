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

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="sale_invoice_cash",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"saleicashCode", "pos_id"})})
public class SaleInvoiceCash extends AbstractEntity {

    @Column(nullable = false)
    String saleicashCode;

    BigDecimal saleicashAmountexpected;

    BigDecimal saleicashAmountpaid;

    BigDecimal saleicashAmountreimbourse;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(nullable = false)
    Instant saleicashDeliveryDate;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(nullable = false)
    Instant saleicashInvoicingDate;

    /******************************
     * Relation between entities  *
     * ****************************/
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pos_id", nullable = false, referencedColumnName = "id")
    Pointofsale saleicashPos;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false, referencedColumnName = "id")
    Client saleicashClient;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userbm_id", nullable = false, referencedColumnName = "id")
    UserBM saleicashUserbm;

}
