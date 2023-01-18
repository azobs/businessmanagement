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
@Table(name="client_cash_account")
public class ClientCashAccount extends AbstractEntity{
    /*****
     * cca_balance < 0 signifie que le client doit verser du cash
     * donc il a été payé sous reserve des capsules de l'article en question
     * cca_balance > 0 signifie que le point de vente a recu du cash du client
     * mais n'a pas encore livré les articles
     */

    BigDecimal ccaBalance;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Each clientcashaccount must belonging to one pointofsale
    /*@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pointofsale_id", nullable = false, referencedColumnName = "id")
    Pointofsale ccaPointofsale;*/

    //Each clientcapsuleaccount must belonging to one client
    /*@OneToOne(mappedBy = "cltCa")
    Client ccaClient;*/
}
