package com.c2psi.businessmanagement.models;

import com.c2psi.businessmanagement.Enumerations.SaleType;
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
@Table(name="sale")
public class Sale extends AbstractEntity {
    @NotNull
    @PositiveOrZero
    Double saleQuantity;
    String saleComment;
    BigDecimal saleFinalprice;
    @NotNull
    SaleType saleType;

    /******************************
     * Relation between entities  *
     * ****************************/
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "command_id", nullable = false, referencedColumnName = "id")
    Command saleCommand;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "art_id", nullable = false, referencedColumnName = "id")
    Article saleArticle;
}
