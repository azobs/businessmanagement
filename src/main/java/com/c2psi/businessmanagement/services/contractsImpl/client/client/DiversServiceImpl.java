package com.c2psi.businessmanagement.services.contractsImpl.client.client;

import com.c2psi.businessmanagement.dtos.client.client.DiversCashAccountDto;
import com.c2psi.businessmanagement.dtos.client.client.DiversDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.Divers;
import com.c2psi.businessmanagement.models.DiversCashAccount;
import com.c2psi.businessmanagement.models.Pointofsale;
import com.c2psi.businessmanagement.repositories.client.client.DiversCashAccountRepository;
import com.c2psi.businessmanagement.repositories.client.client.DiversRepository;
import com.c2psi.businessmanagement.repositories.pos.pos.PointofsaleRepository;
import com.c2psi.businessmanagement.services.contracts.client.client.DiversService;
import com.c2psi.businessmanagement.validators.client.client.DiversValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value="DiversServiceV1")
@Slf4j
@Transactional
public class DiversServiceImpl implements DiversService {
    private PointofsaleRepository pointofsaleRepository;
    private DiversRepository diversRepository;
    private DiversCashAccountRepository diversCashAccountRepository;

    @Autowired
    public DiversServiceImpl(PointofsaleRepository pointofsaleRepository, DiversRepository diversRepository,
                             DiversCashAccountRepository diversCashAccountRepository) {
        this.pointofsaleRepository = pointofsaleRepository;
        this.diversRepository = diversRepository;
        this.diversCashAccountRepository = diversCashAccountRepository;
    }

    @Override
    public DiversDto saveDivers(DiversDto diversDto) {

        /***************************************************************
         * Il faut valider le dto pris en parametre pour se rassurer
         * que la saisie des donnees a ete bien faite
         */
        List<String> errors = DiversValidator.validate(diversDto);
        if(!errors.isEmpty()){
            log.error("Entity diversDto not valid {}", diversDto);
            throw new InvalidEntityException("Le divers passe en argument n'est pas valide:  ",
                    ErrorCode.DIVERS_NOT_VALID, errors);
        }

        /**********************************************************************
         * On se rassure de l'existence du pointofsale associe au divers
         */
        if(diversDto.getDiversPosId() == null){
            log.error("The pointofsale for the divers has not been precised");
            throw new InvalidEntityException("Aucun pointofsale n'est associe avec le divers"
                    , ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        Optional<Pointofsale> optionalPointofsale = pointofsaleRepository.findPointofsaleById
                (diversDto.getDiversPosId());
        if(!optionalPointofsale.isPresent()){
            log.error("The pointofsale precised does not exist in the DB");
            throw new InvalidEntityException("Le Pointofsale associe avec le client n'existe pas en BD"
                    , ErrorCode.POINTOFSALE_NOT_FOUND);
        }

        /*********************************************************************************************
         * Il faut se rassurer qu'il n'y aurait pas de duplicata de divers dans la base de donnee
         * au cas ou l'email n'est pas null
         */

        if(!isDiversUnique(diversDto.getDiversAddressDto().getEmail())){

            log.error("A divers already exist in the DB for the same pointofsale with the same name, email");
            throw new DuplicateEntityException("Un divers existe deja dans le pointofsale precise avec le " +
                    diversDto.getDiversAddressDto().getEmail(), ErrorCode.DIVERS_DUPLICATED);
        }

        /******************************************************
         * Il faut verifier si un compte Divers existe et si ce
         * n'est pas le cas on cree un compte cash sinon on associe celui
         * qui existe au divers cree
         */
        Optional<List<DiversCashAccount>> optionalDiversCashAccount = Optional.of(diversCashAccountRepository.findAll());
        if(!optionalDiversCashAccount.isPresent()){
            /****
             * Aucun compte cash Divers n'existe dans la BD
             */
            log.error("Aucun compte Divers n'existe dans la BD");
            throw new EntityNotFoundException("Aucun compte cash n'existe dans la BD");
        }
        List<DiversCashAccount> diversCashAccountList = optionalDiversCashAccount.get();
        if(diversCashAccountList.size()>1){
            log.error("Plus d'un compte cash existe dans la BD pour le Divers");
            throw new InvalidEntityException("Plus d'un compte cash existe dans la BD pour les divers");
        }

        if(diversCashAccountList.size()==0){
            /****************************
             * Ici on doit creer le compte cash des divers
             * car aucun n'existe donc en fait c'est le premier c
             * compte cash en creation
             */
            DiversCashAccountDto diversCashAccountDto = DiversCashAccountDto.builder()
                    .dcaBalance(BigDecimal.valueOf(0))
                    .build();
            DiversCashAccountDto diversCashAccountDtoSaved = DiversCashAccountDto.fromEntity(
                    diversCashAccountRepository.save(DiversCashAccountDto.toEntity(diversCashAccountDto)));
            diversDto.setDiversCaDto(diversCashAccountDtoSaved);
        }
        /******
         * Ici on est sur qu'il existe un et un seul compte cash donc
         * c'est dans ce compte que les operations du cash seront execute
         */

        log.info("After all verification, the record {} can be done on the DB", diversDto);

        return DiversDto.fromEntity(
                diversRepository.save(
                        DiversDto.toEntity(diversDto)
                )
        );
    }

    @Override
    public DiversDto updateDivers(DiversDto diversDto) {
        /***************************************************************
         * Il faut valider le dto pris en parametre pour se rassurer
         * que la saisie des donnees a ete bien faite
         */
        List<String> errors = DiversValidator.validate(diversDto);
        if(!errors.isEmpty()){
            log.error("Entity diversDto not valid {}", diversDto);
            throw new InvalidEntityException("Le divers passe en argument n'est pas valide:  ",
                    ErrorCode.DIVERS_NOT_VALID, errors);
        }

        Optional<Divers> optionalDivers = diversRepository.findDiversById(diversDto.getId());
        if(!optionalDivers.isPresent()){
            log.error("The Divers precised does not exist in the DB");
            throw new InvalidEntityException("Le Divers associe avec le id n'existe pas en BD"
                    , ErrorCode.DIVERS_NOT_VALID);
        }
        Divers diversToUpdate = optionalDivers.get();

        /***************************************************************
         * On verifie que ce n'est pas le pointofsale quon veut modifier
         */
        if(!diversDto.getDiversPosId().equals(diversToUpdate.getDiversPosId())){
            log.error("the pointofsale cannot be update");
            throw new InvalidEntityException("Le pointofsale ne peut etre modifier", ErrorCode.DIVERS_NOT_VALID);
        }

        if(diversDto.getDiversAddressDto().getEmail() != null && diversToUpdate.getDiversAddress().getEmail() != null){
            if(!diversDto.getDiversAddressDto().getEmail().equals(diversToUpdate.getDiversAddress().getEmail())) {
                if (!isDiversUnique(diversDto.getDiversAddressDto().getEmail())) {
                    log.error("A divers already exist in the DB for the same pointofsale with the same name, email");
                    throw new DuplicateEntityException("Un client existe deja dans le pointofsale precise avec l'email " +
                            diversDto.getDiversAddressDto().getEmail(), ErrorCode.DIVERS_DUPLICATED);
                }
                //ici cela signifie donc quon peut faire la modification de l'email sans souci de duplication
                diversToUpdate.getDiversAddress().setEmail(diversDto.getDiversAddressDto().getEmail());
            }
        }
        /*
        Mise a jour des donnes de ladresse
        String numtel1;
        String numtel2;
        String numtel3;
        String quartier;
        String pays;
        String ville;
        String localisation;
        String email;
         */
        diversToUpdate.getDiversAddress().setLocalisation(diversDto.getDiversAddressDto().getLocalisation());
        diversToUpdate.getDiversAddress().setNumtel1(diversDto.getDiversAddressDto().getNumtel1());
        diversToUpdate.getDiversAddress().setNumtel2(diversDto.getDiversAddressDto().getNumtel2());
        diversToUpdate.getDiversAddress().setNumtel3(diversDto.getDiversAddressDto().getNumtel3());
        diversToUpdate.getDiversAddress().setQuartier(diversDto.getDiversAddressDto().getQuartier());
        diversToUpdate.getDiversAddress().setPays(diversDto.getDiversAddressDto().getPays());
        diversToUpdate.getDiversAddress().setVille(diversDto.getDiversAddressDto().getVille());

        /*********************************************************
         * On fait le reste des mise a jour sur l'entite Divers
         */
        diversToUpdate.setDiversName(diversDto.getDiversName());

        log.info("After all verification, the record {} can be done on the DB", diversDto);
        return DiversDto.fromEntity(diversRepository.save(diversToUpdate));
    }

    @Override
    public DiversDto findDiversByEmail(String diversEmail) {
        if(!StringUtils.hasLength(diversEmail)){
            log.error("Divers diversEmail  is null");
            throw new NullArgumentException("le diversEmail passe en argument de la methode est null");
        }

        Optional<Divers> optionalDivers = diversRepository.findDiversByEmail(diversEmail);
        if(!optionalDivers.isPresent()){
            log.error("There is no divers with the name {}",diversEmail);
            throw new EntityNotFoundException("Aucun divers n'existe avec email indique");
        }
        return DiversDto.fromEntity(optionalDivers.get());
    }

    @Override
    public DiversDto findDiversById(Long diversId) {
        if(diversId == null){
            log.error("The diversId precised is null");
            throw new NullArgumentException("L'argument precise est null");
        }

        Optional<Divers> optionalDivers = diversRepository.findDiversById(diversId);
        if(!optionalDivers.isPresent()){
            log.error("There is no divers with the id {}",diversId);
            throw new EntityNotFoundException("Aucun divers n'existe avec l'Id indique");
        }
        return DiversDto.fromEntity(optionalDivers.get());
    }

    @Override
    public Boolean isDiversUnique(String diversEmail) {
        if(!StringUtils.hasLength(diversEmail)){
            log.error("Divers diversEmail is null");
            throw new NullArgumentException("le diversEmail passe en argument de la methode est null");
        }

        //on doit aussi verifier que lemail sera unique dans ladresse
        Optional<Divers> optionalDivers = diversRepository.findDiversByEmail(diversEmail);

        return optionalDivers.isEmpty();
    }

    @Override
    public List<DiversDto> findAllDiversofPos(Long posId) {
        if(posId == null){
            log.error("The posId precised in the method is null");
            throw new NullArgumentException("L'argument de la methode est null");
        }
        Optional<List<Divers>> optionalDiversList = diversRepository.findAllDiversofPos(posId);

        if(!optionalDiversList.isPresent()){
            log.error("There is no pointofsale with the id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'a l'id precise "+posId, ErrorCode.POINTOFSALE_NOT_FOUND);
        }

        return optionalDiversList.get().stream().map(DiversDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<DiversDto> findPageDiversofPos(Long posId, int pagenum, int pagesize) {
        if(posId == null){
            log.error("The posId precised in the method is null");
            throw new NullArgumentException("L'argument de la methode est null");
        }
        Optional<Page<Divers>> optionalDiversPage = diversRepository.findPageDiversofPos(posId,
                PageRequest.of(pagenum, pagesize));

        if(!optionalDiversPage.isPresent()){
            log.error("There is no pointofsale with the id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'a l'id precise "+posId, ErrorCode.POINTOFSALE_NOT_FOUND);
        }

        return optionalDiversPage.get().map(DiversDto::fromEntity);
    }

    @Override
    public List<DiversDto> findAllDiversByNameinPos(String diversName, Long posId) {

        if(!StringUtils.hasLength(diversName)){
            log.error("The diversName precised in the method is null or empty");
            throw new NullArgumentException("L'argument de la methode est vide ou null");
        }

        if(posId == null){
            log.error("The posId precised in the method is null");
            throw new NullArgumentException("L'argument de la methode est null");
        }

        Optional<List<Divers>> optionalDiversList = diversRepository.findAllDiversByNameinPos(diversName, posId);

        if(!optionalDiversList.isPresent()){
            log.error("There is no divers with the name {}", diversName);
            throw new EntityNotFoundException("Aucun Divers n'a le name precise "+diversName,
                    ErrorCode.DIVERS_NOT_FOUND);
        }

        return optionalDiversList.get().stream().map(DiversDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<DiversDto> findPageDiversByNameinPos(String diversName, Long posId, int pagenum, int pagesize) {
        if(!StringUtils.hasLength(diversName)){
            log.error("The diversName precised in the method is null or empty");
            throw new NullArgumentException("L'argument de la methode est vide ou null");
        }

        if(posId == null){
            log.error("The posId precised in the method is null");
            throw new NullArgumentException("L'argument de la methode est null");
        }

        Optional<Page<Divers>> optionalDiversPage = diversRepository.findPageDiversByNameinPos(diversName, posId,
                PageRequest.of(pagenum, pagesize));

        if(!optionalDiversPage.isPresent()){
            log.error("There is no divers with the name {}", diversName);
            throw new EntityNotFoundException("Aucun Divers n'a le name precise "+diversName,
                    ErrorCode.DIVERS_NOT_FOUND);
        }

        return optionalDiversPage.get().map(DiversDto::fromEntity);
    }

    @Override
    public Boolean isDiversDeleteable(Long diversId) {
        return true;
    }

    @Override
    public Boolean deleteDiversById(Long diversId) {
        if(diversId == null){
            log.error("The diversId precised is null");
            throw new NullArgumentException("L'argument precise est null");
        }

        Optional<Divers> optionalDivers = diversRepository.findDiversById(diversId);

        if(!optionalDivers.isPresent()){
            log.error("There is no divers in the DB with the indicated id {}", diversId);
            throw new EntityNotFoundException("Aucun divers n'existe avec l'id indique ", ErrorCode.DIVERS_NOT_FOUND);
        }

        if(!isDiversDeleteable(diversId)){
            log.error("The divers indicated cannot be deleted");
            throw new EntityNotDeleteableException("Le divers indique ne peut etre supprime",
                    ErrorCode.DIVERS_NOT_DELETEABLE);
        }
        diversRepository.delete(optionalDivers.get());
        return true;
    }
}
