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
                columnNames = {"unitName", "pos_id"})})
public class Unit extends AbstractEntity {

    @Column(nullable = false)
    String unitName;
    String unitAbbreviation;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many unit for 1 Pointofsale
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pos_id", nullable = false, referencedColumnName = "id")
    Pointofsale unitPos;

}
