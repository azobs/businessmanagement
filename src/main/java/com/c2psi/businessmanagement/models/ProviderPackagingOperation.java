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
@Table(name="provider_packaging_operation")
public class ProviderPackagingOperation extends AbstractEntity {
    /*Instant propo_date;
    String propo_object;
    String propo_description;
    OperationType propo_Type;*/
    @Embedded
    Operation propoOperation;

    BigDecimal propoNumberinmvt;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many ClientpackagingOperation for 1 ClientPackagingAccount
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pro_packaging_account_id", nullable = false, referencedColumnName = "id")
    ProviderPackagingAccount propoProPackagingAccount;
    //Many ProviderPackagingOperation for 1 UserBM
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userbm_id", nullable = false, referencedColumnName = "id")
    UserBM propoUserbm;
}
