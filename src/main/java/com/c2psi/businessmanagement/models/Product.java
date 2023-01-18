package com.c2psi.businessmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="product",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"prodName", "pos_id"})})
public class Product extends AbstractEntity {

    @Column(nullable = false)
    String prodName;
    String prodDescription;
    String prodAlias;
    Boolean prodPerishable;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many Product for one Category
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cat_id", nullable = false, referencedColumnName = "id")
    Category prodCat;//sabc, ucb, guiness, biere, jus, eau, gaz

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pos_id", nullable = false, referencedColumnName = "id")
    Pointofsale prodPos;
}
