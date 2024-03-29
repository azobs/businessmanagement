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
@Table(name="client_capsule_operation")
public class ClientCapsuleOperation extends AbstractEntity {

    @Embedded
    Operation cltcsoOperation;

    BigDecimal cltcsoNumberinmvt;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many ClientCapsuleOperation for 1 ClientCapsuleAccount
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "clt_capsule_account_id", nullable = false, referencedColumnName = "id")
    ClientCapsuleAccount cltcsoCltCapsuleAccount;
    //Many ClientCapsuleOperation for 1 UserBM
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userbm_id", nullable = false, referencedColumnName = "id")
    UserBM cltcsoUserbm;
}
