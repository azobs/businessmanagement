package com.c2psi.businessmanagement.dtos.stock.product;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.models.Inventory;
import com.c2psi.businessmanagement.models.InventoryLine;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class InventoryDto {
    Long id;
    String invComment;
    @NotNull(message = "The inventory date cannot be null")
    @PastOrPresent(message = "The inventory date cannot be in the future")
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
    PointofsaleDto invPosDto;

    /*@JsonIgnore
    List<InventoryLineDto> inventoryLineDtoList;*/
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
                .invPosDto(PointofsaleDto.fromEntity(inv.getInvPos()))
                /*.inventoryLineDtoList(inv.getInventoryLineList() != null ?
                        inv.getInventoryLineList().stream()
                        .map(InventoryLineDto::fromEntity)
                        .collect(Collectors.toList()) : null)*/
                .build();
    }

    public static Inventory toEntity(InventoryDto invDto){
        if(invDto == null){
            return null;
        }
        Inventory inv = new Inventory();
        inv.setInvCode(invDto.getInvCode());
        inv.setInvComment(invDto.getInvComment());
        inv.setInvDate(invDto.getInvDate());
        inv.setInvPos(PointofsaleDto.toEntity(invDto.getInvPosDto()));
        /*inv.setInventoryLineList(invDto.getInventoryLineDtoList() != null ?
                invDto.getInventoryLineDtoList().stream()
                .map(InventoryLineDto::toEntity)
                .collect(Collectors.toList()) : null);*/
        return inv;
    }
}
