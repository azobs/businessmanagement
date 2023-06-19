package com.c2psi.businessmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="inventory",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"invCode", "invPosId"})})
public class Inventory extends AbstractEntity {
    String invComment;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false)
    Instant invDate;

    @Column(nullable = false)
    String invCode;

    /******************************
     * Relation between entities  *
     ******************************/
    /*@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pos_id", nullable = false, referencedColumnName = "id")
    Pointofsale invPos;*/
    Long invPosId;

    @OneToMany(mappedBy = "invlineInv", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<InventoryLine> inventoryLineList;
}
