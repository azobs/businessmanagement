package com.c2psi.businessmanagement.services.contractsImpl.pos.pos;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.pos.pos.PosPackagingAccountDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosPackagingOperationDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.*;
import com.c2psi.businessmanagement.repositories.pos.pos.*;
import com.c2psi.businessmanagement.repositories.pos.userbm.UserBMRepository;
import com.c2psi.businessmanagement.repositories.stock.product.PackagingRepository;
import com.c2psi.businessmanagement.services.contracts.pos.pos.PosPackagingAccountService;
import com.c2psi.businessmanagement.validators.pos.pos.PosPackagingAccountValidator;
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

@Service(value="PosPackagingAccountServiceV1")
@Slf4j
@Transactional
public class PosPackagingAccountServiceImpl implements PosPackagingAccountService {

    private PosPackagingAccountRepository posPackagingAccountRepository;
    private PosPackagingOperationRepository posPackagingOperationRepository;
    private UserBMRepository userBMRepository;
    private PackagingRepository packagingRepository;
    private PointofsaleRepository pointofsaleRepository;

    @Autowired
    public PosPackagingAccountServiceImpl(PosPackagingAccountRepository posPackagingAccountRepository,
                                          PosPackagingOperationRepository posPackagingOperationRepository,
                                          UserBMRepository userBMRepository, PackagingRepository packagingRepository,
                                          PointofsaleRepository pointofsaleRepository) {
        this.posPackagingAccountRepository = posPackagingAccountRepository;
        this.posPackagingOperationRepository = posPackagingOperationRepository;
        this.userBMRepository = userBMRepository;
        this.packagingRepository = packagingRepository;
        this.pointofsaleRepository = pointofsaleRepository;
    }

    public Boolean isPosPackagingAccountExistinPos(Long packagingId, Long posId){
        if(packagingId == null || posId == null){
            log.error("packagingId or posId is null");
            throw new NullArgumentException("le packagingId ou le posId passe en argument de la methode est null");
        }
        Optional<PosPackagingAccount> optionalPosPackagingAccount = posPackagingAccountRepository.
                findPosPackagingAccountInPos(packagingId, posId);
        return optionalPosPackagingAccount.isPresent()?true:false;
    }

    @Override
    public PosPackagingAccountDto savePosPackagingAccount(PosPackagingAccountDto ppackaccDto) {
        /*****************************************************************
         * On valide d'abord le parametre pris en argument
         */
        List<String> errors = PosPackagingAccountValidator.validate(ppackaccDto);
        if(!errors.isEmpty()){
            log.error("Entity ppackaccDto not valid {}", ppackaccDto);
            throw new InvalidEntityException("Le ppackaccDto passé en argument n'est pas valide: "+errors,
                    ErrorCode.POSPACKAGINGACCOUNT_NOT_VALID, errors);
        }

        /***************************************************************************
         * Maintenant faut se rassurer que le pointofsale precise existe vraiment
         */
        if(ppackaccDto.getPpaPointofsaleId() == null){
            log.error("The id of the pointofsale associated cannot be null");
            throw new InvalidEntityException("Le id du pointofsale associe au compte capsule ne peut etre null",
                    ErrorCode.POSPACKAGINGACCOUNT_NOT_VALID);
        }
        //A ce niveau on est sur que le id du pointofsale nest pas null
        Optional<Pointofsale> optionalPointofsale = pointofsaleRepository.findPointofsaleById(
                ppackaccDto.getPpaPointofsaleId());
        if(!optionalPointofsale.isPresent()){
            log.error("The pointofsale indicated in the pospackaccount doesn't exist in DB ");
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }

        /**********************************************************************
         * Maintenant on va se rassurer de l'existance du packaging en BD
         */
        if(ppackaccDto.getPpaPackagingDto().getId() == null){
            log.error("The id of the packaging associated cannot be null");
            throw new InvalidEntityException("Le id du packaging associe au compte capsule ne peut etre null",
                    ErrorCode.POSPACKAGINGACCOUNT_NOT_VALID);
        }
        //A ce niveau on est sur que le id du article nest pas null
        Optional<Packaging> optionalPackaging = packagingRepository.findPackagingById(ppackaccDto.getPpaPackagingDto().getId());
        if(!optionalPackaging.isPresent()){
            log.error("The packaging indicated in the pospackaccount doesn't exist in DB ");
            throw new EntityNotFoundException("Aucun packaging n'existe avec l'id precise ",
                    ErrorCode.PACKAGING_NOT_FOUND);
        }

        /********************************************************************************************
         * On verifie que le packaging est dans le meme pointofsale que celui precise pour le compte
         */
        if(!ppackaccDto.getPpaPackagingDto().getPackPosId().equals(ppackaccDto.getPpaPointofsaleId())){
            log.error("The precised packaging is not in the pointofsale precise for the account");
            throw new InvalidEntityException("Le packaging pour du compte doit etre dans le meme pointofsale " +
                    "que celui dans lequel le compte est cree ", ErrorCode.POSPACKAGINGACCOUNT_NOT_VALID);
        }

        /*************************************************************************************************
         * On verifie qu'aucun compte packaging n'est pas deja creer pour ce packaging dans ce pointofsale
         */
        if(isPosPackagingAccountExistinPos(ppackaccDto.getPpaPackagingDto().getId(),
                ppackaccDto.getPpaPointofsaleId())){
            log.error("An account for packaging has been already created for this packaging in this pointofsale");
            throw new DuplicateEntityException("Un compte packaging pour ce packaging dans ce pointofsale existe deja " +
                    "en BD ", ErrorCode.POSPACKAGINGACCOUNT_DUPLICATED);
        }

        log.info("After all verification, the entity {} can be saved without any problem in DB ", ppackaccDto);

        return PosPackagingAccountDto.fromEntity(
                posPackagingAccountRepository.save(
                        PosPackagingAccountDto.toEntity(ppackaccDto)
                )
        );

    }

    @Override
    public PosPackagingAccountDto findPosPackagingAccountById(Long ppackaccId) {
        if(ppackaccId == null){
            log.error("The ppackaccId passed as argument is null");
            throw new NullArgumentException("Le ppackaccId passe en argument est null");
        }
        Optional<PosPackagingAccount> optionalPosPackagingAccount = posPackagingAccountRepository.findPosPackagingAccountById(ppackaccId);
        if(!optionalPosPackagingAccount.isPresent()){
            log.error("There is no PosPackagingAccount with the id {} sent", ppackaccId);
            throw new EntityNotFoundException("Aucun PosPackagingAccount n'existe avec l'Id passe en parametre "+ppackaccId,
                    ErrorCode.POSPACKAGINGACCOUNT_NOT_FOUND);
        }

        return PosPackagingAccountDto.fromEntity(optionalPosPackagingAccount.get());
    }

    @Override
    public PosPackagingAccountDto findPosPackagingAccountInPos(Long packagingId, Long posId) {
        if(posId == null){
            log.error("The posId passed as argument is null");
            throw new NullArgumentException("Le posId passe en argument est null");
        }
        if(packagingId == null){
            log.error("The packagingId passed as argument is null");
            throw new NullArgumentException("Le packagingId passe en argument est null");
        }

        Optional<PosPackagingAccount> optionalPosPackagingAccount = posPackagingAccountRepository.
                findPosPackagingAccountInPos(packagingId, posId);
        if(!optionalPosPackagingAccount.isPresent()){
            log.error("There is no PosPackagingAccount with the argument packagingId =  {} and posId = {} sent", packagingId, posId);
            throw new EntityNotFoundException("Aucun PosPackagingAccount n'existe avec les parametres passe en parametre " +
                    "packagingId = "+packagingId+ " posId = "+posId, ErrorCode.POSPACKAGINGACCOUNT_NOT_FOUND);
        }

        return PosPackagingAccountDto.fromEntity(optionalPosPackagingAccount.get());
    }

    @Override
    public List<PosPackagingAccountDto> findAllPackagingAccountInPos(Long posId) {
        if(posId == null){
            log.error("The posId sent as argument is null");
            throw new NullArgumentException("L'argument passe est null");
        }
        Optional<List<PosPackagingAccount>> optionalPosPackagingAccountList = posPackagingAccountRepository.
                findAllPackagingAccountInPos(posId);
        if(!optionalPosPackagingAccountList.isPresent()){
            log.error("There is no pointofsale with the id posId");
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id passe en argument "+posId,
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }

        return optionalPosPackagingAccountList.get().stream().map(PosPackagingAccountDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<PosPackagingAccountDto> findPagePackagingAccountInPos(Long posId, int pagenum, int pagesize) {
        if(posId == null){
            log.error("The posId sent as argument is null");
            throw new NullArgumentException("L'argument passe est null");
        }
        Optional<Page<PosPackagingAccount>> optionalPosPackagingAccountPage = posPackagingAccountRepository.
                findPagePackagingAccountInPos(posId, PageRequest.of(pagenum, pagesize));
        if(!optionalPosPackagingAccountPage.isPresent()){
            log.error("There is no pointofsale with the id posId {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id passe en argument "+posId,
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }

        return optionalPosPackagingAccountPage.get().map(PosPackagingAccountDto::fromEntity);
    }

    @Override
    public List<PosPackagingAccountDto> findAllPackagingAccount(Long packagingId) {
        if(packagingId == null){
            log.error("The packagingId sent as argument is null");
            throw new NullArgumentException("L'argument passe est null");
        }
        Optional<List<PosPackagingAccount>> optionalPosPackagingAccountList = posPackagingAccountRepository.
                findAllPackagingAccount(packagingId);
        if(!optionalPosPackagingAccountList.isPresent()){
            log.error("There is no packaging with the id {}", packagingId);
            throw new EntityNotFoundException("Aucun packaging n'existe avec l'id passe en argument "+packagingId,
                    ErrorCode.PACKAGING_NOT_FOUND);
        }

        return optionalPosPackagingAccountList.get().stream().map(PosPackagingAccountDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<PosPackagingAccountDto> findPagePackagingAccount(Long packagingId, int pagenum, int pagesize) {
        if(packagingId == null){
            log.error("The packagingId sent as argument is null");
            throw new NullArgumentException("L'argument passe est null");
        }
        Optional<Page<PosPackagingAccount>> optionalPosPackagingAccountPage = posPackagingAccountRepository.
                findPagePackagingAccount(packagingId, PageRequest.of(pagenum, pagesize));
        if(!optionalPosPackagingAccountPage.isPresent()){
            log.error("There is no Packaging with the id packagingId {}", packagingId);
            throw new EntityNotFoundException("Aucun packaging n'existe avec l'id passe en argument "+packagingId,
                    ErrorCode.PACKAGING_NOT_FOUND);
        }

        return optionalPosPackagingAccountPage.get().map(PosPackagingAccountDto::fromEntity);
    }

    @Override
    public Boolean deletePosPackagingAccountById(Long ppackaccId) {
        if(ppackaccId == null){
            log.error("The argument cannot be null for this method please check it");
            throw new NullArgumentException("L'argument passe est null et la methode ne peut donc s'executer");
        }

        Optional<PosPackagingAccount> optionalPosPackagingAccount = posPackagingAccountRepository.
                findPosPackagingAccountById(ppackaccId);
        if(!optionalPosPackagingAccount.isPresent()){
            log.error("There is no posPackagingAccount with the precised id {}", ppackaccId);
            throw new EntityNotFoundException("Aucun posPackagingAccount n'existe avec l'Id passe en argument dans la BD");
        }
        if(!isPosPackagingAccountDeleteable(ppackaccId)){
            log.error("Le compte packaging ne peut etre supprime");
            throw new EntityNotDeleteableException("Le compte packaging designe ne peut etre supprime",
                    ErrorCode.POSPACKAGINGACCOUNT_NOT_DELETEABLE);
        }
        posPackagingAccountRepository.delete(optionalPosPackagingAccount.get());
        return true;
    }

    @Override
    public Boolean isPosPackagingAccountDeleteable(Long ppackaccId) {
        return true;
    }

    @Override
    public Boolean savePackagingOperation(Long ppackaccId, BigDecimal qte, OperationType operationType,
                                          Long userbmId, String opObject, String opDescription) {

        /******************************************************************
         * Se rassurer que les donnees dans la fonction ne sont pas null
         */
        if(ppackaccId == null || qte == null || userbmId == null || operationType == null){
            log.error("ppackaccId, qte or even userbmId is null ");
            throw new NullArgumentException("Appel de la methode savePackagingOperation avec des parametres null");
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
        if(!this.isPosPackagingAccountExistWithId(ppackaccId)){
            log.error("The ppackaccId {} does not identify any account ", ppackaccId);
            throw  new EntityNotFoundException("Aucun PosPackagingAccount n'existe avec le ID precise "+ppackaccId,
                    ErrorCode.POSPACKAGINGACCOUNT_NOT_FOUND);
        }
        Optional<PosPackagingAccount> optionalPosPackagingAccount = posPackagingAccountRepository.
                findPosPackagingAccountById(ppackaccId);
        //A ce niveau on na pas besoin de regarder si isPresent est true car on est sur que ca existe
        PosPackagingAccount posPackagingAccountToUpdate = optionalPosPackagingAccount.get();

        BigDecimal solde = posPackagingAccountToUpdate.getPpaNumber();
        BigDecimal updatedSolde = BigDecimal.valueOf(0.0);

        /***
         * On doit ici enregistrer un depot dans un compte packaging d'un point de vente. Pour cela il
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
        posPackagingAccountToUpdate.setPpaNumber(updatedSolde);

        posPackagingAccountRepository.save(posPackagingAccountToUpdate);

        PosPackagingOperation pospackop = new PosPackagingOperation();
        pospackop.setPospoNumberinmvt(qte);
        pospackop.setPospoUserbm(optionalUserBM.get());
        pospackop.setPospoPosPackagingAccount(posPackagingAccountToUpdate);

        Operation op = new Operation();
        op.setOpDate(new Date().toInstant());
        op.setOpDescription(opDescription);
        op.setOpObject(opObject);
        op.setOpType(operationType);
        pospackop.setPospoOperation(op);
        //Il faut save le PosPackagingOperation
        posPackagingOperationRepository.save(pospackop);

        return true;
    }

    @Override
    public Boolean savePackagingOperation(PosPackagingAccountDto posPackagingAccountDto,
                                          PosPackagingOperationDto posPackagingOperationDto) {

       Long ppackaccId = posPackagingAccountDto.getId();
       BigDecimal qte = posPackagingOperationDto.getPospoNumberinmvt();
       Long userbmId = posPackagingOperationDto.getPospoUserbmDto().getId();
       OperationType operationType = posPackagingOperationDto.getPospoOperationDto().getOpType();
       String opDescription = posPackagingOperationDto.getPospoOperationDto().getOpDescription();
       String opObject = posPackagingOperationDto.getPospoOperationDto().getOpObject();
        /******************************************************************
         * Se rassurer que les donnees dans la fonction ne sont pas null
         */
        if(ppackaccId == null || qte == null || userbmId == null || operationType == null){
            log.error("ppackaccId, qte or even userbmId is null ");
            throw new NullArgumentException("Appel de la methode savePackagingOperation avec des parametres null");
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
        if(!this.isPosPackagingAccountExistWithId(ppackaccId)){
            log.error("The ppackaccId {} does not identify any account ", ppackaccId);
            throw  new EntityNotFoundException("Aucun PosPackagingAccount n'existe avec le ID precise "+ppackaccId,
                    ErrorCode.POSPACKAGINGACCOUNT_NOT_FOUND);
        }
        Optional<PosPackagingAccount> optionalPosPackagingAccount = posPackagingAccountRepository.
                findPosPackagingAccountById(ppackaccId);
        //A ce niveau on na pas besoin de regarder si isPresent est true car on est sur que ca existe
        PosPackagingAccount posPackagingAccountToUpdate = optionalPosPackagingAccount.get();

        BigDecimal solde = posPackagingAccountToUpdate.getPpaNumber();
        BigDecimal updatedSolde = BigDecimal.valueOf(0.0);

        /***
         * On doit ici enregistrer un depot dans un compte packaging d'un point de vente. Pour cela il
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
        posPackagingAccountToUpdate.setPpaNumber(updatedSolde);

        posPackagingAccountRepository.save(posPackagingAccountToUpdate);

        PosPackagingOperation pospackop = new PosPackagingOperation();
        pospackop.setPospoNumberinmvt(qte);
        pospackop.setPospoUserbm(optionalUserBM.get());
        pospackop.setPospoPosPackagingAccount(posPackagingAccountToUpdate);

        Operation op = new Operation();
        op.setOpDate(new Date().toInstant());
        op.setOpDescription(opDescription);
        op.setOpObject(opObject);
        op.setOpType(operationType);
        pospackop.setPospoOperation(op);
        //Il faut save le PosPackagingOperation
        posPackagingOperationRepository.save(pospackop);

        return true;
    }

    public Boolean isPosPackagingAccountExistWithId(Long ppackId){
        if(ppackId == null){
            log.error("ppackId is null");
            throw new NullArgumentException("le ppackId passe en argument de la methode est null");
        }
        Optional<PosPackagingAccount> optionalPosPackagingAccount = posPackagingAccountRepository.
                findPosPackagingAccountById(ppackId);
        return optionalPosPackagingAccount.isPresent()?true:false;
    }
}
