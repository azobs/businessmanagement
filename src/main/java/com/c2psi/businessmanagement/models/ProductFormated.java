package com.c2psi.businessmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="product_formated",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"product_id", "format_id"})})
public class ProductFormated extends AbstractEntity {
    String pfPicture;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Each productformated is associated to 1 Product
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false, referencedColumnName = "id")
    Product pfProduct;
    //Each productformated is associated to 1 format
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "format_id", nullable = false, referencedColumnName = "id")
    Format pfFormat;
}
