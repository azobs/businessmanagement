package com.c2psi.businessmanagement.services.contractsImpl.stock.price;

import com.c2psi.businessmanagement.dtos.stock.price.CurrencyConversionDto;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.CurrencyConversion;
import com.c2psi.businessmanagement.repositories.stock.price.CurrencyConversionRepository;
import com.c2psi.businessmanagement.services.contracts.stock.price.CurrencyConversionService;
import com.c2psi.businessmanagement.validators.stock.price.CurrencyConversionValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service(value="CurrencyConversionServiceV1")
@Slf4j
@Transactional
public class CurrencyConversionServiceImpl implements CurrencyConversionService {

    private CurrencyConversionRepository curConvRepository;

    @Autowired
    public CurrencyConversionServiceImpl(CurrencyConversionRepository curConvRepository) {
        this.curConvRepository = curConvRepository;
    }

    @Override
    public CurrencyConversionDto saveCurrencyConversion(CurrencyConversionDto ccDto) {
        List<String> errors = CurrencyConversionValidator.validate(ccDto);
        if(!errors.isEmpty()){
            log.error("Entity CurrencyConversionDto not valid {}", ccDto);
            throw new InvalidEntityException("Le currencyConversionDto passé en argument n'est pas valide: "+errors,
                    ErrorCode.CURRENCYCONVERSION_NOT_VALID, errors);
        }

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
    public BigDecimal convertTo(BigDecimal amountToConvert, Long currencySourceId, Long currencyDestinationId) {
        if(amountToConvert == null || currencySourceId == null || currencyDestinationId == null){
            log.error("currencySourceId or currencyDestinationId is null");
            throw new NullArgumentException("le currencySourceId ou le currencyDestinationId passe en " +
                    "argument de la methode est null");
        }

        if(!this.isCurrencyConversionExist(currencySourceId, currencyDestinationId)){
            log.error("There is no conversion rule that link currencySourceId and currencyDestinationId");
            throw new EntityNotFoundException("Aucun Currency Conversion liant le currency d'id "+
                    currencySourceId +" et le currency d'id "+currencyDestinationId
                    +" n'a été trouve dans la BDD", ErrorCode.CURRENCYCONVERSION_NOT_FOUND);
        }

        //Si on arrive ici alors on est sur que la regle de conversion entre les 02 devises existent
        CurrencyConversionDto curconvDto = this.findConversionRuleBetween(currencySourceId,
                currencyDestinationId);

        BigDecimal amountConverted = amountToConvert.multiply(curconvDto.getConversionFactor());

        return amountConverted;
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
        return null;
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
            curConvRepository.delete(optionalCurConv.get());
            return true;
        }
        return false;
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
