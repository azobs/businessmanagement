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
@Table(name="backin",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"biCode"})})
public class BackIn extends AbstractEntity{

    String biCode;
    Instant biDate;
    String biComment;

    /******************************
     * Relation between entities  *
     * ****************************/
    @OneToMany(mappedBy = "bidbi", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<BackInDetails> bidList;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cmd_id", nullable = false, referencedColumnName = "id")
    Command biCommand;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userbm_id", nullable = false, referencedColumnName = "id")
    UserBM biUserbm;

}
