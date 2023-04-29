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
@Table(name="provider_packaging_account", uniqueConstraints = {@UniqueConstraint(
        columnNames = {"provider_id", "packaging_id"})})
public class ProviderPackagingAccount extends AbstractEntity {

    BigDecimal ppaNumber;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Each providerpackagingaccount must belonging to one pointofsale
    /*@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pointofsale_id", nullable = false, referencedColumnName = "id")
    Pointofsale ppaPos;*/

    //Each providerpackagingaccount must belonging to one provider
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "provider_id", nullable = false, referencedColumnName = "id")
    Provider ppaProvider;

    //Each ProviderPackagingAccount is for 1 packaging
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "packaging_id", nullable = false, referencedColumnName = "id")
    Packaging ppaPackaging;
}
