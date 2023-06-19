package com.c2psi.businessmanagement.dtos.stock.product;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.models.Inventory;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.Instant;

@Data
@Builder
public class InventoryDto {
    Long id;
    String invComment;
    @NotNull(message = "The inventory date cannot be null")
    @PastOrPresent(message = "The inventory date cannot be in the future")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    Instant invDate;
    @NotNull(message = "The inventory code cannot be null")
    @NotEmpty(message = "The inventory code cannot be empty")
    @NotBlank(message = "The inventory code cannot be blank")
    @Size(min = 3, max = 20, message = "The inventory code size must be between 3 and 20 characters")
    String invCode;

    /******************************
     * Relation between entities  *
     * ****************************/
    @NotNull(message = "The point of sale associated with the inventory cannot be null")
    //PointofsaleDto invPosDto;
    Long invPosId;


    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static InventoryDto fromEntity(Inventory inv){
        if(inv == null){
            return null;
        }
        return InventoryDto.builder()
                .id(inv.getId())
                .invCode(inv.getInvCode())
                .invComment(inv.getInvComment())
                .invDate(inv.getInvDate())
                //.invPosDto(PointofsaleDto.fromEntity(inv.getInvPos()))
                .invPosId(inv.getInvPosId())
                .build();
    }

    public static Inventory toEntity(InventoryDto invDto){
        if(invDto == null){
            return null;
        }
        Inventory inv = new Inventory();
        inv.setId(invDto.getId());
        inv.setInvCode(invDto.getInvCode());
        inv.setInvComment(invDto.getInvComment());
        inv.setInvDate(invDto.getInvDate());
        //inv.setInvPos(PointofsaleDto.toEntity(invDto.getInvPosDto()));
        inv.setInvPosId(invDto.getInvPosId());
        return inv;
    }
}
