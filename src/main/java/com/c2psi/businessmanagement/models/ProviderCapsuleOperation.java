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
@Table(name="provider_capsule_operation")
public class ProviderCapsuleOperation extends AbstractEntity {
    /*Instant procso_date;
    String procso_object;
    String procso_description;
    OperationType procso_Type;*/
    @Embedded
    Operation proscoOperation;

    BigDecimal procsoNumberinmvt;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many ProviderCapsuleOperation for 1 ProviderCapsuleAccount
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pro_capsule_account_id", nullable = false, referencedColumnName = "id")
    ProviderCapsuleAccount procsoProCapsuleAccount;
    //Many ProviderCapsuleOperation for 1 UserBM
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userbm_id", nullable = false, referencedColumnName = "id")
    UserBM procsoUserbm;
}
