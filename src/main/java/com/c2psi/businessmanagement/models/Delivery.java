package com.c2psi.businessmanagement.models;

import com.c2psi.businessmanagement.Enumerations.DeliveryState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="delivery",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"deliveryCode", "pos_id"})})
public class Delivery extends AbstractEntity {

    @Column(nullable = false)
    String deliveryCode;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(nullable = false)
    Instant deliveryDate;

    DeliveryState deliveryState;
    String deliveryComment;

    /******************************
     * Relation between entities  *
     * ****************************/
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pos_id", nullable = false, referencedColumnName = "id")
    Pointofsale deliveryPos;

    @OneToMany(mappedBy = "ddDelivery", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<DeliveryDetails> deliveryDetailsList;

    @OneToMany(mappedBy = "cmdDelivery", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Command> commandList;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userbm_id", nullable = false, referencedColumnName = "id")
    UserBM deliveryUserbm;
}
