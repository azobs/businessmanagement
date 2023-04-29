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
@Table(name="client_packaging_operation")
public class ClientPackagingOperation extends AbstractEntity {
    /*@DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(nullable = false)
    Instant cltpo_date;
    @NotNull
    @NotEmpty
    String cltpo_object;
    String cltpo_description;
    @NotNull
    OperationType cltpo_Type;*/
    @Embedded
    Operation cltpoOperation;
    BigDecimal cltpoNumberinmvt;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many ClientPackagingOperation for 1 ClientPackagingAccount
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "clt_packaging_account_id", nullable = false, referencedColumnName = "id")
    ClientPackagingAccount cltpoCltPackagingAccount;
    //Many ClientPackagingOperation for 1 UserBM
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userbm_id", nullable = false, referencedColumnName = "id")
    UserBM cltpoUserbm;
}
