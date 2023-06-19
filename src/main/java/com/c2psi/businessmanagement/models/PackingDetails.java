package com.c2psi.businessmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="packing_details",uniqueConstraints = {@UniqueConstraint(
        columnNames = {"packaging_id", "loading_id"})})
public class PackingDetails extends AbstractEntity{

    BigDecimal pdNumberofpackageused;

    BigDecimal pdNumberofpackagereturn;

    /******************************
     * Relation between entities  *
     * ****************************/
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "packaging_id", nullable = false, referencedColumnName = "id")
    Packaging pdPackaging;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "loading_id", nullable = false, referencedColumnName = "id")
    Loading pdLoading;
}
