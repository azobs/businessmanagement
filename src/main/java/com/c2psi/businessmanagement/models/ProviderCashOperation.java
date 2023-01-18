package com.c2psi.businessmanagement.models;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="provider_cash_operation")
public class ProviderCashOperation extends AbstractEntity {
    /*Instant pco_date;
    String pco_object;
    String pco_description;
    OperationType pco_Type;*/
    @Embedded
    Operation pcoOperation;
    @NotNull
    @PositiveOrZero
    BigDecimal pcoAmountinmvt;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many ProvidercashOperation for 1 ProvidercashAccount
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pro_cash_account_id", nullable = false, referencedColumnName = "id")
    ProviderCashAccount pcoProCashAccount;
    //Many ProviderCashOperation for 1 UserBM
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userbm_id", nullable = false, referencedColumnName = "id")
    UserBM pcoUserbm;
}
