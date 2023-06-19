package com.c2psi.businessmanagement.services.contractsImpl.client.client;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.client.client.ClientCapsuleAccountDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientCapsuleOperationDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.*;
import com.c2psi.businessmanagement.repositories.client.client.ClientCapsuleAccountRepository;
import com.c2psi.businessmanagement.repositories.client.client.ClientCapsuleOperationRepository;
import com.c2psi.businessmanagement.repositories.client.client.ClientRepository;
import com.c2psi.businessmanagement.repositories.pos.pos.PointofsaleRepository;
import com.c2psi.businessmanagement.repositories.pos.userbm.UserBMRepository;
import com.c2psi.businessmanagement.repositories.stock.product.ArticleRepository;
import com.c2psi.businessmanagement.services.contracts.client.client.ClientCapsuleAccountService;
import com.c2psi.businessmanagement.validators.client.client.ClientCapsuleAccountValidator;
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

@Service(value="ClientCapsuleAccountServiceV1")
@Slf4j
@Transactional
public class ClientCapsuleAccountServiceImpl implements ClientCapsuleAccountService {

    private ClientCapsuleAccountRepository clientCapsAccountRepository;
    private ClientCapsuleOperationRepository clientCapsOperationRepository;
    private UserBMRepository userBMRepository;
    private ArticleRepository articleRepository;
    private PointofsaleRepository pointofsaleRepository;

    private ClientRepository clientRepository;

    @Autowired
    public ClientCapsuleAccountServiceImpl(ClientCapsuleAccountRepository clientCapsAccountRepository,
                                           ClientCapsuleOperationRepository clientCapsOperationRepository,
                                           UserBMRepository userBMRepository, ArticleRepository articleRepository,
                                           PointofsaleRepository pointofsaleRepository,
                                           ClientRepository clientRepository) {
        this.clientCapsAccountRepository = clientCapsAccountRepository;
        this.clientCapsOperationRepository = clientCapsOperationRepository;
        this.userBMRepository = userBMRepository;
        this.articleRepository = articleRepository;
        this.pointofsaleRepository = pointofsaleRepository;
        this.clientRepository = clientRepository;
    }

    public Boolean isClientCapsuleAccountofArticleExistinPos(Long clientId, Long artId){
        if(artId == null || clientId == null){
            log.error("artId or clientId is null");
            throw new NullArgumentException("le artId ou le clientId passe en argument de la methode est null");
        }
        Optional<ClientCapsuleAccount> optionalClientCapsuleAccount = clientCapsAccountRepository.
                findClientCapsuleAccountofArticleinPos(clientId, artId);
        return optionalClientCapsuleAccount.isPresent()?true:false;
    }

    @Override
    public ClientCapsuleAccountDto saveClientCapsuleAccount(ClientCapsuleAccountDto ccaccDto) {

        /*****************************************************************
         * On valide d'abord le parametre pris en argument
         */
        List<String> errors = ClientCapsuleAccountValidator.validate(ccaccDto);
        if(!errors.isEmpty()){
            log.error("Entity ccaccDto not valid {}", ccaccDto);
            throw new InvalidEntityException("Le ccaccDto passé en argument n'est pas valide: "+errors,
                    ErrorCode.CLIENTCAPSULEACCOUNT_NOT_VALID, errors);
        }

        /***************************************************************************
         * Maintenant faut se rassurer que le client precise existe vraiment
         */
        if(ccaccDto.getCcsaClientDto().getId() == null){
            log.error("The id of the client associated cannot be null");
            throw new InvalidEntityException("Le id du client associe au compte capsule ne peut etre null",
                    ErrorCode.CLIENTCAPSULEACCOUNT_NOT_VALID);
        }
        //A ce niveau on est sur que le id du client nest pas null
        Optional<Client> optionalClient = clientRepository.findClientById(
                ccaccDto.getCcsaClientDto().getId());
        if(!optionalClient.isPresent()){
            log.error("The client indicated in the ccaccDto doesn't exist in DB ");
            throw new EntityNotFoundException("Aucun client n'existe avec l'id precise ",
                    ErrorCode.CLIENT_NOT_FOUND);
        }

        /**********************************************************************
         * Maintenant on va se rassurer de l'existance de l'article en BD
         */
        if(ccaccDto.getCcsaArticleDto().getId() == null){
            log.error("The id of the article associated cannot be null");
            throw new InvalidEntityException("Le id du article associe au compte capsule ne peut etre null",
                    ErrorCode.PROVIDERCAPSULEACCOUNT_NOT_VALID);
        }
        //A ce niveau on est sur que le id du article nest pas null
        Optional<Article> optionalArticle = articleRepository.findArticleById(ccaccDto.getCcsaArticleDto().getId());
        if(!optionalArticle.isPresent()){
            log.error("The article indicated in the ccaccDto doesn't exist in DB ");
            throw new EntityNotFoundException("Aucun article n'existe avec l'id precise ",
                    ErrorCode.ARTICLE_NOT_FOUND);
        }

        /*************************************************************************************************
         * On verifie qu'aucun compte capsule n'est pas deja creer pour cet article et ce client
         */
        if(isClientCapsuleAccountofArticleExistinPos(ccaccDto.getCcsaClientDto().getId(),
                ccaccDto.getCcsaArticleDto().getId())){
            log.error("An account for capsule has been already created for this article and this client");
            throw new DuplicateEntityException("Un compte capsule pour cet article et ce client existe deja " +
                    "en BD ", ErrorCode.CLIENTCAPSULEACCOUNT_DUPLICATED);
        }

        log.info("After all verification, the entity {} can be saved without any problem in DB ", ccaccDto);

        return ClientCapsuleAccountDto.fromEntity(
                clientCapsAccountRepository.save(
                        ClientCapsuleAccountDto.toEntity(ccaccDto)
                )
        );
    }

    @Override
    public ClientCapsuleAccountDto findClientCapsuleAccountById(Long ccaccId) {
        if(ccaccId == null){
            log.error("The ccaccId passed as argument is null");
            throw new NullArgumentException("Le ccaccId passe en argument est null");
        }
        Optional<ClientCapsuleAccount> optionalClientCapsuleAccount = clientCapsAccountRepository.
                findClientCapsuleAccountById(ccaccId);
        if(!optionalClientCapsuleAccount.isPresent()){
            log.error("There is no ClientCapsuleAccount with the id {} sent", ccaccId);
            throw new EntityNotFoundException("Aucun ClientCapsuleAccount n'existe avec l'Id passe en parametre "+ccaccId,
                    ErrorCode.CLIENTCAPSULEACCOUNT_NOT_FOUND);
        }

        return ClientCapsuleAccountDto.fromEntity(optionalClientCapsuleAccount.get());
    }

    @Override
    public ClientCapsuleAccountDto findClientCapsuleAccountofArticleinPos(Long clientId, Long artId) {
        if(clientId == null){
            log.error("The ClientId passed as argument is null");
            throw new NullArgumentException("Le ClientId passe en argument est null");
        }
        if(artId == null){
            log.error("The artId passed as argument is null");
            throw new NullArgumentException("Le artId passe en argument est null");
        }

        Optional<ClientCapsuleAccount> optionalClientCapsuleAccount = clientCapsAccountRepository.
                findClientCapsuleAccountofArticleinPos(clientId, artId);
        if(!optionalClientCapsuleAccount.isPresent()){
            log.error("There is no ClientCapsuleAccount with the argument artId =  {} and providerId = {} sent",
                    artId, clientId);
            throw new EntityNotFoundException("Aucun ClientCapsuleAccount n'existe avec les parametres passe en parametre "
                    + "artId = "+artId+ " clientId = "+clientId, ErrorCode.CLIENTCAPSULEACCOUNT_NOT_FOUND);
        }

        return ClientCapsuleAccountDto.fromEntity(optionalClientCapsuleAccount.get());
    }

    @Override
    public List<ClientCapsuleAccountDto> findAllClientCapsuleAccountinPos(Long clientId) {
        if(clientId == null){
            log.error("The ClientId sent as argument is null");
            throw new NullArgumentException("L'argument passe est null");
        }
        Optional<List<ClientCapsuleAccount>> optionalClientCapsuleAccountList = clientCapsAccountRepository.
                findAllClientCapsuleAccountinPos(clientId);
        if(!optionalClientCapsuleAccountList.isPresent()){
            log.error("There is no client with the id={}", clientId);
            throw new EntityNotFoundException("Aucun client n'existe avec l'id passe en argument "+clientId,
                    ErrorCode.CLIENT_NOT_FOUND);
        }

        return optionalClientCapsuleAccountList.get().stream().map(ClientCapsuleAccountDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<ClientCapsuleAccountDto> findPageClientCapsuleAccountinPos(Long clientId, int pagenum, int pagesize) {
        if(clientId == null){
            log.error("The clientId sent as argument is null");
            throw new NullArgumentException("L'argument passe est null");
        }
        Optional<Page<ClientCapsuleAccount>> optionalClientCapsuleAccountPage = clientCapsAccountRepository.
                findPageClientCapsuleAccountinPos(clientId, PageRequest.of(pagenum, pagesize));
        if(!optionalClientCapsuleAccountPage.isPresent()){
            log.error("There is no client with the id posId {}", clientId);
            throw new EntityNotFoundException("Aucun client n'existe avec l'id passe en argument "+clientId,
                    ErrorCode.CLIENT_NOT_FOUND);
        }

        return optionalClientCapsuleAccountPage.get().map(ClientCapsuleAccountDto::fromEntity);
    }

    @Override
    public Boolean isClientCapsuleAccountDeleteable(Long ccaccId) {
        return true;
    }

    @Override
    public Boolean deleteClientCapsuleAccountById(Long ccaccId) {
        if(ccaccId == null){
            log.error("The argument cannot be null for this method please check it");
            throw new NullArgumentException("L'argument passe est null et la methode ne peut donc s'executer");
        }

        Optional<ClientCapsuleAccount> optionalClientCapsuleAccount = clientCapsAccountRepository.
                findClientCapsuleAccountById(ccaccId);
        if(!optionalClientCapsuleAccount.isPresent()){
            log.error("There is no clientCapsuleAccount with the precised id {}", ccaccId);
            throw new EntityNotFoundException("Aucun clientCapsuleAccount n'existe avec l'Id passe en argument dans la BD");
        }
        if(!isClientCapsuleAccountDeleteable(ccaccId)){
            log.error("Le compte capsule ne peut etre supprime");
            throw new EntityNotDeleteableException("Le compte capsule designe ne peut etre supprime",
                    ErrorCode.CLIENTCAPSULEACCOUNT_NOT_DELETEABLE);
        }
        clientCapsAccountRepository.delete(optionalClientCapsuleAccount.get());
        return true;
    }

    @Override
    public Boolean saveCapsuleOperation(Long ccaccId, BigDecimal qte, OperationType operationType,
                                        Long userbmId, String opObject, String opDescription) {

        /******************************************************************
         * Se rassurer que les donnees dans la fonction ne sont pas null
         */
        if(ccaccId == null || qte == null || userbmId == null || operationType == null){
            log.error("ccaccId, qte or even userbmId is null ");
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
        if(!this.isClientCapsuleAccountExistWithId(ccaccId)){
            log.error("The ccaccId {} does not identify any account ", ccaccId);
            throw  new EntityNotFoundException("Aucun ClientCapsuleAccount n'existe avec le ID precise "+ccaccId,
                    ErrorCode.CLIENTCAPSULEACCOUNT_NOT_FOUND);
        }
        Optional<ClientCapsuleAccount> optionalClientCapsuleAccount = clientCapsAccountRepository.
                findClientCapsuleAccountById(ccaccId);
        //A ce niveau on na pas besoin de regarder si isPresent est true car on est sur que ca existe
        ClientCapsuleAccount clientCapsuleAccountToUpdate = optionalClientCapsuleAccount.get();

        BigDecimal solde = clientCapsuleAccountToUpdate.getCcsaNumber();
        BigDecimal updatedSolde = BigDecimal.valueOf(0.0);

        /***
         * On doit ici enregistrer un depot dans un compte capsule d'un client pour un article. Pour cela il
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
        clientCapsuleAccountToUpdate.setCcsaNumber(updatedSolde);

        clientCapsAccountRepository.save(clientCapsuleAccountToUpdate);

        ClientCapsuleOperation ccapso = new ClientCapsuleOperation();
        ccapso.setCltcsoNumberinmvt(qte);
        ccapso.setCltcsoUserbm(optionalUserBM.get());
        ccapso.setCltcsoCltCapsuleAccount(clientCapsuleAccountToUpdate);

        Operation op = new Operation();
        op.setOpDate(new Date().toInstant());
        op.setOpDescription(opDescription);
        op.setOpObject(opObject);
        op.setOpType(operationType);
        ccapso.setCltcsoOperation(op);
        //Il faut save le ClientCapsuleOperation
        clientCapsOperationRepository.save(ccapso);

        return true;
    }

    @Override
    public Boolean saveCapsuleOperation(ClientCapsuleAccountDto clientCapsuleAccountDto,
                                        ClientCapsuleOperationDto clientCapsuleOperationDto) {
        Long ccaccId = clientCapsuleAccountDto.getId();
        BigDecimal qte = clientCapsuleOperationDto.getCltcsoNumberinmvt();
        Long userbmId = clientCapsuleOperationDto.getCltcsoUserbmDto().getId();
        OperationType operationType = clientCapsuleOperationDto.getCltcsoOperationDto().getOpType();
        String opObject = clientCapsuleOperationDto.getCltcsoOperationDto().getOpObject();
        String opDescription = clientCapsuleOperationDto.getCltcsoOperationDto().getOpDescription();
        /******************************************************************
         * Se rassurer que les donnees dans la fonction ne sont pas null
         */
        if(ccaccId == null || qte == null || userbmId == null || operationType == null){
            log.error("ccaccId, qte or even userbmId is null ");
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
        if(!this.isClientCapsuleAccountExistWithId(ccaccId)){
            log.error("The ccaccId {} does not identify any account ", ccaccId);
            throw  new EntityNotFoundException("Aucun ClientCapsuleAccount n'existe avec le ID precise "+ccaccId,
                    ErrorCode.CLIENTCAPSULEACCOUNT_NOT_FOUND);
        }
        Optional<ClientCapsuleAccount> optionalClientCapsuleAccount = clientCapsAccountRepository.
                findClientCapsuleAccountById(ccaccId);
        //A ce niveau on na pas besoin de regarder si isPresent est true car on est sur que ca existe
        ClientCapsuleAccount clientCapsuleAccountToUpdate = optionalClientCapsuleAccount.get();

        BigDecimal solde = clientCapsuleAccountToUpdate.getCcsaNumber();
        BigDecimal updatedSolde = BigDecimal.valueOf(0.0);

        /***
         * On doit ici enregistrer un depot dans un compte capsule d'un client pour un article. Pour cela il
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
        clientCapsuleAccountToUpdate.setCcsaNumber(updatedSolde);

        clientCapsAccountRepository.save(clientCapsuleAccountToUpdate);

        ClientCapsuleOperation ccapso = new ClientCapsuleOperation();
        ccapso.setCltcsoNumberinmvt(qte);
        ccapso.setCltcsoUserbm(optionalUserBM.get());
        ccapso.setCltcsoCltCapsuleAccount(clientCapsuleAccountToUpdate);

        Operation op = new Operation();
        op.setOpDate(new Date().toInstant());
        op.setOpDescription(opDescription);
        op.setOpObject(opObject);
        op.setOpType(operationType);
        ccapso.setCltcsoOperation(op);
        //Il faut save le ClientCapsuleOperation
        clientCapsOperationRepository.save(ccapso);

        return true;
    }

    public Boolean isClientCapsuleAccountExistWithId(Long ccaccId){
        if(ccaccId == null){
            log.error("ccaccId is null");
            throw new NullArgumentException("le ccaccId passe en argument de la methode est null");
        }
        Optional<ClientCapsuleAccount> optionalClientCapsuleAccount = clientCapsAccountRepository.
                findClientCapsuleAccountById(ccaccId);
        return optionalClientCapsuleAccount.isPresent()?true:false;
    }

}
