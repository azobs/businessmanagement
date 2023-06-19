package com.c2psi.businessmanagement.services.contractsImpl.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.InventoryLineDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.Article;
import com.c2psi.businessmanagement.models.Inventory;
import com.c2psi.businessmanagement.models.InventoryLine;
import com.c2psi.businessmanagement.repositories.stock.product.ArticleRepository;
import com.c2psi.businessmanagement.repositories.stock.product.InventoryLineRepository;
import com.c2psi.businessmanagement.repositories.stock.product.InventoryRepository;
import com.c2psi.businessmanagement.services.contracts.stock.product.InventoryLineService;
import com.c2psi.businessmanagement.validators.stock.product.InventoryLineValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value="InventoryLineServiceV1")
@Slf4j
@Transactional
public class InventoryLineServiceImpl implements InventoryLineService {

    private ArticleRepository articleRepository;
    private InventoryLineRepository invLineRepository;
    private InventoryRepository inventoryRepository;

    @Autowired
    public InventoryLineServiceImpl(ArticleRepository articleRepository, InventoryLineRepository invLineRepository,
                                    InventoryRepository inventoryRepository) {
        this.articleRepository = articleRepository;
        this.invLineRepository = invLineRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public InventoryLineDto saveInventoryLine(InventoryLineDto invlineDto) {

        /******************************************************
         * Il faut valider l'invlineDto passe en parametre
         */
        List<String> errors = InventoryLineValidator.validate(invlineDto);
        if(!errors.isEmpty()){
            log.error("Entity invLineDto not valid {}", invlineDto);
            throw new InvalidEntityException("Le invlineDto passe en argument n'est pas valide: "+errors,
                    ErrorCode.INVENTORYLINE_NOT_VALID, errors);
        }

        /**********************************************************
         * Il faut verifier que l'id de l'inventory n'est pas null
         * et si c'est le cas que cet Inventory existe bel et bien
         * dans la BD
         */
        if(invlineDto.getInvlineInvDto().getId() == null){
            log.error("Dans l'invLineDto passe en argument l'id de Linventory est null donc on ne peut pas " +
                    "savoir pour quel inventory l'inventoryline sera enregistre");
            throw new InvalidEntityException("L'id de l'inventory ne doit pas etre null", ErrorCode.INVENTORYLINE_NOT_VALID);
        }
        //A ce niveau on est sur que l'id de l'inventory n'est pas null
        Optional<Inventory> optionalInventory = inventoryRepository.findInventoryById(
                invlineDto.getInvlineInvDto().getId());
        if(!optionalInventory.isPresent()){
            log.error("The inventory precise in the request does not exist in DB");
            throw new InvalidEntityException("L'inventory precise dans la requete n'existe pas en BD",
                    ErrorCode.INVENTORYLINE_NOT_VALID);
        }
        //On recupere l'Inventory qui sera associe a l'InventoryLine
        Inventory inventoryAssociated = optionalInventory.get();
        /**********************************************************
         * Il faut verifier que l'id de l'article n'est pas null
         * et si c'est le cas que cet article existe bel et bien
         * dans la BD
         */
        if(invlineDto.getInvlineArtDto().getId() == null){
            log.error("Dans l'invLineDto passe en argument l'id de Larticle est null donc on ne peut pas " +
                    "savoir pour quel article l'inventoryline sera enregistre");
            throw new InvalidEntityException("L'id de l'article ne doit pas etre null", ErrorCode.INVENTORYLINE_NOT_VALID);
        }
        //A ce niveau on est sur que l'id de l'article n'est pas null
        Optional<Article> optionalArticle = articleRepository.findArticleById(
                invlineDto.getInvlineArtDto().getId());
        if(!optionalArticle.isPresent()){
            log.error("The article precise in the request does not exist in DB");
            throw new InvalidEntityException("L'article precise dans la requete n'existe pas en BD",
                    ErrorCode.INVENTORYLINE_NOT_VALID);
        }
        //On recupere l'Article qui sera associe a l'InventoryLine
        Article articleAssociated = optionalArticle.get();

        /*************************************************************
         * Il faut se rassurer que le pointofsale de l'id et celui de
         * l'inventory sont les memes sinon il y a probleme
         */
        if(!articleAssociated.getArtPosId().equals(inventoryAssociated.getInvPosId())){
            log.error("The article and the inventory associated in the inventoryline must belong to the same pointofsale");
            throw new InvalidEntityException("L'article et l'inventory qui seront associe dans l'inventoryline " +
                    "doivent appartenir au meme pointofsale");
        }

        /**************************************************************
         * Il faut se rassurer que l'inventoryLine sera unique c'est
         * a dire quon aura une seule ligne d'inventaire pour un
         * inventaire concernant un article
         */
        if(!isInventoryLineUniqueinInv(inventoryAssociated.getId(), articleAssociated.getId())){
            log.error("An inventoryline already exist for the article and inventory precised");
            throw new DuplicateEntityException("Une inventoryline est deja existant entre l'article et l'inventory precise ",
                    ErrorCode.INVENTORYLINE_DUPLICATED);
        }

        log.error("After all verification, the invlineDto {} can be save in the DB without any problem", invlineDto);

        return InventoryLineDto.fromEntity(
                invLineRepository.save(
                        InventoryLineDto.toEntity(invlineDto)
                )
        );
    }

    @Override
    public InventoryLineDto updateInventoryLine(InventoryLineDto invlineDto) {

        /******************************************************
         * Il faut valider l'invlineDto passe en parametre
         */
        List<String> errors = InventoryLineValidator.validate(invlineDto);
        if(!errors.isEmpty()){
            log.error("Entity invLineDto not valid {}", invlineDto);
            throw new InvalidEntityException("Le invlineDto passe en argument n'est pas valide: "+errors,
                    ErrorCode.INVENTORYLINE_NOT_VALID, errors);
        }

        /*********************************************************
         * Il faut se rassurer que l'id du l'invline est non null
         */
        if(invlineDto.getId() == null){
            log.error("The invlineDto precised has an id null and then cannot exist in the DB");
            throw new InvalidEntityException("L'identifiant de l'inventoryLine a update ne saurait etre null ",
                    ErrorCode.INVENTORYLINE_NOT_VALID);
        }
        //ici on est sur que l'id de l'invline n'est pas null
        Optional<InventoryLine> optionalInventoryLine = invLineRepository.findInventoryLineById(invlineDto.getId());
        if(!optionalInventoryLine.isPresent()){
            log.error("There is no InventoryLine in the DB with the id precised in the request");
            throw new InvalidEntityException("L'inventoryline a update n'existe pas en BD ",
                    ErrorCode.INVENTORYLINE_NOT_FOUND);
        }
        InventoryLine inventoryLineToUpdate = optionalInventoryLine.get();

        /*********************************************************************
         * On se rassure que ce n'est pas l'inventaire quon veut update
         */
        if(!inventoryLineToUpdate.getInvlineInv().getId().equals(invlineDto.getInvlineInvDto().getId())){
            log.error("It is not possible to modify the inventory in an inventoryline");
            throw new InvalidEntityException("Il n'est pas possible de modifier l'inventory d'un inventoryline ",
                    ErrorCode.INVENTORYLINE_NOT_VALID);
        }

        /**********************************************************************
         * On verifie si c'est l'article quon veut modifier et si cest le cas
         * on doit se rassurer de l'unicite dans l'inventory
         */
        if(!inventoryLineToUpdate.getInvlineArt().getId().equals(invlineDto.getInvlineArtDto().getId())){
            /*********************************************************
             * On verifie que le nouvel article precise existe en BD
             */
            Optional<Article> optionalnewArticle = articleRepository.findArticleById(invlineDto.getInvlineArtDto().getId());
            if(!optionalnewArticle.isPresent()){
                log.error("The new article precised for the inventoryline does not exist in DB");
                throw new InvalidEntityException("Le nouvel article precise pour le inventoryline est inexistant en BD ",
                        ErrorCode.INVENTORYLINE_NOT_VALID);
            }
            Article newArticle = optionalnewArticle.get();
            if(!isInventoryLineUniqueinInv(inventoryLineToUpdate.getInvlineInv().getId(),
                    invlineDto.getInvlineArtDto().getId())){
                log.error("An inventoryline already exist for the article and inventory precised");
                throw new DuplicateEntityException("Une inventoryline est deja existant entre l'article et l'inventory precise ",
                        ErrorCode.INVENTORYLINE_DUPLICATED);
            }
            //Ici on est sur qu'il y aura pas duplicata
            inventoryLineToUpdate.setInvlineArt(newArticle);
        }

        /**********************
         * Toutes les verifications etant faite on peut modifier le reste des parametres sans souci
         */
        inventoryLineToUpdate.setInvlineComment(invlineDto.getInvlineComment());
        inventoryLineToUpdate.setInvlineRealqteinstock(invlineDto.getInvlineRealqteinstock());
        inventoryLineToUpdate.setInvlineLogicqteinstock(invlineDto.getInvlineLogicqteinstock());

        return InventoryLineDto.fromEntity(invLineRepository.save(inventoryLineToUpdate));
    }

    @Override
    public Boolean isInventoryLineUniqueinInv(Long invId, Long artId) {

        if(invId == null){
            log.error("The invId sent cannot be null");
            throw new NullArgumentException("Le invId envoye est null");
        }

        if(artId == null){
            log.error("The artId sent cannot be null");
            throw new NullArgumentException("Le artId envoye est null");
        }

        Optional<InventoryLine> optionalInventoryLine = invLineRepository.findInventoryLineByArticleinInv(artId, invId);

        return optionalInventoryLine.isPresent()?false:true;
    }

    @Override
    public InventoryLineDto findInventoryLineById(Long invlineId) {
        if(invlineId == null){
            log.error("The invlineId sent cannot be null");
            throw new NullArgumentException("Le invlineId envoye est null");
        }
        Optional<InventoryLine> optionalInventoryLine = invLineRepository.findInventoryLineById(invlineId);
        if(!optionalInventoryLine.isPresent()){
            log.error("There is no inventoryline in the DB with the id precised as argument ");
            throw new EntityNotFoundException("Il n'existe pas d'inventoryline dans la BD avec l'id precise ",
                    ErrorCode.INVENTORYLINE_NOT_FOUND);
        }
        return InventoryLineDto.fromEntity(optionalInventoryLine.get());
    }

    @Override
    public InventoryLineDto findInventoryLineByArticleinInv(Long invId , Long articleId) {

        if(invId == null){
            log.error("The invId sent cannot be null");
            throw new NullArgumentException("Le invId envoye est null");
        }

        if(articleId == null){
            log.error("The articleId sent cannot be null");
            throw new NullArgumentException("Le articleId envoye est null");
        }

        Optional<InventoryLine> optionalInventoryLine = invLineRepository.findInventoryLineByArticleinInv(articleId, invId);

        return InventoryLineDto.fromEntity(optionalInventoryLine.get());
    }

    @Override
    public Boolean isInventoryLineDeleteable(Long invLineId) {
        return true;
    }

    @Override
    public Boolean deleteInventoryLineById(Long invlineId) {
        if(invlineId == null){
            log.error("The invlineId sent cannot be null");
            throw new NullArgumentException("Le invlineId envoye est null");
        }
        Optional<InventoryLine> optionalInventoryLine = invLineRepository.findInventoryLineById(invlineId);
        if(!optionalInventoryLine.isPresent()){
            log.error("An inventoryline does not exist in DB with the id {} precised ", invlineId);
            throw new EntityNotFoundException("Aucun inventoryline n'existe avec l'id precise ",
                    ErrorCode.INVENTORYLINE_NOT_FOUND);
        }

        if(!isInventoryLineDeleteable(invlineId)){
            log.error("It is not possible to delete this inventoryline ");
            throw new EntityNotDeleteableException("Il n'est pas possible de supprimer linventoryline " +
                    "precise ", ErrorCode.INVENTORY_NOT_DELETEABLE);
        }

        invLineRepository.delete(optionalInventoryLine.get());
        return true;
    }

    @Override
    public List<InventoryLineDto> findAllInventorylineofInv(Long invId) {

        if(invId == null){
            log.error("The invId sent cannot be null");
            throw new NullArgumentException("Le invId envoye est null");
        }

        Optional<List<InventoryLine>> optionalInventoryLineList = invLineRepository.findAllInventorylineofInv(invId);
        if(!optionalInventoryLineList.isPresent()){
            log.error("There is no inventory with the id {} precised as argument", invId);
            throw new EntityNotFoundException("Aucun inventory n'existe avec l'id passe en argument ",
                    ErrorCode.INVENTORY_NOT_FOUND);
        }

        return optionalInventoryLineList.get().stream().map(InventoryLineDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<InventoryLineDto> findPageInventorylineofInv(Long invId, int pagenum, int pagesize) {
        if(invId == null){
            log.error("The invId sent cannot be null");
            throw new NullArgumentException("Le invId envoye est null");
        }

        Optional<Page<InventoryLine>> optionalInventoryLinePage = invLineRepository.findPageInventorylineofInv(invId,
                PageRequest.of(pagenum, pagesize));
        if(!optionalInventoryLinePage.isPresent()){
            log.error("There is no inventory with the id {} precised as argument", invId);
            throw new EntityNotFoundException("Aucun inventory n'existe avec l'id passe en argument ",
                    ErrorCode.INVENTORY_NOT_FOUND);
        }

        return optionalInventoryLinePage.get().map(InventoryLineDto::fromEntity);
    }




}
