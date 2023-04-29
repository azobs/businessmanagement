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
@Table(name="pointofsale",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"posName", "ent_id"})})
public class Pointofsale extends AbstractEntity{

    @Column(nullable = false)
    String posName;

    String posAcronym;
    String posDescription;
    @Embedded
    Address posAddress;

    /******************************
     * Relation between entities  *
     * ****************************/
    //Each pointofsale must be related to a list of packagingaccount (one per packaging)
    @OneToMany(mappedBy = "ppaPointofsale", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<PosPackagingAccount> posPackagingAccountList;
    @OneToMany(mappedBy = "packPos", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Packaging> packagingList;
    //Each pointofsale must be related to a list of capsuleaccount (one per article)
    @OneToMany(mappedBy = "pcsaPointofsale", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<PosCapsuleAccount> posCapsuleAccountList;
    //Each pointofsale must be related to a list of damageaccount (one per article)
    @OneToMany(mappedBy = "pdaPointofsale", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<PosDamageAccount> posDamageAccountList;
    //Each pointofsale has a list of client
    @OneToMany(mappedBy = "clientPos", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Client> clientList;
    //Each pointofsale has a list of client
    @OneToMany(mappedBy = "providerPos", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Provider> providerList;
    //Each pointofsale has ea list of article
    @OneToMany(mappedBy = "artPos", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Article> articleList;
    //Each pointofsale must be related to exactly one cashaccount
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cashaccount_id", referencedColumnName = "id")
    PosCashAccount posCashaccount;
    //Many Pointofsale is associated to 1 Currency
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "currency_id", nullable = false, referencedColumnName = "id")
    Currency posCurrency;
    //Each pointofsale belongs to 1 enterprise
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ent_id", nullable = false, referencedColumnName = "id")
    Enterprise posEnterprise;


}
