package com.c2psi.businessmanagement.services.contractsImpl.pos.pos;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCashAccountDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.exceptions.EntityNotFoundException;
import com.c2psi.businessmanagement.exceptions.ErrorCode;
import com.c2psi.businessmanagement.exceptions.InvalidEntityException;
import com.c2psi.businessmanagement.exceptions.NullArgumentException;
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

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
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
        List<String> errors = PosCashAccountValidator.validate(pcaDto);
        if(!errors.isEmpty()){
            log.error("Entity pca not valid {}", pcaDto);
            System.out.println("errors == "+errors);
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

        return Optional.of(UserBMDto.fromEntity(optionalUserBM.get())).orElseThrow(()->
                new EntityNotFoundException("Aucun UserBM avec l'id "+bmId
                        +" n'a été trouve dans la BDD", ErrorCode.USERBM_NOT_FOUND));
    }

    @Override
    public PosCashAccountDto findPosCashAccountById(Long pcaId) {
        if(pcaId == null){
            log.error("pcaId is null");
            throw new NullArgumentException("le pcaId passe en argument de la methode est null");
        }
        Optional<PosCashAccount> optionalPosCashAccount = posCashAccountRepository.findPosCashAccountById(pcaId);

        return Optional.of(PosCashAccountDto.fromEntity(optionalPosCashAccount.get())).orElseThrow(()->
                new EntityNotFoundException("Aucune PosCashAccount avec l'id "+pcaId
                        +" n'a été trouve dans la BDD", ErrorCode.POSCASHACCOUNT_NOT_FOUND));
    }


    @Override
    public Boolean saveCashOperation(Long pcaId, BigDecimal amount, OperationType operationType,
                                     Long userbmId, String opObject, String opDescription) {
        if(pcaId == null || amount == null || userbmId == null){
            throw new NullArgumentException("Appel de la methode saveCashDeposit avec des parametres null");
        }
        if(!this.isUserBMExistWithId(userbmId)){
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
        /***
         * On doit ici enregistrer un depot dans un compte cash d'un point de vente. Pour cela il
         * faut ajouter le montant de la transaction au solde du compte et ensuite enregistrer l'operation ainsi
         * réalise
         */
        if(operationType.equals(OperationType.Credit)){
            solde.add(amount);
        }
        else{
            solde.subtract(amount);
        }
        pca.setPcaBalance(solde);
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
        pco.setPoscoOperation(op);
        //Il faut save le PosCashOperation
        posCashOperationRepository.save(pco);

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
            posCashAccountRepository.delete(optionalPosCashAccount.get());
            return true;
        }
        return false;
    }


}
