package com.c2psi.businessmanagement.services.contractsImpl.client.delivery;

import com.c2psi.businessmanagement.Enumerations.DeliveryState;
import com.c2psi.businessmanagement.dtos.client.delivery.DeliveryDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.Delivery;
import com.c2psi.businessmanagement.models.Pointofsale;
import com.c2psi.businessmanagement.models.UserBM;
import com.c2psi.businessmanagement.repositories.client.delivery.DeliveryRepository;
import com.c2psi.businessmanagement.repositories.pos.pos.PointofsaleRepository;
import com.c2psi.businessmanagement.repositories.pos.userbm.UserBMRepository;
import com.c2psi.businessmanagement.services.contracts.client.delivery.DeliveryService;
import com.c2psi.businessmanagement.validators.client.delivery.DeliveryValidator;
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

@Service(value="DeliveryServiceV1")
@Slf4j
@Transactional
public class DeliveryServiceImpl implements DeliveryService {

    private DeliveryRepository deliveryRepository;
    private UserBMRepository userBMRepository;
    private PointofsaleRepository pointofsaleRepository;

    @Autowired
    public DeliveryServiceImpl(DeliveryRepository deliveryRepository, UserBMRepository userBMRepository,
                               PointofsaleRepository pointofsaleRepository) {
        this.deliveryRepository = deliveryRepository;
        this.userBMRepository = userBMRepository;
        this.pointofsaleRepository = pointofsaleRepository;
    }

    @Override
    public DeliveryDto saveDelivery(DeliveryDto deliveryDto) {
        /**********************************************************************
         * Il faut valider le parametre passe grace au validateur
         */
        List<String> errors = DeliveryValidator.validate(deliveryDto);
        if(!errors.isEmpty()){
            log.error("Entity deliveryDto not valid {}", deliveryDto);
            throw new InvalidEntityException("Le deliveryDto passe en argument n'est pas valide:  ",
                    ErrorCode.DELIVERY_NOT_VALID, errors);
        }

        /**********************************************************************
         * Sachant que d'apres le validateur le pos ne peut etre null
         * il faut se rassurer que son id n'est pas null et qu'il existe en BD
         */
        if(deliveryDto.getDeliveryPosDto().getId() == null){
            log.error("The id of the pointofsale associate with the delivery is null");
            throw new InvalidEntityException("L'Id du pointofsale associe a un delivery ne peut etre null ",
                    ErrorCode.DELIVERY_NOT_VALID);
        }
        //ici on est sur que cet id n'est pas null
        Optional<Pointofsale> optionalPointofsale = pointofsaleRepository.
                findPointofsaleById(deliveryDto.getDeliveryPosDto().getId());
        if(!optionalPointofsale.isPresent()){
            log.error("The precised posId does not match any pointofsale in the DB");
            throw new InvalidEntityException("L'id du pointofsale precise ne correspond a aucun pointofsale dans le systeme ",
                    ErrorCode.DELIVERY_NOT_VALID);
        }
        //A ce niveau on est sur le Pointofsale existe bel et bien en BD

        /**********************************************************************
         * Sachant que d'apres le validateur le userbm ne peut etre null
         * il faut se rassurer que son id n'est pas null et qu'il existe en BD
         */
        if(deliveryDto.getDeliveryUserbmDto().getId() == null){
            log.error("The id of the userbm associate with the delivery is null");
            throw new InvalidEntityException("L'Id du userbm associe a un delivery ne peut etre null ",
                    ErrorCode.DELIVERY_NOT_VALID);
        }
        //ici on est sur que cet id n'est pas null
        Optional<UserBM> optionalUserBM = userBMRepository.findUserBMById(deliveryDto.getDeliveryUserbmDto().getId());
        if(!optionalUserBM.isPresent()){
            log.error("The precised userbmId does not match any UserBM in the DB");
            throw new InvalidEntityException("L'id du userbm precise ne correspond a aucun userbm dans le systeme ",
                    ErrorCode.DELIVERY_NOT_VALID);
        }
        //A ce niveau on est sur le UserBM existe bel et bien en BD

        /**********************************************************************
         * Il faut se rassurer que le pos du Userbm s'il existe est le meme que
         * le pos precise pour le delivery
         */
        if(deliveryDto.getDeliveryUserbmDto().getBmPosDto() != null) {
            if (!deliveryDto.getDeliveryPosDto().getId().equals(deliveryDto.getDeliveryUserbmDto().getBmPosDto().getId())) {
                log.error("The userbm precise must belong to the same pointofsale concerned by the delivery");
                throw new InvalidEntityException("le pointofsale du userbm doit etre le meme que celui concerne par le delivery ",
                        ErrorCode.DELIVERY_NOT_VALID);
            }
        }

        /*****************************************************************************
         * Il faut se rassurer que le delivery sera unique dans le Pos sur son code
         */
        if(!isDeliveryUniqueinPos(deliveryDto.getDeliveryCode(), deliveryDto.getDeliveryPosDto().getId())){
            log.error("There is another delivery register in the DB with the same code for the same pointofsale");
            throw new DuplicateEntityException("Un autre delivery existe deja pour le pointofsale avec le meme code ",
                    ErrorCode.DELIVERY_DUPLICATED);
        }

        /*****************************************************************************
         * Lorsqu'on cree un delivery il est a l'etat Packup obligatoirement car
         * pour changer d'etat il faut que des verification soit faite
         */
        deliveryDto.setDeliveryState(DeliveryState.PackedUp);

        ///////////////////
        log.info("After all verification, the record {} can be saved on the DB", deliveryDto);
        return DeliveryDto.fromEntity(
                deliveryRepository.save(
                        DeliveryDto.toEntity(deliveryDto)
                )
        );
    }

    @Override
    public DeliveryDto updateDelivery(DeliveryDto deliveryDto) {
        /**********************************************************************
         * Il faut valider le parametre passe grace au validateur
         */
        List<String> errors = DeliveryValidator.validate(deliveryDto);
        if(!errors.isEmpty()){
            log.error("Entity deliveryDto not valid {}", deliveryDto);
            throw new InvalidEntityException("Le deliveryDto passe en argument n'est pas valide:  ",
                    ErrorCode.DELIVERY_NOT_VALID, errors);
        }

        /******************************************************************
         * On se rassure que l'id du delivery a update nest pas null et
         * quil existe en BD puis on le recupere
         */
        if(deliveryDto.getId() == null){
            log.error("The id of the delivery to update cannot be null");
            throw new InvalidEntityException("L'id du delivery a update ne saurait etre null ",
                    ErrorCode.DELIVERY_NOT_VALID);
        }
        //Ici on est sur qu'il n'est pas null mais est ce quil existe alors
        Optional<Delivery> optionalDelivery = deliveryRepository.findDeliveryById(deliveryDto.getId());
        if(!optionalDelivery.isPresent()){
            log.error("There is no delivery on the DB with the precised id");
            throw new InvalidEntityException("Aucun delivery n'existe en BD avec l'Id precise ",
                    ErrorCode.DELIVERY_NOT_VALID);
        }
        //Ici on est sur que tout existe bel et bien
        Delivery deliveryToUpdate = optionalDelivery.get();

        /*****************************************************************
         * On se rassure que ce n'est pas le pos quon veut changer
         */
        if(deliveryDto.getDeliveryPosDto().getId() == null){
            log.error("The deliveryPos id can't be null");
            throw new InvalidEntityException("L'id du deliveryPos ne saurait etre null ",
                    ErrorCode.DELIVERY_NOT_VALID);
        }
        if(!deliveryToUpdate.getDeliveryPos().getId().equals(deliveryDto.getDeliveryPosDto().getId())){
            log.error("It is not possible to update the Pointofsale of a delivery");
            throw new InvalidEntityException("Il n'est pas possible de mettre a jour le Pointofsale d'un Delivery ",
                    ErrorCode.DELIVERY_NOT_VALID);
        }


        /**************************************************************
         * On se rassure que ce n'est pas le userbm qu'on veut changer
         */
        if(deliveryDto.getDeliveryUserbmDto().getId() == null){
            log.error("The userbm id can't be null");
            throw new InvalidEntityException("L'id du userbm ne saurait etre null ",
                    ErrorCode.DELIVERY_NOT_VALID);
        }
        if(!deliveryToUpdate.getDeliveryUserbm().getId().equals(deliveryDto.getDeliveryUserbmDto().getId())){
            log.error("It is not possible to update the userbm of a delivery");
            throw new InvalidEntityException("Il n'est pas possible de mettre a jour le Userbm d'un Delivery ",
                    ErrorCode.DELIVERY_NOT_VALID);
        }

        /********************************************************************
         * Si cest le code quon veut changer alors on verifie que le newcode
         * va assurer l'unicite
         */
        if(!deliveryToUpdate.getDeliveryCode().equals(deliveryDto.getDeliveryCode())){
            if(!isDeliveryUniqueinPos(deliveryDto.getDeliveryCode(), deliveryToUpdate.getDeliveryPos().getId())){
                log.error("The new precised code is already user by another delivery");
                throw new DuplicateEntityException("Le nouveau code precise est deja utilise par un autre delivery ",
                        ErrorCode.DELIVERY_DUPLICATED);
            }
            //A ce niveau on est sur quil y aura pas de redondance
            deliveryToUpdate.setDeliveryCode(deliveryDto.getDeliveryCode());
        }

        /**********************************************************************
         * On modifie simplement les autres parametre sauf la date
         */
        deliveryToUpdate.setDeliveryDate(deliveryDto.getDeliveryDate());
        //deliveryToUpdate.setDeliveryState(deliveryDto.getDeliveryState());
        deliveryToUpdate.setDeliveryComment(deliveryDto.getDeliveryComment());

        log.info("After all verification, the record {} can be done on the DB", deliveryDto);
        return DeliveryDto.fromEntity(deliveryRepository.save(deliveryToUpdate));
    }

    @Override
    public DeliveryDto switchDeliveryState(Long deliveryId, DeliveryState deliveryState) {
        /***********************************
         * PackedUp,
         *     OutForDelivery,
         *     Delivery,
         *     Finish
         *
         * TRANSITION POSSIBLE
         * PackedUp to OutForDelivery: lorsque la livraison est confirme
         * Packup to Delivery: Pas possible
         * PackedUp to Finish: Pas possible
         *
         * OutofDelivery to Packup: si on veut modifier le delivery
         * OutForDelivery to Delivery: lorsque la livraison est effectue mais son rapport n'est pas enregistre
         * OutForDelivery to Finish: Pas possible
         *
         * Delivery to Finish lorsque: le rapport de la livraison est saisi pour toutes les commandes qui sont dans le delivery
         * Delivery to OutOfDelivery lorsque: Pas possible
         * Delivery to PackedUp lorsque: Pas possible
         *
         * Finish to Delivery lorsque: le rapport de livraison veut etre modifier
         * Finish to OutForDelivery lorsque: Pas possible
         * Finish to PackedUp lorsque: Pas possible
         *
         */

        return null;
    }

    @Override
    public DeliveryDto findDeliveryById(Long deliveryId) {
        if(deliveryId == null){
            log.error("The deliveryId can't be null");
            throw new NullArgumentException("L'id du delivery a retrouver est null");
        }
        Optional<Delivery> optionalDelivery = deliveryRepository.findDeliveryById(deliveryId);
        if(!optionalDelivery.isPresent()){
            log.error("There is no delivery in the DB with the precised id {}", deliveryId);
            throw new EntityNotFoundException("Aucun delivery n'existe en BD avec l'id precise ",
                    ErrorCode.DELIVERY_NOT_FOUND);
        }
        return DeliveryDto.fromEntity(optionalDelivery.get());
    }

    @Override
    public DeliveryDto findDeliveryByCodeinPos(String deliveryCode, Long posId) {
        if(!StringUtils.hasLength(deliveryCode)){
            log.error("the code precise can't be empty or null");
            throw new NullArgumentException("le code du delivery ne saurait etre null ou vide");
        }

        if(posId == null){
            log.error("The posId can't be null");
            throw new NullArgumentException("L'id du pos precise est null");
        }

        Optional<Delivery> optionalDelivery = deliveryRepository.findDeliveryByCodeInPos(deliveryCode, posId);
        return DeliveryDto.fromEntity(optionalDelivery.get());
    }

    @Override
    public Boolean deleteDeliveryById(Long deliveryId) {
        if(deliveryId == null){
            log.error("The deliveryId can't be null");
            throw new NullArgumentException("L'id du delivery a retrouver est null");
        }
        if(!isDeliveryDeleteable(deliveryId)){
            log.error("The delivery with the id {} can't be deleteable", deliveryId);
            throw new EntityNotFoundException("Le delivery d'id precise ne saurait etre supprime ",
                    ErrorCode.DELIVERY_NON_DELETEABLE);
        }
        Optional<Delivery> optionalDelivery = deliveryRepository.findDeliveryById(deliveryId);
        if(!optionalDelivery.isPresent()){
            log.error("There is no delivery in the DB with the precised id {}", deliveryId);
            throw new EntityNotFoundException("Aucun delivery n'existe en BD avec l'id precise ",
                    ErrorCode.DELIVERY_NOT_FOUND);
        }
        deliveryRepository.delete(optionalDelivery.get());
        return true;
    }

    @Override
    public Boolean isDeliveryUniqueinPos(String deliveryCode, Long posId) {
        if(!StringUtils.hasLength(deliveryCode)){
            log.error("the code precise can't be empty or null");
            throw new NullArgumentException("le code du delivery ne saurait etre null ou vide");
        }

        if(posId == null){
            log.error("The posId can't be null");
            throw new NullArgumentException("L'id du pos precise est null");
        }

        Optional<Delivery> optionalDelivery = deliveryRepository.findDeliveryByCodeInPos(deliveryCode, posId);

        return optionalDelivery.isEmpty();
    }

    @Override
    public Boolean isDeliveryDeleteable(Long deliveryId) {
        return true;
    }

    @Override
    public List<DeliveryDto> findAllDeliveryinPosBetween(Long posId, Instant startDate, Instant endDate) {
        if(posId == null){
            log.error("The posId can't be null");
            throw new NullArgumentException("L'id du pos precise est null");
        }
        Optional<List<Delivery>> optionalDeliveryList = deliveryRepository.findAllDeliveryinPosBetween(posId, startDate,
                endDate);
        return optionalDeliveryList.get().stream().map(DeliveryDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<DeliveryDto> findPageDeliveryinPosBetween(Long posId, Instant startDate, Instant endDate,
                                                          int pagenum, int pagesize) {
        if(posId == null){
            log.error("The posId can't be null");
            throw new NullArgumentException("L'id du pos precise est null");
        }
        Optional<Page<Delivery>> optionalDeliveryPage = deliveryRepository.findPageDeliveryinPosBetween(posId, startDate,
                endDate, PageRequest.of(pagenum, pagesize));
        return optionalDeliveryPage.get().map(DeliveryDto::fromEntity);
    }

    @Override
    public List<DeliveryDto> findAllDeliveryinPoswithStateBetween(Long posId, DeliveryState deliveryState,
                                                                  Instant startDate, Instant endDate) {
        if(posId == null){
            log.error("The posId can't be null");
            throw new NullArgumentException("L'id du pos precise est null");
        }
        Optional<List<Delivery>> optionalDeliveryList = deliveryRepository.findAllDeliveryinPoswithStateBetween(posId,
                deliveryState, startDate, endDate);
        return optionalDeliveryList.get().stream().map(DeliveryDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<DeliveryDto> findPageDeliveryinPoswithStateBetween(Long posId, DeliveryState deliveryState, Instant startDate, Instant endDate, int pagenum, int pagesize) {
        if(posId == null){
            log.error("The posId can't be null");
            throw new NullArgumentException("L'id du pos precise est null");
        }
        Optional<Page<Delivery>> optionalDeliveryPage = deliveryRepository.findPageDeliveryinPoswithStateBetween(posId,
                deliveryState, startDate, endDate, PageRequest.of(pagenum, pagesize));
        return optionalDeliveryPage.get().map(DeliveryDto::fromEntity);
    }

    @Override
    public List<DeliveryDto> findAllDeliveryinPosforUserbmBetween(Long posId, Long userbmId, Instant startDate, Instant endDate) {
        if(posId == null){
            log.error("The posId can't be null");
            throw new NullArgumentException("L'id du pos precise est null");
        }
        Optional<List<Delivery>> optionalDeliveryList = deliveryRepository.findAllDeliveryinPosforUserbmBetween(posId,
                userbmId, startDate, endDate);
        return optionalDeliveryList.get().stream().map(DeliveryDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<DeliveryDto> findPageDeliveryinPosforUserbmBetween(Long posId, Long userbmId, Instant startDate,
                                                                   Instant endDate, int pagenum, int pagesize) {
        if(posId == null){
            log.error("The posId can't be null");
            throw new NullArgumentException("L'id du pos precise est null");
        }
        Optional<Page<Delivery>> optionalDeliveryPage = deliveryRepository.findPageDeliveryinPosforUserbmBetween(posId,
                userbmId, startDate, endDate, PageRequest.of(pagenum, pagesize));
        return optionalDeliveryPage.get().map(DeliveryDto::fromEntity);
    }

    @Override
    public List<DeliveryDto> findAllDeliveryinPosforUserbmwithStateBetween(Long posId, Long userbmId, DeliveryState deliveryState, Instant startDate, Instant endDate) {
        if(posId == null){
            log.error("The posId can't be null");
            throw new NullArgumentException("L'id du pos precise est null");
        }
        Optional<List<Delivery>> optionalDeliveryList = deliveryRepository.findAllDeliveryinPosforUserbmwithStateBetween(posId,
                userbmId, deliveryState, startDate, endDate);
        return optionalDeliveryList.get().stream().map(DeliveryDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<DeliveryDto> findPageDeliveryinPosforUserbmwithStateBetween(Long posId, Long userbmId, DeliveryState deliveryState, Instant startDate, Instant endDate, int pagenum, int pagesize) {
        if(posId == null){
            log.error("The posId can't be null");
            throw new NullArgumentException("L'id du pos precise est null");
        }
        Optional<Page<Delivery>> optionalDeliveryPage = deliveryRepository.findPageDeliveryinPosforUserbmwithStateBetween(posId,
                userbmId, deliveryState, startDate, endDate, PageRequest.of(pagenum, pagesize));
        return optionalDeliveryPage.get().map(DeliveryDto::fromEntity);
    }
}
