package com.c2psi.businessmanagement.services.contractsImpl.pos.pos;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCapsuleAccountDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosDamageAccountDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.*;
import com.c2psi.businessmanagement.repositories.pos.pos.*;
import com.c2psi.businessmanagement.repositories.pos.userbm.UserBMRepository;
import com.c2psi.businessmanagement.repositories.stock.product.ArticleRepository;
import com.c2psi.businessmanagement.services.contracts.pos.pos.PosDamageAccountService;
import com.c2psi.businessmanagement.validators.pos.pos.PosDamageAccountValidator;
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

@Service(value="PosDamageAccountServiceV1")
@Slf4j
@Transactional
public class PosDamageAccountServiceImpl implements PosDamageAccountService {
    private PosDamageAccountRepository posDamAccountRepository;
    private PosDamageOperationRepository posDamOperationRepository;
    private UserBMRepository userBMRepository;
    private ArticleRepository articleRepository;
    private PointofsaleRepository pointofsaleRepository;

    @Autowired
    public PosDamageAccountServiceImpl(PosDamageAccountRepository posDamAccountRepository,
                                       PosDamageOperationRepository posDamOperationRepository,
                                       UserBMRepository userBMRepository, ArticleRepository articleRepository,
                                       PointofsaleRepository pointofsaleRepository) {
        this.posDamAccountRepository = posDamAccountRepository;
        this.posDamOperationRepository = posDamOperationRepository;
        this.userBMRepository = userBMRepository;
        this.articleRepository = articleRepository;
        this.pointofsaleRepository = pointofsaleRepository;
    }

    public Boolean isPosDamageAccountofArticleExistinPos(Long artId, Long posId){
        if(artId == null || posId == null){
            log.error("artId or posId is null");
            throw new NullArgumentException("le artId ou le posId passe en argument de la methode est null");
        }
        Optional<PosDamageAccount> optionalPosDamageAccount = posDamAccountRepository.
                findPosDamageAccountofArticleInPos(artId, posId);
        return optionalPosDamageAccount.isPresent()?true:false;
    }

    @Override
    public PosDamageAccountDto savePosDamageAccount(PosDamageAccountDto posdamaccDto) {
        /*****************************************************************
         * On valide d'abord le parametre pris en argument
         */
        List<String> errors = PosDamageAccountValidator.validate(posdamaccDto);
        if(!errors.isEmpty()){
            log.error("Entity posdamaccDto not valid {}", posdamaccDto);
            throw new InvalidEntityException("Le posdamaccDto passé en argument n'est pas valide: "+errors,
                    ErrorCode.POSDAMAGEACCOUNT_NOT_VALID, errors);
        }

        /***************************************************************************
         * Maintenant faut se rassurer que le pointofsale precise existe vraiment
         */
        if(posdamaccDto.getPdaPointofsaleDto().getId() == null){
            log.error("The id of the pointofsale associated cannot be null");
            throw new InvalidEntityException("Le id du pointofsale associe au compte damage ne peut etre null",
                    ErrorCode.POSDAMAGEACCOUNT_NOT_VALID);
        }
        //A ce niveau on est sur que le id du pointofsale nest pas null
        Optional<Pointofsale> optionalPointofsale = pointofsaleRepository.findPointofsaleById(
                posdamaccDto.getPdaPointofsaleDto().getId());
        if(!optionalPointofsale.isPresent()){
            log.error("The pointofsale indicated in the posdamaccount doesn't exist in DB ");
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }

        /**********************************************************************
         * Maintenant on va se rassurer de l'existance de l'article en BD
         */
        if(posdamaccDto.getPdaArticleDto().getId() == null){
            log.error("The id of the article associated cannot be null");
            throw new InvalidEntityException("Le id du article associe au compte damage ne peut etre null",
                    ErrorCode.POSDAMAGEACCOUNT_NOT_VALID);
        }
        //A ce niveau on est sur que le id du article nest pas null
        Optional<Article> optionalArticle = articleRepository.findArticleById(posdamaccDto.getPdaArticleDto().getId());
        if(!optionalArticle.isPresent()){
            log.error("The article indicated in the posdamaccount doesn't exist in DB ");
            throw new EntityNotFoundException("Aucun article n'existe avec l'id precise ",
                    ErrorCode.ARTICLE_NOT_FOUND);
        }

        /******************************************************************************************
         * On verifie que l'article est dans le meme pointofsale que celui precise pour le compte
         */
        if(!posdamaccDto.getPdaArticleDto().getArtPosDto().getId().equals(posdamaccDto.getPdaPointofsaleDto().getId())){
            log.error("The precised article is not in the pointofsale precise for the account");
            throw new InvalidEntityException("L'article pour lequel creer le compte doit etre dans le meme pointofsale " +
                    "que celui dans lequel le compte est cree ", ErrorCode.POSDAMAGEACCOUNT_NOT_VALID);
        }

        /*************************************************************************************************
         * On verifie qu'aucun compte damage n'est pas deja creer pour cet article dans ce pointofsale
         */
        if(isPosDamageAccountofArticleExistinPos(posdamaccDto.getPdaArticleDto().getId(),
                posdamaccDto.getPdaPointofsaleDto().getId())){
            log.error("An account for damage has been already created for this article in this pointofsale");
            throw new DuplicateEntityException("Un compte damage pour cet article dans ce pointofsale existe deja " +
                    "en BD ", ErrorCode.POSDAMAGEACCOUNT_DUPLICATED);
        }

        log.info("After all verification, the entity {} can be saved without any problem in DB ", posdamaccDto);

        return PosDamageAccountDto.fromEntity(
                posDamAccountRepository.save(
                        PosDamageAccountDto.toEntity(posdamaccDto)
                )
        );
    }

    @Override
    public PosDamageAccountDto findPosDamageAccountById(Long posdamaccId) {
        if(posdamaccId == null){
            log.error("The posdamaccId passed as argument is null");
            throw new NullArgumentException("Le posdamaccId passe en argument est null");
        }
        Optional<PosDamageAccount> optionalPosDamageAccount = posDamAccountRepository.findPosDamageAccountById(posdamaccId);
        if(!optionalPosDamageAccount.isPresent()){
            log.error("There is no PosDamageAccount with the id {} sent", posdamaccId);
            throw new EntityNotFoundException("Aucun PosDamageAccount n'existe avec l'Id passe en parametre "+posdamaccId,
                    ErrorCode.POSDAMAGEACCOUNT_NOT_FOUND);
        }

        return PosDamageAccountDto.fromEntity(optionalPosDamageAccount.get());
    }

    @Override
    public PosDamageAccountDto findPosDamageAccountofArticleInPos(Long artId, Long posId) {
        if(posId == null){
            log.error("The posId passed as argument is null");
            throw new NullArgumentException("Le posId passe en argument est null");
        }
        if(artId == null){
            log.error("The artId passed as argument is null");
            throw new NullArgumentException("Le artId passe en argument est null");
        }

        Optional<PosDamageAccount> optionalPosDamageAccount = posDamAccountRepository.
                findPosDamageAccountofArticleInPos(artId, posId);
        if(!optionalPosDamageAccount.isPresent()){
            log.error("There is no PosDamageAccount with the argument artId =  {} and posId = {} sent", artId, posId);
            throw new EntityNotFoundException("Aucun PosDamageAccount n'existe avec les parametres passe en parametre " +
                    "artId = "+artId+ " posId = "+posId, ErrorCode.POSDAMAGEACCOUNT_NOT_FOUND);
        }

        return PosDamageAccountDto.fromEntity(optionalPosDamageAccount.get());
    }

    @Override
    public List<PosDamageAccountDto> findAllDamageAccountInPos(Long posId) {
        if(posId == null){
            log.error("The posId sent as argument is null");
            throw new NullArgumentException("L'argument passe est null");
        }
        Optional<List<PosDamageAccount>> optionalPosDamageAccountList = posDamAccountRepository.
                findAllDamageAccountInPos(posId);
        if(!optionalPosDamageAccountList.isPresent()){
            log.error("There is no pointofsale with the id posId");
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id passe en argument "+posId,
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }

        return optionalPosDamageAccountList.get().stream().map(PosDamageAccountDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<PosDamageAccountDto> findPageDamageAccountInPos(Long posId, int pagenum, int pagesize) {
        if(posId == null){
            log.error("The posId sent as argument is null");
            throw new NullArgumentException("L'argument passe est null");
        }
        Optional<Page<PosDamageAccount>> optionalPosDamageAccountPage = posDamAccountRepository.
                findPageDamageAccountInPos(posId, PageRequest.of(pagenum, pagesize));
        if(!optionalPosDamageAccountPage.isPresent()){
            log.error("There is no pointofsale with the id posId {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id passe en argument "+posId,
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }

        return optionalPosDamageAccountPage.get().map(PosDamageAccountDto::fromEntity);
    }

    @Override
    public List<PosDamageAccountDto> findAllDamageAccountofArticle(Long artId) {
        if(artId == null){
            log.error("The artId sent as argument is null");
            throw new NullArgumentException("L'argument passe est null");
        }
        Optional<List<PosDamageAccount>> optionalPosDamageAccountList = posDamAccountRepository.
                findAllDamageAccountofArticle(artId);
        if(!optionalPosDamageAccountList.isPresent()){
            log.error("There is no article with the id {}", artId);
            throw new EntityNotFoundException("Aucun article n'existe avec l'id passe en argument "+artId,
                    ErrorCode.ARTICLE_NOT_FOUND);
        }

        return optionalPosDamageAccountList.get().stream().map(PosDamageAccountDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<PosDamageAccountDto> findPageDamageAccountofArticle(Long artId, int pagenum, int pagesize) {
        if(artId == null){
            log.error("The artId sent as argument is null");
            throw new NullArgumentException("L'argument passe est null");
        }
        Optional<Page<PosDamageAccount>> optionalPosDamageAccountPage = posDamAccountRepository.
                findPageDamageAccountofArticle(artId, PageRequest.of(pagenum, pagesize));
        if(!optionalPosDamageAccountPage.isPresent()){
            log.error("There is no Article with the id artId {}", artId);
            throw new EntityNotFoundException("Aucun article n'existe avec l'id passe en argument "+artId,
                    ErrorCode.ARTICLE_NOT_FOUND);
        }

        return optionalPosDamageAccountPage.get().map(PosDamageAccountDto::fromEntity);
    }

    @Override
    public Boolean deletePosDamageAccountById(Long posdamaccId) {
        if(posdamaccId == null){
            log.error("The argument cannot be null for this method please check it");
            throw new NullArgumentException("L'argument passe est null et la methode ne peut donc s'executer");
        }

        Optional<PosDamageAccount> optionalPosDamageAccount = posDamAccountRepository.findPosDamageAccountById(posdamaccId);
        if(!optionalPosDamageAccount.isPresent()){
            log.error("There is no posDamageAccount with the precised id {}", posdamaccId);
            throw new EntityNotFoundException("Aucun posDamageAccount n'existe avec l'Id passe en argument dans la BD");
        }
        if(!isPosDamageAccountDeleteable(posdamaccId)){
            log.error("Le compte damge ne peut etre supprime");
            throw new EntityNotDeleteableException("Le compte damage designe ne peut etre supprime",
                    ErrorCode.POSCAPSULEACCOUNT_NOT_DELETEABLE);
        }
        posDamAccountRepository.delete(optionalPosDamageAccount.get());
        return true;
    }

    @Override
    public Boolean saveDamageOperation(Long posdamaccId, BigDecimal qte, OperationType operationType, Long userbmId,
                                       String opObject, String opDescription) {
        /******************************************************************
         * Se rassurer que les donnees dans la fonction ne sont pas null
         */
        if(posdamaccId == null || qte == null || userbmId == null || operationType == null){
            log.error("posdamaccId, qte or even userbmId is null ");
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
        if(!this.isPosDamageAccountExistWithId(posdamaccId)){
            log.error("The posdamaccId {} does not identify any account ", posdamaccId);
            throw  new EntityNotFoundException("Aucun PosDamageAccount n'existe avec le ID precise "+posdamaccId,
                    ErrorCode.POSCAPSULEACCOUNT_NOT_FOUND);
        }
        Optional<PosDamageAccount> optionalPosDamageAccount = posDamAccountRepository.
                findPosDamageAccountById(posdamaccId);
        //A ce niveau on na pas besoin de regarder si isPresent est true car on est sur que ca existe
        PosDamageAccount posDamageAccountToUpdate = optionalPosDamageAccount.get();

        BigDecimal solde = posDamageAccountToUpdate.getPdaNumber();
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
        posDamageAccountToUpdate.setPdaNumber(updatedSolde);

        posDamAccountRepository.save(posDamageAccountToUpdate);

        PosDamageOperation posdamo = new PosDamageOperation();
        posdamo.setPosdoNumberinmvt(qte);
        posdamo.setPosdoUserbm(optionalUserBM.get());
        posdamo.setPosdoPosDamageAccount(posDamageAccountToUpdate);

        Operation op = new Operation();
        op.setOpDate(new Date().toInstant());
        op.setOpDescription(opDescription);
        op.setOpObject(opObject);
        op.setOpType(operationType);
        posdamo.setPosdoOperation(op);
        //Il faut save le PosCapsuleOperation
        posDamOperationRepository.save(posdamo);

        return true;
    }

    public Boolean isPosDamageAccountExistWithId(Long pdaId){
        if(pdaId == null){
            log.error("pdaId is null");
            throw new NullArgumentException("le pdaId passe en argument de la methode est null");
        }
        Optional<PosDamageAccount> optionalPosDamageAccount = posDamAccountRepository.findPosDamageAccountById(pdaId);
        return optionalPosDamageAccount.isPresent()?true:false;
    }

    @Override
    public Boolean isPosDamageAccountDeleteable(Long posdamAccId) {
        return true;
    }
}
