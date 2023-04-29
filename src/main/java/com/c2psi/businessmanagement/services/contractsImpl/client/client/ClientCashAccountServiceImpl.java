package com.c2psi.businessmanagement.services.contractsImpl.client.client;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.client.client.ClientCashAccountDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.*;
import com.c2psi.businessmanagement.repositories.client.client.ClientCashAccountRepository;
import com.c2psi.businessmanagement.repositories.client.client.ClientCashOperationRepository;
import com.c2psi.businessmanagement.repositories.pos.userbm.UserBMRepository;
import com.c2psi.businessmanagement.services.contracts.client.client.ClientCashAccountService;
import com.c2psi.businessmanagement.validators.client.client.ClientCashAccountValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service(value="ClientCashAccountServiceV1")
@Slf4j
@Transactional
public class ClientCashAccountServiceImpl implements ClientCashAccountService {
    private ClientCashAccountRepository clientCashAccountRepository;
    private UserBMRepository userBMRepository;
    private ClientCashOperationRepository clientCashOperationRepository;

    @Autowired
    public ClientCashAccountServiceImpl(ClientCashAccountRepository clientCashAccountRepository,
                                        UserBMRepository userBMRepository,
                                        ClientCashOperationRepository clientCashOperationRepository) {
        this.clientCashAccountRepository = clientCashAccountRepository;
        this.userBMRepository = userBMRepository;
        this.clientCashOperationRepository = clientCashOperationRepository;
    }

    @Override
    public Boolean saveCashOperation(Long ccaId, BigDecimal amount, OperationType operationType, Long userbmId,
                                     String opObject, String opDescription) {
        /***************************************************************************************
         * Il faut se rassurer que les parametres sensible de l'operation ne sont pas null
         */
        if(ccaId == null || amount == null || userbmId == null || operationType == null){
            log.error("ccaId, amount or even userbmId is null ");
            throw new NullArgumentException("Appel de la methode saveCashDeposit avec des parametres null");
        }

        /******************************************************************************
         * On verifie que la valeur ou le montant en jeu est strictement superieur a 0
         */
        if(amount.compareTo(BigDecimal.valueOf(0)) <= 0){
            log.error("The amount cannot be negative value");
            throw new InvalidValueException("La valeur du montant ne saurait etre negative");
        }

        /******************************************************************************************
         * On se rassure que le type d'operation precise est soit le retrait soit le depot car
         * ce sont les seuls operations possible sur un compte cash
         */
        if(!operationType.equals(OperationType.Credit) && !operationType.equals(OperationType.Withdrawal)){
            log.error("The operationType is not recognized for this operation");
            throw new InvalidValueException("Le type d'operation precise n'est pas valide dans cette fonction ");
        }

        /***********************************************************
         * On doit recuperer le UserBM qui effectue l'operation ou
         * lancer une exception sil n'existe pas
         */
        Optional<UserBM> optionalUserBM = userBMRepository.findUserBMById(userbmId);
        if(!optionalUserBM.isPresent()){
            log.error("There is no UserBM in DB with the id pass as argument");
            throw new EntityNotFoundException("Aucun userBM n'existe avec l'id envoye ", ErrorCode.USERBM_NOT_FOUND);
        }
        UserBM userAssociate = optionalUserBM.get();

        /**********************************************************
         * On recupere le compte qui sera affecte par l'operation
         */
        Optional<ClientCashAccount> optionalClientCashAccount =
                clientCashAccountRepository.findClientCashAccountById(ccaId);
        if(!optionalClientCashAccount.isPresent()){
            log.error("There is no clientcashAccount in DB that match with the id passed");
            throw new EntityNotFoundException("Aucun compte cash client n'existe avec l'id passe en argument ",
                    ErrorCode.CLIENTCASHACCOUNT_NOT_FOUND);
        }
        ClientCashAccount clientCashAccount = optionalClientCashAccount.get();
        BigDecimal solde = clientCashAccount.getCcaBalance();
        BigDecimal updatedSolde = BigDecimal.valueOf(0.0);

        /*******************************************************************
         * On calcule le nouveau solde du compte en fonction de l'operation
         */
        if(operationType.equals(OperationType.Credit)){
            updatedSolde = solde.add(amount);//Car BigDecimal est immutable on peut pas directement modifier sa valeur
        }
        else if(operationType.equals(OperationType.Withdrawal)){
            if(solde.compareTo(amount)<0){
                //Cela veut dire le solde est insuffisant
                log.error("The account balance is insufficient for the operation asked");
                throw new InvalidValueException("Le solde du compte est insuffisant pour retirer le montant demamdee ");
            }
            updatedSolde = solde.subtract(amount);
        }

        /***********************
         * Il faut donc effectuer la mise a jour effective du compte
         */
        clientCashAccount.setCcaBalance(updatedSolde);
        //Il faut save le PosCashAccount
        clientCashAccountRepository.save(clientCashAccount);

        /*****************************
         * Il faut maintenant enregistrer l'operation ainsi realise
         */
        ClientCashOperation cco = new ClientCashOperation();
        cco.setCcoAmountinmvt(amount);
        cco.setCcoUserbm(userAssociate);
        cco.setCcoCltCashAccount(clientCashAccount);

        Operation op = new Operation();
        op.setOpDate(new Date().toInstant());
        op.setOpDescription(opDescription);
        op.setOpObject(opObject);
        op.setOpType(operationType);
        cco.setCcoOperation(op);
        //Il faut save le ProviderCashOperation
        clientCashOperationRepository.save(cco);

        return true;
    }

    @Override
    public ClientCashAccountDto saveClientCashAccount(ClientCashAccountDto ccaDto) {
        List<String> errors = ClientCashAccountValidator.validate(ccaDto);
        if(!errors.isEmpty()){
            log.error("Entity pca not valid {}", ccaDto);
            throw new InvalidEntityException("Le ccaDto passé en argument n'est pas valide: "+errors,
                    ErrorCode.CLIENTCASHACCOUNT_NOT_VALID, errors);
        }

        return ClientCashAccountDto.fromEntity(
                clientCashAccountRepository.save(
                        ClientCashAccountDto.toEntity(ccaDto)
                )
        );
    }

    @Override
    public Boolean deleteClientCashAccountById(Long ccaId) {
        if(ccaId == null){
            log.error("The id pass as argument is null");
            throw new NullArgumentException("L'argument passe comme parametre est null");
        }
        Optional<ClientCashAccount> optionalClientCashAccount = clientCashAccountRepository.findClientCashAccountById(ccaId);
        if(!optionalClientCashAccount.isPresent()){
            log.error("There is no cash account for client with the id {}", ccaId);
            throw new EntityNotFoundException("Aucun compte cash de client n'existe avec le id passe en parametre ",
                    ErrorCode.CLIENTCASHACCOUNT_NOT_FOUND);
        }

        if(!isClientCashAccountDeleteable(ccaId)){
            log.error("The client cash account can't be deleteable");
            throw new EntityNotDeleteableException("Il nest pas possible de supprimer un compte cash client ",
                    ErrorCode.CLIENTCASHACCOUNT_NOT_DELETEABLE);
        }
        clientCashAccountRepository.delete(optionalClientCashAccount.get());

        return true;
    }

    @Override
    public Boolean isClientCashAccountDeleteable(Long ccaId) {
        return true;
    }

    @Override
    public ClientCashAccountDto findClientCashAccountById(Long ccaId) {
        if(ccaId == null){
            log.error("The pcaId pass as argument is null");
            throw new NullArgumentException("l'argument renseigne dans la methode est null");
        }
        Optional<ClientCashAccount> optionalClientCashAccount = clientCashAccountRepository.
                findClientCashAccountById(ccaId);
        if(!optionalClientCashAccount.isPresent()){
            log.error("The ccaId precised {} does not match any client cash account in the DB", ccaId);
            throw new EntityNotFoundException("Aucun compte cash ne correspond a l'id passe en argument ",
                    ErrorCode.CLIENTCASHACCOUNT_NOT_FOUND);
        }
        return ClientCashAccountDto.fromEntity(optionalClientCashAccount.get());
    }
}
