package com.c2psi.businessmanagement.models;

import com.c2psi.businessmanagement.Enumerations.LoadingState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="loading",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"loadCode", "pos_id"})})
public class Loading extends AbstractEntity{

    @Column(nullable = false)
    String loadCode;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(nullable = false)
    Instant loadDate;

    LoadingState loadState;

    BigDecimal loadTotalamountexpected;

    BigDecimal loadTotalamountpaid;
    //Where we can add an explanation of what happens during the saling session
    String loadSalereport;
    String loadComment;

    /******************************
     * Relation between entities  *
     * ****************************/
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pos_id", nullable = false, referencedColumnName = "id")
    Pointofsale loadPos;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userbm_manager_id", nullable = false, referencedColumnName = "id")
    UserBM loadUserbmManager;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userbm_saler_id", nullable = false, referencedColumnName = "id")
    UserBM loadUserbmSaler;

    @OneToMany(mappedBy = "ldLoading", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<LoadingDetails> loadingDetailsList;

    @OneToMany(mappedBy = "pdLoading", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<PackingDetails> packingDetailsList;
}
