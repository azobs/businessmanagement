package com.c2psi.businessmanagement.validators.stock.price;

import com.c2psi.businessmanagement.dtos.client.delivery.DeliveryDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.price.BasePriceDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class BasePriceValidator {
    /***********************************************************************************
     * *Le parametre a valider ne peut etre null
     * *Le prix d'achat moyen (bpPurchaseprice) ne peut etre negatif
     * *Le prix de vente en gros (bpWholesaleprice) ne peut etre negatif
     * *le prix de vente en semi gros (bpSemiwholesaleprice) ne peut etre negatif
     * *Le prix de vente en details (bpDetailprice) ne peut etre negatif
     * *Le montant des precomptes (bpPrecompte) ne peut etre negatif
     * *Le montant des ristournes (bpRistourne) ne peut etre negatif
     * *La devise ou monnaie (bpCurrencyDto) associe au prix ne peut etre null
     * *L'article associe (bp_artDto) ne peut etre null
     * *L'article (bp_artDto) et la devise (bpCurrencyDto) doivent appartienir au
     * meme point de vente
     * @param bpDto
     * @return
     */
    public static List<String> validate(BasePriceDto bpDto){
        List<String> errors = new ArrayList<>();
        if(!Optional.ofNullable(bpDto).isPresent()){
            errors.add("--Le parametre a valider ne peut etre null--");
        }
        else{
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<BasePriceDto>> constraintViolations = validator.validate(bpDto);

            if (constraintViolations.size() > 0 ) {
                for (ConstraintViolation<BasePriceDto> contraintes : constraintViolations) {
                    errors.add(contraintes.getMessage());
                }
            }

            /****
             * Le prix de details doit etre superieur au prix de semi gros
             * qui lui meme doit etre superieur au prix de gros
             */
            if(Optional.ofNullable(bpDto.getBpDetailprice()).isPresent() &&
                    Optional.ofNullable(bpDto.getBpSemiwholesaleprice()).isPresent() &&
                    Optional.ofNullable(bpDto.getBpWholesaleprice()).isPresent()){
                if(bpDto.getBpDetailprice().compareTo(bpDto.getBpSemiwholesaleprice())<0){
                    errors.add("--Le prix de details ne saurait etre inferieur au prix de semi gros--");
                }
                if(bpDto.getBpSemiwholesaleprice().compareTo(bpDto.getBpWholesaleprice())<0){
                    errors.add("--Le prix de semi gros ne saurait etre inferieur au prix de gros--");
                }
                if(bpDto.getBpDetailprice().compareTo(bpDto.getBpWholesaleprice())<0){
                    errors.add("--Le prix de details ne saurait etre inferieur au prix de gros--");
                }
            }

        }
        return errors;
    }
}
