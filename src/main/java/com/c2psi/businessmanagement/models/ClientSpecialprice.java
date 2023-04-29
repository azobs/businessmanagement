package com.c2psi.businessmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="client_specialprice")
public class ClientSpecialprice extends AbstractEntity {
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false)
    Instant cltSpApplieddate;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Many clientspecialprice for 1 Client
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "clt_id", nullable = false, referencedColumnName = "id")
    Client cltSpClient;
    //Many Clientspecialprice for 1 Specialprice
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sp_id", nullable = false, referencedColumnName = "id")
    SpecialPrice cltSpSp;
    //One Clientspecialprice for 1 Article
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "art_id", nullable = false, referencedColumnName = "id")
    Article cltSpArt;
}

