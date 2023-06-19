package com.c2psi.businessmanagement.services.contractsImpl.pos.pos;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCashAccountDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCashOperationDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.Operation;
import com.c2psi.businessmanagement.models.PosCashAccount;
import com.c2psi.businessmanagement.models.PosCashOperation;
import com.c2psi.businessmanagement.models.UserBM;
import com.c2psi.businessmanagement.repositories.pos.pos.PosCashAccountRepository;
import com.c2psi.businessmanagement.repositories.pos.pos.PosCashOperationRepository;
import com.c2psi.businessmanagement.repositories.pos.userbm.UserBMRepository;
import com.c2psi.businessmanagement.services.contracts.pos.pos.PosCashAccountService;
import com.c2psi.businessmanagement.validators.pos.pos.PosCashAccountValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service(value="PosCashAccountServiceV1")
@Slf4j
@Transactional
public class PosCashAccountServiceImpl implements PosCashAccountService {

    private PosCashAccountRepository posCashAccountRepository;
    private PosCashOperationRepository posCashOperationRepository;
    private UserBMRepository userBMRepository;

    @Autowired
    public PosCashAccountServiceImpl(
            PosCashAccountRepository posCashAccountRepository,
            PosCashOperationRepository posCashOperationRepository,
            UserBMRepository userBMRepository) {
        this.posCashAccountRepository = posCashAccountRepository;
        this.posCashOperationRepository = posCashOperationRepository;
        this.userBMRepository = userBMRepository;
    }

    @Override
    public PosCashAccountDto savePosCashAccount(PosCashAccountDto pcaDto) {
        /*********************************************************************
         * On valide d'abord le parametre pris en parametre
         */
        List<String> errors = PosCashAccountValidator.validate(pcaDto);
        if(!errors.isEmpty()){
            log.error("Entity pca not valid {}", pcaDto);
            throw new InvalidEntityException("Le pca passé en argument n'est pas valide: "+errors,
                    ErrorCode.POSCASHACCOUNT_NOT_VALID, errors);
        }

        return PosCashAccountDto.fromEntity(
                posCashAccountRepository.save(
                        PosCashAccountDto.toEntity(pcaDto)
                )
        );
    }

    public Boolean isPosCashAccountExistWithId(Long pcaId){
        if(pcaId == null){
            log.error("pcaId is null");
            throw new NullArgumentException("le pcaId passe en argument de la methode est null");
        }
        Optional<PosCashAccount> optionalPosCashAccount = posCashAccountRepository.findPosCashAccountById(pcaId);
        return optionalPosCashAccount.isPresent()?true:false;
    }

    public Boolean isUserBMExistWithId(Long bmId){
        if(bmId == null){
            log.error("bmId is null");
            throw new NullArgumentException("le bmId passe en argument de la methode est null");
        }
        Optional<UserBM> optionalUserBM = userBMRepository.findUserBMById(bmId);
        return optionalUserBM.isPresent()?true:false;
    }

    public UserBMDto findUserBMById(Long bmId) {
        if(bmId == null){
            log.error("bmId is null");
            throw new NullArgumentException("le bmId passe en argument de la methode est null");
        }
        Optional<UserBM> optionalUserBM = userBMRepository.findUserBMById(bmId);

        if(optionalUserBM.isPresent()){
            return UserBMDto.fromEntity(optionalUserBM.get());
        }
        else{
            throw new EntityNotFoundException("Aucun UserBM avec l'id "+bmId
                    +" n'a été trouve dans la BDD", ErrorCode.USERBM_NOT_FOUND);
        }

    }

    @Override
    public PosCashAccountDto findPosCashAccountById(Long pcaId) {
        if(pcaId == null){
            log.error("pcaId is null");
            throw new NullArgumentException("le pcaId passe en argument de la methode est null");
        }
        Optional<PosCashAccount> optionalPosCashAccount = posCashAccountRepository.findPosCashAccountById(pcaId);

        if(optionalPosCashAccount.isPresent()){
            return PosCashAccountDto.fromEntity(optionalPosCashAccount.get());
        }
        else{
            throw new EntityNotFoundException("Aucune PosCashAccount avec l'id "+pcaId
                    +" n'a été trouve dans la BDD", ErrorCode.POSCASHACCOUNT_NOT_FOUND);
        }
    }


    @Override
    public Boolean saveCashOperation(Long pcaId, BigDecimal amount, OperationType operationType,
                                     Long userbmId, String opObject, String opDescription) {
        if(pcaId == null || amount == null || userbmId == null || operationType == null){
            log.error("pcaId, amount or even userbmId is null ");
            throw new NullArgumentException("Appel de la methode saveCashOperation avec des parametres null");
        }
        if(amount.compareTo(BigDecimal.valueOf(0)) <= 0){
            log.error("The amount cannot be negative value");
            throw new InvalidValueException("La valeur du montant ne saurait etre negative");
        }
        if(!operationType.equals(OperationType.Credit) && !operationType.equals(OperationType.Withdrawal)){
            log.error("The operationType is not recognized for this operation");
            throw new InvalidValueException("Le type d'operation precise n'est pas valide dans cette fonction ");
        }
        if(!this.isUserBMExistWithId(userbmId)){
            log.error("The userbmId sent as argument don't identify any userBM in the DB");
            throw new EntityNotFoundException("Aucun UserBM n'existe avec le ID precise "+userbmId,
                    ErrorCode.USERBM_NOT_FOUND);
        }
        if(!this.isPosCashAccountExistWithId(pcaId)){
            throw  new EntityNotFoundException("Aucun PosCashAccount n'existe avec le ID precise "+pcaId,
                    ErrorCode.POSCASHACCOUNT_NOT_FOUND);
        }
        PosCashAccount pca = PosCashAccountDto.toEntity(this.findPosCashAccountById(pcaId));
        UserBM userBM = UserBMDto.toEntity(this.findUserBMById(userbmId));
        BigDecimal solde = pca.getPcaBalance();
        BigDecimal updatedSolde = BigDecimal.valueOf(0.0);

        /***
         * On doit ici enregistrer un depot dans un compte cash d'un point de vente. Pour cela il
         * faut ajouter le montant de la transaction au solde du compte et ensuite enregistrer l'operation ainsi
         * réalise
         */
        if(operationType.equals(OperationType.Credit)){
            updatedSolde = solde.add(amount);//Car BigDecimal est immutable on peut pas directement modifier sa valeur
        }
        else if(operationType.equals(OperationType.Withdrawal)){
            if(solde.compareTo(amount) < 0){
                log.error("Insufficient balance");
                throw new InvalidValueException("Solde insuffisant "+solde);
            }
            updatedSolde = solde.subtract(amount);
        }

        pca.setPcaBalance(updatedSolde);
        //Il faut save le PosCashAccount
        posCashAccountRepository.save(pca);
        //Preparation de l'enregistrement de l'operation correspondante
        PosCashOperation pco = new PosCashOperation();
        pco.setPoscoAmountinmvt(amount);
        pco.setPoscoUserbm(userBM);
        pco.setPoscoPosCashAccount(pca);

        Operation op = new Operation();
        op.setOpDate(new Date().toInstant());
        op.setOpDescription(opDescription);
        op.setOpObject(opObject);
        op.setOpType(operationType);
        pco.setPoscoOperation(op);
        //Il faut save le PosCashOperation
        posCashOperationRepository.save(pco);

        return true;
    }

    @Override
    public Boolean saveCashOperation(PosCashAccountDto posCashAccountDto, PosCashOperationDto posCashOperationDto) {

        Long pcaId = posCashAccountDto.getId();
        BigDecimal amount = posCashOperationDto.getPoscoAmountinmvt();
        Long userbmId = posCashOperationDto.getPoscoUserbmDto().getId();
        OperationType operationType = posCashOperationDto.getPoscoOperationDto().getOpType();
        String opDescription = posCashOperationDto.getPoscoOperationDto().getOpDescription();
        String opObject = posCashOperationDto.getPoscoOperationDto().getOpObject();

        if(pcaId == null || amount == null || userbmId == null || operationType == null){
            log.error("pcaId, amount or even userbmId is null ");
            throw new NullArgumentException("Appel de la methode saveCashOperation avec des parametres null");
        }
        if(amount.compareTo(BigDecimal.valueOf(0)) <= 0){
            log.error("The amount cannot be negative value");
            throw new InvalidValueException("La valeur du montant ne saurait etre negative");
        }
        if(!operationType.equals(OperationType.Credit) && !operationType.equals(OperationType.Withdrawal)){
            log.error("The operationType is not recognized for this operation");
            throw new InvalidValueException("Le type d'operation precise n'est pas valide dans cette fonction ");
        }
        if(!this.isUserBMExistWithId(userbmId)){
            log.error("The userbmId sent as argument don't identify any userBM in the DB");
            throw new EntityNotFoundException("Aucun UserBM n'existe avec le ID precise "+userbmId,
                    ErrorCode.USERBM_NOT_FOUND);
        }
        if(!this.isPosCashAccountExistWithId(pcaId)){
            throw  new EntityNotFoundException("Aucun PosCashAccount n'existe avec le ID precise "+pcaId,
                    ErrorCode.POSCASHACCOUNT_NOT_FOUND);
        }
        PosCashAccount pca = PosCashAccountDto.toEntity(this.findPosCashAccountById(pcaId));
        UserBM userBM = UserBMDto.toEntity(this.findUserBMById(userbmId));
        BigDecimal solde = pca.getPcaBalance();
        BigDecimal updatedSolde = BigDecimal.valueOf(0.0);

        /***
         * On doit ici enregistrer un depot dans un compte cash d'un point de vente. Pour cela il
         * faut ajouter le montant de la transaction au solde du compte et ensuite enregistrer l'operation ainsi
         * réalise
         */
        if(operationType.equals(OperationType.Credit)){
            updatedSolde = solde.add(amount);//Car BigDecimal est immutable on peut pas directement modifier sa valeur
        }
        else if(operationType.equals(OperationType.Withdrawal)){
            if(solde.compareTo(amount) < 0){
                log.error("Insufficient balance");
                throw new InvalidValueException("Solde insuffisant "+solde);
            }
            updatedSolde = solde.subtract(amount);
        }

        pca.setPcaBalance(updatedSolde);
        //Il faut save le PosCashAccount
        posCashAccountRepository.save(pca);
        //Preparation de l'enregistrement de l'operation correspondante
        PosCashOperation pco = new PosCashOperation();
        pco.setPoscoAmountinmvt(amount);
        pco.setPoscoUserbm(userBM);
        pco.setPoscoPosCashAccount(pca);

        Operation op = new Operation();
        op.setOpDate(new Date().toInstant());
        op.setOpDescription(opDescription);
        op.setOpObject(opObject);
        op.setOpType(operationType);
        pco.setPoscoOperation(op);
        //Il faut save le PosCashOperation
        posCashOperationRepository.save(pco);

        return true;
    }

    @Override
    public Boolean isPosCashAccountDeleteable(Long id) {
        return true;
    }

    @Override
    public Boolean deletePosCashAccountById(Long pcaId) {
        if(pcaId == null){
            log.error("pcaId is null");
            throw new NullArgumentException("le pcaId passe en argument de la methode est null");
        }
        Optional<PosCashAccount> optionalPosCashAccount = posCashAccountRepository.findPosCashAccountById(pcaId);
        if(optionalPosCashAccount.isPresent()){
            if(isPosCashAccountDeleteable(pcaId)){
                posCashAccountRepository.delete(optionalPosCashAccount.get());
                return true;
            }

        }
        throw new EntityNotFoundException("Aucun compte cash n'existe avec cet "+pcaId);
    }


}
