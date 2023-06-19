package com.c2psi.businessmanagement.dtos.pos.pos;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.models.Operation;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.Instant;

@Data
@Builder
public class OperationDto {
    @NotNull(message = "The operation date cannot be null")
    @PastOrPresent(message = "The operation date cannot be in the future")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "The date of the operation", name = "opDate", dataType = "Instant")
    Instant opDate;

    @NotNull(message = "The object of an operation cannot be null")
    @NotEmpty(message = "The object of an operation cannot be empty value")
    @NotBlank(message = "The object of an operation cannot be blank value")
    @Size(max = 75, message = "The Object of an operation can't exceed 75 characters")
    @ApiModelProperty(value = "The object of the operation", name = "opObject", dataType = "String")
    String opObject;
    @ApiModelProperty(value = "The description of the operation", name = "opDescription", dataType = "String")
    String opDescription;
    @NotNull(message = "The operation type cannot be null")
    @ApiModelProperty(value = "The type of the operation", name = "opType", dataType = "OperationType", allowableValues = "Credit;Withdrawal;Change")
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
