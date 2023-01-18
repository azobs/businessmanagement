package com.c2psi.businessmanagement.models;

import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
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

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="sale_invoice_capsule",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"saleicapsCode", "pos_id"})})
public class SaleInvoiceCapsule extends AbstractEntity {

    @Column(nullable = false)
    String saleicapsCode;

    Integer saleicapsNumbertochange;

    Integer saleicapsNumberchanged;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(nullable = false)
    Instant saleicapsDeliveryDate;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(nullable = false)
    Instant saleicapsInvoicingDate;

    /******************************
     * Relation between entities  *
     * ****************************/
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pos_id", nullable = false, referencedColumnName = "id")
    Pointofsale saleicapsPos;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false, referencedColumnName = "id")
    Client saleicapsClient;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userbm_id", nullable = false, referencedColumnName = "id")
    UserBM saleicapsUserbm;
}
