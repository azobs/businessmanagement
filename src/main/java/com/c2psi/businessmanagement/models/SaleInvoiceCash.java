package com.c2psi.businessmanagement.models;

import com.c2psi.businessmanagement.Enumerations.CashSourceType;
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
                columnNames = {"saleicashCode", "saleicashPosId"})})
public class SaleInvoiceCash extends AbstractEntity {

    @Column(nullable = false)
    String saleicashCode;

    BigDecimal saleicashAmountexpected;//The total amount after the calculation of debt and all due to all the last command

    BigDecimal saleicashCurrentAmountexpected;//The real amount of the current client command

    BigDecimal saleicashAmountpaid;//The real amount paid by the corresponding client

    BigDecimal saleicashAmountreimbourse;//The real amount reimbourse

    BigDecimal saleicashTotalcolis;

    CashSourceType saleicashSourceofcash;//If the money was paid by electronic money or direct cash

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false)
    Instant saleicashDeliveryDate;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false)
    Instant saleicashInvoicingDate;

    /******************************
     * Relation between entities  *
     * ****************************/
    /*@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pos_id", nullable = false, referencedColumnName = "id")
    Pointofsale saleicashPos;*/
    Long saleicashPosId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false, referencedColumnName = "id")
    Client saleicashClient;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userbm_id", nullable = false, referencedColumnName = "id")
    UserBM saleicashUserbm;

}
