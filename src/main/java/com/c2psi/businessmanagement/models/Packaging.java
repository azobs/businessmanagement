package com.c2psi.businessmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="packaging",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"packLabel", "packFirstcolor", "provider_id", "pos_id"})})
public class Packaging extends AbstractEntity {

    @Column(nullable = false)
    String packLabel;
    String packDescription;

    String packFirstcolor;

    BigDecimal packPrice;

    /******************************
     * Relation between entities  *
     * ****************************/
    //Many packaging must be produce by one provider
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "provider_id", nullable = false, referencedColumnName = "id")
    Provider packProvider;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pos_id", nullable = false, referencedColumnName = "id")
    Pointofsale packPos;
}
