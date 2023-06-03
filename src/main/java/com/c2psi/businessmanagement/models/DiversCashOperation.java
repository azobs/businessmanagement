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
@Table(name="divers_cash_operation")
public class DiversCashOperation extends AbstractEntity{
    @Embedded
    Operation dcoOperation;
    BigDecimal dcoAmountinmvt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "divers_cash_account_id", nullable = false, referencedColumnName = "id")
    DiversCashAccount dcoDiversCashAccount;
    //Many ClientCashOperation for 1 UserBM
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userbm_id", nullable = false, referencedColumnName = "id")
    UserBM dcoUserbm;
}
