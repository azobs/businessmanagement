package com.c2psi.businessmanagement.services.contractsImpl.stock.provider;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCapsuleAccountDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCapsuleOperationDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.*;
import com.c2psi.businessmanagement.repositories.pos.pos.PointofsaleRepository;
import com.c2psi.businessmanagement.repositories.pos.userbm.UserBMRepository;
import com.c2psi.businessmanagement.repositories.stock.product.ArticleRepository;
import com.c2psi.businessmanagement.repositories.stock.provider.ProviderCapsuleAccountRepository;
import com.c2psi.businessmanagement.repositories.stock.provider.ProviderCapsuleOperationRepository;
import com.c2psi.businessmanagement.repositories.stock.provider.ProviderRepository;
import com.c2psi.businessmanagement.services.contracts.stock.provider.ProviderCapsuleAccountService;
import com.c2psi.businessmanagement.validators.stock.provider.ProviderCapsuleAccountValidator;
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

@Service(value="ProviderCapsuleAccountServiceV1")
@Slf4j
@Transactional
public class ProviderCapsuleAccountServiceImpl implements ProviderCapsuleAccountService {

    private ProviderCapsuleAccountRepository providerCapsAccountRepository;
    private ProviderCapsuleOperationRepository providerCapsOperationRepository;
    private UserBMRepository userBMRepository;
    private ArticleRepository articleRepository;
    private PointofsaleRepository pointofsaleRepository;

    private ProviderRepository providerRepository;

    @Autowired
    public ProviderCapsuleAccountServiceImpl(ProviderCapsuleAccountRepository providerCapsAccountRepository,
                                             ProviderCapsuleOperationRepository providerCapsOperationRepository,
                                             UserBMRepository userBMRepository, ArticleRepository articleRepository,
                                             PointofsaleRepository pointofsaleRepository,
                                             ProviderRepository providerRepository) {
        this.providerCapsAccountRepository = providerCapsAccountRepository;
        this.providerCapsOperationRepository = providerCapsOperationRepository;
        this.userBMRepository = userBMRepository;
        this.articleRepository = articleRepository;
        this.pointofsaleRepository = pointofsaleRepository;
        this.providerRepository = providerRepository;
    }

    public Boolean isProviderCapsuleAccountofArticleExistinPos(Long providerId, Long artId){
        if(artId == null || providerId == null){
            log.error("artId or providerId is null");
            throw new NullArgumentException("le artId ou le providerId passe en argument de la methode est null");
        }
        Optional<ProviderCapsuleAccount> optionalProviderCapsuleAccount = providerCapsAccountRepository.
                findProviderCapsuleAccountofArticleinPos(providerId, artId);
        return optionalProviderCapsuleAccount.isPresent()?true:false;
    }

    @Override
    public ProviderCapsuleAccountDto saveProviderCapsuleAccount(ProviderCapsuleAccountDto procsaccDto) {

        /*****************************************************************
         * On valide d'abord le parametre pris en argument
         */
        List<String> errors = ProviderCapsuleAccountValidator.validate(procsaccDto);
        if(!errors.isEmpty()){
            log.error("Entity poscapsaccDto not valid {}", procsaccDto);
            throw new InvalidEntityException("Le poscapsaccDto passé en argument n'est pas valide: "+errors,
                    ErrorCode.PROVIDERCAPSULEACCOUNT_NOT_VALID, errors);
        }

        /***************************************************************************
         * Maintenant faut se rassurer que le provider precise existe vraiment
         */
        if(procsaccDto.getPcsaProviderDto().getId() == null){
            log.error("The id of the provider associated cannot be null");
            throw new InvalidEntityException("Le id du provider associe au compte capsule ne peut etre null",
                    ErrorCode.PROVIDERCAPSULEACCOUNT_NOT_VALID);
        }
        //A ce niveau on est sur que le id du provider nest pas null
        Optional<Provider> optionalProvider = providerRepository.findProviderById(
                procsaccDto.getPcsaProviderDto().getId());
        if(!optionalProvider.isPresent()){
            log.error("The provider indicated in the procsaccDto doesn't exist in DB ");
            throw new EntityNotFoundException("Aucun provider n'existe avec l'id precise ",
                    ErrorCode.PROVIDER_NOT_FOUND);
        }

        /**********************************************************************
         * Maintenant on va se rassurer de l'existance de l'article en BD
         */
        if(procsaccDto.getPcsaXArticleDto().getId() == null){
            log.error("The id of the article associated cannot be null");
            throw new InvalidEntityException("Le id du article associe au compte capsule ne peut etre null",
                    ErrorCode.PROVIDERCAPSULEACCOUNT_NOT_VALID);
        }
        //A ce niveau on est sur que le id du article nest pas null
        Optional<Article> optionalArticle = articleRepository.findArticleById(procsaccDto.getPcsaXArticleDto().getId());
        if(!optionalArticle.isPresent()){
            log.error("The article indicated in the poscapsaccount doesn't exist in DB ");
            throw new EntityNotFoundException("Aucun article n'existe avec l'id precise ",
                    ErrorCode.ARTICLE_NOT_FOUND);
        }

        /*************************************************************************************************
         * On verifie qu'aucun compte capsule n'est pas deja creer pour cet article et ce provider
         */
        if(isProviderCapsuleAccountofArticleExistinPos(procsaccDto.getPcsaProviderDto().getId(),
                procsaccDto.getPcsaXArticleDto().getId())){
            log.error("An account for capsule has been already created for this article and this provider");
            throw new DuplicateEntityException("Un compte capsule pour cet article et ce provider existe deja " +
                    "en BD ", ErrorCode.PROVIDERCAPSULEACCOUNT_DUPLICATED);
        }

        log.info("After all verification, the entity {} can be saved without any problem in DB ", procsaccDto);

        return ProviderCapsuleAccountDto.fromEntity(
                providerCapsAccountRepository.save(
                        ProviderCapsuleAccountDto.toEntity(procsaccDto)
                )
        );
    }

    @Override
    public ProviderCapsuleAccountDto findProviderCapsuleAccountById(Long procapaccId) {
        if(procapaccId == null){
            log.error("The procapaccId passed as argument is null");
            throw new NullArgumentException("Le procapaccId passe en argument est null");
        }
        Optional<ProviderCapsuleAccount> optionalProviderCapsuleAccount = providerCapsAccountRepository.findProviderCapsuleAccountById(procapaccId);
        if(!optionalProviderCapsuleAccount.isPresent()){
            log.error("There is no ProviderCapsuleAccount with the id {} sent", procapaccId);
            throw new EntityNotFoundException("Aucun ProviderCapsuleAccount n'existe avec l'Id passe en parametre "+procapaccId,
                    ErrorCode.PROVIDERCAPSULEACCOUNT_NOT_FOUND);
        }

        return ProviderCapsuleAccountDto.fromEntity(optionalProviderCapsuleAccount.get());
    }

    @Override
    public ProviderCapsuleAccountDto findProviderCapsuleAccountofArticleinPos(Long providerId, Long artId) {
        if(providerId == null){
            log.error("The providerId passed as argument is null");
            throw new NullArgumentException("Le providerId passe en argument est null");
        }
        if(artId == null){
            log.error("The artId passed as argument is null");
            throw new NullArgumentException("Le artId passe en argument est null");
        }

        Optional<ProviderCapsuleAccount> optionalProviderCapsuleAccount = providerCapsAccountRepository.
                findProviderCapsuleAccountofArticleinPos(providerId, artId);
        if(!optionalProviderCapsuleAccount.isPresent()){
            log.error("There is no ProviderCapsuleAccount with the argument artId =  {} and providerId = {} sent",
                    artId, providerId);
            throw new EntityNotFoundException("Aucun ProviderCapsuleAccount n'existe avec les parametres passe en parametre "
                    + "artId = "+artId+ " providerId = "+providerId, ErrorCode.PROVIDERCAPSULEACCOUNT_NOT_FOUND);
        }

        return ProviderCapsuleAccountDto.fromEntity(optionalProviderCapsuleAccount.get());
    }

    @Override
    public List<ProviderCapsuleAccountDto> findAllProviderCapsuleAccountinPos(Long providerId) {
        if(providerId == null){
            log.error("The providerId sent as argument is null");
            throw new NullArgumentException("L'argument passe est null");
        }
        Optional<List<ProviderCapsuleAccount>> optionalProviderCapsuleAccountList = providerCapsAccountRepository.
                findAllProviderCapsuleAccountinPos(providerId);
        if(!optionalProviderCapsuleAccountList.isPresent()){
            log.error("There is no provider with the id providerId");
            throw new EntityNotFoundException("Aucun provider n'existe avec l'id passe en argument "+providerId,
                    ErrorCode.PROVIDER_NOT_FOUND);
        }

        return optionalProviderCapsuleAccountList.get().stream().map(ProviderCapsuleAccountDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<ProviderCapsuleAccountDto> findPageProviderCapsuleAccountinPos(Long providerId, int pagenum, int pagesize) {
        if(providerId == null){
            log.error("The providerId sent as argument is null");
            throw new NullArgumentException("L'argument passe est null");
        }
        Optional<Page<ProviderCapsuleAccount>> optionalProviderCapsuleAccountPage = providerCapsAccountRepository.
                findPageProviderCapsuleAccountinPos(providerId, PageRequest.of(pagenum, pagesize));
        if(!optionalProviderCapsuleAccountPage.isPresent()){
            log.error("There is no provider with the id posId {}", providerId);
            throw new EntityNotFoundException("Aucun provider n'existe avec l'id passe en argument "+providerId,
                    ErrorCode.PROVIDER_NOT_FOUND);
        }

        return optionalProviderCapsuleAccountPage.get().map(ProviderCapsuleAccountDto::fromEntity);
    }

    @Override
    public Boolean isProviderCapsuleAccountDeleteable(Long procsaccId) {
        return true;
    }

    @Override
    public Boolean deleteProviderCapsuleAccountById(Long procsaccId) {
        if(procsaccId == null){
            log.error("The argument cannot be null for this method please check it");
            throw new NullArgumentException("L'argument passe est null et la methode ne peut donc s'executer");
        }

        Optional<ProviderCapsuleAccount> optionalProviderCapsuleAccount = providerCapsAccountRepository.findProviderCapsuleAccountById(procsaccId);
        if(!optionalProviderCapsuleAccount.isPresent()){
            log.error("There is no providerCapsuleAccount with the precised id {}", procsaccId);
            throw new EntityNotFoundException("Aucun providerCapsuleAccount n'existe avec l'Id passe en argument dans la BD");
        }
        if(!isProviderCapsuleAccountDeleteable(procsaccId)){
            log.error("Le compte capsule ne peut etre supprime");
            throw new EntityNotDeleteableException("Le compte capsule designe ne peut etre supprime",
                    ErrorCode.PROVIDERCAPSULEACCOUNT_NOT_DELETEABLE);
        }
        providerCapsAccountRepository.delete(optionalProviderCapsuleAccount.get());
        return true;
    }

    @Override
    public Boolean saveCapsuleOperation(Long procapaccId, BigDecimal qte, OperationType operationType, Long userbmId,
                                        String opObject, String opDescription) {

        /******************************************************************
         * Se rassurer que les donnees dans la fonction ne sont pas null
         */
        if(procapaccId == null || qte == null || userbmId == null || operationType == null){
            log.error("procapaccId, qte or even userbmId is null ");
            throw new NullArgumentException("Appel de la methode saveCapsuleOperation avec des parametres null");
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
        if(!this.isProviderCapsuleAccountExistWithId(procapaccId)){
            log.error("The procapaccId {} does not identify any account ", procapaccId);
            throw  new EntityNotFoundException("Aucun ProviderCapsuleAccount n'existe avec le ID precise "+procapaccId,
                    ErrorCode.PROVIDERCAPSULEACCOUNT_NOT_FOUND);
        }
        Optional<ProviderCapsuleAccount> optionalProviderCapsuleAccount = providerCapsAccountRepository.
                findProviderCapsuleAccountById(procapaccId);
        //A ce niveau on na pas besoin de regarder si isPresent est true car on est sur que ca existe
        ProviderCapsuleAccount providerCapsuleAccountToUpdate = optionalProviderCapsuleAccount.get();

        BigDecimal solde = providerCapsuleAccountToUpdate.getPcsaNumber();
        BigDecimal updatedSolde = BigDecimal.valueOf(0.0);

        /***
         * On doit ici enregistrer un depot dans un compte capsule d'un provider pour un article. Pour cela il
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
        providerCapsuleAccountToUpdate.setPcsaNumber(updatedSolde);

        providerCapsAccountRepository.save(providerCapsuleAccountToUpdate);

        ProviderCapsuleOperation procapso = new ProviderCapsuleOperation();
        procapso.setProcsoNumberinmvt(qte);
        procapso.setProcsoUserbm(optionalUserBM.get());
        procapso.setProcsoProCapsuleAccount(providerCapsuleAccountToUpdate);

        Operation op = new Operation();
        op.setOpDate(new Date().toInstant());
        op.setOpDescription(opDescription);
        op.setOpObject(opObject);
        op.setOpType(operationType);
        procapso.setProscoOperation(op);
        //Il faut save le ProviderCapsuleOperation
        providerCapsOperationRepository.save(procapso);

        return true;
    }

    @Override
    public Boolean saveCapsuleOperation(ProviderCapsuleAccountDto providerCapsuleAccountDto,
                                        ProviderCapsuleOperationDto providerCapsuleOperationDto) {
        Long procapaccId = providerCapsuleAccountDto.getId();
        BigDecimal qte = providerCapsuleOperationDto.getProcsoNumberinmvt();
        Long userbmId = providerCapsuleOperationDto.getProcsoUserbmDto().getId();
        OperationType operationType = providerCapsuleOperationDto.getProscoOperationDto().getOpType();
        String opObject = providerCapsuleOperationDto.getProscoOperationDto().getOpObject();
        String opDescription = providerCapsuleOperationDto.getProscoOperationDto().getOpDescription();

        /******************************************************************
         * Se rassurer que les donnees dans la fonction ne sont pas null
         */
        if(procapaccId == null || qte == null || userbmId == null || operationType == null){
            log.error("procapaccId, qte or even userbmId is null ");
            throw new NullArgumentException("Appel de la methode saveCapsuleOperation avec des parametres null");
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
        if(!this.isProviderCapsuleAccountExistWithId(procapaccId)){
            log.error("The procapaccId {} does not identify any account ", procapaccId);
            throw  new EntityNotFoundException("Aucun ProviderCapsuleAccount n'existe avec le ID precise "+procapaccId,
                    ErrorCode.PROVIDERCAPSULEACCOUNT_NOT_FOUND);
        }
        Optional<ProviderCapsuleAccount> optionalProviderCapsuleAccount = providerCapsAccountRepository.
                findProviderCapsuleAccountById(procapaccId);
        //A ce niveau on na pas besoin de regarder si isPresent est true car on est sur que ca existe
        ProviderCapsuleAccount providerCapsuleAccountToUpdate = optionalProviderCapsuleAccount.get();

        BigDecimal solde = providerCapsuleAccountToUpdate.getPcsaNumber();
        BigDecimal updatedSolde = BigDecimal.valueOf(0.0);

        /***
         * On doit ici enregistrer un depot dans un compte capsule d'un provider pour un article. Pour cela il
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
        providerCapsuleAccountToUpdate.setPcsaNumber(updatedSolde);

        providerCapsAccountRepository.save(providerCapsuleAccountToUpdate);

        ProviderCapsuleOperation procapso = new ProviderCapsuleOperation();
        procapso.setProcsoNumberinmvt(qte);
        procapso.setProcsoUserbm(optionalUserBM.get());
        procapso.setProcsoProCapsuleAccount(providerCapsuleAccountToUpdate);

        Operation op = new Operation();
        op.setOpDate(new Date().toInstant());
        op.setOpDescription(opDescription);
        op.setOpObject(opObject);
        op.setOpType(operationType);
        procapso.setProscoOperation(op);
        //Il faut save le ProviderCapsuleOperation
        providerCapsOperationRepository.save(procapso);

        return true;
    }

    @Override
    public Boolean saveCapsuleOperation(ProviderCapsuleOperationDto providerCapsuleOperationDto) {
        Long procapaccId = providerCapsuleOperationDto.getProcsoProCapsuleAccountDto().getId();
        BigDecimal qte = providerCapsuleOperationDto.getProcsoNumberinmvt();
        Long userbmId = providerCapsuleOperationDto.getProcsoUserbmDto().getId();
        OperationType operationType = providerCapsuleOperationDto.getProscoOperationDto().getOpType();
        String opObject = providerCapsuleOperationDto.getProscoOperationDto().getOpObject();
        String opDescription = providerCapsuleOperationDto.getProscoOperationDto().getOpDescription();

        /******************************************************************
         * Se rassurer que les donnees dans la fonction ne sont pas null
         */
        if(procapaccId == null || qte == null || userbmId == null || operationType == null){
            log.error("procapaccId, qte or even userbmId is null ");
            throw new NullArgumentException("Appel de la methode saveCapsuleOperation avec des parametres null");
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
        if(!this.isProviderCapsuleAccountExistWithId(procapaccId)){
            log.error("The procapaccId {} does not identify any account ", procapaccId);
            throw  new EntityNotFoundException("Aucun ProviderCapsuleAccount n'existe avec le ID precise "+procapaccId,
                    ErrorCode.PROVIDERCAPSULEACCOUNT_NOT_FOUND);
        }
        Optional<ProviderCapsuleAccount> optionalProviderCapsuleAccount = providerCapsAccountRepository.
                findProviderCapsuleAccountById(procapaccId);
        //A ce niveau on na pas besoin de regarder si isPresent est true car on est sur que ca existe
        ProviderCapsuleAccount providerCapsuleAccountToUpdate = optionalProviderCapsuleAccount.get();

        BigDecimal solde = providerCapsuleAccountToUpdate.getPcsaNumber();
        BigDecimal updatedSolde = BigDecimal.valueOf(0.0);

        /***
         * On doit ici enregistrer un depot dans un compte capsule d'un provider pour un article. Pour cela il
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
        providerCapsuleAccountToUpdate.setPcsaNumber(updatedSolde);

        providerCapsAccountRepository.save(providerCapsuleAccountToUpdate);

        ProviderCapsuleOperation procapso = new ProviderCapsuleOperation();
        procapso.setProcsoNumberinmvt(qte);
        procapso.setProcsoUserbm(optionalUserBM.get());
        procapso.setProcsoProCapsuleAccount(providerCapsuleAccountToUpdate);

        Operation op = new Operation();
        op.setOpDate(new Date().toInstant());
        op.setOpDescription(opDescription);
        op.setOpObject(opObject);
        op.setOpType(operationType);
        procapso.setProscoOperation(op);
        //Il faut save le ProviderCapsuleOperation
        providerCapsOperationRepository.save(procapso);

        return true;
    }

    public Boolean isProviderCapsuleAccountExistWithId(Long procapaccId){
        if(procapaccId == null){
            log.error("procapaccId is null");
            throw new NullArgumentException("le procapaccId passe en argument de la methode est null");
        }
        Optional<ProviderCapsuleAccount> optionalProviderCapsuleAccount = providerCapsAccountRepository.
                findProviderCapsuleAccountById(procapaccId);
        return optionalProviderCapsuleAccount.isPresent()?true:false;
    }
}
