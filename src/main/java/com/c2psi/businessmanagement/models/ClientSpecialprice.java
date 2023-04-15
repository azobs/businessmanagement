package com.c2psi.businessmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="client_specialprice")
public class ClientSpecialprice extends AbstractEntity {
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(nullable = false)
    Date cltSpApplieddate;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many clientspecialprice for 1 Client
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "clt_id", nullable = false, referencedColumnName = "id")
    Client cltSpClient;
    //Many clientspecialprice for 1 Specialprice
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sp_id", nullable = false, referencedColumnName = "id")
    SpecialPrice cltSpSp;
    //One clientspecialprice for 1 Article
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "art_id", nullable = false, referencedColumnName = "id")
    Article cltSpArt;
}

