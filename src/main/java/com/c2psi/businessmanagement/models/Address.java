package com.c2psi.businessmanagement.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Address {
    String numtel1;
    String numtel2;
    String numtel3;
    String quartier;
    String pays;
    String ville;
    String localisation;
    @Column(nullable = false, unique = true)
    String email;
}
