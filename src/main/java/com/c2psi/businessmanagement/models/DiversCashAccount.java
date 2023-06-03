package com.c2psi.businessmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="divers_cash_account")
public class DiversCashAccount extends AbstractEntity{
    BigDecimal dcaBalance;

    @OneToMany(mappedBy = "diversCa", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Divers> diversList;
}
