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
@Table(name="pos_capsule_operation")
public class PosCapsuleOperation extends AbstractEntity {
    /*Instant poscso_date;
    String poscso_object;
    String poscso_description;
    OperationType poscso_Type;*/
    @Embedded
    Operation poscsoOperation;

    BigDecimal poscsoNumberinmvt;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many PosCapsuleOperation for 1 PosCapsuleAccount
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pos_capsule_account_id", nullable = false, referencedColumnName = "id")
    PosCapsuleAccount poscsoPosCapsuleAccount;
    //Many PosCapsuleOperation for 1 UserBM
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userbm_id", nullable = false, referencedColumnName = "id")
    UserBM poscsoUserbm;
}
