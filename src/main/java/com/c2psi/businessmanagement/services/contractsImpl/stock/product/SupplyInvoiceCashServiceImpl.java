package com.c2psi.businessmanagement.services.contractsImpl.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.SupplyInvoiceCashDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.Pointofsale;
import com.c2psi.businessmanagement.models.Provider;
import com.c2psi.businessmanagement.models.SupplyInvoiceCash;
import com.c2psi.businessmanagement.models.UserBM;
import com.c2psi.businessmanagement.repositories.pos.pos.PointofsaleRepository;
import com.c2psi.businessmanagement.repositories.pos.userbm.UserBMRepository;
import com.c2psi.businessmanagement.repositories.stock.product.SupplyInvoiceCashRepository;
import com.c2psi.businessmanagement.repositories.stock.provider.ProviderRepository;
import com.c2psi.businessmanagement.services.contracts.stock.product.SupplyInvoiceCashService;
import com.c2psi.businessmanagement.validators.stock.product.ArticleValidator;
import com.c2psi.businessmanagement.validators.stock.product.SupplyInvoiceCashValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value="SupplyInvoiceCashServiceV1")
@Slf4j
@Transactional
public class SupplyInvoiceCashServiceImpl implements SupplyInvoiceCashService {

    private UserBMRepository userBMRepository;
    private ProviderRepository providerRepository;
    private PointofsaleRepository pointofsaleRepository;
    private SupplyInvoiceCashRepository supplyInvoiceCashRepository;

    @Autowired
    public SupplyInvoiceCashServiceImpl(UserBMRepository userBMRepository, ProviderRepository providerRepository,
                                        PointofsaleRepository pointofsaleRepository,
                                        SupplyInvoiceCashRepository supplyInvoiceCashRepository) {
        this.userBMRepository = userBMRepository;
        this.providerRepository = providerRepository;
        this.pointofsaleRepository = pointofsaleRepository;
        this.supplyInvoiceCashRepository = supplyInvoiceCashRepository;
    }

    @Override
    public SupplyInvoiceCashDto saveSupplyInvoiceCash(SupplyInvoiceCashDto sicashDto) {
        /********************************************
         * On valide le parametre pris en argument
         */
        List<String> errors = SupplyInvoiceCashValidator.validate(sicashDto);
        if(!errors.isEmpty()){
            log.error("Entity sicashDto not valid {}", sicashDto);
            throw new InvalidEntityException("Le sicashDto passe en argument n'est pas valide: "+errors,
                    ErrorCode.SUPPLYINVOICECASH_NOT_VALID, errors);
        }

        /*********************************************************************
         * Verifier que l'id du pointofsale n'est pas null et quil identifie
         * vraiment un pointofsale
         */
        if(sicashDto.getSicashPosDto().getId() == null){
            log.error("The id of the pointofsale in the request is null");
            throw new InvalidEntityException("L'Id du pointofsale dans le sicashDto est null ",
                    ErrorCode.SUPPLYINVOICECASH_NOT_VALID);
        }
        //L'id du pointofsale n'est pas null on verifie si c'est vraiment un pos dans la BD
        Optional<Pointofsale> optionalPointofsale = pointofsaleRepository.findPointofsaleById(
                sicashDto.getSicashPosDto().getId());
        if(!optionalPointofsale.isPresent()){
            log.error("There is no pointofsale in the DB with the precised id {}", sicashDto.getSicashPosDto().getId());
            throw new InvalidEntityException("Aucun Pointofsale n'existe avec l'id precise dans la requete ",
                    ErrorCode.SUPPLYINVOICECASH_NOT_VALID);
        }

        /******************************************************************
         * Verifier que l'id du userbm n'est pas null et quil identifie
         * vraiment un userbm
         */
        if(sicashDto.getSicashUserbmDto().getId() == null){
            log.error("The id of the userBM in the request is null");
            throw new InvalidEntityException("L'Id du UserBM dans le sicashDto est null ",
                    ErrorCode.SUPPLYINVOICECASH_NOT_VALID);
        }
        //L'id du userbm n'est pas null on verifie si c'est vraiment un userbm dans la BD
        Optional<UserBM> optionalUserBM = userBMRepository.findUserBMById(
                sicashDto.getSicashUserbmDto().getId());
        if(!optionalUserBM.isPresent()){
            log.error("There is no userbm in the DB with the precised id {}", sicashDto.getSicashUserbmDto().getId());
            throw new InvalidEntityException("Aucun UserBM n'existe avec l'id precise dans la requete ",
                    ErrorCode.SUPPLYINVOICECASH_NOT_VALID);
        }

        /******************************************************************
         * Verifier que l'id du provider n'est pas null et quil identifie
         * vraiment un provider
         */
        if(sicashDto.getSicashProviderDto().getId() == null){
            log.error("The id of the provider in the request is null");
            throw new InvalidEntityException("L'Id du provider dans le sicashDto est null ",
                    ErrorCode.SUPPLYINVOICECASH_NOT_VALID);
        }
        //L'id du provider n'est pas null on verifie si c'est vraiment un userbm dans la BD
        Optional<Provider> optionalProvider = providerRepository.findProviderById(
                sicashDto.getSicashProviderDto().getId());
        if(!optionalProvider.isPresent()){
            log.error("There is no provider in the DB with the precised id {}", sicashDto.getSicashProviderDto().getId());
            throw new InvalidEntityException("Aucun provider n'existe avec l'id precise dans la requete ",
                    ErrorCode.SUPPLYINVOICECASH_NOT_VALID);
        }

        /********************************************************************************************
         * On verifie que le pointofsale dans la requete est le meme que celui du userbm et provider
         */
        if(!optionalPointofsale.get().getId().equals(optionalUserBM.get().getBmPos().getId())){
            log.error("The pointofsale in the sicashDto must be the same with the one of the userBM");
            throw new InvalidEntityException("Le pointofsale dans la requete doit etre le meme que celui du UserBM " +
                    "precise precise dans le sicashDto ", ErrorCode.SUPPLYINVOICECASH_NOT_VALID);
        }

        if(!optionalPointofsale.get().getId().equals(optionalProvider.get().getProviderPos().getId())){
            log.error("The pointofsale in the sicashDto must be the same with the one of the provider");
            throw new InvalidEntityException("Le pointofsale dans la requete doit etre le meme que celui du provider " +
                    "precise precise dans le sicashDto ", ErrorCode.SUPPLYINVOICECASH_NOT_VALID);
        }

        if(!optionalProvider.get().getProviderPos().getId().equals(optionalUserBM.get().getBmPos().getId())){
            log.error("The Provider in the sicashDto must be the same with the one of the UserBM");
            throw new InvalidEntityException("Le Provider dans la requete doit etre le meme que celui du UserBM " +
                    "precise precise dans le sicashDto ", ErrorCode.SUPPLYINVOICECASH_NOT_VALID);
        }

        /**********************************************************************************************
         * On verifie quil y a pas un autre SupplyInvoiceCash avec le meme code deja enregistre en BD
         * pour le meme pointofsale
         */
        if(!isSupplyInvoiceCashUnique(sicashDto.getSicashCode(), sicashDto.getSicashPosDto().getId())){
            log.error("There is a supplyinvoicecash already register in the DB with the same code in the same pointofsale");
            throw new DuplicateEntityException("Une facture supplyinvoicecash est deja existante dans la BD avec ce code",
                    ErrorCode.SUPPLYINVOICECASH_DUPLICATED);
        }

        log.error("After all verification, the sicashDto {} can be save in the DB without any problem", sicashDto);
        return SupplyInvoiceCashDto.fromEntity(
                supplyInvoiceCashRepository.save(
                        SupplyInvoiceCashDto.toEntity(sicashDto)
                )
        );
    }

    @Override
    public SupplyInvoiceCashDto updateSupplyInvoiceCash(SupplyInvoiceCashDto sicashDto) {

        /********************************************
         * On valide le parametre pris en argument
         */
        List<String> errors = SupplyInvoiceCashValidator.validate(sicashDto);
        if(!errors.isEmpty()){
            log.error("Entity sicashDto not valid {}", sicashDto);
            throw new InvalidEntityException("Le sicashDto passe en argument n'est pas valide: "+errors,
                    ErrorCode.SUPPLYINVOICECASH_NOT_VALID, errors);
        }

        /**********************************************************************************
         * On ne peut pas lors de la mise a jour d'une entite supplyInvoiceCash modifier
         * ni le userbm, ni le pointofsale
         */
        if(sicashDto.getId() == null){
            log.error("The id of the sicashDto to modify is null");
            throw new NullArgumentException("Le sicashDto passe en argument est null");
        }
        Optional<SupplyInvoiceCash> optionalSupplyInvoiceCash = supplyInvoiceCashRepository.findSupplyInvoiceCashById(
                sicashDto.getId());
        if(!optionalSupplyInvoiceCash.isPresent()){
            log.error("There is no SupplyInvoiceCash in the DB with the id precised");
            throw new InvalidEntityException("Aucun SupplyInvoiceCash avec l'id precise en BD ",
                    ErrorCode.SUPPLYINVOICECASH_NOT_FOUND);
        }
        //On recupere donc le supplyInvoiceCash a modifier
        SupplyInvoiceCash supplyInvoiceCashToUpdate = optionalSupplyInvoiceCash.get();

        /***************************************************************************
         * On va se rassurer que ce n'est pas le userbm quon veut modifier
         */
        if(!supplyInvoiceCashToUpdate.getSicashUserbm().getId().equals(sicashDto.getSicashUserbmDto().getId())){
            log.error("It is not possible to update the userbm responsible of the registration of that sicashDto");
            throw new InvalidEntityException("Il n'est pas possible de modifier le Userbm associe au sicashDto ",
                    ErrorCode.SUPPLYINVOICECASH_NOT_VALID);
        }

        /**************************************************************************
         * On va se rassurer que ce n'est pas le pointofsale qu'on veut modifier
         */
        if(!supplyInvoiceCashToUpdate.getSicashPos().getId().equals(sicashDto.getSicashPosDto().getId())){
            log.error("It is not possible to update the pointofsale of that sicashDto");
            throw new InvalidEntityException("Il n'est pas possible de modifier le Pointofsale du sicashDto ",
                    ErrorCode.SUPPLYINVOICECASH_NOT_VALID);
        }

        /**************************************************************************************
         * On va se rassurer si cest le provider quon veut modifier et si cest le cas on va se
         * rassurer que le nouveau existe vraiment en BD
         */
        if(!supplyInvoiceCashToUpdate.getSicashProvider().getId().equals(sicashDto.getSicashProviderDto().getId())){
            /*
            Alors cest le provider qu'on veut modifier
             */
            /*******************************************
             * On va donc se rassurer que le provider existe vraiment
             */
            Optional<Provider> optionalProvider = providerRepository.findProviderById(
                    sicashDto.getSicashProviderDto().getId());
            if(!optionalProvider.isPresent()){
                log.error("There is no provider in the DB with the precised id {}", sicashDto.getSicashProviderDto().getId());
                throw new InvalidEntityException("Aucun provider n'existe avec l'id precise dans la requete ",
                        ErrorCode.SUPPLYINVOICECASH_NOT_VALID);
            }
            //La on est sur que le nouveau provider existe.
            Provider newProvider = optionalProvider.get();
            /********************
             * On se rassure donc que le nouveau provider est dans le meme pointofsale
             */
            if(!newProvider.getProviderPos().getId().equals(sicashDto.getSicashPosDto().getId())){
                log.error("The new provider don't belong to the same pointofsale");
                throw new InvalidEntityException("Le nouveau provider n'est pas dans le meme pointofsale que le " +
                        "sicashDto et cette mise a jour ne peut donc etre effectue", ErrorCode.SUPPLYINVOICECASH_NOT_VALID);
            }
            //Ici on fait donc la mise a jour du provider dans le sicash a update
            supplyInvoiceCashToUpdate.setSicashProvider(newProvider);
        }

        /*******************************************************************************
         * On va verifier si cest le sicashCode quon veut modifier et si cest le cas il
         * se rassurer qu'il ny aura pas de duplicata
         */
        if(!supplyInvoiceCashToUpdate.getSicashCode().equals(sicashDto.getSicashCode())){
            /*********************************
             *  Alors cest le sicashCode quon veut modifier
             *  on se rassure donc quil ny aura pas de duplicata
             */
            if(!isSupplyInvoiceCashUnique(sicashDto.getSicashCode(), sicashDto.getSicashPosDto().getId())){
                log.error("There is a supplyinvoicecash already register in the DB with the same code in the same pointofsale");
                throw new DuplicateEntityException("Une facture supplyinvoicecash est deja existante dans la BD avec ce code",
                        ErrorCode.SUPPLYINVOICECASH_DUPLICATED);
            }
            //Ici on est sur quil y aura pas de duplicata
            supplyInvoiceCashToUpdate.setSicashCode(sicashDto.getSicashCode());
        }

        /*****
         * Pour le reste on peut modifier sans souci cest a dire
         *     String sicashComment;
         *     String sicashPicture;
         *     Instant sicashDeliveryDate;
         *     Instant sicashInvoicingDate;
         *     BigDecimal sicashAmountexpected;
         *     BigDecimal sicashAmountpaid;
         *     BigDecimal sicashTotalcolis;
         *     ArticleDto.fromEntity(articleRepository.save(articleToUpdate))
         */
        supplyInvoiceCashToUpdate.setSicashComment(sicashDto.getSicashComment());
        supplyInvoiceCashToUpdate.setSicashPicture(sicashDto.getSicashPicture());
        supplyInvoiceCashToUpdate.setSicashDeliveryDate(sicashDto.getSicashDeliveryDate());
        supplyInvoiceCashToUpdate.setSicashInvoicingDate(sicashDto.getSicashInvoicingDate());
        supplyInvoiceCashToUpdate.setSicashAmountexpected(sicashDto.getSicashAmountexpected());
        supplyInvoiceCashToUpdate.setSicashAmountpaid(sicashDto.getSicashAmountpaid());
        supplyInvoiceCashToUpdate.setSicashTotalcolis(sicashDto.getSicashTotalcolis());
        supplyInvoiceCashToUpdate.setSicashSourceofcash(sicashDto.getSicashSourceofcash());

        log.info("All verification have been done and the updated can be done normally ");

        return SupplyInvoiceCashDto.fromEntity(supplyInvoiceCashRepository.save(supplyInvoiceCashToUpdate));
    }

    @Override
    public Boolean isSupplyInvoiceCashUnique(String sicashCode, Long posId) {
        if(posId == null){
            log.error("The posId sent is null");
            throw new NullArgumentException("Le posId envoye a la methode est null");
        }

        if(!StringUtils.hasLength(sicashCode)){
            log.error("The sicashcode is null or empty");
            throw new NullArgumentException("Le code facture envoye est soit null soit vide");
        }

        Optional<SupplyInvoiceCash> optionalSupplyInvoiceCash = supplyInvoiceCashRepository.
                findSupplyInvoiceCashByCodeAndPos(sicashCode, posId);

        return optionalSupplyInvoiceCash.isEmpty();
    }

    @Override
    public Boolean isSupplyInvoiceCashDeleteable(Long sicashId) {
        return true;
    }

    @Override
    public Boolean deleteSupplyInvoiceCashById(Long sicashId) {

        if(sicashId == null){
            log.error("The argument sicashId is null when calling the method");
            throw new NullArgumentException("L'argument de la methode est null pendant son appel");
        }
        Optional<SupplyInvoiceCash> optionalSupplyInvoiceCash = supplyInvoiceCashRepository.findSupplyInvoiceCashById(
                sicashId);
        if(!optionalSupplyInvoiceCash.isPresent()){
            log.error("There is no supplyinvoicecash with the precise id {}", sicashId);
            throw new EntityNotFoundException("Aucun supplyinvoicecash n'existe en BD avec l'id precise ",
                    ErrorCode.SUPPLYINVOICECASH_NOT_FOUND);
        }
        if(!isSupplyInvoiceCashDeleteable(sicashId)){
           log.error("The supplyinvoicecash is not yet deleteable");
           throw new EntityNotDeleteableException("On ne peut supprimer le supplyinvoicecash en ce moment de la BD",
                   ErrorCode.SUPPLYINVOICECASH_NOT_DELETEABLE);
        }
        supplyInvoiceCashRepository.delete(optionalSupplyInvoiceCash.get());
        return true;
    }

    @Override
    public SupplyInvoiceCashDto findSupplyInvoiceCashById(Long sicashId) {
        if(sicashId == null){
            log.error("The argument sicashId is null when calling the method");
            throw new NullArgumentException("L'argument de la methode est null pendant son appel");
        }
        Optional<SupplyInvoiceCash> optionalSupplyInvoiceCash = supplyInvoiceCashRepository.findSupplyInvoiceCashById(
                sicashId);
        if(!optionalSupplyInvoiceCash.isPresent()){
            log.error("There is no supplyinvoicecash with the precise id {}", sicashId);
            throw new EntityNotFoundException("Aucun supplyinvoicecash n'existe en BD avec l'id precise ",
                    ErrorCode.SUPPLYINVOICECASH_NOT_FOUND);
        }
        return SupplyInvoiceCashDto.fromEntity(optionalSupplyInvoiceCash.get());
    }

    @Override
    public SupplyInvoiceCashDto findSupplyInvoiceCashByCode(String sicashCode, Long posId) {
        if(posId == null){
            log.error("The argument posId is null when calling the method");
            throw new NullArgumentException("L'argument de la methode est null pendant son appel");
        }
        if(!StringUtils.hasLength(sicashCode)){
            log.error("The precised code is empty of null");
            throw new NullArgumentException("Le code de facture precise est soit null soit vide lors de l'appel");
        }
        Optional<SupplyInvoiceCash> optionalSupplyInvoiceCash = supplyInvoiceCashRepository.
                findSupplyInvoiceCashByCodeAndPos(sicashCode, posId);
        if(!optionalSupplyInvoiceCash.isPresent()){
            log.error("There is no supplyinvoicecash with the precise code {} in the pointofsale with the id {}",
                    sicashCode, posId);
            throw new EntityNotFoundException("Aucun supplyinvoicecash n'existe en BD avec le code precise dans le " +
                    "pointofsale d'id precise", ErrorCode.SUPPLYINVOICECASH_NOT_FOUND);
        }

        return SupplyInvoiceCashDto.fromEntity(optionalSupplyInvoiceCash.get());
    }

    @Override
    public List<SupplyInvoiceCashDto> findAllSupplyInvoiceCashBetween(Long posId, Instant startDate, Instant endDate) {
        if(posId == null){
            log.error("The argument posId is null when calling the method");
            throw new NullArgumentException("L'argument posId de la methode est null pendant son appel");
        }
        Optional<List<SupplyInvoiceCash>> optionalSupplyInvoiceCashList = supplyInvoiceCashRepository.
                findAllSupplyInvoiceCashofPosBetween(posId, startDate, endDate);
        if(!optionalSupplyInvoiceCashList.isPresent()){
            log.error("There is no pointofsale with the id {} in the DB", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id passe en argument ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalSupplyInvoiceCashList.get().stream().map(SupplyInvoiceCashDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<SupplyInvoiceCashDto> findPageSupplyInvoiceCashBetween(Long posId, Instant startDate, Instant endDate,
                                                                       int pagenum, int pagesize) {
        if(posId == null){
            log.error("The argument posId is null when calling the method");
            throw new NullArgumentException("L'argument posId de la methode est null pendant son appel");
        }
        Optional<Page<SupplyInvoiceCash>> optionalSupplyInvoiceCashPage = supplyInvoiceCashRepository.
                findPageSupplyInvoiceCashofPosBetween(posId, startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalSupplyInvoiceCashPage.isPresent()){
            log.error("There is no pointofsale with the id {} in the DB", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id passe en argument ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }

        return optionalSupplyInvoiceCashPage.get().map(SupplyInvoiceCashDto::fromEntity);
    }

    @Override
    public List<SupplyInvoiceCashDto> findAllSupplyInvoiceCashofUserbmBetween(Long posId, Long userbmId, Instant startDate,
                                                                              Instant endDate) {
        if(posId == null){
            log.error("The argument posId is null when calling the method");
            throw new NullArgumentException("L'argument posId de la methode est null pendant son appel");
        }
        if(userbmId == null){
            log.error("The argument userbmId is null when calling the method");
            throw new NullArgumentException("L'argument  userbmId de la methode est null pendant son appel");
        }
        Optional<List<SupplyInvoiceCash>> optionalSupplyInvoiceCashList = supplyInvoiceCashRepository.
                findAllSupplyInvoiceCashofPosandUserbmBetween(posId, userbmId, startDate, endDate);
        if(!optionalSupplyInvoiceCashList.isPresent()){
            log.error("There is no pointofsale with the id {} in the DB", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id passe en argument ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalSupplyInvoiceCashList.get().stream().map(SupplyInvoiceCashDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<SupplyInvoiceCashDto> findPageSupplyInvoiceCashofUserbmBetween(Long posId, Long userbmId, Instant startDate,
                                                                               Instant endDate, int pagenum, int pagesize) {
        if(posId == null){
            log.error("The argument posId is null when calling the method");
            throw new NullArgumentException("L'argument posId de la methode est null pendant son appel");
        }
        if(userbmId == null){
            log.error("The argument userbmId is null when calling the method");
            throw new NullArgumentException("L'argument  userbmId de la methode est null pendant son appel");
        }
        Optional<Page<SupplyInvoiceCash>> optionalSupplyInvoiceCashPage = supplyInvoiceCashRepository.
                findPageSupplyInvoiceCashofPosandUserbmBetween(posId, userbmId, startDate, endDate,
                        PageRequest.of(pagenum, pagesize));
        if(!optionalSupplyInvoiceCashPage.isPresent()){
            log.error("There is no pointofsale with the id {} in the DB", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id passe en argument ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalSupplyInvoiceCashPage.get().map(SupplyInvoiceCashDto::fromEntity);
    }

    @Override
    public List<SupplyInvoiceCashDto> findAllSupplyInvoiceCashofProviderBetween(Long posId, Long providerId, Instant startDate,
                                                                                Instant endDate) {
        if(posId == null){
            log.error("The argument posId is null when calling the method");
            throw new NullArgumentException("L'argument posId de la methode est null pendant son appel");
        }
        if(providerId == null){
            log.error("The argument providerId is null when calling the method");
            throw new NullArgumentException("L'argument  providerId de la methode est null pendant son appel");
        }
        Optional<List<SupplyInvoiceCash>> optionalSupplyInvoiceCashList = supplyInvoiceCashRepository.
                findAllSupplyInvoiceCashofPosandProviderBetween(posId, providerId, startDate, endDate);
        if(!optionalSupplyInvoiceCashList.isPresent()){
            log.error("There is no pointofsale with the id {} in the DB", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id passe en argument ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalSupplyInvoiceCashList.get().stream().map(SupplyInvoiceCashDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<SupplyInvoiceCashDto> findPageSupplyInvoiceCashofProviderBetween(Long posId, Long providerId, Instant startDate,
                                                                                 Instant endDate, int pagenum, int pagesize) {
        if(posId == null){
            log.error("The argument posId is null when calling the method");
            throw new NullArgumentException("L'argument posId de la methode est null pendant son appel");
        }
        if(providerId == null){
            log.error("The argument providerId is null when calling the method");
            throw new NullArgumentException("L'argument  providerId de la methode est null pendant son appel");
        }
        Optional<Page<SupplyInvoiceCash>> optionalSupplyInvoiceCashPage = supplyInvoiceCashRepository.
                findPageSupplyInvoiceCashofPosandProviderBetween(posId, providerId, startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalSupplyInvoiceCashPage.isPresent()){
            log.error("There is no pointofsale with the id {} in the DB", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id passe en argument ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalSupplyInvoiceCashPage.get().map(SupplyInvoiceCashDto::fromEntity);
    }

    @Override
    public List<SupplyInvoiceCashDto> findAllSupplyInvoiceCashofProviderAndUserbmBetween(Long posId, Long providerId,
                                                                                         Long userbmId, Instant startDate,
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
        Optional<List<SupplyInvoiceCash>> optionalSupplyInvoiceCashList = supplyInvoiceCashRepository.
                findAllSupplyInvoiceCashofPosandProviderAndUserbmBetween(posId, providerId, userbmId, startDate, endDate);
        if(!optionalSupplyInvoiceCashList.isPresent()){
            log.error("There is no pointofsale with the id {} in the DB", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id passe en argument ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalSupplyInvoiceCashList.get().stream().map(SupplyInvoiceCashDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<SupplyInvoiceCashDto> findPageSupplyInvoiceCashofProviderAndUserbmBetween(Long posId, Long providerId,
                                                                                          Long userbmId, Instant startDate,
                                                                                          Instant endDate, int pagenum,
                                                                                          int pagesize) {
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
        Optional<Page<SupplyInvoiceCash>> optionalSupplyInvoiceCashPage = supplyInvoiceCashRepository.
                findPageSupplyInvoiceCashofPosandProviderAndUserbmBetween(posId, providerId, userbmId, startDate,
                        endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalSupplyInvoiceCashPage.isPresent()){
            log.error("There is no pointofsale with the id {} in the DB", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id passe en argument ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalSupplyInvoiceCashPage.get().map(SupplyInvoiceCashDto::fromEntity);
    }

}
