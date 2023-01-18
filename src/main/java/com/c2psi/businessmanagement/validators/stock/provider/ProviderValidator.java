package com.c2psi.businessmanagement.validators.stock.provider;

import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;
import com.c2psi.businessmanagement.validators.pos.userbm.AddressValidator;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProviderValidator {
    /********************************************************************************
     * *Le parametre a valider ne saurait etre null
     * *Le nom du provider (providerName) et son acronym (providerAcronym) ne peuveut
     * etre ni null ni vide
     * *L'adresse doit etre valide
     * *Le compte cash (providerCaDto) associe ne peut etre null
     * *Le point de vente associe au provider (providerPosDto) ne peut etre null
     * @param providerDto
     * @return
     */
    public static List<String> validate(ProviderDto providerDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(providerDto).isPresent()){
            errors.add("--le parametre ProviderDto a valider ne peut etre null: "+errors);
        }
        else{
            if(!Optional.ofNullable(providerDto.getProviderCaDto()).isPresent()){
                errors.add("--Le compte cash associe au provider ne peut etre null: "+errors);
            }
            if(!Optional.ofNullable(providerDto.getProviderPosDto()).isPresent()){
                errors.add("--Le point de vente associe au provider ne peut etre null: "+errors);
            }
            if(!StringUtils.hasLength(providerDto.getProviderName())){
                errors.add("--Le nom du provider ne doit pas etre vide: "+errors);
            }
            if(!StringUtils.hasLength(providerDto.getProviderAcronym())){
                errors.add("--L'acronym du provider ne doit pas etre vide: "+errors);
            }
            if(providerDto.getProviderAcronym().length()>providerDto.getProviderName().length()){
                errors.add("--L'acronym du provider ne peut etre plus long que son nom: "+errors);
            }
            List<String> adr_errors = AddressValidator.validate(
                    providerDto.getProviderAddressDto());
            if(adr_errors.size()>0){
                errors.addAll(adr_errors);
            }
        }
        return errors;
    }
}
