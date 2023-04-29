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
@Table(name="client_damage_operation")
public class ClientDamageOperation extends AbstractEntity {
    /*@DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(nullable = false)
    Instant cltdo_date;
    @NotNull
    @NotEmpty
    String cltdo_object;
    String cltdo_description;
    @NotNull
    OperationType cltdo_Type;*/
    @Embedded
    Operation cltdoOperation;

    BigDecimal cltdoNumberinmvt;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many ClientDamageOperation for 1 ClientDamageAccount
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "clt_damage_account_id", nullable = false, referencedColumnName = "id")
    ClientDamageAccount cltdoCltDamageAccount;
    //Many ClientDamageOperation for 1 UserBM
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userbm_id", nullable = false, referencedColumnName = "id")
    UserBM cltdoUserbm;
}
