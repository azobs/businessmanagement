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
@Table(name="category",
        uniqueConstraints = {@UniqueConstraint(
            columnNames = {"catCode", "pos_id"})})
public class Category extends AbstractEntity {

    @Column(nullable = false)
    String catName;
    String catShortname;

    @Column(nullable = false)
    String catCode;
    String catDescription;
    /******************************
     * Relation between entities  *
     * ****************************/

    //Each category belongs to 1 pointofsale
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pos_id", nullable = false, referencedColumnName = "id")
    Pointofsale catPos;
    @OneToMany(mappedBy = "prodCat", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Product> productList;

    /*********************************************************
     * Example of category according to that project
     * BIERRE, JUS, EAU, GAZ
     *      SUB CATEGORY
     *          SABC, UCB, BRAZAF, GUINNESS, SCTM
     ********************************************************/


}
