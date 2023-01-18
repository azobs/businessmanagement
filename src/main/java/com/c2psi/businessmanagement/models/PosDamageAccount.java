package com.c2psi.businessmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="pos_damage_account")
public class PosDamageAccount extends AbstractEntity{

    Integer pdaNumber;

    /******************************
     * Relation between entities  *
     * ****************************/
    //Each damageaccount must belonging to one pointofsale
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pointofsale_id", nullable = false, referencedColumnName = "id")
    Pointofsale pdaPointofsale;

    //Each PosDamageAccount is for 1 article but not all article must have an PosDamageAccount
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "art_id", nullable = false, referencedColumnName = "id")
    Article pdaArticle;
}
