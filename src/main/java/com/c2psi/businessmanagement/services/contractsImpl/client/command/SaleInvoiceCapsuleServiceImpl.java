package com.c2psi.businessmanagement.services.contractsImpl.client.command;

import com.c2psi.businessmanagement.dtos.client.command.SaleInvoiceCapsuleDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.Client;
import com.c2psi.businessmanagement.models.Pointofsale;
import com.c2psi.businessmanagement.models.SaleInvoiceCapsule;
import com.c2psi.businessmanagement.models.UserBM;
import com.c2psi.businessmanagement.repositories.client.client.ClientRepository;
import com.c2psi.businessmanagement.repositories.client.command.SaleInvoiceCapsuleRepository;
import com.c2psi.businessmanagement.repositories.pos.pos.PointofsaleRepository;
import com.c2psi.businessmanagement.repositories.pos.userbm.UserBMRepository;
import com.c2psi.businessmanagement.services.contracts.client.command.SaleInvoiceCapsuleService;
import com.c2psi.businessmanagement.validators.client.command.SaleInvoiceCapsuleValidator;
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

@Service(value="SaleInvoiceCapsuleServiceV1")
@Slf4j
@Transactional
public class SaleInvoiceCapsuleServiceImpl implements SaleInvoiceCapsuleService {
    private PointofsaleRepository pointofsaleRepository;
    private ClientRepository clientRepository;
    private UserBMRepository userBMRepository;
    private SaleInvoiceCapsuleRepository saleInvoiceCapsRepository;

    @Autowired
    public SaleInvoiceCapsuleServiceImpl(PointofsaleRepository pointofsaleRepository, ClientRepository clientRepository,
                                         UserBMRepository userBMRepository,
                                         SaleInvoiceCapsuleRepository saleInvoiceCapsRepository) {
        this.pointofsaleRepository = pointofsaleRepository;
        this.clientRepository = clientRepository;
        this.userBMRepository = userBMRepository;
        this.saleInvoiceCapsRepository = saleInvoiceCapsRepository;
    }


    @Override
    public SaleInvoiceCapsuleDto saveSaleInvoiceCapsule(SaleInvoiceCapsuleDto saleiCapsuleDto) {

        /*******************************************************************
         * Il faut valider l'argument pris en parametre grace au validateur
         */
        List<String> errors = SaleInvoiceCapsuleValidator.validate(saleiCapsuleDto);
        if(!errors.isEmpty()){
            log.error("Entity saleiCapsuleDto not valid {}", saleiCapsuleDto);
            throw new InvalidEntityException("Le saleiCapsuleDto passe en argument n'est pas valide:  ",
                    ErrorCode.SALEINVOICECAPSULE_NOT_VALID, errors);
        }

        /******************************************************************
         * Se rassurer que l'id du pointofsale n'est pas null et lorsque
         * c'est le cas il faut se rassurer que le pointofsale existe
         * vraiment en BD
         */
        if(saleiCapsuleDto.getSaleicapsPosId() == null){
            log.error("The id of the pointofsale associeted with the saleinvoiceCapsule can't be null");
            throw new InvalidEntityException("L'id du pointofsale associe au saleinvoiceCapsule ne peut etre null",
                    ErrorCode.SALEINVOICECAPSULE_NOT_VALID);
        }
        //L'id netant pas null on va se rassurer que ca existe vraiment en BD
        Optional<Pointofsale> optionalPointofsale = pointofsaleRepository.findPointofsaleById(
                saleiCapsuleDto.getSaleicapsPosId());
        if(!optionalPointofsale.isPresent()){
            log.error("The pointofsale precised in the request is not in the DB");
            throw new InvalidEntityException("Le pointofsale precise dans la requete n'est pas existant dans la BD ",
                    ErrorCode.SALEINVOICECAPSULE_NOT_VALID);
        }

        /******************************************************************
         * Se rassurer que l'id du userbm n'est pas null et lorsque
         * c'est le cas il faut se rassurer que le userbm existe
         * vraiment en BD
         */
        if(saleiCapsuleDto.getSaleicapsUserbmDto().getId() == null){
            log.error("The id of the userbm associeted with the saleinvoiceCapsule can't be null");
            throw new InvalidEntityException("L'id du userbm associe au saleinvoiceCapsule ne peut etre null",
                    ErrorCode.SALEINVOICECAPSULE_NOT_VALID);
        }
        Optional<UserBM> optionalUserBM = userBMRepository.findUserBMById(saleiCapsuleDto.getSaleicapsUserbmDto().getId());
        if(!optionalUserBM.isPresent()){
            log.error("The userbm precised in the request is not in the DB");
            throw new InvalidEntityException("Le userbm precise dans la requete n'est pas existant dans la BD ",
                    ErrorCode.SALEINVOICECAPSULE_NOT_VALID);
        }

        /******************************************************************
         * Se rassurer que l'id du client n'est pas null et lorsque
         * c'est le cas il faut se rassurer que le client existe
         * vraiment en BD
         */
        if(saleiCapsuleDto.getSaleicapsClientDto().getId() == null){
            log.error("The id of the client associeted with the saleinvoiceCapsule can't be null");
            throw new InvalidEntityException("L'id du client associe au saleinvoiceCapsule ne peut etre null",
                    ErrorCode.SALEINVOICECAPSULE_NOT_VALID);
        }
        Optional<Client> optionalClient = clientRepository.findClientById(saleiCapsuleDto.getSaleicapsClientDto().getId());
        if(!optionalClient.isPresent()){
            log.error("The client precised in the request is not in the DB");
            throw new InvalidEntityException("Le client precise dans la requete n'est pas existant dans la BD ",
                    ErrorCode.SALEINVOICECAPSULE_NOT_VALID);
        }

        /********************************************************************
         * Se rassurer que si le pointofsale du userbm existe alors il est
         * le meme que celui du saleinvoiceCapsule
         */
        if(saleiCapsuleDto.getSaleicapsUserbmDto().getBmPosId() != null){
            //Alors il faut que ce soit un Userbm du meme pointofsale de saleinvoiceCapsule
            if(!saleiCapsuleDto.getSaleicapsUserbmDto().getBmPosId().equals(optionalPointofsale.get().getId())){
                log.error("The userbm and the saleinvoiceCapsule must belong to the same pointofsale ");
                throw new InvalidEntityException("Le userbm et le saleinvoiceCapsule doivent appartenir au meme pointofsale ",
                        ErrorCode.SALEINVOICECAPSULE_NOT_VALID);
            }
        }

        /********************************************************************
         * Se rassurer que le pointofsale du client est le meme que celui
         * du saleinvoiceCapsule
         */
        if(!optionalPointofsale.get().getId().equals(saleiCapsuleDto.getSaleicapsClientDto().getClientPosId())){
            log.error("The client and the saleinvoiceCapsule must belong to the same pointofsale ");
            throw new InvalidEntityException("Le client et le saleinvoiceCapsule doivent appartenir au meme pointofsale ",
                    ErrorCode.SALEINVOICECAPSULE_NOT_VALID);
        }

        /*********************************************************************
         * Se rassurer que le saleinvoiceCapsule sera unique dans le pos indique
         */
        if(!isSaleInvoiceCapsuleUniqueinPos(saleiCapsuleDto.getSaleicapsCode(), saleiCapsuleDto.getSaleicapsPosId())){
            log.error("There is another SaleinvoiceCapsule in the DB with the same code in the pointofsale indicated ");
            throw new DuplicateEntityException("Une saleinvoiceCapsule existe deja dans la BD avec le meme code dans " +
                    "pour le pointofsale indique", ErrorCode.SALEINVOICECAPSULE_DUPLICATED);
        }

        /////////////////
        log.info("After all verification, the record {} can be saved on the DB", saleiCapsuleDto);

        return SaleInvoiceCapsuleDto.fromEntity(
                saleInvoiceCapsRepository.save(
                        SaleInvoiceCapsuleDto.toEntity(saleiCapsuleDto)
                )
        );
    }

    @Override
    public SaleInvoiceCapsuleDto updateSaleInvoiceCapsule(SaleInvoiceCapsuleDto saleiCapsuleDto) {

        /*******************************************************************
         * Il faut valider l'argument pris en parametre grace au validateur
         */
        List<String> errors = SaleInvoiceCapsuleValidator.validate(saleiCapsuleDto);
        if(!errors.isEmpty()){
            log.error("Entity saleiCapsuleDto not valid {}", saleiCapsuleDto);
            throw new InvalidEntityException("Le saleiCapsuleDto passe en argument n'est pas valide:  ",
                    ErrorCode.SALEINVOICECAPSULE_NOT_VALID, errors);
        }

        /*********************************************
         * On recherche le saleinvoiceCapsule a update
         */
        if(saleiCapsuleDto.getId() == null){
            log.error("It is not possible to update a saleinvoiceCapsule without get its id");
            throw new InvalidEntityException("l'Id du saleinvoiceCapsule a update est null ",
                    ErrorCode.SALEINVOICECAPSULE_NOT_VALID);
        }
        Optional<SaleInvoiceCapsule> optionalSaleInvoiceCapsule = saleInvoiceCapsRepository.
                findSaleInvoiceCapsuleById(saleiCapsuleDto.getId());
        if(!optionalSaleInvoiceCapsule.isPresent()){
            log.error("There is no SaleinvoiceCapsule in the DB with the precised id {}", saleiCapsuleDto.getId());
            throw new InvalidEntityException("Il n'existe pas de SaleInvoiceCapsule in the DB with the precised id ",
                    ErrorCode.SALEINVOICECAPSULE_NOT_VALID);
        }
        SaleInvoiceCapsule saleInvoiceCapsuleToUpdate = optionalSaleInvoiceCapsule.get();

        /********************************************************************
         * On se rassure que ce n'est pas le pointofsale quon veut modifier
         */
        if(saleiCapsuleDto.getSaleicapsPosId() != null) {
            if (!saleInvoiceCapsuleToUpdate.getSaleicapsPosId().equals(saleiCapsuleDto.getSaleicapsPosId())) {
                log.error("It is not possible to update the pointofsale of a saleinvoiceCapsule");
                throw new InvalidEntityException("Il n'est pas possible de update le pointofsale du saleinvoiceCapsule ",
                        ErrorCode.SALEINVOICECAPSULE_NOT_VALID);
            }
        }

        /********************************************************************
         * On se rassure que ce n'est pas le userbm quon veut modifier
         */
        if(saleiCapsuleDto.getSaleicapsUserbmDto().getId() != null){
            if(!saleInvoiceCapsuleToUpdate.getSaleicapsUserbm().getId().equals(saleiCapsuleDto.getSaleicapsUserbmDto().getId())){
                log.error("It is not possible to update the userbm of a saleinvoiceCapsule");
                throw new InvalidEntityException("Il n'est pas possible de update le userbm du saleinvoiceCapsule ",
                        ErrorCode.SALEINVOICECAPSULE_NOT_VALID);
            }
        }

        if(saleiCapsuleDto.getSaleicapsClientDto().getId() != null){
            if(!saleInvoiceCapsuleToUpdate.getSaleicapsClient().getId().equals(saleiCapsuleDto.getSaleicapsClientDto().getId())){
                /**************************************************
                 * Ceci signifie qu'on veut modifier le client donc on doit verifier si le nouveau existe
                 */
                Optional<Client> optionalClient = clientRepository.findClientById(saleiCapsuleDto.getSaleicapsClientDto().getId());
                if(!optionalClient.isPresent()){
                    log.error("The new client precised does not exist in the DB ");
                    throw new InvalidEntityException("Le nouveau client precise dans la requete de mise a jour n'existe " +
                            "pas en BD ", ErrorCode.SALEINVOICECAPSULE_NOT_VALID);
                }
                //Ici on est sur que le nouveau client existe donc on prepare la mise a jour
                saleInvoiceCapsuleToUpdate.setSaleicapsClient(optionalClient.get());
            }
        }

        /****************************************************************************************
         * On verifie si c'est le code de la facture quon veut modifier et si cest le cas on va
         * verifier si ca va etre unique
         */
        if(!saleInvoiceCapsuleToUpdate.getSaleicapsCode().equals(saleiCapsuleDto.getSaleicapsCode())){
            if(!isSaleInvoiceCapsuleUniqueinPos(saleiCapsuleDto.getSaleicapsCode(),
                    saleInvoiceCapsuleToUpdate.getSaleicapsPosId())){
                log.error("There is another SaleinvoiceCapsule in the DB with the same code in the pointofsale indicated ");
                throw new DuplicateEntityException("Une saleinvoiceCapsule existe deja dans la BD avec le meme code dans " +
                        "pour le pointofsale indique", ErrorCode.SALEINVOICECAPSULE_DUPLICATED);
            }
            //On est sur quil y aura pas de duplicata
            saleInvoiceCapsuleToUpdate.setSaleicapsCode(saleiCapsuleDto.getSaleicapsCode());
        }

        //Maintenant on peut faire toutes les autres update
        saleInvoiceCapsuleToUpdate.setSaleicapsTotalcolis(saleiCapsuleDto.getSaleicapsTotalcolis());
        saleInvoiceCapsuleToUpdate.setSaleicapsNumberchanged(saleiCapsuleDto.getSaleicapsNumberchanged());
        saleInvoiceCapsuleToUpdate.setSaleicapsNumbertochange(saleiCapsuleDto.getSaleicapsNumbertochange());
        saleInvoiceCapsuleToUpdate.setSaleicapsDeliveryDate(saleiCapsuleDto.getSaleicapsDeliveryDate());

        log.info("After all verification, the record {} can be done on the DB", saleiCapsuleDto);
        return SaleInvoiceCapsuleDto.fromEntity(saleInvoiceCapsRepository.save(saleInvoiceCapsuleToUpdate));
    }

    @Override
    public Boolean isSaleInvoiceCapsuleUniqueinPos(String saleiCapsuleCode, Long posId) {
        if(!StringUtils.hasLength(saleiCapsuleCode)){
            log.error("The precise code {} is empty in the method ", saleiCapsuleCode);
            throw new NullArgumentException("L'argument code precise est null ou vide ");
        }

        if(posId == null){
            log.error("The posId precised in the method is null");
            throw new NullArgumentException("L'argument posId precise est null");
        }

        Optional<SaleInvoiceCapsule> optionalSaleInvoiceCapsule = saleInvoiceCapsRepository.
                findSaleInvoiceCapsuleByCodeinPos(saleiCapsuleCode, posId);

        return optionalSaleInvoiceCapsule.isEmpty();
    }

    @Override
    public Boolean isSaleInvoiceCapsuleDeleteable(Long saleiCapsuleId) {
        return true;
    }

    @Override
    public Boolean deleteSaleInvoiceCapsuleById(Long saleiCapsuleId) {
        if(saleiCapsuleId == null){
            log.error("The saleiCapsuleId precised in the method is null");
            throw new NullArgumentException("L'argument saleiCapsuleId precise est null");
        }
        Optional<SaleInvoiceCapsule> optionalSaleInvoiceCapsule = saleInvoiceCapsRepository.findSaleInvoiceCapsuleById(saleiCapsuleId);
        if(!optionalSaleInvoiceCapsule.isPresent()){
            log.error("There is no saleinvoiceCapsule in the DB with that id {}", saleiCapsuleId);
            throw new EntityNotFoundException("Le saleinvoiceCapsule qu'on veut supprimer n'existe pas en BD",
                    ErrorCode.SALEINVOICECAPSULE_NOT_FOUND);
        }
        if(!isSaleInvoiceCapsuleDeleteable(saleiCapsuleId)){
            log.error("It is not possible to delete the saleinvoiceCapsule precise with the id {}", saleiCapsuleId);
            throw new EntityNotDeleteableException("Le saleinvoiceCapsule precise par l'id ne peut etre supprimer ",
                    ErrorCode.SALEINVOICECAPSULE_NOT_DELETEABLE);
        }
        saleInvoiceCapsRepository.delete(optionalSaleInvoiceCapsule.get());
        return true;
    }

    @Override
    public SaleInvoiceCapsuleDto findSaleInvoiceCapsuleById(Long saleiCapsuleId) {
        if(saleiCapsuleId == null){
            log.error("The saleiCapsuleid precised is null");
            throw new NullArgumentException("L'id passe a la methode est null");
        }
        Optional<SaleInvoiceCapsule> optionalSaleInvoiceCapsule = saleInvoiceCapsRepository.
                findSaleInvoiceCapsuleById(saleiCapsuleId);
        if(!optionalSaleInvoiceCapsule.isPresent()){
            log.error("There is no saleinvoiceCapsule in the DB with the precise id {}", saleiCapsuleId);
            throw new EntityNotFoundException("Aucun saleinvoiceCapsule n'existe avec le id precise ",
                    ErrorCode.SALEINVOICECAPSULE_NOT_FOUND);
        }
        return SaleInvoiceCapsuleDto.fromEntity(optionalSaleInvoiceCapsule.get());
    }

    @Override
    public SaleInvoiceCapsuleDto findSaleInvoiceCapsuleByCode(String saleiCapsuleCode, Long posId) {
        if(!StringUtils.hasLength(saleiCapsuleCode)){
            log.error("The precise code {} is empty in the method ", saleiCapsuleCode);
            throw new NullArgumentException("L'argument code precise est null ou vide ");
        }

        if(posId == null){
            log.error("The posId precised in the method is null");
            throw new NullArgumentException("L'argument posId precise est null");
        }

        Optional<SaleInvoiceCapsule> optionalSaleInvoiceCapsule = saleInvoiceCapsRepository.
                findSaleInvoiceCapsuleByCodeinPos(saleiCapsuleCode, posId);

        return SaleInvoiceCapsuleDto.fromEntity(optionalSaleInvoiceCapsule.get());
    }

    @Override
    public List<SaleInvoiceCapsuleDto> findAllSaleiCapsuleBetween(Instant startDate, Instant endDate) {
        Optional<List<SaleInvoiceCapsule>> optionalSaleInvoiceCapsuleList = saleInvoiceCapsRepository.
                findAllSaleicapsBetween(startDate, endDate);

        return optionalSaleInvoiceCapsuleList.get().stream().map(SaleInvoiceCapsuleDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<SaleInvoiceCapsuleDto> findPageSaleiCapsuleBetween(Instant startDate, Instant endDate, int pagenum, int pagesize) {
        Optional<Page<SaleInvoiceCapsule>> optionalSaleInvoiceCapsulePage = saleInvoiceCapsRepository.
                findPageSaleicapsBetween(startDate, endDate, PageRequest.of(pagenum, pagesize));

        return optionalSaleInvoiceCapsulePage.get().map(SaleInvoiceCapsuleDto::fromEntity);
    }

    @Override
    public List<SaleInvoiceCapsuleDto> findAllSaleiCapsuleofClientBetween(Long clientId, Instant startDate, Instant endDate) {
        if(clientId == null){
            log.error("The id precised for the client is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        Optional<List<SaleInvoiceCapsule>> optionalSaleInvoiceCapsuleList = saleInvoiceCapsRepository.
                findAllSaleicapsofClientBetween(clientId, startDate, endDate);

        return optionalSaleInvoiceCapsuleList.get().stream().map(SaleInvoiceCapsuleDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<SaleInvoiceCapsuleDto> findPageSaleiCapsuleofClientBetween(Long clientId, Instant startDate, Instant endDate,
                                                                     int pagenum, int pagesize) {
        if(clientId == null){
            log.error("The id precised for the client is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        Optional<Page<SaleInvoiceCapsule>> optionalSaleInvoiceCapsulePage = saleInvoiceCapsRepository.
                findPageSaleicapsofClientBetween(clientId, startDate, endDate, PageRequest.of(pagenum, pagesize));

        return optionalSaleInvoiceCapsulePage.get().map(SaleInvoiceCapsuleDto::fromEntity);
    }

    @Override
    public List<SaleInvoiceCapsuleDto> findAllSaleiCapsuleofUserbmBetween(Long userbmId, Instant startDate, Instant endDate) {
        if(userbmId == null){
            log.error("The id precised for the userbm is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        Optional<List<SaleInvoiceCapsule>> optionalSaleInvoiceCapsuleList = saleInvoiceCapsRepository.
                findAllSaleicapsofUserbmBetween(userbmId, startDate, endDate);

        return optionalSaleInvoiceCapsuleList.get().stream().map(SaleInvoiceCapsuleDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<SaleInvoiceCapsuleDto> findPageSaleiCapsuleofUserbmBetween(Long userbmId, Instant startDate, Instant endDate,
                                                                     int pagenum, int pagesize) {
        if(userbmId == null){
            log.error("The id precised for the userbm is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        Optional<Page<SaleInvoiceCapsule>> optionalSaleInvoiceCapsulePage = saleInvoiceCapsRepository.
                findPageSaleicapsofUserbmBetween(userbmId, startDate, endDate, PageRequest.of(pagenum, pagesize));

        return optionalSaleInvoiceCapsulePage.get().map(SaleInvoiceCapsuleDto::fromEntity);
    }

    @Override
    public List<SaleInvoiceCapsuleDto> findAllSaleiCapsuleinPosBetween(Long posId, Instant startDate, Instant endDate) {
        if(posId == null){
            log.error("The id precised for the pos is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        Optional<List<SaleInvoiceCapsule>> optionalSaleInvoiceCapsuleList = saleInvoiceCapsRepository.
                findAllSaleicapsinPosBetween(posId, startDate, endDate);

        return optionalSaleInvoiceCapsuleList.get().stream().map(SaleInvoiceCapsuleDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<SaleInvoiceCapsuleDto> findAllSaleiCapsuleinPosBetween(Long posId, Instant startDate, Instant endDate, int pagenum, int pagesize) {
        if(posId == null){
            log.error("The id precised for the pos is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        Optional<Page<SaleInvoiceCapsule>> optionalSaleInvoiceCapsulePage = saleInvoiceCapsRepository.
                findPageSaleicapsinPosBetween(posId, startDate, endDate, PageRequest.of(pagenum, pagesize));

        return optionalSaleInvoiceCapsulePage.get().map(SaleInvoiceCapsuleDto::fromEntity);
    }

    @Override
    public List<SaleInvoiceCapsuleDto> findAllSaleiCapsuleofUserbminPosBetween(Long userbmId, Long posId, Instant startDate,
                                                                         Instant endDate) {
        if(userbmId == null){
            log.error("The id precised for the userbm is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        if(posId == null){
            log.error("The id precised for the pos is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        Optional<List<SaleInvoiceCapsule>> optionalSaleInvoiceCapsuleList = saleInvoiceCapsRepository.
                findAllSaleicapsofUserbminPosBetween(userbmId, posId, startDate, endDate);

        return optionalSaleInvoiceCapsuleList.get().stream().map(SaleInvoiceCapsuleDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<SaleInvoiceCapsuleDto> findAllSaleiCapsuleofUserbminPosBetween(Long userbmId, Long posId, Instant startDate,
                                                                         Instant endDate, int pagenum, int pagesize) {
        if(userbmId == null){
            log.error("The id precised for the userbm is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        if(posId == null){
            log.error("The id precised for the pos is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        Optional<Page<SaleInvoiceCapsule>> optionalSaleInvoiceCapsulePage = saleInvoiceCapsRepository.
                findPageSaleicapsofUserbminPosBetween(userbmId, posId, startDate, endDate, PageRequest.of(pagenum, pagesize));

        return optionalSaleInvoiceCapsulePage.get().map(SaleInvoiceCapsuleDto::fromEntity);
    }

    @Override
    public List<SaleInvoiceCapsuleDto> findAllSaleiCapsuleofClientinPosBetween(Long clientId, Long posId, Instant startDate, Instant endDate) {
        if(clientId == null){
            log.error("The id precised for the client is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        if(posId == null){
            log.error("The id precised for the pos is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        Optional<List<SaleInvoiceCapsule>> optionalSaleInvoiceCapsuleList = saleInvoiceCapsRepository.
                findAllSaleicapsofClientinPosBetween(clientId, posId, startDate, endDate);

        return optionalSaleInvoiceCapsuleList.get().stream().map(SaleInvoiceCapsuleDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<SaleInvoiceCapsuleDto> findPageSaleiCapsuleofClientinPosBetween(Long clientId, Long posId, Instant startDate,
                                                                          Instant endDate, int pagenum, int pagesize) {
        if(clientId == null){
            log.error("The id precised for the client is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        if(posId == null){
            log.error("The id precised for the pos is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        Optional<Page<SaleInvoiceCapsule>> optionalSaleInvoiceCapsulePage = saleInvoiceCapsRepository.
                findPageSaleicapsofClientinPosBetween(clientId, posId, startDate, endDate, PageRequest.of(pagenum, pagesize));

        return optionalSaleInvoiceCapsulePage.get().map(SaleInvoiceCapsuleDto::fromEntity);
    }

    @Override
    public List<SaleInvoiceCapsuleDto> findAllSaleiCapsuleofClientforUserbmBetween(Long clientId, Long userbmId, Instant startDate,
                                                                             Instant endDate) {
        if(clientId == null){
            log.error("The id precised for the client is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        if(userbmId == null){
            log.error("The id precised for the userbm is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        Optional<List<SaleInvoiceCapsule>> optionalSaleInvoiceCapsuleList = saleInvoiceCapsRepository.
                findAllSaleicapsofClientforUserbmBetween(clientId, userbmId, startDate, endDate);

        return optionalSaleInvoiceCapsuleList.get().stream().map(SaleInvoiceCapsuleDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<SaleInvoiceCapsuleDto> findPageSaleiCapsuleofClientforUserbmBetween(Long clientId, Long userbmId,
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
        Optional<Page<SaleInvoiceCapsule>> optionalSaleInvoiceCapsulePage = saleInvoiceCapsRepository.
                findPageSaleicapsofClientforUserbmBetween(clientId, userbmId, startDate, endDate,
                        PageRequest.of(pagenum, pagesize));

        return optionalSaleInvoiceCapsulePage.get().map(SaleInvoiceCapsuleDto::fromEntity);
    }

    @Override
    public List<SaleInvoiceCapsuleDto> findAllSaleiCapsuleofClientforUserbminPosBetween(Long clientId, Long userbmId,
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
        Optional<List<SaleInvoiceCapsule>> optionalSaleInvoiceCapsuleList = saleInvoiceCapsRepository.
                findAllSaleicapsofClientforUserbminPosBetween(clientId, userbmId, posId, startDate, endDate);

        return optionalSaleInvoiceCapsuleList.get().stream().map(SaleInvoiceCapsuleDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<SaleInvoiceCapsuleDto> findPageSaleiCapsuleofClientforUserbminPosBetween(Long clientId, Long userbmId,
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
        Optional<Page<SaleInvoiceCapsule>> optionalSaleInvoiceCapsulePage = saleInvoiceCapsRepository.
                findPageSaleicapsofClientforUserbminPosBetween(clientId, userbmId, posId, startDate, endDate,
                        PageRequest.of(pagenum, pagesize));

        return optionalSaleInvoiceCapsulePage.get().map(SaleInvoiceCapsuleDto::fromEntity);
    }
    
    
    
    
    
    
}
