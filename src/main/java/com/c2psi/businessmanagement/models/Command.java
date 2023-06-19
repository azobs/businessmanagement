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
                columnNames = {"cmdCode", "commandPosId"})})
public class Command extends AbstractEntity{

    @Column(nullable = false)
    String cmdCode;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false)
    Instant cmdDate;

    /***
     * Une command a la creation est en etat InEditing. A ce moment il est encore possible de la modifier sans aucune
     * condition
     * Lorsqu'une facture a deja ete imprime pour elle, elle passe a letat Edited. Jusqu'ici on peut la modifier sans
     * aucune condition aussi
     * Une fois que cette commande est associee a une livraison, elle passe a l'etat PackUp mais pour la modifier
     * il faut que:
     *    La livraison dans laquelle la commande se trouve n'est pas encore cloturee (ie pas encore a l'etat finish)
     */
    CommandState cmdState;//InEditing, Edited, PackupUp
    String cmdComment;

    CommandType cmdType;//Divers, Standard

    CommandStatus cmdStatus;//Cash, Capsule, Damage

    /******************************
     * Relation between entities  *
     * ****************************/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id",  referencedColumnName = "id")
    Delivery cmdDelivery;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loading_id", referencedColumnName = "id")
    Loading cmdLoading;

    /*@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pos_id", nullable = false, referencedColumnName = "id")
    Pointofsale commandPos;*/
    Long commandPosId;

    @OneToMany(mappedBy = "saleCommand", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Sale> saleList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    Client cmdClient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "divers_id", referencedColumnName = "id")
    Divers cmdDivers;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userbm_id", nullable = false, referencedColumnName = "id")
    UserBM cmdUserbm;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "saleicash_id",  referencedColumnName = "id")
    SaleInvoiceCash cmdSaleicash;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sicaps_id",  referencedColumnName = "id")
    SaleInvoiceCapsule cmdSaleicaps;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sidam_id",  referencedColumnName = "id")
    SaleInvoiceDamage cmdSaleidam;

}
