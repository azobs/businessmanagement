package com.c2psi.businessmanagement.models;

import com.c2psi.businessmanagement.Enumerations.CommandState;
import com.c2psi.businessmanagement.Enumerations.CommandStatus;
import com.c2psi.businessmanagement.Enumerations.CommandType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="command",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"cmdCode", "pos_id"})})
public class Command extends AbstractEntity{

    @Column(nullable = false)
    String cmdCode;
    @DateTimeFormat(pattern="yyyy-MM-dd")

    @Column(nullable = false)
    Instant cmdDate;

    CommandState cmdState;
    String cmdComment;

    CommandType cmdType;

    CommandStatus cmdStatus;

    /******************************
     * Relation between entities  *
     * ****************************/
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "delivery_id", nullable = false, referencedColumnName = "id")
    Delivery cmdDelivery;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pos_id", nullable = false, referencedColumnName = "id")
    Pointofsale commandPos;

    @OneToMany(mappedBy = "saleCommand", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Sale> saleList;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false, referencedColumnName = "id")
    Client cmdClient;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userbm_id", nullable = false, referencedColumnName = "id")
    UserBM cmdUserbm;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "saleicash_id",  referencedColumnName = "id")
    SaleInvoiceCash cmdSaleicash;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sicaps_id",  referencedColumnName = "id")
    SaleInvoiceCapsule cmdSicaps;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sidam_id",  referencedColumnName = "id")
    SaleInvoiceDamage cmdSidam;

}
