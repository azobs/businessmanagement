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
@Table(name="provider_cash_account")
public class ProviderCashAccount extends AbstractEntity{
    @NotNull
    BigDecimal pcaBalance;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Each providercashaccount must belonging to one pointofsale
    /*@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pointofsale_id", nullable = false, referencedColumnName = "id")
    Pointofsale pcaPointofsale;*/

    //Each providercapsuleaccount must belonging to one provider
    /*@OneToOne(mappedBy = "providerCa")
    Provider pcaProvider;*/
}
