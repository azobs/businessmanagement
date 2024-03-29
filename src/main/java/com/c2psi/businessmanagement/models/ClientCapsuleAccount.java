package com.c2psi.businessmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="client_capsule_account")
public class ClientCapsuleAccount extends AbstractEntity {

    BigDecimal ccsaNumber;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Each clientcapsuleaccount must belonging to one pointofsale
    /*@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pointofsale_id", nullable = false, referencedColumnName = "id")
    Pointofsale ccsaPointofsale;*/

    //Each clientcapsuleaccount must belonging to one client
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false, referencedColumnName = "id")
    Client ccsaClient;

    //Each ClientCapsuleAccount is for 1 article but not all article must have an ClientCapsuleAccount
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "art_id", nullable = false, referencedColumnName = "id")
    Article ccsaArticle;
}
