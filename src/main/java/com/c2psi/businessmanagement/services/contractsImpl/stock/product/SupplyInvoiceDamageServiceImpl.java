package com.c2psi.businessmanagement.services.contractsImpl.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.SupplyInvoiceCapsuleDto;
import com.c2psi.businessmanagement.dtos.stock.product.SupplyInvoiceDamageDto;
import com.c2psi.businessmanagement.dtos.stock.product.SupplyInvoiceDamageDto;
import com.c2psi.businessmanagement.dtos.stock.product.SupplyInvoiceDamageDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.*;
import com.c2psi.businessmanagement.repositories.pos.pos.PointofsaleRepository;
import com.c2psi.businessmanagement.repositories.pos.userbm.UserBMRepository;
import com.c2psi.businessmanagement.repositories.stock.product.SupplyInvoiceDamageRepository;
import com.c2psi.businessmanagement.repositories.stock.provider.ProviderRepository;
import com.c2psi.businessmanagement.services.contracts.stock.product.SupplyInvoiceDamageService;
import com.c2psi.businessmanagement.validators.stock.product.SupplyInvoiceDamageValidator;
import com.c2psi.businessmanagement.validators.stock.product.SupplyInvoiceDamageValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value="SupplyInvoiceDamageServiceV1")
@Slf4j
@Transactional
public class SupplyInvoiceDamageServiceImpl implements SupplyInvoiceDamageService {

    private UserBMRepository userBMRepository;
    private ProviderRepository providerRepository;
    private PointofsaleRepository pointofsaleRepository;
    private SupplyInvoiceDamageRepository supplyInvoiceDamageRepository;

    @Autowired
    public SupplyInvoiceDamageServiceImpl(UserBMRepository userBMRepository, ProviderRepository providerRepository,
                                          PointofsaleRepository pointofsaleRepository,
                                          SupplyInvoiceDamageRepository supplyInvoiceDamageRepository) {
        this.userBMRepository = userBMRepository;
        this.providerRepository = providerRepository;
        this.pointofsaleRepository = pointofsaleRepository;
        this.supplyInvoiceDamageRepository = supplyInvoiceDamageRepository;
    }

    @Override
    public SupplyInvoiceDamageDto saveSupplyInvoiceDamage(SupplyInvoiceDamageDto sidamDto) {
        /********************************************
         * On valide le parametre pris en argument
         */
        List<String> errors = SupplyInvoiceDamageValidator.validate(sidamDto);
        if(!errors.isEmpty()){
            log.error("Entity sidamDto not valid {}", sidamDto);
            throw new InvalidEntityException("Le sidamDto passe en argument n'est pas valide: "+errors,
                    ErrorCode.SUPPLYINVOICEDAMAGE_NOT_VALID, errors);
        }

        /*********************************************************************
         * Verifier que l'id du pointofsale n'est pas null et quil identifie
         * vraiment un pointofsale
         */
        if(sidamDto.getSidamPosDto().getId() == null){
            log.error("The id of the pointofsale in the request is null");
            throw new InvalidEntityException("L'Id du pointofsale dans le sidamDto est null ",
                    ErrorCode.SUPPLYINVOICEDAMAGE_NOT_VALID);
        }
        //L'id du pointofsale n'est pas null on verifie si c'est vraiment un pos dans la BD
        Optional<Pointofsale> optionalPointofsale = pointofsaleRepository.findPointofsaleById(
                sidamDto.getSidamPosDto().getId());
        if(!optionalPointofsale.isPresent()){
            log.error("There is no pointofsale in the DB with the precised id {}", sidamDto.getSidamPosDto().getId());
            throw new InvalidEntityException("Aucun Pointofsale n'existe avec l'id precise dans la requete ",
                    ErrorCode.SUPPLYINVOICEDAMAGE_NOT_VALID);
        }

        /******************************************************************
         * Verifier que l'id du userbm n'est pas null et quil identifie
         * vraiment un userbm
         */
        if(sidamDto.getSidamUserbmDto().getId() == null){
            log.error("The id of the userBM in the request is null");
            throw new InvalidEntityException("L'Id du UserBM dans le sidamDto est null ",
                    ErrorCode.SUPPLYINVOICEDAMAGE_NOT_VALID);
        }
        //L'id du userbm n'est pas null on verifie si c'est vraiment un userbm dans la BD
        Optional<UserBM> optionalUserBM = userBMRepository.findUserBMById(
                sidamDto.getSidamUserbmDto().getId());
        if(!optionalUserBM.isPresent()){
            log.error("There is no userbm in the DB with the precised id {}", sidamDto.getSidamUserbmDto().getId());
            throw new InvalidEntityException("Aucun UserBM n'existe avec l'id precise dans la requete ",
                    ErrorCode.SUPPLYINVOICEDAMAGE_NOT_VALID);
        }

        /******************************************************************
         * Verifier que l'id du provider n'est pas null et quil identifie
         * vraiment un provider
         */
        if(sidamDto.getSidamProviderDto().getId() == null){
            log.error("The id of the provider in the request is null");
            throw new InvalidEntityException("L'Id du provider dans le sidamDto est null ",
                    ErrorCode.SUPPLYINVOICEDAMAGE_NOT_VALID);
        }
        //L'id du provider n'est pas null on verifie si c'est vraiment un userbm dans la BD
        Optional<Provider> optionalProvider = providerRepository.findProviderById(
                sidamDto.getSidamProviderDto().getId());
        if(!optionalProvider.isPresent()){
            log.error("There is no provider in the DB with the precised id {}", sidamDto.getSidamProviderDto().getId());
            throw new InvalidEntityException("Aucun provider n'existe avec l'id precise dans la requete ",
                    ErrorCode.SUPPLYINVOICEDAMAGE_NOT_VALID);
        }

        /********************************************************************************************
         * On verifie que le pointofsale dans la requete est le meme que celui du userbm et provider
         */
        if(!optionalPointofsale.get().getId().equals(optionalUserBM.get().getBmPos().getId())){
            log.error("The pointofsale in the sidamDto must be the same with the one of the userBM");
            throw new InvalidEntityException("Le pointofsale dans la requete doit etre le meme que celui du UserBM " +
                    "precise precise dans le sidamDto ", ErrorCode.SUPPLYINVOICEDAMAGE_NOT_VALID);
        }

        if(!optionalPointofsale.get().getId().equals(optionalProvider.get().getProviderPos().getId())){
            log.error("The pointofsale in the sidamDto must be the same with the one of the provider");
            throw new InvalidEntityException("Le pointofsale dans la requete doit etre le meme que celui du provider " +
                    "precise precise dans le sidamDto ", ErrorCode.SUPPLYINVOICEDAMAGE_NOT_VALID);
        }

        if(!optionalProvider.get().getProviderPos().getId().equals(optionalUserBM.get().getBmPos().getId())){
            log.error("The Provider in the sidamDto must be the same with the one of the UserBM");
            throw new InvalidEntityException("Le Provider dans la requete doit etre le meme que celui du UserBM " +
                    "precise precise dans le sidamDto ", ErrorCode.SUPPLYINVOICEDAMAGE_NOT_VALID);
        }

        /**********************************************************************************************
         * On verifie quil y a pas un autre SupplyInvoiceDamage avec le meme code deja enregistre en BD
         * pour le meme pointofsale
         */
        if(!isSupplyInvoiceDamageUnique(sidamDto.getSidamCode(), sidamDto.getSidamPosDto().getId())){
            log.error("There is a supplyinvoiceDamage already register in the DB with the same code in the same pointofsale");
            throw new DuplicateEntityException("Une facture supplyinvoiceDamage est deja existante dans la BD avec ce code",
                    ErrorCode.SUPPLYINVOICEDAMAGE_DUPLICATED);
        }

        log.error("After all verification, the sidamDto {} can be save in the DB without any problem", sidamDto);
        return SupplyInvoiceDamageDto.fromEntity(
                supplyInvoiceDamageRepository.save(
                        SupplyInvoiceDamageDto.toEntity(sidamDto)
                )
        );
    }

    @Override
    public SupplyInvoiceDamageDto updateSupplyInvoiceDamage(SupplyInvoiceDamageDto sidamDto) {
        /********************************************
         * On valide le parametre pris en argument
         */
        List<String> errors = SupplyInvoiceDamageValidator.validate(sidamDto);
        if(!errors.isEmpty()){
            log.error("Entity sidamDto not valid {}", sidamDto);
            throw new InvalidEntityException("Le siDamageDto passe en argument n'est pas valide: "+errors,
                    ErrorCode.SUPPLYINVOICEDAMAGE_NOT_VALID, errors);
        }

        /**********************************************************************************
         * On ne peut pas lors de la mise a jour d'une entite supplyInvoiceDamage modifier
         * ni le userbm, ni le pointofsale
         */
        if(sidamDto.getId() == null){
            log.error("The id of the siDamageDto to modify is null");
            throw new NullArgumentException("Le siDamageDto passe en argument est null");
        }
        Optional<SupplyInvoiceDamage> optionalSupplyInvoiceDamage = supplyInvoiceDamageRepository.findSupplyInvoiceDamageById(
                sidamDto.getId());
        if(!optionalSupplyInvoiceDamage.isPresent()){
            log.error("There is no SupplyInvoiceDamage in the DB with the id precised");
            throw new InvalidEntityException("Aucun SupplyInvoiceDamage avec l'id precise en BD ",
                    ErrorCode.SUPPLYINVOICEDAMAGE_NOT_FOUND);
        }
        //On recupere donc le supplyInvoiceDamage a modifier
        SupplyInvoiceDamage supplyInvoiceDamageToUpdate = optionalSupplyInvoiceDamage.get();

        /***************************************************************************
         * On va se rassurer que ce n'est pas le userbm quon veut modifier
         */
        if(!supplyInvoiceDamageToUpdate.getSidamUserbm().getId().equals(sidamDto.getSidamUserbmDto().getId())){
            log.error("It is not possible to update the userbm responsible of the registration of that siDamageDto");
            throw new InvalidEntityException("Il n'est pas possible de modifier le Userbm associe au siDamageDto ",
                    ErrorCode.SUPPLYINVOICEDAMAGE_NOT_VALID);
        }

        /**************************************************************************
         * On va se rassurer que ce n'est pas le pointofsale qu'on veut modifier
         */
        if(!supplyInvoiceDamageToUpdate.getSidamPos().getId().equals(sidamDto.getSidamPosDto().getId())){
            log.error("It is not possible to update the pointofsale of that siDamageDto");
            throw new InvalidEntityException("Il n'est pas possible de modifier le Pointofsale du siDamageDto ",
                    ErrorCode.SUPPLYINVOICEDAMAGE_NOT_VALID);
        }

        /**************************************************************************************
         * On va se rassurer si cest le provider quon veut modifier et si cest le cas on va se
         * rassurer que le nouveau existe vraiment en BD
         */
        if(!supplyInvoiceDamageToUpdate.getSidamProvider().getId().equals(sidamDto.getSidamProviderDto().getId())){
            /*
            Alors cest le provider qu'on veut modifier
             */
            /*******************************************
             * On va donc se rassurer que le provider existe vraiment
             */
            Optional<Provider> optionalProvider = providerRepository.findProviderById(
                    sidamDto.getSidamProviderDto().getId());
            if(!optionalProvider.isPresent()){
                log.error("There is no provider in the DB with the precised id {}", sidamDto.getSidamProviderDto().getId());
                throw new InvalidEntityException("Aucun provider n'existe avec l'id precise dans la requete ",
                        ErrorCode.SUPPLYINVOICEDAMAGE_NOT_VALID);
            }
            //La on est sur que le nouveau provider existe.
            Provider newProvider = optionalProvider.get();
            /********************
             * On se rassure donc que le nouveau provider est dans le meme pointofsale
             */
            if(!newProvider.getProviderPos().getId().equals(sidamDto.getSidamPosDto().getId())){
                log.error("The new provider don't belong to the same pointofsale");
                throw new InvalidEntityException("Le nouveau provider n'est pas dans le meme pointofsale que le " +
                        "siDamageDto et cette mise a jour ne peut donc etre effectue", ErrorCode.SUPPLYINVOICEDAMAGE_NOT_VALID);
            }
            //Ici on fait donc la mise a jour du provider dans le siDamage a update
            supplyInvoiceDamageToUpdate.setSidamProvider(newProvider);
        }

        /*******************************************************************************
         * On va verifier si cest le siDamageCode quon veut modifier et si cest le cas il
         * se rassurer qu'il ny aura pas de duplicata
         */
        if(!supplyInvoiceDamageToUpdate.getSidamCode().equals(sidamDto.getSidamCode())){
            /*********************************
             *  Alors cest le siDamageCode quon veut modifier
             *  on se rassure donc quil ny aura pas de duplicata
             */
            if(!isSupplyInvoiceDamageUnique(sidamDto.getSidamCode(), sidamDto.getSidamPosDto().getId())){
                log.error("There is a supplyinvoiceDamage already register in the DB with the same code in the same pointofsale");
                throw new DuplicateEntityException("Une facture supplyinvoiceDamage est deja existante dans la BD avec ce code",
                        ErrorCode.SUPPLYINVOICEDAMAGE_DUPLICATED);
            }
            //Ici on est sur quil y aura pas de duplicata
            supplyInvoiceDamageToUpdate.setSidamCode(sidamDto.getSidamCode());
        }

        /*****
         * Pour le reste on peut modifier sans souci cest a dire
         *     String sidamComment;
         *     String sidamPicture;
         *     Instant sidamDeliveryDate;
         *     Instant sidamInvoicingDate;
         *     BigDecimal sidamAmountexpected;
         *     BigDecimal sidamAmountpaid;
         *     BigDecimal sidamTotalcolis;
         *     ArticleDto.fromEntity(articleRepository.save(articleToUpdate))
         */
        supplyInvoiceDamageToUpdate.setSidamComment(sidamDto.getSidamComment());
        supplyInvoiceDamageToUpdate.setSidamPicture(sidamDto.getSidamPicture());
        supplyInvoiceDamageToUpdate.setSidamDeliveryDate(sidamDto.getSidamDeliveryDate());
        supplyInvoiceDamageToUpdate.setSidamInvoicingDate(sidamDto.getSidamInvoicingDate());
        supplyInvoiceDamageToUpdate.setSidamTotalDamToChange(sidamDto.getSidamTotalDamToChange());
        supplyInvoiceDamageToUpdate.setSidamTotalDamChange(sidamDto.getSidamTotalDamChange());
        supplyInvoiceDamageToUpdate.setSidamTotalcolis(sidamDto.getSidamTotalcolis());

        log.info("All verification have been done and the updated can be done normally ");

        return SupplyInvoiceDamageDto.fromEntity(supplyInvoiceDamageRepository.save(supplyInvoiceDamageToUpdate));
    }

    @Override
    public Boolean isSupplyInvoiceDamageUnique(String sidamCode, Long posId) {
        if(posId == null){
            log.error("The posId sent is null");
            throw new NullArgumentException("Le posId envoye a la methode est null");
        }

        if(!StringUtils.hasLength(sidamCode)){
            log.error("The sidamcode is null or empty");
            throw new NullArgumentException("Le code facture envoye est soit null soit vide");
        }

        Optional<SupplyInvoiceDamage> optionalSupplyInvoiceDamage = supplyInvoiceDamageRepository.
                findSupplyInvoiceDamageByCodeAndPos(sidamCode, posId);

        return optionalSupplyInvoiceDamage.isEmpty();
    }

    @Override
    public Boolean isSupplyInvoiceDamageDeleteable(Long sidamId) {
        return true;
    }

    @Override
    public Boolean deleteSupplyInvoiceDamageById(Long sidamId) {
        if(sidamId == null){
            log.error("The argument sidamId is null when calling the method");
            throw new NullArgumentException("L'argument de la methode est null pendant son appel");
        }
        Optional<SupplyInvoiceDamage> optionalSupplyInvoiceDamage = supplyInvoiceDamageRepository.findSupplyInvoiceDamageById(
                sidamId);
        if(!optionalSupplyInvoiceDamage.isPresent()){
            log.error("There is no supplyinvoicedamage with the precise id {}", sidamId);
            throw new EntityNotFoundException("Aucun supplyinvoicedam n'existe en BD avec l'id precise ",
                    ErrorCode.SUPPLYINVOICEDAMAGE_NOT_FOUND);
        }
        if(!isSupplyInvoiceDamageDeleteable(sidamId)){
            log.error("The supplyinvoicedamage is not yet deleteable");
            throw new EntityNotDeleteableException("On ne peut supprimer le supplyinvoicedamage en ce moment de la BD",
                    ErrorCode.SUPPLYINVOICEDAMAGE_NOT_DELETEABLE);
        }
        supplyInvoiceDamageRepository.delete(optionalSupplyInvoiceDamage.get());
        return true;
    }

    @Override
    public SupplyInvoiceDamageDto findSupplyInvoiceDamageById(Long sidamId) {
        if(sidamId == null){
            log.error("The argument sidamId is null when calling the method");
            throw new NullArgumentException("L'argument de la methode est null pendant son appel");
        }
        Optional<SupplyInvoiceDamage> optionalSupplyInvoiceDamage = supplyInvoiceDamageRepository.
                findSupplyInvoiceDamageById(sidamId);
        if(!optionalSupplyInvoiceDamage.isPresent()){
            log.error("There is no supplyinvoicedamage with the precise id {}", sidamId);
            throw new EntityNotFoundException("Aucun supplyinvoicedamage n'existe en BD avec l'id precise ",
                    ErrorCode.SUPPLYINVOICEDAMAGE_NOT_FOUND);
        }
        return SupplyInvoiceDamageDto.fromEntity(optionalSupplyInvoiceDamage.get());
    }

    @Override
    public SupplyInvoiceDamageDto findSupplyInvoiceDamageByCode(String sidamCode, Long posId) {
        if(posId == null){
            log.error("The argument posId is null when calling the method");
            throw new NullArgumentException("L'argument de la methode est null pendant son appel");
        }
        if(!StringUtils.hasLength(sidamCode)){
            log.error("The precised code is empty of null");
            throw new NullArgumentException("Le code de facture precise est soit null soit vide lors de l'appel");
        }
        Optional<SupplyInvoiceDamage> optionalSupplyInvoiceDamage = supplyInvoiceDamageRepository.
                findSupplyInvoiceDamageByCodeAndPos(sidamCode, posId);
        if(!optionalSupplyInvoiceDamage.isPresent()){
            log.error("There is no supplyinvoicedamage with the precise code {} in the pointofsale with the id {}",
                    sidamCode, posId);
            throw new EntityNotFoundException("Aucun supplyinvoicedamage n'existe en BD avec le code precise dans le " +
                    "pointofsale d'id precise", ErrorCode.SUPPLYINVOICEDAMAGE_NOT_FOUND);
        }

        return SupplyInvoiceDamageDto.fromEntity(optionalSupplyInvoiceDamage.get());
    }

    @Override
    public List<SupplyInvoiceDamageDto> findAllSupplyInvoiceDamageBetween(Long posId, Instant startDate,
                                                                          Instant endDate) {
        if(posId == null){
            log.error("The argument posId is null when calling the method");
            throw new NullArgumentException("L'argument posId de la methode est null pendant son appel");
        }
        Optional<List<SupplyInvoiceDamage>> optionalSupplyInvoiceDamageList = supplyInvoiceDamageRepository.
                findAllSupplyInvoiceDamageofPosBetween(posId, startDate, endDate);
        if(!optionalSupplyInvoiceDamageList.isPresent()){
            log.error("There is no pointofsale with the id {} in the DB", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id passe en argument ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalSupplyInvoiceDamageList.get().stream().map(SupplyInvoiceDamageDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<SupplyInvoiceDamageDto> findPageSupplyInvoiceDamageBetween(Long posId, Instant startDate, Instant endDate,
                                                                           int pagenum, int pagesize) {
        if(posId == null){
            log.error("The argument posId is null when calling the method");
            throw new NullArgumentException("L'argument posId de la methode est null pendant son appel");
        }
        Optional<Page<SupplyInvoiceDamage>> optionalSupplyInvoiceDamagePage = supplyInvoiceDamageRepository.
                findPageSupplyInvoiceDamageofPosBetween(posId, startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalSupplyInvoiceDamagePage.isPresent()){
            log.error("There is no pointofsale with the id {} in the DB", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id passe en argument ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }

        return optionalSupplyInvoiceDamagePage.get().map(SupplyInvoiceDamageDto::fromEntity);
    }

    @Override
    public List<SupplyInvoiceDamageDto> findAllSupplyInvoiceDamageofUserbmBetween(Long posId, Long userbmId,
                                                                                  Instant startDate, Instant endDate) {
        if(posId == null){
            log.error("The argument posId is null when calling the method");
            throw new NullArgumentException("L'argument posId de la methode est null pendant son appel");
        }
        if(userbmId == null){
            log.error("The argument userbmId is null when calling the method");
            throw new NullArgumentException("L'argument  userbmId de la methode est null pendant son appel");
        }
        Optional<List<SupplyInvoiceDamage>> optionalSupplyInvoiceDamageList = supplyInvoiceDamageRepository.
                findAllSupplyInvoiceDamageofPosandUserbmBetween(posId, userbmId, startDate, endDate);
        if(!optionalSupplyInvoiceDamageList.isPresent()){
            log.error("There is no pointofsale with the id {} in the DB", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id passe en argument ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalSupplyInvoiceDamageList.get().stream().map(SupplyInvoiceDamageDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<SupplyInvoiceDamageDto> findPageSupplyInvoiceDamageofUserbmBetween(Long posId, Long userbmId,
                                                                                   Instant startDate, Instant endDate,
                                                                                   int pagenum, int pagesize) {
        if(posId == null){
            log.error("The argument posId is null when calling the method");
            throw new NullArgumentException("L'argument posId de la methode est null pendant son appel");
        }
        if(userbmId == null){
            log.error("The argument userbmId is null when calling the method");
            throw new NullArgumentException("L'argument  userbmId de la methode est null pendant son appel");
        }
        Optional<Page<SupplyInvoiceDamage>> optionalSupplyInvoiceDamagePage = supplyInvoiceDamageRepository.
                findPageSupplyInvoiceDamageofPosandUserbmBetween(posId, userbmId, startDate, endDate,
                        PageRequest.of(pagenum, pagesize));
        if(!optionalSupplyInvoiceDamagePage.isPresent()){
            log.error("There is no pointofsale with the id {} in the DB", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id passe en argument ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalSupplyInvoiceDamagePage.get().map(SupplyInvoiceDamageDto::fromEntity);
    }

    @Override
    public List<SupplyInvoiceDamageDto> findAllSupplyInvoiceDamageofProviderBetween(Long posId, Long providerId,
                                                                                    Instant startDate, Instant endDate) {
        if(posId == null){
            log.error("The argument posId is null when calling the method");
            throw new NullArgumentException("L'argument posId de la methode est null pendant son appel");
        }
        if(providerId == null){
            log.error("The argument providerId is null when calling the method");
            throw new NullArgumentException("L'argument  providerId de la methode est null pendant son appel");
        }
        Optional<List<SupplyInvoiceDamage>> optionalSupplyInvoiceDamageList = supplyInvoiceDamageRepository.
                findAllSupplyInvoiceDamageofPosandProviderBetween(posId, providerId, startDate, endDate);
        if(!optionalSupplyInvoiceDamageList.isPresent()){
            log.error("There is no pointofsale with the id {} in the DB", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id passe en argument ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalSupplyInvoiceDamageList.get().stream().map(SupplyInvoiceDamageDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<SupplyInvoiceDamageDto> findPageSupplyInvoiceDamageofProviderBetween(Long posId, Long providerId,
                                                                                     Instant startDate, Instant endDate,
                                                                                     int pagenum, int pagesize) {
        if(posId == null){
            log.error("The argument posId is null when calling the method");
            throw new NullArgumentException("L'argument posId de la methode est null pendant son appel");
        }
        if(providerId == null){
            log.error("The argument providerId is null when calling the method");
            throw new NullArgumentException("L'argument  providerId de la methode est null pendant son appel");
        }
        Optional<Page<SupplyInvoiceDamage>> optionalSupplyInvoiceDamagePage = supplyInvoiceDamageRepository.
                findPageSupplyInvoiceDamageofPosandProviderBetween(posId, providerId, startDate, endDate,
                        PageRequest.of(pagenum, pagesize));
        if(!optionalSupplyInvoiceDamagePage.isPresent()){
            log.error("There is no pointofsale with the id {} in the DB", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id passe en argument ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalSupplyInvoiceDamagePage.get().map(SupplyInvoiceDamageDto::fromEntity);
    }

    @Override
    public List<SupplyInvoiceDamageDto> findAllSupplyInvoiceDamageofProviderAndUserbmBetween(Long posId, Long providerId,
                                                                                             Long userbmId,
                                                                                             Instant startDate,
                                                                                             Instant endDate) {
        if(posId == null){
            log.error("The argument posId is null when calling the method");
            throw new NullArgumentException("L'argument posId de la methode est null pendant son appel");
        }
        if(providerId == null){
            log.error("The argument providerId is null when calling the method");
            throw new NullArgumentException("L'argument  providerId de la methode est null pendant son appel");
        }
        if(userbmId == null){
            log.error("The argument userbmId is null when calling the method");
            throw new NullArgumentException("L'argument  userbmId de la methode est null pendant son appel");
        }
        Optional<List<SupplyInvoiceDamage>> optionalSupplyInvoiceDamageList = supplyInvoiceDamageRepository.
                findAllSupplyInvoiceDamageofPosandProviderAndUserbmBetween(posId, providerId, userbmId, startDate,
                        endDate);
        if(!optionalSupplyInvoiceDamageList.isPresent()){
            log.error("There is no pointofsale with the id {} in the DB", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id passe en argument ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalSupplyInvoiceDamageList.get().stream().map(SupplyInvoiceDamageDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<SupplyInvoiceDamageDto> findPageSupplyInvoiceDamageofProviderAndUserbmBetween(Long posId, Long providerId,
                                                                                              Long userbmId,
                                                                                              Instant startDate,
                                                                                              Instant endDate,
                                                                                              int pagenum, int pagesize) {
        if(posId == null){
            log.error("The argument posId is null when calling the method");
            throw new NullArgumentException("L'argument posId de la methode est null pendant son appel");
        }
        if(providerId == null){
            log.error("The argument providerId is null when calling the method");
            throw new NullArgumentException("L'argument  providerId de la methode est null pendant son appel");
        }
        if(userbmId == null){
            log.error("The argument userbmId is null when calling the method");
            throw new NullArgumentException("L'argument  userbmId de la methode est null pendant son appel");
        }
        Optional<Page<SupplyInvoiceDamage>> optionalSupplyInvoiceDamagePage = supplyInvoiceDamageRepository.
                findPageSupplyInvoiceDamageofPosandProviderAndUserbmBetween(posId, providerId, userbmId, startDate,
                        endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalSupplyInvoiceDamagePage.isPresent()){
            log.error("There is no pointofsale with the id {} in the DB", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id passe en argument ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalSupplyInvoiceDamagePage.get().map(SupplyInvoiceDamageDto::fromEntity);
    }
}
