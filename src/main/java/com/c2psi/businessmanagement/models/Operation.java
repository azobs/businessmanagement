package com.c2psi.businessmanagement.models;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Operation {

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(nullable = false)
    Instant opDate;


    String opObject;
    String opDescription;

    @Column(nullable = false)
    OperationType opType;
}
