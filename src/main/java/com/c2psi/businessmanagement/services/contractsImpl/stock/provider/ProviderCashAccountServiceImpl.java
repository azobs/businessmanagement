package com.c2psi.businessmanagement.services.contractsImpl.stock.provider;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCashAccountDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCashOperationDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.*;
import com.c2psi.businessmanagement.repositories.pos.userbm.UserBMRepository;
import com.c2psi.businessmanagement.repositories.stock.provider.ProviderCashAccountRepository;
import com.c2psi.businessmanagement.repositories.stock.provider.ProviderCashOperationRepository;
import com.c2psi.businessmanagement.services.contracts.stock.provider.ProviderCashAccountService;
import com.c2psi.businessmanagement.validators.stock.provider.ProviderCashAccountValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service(value="ProviderCashAccountServiceV1")
@Slf4j
@Transactional
public class ProviderCashAccountServiceImpl implements ProviderCashAccountService {
    private ProviderCashAccountRepository providerCashAccountRepository;
    private UserBMRepository userBMRepository;
    private ProviderCashOperationRepository providerCashOperationRepository;

    @Autowired
    public ProviderCashAccountServiceImpl(ProviderCashAccountRepository providerCashAccountRepository,
                                          UserBMRepository userBMRepository,
                                          ProviderCashOperationRepository providerCashOperationRepository) {
        this.providerCashAccountRepository = providerCashAccountRepository;
        this.userBMRepository = userBMRepository;
        this.providerCashOperationRepository = providerCashOperationRepository;
    }


    @Override
    public Boolean saveCashOperation(Long pcaId, BigDecimal amount, OperationType operationType, Long userbmId,
                                     String opObject, String opDescription) {
        /***************************************************************************************
         * Il faut se rassurer que les parametres sensible de l'operation ne sont pas null
         */
        if(pcaId == null || amount == null || userbmId == null || operationType == null){
            log.error("pcaId, amount or even userbmId is null ");
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
        Optional<ProviderCashAccount> optionalProviderCashAccount =
                providerCashAccountRepository.findProviderCashAccountById(pcaId);
        if(!optionalProviderCashAccount.isPresent()){
            log.error("There is no providercashAccount in DB that match with the id passed");
            throw new EntityNotFoundException("Aucun compte cash provider n'existe avec l'id passe en argument ",
                    ErrorCode.PROVIDERCASHACCOUNT_NOT_FOUND);
        }
        ProviderCashAccount providerCashAccount = optionalProviderCashAccount.get();
        BigDecimal solde = providerCashAccount.getPcaBalance();
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
        providerCashAccount.setPcaBalance(updatedSolde);
        //Il faut save le PosCashAccount
        providerCashAccountRepository.save(providerCashAccount);

        /*****************************
         * Il faut maintenant enregistrer l'operation ainsi realise
         */
        ProviderCashOperation pco = new ProviderCashOperation();
        pco.setPcoAmountinmvt(amount);
        pco.setPcoUserbm(userAssociate);
        pco.setPcoProCashAccount(providerCashAccount);

        Operation op = new Operation();
        op.setOpDate(new Date().toInstant());
        op.setOpDescription(opDescription);
        op.setOpObject(opObject);
        op.setOpType(operationType);
        pco.setPcoOperation(op);
        //Il faut save le ProviderCashOperation
        providerCashOperationRepository.save(pco);

        return true;
    }

    @Override
    public Boolean saveCashOperation(ProviderCashAccountDto providerCashAccountDto,
                                     ProviderCashOperationDto providerCashOperationDto) {
        Long pcaId = providerCashAccountDto.getId();
        BigDecimal amount = providerCashOperationDto.getPcoAmountinmvt();
        Long userbmId = providerCashOperationDto.getPcoUserbmDto().getId();
        OperationType operationType = providerCashOperationDto.getPcoOperationDto().getOpType();
        String opDescription = providerCashOperationDto.getPcoOperationDto().getOpDescription();
        String opObject = providerCashOperationDto.getPcoOperationDto().getOpObject();
        /***************************************************************************************
         * Il faut se rassurer que les parametres sensible de l'operation ne sont pas null
         */
        if(pcaId == null || amount == null || userbmId == null || operationType == null){
            log.error("pcaId, amount or even userbmId is null ");
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
        Optional<ProviderCashAccount> optionalProviderCashAccount =
                providerCashAccountRepository.findProviderCashAccountById(pcaId);
        if(!optionalProviderCashAccount.isPresent()){
            log.error("There is no providercashAccount in DB that match with the id passed");
            throw new EntityNotFoundException("Aucun compte cash provider n'existe avec l'id passe en argument ",
                    ErrorCode.PROVIDERCASHACCOUNT_NOT_FOUND);
        }
        ProviderCashAccount providerCashAccount = optionalProviderCashAccount.get();
        BigDecimal solde = providerCashAccount.getPcaBalance();
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
        providerCashAccount.setPcaBalance(updatedSolde);
        //Il faut save le PosCashAccount
        providerCashAccountRepository.save(providerCashAccount);

        /*****************************
         * Il faut maintenant enregistrer l'operation ainsi realise
         */
        ProviderCashOperation pco = new ProviderCashOperation();
        pco.setPcoAmountinmvt(amount);
        pco.setPcoUserbm(userAssociate);
        pco.setPcoProCashAccount(providerCashAccount);

        Operation op = new Operation();
        op.setOpDate(new Date().toInstant());
        op.setOpDescription(opDescription);
        op.setOpObject(opObject);
        op.setOpType(operationType);
        pco.setPcoOperation(op);
        //Il faut save le ProviderCashOperation
        providerCashOperationRepository.save(pco);

        return true;
    }

    @Override
    public ProviderCashAccountDto saveProviderCashAccount(ProviderCashAccountDto pcaDto) {
        List<String> errors = ProviderCashAccountValidator.validate(pcaDto);
        if(!errors.isEmpty()){
            log.error("Entity pca not valid {}", pcaDto);
            throw new InvalidEntityException("Le pca passé en argument n'est pas valide: "+errors,
                    ErrorCode.PROVIDERCASHACCOUNT_NOT_VALID, errors);
        }

        return ProviderCashAccountDto.fromEntity(
                providerCashAccountRepository.save(
                        ProviderCashAccountDto.toEntity(pcaDto)
                )
        );
    }

    @Override
    public Boolean deleteProviderCashAccountById(Long pcaId) {

        if(pcaId == null){
            log.error("The id pass as argument is null");
            throw new NullArgumentException("L'argument passe comme parametre est null");
        }
        Optional<ProviderCashAccount> optionalProviderCashAccount = providerCashAccountRepository.findProviderCashAccountById(pcaId);
        if(!optionalProviderCashAccount.isPresent()){
            log.error("There is no cash account for provider with the id {}", pcaId);
            throw new EntityNotFoundException("Aucun compte cash de provider n'existe avec le id passe en parametre ",
                    ErrorCode.PROVIDERCASHACCOUNT_NOT_FOUND);
        }

        if(!isProviderCashAccountDeleteable(pcaId)){
            log.error("The provider cash account can't be deleteable");
            throw new EntityNotDeleteableException("Il nest pas possible de supprimer un compte cash provider ",
                    ErrorCode.PROVIDERCASHACCOUNT_NOT_DELETEABLE);
        }
        providerCashAccountRepository.delete(optionalProviderCashAccount.get());

        return true;
    }

    @Override
    public Boolean isProviderCashAccountDeleteable(Long pcaId) {
        return true;
    }

    @Override
    public ProviderCashAccountDto findProviderCashAccountById(Long pcaId) {
        if(pcaId == null){
            log.error("The pcaId pass as argument is null");
            throw new NullArgumentException("l'argument renseigne dans la methode est null");
        }
        Optional<ProviderCashAccount> optionalProviderCashAccount = providerCashAccountRepository.
                findProviderCashAccountById(pcaId);
        if(!optionalProviderCashAccount.isPresent()){
            log.error("The pcaId precised {} does not match any provider cash account in the DB", pcaId);
            throw new EntityNotFoundException("Aucun compte cash ne correspond a l'id passe en argument ",
                    ErrorCode.PROVIDERCASHACCOUNT_NOT_FOUND);
        }
        return ProviderCashAccountDto.fromEntity(optionalProviderCashAccount.get());
    }
}
