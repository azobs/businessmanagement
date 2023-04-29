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
@Table(name="provider_damage_operation")
public class ProviderDamageOperation extends AbstractEntity{
    /*Instant prodo_date;
    String prodo_object;
    String prodo_description;
    OperationType prodo_Type;*/
    @Embedded
    Operation prodoOperation;

    BigDecimal prodoNumberinmvt;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many ProviderDamageOperation for 1 ProviderDamageAccount
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pro_damage_account_id", nullable = false, referencedColumnName = "id")
    ProviderDamageAccount prodoProDamageAccount;
    //Many ProviderDamageOperation for 1 UserBM
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userbm_id", nullable = false, referencedColumnName = "id")
    UserBM prodoUserbm;
}
