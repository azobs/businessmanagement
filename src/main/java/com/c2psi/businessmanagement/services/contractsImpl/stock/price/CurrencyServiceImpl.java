package com.c2psi.businessmanagement.services.contractsImpl.stock.price;

import com.c2psi.businessmanagement.dtos.stock.price.CurrencyDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.Currency;
import com.c2psi.businessmanagement.repositories.stock.price.CurrencyRepository;
import com.c2psi.businessmanagement.services.contracts.stock.price.CurrencyService;
import com.c2psi.businessmanagement.validators.stock.price.CurrencyValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value="CurrencyServiceV1")
@Slf4j
@Transactional
public class CurrencyServiceImpl implements CurrencyService {

    private CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {

        this.currencyRepository = currencyRepository;
    }

    @Override
    public CurrencyDto saveCurrency(CurrencyDto currencyDto) {
        List<String> errors = CurrencyValidator.validate(currencyDto);
        if(!errors.isEmpty()){
            log.error("Entity currencyDto not valid {}", currencyDto);
            throw new InvalidEntityException("Le currencyDto passé en argument n'est pas valide: ",
                    ErrorCode.CURRENCY_NOT_VALID, errors);
        }

        if(!this.isCurrencyUnique(currencyDto.getCurrencyName(), currencyDto.getCurrencyShortname())){
            log.error("An entity currency already exist may be with the same name and shortname  in the DB {}",
                    currencyDto);
            throw new DuplicateEntityException("Un Currency existe deja dans la BD avec les mêmes name et shortname",
                    ErrorCode.CURRENCY_DUPLICATED);
        }

        return CurrencyDto.fromEntity(
                currencyRepository.save(
                        CurrencyDto.toEntity(currencyDto)
                )
        );
    }

    @Override
    public CurrencyDto updateCurrency(CurrencyDto currencyDto) {
        List<String> errors = CurrencyValidator.validate(currencyDto);
        if(!errors.isEmpty()){
            log.error("Entity currencyDto not valid {}", currencyDto);
            throw new InvalidEntityException("Le currencyDto passé en argument n'est pas valide: "+errors,
                    ErrorCode.CURRENCY_NOT_VALID, errors);
        }
        if(!this.isCurrencyExistWithId(currencyDto.getId())){
            throw new EntityNotFoundException("Le currency a update n'existe pas en BD ", ErrorCode.CURRENCY_NOT_FOUND);
        }
        //Ici on est sur que le currency existe et on doit donc le recuperer
        Currency currencyToUpdate = CurrencyDto.toEntity(this.findCurrencyById(currencyDto.getId()));
        if(!currencyToUpdate.getCurrencyName().equalsIgnoreCase(currencyDto.getCurrencyName()) ||
        !currencyToUpdate.getCurrencyShortname().equalsIgnoreCase(currencyDto.getCurrencyShortname())){
            if(!this.isCurrencyExistWithFullname(currencyDto.getCurrencyName(),
                    currencyDto.getCurrencyShortname())){
                currencyToUpdate.setCurrencyName(currencyDto.getCurrencyName());
                currencyToUpdate.setCurrencyShortname(currencyDto.getCurrencyShortname());
            }
            else{
                throw  new DuplicateEntityException("Un Currency existe deja dans la BD avec le meme fullname  "
                        , ErrorCode.CURRENCY_DUPLICATED);
            }
        }

        return CurrencyDto.fromEntity(
                currencyRepository.save(currencyToUpdate)
        );
    }

    @Override
    public List<CurrencyDto> listofExistingCurrency() {
        List<Currency> listofCurrency = currencyRepository.findAll();
        return listofCurrency.stream().map(CurrencyDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CurrencyDto> pageofExistingCurrency(int pagenum, int pagesize) {
        Page<Currency> pageofCurrency = currencyRepository.findAll(
                PageRequest.of(pagenum, pagesize, Sort.by(Sort.Direction.ASC, "currencyName")));
        return pageofCurrency.map(CurrencyDto::fromEntity);
    }

    @Override
    public Boolean isCurrencyUnique(String currencyName, String currencyShortName) {
        if(!StringUtils.hasLength(currencyName)){
            log.error("currencyName is null");
            throw new NullArgumentException("le currencyName passe en argument de la methode est null");
        }
        Optional<Currency> optionalCurrency = currencyRepository.findCurrencyByCurrencyNameAndAndCurrencyShortname(currencyName,
                currencyShortName);
        return optionalCurrency.isPresent()?false:true;
    }

    @Override
    public CurrencyDto findCurrencyByFullname(String currencyName, String currencyShortName) {
        if(currencyName == null){
            log.error("currencyName is null");
            throw new NullArgumentException("le currencyName passe en argument de la methode est null");
        }
        if(currencyShortName == null){
            log.error("currencyShortName is null");
            throw new NullArgumentException("le currencyShortName passe en argument de la methode est null");
        }
        Optional<Currency> optionalCurrency = currencyRepository.
                findCurrencyByCurrencyNameAndAndCurrencyShortname(currencyName, currencyShortName);

        if(optionalCurrency.isPresent()){
            return CurrencyDto.fromEntity(optionalCurrency.get());
        }
        else{
            throw new EntityNotFoundException("Aucun Currency avec le fullname "+currencyName+"  "+currencyShortName
                    +" n'a été trouve dans la BDD", ErrorCode.CURRENCY_NOT_FOUND);
        }

    }

    @Override
    public CurrencyDto findCurrencyById(Long currencyId) {
        if(currencyId == null){
            log.error("currencyId is null");
            throw new NullArgumentException("le currencyId passe en argument de la methode est null");
        }
        Optional<Currency> optionalCurrency = currencyRepository.findCurrencyById(currencyId);

        if(optionalCurrency.isPresent()){
            return CurrencyDto.fromEntity(optionalCurrency.get());
        }
        else{
            throw new EntityNotFoundException("Aucun Currency avec l'id "+currencyId
                    +" n'a été trouve dans la BDD", ErrorCode.CURRENCY_NOT_FOUND);
        }

    }

    public Boolean isCurrencyExistWithFullname(String currencyName, String currencyShortName) {
        if(!StringUtils.hasLength(currencyName)){
            log.error("currencyName is null");
            throw new NullArgumentException("le currencyName passe en argument de la methode est null");
        }
        Optional<Currency> optionalCurrency = currencyRepository.findCurrencyByCurrencyNameAndAndCurrencyShortname(currencyName,
                currencyShortName);
        return optionalCurrency.isPresent()?true:false;
    }

    public Boolean isCurrencyExistWithId(Long currencyId) {
        if(currencyId == null){
            log.error("currencyId is null");
            throw new NullArgumentException("le currencyId passe en argument de la methode est null");
        }
        Optional<Currency> optionalCurrency = currencyRepository.findCurrencyById(currencyId);
        return optionalCurrency.isPresent()?true:false;
    }

    @Override
    public Boolean deleteCurrencyById(Long currencyId) {
        if(currencyId == null){
            log.error("currencyId is null");
            throw new NullArgumentException("le currencyId passe en argument de la methode est null");
        }
        Optional<Currency> optionalCurrency = currencyRepository.findCurrencyById(currencyId);
        if(optionalCurrency.isPresent()){
            currencyRepository.delete(optionalCurrency.get());
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteCurrencyByFullname(String currencyName, String currencyShortName) {
        if(!StringUtils.hasLength(currencyName)){
            log.error("currencyName is null");
            throw new NullArgumentException("le currencyName passe en argument de la methode est null");
        }

        if(!StringUtils.hasLength(currencyShortName)){
            log.error("currencyShortName is null");
            throw new NullArgumentException("le currencyShortName passe en argument de la methode est null");
        }

        Optional<Currency> optionalCurrency = currencyRepository.
                findCurrencyByCurrencyNameAndAndCurrencyShortname(currencyName, currencyShortName);
        if(optionalCurrency.isPresent()){
            currencyRepository.delete(optionalCurrency.get());
            return true;
        }
        return false;
    }
}
