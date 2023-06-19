package com.c2psi.businessmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="category",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"catCode", "catPosId"})})
public class Category extends AbstractEntity implements Serializable {

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
    /*@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pos_id", nullable = false, referencedColumnName = "id")
    Pointofsale catPos;*/
    Long catPosId;
    @OneToMany(mappedBy = "prodCat", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Product> productList;

    //Each component has a parent which is a composite
    Long categoryParentId;




    /*********************************************************
     * Example of category according to that project
     * BIERRE, JUS, EAU, GAZ
     *      SUB CATEGORY
     *          SABC, UCB, BRAZAF, GUINNESS, SCTM
     ********************************************************/

    /**************************************************************************************************************
     * Pour qu'une categorie A soit parente d'une categorie B alors il faut que
     *  *si A a une categorie parente C alors
     *      *la categorie C ne doit pas etre dans la descendance de la categorie B car B sera desormais dans la
     *      descandance de A.
     *  ***La descandance d'une categorie: Cest
     *   ** ses fils direct; les fils de ses fils jusqu'a ce qu'aucun fils ne soit encore disponible
     ***************************************************************************************************************/


}
