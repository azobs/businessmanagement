package com.c2psi.businessmanagement.services.contractsImpl.pos.loading;

import com.c2psi.businessmanagement.dtos.pos.loading.LoadingDetailsDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.Article;
import com.c2psi.businessmanagement.models.Loading;
import com.c2psi.businessmanagement.models.LoadingDetails;
import com.c2psi.businessmanagement.repositories.pos.loading.LoadingDetailsRepository;
import com.c2psi.businessmanagement.repositories.pos.loading.LoadingRepository;
import com.c2psi.businessmanagement.repositories.stock.product.ArticleRepository;
import com.c2psi.businessmanagement.services.contracts.pos.loading.LoadingDetailsService;
import com.c2psi.businessmanagement.validators.pos.loading.LoadingDetailsValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value="LoadingDetailsServiceV1")
@Slf4j
@Transactional
public class LoadingDetailsServiceImpl implements LoadingDetailsService {

    private ArticleRepository articleRepository;
    private LoadingRepository loadingRepository;
    private LoadingDetailsRepository loadingDetailsRepository;

    @Autowired
    public LoadingDetailsServiceImpl(ArticleRepository articleRepository, LoadingRepository loadingRepository,
                                     LoadingDetailsRepository loadingDetailsRepository) {
        this.articleRepository = articleRepository;
        this.loadingRepository = loadingRepository;
        this.loadingDetailsRepository = loadingDetailsRepository;
    }

    @Override
    public LoadingDetailsDto saveLoadingDetails(LoadingDetailsDto ldDto) {
        /***************************************************
         * IL faut valider le parametre pris en argument
         */
        List<String> errors = LoadingDetailsValidator.validate(ldDto);
        if(!errors.isEmpty()){
            log.error("Entity loadingDetailsDto not valid {}", ldDto);
            throw new InvalidEntityException("Le loadingDetailsDto passe en argument n'est pas valide:  ",
                    ErrorCode.LOADINGDETAILS_NOT_VALID, errors);
        }

        /*******************************************************
         * On se rassure que l'article existe bel et bien en BD
         */
        if(ldDto.getLdXArticleDto().getId() == null){
            log.error("The article id associated is null");
            throw new InvalidEntityException("L'id de l'article associe au LoadingDetails est null",
                    ErrorCode.LOADINGDETAILS_NOT_VALID);
        }
        //Ici on est sur que l'id de l'article a ete precise mais est ce que ca existe en BD
        Optional<Article> optionalArticle = articleRepository.findArticleById(ldDto.getLdXArticleDto().getId());
        if(!optionalArticle.isPresent()){
            log.error("There is no article with the id {} precise ", ldDto.getLdXArticleDto().getId());
            throw new InvalidEntityException("Aucun article n'existe en BD avec l'id precise ",
                    ErrorCode.LOADINGDETAILS_NOT_VALID);
        }
        //ici on est sur l'article existe bel et bien dans la BD

        /***********************************************************
         * On se rassure que le loading existe bel et bien en BD
         */
        if(ldDto.getLdLoadingDto().getId() == null){
            log.error("The loading id associated is null");
            throw new InvalidEntityException("L'id du loading associe au LoadingDetails est null",
                    ErrorCode.LOADINGDETAILS_NOT_VALID);
        }
        //Ici on est sur que l'id du loading a ete precise mais est ce que ca existe en BD
        Optional<Loading> optionalLoading = loadingRepository.findLoadingById(ldDto.getLdLoadingDto().getId());
        if(!optionalLoading.isPresent()){
            log.error("There is no loading with the id {} precise ", ldDto.getLdLoadingDto().getId());
            throw new InvalidEntityException("Aucun loading n'existe en BD avec l'id precise ",
                    ErrorCode.LOADINGDETAILS_NOT_VALID);
        }
        //Ici on est sur que le loading existe bel et bien en BD

        /***************************************************
         * On se rassure qu'aucun loadingdetails ne concerne
         * l'article indiaue dans le loading
         */
        if(!isLoadingDetailsUniqueinLoading(ldDto.getLdXArticleDto().getId(), ldDto.getLdLoadingDto().getId())){
            log.error("It already exist in the loading precise a details concerned the article precised");
            throw new DuplicateEntityException("Il existe deja un details concernant l'article indique dans le " +
                    "loading indique ", ErrorCode.LOADINGDETAILS_DUPLICATED);
        }

        //On peut donc faire l'enregistrement du details en BD

        log.info("After all verification the record {} can be register in the DB", ldDto);
        return LoadingDetailsDto.fromEntity(
                loadingDetailsRepository.save(
                        LoadingDetailsDto.toEntity(ldDto)
                )
        );
    }

    @Override
    public LoadingDetailsDto updateLoadingDetails(LoadingDetailsDto ldDto) {
        /***************************************************
         * IL faut valider le parametre pris en argument
         */
        List<String> errors = LoadingDetailsValidator.validate(ldDto);
        if(!errors.isEmpty()){
            log.error("Entity loadingDetailsDto not valid {}", ldDto);
            throw new InvalidEntityException("Le loadingDetailsDto passe en argument n'est pas valide:  ",
                    ErrorCode.LOADINGDETAILS_NOT_VALID, errors);
        }
        /***********************************************
         * Il faut faire la recherche du LoadingDetails
         * a update
         */
        if(ldDto.getId() == null){
            log.error("The id of the loadingdetails to modify is null");
            throw new InvalidEntityException("L'id du LoadingDetails a update est null ",
                    ErrorCode.LOADINGDETAILS_NOT_VALID);
        }
        Optional<LoadingDetails> optionalLoadingDetails = loadingDetailsRepository.findLoadingDetailsById(ldDto.getId());
        if(!optionalLoadingDetails.isPresent()){
            log.error("There is no loadingdetails in the DB with the precise id {}", ldDto.getId());
            throw new InvalidEntityException("Aucun loadingDetails n'existe avec l'Id precise ",
                    ErrorCode.LOADINGDETAILS_NOT_VALID);
        }
        //Ici on est que le loadingDetails a update existe vraiment
        LoadingDetails ldToUpdate = optionalLoadingDetails.get();

        /*******************************************************
         * On se rassure que ce n'est pas le loading quon veut
         * update car cela n'est pas possible
         */
        if(ldDto.getLdLoadingDto().getId() == null){
            log.error("The id of the loading associated is null");
            throw new InvalidEntityException("Le id du loading associe est null",
                    ErrorCode.LOADINGDETAILS_NOT_VALID);
        }
        if(!ldToUpdate.getLdLoading().getId().equals(ldDto.getLdLoadingDto().getId())){
            log.error("It is not normal to update the loading of a loadingg details");
            throw new InvalidEntityException("Il n'est pas possible de update le Loadimg dun details",
                    ErrorCode.LOADINGDETAILS_NOT_VALID);
        }

        /**********************************************************
         * On regarde si c'est l'article quon veut modifier et si
         * c'est le cas on va se rassurer de l'unicite du loading
         * details resultant
         */
        if(ldDto.getLdXArticleDto().getId() == null){
            log.error("The id of the article associated is null");
            throw new InvalidEntityException("Le id de l'article associe est null",
                    ErrorCode.LOADINGDETAILS_NOT_VALID);
        }
        if(!ldToUpdate.getLdArticle().getId().equals(ldDto.getLdXArticleDto().getId())){
            //Est ce que le nouvel article indique existe en BD
            Optional<Article> optionalArticle = articleRepository.findArticleById(ldDto.getLdXArticleDto().getId());
            if(!optionalArticle.isPresent()){
                log.error("There is no article in the DB with the ID precise");
                throw new InvalidEntityException("Aucun article n'existe en BD avec l'Id precise ",
                        ErrorCode.LOADINGDETAILS_NOT_VALID);
            }
            //Donc c'est l'article quon veut modifier
            if(!isLoadingDetailsUniqueinLoading(ldDto.getLdXArticleDto().getId(), ldDto.getLdLoadingDto().getId())){
                log.error("There already exist a loadingDetails concerning this article in the loading");
                throw new DuplicateEntityException("Il existe deja un loadingDetails concernant cet article dans le loading",
                        ErrorCode.LOADINGDETAILS_DUPLICATED);
            }
            //Donc on est quil y aura pas de duplicata donc on realise le update
            ldToUpdate.setLdArticle(optionalArticle.get());
        }

        ldToUpdate.setLdQuantityreturn(ldDto.getLdQuantityreturn());
        ldToUpdate.setLdQuantitytaken(ldDto.getLdQuantitytaken());

        log.info("Le update peut donc etre effectue sans souci");
        return LoadingDetailsDto.fromEntity(loadingDetailsRepository.save(ldToUpdate));
    }

    @Override
    public LoadingDetailsDto findLoadingDetailsById(Long ldId) {
        if(ldId == null){
            log.error("The argument ldId is null");
            throw new NullArgumentException("Le parametre ldId est null");
        }
        Optional<LoadingDetails> optionalLoadingDetails = loadingDetailsRepository.findLoadingDetailsById(ldId);
        if(!optionalLoadingDetails.isPresent()){
            log.error("There is no loadingdetails in the DB with the id {} precised", ldId);
            throw new EntityNotFoundException("Aucun loadingdetails n'existe en BD avec le id precise ",
                    ErrorCode.LOADINGDETAILS_NOT_FOUND);
        }
        return LoadingDetailsDto.fromEntity(optionalLoadingDetails.get());
    }

    @Override
    public LoadingDetailsDto findLoadingDetailsofArticleinLoading(Long artId, Long loadingId) {
        if(artId == null){
            log.error("The argument artId is null");
            throw new NullArgumentException("Le parametre artId est null");
        }
        if(loadingId == null){
            log.error("The argument loadingId is null");
            throw new NullArgumentException("Le parametre loadingId est null");
        }
        Optional<LoadingDetails> optionalLoadingDetails = loadingDetailsRepository.
                findLoadingDetailsofArticleinLoading(artId, loadingId);
        if(!optionalLoadingDetails.isPresent()){
            log.error("There is no loadingdetails in the DB with the argument precise precised");
            throw new EntityNotFoundException("Aucun loadingdetails n'existe en BD avec les arguments precises ",
                    ErrorCode.LOADINGDETAILS_NOT_FOUND);
        }
        return LoadingDetailsDto.fromEntity(optionalLoadingDetails.get());
    }

    @Override
    public List<LoadingDetailsDto> findAllLoadingDetailsinLoading(Long loadingId) {
        if(loadingId == null){
            log.error("The argument loadingId is null");
            throw new NullArgumentException("Le parametre loadingId est null");
        }
        Optional<List<LoadingDetails>> optionalLoadingDetailsList = loadingDetailsRepository.
                findAllLoadingDetailsofLoading(loadingId);
        if(!optionalLoadingDetailsList.isPresent()){
            log.error("There is no loading in the DB with the id precise {}", loadingId);
            throw new EntityNotFoundException("Aucun loading n'existe en BD avec le Id precise ",
                    ErrorCode.LOADING_NOT_FOUND);
        }

        return optionalLoadingDetailsList.get().stream().map(LoadingDetailsDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<LoadingDetailsDto> findPageLoadingDetailsinLoading(Long loadingId, int pagenum, int pagesize) {
        if(loadingId == null){
            log.error("The argument loadingId is null");
            throw new NullArgumentException("Le parametre loadingId est null");
        }
        Optional<Page<LoadingDetails>> optionalLoadingDetailsPage = loadingDetailsRepository.
                findPageLoadingDetailsofLoading(loadingId, PageRequest.of(pagenum, pagesize));
        if(!optionalLoadingDetailsPage.isPresent()){
            log.error("There is no loading in the DB with the id precise {}", loadingId);
            throw new EntityNotFoundException("Aucun loading n'existe en BD avec le Id precise ",
                    ErrorCode.LOADING_NOT_FOUND);
        }

        return optionalLoadingDetailsPage.get().map(LoadingDetailsDto::fromEntity);
    }

    @Override
    public Boolean isLoadingDetailsUniqueinLoading(Long artId, Long loadingId) {
        if(artId == null){
            log.error("The argument artId is null");
            throw new NullArgumentException("Le parametre artId est null");
        }
        if(loadingId == null){
            log.error("The argument loadingId is null");
            throw new NullArgumentException("Le parametre loadingId est null");
        }
        Optional<LoadingDetails> optionalLoadingDetails = loadingDetailsRepository.
                findLoadingDetailsofArticleinLoading(artId, loadingId);
        return optionalLoadingDetails.isEmpty();
    }

    @Override
    public Boolean isLoadingDetailsDeleatable(Long ldId) {
        return true;
    }

    @Override
    public Boolean deleteLoadingDetailsById(Long ldId) {
        if(ldId == null){
            log.error("The argument ldId is null");
            throw new NullArgumentException("Le parametre ldId est null");
        }
        Optional<LoadingDetails> optionalLoadingDetails = loadingDetailsRepository.findLoadingDetailsById(ldId);
        if(!optionalLoadingDetails.isPresent()){
            log.error("There is no loadingdetails in the DB with the id {} precised", ldId);
            throw new EntityNotFoundException("Aucun loadingdetails n'existe en BD avec le id precise ",
                    ErrorCode.LOADINGDETAILS_NOT_FOUND);
        }
        if(!isLoadingDetailsDeleatable(ldId)){
            log.error("The loadingdetails precised can't be deleatable");
            throw new EntityNotDeleteableException("On ne peut supprimer le loadingdetails indique en ce moment ",
                    ErrorCode.LOADINGDETAILS_NOT_DELETEABLE);
        }
        loadingDetailsRepository.delete(optionalLoadingDetails.get());
        return true;
    }
}
