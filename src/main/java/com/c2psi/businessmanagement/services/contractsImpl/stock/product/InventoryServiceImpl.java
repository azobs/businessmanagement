package com.c2psi.businessmanagement.services.contractsImpl.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.InventoryDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.Inventory;
import com.c2psi.businessmanagement.models.Pointofsale;
import com.c2psi.businessmanagement.repositories.pos.pos.PointofsaleRepository;
import com.c2psi.businessmanagement.repositories.stock.product.InventoryRepository;
import com.c2psi.businessmanagement.services.contracts.stock.product.InventoryService;
import com.c2psi.businessmanagement.validators.stock.product.InventoryValidator;
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

@Service(value="InventoryServiceV1")
@Slf4j
@Transactional
public class InventoryServiceImpl implements InventoryService {

    PointofsaleRepository pointofsaleRepository;
    InventoryRepository inventoryRepository;

    @Autowired
    public InventoryServiceImpl(PointofsaleRepository pointofsaleRepository, InventoryRepository inventoryRepository) {
        this.pointofsaleRepository = pointofsaleRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public InventoryDto saveInventory(InventoryDto invDto) {

        /******************************************************
         * Il faut valider l'invDto passe en parametre
         */
        List<String> errors = InventoryValidator.validate(invDto);
        if(!errors.isEmpty()){
            log.error("Entity invDto not valid {}", invDto);
            throw new InvalidEntityException("Le invDto passe en argument n'est pas valide: "+errors,
                    ErrorCode.INVENTORY_NOT_VALID, errors);
        }

        /****************************************************************
         * Se rassurer que l'id du pointofsale n'est pas null et enfin
         * quil existe vraiment en BD
         ****************************************************************/
        if(invDto.getInvPosId() == null){
            log.error("The posId associate is null");
            throw new InvalidEntityException("Le id du pointofsale associe a l'inventaire est null",
                    ErrorCode.INVENTORY_NOT_VALID);
        }
        Optional<Pointofsale> optionalPointofsale = pointofsaleRepository.findPointofsaleById(invDto.getInvPosId());
        if(!optionalPointofsale.isPresent()){
            log.error("The pointofsale is not in the DB and an inventory cannot be saved without pointofsale");
            throw new InvalidEntityException("Le pointofsale associe a l'inventaire est inexistant en BD ",
                    ErrorCode.INVENTORY_NOT_VALID);
        }
        /***********************************************************************
         * On se rassure de l'unicite de linventaire (du code) dans le Pos en BD
         */
        if(!isInventoryUniqueinPos(invDto.getInvCode(), invDto.getInvPosId())){
            log.error("There is another inventory for the pointofsale with the same code");
            throw new DuplicateEntityException("Un inventaire est deja enregistre avec le meme code pour le meme pointofsale ",
                    ErrorCode.INVENTORY_DUPLICATED);
        }

        log.error("After all verification, the invDto {} can be save in the DB without any problem", invDto);

        return InventoryDto.fromEntity(
                inventoryRepository.save(
                        InventoryDto.toEntity(invDto)
                )
        );
    }

    @Override
    public InventoryDto updateInventory(InventoryDto invDto) {

        /******************************************************
         * Il faut valider l'invDto passe en parametre
         */
        List<String> errors = InventoryValidator.validate(invDto);
        if(!errors.isEmpty()){
            log.error("Entity invDto not valid {}", invDto);
            throw new InvalidEntityException("Le invDto passe en argument n'est pas valide: "+errors,
                    ErrorCode.INVENTORY_NOT_VALID, errors);
        }

        /*******************************************
         * Se rassurer que l'invId n'est pas null
         * et quil existe vraiment en BD et si cest
         * le cas on le recupere
         */
        if(invDto.getId() == null){
            log.error("The id of the inventory to update is null");
            throw new InvalidEntityException("L'identifiant de l'Inventory a update est null ",
                    ErrorCode.INVENTORY_NOT_VALID);
        }
        //Ici on est sur que l'id n'est pas null
        Optional<Inventory> optionalInventoryToUpdate = inventoryRepository.findInventoryById(invDto.getId());
        if(!optionalInventoryToUpdate.isPresent()){
            log.error("The invId precised does not identified any Inventory in the DB");
            throw new EntityNotFoundException("Aucun Inventory n'existe en stock avec l'Id precise ",
                    ErrorCode.INVENTORY_NOT_FOUND);
        }
        //On peut donc recuperer L'Inventory a update
        Inventory inventoryToUpdate = optionalInventoryToUpdate.get();

        /****************************************************************
         * Verifier que ce n'est pas le pointofsale quon veut modifier
         */
        if(!inventoryToUpdate.getInvPosId().equals(invDto.getInvPosId())){
            log.error("There is not possible to modify the pointofsale of an Inventory");
            throw new InvalidEntityException("Il n'est pas possible de modifier un Pointofsale d'un Inventory ",
                    ErrorCode.INVENTORY_NOT_VALID);
        }

        /*****************************************************************
         * Verifier si cest le code quon veut modifier
         * et si cest le cas on se rassure qu'il y aura pas de duplicata
         * avant de update
         */
        if(!inventoryToUpdate.getInvCode().equals(invDto.getInvCode())){
            /**
             * On doit se rassurer que le code sera unique
             */
            if(!isInventoryUniqueinPos(invDto.getInvCode(), inventoryToUpdate.getInvPosId())){
                log.error("There is another inventory for the pointofsale with the same code");
                throw new DuplicateEntityException("Un inventaire est deja enregistre avec le meme code pour le meme pointofsale ",
                        ErrorCode.INVENTORY_DUPLICATED);
            }
            //Ici on est sur que la modification ne va pas creer des duplicata
            inventoryToUpdate.setInvCode(invDto.getInvCode());
        }

        /**********************************************************
         * Update le reste des attributs et lancer la mise a jour
         */
        inventoryToUpdate.setInvDate(invDto.getInvDate());
        inventoryToUpdate.setInvComment(invDto.getInvComment());
        //return ArticleDto.fromEntity(articleRepository.save(articleToUpdate));
        return InventoryDto.fromEntity(inventoryRepository.save(inventoryToUpdate));
    }

    @Override
    public Boolean isInventoryUniqueinPos(String invCode, Long posId) {
        if(posId == null){
            log.error("The posId sent cannot be null");
            throw new NullArgumentException("Le posId envoye est null");
        }

        if(!StringUtils.hasLength(invCode)){
            log.error("invCode is null but must be notnull");
            throw new NullArgumentException("le invCode passe en argument de la methode est null ou vide");
        }

        Optional<Inventory> optionalInventory = inventoryRepository.findInventoryByCodeinPos(invCode, posId);

        return optionalInventory.isPresent()?false:true;
    }

    @Override
    public InventoryDto findInventoryByCodeinPos(String invCode, Long posId) {
        if(posId == null){
            log.error("The posId sent cannot be null");
            throw new NullArgumentException("Le posId envoye est null");
        }

        if(!StringUtils.hasLength(invCode)){
            log.error("invCode is null but must be notnull");
            throw new NullArgumentException("le invCode passe en argument de la methode est null ou vide");
        }

        Optional<Inventory> optionalInventory = inventoryRepository.findInventoryByCodeinPos(invCode, posId);
        return InventoryDto.fromEntity(optionalInventory.get());
    }

    @Override
    public List<InventoryDto> findAllInventoryinPosBetween(Long posId, Instant startDate, Instant endDate) {
        if(posId == null){
            log.error("The posId sent cannot be null");
            throw new NullArgumentException("Le posId envoye est null");
        }
        Optional<List<Inventory>> optionalInventoryList = inventoryRepository.findAllInventoryinPosBetween(posId,
                startDate, endDate);
        if(!optionalInventoryList.isPresent()){
            log.error("There is no pointofsale in the database with the posId {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe en BD avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }

        return optionalInventoryList.get().stream().map(InventoryDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<InventoryDto> findPageInventoryinPosBetween(Long posId, Instant startDate, Instant endDate,
                                                            int pagenum, int pagesize) {
        if(posId == null){
            log.error("The posId sent cannot be null");
            throw new NullArgumentException("Le posId envoye est null");
        }
        Optional<Page<Inventory>> optionalInventoryPage = inventoryRepository.findPageInventoryinPosBetween(posId,
                startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalInventoryPage.isPresent()){
            log.error("There is no pointofsale in the database with the posId {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe en BD avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }

        return optionalInventoryPage.get().map(InventoryDto::fromEntity);
    }

    @Override
    public Boolean isInventoryDeleteable(Long invId) {
        return true;
    }

    @Override
    public Boolean deleteInventoryById(Long invId) {
        if(invId == null){
            log.error("The invId sent cannot be null");
            throw new NullArgumentException("Le invId envoye est null");
        }
        Optional<Inventory> optionalInventory = inventoryRepository.findInventoryById(invId);
        if(!optionalInventory.isPresent()){
            log.error("There is Inventory in the DB with the Id precise");
            throw new EntityNotFoundException("Aucun inventory n'existe dans le stock avec l'Id precise",
                    ErrorCode.INVENTORY_NOT_FOUND);
        }
        inventoryRepository.delete(optionalInventory.get());
        return true;
    }
}
