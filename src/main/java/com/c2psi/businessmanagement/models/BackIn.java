package com.c2psi.businessmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="backin")
public class BackIn extends AbstractEntity{

    Instant biDate;
    String biComment;

    /******************************
     * Relation between entities  *
     * ****************************/
    @OneToMany(mappedBy = "bidbi", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<BackInDetails> bidList;

    /*****************************************************************************
     * La date du BackIn doit etre ulterieure obligatoirement a la
     * date de la commande associe(la commande pour laquelle elle est enregistre)
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cmd_id", nullable = false, referencedColumnName = "id")
    Command biCommand;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userbm_id", nullable = false, referencedColumnName = "id")
    UserBM biUserbm;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pos_id", nullable = false, referencedColumnName = "id")
    Pointofsale biPos;

}
