package com.c2psi.businessmanagement.services.contractsImpl.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.CapsuleArrivalDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.*;
import com.c2psi.businessmanagement.repositories.stock.product.*;
import com.c2psi.businessmanagement.services.contracts.stock.product.CapsuleArrivalService;
import com.c2psi.businessmanagement.validators.stock.product.CapsuleArrivalValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value="CapsuleArrivalServiceV1")
@Slf4j
@Transactional
public class CapsuleArrivalServiceImpl implements CapsuleArrivalService {

    private CapsuleArrivalRepository capsuleArrivalRepository;
    private ArticleRepository articleRepository;
    private SupplyInvoiceCapsuleRepository supplyInvoiceCapsuleRepository;

    @Autowired
    public CapsuleArrivalServiceImpl(CapsuleArrivalRepository capsuleArrivalRepository, ArticleRepository articleRepository,
                                     SupplyInvoiceCapsuleRepository supplyInvoiceCapsuleRepository) {
        this.capsuleArrivalRepository = capsuleArrivalRepository;
        this.articleRepository = articleRepository;
        this.supplyInvoiceCapsuleRepository = supplyInvoiceCapsuleRepository;
    }

    @Override
    public CapsuleArrivalDto saveCapsuleArrival(CapsuleArrivalDto capsaDto) {

        /***************************************************
         * Il faut grace au validateur valider le parametre
         */
        List<String> errors = CapsuleArrivalValidator.validate(capsaDto);
        if(!errors.isEmpty()){
            log.error("Entity cashaDto not valid {}", capsaDto);
            throw new InvalidEntityException("Le capsaDto passe en argument n'est pas valide: "+errors,
                    ErrorCode.CAPSULEARRIVAL_NOT_VALID, errors);
        }

        /*********************************************************************
         * Il faut se rassurer que l'id de l'article associe n'est pas null
         * Et qu'il existe vraiment en BD
         */
        if(capsaDto.getCapsaArtDto().getId() ==  null){
            log.error("The id of the article associated with the capsarrival is null");
            throw new InvalidEntityException("L'id de l'article associe au capsarrival est null",
                    ErrorCode.CAPSULEARRIVAL_NOT_VALID);
        }
        //Ici on est sur que cet Id n'est pas null donc on doit donc le rechercher en BD
        Optional<Article> optionalArticle = articleRepository.findArticleById(capsaDto.getCapsaArtDto().getId());
        if(!optionalArticle.isPresent()){
            log.error("The articleId precised in the capsarrival {} does not identify any article in the DB", capsaDto);
            throw new InvalidEntityException("Dans le capsaDto passe en argument, l'id de l'article n'identifie pas " +
                    "un article existant en BD", ErrorCode.CAPSULEARRIVAL_NOT_VALID);
        }
        //ici on est sur qu'on a l'article

        /**********************************************************************************
         * Il faut se rassurer que l'id du supplyinvoicecapsule (la facture) n'est pas null
         * Et quil existe vraiment en BD
         * Car en fait on peut enregistrer un arrivage sans facture meme sil est standard
         * mais si l'id est deja non nul il faut que le sicaps soit dans la BD
         */
        if(capsaDto.getCapsaSicapsDto() !=  null) {
            //Ici on est sur que cet Id n'est pas null donc on doit donc le rechercher en BD
            Optional<SupplyInvoiceCapsule> optionalSupplyInvoiceCapsule = supplyInvoiceCapsuleRepository.
                    findSupplyInvoiceCapsuleById(capsaDto.getCapsaSicapsDto().getId());
            if (!optionalSupplyInvoiceCapsule.isPresent()) {
                log.error("The sicapsId precised in the capsarrival {} does not identify any supplyinvoicecapsule " +
                        "in the DB", capsaDto);
                throw new InvalidEntityException("Dans le capsaDto passe en argument, l'id de la facture cash n'identifie pas " +
                        "une facture cash existante en BD", ErrorCode.CAPSULEARRIVAL_NOT_VALID);
            }
            //si le sicash est precise alors
            /********************************************************************************************
             * Il faut se rassurer que le pointofsale dans le sicaps est le meme que celui de l'article
             */
            if(!capsaDto.getCapsaArtDto().getArtPosDto().getId().equals(capsaDto.getCapsaSicapsDto().getSicapsPosDto().getId())){
                log.error("The article and the supplyinvoicecapsule must belong to the same pointofsale");
                throw new InvalidEntityException("L'article dans le capsArrival et la facture cash doivent appartenir " +
                        "au meme point de vente");
            }

            /***********************************************************************************
             * Il faut se rassurer qu'il y aura pas de duplicata cest a dire quaucun capsarrival
             * na ete enregistrer pour le meme sicapsid (la meme facture) et le meme article
             */
            if(!isCapsuleArrivalUnique(capsaDto.getCapsaArtDto().getId(), capsaDto.getCapsaSicapsDto().getId())){
                log.error("We have in the same sicaps a capsarrival for the same article. but only one line per article " +
                        "is allow in the sicaps");
                throw new DuplicateEntityException("Un capsarrival existe deja dans le sicaps pour le meme article. Faut " +
                        "plutot modifier celui la car les duplicata ne sont pas permis", ErrorCode.CAPSULEARRIVAL_NOT_DUPLICATED);
            }
        }
        //Ici on est sur qu'on a la facture cash au cas ou elle a ete precise.

        log.error("After all verification, the capsaDto {} can be save in the DB without any problem", capsaDto);

        return CapsuleArrivalDto.fromEntity(
                capsuleArrivalRepository.save(
                        CapsuleArrivalDto.toEntity(capsaDto)
                )
        );
    }

    @Override
    public CapsuleArrivalDto updateCapsuleArrival(CapsuleArrivalDto capsaDto) {

        /******************************************************************
         * On valide grace au Dto le parametre passe
         */
        List<String> errors = CapsuleArrivalValidator.validate(capsaDto);
        if (!errors.isEmpty()) {
            log.error("Entity capsaDto not valid {}", capsaDto);
            throw new InvalidEntityException("Le capsaDto passe en argument n'est pas valide: " + errors,
                    ErrorCode.CAPSULEARRIVAL_NOT_VALID, errors);
        }

        /*******************************************************************
         * On se rassure que l'id de lentite a modifier nest pas null
         * Et quil identifie effectivement un capsaDto et a ce moment on
         * recupere l'entite a modifier
         */
        if (capsaDto.getId() == null) {
            log.error("The id of the capsarrival to update is null");
            throw new InvalidEntityException("L'id du capsarrival a modifier est null");
        }
        //ici on est sur quil nest pas null
        Optional<CapsuleArrival> optionalCapsuleArrivalToUpdate = capsuleArrivalRepository.findCapsuleArrivalById(capsaDto.getId());
        if (!optionalCapsuleArrivalToUpdate.isPresent()) {
            log.error("The capsarrival sent for update does not exist in the DB");
            throw new InvalidEntityException("Il n'existe pas en BD un capsarrival avec l'id passe en argument ",
                    ErrorCode.CAPSULEARRIVAL_NOT_VALID);
        }
        CapsuleArrival capsArrivalToUpdate = optionalCapsuleArrivalToUpdate.get();

        /****************************************************************************
         * On se rassure que si le sicaps existe deja alors on ne peut le modifier
         * mais sil n'existe pas alors on doit pouvoir le modifier en le fixant
         * On se rassure que ce n'est pas le sicaps (la facture) qu'on veut modifier
         * sinon on refuse.
         */
        if(capsaDto.getCapsaSicapsDto() != null){
            if(capsArrivalToUpdate.getCapsaSicaps() != null){
                if (!capsaDto.getCapsaSicapsDto().getId().equals(capsArrivalToUpdate.getCapsaSicaps().getId())) {
                    log.error("It is not possible to modify the sicaps during updating a capsarrival");
                    throw new InvalidEntityException("Il n'est pas possible de modifier la facture capsule d'un arrival. " +
                            "Supprimer carrement la facture en question et creer une autre", ErrorCode.CAPSULEARRIVAL_NOT_VALID);
                }
            }
            else{
                //Alors le sicaps de lentite a update etait null alors que celui de la mise a jour est non null
                if(capsaDto.getCapsaSicapsDto().getId() == null){
                    log.error("The sicaps id to fix to update the capsDtoToUpdate is null");
                    throw new InvalidEntityException("L'id du nouveau sicaps code est null ", ErrorCode.CAPSULEARRIVAL_NOT_VALID);
                }
                Optional<SupplyInvoiceCapsule> optionalSupplyInvoiceCaps = supplyInvoiceCapsuleRepository.
                        findSupplyInvoiceCapsuleById(capsaDto.getCapsaSicapsDto().getId());
                if (!optionalSupplyInvoiceCaps.isPresent()) {
                    log.error("The sicapsId precised in the capsarrival {} does not identify any supplyinvoicecaps in the DB",
                            capsaDto);
                    throw new InvalidEntityException("Dans le capsaDto passe en argument, l'id de la facture capsule n'identifie pas " +
                            "une facture caps existante en BD", ErrorCode.CAPSULEARRIVAL_NOT_VALID);
                }
                //On est sur que le sicash existe en BD. on peut donc le fixer sans souci sur le cashArrival
                capsArrivalToUpdate.setCapsaSicaps(optionalSupplyInvoiceCaps.get());
            }

            if(!capsaDto.getCapsaArtDto().getId().equals(capsArrivalToUpdate.getCapsaArt().getId())){
                /***************************************************
                 * On recherche le nouvel article dans la BD
                 */
                Optional<Article> optionalArticle = articleRepository.findArticleById(capsaDto.getCapsaArtDto().getId());
                if(!optionalArticle.isPresent()){
                    log.error("The articleId precised in the capsarrival {} does not identify any article in the DB", capsaDto);
                    throw new InvalidEntityException("Dans le capsaDto passe en argument, l'id de l'article n'identifie pas " +
                            "un article existant en BD", ErrorCode.CAPSULEARRIVAL_NOT_VALID);
                }
                Article newArticle = optionalArticle.get();
                /******
                 * Et dans ce cas on doit se rassurer de lunicite de larrivage
                 */
                if(!isCapsuleArrivalUnique(capsaDto.getCapsaArtDto().getId(), capsaDto.getCapsaSicapsDto().getId())){
                    log.error("There is another capsarrival for the same article in the same sicaps");
                    throw new DuplicateEntityException("Il existe deja un arrivage pour le meme article dans la meme " +
                            "facture capsule. il faut juste la modifier pour ajuster les quantites", ErrorCode.CAPSULEARRIVAL_NOT_DUPLICATED);
                }
                //ici on est sur qu'en modifiant l'article il ny a pas de duplicata
                capsArrivalToUpdate.setCapsaArt(newArticle);
            }

        }
        //////////////////
        //si le capsaDto.getCapsaSicapsDto() est null on fait rien car on peut avoir des arrivages sans facture
        /////////////////////////////////////////////////
        /**********************************************************************************
         * On verifie si cest l'article quon veut modifier et si cest le cas on se rassure
         * que le nouveau existe vraiment en BD
         */
        if(capsaDto.getCapsaArtDto() == null){
            log.error("The article cannot be null in the capsaDto");
            throw new InvalidEntityException("L'article dans le capsaDto ne peut etre null",
                    ErrorCode.CAPSULEARRIVAL_NOT_VALID);
        }
        if(capsaDto.getCapsaArtDto().getId() == null) {
            log.error("The id of article cannot be null in the capsaDto");
            throw new InvalidEntityException("L'id de l'article dans le capsaDto ne peut etre null",
                    ErrorCode.CAPSULEARRIVAL_NOT_VALID);
        }

        /*************************
         * On peut a present modifier le reste de parametre de l'arrivage sans souci
         */
        capsArrivalToUpdate.setCapsaDeliveryquantity(capsaDto.getCapsaDeliveryquantity());
        capsArrivalToUpdate.setCapsaArrivalEntryDate(capsaDto.getCapsaArrivalEntryDate());
        capsArrivalToUpdate.setCapsaQuantitycapschanged(capsaDto.getCapsaQuantitycapschanged());

        log.info("After all verification and all the updation well prepared, we can lauch the operation");

        return CapsuleArrivalDto.fromEntity(capsuleArrivalRepository.save(capsArrivalToUpdate));
    }

    @Override
    public Boolean isCapsuleArrivalUnique(Long articleId, Long sicapsId) {
        if(articleId == null){
            log.error("The method can't be running on null argument");
            throw new NullArgumentException("La methode ne peut s'executer sur un argument null");
        }
        if(sicapsId == null){
            log.error("The method can't be running on null argument");
            throw new NullArgumentException("La methode ne peut s'executer sur un argument null");
        }

        Optional<CapsuleArrival> optionalCapsArrival = capsuleArrivalRepository.findCapsArrivalByArticleAndSicaps(articleId, sicapsId);

        return optionalCapsArrival.isEmpty();
    }

    @Override
    public Boolean isCapsuleArrivalDeleteable(Long capsaId) {
        return true;
    }

    @Override
    public Boolean deleteCapsuleArrivalById(Long capsaId) {
        if(capsaId == null){
            log.error("The method can't be running on null argument");
            throw new NullArgumentException("La methode ne peut s'executer sur un argument null");
        }
        Optional<CapsuleArrival> optionalCapsuleArrival = capsuleArrivalRepository.findCapsuleArrivalById(capsaId);
        if(!optionalCapsuleArrival.isPresent()){
            log.error("There is no capsArrival in DB with the id {} precised as argument", capsaId);
            throw new EntityNotFoundException("Aucun capsArrival n'existe avec l'Id passe en argument ",
                    ErrorCode.CAPSULEARRIVAL_NOT_FOUND);
        }
        if(!isCapsuleArrivalDeleteable(capsaId)){
            log.error("The capsarrival is not yet deleteable");
            throw new EntityNotDeleteableException("Il n'est plus possible de supprimer le capsArrival ",
                    ErrorCode.CAPSULEARRIVAL_NOT_DELETEABLE);
        }
        capsuleArrivalRepository.delete(optionalCapsuleArrival.get());
        return true;
    }

    @Override
    public CapsuleArrivalDto findCapsuleArrivalById(Long capsaId) {
        if(capsaId == null){
            log.error("The method can't be running on null argument");
            throw new NullArgumentException("La methode ne peut s'executer sur un argument null");
        }
        Optional<CapsuleArrival> optionalCapsArrival = capsuleArrivalRepository.findCapsuleArrivalById(capsaId);
        if(!optionalCapsArrival.isPresent()){
            log.error("There is no capsArrival in DB with the id {} precised as argument", capsaId);
            throw new EntityNotFoundException("Aucun capsArrival n'existe avec l'Id passe en argument ",
                    ErrorCode.CAPSULEARRIVAL_NOT_FOUND);
        }
        return CapsuleArrivalDto.fromEntity(optionalCapsArrival.get());
    }

    @Override
    public CapsuleArrivalDto findCapsuleArrivalofArticleinSicaps(Long articleId, Long sicapsId) {
        if(articleId == null){
            log.error("The method can't be running on null articleId argument");
            throw new NullArgumentException("La methode ne peut s'executer sur un argument articleId null");
        }
        //Le sicapsId peut etre null car on peut avoir des arrivages sans facture
        Optional<CapsuleArrival> optionalCapsArrival = capsuleArrivalRepository.findCapsArrivalByArticleAndSicaps(articleId,
                sicapsId);
        if(!optionalCapsArrival.isPresent()){
            log.error("There is no capsArrival in DB for the articleId {} precised in the sicapsid {} precise", articleId, sicapsId);
            throw new EntityNotFoundException("Aucun capsArrival n'existe avec les parametres passe en argument ",
                    ErrorCode.CAPSULEARRIVAL_NOT_FOUND);
        }
        return CapsuleArrivalDto.fromEntity(optionalCapsArrival.get());
    }

    @Override
    public List<CapsuleArrivalDto> findAllCapsuleArrivalinSicaps(Long sicapsId) {
        if(sicapsId == null){
            log.error("The method can't be running on null sicapsId argument");
            throw new NullArgumentException("La methode ne peut s'executer sur un argument sicapsId null");
        }

        Optional<List<CapsuleArrival>> optionalCapsArrivalList = capsuleArrivalRepository.findAllCapsArrivalinSicaps(sicapsId);
        if(!optionalCapsArrivalList.isPresent()){
            log.error("There is no sicaps in DB with the precised id {}", sicapsId);
            throw new EntityNotFoundException("Aucune facture cash n'existe avec l'id envoye ",
                    ErrorCode.SUPPLYINVOICECAPSULE_NOT_FOUND);
        }
        List<CapsuleArrival> capsuleArrivalList = optionalCapsArrivalList.get();

        return capsuleArrivalList.stream().map(CapsuleArrivalDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CapsuleArrivalDto> findPageCapsuleArrivalinSicaps(Long sicapsId, int pagenum, int pagesize) {
        if(sicapsId == null){
            log.error("The method can't be running on null sicapsId argument");
            throw new NullArgumentException("La methode ne peut s'executer sur un argument sicapsId null");
        }

        Optional<Page<CapsuleArrival>> optionalCapsArrivalPage = capsuleArrivalRepository.findPageCapsArrivalinSicaps(
                sicapsId, PageRequest.of(pagenum, pagesize));
        if(!optionalCapsArrivalPage.isPresent()){
            log.error("There is no sicaps in DB with the precised id {}", sicapsId);
            throw new EntityNotFoundException("Aucune facture cash n'existe avec l'id envoye ",
                    ErrorCode.SUPPLYINVOICECAPSULE_NOT_FOUND);
        }
        Page<CapsuleArrival> capsuleArrivalPage = optionalCapsArrivalPage.get();

        return capsuleArrivalPage.map(CapsuleArrivalDto::fromEntity);
    }

    @Override
    public List<CapsuleArrivalDto> findAllCapsuleArrivalinSicapsBetween(Long sicapsId, Instant startDate,
                                                                        Instant endDate) {
        if(sicapsId == null){
            log.error("The method can't be running on null sicapsId argument");
            throw new NullArgumentException("La methode ne peut s'executer sur un argument sicapsId null");
        }

        Optional<List<CapsuleArrival>> optionalCapsArrivalList = capsuleArrivalRepository.findAllCapsArrivalinSicapsBetween(
                sicapsId, startDate, endDate);
        if(!optionalCapsArrivalList.isPresent()){
            log.error("There is no sicaps in DB with the precised id {}", sicapsId);
            throw new EntityNotFoundException("Aucune facture cash n'existe avec l'id envoye ",
                    ErrorCode.SUPPLYINVOICECAPSULE_NOT_FOUND);
        }
        List<CapsuleArrival> capsuleArrivalList = optionalCapsArrivalList.get();

        return capsuleArrivalList.stream().map(CapsuleArrivalDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CapsuleArrivalDto> findPageCapsuleArrivalinSicapsBetween(Long sicapsId, Instant startDate,
                                                                         Instant endDate, int pagenum, int pagesize) {
        if(sicapsId == null){
            log.error("The method can't be running on null sicapsId argument");
            throw new NullArgumentException("La methode ne peut s'executer sur un argument sicapsId null");
        }

        Optional<Page<CapsuleArrival>> optionalCapsArrivalPage = capsuleArrivalRepository.findPageCapsArrivalinSicapsBetween(
                sicapsId, startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCapsArrivalPage.isPresent()){
            log.error("There is no sicaps in DB with the precised id {}", sicapsId);
            throw new EntityNotFoundException("Aucune facture capsule n'existe avec l'id envoye ",
                    ErrorCode.SUPPLYINVOICECAPSULE_NOT_FOUND);
        }
        Page<CapsuleArrival> capsuleArrivalPage = optionalCapsArrivalPage.get();

        return capsuleArrivalPage.map(CapsuleArrivalDto::fromEntity);
    }
}
