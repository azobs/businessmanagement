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
@Table(name="pos_capsule_account", uniqueConstraints = {@UniqueConstraint(
        columnNames = {"pointofsale_id", "art_id"})})
public class PosCapsuleAccount extends AbstractEntity{

    BigDecimal pcsaNumber;

    /******************************
     * Relation between entities  *
     * ****************************/
    //Each capsuleaccount must belonging to one pointofsale
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pointofsale_id", nullable = false, referencedColumnName = "id")
    Pointofsale pcsaPointofsale;

    //Each PosCapsuleAccount is for 1 article but not all article must have an PosCapsuleAccount
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "art_id", nullable = false, referencedColumnName = "id")
    Article pcsaArticle;
}
