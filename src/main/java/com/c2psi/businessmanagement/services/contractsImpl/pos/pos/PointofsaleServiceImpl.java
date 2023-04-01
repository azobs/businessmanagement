package com.c2psi.businessmanagement.services.contractsImpl.pos.pos;

import com.c2psi.businessmanagement.dtos.pos.pos.EnterpriseDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyConversionDto;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyDto;
import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.Currency;
import com.c2psi.businessmanagement.models.CurrencyConversion;
import com.c2psi.businessmanagement.models.Enterprise;
import com.c2psi.businessmanagement.models.Pointofsale;
import com.c2psi.businessmanagement.repositories.pos.pos.EnterpriseRepository;
import com.c2psi.businessmanagement.repositories.pos.pos.PointofsaleRepository;
import com.c2psi.businessmanagement.repositories.pos.pos.PosCashAccountRepository;
import com.c2psi.businessmanagement.repositories.stock.price.CurrencyConversionRepository;
import com.c2psi.businessmanagement.repositories.stock.price.CurrencyRepository;
import com.c2psi.businessmanagement.services.contracts.pos.pos.PointofsaleService;
import com.c2psi.businessmanagement.validators.pos.pos.PointofsaleValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value="PointofsaleServiceV1")
@Slf4j
@Transactional
public class PointofsaleServiceImpl implements PointofsaleService {

    private EnterpriseRepository entRepository;
    private CurrencyRepository currencyRepository;
    private PosCashAccountRepository posCashAccountRepository;
    private PointofsaleRepository posRepository;

    private CurrencyConversionRepository currencyConversionRepository;

    @Autowired
    public PointofsaleServiceImpl(EnterpriseRepository entRepo, CurrencyRepository currRepo,
                                  PosCashAccountRepository pcaRepo, PointofsaleRepository posRepo,
                                  CurrencyConversionRepository currConvRepository){
        this.currencyRepository = currRepo;
        this.entRepository = entRepo;
        this.posCashAccountRepository = pcaRepo;
        this.posRepository = posRepo;
        this.currencyConversionRepository = currConvRepository;
    }

    @Override
    public Double getTurnover(PointofsaleDto posDto, Date startDate, Date endDate) {
        return null;
    }

    @Override
    public List<UserBMDto> findAllEmployeofPos(PointofsaleDto posDto) {
        return null;
    }

    @Override
    public List<ProviderDto> findAllProviderofPos(PointofsaleDto posDto) {
        return null;
    }

    @Override
    public Double getTotalCash(Long entId) {
        return null;
    }

    @Override
    public Integer getNumberofDamage(PointofsaleDto posDto) {
        return null;
    }

    @Override
    public Integer getNumberofDamage(PointofsaleDto posDto, ArticleDto artDto) {
        return null;
    }

    @Override
    public Integer getNumberofCapsule(PointofsaleDto posDto) {
        return null;
    }

    @Override
    public Integer getNumberofCapsule(PointofsaleDto posDto, ArticleDto artDto) {
        return null;
    }

    @Override
    public Integer getNumberofPackaging(PointofsaleDto posDto) {
        return null;
    }

    @Override
    public Integer getNumberofPackaging(PointofsaleDto posDto, ProviderDto providerDto) {
        return null;
    }

    @Override
    public PointofsaleDto savePointofsale(PointofsaleDto posDto) {
        List<String> errors = PointofsaleValidator.validate(posDto);
        if(!errors.isEmpty()){
            log.error("Entity Pointofsale not valid {}", posDto);
            throw new InvalidEntityException("Le pointofsale passe en argument n'est pas valide:  ",
                    ErrorCode.POINTOFSALE_NOT_VALID, errors);
        }
        //Il faut deja se rassurer que l'entreprise associe avec le pointofsale est existante
        if(Optional.ofNullable(posDto.getPosEnterpriseDto()).isPresent()){
            Optional<Enterprise> optionalPosEnt = entRepository.findEnterpriseById(posDto.getPosEnterpriseDto().getId());
            if(!optionalPosEnt.isPresent()){
                log.error("The enterprise owner of the pointofsale precised does not exist in the system");
                throw new EntityNotFoundException("L'entreprise associe avec le pointofsale n'existe pas en BD"
                        , ErrorCode.ENTERPRISE_NOT_FOUND);
            }
        }
        else{
            log.error("The enterprise owner of the pointofsale has not been precised");
            throw new EntityNotFoundException("Aucune entreprise n'est associe avec le pointofsale"
                    , ErrorCode.ENTERPRISE_NOT_FOUND);
        }
        //IL faut verifier que le pointofsale sera unique dans l'entreprise
        if(!this.isPosUnique(posDto.getPosName(), posDto.getPosEnterpriseDto())){
            //Si c'est true alors le pointofsale sera unique donc le non signifie que ca ne va pas etre unique
            throw new DuplicateEntityException("Un Pointofsale existe deja dans l'entreprise precise avec  " +
                    "le meme nom :", ErrorCode.POINTOFSALE_DUPLICATED);
        }

        log.info("After all verification, the record {} can be done on the DB", posDto);

        return PointofsaleDto.fromEntity(
                posRepository.save(
                        PointofsaleDto.toEntity(posDto)
                )
        );
    }

    @Override
    public PointofsaleDto updatePointofsale(PointofsaleDto posDto) {
        List<String> errors = PointofsaleValidator.validate(posDto);
        if(!errors.isEmpty()){
            log.error("Entity Pointofsale not valid {}", posDto);
            throw new InvalidEntityException("Le pointofsale passe en argument n'est pas valide:  "+errors,
                    ErrorCode.POINTOFSALE_NOT_VALID, errors);
        }
        /***
         * Les parametres à modifier sans controle sont:
         * posAcronym, posDescription, posAddress(sans l'email),
         * Les parametres sur lesquelles il faut faire attention sont:
         * posName, email
         */
        if(!this.isPointofsaleExistWithId(posDto.getId())){
            throw new EntityNotFoundException("Le pointofsale a update n'existe pas dans la BD",
                    ErrorCode.POINTOFSALE_NOT_VALID, errors);
        }
        //Tout est bon et on peut maintenant recuperer le pointofsale a modifier
        Pointofsale posToUpdate = PointofsaleDto.toEntity(this.findPointofsaleById(posDto.getId()));

        posToUpdate.setPosAcronym(posDto.getPosAcronym());
        posToUpdate.setPosDescription(posToUpdate.getPosDescription());
        if(posDto.getPosAddressDto() != null){
            posToUpdate.getPosAddress().setLocalisation(posDto.getPosAddressDto().getLocalisation());
            posToUpdate.getPosAddress().setPays(posDto.getPosAddressDto().getPays());
            posToUpdate.getPosAddress().setVille(posDto.getPosAddressDto().getVille());
            posToUpdate.getPosAddress().setQuartier(posDto.getPosAddressDto().getQuartier());
            posToUpdate.getPosAddress().setNumtel3(posDto.getPosAddressDto().getNumtel3());
            posToUpdate.getPosAddress().setNumtel2(posDto.getPosAddressDto().getNumtel2());
            posToUpdate.getPosAddress().setNumtel1(posDto.getPosAddressDto().getNumtel1());
        }

        if(!posToUpdate.getPosAddress().getEmail().equalsIgnoreCase(posDto.getPosAddressDto().getEmail())){
            /**
             * Si les adresse email ne sont pas les meme alors on souhaite modifier les adresses email
             * Il faut donc verifier l'unicite de l'adresse email en BD
             */
            if(!this.isPointofsaleExistWithEmail(posDto.getPosAddressDto().getEmail())){
                posToUpdate.getPosAddress().setEmail(posDto.getPosAddressDto().getEmail());
            }
            else{
                throw new DuplicateEntityException("Un pointofsale existe en BD avec cet email",
                        ErrorCode.POINTOFSALE_DUPLICATED);
            }
        }

        if(!posToUpdate.getPosName().equalsIgnoreCase(posDto.getPosName())){
            /***
             * Si le nom du pointofsale pris en BD et celui envoye en Dto sont different alors on souhaite aussi
             * modifier le nom et pour cela des verification doivent etre faite
             */
            if(!this.isPointofsaleExistInEnterpriseWithName(posDto.getPosName(), posDto.getPosEnterpriseDto().getId())){
                posToUpdate.setPosName(posDto.getPosName());
            }
            else{
                throw new DuplicateEntityException("Un pointofsale existe en BD avec ce nom",
                        ErrorCode.POINTOFSALE_DUPLICATED);
            }
        }
        return PointofsaleDto.fromEntity(posRepository.save(posToUpdate));
    }

    public Boolean isPointofsaleExistInEnterpriseWithName(String posName, Long entId) {
        if(!StringUtils.hasLength(posName)){
            log.error("Pointofsale posName is null");
            throw new NullArgumentException("le posName passe en argument de la methode est null");
        }

        if(entId == null){
            log.error("entId is null");
            throw new NullArgumentException("le entId passe en argument de la methode est null");
        }

        Optional<Pointofsale> optionalPointofsale = posRepository.findPointofsaleOfEnterpriseByName(posName, entId);

        return optionalPointofsale.isPresent()?true:false;
    }

    public Boolean isPointofsaleExistWithEmail(String posEmail) {
        if(!StringUtils.hasLength(posEmail)){
            log.error("Pointofsale posEmail is null");
            throw new NullArgumentException("L'Email passe en argument de la methode est null");
        }

        Optional<Pointofsale> optionalPointofsale = posRepository.findPointofsaleByPosEmail(posEmail);

        return optionalPointofsale.isPresent()?true:false;
    }

    @Override
    public PointofsaleDto findPointofsaleById(Long posId) {
        if(posId == null){
            log.error("posId is null");
            throw new NullArgumentException("le posId passe en argument de la methode findEnterpriseById est null");
        }
        Optional<Pointofsale> optionalPointofsale = posRepository.findPointofsaleById(posId);

        if(optionalPointofsale.isPresent()){
            return PointofsaleDto.fromEntity(optionalPointofsale.get());
        }
        else {
            throw new EntityNotFoundException("Aucun Pointofsale avec le id ="+posId
                    +" n'a ete trouve dans la BDD ", ErrorCode.POINTOFSALE_NOT_FOUND);
        }
    }

    @Override
    public PointofsaleDto findPosInEnterpriseByName(String posName, EnterpriseDto entDto) {
        if(!StringUtils.hasLength(posName)){
            log.error("Pointofsale name is null");
            throw new NullArgumentException("le posName passe en argument de la methode est null: ");
        }
        if(entDto == null){
            log.error("EnterpriseDto name is null");
            throw new NullArgumentException("l'entreprise passe en argument de la methode est null: ");
        }
        if(entDto.getId() == null){
            log.error("L'ID de l'entreprise is null");
            throw new NullArgumentException("l'ID de l'entreprise passe en argument de la methode est null: ");
        }
        Optional<Pointofsale> optionalPointofsale = posRepository.findPointofsaleOfEnterpriseByName(posName,
                entDto.getId());

        if(optionalPointofsale.isPresent()){
            return PointofsaleDto.fromEntity(optionalPointofsale.get());
        }
        else {
            throw new EntityNotFoundException("Aucun Pointofsale avec le nom ="+posName
                    +" n'a ete trouve dans la BDD ", ErrorCode.POINTOFSALE_NOT_FOUND);
        }
    }

    @Override
    public Boolean isPointofsaleExistWithId(Long posId) {
        if(posId == null){
            log.error("posId is null");
            throw new NullArgumentException("le posId passe en argument de la methode est null");
        }
        Optional<Pointofsale> optionalPointofsale = posRepository.findPointofsaleById(posId);
        return optionalPointofsale.isPresent()?true:false;
    }

    @Override
    public Boolean isPointofsaleDeleteable(Long posId) {
        return null;
    }

    @Override
    public Boolean isPosUnique(String posName, EnterpriseDto entDto) {
        if(!StringUtils.hasLength(posName)){
            log.error("Le pointofsale posName is null");
            throw new NullArgumentException("le posName passe en argument de la methode est null");
        }
        if(entDto == null){
            log.error("Enterprise is null");
            throw new NullArgumentException("L'entreprise a laquelle appartient le Pos est null");
        }
        Optional<Pointofsale> optionalPointofsale = posRepository.findPointofsaleOfEnterpriseByName(posName,
                entDto.getId());
        return optionalPointofsale.isPresent()?false:true;
    }

    @Override
    public Boolean deletePosById(Long posId) {
        if(posId == null){
            log.error("Pointofsale posId is null");
            throw new NullArgumentException("le PosId passe en argument de la methode est null");
        }
        Optional<Pointofsale> optionalPointofsale = posRepository.findPointofsaleById(posId);
        if(optionalPointofsale.isPresent()){
            posRepository.delete(optionalPointofsale.get());
            return true;
        }
        return false;
    }

    @Override
    public Boolean deletePosInEnterpriseByName(String posName, EnterpriseDto entDto) {
        if(entDto == null){
            log.error("L'entreprise entDto is null");
            throw new NullArgumentException("le entDto passe en argument de la methode est null");
        }
        if(!StringUtils.hasLength(posName)){
            log.error("Le pointofsale posName is null");
            throw new NullArgumentException("le posName passe en argument de la methode est null");
        }
        Optional<Pointofsale> optionalPointofsale = posRepository.findPointofsaleOfEnterpriseByName(posName,
                entDto.getId());
        if(optionalPointofsale.isPresent()){
            posRepository.delete(optionalPointofsale.get());
            return true;
        }
        return false;

    }


    @Override
    public List<CurrencyDto> listofConvertibleCurrency(PointofsaleDto posDto) {
        List<CurrencyDto> currencyDtoList = new ArrayList<>();
        if(posDto == null){
            log.error("posDto is null");
            throw new NullArgumentException("le posDto passe en argument de la methode est null");
        }
        /********
         * Est ce au'un Currency par defaut existe?
         * Si oui faut d'abord le rechercher. puis
         * Il faut faire la liste de toutes les Currency dans le systeme
         * Puis pour chacun des Currency il faut regarder si il y a une regle
         * de conversion avec le Currency par defaut du Pointofsale. si c'est le cas
         * alors on releve sinon on passe.
         */
        //System.out.println("isDefaultCurrencyPresent(posDto.getId()) ==> "+isDefaultCurrencyPresent(posDto.getId()));
        if(isDefaultCurrencyPresent(posDto.getId())){
            CurrencyDto defaultCurrency = posDto.getPosCurrencyDto();
            List<Currency> currencyList = currencyRepository.findAll();
            System.out.println("ALL currencyList ===> "+currencyList);
            for (Currency curr : currencyList){
                Optional<CurrencyConversion> optionalcurconvDto = currencyConversionRepository.findConversionRuleBetweenViceVersa(
                        defaultCurrency.getId(), curr.getId());

                if(optionalcurconvDto.isPresent()){
                    CurrencyConversionDto curconvDto = CurrencyConversionDto.fromEntity(optionalcurconvDto.get());
                    Boolean exist1 = false;
                    Boolean exist2 = false;
                    for(CurrencyDto curDto : currencyDtoList){
                        if(curDto.getId().equals(curconvDto.getCurrencyDestinationDto().getId())){
                            exist1 = true;
                        }
                        if(curDto.getId().equals(curconvDto.getCurrencySourceDto().getId())){
                            exist2 = true;
                        }
                    }
                   if(!exist1){
                       currencyDtoList.add(curconvDto.getCurrencyDestinationDto());
                   }
                    if(!exist2){
                        currencyDtoList.add(curconvDto.getCurrencySourceDto());
                    }
                }
            }
        }
        return currencyDtoList;
    }

    public Boolean isDefaultCurrencyPresent(Long posId){
        if(posId == null){
            log.error("posId is null");
            throw new NullArgumentException("le posId passe en argument de la methode est null");
        }
        //On recherche le pointofsale dont l'id est passe en parametre
        Optional<Pointofsale> optionalPointofsale = posRepository.findPointofsaleById(posId);
        if(!optionalPointofsale.isPresent()){
           throw new EntityNotFoundException("Aucun Pointofsale avec l'id ="+posId
                   +" n'a ete trouve dans la BDD ", ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        //Ici on a bel et bien retrouver le Pointofsale
        Optional<Currency> optionalCurrency = Optional.ofNullable(optionalPointofsale.get().getPosCurrency());
        return optionalCurrency.isPresent()?true:false;
    }

    @Override
    public CurrencyDto findDefaultCurrency(PointofsaleDto posDto) {
        if(this.isDefaultCurrencyPresent(posDto.getId())){
            return posDto.getPosCurrencyDto();
        }
        return null;
    }
}
