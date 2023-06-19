package com.c2psi.businessmanagement.services.contractsImpl.stock.price;

import com.c2psi.businessmanagement.dtos.stock.price.CurrencyConversionDto;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.Currency;
import com.c2psi.businessmanagement.models.CurrencyConversion;
import com.c2psi.businessmanagement.repositories.stock.price.CurrencyConversionRepository;
import com.c2psi.businessmanagement.repositories.stock.price.CurrencyRepository;
import com.c2psi.businessmanagement.services.contracts.stock.price.CurrencyConversionService;
import com.c2psi.businessmanagement.validators.stock.price.CurrencyConversionValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service(value="CurrencyConversionServiceV1")
@Slf4j
@Transactional
public class CurrencyConversionServiceImpl implements CurrencyConversionService {

    private CurrencyConversionRepository curConvRepository;
    private CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyConversionServiceImpl(CurrencyConversionRepository curConvRepository,
                                         CurrencyRepository currencyRepository) {
        this.curConvRepository = curConvRepository;
        this.currencyRepository = currencyRepository;
    }

    @Override
    public CurrencyConversionDto saveCurrencyConversion(CurrencyConversionDto ccDto) {
        /******************
         * Il faut d'abord valider le Dto pris en parametre grace au validator
         */
        List<String> errors = CurrencyConversionValidator.validate(ccDto);
        if(!errors.isEmpty()){
            log.error("Entity CurrencyConversionDto not valid {}", ccDto);
            throw new InvalidEntityException("Le currencyConversionDto passé en argument n'est pas valide: "+errors,
                    ErrorCode.CURRENCYCONVERSION_NOT_VALID, errors);
        }

        /******************************************************
         * Il faut verifier que les 02 currency a lier existe vraiment en BD
         */
        Optional<Currency> optionalCurrencySource = currencyRepository.findCurrencyById(ccDto.getCurrencySourceDto().getId());
        Optional<Currency> optionalCurrencyDestination = currencyRepository.findCurrencyById(ccDto.getCurrencyDestinationDto().getId());
        if(!optionalCurrencySource.isPresent() || !optionalCurrencyDestination.isPresent()){
            log.error("Entity CurrencyConversionDto {} not valid one or all the currency to link does not exist un DB", ccDto);
            throw new InvalidEntityException("Le currencyConversionDto passé en argument n'est pas valide car l'un ou les " +
                    "2 currency a lie n'existe pas dans la BD", ErrorCode.CURRENCYCONVERSION_NOT_VALID);
        }

        /***********************************************
         * Il faut se rassurer que les 02 currency a relier par la regle ne sont pas les memes
         */
        if(optionalCurrencySource.get().getId().equals(optionalCurrencyDestination.get().getId())){
            log.error("Entity CurrencyConversionDto {} not valid because the currency source ans destination are the same", ccDto);
            throw new InvalidEntityException("Le currencyConversionDto passé en argument n'est pas valide car le currency " +
                    "source et destination sont les memes", ErrorCode.CURRENCYCONVERSION_NOT_VALID);
        }

        /************************
         * Il faut verifier que la relation quon veut enregistrer entre les 2 Currency nexiste pas deja
         */
        if(!this.isCurrencyConversionUnique(ccDto.getCurrencySourceDto().getId(),
                ccDto.getCurrencyDestinationDto().getId())){
            log.error("An entity currencyconversion that link the two precised currency already exist in the DB {}",
                    ccDto);
            throw new DuplicateEntityException("Un CurrencyConversion existe deja dans la BD liant les Currency",
                    ErrorCode.CURRENCYCONVERSION_DUPLICATED);
        }

        log.info("After all verification the data {} can be registered in the system without any problem", ccDto);

        return CurrencyConversionDto.fromEntity(
                curConvRepository.save(
                        CurrencyConversionDto.toEntity(ccDto)
                )
        );
    }

    @Override
    public CurrencyConversionDto updateCurrencyConversion(CurrencyConversionDto ccDto) {
        /******************
         * Il faut d'abord valider le Dto pris en parametre grace au validator
         */
        List<String> errors = CurrencyConversionValidator.validate(ccDto);
        if(!errors.isEmpty()){
            log.error("Entity CurrencyConversionDto not valid {}", ccDto);
            throw new InvalidEntityException("Le currencyConversionDto passé en argument n'est pas valide: "+errors,
                    ErrorCode.CURRENCYCONVERSION_NOT_VALID, errors);
        }
        /************************
         * Il faut se rassurer que le id du currencyconversion existe vraiment
         */
        if(ccDto.getId() == null){
            log.error("The id of the currencyConversionDto to update is not precise {}", ccDto);
            throw new InvalidEntityException("L'Id du currencyConversion a update n'est pas precise ",
                    ErrorCode.CURRENCYCONVERSION_NOT_VALID);
        }

        /*****
         * Il faut faire la recherche du CurrencyConversion a modifier
         */
        Optional<CurrencyConversion> optionalCurconvToUpdate = curConvRepository.findCurrencyConversionById(ccDto.getId());
        if(!optionalCurconvToUpdate.isPresent()){
            log.error("There is no currencyConversion with the id precised in DB {}", ccDto);
            throw new EntityNotFoundException("Aucun currencyconversion n'existe en BD avec l'id precise ",
                    ErrorCode.CURRENCYCONVERSION_NOT_FOUND);
        }
        CurrencyConversion currencyConversionToUpdate = optionalCurconvToUpdate.get();

        /*****************
         * On se rassure que c'est ni le currency source ni le destination quon essaye de modifier
         * car cela n'est pas permi. on ne peut que modifier le conversionFactor. pour les autres
         * il faut supprimer la regle et creer une autre ou simplement creer une autre
         */
        if(!ccDto.getCurrencySourceDto().getId().equals(currencyConversionToUpdate.getCurrencySource().getId()) ||
        !ccDto.getCurrencyDestinationDto().getId().equals(currencyConversionToUpdate.getCurrencyDestination().getId())){
            log.error("It is not possible to change one or all the currency link with this rule. Add a new one " +
                    "or delete this");
            throw new InvalidEntityException("Il n'est pas possible de update un currency dans une regle de " +
                    "conversion. il faut soit supprimer la regle soit creer une autre entre les nouvelles currency",
                    ErrorCode.CURRENCYCONVERSION_NOT_VALID);
        }
        /****
         * Ici on est sur que les currency source et destination sont les meme et que cest seul le
         * conversionFactor qui peut changer
         */

        currencyConversionToUpdate.setConversionFactor(ccDto.getConversionFactor());

        return CurrencyConversionDto.fromEntity(curConvRepository.save(currencyConversionToUpdate));
    }

    @Override
    public BigDecimal convertTo(BigDecimal amountToConvert, Long currencySourceId, Long currencyDestinationId) {
        /************************************************
         * On se rassure qu'aucun parametre n'est null
         */
        if(amountToConvert == null || currencySourceId == null || currencyDestinationId == null){
            log.error("currencySourceId or currencyDestinationId is null");
            throw new NullArgumentException("le currencySourceId ou le currencyDestinationId passe en " +
                    "argument de la methode est null");
        }

        /*********************************************************************************
         * On se rassure qu'il existe une regle de conversion liant les 02 currency
         */
        if(!this.isCurrencyConversionExist(currencySourceId, currencyDestinationId)){
            log.error("There is no conversion rule that link currencySourceId and currencyDestinationId");
            throw new EntityNotFoundException("Aucun Currency Conversion liant le currency d'id "+
                    currencySourceId +" et le currency d'id "+currencyDestinationId
                    +" n'a été trouve dans la BDD", ErrorCode.CURRENCYCONVERSION_NOT_FOUND);
        }

        /**************************************************************************
         * On recupere la regle de conversion qui existe entre les 02 currency
         */
        //Si on arrive ici alors on est sur que la regle de conversion entre les 02 devises existent
        CurrencyConversionDto curconvDto = this.findConversionRuleBetween(currencySourceId,
                currencyDestinationId);

        /************************************
         * On l'utilise pour effectuer la conversion
         */
        BigDecimal amountConverted = amountToConvert.multiply(curconvDto.getConversionFactor());
        BigDecimal newamountConverted = amountConverted.setScale(1, RoundingMode.UP);
        //log.info("amountConverted is {} and newamountConverted is {}", amountConverted, newamountConverted);

        /*********************
         * On retourne le resultat de la conversion
         */
        return newamountConverted;
    }

    @Override
    public CurrencyConversionDto findCurrencyConversionById(Long curconvId) {
        if(curconvId == null){
            log.error("curconvId is null");
            throw new NullArgumentException("le curconvId passe en argument de la methode est null");
        }
        Optional<CurrencyConversion> optionalCurConv = curConvRepository.findCurrencyConversionById(curconvId);

        if(optionalCurConv.isPresent()){
            return CurrencyConversionDto.fromEntity(optionalCurConv.get());
        }
        else{
            throw  new EntityNotFoundException("Aucun Currency Conversion avec l'id "+curconvId
                    +" n'a été trouve dans la BDD", ErrorCode.CURRENCYCONVERSION_NOT_FOUND);
        }

    }

    public Boolean isCurrencyConversionExist(Long currencySourceId, Long currencyDestinationId) {
        if(currencySourceId == null || currencyDestinationId == null){
            log.error("currencySourceId or currencyDestinationId is null");
            throw new NullArgumentException("le currencySourceId ou le currencyDestinationId passe en " +
                    "argument de la methode est null");
        }
        Optional<CurrencyConversion> optionalCurConv = curConvRepository.findConversionRuleBetween(currencySourceId,
                currencyDestinationId);
        return optionalCurConv.isPresent()?true:false;
    }

    @Override
    public Boolean isCurrencyConversionUnique(Long currencySourceId, Long currencyDestinationId) {
        if(currencySourceId == null || currencyDestinationId == null){
            log.error("currencySourceId or currencyDestinationId is null");
            throw new NullArgumentException("le currencySourceId ou le currencyDestinationId passe en " +
                    "argument de la methode est null");
        }
        Optional<CurrencyConversion> optionalCurConv1 = curConvRepository.findConversionRuleBetween(currencySourceId,
                currencyDestinationId);
        Boolean b1= optionalCurConv1.isPresent()?Boolean.FALSE:Boolean.TRUE;
        Optional<CurrencyConversion> optionalCurConv2 = curConvRepository.findConversionRuleBetween(currencyDestinationId,
                currencySourceId);
        Boolean b2= optionalCurConv2.isPresent()?Boolean.FALSE:Boolean.TRUE;
        return b1 && b2;
    }

    @Override
    public CurrencyConversionDto findConversionRuleBetween(Long currencySourceId, Long currencyDestinationId) {
        if(currencySourceId == null || currencyDestinationId == null){
            log.error("currencySourceId or currencyDestinationId is null");
            throw new NullArgumentException("le currencySourceId ou le currencyDestinationId passe en " +
                    "argument de la methode est null");
        }
        Optional<CurrencyConversion> optionalCurConv = curConvRepository.findConversionRuleBetween(currencySourceId,
                currencyDestinationId);
        if(optionalCurConv.isPresent()){
            return CurrencyConversionDto.fromEntity(optionalCurConv.get());
        }
        else{
            throw new EntityNotFoundException("Aucun CurrencyConversion liant le currency d'id source "+currencySourceId+"  avec le currency d'id destination "+currencyDestinationId
                    +" n'a été trouve dans la BDD", ErrorCode.CURRENCYCONVERSION_NOT_FOUND);
        }
    }

    @Override
    public List<CurrencyDto> listofConvertibleCurrencyWith(Long currencySourceId) {
        if(currencySourceId == null){
            log.error("The currencySourceId precised is null ");
            throw new NullArgumentException("Le currencySourceId passe en argument est null");
        }
        Optional<List<CurrencyConversion>> optListofCurConv =
                curConvRepository.findAllCurrencyConversionLinkWith(currencySourceId);
        List<CurrencyDto> listofCurrencyDtoLinkWith = new ArrayList<>();
        if(optListofCurConv.isPresent()){
            for(CurrencyConversion curconv: optListofCurConv.get()){
                listofCurrencyDtoLinkWith.add(CurrencyDto.fromEntity(curconv.getCurrencyDestination()));
            }
        }
        return listofCurrencyDtoLinkWith;
    }

    @Override
    public Boolean isCurrencyConversionDeleteable(Long curconvId) {
        return true;
    }

    @Override
    public Boolean deleteCurrencyConversionById(Long curconvid) {
        if(curconvid == null){
            log.error("curconvid is null");
            throw new NullArgumentException("le curconvid  passe en " +
                    "argument de la methode est null");
        }
        Optional<CurrencyConversion> optionalCurConv = curConvRepository.findCurrencyConversionById(curconvid);
        if(optionalCurConv.isPresent()){
            if(isCurrencyConversionDeleteable(curconvid)) {
                curConvRepository.delete(optionalCurConv.get());
                return true;
            }
            log.error("The entity {} is not deleteable because it encompasses some other elements ", optionalCurConv.get());
            throw new EntityNotDeleteableException("Ce currencyConversion ne peut etre supprime ",
                    ErrorCode.FORMAT_NOT_DELETEABLE);
        }
        throw new EntityNotFoundException("Aucune currencyConversion n'existe avec l'ID = "+curconvid);
    }

    @Override
    public Boolean deleteCurrencyConversionByCurrencyLink(Long currencySourceId, Long currencyDestinationId) {
        if(currencySourceId == null || currencyDestinationId == null){
            log.error("currencySourceId or currencyDestinationId is null");
            throw new NullArgumentException("le currencySourceId ou le currencyDestinationId passe en " +
                    "argument de la methode est null");
        }
        Optional<CurrencyConversion> optionalCurConv = curConvRepository.findConversionRuleBetween(currencySourceId,
                currencyDestinationId);
        if(optionalCurConv.isPresent()){
            curConvRepository.delete(optionalCurConv.get());
            return true;
        }
        return false;
    }
}
