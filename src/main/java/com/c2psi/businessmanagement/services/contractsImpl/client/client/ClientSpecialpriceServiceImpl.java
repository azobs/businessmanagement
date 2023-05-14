package com.c2psi.businessmanagement.services.contractsImpl.client.client;

import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientSpecialpriceDto;
import com.c2psi.businessmanagement.dtos.stock.price.BasePriceDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.*;
import com.c2psi.businessmanagement.repositories.client.client.ClientRepository;
import com.c2psi.businessmanagement.repositories.client.client.ClientSpecialpriceRepository;
import com.c2psi.businessmanagement.repositories.stock.price.SpecialPriceRepository;
import com.c2psi.businessmanagement.repositories.stock.product.ArticleRepository;
import com.c2psi.businessmanagement.services.contracts.client.client.ClientSpecialpriceService;
import com.c2psi.businessmanagement.services.contractsImpl.UtilitiesImpl;
import com.c2psi.businessmanagement.validators.client.client.ClientSpecialpriceValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value="ClientSpecialpriceServiceV1")
@Slf4j
@Transactional
public class ClientSpecialpriceServiceImpl implements ClientSpecialpriceService {

    private ClientSpecialpriceRepository clientSpecialpriceRepository;
    private SpecialPriceRepository specialPriceRepository;
    private ArticleRepository articleRepository;
    private ClientRepository clientRepository;
    private UtilitiesImpl utilities;

    @Autowired
    public ClientSpecialpriceServiceImpl(ClientSpecialpriceRepository clientSpecialpriceRepository,
                                         SpecialPriceRepository specialPriceRepository,
                                         ArticleRepository articleRepository, ClientRepository clientRepository,
                                         UtilitiesImpl utilities) {
        this.clientSpecialpriceRepository = clientSpecialpriceRepository;
        this.specialPriceRepository = specialPriceRepository;
        this.articleRepository = articleRepository;
        this.clientRepository = clientRepository;
        this.utilities = utilities;
    }

    @Override
    public ClientSpecialpriceDto saveClientSpecialprice(ClientSpecialpriceDto cltspepriceDto) {
        /********************************************************************************************************
         * Il faut valider le dto pris en parametre pour se rassurer que la saisie des donnees a ete bien faite
         */
        List<String> errors = ClientSpecialpriceValidator.validate(cltspepriceDto);
        if(!errors.isEmpty()){
            log.error("Entity ClientspecialpriceDto not valid {}", cltspepriceDto);
            throw new InvalidEntityException("Le ClientspecialpriceDto passe en argument n'est pas valide:  ",
                    ErrorCode.CLIENTSPECIALPRICE_NOT_VALID, errors);
        }

        /***********************************************************************
         * Il faut verifier que l'Id du client nest pas null et que ce client
         * existe vraiment en BD
         */
        if(cltspepriceDto.getCltSpClientDto().getId() == null){
            log.error("The clientId in the cltspepriceDto {} passed as argument is null ", cltspepriceDto);
            throw new InvalidEntityException("Le ClientspecialpriceDto passe en argument n'est pas valide: ",
                    ErrorCode.CLIENTSPECIALPRICE_NOT_VALID, errors);
        }
        //l'id du client nest pas null et il faut donc voir si l'id non null la identifie vraiment un client
        Optional<Client> optionalClient = clientRepository.findClientById(cltspepriceDto.getCltSpClientDto().getId());
        if(!optionalClient.isPresent()){
            log.error("There is no client identified by id {} ", cltspepriceDto.getCltSpClientDto().getId());
            throw new InvalidEntityException("Aucun client n'existe en BD avec l'idclient passe en argument ",
                    ErrorCode.CLIENT_NOT_FOUND);
        }
        //A ce niveau on est sur que le client existe

        /***************************************************************************
         * Il faut verifier que l'Id de l'article nest pas null et que cet article
         * existe vraiment en BD
         */
        if(cltspepriceDto.getCltSpArtDto().getId() == null){
            log.error("The articleId in the cltspepriceDto {} passed as argument is null ", cltspepriceDto);
            throw new InvalidEntityException("Le ClientspecialpriceDto passe en argument n'est pas valide: ",
                    ErrorCode.CLIENTSPECIALPRICE_NOT_VALID, errors);
        }
        //l'id de l'article nest pas null et il faut donc voir si l'id non null la identifie vraiment un article
        Optional<Article> optionalArticle = articleRepository.findArticleById(cltspepriceDto.getCltSpArtDto().getId());
        if(!optionalArticle.isPresent()){
            log.error("There is no article identified by id {} ", cltspepriceDto.getCltSpArtDto().getId());
            throw new InvalidEntityException("Aucun article n'existe en BD avec l'idArticle passe en argument ",
                    ErrorCode.ARTICLE_NOT_FOUND);
        }
        //A ce niveau on est sur que l'article existe

        /**********************************************************************************
         * Il faut verifier que l'Id du specialprice nest pas null et que ce specialprice
         * existe vraiment en BD
         */
        if(cltspepriceDto.getCltSpPDto().getId() == null){
            log.error("The specialprice id in the cltspepriceDto {} passed as argument is null ", cltspepriceDto);
            throw new InvalidEntityException("Le ClientspecialpriceDto passe en argument n'est pas valide : ",
                    ErrorCode.CLIENTSPECIALPRICE_NOT_VALID);
        }
        //l'id du specialprice nest pas null et il faut donc voir si lid non null la identifie vraiment un specialprice
        Optional<SpecialPrice> optionalSpecialPrice = specialPriceRepository.findSpecialPriceById(
                cltspepriceDto.getCltSpPDto().getId());
        if(!optionalSpecialPrice.isPresent()){
            log.error("There is no specialprice identified by id {} ", cltspepriceDto.getCltSpPDto().getId());
            throw new InvalidEntityException("Aucun specialprice n'existe en BD avec l'idSpecialprice passe en argument ",
                    ErrorCode.SPECIALPRICE_NOT_FOUND);
        }
        //A ce niveau on est sur que le specialprice existe

        /*************************************************************************************
         * Il faut verifier que l'article et le client sont bel et bien du meme pointofsale
         */
        Long idPosArticle = optionalArticle.get().getArtPos().getId();
        Long idPosClient = optionalClient.get().getClientPos().getId();
        if(!idPosClient.equals(idPosArticle)){
            log.error("The article and the Client that will be associated by the specialprice must belong to the same " +
                    " pointofsale ");
            throw new InvalidEntityException("Le client et l'article doivent etre dans le meme pointofsale ",
                    ErrorCode.CLIENTSPECIALPRICE_NOT_VALID);
        }

        /************************************************************************************
         * Il faut se rassurer que le specialprice indique est bel et bien un specialprice
         * de l'article indique dans la transaction
         *         //Pour cela il faut recuperer le baseprice associe au specialprice
         *         //Puis recuperer le baseprice de l'article dans la transaction
         *         //Puis comparer les 2 id et il devrait etre identique sinon il y a probleme
         */
        BasePriceDto basePriceDtoofspecialprice = BasePriceDto.fromEntity(optionalSpecialPrice.get().getSpBaseprice());
        BasePriceDto basePriceDtoofArticle = BasePriceDto.fromEntity(optionalArticle.get().getArtBp());
        if(!basePriceDtoofArticle.getId().equals(basePriceDtoofspecialprice.getId())){
            log.error("The baseprice associate with the specialprice in the request {} is not the same with the " +
                    "baseprice of the article in that request {} ", basePriceDtoofspecialprice, basePriceDtoofArticle);
            throw new InvalidEntityException("Le prix de base de l'article et celui associe au prix special dans la requete " +
                    "ne sont pas les memes ", ErrorCode.CLIENTSPECIALPRICE_NOT_VALID);
        }

        /************************************************************************************************
         * Maintenant il faut verifier que cette relation client+article+specialprice n'existe pas deja *
         ************************************************************************************************/
        if(!isClientSpecialpriceUnique(optionalClient.get().getId(), optionalArticle.get().getId())){
            log.error("A specialprice is already assigned to the client for the article ");
            throw new DuplicateEntityException("A specialprice for that article is already assigned to the indicated " +
                    "client ", ErrorCode.CLIENTSPECIALPRICE_DUPLICATED);
        }

        return ClientSpecialpriceDto.fromEntity(
                clientSpecialpriceRepository.save(
                        ClientSpecialpriceDto.toEntity(cltspepriceDto)
                )
        );
    }

    @Override
    public ClientSpecialpriceDto updateClientSpecialprice(ClientSpecialpriceDto cltspepriceDto) {
        /**********************
         * Ici dans cette mise a jour on ne peut que modifier le prixspecial mais
         * ni l'article ni le client ne peut etre modifier dans cette methode
         */

        /********************************************************************************************************
         * Il faut valider le dto pris en parametre pour se rassurer que la saisie des donnees a ete bien faite
         */
        List<String> errors = ClientSpecialpriceValidator.validate(cltspepriceDto);
        if(!errors.isEmpty()){
            log.error("Entity ClientspecialpriceDto not valid {}", cltspepriceDto);
            throw new InvalidEntityException("Le ClientspecialpriceDto passe en argument n'est pas valide:  ",
                    ErrorCode.CLIENTSPECIALPRICE_NOT_VALID, errors);
        }

        if(cltspepriceDto.getId() == null){
            log.error("The id of clientspecialprice to update is null");
            throw new InvalidEntityException("Le ClientspecialpriceDto passe en argument n'est pas valide:  ",
                    ErrorCode.CLIENTSPECIALPRICE_NOT_VALID, errors);
        }
        //Il faut retrouver l'entite a modifier
        Optional<ClientSpecialprice> optionalClientSpecialprice = clientSpecialpriceRepository.
                findClientSpecialpriceById(cltspepriceDto.getId());
        if(!optionalClientSpecialprice.isPresent()){
            log.error("The entity to update is not found");
            throw new EntityNotFoundException("L'entite a modifier n'est pas existante dans la BD",
                    ErrorCode.CLIENTSPECIALPRICE_NOT_FOUND);
        }
        ClientSpecialprice clientSpecialpriceDtoToUpdate = optionalClientSpecialprice.get();

        /***********************************************************************
         * Il faut verifier que l'Id du client nest pas null et que ce client
         * existe vraiment en BD
         */
        if(cltspepriceDto.getCltSpClientDto().getId() == null){
            log.error("The clientId in the cltspepriceDto {} passed as argument is null ", cltspepriceDto);
            throw new InvalidEntityException("Le ClientspecialpriceDto passe en argument n'est pas valide: ",
                    ErrorCode.CLIENTSPECIALPRICE_NOT_VALID, errors);
        }
        //Il faut verifier que c'est pas le client quon veut modifier
        if(!cltspepriceDto.getCltSpClientDto().getId().equals(
                clientSpecialpriceDtoToUpdate.getCltSpClient().getId())){
            log.error("The client associate with the specialprice of the article cannot be modify");
            throw new InvalidEntityException("Le client ne peut etre modifier lors de la modification de l'entite " +
                    "clientspecialprice ", ErrorCode.CLIENTSPECIALPRICE_NOT_VALID);
        }
        //On a pas besoin de verifier que le client existe car si cest pas lui quon modifie alors il existe

        /***************************************************************************
         * Il faut verifier que l'Id de l'article nest pas null et que cet article
         * existe vraiment en BD
         */
        if(cltspepriceDto.getCltSpArtDto().getId() == null){
            log.error("The articleId in the cltspepriceDto {} passed as argument is null ", cltspepriceDto);
            throw new InvalidEntityException("Le ClientspecialpriceDto passe en argument n'est pas valide: ",
                    ErrorCode.CLIENTSPECIALPRICE_NOT_VALID, errors);
        }
        //Il faut verifier que c'est pas l'article quon veut modifier
        if(!cltspepriceDto.getCltSpArtDto().getId().equals(
                clientSpecialpriceDtoToUpdate.getCltSpArt().getId())){
            log.error("The article associate with the specialprice of the client cannot be modify");
            throw new InvalidEntityException("L'article ne peut etre modifier lors de la modification de l'entite " +
                    "clientspecialprice ", ErrorCode.CLIENTSPECIALPRICE_NOT_VALID);
        }
        //On a pas besoin de verifier que l'article existe car si cest pas lui quon modifie alors il existe

        /**********************************************************************************
         * Il faut verifier que l'Id du specialprice nest pas null et que ce specialprice
         * existe vraiment en BD
         */
        if(cltspepriceDto.getCltSpPDto().getId() == null){
            log.error("The specialprice id in the cltspepriceDto {} passed as argument is null ", cltspepriceDto);
            throw new InvalidEntityException("Le ClientspecialpriceDto passe en argument n'est pas valide : ",
                    ErrorCode.CLIENTSPECIALPRICE_NOT_VALID);
        }
        //l'id du specialprice nest pas null et il faut donc voir si lid non null la identifie vraiment un specialprice
        Optional<SpecialPrice> optionalSpecialPrice = specialPriceRepository.findSpecialPriceById(
                cltspepriceDto.getCltSpPDto().getId());
        if(!optionalSpecialPrice.isPresent()){
            log.error("There is no specialprice identified by id {} ", cltspepriceDto.getCltSpPDto().getId());
            throw new InvalidEntityException("Aucun specialprice n'existe en BD avec l'idSpecialprice passe en argument ",
                    ErrorCode.SPECIALPRICE_NOT_FOUND);
        }
        //A ce niveau on est sur que le specialprice existe

        clientSpecialpriceDtoToUpdate.setCltSpSp(optionalSpecialPrice.get());


        log.info("After all verification, the record {} can be done on the DB", cltspepriceDto);
        return ClientSpecialpriceDto.fromEntity(clientSpecialpriceRepository.save(clientSpecialpriceDtoToUpdate));
    }

    @Override
    public ClientSpecialpriceDto findClientSpecialpriceById(Long cltspepriceId) {
        if(cltspepriceId == null){
            log.error("The cltspepriceId passed as argument is null and for that the method can't be executed");
            throw new NullArgumentException("L'argument de la methode est null ");
        }
        Optional<ClientSpecialprice> optionalClientSpecialprice = clientSpecialpriceRepository.findClientSpecialpriceById(cltspepriceId);
        if(!optionalClientSpecialprice.isPresent()){
            log.error("There is no clientspecialprice in the DB with the id {} passed as argument ", cltspepriceId);
            throw new EntityNotFoundException("Aucun clientspecialprice n'existe en BD avec l'id envoye ", ErrorCode.CLIENTSPECIALPRICE_NOT_FOUND);
        }
        return ClientSpecialpriceDto.fromEntity(optionalClientSpecialprice.get());
    }

    @Override
    public Boolean isClientSpecialpriceUnique(Long clientId, Long articleId) {
        if(clientId == null){
            log.error("The clientId precised as argument is null");
            throw new NullArgumentException("Le clientId passe en argument est null");
        }

        if(articleId == null){
            log.error("The articleId precised as argument is null");
            throw new NullArgumentException("Le articleId passe en argument est null");
        }

        Optional<ClientSpecialprice> optionalClientSpecialprice = clientSpecialpriceRepository.
                findClientSpecialpriceofArticleforClient(clientId, articleId);

        return optionalClientSpecialprice.isEmpty();
    }

    @Override
    public ClientSpecialpriceDto findClientSpecialpriceofArticleforClient(Long articleId, Long clientId) {

        if(clientId == null){
            log.error("The clientId precised as argument is null");
            throw new NullArgumentException("Le clientId passe en argument est null");
        }

        if(articleId == null){
            log.error("The articleId precised as argument is null");
            throw new NullArgumentException("Le articleId passe en argument est null");
        }

        Optional<ClientSpecialprice> optionalClientSpecialprice = clientSpecialpriceRepository.
                findClientSpecialpriceofArticleforClient(clientId, articleId);

        if(!optionalClientSpecialprice.isPresent()){
            log.error("There is no specialprice assign to the client with the id {} for the article with the id {} " +
                    "in the DB ", clientId, articleId);
            throw new EntityNotFoundException("Aucun specialprice n'est assigne au client passe en argument pour l'article " +
                    "passe en argument ", ErrorCode.CLIENTSPECIALPRICE_NOT_FOUND);
        }

        return ClientSpecialpriceDto.fromEntity(optionalClientSpecialprice.get());
    }

    @Override
    public List<ClientSpecialpriceDto> findAllSpecialpriceofArticle(Long articleId) {

        if(articleId == null){
            log.error("There articleId passed as argument is null and the method can't be executed");
            throw new NullArgumentException("L'argument articleId passe en argument est null ");
        }

        Optional<List<ClientSpecialprice>> optionalClientSpecialpriceList = clientSpecialpriceRepository.
                findAllSpecialpriceofArticle(articleId);
        if(!optionalClientSpecialpriceList.isPresent()){
            log.error("There is no specialprice assigned for the article with the id {} passed as argument", articleId);
            throw new EntityNotFoundException("Aucun specialprice assigne a l'article precise en argument ",
                    ErrorCode.CLIENTSPECIALPRICE_NOT_FOUND);
        }

        return optionalClientSpecialpriceList.get().stream().map(ClientSpecialpriceDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<ClientSpecialpriceDto> findPageSpecialpriceofArticle(Long articleId, int pagenum, int pagesize) {

        if(articleId == null){
            log.error("There articleId passed as argument is null and the method can't be executed");
            throw new NullArgumentException("L'argument articleId passe en argument est null ");
        }

        Optional<Page<ClientSpecialprice>> optionalClientSpecialpricePage = clientSpecialpriceRepository.
                findPageSpecialpriceofArticle(articleId, PageRequest.of(pagenum, pagesize));
        if(!optionalClientSpecialpricePage.isPresent()){
            log.error("There is no specialprice assigned for the article with the id {} passed as argument", articleId);
            throw new EntityNotFoundException("Aucun specialprice assigne a l'article precise en argument ",
                    ErrorCode.CLIENTSPECIALPRICE_NOT_FOUND);
        }

        return optionalClientSpecialpricePage.get().map(ClientSpecialpriceDto::fromEntity);
    }

    @Override
    public List<ClientSpecialpriceDto> findAllSpecialpriceofClient(Long clientId) {
        if(clientId == null){
            log.error("There clientId passed as argument is null and the method can't be executed");
            throw new NullArgumentException("L'argument clientId passe en argument est null ");
        }

        Optional<List<ClientSpecialprice>> optionalClientSpecialpriceList = clientSpecialpriceRepository.
                findAllSpecialpriceofClient(clientId);
        if(!optionalClientSpecialpriceList.isPresent()){
            log.error("There is no specialprice assigned to the client with the id {} passed as argument", clientId);
            throw new EntityNotFoundException("Aucun specialprice attribue au client precise en argument ",
                    ErrorCode.CLIENTSPECIALPRICE_NOT_FOUND);
        }

        return optionalClientSpecialpriceList.get().stream().map(ClientSpecialpriceDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<ClientSpecialpriceDto> findPageSpecialpriceofClient(Long clientId, int pagenum, int pagesize) {
        if(clientId == null){
            log.error("There clientId passed as argument is null and the method can't be executed");
            throw new NullArgumentException("L'argument clientId passe en argument est null ");
        }

        Optional<Page<ClientSpecialprice>> optionalClientSpecialpricePage = clientSpecialpriceRepository.
                findPageSpecialpriceofClient(clientId, PageRequest.of(pagenum, pagesize));
        if(!optionalClientSpecialpricePage.isPresent()){
            log.error("There is no specialprice assigned to the client with the id {} passed as argument", clientId);
            throw new EntityNotFoundException("Aucun specialprice assigne au client precise en argument ",
                    ErrorCode.CLIENTSPECIALPRICE_NOT_FOUND);
        }

        return optionalClientSpecialpricePage.get().map(ClientSpecialpriceDto::fromEntity);
    }

    @Override
    public List<ClientDto> findAllClientWithSpecialpriceonArticle(Long articleId) {

        if(articleId == null){
            log.error("The articleId precise as argument is null");
            throw new NullArgumentException("L'argument de la methode ne saurait etre null");
        }

        List<ClientSpecialpriceDto> clientSpecialpriceDtoList = this.findAllSpecialpriceofArticle(articleId);
        List<ClientDto> clientDtoList = new ArrayList<>();
        for(ClientSpecialpriceDto clientSpecialpriceDto: clientSpecialpriceDtoList){
            clientDtoList.add(clientSpecialpriceDto.getCltSpClientDto());
        }

        return clientDtoList;
    }

    @Override
    public Page<ClientDto> findPageClientWithSpecialpriceonArticle(Long articleId, int pagenum, int pagesize) {

        List<ClientDto> clientDtoList = this.findAllClientWithSpecialpriceonArticle(articleId);

        Page<ClientDto> clientDtoPage = utilities.toPage(clientDtoList, PageRequest.of(pagenum, pagesize));
        return clientDtoPage;
    }

    @Override
    public BigDecimal getCommonEffectivePriceToApplied(BigDecimal qteCommand, Long articleId) {
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

    @Override
    public BigDecimal getEffectivePriceToApplied(BigDecimal qteCommand, Long articleId, Long clientId) {
        /*************************************************************
         * Verifier que les parametres envoye ne sont pas null
         */
        if(qteCommand == null || articleId == null || clientId == null){
            log.error("The articleId or the qteCommand or the clientId is null");
            throw new NullArgumentException("Un des arguments de la methode est null");
        }

        /*******************************************************
         * On doit se rassurer que la qteCommand est positive
         */
        if(qteCommand.compareTo(BigDecimal.valueOf(0))<=0){
            log.error("The quantity commanded for an article can't be negative or null");
            throw new InvalidValueException("La quantite commande pour un article ne peut etre negative ou null");
        }

        /*******************************************************************
         * Recuperer l'article dont l'id est passe en argument
         */
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

        /************************************************************
         * Recuperer le client dont l'id est passe en argument
         */
        Optional<Client> optionalClient = clientRepository.findClientById(clientId);
        if(!optionalClient.isPresent()){
            /****
             * Si le client n'existe pas on retourne le prix commun de l'article
             */
            return this.getCommonEffectivePriceToApplied(qteCommand, articleId);
        }
        //On a le client

        /******************************************************
         * On recupere le prix special associe a cel client pour cet article si il existe
         * car s'il nexiste pas alors on va simplement retourner le prix normal de l'article
         * comme un client divers
         */
        Optional<ClientSpecialprice> optionalClientSpecialprice = clientSpecialpriceRepository.
                findClientSpecialpriceofArticleforClient(clientId, articleId);
        if(!optionalClientSpecialprice.isPresent()){
            /************************
             * Aucun prix special n'est encore attribue au client trouve pour cet article
             * on retourne donc le prix commun de larticle en question
             */
            return this.getCommonEffectivePriceToApplied(qteCommand, articleId);
        }
        //Ici on a un prix special de l'article pour le client
        SpecialPrice specialPriceofClientonArticle = optionalClientSpecialprice.get().getCltSpSp();

        /****************************************************
         * si qtecommand < artLowLimitSemiWholesale alors on retourne le prix de detail
         * si qtecommand > artLowLimitSemiWholesale et < artLowLimitWholesale alors on retourne le prix de semi gros
         * si qtecommand >= artLowLimitWholesale alors on retourne le prix de gros
         ***********************************************************/
        //Si la quantite est superieur ou egale a la quantite a partir duquel on vend en gros
        if(qteCommand.compareTo(article.getArtLowLimitWholesale())>=0){
            return specialPriceofClientonArticle.getSpWholesaleprice();
        }

        //Ici on est sur que la quantite est inferieur a celle de la vente en gros.
        //On verifie donc si elle atteint ou est superieur a celle de la vente en semi gros
        if(qteCommand.compareTo(article.getArtLowLimitSemiWholesale())>=0){
            return specialPriceofClientonArticle.getSpSemiwholesaleprice();
        }
        /*
        Ici on est sur que cette quantite est inferieur a celle de la vente en semi gros donc on retourne le prix de detail
         */
        return specialPriceofClientonArticle.getSpDetailprice();
    }

    @Override
    public Boolean isClientSpecialpriceDeleteable(Long cltspepriceId) {
        return true;
    }

    @Override
    public Boolean deleteClientSpecialprice(Long cltspepriceId) {
        if(cltspepriceId == null){
            log.error("The clientDtoPage passed as argument is null");
            throw new NullArgumentException("L'argument de l'entite a modifier ne saurait etre null");
        }
        Optional<ClientSpecialprice> optionalClientSpecialprice = clientSpecialpriceRepository.
                findClientSpecialpriceById(cltspepriceId);
        if(!optionalClientSpecialprice.isPresent()){
            log.error("There is no clientspecialprice with the id {} passed as argument", cltspepriceId);
            throw new InvalidEntityException("Aucune entite clientspecialprice n'a l'id passe en argument ",
                    ErrorCode.CLIENTSPECIALPRICE_NOT_FOUND);
        }

        if(!isClientSpecialpriceDeleteable(cltspepriceId)){
           log.error("The entity clientspecialprice  can't be deleteable now due to the actual relation in the DB");
           throw new EntityNotDeleteableException("L'entite ne peut etre supprime de la base de donnee a cause des " +
                   "relations avec lui ", ErrorCode.CLIENTSPECIALPRICE_NOT_DELETEABLE);
        }
        clientSpecialpriceRepository.delete(optionalClientSpecialprice.get());
        return true;
    }
}
