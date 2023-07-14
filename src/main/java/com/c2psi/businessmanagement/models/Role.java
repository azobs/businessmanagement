package com.c2psi.businessmanagement.models;


import com.c2psi.businessmanagement.Enumerations.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="role")
public class Role extends AbstractEntity {

    @Column(unique = true)
    RoleType roleName;
    String roleAlias;
    String roleDescription;

    /******************************
     * Relation between entities  *
     * ****************************/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ent_id", referencedColumnName = "id")
    Enterprise roleEnt;
}
