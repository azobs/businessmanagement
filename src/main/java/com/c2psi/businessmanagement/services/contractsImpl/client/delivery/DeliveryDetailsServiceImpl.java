package com.c2psi.businessmanagement.services.contractsImpl.client.delivery;

import com.c2psi.businessmanagement.dtos.client.delivery.DeliveryDetailsDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.Delivery;
import com.c2psi.businessmanagement.models.DeliveryDetails;
import com.c2psi.businessmanagement.models.Packaging;
import com.c2psi.businessmanagement.repositories.client.delivery.DeliveryDetailsRepository;
import com.c2psi.businessmanagement.repositories.client.delivery.DeliveryRepository;
import com.c2psi.businessmanagement.repositories.stock.product.PackagingRepository;
import com.c2psi.businessmanagement.services.contracts.client.delivery.DeliveryDetailsService;
import com.c2psi.businessmanagement.validators.client.delivery.DeliveryDetailsValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value="DeliveryDetailsServiceV1")
@Slf4j
@Transactional
public class DeliveryDetailsServiceImpl implements DeliveryDetailsService {

    private DeliveryDetailsRepository deliveryDetailsRepository;
    private DeliveryRepository deliveryRepository;
    private PackagingRepository packagingRepository;

    @Autowired
    public DeliveryDetailsServiceImpl(DeliveryDetailsRepository deliveryDetailsRepository,
                                      DeliveryRepository deliveryRepository, PackagingRepository packagingRepository) {
        this.deliveryDetailsRepository = deliveryDetailsRepository;
        this.deliveryRepository = deliveryRepository;
        this.packagingRepository = packagingRepository;
    }

    @Override
    public DeliveryDetailsDto saveDeliveryDetails(DeliveryDetailsDto ddDto) {
        /*****************************************************
         * On valide d'abord l'argument passe en argument
         */
        List<String> errors = DeliveryDetailsValidator.validate(ddDto);
        if(!errors.isEmpty()){
            log.error("Entity deliverydetailsDto not valid {}", ddDto);
            throw new InvalidEntityException("Le deliverydetailsDto passe en argument n'est pas valide:  ",
                    ErrorCode.DELIVERYDETAILS_NOT_VALID, errors);
        }

        /************************************************************************
         * On se rassure que l'id du Packaging n'est pas null et si c'est le cas
         * on se rassure qu'il existe vraiment en BD
         */
        if(ddDto.getDdPackagingDto().getId() == null){
            log.error("The id of the packaging associate in this deliverydetails is null");
            throw new InvalidEntityException("L'id du packaging associe a ce deliverydetails est null ",
                    ErrorCode.DELIVERYDETAILS_NOT_VALID);
        }
        Optional<Packaging> optionalPackaging = packagingRepository.findPackagingById(ddDto.getDdPackagingDto().getId());
        if(!optionalPackaging.isPresent()){
            log.error("The packaging precise in the deliverydetails not exist in the DB");
            throw new InvalidEntityException("Le packaging associe a ce deliverydetails n'existe pas en BD ",
                    ErrorCode.DELIVERYDETAILS_NOT_VALID);
        }
        //Ici in est sur que le packaging existe vraiment pour cet enregistrement

        /*************************************************************************
         * On se rassure que l'id du Delivery n'est pas null et si c'est le cas on
         * se rassure qu'il existe vraiment en BD
         */
        if(ddDto.getDdDeliveryDto().getId() == null){
            log.error("The id of the delivery associate in this deliverydetails is null");
            throw new InvalidEntityException("L'id du delivery associe a ce deliverydetails est null ",
                    ErrorCode.DELIVERYDETAILS_NOT_VALID);
        }
        Optional<Delivery> optionalDelivery = deliveryRepository.findDeliveryById(ddDto.getDdDeliveryDto().getId());
        if(!optionalDelivery.isPresent()){
            log.error("The delivery precise in the deliverydetails not exist in the DB");
            throw new InvalidEntityException("Le delivery associe a ce deliverydetails n'existe pas en BD ",
                    ErrorCode.DELIVERYDETAILS_NOT_VALID);
        }

        /**************************************************************************
         * On se rassure de l'unicite du deliverydetails en se rassurant qu'il
         * n'y existe pas deja une deliverydetails avec le meme delivery pour le
         * meme packaging.
         */
        if(!isDeliveryDetailsUniqueinDeliverywithPackaging(optionalPackaging.get().getId(),
                optionalDelivery.get().getId())){
            log.error("The deliverydetails is already existing in the DB");
            throw new DuplicateEntityException("Le deliverydetails existe deja dans la BD ",
                    ErrorCode.DELIVERYDETAILS_DUPLICATED);
        }

        /////////////////////////
        log.info("After all verification, the record {} can be saved on the DB", ddDto);
        return DeliveryDetailsDto.fromEntity(
                deliveryDetailsRepository.save(
                        DeliveryDetailsDto.toEntity(ddDto)
                )
        );
    }

    @Override
    public DeliveryDetailsDto updateDeliveryDetails(DeliveryDetailsDto ddDto) {

        /*****************************************************
         * On valide d'abord l'argument passe en argument
         */
        List<String> errors = DeliveryDetailsValidator.validate(ddDto);
        if(!errors.isEmpty()){
            log.error("Entity deliverydetailsDto not valid {}", ddDto);
            throw new InvalidEntityException("Le deliverydetailsDto passe en argument n'est pas valide:  ",
                    ErrorCode.DELIVERYDETAILS_NOT_VALID, errors);
        }

        /****************************************************************
         * On recupere le deliverydetails a update apres avoir verifier
         * que l'id existe
         */
        if(ddDto.getId() == null){
            log.error("The id of the deliverydetails to update is null and it is not possible");
            throw new InvalidEntityException("Il n'est pas possible de recuperer de deliverydetails a update car " +
                    "son id est null", ErrorCode.DELIVERYDETAILS_NOT_VALID);
        }
        Optional<DeliveryDetails> optionalDeliveryDetails = deliveryDetailsRepository.findDeliveryDetailsById(ddDto.getId());
        if(!optionalDeliveryDetails.isPresent()){
            log.error("The id precise in the request does not match any deliverydetails");
            throw new InvalidEntityException("L'id precise dans la requete ne correspond a aucun deliverydetails dans la BD",
                    ErrorCode.DELIVERYDETAILS_NOT_VALID);
        }
        //On recupere le deliverydetails a update
        DeliveryDetails deliveryDetailsToUpdate = optionalDeliveryDetails.get();

        /******************************************************************************
         * On se rassure que ce n'est pas le delivery quon veut modifier
         */
        if(ddDto.getDdDeliveryDto().getId() == null){
            log.error("The id of the delivery associate cannot be null");
            throw new InvalidEntityException("L'Id du delivery associe ne peut etre null ",
                    ErrorCode.DELIVERYDETAILS_NOT_VALID);
        }
        if(!deliveryDetailsToUpdate.getDdDelivery().getId().equals(ddDto.getDdDeliveryDto().getId())){
            log.error("It is not possible to update the delivery in the deliverydetails");
            throw new InvalidEntityException("Il n'est pas possible de mettre a jour le delivery dans le deliverydetails ",
                    ErrorCode.DELIVERYDETAILS_NOT_VALID);
        }

        /******************************************************************************
         * On verifie si c'est le packaging qu'on veut modifier et si c'est le cas on
         * regarde si ce packaging existe bel et bien
         */
        if(ddDto.getDdPackagingDto().getId() == null){
            log.error("The id of the associate packaging cannot be null");
            throw new InvalidEntityException("L'id du Packaging associe ne saurait etre null",
                    ErrorCode.DELIVERYDETAILS_NOT_VALID);
        }
        if(!deliveryDetailsToUpdate.getDdPackaging().getId().equals(ddDto.getDdPackagingDto().getId())){
            Optional<Packaging> optionalPackaging = packagingRepository.findPackagingById(ddDto.getDdPackagingDto().getId());
            if(!optionalPackaging.isPresent()){
                log.error("The packaging precise in the deliverydetails not exist in the DB");
                throw new InvalidEntityException("Le packaging associe a ce deliverydetails n'existe pas en BD ",
                        ErrorCode.DELIVERYDETAILS_NOT_VALID);
            }
            /****************************************************************************************
             * On a le new packaging mais il faut avant la mise a jour se rassurer quil n'y a pas de
             * redondance
             */
            if(!isDeliveryDetailsUniqueinDeliverywithPackaging(optionalPackaging.get().getId(),
                    deliveryDetailsToUpdate.getDdDelivery().getId())){
                log.error("The deliverydetails is already existing in the DB");
                throw new InvalidEntityException("Le deliverydetails existe deja dans la BD ",
                        ErrorCode.DELIVERYDETAILS_DUPLICATED);
            }
            //Ici on est sur quon peut mettre a jour le packaging sans risque de redondance
            deliveryDetailsToUpdate.setDdPackaging(optionalPackaging.get());
        }

        /*******************************************************************************
         * On peut maintenant mettre a jour le reste des parametres du deliverydetails
         */
        deliveryDetailsToUpdate.setDdNumberofpackagereturn(ddDto.getDdNumberofpackagereturn());
        deliveryDetailsToUpdate.setDdNumberofpackageused(ddDto.getDdNumberofpackageused());

        log.info("After all verification, the record {} can be done on the DB", ddDto);
        return DeliveryDetailsDto.fromEntity(deliveryDetailsRepository.save(deliveryDetailsToUpdate));
    }

    @Override
    public DeliveryDetailsDto findDeliveryDetailsById(Long ddId) {
        if(ddId == null){
            log.error("The id precised as argument is null");
            throw new NullArgumentException("L'argument precise est null dans la methode");
        }
        Optional<DeliveryDetails> optionalDeliveryDetails = deliveryDetailsRepository.findDeliveryDetailsById(ddId);
        if(!optionalDeliveryDetails.isPresent()){
            log.error("There is no deliverydetails in the DB with the id {} precised", ddId);
            throw new EntityNotFoundException("Aucun deliverydetails n'existe avec l'id precise ",
                    ErrorCode.DELIVERYDETAILS_NOT_FOUND);
        }

        return DeliveryDetailsDto.fromEntity(optionalDeliveryDetails.get());
    }

    @Override
    public Boolean isDeliveryDetailsUniqueinDeliverywithPackaging(Long packagingId, Long deliveryId) {
        if(packagingId == null){
            log.error("The packagingId precised as argument is null");
            throw new NullArgumentException("L'argument packagingId precise est null dans la methode");
        }

        if(deliveryId == null){
            log.error("The deliveryId precised as argument is null");
            throw new NullArgumentException("L'argument deliveryId precise est null dans la methode");
        }

        Optional<DeliveryDetails> optionalDeliveryDetails = deliveryDetailsRepository.
                findDeliveryDetailsByPackagingAndDelivery(packagingId, deliveryId);

        return optionalDeliveryDetails.isEmpty();
    }

    @Override
    public DeliveryDetailsDto findDeliveryDetailsinDeliverywithPackaging(Long packagingId, Long deliveryId) {

        if(packagingId == null){
            log.error("The packagingId precised as argument is null");
            throw new NullArgumentException("L'argument packagingId precise est null dans la methode");
        }

        if(deliveryId == null){
            log.error("The deliveryId precised as argument is null");
            throw new NullArgumentException("L'argument deliveryId precise est null dans la methode");
        }

        Optional<DeliveryDetails> optionalDeliveryDetails = deliveryDetailsRepository.
                findDeliveryDetailsByPackagingAndDelivery(packagingId, deliveryId);
        if(!optionalDeliveryDetails.isPresent()){
            log.error("There is no deliverydetails in the DB with the packagingId {} and the deliveryID {} precise ",
                    packagingId, deliveryId);
            throw new EntityNotFoundException("Aucun deliverydetails n'existe dans la BD pour le packaging precise " +
                    "et le delivery precise ", ErrorCode.DELIVERYDETAILS_NOT_FOUND);
        }

        return DeliveryDetailsDto.fromEntity(optionalDeliveryDetails.get());
    }

    @Override
    public Boolean deleteDeliveryDetailsById(Long ddId) {

        if(ddId == null){
            log.error("The id precised as argument is null");
            throw new NullArgumentException("L'argument precise est null dans la methode");
        }
        Optional<DeliveryDetails> optionalDeliveryDetails = deliveryDetailsRepository.findDeliveryDetailsById(ddId);
        if(!optionalDeliveryDetails.isPresent()){
            log.error("There is no deliverydetails in the DB with the id {} precised", ddId);
            throw new EntityNotFoundException("Aucun deliverydetails n'existe avec l'id precise ",
                    ErrorCode.DELIVERYDETAILS_NOT_FOUND);
        }

        if(!isDeliveryDetailsDeleteable(ddId)){
            log.error("The deliverydetails precise by the id {} can't be deleted", ddId);
            throw new EntityNotFoundException("Le deliverydetails precise ne peut etre supprime",
                    ErrorCode.DELIVERYDETAILS_NOT_DELETEABLE);
        }

        deliveryDetailsRepository.delete(optionalDeliveryDetails.get());

        return true;
    }

    @Override
    public Boolean isDeliveryDetailsDeleteable(Long ddId) {

        return true;
    }

    @Override
    public List<DeliveryDetailsDto> findAllDeliveryDetailsinDelivery(Long deliveryId) {
        if(deliveryId == null){
            log.error("The id precised as argument is null");
            throw new NullArgumentException("L'argument precise est null dans la methode");
        }
        Optional<List<DeliveryDetails>> optionalDeliveryDetailsList = deliveryDetailsRepository.
                findAllDeliveryDetailsinDelivery(deliveryId);
        return optionalDeliveryDetailsList.get().stream().map(DeliveryDetailsDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<DeliveryDetailsDto> findPageDeliveryDetailsinDelivery(Long deliveryId, int pagenum, int pagesize) {
        if(deliveryId == null){
            log.error("The id precised as argument is null");
            throw new NullArgumentException("L'argument precise est null dans la methode");
        }
        Optional<Page<DeliveryDetails>> optionalDeliveryDetailsPage = deliveryDetailsRepository.
                findPageDeliveryDetailsinDelivery(deliveryId, PageRequest.of(pagenum, pagesize));
        return optionalDeliveryDetailsPage.get().map(DeliveryDetailsDto::fromEntity);
    }
}
