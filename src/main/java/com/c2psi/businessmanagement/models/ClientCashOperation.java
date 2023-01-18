package com.c2psi.businessmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="client_cash_operation")
public class ClientCashOperation extends AbstractEntity {
    /*@DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(nullable = false)
    Instant cco_date;
    @NotNull
    @NotEmpty
    String cco_object;
    String cco_description;
    @NotNull
    OperationType cco_Type;*/
    @Embedded
    Operation ccoOperation;
    BigDecimal ccoAmountinmvt;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many ClientcashOperation for 1 ClientcashAccount
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "clt_cash_account_id", nullable = false, referencedColumnName = "id")
    ClientCashAccount ccoCltCashAccount;
    //Many ClientCashOperation for 1 UserBM
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userbm_id", nullable = false, referencedColumnName = "id")
    UserBM ccoUserbm;
}
