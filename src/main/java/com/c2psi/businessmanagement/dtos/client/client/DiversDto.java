package com.c2psi.businessmanagement.dtos.client.client;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.AddressDto;
import com.c2psi.businessmanagement.models.Divers;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
@Data
@Builder
public class DiversDto {
    Long id;
    @NotNull(message = "The divers name cannot be null")
    @NotEmpty(message = "The divers name cannot be empty")
    @NotBlank(message = "The divers name cannot be blank value")
    String diversName;

    AddressDto diversAddressDto;

    @NotNull(message = "The pointofsale cannot be null")
    //PointofsaleDto diversPosDto;
    Long diversPosId;

    @NotNull(message = "The Cash account of Divers cannot be null")
    DiversCashAccountDto diversCaDto;

    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static DiversDto fromEntity(Divers divers){
        if(divers == null){
            return null;
        }
        return DiversDto.builder()
                .id(divers.getId())
                .diversName(divers.getDiversName())
                .diversAddressDto(AddressDto.fromEntity(divers.getDiversAddress()))
                //.diversPosDto(PointofsaleDto.fromEntity(divers.getDiversPos()))
                .diversPosId(divers.getDiversPosId())
                .diversCaDto(DiversCashAccountDto.fromEntity(divers.getDiversCa()))
                .build();
    }

    public static Divers toEntity(DiversDto diversDto){
        if(diversDto == null){
            return null;
        }
        Divers divers = new Divers();
        divers.setId(diversDto.getId());
        divers.setDiversName(diversDto.getDiversName());
        divers.setDiversAddress(AddressDto.toEntity(diversDto.getDiversAddressDto()));
        divers.setDiversCa(DiversCashAccountDto.toEntity(diversDto.diversCaDto));
        //divers.setDiversPos(PointofsaleDto.toEntity(diversDto.getDiversPosDto()));
        divers.setDiversPosId(diversDto.getDiversPosId());

        return divers;
    }

}
