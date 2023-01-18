package com.c2psi.businessmanagement.validators.client.command;

import com.c2psi.businessmanagement.Enumerations.SaleType;
import com.c2psi.businessmanagement.dtos.client.command.SaleDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SaleValidator {
    /**************************************************************************************
     *  *Le parametre a valider ne peut etre null
     *  *La quantite (saleQuantity) doit etre strictement positive
     *  *Le prix final (saleFinalprice) doit etre strictement positif
     *  *Le type de vente (saleType) ne doit pas etre null et doit etre parmi ceux reconnu
     *  *La command associe (commandDto) ne doit pas etre null
     *  *L'article associe (saleArticleDto) ne doit pas etre null
     *  *L'article et la commande doivent etre dans le même point de vente
     * @param saleDto
     * @return
     */
    public static List<String> validate(SaleDto saleDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(saleDto).isPresent()){
            errors.add("--Le parametre a valider ne peut etre null: "+errors);
        }
        else{
            if(saleDto.getSaleQuantity().doubleValue()<0){
                errors.add("--La quantite ne peut etre inférieur a 0: "+errors);
            }
            if(saleDto.getSaleFinalprice().compareTo(BigDecimal.valueOf(0.0))<=0){
                errors.add("--Le prix final de vente ne saurait etre negatif ou null: "+errors);
            }
            if(!Optional.ofNullable(saleDto.getSaleType()).isPresent()){
                errors.add("--Le type de vente ne peut etre null: "+errors);
            }
            else{
                if(!saleDto.getSaleType().equals(SaleType.Details) &&
                !saleDto.getSaleType().equals(SaleType.Permutation) &&
                !saleDto.getSaleType().equals(SaleType.SemiWhole) &&
                !saleDto.getSaleType().equals(SaleType.Whole)){
                    errors.add("--Le type de vente n'est pas reconnu par le systeme: "+errors);
                }
            }
            if(!Optional.ofNullable(saleDto.getSaleCommandDto()).isPresent() ||
            !Optional.ofNullable(saleDto.getSaleArticleDto()).isPresent()){
                errors.add("--Ni la commande ni l'article ne doit etre null: "+errors);
            }
            /*else{
                PointofsaleDto cmdPosDto = Optional.ofNullable(
                        saleDto.getSaleCommandDto()).get().getCmdPosDto();
                PointofsaleDto artPosDto = Optional.ofNullable(
                    saleDto.getSaleArticleDto().getArtPosDto()).get();
                if(!cmdPosDto.getId().equals(artPosDto.getId())){
                    errors.add("--L'article vendu doit etre du meme point de vente que " +
                            "la commande dans laquelle se trouve la vente: "+errors);
                }
            }*/
        }
        return errors;
    }
}
