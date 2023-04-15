package com.c2psi.businessmanagement.dtos.stock.product;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.models.Format;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
@Builder
public class FormatDto {
    Long id;
    @NotNull(message = "The format name cannot be null")
    @NotEmpty(message = "The format name cannot be empty")
    @NotBlank(message = "The format name cannot be blank")
    @Size(min = 3, max = 100, message = "The format name size must be between 3 and 100 characters")
    String formatName;
    @NotNull(message = "The capacity cannot be null")
    @Positive(message = "The capacity must be positive")
    BigDecimal formatCapacity;
    /******************************
     * Relation between entities  *
     * ****************************/
    //Each format belongs to 1 pointofsale
    @NotNull(message = "The point of sale associated to the format cannot be null")
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
