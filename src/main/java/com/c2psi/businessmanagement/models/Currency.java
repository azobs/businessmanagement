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
@Table(name="currency",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"currencyName", "currencyShortname"})})
public class Currency extends AbstractEntity {

    String currencyName;
    String currencyShortname;
    /******************************
     * Relation between entities  *
     * ****************************/


}
