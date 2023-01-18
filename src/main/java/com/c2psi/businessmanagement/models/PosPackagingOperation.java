package com.c2psi.businessmanagement.models;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="pos_packaging_operation")
public class PosPackagingOperation extends AbstractEntity {
    /*Instant pospo_date;
    String pospo_object;
    String pospo_description;
    OperationType pospo_Type;*/
    @Embedded
    Operation pospoOperation;

    Integer pospoNumberinmvt;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many PosPackagingOperation for 1 PosPackagingAccount
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pos_packaging_account_id", nullable = false, referencedColumnName = "id")
    PosPackagingAccount pospoPosPackagingAccount;
    //Many PosPackagingOperation for 1 UserBM
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userbm_id", nullable = false, referencedColumnName = "id")
    UserBM pospoUserbm;
}
