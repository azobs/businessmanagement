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
@Table(name="pos_damage_operation")
public class PosDamageOperation extends AbstractEntity {
    /*Instant posdo_date;
    String posdo_object;
    String posdo_description;
    OperationType posdo_Type;*/
    @Embedded
    Operation posdoOperation;

    Integer posdoNumberinmvt;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many PosDamageOperation for 1 PosDamageAccount
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pos_damage_account_id", nullable = false, referencedColumnName = "id")
    PosDamageAccount posdoPosDamageAccount;
    //Many PosDamageOperation for 1 UserBM
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userbm_id", nullable = false, referencedColumnName = "id")
    UserBM posdoUserbm;
}
