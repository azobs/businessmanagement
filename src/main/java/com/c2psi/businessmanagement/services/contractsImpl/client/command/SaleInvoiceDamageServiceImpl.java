package com.c2psi.businessmanagement.services.contractsImpl.client.command;

import com.c2psi.businessmanagement.dtos.client.command.SaleInvoiceDamageDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.Client;
import com.c2psi.businessmanagement.models.Pointofsale;
import com.c2psi.businessmanagement.models.SaleInvoiceDamage;
import com.c2psi.businessmanagement.models.UserBM;
import com.c2psi.businessmanagement.repositories.client.client.ClientRepository;
import com.c2psi.businessmanagement.repositories.client.command.SaleInvoiceDamageRepository;
import com.c2psi.businessmanagement.repositories.pos.pos.PointofsaleRepository;
import com.c2psi.businessmanagement.repositories.pos.userbm.UserBMRepository;
import com.c2psi.businessmanagement.services.contracts.client.command.SaleInvoiceDamageService;
import com.c2psi.businessmanagement.validators.client.command.SaleInvoiceDamageValidator;
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

@Service(value="SaleInvoiceDamageServiceV1")
@Slf4j
@Transactional
public class SaleInvoiceDamageServiceImpl implements SaleInvoiceDamageService {
    private PointofsaleRepository pointofsaleRepository;
    private ClientRepository clientRepository;
    private UserBMRepository userBMRepository;
    private SaleInvoiceDamageRepository saleInvoicedamRepository;

    @Autowired
    public SaleInvoiceDamageServiceImpl(PointofsaleRepository pointofsaleRepository, ClientRepository clientRepository,
                                         UserBMRepository userBMRepository,
                                         SaleInvoiceDamageRepository saleInvoicedamRepository) {
        this.pointofsaleRepository = pointofsaleRepository;
        this.clientRepository = clientRepository;
        this.userBMRepository = userBMRepository;
        this.saleInvoicedamRepository = saleInvoicedamRepository;
    }


    @Override
    public SaleInvoiceDamageDto saveSaleInvoiceDamage(SaleInvoiceDamageDto saleiDamageDto) {

        /*******************************************************************
         * Il faut valider l'argument pris en parametre grace au validateur
         */
        List<String> errors = SaleInvoiceDamageValidator.validate(saleiDamageDto);
        if(!errors.isEmpty()){
            log.error("Entity saleiDamageDto not valid {}", saleiDamageDto);
            throw new InvalidEntityException("Le saleiDamageDto passe en argument n'est pas valide:  ",
                    ErrorCode.SALEINVOICEDAMAGE_NOT_VALID, errors);
        }

        /******************************************************************
         * Se rassurer que l'id du pointofsale n'est pas null et lorsque
         * c'est le cas il faut se rassurer que le pointofsale existe
         * vraiment en BD
         */
        if(saleiDamageDto.getSaleidamPosId() == null){
            log.error("The id of the pointofsale associeted with the saleinvoiceDamage can't be null");
            throw new InvalidEntityException("L'id du pointofsale associe au saleinvoiceDamage ne peut etre null",
                    ErrorCode.SALEINVOICEDAMAGE_NOT_VALID);
        }
        //L'id netant pas null on va se rassurer que ca existe vraiment en BD
        Optional<Pointofsale> optionalPointofsale = pointofsaleRepository.findPointofsaleById(
                saleiDamageDto.getSaleidamPosId());
        if(!optionalPointofsale.isPresent()){
            log.error("The pointofsale precised in the request is not in the DB");
            throw new InvalidEntityException("Le pointofsale precise dans la requete n'est pas existant dans la BD ",
                    ErrorCode.SALEINVOICEDAMAGE_NOT_VALID);
        }

        /******************************************************************
         * Se rassurer que l'id du userbm n'est pas null et lorsque
         * c'est le cas il faut se rassurer que le userbm existe
         * vraiment en BD
         */
        if(saleiDamageDto.getSaleidamUserbmDto().getId() == null){
            log.error("The id of the userbm associeted with the saleinvoiceDamage can't be null");
            throw new InvalidEntityException("L'id du userbm associe au saleinvoiceDamage ne peut etre null",
                    ErrorCode.SALEINVOICEDAMAGE_NOT_VALID);
        }
        Optional<UserBM> optionalUserBM = userBMRepository.findUserBMById(saleiDamageDto.getSaleidamUserbmDto().getId());
        if(!optionalUserBM.isPresent()){
            log.error("The userbm precised in the request is not in the DB");
            throw new InvalidEntityException("Le userbm precise dans la requete n'est pas existant dans la BD ",
                    ErrorCode.SALEINVOICEDAMAGE_NOT_VALID);
        }

        /******************************************************************
         * Se rassurer que l'id du client n'est pas null et lorsque
         * c'est le cas il faut se rassurer que le client existe
         * vraiment en BD
         */
        if(saleiDamageDto.getSaleidamClientDto().getId() == null){
            log.error("The id of the client associeted with the saleinvoiceDamage can't be null");
            throw new InvalidEntityException("L'id du client associe au saleinvoiceDamage ne peut etre null",
                    ErrorCode.SALEINVOICEDAMAGE_NOT_VALID);
        }
        Optional<Client> optionalClient = clientRepository.findClientById(saleiDamageDto.getSaleidamClientDto().getId());
        if(!optionalClient.isPresent()){
            log.error("The client precised in the request is not in the DB");
            throw new InvalidEntityException("Le client precise dans la requete n'est pas existant dans la BD ",
                    ErrorCode.SALEINVOICEDAMAGE_NOT_VALID);
        }

        /********************************************************************
         * Se rassurer que si le pointofsale du userbm existe alors il est
         * le meme que celui du saleinvoiceDamage
         */
        if(saleiDamageDto.getSaleidamUserbmDto().getBmPosId() != null){
            //Alors il faut que ce soit un Userbm du meme pointofsale de saleinvoiceDamage
            if(!saleiDamageDto.getSaleidamUserbmDto().getBmPosId().equals(optionalPointofsale.get().getId())){
                log.error("The userbm and the saleinvoiceDamage must belong to the same pointofsale ");
                throw new InvalidEntityException("Le userbm et le saleinvoiceDamage doivent appartenir au meme pointofsale ",
                        ErrorCode.SALEINVOICEDAMAGE_NOT_VALID);
            }
        }

        /********************************************************************
         * Se rassurer que le pointofsale du client est le meme que celui
         * du saleinvoiceDamage
         */
        if(!optionalPointofsale.get().getId().equals(saleiDamageDto.getSaleidamClientDto().getClientPosId())){
            log.error("The client and the saleinvoiceDamage must belong to the same pointofsale ");
            throw new InvalidEntityException("Le client et le saleinvoiceDamage doivent appartenir au meme pointofsale ",
                    ErrorCode.SALEINVOICEDAMAGE_NOT_VALID);
        }

        /*********************************************************************
         * Se rassurer que le saleinvoiceDamage sera unique dans le pos indique
         */
        if(!isSaleInvoiceDamageUniqueinPos(saleiDamageDto.getSaleidamCode(), saleiDamageDto.getSaleidamPosId())){
            log.error("There is another SaleinvoiceDamage in the DB with the same code in the pointofsale indicated ");
            throw new DuplicateEntityException("Une saleinvoiceDamage existe deja dans la BD avec le meme code dans " +
                    "pour le pointofsale indique", ErrorCode.SALEINVOICEDAMAGE_DUPLICATED);
        }

        /////////////////
        log.info("After all verification, the record {} can be saved on the DB", saleiDamageDto);

        return SaleInvoiceDamageDto.fromEntity(
                saleInvoicedamRepository.save(
                        SaleInvoiceDamageDto.toEntity(saleiDamageDto)
                )
        );
    }

    @Override
    public SaleInvoiceDamageDto updateSaleInvoiceDamage(SaleInvoiceDamageDto saleiDamageDto) {

        /*******************************************************************
         * Il faut valider l'argument pris en parametre grace au validateur
         */
        List<String> errors = SaleInvoiceDamageValidator.validate(saleiDamageDto);
        if(!errors.isEmpty()){
            log.error("Entity saleiDamageDto not valid {}", saleiDamageDto);
            throw new InvalidEntityException("Le saleiDamageDto passe en argument n'est pas valide:  ",
                    ErrorCode.SALEINVOICEDAMAGE_NOT_VALID, errors);
        }

        /*********************************************
         * On recherche le saleinvoiceDamage a update
         */
        if(saleiDamageDto.getId() == null){
            log.error("It is not possible to update a saleinvoiceDamage without get its id");
            throw new InvalidEntityException("l'Id du saleinvoiceDamage a update est null ",
                    ErrorCode.SALEINVOICEDAMAGE_NOT_VALID);
        }
        Optional<SaleInvoiceDamage> optionalSaleInvoiceDamage = saleInvoicedamRepository.
                findSaleInvoiceDamageById(saleiDamageDto.getId());
        if(!optionalSaleInvoiceDamage.isPresent()){
            log.error("There is no SaleinvoiceDamage in the DB with the precised id {}", saleiDamageDto.getId());
            throw new InvalidEntityException("Il n'existe pas de SaleInvoiceDamage in the DB with the precised id ",
                    ErrorCode.SALEINVOICEDAMAGE_NOT_VALID);
        }
        SaleInvoiceDamage saleInvoiceDamageToUpdate = optionalSaleInvoiceDamage.get();

        /********************************************************************
         * On se rassure que ce n'est pas le pointofsale quon veut modifier
         */
        if(saleiDamageDto.getSaleidamPosId() != null) {
            if (!saleInvoiceDamageToUpdate.getSaleidamPosId().equals(saleiDamageDto.getSaleidamPosId())) {
                log.error("It is not possible to update the pointofsale of a saleinvoiceDamage");
                throw new InvalidEntityException("Il n'est pas possible de update le pointofsale du saleinvoiceDamage ",
                        ErrorCode.SALEINVOICEDAMAGE_NOT_VALID);
            }
        }

        /********************************************************************
         * On se rassure que ce n'est pas le userbm quon veut modifier
         */
        if(saleiDamageDto.getSaleidamUserbmDto().getId() != null){
            if(!saleInvoiceDamageToUpdate.getSaleidamUserbm().getId().equals(saleiDamageDto.getSaleidamUserbmDto().getId())){
                log.error("It is not possible to update the userbm of a saleinvoiceDamage");
                throw new InvalidEntityException("Il n'est pas possible de update le userbm du saleinvoiceDamage ",
                        ErrorCode.SALEINVOICEDAMAGE_NOT_VALID);
            }
        }

        if(saleiDamageDto.getSaleidamClientDto().getId() != null){
            if(!saleInvoiceDamageToUpdate.getSaleidamClient().getId().equals(saleiDamageDto.getSaleidamClientDto().getId())){
                /**************************************************
                 * Ceci signifie qu'on veut modifier le client donc on doit verifier si le nouveau existe
                 */
                Optional<Client> optionalClient = clientRepository.findClientById(saleiDamageDto.getSaleidamClientDto().getId());
                if(!optionalClient.isPresent()){
                    log.error("The new client precised does not exist in the DB ");
                    throw new InvalidEntityException("Le nouveau client precise dans la requete de mise a jour n'existe " +
                            "pas en BD ", ErrorCode.SALEINVOICEDAMAGE_NOT_VALID);
                }
                //Ici on est sur que le nouveau client existe donc on prepare la mise a jour
                saleInvoiceDamageToUpdate.setSaleidamClient(optionalClient.get());
            }
        }

        /****************************************************************************************
         * On verifie si c'est le code de la facture quon veut modifier et si cest le cas on va
         * verifier si ca va etre unique
         */
        if(!saleInvoiceDamageToUpdate.getSaleidamCode().equals(saleiDamageDto.getSaleidamCode())){
            if(!isSaleInvoiceDamageUniqueinPos(saleiDamageDto.getSaleidamCode(),
                    saleInvoiceDamageToUpdate.getSaleidamPosId())){
                log.error("There is another SaleinvoiceDamage in the DB with the same code in the pointofsale indicated ");
                throw new DuplicateEntityException("Une saleinvoiceDamage existe deja dans la BD avec le meme code dans " +
                        "pour le pointofsale indique", ErrorCode.SALEINVOICEDAMAGE_DUPLICATED);
            }
            //On est sur quil y aura pas de duplicata
            saleInvoiceDamageToUpdate.setSaleidamCode(saleiDamageDto.getSaleidamCode());
        }

        //Maintenant on peut faire toutes les autres update
        saleInvoiceDamageToUpdate.setSaleidamTotalcolis(saleiDamageDto.getSaleidamTotalcolis());
        saleInvoiceDamageToUpdate.setSaleidamNumberchanged(saleiDamageDto.getSaleidamNumberchanged());
        saleInvoiceDamageToUpdate.setSaleidamNumbertochange(saleiDamageDto.getSaleidamNumbertochange());
        saleInvoiceDamageToUpdate.setSaleidamDeliveryDate(saleiDamageDto.getSaleidamDeliveryDate());

        log.info("After all verification, the record {} can be done on the DB", saleiDamageDto);
        return SaleInvoiceDamageDto.fromEntity(saleInvoicedamRepository.save(saleInvoiceDamageToUpdate));
    }

    @Override
    public Boolean isSaleInvoiceDamageUniqueinPos(String saleiDamageCode, Long posId) {
        if(!StringUtils.hasLength(saleiDamageCode)){
            log.error("The precise code {} is empty in the method ", saleiDamageCode);
            throw new NullArgumentException("L'argument code precise est null ou vide ");
        }

        if(posId == null){
            log.error("The posId precised in the method is null");
            throw new NullArgumentException("L'argument posId precise est null");
        }

        Optional<SaleInvoiceDamage> optionalSaleInvoiceDamage = saleInvoicedamRepository.
                findSaleInvoiceDamageByCodeinPos(saleiDamageCode, posId);

        return optionalSaleInvoiceDamage.isEmpty();
    }

    @Override
    public Boolean isSaleInvoiceDamageDeleteable(Long saleiDamageId) {
        return true;
    }

    @Override
    public Boolean deleteSaleInvoiceDamageById(Long saleiDamageId) {
        if(saleiDamageId == null){
            log.error("The saleiDamageId precised in the method is null");
            throw new NullArgumentException("L'argument saleiDamageId precise est null");
        }
        Optional<SaleInvoiceDamage> optionalSaleInvoiceDamage = saleInvoicedamRepository.findSaleInvoiceDamageById(saleiDamageId);
        if(!optionalSaleInvoiceDamage.isPresent()){
            log.error("There is no saleinvoiceDamage in the DB with that id {}", saleiDamageId);
            throw new EntityNotFoundException("Le saleinvoiceDamage qu'on veut supprimer n'existe pas en BD",
                    ErrorCode.SALEINVOICEDAMAGE_NOT_FOUND);
        }
        if(!isSaleInvoiceDamageDeleteable(saleiDamageId)){
            log.error("It is not possible to delete the saleinvoiceDamage precise with the id {}", saleiDamageId);
            throw new EntityNotDeleteableException("Le saleinvoiceDamage precise par l'id ne peut etre supprimer ",
                    ErrorCode.SALEINVOICEDAMAGE_NOT_DELETEABLE);
        }
        saleInvoicedamRepository.delete(optionalSaleInvoiceDamage.get());
        return true;
    }

    @Override
    public SaleInvoiceDamageDto findSaleInvoiceDamageById(Long saleiDamageId) {
        if(saleiDamageId == null){
            log.error("The saleiDamageid precised is null");
            throw new NullArgumentException("L'id passe a la methode est null");
        }
        Optional<SaleInvoiceDamage> optionalSaleInvoiceDamage = saleInvoicedamRepository.
                findSaleInvoiceDamageById(saleiDamageId);
        if(!optionalSaleInvoiceDamage.isPresent()){
            log.error("There is no saleinvoiceDamage in the DB with the precise id {}", saleiDamageId);
            throw new EntityNotFoundException("Aucun saleinvoiceDamage n'existe avec le id precise ",
                    ErrorCode.SALEINVOICEDAMAGE_NOT_FOUND);
        }
        return SaleInvoiceDamageDto.fromEntity(optionalSaleInvoiceDamage.get());
    }

    @Override
    public SaleInvoiceDamageDto findSaleInvoiceDamageByCode(String saleiDamageCode, Long posId) {
        if(!StringUtils.hasLength(saleiDamageCode)){
            log.error("The precise code {} is empty in the method ", saleiDamageCode);
            throw new NullArgumentException("L'argument code precise est null ou vide ");
        }

        if(posId == null){
            log.error("The posId precised in the method is null");
            throw new NullArgumentException("L'argument posId precise est null");
        }

        Optional<SaleInvoiceDamage> optionalSaleInvoiceDamage = saleInvoicedamRepository.
                findSaleInvoiceDamageByCodeinPos(saleiDamageCode, posId);

        return SaleInvoiceDamageDto.fromEntity(optionalSaleInvoiceDamage.get());
    }

    @Override
    public List<SaleInvoiceDamageDto> findAllSaleiDamageBetween(Instant startDate, Instant endDate) {
        Optional<List<SaleInvoiceDamage>> optionalSaleInvoiceDamageList = saleInvoicedamRepository.
                findAllsaleidamBetween(startDate, endDate);

        return optionalSaleInvoiceDamageList.get().stream().map(SaleInvoiceDamageDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<SaleInvoiceDamageDto> findPageSaleiDamageBetween(Instant startDate, Instant endDate, int pagenum, int pagesize) {
        Optional<Page<SaleInvoiceDamage>> optionalSaleInvoiceDamagePage = saleInvoicedamRepository.
                findPagesaleidamBetween(startDate, endDate, PageRequest.of(pagenum, pagesize));

        return optionalSaleInvoiceDamagePage.get().map(SaleInvoiceDamageDto::fromEntity);
    }

    @Override
    public List<SaleInvoiceDamageDto> findAllSaleiDamageofClientBetween(Long clientId, Instant startDate, Instant endDate) {
        if(clientId == null){
            log.error("The id precised for the client is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        Optional<List<SaleInvoiceDamage>> optionalSaleInvoiceDamageList = saleInvoicedamRepository.
                findAllsaleidamofClientBetween(clientId, startDate, endDate);

        return optionalSaleInvoiceDamageList.get().stream().map(SaleInvoiceDamageDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<SaleInvoiceDamageDto> findPageSaleiDamageofClientBetween(Long clientId, Instant startDate, Instant endDate,
                                                                           int pagenum, int pagesize) {
        if(clientId == null){
            log.error("The id precised for the client is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        Optional<Page<SaleInvoiceDamage>> optionalSaleInvoiceDamagePage = saleInvoicedamRepository.
                findPagesaleidamofClientBetween(clientId, startDate, endDate, PageRequest.of(pagenum, pagesize));

        return optionalSaleInvoiceDamagePage.get().map(SaleInvoiceDamageDto::fromEntity);
    }

    @Override
    public List<SaleInvoiceDamageDto> findAllSaleiDamageofUserbmBetween(Long userbmId, Instant startDate, Instant endDate) {
        if(userbmId == null){
            log.error("The id precised for the userbm is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        Optional<List<SaleInvoiceDamage>> optionalSaleInvoiceDamageList = saleInvoicedamRepository.
                findAllsaleidamofUserbmBetween(userbmId, startDate, endDate);

        return optionalSaleInvoiceDamageList.get().stream().map(SaleInvoiceDamageDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<SaleInvoiceDamageDto> findPageSaleiDamageofUserbmBetween(Long userbmId, Instant startDate, Instant endDate,
                                                                           int pagenum, int pagesize) {
        if(userbmId == null){
            log.error("The id precised for the userbm is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        Optional<Page<SaleInvoiceDamage>> optionalSaleInvoiceDamagePage = saleInvoicedamRepository.
                findPagesaleidamofUserbmBetween(userbmId, startDate, endDate, PageRequest.of(pagenum, pagesize));

        return optionalSaleInvoiceDamagePage.get().map(SaleInvoiceDamageDto::fromEntity);
    }

    @Override
    public List<SaleInvoiceDamageDto> findAllSaleiDamageinPosBetween(Long posId, Instant startDate, Instant endDate) {
        if(posId == null){
            log.error("The id precised for the pos is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        Optional<List<SaleInvoiceDamage>> optionalSaleInvoiceDamageList = saleInvoicedamRepository.
                findAllsaleidaminPosBetween(posId, startDate, endDate);

        return optionalSaleInvoiceDamageList.get().stream().map(SaleInvoiceDamageDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<SaleInvoiceDamageDto> findPageSaleiDamageinPosBetween(Long posId, Instant startDate, Instant endDate, int pagenum, int pagesize) {
        if(posId == null){
            log.error("The id precised for the pos is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        Optional<Page<SaleInvoiceDamage>> optionalSaleInvoiceDamagePage = saleInvoicedamRepository.
                findPagesaleidaminPosBetween(posId, startDate, endDate, PageRequest.of(pagenum, pagesize));

        return optionalSaleInvoiceDamagePage.get().map(SaleInvoiceDamageDto::fromEntity);
    }

    @Override
    public List<SaleInvoiceDamageDto> findAllSaleiDamageofUserbminPosBetween(Long userbmId, Long posId, Instant startDate,
                                                                               Instant endDate) {
        if(userbmId == null){
            log.error("The id precised for the userbm is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        if(posId == null){
            log.error("The id precised for the pos is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        Optional<List<SaleInvoiceDamage>> optionalSaleInvoiceDamageList = saleInvoicedamRepository.
                findAllsaleidamofUserbminPosBetween(userbmId, posId, startDate, endDate);

        return optionalSaleInvoiceDamageList.get().stream().map(SaleInvoiceDamageDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<SaleInvoiceDamageDto> findPageSaleiDamageofUserbminPosBetween(Long userbmId, Long posId, Instant startDate,
                                                                               Instant endDate, int pagenum, int pagesize) {
        if(userbmId == null){
            log.error("The id precised for the userbm is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        if(posId == null){
            log.error("The id precised for the pos is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        Optional<Page<SaleInvoiceDamage>> optionalSaleInvoiceDamagePage = saleInvoicedamRepository.
                findPagesaleidamofUserbminPosBetween(userbmId, posId, startDate, endDate, PageRequest.of(pagenum, pagesize));

        return optionalSaleInvoiceDamagePage.get().map(SaleInvoiceDamageDto::fromEntity);
    }

    @Override
    public List<SaleInvoiceDamageDto> findAllSaleiDamageofClientinPosBetween(Long clientId, Long posId, Instant startDate, Instant endDate) {
        if(clientId == null){
            log.error("The id precised for the client is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        if(posId == null){
            log.error("The id precised for the pos is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        Optional<List<SaleInvoiceDamage>> optionalSaleInvoiceDamageList = saleInvoicedamRepository.
                findAllsaleidamofClientinPosBetween(clientId, posId, startDate, endDate);

        return optionalSaleInvoiceDamageList.get().stream().map(SaleInvoiceDamageDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<SaleInvoiceDamageDto> findPageSaleiDamageofClientinPosBetween(Long clientId, Long posId, Instant startDate,
                                                                                Instant endDate, int pagenum, int pagesize) {
        if(clientId == null){
            log.error("The id precised for the client is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        if(posId == null){
            log.error("The id precised for the pos is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        Optional<Page<SaleInvoiceDamage>> optionalSaleInvoiceDamagePage = saleInvoicedamRepository.
                findPagesaleidamofClientinPosBetween(clientId, posId, startDate, endDate, PageRequest.of(pagenum, pagesize));

        return optionalSaleInvoiceDamagePage.get().map(SaleInvoiceDamageDto::fromEntity);
    }

    @Override
    public List<SaleInvoiceDamageDto> findAllSaleiDamageofClientforUserbmBetween(Long clientId, Long userbmId, Instant startDate,
                                                                                   Instant endDate) {
        if(clientId == null){
            log.error("The id precised for the client is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        if(userbmId == null){
            log.error("The id precised for the userbm is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        Optional<List<SaleInvoiceDamage>> optionalSaleInvoiceDamageList = saleInvoicedamRepository.
                findAllsaleidamofClientforUserbmBetween(clientId, userbmId, startDate, endDate);

        return optionalSaleInvoiceDamageList.get().stream().map(SaleInvoiceDamageDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<SaleInvoiceDamageDto> findPageSaleiDamageofClientforUserbmBetween(Long clientId, Long userbmId,
                                                                                    Instant startDate, Instant endDate,
                                                                                    int pagenum, int pagesize) {
        if(clientId == null){
            log.error("The id precised for the client is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        if(userbmId == null){
            log.error("The id precised for the userbm is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        Optional<Page<SaleInvoiceDamage>> optionalSaleInvoiceDamagePage = saleInvoicedamRepository.
                findPagesaleidamofClientforUserbmBetween(clientId, userbmId, startDate, endDate,
                        PageRequest.of(pagenum, pagesize));

        return optionalSaleInvoiceDamagePage.get().map(SaleInvoiceDamageDto::fromEntity);
    }

    @Override
    public List<SaleInvoiceDamageDto> findAllSaleiDamageofClientforUserbminPosBetween(Long clientId, Long userbmId,
                                                                                        Long posId, Instant startDate,
                                                                                        Instant endDate) {
        if(clientId == null){
            log.error("The id precised for the client is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        if(userbmId == null){
            log.error("The id precised for the userbm is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        if(posId == null){
            log.error("The id precised for the pos is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        Optional<List<SaleInvoiceDamage>> optionalSaleInvoiceDamageList = saleInvoicedamRepository.
                findAllsaleidamofClientforUserbminPosBetween(clientId, userbmId, posId, startDate, endDate);

        return optionalSaleInvoiceDamageList.get().stream().map(SaleInvoiceDamageDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<SaleInvoiceDamageDto> findPageSaleiDamageofClientforUserbminPosBetween(Long clientId, Long userbmId,
                                                                                         Long posId, Instant startDate,
                                                                                         Instant endDate, int pagenum,
                                                                                         int pagesize) {
        if(clientId == null){
            log.error("The id precised for the client is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        if(userbmId == null){
            log.error("The id precised for the userbm is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        if(posId == null){
            log.error("The id precised for the pos is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        Optional<Page<SaleInvoiceDamage>> optionalSaleInvoiceDamagePage = saleInvoicedamRepository.
                findPagesaleidamofClientforUserbminPosBetween(clientId, userbmId, posId, startDate, endDate,
                        PageRequest.of(pagenum, pagesize));

        return optionalSaleInvoiceDamagePage.get().map(SaleInvoiceDamageDto::fromEntity);
    }






}
