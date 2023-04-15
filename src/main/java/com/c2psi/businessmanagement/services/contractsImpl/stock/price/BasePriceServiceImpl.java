package com.c2psi.businessmanagement.services.contractsImpl.stock.price;

import com.c2psi.businessmanagement.dtos.stock.price.BasePriceDto;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.BasePrice;
import com.c2psi.businessmanagement.models.Currency;
import com.c2psi.businessmanagement.repositories.stock.price.BasePriceRepository;
import com.c2psi.businessmanagement.repositories.stock.price.CurrencyRepository;
import com.c2psi.businessmanagement.services.contracts.stock.price.BasePriceService;
import com.c2psi.businessmanagement.validators.stock.price.BasePriceValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service(value="BasePriceServiceV1")
@Slf4j
@Transactional
public class BasePriceServiceImpl implements BasePriceService {
    private CurrencyRepository currencyRepository;
    private BasePriceRepository bpRepository;

    @Autowired
    public BasePriceServiceImpl(CurrencyRepository currencyRepository, BasePriceRepository bpRepository) {
        this.currencyRepository = currencyRepository;
        this.bpRepository = bpRepository;
    }

    @Override
    public BasePriceDto saveBasePrice(BasePriceDto basePriceDto) {
        /**********************************************************
         * We must first validate the entity dto pass as argument *
         **********************************************************/
        List<String> errors = BasePriceValidator.validate(basePriceDto);
        if(!errors.isEmpty()){
            log.error("Entity basePriceDto not valid {}", basePriceDto);
            throw new InvalidEntityException("Le basePriceDto passé en argument n'est pas valide: ",
                    ErrorCode.BASEPRICE_NOT_VALID, errors);
        }
        /****************************************
         * Verify the existence of the currency *
         ****************************************/
        Optional<Currency> optionalCurrency = currencyRepository.findCurrencyById(basePriceDto.getBpCurrencyDto().getId());
        if(!optionalCurrency.isPresent()){
            log.error("The currency precise for baseprice {} is not yet register iun the system ", basePriceDto);
            throw new EntityNotFoundException("Le currency precise dans l'object Baseprice n'existe pas en BD "
                    +basePriceDto, ErrorCode.BASEPRICE_NOT_VALID);
        }
        //Si on est ici cela signifie que tout existe meme le currency associe au baseprice
        log.info("After all verification the basepricedto {} can be register in the DB without any problem ", basePriceDto);
        return BasePriceDto.fromEntity(
                bpRepository.save(
                        BasePriceDto.toEntity(basePriceDto)
                )
        );
    }

    @Override
    public BasePriceDto updateBasePrice(BasePriceDto basePriceDto) {
        /**********************************************************
         * We must first validate the entity dto pass as argument *
         **********************************************************/
        List<String> errors = BasePriceValidator.validate(basePriceDto);
        if(!errors.isEmpty()){
            log.error("Entity basePriceDto not valid {}", basePriceDto);
            throw new InvalidEntityException("Le basePriceDto passé en argument n'est pas valide: ",
                    ErrorCode.BASEPRICE_NOT_VALID, errors);
        }
        /***********************************************************************************
         * Il faut se rassurer que l'id du basepricedto est bel et bien precise puisque le
         * validator ne verifie ps ca
         */
        if(basePriceDto.getId() == null){
            log.error("The id of the baseprice to modify does'nt exist in the DB");
            throw new InvalidEntityException("Le baseprice a modifier ne peut etre identifier puisque son id est null ",
                    ErrorCode.BASEPRICE_NOT_VALID);
        }
        /************************************************************************************
         * Il faut se rassurer que le baseprice a modifier existe vraiment dans le systeme
         */
        Optional<BasePrice> optionalBasePrice = bpRepository.findBasePriceById(basePriceDto.getId());
        if(!optionalBasePrice.isPresent()){
            log.error("The id precise in the {} does not identify any baseprice in the DB ", basePriceDto);
            throw  new EntityNotFoundException("Le baseprice indique pour etre modifie est inexistant dans la BD ",
                    ErrorCode.BASEPRICE_NOT_FOUND);
        }
        /*********************************************
         * Il faut recuperer le baseprice a modifier
         */
        BasePrice basePriceToUpdate = optionalBasePrice.get();
        /***********************************************************************
         * On verifie si ce n'est pas le currency quon veut changer
         */
        if(!basePriceDto.getBpCurrencyDto().getId().equals(basePriceToUpdate.getBpCurrency().getId())){
            //sil sont different ca veut dire quon veut juste modifier le currency de ce baseprice
            //Pour cela il faut se rassurer que le nouveau currency existe bien en BD
            Optional<Currency> optionalCurrency = currencyRepository.findCurrencyById(basePriceDto.getBpCurrencyDto().getId());
            if(!optionalCurrency.isPresent()){
                log.error("The currency precise for baseprice {} is not yet register iun the system ", basePriceDto);
                throw new EntityNotFoundException("Le currency precise dans l'object Baseprice n'existe pas en BD "
                        +basePriceDto, ErrorCode.BASEPRICE_NOT_VALID);
            }
            //On peut donc modifier le currency sans souci
            basePriceToUpdate.setBpCurrency(CurrencyDto.toEntity(basePriceDto.getBpCurrencyDto()));
        }
        /*******************************************************************
         * Maintenant on peut faire le reste des modification sans crainte
         */
        basePriceToUpdate.setBpPurchaseprice(basePriceDto.getBpPurchaseprice());
        basePriceToUpdate.setBpDetailprice(basePriceDto.getBpDetailprice());
        basePriceToUpdate.setBpSemiwholesaleprice(basePriceDto.getBpSemiwholesaleprice());
        basePriceToUpdate.setBpWholesaleprice(basePriceDto.getBpWholesaleprice());
        basePriceToUpdate.setBpRistourne(basePriceDto.getBpRistourne());
        basePriceToUpdate.setBpPrecompte(basePriceDto.getBpPrecompte());

        return BasePriceDto.fromEntity(
                bpRepository.save(basePriceToUpdate)
        );
    }

    @Override
    public BasePriceDto findBasePriceById(Long basePriceId) {
        if(basePriceId == null){
            log.error("The basepriceId sent as argument is null");
            throw new NullArgumentException("Le parametre de la methode findBasepriceById est null ");
        }
        Optional<BasePrice> optionalBasePrice = bpRepository.findBasePriceById(basePriceId);
        if(!optionalBasePrice.isPresent()){
            log.error("Any Baseprice exist in DB with the id {}", basePriceId);
            throw new EntityNotFoundException("Aucun baseprice n'existe en BD avec l'Id = "+basePriceId,
                    ErrorCode.BASEPRICE_NOT_FOUND);
        }
        return BasePriceDto.fromEntity(optionalBasePrice.get());
    }

    @Override
    public Boolean deleteBasePriceById(Long basePriceId) {
        if(basePriceId == null){
            log.error("The basepriceId sent as argument is null");
            throw new NullArgumentException("Le parametre de la methode findBasepriceById est null ");
        }
        Optional<BasePrice> optionalBasePrice = bpRepository.findBasePriceById(basePriceId);
        if(!optionalBasePrice.isPresent()){
            log.error("Any Baseprice exist in DB with the id {}", basePriceId);
            throw new EntityNotFoundException("Aucun baseprice n'existe en BD avec l'Id = "+basePriceId,
                    ErrorCode.BASEPRICE_NOT_FOUND);
        }
        if(!isBasePriceDeleteable(basePriceId)){
            log.error("The baseprice can't be deleted due to the fact it is already used by an article");
            throw new EntityNotDeleteableException("Le baseprice ne peut etre supprime puisque deja associe a un article ",
                    ErrorCode.BASEPRICE_NOT_DELETEABLE);
        }
        bpRepository.delete(optionalBasePrice.get());
        return true;
    }

    @Override
    public Boolean isBasePriceDeleteable(Long basePriceId) {
        return true;
    }
}
