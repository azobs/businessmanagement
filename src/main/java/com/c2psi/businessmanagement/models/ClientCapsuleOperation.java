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
@Table(name="client_capsule_operation")
public class ClientCapsuleOperation extends AbstractEntity {
    /*@DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(nullable = false)
    Instant cltcso_date;
    @NotNull
    @NotEmpty
    String cltcso_object;
    String cltcso_description;
    @NotNull
    OperationType cltcso_Type;*/
    @Embedded
    Operation cltcsoOperation;

    Integer cltcsoNumberinmvt;
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
