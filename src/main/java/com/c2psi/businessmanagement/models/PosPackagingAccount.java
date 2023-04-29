package com.c2psi.businessmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="pos_packaging_account", uniqueConstraints = {@UniqueConstraint(
        columnNames = {"pointofsale_id", "packaging_id"})})
public class PosPackagingAccount extends AbstractEntity {

    BigDecimal ppaNumber;


    /******************************
     * Relation between entities  *
     * ****************************/
    //Each packagingaccount must belonging to one pointofsale
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pointofsale_id", nullable = false, referencedColumnName = "id")
    Pointofsale ppaPointofsale;

    //Each packagingaccount must be related to exactly one packaging
    @OneToOne
    @JoinColumn(name = "packaging_id", referencedColumnName = "id")
    Packaging ppaPackaging;
}
