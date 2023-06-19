package com.c2psi.businessmanagement.models;

import com.c2psi.businessmanagement.Enumerations.DeliveryState;
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
@Table(name="delivery",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"deliveryCode", "deliveryPosId"})})
public class Delivery extends AbstractEntity {

    @Column(nullable = false)
    String deliveryCode;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false)
    Instant deliveryDate;

    /****
     * PackUp: La livraison est deja charge dans le moyen de livraison
     * OutForDelivery: La livraison est en cours de livraison. Le livreur s'est deja leve avec la livraison
     * Delivery: La livraison a ete effectue mais il y a une des commandes qu'elle contient dont le rapport
     * de livraison n'est pas encore donnee par le livreur. Par exemple on attend son versement.
     * Finish: Toutes les informations concernant toutes les commandes que comportait la livraison ont ete donne et
     * enregistre. Finish veut donc dire que la livraison est terminee
     */
    DeliveryState deliveryState;
    String deliveryComment;

    /******************************
     * Relation between entities  *
     * ****************************/
    /*@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pos_id", nullable = false, referencedColumnName = "id")
    Pointofsale deliveryPos;*/
    Long deliveryPosId;

    @OneToMany(mappedBy = "ddDelivery", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<DeliveryDetails> deliveryDetailsList;

    @OneToMany(mappedBy = "cmdDelivery", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Command> commandList;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userbm_id", nullable = false, referencedColumnName = "id")
    UserBM deliveryUserbm;
}
