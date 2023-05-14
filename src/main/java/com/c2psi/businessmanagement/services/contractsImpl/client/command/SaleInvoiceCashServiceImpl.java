package com.c2psi.businessmanagement.services.contractsImpl.client.command;

import com.c2psi.businessmanagement.dtos.client.command.SaleInvoiceCashDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.Client;
import com.c2psi.businessmanagement.models.Pointofsale;
import com.c2psi.businessmanagement.models.SaleInvoiceCash;
import com.c2psi.businessmanagement.models.UserBM;
import com.c2psi.businessmanagement.repositories.client.client.ClientRepository;
import com.c2psi.businessmanagement.repositories.client.command.SaleInvoiceCashRepository;
import com.c2psi.businessmanagement.repositories.pos.pos.PointofsaleRepository;
import com.c2psi.businessmanagement.repositories.pos.userbm.UserBMRepository;
import com.c2psi.businessmanagement.services.contracts.client.command.SaleInvoiceCashService;
import com.c2psi.businessmanagement.validators.client.command.SaleInvoiceCashValidator;
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

@Service(value="SaleInvoiceCashServiceV1")
@Slf4j
@Transactional
public class SaleInvoiceCashServiceImpl implements SaleInvoiceCashService {

    private PointofsaleRepository pointofsaleRepository;
    private ClientRepository clientRepository;
    private UserBMRepository userBMRepository;
    private SaleInvoiceCashRepository saleInvoiceCashRepository;

    @Autowired
    public SaleInvoiceCashServiceImpl(PointofsaleRepository pointofsaleRepository,
                                      ClientRepository clientRepository,
                                      UserBMRepository userBMRepository,
                                      SaleInvoiceCashRepository saleInvoiceCashRepository) {
        this.pointofsaleRepository = pointofsaleRepository;
        this.clientRepository = clientRepository;
        this.userBMRepository = userBMRepository;
        this.saleInvoiceCashRepository = saleInvoiceCashRepository;
    }

    @Override
    public SaleInvoiceCashDto saveSaleInvoiceCash(SaleInvoiceCashDto saleicashDto) {

        /*******************************************************************
         * Il faut valider l'argument pris en parametre grace au validateur
         */
        List<String> errors = SaleInvoiceCashValidator.validate(saleicashDto);
        if(!errors.isEmpty()){
            log.error("Entity saleicashDto not valid {}", saleicashDto);
            throw new InvalidEntityException("Le saleicashDto passe en argument n'est pas valide:  ",
                    ErrorCode.SALEINVOICECASH_NOT_VALID, errors);
        }

        /******************************************************************
         * Se rassurer que l'id du pointofsale n'est pas null et lorsque
         * c'est le cas il faut se rassurer que le pointofsale existe
         * vraiment en BD
         */
        if(saleicashDto.getSaleicashPosDto().getId() == null){
            log.error("The id of the pointofsale associeted with the saleinvoicecash can't be null");
            throw new InvalidEntityException("L'id du pointofsale associe au saleinvoicecash ne peut etre null",
                    ErrorCode.SALEINVOICECASH_NOT_VALID);
        }
        //L'id netant pas null on va se rassurer que ca existe vraiment en BD
        Optional<Pointofsale> optionalPointofsale = pointofsaleRepository.findPointofsaleById(
                saleicashDto.getSaleicashPosDto().getId());
        if(!optionalPointofsale.isPresent()){
            log.error("The pointofsale precised in the request is not in the DB");
            throw new InvalidEntityException("Le pointofsale precise dans la requete n'est pas existant dans la BD ",
                    ErrorCode.SALEINVOICECASH_NOT_VALID);
        }

        /******************************************************************
         * Se rassurer que l'id du userbm n'est pas null et lorsque
         * c'est le cas il faut se rassurer que le userbm existe
         * vraiment en BD
         */
        if(saleicashDto.getSaleicashUserbmDto().getId() == null){
            log.error("The id of the userbm associeted with the saleinvoicecash can't be null");
            throw new InvalidEntityException("L'id du userbm associe au saleinvoicecash ne peut etre null",
                    ErrorCode.SALEINVOICECASH_NOT_VALID);
        }
        Optional<UserBM> optionalUserBM = userBMRepository.findUserBMById(saleicashDto.getSaleicashUserbmDto().getId());
        if(!optionalUserBM.isPresent()){
            log.error("The userbm precised in the request is not in the DB");
            throw new InvalidEntityException("Le userbm precise dans la requete n'est pas existant dans la BD ",
                    ErrorCode.SALEINVOICECASH_NOT_VALID);
        }

        /******************************************************************
         * Se rassurer que l'id du client n'est pas null et lorsque
         * c'est le cas il faut se rassurer que le client existe
         * vraiment en BD
         */
        if(saleicashDto.getSaleicashClientDto().getId() == null){
            log.error("The id of the client associeted with the saleinvoicecash can't be null");
            throw new InvalidEntityException("L'id du client associe au saleinvoicecash ne peut etre null",
                    ErrorCode.SALEINVOICECASH_NOT_VALID);
        }
        Optional<Client> optionalClient = clientRepository.findClientById(saleicashDto.getSaleicashClientDto().getId());
        if(!optionalClient.isPresent()){
            log.error("The client precised in the request is not in the DB");
            throw new InvalidEntityException("Le client precise dans la requete n'est pas existant dans la BD ",
                    ErrorCode.SALEINVOICECASH_NOT_VALID);
        }

        /********************************************************************
         * Se rassurer que si le pointofsale du userbm existe alors il est
         * le meme que celui du saleinvoicecash
         */
        if(saleicashDto.getSaleicashUserbmDto().getBmPosDto() != null){
            //Alors il faut que ce soit un Userbm du meme pointofsale de saleinvoicecash
            if(!saleicashDto.getSaleicashUserbmDto().getBmPosDto().getId().equals(optionalPointofsale.get().getId())){
                log.error("The userbm and the saleinvoicecash must belong to the same pointofsale ");
                throw new InvalidEntityException("Le userbm et le saleinvoicecash doivent appartenir au meme pointofsale ",
                        ErrorCode.SALEINVOICECASH_NOT_VALID);
            }
        }

        /********************************************************************
         * Se rassurer que le pointofsale du client est le meme que celui
         * du saleinvoicecash
         */
        if(!optionalPointofsale.get().getId().equals(saleicashDto.getSaleicashClientDto().getClientPosDto().getId())){
            log.error("The client and the saleinvoicecash must belong to the same pointofsale ");
            throw new InvalidEntityException("Le client et le saleinvoicecash doivent appartenir au meme pointofsale ",
                    ErrorCode.SALEINVOICECASH_NOT_VALID);
        }

        /*********************************************************************
         * Se rassurer que le saleinvoicecash sera unique dans le pos indique
         */
        if(!isSaleInvoiceCashUniqueinPos(saleicashDto.getSaleicashCode(), saleicashDto.getSaleicashPosDto().getId())){
            log.error("There is another Saleinvoicecash in the DB with the same code in the pointofsale indicated ");
            throw new DuplicateEntityException("Une saleinvoicecash existe deja dans la BD avec le meme code dans " +
                    "pour le pointofsale indique", ErrorCode.SALEINVOICECASH_DUPLICATED);
        }

        /////////////////
        log.info("After all verification, the record {} can be saved on the DB", saleicashDto);

        return SaleInvoiceCashDto.fromEntity(
                saleInvoiceCashRepository.save(
                        SaleInvoiceCashDto.toEntity(saleicashDto)
                )
        );
    }

    @Override
    public SaleInvoiceCashDto updateSaleInvoiceCash(SaleInvoiceCashDto saleicashDto) {

        /*******************************************************************
         * Il faut valider l'argument pris en parametre grace au validateur
         */
        List<String> errors = SaleInvoiceCashValidator.validate(saleicashDto);
        if(!errors.isEmpty()){
            log.error("Entity saleicashDto not valid {}", saleicashDto);
            throw new InvalidEntityException("Le saleicashDto passe en argument n'est pas valide:  ",
                    ErrorCode.SALEINVOICECASH_NOT_VALID, errors);
        }

        /*********************************************
         * On recherche le saleinvoicecash a update
         */
        if(saleicashDto.getId() == null){
            log.error("It is not possible to update a saleinvoicecash without get its id");
            throw new InvalidEntityException("l'Id du saleinvoicecash a update est null ",
                    ErrorCode.SALEINVOICECASH_NOT_VALID);
        }
        Optional<SaleInvoiceCash> optionalSaleInvoiceCash = saleInvoiceCashRepository.
                findSaleInvoiceCashById(saleicashDto.getId());
        if(!optionalSaleInvoiceCash.isPresent()){
            log.error("There is no Saleinvoicecash in the DB with the precised id {}", saleicashDto.getId());
            throw new InvalidEntityException("Il n'existe pas de SaleInvoiceCash in the DB with the precised id ",
                    ErrorCode.SALEINVOICECASH_NOT_VALID);
        }
        SaleInvoiceCash saleInvoiceCashToUpdate = optionalSaleInvoiceCash.get();

        /********************************************************************
         * On se rassure que ce n'est pas le pointofsale quon veut modifier
         */
        if(saleicashDto.getSaleicashPosDto().getId() != null) {
            if (!saleInvoiceCashToUpdate.getSaleicashPos().getId().equals(saleicashDto.getSaleicashPosDto().getId())) {
                log.error("It is not possible to update the pointofsale of a saleinvoicecash");
                throw new InvalidEntityException("Il n'est pas possible de update le pointofsale du saleinvoicecash ",
                        ErrorCode.SALEINVOICECASH_NOT_VALID);
            }
        }

        /********************************************************************
         * On se rassure que ce n'est pas le userbm quon veut modifier
         */
        if(saleicashDto.getSaleicashUserbmDto().getId() != null){
            if(!saleInvoiceCashToUpdate.getSaleicashUserbm().getId().equals(saleicashDto.getSaleicashUserbmDto().getId())){
                log.error("It is not possible to update the userbm of a saleinvoicecash");
                throw new InvalidEntityException("Il n'est pas possible de update le userbm du saleinvoicecash ",
                        ErrorCode.SALEINVOICECASH_NOT_VALID);
            }
        }

        if(saleicashDto.getSaleicashClientDto().getId() != null){
            if(!saleInvoiceCashToUpdate.getSaleicashClient().getId().equals(saleicashDto.getSaleicashClientDto().getId())){
                /**************************************************
                 * Ceci signifie qu'on veut modifier le client donc on doit verifier si le nouveau existe
                 */
                Optional<Client> optionalClient = clientRepository.findClientById(saleicashDto.getSaleicashClientDto().getId());
                if(!optionalClient.isPresent()){
                    log.error("The new client precised does not exist in the DB ");
                    throw new InvalidEntityException("Le nouveau client precise dans la requete de mise a jour n'existe " +
                            "pas en BD ", ErrorCode.SALEINVOICECASH_NOT_VALID);
                }
                //Ici on est sur que le nouveau client existe donc on prepare la mise a jour
                saleInvoiceCashToUpdate.setSaleicashClient(optionalClient.get());
            }
        }

        /****************************************************************************************
         * On verifie si c'est le code de la facture quon veut modifier et si cest le cas on va
         * verifier si ca va etre unique
         */
        if(!saleInvoiceCashToUpdate.getSaleicashCode().equals(saleicashDto.getSaleicashCode())){
            if(!isSaleInvoiceCashUniqueinPos(saleicashDto.getSaleicashCode(),
                    saleInvoiceCashToUpdate.getSaleicashPos().getId())){
                log.error("There is another Saleinvoicecash in the DB with the same code in the pointofsale indicated ");
                throw new DuplicateEntityException("Une saleinvoicecash existe deja dans la BD avec le meme code dans " +
                        "pour le pointofsale indique", ErrorCode.SALEINVOICECASH_DUPLICATED);
            }
            //On est sur quil y aura pas de duplicata
            saleInvoiceCashToUpdate.setSaleicashCode(saleicashDto.getSaleicashCode());
        }

        //Maintenant on peut faire toutes les autres update
        saleInvoiceCashToUpdate.setSaleicashAmountexpected(saleicashDto.getSaleicashAmountexpected());
        saleInvoiceCashToUpdate.setSaleicashAmountpaid(saleicashDto.getSaleicashAmountpaid());
        saleInvoiceCashToUpdate.setSaleicashTotalcolis(saleicashDto.getSaleicashTotalcolis());
        saleInvoiceCashToUpdate.setSaleicashAmountreimbourse(saleicashDto.getSaleicashAmountreimbourse());
        saleInvoiceCashToUpdate.setSaleicashDeliveryDate(saleicashDto.getSaleicashDeliveryDate());

        log.info("After all verification, the record {} can be done on the DB", saleicashDto);
        return SaleInvoiceCashDto.fromEntity(saleInvoiceCashRepository.save(saleInvoiceCashToUpdate));
    }

    @Override
    public Boolean isSaleInvoiceCashUniqueinPos(String saleicashCode, Long posId) {
        if(!StringUtils.hasLength(saleicashCode)){
            log.error("The precise code {} is empty in the method ", saleicashCode);
            throw new NullArgumentException("L'argument code precise est null ou vide ");
        }

        if(posId == null){
            log.error("The posId precised in the method is null");
            throw new NullArgumentException("L'argument posId precise est null");
        }

        Optional<SaleInvoiceCash> optionalSaleInvoiceCash = saleInvoiceCashRepository.
                findSaleInvoiceCashByCodeinPos(saleicashCode, posId);

        return optionalSaleInvoiceCash.isEmpty();
    }

    @Override
    public Boolean isSaleInvoiceCashDeleteable(Long saleicashId) {
        return true;
    }

    @Override
    public Boolean deleteSaleInvoiceCashById(Long saleicashId) {
        if(saleicashId == null){
            log.error("The saleicashId precised in the method is null");
            throw new NullArgumentException("L'argument saleicashId precise est null");
        }
        Optional<SaleInvoiceCash> optionalSaleInvoiceCash = saleInvoiceCashRepository.findSaleInvoiceCashById(saleicashId);
        if(!optionalSaleInvoiceCash.isPresent()){
            log.error("There is no saleinvoicecash in the DB with that id {}", saleicashId);
            throw new EntityNotFoundException("Le saleinvoicecash qu'on veut supprimer n'existe pas en BD",
                    ErrorCode.SALEINVOICECASH_NOT_FOUND);
        }
        if(!isSaleInvoiceCashDeleteable(saleicashId)){
            log.error("It is not possible to delete the saleinvoicecash precise with the id {}", saleicashId);
            throw new EntityNotDeleteableException("Le saleinvoicecash precise par l'id ne peut etre supprimer ",
                    ErrorCode.SALEINVOICECASH_NOT_DELETEABLE);
        }
        saleInvoiceCashRepository.delete(optionalSaleInvoiceCash.get());
        return true;
    }

    @Override
    public SaleInvoiceCashDto findSaleInvoiceCashById(Long saleicashId) {
        if(saleicashId == null){
            log.error("The saleicashid precised is null");
            throw new NullArgumentException("L'id passe a la methode est null");
        }
        Optional<SaleInvoiceCash> optionalSaleInvoiceCash = saleInvoiceCashRepository.
                findSaleInvoiceCashById(saleicashId);
        if(!optionalSaleInvoiceCash.isPresent()){
            log.error("There is no saleinvoicecash in the DB with the precise id {}", saleicashId);
            throw new EntityNotFoundException("Aucun saleinvoicecash n'existe avec le id precise ",
                    ErrorCode.SALEINVOICECASH_NOT_FOUND);
        }
        return SaleInvoiceCashDto.fromEntity(optionalSaleInvoiceCash.get());
    }

    @Override
    public SaleInvoiceCashDto findSaleInvoiceCashByCode(String saleicashCode, Long posId) {
        if(!StringUtils.hasLength(saleicashCode)){
            log.error("The precise code {} is empty in the method ", saleicashCode);
            throw new NullArgumentException("L'argument code precise est null ou vide ");
        }

        if(posId == null){
            log.error("The posId precised in the method is null");
            throw new NullArgumentException("L'argument posId precise est null");
        }

        Optional<SaleInvoiceCash> optionalSaleInvoiceCash = saleInvoiceCashRepository.
                findSaleInvoiceCashByCodeinPos(saleicashCode, posId);

        return SaleInvoiceCashDto.fromEntity(optionalSaleInvoiceCash.get());
    }

    @Override
    public List<SaleInvoiceCashDto> findAllSaleicashBetween(Instant startDate, Instant endDate) {
        Optional<List<SaleInvoiceCash>> optionalSaleInvoiceCashList = saleInvoiceCashRepository.
                findAllSaleicashBetween(startDate, endDate);

        return optionalSaleInvoiceCashList.get().stream().map(SaleInvoiceCashDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<SaleInvoiceCashDto> findPageSaleicashBetween(Instant startDate, Instant endDate, int pagenum, int pagesize) {
        Optional<Page<SaleInvoiceCash>> optionalSaleInvoiceCashPage = saleInvoiceCashRepository.
                findPageSaleicashBetween(startDate, endDate, PageRequest.of(pagenum, pagesize));

        return optionalSaleInvoiceCashPage.get().map(SaleInvoiceCashDto::fromEntity);
    }

    @Override
    public List<SaleInvoiceCashDto> findAllSaleicashofClientBetween(Long clientId, Instant startDate, Instant endDate) {
        if(clientId == null){
            log.error("The id precised for the client is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        Optional<List<SaleInvoiceCash>> optionalSaleInvoiceCashList = saleInvoiceCashRepository.
                findAllSaleicashofClientBetween(clientId, startDate, endDate);

        return optionalSaleInvoiceCashList.get().stream().map(SaleInvoiceCashDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<SaleInvoiceCashDto> findPageSaleicashofClientBetween(Long clientId, Instant startDate, Instant endDate,
                                                                     int pagenum, int pagesize) {
        if(clientId == null){
            log.error("The id precised for the client is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        Optional<Page<SaleInvoiceCash>> optionalSaleInvoiceCashPage = saleInvoiceCashRepository.
                findPageSaleicashofClientBetween(clientId, startDate, endDate, PageRequest.of(pagenum, pagesize));

        return optionalSaleInvoiceCashPage.get().map(SaleInvoiceCashDto::fromEntity);
    }

    @Override
    public List<SaleInvoiceCashDto> findAllSaleicashofUserbmBetween(Long userbmId, Instant startDate, Instant endDate) {
        if(userbmId == null){
            log.error("The id precised for the userbm is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        Optional<List<SaleInvoiceCash>> optionalSaleInvoiceCashList = saleInvoiceCashRepository.
                findAllSaleicashofUserbmBetween(userbmId, startDate, endDate);

        return optionalSaleInvoiceCashList.get().stream().map(SaleInvoiceCashDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<SaleInvoiceCashDto> findPageSaleicashofUserbmBetween(Long userbmId, Instant startDate, Instant endDate,
                                                                     int pagenum, int pagesize) {
        if(userbmId == null){
            log.error("The id precised for the userbm is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        Optional<Page<SaleInvoiceCash>> optionalSaleInvoiceCashPage = saleInvoiceCashRepository.
                findPageSaleicashofUserbmBetween(userbmId, startDate, endDate, PageRequest.of(pagenum, pagesize));

        return optionalSaleInvoiceCashPage.get().map(SaleInvoiceCashDto::fromEntity);
    }

    @Override
    public List<SaleInvoiceCashDto> findAllSaleicashinPosBetween(Long posId, Instant startDate, Instant endDate) {
        if(posId == null){
            log.error("The id precised for the pos is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        Optional<List<SaleInvoiceCash>> optionalSaleInvoiceCashList = saleInvoiceCashRepository.
                findAllSaleicashinPosBetween(posId, startDate, endDate);

        return optionalSaleInvoiceCashList.get().stream().map(SaleInvoiceCashDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<SaleInvoiceCashDto> findPageSaleicashinPosBetween(Long posId, Instant startDate, Instant endDate, int pagenum, int pagesize) {
        if(posId == null){
            log.error("The id precised for the pos is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        Optional<Page<SaleInvoiceCash>> optionalSaleInvoiceCashPage = saleInvoiceCashRepository.
                findPageSaleicashinPosBetween(posId, startDate, endDate, PageRequest.of(pagenum, pagesize));

        return optionalSaleInvoiceCashPage.get().map(SaleInvoiceCashDto::fromEntity);
    }

    @Override
    public List<SaleInvoiceCashDto> findAllSaleicashofUserbminPosBetween(Long userbmId, Long posId, Instant startDate,
                                                                         Instant endDate) {
        if(userbmId == null){
            log.error("The id precised for the userbm is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        if(posId == null){
            log.error("The id precised for the pos is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        Optional<List<SaleInvoiceCash>> optionalSaleInvoiceCashList = saleInvoiceCashRepository.
                findAllSaleicashofUserbminPosBetween(userbmId, posId, startDate, endDate);

        return optionalSaleInvoiceCashList.get().stream().map(SaleInvoiceCashDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<SaleInvoiceCashDto> findPageSaleicashofUserbminPosBetween(Long userbmId, Long posId, Instant startDate,
                                                                         Instant endDate, int pagenum, int pagesize) {
        if(userbmId == null){
            log.error("The id precised for the userbm is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        if(posId == null){
            log.error("The id precised for the pos is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        Optional<Page<SaleInvoiceCash>> optionalSaleInvoiceCashPage = saleInvoiceCashRepository.
                findPageSaleicashofUserbminPosBetween(userbmId, posId, startDate, endDate, PageRequest.of(pagenum, pagesize));

        return optionalSaleInvoiceCashPage.get().map(SaleInvoiceCashDto::fromEntity);
    }

    @Override
    public List<SaleInvoiceCashDto> findAllSaleicashofClientinPosBetween(Long clientId, Long posId, Instant startDate, Instant endDate) {
        if(clientId == null){
            log.error("The id precised for the client is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        if(posId == null){
            log.error("The id precised for the pos is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        Optional<List<SaleInvoiceCash>> optionalSaleInvoiceCashList = saleInvoiceCashRepository.
                findAllSaleicashofClientinPosBetween(clientId, posId, startDate, endDate);

        return optionalSaleInvoiceCashList.get().stream().map(SaleInvoiceCashDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<SaleInvoiceCashDto> findPageSaleicashofClientinPosBetween(Long clientId, Long posId, Instant startDate,
                                                                          Instant endDate, int pagenum, int pagesize) {
        if(clientId == null){
            log.error("The id precised for the client is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        if(posId == null){
            log.error("The id precised for the pos is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        Optional<Page<SaleInvoiceCash>> optionalSaleInvoiceCashPage = saleInvoiceCashRepository.
                findPageSaleicashofClientinPosBetween(clientId, posId, startDate, endDate, PageRequest.of(pagenum, pagesize));

        return optionalSaleInvoiceCashPage.get().map(SaleInvoiceCashDto::fromEntity);
    }

    @Override
    public List<SaleInvoiceCashDto> findAllSaleicashofClientforUserbmBetween(Long clientId, Long userbmId, Instant startDate,
                                                                             Instant endDate) {
        if(clientId == null){
            log.error("The id precised for the client is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        if(userbmId == null){
            log.error("The id precised for the userbm is null");
            throw new NullArgumentException("L'argument precise dans la requete est null");
        }
        Optional<List<SaleInvoiceCash>> optionalSaleInvoiceCashList = saleInvoiceCashRepository.
                findAllSaleicashofClientforUserbmBetween(clientId, userbmId, startDate, endDate);

        return optionalSaleInvoiceCashList.get().stream().map(SaleInvoiceCashDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<SaleInvoiceCashDto> findPageSaleicashofClientforUserbmBetween(Long clientId, Long userbmId,
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
        Optional<Page<SaleInvoiceCash>> optionalSaleInvoiceCashPage = saleInvoiceCashRepository.
                findPageSaleicashofClientforUserbmBetween(clientId, userbmId, startDate, endDate,
                        PageRequest.of(pagenum, pagesize));

        return optionalSaleInvoiceCashPage.get().map(SaleInvoiceCashDto::fromEntity);
    }

    @Override
    public List<SaleInvoiceCashDto> findAllSaleicashofClientforUserbminPosBetween(Long clientId, Long userbmId,
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
        Optional<List<SaleInvoiceCash>> optionalSaleInvoiceCashList = saleInvoiceCashRepository.
                findAllSaleicashofClientforUserbminPosBetween(clientId, userbmId, posId, startDate, endDate);

        return optionalSaleInvoiceCashList.get().stream().map(SaleInvoiceCashDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<SaleInvoiceCashDto> findPageSaleicashofClientforUserbminPosBetween(Long clientId, Long userbmId,
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
        Optional<Page<SaleInvoiceCash>> optionalSaleInvoiceCashPage = saleInvoiceCashRepository.
                findPageSaleicashofClientforUserbminPosBetween(clientId, userbmId, posId, startDate, endDate,
                        PageRequest.of(pagenum, pagesize));

        return optionalSaleInvoiceCashPage.get().map(SaleInvoiceCashDto::fromEntity);
    }




}
