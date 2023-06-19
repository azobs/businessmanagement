package com.c2psi.businessmanagement.models;

import com.c2psi.businessmanagement.Enumerations.UserBMState;
import com.c2psi.businessmanagement.Enumerations.UserBMType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="userbm",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"bmName", "bmSurname",
                        "bmDob"})})
public class UserBM extends AbstractEntity {

    @Column(nullable = false, unique = true)
    String bmLogin;

    @Column(nullable = false)
    String bmPassword;

    @Column(nullable = false)
    String bmName;

    String bmSurname;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(nullable = false)
    //@Temporal(TemporalType.DATE)
    Date bmDob;

    @Column(unique = true)
    String bmCni;
    String bmPicture;

    UserBMState bmState;

    UserBMType bmUsertype;//AdminBM, AdminEnterprise, Employe

    @Embedded
    Address bmAddress;

    /******************************
     * Relation between entities  *
     * ****************************/
    @OneToMany(mappedBy = "userbmroleUserbm", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<UserBMRole> userBMRoleList;
    //Some UserBM must work for one Pointofsale but that Pointofsale must belong to the Enterprise
    //An user can be added without precised the pointofsale and that user user must be an admin
    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pos_id", referencedColumnName = "id")
    Pointofsale bmPos;*/
    Long bmPosId;
}
