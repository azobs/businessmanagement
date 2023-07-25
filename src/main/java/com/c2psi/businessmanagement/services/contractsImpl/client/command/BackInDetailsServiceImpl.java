package com.c2psi.businessmanagement.services.contractsImpl.client.command;

import com.c2psi.businessmanagement.dtos.client.command.BackInDetailsDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.Article;
import com.c2psi.businessmanagement.models.BackIn;
import com.c2psi.businessmanagement.models.BackInDetails;
import com.c2psi.businessmanagement.repositories.client.command.BackInDetailsRepository;
import com.c2psi.businessmanagement.repositories.client.command.BackInRepository;
import com.c2psi.businessmanagement.repositories.stock.product.ArticleRepository;
import com.c2psi.businessmanagement.services.contracts.client.command.BackInDetailsService;
import com.c2psi.businessmanagement.validators.client.command.BackInDetailsValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value="BackInDetailsServiceV1")
@Slf4j
@Transactional
public class BackInDetailsServiceImpl implements BackInDetailsService {

    private ArticleRepository articleRepository;
    private BackInRepository backInRepository;
    private BackInDetailsRepository backInDetailsRepository;

    @Autowired
    public BackInDetailsServiceImpl(ArticleRepository articleRepository, BackInRepository backInRepository,
                                    BackInDetailsRepository backInDetailsRepository) {
        this.articleRepository = articleRepository;
        this.backInRepository = backInRepository;
        this.backInDetailsRepository = backInDetailsRepository;
    }

    @Override
    public BackInDetailsDto saveBackInDetails(BackInDetailsDto backInDetailsDto) {
        /***********************************************************************
         * Il faut valider le backindetails envoye avec le validateur
         */
        List<String> errors = BackInDetailsValidator.validate(backInDetailsDto);
        if(!errors.isEmpty()){
            log.error("Entity backInDetailsDto not valid {}", backInDetailsDto);
            throw new InvalidEntityException("Le backInDetailsDto passe en argument n'est pas valide:  ",
                    ErrorCode.BACKINDETAILS_NOT_VALID, errors);
        }

        /**********************************************************************
         * On se rassure que l'id de article n'est pas nul et que le Article
         * existe vraiment en BD
         */
        if(backInDetailsDto.getBidXArticleDto().getId() == null){
            log.error("The Id of the Article is null");
            throw new InvalidEntityException("L'id de l'article associe au backInDetails est null",
                    ErrorCode.BACKINDETAILS_NOT_VALID);
        }
        Optional<Article> optionalArticle = articleRepository.findArticleById(backInDetailsDto.getBidXArticleDto().getId());
        if(!optionalArticle.isPresent()){
            log.error("There is no Article associate with id precised");
            throw new InvalidEntityException("L'article precise dans le backInDetails n'existe pas en BD",
                    ErrorCode.BACKINDETAILS_NOT_VALID);
        }

        /*************************************************************************
         * On se rassure que l'id du BackIn n'est pas nul et que le BackIn existe
         * vraiment en BD
         */
        if(backInDetailsDto.getBidbiDto().getId() == null){
            log.error("The Id of the BackIn is null");
            throw new InvalidEntityException("L'id du BackIn associe au backInDetails est null",
                    ErrorCode.BACKINDETAILS_NOT_VALID);
        }
        Optional<BackIn> optionalBackIn = backInRepository.findBackInById(backInDetailsDto.getBidbiDto().getId());
        if(!optionalBackIn.isPresent()){
            log.error("There is no BackIn associate with id precised");
            throw new InvalidEntityException("Le BackIn precise dans le backInDetails n'existe pas en BD",
                    ErrorCode.BACKINDETAILS_NOT_VALID);
        }

        /*********************************************************************
         * On se rassure de l'unicite du BackInDetails dans le Backin en BD
         */
        if(!isBackInDetailsUnique(backInDetailsDto.getBidXArticleDto().getId(), backInDetailsDto.getBidbiDto().getId())){
            log.error("There exist a details about the article precised in the BackIn precised");
            throw new DuplicateEntityException("Il existe deja dans le BackIn un details concernant cet article ",
                    ErrorCode.BACKINDETAILS_DUPLICATED);
        }

        /********************************************************************
         * On peut donc effectuer l'enregistrement
         */
        log.info("After all verification the record {} can be register in the DB", backInDetailsDto);
        return BackInDetailsDto.fromEntity(
                backInDetailsRepository.save(
                        BackInDetailsDto.toEntity(backInDetailsDto)
                )
        );
    }

    @Override
    public BackInDetailsDto updateBackInDetails(BackInDetailsDto backInDetailsDto) {

        /***********************************************************************
         * Il faut valider le backindetails envoye avec le validateur
         */
        List<String> errors = BackInDetailsValidator.validate(backInDetailsDto);
        if(!errors.isEmpty()){
            log.error("Entity backInDetailsDto not valid {}", backInDetailsDto);
            throw new InvalidEntityException("Le backInDetailsDto passe en argument n'est pas valide:  ",
                    ErrorCode.BACKINDETAILS_NOT_VALID, errors);
        }

        /************************************************************
         * Il faut rechercher le BackInDetails a update
         */
        if(backInDetailsDto.getId() == null){
            log.error("The id of the backInDetails is null");
            throw new InvalidEntityException("Le id du backinDetails est null ", ErrorCode.BACKINDETAILS_NOT_VALID);
        }
        Optional<BackInDetails> optionalBackInDetails = backInDetailsRepository.findBackInDetailsById(
                backInDetailsDto.getId());
        if(!optionalBackInDetails.isPresent()){
            log.error("There is no BackInDetails with the id precised");
            throw new InvalidEntityException("Aucun BackInDetails n'existe avec l'Id precise ",
                    ErrorCode.BACKINDETAILS_NOT_VALID);
        }
        BackInDetails backInDetailsToUpdate = optionalBackInDetails.get();

        /*********************************************************************
         * Il faut verifier que c'est pas le backIn quon veut changer
         */
        if(backInDetailsDto.getBidbiDto().getId() == null){
            log.error("The id of the backIn associate with the backindetails cannot be null");
            throw new InvalidEntityException("L'id du backin associe au backindetails ne peut etre null",
                    ErrorCode.BACKINDETAILS_NOT_VALID);
        }
        if(!backInDetailsToUpdate.getBidbi().getId().equals(backInDetailsDto.getBidbiDto().getId())){
            log.error("It is not possible to modify the backIn concerned by the backIn details");
            throw new InvalidEntityException("Il n'est pas possible de update le BackIn associe a un BackIn details ",
                    ErrorCode.BACKINDETAILS_NOT_VALID);
        }

        /***************************************************************
         * Il faut verifier si cest l'article quon veut changer et si
         * cest le cas on verifie que il y aura pas duplicata
         */
        if(backInDetailsDto.getBidXArticleDto().getId() == null){
            log.error("The id of the article associate with the backIndetails cannot be null");
            throw new InvalidEntityException("L'id de l'article associe au backindetails ne peut etre null ",
                    ErrorCode.BACKINDETAILS_NOT_VALID);
        }

        if(!backInDetailsToUpdate.getBidArticle().getId().equals(backInDetailsDto.getBidXArticleDto().getId())){
            /******************************
             * C'est l'article quon veut modifier il faut donc se rassurer de l'unicite
             * du backIn details
             */
            if(!isBackInDetailsUnique(backInDetailsDto.getBidXArticleDto().getId(), backInDetailsDto.getBidbiDto().getId())){
                log.error("There exist a details about the article precised in the BackIn precised");
                throw new DuplicateEntityException("Il existe deja dans le BackIn un details concernant cet article ",
                        ErrorCode.BACKINDETAILS_DUPLICATED);
            }

            /*****
             * On verifie que le nouvel article existe bel et bien en Base de donnees
             */
            Optional<Article> optionalArticle = articleRepository.findArticleById(backInDetailsDto.getBidXArticleDto().getId());
            if(!optionalArticle.isPresent()){
                log.error("There is no Article associate with id precised");
                throw new InvalidEntityException("L'article precise dans le backInDetails n'existe pas en BD",
                        ErrorCode.BACKINDETAILS_NOT_VALID);
            }
            //Ici on est sur la modification de l'article ne cause pas de souci
            backInDetailsToUpdate.setBidArticle(optionalArticle.get());
        }

        /************************************************************
         * On peut donc faire les update sur les autres champs
         */
        backInDetailsToUpdate.setBidQuantity(backInDetailsDto.getBidQuantity());
        backInDetailsToUpdate.setBidComment(backInDetailsDto.getBidComment());

        log.info("After all verification, the record can be done on the DB");
        return BackInDetailsDto.fromEntity(backInDetailsRepository.save(backInDetailsToUpdate));
    }

    @Override
    public BackInDetailsDto findBackInDetailsById(Long backInDetailsId) {
        if(backInDetailsId == null){
            log.error("The backindetailsid precised is null");
            throw new NullArgumentException("L'argument backIndetails precise est null");
        }
        Optional<BackInDetails> optionalBackInDetails = backInDetailsRepository.findBackInDetailsById(backInDetailsId);
        if(!optionalBackInDetails.isPresent()){
            log.error("There is no BackInDetails in DB with the indicated Id");
            throw new EntityNotFoundException("Aucun backindetails n'existe avec l'Id passe en argument ",
                    ErrorCode.BACKINDETAILS_NOT_FOUND);
        }
        return BackInDetailsDto.fromEntity(optionalBackInDetails.get());
    }

    @Override
    public Boolean isBackInDetailsUnique(Long artId, Long backInId) {
        if(artId == null){
            log.error("The artId passed as argument is null");
            throw new NullArgumentException("L'argument artId passe dans la requete est null");
        }
        if(backInId == null){
            log.error("The backInId passed as argument is null");
            throw new NullArgumentException("L'argument backinId passe dans la requete est null");
        }
        Optional<BackInDetails> optionalBackInDetails = backInDetailsRepository.
                findBackInDetailsofArticleinBackIn(artId, backInId);
        return optionalBackInDetails.isEmpty();
    }

    @Override
    public BackInDetailsDto findBackInDetailsofArticleinBackIn(Long artId, Long backInId) {
        if(artId == null){
            log.error("The artId passed as argument is null");
            throw new NullArgumentException("L'argument artId passe dans la requete est null");
        }
        if(backInId == null){
            log.error("The backInId passed as argument is null");
            throw new NullArgumentException("L'argument backinId passe dans la requete est null");
        }
        Optional<BackInDetails> optionalBackInDetails = backInDetailsRepository.
                findBackInDetailsofArticleinBackIn(artId, backInId);
        if(!optionalBackInDetails.isPresent()){
            log.error("There is no BackInDetails in the BackIn precised for the article precised in the request");
            throw new EntityNotFoundException("Aucun details de BackIn n'existe pour l'article dont l'Id est propose " +
                    "dans le BackIn ", ErrorCode.BACKINDETAILS_NOT_FOUND);
        }

        return BackInDetailsDto.fromEntity(optionalBackInDetails.get());
    }

    @Override
    public List<BackInDetailsDto> findAllBackInDetailsofBackIn(Long backInId) {
        if(backInId == null){
            log.error("The backInId passed as argument is null");
            throw new NullArgumentException("L'argument passe en argument est null");
        }
        Optional<List<BackInDetails>> optionalBackInDetailsList = backInDetailsRepository.
                findAllBackInDetailsofBackIn(backInId);
        if(!optionalBackInDetailsList.isPresent()){
            log.error("There is any BackIn in the Database with the id {} precised ", backInId);
            throw new EntityNotFoundException("Aucun BackIn n'existe en BD avec l'Id Precise ",
                    ErrorCode.BACKIN_NOT_FOUND);
        }

        return optionalBackInDetailsList.get().stream().map(BackInDetailsDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<BackInDetailsDto> findPageBackInDetailsofBackIn(Long backInId, int pagenum, int pagesize) {
        if(backInId == null){
            log.error("The backInId passed as argument is null");
            throw new NullArgumentException("L'argument passe en argument est null");
        }
        Optional<Page<BackInDetails>> optionalBackInDetailsPage = backInDetailsRepository.
                findPageBackInDetailsofBackIn(backInId, PageRequest.of(pagenum, pagesize));
        if(!optionalBackInDetailsPage.isPresent()){
            log.error("There is any BackIn in the Database with the id {} precised ", backInId);
            throw new EntityNotFoundException("Aucun BackIn n'existe en BD avec l'Id Precise ",
                    ErrorCode.BACKIN_NOT_FOUND);
        }

        return optionalBackInDetailsPage.get().map(BackInDetailsDto::fromEntity);
    }

    @Override
    public Boolean isBackInDetailsDeleteable(Long backInDetailsId) {
        return true;
    }

    @Override
    public Boolean deleteBackInDetailsById(Long backInDetailsId) {
        if(backInDetailsId == null){
            log.error("The backindetailsid precised is null");
            throw new NullArgumentException("L'argument backIndetails precise est null");
        }
        Optional<BackInDetails> optionalBackInDetails = backInDetailsRepository.findBackInDetailsById(backInDetailsId);
        if(!optionalBackInDetails.isPresent()){
            log.error("There is no BackInDetails in DB with the indicated Id");
            throw new EntityNotFoundException("Aucun backindetails n'existe avec l'Id passe en argument ",
                    ErrorCode.BACKINDETAILS_NOT_FOUND);
        }
        if(!isBackInDetailsDeleteable(backInDetailsId)){
            log.error("It is not possible to delete the backindetails precised by the id {}", backInDetailsId);
            throw new EntityNotDeleteableException("Il n'est pas possible de supprimer le BackIndetails precise ",
                    ErrorCode.BACKINDETAILS_NOT_DELETEABLE);
        }
        backInDetailsRepository.delete(optionalBackInDetails.get());
        return true;
    }


}
