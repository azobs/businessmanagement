package com.c2psi.businessmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="unit",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"unitName", "unitPosId"})})
public class Unit extends AbstractEntity {

    /****
     * This Unit represent what we call conditionnement: Bouteille, quart de casier, tier de casier,
     * Demi casier, trois quart de casier, casier, etc.
     */
    @Column(nullable = false)
    String unitName;
    String unitAbbreviation;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many unit for 1 Pointofsale
    /*@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pos_id", nullable = false, referencedColumnName = "id")
    Pointofsale unitPos;*/
    Long unitPosId;

}
