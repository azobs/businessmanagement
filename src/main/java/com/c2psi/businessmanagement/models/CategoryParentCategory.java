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
@Table(name="category_parent_category",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"category_child_id", "category_parent_id"}), @UniqueConstraint(
                columnNames = {"category_parent_id", "category_child_id"})})
public class CategoryParentCategory extends AbstractEntity{

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_child_id", nullable = false, referencedColumnName = "id")
    Category childCategory;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_parent_id", nullable = false, referencedColumnName = "id")
    Category parentCategory;

}
