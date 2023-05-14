package com.c2psi.businessmanagement.services.contractsImpl.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.CapsuleArrivalDto;
import com.c2psi.businessmanagement.dtos.stock.product.DamageArrivalDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.*;
import com.c2psi.businessmanagement.repositories.stock.product.*;
import com.c2psi.businessmanagement.services.contracts.stock.product.DamageArrivalService;
import com.c2psi.businessmanagement.validators.stock.product.CapsuleArrivalValidator;
import com.c2psi.businessmanagement.validators.stock.product.DamageArrivalValidator;
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

@Service(value="DamageArrivalServiceV1")
@Slf4j
@Transactional
public class DamageArrivalServiceImpl implements DamageArrivalService {

    private DamageArrivalRepository damageArrivalRepository;
    private ArticleRepository articleRepository;
    private SupplyInvoiceDamageRepository supplyInvoiceDamageRepository;

    @Autowired
    public DamageArrivalServiceImpl(DamageArrivalRepository damageArrivalRepository, ArticleRepository articleRepository,
                                    SupplyInvoiceDamageRepository supplyInvoiceDamageRepository) {
        this.damageArrivalRepository = damageArrivalRepository;
        this.articleRepository = articleRepository;
        this.supplyInvoiceDamageRepository = supplyInvoiceDamageRepository;
    }

    @Override
    public DamageArrivalDto saveDamageArrival(DamageArrivalDto damaDto) {

        /***************************************************
         * Il faut grace au validateur valider le parametre
         */
        List<String> errors = DamageArrivalValidator.validate(damaDto);
        if(!errors.isEmpty()){
            log.error("Entity damaDto not valid {}", damaDto);
            throw new InvalidEntityException("Le damaDto passe en argument n'est pas valide: "+errors,
                    ErrorCode.DAMAGEARRIVAL_NOT_VALID, errors);
        }

        /*********************************************************************
         * Il faut se rassurer que l'id de l'article associe n'est pas null
         * Et qu'il existe vraiment en BD
         */
        if(damaDto.getDamaArtDto().getId() ==  null){
            log.error("The id of the article associated with the damagearrival is null");
            throw new InvalidEntityException("L'id de l'article associe au damagearrival est null",
                    ErrorCode.DAMAGEARRIVAL_NOT_VALID);
        }
        //Ici on est sur que cet Id n'est pas null donc on doit donc le rechercher en BD
        Optional<Article> optionalArticle = articleRepository.findArticleById(damaDto.getDamaArtDto().getId());
        if(!optionalArticle.isPresent()){
            log.error("The articleId precised in the damagearrival {} does not identify any article in the DB", damaDto);
            throw new InvalidEntityException("Dans le damaDto passe en argument, l'id de l'article n'identifie pas " +
                    "un article existant en BD", ErrorCode.DAMAGEARRIVAL_NOT_VALID);
        }
        //ici on est sur qu'on a l'article

        /**********************************************************************************
         * Il faut se rassurer que l'id du supplyinvoicedamage (la facture) n'est pas null
         * Et quil existe vraiment en BD
         * Car en fait on peut enregistrer un arrivage sans facture meme sil est standard
         * mais si l'id est deja non nul il faut que le sidam soit dans la BD
         */
        if(damaDto.getDamaSidamDto() !=  null) {
            //Ici on est sur que cet Id n'est pas null donc on doit donc le rechercher en BD
            Optional<SupplyInvoiceDamage> optionalSupplyInvoiceDamage = supplyInvoiceDamageRepository.
                    findSupplyInvoiceDamageById(damaDto.getDamaSidamDto().getId());
            if (!optionalSupplyInvoiceDamage.isPresent()) {
                log.error("The sidamId precised in the damagearrival {} does not identify any supplyinvoicedamage " +
                        "in the DB", damaDto);
                throw new InvalidEntityException("Dans le damaDto passe en argument, l'id de la facture cash n'identifie pas " +
                        "une facture cash existante en BD", ErrorCode.DAMAGEARRIVAL_NOT_VALID);
            }
            //si le sicash est precise alors
            /********************************************************************************************
             * Il faut se rassurer que le pointofsale dans le sicaps est le meme que celui de l'article
             */
            if(!damaDto.getDamaArtDto().getArtPosDto().getId().equals(damaDto.getDamaSidamDto().getSidamPosDto().getId())){
                log.error("The article and the supplyinvoicedamage must belong to the same pointofsale");
                throw new InvalidEntityException("L'article dans le damageArrival et la facture cash doivent appartenir " +
                        "au meme point de vente");
            }

            /***************************************************************************************
             * Il faut se rassurer qu'il y aura pas de duplicata cest a dire quaucun damagearrival
             * na ete enregistrer pour le meme sidamid (la meme facture) et le meme article
             */
            if(!isDamageArrivalUnique(damaDto.getDamaArtDto().getId(), damaDto.getDamaSidamDto().getId())){
                log.error("We have in the same sidam a damagearrival for the same article. but only one line per article " +
                        "is allow in the sidam");
                throw new DuplicateEntityException("Un damarrival existe deja dans le sidam pour le meme article. Faut " +
                        "plutot modifier celui la car les duplicata ne sont pas permis", ErrorCode.DAMAGEARRIVAL_NOT_DUPLICATED);
            }
        }
        //Ici on est sur qu'on a la facture cash au cas ou elle a ete precise.

        log.error("After all verification, the damaDto {} can be save in the DB without any problem", damaDto);

        return DamageArrivalDto.fromEntity(
                damageArrivalRepository.save(
                        DamageArrivalDto.toEntity(damaDto)
                )
        );
    }

    @Override
    public DamageArrivalDto updateDamageArrival(DamageArrivalDto damaDto) {

        /******************************************************************
         * On valide grace au Dto le parametre passe
         */
        List<String> errors = DamageArrivalValidator.validate(damaDto);
        if (!errors.isEmpty()) {
            log.error("Entity damaDto not valid {}", damaDto);
            throw new InvalidEntityException("Le damaDto passe en argument n'est pas valide: " + errors,
                    ErrorCode.DAMAGEARRIVAL_NOT_VALID, errors);
        }

        /*******************************************************************
         * On se rassure que l'id de lentite a modifier nest pas null
         * Et quil identifie effectivement un damaDto et a ce moment on
         * recupere l'entite a modifier
         */
        if (damaDto.getId() == null) {
            log.error("The id of the damagearrival to update is null");
            throw new InvalidEntityException("L'id du damarrival a modifier est null");
        }
        //ici on est sur quil nest pas null
        Optional<DamageArrival> optionalDamageArrivalToUpdate = damageArrivalRepository.findDamageArrivalById(damaDto.getId());
        if (!optionalDamageArrivalToUpdate.isPresent()) {
            log.error("The damarrival sent for update does not exist in the DB");
            throw new InvalidEntityException("Il n'existe pas en BD un damarrival avec l'id passe en argument ",
                    ErrorCode.DAMAGEARRIVAL_NOT_VALID);
        }
        DamageArrival damageArrivalToUpdate = optionalDamageArrivalToUpdate.get();

        /****************************************************************************
         * On se rassure que si le sidam existe deja alors on ne peut le modifier
         * mais sil n'existe pas alors on doit pouvoir le modifier en le fixant
         * On se rassure que ce n'est pas le sidam (la facture) qu'on veut modifier
         * sinon on refuse.
         */
        if(damaDto.getDamaSidamDto() != null){
            if(damageArrivalToUpdate.getDamaSidam() != null){
                if (!damaDto.getDamaSidamDto().getId().equals(damageArrivalToUpdate.getDamaSidam().getId())) {
                    log.error("It is not possible to modify the sicaps during updating a capsarrival");
                    throw new InvalidEntityException("Il n'est pas possible de modifier la facture capsule d'un arrival. " +
                            "Supprimer carrement la facture en question et creer une autre", ErrorCode.CAPSULEARRIVAL_NOT_VALID);
                }
            }
            else{
                //Alors le sicaps de lentite a update etait null alors que celui de la mise a jour est non null
                if(damaDto.getDamaSidamDto().getId() == null){
                    log.error("The sicaps id to fix to update the capsDtoToUpdate is null");
                    throw new InvalidEntityException("L'id du nouveau sicaps code est null ", ErrorCode.CAPSULEARRIVAL_NOT_VALID);
                }
                Optional<SupplyInvoiceDamage> optionalSupplyInvoiceDamage = supplyInvoiceDamageRepository.
                        findSupplyInvoiceDamageById(damaDto.getDamaSidamDto().getId());
                if (!optionalSupplyInvoiceDamage.isPresent()) {
                    log.error("The sicapsId precised in the capsarrival {} does not identify any supplyinvoicecaps in the DB",
                            damaDto);
                    throw new InvalidEntityException("Dans le capsaDto passe en argument, l'id de la facture capsule n'identifie pas " +
                            "une facture caps existante en BD", ErrorCode.CAPSULEARRIVAL_NOT_VALID);
                }
                //On est sur que le sicash existe en BD. on peut donc le fixer sans souci sur le cashArrival
                damageArrivalToUpdate.setDamaSidam(optionalSupplyInvoiceDamage.get());
            }

            if(!damaDto.getDamaArtDto().getId().equals(damageArrivalToUpdate.getDamaArt().getId())){
                /***************************************************
                 * On recherche le nouvel article dans la BD
                 */
                Optional<Article> optionalArticle = articleRepository.findArticleById(damaDto.getDamaArtDto().getId());
                if(!optionalArticle.isPresent()){
                    log.error("The articleId precised in the capsarrival {} does not identify any article in the DB", damaDto);
                    throw new InvalidEntityException("Dans le capsaDto passe en argument, l'id de l'article n'identifie pas " +
                            "un article existant en BD", ErrorCode.CAPSULEARRIVAL_NOT_VALID);
                }
                Article newArticle = optionalArticle.get();
                /******
                 * Et dans ce cas on doit se rassurer de lunicite de larrivage
                 */
                if(!isDamageArrivalUnique(damaDto.getDamaArtDto().getId(), damaDto.getDamaSidamDto().getId())){
                    log.error("There is another damagearrival for the same article in the same sidam");
                    throw new DuplicateEntityException("Il existe deja un arrivage pour le meme article dans la meme " +
                            "facture damage. il faut juste la modifier pour ajuster les quantites", ErrorCode.DAMAGEARRIVAL_NOT_DUPLICATED);
                }
                //ici on est sur qu'en modifiant l'article il ny a pas de duplicata
                damageArrivalToUpdate.setDamaArt(newArticle);
            }

        }
        //////////////////
        //si le damaDto.getDamaSidam() est null on fait rien car on peut avoir des arrivages sans facture
        /////////////////////////////////////////////////
        /**********************************************************************************
         * On verifie si cest l'article quon veut modifier et si cest le cas on se rassure
         * que le nouveau existe vraiment en BD
         */
        if(damaDto.getDamaArtDto() == null){
            log.error("The article cannot be null in the damaDto");
            throw new InvalidEntityException("L'article dans le damaDto ne peut etre null",
                    ErrorCode.DAMAGEARRIVAL_NOT_VALID);
        }
        if(damaDto.getDamaArtDto().getId() == null) {
            log.error("The id of article cannot be null in the damaDto");
            throw new InvalidEntityException("L'id de l'article dans le damaDto ne peut etre null",
                    ErrorCode.DAMAGEARRIVAL_NOT_VALID);
        }

        /*************************
         * On peut a present modifier le reste de parametre de l'arrivage sans souci
         */
        damageArrivalToUpdate.setDamaDeliveryquantity(damaDto.getDamaDeliveryquantity());
        damageArrivalToUpdate.setDamaArrivalEntryDate(damaDto.getDamaArrivalEntryDate());
        damageArrivalToUpdate.setDamaQuantityartchanged(damaDto.getDamaQuantityartchanged());

        log.info("After all verification and all the updation well prepared, we can lauch the operation");

        return DamageArrivalDto.fromEntity(damageArrivalRepository.save(damageArrivalToUpdate));
    }

    @Override
    public Boolean isDamageArrivalUnique(Long articleId, Long sidamId) {
        if(articleId == null){
            log.error("The method can't be running on null argument");
            throw new NullArgumentException("La methode ne peut s'executer sur un argument null");
        }
        if(sidamId == null){
            log.error("The method can't be running on null argument");
            throw new NullArgumentException("La methode ne peut s'executer sur un argument null");
        }

        Optional<DamageArrival> optionalDamageArrival = damageArrivalRepository.findDamArrivalByArticleAndSidam(articleId,
                sidamId);

        return optionalDamageArrival.isEmpty();
    }

    @Override
    public Boolean isDamageArrivalDeleteable(Long damaId) {
        return true;
    }

    @Override
    public Boolean deleteDamageArrivalById(Long damaId) {
        if(damaId == null){
            log.error("The method can't be running on null argument");
            throw new NullArgumentException("La methode ne peut s'executer sur un argument null");
        }
        Optional<DamageArrival> optionalDamageArrival = damageArrivalRepository.findDamageArrivalById(damaId);
        if(!optionalDamageArrival.isPresent()){
            log.error("There is no damageArrival in DB with the id {} precised as argument", damaId);
            throw new EntityNotFoundException("Aucun damageArrival n'existe avec l'Id passe en argument ",
                    ErrorCode.DAMAGEARRIVAL_NOT_FOUND);
        }
        if(!isDamageArrivalDeleteable(damaId)){
            log.error("The damarrival is not yet deleteable");
            throw new EntityNotDeleteableException("Il n'est plus possible de supprimer le damArrival ",
                    ErrorCode.DAMAGEARRIVAL_NOT_DELETEABLE);
        }
        damageArrivalRepository.delete(optionalDamageArrival.get());
        return true;
    }

    @Override
    public DamageArrivalDto findDamageArrivalById(Long damaId) {
        if(damaId == null){
            log.error("The method can't be running on null argument");
            throw new NullArgumentException("La methode ne peut s'executer sur un argument null");
        }
        Optional<DamageArrival> optionalDamArrival = damageArrivalRepository.findDamageArrivalById(damaId);
        if(!optionalDamArrival.isPresent()){
            log.error("There is no damArrival in DB with the id {} precised as argument", damaId);
            throw new EntityNotFoundException("Aucun damArrival n'existe avec l'Id passe en argument ",
                    ErrorCode.DAMAGEARRIVAL_NOT_FOUND);
        }
        return DamageArrivalDto.fromEntity(optionalDamArrival.get());
    }

    @Override
    public DamageArrivalDto findDamageArrivalofArticleinSidam(Long articleId, Long sidamId) {
        if(articleId == null){
            log.error("The method can't be running on null articleId argument");
            throw new NullArgumentException("La methode ne peut s'executer sur un argument articleId null");
        }
        //Le sidamId peut etre null car on peut avoir des arrivages sans facture
        Optional<DamageArrival> optionalDamArrival = damageArrivalRepository.findDamArrivalByArticleAndSidam(articleId,
                sidamId);
        if(!optionalDamArrival.isPresent()){
            log.error("There is no damArrival in DB for the articleId {} precised in the sidamid {} precise", articleId, sidamId);
            throw new EntityNotFoundException("Aucun damArrival n'existe avec les parametres passe en argument ",
                    ErrorCode.DAMAGEARRIVAL_NOT_FOUND);
        }
        return DamageArrivalDto.fromEntity(optionalDamArrival.get());
    }

    @Override
    public List<DamageArrivalDto> findAllDamageArrivalinSidam(Long sidamId) {
        if(sidamId == null){
            log.error("The method can't be running on null sidamId argument");
            throw new NullArgumentException("La methode ne peut s'executer sur un argument sidamId null");
        }

        Optional<List<DamageArrival>> optionalDamArrivalList = damageArrivalRepository.findAllDamArrivalinSidam(sidamId);
        if(!optionalDamArrivalList.isPresent()){
            log.error("There is no sidam in DB with the precised id {}", sidamId);
            throw new EntityNotFoundException("Aucune facture cash n'existe avec l'id envoye ",
                    ErrorCode.SUPPLYINVOICEDAMAGE_NOT_FOUND);
        }
        List<DamageArrival> damageArrivalList = optionalDamArrivalList.get();

        return damageArrivalList.stream().map(DamageArrivalDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<DamageArrivalDto> findPageDamageArrivalinSidam(Long sidamId, int pagenum, int pagesize) {
        if(sidamId == null){
            log.error("The method can't be running on null sidamId argument");
            throw new NullArgumentException("La methode ne peut s'executer sur un argument sidamId null");
        }

        Optional<Page<DamageArrival>> optionalDamArrivalPage = damageArrivalRepository.findPageDamArrivalinSidam(sidamId,
                PageRequest.of(pagenum, pagesize));
        if(!optionalDamArrivalPage.isPresent()){
            log.error("There is no sidam in DB with the precised id {}", sidamId);
            throw new EntityNotFoundException("Aucune facture cash n'existe avec l'id envoye ",
                    ErrorCode.SUPPLYINVOICEDAMAGE_NOT_FOUND);
        }
        Page<DamageArrival> damageArrivalPage = optionalDamArrivalPage.get();

        return damageArrivalPage.map(DamageArrivalDto::fromEntity);
    }

    @Override
    public List<DamageArrivalDto> findAllDamageArrivalinSidamBetween(Long sidamId, Instant startDate,
                                                                     Instant endDate) {
        if(sidamId == null){
            log.error("The method can't be running on null sidamId argument");
            throw new NullArgumentException("La methode ne peut s'executer sur un argument sidamId null");
        }

        Optional<List<DamageArrival>> optionalDamArrivalList = damageArrivalRepository.findAllDamArrivalinSidamBetween(
                sidamId, startDate, endDate);
        if(!optionalDamArrivalList.isPresent()){
            log.error("There is no sidam in DB with the precised id {}", sidamId);
            throw new EntityNotFoundException("Aucune facture cash n'existe avec l'id envoye ",
                    ErrorCode.SUPPLYINVOICEDAMAGE_NOT_FOUND);
        }
        List<DamageArrival> damageArrivalList = optionalDamArrivalList.get();

        return damageArrivalList.stream().map(DamageArrivalDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<DamageArrivalDto> findPageDamageArrivalinSidamBetween(Long sidamId, Instant startDate,
                                                                      Instant endDate, int pagenum, int pagesize) {
        if(sidamId == null){
            log.error("The method can't be running on null sidamId argument");
            throw new NullArgumentException("La methode ne peut s'executer sur un argument sidamId null");
        }

        Optional<Page<DamageArrival>> optionalDamArrivalPage = damageArrivalRepository.findPageDamArrivalinSidamBetween(
                sidamId, startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalDamArrivalPage.isPresent()){
            log.error("There is no sidam in DB with the precised id {}", sidamId);
            throw new EntityNotFoundException("Aucune facture damage n'existe avec l'id envoye ",
                    ErrorCode.SUPPLYINVOICEDAMAGE_NOT_FOUND);
        }
        Page<DamageArrival> damageArrivalPage = optionalDamArrivalPage.get();

        return damageArrivalPage.map(DamageArrivalDto::fromEntity);
    }
}
