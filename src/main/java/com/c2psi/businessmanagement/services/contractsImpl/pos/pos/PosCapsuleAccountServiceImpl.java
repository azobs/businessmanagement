package com.c2psi.businessmanagement.services.contractsImpl.pos.pos;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCapsuleAccountDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCapsuleOperationDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.*;
import com.c2psi.businessmanagement.repositories.pos.pos.*;
import com.c2psi.businessmanagement.repositories.pos.userbm.UserBMRepository;
import com.c2psi.businessmanagement.repositories.stock.product.ArticleRepository;
import com.c2psi.businessmanagement.services.contracts.pos.pos.PosCapsuleAccountService;
import com.c2psi.businessmanagement.validators.pos.pos.PosCapsuleAccountValidator;
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

@Service(value="PosCapsuleAccountServiceV1")
@Slf4j
@Transactional
public class PosCapsuleAccountServiceImpl implements PosCapsuleAccountService {

    private PosCapsuleAccountRepository posCapsAccountRepository;
    private PosCapsuleOperationRepository posCapsOperationRepository;
    private UserBMRepository userBMRepository;
    private ArticleRepository articleRepository;
    private PointofsaleRepository pointofsaleRepository;

    @Autowired
    public PosCapsuleAccountServiceImpl(PosCapsuleAccountRepository posCapsAccountRepository,
                                        PosCapsuleOperationRepository posCapsOperationRepository,
                                        UserBMRepository userBMRepository, ArticleRepository articleRepository,
                                        PointofsaleRepository posRepository) {
        this.posCapsAccountRepository = posCapsAccountRepository;
        this.posCapsOperationRepository = posCapsOperationRepository;
        this.userBMRepository = userBMRepository;
        this.articleRepository = articleRepository;
        this.pointofsaleRepository = posRepository;
    }

    public Boolean isPosCapsuleAccountofArticleExistinPos(Long artId, Long posId){
        if(artId == null || posId == null){
            log.error("artId or posId is null");
            throw new NullArgumentException("le artId ou le posId passe en argument de la methode est null");
        }
        Optional<PosCapsuleAccount> optionalPosCapsuleAccount = posCapsAccountRepository.
                findPosCapsuleAccountofArticleInPos(artId, posId);
        return optionalPosCapsuleAccount.isPresent()?true:false;
    }

    @Override
    public PosCapsuleAccountDto savePosCapsuleAccount(PosCapsuleAccountDto poscapsaccDto) {
        /*****************************************************************
         * On valide d'abord le parametre pris en argument
         */
        List<String> errors = PosCapsuleAccountValidator.validate(poscapsaccDto);
        if(!errors.isEmpty()){
            log.error("Entity poscapsaccDto not valid {}", poscapsaccDto);
            throw new InvalidEntityException("Le poscapsaccDto passé en argument n'est pas valide: "+errors,
                    ErrorCode.POSCAPSULEACCOUNT_NOT_VALID, errors);
        }

        /***************************************************************************
         * Maintenant faut se rassurer que le pointofsale precise existe vraiment
         */
        if(poscapsaccDto.getPcsaPointofsaleId() == null){
            log.error("The id of the pointofsale associated cannot be null");
            throw new InvalidEntityException("Le id du pointofsale associe au compte capsule ne peut etre null",
                    ErrorCode.POSCAPSULEACCOUNT_NOT_VALID);
        }
        //A ce niveau on est sur que le id du pointofsale nest pas null
        Optional<Pointofsale> optionalPointofsale = pointofsaleRepository.findPointofsaleById(
                poscapsaccDto.getPcsaPointofsaleId());
        if(!optionalPointofsale.isPresent()){
            log.error("The pointofsale indicated in the poscapsaccount doesn't exist in DB ");
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }

        /**********************************************************************
         * Maintenant on va se rassurer de l'existance de l'article en BD
         */
        if(poscapsaccDto.getPcsaArticleDto().getId() == null){
            log.error("The id of the article associated cannot be null");
            throw new InvalidEntityException("Le id du article associe au compte capsule ne peut etre null",
                    ErrorCode.POSCAPSULEACCOUNT_NOT_VALID);
        }
        //A ce niveau on est sur que le id du article nest pas null
        Optional<Article> optionalArticle = articleRepository.findArticleById(poscapsaccDto.getPcsaArticleDto().getId());
        if(!optionalArticle.isPresent()){
            log.error("The article indicated in the poscapsaccount doesn't exist in DB ");
            throw new EntityNotFoundException("Aucun article n'existe avec l'id precise ",
                    ErrorCode.ARTICLE_NOT_FOUND);
        }

        /******************************************************************************************
         * On verifie que l'article est dans le meme pointofsale que celui precise pour le compte
         */
        if(!poscapsaccDto.getPcsaArticleDto().getArtPosId().equals(poscapsaccDto.getPcsaPointofsaleId())){
            log.error("The precised article is not in the pointofsale precise for the account");
            throw new InvalidEntityException("L'article pour lequel creer le compte doit etre dans le meme pointofsale " +
                    "que celui dans lequel le compte est cree ", ErrorCode.POSCAPSULEACCOUNT_NOT_VALID);
        }

        /*************************************************************************************************
         * On verifie qu'aucun compte capsule n'est pas deja creer pour cet article dans ce pointofsale
         */
        if(isPosCapsuleAccountofArticleExistinPos(poscapsaccDto.getPcsaArticleDto().getId(),
                poscapsaccDto.getPcsaPointofsaleId())){
            log.error("An account for capsule has been already created for this article in this pointofsale");
            throw new DuplicateEntityException("Un compte capsule pour cet article dans ce pointofsale existe deja " +
                    "en BD ", ErrorCode.POSCAPSULEACCOUNT_DUPLICATED);
        }

        log.info("After all verification, the entity {} can be saved without any problem in DB ", poscapsaccDto);

        return PosCapsuleAccountDto.fromEntity(
                posCapsAccountRepository.save(
                        PosCapsuleAccountDto.toEntity(poscapsaccDto)
                )
        );
    }

    @Override
    public PosCapsuleAccountDto findPosCapsuleAccountById(Long poscapsaccId) {
        if(poscapsaccId == null){
            log.error("The poscapsaccId passed as argument is null");
            throw new NullArgumentException("Le poscapsaccId passe en argument est null");
        }
        Optional<PosCapsuleAccount> optionalPosCapsuleAccount = posCapsAccountRepository.findPosCapsuleAccountById(poscapsaccId);
        if(!optionalPosCapsuleAccount.isPresent()){
            log.error("There is no PosCapsuleAccount with the id {} sent", poscapsaccId);
            throw new EntityNotFoundException("Aucun PosCapsuleAccount n'existe avec l'Id passe en parametre "+poscapsaccId,
                    ErrorCode.POSCAPSULEACCOUNT_NOT_FOUND);
        }

        return PosCapsuleAccountDto.fromEntity(optionalPosCapsuleAccount.get());
    }

    @Override
    public PosCapsuleAccountDto findPosCapsuleAccountofArticleInPos(Long artId, Long posId) {
        if(posId == null){
            log.error("The posId passed as argument is null");
            throw new NullArgumentException("Le posId passe en argument est null");
        }
        if(artId == null){
            log.error("The artId passed as argument is null");
            throw new NullArgumentException("Le artId passe en argument est null");
        }

        Optional<PosCapsuleAccount> optionalPosCapsuleAccount = posCapsAccountRepository.
                findPosCapsuleAccountofArticleInPos(artId, posId);
        if(!optionalPosCapsuleAccount.isPresent()){
            log.error("There is no PosCapsuleAccount with the argument artId =  {} and posId = {} sent", artId, posId);
            throw new EntityNotFoundException("Aucun PosCapsuleAccount n'existe avec les parametres passe en parametre " +
                    "artId = "+artId+ " posId = "+posId, ErrorCode.POSCAPSULEACCOUNT_NOT_FOUND);
        }

        return PosCapsuleAccountDto.fromEntity(optionalPosCapsuleAccount.get());
    }

    @Override
    public List<PosCapsuleAccountDto> findAllCapsuleAccountInPos(Long posId) {
        if(posId == null){
            log.error("The posId sent as argument is null");
            throw new NullArgumentException("L'argument passe est null");
        }
        Optional<List<PosCapsuleAccount>> optionalPosCapsuleAccountList = posCapsAccountRepository.
                findAllCapsuleAccountInPos(posId);
        if(!optionalPosCapsuleAccountList.isPresent()){
            log.error("There is no pointofsale with the id posId");
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id passe en argument "+posId,
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }

        return optionalPosCapsuleAccountList.get().stream().map(PosCapsuleAccountDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<PosCapsuleAccountDto> findPageCapsuleAccountInPos(Long posId, int pagenum, int pagesize) {
        if(posId == null){
            log.error("The posId sent as argument is null");
            throw new NullArgumentException("L'argument passe est null");
        }
        Optional<Page<PosCapsuleAccount>> optionalPosCapsuleAccountPage = posCapsAccountRepository.
                findPageCapsuleAccountInPos(posId, PageRequest.of(pagenum, pagesize));
        if(!optionalPosCapsuleAccountPage.isPresent()){
            log.error("There is no pointofsale with the id posId {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id passe en argument "+posId,
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }

        return optionalPosCapsuleAccountPage.get().map(PosCapsuleAccountDto::fromEntity);
    }

    @Override
    public List<PosCapsuleAccountDto> findAllCapsuleAccountofArticle(Long artId) {
        if(artId == null){
            log.error("The artId sent as argument is null");
            throw new NullArgumentException("L'argument passe est null");
        }
        Optional<List<PosCapsuleAccount>> optionalPosCapsuleAccountList = posCapsAccountRepository.
                findAllCapsuleAccountofArticle(artId);
        if(!optionalPosCapsuleAccountList.isPresent()){
            log.error("There is no article with the id {}", artId);
            throw new EntityNotFoundException("Aucun article n'existe avec l'id passe en argument "+artId,
                    ErrorCode.ARTICLE_NOT_FOUND);
        }

        return optionalPosCapsuleAccountList.get().stream().map(PosCapsuleAccountDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<PosCapsuleAccountDto> findPageCapsuleAccountofArticle(Long artId, int pagenum, int pagesize) {
        if(artId == null){
            log.error("The artId sent as argument is null");
            throw new NullArgumentException("L'argument passe est null");
        }
        Optional<Page<PosCapsuleAccount>> optionalPosCapsuleAccountPage = posCapsAccountRepository.
                findPageCapsuleAccountofArticle(artId, PageRequest.of(pagenum, pagesize));
        if(!optionalPosCapsuleAccountPage.isPresent()){
            log.error("There is no Article with the id artId {}", artId);
            throw new EntityNotFoundException("Aucun article n'existe avec l'id passe en argument "+artId,
                    ErrorCode.ARTICLE_NOT_FOUND);
        }

        return optionalPosCapsuleAccountPage.get().map(PosCapsuleAccountDto::fromEntity);
    }

    @Override
    public Boolean deletePosCapsuleAccountById(Long poscapsaccId) {
        if(poscapsaccId == null){
            log.error("The argument cannot be null for this method please check it");
            throw new NullArgumentException("L'argument passe est null et la methode ne peut donc s'executer");
        }

        Optional<PosCapsuleAccount> optionalPosCapsuleAccount = posCapsAccountRepository.findPosCapsuleAccountById(poscapsaccId);
        if(!optionalPosCapsuleAccount.isPresent()){
            log.error("There is no posCapsuleAccount with the precised id {}", poscapsaccId);
            throw new EntityNotFoundException("Aucun posCapsuleAccount n'existe avec l'Id passe en argument dans la BD");
        }
        if(!isPosCapsuleAccountDeleteable(poscapsaccId)){
            log.error("Le compte capsule ne peut etre supprime");
            throw new EntityNotDeleteableException("Le compte capsule designe ne peut etre supprime",
                    ErrorCode.POSCAPSULEACCOUNT_NOT_DELETEABLE);
        }
        posCapsAccountRepository.delete(optionalPosCapsuleAccount.get());
        return true;
    }

    @Override
    public Boolean saveCapsuleOperation(Long poscapsaccId, BigDecimal qte, OperationType operationType,
                                        Long userbmId, String opObject, String opDescription) {

        /******************************************************************
         * Se rassurer que les donnees dans la fonction ne sont pas null
         */
        if(poscapsaccId == null || qte == null || userbmId == null || operationType == null){
            log.error("poscaspaccId, qte or even userbmId is null ");
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
        if(!this.isPosCapsuleAccountExistWithId(poscapsaccId)){
            log.error("The poscaspaccId {} does not identify any account ", poscapsaccId);
            throw  new EntityNotFoundException("Aucun PosCapsuleAccount n'existe avec le ID precise "+poscapsaccId,
                    ErrorCode.POSCAPSULEACCOUNT_NOT_FOUND);
        }
        Optional<PosCapsuleAccount> optionalPosCapsuleAccount = posCapsAccountRepository.
                findPosCapsuleAccountById(poscapsaccId);
        //A ce niveau on na pas besoin de regarder si isPresent est true car on est sur que ca existe
        PosCapsuleAccount posCapsuleAccountToUpdate = optionalPosCapsuleAccount.get();

        BigDecimal solde = posCapsuleAccountToUpdate.getPcsaNumber();
        BigDecimal updatedSolde = BigDecimal.valueOf(0.0);

        /***
         * On doit ici enregistrer un depot dans un compte capsule d'un point de vente. Pour cela il
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
        posCapsuleAccountToUpdate.setPcsaNumber(updatedSolde);

        posCapsAccountRepository.save(posCapsuleAccountToUpdate);

        PosCapsuleOperation poscapso = new PosCapsuleOperation();
        poscapso.setPoscsoNumberinmvt(qte);
        poscapso.setPoscsoUserbm(optionalUserBM.get());
        poscapso.setPoscsoPosCapsuleAccount(posCapsuleAccountToUpdate);

        Operation op = new Operation();
        op.setOpDate(new Date().toInstant());
        op.setOpDescription(opDescription);
        op.setOpObject(opObject);
        op.setOpType(operationType);
        poscapso.setPoscsoOperation(op);
        //Il faut save le PosCapsuleOperation
        posCapsOperationRepository.save(poscapso);

        return true;
    }

    @Override
    public Boolean saveCapsuleOperation(PosCapsuleAccountDto poscapaccDto, PosCapsuleOperationDto poscapopDto) {

        if(poscapaccDto == null || poscapopDto == null){
            log.error("The poscapaccDto or poscapopDto is null");
            throw new NullArgumentException("Appel de la methode saveCapsuleOperation avec des parametres null");
        }

        Long poscapsaccId = poscapaccDto.getId();
        BigDecimal qte = poscapopDto.getPoscsoNumberinmvt();
        Long userbmId = poscapopDto.getPoscsoUserbmDto().getId();
        OperationType operationType = poscapopDto.getPoscsoOperationDto().getOpType();
        String opDescription = poscapopDto.getPoscsoOperationDto().getOpDescription();
        String opObject = poscapopDto.getPoscsoOperationDto().getOpObject();

        /******************************************************************
         * Se rassurer que les donnees dans la fonction ne sont pas null
         */
        if(poscapsaccId == null || qte == null || userbmId == null || operationType == null){
            log.error("poscaspaccId, qte or even userbmId is null ");
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
        if(!this.isPosCapsuleAccountExistWithId(poscapsaccId)){
            log.error("The poscaspaccId {} does not identify any account ", poscapsaccId);
            throw  new EntityNotFoundException("Aucun PosCapsuleAccount n'existe avec le ID precise "+poscapsaccId,
                    ErrorCode.POSCAPSULEACCOUNT_NOT_FOUND);
        }
        Optional<PosCapsuleAccount> optionalPosCapsuleAccount = posCapsAccountRepository.
                findPosCapsuleAccountById(poscapsaccId);
        //A ce niveau on na pas besoin de regarder si isPresent est true car on est sur que ca existe
        PosCapsuleAccount posCapsuleAccountToUpdate = optionalPosCapsuleAccount.get();

        BigDecimal solde = posCapsuleAccountToUpdate.getPcsaNumber();
        BigDecimal updatedSolde = BigDecimal.valueOf(0.0);

        /***
         * On doit ici enregistrer un depot dans un compte capsule d'un point de vente. Pour cela il
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
        posCapsuleAccountToUpdate.setPcsaNumber(updatedSolde);

        posCapsAccountRepository.save(posCapsuleAccountToUpdate);

        PosCapsuleOperation poscapso = new PosCapsuleOperation();
        poscapso.setPoscsoNumberinmvt(qte);
        poscapso.setPoscsoUserbm(optionalUserBM.get());
        poscapso.setPoscsoPosCapsuleAccount(posCapsuleAccountToUpdate);

        Operation op = new Operation();
        op.setOpDate(new Date().toInstant());
        op.setOpDescription(opDescription);
        op.setOpObject(opObject);
        op.setOpType(operationType);
        poscapso.setPoscsoOperation(op);
        //Il faut save le PosCapsuleOperation
        posCapsOperationRepository.save(poscapso);

        return true;
    }

    public Boolean isPosCapsuleAccountExistWithId(Long pcaId){
        if(pcaId == null){
            log.error("pcaId is null");
            throw new NullArgumentException("le pcaId passe en argument de la methode est null");
        }
        Optional<PosCapsuleAccount> optionalPosCapsuleAccount = posCapsAccountRepository.findPosCapsuleAccountById(pcaId);
        return optionalPosCapsuleAccount.isPresent()?true:false;
    }


    @Override
    public Boolean isPosCapsuleAccountDeleteable(Long posCapsAccId) {
        return true;
    }
}
