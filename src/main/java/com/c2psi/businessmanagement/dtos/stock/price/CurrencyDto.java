package com.c2psi.businessmanagement.dtos.stock.price;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.models.Currency;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class CurrencyDto {
    Long id;
    @NotNull
    @NotEmpty
    String currencyName;
    String currencyShortname;
    /******************************
     * Relation between entities  *
     * ****************************/

    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static CurrencyDto fromEntity(Currency c){
        if(c == null){
            return null;
        }
        return CurrencyDto.builder()
                .id(c.getId())
                .currencyName(c.getCurrencyName())
                .currencyShortname(c.getCurrencyShortname())
                .build();
    }
    public static  Currency toEntity(CurrencyDto c_dto){
        if(c_dto == null){
            return null;
        }
        Currency c = new Currency();
        c.setId(c_dto.getId());
        c.setCurrencyName(c_dto.getCurrencyName());
        c.setCurrencyShortname(c_dto.getCurrencyShortname());
        return c;
    }
}
