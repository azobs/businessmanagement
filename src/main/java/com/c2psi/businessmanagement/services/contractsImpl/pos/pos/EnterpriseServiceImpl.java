package com.c2psi.businessmanagement.services.contractsImpl.pos.pos;

import com.c2psi.businessmanagement.Enumerations.UserBMType;
import com.c2psi.businessmanagement.dtos.pos.pos.*;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.Enterprise;
import com.c2psi.businessmanagement.models.Pointofsale;
import com.c2psi.businessmanagement.models.UserBM;
import com.c2psi.businessmanagement.repositories.pos.pos.EnterpriseRepository;
import com.c2psi.businessmanagement.repositories.pos.pos.PointofsaleRepository;
import com.c2psi.businessmanagement.repositories.pos.userbm.UserBMRepository;
import com.c2psi.businessmanagement.repositories.stock.provider.ProviderRepository;
import com.c2psi.businessmanagement.services.contracts.pos.pos.EnterpriseService;
import com.c2psi.businessmanagement.validators.pos.pos.EnterpriseValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.lang.IllegalArgumentException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value="EnterpriseServiceV1")
@Slf4j
@Transactional
public class EnterpriseServiceImpl implements EnterpriseService {

    private EnterpriseRepository entRepository;
    private PointofsaleRepository posRepository;
    private UserBMRepository userbmRepository;
    private ProviderRepository providerRepository;

    @Autowired
    public EnterpriseServiceImpl(
            EnterpriseRepository entRepository,
            PointofsaleRepository posRepository,
            UserBMRepository userbmRepository,
            ProviderRepository providerRepository
    )
    {
        this.entRepository = entRepository;
        this.posRepository = posRepository;
        this.userbmRepository = userbmRepository;
        this.providerRepository = providerRepository;
    }

    /*****
     * Retourne false si l'entreprise passe en parametre m'a pas d'administrateur et true s'il en a
     * @param entDto
     * @return Boolean
     */
    Boolean isUserBMAdminOfEnterpriseExist(EnterpriseDto entDto){
        Optional<UserBM> optionalUserBM = userbmRepository.findUserBMById(entDto.getEntAdminDto().getId());
        if(!optionalUserBM.isPresent()){
            return false;
        }
        return true;
    }

    /***
     * Cette methode verifie si l'objet userBM est du type precise
     * Elle retourne true si c'est le cas et false dans le cas contraire
     * @param userBMDto
     * @param userBMType
     * @return Boolean
     */
    Boolean isUserBMIsOfType(UserBMDto userBMDto, UserBMType userBMType){
        Optional<UserBM> optionalUserBM = userbmRepository.findUserBMById(userBMDto.getId());
        if(!optionalUserBM.isPresent()){
            return false;
        }
        else{
            if(!optionalUserBM.get().getBmUsertype().equals(userBMType)){
                return false;
            }
        }
        return true;
    }

    /****
     * Cette methode permet d'enregistrer ou d'ajouter une nouvelle entreprise dans le systeme.
     * Elle lance une exception du type:
     *      +InvalidEntityException:
     *          -si la donnee envoye n'est pas valide
     *          -si l'administrateur indique dans la requete n'existe pas
     *          -si l'administrateur existe mais n'est pas du type AdminEnterprise
     *      +DuplicateEntityException:
     *          -si une entreprise est deja existante avec les memes email, name et niu
     *
     * @param entDto
     * @return EnterpriseDto
     */
    @Override
    public EnterpriseDto saveEnterprise(EnterpriseDto entDto) {
        /***
         * If the userBM don't have a pointofsale, he must be of the type AdminBM if not
         * the record must not be registered
         */
        List<String> errors = EnterpriseValidator.validate(entDto);
        if(!errors.isEmpty()){
            log.error("Entity enterprise not valid {}", entDto);
            throw new InvalidEntityException("L'entreprise passe en argument n'est pas valide:  "+errors,
                    ErrorCode.ENTERPRISE_NOT_VALID, errors);
        }

        if(!this.isEnterpriseUnique(entDto.getEntName(), entDto.getEntNiu())){
            log.error("An entity enterprise already exist with the same name or Niu in the DB {}", entDto);
            throw new DuplicateEntityException("Une entreprise existe deja dans la BD avec le meme nom ou le " +
                    "le meme Niu :", ErrorCode.ENTERPRISE_DUPLICATED);
        }

        if(entDto.getEntAddressDto().getEmail() != null){
            if(!this.isEnterpriseUnique(entDto.getEntAddressDto().getEmail())){
                log.error("An entity enterprise already exist with the same email in the DB {}", entDto);
                throw new DuplicateEntityException("Une entreprise existe deja dans la BD avec le meme email",
                        ErrorCode.ENTERPRISE_DUPLICATED);
            }
        }

        if(Optional.ofNullable(entDto.getEntAdminDto()).isPresent()){
            /**
             * Si l'admin n'est pas precise alors la validation va echoue. mais s'il est precise il faut
             * encore se rassurer que celui qu'on a precise existe en BD et est du bon type
             */
            if(!this.isUserBMAdminOfEnterpriseExist(entDto)){
                log.error("The administrator precised for that enterprise does not exist");
                throw new InvalidEntityException("L'administrateur indique dans cette requete n'existe " +
                        "pas!", ErrorCode.ENTERPRISE_NOT_VALID);
            }
            else{
                if(!this.isUserBMIsOfType(entDto.getEntAdminDto(), UserBMType.AdminEnterprise)){
                    log.error("The administrator precised for that enterprise is not an AdminEnterprise");
                    throw new InvalidEntityException("L'administrateur indique dans cette requete n'est pas " +
                            "du type requis", ErrorCode.ENTERPRISE_NOT_VALID);
                }
            }
        }
        log.info("After all verification, the record {} can be done on the DB", entDto);
        return EnterpriseDto.fromEntity(
                entRepository.save(
                        EnterpriseDto.toEntity(entDto)
                )
        );
    }

    /*********************************************************************************************************
     *  Cette methode permet de modifier ou de mettre a jour  les donnees d'une entreprise qui
     *  existe deja dans le systeme. Mais pas son administrateur car c'est dans une autre procedure
     *  que la mise a jour de l'admistrateur est possible
     *  Elle lance une exception du type:
     *      +InvalidEntityException si:
     *          -L'entreprise a mettre a jour n'est pas valide apres modification
     *      +EntityNotFoundException si:
     *          -L'entreprise a mettre a jour n'existe pas dans le systeme donc n'est pas enregistre en BD
     *      +DuplicateEntityException:
     *          -Le nouvel email identifie deja une autre adresse
     *          -Le nouveau nom identifie deja une autre entreprise
     *          -Le nouveau NIU identifie deja une autre entreprise
     * @param entDto
     * @return EnterpriseDto
     */
    @Override
    public EnterpriseDto updateEnterprise(EnterpriseDto entDto) {
        List<String> errors = EnterpriseValidator.validate(entDto);
        if(!errors.isEmpty()){
            log.error("Entity enterprise not valid {}", entDto);
            throw new InvalidEntityException("L'entreprise passé en argument n'est pas valide:  "+errors,
                    ErrorCode.ENTERPRISE_NOT_VALID, errors);
        }
        /***
         * Les parametres à modifier sans controle sont:
         * entRegime, entSocialreason, entDescription, entAcronym, entAddress(sans l'email),
         * Les parametres sur lesquelles il faut faire attention sont:
         * entNiu, entName, email
         */
        if(!this.isEnterpriseExistWithId(entDto.getId())){
            throw new EntityNotFoundException("L'Enterprise a update n'existe pas dans la BD",
                    ErrorCode.ENTERPRISE_NOT_VALID, errors);
        }
        //Tout est bon et on peut maintenant recuperer l'enterprise a modifier
        Enterprise entToUpdate = EnterpriseDto.toEntity(this.findEnterpriseById(entDto.getId()));

        entToUpdate.setEntAcronym(entDto.getEntAcronym());
        entToUpdate.setEntDescription(entDto.getEntDescription());
        entToUpdate.setEntRegime(entDto.getEntRegime());
        entToUpdate.setEntSocialreason(entDto.getEntSocialreason());
        if(entDto.getEntAddressDto() != null) {
            entToUpdate.getEntAddress().setLocalisation(entDto.getEntAddressDto().getLocalisation());
            entToUpdate.getEntAddress().setPays(entDto.getEntAddressDto().getPays());
            entToUpdate.getEntAddress().setVille(entDto.getEntAddressDto().getVille());
            entToUpdate.getEntAddress().setQuartier(entDto.getEntAddressDto().getQuartier());
            entToUpdate.getEntAddress().setNumtel3(entDto.getEntAddressDto().getNumtel3());
            entToUpdate.getEntAddress().setNumtel2(entDto.getEntAddressDto().getNumtel2());
            entToUpdate.getEntAddress().setNumtel1(entDto.getEntAddressDto().getNumtel1());
        }

        if(entToUpdate.getEntAddress().getEmail() != null && entDto.getEntAddressDto().getEmail() != null) {
            if (!entToUpdate.getEntAddress().getEmail().equalsIgnoreCase(entDto.getEntAddressDto().getEmail())) {
                /**
                 * Si les adresse email ne sont pas les meme alors on souhaite modifier les adresses email
                 * Il faut donc verifier l'unicite de l'adresse email en BD
                 */
                if (!this.isEnterpriseExistWithEmail(entDto.getEntAddressDto().getEmail())) {
                    entToUpdate.getEntAddress().setEmail(entDto.getEntAddressDto().getEmail());
                } else {
                    throw new DuplicateEntityException("Une entreprise existe en BD avec cet email",
                            ErrorCode.ENTERPRISE_DUPLICATED);
                }
            }
        }

        if(!entToUpdate.getEntName().equalsIgnoreCase(entDto.getEntName())){
            /***
             * Si le nom de l'entreprise pris en BD et celui envoye en Dto sont different alors on souhaite aussi
             * modifier le nom et pour cela des verification doivent etre faite
             */
            if(!this.isEnterpriseExistWithName(entDto.getEntName())){
                entToUpdate.setEntName(entDto.getEntName());
            }
            else{
                throw new DuplicateEntityException("Une entreprise existe en BD avec ce nom",
                        ErrorCode.ENTERPRISE_DUPLICATED);
            }
        }

        if(!entToUpdate.getEntNiu().equalsIgnoreCase(entDto.getEntNiu())){
            /***
             * Si le Niu de l'entreprise pris en BD et celui envoye en Dto sont different alors on souhaite aussi
             * modifier le Niu et pour cela des verification doivent etre faite
             */
            if(!this.isEnterpriseExistWithNiu(entDto.getEntNiu())){
                entToUpdate.setEntNiu(entDto.getEntNiu());
            }
            else{
                throw new DuplicateEntityException("Une entreprise existe en BD avec cet NIU",
                        ErrorCode.ENTERPRISE_DUPLICATED);
            }
        }

        return EnterpriseDto.fromEntity(entRepository.save(entToUpdate));
    }

    public Boolean isEnterpriseExistWithId(Long entId) {
        if(entId == null){
            log.error("entId is null");
            throw new NullArgumentException("le entId passe en argument de la methode est null");
        }
        Optional<Enterprise> optionalEnterprise = entRepository.findEnterpriseById(entId);
        return optionalEnterprise.isPresent()?true:false;
    }

    public Boolean isUserBMExistWithId(Long userBMId) {
        if(userBMId == null){
            log.error("userBMId is null");
            throw new NullArgumentException("le userBMId passe en argument de la methode est null");
        }
        Optional<UserBM> optionalUserBM = userbmRepository.findUserBMById(userBMId);
        return optionalUserBM.isPresent()?true:false;
    }

    /*********************************************************************************************************
     * Cette methode va permettre de mettre a jour l'admistrateur d'une entreprise.
     * Elle lance une exception de type:
     *      +NullArgumentException si: les l'un ou tous les arguments obligatoires de la methode sont null
     *      +EntityNotFoundException si:
     *          -l'ID de l'entreprise precise pour lequel l'admin sera change n'identifie aucune Entreprise en BD
     *          -L'ID du nouvel admin n'identifie aucun userbm en BD
     *      +IllegalArgumentException si:
     *          -Le nouveau userBM qu'on veut mettre admin n'est pas dy type requis
     * @param entId
     * @param userBMAdminId
     * @return EnterpriseDto
     */
    @Override
    public EnterpriseDto setAdminEnterprise(Long entId, Long userBMAdminId) {
        if(entId == null || userBMAdminId == null){
            throw new NullArgumentException("Le Enterprise ou le userBMAdmin dans la methode setAdminEnterprise " +
                    "est null");
        }
        if(!this.isEnterpriseExistWithId(entId)){
            throw new EntityNotFoundException("Le Enterprise a update n'existe pas dans la BD",
                    ErrorCode.ENTERPRISE_NOT_FOUND);
        }
        if(!this.isUserBMExistWithId(userBMAdminId)){
            throw new EntityNotFoundException("Le UserBM qu'on veut set comme admin n'existe pas dans la BD",
                    ErrorCode.USERBM_NOT_FOUND);
        }

        EnterpriseDto entDto = this.findEnterpriseById(entId);
        UserBMDto userBMDtoAdmin = this.findUserBMById(userBMAdminId);

        /*if(!this.isEnterpriseExistWithId(entDto.getId())){
            throw new EntityNotFoundException("Le Enterprise a update n'existe pas dans la BD",
                    ErrorCode.ENTERPRISE_NOT_FOUND);
        }*/
        //Tout est bon et on peut maintenant recuperer l'enterprise a modifier
        Enterprise entToSetAdmin = EnterpriseDto.toEntity(this.findEnterpriseById(entDto.getId()));

        /**
         * Maintenant il faut verifier que le nouvel admin precise existe et est bien du type AdminEnterprise
         */
        if(!this.isUserBMIsOfType(userBMDtoAdmin, UserBMType.AdminEnterprise)){
            throw new IllegalArgumentException("Le userbm precise est soit inexistant soit n'est pas du type " +
                    "AdminEnterprise ");
        }
        /**
         * Tout est desormais bon tant pour l'entreprise dont l'admin sera modifie que pour le nouvel admin
         */
        entToSetAdmin.setEntAdmin(UserBMDto.toEntity(userBMDtoAdmin));

        return EnterpriseDto.fromEntity(entRepository.save(entToSetAdmin));
    }

    /***********************************************************************************************
     * Cette methode retourne l'entreprise dont le nom est passe en parametre.
     * Elle leve un exception du type:
     *      +NullArgumentException: si le parametre nom envoye pour effectuer la recherche est null
     *      +EntityNotFoundException: si le nom renseigne n'identifie aucune entreprise dans la BD
     * @param entName
     * @return
     * @throws EntityNotFoundException
     */
    @Override
    public EnterpriseDto findEnterpriseByName(String entName) throws EntityNotFoundException {
        if(!StringUtils.hasLength(entName)){
            log.error("Enterprise name is null");
            throw new NullArgumentException("le nom passe en argument de la methode est null: ");
        }

        Optional<Enterprise> optionalEnterprise = entRepository.findEnterpriseByEntName(entName);

        if(optionalEnterprise.isPresent()){
            return EnterpriseDto.fromEntity(optionalEnterprise.get());
        }
        else{
            throw new EntityNotFoundException("Aucune entreprise avec le nom ="+entName
                    +" n'a été trouve dans la BDD ", ErrorCode.ENTERPRISE_NOT_FOUND);
        }

        /*return Optional.of(EnterpriseDto.fromEntity(optionalEnterprise.get())).orElseThrow(()->
                new EntityNotFoundException("Aucune entreprise avec le nom ="+entName
                        +" n'a été trouve dans la BDD ", ErrorCode.ENTERPRISE_NOT_FOUND));*/
    }

    public Boolean isEnterpriseExistWithName(String entName) {
        if(!StringUtils.hasLength(entName)){
            log.error("Enterprise name is null: ");
            throw new NullArgumentException("le nom passe en argument de la methode est null");
        }

        Optional<Enterprise> optionalEnterprise = entRepository.findEnterpriseByEntName(entName);

        return optionalEnterprise.isPresent() ? true: false;
    }

    @Override
    public EnterpriseDto findEnterpriseByNiu(String entNiu) {
        if(!StringUtils.hasLength(entNiu)){
            log.error("Enterprise entNiu is null");
            throw new NullArgumentException("le Niu passe en argument de la methode est null");
        }

        Optional<Enterprise> optionalEnterprise = entRepository.findEnterpriseByEntNiu(entNiu);

        if(optionalEnterprise.isPresent()){
            return EnterpriseDto.fromEntity(optionalEnterprise.get());
        }
        else {
            throw new EntityNotFoundException("Aucune entreprise avec le Niu ="+entNiu
                    +" n'a été trouve dans la BDD ", ErrorCode.ENTERPRISE_NOT_FOUND);
        }

        /*return Optional.of(EnterpriseDto.fromEntity(optionalEnterprise.get())).orElseThrow(()->
                new EntityNotFoundException("Aucune entreprise avec le Niu ="+entNiu
                        +" n'a été trouve dans la BDD ", ErrorCode.ENTERPRISE_NOT_FOUND));*/
    }

    public Boolean isEnterpriseExistWithNiu(String entNiu) {
        if(!StringUtils.hasLength(entNiu)){
            log.error("Enterprise entNiu is null");
            throw new NullArgumentException("le Niu passe en argument de la methode est null");
        }

        Optional<Enterprise> optionalEnterprise = entRepository.findEnterpriseByEntNiu(entNiu);

        return optionalEnterprise.isPresent()?true:false;
    }

    public Boolean isEnterpriseExistWithEmail(String entEmail) {
        if(!StringUtils.hasLength(entEmail)){
            log.error("Enterprise entEmail is null");
            throw new NullArgumentException("L'Email passe en argument de la methode est null");
        }

        Optional<Enterprise> optionalEnterprise = entRepository.findEnterpriseByEntEmail(entEmail);

        return optionalEnterprise.isPresent()?true:false;
    }

    @Override
    public Boolean isEnterpriseUnique(String entName, String entNiu) {
        if(!StringUtils.hasLength(entName)){
            log.error("Enterprise entName is null");
            throw new NullArgumentException("le entName passe en argument de la methode est null");
        }
        if(!StringUtils.hasLength(entNiu)){
            log.error("Enterprise entNiu is null");
            throw new NullArgumentException("le Niu passe en argument de la methode est null");
        }

        Boolean isEntExistWithName = this.isEnterpriseExistWithName(entName);
        Boolean isEntExistWithNiu = this.isEnterpriseExistWithNiu(entNiu);
        if(isEntExistWithName || isEntExistWithNiu){
            return false;
        }
        return true;
    }

    @Override
    public Boolean isEnterpriseUnique(String entEmail) {

        if(!StringUtils.hasLength(entEmail)){
            log.error("Enterprise entEmail is null");
            throw new NullArgumentException("L'Email passe en argument de la methode est null");
        }

        Boolean isEntExistWithEmail = this.isEnterpriseExistWithEmail(entEmail);
        if(isEntExistWithEmail){
            return false;
        }
        return true;
    }


    @Override
    public EnterpriseDto findEnterpriseById(Long entId) {
        if(entId == null){
            log.error("entId is null");
            throw new NullArgumentException("le entId passe en argument de la methode findEnterpriseById est null");
        }

        Optional<Enterprise> optionalEnterprise = entRepository.findEnterpriseById(entId);

        if(optionalEnterprise.isPresent()){
            return EnterpriseDto.fromEntity(optionalEnterprise.get());
        }
        else {
            throw new EntityNotFoundException("Aucune entreprise avec le id ="+entId
                    +" n'a ete trouve dans la BDD ", ErrorCode.ENTERPRISE_NOT_FOUND);
        }

        /*return Optional.of(EnterpriseDto.fromEntity(optionalEnterprise.get())).orElseThrow(()->
                new EntityNotFoundException("Aucune entreprise avec le id ="+entId
                        +" n'a été trouve dans la BDD ", ErrorCode.ENTERPRISE_NOT_FOUND));*/
    }

    @Override
    public UserBMDto findUserBMById(Long userBMId) {
        if(userBMId == null){
            log.error("userBMId is null");
            throw new NullArgumentException("le userBMId passe en argument de la methode findUserBMById est null");
        }

        Optional<UserBM> optionalUserBM = userbmRepository.findUserBMById(userBMId);

        /*return Optional.of(UserBMDto.fromEntity(optionalUserBM.get())).orElseThrow(()->
                new EntityNotFoundException("Aucun UserBM avec le id ="+userBMId
                        +" n'a été trouve dans la BDD ", ErrorCode.USERBM_NOT_FOUND));*/
        if(optionalUserBM.isPresent()){
            return UserBMDto.fromEntity(optionalUserBM.get());
        }
        else {
            throw new EntityNotFoundException("Aucun UserBM avec le id ="+userBMId
                    +" n'a été trouve dans la BDD ", ErrorCode.USERBM_NOT_FOUND);
        }
    }

    @Override
    public Boolean isEnterpriseDeleteable(Long entId) {
        return null;
    }

    public Boolean deleteEnterprise(Enterprise ent){
        /***
         * It is not possible to detroy an enterprise wich contain pointofsale already
         */
        List<PointofsaleDto> pointofsaleDtoList = findAllPosofEnterprise(ent.getId());
        if(pointofsaleDtoList.size() == 0){
            entRepository.delete(ent);
            return true;
        }
        throw new EntityNotDeleteableException("L'entreprise d'ID "+ent.getId()
             +" ne peut etre supprime car contient au moins un pointofsale ", ErrorCode.ENTERPRISE_NOT_DELETEABLE);

    }

    /*************************************************************************************************
     * Cette methode recherche et supprime de la BD l'entreprise dont le nom est passe en parametre
     * Elle leve l'exception:
     *      +NullArgumentException: Si le parametre passe en parametre est null
     *      +EntityNotRemovableException: Si le parametre passe imbrique dautres
     * @param entName
     * @return
     */
    @Override
    public Boolean deleteEnterpriseByName(String entName) {
        if(!StringUtils.hasLength(entName)){
            log.error("Enterprise entName is null");
            throw new NullArgumentException("le entName passe en argument de la methode est null");
        }
        Optional<Enterprise> optionalEnterprise = entRepository.findEnterpriseByEntName(entName);
        if(optionalEnterprise.isPresent()){
            if(isEnterpriseDeleteable(optionalEnterprise.get().getId())) {
                return deleteEnterprise(optionalEnterprise.get());
            }
            throw new EntityNotDeleteableException("L'entreprise ne peut etre supprime car deja associe avec dautre " +
                    "elements ", ErrorCode.ENTERPRISE_NOT_DELETEABLE);
        }
        throw new EntityNotFoundException("Aucune entreprise n'existe avec le name passe en argument "+entName,
                ErrorCode.ENTERPRISE_NOT_FOUND);
    }

    @Override
    public Boolean deleteEnterpriseByNiu(String entNiu) {
        if(!StringUtils.hasLength(entNiu)){
            log.error("Enterprise entNiu is null");
            throw new NullArgumentException("le entNiu passe en argument de la methode est null");
        }
        Optional<Enterprise> optionalEnterprise = entRepository.findEnterpriseByEntNiu(entNiu);
        if(optionalEnterprise.isPresent()){
            if(isEnterpriseDeleteable(optionalEnterprise.get().getId())) {
                return deleteEnterprise(optionalEnterprise.get());
            }
            throw new EntityNotDeleteableException("L'entreprise ne peut etre supprime car deja associe avec dautre " +
                    "elements ", ErrorCode.ENTERPRISE_NOT_DELETEABLE);
        }
        throw new EntityNotFoundException("Aucune entreprise n'existe avec le niu passe en argument "+entNiu,
                ErrorCode.ENTERPRISE_NOT_FOUND);
    }

    @Override
    public Boolean deleteEnterpriseById(Long entId) {
        if(entId==null){
            log.error("Enterprise ID is null");
            throw new NullArgumentException("L'id specifie est null");
        }
        Optional<Enterprise> optionalEnterprise = entRepository.findEnterpriseById(entId);
        if(optionalEnterprise.isPresent()){
            if(isEnterpriseDeleteable(entId)) {
                return deleteEnterprise(optionalEnterprise.get());
            }
            throw new EntityNotDeleteableException("L'entreprise ne peut etre supprime car deja associe avec dautre " +
                    "elements ", ErrorCode.ENTERPRISE_NOT_DELETEABLE);
        }
        throw new EntityNotFoundException("Aucune entreprise n'existe avec le id passe en argument "+entId,
                ErrorCode.ENTERPRISE_NOT_FOUND);
    }

    @Override
    public List<EnterpriseDto> findAllEnterprise() {
        List<EnterpriseDto> listofEnterprise = entRepository
                .findAll().stream().map(EnterpriseDto::fromEntity).collect(Collectors.toList());
        return listofEnterprise;
    }

    @Override
    public List<PointofsaleDto> findAllPosofEnterprise(Long entId) {
        if(entId==null){
            log.error("Enterprise ID is null");
            throw new NullArgumentException("L'id specifie est null");
        }
        EnterpriseDto entDto = this.findEnterpriseById(entId);
        List<Pointofsale> listofPos = posRepository.findAllByPosEnterprise(EnterpriseDto.toEntity(entDto));
        return listofPos.stream().map(PointofsaleDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public BigDecimal getTurnover(Long entId, Date startDate, Date endDate) {
        return null;
    }

    @Override
    public List<UserBMDto> findAllEmployeofEnterprise(Long entId) {
        List<PointofsaleDto> listofPosofEnt = this.findAllPosofEnterprise(entId);
        List<UserBMDto> listofEmployeofEnt = new ArrayList<>();
        for(PointofsaleDto pos: listofPosofEnt){
            List<UserBMDto> listofEmployeofPos = userbmRepository.findAllByBmPos(PointofsaleDto.toEntity(pos).getId())
                    .stream().map(UserBMDto::fromEntity).collect(Collectors.toList());
            listofEmployeofEnt.addAll(listofEmployeofPos);
        }
        return listofEmployeofEnt;
    }

    @Override
    public List<ProviderDto> findAllProviderofEnterprise(Long entId) {
        List<PointofsaleDto> listofPosofEnt = this.findAllPosofEnterprise(entId);
        List<ProviderDto> listofProviderEnt = new ArrayList<>();
        for(PointofsaleDto pos: listofPosofEnt){
            List<ProviderDto> listofProviderofPos = providerRepository.findAllByProviderPos(
                    PointofsaleDto.toEntity(pos)
            ).stream().map(ProviderDto::fromEntity).collect(Collectors.toList());
            listofProviderEnt.addAll(listofProviderofPos);
        }
        return listofProviderEnt;
    }

    @Override
    public BigDecimal getTotalCash(Long entId) {
        List<PointofsaleDto> listofPosofEnt = this.findAllPosofEnterprise(entId);
        BigDecimal totalCashofEnt = new BigDecimal(0);
        for(PointofsaleDto pos: listofPosofEnt){
            totalCashofEnt.add(pos.getPosCashaccountDto().getPcaBalance());
        }
        return totalCashofEnt;
    }

    @Override
    public Integer getNumberofDamage(Long entId) {
        List<PointofsaleDto> listofPosofEnt = this.findAllPosofEnterprise(entId);
        Integer totalDamageofEnt = Integer.valueOf(0);

        return totalDamageofEnt;
    }

    @Override
    public Integer getNumberofDamage(Long entId, Long artId) {
        List<PointofsaleDto> listofPosofEnt = this.findAllPosofEnterprise(entId);
        Integer totalDamageofEnt = Integer.valueOf(0);

        return totalDamageofEnt;
    }

    @Override
    public Integer getNumberofCapsule(Long entId) {
        List<PointofsaleDto> listofPosofEnt = this.findAllPosofEnterprise(entId);
        Integer totalCapsuleofEnt = Integer.valueOf(0);

        return totalCapsuleofEnt;
    }

    @Override
    public Integer getNumberofCapsule(Long entId, Long artId) {
        List<PointofsaleDto> listofPosofEnt = this.findAllPosofEnterprise(entId);
        Integer totalCapsuleofEnt = Integer.valueOf(0);

        return totalCapsuleofEnt;
    }

    @Override
    public Integer getNumberofPackaging(Long entId) {
        List<PointofsaleDto> listofPosofEnt = this.findAllPosofEnterprise(entId);
        Integer totalPackagingofEnt = Integer.valueOf(0);

        return totalPackagingofEnt;
    }

    @Override
    public Integer getNumberofPackaging(Long entId, Long providerId) {
        List<PointofsaleDto> listofPosofEnt = this.findAllPosofEnterprise(entId);
        Integer totalPackagingofEnt = Integer.valueOf(0);

        return totalPackagingofEnt;
    }


}
