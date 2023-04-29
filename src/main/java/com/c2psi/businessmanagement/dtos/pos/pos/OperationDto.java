package com.c2psi.businessmanagement.dtos.pos.pos;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.models.Operation;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.Instant;

@Data
@Builder
public class OperationDto {
    @NotNull(message = "The operation date cannot be null")
    @PastOrPresent(message = "The operation date cannot be in the future")
    Instant opDate;

    @NotNull(message = "The object of an operation cannot be null")
    @NotEmpty(message = "The object of an operation cannot be empty value")
    @NotBlank(message = "The object of an operation cannot be blank value")
    @Size(max = 75, message = "The Object of an operation can't exceed 75 characters")
    String opObject;
    String opDescription;
    @NotNull(message = "The operation type cannot be null")
    OperationType opType;
    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static OperationDto fromEntity(Operation op){
        if(op == null){
            return null;
        }
        return OperationDto.builder()
                .opDate(op.getOpDate())
                .opDescription(op.getOpDescription())
                .opObject(op.getOpObject())
                .opType(op.getOpType())
                .build();
    }
    public static Operation toEntity(OperationDto opDto){
        if(opDto == null){
            return null;
        }
        Operation op = new Operation();
        op.setOpDate(opDto.getOpDate());
        op.setOpObject(opDto.getOpDescription());
        op.setOpDescription(opDto.getOpDescription());
        op.setOpType(opDto.getOpType());
        return op;
    }
}
