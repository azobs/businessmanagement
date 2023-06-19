package com.c2psi.businessmanagement.services.contractsImpl.client.client;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.client.client.DiversCashAccountDto;
import com.c2psi.businessmanagement.dtos.client.client.DiversCashOperationDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.*;
import com.c2psi.businessmanagement.repositories.client.client.DiversCashAccountRepository;
import com.c2psi.businessmanagement.repositories.client.client.DiversCashOperationRepository;
import com.c2psi.businessmanagement.repositories.pos.userbm.UserBMRepository;
import com.c2psi.businessmanagement.services.contracts.client.client.DiversCashAccountService;
import com.c2psi.businessmanagement.validators.client.client.DiversCashAccountValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service(value="DiversCashAccountServiceV1")
@Slf4j
@Transactional
public class DiversCashAccountServiceImpl implements DiversCashAccountService {

    private DiversCashAccountRepository diversCashAccountRepository;
    private UserBMRepository userBMRepository;
    private DiversCashOperationRepository diversCashOperationRepository;

    @Autowired
    public DiversCashAccountServiceImpl(DiversCashAccountRepository diversCashAccountRepository,
                                        UserBMRepository userBMRepository,
                                        DiversCashOperationRepository diversCashOperationRepository) {
        this.diversCashAccountRepository = diversCashAccountRepository;
        this.userBMRepository = userBMRepository;
        this.diversCashOperationRepository = diversCashOperationRepository;
    }

    @Override
    public Boolean saveCashOperation(Long dcaId, BigDecimal amount, OperationType operationType,
                                     Long userbmId, String opObject, String opDescription) {

        /***************************************************************************************
         * Il faut se rassurer que les parametres sensible de l'operation ne sont pas null
         */
        if(dcaId == null || amount == null || userbmId == null || operationType == null){
            log.error("dcaId, amount or even userbmId is null ");
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
        Optional<DiversCashAccount> optionalDiversCashAccount =
                diversCashAccountRepository.findDiversCashAccountById(dcaId);
        if(!optionalDiversCashAccount.isPresent()){
            log.error("There is no diverscashAccount in DB that match with the id passed");
            throw new EntityNotFoundException("Aucun compte cash client n'existe avec l'id passe en argument ",
                    ErrorCode.DIVERSCASHACCOUNT_NOT_FOUND);
        }
        DiversCashAccount diversCashAccount = optionalDiversCashAccount.get();
        BigDecimal solde = diversCashAccount.getDcaBalance();
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
        diversCashAccount.setDcaBalance(updatedSolde);
        //Il faut save le PosCashAccount
        diversCashAccountRepository.save(diversCashAccount);

        /*****************************
         * Il faut maintenant enregistrer l'operation ainsi realise
         */
        DiversCashOperation dco = new DiversCashOperation();
        dco.setDcoAmountinmvt(amount);
        dco.setDcoUserbm(userAssociate);
        dco.setDcoDiversCashAccount(diversCashAccount);

        Operation op = new Operation();
        op.setOpDate(new Date().toInstant());
        op.setOpDescription(opDescription);
        op.setOpObject(opObject);
        op.setOpType(operationType);
        dco.setDcoOperation(op);

        diversCashOperationRepository.save(dco);

        return true;
    }

    @Override
    public Boolean saveCashOperation(DiversCashAccountDto diversCashAccountDto,
                                     DiversCashOperationDto diversCashOperationDto) {
        Long dcaId = diversCashAccountDto.getId();
        BigDecimal amount = diversCashOperationDto.getDcoAmountinmvt();
        Long userbmId = diversCashOperationDto.getDcoUserbmDto().getId();
        OperationType operationType = diversCashOperationDto.getDcoOperationDto().getOpType();
        String opObject = diversCashOperationDto.getDcoOperationDto().getOpObject();
        String opDescription = diversCashOperationDto.getDcoOperationDto().getOpDescription();
        /***************************************************************************************
         * Il faut se rassurer que les parametres sensible de l'operation ne sont pas null
         */
        if(dcaId == null || amount == null || userbmId == null || operationType == null){
            log.error("dcaId, amount or even userbmId is null ");
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
        Optional<DiversCashAccount> optionalDiversCashAccount =
                diversCashAccountRepository.findDiversCashAccountById(dcaId);
        if(!optionalDiversCashAccount.isPresent()){
            log.error("There is no diverscashAccount in DB that match with the id passed");
            throw new EntityNotFoundException("Aucun compte cash client n'existe avec l'id passe en argument ",
                    ErrorCode.DIVERSCASHACCOUNT_NOT_FOUND);
        }
        DiversCashAccount diversCashAccount = optionalDiversCashAccount.get();
        BigDecimal solde = diversCashAccount.getDcaBalance();
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
        diversCashAccount.setDcaBalance(updatedSolde);
        //Il faut save le PosCashAccount
        diversCashAccountRepository.save(diversCashAccount);

        /*****************************
         * Il faut maintenant enregistrer l'operation ainsi realise
         */
        DiversCashOperation dco = new DiversCashOperation();
        dco.setDcoAmountinmvt(amount);
        dco.setDcoUserbm(userAssociate);
        dco.setDcoDiversCashAccount(diversCashAccount);

        Operation op = new Operation();
        op.setOpDate(new Date().toInstant());
        op.setOpDescription(opDescription);
        op.setOpObject(opObject);
        op.setOpType(operationType);
        dco.setDcoOperation(op);

        diversCashOperationRepository.save(dco);

        return true;
    }

    @Override
    public DiversCashAccountDto saveDiversCashAccount(DiversCashAccountDto dcaDto) {
        List<String> errors = DiversCashAccountValidator.validate(dcaDto);
        if(!errors.isEmpty()){
            log.error("Entity pca not valid {}", dcaDto);
            throw new InvalidEntityException("Le dcaDto passé en argument n'est pas valide: "+errors,
                    ErrorCode.DIVERSCASHACCOUNT_NOT_VALID, errors);
        }

        return DiversCashAccountDto.fromEntity(
                diversCashAccountRepository.save(
                        DiversCashAccountDto.toEntity(dcaDto)
                )
        );
    }

    @Override
    public Boolean deleteDiversCashAccountById(Long dcaId) {
        if(dcaId == null){
            log.error("The id pass as argument is null");
            throw new NullArgumentException("L'argument passe comme parametre est null");
        }
        Optional<DiversCashAccount> optionalDiversCashAccount = diversCashAccountRepository.findDiversCashAccountById(dcaId);
        if(!optionalDiversCashAccount.isPresent()){
            log.error("There is no cash account for client with the id {}", dcaId);
            throw new EntityNotFoundException("Aucun compte cash de client n'existe avec le id passe en parametre ",
                    ErrorCode.DIVERSCASHACCOUNT_NOT_FOUND);
        }

        if(!isDiversCashAccountDeleteable(dcaId)){
            log.error("The client cash account can't be deleteable");
            throw new EntityNotDeleteableException("Il nest pas possible de supprimer un compte cash client ",
                    ErrorCode.DIVERSCASHACCOUNT_NOT_DELETEABLE);
        }
        diversCashAccountRepository.delete(optionalDiversCashAccount.get());

        return true;
    }

    @Override
    public Boolean isDiversCashAccountDeleteable(Long dcaId) {
        return true;
    }

    @Override
    public DiversCashAccountDto findDiversCashAccountById(Long dcaId) {
        if(dcaId == null){
            log.error("The dcaId pass as argument is null");
            throw new NullArgumentException("l'argument renseigne dans la methode est null");
        }
        Optional<DiversCashAccount> optionalDiversCashAccount = diversCashAccountRepository.
                findDiversCashAccountById(dcaId);
        if(!optionalDiversCashAccount.isPresent()){
            log.error("The dcaId precised {} does not match any divers cash account in the DB", dcaId);
            throw new EntityNotFoundException("Aucun compte cash ne correspond a l'id passe en argument ",
                    ErrorCode.DIVERSCASHACCOUNT_NOT_FOUND);
        }
        return DiversCashAccountDto.fromEntity(optionalDiversCashAccount.get());
    }

    @Override
    public DiversCashAccountDto findDiversCashAccountIfExistOrCreate() {
        /******************************************************
         * Il faut verifier si un compte Divers existe et si ce
         * n'est pas le cas on cree un compte cash sinon on associe celui
         * qui existe au divers cree
         */
        Optional<List<DiversCashAccount>> optionalDiversCashAccount = Optional.of(diversCashAccountRepository.findAll());
        if(!optionalDiversCashAccount.isPresent()){
            /****
             * Aucun compte cash Divers n'existe dans la BD
             */
            log.error("Aucun compte Divers n'existe dans la BD");
            throw new EntityNotFoundException("Aucun compte cash n'existe dans la BD");
        }
        List<DiversCashAccount> diversCashAccountList = optionalDiversCashAccount.get();
        if(diversCashAccountList.size()>1){
            log.error("Plus d'un compte cash existe dans la BD pour le Divers");
            throw new InvalidEntityException("Plus d'un compte cash existe dans la BD pour les divers");
        }

        if(diversCashAccountList.size()==0){
            /****************************
             * Ici on doit creer le compte cash des divers
             * car aucun n'existe donc en fait c'est le premier c
             * compte cash en creation
             */
            DiversCashAccountDto diversCashAccountDto = DiversCashAccountDto.builder()
                    .dcaBalance(BigDecimal.valueOf(0))
                    .build();
            DiversCashAccountDto diversCashAccountDtoSaved = DiversCashAccountDto.fromEntity(
                    diversCashAccountRepository.save(DiversCashAccountDto.toEntity(diversCashAccountDto)));
            return diversCashAccountDtoSaved;
        }
        /******
         * Ici on est sur qu'il existe un et un seul compte cash donc
         * c'est dans ce compte que les operations du cash seront execute
         */
        return DiversCashAccountDto.fromEntity(diversCashAccountList.get(0));
    }
}
