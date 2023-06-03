package com.c2psi.businessmanagement.services.contractsImpl.pos.loading;

import com.c2psi.businessmanagement.dtos.pos.loading.LoadingDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.Loading;
import com.c2psi.businessmanagement.models.Pointofsale;
import com.c2psi.businessmanagement.models.UserBM;
import com.c2psi.businessmanagement.repositories.pos.loading.LoadingRepository;
import com.c2psi.businessmanagement.repositories.pos.pos.PointofsaleRepository;
import com.c2psi.businessmanagement.repositories.pos.userbm.UserBMRepository;
import com.c2psi.businessmanagement.services.contracts.pos.loading.LoadingService;
import com.c2psi.businessmanagement.validators.pos.loading.LoadingValidator;
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

@Service(value="LoadingServiceV1")
@Slf4j
@Transactional
public class LoadingServiceImpl implements LoadingService {

    private PointofsaleRepository pointofsaleRepository;
    private UserBMRepository userBMRepository;
    private LoadingRepository loadingRepository;

    @Autowired
    public LoadingServiceImpl(PointofsaleRepository pointofsaleRepository, UserBMRepository userBMRepository,
                              LoadingRepository loadingRepository) {
        this.pointofsaleRepository = pointofsaleRepository;
        this.userBMRepository = userBMRepository;
        this.loadingRepository = loadingRepository;
    }

    @Override
    public LoadingDto saveLoading(LoadingDto loadingDto) {
        /***********************************************************
         * Il faut valider le loadingDto grace au validator
         */
        List<String> errors = LoadingValidator.validate(loadingDto);
        if(!errors.isEmpty()){
            log.error("Entity loadingDto not valid {}", loadingDto);
            throw new InvalidEntityException("Le loadingDto passe en argument n'est pas valide:  ",
                    ErrorCode.LOADING_NOT_VALID, errors);
        }

        /************************************************************
         * On verifie que l'id du pointofsale est non null et que ce
         * pointofsale est bel et bien existant
         */
        if(loadingDto.getLoadPosDto().getId() == null){
            log.error("The Id of the Pointofsale indicated is null");
            throw new InvalidEntityException("L'Id du pointofsale associe au loading ne peut etre null ",
                    ErrorCode.LOADING_NOT_VALID);
        }
        Optional<Pointofsale> optionalPointofsale = pointofsaleRepository.findPointofsaleById(
                loadingDto.getLoadPosDto().getId());
        if(!optionalPointofsale.isPresent()){
            log.error("The pointofsale indicated is not in the DB");
            throw new InvalidEntityException("Le pointofsale indique n'est pas existant en base de donnee",
                    ErrorCode.LOADING_NOT_VALID);
        }
        //A ce niveau on est sur que le pointofsale existe bien dans la BD

        /***************************************************************
         * On verifie que l'id du UserBM manager est non null et que ce
         * UserBM est bel et bien existant
         */
        if(loadingDto.getLoadUserbmManagerDto().getId() == null){
            log.error("The Id of the UserBMManager is null");
            throw new InvalidEntityException("L'Id du UserbmManager est null ", ErrorCode.LOADING_NOT_VALID);
        }
        Optional<UserBM> optionalUserBMManager = userBMRepository.findUserBMById(loadingDto.getLoadUserbmManagerDto().getId());
        if(!optionalUserBMManager.isPresent()){
            log.error("There is no UserBM in the DB with the ID precise");
            throw new InvalidEntityException("Le UserBM manager associe n'est pas existant en BD",
                    ErrorCode.LOADING_NOT_VALID);
        }
        //A ce niveau on est sur que le UserBMManager existe bien en BD

        /***************************************************************
         * On verifie que l'id du UserBM Saler est non null et que ce
         * UserBM est bel et bien existant
         */
        if(loadingDto.getLoadUserbmSalerDto().getId() == null){
            log.error("The Id of the UserBMSaler is null");
            throw new InvalidEntityException("L'Id du UserbmSaler est null ", ErrorCode.LOADING_NOT_VALID);
        }
        Optional<UserBM> optionalUserBMSaler = userBMRepository.findUserBMById(loadingDto.getLoadUserbmSalerDto().getId());
        if(!optionalUserBMSaler.isPresent()){
            log.error("There is no UserBM in the DB with the ID precise");
            throw new InvalidEntityException("Le UserBM saler associe n'est pas existant en BD",
                    ErrorCode.LOADING_NOT_VALID);
        }
        //A ce niveau on est sur que le UserBMSaler existe bien en BD

        /*****************************************************************
         * On se rassure de l'unicite du code du Loading dans le Pos
         */
        if(!isLoadingUniqueinPos(loadingDto.getLoadCode(), loadingDto.getLoadPosDto().getId())){
            log.error("The loadingCode indicated is already used by another loading in the pointofsale indicated");
            throw new DuplicateEntityException("Le code de loading indique est deja utilise par un autre loqding dans " +
                    "la base de donnee", ErrorCode.LOADING_DUPLICATED);
        }

        log.info("After all verification the record {} can be register in the DB", loadingDto);
        return LoadingDto.fromEntity(
                loadingRepository.save(
                        LoadingDto.toEntity(loadingDto)
                )
        );
    }

    @Override
    public LoadingDto updateLoading(LoadingDto loadingDto) {
        /*****************************************************
         * Il faut valider le loadingDto grace au validator
         */
        List<String> errors = LoadingValidator.validate(loadingDto);
        if(!errors.isEmpty()){
            log.error("Entity loadingDto not valid {}", loadingDto);
            throw new InvalidEntityException("Le loadingDto passe en argument n'est pas valide:  ",
                    ErrorCode.LOADING_NOT_VALID, errors);
        }

        /********************************************************
         * Il faut se rassurer que l'Id du Loading n'est pas null
         * et le cas echeant rechercher le Loading a update
         */
        if(loadingDto.getId() == null){
            log.error("The loading id is null ");
            throw new InvalidEntityException("Le id du loading a update est null ", ErrorCode.LOADING_NOT_VALID);
        }
        Optional<Loading> optionalLoading = loadingRepository.findLoadingById(loadingDto.getId());
        if(!optionalLoading.isPresent()){
            log.error("There is no Loading in the DB with the id precise");
            throw new InvalidEntityException("Aucun loading n'existe en BD avec l'Id precise ",
                    ErrorCode.LOADING_NOT_VALID);
        }
        //On recupere donc le Loading a update
        Loading loadingToUpdate = optionalLoading.get();

        /*********************************************************
         * On se rassure que ce n'est pas le Pointofsale qu'on
         * veut modifier car cela n'est pas possible
         */
        if(loadingDto.getLoadPosDto().getId() == null){
            log.error("The id of the pointofsale indicated in the loading is null");
            throw new InvalidEntityException("L'Id du pointofsale indique dans le loading est null ",
                    ErrorCode.LOADING_NOT_VALID);
        }
        if(!loadingDto.getLoadPosDto().getId().equals(loadingToUpdate.getLoadPos().getId())){
            log.error("It is not possible to modify the pointofsale associated with the Loading");
            throw new InvalidEntityException("Il n'est pas possible de modifier le pointofsale associe au Loading ",
                    ErrorCode.LOADING_NOT_VALID);
        }

        /***********************************************************
         * On verifie que ce n'est pas le UserManager qu'on veut
         * modifier car cela n'est pas possible
         */
        if(loadingDto.getLoadUserbmManagerDto().getId() == null){
            log.error("The id of the userbm manager indicated in the loading is null");
            throw new InvalidEntityException("L'Id du userbm manager indique dans le loading est null ",
                    ErrorCode.LOADING_NOT_VALID);
        }
        if(!loadingDto.getLoadUserbmManagerDto().getId().equals(loadingToUpdate.getLoadUserbmManager().getId())){
            log.error("It is not possible to modify the userbm manager associated with the Loading");
            throw new InvalidEntityException("Il n'est pas possible de modifier le userbm manager associe au Loading ",
                    ErrorCode.LOADING_NOT_VALID);
        }

        /*************************************************************
         * On verifie si c'est le UserSaler qu'on veut modifier et
         * si c'est le cas on se rassure qu'il existe vraiment en BD
         */
        if(loadingDto.getLoadUserbmManagerDto().getId() == null){
            log.error("The id of the userbm saler indicated in the loading is null");
            throw new InvalidEntityException("L'Id du userbm saler indique dans le loading est null ",
                    ErrorCode.LOADING_NOT_VALID);
        }
        if(!loadingDto.getLoadUserbmSalerDto().getId().equals(loadingToUpdate.getLoadUserbmSaler().getId())){
            /******
             * On va donc se rassurer que le nouveau userbm saler existe bien en BD
             */
            Optional<UserBM> optionalUserBMSaler = userBMRepository.findUserBMById(
                    loadingDto.getLoadUserbmManagerDto().getId());
            if(!optionalUserBMSaler.isPresent()){
                log.error("The new userbm indicated is not exist in the DB");
                throw new InvalidEntityException("Le nouveau userbm saler indique n'existe pas en BD ",
                        ErrorCode.LOADING_NOT_VALID);
            }
            //Ici on est sur que le nouveau userbm saler existe bien en BD et on peut donc faire le update
            loadingToUpdate.setLoadUserbmSaler(optionalUserBMSaler.get());
        }


        /***************************************************************
         * On verifie si c'est le code qu'on veut modifier et si c'est
         * le cas on se rassure quil n'y aura pas duplicata
         */
        if(!loadingDto.getLoadCode().equals(loadingToUpdate.getLoadCode())){
            //c'est le code qu'on veut modifier et on va donc verifier si l'unicite reste verifie
            if(!isLoadingUniqueinPos(loadingDto.getLoadCode(), loadingDto.getLoadPosDto().getId())){
                log.error("The new code indicated identify already another pointofsale in the DB");
                throw new DuplicateEntityException("Le nouveau code indique identifie deja un autre Loading dans le " +
                        "pointofsale indique ", ErrorCode.LOADING_DUPLICATED);
            }
            //Ici on est sur que le nouveau code est bon
            loadingToUpdate.setLoadCode(loadingDto.getLoadCode());
        }

        /***********************************************************
         * Et si tout va bien on effectue les mises a jour
         */
        loadingToUpdate.setLoadDate(loadingDto.getLoadDate());
        loadingToUpdate.setLoadTotalamountexpected(loadingDto.getLoadTotalamountexpected());
        loadingToUpdate.setLoadTotalamountpaid(loadingDto.getLoadTotalamountpaid());
        loadingToUpdate.setLoadSalereport(loadingDto.getLoadSalereport());
        loadingToUpdate.setLoadComment(loadingDto.getLoadComment());

        log.info("After all verification, the record {} can be done on the DB", loadingDto);
        return LoadingDto.fromEntity(loadingRepository.save(loadingToUpdate));
    }

    @Override
    public LoadingDto findLoadingById(Long loadingId) {
        if(loadingId == null){
            log.error("The loadingId sent is null");
            throw new NullArgumentException("Le loadingId precise est null ");
        }
        Optional<Loading> optionalLoading = loadingRepository.findLoadingById(loadingId);
        if(!optionalLoading.isPresent()){
            log.error("There is no Loading in the DB with the id {} precise", loadingId);
            throw new EntityNotFoundException("Aucun Loading n'existe en BD avec l'Id precise ",
                    ErrorCode.LOADING_NOT_FOUND);
        }
        return LoadingDto.fromEntity(optionalLoading.get());
    }

    @Override
    public Boolean isLoadingUniqueinPos(String loadingCode, Long posId) {
        if(posId == null){
            log.error("The posId sent is null");
            throw new NullArgumentException("Le posId precise est null ");
        }
        if(!StringUtils.hasLength(loadingCode)){
            log.error("The code precised must be not null");
            throw new NullArgumentException("Le code passe en argument pour le loading est null");
        }
        Optional<Loading> optionalLoading = loadingRepository.findLoadingByCodeandPos(loadingCode, posId);

        return optionalLoading.isEmpty();
    }

    @Override
    public LoadingDto findLoadingByCodeinPos(String loadingCode, Long posId) {
        if(posId == null){
            log.error("The posId sent is null");
            throw new NullArgumentException("Le posId precise est null ");
        }
        if(!StringUtils.hasLength(loadingCode)){
            log.error("The code precised must be not null");
            throw new NullArgumentException("Le code passe en argument pour le loading est null");
        }
        Optional<Loading> optionalLoading = loadingRepository.findLoadingByCodeandPos(loadingCode, posId);

        return LoadingDto.fromEntity(optionalLoading.get());
    }

    @Override
    public List<LoadingDto> findAllLoadinginPosBetween(Long posId, Instant startDate, Instant endDate) {
        if(posId == null){
            log.error("The posId precised is null");
            throw new NullArgumentException("Le posId precise en argument est null");
        }

        Optional<List<Loading>> optionalLoadingList = loadingRepository.findAllLoadinginPosBetween(posId, startDate,
                endDate);
        if(!optionalLoadingList.isPresent()){
            log.error("There is no Pointofsale in the DB with the posId precised");
            throw new EntityNotFoundException("Aucun pointofsale n'existe en BD avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }

        return optionalLoadingList.get().stream().map(LoadingDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<LoadingDto> findPageLoadinginPosBetween(Long posId, Instant startDate, Instant endDate,
                                                        int pagenum, int pagesize) {
        if(posId == null){
            log.error("The posId precised is null");
            throw new NullArgumentException("Le posId precise en argument est null");
        }

        Optional<Page<Loading>> optionalLoadingPage = loadingRepository.findPageLoadinginPosBetween(posId, startDate,
                endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalLoadingPage.isPresent()){
            log.error("There is no Pointofsale in the DB with the posId precised");
            throw new EntityNotFoundException("Aucun pointofsale n'existe en BD avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }

        return optionalLoadingPage.get().map(LoadingDto::fromEntity);
    }

    @Override
    public List<LoadingDto> findAllLoadingofUserbmManagerinPosBetween(Long posId, Long userbmManagerId, Instant startDate,
                                                                      Instant endDate) {
        if(posId == null){
            log.error("The posId precised is null");
            throw new NullArgumentException("Le posId precise en argument est null");
        }
        if(userbmManagerId == null){
            log.error("The userbmManagerId precised is null");
            throw new NullArgumentException("Le userbmManagerId precise en argument est null");
        }
        Optional<List<Loading>> optionalLoadingList = loadingRepository.findAllLoadingofUserbmManagerinPosBetween(posId,
                userbmManagerId, startDate, endDate);
        if(!optionalLoadingList.isPresent()){
            log.error("There is no Pointofsale in the DB with the posId precised");
            throw new EntityNotFoundException("Aucun pointofsale n'existe en BD avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }

        return optionalLoadingList.get().stream().map(LoadingDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<LoadingDto> findPageLoadingofUserbmManagerinPosBetween(Long posId, Long userbmManagerId, Instant startDate,
                                                                       Instant endDate, int pagenum, int pagesize) {
        if(posId == null){
            log.error("The posId precised is null");
            throw new NullArgumentException("Le posId precise en argument est null");
        }
        if(userbmManagerId == null){
            log.error("The userbmManagerId precised is null");
            throw new NullArgumentException("Le userbmManagerId precise en argument est null");
        }
        Optional<Page<Loading>> optionalLoadingPage = loadingRepository.findPageLoadingofUserbmManagerinPosBetween(posId,
                userbmManagerId, startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalLoadingPage.isPresent()){
            log.error("There is no Pointofsale in the DB with the posId precised");
            throw new EntityNotFoundException("Aucun pointofsale n'existe en BD avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }

        return optionalLoadingPage.get().map(LoadingDto::fromEntity);
    }

    @Override
    public List<LoadingDto> findAllLoadingofUserbmSalerinPosBetween(Long posId, Long userbmSalerId, Instant startDate,
                                                                    Instant endDate) {
        if(posId == null){
            log.error("The posId precised is null");
            throw new NullArgumentException("Le posId precise en argument est null");
        }
        if(userbmSalerId == null){
            log.error("The userbmSalerId precised is null");
            throw new NullArgumentException("Le userbmSalerId precise en argument est null");
        }
        Optional<List<Loading>> optionalLoadingList = loadingRepository.findAllLoadingofUserbmSalerinPosBetween(posId,
                userbmSalerId, startDate, endDate);
        if(!optionalLoadingList.isPresent()){
            log.error("There is no Pointofsale in the DB with the posId precised");
            throw new EntityNotFoundException("Aucun pointofsale n'existe en BD avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }

        return optionalLoadingList.get().stream().map(LoadingDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<LoadingDto> findPageLoadingofUserbmSalerinPosBetween(Long posId, Long userbmSalerId, Instant startDate,
                                                                     Instant endDate, int pagenum, int pagesize) {
        if(posId == null){
            log.error("The posId precised is null");
            throw new NullArgumentException("Le posId precise en argument est null");
        }
        if(userbmSalerId == null){
            log.error("The userbmSalerId precised is null");
            throw new NullArgumentException("Le userbmSalerId precise en argument est null");
        }
        Optional<Page<Loading>> optionalLoadingPage = loadingRepository.findPageLoadingofUserbmSalerinPosBetween(posId,
                userbmSalerId, startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalLoadingPage.isPresent()){
            log.error("There is no Pointofsale in the DB with the posId precised");
            throw new EntityNotFoundException("Aucun pointofsale n'existe en BD avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }

        return optionalLoadingPage.get().map(LoadingDto::fromEntity);
    }

    @Override
    public List<LoadingDto> findAllLoadingofUserbmManagerandSalerinPosBetween(Long posId, Long userbmManagerId,
                                                                              Long userbmSalerId, Instant startDate,
                                                                              Instant endDate) {
        if(posId == null){
            log.error("The posId precised is null");
            throw new NullArgumentException("Le posId precise en argument est null");
        }
        if(userbmManagerId == null){
            log.error("The userbmManagerId precised is null");
            throw new NullArgumentException("Le userbmManagerId precise en argument est null");
        }
        if(userbmSalerId == null){
            log.error("The userbmSalerId precised is null");
            throw new NullArgumentException("Le userbmSalerId precise en argument est null");
        }
        Optional<List<Loading>> optionalLoadingList = loadingRepository.
                findAllLoadingofUserbmManagerandSalerinPosBetween(
                        posId, userbmSalerId, userbmSalerId, startDate, endDate);
        if(!optionalLoadingList.isPresent()){
            log.error("There is no Pointofsale in the DB with the posId precised");
            throw new EntityNotFoundException("Aucun pointofsale n'existe en BD avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }

        return optionalLoadingList.get().stream().map(LoadingDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<LoadingDto> findPageLoadingofUserbmManagerandSalerinPosBetween(Long posId, Long userbmManagerId,
                                                                               Long userbmSalerId, Instant startDate,
                                                                               Instant endDate, int pagenum, int pagesize) {
        if(posId == null){
            log.error("The posId precised is null");
            throw new NullArgumentException("Le posId precise en argument est null");
        }
        if(userbmManagerId == null){
            log.error("The userbmManagerId precised is null");
            throw new NullArgumentException("Le userbmManagerId precise en argument est null");
        }
        if(userbmSalerId == null){
            log.error("The userbmSalerId precised is null");
            throw new NullArgumentException("Le userbmSalerId precise en argument est null");
        }
        Optional<Page<Loading>> optionalLoadingPage = loadingRepository.
                findPageLoadingofUserbmManagerandSalerinPosBetween(posId, userbmSalerId, userbmSalerId, startDate,
                        endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalLoadingPage.isPresent()){
            log.error("There is no Pointofsale in the DB with the posId precised");
            throw new EntityNotFoundException("Aucun pointofsale n'existe en BD avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }

        return optionalLoadingPage.get().map(LoadingDto::fromEntity);
    }

    @Override
    public Boolean isLoadingDeleteableById(Long loadingId) {
        return true;
    }

    @Override
    public Boolean deleteLoadingById(Long loadingId) {
        if(loadingId == null){
            log.error("The loadingId sent is null");
            throw new NullArgumentException("Le loadingId precise est null ");
        }
        Optional<Loading> optionalLoading = loadingRepository.findLoadingById(loadingId);
        if(!optionalLoading.isPresent()){
            log.error("There is loading in the DB with the loadingId {} indicated ", loadingId);
            throw new EntityNotFoundException("Il n'existe pas de Loading avec l'id precise ",
                    ErrorCode.LOADING_NOT_FOUND);
        }
        if(!isLoadingDeleteableById(loadingId)){
            log.error("The loading indicated is not deleteable");
            throw new EntityNotDeleteableException("Le loading ne peut etre supprimer ",
                    ErrorCode.LOADING_NOT_DELETEABLE);
        }
        loadingRepository.delete(optionalLoading.get());
        return true;
    }

}
