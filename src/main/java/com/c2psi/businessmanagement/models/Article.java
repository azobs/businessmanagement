package com.c2psi.businessmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="article",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"artCode", "pos_id"})})
public class Article extends AbstractEntity {

    @Column(nullable = false)
    String artCode;

    @Column(nullable = false)
    String artName;

    @Column(nullable = false)
    String artShortname;
    String artComment;

    Integer artThreshold;

    Integer artLowLimitWholesale;

    Integer artLowLimitSemiWholesale;

    Integer artQuantityinstock;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many article is associated to 1 ProductFormated
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pf_id", nullable = false, referencedColumnName = "id")
    ProductFormated artPf;
    //Many article must be related to 1 unit
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "unit_id", nullable = false, referencedColumnName = "id")
    Unit artUnit;
    //Many baseprice is for 1 article
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bp_id", nullable = false, referencedColumnName = "id")
    BasePrice artBp;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pos_id", nullable = false, referencedColumnName = "id")
    Pointofsale artPos;

    @OneToMany(mappedBy = "invlineArt", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<InventoryLine> inventoryLineList;
}
