package com.c2psi.businessmanagement.services.contractsImpl.stock.provider;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderPackagingAccountDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderPackagingOperationDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.*;
import com.c2psi.businessmanagement.repositories.pos.userbm.UserBMRepository;
import com.c2psi.businessmanagement.repositories.stock.product.PackagingRepository;
import com.c2psi.businessmanagement.repositories.stock.provider.ProviderPackagingAccountRepository;
import com.c2psi.businessmanagement.repositories.stock.provider.ProviderPackagingOperationRepository;
import com.c2psi.businessmanagement.repositories.stock.provider.ProviderRepository;
import com.c2psi.businessmanagement.services.contracts.stock.provider.ProviderPackagingAccountService;
import com.c2psi.businessmanagement.validators.stock.provider.ProviderPackagingAccountValidator;
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

@Service(value="ProviderPackagingAccountServiceV1")
@Slf4j
@Transactional
public class ProviderPackagingAccountServiceImpl implements ProviderPackagingAccountService {

    private ProviderPackagingAccountRepository providerPackagingAccountRepository;
    private ProviderPackagingOperationRepository providerPackagingOperationRepository;
    private UserBMRepository userBMRepository;
    private PackagingRepository packagingRepository;
    private ProviderRepository providerRepository;

    @Autowired
    public ProviderPackagingAccountServiceImpl(ProviderPackagingAccountRepository providerPackagingAccountRepository,
                                               ProviderPackagingOperationRepository providerPackagingOperationRepository,
                                               UserBMRepository userBMRepository, PackagingRepository packagingRepository,
                                               ProviderRepository providerRepository) {
        this.providerPackagingAccountRepository = providerPackagingAccountRepository;
        this.providerPackagingOperationRepository = providerPackagingOperationRepository;
        this.userBMRepository = userBMRepository;
        this.packagingRepository = packagingRepository;
        this.providerRepository = providerRepository;
    }

    public Boolean isProviderPackagingAccountExistinPos(Long packagingId, Long providerId){
        if(packagingId == null || providerId == null){
            log.error("packagingId or providerId is null");
            throw new NullArgumentException("le packagingId ou le providerId passe en argument de la methode est null");
        }
        Optional<ProviderPackagingAccount> optionalProviderPackagingAccount = providerPackagingAccountRepository.
                findProviderPackagingAccount(packagingId, providerId);
        return optionalProviderPackagingAccount.isPresent()?true:false;
    }

    @Override
    public ProviderPackagingAccountDto saveProviderPackagingAccount(ProviderPackagingAccountDto propackaccDto) {
        /*****************************************************************
         * On valide d'abord le parametre pris en argument
         */
        List<String> errors = ProviderPackagingAccountValidator.validate(propackaccDto);
        if(!errors.isEmpty()){
            log.error("Entity propackaccDto not valid {}", propackaccDto);
            throw new InvalidEntityException("Le propackaccDto passé en argument n'est pas valide: "+errors,
                    ErrorCode.PROVIDERPACKAGINGACCOUNT_NOT_VALID, errors);
        }

        /***************************************************************************
         * Maintenant faut se rassurer que le provider precise existe vraiment
         */
        if(propackaccDto.getPpaProviderDto().getId() == null){
            log.error("The id of the provider associated cannot be null");
            throw new InvalidEntityException("Le id du provider associe au compte packaging ne peut etre null",
                    ErrorCode.PROVIDERPACKAGINGACCOUNT_NOT_VALID);
        }
        //A ce niveau on est sur que le id du provider nest pas null
        Optional<Provider> optionalProvider = providerRepository.findProviderById(
                propackaccDto.getPpaProviderDto().getId());
        if(!optionalProvider.isPresent()){
            log.error("The provider indicated in the propackaccount doesn't exist in DB ");
            throw new EntityNotFoundException("Aucun provider n'existe avec l'id precise ",
                    ErrorCode.PROVIDER_NOT_FOUND);
        }

        /**********************************************************************
         * Maintenant on va se rassurer de l'existance du packaging en BD
         */
        if(propackaccDto.getPpaPackagingDto().getId() == null){
            log.error("The id of the packaging associated cannot be null");
            throw new InvalidEntityException("Le id du packaging associe au compte capsule ne peut etre null",
                    ErrorCode.PROVIDERPACKAGINGACCOUNT_NOT_VALID);
        }
        //A ce niveau on est sur que le id du article nest pas null
        Optional<Packaging> optionalPackaging = packagingRepository.findPackagingById(propackaccDto.getPpaPackagingDto().getId());
        if(!optionalPackaging.isPresent()){
            log.error("The packaging indicated in the propackaccount doesn't exist in DB ");
            throw new EntityNotFoundException("Aucun packaging n'existe avec l'id precise ",
                    ErrorCode.PACKAGING_NOT_FOUND);
        }

        /****************************************************************************************************
         * On verifie que le packaging est dans le meme pointofsale que le provider indique dans le compte
         */
        if(!propackaccDto.getPpaPackagingDto().getPackPosId().equals(propackaccDto.getPpaProviderDto().getProviderPosId())){
            log.error("The precised packaging and provider are not in the same pointofsale precise in the account");
            throw new InvalidEntityException("Le packaging et le provider precise pour le compte ne sont pas dans le meme  " +
                    "pointofsale ", ErrorCode.PROVIDERPACKAGINGACCOUNT_NOT_VALID);
        }

        /*************************************************************************************************
         * On verifie qu'aucun compte packaging n'est pas deja creer pour ce packaging et ce provider
         */
        if(isProviderPackagingAccountExistinPos(propackaccDto.getPpaPackagingDto().getId(),
                propackaccDto.getPpaProviderDto().getId())){
            log.error("An account for packaging has been already created for this provider");
            throw new DuplicateEntityException("Un compte packaging pour ce packaging et ce provider existe deja " +
                    "en BD ", ErrorCode.PROVIDERPACKAGINGACCOUNT_DUPLICATED);
        }

        log.info("After all verification, the entity {} can be saved without any problem in DB ", propackaccDto);


        return ProviderPackagingAccountDto.fromEntity(
                providerPackagingAccountRepository.save(
                        ProviderPackagingAccountDto.toEntity(propackaccDto)
                )
        );
    }

    @Override
    public ProviderPackagingAccountDto findProviderPackagingAccountById(Long propackaccId) {
        if(propackaccId == null){
            log.error("The propackaccId passed as argument is null");
            throw new NullArgumentException("Le propackaccId passe en argument est null");
        }
        Optional<ProviderPackagingAccount> optionalProviderPackagingAccount = providerPackagingAccountRepository.
                findProviderPackagingAccountById(propackaccId);
        if(!optionalProviderPackagingAccount.isPresent()){
            log.error("There is no ProviderPackagingAccount with the id {} sent", propackaccId);
            throw new EntityNotFoundException("Aucun ProviderPackagingAccount n'existe avec l'Id passe en parametre "+propackaccId,
                    ErrorCode.PROVIDERPACKAGINGACCOUNT_NOT_FOUND);
        }

        return ProviderPackagingAccountDto.fromEntity(optionalProviderPackagingAccount.get());
    }

    @Override
    public ProviderPackagingAccountDto findProviderPackagingAccount(Long packagingId, Long providerId) {
        if(providerId == null){
            log.error("The providerId passed as argument is null");
            throw new NullArgumentException("Le providerId passe en argument est null");
        }
        if(packagingId == null){
            log.error("The packagingId passed as argument is null");
            throw new NullArgumentException("Le packagingId passe en argument est null");
        }

        Optional<ProviderPackagingAccount> optionalProviderPackagingAccount = providerPackagingAccountRepository.
                findProviderPackagingAccount(packagingId, providerId);
        if(!optionalProviderPackagingAccount.isPresent()){
            log.error("There is no ProviderPackagingAccount with the argument packagingId =  {} and providerId = {} sent", packagingId, providerId);
            throw new EntityNotFoundException("Aucun ProviderPackagingAccount n'existe avec les parametres passe en parametre " +
                    "packagingId = "+packagingId+ " providerId = "+providerId, ErrorCode.PROVIDERPACKAGINGACCOUNT_NOT_FOUND);
        }

        return ProviderPackagingAccountDto.fromEntity(optionalProviderPackagingAccount.get());
    }

    @Override
    public List<ProviderPackagingAccountDto> findAllPackagingAccountofProvider(Long providerId) {
        if(providerId == null){
            log.error("The providerId sent as argument is null");
            throw new NullArgumentException("L'argument passe est null");
        }
        Optional<List<ProviderPackagingAccount>> optionalProviderPackagingAccountList = providerPackagingAccountRepository.
                findAllPackagingAccountofProvider(providerId);
        if(!optionalProviderPackagingAccountList.isPresent()){
            log.error("There is no provider with the id posId");
            throw new EntityNotFoundException("Aucun provider n'existe avec l'id passe en argument "+providerId,
                    ErrorCode.PROVIDER_NOT_FOUND);
        }

        return optionalProviderPackagingAccountList.get().stream().map(ProviderPackagingAccountDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<ProviderPackagingAccountDto> findPagePackagingAccountofProvider(Long providerId, int pagenum, int pagesize) {
        if(providerId == null){
            log.error("The providerId sent as argument is null");
            throw new NullArgumentException("L'argument passe est null");
        }
        Optional<Page<ProviderPackagingAccount>> optionalProviderPackagingAccountPage = providerPackagingAccountRepository.
                findPagePackagingAccountofProvider(providerId, PageRequest.of(pagenum, pagesize));
        if(!optionalProviderPackagingAccountPage.isPresent()){
            log.error("There is no provider with the id posId {}", providerId);
            throw new EntityNotFoundException("Aucun provider n'existe avec l'id passe en argument "+providerId,
                    ErrorCode.PROVIDER_NOT_FOUND);
        }

        return optionalProviderPackagingAccountPage.get().map(ProviderPackagingAccountDto::fromEntity);
    }

    @Override
    public Boolean deleteProviderPackagingAccountById(Long propackaccId) {
        if(propackaccId == null){
            log.error("The argument cannot be null for this method please check it");
            throw new NullArgumentException("L'argument passe est null et la methode ne peut donc s'executer");
        }

        Optional<ProviderPackagingAccount> optionalProviderPackagingAccount = providerPackagingAccountRepository.
                findProviderPackagingAccountById(propackaccId);
        if(!optionalProviderPackagingAccount.isPresent()){
            log.error("There is no providerPackagingAccount with the precised id {}", propackaccId);
            throw new EntityNotFoundException("Aucun providerPackagingAccount n'existe avec l'Id passe en argument dans la BD");
        }
        if(!isProviderPackagingAccountDeleteable(propackaccId)){
            log.error("Le compte packaging ne peut etre supprime");
            throw new EntityNotDeleteableException("Le compte packaging designe ne peut etre supprime",
                    ErrorCode.PROVIDERPACKAGINGACCOUNT_NOT_DELETEABLE);
        }
        providerPackagingAccountRepository.delete(optionalProviderPackagingAccount.get());
        return true;
    }

    @Override
    public Boolean isProviderPackagingAccountDeleteable(Long propackaccId) {
        return true;
    }

    @Override
    public Boolean savePackagingOperationforProvider(Long propackaccId, BigDecimal qte, OperationType operationType,
                                                     Long userbmId, String opObject, String opDescription) {

        /******************************************************************
         * Se rassurer que les donnees dans la fonction ne sont pas null
         */
        if(propackaccId == null || qte == null || userbmId == null || operationType == null){
            log.error("propackaccId, qte or even userbmId is null ");
            throw new NullArgumentException("Appel de la methode savePackagingOperationforProvider avec des parametres null");
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
        if(!this.isProviderPackagingAccountExistWithId(propackaccId)){
            log.error("The propackaccId {} does not identify any account ", propackaccId);
            throw  new EntityNotFoundException("Aucun ProviderPackagingAccount n'existe avec le ID precise "+propackaccId,
                    ErrorCode.PROVIDERPACKAGINGACCOUNT_NOT_FOUND);
        }
        Optional<ProviderPackagingAccount> optionalProviderPackagingAccount = providerPackagingAccountRepository.
                findProviderPackagingAccountById(propackaccId);
        //A ce niveau on na pas besoin de regarder si isPresent est true car on est sur que ca existe
        ProviderPackagingAccount providerPackagingAccountToUpdate = optionalProviderPackagingAccount.get();

        BigDecimal solde = providerPackagingAccountToUpdate.getPpaNumber();
        BigDecimal updatedSolde = BigDecimal.valueOf(0.0);

        /***
         * On doit ici enregistrer un depot dans un compte packaging d'un provider. Pour cela il
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
        providerPackagingAccountToUpdate.setPpaNumber(updatedSolde);

        providerPackagingAccountRepository.save(providerPackagingAccountToUpdate);

        ProviderPackagingOperation propackop = new ProviderPackagingOperation();
        propackop.setPropoNumberinmvt(qte);
        propackop.setPropoUserbm(optionalUserBM.get());
        propackop.setPropoProPackagingAccount(providerPackagingAccountToUpdate);

        Operation op = new Operation();
        op.setOpDate(new Date().toInstant());
        op.setOpDescription(opDescription);
        op.setOpObject(opObject);
        op.setOpType(operationType);
        propackop.setPropoOperation(op);
        //Il faut save le ProviderPackagingOperation
        providerPackagingOperationRepository.save(propackop);

        return true;
    }

    @Override
    public Boolean savePackagingOperationforProvider(ProviderPackagingAccountDto providerPackagingAccountDto,
                                                     ProviderPackagingOperationDto providerPackagingOperationDto) {
        Long propackaccId = providerPackagingAccountDto.getId();
        BigDecimal qte = providerPackagingOperationDto.getPropoNumberinmvt();
        Long userbmId = providerPackagingOperationDto.getPropoUserbmDto().getId();
        OperationType operationType = providerPackagingOperationDto.getPropoOperationDto().getOpType();
        String opDescription = providerPackagingOperationDto.getPropoOperationDto().getOpDescription();
        String opObject = providerPackagingOperationDto.getPropoOperationDto().getOpObject();
        /******************************************************************
         * Se rassurer que les donnees dans la fonction ne sont pas null
         */
        if(propackaccId == null || qte == null || userbmId == null || operationType == null){
            log.error("propackaccId, qte or even userbmId is null ");
            throw new NullArgumentException("Appel de la methode savePackagingOperationforProvider avec des parametres null");
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
        if(!this.isProviderPackagingAccountExistWithId(propackaccId)){
            log.error("The propackaccId {} does not identify any account ", propackaccId);
            throw  new EntityNotFoundException("Aucun ProviderPackagingAccount n'existe avec le ID precise "+propackaccId,
                    ErrorCode.PROVIDERPACKAGINGACCOUNT_NOT_FOUND);
        }
        Optional<ProviderPackagingAccount> optionalProviderPackagingAccount = providerPackagingAccountRepository.
                findProviderPackagingAccountById(propackaccId);
        //A ce niveau on na pas besoin de regarder si isPresent est true car on est sur que ca existe
        ProviderPackagingAccount providerPackagingAccountToUpdate = optionalProviderPackagingAccount.get();

        BigDecimal solde = providerPackagingAccountToUpdate.getPpaNumber();
        BigDecimal updatedSolde = BigDecimal.valueOf(0.0);

        /***
         * On doit ici enregistrer un depot dans un compte packaging d'un provider. Pour cela il
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
        providerPackagingAccountToUpdate.setPpaNumber(updatedSolde);

        providerPackagingAccountRepository.save(providerPackagingAccountToUpdate);

        ProviderPackagingOperation propackop = new ProviderPackagingOperation();
        propackop.setPropoNumberinmvt(qte);
        propackop.setPropoUserbm(optionalUserBM.get());
        propackop.setPropoProPackagingAccount(providerPackagingAccountToUpdate);

        Operation op = new Operation();
        op.setOpDate(new Date().toInstant());
        op.setOpDescription(opDescription);
        op.setOpObject(opObject);
        op.setOpType(operationType);
        propackop.setPropoOperation(op);
        //Il faut save le ProviderPackagingOperation
        providerPackagingOperationRepository.save(propackop);

        return true;
    }

    @Override
    public Boolean savePackagingOperationforProvider(ProviderPackagingOperationDto providerPackagingOperationDto) {
        Long propackaccId = providerPackagingOperationDto.getPropoProPackagingAccountDto().getId();
        BigDecimal qte = providerPackagingOperationDto.getPropoNumberinmvt();
        Long userbmId = providerPackagingOperationDto.getPropoUserbmDto().getId();
        OperationType operationType = providerPackagingOperationDto.getPropoOperationDto().getOpType();
        String opDescription = providerPackagingOperationDto.getPropoOperationDto().getOpDescription();
        String opObject = providerPackagingOperationDto.getPropoOperationDto().getOpObject();
        /******************************************************************
         * Se rassurer que les donnees dans la fonction ne sont pas null
         */
        if(propackaccId == null || qte == null || userbmId == null || operationType == null){
            log.error("propackaccId, qte or even userbmId is null ");
            throw new NullArgumentException("Appel de la methode savePackagingOperationforProvider avec des parametres null");
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
        if(!this.isProviderPackagingAccountExistWithId(propackaccId)){
            log.error("The propackaccId {} does not identify any account ", propackaccId);
            throw  new EntityNotFoundException("Aucun ProviderPackagingAccount n'existe avec le ID precise "+propackaccId,
                    ErrorCode.PROVIDERPACKAGINGACCOUNT_NOT_FOUND);
        }
        Optional<ProviderPackagingAccount> optionalProviderPackagingAccount = providerPackagingAccountRepository.
                findProviderPackagingAccountById(propackaccId);
        //A ce niveau on na pas besoin de regarder si isPresent est true car on est sur que ca existe
        ProviderPackagingAccount providerPackagingAccountToUpdate = optionalProviderPackagingAccount.get();

        BigDecimal solde = providerPackagingAccountToUpdate.getPpaNumber();
        BigDecimal updatedSolde = BigDecimal.valueOf(0.0);

        /***
         * On doit ici enregistrer un depot dans un compte packaging d'un provider. Pour cela il
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
        providerPackagingAccountToUpdate.setPpaNumber(updatedSolde);

        providerPackagingAccountRepository.save(providerPackagingAccountToUpdate);

        ProviderPackagingOperation propackop = new ProviderPackagingOperation();
        propackop.setPropoNumberinmvt(qte);
        propackop.setPropoUserbm(optionalUserBM.get());
        propackop.setPropoProPackagingAccount(providerPackagingAccountToUpdate);

        Operation op = new Operation();
        op.setOpDate(new Date().toInstant());
        op.setOpDescription(opDescription);
        op.setOpObject(opObject);
        op.setOpType(operationType);
        propackop.setPropoOperation(op);
        //Il faut save le ProviderPackagingOperation
        providerPackagingOperationRepository.save(propackop);

        return true;
    }

    public Boolean isProviderPackagingAccountExistWithId(Long propackId){
        if(propackId == null){
            log.error("propackId is null");
            throw new NullArgumentException("le propackId passe en argument de la methode est null");
        }
        Optional<ProviderPackagingAccount> optionalProviderPackagingAccount = providerPackagingAccountRepository.
                findProviderPackagingAccountById(propackId);
        return optionalProviderPackagingAccount.isPresent()?true:false;
    }

}
