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
@Table(name="inventory_line", uniqueConstraints = {@UniqueConstraint(
        columnNames = {"inv_id", "art_id"})})
public class InventoryLine extends AbstractEntity{
    String invlineComment;

    BigDecimal invlineRealqteinstock;

    BigDecimal invlineLogicqteinstock;

    /******************************
     * Relation between entities  *
     * ****************************/
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "inv_id", nullable = false, referencedColumnName = "id")
    Inventory invlineInv;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "art_id", nullable = false, referencedColumnName = "id")
    Article invlineArt;
}
