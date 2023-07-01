package com.c2psi.businessmanagement.services.contractsImpl.client.command;

import com.c2psi.businessmanagement.dtos.client.command.SaleDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.Article;
import com.c2psi.businessmanagement.models.Command;
import com.c2psi.businessmanagement.models.Pointofsale;
import com.c2psi.businessmanagement.models.Sale;
import com.c2psi.businessmanagement.repositories.client.command.CommandRepository;
import com.c2psi.businessmanagement.repositories.client.command.SaleRepository;
import com.c2psi.businessmanagement.repositories.pos.pos.PointofsaleRepository;
import com.c2psi.businessmanagement.repositories.stock.product.ArticleRepository;
import com.c2psi.businessmanagement.services.contracts.client.command.SaleService;
import com.c2psi.businessmanagement.validators.client.command.SaleValidator;
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

@Service(value="SaleServiceV1")
@Slf4j
@Transactional
public class SaleServiceImpl implements SaleService {

    private CommandRepository commandRepository;
    private ArticleRepository articleRepository;
    private PointofsaleRepository pointofsaleRepository;
    private SaleRepository saleRepository;

    @Autowired
    public SaleServiceImpl(CommandRepository commandRepository, ArticleRepository articleRepository,
                           PointofsaleRepository pointofsaleRepository, SaleRepository saleRepository) {
        this.commandRepository = commandRepository;
        this.articleRepository = articleRepository;
        this.pointofsaleRepository = pointofsaleRepository;
        this.saleRepository = saleRepository;
    }

    @Override
    public SaleDto saveSale(SaleDto saleDto) {
        /*******************************************************************
         * Il faut valider le parametres avec le validateur
         */
        List<String> errors = SaleValidator.validate(saleDto);
        if(!errors.isEmpty()){
            log.error("Entity saleDto not valid {}", saleDto);
            throw new InvalidEntityException("Le saleDto passe en argument n'est pas valide:  ",
                    ErrorCode.SALE_NOT_VALID, errors);
        }

        /***************************************************************
         * Il faut se rassurer que le pointofsale id n'est pas null
         * et dans ce cas quil existe vraiment en BD
         */
        if(saleDto.getSalePosId() == null){
            log.error("The associate pointofsale id cannot be null");
            throw new InvalidEntityException("L'id du pointofsale associe ne peut etre null ",
                    ErrorCode.SALE_NOT_VALID);
        }
        Optional<Pointofsale> optionalPointofsale =  pointofsaleRepository.findPointofsaleById(saleDto.getSalePosId());
        if(!optionalPointofsale.isPresent()){
            log.error("The associate pointofsale indicated is not in the DB");
            throw new InvalidEntityException("Le pointofsale associe a la commande est inexistant de la BD",
                    ErrorCode.SALE_NOT_VALID);
        }

        /***************************************************************
         * Il faut se rassurer que le command id n'est pas null
         * et dans ce cas quil existe vraiment en BD
         */
        if(saleDto.getSaleCommandDto().getId() == null){
            log.error("The associate command if cannot be null");
            throw new InvalidEntityException("L'id du Command associe ne peut etre null", ErrorCode.SALE_NOT_VALID);
        }
        Optional<Command> optionalCommand = commandRepository.findCommandById(saleDto.getSaleCommandDto().getId());
        if(!optionalCommand.isPresent()){
            log.error("The associate command indicated is not in the DB");
            throw new InvalidEntityException("La Command associe au sale est inexistant de la BD",
                    ErrorCode.SALE_NOT_VALID);
        }

        /***************************************************************
         * Il faut se rassurer que l'article id n'est pas null
         * et dans ce cas quil existe vraiment en BD
         */
        if(saleDto.getSaleArticleDto().getId() == null){
            log.error("The associate article if cannot be null");
            throw new InvalidEntityException("L'id de Article associe ne peut etre null", ErrorCode.SALE_NOT_VALID);
        }
        Optional<Article> optionalArticle = articleRepository.findArticleById(saleDto.getSaleArticleDto().getId());
        if(!optionalArticle.isPresent()){
            log.error("The associate article indicated is not in the DB");
            throw new InvalidEntityException("L'article associe au sale est inexistant de la BD",
                    ErrorCode.SALE_NOT_VALID);
        }

        /*************************************************************
         * Il faut se rassurer que le pointofsale indique est le
         * meme que celui de l'article et que celui du command
         */
        if(!saleDto.getSalePosId().equals(saleDto.getSaleArticleDto().getArtPosId())){
            log.error("The pointofsale indicated must be the same with the one of the article associated");
            throw new InvalidEntityException("Le pointofsale indique pour le vente doit etre le meme que " +
                    "celui de l'article ", ErrorCode.SALE_NOT_VALID);
        }

        if(!saleDto.getSalePosId().equals(saleDto.getSaleCommandDto().getCmdPosId())){
            log.error("The pointofsale indicated must be the same with the one of the command associated");
            throw new InvalidEntityException("Le pointofsale indique pour le vente doit etre le meme que " +
                    "celui de la command ", ErrorCode.SALE_NOT_VALID);
        }

        /****************************************************************
         * Il faut se rassurer que le sale sera unique c'est a dire
         * quil existe aucun sale pour le meme command et le meme article
         */
        if(!isSaleUniqueInCommand(saleDto.getSaleCommandDto().getId(), saleDto.getSaleArticleDto().getId())){
            log.error("There exist in the command indicated a sale for the same article indicated");
            throw new DuplicateEntityException("Il existe deja dans la command indique une vente concernant le " +
                    "meme article ", ErrorCode.SALE_DUPLICATED);
        }

        /*******************************************************************
         * On peut donc effectuer l'enregistrement du Sale en BD
         *******************************************************************/
        log.info("After all verification the record {} can be register in the DB", saleDto);
        return SaleDto.fromEntity(
                saleRepository.save(
                        SaleDto.toEntity(saleDto)
                )
        );
    }

    @Override
    public SaleDto updateSale(SaleDto saleDto) {
        /*******************************************************************
         * Il faut valider le parametres avec le validateur
         */
        List<String> errors = SaleValidator.validate(saleDto);
        if(!errors.isEmpty()){
            log.error("Entity saleDto not valid {}", saleDto);
            throw new InvalidEntityException("Le saleDto passe en argument n'est pas valide:  ",
                    ErrorCode.SALE_NOT_VALID, errors);
        }

        /***************************************************************
         * Il faut verifier que le Sale existe et le recuperer
         */
        if(saleDto.getId() == null){
            log.error("The id of the sale to update can't be null");
            throw new InvalidEntityException("L'id du SaleDto a update ne peut etre null",
                    ErrorCode.SALE_NOT_VALID);
        }
        Optional<Sale> optionalSale = saleRepository.findSaleById(saleDto.getId());
        if(!optionalSale.isPresent()){
            log.error("There is no Sale in DB with the id precised in the request");
            throw new InvalidEntityException("Aucun sale n'existe en BD avec l'Id precise ",
                    ErrorCode.SALE_NOT_VALID);
        }
        //Ici on est sur que Sale quon veut update existe
        Sale saleToUpdate = optionalSale.get();

        /*******************************************************
         * Il faut se rassurer que ce nest pas le pointofsale
         * quon veut modifier
         */
        if(!saleToUpdate.getSalePosId().equals(saleDto.getSalePosId())){
            log.error("It is not possible to update the pointofsale of a sale in a command");
            throw new InvalidEntityException("Il n'est pas possible de update le pointofsale d'un sale dans la command ",
                    ErrorCode.SALE_NOT_VALID);
        }

        /*******************************************************
         * Il faut se rassurer que ce nest pas le command
         * quon veut modifier
         */
        if(!saleToUpdate.getSaleCommand().getId().equals(saleDto.getSaleCommandDto().getId())){
            log.error("It is not possible to update the command associated with the sale");
            throw new InvalidEntityException("Il n'est pas possible de update la command associe a la vente ",
                    ErrorCode.SALE_NOT_VALID);
        }

        /*******************************************************
         * Si c'est l'article quon veut modifier alors on se
         * rassure de l'unicite du nouveau Sale
         */
        if(!saleToUpdate.getSaleArticle().getId().equals(saleDto.getSaleArticleDto().getId())){
            /******
             * C 'est donc l'article quon veut update on verifie donc que le nouvel article
             * precise ne vas pas causer un duplicata
             */
            if(!isSaleUniqueInCommand(saleDto.getSaleCommandDto().getId(), saleDto.getSaleArticleDto().getId())){
                log.error("There exist in the command indicated a sale for the same article indicated");
                throw new DuplicateEntityException("Il existe deja dans la command indique une vente concernant le " +
                        "meme article ", ErrorCode.SALE_DUPLICATED);
            }
            //Ici on est que le nouvel article precise ne va pas causer de duplicata.
            Optional<Article> optionalnewArticle = articleRepository.findArticleById(saleDto.getSaleArticleDto().getId());
            if(!optionalnewArticle.isPresent()){
                log.error("The new article precised for the sale is not exist in the DB");
                throw new InvalidEntityException("Le nouvel article precise est inexistant en BD ",
                        ErrorCode.SALE_NOT_VALID);
            }
            saleToUpdate.setSaleArticle(optionalnewArticle.get());
        }

        /*****************************************************
         * On peut donc effectuer les update quil faut et
         * effectuer l'enregistrement
         */
        saleToUpdate.setSaleQuantity(saleDto.getSaleQuantity());
        saleToUpdate.setSaleComment(saleDto.getSaleComment());
        saleToUpdate.setSaleFinalprice(saleDto.getSaleFinalprice());
        saleToUpdate.setSaleType(saleDto.getSaleType());

        log.info("After all verification, the record {} can be done on the DB", saleDto);
        return SaleDto.fromEntity(saleRepository.save(saleToUpdate));
    }

    @Override
    public SaleDto findSaleById(Long saleId) {
        if(saleId == null){
            log.error("The saleId precised in the method cannot be null");
            throw new NullArgumentException("Le saleId precise est null");
        }
        Optional<Sale> optionalSale = saleRepository.findSaleById(saleId);
        if(!optionalSale.isPresent()){
            log.error("There is no sale in the DB with the saleId {} precised", saleId);
            throw new EntityNotFoundException("Aucun Sale n'existe en BD avec le saleId precise ",
                    ErrorCode.SALE_NOT_FOUND);
        }

        return SaleDto.fromEntity(optionalSale.get());
    }

    @Override
    public Boolean isSaleUniqueInCommand(Long cmdId, Long artId) {
        if(cmdId == null){
            log.error("The cmd id can't be null in the method");
            throw new NullArgumentException("L'argument cmdId ne peut etre null pour cette methode");
        }
        if(artId == null){
            log.error("The article id can't be null in the method");
            throw new NullArgumentException("L'argument artId ne peut etre null pour cette methode");
        }
        Optional<Sale> optionalSale = saleRepository.findSaleByCommandAndArticle(cmdId, artId);

        return optionalSale.isEmpty();
    }

    @Override
    public Boolean isSaleDeleteableInCommand(Long saleId) {
        return true;
    }

    @Override
    public SaleDto findSaleinCommandaboutArticle(Long cmdId, Long artId) {
        if(cmdId == null){
            log.error("The cmdId precised is null");
            throw new NullArgumentException("Le cmdId precise est null");
        }
        if(artId == null){
            log.error("The artId precised is null");
            throw new NullArgumentException("Le artId precise est null");
        }
        Optional<Sale> optionalSale = saleRepository.findSaleByCommandAndArticle(cmdId, artId);
        if(!optionalSale.isPresent()){
            log.error("There is no Sale that associate the command precised with the article precised");
            throw new EntityNotFoundException("Aucun Sale n'existe en BD liant la command et l'article precise ",
                    ErrorCode.SALE_NOT_FOUND);
        }

        return SaleDto.fromEntity(optionalSale.get());
    }

    @Override
    public Boolean deleteSaleById(Long saleId) {
        if(saleId == null){
            log.error("The saleId precised in the method cannot be null");
            throw new NullArgumentException("Le saleId precise est null");
        }
        Optional<Sale> optionalSale = saleRepository.findSaleById(saleId);
        if(!optionalSale.isPresent()){
            log.error("There is no sale in the DB with the saleId {} precised", saleId);
            throw new EntityNotFoundException("Aucun Sale n'existe en BD avec le saleId precise ",
                    ErrorCode.SALE_NOT_FOUND);
        }
        if(!isSaleDeleteableInCommand(saleId)){
            log.error("It is not possible to delete a Sale precise by {}", saleId);
            throw new EntityNotDeleteableException("Il n'est pas possible de supprimer le Sale indique ",
                    ErrorCode.SALE_NOT_DELETEABLE);
        }

        return true;
    }

    @Override
    public List<SaleDto> findAllSaleofCommand(Long cmdId) {
        if(cmdId == null){
            log.error("The cmdId precised in the method cannot be null");
            throw new NullArgumentException("Le saleId precise est null");
        }
        Optional<List<Sale>> optionalSaleList = saleRepository.findAllSaleofCommand(cmdId);
        if(!optionalSaleList.isPresent()){
            log.error("there is no command with the cmdId {} precised ", cmdId);
            throw new EntityNotFoundException("Aucune commande n'existe avec le cmdId precise ",
                    ErrorCode.COMMAND_NOT_FOUND);
        }

        return optionalSaleList.get().stream().map(SaleDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<SaleDto> findPageSaleofCommand(Long cmdId, int pagenum, int pagesize) {
        if(cmdId == null){
            log.error("The cmdId precised in the method cannot be null");
            throw new NullArgumentException("Le saleId precise est null");
        }
        Optional<Page<Sale>> optionalSalePage = saleRepository.findPageSaleofCommand(cmdId,
                PageRequest.of(pagenum, pagesize));
        if(!optionalSalePage.isPresent()){
            log.error("there is no command with the cmdId {} precised ", cmdId);
            throw new EntityNotFoundException("Aucune commande n'existe avec le cmdId precise ",
                    ErrorCode.COMMAND_NOT_FOUND);
        }

        return optionalSalePage.get().map(SaleDto::fromEntity);
    }

    @Override
    public List<SaleDto> findAllSaleonArticle(Long artId) {
        if(artId == null){
            log.error("The artId precised in the method cannot be null");
            throw new NullArgumentException("Le artId precise est null");
        }
        Optional<List<Sale>> optionalSaleList = saleRepository.findAllSaleonArticle(artId);
        if(!optionalSaleList.isPresent()){
            log.error("There is no command with the artId {} precised ", artId);
            throw new EntityNotFoundException("Aucune commande n'existe avec le artId precise ",
                    ErrorCode.ARTICLE_NOT_FOUND);
        }

        return optionalSaleList.get().stream().map(SaleDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<SaleDto> findPageSaleonArticle(Long artId, int pagenum, int pagesize) {

        if(artId == null){
            log.error("The artId precised in the method cannot be null");
            throw new NullArgumentException("Le artId precise est null");
        }
        Optional<Page<Sale>> optionalSalePage = saleRepository.findPageSaleonArticle(artId,
                PageRequest.of(pagenum, pagesize));
        if(!optionalSalePage.isPresent()){
            log.error("There is no command with the artId {} precised ", artId);
            throw new EntityNotFoundException("Aucune commande n'existe avec le artId precise ",
                    ErrorCode.ARTICLE_NOT_FOUND);
        }

        return optionalSalePage.get().map(SaleDto::fromEntity);
    }

    @Override
    public List<SaleDto> findAllSaleonArticleBetween(Long artId, Instant startDate, Instant endDate) {
        if(artId == null){
            log.error("The artId precised in the method cannot be null");
            throw new NullArgumentException("Le artId precise est null");
        }
        Optional<List<Sale>> optionalSaleList = saleRepository.findAllSaleonArticleBetween(artId, startDate, endDate);
        if(!optionalSaleList.isPresent()){
            log.error("There is no command with the artId {} precised ", artId);
            throw new EntityNotFoundException("Aucune commande n'existe avec le artId precise ",
                    ErrorCode.ARTICLE_NOT_FOUND);
        }

        return optionalSaleList.get().stream().map(SaleDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<SaleDto> findPageSaleonArticleBetween(Long artId, Instant startDate, Instant endDate, int pagenum,
                                                      int pagesize) {
        if(artId == null){
            log.error("The artId precised in the method cannot be null");
            throw new NullArgumentException("Le artId precise est null");
        }
        Optional<Page<Sale>> optionalSalePage = saleRepository.findPageSaleonArticleBetween(artId, startDate, endDate,
                PageRequest.of(pagenum, pagesize));
        if(!optionalSalePage.isPresent()){
            log.error("There is no command with the artId {} precised ", artId);
            throw new EntityNotFoundException("Aucune commande n'existe avec le artId precise ",
                    ErrorCode.ARTICLE_NOT_FOUND);
        }

        return optionalSalePage.get().map(SaleDto::fromEntity);
    }

}
