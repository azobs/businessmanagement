package com.c2psi.businessmanagement.services.contractsImpl.stock.provider;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDamageAccountDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.*;
import com.c2psi.businessmanagement.repositories.pos.pos.PointofsaleRepository;
import com.c2psi.businessmanagement.repositories.pos.userbm.UserBMRepository;
import com.c2psi.businessmanagement.repositories.stock.product.ArticleRepository;
import com.c2psi.businessmanagement.repositories.stock.provider.*;
import com.c2psi.businessmanagement.services.contracts.stock.provider.ProviderDamageAccountService;
import com.c2psi.businessmanagement.validators.stock.provider.ProviderDamageAccountValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value="ProviderDamageAccountServiceV1")
@Slf4j
@Transactional
public class ProviderDamageAccountServiceImpl implements ProviderDamageAccountService {

    private ProviderDamageAccountRepository providerDamageAccountRepository;
    private ProviderDamageOperationRepository providerDamageOperationRepository;
    private UserBMRepository userBMRepository;
    private ArticleRepository articleRepository;
    private PointofsaleRepository pointofsaleRepository;

    private ProviderRepository providerRepository;

    @Autowired
    public ProviderDamageAccountServiceImpl(ProviderDamageAccountRepository providerDamageAccountRepository,
                                            ProviderDamageOperationRepository providerDamageOperationRepository,
                                            UserBMRepository userBMRepository, ArticleRepository articleRepository,
                                            PointofsaleRepository pointofsaleRepository,
                                            ProviderRepository providerRepository) {
        this.providerDamageAccountRepository = providerDamageAccountRepository;
        this.providerDamageOperationRepository = providerDamageOperationRepository;
        this.userBMRepository = userBMRepository;
        this.articleRepository = articleRepository;
        this.pointofsaleRepository = pointofsaleRepository;
        this.providerRepository = providerRepository;
    }

    public Boolean isProviderDamageAccountofArticleExistinPos(Long providerId, Long artId){
        if(artId == null || providerId == null){
            log.error("artId or posId is null");
            throw new NullArgumentException("le artId ou le posId passe en argument de la methode est null");
        }
        Optional<ProviderDamageAccount> optionalProviderDamageAccount = providerDamageAccountRepository.
                findProviderDamageAccountofArticleinPos(providerId, artId);
        return optionalProviderDamageAccount.isPresent()?true:false;
    }

    @Override
    public ProviderDamageAccountDto saveProviderDamageAccount(ProviderDamageAccountDto prodamaccDto) {

        /*****************************************************************
         * On valide d'abord le parametre pris en argument
         */
        List<String> errors = ProviderDamageAccountValidator.validate(prodamaccDto);
        if(!errors.isEmpty()){
            log.error("Entity prodamaccDto not valid {}", prodamaccDto);
            throw new InvalidEntityException("Le prodamaccDto passé en argument n'est pas valide: "+errors,
                    ErrorCode.PROVIDERDAMAGEACCOUNT_NOT_VALID, errors);
        }

        /***************************************************************************
         * Maintenant faut se rassurer que le provider precise existe vraiment
         */
        if(prodamaccDto.getPdaProviderDto().getId() == null){
            log.error("The id of the provider associated cannot be null");
            throw new InvalidEntityException("Le id du provider associe au compte damage ne peut etre null",
                    ErrorCode.PROVIDERDAMAGEACCOUNT_NOT_VALID);
        }
        //A ce niveau on est sur que le id du provider nest pas null
        Optional<Provider> optionalProvider = providerRepository.findProviderById(
                prodamaccDto.getPdaProviderDto().getId());
        if(!optionalProvider.isPresent()){
            log.error("The provider indicated in the prodamaccDto doesn't exist in DB ");
            throw new EntityNotFoundException("Aucun provider n'existe avec l'id precise ",
                    ErrorCode.PROVIDER_NOT_FOUND);
        }

        /**********************************************************************
         * Maintenant on va se rassurer de l'existance de l'article en BD
         */
        if(prodamaccDto.getPdaArticleDto().getId() == null){
            log.error("The id of the article associated cannot be null");
            throw new InvalidEntityException("Le id du article associe au compte capsule ne peut etre null",
                    ErrorCode.PROVIDERDAMAGEACCOUNT_NOT_VALID);
        }
        //A ce niveau on est sur que le id du article nest pas null
        Optional<Article> optionalArticle = articleRepository.findArticleById(prodamaccDto.getPdaArticleDto().getId());
        if(!optionalArticle.isPresent()){
            log.error("The article indicated in the prodamaccDto doesn't exist in DB ");
            throw new EntityNotFoundException("Aucun article n'existe avec l'id precise ",
                    ErrorCode.ARTICLE_NOT_FOUND);
        }

        /*************************************************************************************************
         * On verifie qu'aucun compte damage n'est pas deja creer pour cet article et ce provider
         */
        if(isProviderDamageAccountofArticleExistinPos(prodamaccDto.getPdaProviderDto().getId(),
                prodamaccDto.getPdaArticleDto().getId())){
            log.error("An account for damage has been already created for this article and this provider");
            throw new DuplicateEntityException("Un compte damage pour cet article et ce provider existe deja " +
                    "en BD ", ErrorCode.PROVIDERDAMAGEACCOUNT_DUPLICATED);
        }

        log.info("After all verification, the entity {} can be saved without any problem in DB ", prodamaccDto);

        return ProviderDamageAccountDto.fromEntity(
                providerDamageAccountRepository.save(
                        ProviderDamageAccountDto.toEntity(prodamaccDto)
                )
        );
    }

    @Override
    public ProviderDamageAccountDto findProviderDamageAccountById(Long prodamaccId) {
        if(prodamaccId == null){
            log.error("The prodamaccId passed as argument is null");
            throw new NullArgumentException("Le prodamaccId passe en argument est null");
        }
        Optional<ProviderDamageAccount> optionalProviderDamageAccount = providerDamageAccountRepository.
                findProviderDamageAccountById(prodamaccId);
        if(!optionalProviderDamageAccount.isPresent()){
            log.error("There is no ProviderDamageAccount with the id {} sent", prodamaccId);
            throw new EntityNotFoundException("Aucun ProviderDamageAccount n'existe avec l'Id passe en parametre "+prodamaccId,
                    ErrorCode.PROVIDERDAMAGEACCOUNT_NOT_FOUND);
        }

        return ProviderDamageAccountDto.fromEntity(optionalProviderDamageAccount.get());
    }

    @Override
    public ProviderDamageAccountDto findProviderDamageAccountofArticleinPos(Long providerId, Long artId) {
        if(providerId == null){
            log.error("The providerId passed as argument is null");
            throw new NullArgumentException("Le posId passe en argument est null");
        }
        if(artId == null){
            log.error("The artId passed as argument is null");
            throw new NullArgumentException("Le artId passe en argument est null");
        }

        Optional<ProviderDamageAccount> optionalProviderDamageAccount = providerDamageAccountRepository.
                findProviderDamageAccountofArticleinPos(providerId, artId);
        if(!optionalProviderDamageAccount.isPresent()){
            log.error("There is no ProviderDamageAccount with the argument artId =  {} and providerId = {} sent",
                    artId, providerId);
            throw new EntityNotFoundException("Aucun ProviderDamageAccount n'existe avec les parametres passe en parametre "
                    + "artId = "+artId+ " providerId = "+providerId, ErrorCode.PROVIDERDAMAGEACCOUNT_NOT_FOUND);
        }

        return ProviderDamageAccountDto.fromEntity(optionalProviderDamageAccount.get());
    }

    @Override
    public List<ProviderDamageAccountDto> findAllProviderDamageAccountinPos(Long providerId) {
        if(providerId == null){
            log.error("The providerId sent as argument is null");
            throw new NullArgumentException("L'argument passe est null");
        }
        Optional<List<ProviderDamageAccount>> optionalProviderDamageAccountList = providerDamageAccountRepository.
                findAllProviderDamageAccountinPos(providerId);
        if(!optionalProviderDamageAccountList.isPresent()){
            log.error("There is no provider with the id providerId");
            throw new EntityNotFoundException("Aucun provider n'existe avec l'id passe en argument "+providerId,
                    ErrorCode.PROVIDER_NOT_FOUND);
        }

        return optionalProviderDamageAccountList.get().stream().map(ProviderDamageAccountDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<ProviderDamageAccountDto> findPageProviderDamageAccountinPos(Long providerId, int pagenum, int pagesize) {
        if(providerId == null){
            log.error("The providerId sent as argument is null");
            throw new NullArgumentException("L'argument passe est null");
        }
        Optional<Page<ProviderDamageAccount>> optionalProviderDamageAccountPage = providerDamageAccountRepository.
                findPageProviderDamageAccountinPos(providerId, PageRequest.of(pagenum, pagesize));
        if(!optionalProviderDamageAccountPage.isPresent()){
            log.error("There is no provider with the id posId {}", providerId);
            throw new EntityNotFoundException("Aucun provider n'existe avec l'id passe en argument "+providerId,
                    ErrorCode.PROVIDER_NOT_FOUND);
        }

        return optionalProviderDamageAccountPage.get().map(ProviderDamageAccountDto::fromEntity);
    }

    @Override
    public Boolean isProviderDamageAccountDeleteable(Long prodamaccId) {
        return true;
    }

    @Override
    public Boolean deleteProviderDamageAccountById(Long prodamaccId) {
        if(prodamaccId == null){
            log.error("The argument cannot be null for this method please check it");
            throw new NullArgumentException("L'argument passe est null et la methode ne peut donc s'executer");
        }

        Optional<ProviderDamageAccount> optionalProviderDamageAccount = providerDamageAccountRepository.findProviderDamageAccountById(prodamaccId);
        if(!optionalProviderDamageAccount.isPresent()){
            log.error("There is no providerDamageAccount with the precised id {}", prodamaccId);
            throw new EntityNotFoundException("Aucun providerDamageAccount n'existe avec l'Id passe en argument dans la BD");
        }
        if(!isProviderDamageAccountDeleteable(prodamaccId)){
            log.error("Le compte damage ne peut etre supprime");
            throw new EntityNotDeleteableException("Le compte damage designe ne peut etre supprime",
                    ErrorCode.PROVIDERDAMAGEACCOUNT_NOT_DELETEABLE);
        }
        providerDamageAccountRepository.delete(optionalProviderDamageAccount.get());
        return true;
    }

    @Override
    public Boolean saveDamageOperation(Long prodamaccId, BigDecimal qte, OperationType operationType, Long userbmId,
                                       String opObject, String opDescription) {

        /******************************************************************
         * Se rassurer que les donnees dans la fonction ne sont pas null
         */
        if(prodamaccId == null || qte == null || userbmId == null || operationType == null){
            log.error("prodamaccId, qte or even userbmId is null ");
            throw new NullArgumentException("Appel de la methode saveDamageOperation avec des parametres null");
        }

        /**********************************************************************************
         * Se rassurer que la quantite d'article dans l'operation est strictement positive
         */
        if(qte.compareTo(BigDecimal.valueOf(0)) <= 0){
            log.error("The qte cannot be negative value");
            throw new InvalidValueException("La quantite dans l'operation ne saurait etre negative");
        }

        /******************************************************************************************
         * On va essayer de recuperer le userbm qui est associe a cette operation
         */
        Optional<UserBM> optionalUserBM = userBMRepository.findUserBMById(userbmId);
        if(!optionalUserBM.isPresent()){
            log.error("There is no userbm associated with the id {} precised in argument ", userbmId);
            throw new EntityNotFoundException("Aucun userbm n'existe avec le id precise ", ErrorCode.USERBM_NOT_FOUND);
        }

        /***************************************************************************************
         * Se rassurer que le type d'operation souhaite est soit un credit soit un debit
         */
        if(!operationType.equals(OperationType.Credit) && !operationType.equals(OperationType.Withdrawal)){
            log.error("The operationType is not recognized for this operation");
            throw new InvalidValueException("Le type d'operation precise n'est pas valide dans cette fonction ");
        }

        /*************************************************************************************
         * On essaye donc de recuperer d'abord le compte dans lequel l'operation sera realise
         */
        if(!this.isProviderDamageAccountExistWithId(prodamaccId)){
            log.error("The prodamaccId {} does not identify any account ", prodamaccId);
            throw  new EntityNotFoundException("Aucun ProviderDamageAccount n'existe avec le ID precise "+prodamaccId,
                    ErrorCode.PROVIDERDAMAGEACCOUNT_NOT_FOUND);
        }
        Optional<ProviderDamageAccount> optionalProviderDamageAccount = providerDamageAccountRepository.
                findProviderDamageAccountById(prodamaccId);
        //A ce niveau on na pas besoin de regarder si isPresent est true car on est sur que ca existe
        ProviderDamageAccount providerDamageAccountToUpdate = optionalProviderDamageAccount.get();

        BigDecimal solde = providerDamageAccountToUpdate.getPdaNumber();
        BigDecimal updatedSolde = BigDecimal.valueOf(0.0);

        /***
         * On doit ici enregistrer un depot dans un compte damage d'un provider pour un article. Pour cela il
         * faut ajouter la qte de la transaction au solde du compte et ensuite enregistrer l'operation ainsi
         * réalise
         */
        if(operationType.equals(OperationType.Credit)){
            updatedSolde = solde.add(qte);//Car BigDecimal est immutable on peut pas directement modifier sa valeur
        }
        else if(operationType.equals(OperationType.Withdrawal)){
            if(solde.compareTo(qte) < 0){
                log.error("Insufficient balance");
                throw new InvalidValueException("Solde insuffisant "+solde);
            }
            updatedSolde = solde.subtract(qte);
        }
        providerDamageAccountToUpdate.setPdaNumber(updatedSolde);

        providerDamageAccountRepository.save(providerDamageAccountToUpdate);

        ProviderDamageOperation prodamop = new ProviderDamageOperation();
        prodamop.setProdoNumberinmvt(qte);
        prodamop.setProdoUserbm(optionalUserBM.get());
        prodamop.setProdoProDamageAccount(providerDamageAccountToUpdate);

        Operation op = new Operation();
        op.setOpDate(new Date().toInstant());
        op.setOpDescription(opDescription);
        op.setOpObject(opObject);
        op.setOpType(operationType);
        prodamop.setProdoOperation(op);
        //Il faut save le ProviderCapsuleOperation
        providerDamageOperationRepository.save(prodamop);

        return true;
    }

    public Boolean isProviderDamageAccountExistWithId(Long prodamaccId){
        if(prodamaccId == null){
            log.error("procapaccId is null");
            throw new NullArgumentException("le prodamaccId passe en argument de la methode est null");
        }
        Optional<ProviderDamageAccount> optionalProviderDamageAccount = providerDamageAccountRepository.
                findProviderDamageAccountById(prodamaccId);
        return optionalProviderDamageAccount.isPresent()?true:false;
    }
}
