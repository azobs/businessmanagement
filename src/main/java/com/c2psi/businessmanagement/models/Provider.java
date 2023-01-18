package com.c2psi.businessmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="provider",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"providerName", "pos_id"})})
public class Provider extends AbstractEntity {

    @Column(nullable = false)
    String providerName;

    @Column(nullable = false)
    String providerAcronym;
    String providerDescription;
    @Embedded
    Address providerAddress;

    /******************************
     * Relation between entities  *
     * ****************************/
    //Each provider produce a list of packaging
    @OneToMany(mappedBy = "packProvider", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Packaging> packagingList;
    //List of providercapsuleaccount applied to a provider one per pointofsale
    @OneToMany(mappedBy = "pcsaProvider", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<ProviderCapsuleAccount> providerCapsuleAccountList;
    //List of providerdamageaccount applied to a provider one per pointofsale
    @OneToMany(mappedBy = "pdaProvider", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<ProviderDamageAccount> providerDamageAccountList;
    //List of providerpackageaccount applied to a client one per pointofsale
    @OneToMany(mappedBy = "ppaProvider", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<ProviderPackagingAccount> providerPackagingAccountList;
    //List of clientcashaccount applied to a client one per pointofsale
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pca_id", nullable = false, referencedColumnName = "id")
    ProviderCashAccount providerCa;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pos_id", nullable = false, referencedColumnName = "id")
    Pointofsale providerPos;
}
