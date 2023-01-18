package com.c2psi.businessmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="client_packaging_account")
public class ClientPackagingAccount extends AbstractEntity {

    Integer cpaNumber;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Each clientcapsuleaccount must belonging to one pointofsale
    /*@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pointofsale_id", nullable = false, referencedColumnName = "id")
    Pointofsale cpaPointofsale;*/

    //Each clientcapsuleaccount must belonging to one client
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false, referencedColumnName = "id")
    Client cpaClient;

    //Each ClientPackagingAccount is for 1 packaging
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "packaging_id", nullable = false, referencedColumnName = "id")
    Packaging cpaPackaging;
}
