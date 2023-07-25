package com.c2psi.businessmanagement.services.contractsImpl.client.client;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.client.client.ClientDamageAccountDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientDamageOperationDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.*;
import com.c2psi.businessmanagement.repositories.client.client.*;
import com.c2psi.businessmanagement.repositories.pos.pos.PointofsaleRepository;
import com.c2psi.businessmanagement.repositories.pos.userbm.UserBMRepository;
import com.c2psi.businessmanagement.repositories.stock.product.ArticleRepository;
import com.c2psi.businessmanagement.services.contracts.client.client.ClientDamageAccountService;
import com.c2psi.businessmanagement.validators.client.client.ClientDamageAccountValidator;
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

@Service(value="ClientDamageAccountServiceV1")
@Slf4j
@Transactional
public class ClientDamageAccountServiceImpl implements ClientDamageAccountService {

    private ClientDamageAccountRepository clientDamageAccountRepository;
    private ClientDamageOperationRepository clientDamageOperationRepository;
    private UserBMRepository userBMRepository;
    private ArticleRepository articleRepository;
    private PointofsaleRepository pointofsaleRepository;
    private ClientRepository clientRepository;

    @Autowired
    public ClientDamageAccountServiceImpl(ClientDamageAccountRepository clientDamageAccountRepository,
                                          ClientDamageOperationRepository clientDamageOperationRepository,
                                          UserBMRepository userBMRepository, ArticleRepository articleRepository,
                                          PointofsaleRepository pointofsaleRepository,
                                          ClientRepository clientRepository) {
        this.clientDamageAccountRepository = clientDamageAccountRepository;
        this.clientDamageOperationRepository = clientDamageOperationRepository;
        this.userBMRepository = userBMRepository;
        this.articleRepository = articleRepository;
        this.pointofsaleRepository = pointofsaleRepository;
        this.clientRepository = clientRepository;
    }

    public Boolean isClientDamageAccountofArticleExistinPos(Long clientId, Long artId){
        if(artId == null || clientId == null){
            log.error("artId or clientId is null");
            throw new NullArgumentException("le artId ou le clientId passe en argument de la methode est null");
        }
        Optional<ClientDamageAccount> optionalClientDamageAccount = clientDamageAccountRepository.
                findClientDamageAccountofArticleinPos(clientId, artId);
        return optionalClientDamageAccount.isPresent()?true:false;
    }

    @Override
    public ClientDamageAccountDto saveClientDamageAccount(ClientDamageAccountDto cdaccDto) {

        /*****************************************************************
         * On valide d'abord le parametre pris en argument
         */
        List<String> errors = ClientDamageAccountValidator.validate(cdaccDto);
        if(!errors.isEmpty()){
            log.error("Entity cdaccDto not valid {}", cdaccDto);
            throw new InvalidEntityException("Le cdaccDto passé en argument n'est pas valide: "+errors,
                    ErrorCode.CLIENTDAMAGEACCOUNT_NOT_VALID, errors);
        }

        /***************************************************************************
         * Maintenant faut se rassurer que le client precise existe vraiment
         */
        if(cdaccDto.getCdaClientDto().getId() == null){
            log.error("The id of the client associated cannot be null");
            throw new InvalidEntityException("Le id du client associe au compte damage ne peut etre null",
                    ErrorCode.CLIENTDAMAGEACCOUNT_NOT_VALID);
        }
        //A ce niveau on est sur que le id du client nest pas null
        Optional<Client> optionalClient = clientRepository.findClientById(
                cdaccDto.getCdaClientDto().getId());
        if(!optionalClient.isPresent()){
            log.error("The client indicated in the cdaccDto doesn't exist in DB ");
            throw new EntityNotFoundException("Aucun client n'existe avec l'id precise ",
                    ErrorCode.CLIENT_NOT_FOUND);
        }

        /**********************************************************************
         * Maintenant on va se rassurer de l'existance de l'article en BD
         */
        if(cdaccDto.getCdaXArticleDto().getId() == null){
            log.error("The id of the article associated cannot be null");
            throw new InvalidEntityException("Le id du article associe au compte capsule ne peut etre null",
                    ErrorCode.PROVIDERDAMAGEACCOUNT_NOT_VALID);
        }
        //A ce niveau on est sur que le id du article nest pas null
        Optional<Article> optionalArticle = articleRepository.findArticleById(cdaccDto.getCdaXArticleDto().getId());
        if(!optionalArticle.isPresent()){
            log.error("The article indicated in the cdaccDto doesn't exist in DB ");
            throw new EntityNotFoundException("Aucun article n'existe avec l'id precise ",
                    ErrorCode.ARTICLE_NOT_FOUND);
        }

        /*************************************************************************************************
         * On verifie qu'aucun compte capsule n'est pas deja creer pour cet article et ce client
         */
        if(isClientDamageAccountofArticleExistinPos(cdaccDto.getCdaClientDto().getId(),
                cdaccDto.getCdaXArticleDto().getId())){
            log.error("An account for damage has been already created for this article and this client");
            throw new DuplicateEntityException("Un compte damage pour cet article et ce client existe deja " +
                    "en BD ", ErrorCode.CLIENTDAMAGEACCOUNT_DUPLICATED);
        }

        log.info("After all verification, the entity {} can be saved without any problem in DB ", cdaccDto);

        return ClientDamageAccountDto.fromEntity(
                clientDamageAccountRepository.save(
                        ClientDamageAccountDto.toEntity(cdaccDto)
                )
        );
    }

    @Override
    public ClientDamageAccountDto findClientDamageAccountById(Long cdaccId) {
        if(cdaccId == null){
            log.error("The cdaccId passed as argument is null");
            throw new NullArgumentException("Le cdaccId passe en argument est null");
        }
        Optional<ClientDamageAccount> optionalClientDamageAccount = clientDamageAccountRepository.
                findClientDamageAccountById(cdaccId);
        if(!optionalClientDamageAccount.isPresent()){
            log.error("There is no ClientDamageAccount with the id {} sent", cdaccId);
            throw new EntityNotFoundException("Aucun ClientDamageAccount n'existe avec l'Id passe en parametre "+cdaccId,
                    ErrorCode.CLIENTDAMAGEACCOUNT_NOT_FOUND);
        }

        return ClientDamageAccountDto.fromEntity(optionalClientDamageAccount.get());
    }

    @Override
    public ClientDamageAccountDto findClientDamageAccountofArticleinPos(Long clientId, Long artId) {
        if(clientId == null){
            log.error("The ClientId passed as argument is null");
            throw new NullArgumentException("Le ClientId passe en argument est null");
        }
        if(artId == null){
            log.error("The artId passed as argument is null");
            throw new NullArgumentException("Le artId passe en argument est null");
        }

        Optional<ClientDamageAccount> optionalClientDamageAccount = clientDamageAccountRepository.
                findClientDamageAccountofArticleinPos(clientId, artId);
        if(!optionalClientDamageAccount.isPresent()){
            log.error("There is no ClientDamageAccount with the argument artId =  {} and providerId = {} sent",
                    artId, clientId);
            throw new EntityNotFoundException("Aucun ClientDamageAccount n'existe avec les parametres passe en parametre "
                    + "artId = "+artId+ " clientId = "+clientId, ErrorCode.CLIENTDAMAGEACCOUNT_NOT_FOUND);
        }

        return ClientDamageAccountDto.fromEntity(optionalClientDamageAccount.get());
    }

    @Override
    public List<ClientDamageAccountDto> findAllClientDamageAccountinPos(Long clientId) {
        if(clientId == null){
            log.error("The ClientId sent as argument is null");
            throw new NullArgumentException("L'argument passe est null");
        }
        Optional<List<ClientDamageAccount>> optionalClientDamageAccountList = clientDamageAccountRepository.
                findAllClientDamageAccountinPos(clientId);
        if(!optionalClientDamageAccountList.isPresent()){
            log.error("There is no client with the id={}", clientId);
            throw new EntityNotFoundException("Aucun client n'existe avec l'id passe en argument "+clientId,
                    ErrorCode.CLIENT_NOT_FOUND);
        }

        return optionalClientDamageAccountList.get().stream().map(ClientDamageAccountDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<ClientDamageAccountDto> findPageClientDamageAccountinPos(Long clientId, int pagenum, int pagesize) {
        if(clientId == null){
            log.error("The clientId sent as argument is null");
            throw new NullArgumentException("L'argument passe est null");
        }
        Optional<Page<ClientDamageAccount>> optionalClientDamageAccountPage = clientDamageAccountRepository.
                findPageClientDamageAccountinPos(clientId, PageRequest.of(pagenum, pagesize));
        if(!optionalClientDamageAccountPage.isPresent()){
            log.error("There is no client with the id posId {}", clientId);
            throw new EntityNotFoundException("Aucun client n'existe avec l'id passe en argument "+clientId,
                    ErrorCode.CLIENT_NOT_FOUND);
        }

        return optionalClientDamageAccountPage.get().map(ClientDamageAccountDto::fromEntity);
    }

    @Override
    public Boolean isClientDamageAccountDeleteable(Long cdaccId) {
        return true;
    }

    @Override
    public Boolean deleteClientDamageAccountById(Long cdaccId) {
        if(cdaccId == null){
            log.error("The argument cannot be null for this method please check it");
            throw new NullArgumentException("L'argument passe est null et la methode ne peut donc s'executer");
        }

        Optional<ClientDamageAccount> optionalClientDamageAccount = clientDamageAccountRepository.
                findClientDamageAccountById(cdaccId);
        if(!optionalClientDamageAccount.isPresent()){
            log.error("There is no clientDamageAccount with the precised id {}", cdaccId);
            throw new EntityNotFoundException("Aucun clientDamageAccount n'existe avec l'Id passe en argument dans la BD");
        }
        if(!isClientDamageAccountDeleteable(cdaccId)){
            log.error("Le compte damage ne peut etre supprime");
            throw new EntityNotDeleteableException("Le compte damage designe ne peut etre supprime",
                    ErrorCode.CLIENTDAMAGEACCOUNT_NOT_DELETEABLE);
        }
        clientDamageAccountRepository.delete(optionalClientDamageAccount.get());
        return true;
    }

    @Override
    public Boolean saveDamageOperation(Long cdaccId, BigDecimal qte, OperationType operationType,
                                       Long userbmId, String opObject, String opDescription) {

        /******************************************************************
         * Se rassurer que les donnees dans la fonction ne sont pas null
         */
        if(cdaccId == null || qte == null || userbmId == null || operationType == null){
            log.error("cdaccId, qte or even userbmId is null ");
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
        if(!this.isClientDamageAccountExistWithId(cdaccId)){
            log.error("The cdaccId {} does not identify any account ", cdaccId);
            throw  new EntityNotFoundException("Aucun ClientCapsuleAccount n'existe avec le ID precise "+cdaccId,
                    ErrorCode.CLIENTDAMAGEACCOUNT_NOT_FOUND);
        }
        Optional<ClientDamageAccount> optionalClientDamageAccount = clientDamageAccountRepository.
                findClientDamageAccountById(cdaccId);
        //A ce niveau on na pas besoin de regarder si isPresent est true car on est sur que ca existe
        ClientDamageAccount clientDamageAccountToUpdate = optionalClientDamageAccount.get();

        BigDecimal solde = clientDamageAccountToUpdate.getCdaNumber();
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
        clientDamageAccountToUpdate.setCdaNumber(updatedSolde);

        clientDamageAccountRepository.save(clientDamageAccountToUpdate);

        ClientDamageOperation cdapso = new ClientDamageOperation();
        cdapso.setCltdoNumberinmvt(qte);
        cdapso.setCltdoUserbm(optionalUserBM.get());
        cdapso.setCltdoCltDamageAccount(clientDamageAccountToUpdate);

        Operation op = new Operation();
        op.setOpDate(new Date().toInstant());
        op.setOpDescription(opDescription);
        op.setOpObject(opObject);
        op.setOpType(operationType);
        cdapso.setCltdoOperation(op);
        //Il faut save le ClientCapsuleOperation
        clientDamageOperationRepository.save(cdapso);

        return true;
    }

    @Override
    public Boolean saveDamageOperation(ClientDamageAccountDto cltdamaccDto, ClientDamageOperationDto cltcapopDto) {
        Long cdaccId = cltdamaccDto.getId();
        BigDecimal qte = cltcapopDto.getCltdoNumberinmvt();
        Long userbmId = cltcapopDto.getCltdoUserbmDto().getId();
        OperationType operationType = cltcapopDto.getCltdoOperationDto().getOpType();
        String opObject = cltcapopDto.getCltdoOperationDto().getOpObject();
        String opDescription = cltcapopDto.getCltdoOperationDto().getOpDescription();
        /******************************************************************
         * Se rassurer que les donnees dans la fonction ne sont pas null
         */
        if(cdaccId == null || qte == null || userbmId == null || operationType == null){
            log.error("cdaccId, qte or even userbmId is null ");
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
        if(!this.isClientDamageAccountExistWithId(cdaccId)){
            log.error("The cdaccId {} does not identify any account ", cdaccId);
            throw  new EntityNotFoundException("Aucun ClientCapsuleAccount n'existe avec le ID precise "+cdaccId,
                    ErrorCode.CLIENTDAMAGEACCOUNT_NOT_FOUND);
        }
        Optional<ClientDamageAccount> optionalClientDamageAccount = clientDamageAccountRepository.
                findClientDamageAccountById(cdaccId);
        //A ce niveau on na pas besoin de regarder si isPresent est true car on est sur que ca existe
        ClientDamageAccount clientDamageAccountToUpdate = optionalClientDamageAccount.get();

        BigDecimal solde = clientDamageAccountToUpdate.getCdaNumber();
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
        clientDamageAccountToUpdate.setCdaNumber(updatedSolde);

        clientDamageAccountRepository.save(clientDamageAccountToUpdate);

        ClientDamageOperation cdapso = new ClientDamageOperation();
        cdapso.setCltdoNumberinmvt(qte);
        cdapso.setCltdoUserbm(optionalUserBM.get());
        cdapso.setCltdoCltDamageAccount(clientDamageAccountToUpdate);

        Operation op = new Operation();
        op.setOpDate(new Date().toInstant());
        op.setOpDescription(opDescription);
        op.setOpObject(opObject);
        op.setOpType(operationType);
        cdapso.setCltdoOperation(op);
        //Il faut save le ClientCapsuleOperation
        clientDamageOperationRepository.save(cdapso);

        return true;
    }

    @Override
    public Boolean saveDamageOperation(ClientDamageOperationDto cltdamopDto) {

        Long cdaccId = cltdamopDto.getCltdoCltDamageAccountDto().getId();
        BigDecimal qte = cltdamopDto.getCltdoNumberinmvt();
        Long userbmId = cltdamopDto.getCltdoUserbmDto().getId();
        OperationType operationType = cltdamopDto.getCltdoOperationDto().getOpType();
        String opObject = cltdamopDto.getCltdoOperationDto().getOpObject();
        String opDescription = cltdamopDto.getCltdoOperationDto().getOpDescription();
        /******************************************************************
         * Se rassurer que les donnees dans la fonction ne sont pas null
         */
        if(cdaccId == null || qte == null || userbmId == null || operationType == null){
            log.error("cdaccId, qte or even userbmId is null ");
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
        if(!this.isClientDamageAccountExistWithId(cdaccId)){
            log.error("The cdaccId {} does not identify any account ", cdaccId);
            throw  new EntityNotFoundException("Aucun ClientCapsuleAccount n'existe avec le ID precise "+cdaccId,
                    ErrorCode.CLIENTDAMAGEACCOUNT_NOT_FOUND);
        }
        Optional<ClientDamageAccount> optionalClientDamageAccount = clientDamageAccountRepository.
                findClientDamageAccountById(cdaccId);
        //A ce niveau on na pas besoin de regarder si isPresent est true car on est sur que ca existe
        ClientDamageAccount clientDamageAccountToUpdate = optionalClientDamageAccount.get();

        BigDecimal solde = clientDamageAccountToUpdate.getCdaNumber();
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
        clientDamageAccountToUpdate.setCdaNumber(updatedSolde);

        clientDamageAccountRepository.save(clientDamageAccountToUpdate);

        ClientDamageOperation cdapso = new ClientDamageOperation();
        cdapso.setCltdoNumberinmvt(qte);
        cdapso.setCltdoUserbm(optionalUserBM.get());
        cdapso.setCltdoCltDamageAccount(clientDamageAccountToUpdate);

        Operation op = new Operation();
        op.setOpDate(new Date().toInstant());
        op.setOpDescription(opDescription);
        op.setOpObject(opObject);
        op.setOpType(operationType);
        cdapso.setCltdoOperation(op);
        //Il faut save le ClientCapsuleOperation
        clientDamageOperationRepository.save(cdapso);

        return true;
    }

    public Boolean isClientDamageAccountExistWithId(Long cdaccId){
        if(cdaccId == null){
            log.error("cdaccId is null");
            throw new NullArgumentException("le cdaccId passe en argument de la methode est null");
        }
        Optional<ClientDamageAccount> optionalClientDamageAccount = clientDamageAccountRepository.
                findClientDamageAccountById(cdaccId);
        return optionalClientDamageAccount.isPresent()?true:false;
    }

}
