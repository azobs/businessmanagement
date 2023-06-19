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
@Table(name="divers")
public class Divers extends AbstractEntity{
    @Column(nullable = false)
    String diversName;
    @Embedded
    Address diversAddress;

    @OneToMany(mappedBy = "cmdDivers", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Command> commandList;

    /*@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pos_id", nullable = false, referencedColumnName = "id")
    Pointofsale diversPos;*/
    Long diversPosId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "dcashacc_id", nullable = false, referencedColumnName = "id")
    DiversCashAccount diversCa;
}
