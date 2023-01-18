package com.c2psi.businessmanagement.dtos.stock.product;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.models.Inventory;
import com.c2psi.businessmanagement.models.InventoryLine;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class InventoryDto {
    Long id;
    String invComment;
    @PastOrPresent
    Instant invDate;
    @NotNull
    @NotEmpty
    String invCode;

    /******************************
     * Relation between entities  *
     * ****************************/
    @NotNull
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
