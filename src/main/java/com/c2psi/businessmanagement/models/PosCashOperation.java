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
@Table(name="pos_cash_operation")
public class PosCashOperation extends AbstractEntity {
    /*Instant posco_date;
    String posco_object;
    String posco_description;
    OperationType posco_Type;*/
    @Embedded
    Operation poscoOperation;

    BigDecimal poscoAmountinmvt;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many PosCashOperation for 1 PosCashAccount
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pos_cash_account_id", nullable = false, referencedColumnName = "id")
    PosCashAccount poscoPosCashAccount;
    //Many PosCashOperation for 1 UserBM
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userbm_id", nullable = false, referencedColumnName = "id")
    UserBM poscoUserbm;
}
