package com.c2psi.businessmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="pos_operation")
public class PosOperation extends AbstractEntity{
    /**
     *Toutes les operations realisees sur un point de vente ou alors concernant un point de vente
     * doivent etre listees. Chaque operation a un niveau de criticite permettant de selectionner
     * le type d'utilisateur pouvant le voir.
     *
     * Ainsi lorsqu'un userbm d'un point de vente se connecte, toutes les operations qu'il a realise dans
     * la journee s'affiche avec possibilite de chager les operations qu'il a realise un jour quelconque
     */
    @Embedded
    Operation posopOperation;
    //Many PosOperation for 1 UserBM
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userbm_id", nullable = false, referencedColumnName = "id")
    UserBM posopUserbm;
}
