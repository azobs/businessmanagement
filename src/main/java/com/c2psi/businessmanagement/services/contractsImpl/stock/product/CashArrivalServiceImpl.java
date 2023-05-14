package com.c2psi.businessmanagement.services.contractsImpl.stock.product;

import com.c2psi.businessmanagement.Enumerations.CashArrivalType;
import com.c2psi.businessmanagement.dtos.stock.product.CashArrivalDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.Article;
import com.c2psi.businessmanagement.models.CashArrival;
import com.c2psi.businessmanagement.models.SupplyInvoiceCash;
import com.c2psi.businessmanagement.repositories.stock.product.ArticleRepository;
import com.c2psi.businessmanagement.repositories.stock.product.CashArrivalRepository;
import com.c2psi.businessmanagement.repositories.stock.product.SupplyInvoiceCashRepository;
import com.c2psi.businessmanagement.services.contracts.stock.product.CashArrivalService;
import com.c2psi.businessmanagement.validators.stock.product.CashArrivalValidator;
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

@Service(value="CashArrivalServiceV1")
@Slf4j
@Transactional
public class CashArrivalServiceImpl implements CashArrivalService {

    private CashArrivalRepository cashArrivalRepository;
    private ArticleRepository articleRepository;
    private SupplyInvoiceCashRepository supplyInvoiceCashRepository;

    @Autowired
    public CashArrivalServiceImpl(CashArrivalRepository cashArrivalRepository, ArticleRepository articleRepository,
                                  SupplyInvoiceCashRepository supplyInvoiceCashRepository) {
        this.cashArrivalRepository = cashArrivalRepository;
        this.articleRepository = articleRepository;
        this.supplyInvoiceCashRepository = supplyInvoiceCashRepository;
    }

    @Override
    public CashArrivalDto saveCashArrival(CashArrivalDto cashaDto) {
        /***************************************************
         * Il faut grace au validateur valider le parametre
         */
        List<String> errors = CashArrivalValidator.validate(cashaDto);
        if(!errors.isEmpty()){
            log.error("Entity cashaDto not valid {}", cashaDto);
            throw new InvalidEntityException("Le cashaDto passe en argument n'est pas valide: "+errors,
                    ErrorCode.CASHARRIVAL_NOT_VALID, errors);
        }

        /*********************************************************************
         * Il faut se rassurer que l'id de l'article associe n'est pas null
         * Et qu'il existe vraiment en BD
         */
        if(cashaDto.getCashaArtDto().getId() ==  null){
            log.error("The id of the article associated with the casharrival is null");
            throw new InvalidEntityException("L'id de l'article associe au casharrival est null",
                    ErrorCode.CASHARRIVAL_NOT_VALID);
        }
        //Ici on est sur que cet Id n'est pas null donc on doit donc le rechercher en BD
        Optional<Article> optionalArticle = articleRepository.findArticleById(cashaDto.getCashaArtDto().getId());
        if(!optionalArticle.isPresent()){
            log.error("The articleId precised in the casharrival {} does not identify any article in the DB", cashaDto);
            throw new InvalidEntityException("Dans le cashaDto passe en argument, l'id de l'article n'identifie pas " +
                    "un article existant en BD", ErrorCode.CASHARRIVAL_NOT_VALID);
        }
        //ici on est sur qu'on a l'article

        /**********************************************************************************
         * Il faut se rassurer que l'id du supplyinvoicecash (la facture) n'est pas null
         * Et quil existe vraiment en BD
         * Car en fait on peut enregistrer un arrivage sans facture meme sil est standard
         * mais si l'id est deja non nul il faut que le sicash soit dans la BD
         */
        if(cashaDto.getCashaSicashDto() !=  null) {
            //Ici on est sur que cet Id n'est pas null donc on doit donc le rechercher en BD
            Optional<SupplyInvoiceCash> optionalSupplyInvoiceCash = supplyInvoiceCashRepository.findSupplyInvoiceCashById(cashaDto.getCashaSicashDto().getId());
            if (!optionalSupplyInvoiceCash.isPresent()) {
                log.error("The sicashId precised in the casharrival {} does not identify any supplyinvoicecash " +
                        "in the DB", cashaDto);
                throw new InvalidEntityException("Dans le cashaDto passe en argument, l'id de la facture cash n'identifie pas " +
                        "une facture cash existante en BD", ErrorCode.CASHARRIVAL_NOT_VALID);
            }
            //si le sicash est precise alors
            /********************************************************************************************
             * Il faut se rassurer que le pointofsale dans le sicash est le meme que celui de l'article
             */
            if(!cashaDto.getCashaArtDto().getArtPosDto().getId().equals(cashaDto.getCashaSicashDto().getSicashPosDto().getId())){
                log.error("The article and the supplyinvoicecash must belong to the same pointofsale");
                throw new InvalidEntityException("L'article dans le cashArrival et la facture cash doivent appartenir " +
                        "au meme point de vente");
            }

            /***********************************************************************************
             * Il faut se rassurer qu'il y aura pas de duplicata cest a dire quaucun casharrival
             * na ete enregistrer pour le meme sicashid (la meme facture) et le meme article
             */
            if(!isCashArrivalUnique(cashaDto.getCashaArtDto().getId(), cashaDto.getCashaSicashDto().getId())){
                log.error("We have in the same sicash a casharrival for the same article. but only one line per article " +
                        "is allow in the sicash");
                throw new DuplicateEntityException("Un casharrival existe deja dans le sicash pour le meme article. Faut " +
                        "plutot modifier celui la car les duplicata ne sont pas permis", ErrorCode.CASHARRIVAL_NOT_DUPLICATED);
            }
        }
        //Ici on est sur qu'on a la facture cash au cas ou elle a ete precise.

        /*************************************************************************************
         * Il faut se rassurer que le type d'arrivage est l'un des type prevu par le systeme
         */
        if(cashaDto.getCashaArrivaltype().compareTo(CashArrivalType.Standard) != 0 &&
        cashaDto.getCashaArrivaltype().compareTo(CashArrivalType.Divers) != 0){
            log.error("The cashArrivalType precised in the request is not recognize by the system");
            throw new InvalidValueException("Le type d'arrivage precise dans la requete n'est pas reconu dans le systeme",
                    ErrorCode.CASHARRIVAL_NOT_VALID);
        }

        log.error("After all verification, the cashaDto {} can be save in the DB without any problem", cashaDto);

        return CashArrivalDto.fromEntity(
                cashArrivalRepository.save(
                        CashArrivalDto.toEntity(cashaDto)
                )
        );
    }

    @Override
    public CashArrivalDto updateCashArrival(CashArrivalDto cashaDto) {
        /******************************************************************
         * On valide grace au Dto le parametre passe
         */
        List<String> errors = CashArrivalValidator.validate(cashaDto);
        if (!errors.isEmpty()) {
            log.error("Entity cashaDto not valid {}", cashaDto);
            throw new InvalidEntityException("Le cashaDto passe en argument n'est pas valide: " + errors,
                    ErrorCode.CASHARRIVAL_NOT_VALID, errors);
        }

        /*******************************************************************
         * On se rassure que l'id de lentite a modifier nest pas null
         * Et quil identifie effectivement un cashaDto et a ce moment on
         * recupere l'entite a modifier
         */
        if (cashaDto.getId() == null) {
            log.error("The id of the casharrival to update is null");
            throw new InvalidEntityException("L'id du casharrival a modifier est null");
        }
        //ici on est sur quil nest pas null
        Optional<CashArrival> optionalCashArrivalToUpdate = cashArrivalRepository.findCashArrivalById(cashaDto.getId());
        if (!optionalCashArrivalToUpdate.isPresent()) {
            log.error("The casharrival sent for update does not exist in the DB");
            throw new InvalidEntityException("Il n'existe pas en BD un casharrival avec l'id passe en argument ",
                    ErrorCode.CASHARRIVAL_NOT_VALID);
        }
        CashArrival cashArrivalToUpdate = optionalCashArrivalToUpdate.get();

        /****************************************************************************
         * On se rassure que si le sicash existe deja alors on ne peut le modifier
         * mais sil n'existe pas alors on doit pouvoir le modifier en le fixant
         * On se rassure que ce n'est pas le sicash (la facture) qu'on veut modifier
         * sinon on refuse.
         */
        if(cashaDto.getCashaSicashDto() != null){
            if(cashArrivalToUpdate.getCashaSicash() != null){
                if (!cashaDto.getCashaSicashDto().getId().equals(cashArrivalToUpdate.getCashaSicash().getId())) {
                    log.error("It is not possible to modify the sicash during updating a casharrival");
                    throw new InvalidEntityException("Il n'est pas possible de modifier la facture cash d'un arrival. " +
                            "Supprimer carrement la facture en question et creer une autre", ErrorCode.CASHARRIVAL_NOT_VALID);
                }
            }
            else{
                //Alors le sicash de lentite a update etait null alors que celui de la mise a jour est non null
                if(cashaDto.getCashaSicashDto().getId() == null){
                    log.error("The sicash id to fix to update the cashDtoToUpdate is null");
                    throw new InvalidEntityException("L'id du nouveau sicash code est null ", ErrorCode.CASHARRIVAL_NOT_VALID);
                }
                Optional<SupplyInvoiceCash> optionalSupplyInvoiceCash = supplyInvoiceCashRepository.
                        findSupplyInvoiceCashById(cashaDto.getCashaSicashDto().getId());
                if (!optionalSupplyInvoiceCash.isPresent()) {
                    log.error("The sicashId precised in the casharrival {} does not identify any supplyinvoicecash in the DB",
                            cashaDto);
                    throw new InvalidEntityException("Dans le cashaDto passe en argument, l'id de la facture cash n'identifie pas " +
                            "une facture cash existante en BD", ErrorCode.CASHARRIVAL_NOT_VALID);
                }
                //On est sur que le sicash existe en BD. on peut donc le fixer sans souci sur le cashArrival
                cashArrivalToUpdate.setCashaSicash(optionalSupplyInvoiceCash.get());
            }

            if(!cashaDto.getCashaArtDto().getId().equals(cashArrivalToUpdate.getCashaArt().getId())){
                /***************************************************
                 * On recherche le nouvel article dans la BD
                 */
                Optional<Article> optionalArticle = articleRepository.findArticleById(cashaDto.getCashaArtDto().getId());
                if(!optionalArticle.isPresent()){
                    log.error("The articleId precised in the casharrival {} does not identify any article in the DB", cashaDto);
                    throw new InvalidEntityException("Dans le cashaDto passe en argument, l'id de l'article n'identifie pas " +
                            "un article existant en BD", ErrorCode.CASHARRIVAL_NOT_VALID);
                }
                Article newArticle = optionalArticle.get();
                /******
                 * Et dans ce cas on doit se rassurer de lunicite de larrivage
                 */
                if(!isCashArrivalUnique(cashaDto.getCashaArtDto().getId(), cashaDto.getCashaSicashDto().getId())){
                    log.error("There is another casharrival for the same article in the same sicash");
                    throw new DuplicateEntityException("Il existe deja un arrivage pour le meme article dans la meme " +
                            "facture cash. il faut juste la modifier pour ajuster les quantites", ErrorCode.CASHARRIVAL_NOT_DUPLICATED);
                }
                //ici on est sur qu'en modifiant l'article il ny a pas de duplicata
                cashArrivalToUpdate.setCashaArt(newArticle);
            }

        }
        //////////////////
        //si le cashaDto.getCashaSicashDto() est null on fait rien car on peut avoir des arrivages sans facture
        /////////////////////////////////////////////////

        /**********************************************************************************
         * On verifie si cest l'article quon veut modifier et si cest le cas on se rassure
         * que le nouveau existe vraiment en BD
         */
        if(cashaDto.getCashaArtDto() == null){
            log.error("The article cannot be null in the cashaDto");
            throw new InvalidEntityException("L'article dans le cashaDto ne peut etre null",
                    ErrorCode.CASHARRIVAL_NOT_VALID);
        }
        if(cashaDto.getCashaArtDto().getId() == null) {
            log.error("The id of article cannot be null in the cashaDto");
            throw new InvalidEntityException("L'id de l'article dans le cashaDto ne peut etre null",
                        ErrorCode.CASHARRIVAL_NOT_VALID);
        }


        /*************************************************************************************
         * Il faut se rassurer que le type d'arrivage est l'un des type prevu par le systeme
         */
        if(cashaDto.getCashaArrivaltype().compareTo(CashArrivalType.Standard) != 0 &&
                cashaDto.getCashaArrivaltype().compareTo(CashArrivalType.Divers) != 0){
            log.error("The cashArrivalType precised in the request is not recognize by the system");
            throw new InvalidValueException("Le type d'arrivage precise dans la requete n'est pas reconu dans le systeme",
                    ErrorCode.CASHARRIVAL_NOT_VALID);
        }

        /*************************
         * On peut a present modifier le reste de parametre de l'arrivage sans souci
         */
        cashArrivalToUpdate.setCashaArrivaltype(cashaDto.getCashaArrivaltype());
        cashArrivalToUpdate.setCashaDeliveryquantity(cashaDto.getCashaDeliveryquantity());
        cashArrivalToUpdate.setCashaUnitprice(cashaDto.getCashaUnitprice());
        cashArrivalToUpdate.setCashaArrivalEntryDate(cashaDto.getCashaArrivalEntryDate());

        log.info("After all verification and all the updation well prepared, we can lauch the operation");

        return CashArrivalDto.fromEntity(cashArrivalRepository.save(cashArrivalToUpdate));
    }

    @Override
    public Boolean isCashArrivalUnique(Long articleId, Long sicashId) {
        if(articleId == null){
            log.error("The method can't be running on null argument");
            throw new NullArgumentException("La methode ne peut s'executer sur un argument null");
        }
        if(sicashId == null){
            log.error("The method can't be running on null argument");
            throw new NullArgumentException("La methode ne peut s'executer sur un argument null");
        }

        Optional<CashArrival> optionalCashArrival = cashArrivalRepository.findCashArrivalByArticleAndSicash(articleId, sicashId);

        return optionalCashArrival.isEmpty();
    }

    @Override
    public Boolean isCashArrivalDeleteable(Long cashaId) {
        return true;
    }

    @Override
    public Boolean deleteCashArrivalById(Long cashaId) {
        if(cashaId == null){
            log.error("The method can't be running on null argument");
            throw new NullArgumentException("La methode ne peut s'executer sur un argument null");
        }
        Optional<CashArrival> optionalCashArrival = cashArrivalRepository.findCashArrivalById(cashaId);
        if(!optionalCashArrival.isPresent()){
            log.error("There is no cashArrival in DB with the id {} precised as argument", cashaId);
            throw new EntityNotFoundException("Aucun cashArrival n'existe avec l'Id passe en argument ",
                    ErrorCode.CASHARRIVAL_NOT_FOUND);
        }
        if(!isCashArrivalDeleteable(cashaId)){
            log.error("The casharrival is not yet deleteable");
            throw new EntityNotDeleteableException("Il n'est plus possible de supprimer le cashArrival ",
                    ErrorCode.CASHARRIVAL_NOT_DELETEABLE);
        }
        cashArrivalRepository.delete(optionalCashArrival.get());
        return true;
    }

    @Override
    public CashArrivalDto findCashArrivalById(Long cashaId) {
        if(cashaId == null){
            log.error("The method can't be running on null argument");
            throw new NullArgumentException("La methode ne peut s'executer sur un argument null");
        }
        Optional<CashArrival> optionalCashArrival = cashArrivalRepository.findCashArrivalById(cashaId);
        if(!optionalCashArrival.isPresent()){
            log.error("There is no cashArrival in DB with the id {} precised as argument", cashaId);
            throw new EntityNotFoundException("Aucun cashArrival n'existe avec l'Id passe en argument ",
                    ErrorCode.CASHARRIVAL_NOT_FOUND);
        }
        return CashArrivalDto.fromEntity(optionalCashArrival.get());
    }

    @Override
    public CashArrivalDto findCashArrivalofArticleinSicash(Long articleId, Long sicashId) {
        if(articleId == null){
            log.error("The method can't be running on null articleId argument");
            throw new NullArgumentException("La methode ne peut s'executer sur un argument articleId null");
        }
        //Le sicashId peut etre null car on peut avoir des arrivages sans facture
        Optional<CashArrival> optionalCashArrival = cashArrivalRepository.findCashArrivalByArticleAndSicash(articleId,
                sicashId);
        if(!optionalCashArrival.isPresent()){
            log.error("There is no cashArrival in DB for the articleId {} precised in the sicashid {} precise", articleId, sicashId);
            throw new EntityNotFoundException("Aucun cashArrival n'existe avec les parametres passe en argument ",
                    ErrorCode.CASHARRIVAL_NOT_FOUND);
        }
        return CashArrivalDto.fromEntity(optionalCashArrival.get());
    }

    @Override
    public List<CashArrivalDto> findAllCashArrivalinSicash(Long sicashId) {

        if(sicashId == null){
            log.error("The method can't be running on null sicashId argument");
            throw new NullArgumentException("La methode ne peut s'executer sur un argument sicashId null");
        }

        Optional<List<CashArrival>> optionalCashArrivalList = cashArrivalRepository.findAllCashArrivalinSicash(sicashId);
        if(!optionalCashArrivalList.isPresent()){
            log.error("There is no sicash in DB with the precised id {}", sicashId);
            throw new EntityNotFoundException("Aucune facture cash n'existe avec l'id envoye ",
                    ErrorCode.SUPPLYINVOICECASH_NOT_FOUND);
        }
        List<CashArrival> cashArrivalList = optionalCashArrivalList.get();

        return cashArrivalList.stream().map(CashArrivalDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CashArrivalDto> findPageCashArrivalinSicash(Long sicashId, int pagenum, int pagesize) {
        if(sicashId == null){
            log.error("The method can't be running on null sicashId argument");
            throw new NullArgumentException("La methode ne peut s'executer sur un argument sicashId null");
        }

        Optional<Page<CashArrival>> optionalCashArrivalPage = cashArrivalRepository.findPageCashArrivalinSicash(sicashId,
                PageRequest.of(pagenum, pagesize));
        if(!optionalCashArrivalPage.isPresent()){
            log.error("There is no sicash in DB with the precised id {}", sicashId);
            throw new EntityNotFoundException("Aucune facture cash n'existe avec l'id envoye ",
                    ErrorCode.SUPPLYINVOICECASH_NOT_FOUND);
        }
        Page<CashArrival> cashArrivalPage = optionalCashArrivalPage.get();

        return cashArrivalPage.map(CashArrivalDto::fromEntity);
    }

    @Override
    public List<CashArrivalDto> findAllCashArrivalofCashArrivalTypeinSicash(CashArrivalType cashArrivalType,
                                                                            Long sicashId) {
        if (sicashId == null) {
            log.error("The method can't be running on null sicashId argument");
            throw new NullArgumentException("La methode ne peut s'executer sur un argument sicashId null");
        }

        Optional<List<CashArrival>> optionalCashArrivalList = cashArrivalRepository.
                findAllCashArrivalofCashArrivalTypeinSicash(cashArrivalType, sicashId);
        if (!optionalCashArrivalList.isPresent()) {
            log.error("There is no sicash in DB with the precised id {}", sicashId);
            throw new EntityNotFoundException("Aucune facture cash n'existe avec l'id envoye ",
                    ErrorCode.SUPPLYINVOICECASH_NOT_FOUND);
        }
        List<CashArrival> cashArrivalList = optionalCashArrivalList.get();

        return cashArrivalList.stream().map(CashArrivalDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CashArrivalDto> findPageCashArrivalofCashArrivalTypeinSicash(CashArrivalType cashArrivalType,
                                                                             Long sicashId, int pagenum, int pagesize) {
        if(sicashId == null){
            log.error("The method can't be running on null sicashId argument");
            throw new NullArgumentException("La methode ne peut s'executer sur un argument sicashId null");
        }

        Optional<Page<CashArrival>> optionalCashArrivalPage = cashArrivalRepository.
                findPageCashArrivalofCashArrivalTypeinSicash(cashArrivalType, sicashId, PageRequest.of(pagenum, pagesize));
        if(!optionalCashArrivalPage.isPresent()){
            log.error("There is no sicash in DB with the precised id {}", sicashId);
            throw new EntityNotFoundException("Aucune facture cash n'existe avec l'id envoye ",
                    ErrorCode.SUPPLYINVOICECASH_NOT_FOUND);
        }
        Page<CashArrival> cashArrivalPage = optionalCashArrivalPage.get();

        return cashArrivalPage.map(CashArrivalDto::fromEntity);
    }

    @Override
    public List<CashArrivalDto> findAllCashArrivalinSicashBetween(Long sicashId, Instant startDate, Instant endDate) {
        if(sicashId == null){
            log.error("The method can't be running on null sicashId argument");
            throw new NullArgumentException("La methode ne peut s'executer sur un argument sicashId null");
        }

        Optional<List<CashArrival>> optionalCashArrivalList = cashArrivalRepository.findAllCashArrivalinSicashBetween(
                sicashId, startDate, endDate);
        if(!optionalCashArrivalList.isPresent()){
            log.error("There is no sicash in DB with the precised id {}", sicashId);
            throw new EntityNotFoundException("Aucune facture cash n'existe avec l'id envoye ",
                    ErrorCode.SUPPLYINVOICECASH_NOT_FOUND);
        }
        List<CashArrival> cashArrivalList = optionalCashArrivalList.get();

        return cashArrivalList.stream().map(CashArrivalDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CashArrivalDto> findPageCashArrivalinSicashBetween(Long sicashId, Instant startDate, Instant endDate,
                                                                   int pagenum, int pagesize) {
        if(sicashId == null){
            log.error("The method can't be running on null sicashId argument");
            throw new NullArgumentException("La methode ne peut s'executer sur un argument sicashId null");
        }

        Optional<Page<CashArrival>> optionalCashArrivalPage = cashArrivalRepository.findPageCashArrivalinSicashBetween(
                sicashId, startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCashArrivalPage.isPresent()){
            log.error("There is no sicash in DB with the precised id {}", sicashId);
            throw new EntityNotFoundException("Aucune facture cash n'existe avec l'id envoye ",
                    ErrorCode.SUPPLYINVOICECASH_NOT_FOUND);
        }
        Page<CashArrival> cashArrivalPage = optionalCashArrivalPage.get();

        return cashArrivalPage.map(CashArrivalDto::fromEntity);
    }

    @Override
    public List<CashArrivalDto> findAllCashArrivalofCashArrivalTypeinSicashBetween(CashArrivalType cashArrivalType,
                                                                                   Long sicashId, Instant startDate,
                                                                                   Instant endDate) {
        if (sicashId == null) {
            log.error("The method can't be running on null sicashId argument");
            throw new NullArgumentException("La methode ne peut s'executer sur un argument sicashId null");
        }

        Optional<List<CashArrival>> optionalCashArrivalList = cashArrivalRepository.
                findAllCashArrivalofCashArrivalTypeinSicashBetween(cashArrivalType, sicashId, startDate, endDate);
        if (!optionalCashArrivalList.isPresent()) {
            log.error("There is no sicash in DB with the precised id {}", sicashId);
            throw new EntityNotFoundException("Aucune facture cash n'existe avec l'id envoye ",
                    ErrorCode.SUPPLYINVOICECASH_NOT_FOUND);
        }
        List<CashArrival> cashArrivalList = optionalCashArrivalList.get();

        return cashArrivalList.stream().map(CashArrivalDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CashArrivalDto> findPageCashArrivalofCashArrivalTypeinSicashBetween(CashArrivalType cashArrivalType,
                                                                                    Long sicashId, Instant startDate,
                                                                                    Instant endDate, int pagenum,
                                                                                    int pagesize) {
        if(sicashId == null){
            log.error("The method can't be running on null sicashId argument");
            throw new NullArgumentException("La methode ne peut s'executer sur un argument sicashId null");
        }

        Optional<Page<CashArrival>> optionalCashArrivalPage = cashArrivalRepository.
                findPageCashArrivalofCashArrivalTypeinSicashBetween(cashArrivalType, sicashId, startDate, endDate,
                        PageRequest.of(pagenum, pagesize));
        if(!optionalCashArrivalPage.isPresent()){
            log.error("There is no sicash in DB with the precised id {}", sicashId);
            throw new EntityNotFoundException("Aucune facture cash n'existe avec l'id envoye ",
                    ErrorCode.SUPPLYINVOICECASH_NOT_FOUND);
        }
        Page<CashArrival> cashArrivalPage = optionalCashArrivalPage.get();

        return cashArrivalPage.map(CashArrivalDto::fromEntity);
    }
}
