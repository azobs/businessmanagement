package com.c2psi.businessmanagement.services.contractsImpl.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.*;
import com.c2psi.businessmanagement.repositories.pos.pos.PointofsaleRepository;
import com.c2psi.businessmanagement.repositories.stock.price.BasePriceRepository;
import com.c2psi.businessmanagement.repositories.stock.product.*;
import com.c2psi.businessmanagement.services.contracts.stock.product.ArticleService;
import com.c2psi.businessmanagement.validators.stock.product.ArticleValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value="ArticleServiceV1")
@Slf4j
@Transactional
public class ArticleServiceImpl implements ArticleService {

    private PointofsaleRepository posRepository;
    private ProductFormatedRepository pfRepository;
    private UnitRepository unitRepository;
    private BasePriceRepository basePriceRepository;
    private ArticleRepository articleRepository;

    @Autowired
    public ArticleServiceImpl(PointofsaleRepository posRepository, ProductFormatedRepository pfRepository,
                              UnitRepository unitRepository, BasePriceRepository basePriceRepository,
                              ArticleRepository articleRepository) {
        this.posRepository = posRepository;
        this.pfRepository = pfRepository;
        this.unitRepository = unitRepository;
        this.basePriceRepository = basePriceRepository;
        this.articleRepository = articleRepository;
    }



    @Override
    public ArticleDto fixQuantityofArticle(ArticleDto artDto) {
        /***************************************************************************
         * La nouvelle quantite en stock est precise dans le Dto passe en argument
         */

        Long artId = artDto.getId();
        //BigDecimal new_quantity = artDto.getArtQuantityinstock();
        BigDecimal new_quantity = new BigDecimal(artDto.getArtQuantityinstock(), new MathContext(2));

        if(artId == null ){
            log.error("The artId pass as argument is null");
            throw new NullArgumentException("L'id de l'article passe en argument de la methode est null");
        }

        /*****************************************************************************
         * On doit se rassurer que la quantite a fixe pour corriger le stock present de
         * l'article n'est pas negatif
         */
        if(new_quantity.intValue() < 0){
            log.error("The newQuantity of the article can't be negative");
            throw new InvalidValueException("La nouvelle quantite precise pour l'article n'est pas valide "+new_quantity,
                    ErrorCode.ARTICLE_VALUE_NOT_VALID);
        }

        /*************************************************************************
         * Il faut essayer de recuperer l'article dont la quantite veut etre fixe
         */
        Optional<Article> optionalArticleToUpdate = articleRepository.findArticleById(artId);

        /*************************************************************************
         *Si l'article en question n'existe pas alors on leve l'exception
         */
        if(!optionalArticleToUpdate.isPresent()){
            log.error("there is no article in DB with the id {}", artId);
            throw new EntityNotFoundException("Aucun article n'existe avec l'id passe en argument "+artId,
                    ErrorCode.ARTICLE_NOT_FOUND);
        }

        /******************************************************
         * Lorsque l'article existe, on le met donc a jour
         */
        optionalArticleToUpdate.get().setArtQuantityinstock(new_quantity);

        /***************************************
         * Et on retourne l'article mis a jour
         */
        return ArticleDto.fromEntity(articleRepository.save(optionalArticleToUpdate.get()));
    }

    @Override
    public ArticleDto updateUnitofArticle(ArticleDto artDto) {
        /******************************************************************
         * Les parametre de la nouvelle unite de l'article sont dans le Dto
         */
        Long artId = artDto.getId();
        Long unitId = artDto.getArtUnitDto().getId();
        if(artId == null || unitId == null){
            log.error("The artId or unitId pass as argument is null");
            throw new NullArgumentException("L'id ou unitId passe en argument de la methode est null");
        }


        /*************************************************************************
         * Il faut essayer de recuperer l'article dont la quantite veut etre fixe
         */
        Optional<Article> optionalArticleToUpdate = articleRepository.findArticleById(artId);

        /*************************************************************************
         *Si l'article en question n'existe pas alors on leve l'exception
         */
        if(!optionalArticleToUpdate.isPresent()){
            log.error("there is no article in DB with the id {}", artId);
            throw new EntityNotFoundException("Aucun article n'existe avec l'id passe en argument "+artId,
                    ErrorCode.ARTICLE_NOT_FOUND);
        }

        /*************************************************************************
         * Il faut essayer de recuperer le new unit qui sera fixe pour l'article
         */
        Optional<Unit> optionalUnitToSet = unitRepository.findUnitById(unitId);
        /*************************************************************************
         *Si le unit en question n'existe pas alors on leve l'exception
         */
        if(!optionalUnitToSet.isPresent()){
            log.error("There is no unit in DB with the id {}", unitId);
            throw new EntityNotFoundException("Aucun Unit n'existe avec l'id passe en argument "+unitId,
                    ErrorCode.UNIT_NOT_FOUND);
        }

        /*******************************************************************
         * L'article et le unit existe on va donc effectuer la mise a jour
         */
        Article articleToUpdate = optionalArticleToUpdate.get();
        Unit unitToSet = optionalUnitToSet.get();
        /**************************************************************************************
         * Il faut se rassurer que articleToUpdate et unitToSet sont dans le meme pointofsale
         */
        if(!articleToUpdate.getArtPosId().equals(unitToSet.getUnitPosId())){
            log.error("The new unit to set must belong to the same pointofsale than the article");
            throw new InvalidEntityException("Le nouveau unit a fixer doit etre dans le meme pointofsale que celui de " +
                    "l'article ", ErrorCode.ARTICLE_NOT_VALID);
        }

        articleToUpdate.setArtUnit(unitToSet);

        return ArticleDto.fromEntity(articleRepository.save(articleToUpdate));
    }

    @Override
    public ArticleDto updateBasePriceofArticle(ArticleDto artDto) {
        /******************************************************************
         * Il faut deja se rassurer que les parametres ne sont pas null
         */
        Long artId = artDto.getId();
        Long basepriceId = artDto.getArtBpDto().getId();
        if(artId == null || basepriceId == null){
            log.error("The artId or basepriceId pass as argument is null");
            throw new NullArgumentException("L'id ou basepriceId passe en argument de la methode est null");
        }

        /*************************************************************************
         * Il faut essayer de recuperer l'article dont la quantite veut etre fixe
         */
        Optional<Article> optionalArticleToUpdate = articleRepository.findArticleById(artId);

        /*************************************************************************
         *Si l'article en question n'existe pas alors on leve l'exception
         */
        if(!optionalArticleToUpdate.isPresent()){
            log.error("there is no article in DB with the id {}", artId);
            throw new EntityNotFoundException("Aucun article n'existe avec l'id passe en argument "+artId,
                    ErrorCode.ARTICLE_NOT_FOUND);
        }

        /******************************************************************************
         * Il faut essayer de recuperer le new BasePrice qui sera fixe pour l'article
         */
        Optional<BasePrice> optionalBasePriceToSet = basePriceRepository.findBasePriceById(basepriceId);
        /*************************************************************************
         *Si le BasePrice en question n'existe pas alors on leve l'exception
         */
        if(!optionalBasePriceToSet.isPresent()){
            log.error("There is no baseprice in DB with the id {}", basepriceId);
            throw new EntityNotFoundException("Aucun Baseprice n'existe avec l'id passe en argument "+basepriceId,
                    ErrorCode.BASEPRICE_NOT_FOUND);
        }

        /*******************************************************************
         * L'article et le unit existe on va donc effectuer la mise a jour
         */
        Article articleToUpdate = optionalArticleToUpdate.get();
        BasePrice basepriceToSet = optionalBasePriceToSet.get();

        articleToUpdate.setArtBp(basepriceToSet);

        return ArticleDto.fromEntity(articleRepository.save(articleToUpdate));
    }

    @Override
    public ArticleDto saveArticle(ArticleDto artDto) {
        /******************************************************
         * Il faut valider l'articleDto passe en parametre
         */
        List<String> errors = ArticleValidator.validate(artDto);
        if(!errors.isEmpty()){
            log.error("Entity articleDto not valid {}", artDto);
            throw new InvalidEntityException("Le artDto passe en argument n'est pas valide: "+errors,
                    ErrorCode.ARTICLE_NOT_VALID, errors);
        }

        /********************************************************************
         * Il faut se rassurer que le productformated existe vraiment en BD
         */
        if(artDto.getArtPfDto().getId() == null){
            log.error("The productformated id is null which make the artDto {} invalid", artDto);
            throw new InvalidEntityException("Le artDto passe en argument n'est pas valide puisque le productformated " +
                    "indique a un id null ", ErrorCode.ARTICLE_NOT_VALID);
        }
        Optional<ProductFormated> optionalProductFormated = pfRepository.findProductFormatedById(
                artDto.getArtPfDto().getId());
        if(!optionalProductFormated.isPresent()){
            log.error("There is no productformated with the id {} in the DB which make the artDto {} invalid",
                    artDto.getArtPfDto().getId(), artDto);
            throw new EntityNotFoundException("Le artDto passe en argument n'est pas valide puisque le productformated " +
                    "indique n'existe pas en BD ", ErrorCode.PRODUCTFORMATED_NOT_FOUND);
        }

        /***********************************************************************************
         * Il faut verifier que le Unit precise dans l'artDto envoye existe vraiment en BD
         */
        if(artDto.getArtUnitDto().getId() == null){
            log.error("The Unit id is null which make artDto {} invalid", artDto);
            throw new InvalidEntityException("Le artDto passe en argument n'est pas valide puisque le unit indique " +
                    "u un id null", ErrorCode.ARTICLE_NOT_VALID);
        }
        Optional<Unit> optionalUnit = unitRepository.findUnitById(artDto.getArtUnitDto().getId());
        if(!optionalUnit.isPresent()){
            log.error("There is no Unit with the id {} in the DB which make artDto {} invalid",
                    artDto.getArtUnitDto().getId(), artDto);
            throw new EntityNotFoundException("Le artDto passe en argument n'est pas valide puisque le unit indique " +
                    "n'existe pas en BD", ErrorCode.UNIT_NOT_FOUND);
        }

        /****************************************************************************************
         * Il faut verifier que le BasePrice precise dans l'artDto envoye existe vraiment en BD
         */
        if(artDto.getArtBpDto().getId() == null){
            log.error("There Baseprice id is null which make artDto {} invalid", artDto);
            throw new InvalidEntityException("Le artDto passe en argument n'est pas valide puisque le BasePrice indique " +
                    "a un id null", ErrorCode.ARTICLE_NOT_VALID);
        }
        Optional<BasePrice> optionalBasePrice = basePriceRepository.findBasePriceById(artDto.getArtBpDto().getId());
        if(!optionalBasePrice.isPresent()){
            log.error("There is no Baseprice with the id {} in the DB which make artDto {} invalid",
                    artDto.getArtBpDto().getId(), artDto);
            throw new EntityNotFoundException("Le artDto passe en argument n'est pas valide puisque le BasePrice indique " +
                    "n'existe pas en BD", ErrorCode.BASEPRICE_NOT_FOUND);
        }

        /******************************************************************************************
         * Il faut verifier que le pointofsale precise dans l'artDto envoye existe vraiment en BD
         */
        Optional<Pointofsale> optionalPointofsale = posRepository.findPointofsaleById(artDto.getArtPosId());
        if(!optionalPointofsale.isPresent()){
            log.error("There is no pointofsale with the id {} in the DB which make artDto {} invalid",
                    artDto.getArtPosId(), artDto);
            throw new InvalidEntityException("Le artDto passe en argument n'est pas valide puisque le pointofsale " +
                    " indique n'existe pas en BD", ErrorCode.ARTICLE_NOT_VALID);
        }

        /******************************************************************************
         * Il faut se rassurer que le pointofsale du unit est le meme que celui du
         * productformated indique
         */
        if(!optionalProductFormated.get().getPfProduct().getProdPosId().equals(
                optionalUnit.get().getUnitPosId())){
            log.error("The productformated and the unit must belong to the same pointofsale");
            throw new InvalidEntityException("Dans le artDto, le productfornated et le unit doivent etre dans le " +
                    "meme pointofsale ", ErrorCode.ARTICLE_NOT_VALID);
        }

        /******************************************************************************
         * Il faut se rassurer que le pointofsale indiaue est le meme que celui du
         * productformated indique
         */
        if(!optionalProductFormated.get().getPfProduct().getProdPosId().equals(
                artDto.getArtPosId())){
            log.error("The productformated must belong to the same pointofsale indicated for the article");
            throw new InvalidEntityException("Dans le artDto, le pointofsale indique doit etre le meme que le pointofsale " +
                    "du product formated ", ErrorCode.ARTICLE_NOT_VALID);
        }

        /******************************************************************************
         * Il faut se rassurer que le pointofsale indiaue est le meme que celui du
         * unit indique
         */
        if(!optionalUnit.get().getUnitPosId().equals(
                artDto.getArtPosId())){
            log.error("The unit must belong to the same pointofsale indicated for the article");
            throw new InvalidEntityException("Dans le artDto, le pointofsale indique doit etre le meme que le pointofsale " +
                    "du unit ", ErrorCode.ARTICLE_NOT_VALID);
        }

        /*********************************************************************************
         * On doit se rassurer qu'il n'y aura pas de duplication dans cet enregistrement
         * donc si cest le code quon veut modifier alors on verifie si il n'y aura pas
         * duplication
         */
        if(!isArticleUniqueInPos(artDto.getArtCode(), artDto.getArtPosId())){
            log.error("An article already exist with the same code {} in the DB ", artDto.getArtCode());
            throw new DuplicateEntityException("Le nouveau code precise identifie deja un article dans ce " +
                    "pointofsale", ErrorCode.ARTICLE_DUPLICATED);
        }

        log.error("After all verification, the artDto {} can be save in the DB without any problem", artDto);

        return ArticleDto.fromEntity(
                articleRepository.save(
                        ArticleDto.toEntity(artDto)
                )
        );
    }

    @Override
    public ArticleDto updateArticle(ArticleDto artDto) {
        /******************************************************
         * Il faut valider l'articleDto passe en parametre
         */
        List<String> errors = ArticleValidator.validate(artDto);
        if(!errors.isEmpty()){
            log.error("Entity articleDto not valid {}", artDto);
            throw new InvalidEntityException("Le artDto passe en argument n'est pas valide: "+errors,
                    ErrorCode.ARTICLE_NOT_VALID, errors);
        }

        /*********************************************************************
         * Il faut verifier que l'id de l'article a modifier existe vraiment
         */
        if(artDto.getId() ==  null){
            log.error("The id of article {} to modify is null", artDto);
            throw new InvalidEntityException("L'id de l'article a modifier est null ", ErrorCode.ARTICLE_NOT_VALID);
        }

        /************************************************************************
         * Il faut essayer de recuperer l'article a modifier
         */
        Optional<Article> optionalArticle = articleRepository.findArticleById(artDto.getId());
        if(!optionalArticle.isPresent()){
            log.error("Any article is existing with the id precise in the artDto precise as argument {}", artDto);
            throw new EntityNotFoundException("L'id de l'article a modifier ne correspond a aucun article dans la BD",
                    ErrorCode.ARTICLE_NOT_FOUND);
        }

        /****
         * A ce niveau on peut confirmer que l'article a modifier existe et quon peut le recuperer
         */
        Article articleToUpdate = optionalArticle.get();

        /***********************************************************************
         * Il faut se rassurer que c'est pas le pointofsale quon veut modifier
         */
        if(!articleToUpdate.getArtPosId().equals(artDto.getArtPosId())){
            log.error("The pointofsale of that article cannot be modify through the modification of the article ");
            throw new InvalidEntityException("On ne peut modifier le Pointofsale pendant la modification de l'article ",
                    ErrorCode.ARTICLE_NOT_VALID);
        }

        /**********************************************************************************************
         * La quantite en stock d'un article ne peut etre modifie lors de la modification de l'article
         */
        if(articleToUpdate.getArtQuantityinstock().intValue() != artDto.getArtQuantityinstock().intValue()){
            log.error("The quantity in stock of an article cannot be modify during the modification of the article");
            throw new InvalidEntityException("La quantite en stock d'un article ne peut etre modifie pendant la " +
                    "mise a jour des donnees d'un article ", ErrorCode.ARTICLE_NOT_VALID);
        }

        /*************************************************************************************************
         * On se rassure que c'est pas le Unit quon veut modifier car il y a d'autre voie pour le faire.
         * On ne devrait pas le faire lors de la modification de l'article
         */
        if(!articleToUpdate.getArtUnit().getId().equals(artDto.getArtUnitDto().getId())){
            log.error("The unit of that article cannot be modify through the modification of the article ");
            throw new InvalidEntityException("On ne peut modifier le Unit pendant la modification de l'article ",
                    ErrorCode.ARTICLE_NOT_VALID);
        }

        /**********************************************************************
         * On se rassure que c'est pas le productformated quon veut modifier
         */
        if(!articleToUpdate.getArtPf().getId().equals(artDto.getArtPfDto().getId())){
            log.error("The Productformated of that article cannot be modify through the modification of the article ");
            throw new InvalidEntityException("On ne peut modifier le ProductFormated pendant la modification de l'article ",
                    ErrorCode.ARTICLE_NOT_VALID);
        }

        /****************************************************************
         * On se rassure que c'est pas le Baseprice quon veut modifier
         */
        if(!articleToUpdate.getArtBp().getId().equals(artDto.getArtBpDto().getId())){
            log.error("The Baseprice of that article cannot be modify through the modification of the article ");
            throw new InvalidEntityException("On ne peut modifier le Baseprice pendant la modification de l'article ",
                    ErrorCode.ARTICLE_NOT_VALID);
        }

        /*********************************************************************************
         * On doit se rassurer qu'il n'y aura pas de duplication dans cet enregistrement
         * donc si cest le code quon veut modifier alors on verifie si il n'y aura pas
         * duplication
         */
        if(!articleToUpdate.getArtCode().equals(artDto.getArtCode())){
            if(!isArticleUniqueInPos(artDto.getArtCode(), artDto.getArtPosId())){
                log.error("An article already exist with the same code {} in the DB ", artDto.getArtCode());
                throw new DuplicateEntityException("Le nouveau code precise identifie deja un article dans ce " +
                        "pointofsale", ErrorCode.ARTICLE_DUPLICATED);
            }
            //Si le code ne vas pas creer de duplication alors on le modifie sans souci
            articleToUpdate.setArtCode(artDto.getArtCode());
        }
        //Tout le reste des parametres peuvent etre modifie sans souci
        articleToUpdate.setArtName(artDto.getArtName());
        articleToUpdate.setArtShortname(artDto.getArtShortname());
        articleToUpdate.setArtDescription(artDto.getArtDescription());
//        articleToUpdate.setArtThreshold(artDto.getArtThreshold());
//        articleToUpdate.setArtLowLimitWholesale(artDto.getArtLowLimitWholesale());
//        articleToUpdate.setArtLowLimitSemiWholesale(artDto.getArtLowLimitSemiWholesale());
        articleToUpdate.setArtThreshold(new BigDecimal(artDto.getArtThreshold(), new MathContext(2)));
        articleToUpdate.setArtLowLimitWholesale(new BigDecimal(artDto.getArtLowLimitWholesale(), new MathContext(2)));
        articleToUpdate.setArtLowLimitSemiWholesale(new BigDecimal(artDto.getArtLowLimitSemiWholesale(), new MathContext(2)));
        /****
        //On modifie le Unit
        articleToUpdate.setArtUnit(UnitDto.toEntity(artDto.getArtUnitDto()));
        //On modifie le Baseprice
        articleToUpdate.setArtBp(BasePriceDto.toEntity(artDto.getArtBpDto()));
        */

        return ArticleDto.fromEntity(articleRepository.save(articleToUpdate));
    }

    @Override
    public ArticleDto findArticleByCodeInPos(String artCode, Long posId) {
        if(posId == null){
            log.error("The posId sent cannot be null");
            throw new NullArgumentException("Le posId envoye est null");
        }

        if(!StringUtils.hasLength(artCode)){
            log.error("artCode is null but must be notnull");
            throw new NullArgumentException("le artCode passe en argument de la methode est null ou vide");
        }

        Optional<Article> optionalArticle = articleRepository.findArticleByArtCodeAndPos(artCode, posId);
        if(!optionalArticle.isPresent()){
            log.error("There is no article with the code {} in the pointofsale identify by the id {} ", artCode, posId);
            throw new EntityNotFoundException("Aucun article n'existe en BD avec le code indique dans le pointofsale " +
                    "indique ", ErrorCode.ARTICLE_NOT_FOUND);
        }
        return ArticleDto.fromEntity(optionalArticle.get());
    }

    @Override
    public ArticleDto findArticleById(Long artId) {
        if(artId == null){
            log.error("The artId sent cannot be null");
            throw new NullArgumentException("Le artId envoye est null");
        }

        Optional<Article> optionalArticle = articleRepository.findArticleById(artId);
        if(!optionalArticle.isPresent()){
            log.error("There is no article with the id {} in DB", artId);
            throw new EntityNotFoundException("Aucun article n'existe en BD avec l'ID " +
                    "indique ", ErrorCode.ARTICLE_NOT_FOUND);
        }
        return ArticleDto.fromEntity(optionalArticle.get());
    }

    @Override
    public Boolean isArticleUniqueInPos(String artCode, Long posId) {
        if(posId == null){
            log.error("The posId sent cannot be null");
            throw new NullArgumentException("Le posId envoye est null");
        }

        if(!StringUtils.hasLength(artCode)){
            log.error("artCode is null but must be notnull");
            throw new NullArgumentException("le artCode passe en argument de la methode est null ou vide");
        }

        Optional<Article> optionalArticle = articleRepository.findArticleByArtCodeAndPos(artCode, posId);

        return optionalArticle.isPresent()?false:true;
    }

    @Override
    public Boolean isArticleDeleteable(Long artId) {
        return true;
    }

    @Override
    public Boolean deleteArticleById(Long art_id) {
        if(art_id == null){
            log.error("The art_id sent cannot be null");
            throw new NullArgumentException("Le art_id envoye est null");
        }

        Optional<Article> optionalArticle = articleRepository.findArticleById(art_id);
        if(!optionalArticle.isPresent()){
            log.error("There is no article with the id {} in DB", art_id);
            throw new EntityNotFoundException("Aucun article n'existe en BD avec l'ID " +
                    "indique ", ErrorCode.ARTICLE_NOT_FOUND);
        }
        if(!isArticleDeleteable(art_id)){
            log.error("The article art_id can't be deleteable in the DB");
            throw new EntityNotDeleteableException("L'article d'id "+art_id+" ne peut etre supprime de la BD ",
                    ErrorCode.ARTICLE_NOT_FOUND);
        }

        articleRepository.delete(optionalArticle.get());
        return true;
    }

    @Override
    public List<ArticleDto> findAllArticleofProviderInPos(Long posId, Long providerId) {return null;}

    @Override
    public Page<ArticleDto> findPageArticleofProviderInPos(Long posId, Long providerId, int pagenum, int pagesize) {
        return null;
    }

    @Override
    public List<ArticleDto> findAllArticleofPos(Long posId) {
        /*********************************************************
         * On verifie d'abord si l'id du pointofsale est null
         */
        if(posId == null){
            log.error("The posId precise as argument is null ");
            throw new NullArgumentException("L'argument passe est null");
        }

        /******************************************************************
         * On essaye de recuperer la liste des articles du pointofsale
         */
        Optional<List<Article>> optionalArticleList = articleRepository.findAllArticleofPos(posId);

        /******************************************************************************
         * Si cette liste n'existe pas alors cest le pointofsale qui n'existe pas
         */
        if(!optionalArticleList.isPresent()){
            log.error("There is no pointofsale with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise "+posId,
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }

        /****************************************
         * On retourne donc la liste d'article
         */
        return optionalArticleList.get().stream().map(ArticleDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<ArticleDto> findPageArticleofPos(Long posId, int pagenum, int pagesize) {
        /*********************************************************
         * On verifie d'abord si l'id du pointofsale est null
         */
        if(posId == null){
            log.error("The posId precise as argument is null ");
            throw new NullArgumentException("L'argument passe est null");
        }

        /***********************************************************************
         * On recupere donc page par page la liste des articles du pointofsale
         */

        Optional<Page<Article>> optionalArticlePage = articleRepository.findPageArticleofPos(posId,
                PageRequest.of(pagenum, pagesize, Sort.by(Sort.Direction.ASC, "artName")));

        /******************************************************
         *On verifie si les page recherche existe vraiment
         */
        if(!optionalArticlePage.isPresent()){
            log.error("There is no pointofsale with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise "+posId,
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }

        /******************************************
         * On retourne donc la page des articles
         */
        return optionalArticlePage.get().map(ArticleDto::fromEntity);
    }

    @Override
    public List<ArticleDto> findAllArticleofPosOrderByCreationDate(Long posId) {
        /*********************************************************
         * On verifie d'abord si l'id du pointofsale est null
         */
        if(posId == null){
            log.error("The posId precise as argument is null ");
            throw new NullArgumentException("L'argument passe est null");
        }

        /******************************************************************
         * On essaye de recuperer la liste des articles du pointofsale
         */
        Optional<List<Article>> optionalArticleList = articleRepository.findAllArticleofPosOrderByCreationDate(posId);

        /******************************************************************************
         * Si cette liste n'existe pas alors cest le pointofsale qui n'existe pas
         */
        if(!optionalArticleList.isPresent()){
            log.error("There is no pointofsale with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise "+posId,
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }

        /****************************************
         * On retourne donc la liste d'article
         */
        return optionalArticleList.get().stream().map(ArticleDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<ArticleDto> findPageArticleofPosOrderByCreationDate(Long posId, int pagenum, int pagesize) {
        /*********************************************************
         * On verifie d'abord si l'id du pointofsale est null
         */
        if(posId == null){
            log.error("The posId precise as argument is null ");
            throw new NullArgumentException("L'argument passe est null");
        }

        /***********************************************************************
         * On recupere donc page par page la liste des articles du pointofsale
         */

        Optional<Page<Article>> optionalArticlePage = articleRepository.findPageArticleofPosOrderByCreationDate(posId,
                PageRequest.of(pagenum, pagesize, Sort.by(Sort.Direction.ASC, "artName")));

        /******************************************************
         *On verifie si les page recherche existe vraiment
         */
        if(!optionalArticlePage.isPresent()){
            log.error("There is no pointofsale with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise "+posId,
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }

        /******************************************
         * On retourne donc la page des articles
         */
        return optionalArticlePage.get().map(ArticleDto::fromEntity);
    }

    @Override
    public List<ArticleDto> findAllArticleofCat(Long catId) {
        /*********************************************************
         * On verifie d'abord si l'id de la category est null
         */
        if(catId == null){
            log.error("The catId precise as argument is null ");
            throw new NullArgumentException("L'argument passe est null");
        }

        /******************************************************************
         * On essaye de recuperer la liste des articles du category
         */
        Optional<List<Article>> optionalArticleList = articleRepository.findAllArticleofCat(catId);

        /******************************************************************************
         * Si cette liste n'existe pas alors cest le category qui n'existe pas
         */
        if(!optionalArticleList.isPresent()){
            log.error("There is no category with the precised id {}", catId);
            throw new EntityNotFoundException("Aucun category n'existe avec l'id precise "+catId,
                    ErrorCode.CATEGORY_NOT_FOUND);
        }

        /****************************************
         * On retourne donc la liste d'article
         */
        return optionalArticleList.get().stream().map(ArticleDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<ArticleDto> findPageArticleofCat(Long catId, int pagenum, int pagesize) {
        /*********************************************************
         * On verifie d'abord si l'id du category est null
         */
        if(catId == null){
            log.error("The catId precise as argument is null ");
            throw new NullArgumentException("L'argument passe est null");
        }

        /***********************************************************************
         * On recupere donc page par page la liste des articles du category
         */

        Optional<Page<Article>> optionalArticlePage = articleRepository.findPageArticleofCat(catId,
                PageRequest.of(pagenum, pagesize, Sort.by(Sort.Direction.ASC, "artName")));

        /******************************************************
         *On verifie si les page recherche existe vraiment
         */
        if(!optionalArticlePage.isPresent()){
            log.error("There is no category with the precised id {}", catId);
            throw new EntityNotFoundException("Aucun category n'existe avec l'id precise "+catId,
                    ErrorCode.CATEGORY_NOT_FOUND);
        }

        /******************************************
         * On retourne donc la page des articles
         */
        return optionalArticlePage.get().map(ArticleDto::fromEntity);
    }

    @Override
    public Page<ArticleDto> findPageArticleofPointofsaleContaining(Long posId, String motif, int pagenum, int pagesize) {
        /*********************************************************
         * On verifie d'abord si l'id du pointofsale est null
         */
        if(posId == null){
            log.error("The posId precise as argument is null ");
            throw new NullArgumentException("L'argument passe est null");
        }

        /***********************************************************************
         * On recupere donc page par page la liste des articles du pointofsale
         */

        Optional<Page<Article>> optionalArticlePage = articleRepository.findAllByArtNameInPosContaining(posId, motif,
                PageRequest.of(pagenum, pagesize, Sort.by(Sort.Direction.ASC, "artName")));

        /******************************************************
         *On verifie si les page recherche existe vraiment
         */
        if(!optionalArticlePage.isPresent()){
            log.error("There is no pointofsale with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise "+posId,
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }

        /******************************************
         * On retourne donc la page des articles
         */
        return optionalArticlePage.get().map(ArticleDto::fromEntity);
    }



    @Override
    public ArticleDto addQuantityofArticle(Long artId, BigDecimal quantityToAdd) {
        if(artId == null){
            log.error("The artId sent cannot be null");
            throw new NullArgumentException("Le artId envoye est null");
        }
        /*****************************
         * Il faut se rassurer que la quantite a ajouter est positive
         */
        if(quantityToAdd.compareTo(BigDecimal.valueOf(0)) < 0){
            log.error("The value to add must be positive {}", quantityToAdd);
            throw new InvalidValueException("La quantite de stock a ajouter ne peut etre negative ");
        }

        Optional<Article> optionalArticle = articleRepository.findArticleById(artId);
        if(!optionalArticle.isPresent()){
            log.error("There is no article with the id {} in DB", artId);
            throw new EntityNotFoundException("Aucun article n'existe en BD avec l'ID " +
                    "indique ", ErrorCode.ARTICLE_NOT_FOUND);
        }

        Article articleToSetQuantity = optionalArticle.get();
        BigDecimal qteInStock = articleToSetQuantity.getArtQuantityinstock();
        BigDecimal qteInStockUpdated = qteInStock.add(quantityToAdd);
        articleToSetQuantity.setArtQuantityinstock(qteInStockUpdated);

        return ArticleDto.fromEntity(articleRepository.save(articleToSetQuantity));
    }

    @Override
    public ArticleDto reduceQuantityofArticle(Long artId, BigDecimal quantityToReduce) {
        if(artId == null){
            log.error("The artId sent cannot be null");
            throw new NullArgumentException("Le artId envoye est null");
        }

        /*****************************
         * Il faut se rassurer que la quantite a ajouter est positive
         */
        if(quantityToReduce.compareTo(BigDecimal.valueOf(0)) < 0){
            log.error("The value to reduce must be positive {}", quantityToReduce);
            throw new InvalidValueException("La quantite de stock a reduire ne peut etre negative ");
        }

        Optional<Article> optionalArticle = articleRepository.findArticleById(artId);
        if(!optionalArticle.isPresent()){
            log.error("There is no article with the id {} in DB", artId);
            throw new EntityNotFoundException("Aucun article n'existe en BD avec l'ID " +
                    "indique ", ErrorCode.ARTICLE_NOT_FOUND);
        }

        Article articleToSetQuantity = optionalArticle.get();
        BigDecimal qteInStock = articleToSetQuantity.getArtQuantityinstock();
        BigDecimal qteInStockUpdated = qteInStock.subtract(quantityToReduce);
        articleToSetQuantity.setArtQuantityinstock(qteInStockUpdated);

        return ArticleDto.fromEntity(articleRepository.save(articleToSetQuantity));
    }

    @Override
    public ArticleDto addDamageArticleof(Long artId, BigDecimal qteToAdd) {
        return null;
    }

    @Override
    public ArticleDto reduceDamageArticle(Long artId, BigDecimal qteToReduce) {
        return null;
    }

    @Override
    public BigDecimal getCommonEffectivePriceToApplied(Long articleId, BigDecimal qteCommand) {
        /**************************************************
         * Verifier que les parametres ne sont pas null
         */
        if(qteCommand == null || articleId == null){
            log.error("The articleId or the qteCommand is null");
            throw new NullArgumentException("Un des arguments de la methodes est null");
        }

        /*******************************************************
         * On doit se rassurer que la qteCommand est positive
         */
        if(qteCommand.compareTo(BigDecimal.valueOf(0))<=0){
            log.error("The quantity commanded for an article can't be negative or null");
            throw new InvalidValueException("La quantite commande pour un article ne peut etre negative ou null");
        }

        /*************************************************************
         * On va recuperer l'article dont le prix sera calcule
         */
        Optional<Article> optionalArticle = articleRepository.findArticleById(articleId);
        if(!optionalArticle.isPresent()){
            log.error("There is no article identified with the id {} passed as argument", articleId);
            throw new EntityNotFoundException("Aucun article n'est identifie en BD avec l'id passe en argument ",
                    ErrorCode.ARTICLE_NOT_FOUND);
        }
        //On a l'article
        Article article = optionalArticle.get();

        /******************************
         * A partir de l'article on va recuperer le prixdebase
         */
        BasePrice basePriceofArticle = article.getArtBp();

        /****************************************************
         * si qtecommand < artLowLimitSemiWholesale alors on retourne le prix de detail
         * si qtecommand > artLowLimitSemiWholesale et < artLowLimitWholesale alors on retourne le prix de semi gros
         * si qtecommand >= artLowLimitWholesale alors on retourne le prix de gros
         ***********************************************************/
        //Si la quantite est superieur ou egale a la quantite a partir duquel on vend en gros
        if(qteCommand.compareTo(article.getArtLowLimitWholesale())>=0){
            return basePriceofArticle.getBpWholesaleprice();
        }

        //Ici on est sur que la quantite est inferieur a celle de la vente en gros.
        //On verifie donc si elle atteint ou est superieur a celle de la vente en semi gros
        if(qteCommand.compareTo(article.getArtLowLimitSemiWholesale())>=0){
            return basePriceofArticle.getBpSemiwholesaleprice();
        }
        /*
        Ici on est sur que cette quantite est inferieur a celle de la vente en semi gros donc on retourne le prix de detail
         */
        return basePriceofArticle.getBpDetailprice();
    }







    /*@Override
    public Page<ArticleDto> findPageArticleOfEnterpriseContaining(Long entId, String motif, int pagenum, int pagesize) {*/
    /*********************************************************
     * On verifie d'abord si l'id du pointofsale est null
     */
        /*if(entId == null){
            log.error("The entId precise as argument is null ");
            throw new NullArgumentException("L'argument passe est null");
        }*/

    /***********************************************************************
     * On recupere donc page par page la liste des articles du pointofsale
     */

        /*Optional<Page<Article>> optionalArticlePage = articleRepository.findAllByArtNameInEntContaining(entId, motif,
                PageRequest.of(pagenum, pagesize, Sort.by(Sort.Direction.ASC, "artName")));*/

    /******************************************************
     *On verifie si les page recherche existe vraiment
     */
        /*if(!optionalArticlePage.isPresent()){
            log.error("There is no enterprise with the precised id {}", entId);
            throw new EntityNotFoundException("Aucune enterprise n'existe avec l'id precise "+entId,
                    ErrorCode.ENTERPRISE_NOT_FOUND);
        }*/

    /******************************************
     * On retourne donc la page des produits
     */
        /*return optionalArticlePage.get().map(ArticleDto::fromEntity);
    }*/

    /*@Override
    public List<ArticleDto> findAllArticleofEnterprise(Long entId) {*/
    /*********************************************************
     * On verifie d'abord si l'id de l'enterprise est null
     */
        /*if(entId == null){
            log.error("The entId precise as argument is null ");
            throw new NullArgumentException("L'argument passe est null");
        }*/

    /******************************************************************
     * On essaye de recuperer la liste des articles de l'enterprise
     */
    /*Optional<List<Article>> optionalArticleList = articleRepository.findAllArticleofEnterprise(entId);*/

    /******************************************************************************
     * Si cette liste n'existe pas alors cest le enterprise qui n'existe pas
     */
        /*if(!optionalArticleList.isPresent()){
            log.error("There is no enterprise with the precised id {}", entId);
            throw new EntityNotFoundException("Aucune enterprise n'existe avec l'id precise "+entId,
                    ErrorCode.ENTERPRISE_NOT_FOUND);
        }*/

    /****************************************
     * On retourne donc la page d'article
     */
        /*return optionalArticleList.get().stream().map(ArticleDto::fromEntity).collect(Collectors.toList());
    }*/

    /*@Override
    public Page<ArticleDto> findPageofArticleofEnterprise(Long entId, int pagenum, int pagesize) {*/

    /*********************************************************
     * On verifie d'abord si l'id du enterprise est null
     */
        /*if(entId == null){
            log.error("The entId precise as argument is null ");
            throw new NullArgumentException("L'argument passe est null");
        }*/

    /***********************************************************************
     * On recupere donc page par page la liste des articles du enterprise
     */

        /*Optional<Page<Article>> optionalArticlePage = articleRepository.findPageArticleofEnterprise(entId,
                PageRequest.of(pagenum, pagesize, Sort.by(Sort.Direction.ASC, "artName")));*/
    /******************************************************
     *On verifie si les page recherche existe vraiment
     */
        /*if(!optionalArticlePage.isPresent()){
            log.error("There is no enterprise with the precised id {}", entId);
            throw new EntityNotFoundException("Aucun enterprise n'existe avec l'id precise "+entId,
                    ErrorCode.ENTERPRISE_NOT_FOUND);
        }*/

    /******************************************
     * On retourne donc la page des produits
     */
        /*return optionalArticlePage.get().map(ArticleDto::fromEntity);
    }*/

}
