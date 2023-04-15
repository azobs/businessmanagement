package com.c2psi.businessmanagement.services.contractsImpl.stock.product;

import com.c2psi.businessmanagement.dtos.stock.price.CurrencyConversionDto;
import com.c2psi.businessmanagement.dtos.stock.product.UnitConversionDto;
import com.c2psi.businessmanagement.dtos.stock.product.UnitDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.CurrencyConversion;
import com.c2psi.businessmanagement.models.Unit;
import com.c2psi.businessmanagement.models.UnitConversion;
import com.c2psi.businessmanagement.repositories.stock.product.UnitConversionRepository;
import com.c2psi.businessmanagement.repositories.stock.product.UnitRepository;
import com.c2psi.businessmanagement.services.contracts.stock.product.UnitConversionService;
import com.c2psi.businessmanagement.validators.stock.product.UnitConversionValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service(value="UnitConversionServiceV1")
@Slf4j
@Transactional
public class UnitConversionServiceImpl implements UnitConversionService {
    private UnitConversionRepository unitConversionRepository;
    private UnitRepository unitRepository;

    @Autowired
    public UnitConversionServiceImpl(UnitConversionRepository unitConversionRepository,
                                     UnitRepository unitRepository) {
        this.unitConversionRepository = unitConversionRepository;
        this.unitRepository = unitRepository;
    }

    @Override
    public UnitConversionDto saveUnitConversion(UnitConversionDto unitconvDto) {
        /************************************************************************
         * Il faut d'abord valider le Dto pris en parametre grace au validator
         */
        List<String> errors = UnitConversionValidator.validate(unitconvDto);
        if(!errors.isEmpty()){
            log.error("Entity UnitConversionDto not valid {}", unitconvDto);
            throw new InvalidEntityException("Le unitConversionDto passé en argument n'est pas valide: "+errors,
                    ErrorCode.UNITCONVERSION_NOT_VALID, errors);
        }

        /*********************************************************************
         * Il faut verifier que les 02 unit a lier existe vraiment en BD
         */
        Optional<Unit> optionalUnitSource = unitRepository.findUnitById(unitconvDto.getUnitSourceDto().getId());
        Optional<Unit> optionalUnitDestination = unitRepository.findUnitById(unitconvDto.getUnitDestinationDto().getId());
        if(!optionalUnitSource.isPresent() || !optionalUnitDestination.isPresent()){
            log.error("Entity UnitConversionDto {} not valid one or all the unit to link does not exist un DB", unitconvDto);
            throw new InvalidEntityException("Le unitConversionDto passé en argument n'est pas valide car l'un ou les " +
                    "2 unit a lie n'existe pas dans la BD", ErrorCode.UNITCONVERSION_NOT_VALID);
        }

        /******************************************************************
         * Il faut verifier que les 02 Unit sont dans le meme pointofsale
         */
        if(optionalUnitSource.get().getUnitPos().getId().longValue() !=
                optionalUnitDestination.get().getUnitPos().getId().longValue()){
            log.error("Entity UnitConversionDto {} not valid because the unit source and destination does not belong " +
                    "to the same pointofsale", unitconvDto);
            throw new InvalidEntityException("Le unitConversionDto passé en argument n'est pas valide car les 02 units " +
                    "source and destination n'appartiennent pas au meme pointofsale", ErrorCode.UNITCONVERSION_NOT_VALID);
        }

        /****************************************************************************************
         * Il faut verifier que les 02 units source et destination sont bel et bien different
         */
        if(optionalUnitSource.get().getId().equals(optionalUnitDestination.get().getId())){
            log.error("Entity UnitConversionDto {} not valid because the unit source and destination are the same unit",
                    unitconvDto);
            throw new InvalidEntityException("Le unitConversionDto passé en argument n'est pas valide car les 02 units " +
                    "a relier par la regle de conversion sont les memes", ErrorCode.UNITCONVERSION_NOT_VALID);
        }

        /***************************************************************************************************
         * Il faut verifier que la relation quon veut enregistrer entre les 2 Currency nexiste pas deja
         */
        if(!isUnitConversionUnique(unitconvDto.getUnitSourceDto().getId(), unitconvDto.getUnitDestinationDto().getId())){
            log.error("An entity unitconversion that link the two precised currency already exist in the DB {}",
                    unitconvDto);
            throw new DuplicateEntityException("Un UnitConversion existe deja dans la BD liant les Unit",
                    ErrorCode.UNITCONVERSION_DUPLICATED);
        }

        log.info("After all verification the data {} can be registered in the system without any problem", unitconvDto);

        return UnitConversionDto.fromEntity(
                unitConversionRepository.save(
                        UnitConversionDto.toEntity(unitconvDto)
                )
        );
    }

    @Override
    public UnitConversionDto updateUnitConversion(UnitConversionDto unitconvDto) {
        /************************************************************************
         * Il faut d'abord valider le Dto pris en parametre grace au validator
         */
        List<String> errors = UnitConversionValidator.validate(unitconvDto);
        if(!errors.isEmpty()){
            log.error("Entity UnitConversionDto not valid {}", unitconvDto);
            throw new InvalidEntityException("Le unitConversionDto passé en argument n'est pas valide: "+errors,
                    ErrorCode.UNITCONVERSION_NOT_VALID, errors);
        }

        /*************************************************************************
         * Il faut se rassurer que le id du unitconversion existe vraiment
         */
        if(unitconvDto.getId() == null){
            log.error("The id of the unitConversionDto to update is not precise {}", unitconvDto);
            throw new InvalidEntityException("L'Id du unitConversion a update n'est pas precise ",
                    ErrorCode.UNITCONVERSION_NOT_VALID);
        }

        /*******************************************************************
         * Il faut faire la recherche du CurrencyConversion a modifier
         */
        Optional<UnitConversion> optionalUnitConversionToUpdate = unitConversionRepository.findUnitConversionById(
                unitconvDto.getId());

        if(!optionalUnitConversionToUpdate.isPresent()){
            log.error("There is no unitConversion with the id precised in DB {}", unitconvDto);
            throw new EntityNotFoundException("Aucun unitconversion n'existe en BD avec l'id precise ",
                    ErrorCode.UNITCONVERSION_NOT_FOUND);
        }
        UnitConversion unitConversionToUpdate = optionalUnitConversionToUpdate.get();
        /*****************
         * On se rassure que c'est ni le unit source ni le destination quon essaye de modifier
         * car cela n'est pas permi. on ne peut que modifier le conversionFactor. pour les autres
         * il faut supprimer la regle et creer une autre ou simplement creer une autre
         */
        if(!unitconvDto.getUnitSourceDto().getId().equals(unitConversionToUpdate.getUnitSource().getId()) ||
                !unitconvDto.getUnitDestinationDto().getId().equals(unitConversionToUpdate.getUnitDestination().getId())){
            log.error("It is not possible to change one or all the unit link with this rule. Add a new one " +
                    "or delete this");
            throw new InvalidEntityException("Il n'est pas possible de update un unit dans une regle de " +
                    "conversion. il faut soit supprimer la regle soit creer une autre entre les nouvelles unit",
                    ErrorCode.UNITCONVERSION_NOT_VALID);
        }

        /****
         * Ici on est sur que les unit source et destination sont les meme et que cest seul le
         * conversionFactor qui peut changer
         */

        unitConversionToUpdate.setConversionFactor(unitconvDto.getConversionFactor());

        return UnitConversionDto.fromEntity(unitConversionRepository.save(unitConversionToUpdate));
    }

    @Override
    public Double convertTo(Double nbToConvert, Long unitSourceId, Long unitDestinationId) {
        /************************************************
         * On se rassure qu'aucun parametre n'est null
         */
        if(nbToConvert == null || unitSourceId == null || unitDestinationId == null){
            log.error("unitSourceId or unitDestinationId or nbToConvert is null");
            throw new NullArgumentException("le unitSourceId ou le unitDestinationId passe en " +
                    "argument de la methode est null ou alors c'est le nbToConvert qui est null");
        }
        /*********************************************************************************
         * On se rassure qu'il existe une regle de conversion liant les 02 currency
         */
        if(!this.isUnitConversionExist(unitSourceId, unitDestinationId)){
            log.error("There is no conversion rule that link currencySourceId and currencyDestinationId");
            throw new EntityNotFoundException("Aucun UnitConversion liant le unit d'id "+
                    unitSourceId +" et le unit d'id "+unitDestinationId
                    +" n'a été trouve dans la BDD", ErrorCode.UNITCONVERSION_NOT_FOUND);
        }
        /**************************************************************************
         * On recupere la regle de conversion qui existe entre les 02 currency
         */
        //Si on arrive ici alors on est sur que la regle de conversion entre les 02 unites existent
        UnitConversionDto unitconvDto = this.findConversionRuleBetween(unitSourceId, unitDestinationId);
        /******************************************
         * On l'utilise pour faire la conversion
         */
        Double valueConverted = nbToConvert * unitconvDto.getConversionFactor();
        /************************
         * On retourne la valeur obtenu apres conversion
         */
        return valueConverted;
    }

    public Boolean isUnitConversionExist(Long unitSourceId, Long unitDestinationId){
        if(unitSourceId == null || unitDestinationId == null){
            log.error("unitSourceId or unitDestinationId is null");
            throw new NullArgumentException("le unitSourceId ou le unitDestinationId passe en " +
                    "argument de la methode est null");
        }
        Optional<UnitConversion> optionalUnitConversion = unitConversionRepository.findConversionRuleBetween(unitSourceId,
                unitDestinationId);
        return optionalUnitConversion.isPresent()?true:false;
    }

    @Override
    public Boolean isUnitConversionUnique(Long unitSourceId, Long unitDestinationId) {
        if(unitSourceId == null || unitDestinationId == null){
            log.error("unitSourceId or unitDestinationId is null");
            throw new NullArgumentException("le unitSourceId ou le unitDestinationId passe en " +
                    "argument de la methode est null");
        }
        Optional<UnitConversion> optionalUnitConversion = unitConversionRepository.findConversionRuleBetween(
                unitSourceId, unitDestinationId);

        return optionalUnitConversion.isPresent()?false:true;
    }

    @Override
    public UnitConversionDto findUnitConversionById(Long unitconvId) {
        if(unitconvId == null){
            log.error("unitconvId is null");
            throw new NullArgumentException("le unitconvId passe en " +
                    "argument de la methode est null");
        }
        Optional<UnitConversion> optionalUnitConversion = unitConversionRepository.findUnitConversionById(unitconvId);
        if(!optionalUnitConversion.isPresent()){
            log.error("There is no conversion rule with the id = {}", unitconvId);
            throw new EntityNotFoundException("Aucune regle de conversion n'existe avec l'Id "+unitconvId,
                    ErrorCode.UNITCONVERSION_NOT_FOUND);
        }
        return UnitConversionDto.fromEntity(optionalUnitConversion.get());
    }

    @Override
    public UnitConversionDto findConversionRuleBetween(Long unitSourceId, Long unitDestinationId) {
        if(unitSourceId == null || unitDestinationId == null){
            log.error("unitSourceId or unitDestinationId is null");
            throw new NullArgumentException("le unitSourceId ou le unitDestinationId passe en " +
                    "argument de la methode est null");
        }
        Optional<UnitConversion> optionalUnitConversion = unitConversionRepository.findConversionRuleBetween(unitSourceId,
                unitDestinationId);
        if(!optionalUnitConversion.isPresent()){
            log.error("There is no conversion rule between unitsourceId {} and unitdestinationId {}", unitSourceId, unitDestinationId);
            throw new EntityNotFoundException("Aucune regle de conversion n'existe entre le unit source d'id "+unitSourceId+
                    " et le unit destination d'id "+unitDestinationId, ErrorCode.UNITCONVERSION_NOT_FOUND);
        }
        return UnitConversionDto.fromEntity(optionalUnitConversion.get());
    }

    @Override
    public List<UnitDto> listofConvertibleUnitWith(Long unitSourceId) {
        if(unitSourceId == null){
            log.error("unitSourceId is null");
            throw new NullArgumentException("le unitSourceId passe en argument de la methode est null");
        }
        Optional<List<UnitConversion>> optionalUnitDtoList = unitConversionRepository.findAllUnitConversionLinkWith(
                unitSourceId);
        if(!optionalUnitDtoList.isPresent()){
            log.error("There is no unitconversion with id source "+unitSourceId);
            throw new EntityNotFoundException("Aucune regle de conversion n'a le unit d'id = "+unitSourceId+" comme " +
                    "unitSource");
        }
        List<UnitDto> unitDtoList = new ArrayList<>();
        for(UnitConversion unitConversion : optionalUnitDtoList.get()){
            unitDtoList.add(UnitDto.fromEntity(unitConversion.getUnitDestination()));
        }

        return unitDtoList;
    }

    @Override
    public Boolean isUnitConversionDeleteable(Long unitconvId) {
        return true;
    }

    @Override
    public Boolean deleteUnitConversionById(Long unitconvId) {
        if(unitconvId == null){
            log.error("The argument pass as argument is null ");
            throw new NullArgumentException("Le parametre passe en argument est null");
        }
        Optional<UnitConversion> optionalUnitConversion = unitConversionRepository.findUnitConversionById(unitconvId);
        if(!optionalUnitConversion.isPresent()){
            log.error("There is no unitconversion with the id {} ", unitconvId);
            throw new EntityNotFoundException("Aucun unitconversion n'existe avec l'id "+unitconvId,
                    ErrorCode.UNITCONVERSION_NOT_FOUND);
        }
        if(!isUnitConversionDeleteable(unitconvId)){
            log.error("The conversion rule with id {} cannot be deleted", unitconvId);
            throw new EntityNotDeleteableException("Le unitconversion d'id "+unitconvId+" ne peut etre supprime car ",
                    ErrorCode.UNITCONVERSION_NOT_DELETEABLE);
        }
        unitConversionRepository.delete(optionalUnitConversion.get());
        return true;
    }
}
