package com.c2psi.businessmanagement.dtos.stock.product;

import com.c2psi.businessmanagement.models.AAAA;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@ApiModel
public class AAAADto {
    @ApiModelProperty(value = "The id of the article", name = "id", dataType = "Long")
    Long id;
    @ApiModelProperty(value = "The id of the article", name = "id", dataType = "BigDecimal")
    BigDecimal bg;

    public static AAAADto fromEntity(AAAA a){
        if(a == null){
            return null;
        }
        return AAAADto.builder()
                .id(a.getId())
                .bg(a.getBg())
                .build();
    }

    public static AAAA toEntity(AAAADto articleDto){
        if(articleDto == null){
            return null;
        }
        AAAA art = new AAAA();
        art.setId(articleDto.getId());
        art.setBg(articleDto.getBg());

        return art;
    }
}
