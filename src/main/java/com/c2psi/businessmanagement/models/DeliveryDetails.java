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
@Table(name="delivery_details",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"packaging_id", "delivery_id"})})
public class DeliveryDetails extends AbstractEntity {

    BigDecimal ddNumberofpackageused;

    BigDecimal ddNumberofpackagereturn;

    /******************************
     * Relation between entities  *
     * ****************************/
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "packaging_id", nullable = false, referencedColumnName = "id")
    Packaging ddPackaging;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "delivery_id", nullable = false, referencedColumnName = "id")
    Delivery ddDelivery;
}
