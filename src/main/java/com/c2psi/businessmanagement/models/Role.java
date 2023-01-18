package com.c2psi.businessmanagement.models;


import com.c2psi.businessmanagement.Enumerations.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="role",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"roleName", "ent_id"})})
public class Role extends AbstractEntity {
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 20)
    @Column(nullable = false)
    RoleType roleName;
    String roleAlias;
    String roleDescription;

    /******************************
     * Relation between entities  *
     * ****************************/
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ent_id", nullable = false, referencedColumnName = "id")
    Enterprise roleEnt;
}
