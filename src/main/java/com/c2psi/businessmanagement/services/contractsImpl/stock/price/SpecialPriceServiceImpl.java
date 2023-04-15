package com.c2psi.businessmanagement.services.contractsImpl.stock.price;

import com.c2psi.businessmanagement.dtos.stock.price.BasePriceDto;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyDto;
import com.c2psi.businessmanagement.dtos.stock.price.SpecialPriceDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.BasePrice;
import com.c2psi.businessmanagement.models.Currency;
import com.c2psi.businessmanagement.models.SpecialPrice;
import com.c2psi.businessmanagement.repositories.stock.price.BasePriceRepository;
import com.c2psi.businessmanagement.repositories.stock.price.CurrencyRepository;
import com.c2psi.businessmanagement.repositories.stock.price.SpecialPriceRepository;
import com.c2psi.businessmanagement.services.contracts.stock.price.SpecialPriceService;
import com.c2psi.businessmanagement.validators.stock.price.BasePriceValidator;
import com.c2psi.businessmanagement.validators.stock.price.SpecialPriceValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value="SpecialPriceServiceV1")
@Slf4j
@Transactional
public class SpecialPriceServiceImpl implements SpecialPriceService {

    private BasePriceRepository bpRepository;
    private SpecialPriceRepository spRepository;

    @Autowired
    public SpecialPriceServiceImpl(CurrencyRepository currencyRepository, BasePriceRepository bpRepository,
                                   SpecialPriceRepository spRepository) {
        this.bpRepository = bpRepository;
        this.spRepository = spRepository;
    }

    @Override
    public SpecialPriceDto saveSpecialPrice(SpecialPriceDto specialPriceDto) {
        /**********************************************************
         * Il faut d'abord valider le produit pris en parametre   *
         **********************************************************/
        List<String> errors = SpecialPriceValidator.validate(specialPriceDto);
        if(!errors.isEmpty()){
            log.error("Entity specialPriceDto not valid {}", specialPriceDto);
            throw new InvalidEntityException("Le specialPriceDto passé en argument n'est pas valide: ",
                    ErrorCode.SPECIALPRICE_NOT_VALID, errors);
        }

        /*****************************************************************************************
         * Verify the existence of the baseprice that will be associated with that special price *
         *****************************************************************************************/
        Optional<BasePrice> optionalBasePrice = bpRepository.findBasePriceById(specialPriceDto.getSpBasepriceDto().getId());
        if(!optionalBasePrice.isPresent()){
            log.error("The baseprice precise for specialprice {} is not yet existing in the system ", specialPriceDto);
            throw new EntityNotFoundException("Le baseprice precise dans l'object Specialprice n'existe pas en BD "
                    +specialPriceDto, ErrorCode.SPECIALPRICE_NOT_VALID);
        }

        log.info("After all verification the specialpricedto {} can be register in the DB without any problem ", specialPriceDto);
        return SpecialPriceDto.fromEntity(
                spRepository.save(
                        SpecialPriceDto.toEntity(specialPriceDto)
                )
        );
    }

    @Override
    public SpecialPriceDto updateSpecialPrice(SpecialPriceDto specialPriceDto) {
        /**********************************************************
         * Il faut d'abord valider le produit pris en parametre   *
         **********************************************************/
        List<String> errors = SpecialPriceValidator.validate(specialPriceDto);
        if(!errors.isEmpty()){
            log.error("Entity specialPriceDto not valid {}", specialPriceDto);
            throw new InvalidEntityException("Le specialPriceDto passé en argument n'est pas valide: ",
                    ErrorCode.SPECIALPRICE_NOT_VALID, errors);
        }

        /*************************************************************************************
         * Il faut se rassurer que l'id du specialpricedto est bel et bien precise puisque le
         * validator ne verifie ps ca
         */
        if(specialPriceDto.getId() == null){
            log.error("The id of the specialprice to modify does'nt exist in the DB");
            throw new InvalidEntityException("Le specialprice a modifier ne peut etre identifier puisque son id est null ",
                    ErrorCode.SPECIALPRICE_NOT_VALID);
        }

        /**************************************************************************************
         * Il faut se rassurer que le specialprice a modifier existe vraiment dans le systeme
         */
        Optional<SpecialPrice> optionalSpecialPrice = spRepository.findSpecialPriceById(specialPriceDto.getId());
        if(!optionalSpecialPrice.isPresent()){
            log.error("The id precise in the {} does not identify any specialprice in the DB ", specialPriceDto);
            throw  new EntityNotFoundException("Le specialprice indique pour etre modifie est inexistant dans la BD ",
                    ErrorCode.SPECIALPRICE_NOT_FOUND);
        }

        /*********************************************
         * Il faut recuperer le specialprice a modifier
         */
        SpecialPrice specialPriceToUpdate = optionalSpecialPrice.get();
        /***********************************************************************
         * On verifie si ce n'est pas le baseprice quon veut changer
         */
        if(!specialPriceDto.getSpBasepriceDto().getId().equals(specialPriceToUpdate.getSpBaseprice().getId())){
            //Alors on refuse car on ne saurait modifier le prixdebase dun prixspecial.
            log.error("It is not possible to modify the baseprice of a specialprice");
            throw new InvalidEntityException("le prixdebase dun prixspecial ne peut etre modifie ",
                    ErrorCode.SPECIALPRICE_NOT_VALID);
        }
        log.error("After all verification we are sure that the specialprice can be modify without any problem ");

        /*******************************************************************
         * Maintenant on peut faire le reste des modification sans crainte
         */
        specialPriceToUpdate.setSpDetailprice(specialPriceDto.getSpDetailprice());
        specialPriceToUpdate.setSpSemiwholesaleprice(specialPriceDto.getSpSemiwholesaleprice());
        specialPriceToUpdate.setSpWholesaleprice(specialPriceDto.getSpWholesaleprice());
        specialPriceToUpdate.setSpRistourne(specialPriceDto.getSpRistourne());
        specialPriceToUpdate.setSpPrecompte(specialPriceDto.getSpPrecompte());

        return SpecialPriceDto.fromEntity(
                spRepository.save(specialPriceToUpdate)
        );
    }

    @Override
    public SpecialPriceDto findSpecialPriceById(Long specialPriceId) {
        if(specialPriceId == null){
            log.error("The argument specialPriceId is null");
            throw new NullArgumentException("l'argument de la methode findSpecialPriceById is null");
        }
        Optional<SpecialPrice> optionalSpecialPrice = spRepository.findSpecialPriceById(specialPriceId);
        if(!optionalSpecialPrice.isPresent()){
            log.error("The specialPriceId {} precised don't match with any specialprice ", specialPriceId);
            throw new EntityNotFoundException("Aucun specialprice n'existe avec le id = "+specialPriceId,
                    ErrorCode.SPECIALPRICE_NOT_FOUND);
        }
        return SpecialPriceDto.fromEntity(optionalSpecialPrice.get());
    }

    @Override
    public List<SpecialPriceDto> findListofSpecialPriceOf(Long basePriceId) {
        if(basePriceId == null){
            log.error("The basePriceid precise is null");
            throw new NullArgumentException("L'argument de la methode findListofSpecialPriceof is null");
        }
        Optional<List<SpecialPrice>> optionalSpecialPriceList = spRepository.findListofSpecialPriceOf(basePriceId);
        if(!optionalSpecialPriceList.isPresent()){
            log.error("The specialprice is not found in the DB");
            throw new EntityNotFoundException("Aucun specialprice n'est retrouve dans la BD ",
                    ErrorCode.SPECIALPRICE_NOT_FOUND);
        }
        List<SpecialPrice> specialPriceList = optionalSpecialPriceList.get();
        return specialPriceList.stream().map(SpecialPriceDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Boolean deleteSpecialPriceById(Long specialPriceId) {
        if(specialPriceId == null){
            log.error("The specialPriceId precise is null");
            throw new NullArgumentException("L'argument de la methode deleteSpecialPriceById is null");
        }

        Optional<SpecialPrice> optionalSpecialPrice = spRepository.findSpecialPriceById(specialPriceId);
        if(!optionalSpecialPrice.isPresent()){
            log.error("Any specialprice does not exist with the specialpriceid = "+specialPriceId);
            throw new EntityNotFoundException("Aucun specialprice n'existe avec l'ig passe en argument "+specialPriceId,
                    ErrorCode.SPECIALPRICE_NOT_FOUND);
        }
        if(!isSpecialPriceDeleteable(specialPriceId)){
            log.error("The specialprice precised by {} cannot be deleted ", specialPriceId);
            throw new EntityNotDeleteableException("Ce specialprice ne peut etre supprime ",
                    ErrorCode.SPECIALPRICE_NOT_DELETEABLE);
        }
        spRepository.delete(optionalSpecialPrice.get());
        return true;
    }

    @Override
    public Boolean isSpecialPriceDeleteable(Long basePriceId) {
        return true;
    }
}
