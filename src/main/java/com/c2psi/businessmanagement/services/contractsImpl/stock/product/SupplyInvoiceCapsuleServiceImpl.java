package com.c2psi.businessmanagement.services.contractsImpl.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.SupplyInvoiceCapsuleDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.*;
import com.c2psi.businessmanagement.repositories.pos.pos.PointofsaleRepository;
import com.c2psi.businessmanagement.repositories.pos.userbm.UserBMRepository;
import com.c2psi.businessmanagement.repositories.stock.product.SupplyInvoiceCapsuleRepository;
import com.c2psi.businessmanagement.repositories.stock.provider.ProviderRepository;
import com.c2psi.businessmanagement.services.contracts.stock.product.SupplyInvoiceCapsuleService;
import com.c2psi.businessmanagement.validators.stock.product.SupplyInvoiceCapsuleValidator;
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

@Service(value="SupplyInvoiceCapsuleServiceV1")
@Slf4j
@Transactional
public class SupplyInvoiceCapsuleServiceImpl implements SupplyInvoiceCapsuleService {

    private UserBMRepository userBMRepository;
    private ProviderRepository providerRepository;
    private PointofsaleRepository pointofsaleRepository;
    private SupplyInvoiceCapsuleRepository supplyInvoiceCapsuleRepository;

    @Autowired
    public SupplyInvoiceCapsuleServiceImpl(UserBMRepository userBMRepository, ProviderRepository providerRepository,
                                           PointofsaleRepository pointofsaleRepository,
                                           SupplyInvoiceCapsuleRepository supplyInvoiceCapsuleRepository) {
        this.userBMRepository = userBMRepository;
        this.providerRepository = providerRepository;
        this.pointofsaleRepository = pointofsaleRepository;
        this.supplyInvoiceCapsuleRepository = supplyInvoiceCapsuleRepository;
    }

    @Override
    public SupplyInvoiceCapsuleDto saveSupplyInvoiceCapsule(SupplyInvoiceCapsuleDto sicapsDto) {

        /********************************************
         * On valide le parametre pris en argument
         */
        List<String> errors = SupplyInvoiceCapsuleValidator.validate(sicapsDto);
        if(!errors.isEmpty()){
            log.error("Entity sicapsDto not valid {}", sicapsDto);
            throw new InvalidEntityException("Le sicapsDto passe en argument n'est pas valide: "+errors,
                    ErrorCode.SUPPLYINVOICECAPSULE_NOT_VALID, errors);
        }

        /*********************************************************************
         * Verifier que l'id du pointofsale n'est pas null et quil identifie
         * vraiment un pointofsale
         */
        if(sicapsDto.getSicapsPosId() == null){
            log.error("The id of the pointofsale in the request is null");
            throw new InvalidEntityException("L'Id du pointofsale dans le sicapsDto est null ",
                    ErrorCode.SUPPLYINVOICECAPSULE_NOT_VALID);
        }
        //L'id du pointofsale n'est pas null on verifie si c'est vraiment un pos dans la BD
        Optional<Pointofsale> optionalPointofsale = pointofsaleRepository.findPointofsaleById(
                sicapsDto.getSicapsPosId());
        if(!optionalPointofsale.isPresent()){
            log.error("There is no pointofsale in the DB with the precised id {}", sicapsDto.getSicapsPosId());
            throw new InvalidEntityException("Aucun Pointofsale n'existe avec l'id precise dans la requete ",
                    ErrorCode.SUPPLYINVOICECAPSULE_NOT_VALID);
        }

        /******************************************************************
         * Verifier que l'id du userbm n'est pas null et quil identifie
         * vraiment un userbm
         */
        if(sicapsDto.getSicapsUserbmDto().getId() == null){
            log.error("The id of the userBM in the request is null");
            throw new InvalidEntityException("L'Id du UserBM dans le sicapsDto est null ",
                    ErrorCode.SUPPLYINVOICECAPSULE_NOT_VALID);
        }
        //L'id du userbm n'est pas null on verifie si c'est vraiment un userbm dans la BD
        Optional<UserBM> optionalUserBM = userBMRepository.findUserBMById(
                sicapsDto.getSicapsUserbmDto().getId());
        if(!optionalUserBM.isPresent()){
            log.error("There is no userbm in the DB with the precised id {}", sicapsDto.getSicapsUserbmDto().getId());
            throw new InvalidEntityException("Aucun UserBM n'existe avec l'id precise dans la requete ",
                    ErrorCode.SUPPLYINVOICECAPSULE_NOT_VALID);
        }

        /******************************************************************
         * Verifier que l'id du provider n'est pas null et quil identifie
         * vraiment un provider
         */
        if(sicapsDto.getSicapsProviderDto().getId() == null){
            log.error("The id of the provider in the request is null");
            throw new InvalidEntityException("L'Id du provider dans le sicapsDto est null ",
                    ErrorCode.SUPPLYINVOICECAPSULE_NOT_VALID);
        }
        //L'id du provider n'est pas null on verifie si c'est vraiment un userbm dans la BD
        Optional<Provider> optionalProvider = providerRepository.findProviderById(
                sicapsDto.getSicapsProviderDto().getId());
        if(!optionalProvider.isPresent()){
            log.error("There is no provider in the DB with the precised id {}", sicapsDto.getSicapsProviderDto().getId());
            throw new InvalidEntityException("Aucun provider n'existe avec l'id precise dans la requete ",
                    ErrorCode.SUPPLYINVOICECAPSULE_NOT_VALID);
        }

        /********************************************************************************************
         * On verifie que le pointofsale dans la requete est le meme que celui du userbm et provider
         */
        if(!optionalPointofsale.get().getId().equals(optionalUserBM.get().getBmPosId())){
            log.error("The pointofsale in the sicapsDto must be the same with the one of the userBM");
            throw new InvalidEntityException("Le pointofsale dans la requete doit etre le meme que celui du UserBM " +
                    "precise precise dans le sicapsDto ", ErrorCode.SUPPLYINVOICECAPSULE_NOT_VALID);
        }

        if(!optionalPointofsale.get().getId().equals(optionalProvider.get().getProviderPosId())){
            log.error("The pointofsale in the sicapsDto must be the same with the one of the provider");
            throw new InvalidEntityException("Le pointofsale dans la requete doit etre le meme que celui du provider " +
                    "precise precise dans le sicapsDto ", ErrorCode.SUPPLYINVOICECAPSULE_NOT_VALID);
        }

        if(!optionalProvider.get().getProviderPosId().equals(optionalUserBM.get().getBmPosId())){
            log.error("The Provider in the sicapsDto must be the same with the one of the UserBM");
            throw new InvalidEntityException("Le Provider dans la requete doit etre le meme que celui du UserBM " +
                    "precise precise dans le sicapsDto ", ErrorCode.SUPPLYINVOICECAPSULE_NOT_VALID);
        }

        /**********************************************************************************************
         * On verifie quil y a pas un autre SupplyInvoiceCapsule avec le meme code deja enregistre en BD
         * pour le meme pointofsale
         */
        if(!isSupplyInvoiceCapsuleUnique(sicapsDto.getSicapsCode(), sicapsDto.getSicapsPosId())){
            log.error("There is a supplyinvoicecapsule already register in the DB with the same code in the same pointofsale");
            throw new DuplicateEntityException("Une facture supplyinvoicecapsule est deja existante dans la BD avec ce code",
                    ErrorCode.SUPPLYINVOICECAPSULE_DUPLICATED);
        }

        log.error("After all verification, the sicapsDto {} can be save in the DB without any problem", sicapsDto);
        return SupplyInvoiceCapsuleDto.fromEntity(
                supplyInvoiceCapsuleRepository.save(
                        SupplyInvoiceCapsuleDto.toEntity(sicapsDto)
                )
        );
    }

    @Override
    public SupplyInvoiceCapsuleDto updateSupplyInvoiceCapsule(SupplyInvoiceCapsuleDto sicapsDto) {
        /********************************************
         * On valide le parametre pris en argument
         */
        List<String> errors = SupplyInvoiceCapsuleValidator.validate(sicapsDto);
        if(!errors.isEmpty()){
            log.error("Entity sicapsDto not valid {}", sicapsDto);
            throw new InvalidEntityException("Le sicapsuleDto passe en argument n'est pas valide: "+errors,
                    ErrorCode.SUPPLYINVOICECAPSULE_NOT_VALID, errors);
        }

        /**********************************************************************************
         * On ne peut pas lors de la mise a jour d'une entite supplyInvoiceCapsule modifier
         * ni le userbm, ni le pointofsale
         */
        if(sicapsDto.getId() == null){
            log.error("The id of the sicapsuleDto to modify is null");
            throw new NullArgumentException("Le sicapsuleDto passe en argument est null");
        }
        Optional<SupplyInvoiceCapsule> optionalSupplyInvoiceCapsule = supplyInvoiceCapsuleRepository.findSupplyInvoiceCapsuleById(
                sicapsDto.getId());
        if(!optionalSupplyInvoiceCapsule.isPresent()){
            log.error("There is no SupplyInvoiceCapsule in the DB with the id precised");
            throw new InvalidEntityException("Aucun SupplyInvoiceCapsule avec l'id precise en BD ",
                    ErrorCode.SUPPLYINVOICECAPSULE_NOT_FOUND);
        }
        //On recupere donc le supplyInvoiceCapsule a modifier
        SupplyInvoiceCapsule supplyInvoiceCapsuleToUpdate = optionalSupplyInvoiceCapsule.get();

        /***************************************************************************
         * On va se rassurer que ce n'est pas le userbm quon veut modifier
         */
        if(!supplyInvoiceCapsuleToUpdate.getSicapsUserbm().getId().equals(sicapsDto.getSicapsUserbmDto().getId())){
            log.error("It is not possible to update the userbm responsible of the registration of that sicapsuleDto");
            throw new InvalidEntityException("Il n'est pas possible de modifier le Userbm associe au sicapsuleDto ",
                    ErrorCode.SUPPLYINVOICECAPSULE_NOT_VALID);
        }

        /**************************************************************************
         * On va se rassurer que ce n'est pas le pointofsale qu'on veut modifier
         */
        if(!supplyInvoiceCapsuleToUpdate.getSicapsPosId().equals(sicapsDto.getSicapsPosId())){
            log.error("It is not possible to update the pointofsale of that sicapsuleDto");
            throw new InvalidEntityException("Il n'est pas possible de modifier le Pointofsale du sicapsuleDto ",
                    ErrorCode.SUPPLYINVOICECAPSULE_NOT_VALID);
        }

        /**************************************************************************************
         * On va se rassurer si cest le provider quon veut modifier et si cest le cas on va se
         * rassurer que le nouveau existe vraiment en BD
         */
        if(!supplyInvoiceCapsuleToUpdate.getSicapsProvider().getId().equals(sicapsDto.getSicapsProviderDto().getId())){
            /*
            Alors cest le provider qu'on veut modifier
             */
            /*******************************************
             * On va donc se rassurer que le provider existe vraiment
             */
            Optional<Provider> optionalProvider = providerRepository.findProviderById(
                    sicapsDto.getSicapsProviderDto().getId());
            if(!optionalProvider.isPresent()){
                log.error("There is no provider in the DB with the precised id {}", sicapsDto.getSicapsProviderDto().getId());
                throw new InvalidEntityException("Aucun provider n'existe avec l'id precise dans la requete ",
                        ErrorCode.SUPPLYINVOICECAPSULE_NOT_VALID);
            }
            //La on est sur que le nouveau provider existe.
            Provider newProvider = optionalProvider.get();
            /********************
             * On se rassure donc que le nouveau provider est dans le meme pointofsale
             */
            if(!newProvider.getProviderPosId().equals(sicapsDto.getSicapsPosId())){
                log.error("The new provider don't belong to the same pointofsale");
                throw new InvalidEntityException("Le nouveau provider n'est pas dans le meme pointofsale que le " +
                        "sicapsuleDto et cette mise a jour ne peut donc etre effectue", ErrorCode.SUPPLYINVOICECAPSULE_NOT_VALID);
            }
            //Ici on fait donc la mise a jour du provider dans le sicapsule a update
            supplyInvoiceCapsuleToUpdate.setSicapsProvider(newProvider);
        }

        /*******************************************************************************
         * On va verifier si cest le sicapsuleCode quon veut modifier et si cest le cas il
         * se rassurer qu'il ny aura pas de duplicata
         */
        if(!supplyInvoiceCapsuleToUpdate.getSicapsCode().equals(sicapsDto.getSicapsCode())){
            /*********************************
             *  Alors cest le sicapsuleCode quon veut modifier
             *  on se rassure donc quil ny aura pas de duplicata
             */
            if(!isSupplyInvoiceCapsuleUnique(sicapsDto.getSicapsCode(), sicapsDto.getSicapsPosId())){
                log.error("There is a supplyinvoicecapsule already register in the DB with the same code in the same pointofsale");
                throw new DuplicateEntityException("Une facture supplyinvoicecapsule est deja existante dans la BD avec ce code",
                        ErrorCode.SUPPLYINVOICECAPSULE_DUPLICATED);
            }
            //Ici on est sur quil y aura pas de duplicata
            supplyInvoiceCapsuleToUpdate.setSicapsCode(sicapsDto.getSicapsCode());
        }

        /*****
         * Pour le reste on peut modifier sans souci cest a dire
         *     String sicapsComment;
         *     String sicapsPicture;
         *     Instant sicapsDeliveryDate;
         *     Instant sicapsInvoicingDate;
         *     BigDecimal sicapsAmountexpected;
         *     BigDecimal sicapsAmountpaid;
         *     BigDecimal sicapsTotalcolis;
         *     ArticleDto.fromEntity(articleRepository.save(articleToUpdate))
         */
        supplyInvoiceCapsuleToUpdate.setSicapsComment(sicapsDto.getSicapsComment());
        supplyInvoiceCapsuleToUpdate.setSicapsPicture(sicapsDto.getSicapsPicture());
        supplyInvoiceCapsuleToUpdate.setSicapsDeliveryDate(sicapsDto.getSicapsDeliveryDate());
        supplyInvoiceCapsuleToUpdate.setSicapsInvoicingDate(sicapsDto.getSicapsInvoicingDate());
        supplyInvoiceCapsuleToUpdate.setSicapsTotalCapsToChange(sicapsDto.getSicapsTotalCapsToChange());
        supplyInvoiceCapsuleToUpdate.setSicapsTotalCapsChange(sicapsDto.getSicapsTotalCapsChange());
        supplyInvoiceCapsuleToUpdate.setSicapsTotalcolis(sicapsDto.getSicapsTotalcolis());

        log.info("All verification have been done and the updated can be done normally ");

        return SupplyInvoiceCapsuleDto.fromEntity(supplyInvoiceCapsuleRepository.save(supplyInvoiceCapsuleToUpdate));
    }

    @Override
    public Boolean isSupplyInvoiceCapsuleUnique(String sicapsCode, Long posId) {
        if(posId == null){
            log.error("The posId sent is null");
            throw new NullArgumentException("Le posId envoye a la methode est null");
        }

        if(!StringUtils.hasLength(sicapsCode)){
            log.error("The sicapscode is null or empty");
            throw new NullArgumentException("Le code facture envoye est soit null soit vide");
        }

        Optional<SupplyInvoiceCapsule> optionalSupplyInvoiceCapsule = supplyInvoiceCapsuleRepository.
                findSupplyInvoiceCapsuleByCodeAndPos(sicapsCode, posId);

        return optionalSupplyInvoiceCapsule.isEmpty();
    }

    @Override
    public Boolean isSupplyInvoiceCapsuleDeleteable(Long sicapsId) {
        return true;
    }

    @Override
    public Boolean deleteSupplyInvoiceCapsuleById(Long sicapsId) {
        if(sicapsId == null){
            log.error("The argument sicapsId is null when calling the method");
            throw new NullArgumentException("L'argument de la methode est null pendant son appel");
        }
        Optional<SupplyInvoiceCapsule> optionalSupplyInvoiceCapsule = supplyInvoiceCapsuleRepository.findSupplyInvoiceCapsuleById(
                sicapsId);
        if(!optionalSupplyInvoiceCapsule.isPresent()){
            log.error("There is no supplyinvoicecapsule with the precise id {}", sicapsId);
            throw new EntityNotFoundException("Aucun supplyinvoicecaps n'existe en BD avec l'id precise ",
                    ErrorCode.SUPPLYINVOICECAPSULE_NOT_FOUND);
        }
        if(!isSupplyInvoiceCapsuleDeleteable(sicapsId)){
            log.error("The supplyinvoicecapsule is not yet deleteable");
            throw new EntityNotDeleteableException("On ne peut supprimer le supplyinvoicecapsule en ce moment de la BD",
                    ErrorCode.SUPPLYINVOICECAPSULE_NOT_DELETEABLE);
        }
        supplyInvoiceCapsuleRepository.delete(optionalSupplyInvoiceCapsule.get());
        return true;
    }

    @Override
    public SupplyInvoiceCapsuleDto findSupplyInvoiceCapsuleById(Long sicapsId) {
        if(sicapsId == null){
            log.error("The argument sicapsId is null when calling the method");
            throw new NullArgumentException("L'argument de la methode est null pendant son appel");
        }
        Optional<SupplyInvoiceCapsule> optionalSupplyInvoiceCapsule = supplyInvoiceCapsuleRepository.
                findSupplyInvoiceCapsuleById(sicapsId);
        if(!optionalSupplyInvoiceCapsule.isPresent()){
            log.error("There is no supplyinvoicecapsule with the precise id {}", sicapsId);
            throw new EntityNotFoundException("Aucun supplyinvoicecapsule n'existe en BD avec l'id precise ",
                    ErrorCode.SUPPLYINVOICECAPSULE_NOT_FOUND);
        }
        return SupplyInvoiceCapsuleDto.fromEntity(optionalSupplyInvoiceCapsule.get());
    }

    @Override
    public SupplyInvoiceCapsuleDto findSupplyInvoiceCapsuleByCode(String sicapsCode, Long posId) {
        if(posId == null){
            log.error("The argument posId is null when calling the method");
            throw new NullArgumentException("L'argument de la methode est null pendant son appel");
        }
        if(!StringUtils.hasLength(sicapsCode)){
            log.error("The precised code is empty of null");
            throw new NullArgumentException("Le code de facture precise est soit null soit vide lors de l'appel");
        }
        Optional<SupplyInvoiceCapsule> optionalSupplyInvoiceCapsule = supplyInvoiceCapsuleRepository.
                findSupplyInvoiceCapsuleByCodeAndPos(sicapsCode, posId);
        if(!optionalSupplyInvoiceCapsule.isPresent()){
            log.error("There is no supplyinvoicecapsule with the precise code {} in the pointofsale with the id {}",
                    sicapsCode, posId);
            throw new EntityNotFoundException("Aucun supplyinvoicecapsule n'existe en BD avec le code precise dans le " +
                    "pointofsale d'id precise", ErrorCode.SUPPLYINVOICECAPSULE_NOT_FOUND);
        }

        return SupplyInvoiceCapsuleDto.fromEntity(optionalSupplyInvoiceCapsule.get());
    }

    @Override
    public List<SupplyInvoiceCapsuleDto> findAllSupplyInvoiceCapsuleBetween(Long posId, Instant startDate,
                                                                            Instant endDate) {
        if(posId == null){
            log.error("The argument posId is null when calling the method");
            throw new NullArgumentException("L'argument posId de la methode est null pendant son appel");
        }
        Optional<List<SupplyInvoiceCapsule>> optionalSupplyInvoiceCapsuleList = supplyInvoiceCapsuleRepository.
                findAllSupplyInvoiceCapsuleofPosBetween(posId, startDate, endDate);
        if(!optionalSupplyInvoiceCapsuleList.isPresent()){
            log.error("There is no pointofsale with the id {} in the DB", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id passe en argument ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalSupplyInvoiceCapsuleList.get().stream().map(SupplyInvoiceCapsuleDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<SupplyInvoiceCapsuleDto> findPageSupplyInvoiceCapsuleBetween(Long posId, Instant startDate,
                                                                             Instant endDate, int pagenum,
                                                                             int pagesize) {
        if(posId == null){
            log.error("The argument posId is null when calling the method");
            throw new NullArgumentException("L'argument posId de la methode est null pendant son appel");
        }
        Optional<Page<SupplyInvoiceCapsule>> optionalSupplyInvoiceCapsulePage = supplyInvoiceCapsuleRepository.
                findPageSupplyInvoiceCapsuleofPosBetween(posId, startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalSupplyInvoiceCapsulePage.isPresent()){
            log.error("There is no pointofsale with the id {} in the DB", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id passe en argument ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }

        return optionalSupplyInvoiceCapsulePage.get().map(SupplyInvoiceCapsuleDto::fromEntity);
    }

    @Override
    public List<SupplyInvoiceCapsuleDto> findAllSupplyInvoiceCapsuleofUserbmBetween(Long posId, Long userbmId,
                                                                                    Instant startDate,
                                                                                    Instant endDate) {
        if(posId == null){
            log.error("The argument posId is null when calling the method");
            throw new NullArgumentException("L'argument posId de la methode est null pendant son appel");
        }
        if(userbmId == null){
            log.error("The argument userbmId is null when calling the method");
            throw new NullArgumentException("L'argument  userbmId de la methode est null pendant son appel");
        }
        Optional<List<SupplyInvoiceCapsule>> optionalSupplyInvoiceCapsuleList = supplyInvoiceCapsuleRepository.
                findAllSupplyInvoiceCapsuleofPosandUserbmBetween(posId, userbmId, startDate, endDate);
        if(!optionalSupplyInvoiceCapsuleList.isPresent()){
            log.error("There is no pointofsale with the id {} in the DB", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id passe en argument ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalSupplyInvoiceCapsuleList.get().stream().map(SupplyInvoiceCapsuleDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<SupplyInvoiceCapsuleDto> findPageSupplyInvoiceCapsuleofUserbmBetween(Long posId, Long userbmId,
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
        Optional<Page<SupplyInvoiceCapsule>> optionalSupplyInvoiceCapsulePage = supplyInvoiceCapsuleRepository.
                findPageSupplyInvoiceCapsuleofPosandUserbmBetween(posId, userbmId, startDate, endDate,
                        PageRequest.of(pagenum, pagesize));
        if(!optionalSupplyInvoiceCapsulePage.isPresent()){
            log.error("There is no pointofsale with the id {} in the DB", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id passe en argument ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalSupplyInvoiceCapsulePage.get().map(SupplyInvoiceCapsuleDto::fromEntity);
    }

    @Override
    public List<SupplyInvoiceCapsuleDto> findAllSupplyInvoiceCapsuleofProviderBetween(Long posId, Long providerId,
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
        Optional<List<SupplyInvoiceCapsule>> optionalSupplyInvoiceCapsuleList = supplyInvoiceCapsuleRepository.
                findAllSupplyInvoiceCapsuleofPosandProviderBetween(posId, providerId, startDate, endDate);
        if(!optionalSupplyInvoiceCapsuleList.isPresent()){
            log.error("There is no pointofsale with the id {} in the DB", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id passe en argument ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalSupplyInvoiceCapsuleList.get().stream().map(SupplyInvoiceCapsuleDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<SupplyInvoiceCapsuleDto> findPageSupplyInvoiceCapsuleofProviderBetween(Long posId, Long providerId,
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
        Optional<Page<SupplyInvoiceCapsule>> optionalSupplyInvoiceCapsulePage = supplyInvoiceCapsuleRepository.
                findPageSupplyInvoiceCapsuleofPosandProviderBetween(posId, providerId, startDate, endDate,
                        PageRequest.of(pagenum, pagesize));
        if(!optionalSupplyInvoiceCapsulePage.isPresent()){
            log.error("There is no pointofsale with the id {} in the DB", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id passe en argument ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalSupplyInvoiceCapsulePage.get().map(SupplyInvoiceCapsuleDto::fromEntity);
    }

    @Override
    public List<SupplyInvoiceCapsuleDto> findAllSupplyInvoiceCapsuleofProviderAndUserbmBetween(Long posId, Long providerId,
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
        Optional<List<SupplyInvoiceCapsule>> optionalSupplyInvoiceCapsuleList = supplyInvoiceCapsuleRepository.
                findAllSupplyInvoiceCapsuleofPosandProviderAndUserbmBetween(posId, providerId, userbmId, startDate,
                        endDate);
        if(!optionalSupplyInvoiceCapsuleList.isPresent()){
            log.error("There is no pointofsale with the id {} in the DB", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id passe en argument ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalSupplyInvoiceCapsuleList.get().stream().map(SupplyInvoiceCapsuleDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<SupplyInvoiceCapsuleDto> findPageSupplyInvoiceCapsuleofProviderAndUserbmBetween(Long posId, Long providerId,
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
        Optional<Page<SupplyInvoiceCapsule>> optionalSupplyInvoiceCapsulePage = supplyInvoiceCapsuleRepository.
                findPageSupplyInvoiceCapsuleofPosandProviderAndUserbmBetween(posId, providerId, userbmId, startDate,
                        endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalSupplyInvoiceCapsulePage.isPresent()){
            log.error("There is no pointofsale with the id {} in the DB", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id passe en argument ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalSupplyInvoiceCapsulePage.get().map(SupplyInvoiceCapsuleDto::fromEntity);
    }


}
