package com.c2psi.businessmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="format",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"formatName", "formatCapacity", "pos_id"})})
public class Format extends AbstractEntity {

    String formatName;

    BigDecimal formatCapacity;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Each format belongs to 1 pointofsale
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pos_id", nullable = false, referencedColumnName = "id")
    Pointofsale formatPos;

}
