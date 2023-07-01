package com.c2psi.businessmanagement.services.contractsImpl.client.client;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.client.client.ClientPackagingAccountDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientPackagingOperationDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.*;
import com.c2psi.businessmanagement.repositories.client.client.ClientPackagingAccountRepository;
import com.c2psi.businessmanagement.repositories.client.client.ClientPackagingOperationRepository;
import com.c2psi.businessmanagement.repositories.client.client.ClientRepository;
import com.c2psi.businessmanagement.repositories.pos.userbm.UserBMRepository;
import com.c2psi.businessmanagement.repositories.stock.product.PackagingRepository;
import com.c2psi.businessmanagement.services.contracts.client.client.ClientPackagingAccountService;
import com.c2psi.businessmanagement.validators.client.client.ClientPackagingAccountValidator;
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

@Service(value="ClientPackagingAccountServiceV1")
@Slf4j
@Transactional
public class ClientPackagingAccountServiceImpl implements ClientPackagingAccountService {

    private ClientPackagingAccountRepository clientPackagingAccountRepository;
    private ClientPackagingOperationRepository clientPackagingOperationRepository;
    private UserBMRepository userBMRepository;
    private PackagingRepository packagingRepository;
    private ClientRepository clientRepository;

    @Autowired
    public ClientPackagingAccountServiceImpl(ClientPackagingAccountRepository clientPackagingAccountRepository,
                                             ClientPackagingOperationRepository clientPackagingOperationRepository,
                                             UserBMRepository userBMRepository, PackagingRepository packagingRepository,
                                             ClientRepository clientRepository) {
        this.clientPackagingAccountRepository = clientPackagingAccountRepository;
        this.clientPackagingOperationRepository = clientPackagingOperationRepository;
        this.userBMRepository = userBMRepository;
        this.packagingRepository = packagingRepository;
        this.clientRepository = clientRepository;
    }

    public Boolean isClientPackagingAccountExistinPos(Long packagingId, Long clientId){
        if(packagingId == null || clientId == null){
            log.error("packagingId or clientId is null");
            throw new NullArgumentException("le packagingId ou le clientId passe en argument de la methode est null");
        }
        Optional<ClientPackagingAccount> optionalClientPackagingAccount = clientPackagingAccountRepository.
                findClientPackagingAccount(packagingId, clientId);
        return optionalClientPackagingAccount.isPresent()?true:false;
    }

    @Override
    public ClientPackagingAccountDto saveClientPackagingAccount(ClientPackagingAccountDto cltpackaccDto) {

        /*****************************************************************
         * On valide d'abord le parametre pris en argument
         */
        List<String> errors = ClientPackagingAccountValidator.validate(cltpackaccDto);
        if(!errors.isEmpty()){
            log.error("Entity cltpackaccDto not valid {}", cltpackaccDto);
            throw new InvalidEntityException("Le cltpackaccDto passé en argument n'est pas valide: "+errors,
                    ErrorCode.CLIENTPACKAGINGACCOUNT_NOT_VALID, errors);
        }

        /***************************************************************************
         * Maintenant faut se rassurer que le client precise existe vraiment
         */
        if(cltpackaccDto.getCpaClientDto().getId() == null){
            log.error("The id of the client associated cannot be null");
            throw new InvalidEntityException("Le id du client associe au compte packaging ne peut etre null",
                    ErrorCode.CLIENTPACKAGINGACCOUNT_NOT_VALID);
        }
        //A ce niveau on est sur que le id du client nest pas null
        Optional<Client> optionalClient = clientRepository.findClientById(
                cltpackaccDto.getCpaClientDto().getId());
        if(!optionalClient.isPresent()){
            log.error("The client indicated in the cltpackaccount doesn't exist in DB ");
            throw new EntityNotFoundException("Aucun client n'existe avec l'id precise ",
                    ErrorCode.CLIENT_NOT_FOUND);
        }

        /**********************************************************************
         * Maintenant on va se rassurer de l'existance du packaging en BD
         */
        if(cltpackaccDto.getCpaPackagingDto().getId() == null){
            log.error("The id of the packaging associated cannot be null");
            throw new InvalidEntityException("Le id du packaging associe au compte capsule ne peut etre null",
                    ErrorCode.CLIENTPACKAGINGACCOUNT_NOT_VALID);
        }
        //A ce niveau on est sur que le id du article nest pas null
        Optional<Packaging> optionalPackaging = packagingRepository.findPackagingById(cltpackaccDto.getCpaPackagingDto().getId());
        if(!optionalPackaging.isPresent()){
            log.error("The packaging indicated in the cltpackaccount doesn't exist in DB ");
            throw new EntityNotFoundException("Aucun packaging n'existe avec l'id precise ",
                    ErrorCode.PACKAGING_NOT_FOUND);
        }

        /****************************************************************************************************
         * On verifie que le packaging est dans le meme pointofsale que le client indique dans le compte
         */
        if(!cltpackaccDto.getCpaPackagingDto().getPackPosId().equals(cltpackaccDto.getCpaClientDto().getClientPosId())){
            log.error("The precised packaging and client are not in the same pointofsale precise in the account");
            throw new InvalidEntityException("Le packaging et le client precise pour le compte ne sont pas dans le meme  " +
                    "pointofsale ", ErrorCode.CLIENTPACKAGINGACCOUNT_NOT_VALID);
        }

        /*************************************************************************************************
         * On verifie qu'aucun compte packaging n'est pas deja creer pour ce packaging et ce client
         */
        if(isClientPackagingAccountExistinPos(cltpackaccDto.getCpaPackagingDto().getId(),
                cltpackaccDto.getCpaClientDto().getId())){
            log.error("An account for packaging has been already created for this client");
            throw new DuplicateEntityException("Un compte packaging pour ce packaging et ce client existe deja " +
                    "en BD ", ErrorCode.CLIENTPACKAGINGACCOUNT_DUPLICATED);
        }

        log.info("After all verification, the entity {} can be saved without any problem in DB ", cltpackaccDto);


        return ClientPackagingAccountDto.fromEntity(
                clientPackagingAccountRepository.save(
                        ClientPackagingAccountDto.toEntity(cltpackaccDto)
                )
        );
    }

    @Override
    public ClientPackagingAccountDto findClientPackagingAccountById(Long cltpackaccId) {
        if(cltpackaccId == null){
            log.error("The cltpackaccId passed as argument is null");
            throw new NullArgumentException("Le cltpackaccId passe en argument est null");
        }
        Optional<ClientPackagingAccount> optionalClientPackagingAccount = clientPackagingAccountRepository.
                findClientPackagingAccountById(cltpackaccId);
        if(!optionalClientPackagingAccount.isPresent()){
            log.error("There is no ClientPackagingAccount with the id {} sent", cltpackaccId);
            throw new EntityNotFoundException("Aucun ClientPackagingAccount n'existe avec l'Id passe en parametre "+cltpackaccId,
                    ErrorCode.CLIENTPACKAGINGACCOUNT_NOT_FOUND);
        }

        return ClientPackagingAccountDto.fromEntity(optionalClientPackagingAccount.get());
    }

    @Override
    public ClientPackagingAccountDto findClientPackagingAccountByClientAndPackaging(Long packagingId, Long clientId) {
        if(clientId == null){
            log.error("The clientId passed as argument is null");
            throw new NullArgumentException("Le clientId passe en argument est null");
        }
        if(packagingId == null){
            log.error("The packagingId passed as argument is null");
            throw new NullArgumentException("Le packagingId passe en argument est null");
        }

        Optional<ClientPackagingAccount> optionalClientPackagingAccount = clientPackagingAccountRepository.
                findClientPackagingAccount(packagingId, clientId);
        if(!optionalClientPackagingAccount.isPresent()){
            log.error("There is no ClientPackagingAccount with the argument packagingId =  {} and clientId = {} sent", packagingId, clientId);
            throw new EntityNotFoundException("Aucun ClientPackagingAccount n'existe avec les parametres passe en parametre " +
                    "packagingId = "+packagingId+ " clientId = "+clientId, ErrorCode.CLIENTPACKAGINGACCOUNT_NOT_FOUND);
        }

        return ClientPackagingAccountDto.fromEntity(optionalClientPackagingAccount.get());
    }

    @Override
    public List<ClientPackagingAccountDto> findAllPackagingAccountofClient(Long clientId) {
        if(clientId == null){
            log.error("The clientId sent as argument is null");
            throw new NullArgumentException("L'argument passe est null");
        }
        Optional<List<ClientPackagingAccount>> optionalClientPackagingAccountList = clientPackagingAccountRepository.
                findAllPackagingAccountofClient(clientId);
        if(!optionalClientPackagingAccountList.isPresent()){
            log.error("There is no client with the id posId");
            throw new EntityNotFoundException("Aucun client n'existe avec l'id passe en argument "+clientId,
                    ErrorCode.CLIENT_NOT_FOUND);
        }

        return optionalClientPackagingAccountList.get().stream().map(ClientPackagingAccountDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<ClientPackagingAccountDto> findPagePackagingAccountofClient(Long clientId, int pagenum, int pagesize) {
        if(clientId == null){
            log.error("The clientId sent as argument is null");
            throw new NullArgumentException("L'argument passe est null");
        }
        Optional<Page<ClientPackagingAccount>> optionalClientPackagingAccountPage = clientPackagingAccountRepository.
                findPagePackagingAccountofClient(clientId, PageRequest.of(pagenum, pagesize));
        if(!optionalClientPackagingAccountPage.isPresent()){
            log.error("There is no client with the id posId {}", clientId);
            throw new EntityNotFoundException("Aucun client n'existe avec l'id passe en argument "+clientId,
                    ErrorCode.CLIENT_NOT_FOUND);
        }

        return optionalClientPackagingAccountPage.get().map(ClientPackagingAccountDto::fromEntity);
    }

    @Override
    public List<ClientPackagingAccountDto> findAllClientPackagingAccountinPos(Long posId) {
        if(posId == null){
            log.error("The posId sent as argument is null");
            throw new NullArgumentException("L'argument posId passe est null");
        }
        Optional<List<ClientPackagingAccount>> optionalClientPackagingAccountList = clientPackagingAccountRepository.
                findAllPackagingAccountinPos(posId);
        if(!optionalClientPackagingAccountList.isPresent()){
            log.error("There is no client with the id posId");
            throw new EntityNotFoundException("Aucun client n'existe avec l'id passe en argument "+posId,
                    ErrorCode.CLIENT_NOT_FOUND);
        }

        return optionalClientPackagingAccountList.get().stream().map(ClientPackagingAccountDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<ClientPackagingAccountDto> findPageClientPackagingAccountinPos(Long posId, int pagenum, int pagesize) {
        if(posId == null){
            log.error("The posId sent as argument is null");
            throw new NullArgumentException("L'argument posId passe est null");
        }
        Optional<Page<ClientPackagingAccount>> optionalClientPackagingAccountPage = clientPackagingAccountRepository.
                findPagePackagingAccountinPos(posId, PageRequest.of(pagenum, pagesize));
        if(!optionalClientPackagingAccountPage.isPresent()){
            log.error("There is no client with the id posId {}", posId);
            throw new EntityNotFoundException("Aucun client n'existe avec l'id passe en argument "+posId,
                    ErrorCode.CLIENT_NOT_FOUND);
        }

        return optionalClientPackagingAccountPage.get().map(ClientPackagingAccountDto::fromEntity);
    }

    @Override
    public Boolean deleteClientPackagingAccountById(Long cltpackaccId) {
        if(cltpackaccId == null){
            log.error("The argument cannot be null for this method please check it");
            throw new NullArgumentException("L'argument passe est null et la methode ne peut donc s'executer");
        }

        Optional<ClientPackagingAccount> optionalClientPackagingAccount = clientPackagingAccountRepository.
                findClientPackagingAccountById(cltpackaccId);
        if(!optionalClientPackagingAccount.isPresent()){
            log.error("There is no clientPackagingAccount with the precised id {}", cltpackaccId);
            throw new EntityNotFoundException("Aucun cltPackagingAccount n'existe avec l'Id passe en argument dans la BD");
        }
        if(!isClientPackagingAccountDeleteable(cltpackaccId)){
            log.error("Le compte packaging ne peut etre supprime");
            throw new EntityNotDeleteableException("Le compte packaging designe ne peut etre supprime",
                    ErrorCode.CLIENTPACKAGINGACCOUNT_NOT_DELETEABLE);
        }
        clientPackagingAccountRepository.delete(optionalClientPackagingAccount.get());
        return true;
    }

    @Override
    public Boolean isClientPackagingAccountDeleteable(Long cltpackaccId) {
        return true;
    }

    @Override
    public Boolean savePackagingOperationforClient(Long cltpackaccId, BigDecimal qte, OperationType operationType,
                                                   Long userbmId, String opObject, String opDescription) {

        /******************************************************************
         * Se rassurer que les donnees dans la fonction ne sont pas null
         */
        if(cltpackaccId == null || qte == null || userbmId == null || operationType == null){
            log.error("cltpackaccId, qte or even userbmId is null ");
            throw new NullArgumentException("Appel de la methode savePackagingOperationforClient avec des parametres null");
        }

        /*************************************************************************************
         * Se rassurer que la quantite de packaging dans l'operation est strictement positive
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
        if(!this.isClientPackagingAccountExistWithId(cltpackaccId)){
            log.error("The cltpackaccId {} does not identify any account ", cltpackaccId);
            throw  new EntityNotFoundException("Aucun ClientPackagingAccount n'existe avec le ID precise "+cltpackaccId,
                    ErrorCode.CLIENTPACKAGINGACCOUNT_NOT_FOUND);
        }
        Optional<ClientPackagingAccount> optionalClientPackagingAccount = clientPackagingAccountRepository.
                findClientPackagingAccountById(cltpackaccId);
        //A ce niveau on na pas besoin de regarder si isPresent est true car on est sur que ca existe
        ClientPackagingAccount clientPackagingAccountToUpdate = optionalClientPackagingAccount.get();

        BigDecimal solde = clientPackagingAccountToUpdate.getCpaNumber();
        BigDecimal updatedSolde = BigDecimal.valueOf(0.0);

        /***
         * On doit ici enregistrer un depot dans un compte packaging d'un client. Pour cela il
         * faut ajouter la qte de la transaction au solde du compte et ensuite enregistrer l'operation ainsi
         * realise
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
        clientPackagingAccountToUpdate.setCpaNumber(updatedSolde);

        clientPackagingAccountRepository.save(clientPackagingAccountToUpdate);

        ClientPackagingOperation cltpackop = new ClientPackagingOperation();
        cltpackop.setCltpoNumberinmvt(qte);
        cltpackop.setCltpoUserbm(optionalUserBM.get());
        cltpackop.setCltpoCltPackagingAccount(clientPackagingAccountToUpdate);

        Operation op = new Operation();
        op.setOpDate(new Date().toInstant());
        op.setOpDescription(opDescription);
        op.setOpObject(opObject);
        op.setOpType(operationType);
        cltpackop.setCltpoOperation(op);
        //Il faut save le ClientPackagingOperation
        clientPackagingOperationRepository.save(cltpackop);

        return true;
    }

    @Override
    public Boolean savePackagingOperationforClient(ClientPackagingAccountDto clientPackAccDto,
                                                  ClientPackagingOperationDto clientPackOpDto) {

        Long cltpackaccId = clientPackAccDto.getId();
        BigDecimal qte = clientPackOpDto.getCltpoNumberinmvt();
        Long userbmId  = clientPackOpDto.getCltpoUserbmDto().getId();
        OperationType operationType = clientPackOpDto.getCltpoOperationDto().getOpType();
        String opObject = clientPackOpDto.getCltpoOperationDto().getOpObject();
        String opDescription = clientPackOpDto.getCltpoOperationDto().getOpDescription();
        /******************************************************************
         * Se rassurer que les donnees dans la fonction ne sont pas null
         */
        if(cltpackaccId == null || qte == null || userbmId == null || operationType == null){
            log.error("cltpackaccId, qte or even userbmId is null ");
            throw new NullArgumentException("Appel de la methode savePackagingOperationforClient avec des parametres null");
        }

        /*************************************************************************************
         * Se rassurer que la quantite de packaging dans l'operation est strictement positive
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
        if(!this.isClientPackagingAccountExistWithId(cltpackaccId)){
            log.error("The cltpackaccId {} does not identify any account ", cltpackaccId);
            throw  new EntityNotFoundException("Aucun ClientPackagingAccount n'existe avec le ID precise "+cltpackaccId,
                    ErrorCode.CLIENTPACKAGINGACCOUNT_NOT_FOUND);
        }
        Optional<ClientPackagingAccount> optionalClientPackagingAccount = clientPackagingAccountRepository.
                findClientPackagingAccountById(cltpackaccId);
        //A ce niveau on na pas besoin de regarder si isPresent est true car on est sur que ca existe
        ClientPackagingAccount clientPackagingAccountToUpdate = optionalClientPackagingAccount.get();

        BigDecimal solde = clientPackagingAccountToUpdate.getCpaNumber();
        BigDecimal updatedSolde = BigDecimal.valueOf(0.0);

        /***
         * On doit ici enregistrer un depot dans un compte packaging d'un client. Pour cela il
         * faut ajouter la qte de la transaction au solde du compte et ensuite enregistrer l'operation ainsi
         * realise
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
        clientPackagingAccountToUpdate.setCpaNumber(updatedSolde);

        clientPackagingAccountRepository.save(clientPackagingAccountToUpdate);

        ClientPackagingOperation cltpackop = new ClientPackagingOperation();
        cltpackop.setCltpoNumberinmvt(qte);
        cltpackop.setCltpoUserbm(optionalUserBM.get());
        cltpackop.setCltpoCltPackagingAccount(clientPackagingAccountToUpdate);

        Operation op = new Operation();
        op.setOpDate(new Date().toInstant());
        op.setOpDescription(opDescription);
        op.setOpObject(opObject);
        op.setOpType(operationType);
        cltpackop.setCltpoOperation(op);
        //Il faut save le ClientPackagingOperation
        clientPackagingOperationRepository.save(cltpackop);

        return true;
    }

    @Override
    public Boolean savePackagingOperationforClient(ClientPackagingOperationDto clientPackOpDto) {
        Long cltpackaccId = clientPackOpDto.getCltpoCltPackagingAccountDto().getId();
        BigDecimal qte = clientPackOpDto.getCltpoNumberinmvt();
        Long userbmId  = clientPackOpDto.getCltpoUserbmDto().getId();
        OperationType operationType = clientPackOpDto.getCltpoOperationDto().getOpType();
        String opObject = clientPackOpDto.getCltpoOperationDto().getOpObject();
        String opDescription = clientPackOpDto.getCltpoOperationDto().getOpDescription();
        /******************************************************************
         * Se rassurer que les donnees dans la fonction ne sont pas null
         */
        if(cltpackaccId == null || qte == null || userbmId == null || operationType == null){
            log.error("cltpackaccId, qte or even userbmId is null ");
            throw new NullArgumentException("Appel de la methode savePackagingOperationforClient avec des parametres null");
        }

        /*************************************************************************************
         * Se rassurer que la quantite de packaging dans l'operation est strictement positive
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
        if(!this.isClientPackagingAccountExistWithId(cltpackaccId)){
            log.error("The cltpackaccId {} does not identify any account ", cltpackaccId);
            throw  new EntityNotFoundException("Aucun ClientPackagingAccount n'existe avec le ID precise "+cltpackaccId,
                    ErrorCode.CLIENTPACKAGINGACCOUNT_NOT_FOUND);
        }
        Optional<ClientPackagingAccount> optionalClientPackagingAccount = clientPackagingAccountRepository.
                findClientPackagingAccountById(cltpackaccId);
        //A ce niveau on na pas besoin de regarder si isPresent est true car on est sur que ca existe
        ClientPackagingAccount clientPackagingAccountToUpdate = optionalClientPackagingAccount.get();

        BigDecimal solde = clientPackagingAccountToUpdate.getCpaNumber();
        BigDecimal updatedSolde = BigDecimal.valueOf(0.0);

        /***
         * On doit ici enregistrer un depot dans un compte packaging d'un client. Pour cela il
         * faut ajouter la qte de la transaction au solde du compte et ensuite enregistrer l'operation ainsi
         * realise
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
        clientPackagingAccountToUpdate.setCpaNumber(updatedSolde);

        clientPackagingAccountRepository.save(clientPackagingAccountToUpdate);

        ClientPackagingOperation cltpackop = new ClientPackagingOperation();
        cltpackop.setCltpoNumberinmvt(qte);
        cltpackop.setCltpoUserbm(optionalUserBM.get());
        cltpackop.setCltpoCltPackagingAccount(clientPackagingAccountToUpdate);

        Operation op = new Operation();
        op.setOpDate(new Date().toInstant());
        op.setOpDescription(opDescription);
        op.setOpObject(opObject);
        op.setOpType(operationType);
        cltpackop.setCltpoOperation(op);
        //Il faut save le ClientPackagingOperation
        clientPackagingOperationRepository.save(cltpackop);

        return true;
    }

    public Boolean isClientPackagingAccountExistWithId(Long cltpackId){
        if(cltpackId == null){
            log.error("cltpackId is null");
            throw new NullArgumentException("le cltpackId passe en argument de la methode est null");
        }
        Optional<ClientPackagingAccount> optionalClientPackagingAccount = clientPackagingAccountRepository.
                findClientPackagingAccountById(cltpackId);
        return optionalClientPackagingAccount.isPresent()?true:false;
    }

}
