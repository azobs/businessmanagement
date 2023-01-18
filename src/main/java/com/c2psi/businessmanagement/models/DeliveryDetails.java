package com.c2psi.businessmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="delivery_details")
public class DeliveryDetails extends AbstractEntity {

    Integer ddNumberofpackageused;

    Integer ddNumberofpackagereturn;

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
