package com.c2psi.businessmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="client",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"clientName", "clientOthername", "clientPosId"}),
                @UniqueConstraint(
                        columnNames = {"clientCni", "clientPosId"})})
public class Client extends AbstractEntity {

    @Column(nullable = false)
    String clientName;
    String clientOthername;
    String clientCni;
    @Embedded
    Address clientAddress;
    /******************************
     * Relation between entities  *
     * ****************************/
    //List of specialprice applied to a client
    @OneToMany(mappedBy = "cltSpClient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<ClientSpecialprice> clientSpecialpriceList;
    //List of clientcashaccount applied to a client one per pointofsale
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cca_id", nullable = false, referencedColumnName = "id")
    ClientCashAccount clientCa;
    //List of clientcapsuleaccount applied to a client one per pointofsale
    @OneToMany(mappedBy = "ccsaClient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<ClientCapsuleAccount> clientCapsuleAccountList;
    //List of clientcapsuleaccount applied to a client one per pointofsale
    @OneToMany(mappedBy = "cpaClient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<ClientPackagingAccount> clientPackagingAccountList;
    //List of clientdamageaccount applied to a client one per pointofsale and one per article
    @OneToMany(mappedBy = "cdaClient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<ClientDamageAccount> clientDamageAccountList;

    @OneToMany(mappedBy = "cmdClient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Command> commandList;

    //Each client belongs to 1 pointofsale
    /*@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pos_id", nullable = false, referencedColumnName = "id")
    Pointofsale clientPos;*/
    Long clientPosId;
}
