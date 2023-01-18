package com.c2psi.businessmanagement.dtos.stock.product;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.models.Format;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@Builder
public class FormatDto {
    Long id;
    @NotNull
    @NotEmpty
    String formatName;
    @NotNull
    @PositiveOrZero
    BigDecimal formatCapacity;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Each format belongs to 1 pointofsale
    @NotNull
    PointofsaleDto formatPosDto;
    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static FormatDto fromEntity(Format format){
        if(format == null){
            return null;
        }
        return FormatDto.builder()
                .id(format.getId())
                .formatName(format.getFormatName())
                .formatCapacity(format.getFormatCapacity())
                .formatPosDto(PointofsaleDto.fromEntity(format.getFormatPos()))
                .build();
    }
    public static Format toEntity(FormatDto formatDto){
        if(formatDto == null){
            return null;
        }
        Format f = new Format();
        f.setId(formatDto.getId());
        f.setFormatCapacity(formatDto.getFormatCapacity());
        f.setFormatName(formatDto.getFormatName());
        f.setFormatPos(PointofsaleDto.toEntity(formatDto.getFormatPosDto()));
        return f;
    }
}
