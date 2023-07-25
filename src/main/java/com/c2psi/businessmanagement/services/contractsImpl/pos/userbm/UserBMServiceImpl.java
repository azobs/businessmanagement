package com.c2psi.businessmanagement.services.contractsImpl.pos.userbm;

import com.c2psi.businessmanagement.Enumerations.UserBMState;
import com.c2psi.businessmanagement.Enumerations.UserBMType;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.exceptions.NullArgumentException;
import com.c2psi.businessmanagement.models.Enterprise;
import com.c2psi.businessmanagement.models.Pointofsale;
import com.c2psi.businessmanagement.models.UserBM;
import com.c2psi.businessmanagement.repositories.pos.pos.EnterpriseRepository;
import com.c2psi.businessmanagement.repositories.pos.pos.PointofsaleRepository;
import com.c2psi.businessmanagement.repositories.pos.userbm.UserBMRepository;
import com.c2psi.businessmanagement.services.contracts.pos.userbm.UserBMService;
import com.c2psi.businessmanagement.validators.pos.userbm.UserBMValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value="UserBMService1")
@Slf4j
@Transactional
public class UserBMServiceImpl implements UserBMService {

    private UserBMRepository userBMRepository;
    private PointofsaleRepository posRepository;
    private EnterpriseRepository entRepository;

    @Autowired
    public UserBMServiceImpl(
            UserBMRepository userBMRepository1,
            PointofsaleRepository posRepository1,
            EnterpriseRepository entRepository1

    )
    {
       this.userBMRepository = userBMRepository1;
       this.posRepository = posRepository1;
       this.entRepository = entRepository1;
    }

    @Override
    public UserBMDto saveUserBM(UserBMDto userBMDto) {

        List<String> errors = UserBMValidator.validate(userBMDto);
        if(!errors.isEmpty()){
            log.error("Entity userBM not valid {}", userBMDto);
            throw new InvalidEntityException("Le userBM passé en argument n'est pas valide: "+errors,
                    ErrorCode.USERBM_NOT_VALID, errors);
        }

        if(!this.isUserBMFullnameUnique(userBMDto.getBmName(), userBMDto.getBmSurname(), userBMDto.getBmDob())){
            log.error("An entity userbm already exist with the same name, surname and dob in the DB {}",
                    userBMDto);
            throw new DuplicateEntityException("Un UserBM existe deja dans la BD avec " +
                    "les mêmes nom, prenom et dob ", ErrorCode.USERBM_DUPLICATED);
        }

        if(!this.isUserBMCniUnique(userBMDto.getBmCni())){
            log.error("An entity userbm already exist with the same cni number in the DB {}",
                    userBMDto);
            throw new DuplicateEntityException("Un UserBM existe deja dans la BD avec " +
                    "le meme cni number ", ErrorCode.USERBM_DUPLICATED);
        }

        if(!this.isUserBMLoginUnique(userBMDto.getBmLogin())){
            log.error("An entity userbm already exist with the same login {}",
                    userBMDto);
            throw new DuplicateEntityException("Un UserBM existe deja dans la BD avec " +
                    "le meme login ", ErrorCode.USERBM_DUPLICATED);
        }

        if(userBMDto.getBmAddressDto().getEmail() != null) {
            if (!this.isUserBMEmailUnique(userBMDto.getBmAddressDto().getEmail())) {
                log.error("An entity userbm already exist with the same email in the DB {}",
                        userBMDto);
                throw new DuplicateEntityException("Un UserBM existe deja dans la BD avec " +
                        "une adresse contenant la meme adresse email ", ErrorCode.USERBM_DUPLICATED);
            }
        }

        /*
        Il faut se rassurer si l'admin qu'on veut ajouter est un employe auquel cas il faut
        se rassurer que le pointde vente est fixé pour cet employe.
         */
        UserBMType userBMType = userBMDto.getBmUsertype();
        /*****
         *Si userBMType est different de AdminBM ou AdminEnterprise alors on ne peut ajouter le user
         *que si pos_id n'est pas null
         */

        if(userBMType.equals(UserBMType.AdminBM)){
            userBMDto.setBmPosId(Long.valueOf(0));
            userBMDto.setBmEnterpriseId(Long.valueOf(0));

        }

        if(userBMType.equals(UserBMType.AdminEnterprise)){
            if(!Optional.ofNullable(userBMDto.getBmEnterpriseId()).isPresent()){
                log.error("The Id of the entreprise link with the UserBM to save cannot be null");
                throw new InvalidEntityException("L'id de l'entreprise lie au UserBm quon enregistre ne peut etre null",
                        ErrorCode.USERBM_NOT_VALID);
            }
            else{
                Optional<Enterprise> optionalEnterprise = entRepository.findEnterpriseById(userBMDto.getBmEnterpriseId());
                if(!optionalEnterprise.isPresent()){
                    log.error("The enterprise proposed for the userbm must exist in the DB. ");
                    throw new InvalidEntityException("L'enterprise précisé pour le UserBM n'existe " +
                            "pas dans la BD", ErrorCode.ENTERPRISE_NOT_FOUND);
                }
                /******
                 * Ici on est sur que l'entreprise existe avec l'id envoye
                 * On va donc set le Pointofsale a 0
                 */
                userBMDto.setBmEnterpriseId(Long.valueOf(0));
            }
        }

        if(userBMType.equals(UserBMType.Employe)){
            if(!Optional.ofNullable(userBMDto.getBmPosId()).isPresent()){
                log.error("The Id of the Pointofsale can't be null because he must be link with a Pointofsale");
                throw new InvalidEntityException("Le USERBM n'est pas valide. Il devrait etre " +
                        "lie a un point de vente existant", ErrorCode.USERBM_NOT_VALID);
            }
            Optional<Pointofsale> optionalPos = posRepository.findPointofsaleById(
                    userBMDto.getBmPosId());
            if(!optionalPos.isPresent()){
                log.error("The pointofsale proposed for the userbm must exist in the DB. ");
                throw new InvalidEntityException("Le Point de vente précisé pour le UserBM n'existe " +
                        "pas dans la BD", ErrorCode.POINTOFSALE_NOT_FOUND);
            }
            /*****
             *Ici on est sur que le pointofsale existe avec l'id envoye
             */
        }


        /***********************************************************************************
         * Il faut chiffrer le mot de pass avant de le save dans la base de donnee
         */
        //Pbkdf2PasswordEncoder p = Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
        BCryptPasswordEncoder p = new BCryptPasswordEncoder();
        String passToEncoded = "myPassword";
        String passEncoded = p.encode(passToEncoded);
        Boolean matches = p.matches("myPassword", passEncoded);
        log.info("passToEncoded = {} passEncoded = {} and the matches = {}", passToEncoded, passEncoded, matches);

        userBMDto.setBmPassword(p.encode(userBMDto.getBmPassword()));
        userBMDto.setBmRepassword(p.encode(userBMDto.getBmRepassword()));




        log.info("After all verification on the UserBMDto sent in the request, the record can be done: " +
                "{} ", userBMDto);
        UserBM userBMSaved = userBMRepository.save(UserBMDto.toEntity(userBMDto));

        return UserBMDto.fromEntity(
                userBMSaved
        );
    }

    @Override
    public UserBMDto updateUserBM(UserBMDto userBMDto) {
        List<String> errors = UserBMValidator.validate(userBMDto);
        if(!errors.isEmpty()){
            log.error("Entity userBM not valid {}", userBMDto);
            throw new InvalidEntityException("Le userBM passé en argument n'est pas valide",
                    ErrorCode.USERBM_NOT_VALID, errors);
        }

        if(!this.isUserBMExistWithId(userBMDto.getId())){
            throw new EntityNotFoundException("Le userBM a update n'existe pas dans la BD",
                    ErrorCode.USERBM_NOT_FOUND, errors);
        }

        //Tous est bon donc on peut maintenant recuperer le UserBM a modifier
        UserBM userBMToUpdate = UserBMDto.toEntity(this.findUserBMById(userBMDto.getId()));
        //userBMToUpdate.setBmAddress(AddressDto.toEntity(userBMDto.getBmAddressDto()));
        if(!userBMToUpdate.getBmCni().equalsIgnoreCase(userBMDto.getBmCni())){
            log.info("Alors le CNI pourra etre modifie");
            if(!this.isUserBMExistWithCni(userBMDto.getBmCni())){
                userBMToUpdate.setBmCni(userBMDto.getBmCni());
            }
            else{
                throw new DuplicateEntityException("Un UserBM existe deja dans la BD avec le meme Cni number "
                        , ErrorCode.USERBM_DUPLICATED);
            }
        }
        if(!userBMToUpdate.getBmLogin().equalsIgnoreCase(userBMDto.getBmLogin())){
            log.info("Alors le login pourra etre modifie");
            if(!this.isUserBMExistWithLogin(userBMDto.getBmLogin())){
                userBMToUpdate.setBmLogin(userBMDto.getBmLogin());
            }
            else{
                throw new DuplicateEntityException("Un UserBM existe deja dans la BD avec le meme Login "
                        , ErrorCode.USERBM_DUPLICATED);
            }
        }

        if(userBMDto.getBmAddressDto().getEmail() != null && userBMToUpdate.getBmAddress().getEmail() != null) {
            if (!userBMToUpdate.getBmAddress().getEmail().equals(userBMDto.getBmAddressDto().getEmail())) {
                log.info("Alors l'email pourra etre modifie");
                if (!this.isUserBMExistWithEmail(userBMDto.getBmAddressDto().getEmail())) {
                    userBMToUpdate.getBmAddress().setEmail(userBMDto.getBmAddressDto().getEmail());
                } else {
                    throw new DuplicateEntityException("Un UserBM existe deja dans la BD avec le meme Email "
                            , ErrorCode.USERBM_DUPLICATED);
                }
            }
        }

        if(!userBMToUpdate.getBmName().equalsIgnoreCase(userBMDto.getBmName()) ||
                !userBMToUpdate.getBmSurname().equalsIgnoreCase(userBMDto.getBmSurname()) ||
                (userBMToUpdate.getBmDob().compareTo(userBMDto.getBmDob()))!=0){
            if(!this.isUserBMExistWithNameAndDob(userBMDto.getBmName(), userBMDto.getBmSurname(),
                    userBMDto.getBmDob())){
                userBMToUpdate.setBmName(userBMDto.getBmName());
                userBMToUpdate.setBmSurname(userBMDto.getBmSurname());
                userBMToUpdate.setBmDob(userBMDto.getBmDob());
            }
            else{
                throw new DuplicateEntityException("Un UserBM existe deja dans la BD avec le meme nom, prenom " +
                        "et date de naissance ", ErrorCode.USERBM_DUPLICATED);
            }
        }

        /****
         * La mise a jour de l'etat doit etre faites dans une methode speciale
         * et on ne met pas a jour le type d'un utilisateur faut le supprimer et
         * le recreer dans avec un autre type
         */
        /*userBMToUpdate.setBmState(userBMDto.getBmState());
        userBMToUpdate.setBmUsertype(userBMDto.getBmUsertype());*/

        if(userBMDto.getBmAddressDto() != null){
            userBMToUpdate.getBmAddress().setNumtel1(userBMDto.getBmAddressDto().getNumtel1());
            userBMToUpdate.getBmAddress().setNumtel2(userBMDto.getBmAddressDto().getNumtel2());
            userBMToUpdate.getBmAddress().setNumtel3(userBMDto.getBmAddressDto().getNumtel3());
            userBMToUpdate.getBmAddress().setQuartier(userBMDto.getBmAddressDto().getQuartier());
            userBMToUpdate.getBmAddress().setVille(userBMDto.getBmAddressDto().getVille());
            userBMToUpdate.getBmAddress().setPays(userBMDto.getBmAddressDto().getPays());
            userBMToUpdate.getBmAddress().setLocalisation(userBMDto.getBmAddressDto().getLocalisation());
        }


        /*********************************************************************************
         * On peut aussi dans la requete vouloir fixer ou modifier le pointofsale d'un
         * UserBM
         */
        if(Optional.ofNullable(userBMDto.getBmPosId()).isPresent()){
            if(Optional.ofNullable(userBMToUpdate.getBmPosId()).isPresent()){
                if(!userBMToUpdate.getBmPosId().equals(userBMDto.getBmPosId())){
                    /********************************************************************************
                     * Alors on verifie que le nouveau pointofsale indique existe vraiment en BD
                     */
                    Optional<Pointofsale> optionalPointofsale = posRepository.findPointofsaleById(userBMDto.getBmPosId());
                    if(!optionalPointofsale.isPresent()){
                        log.error("The new Pointofsale precised is not in the DB");
                        throw new EntityNotFoundException("Aucun Pointofsale n'existe avec l'Id precise dans la requete ",
                                ErrorCode.POINTOFSALE_NOT_FOUND);
                    }
                    /******************************************************************
                     * A ce niveau on est sur que le nouveau Pos precise existe en BD
                     * donc on fait la modification
                     */
                    userBMToUpdate.setBmPosId(userBMDto.getBmPosId());
                    userBMToUpdate.setBmEnterpriseId(optionalPointofsale.get().getPosEnterprise().getId());
                }
            }
            /**********************************************************************************
             * Si on est ici alors aucun Pointofsale n'etait encore precise pour le UserBM
             * et on peut donc set un pos sans souci
             */
            userBMToUpdate.setBmPosId(userBMDto.getBmPosId());
        }

        /*********************************************************************************
         * On peut aussi dans la requete vouloir fixer ou modifier l'enterprise d'un
         * UserBM uniquement lorsque celui ci est un AdminEnterprise
         */
        if(Optional.ofNullable(userBMDto.getBmEnterpriseId()).isPresent()){
            if(Optional.ofNullable(userBMToUpdate.getBmEnterpriseId()).isPresent()){
                if(!userBMToUpdate.getBmEnterpriseId().equals(userBMDto.getBmEnterpriseId())){
                    log.error("It is not possible to modify the enterpriseId of an User");
                    throw new InvalidEntityException("Il n'est pas possible de modifier l'id Enterprise " +
                            "d'un User quand il est deja fixe", ErrorCode.USERBM_NOT_VALID);
                }
            }
            else{
                /*****
                 * Ceci signifie que l"idEnterprise n'etait pas encore fixe
                 * et si c'est le cas alors on doit verifier que le UserBM
                 * est un AdminEnterprise sinon on peut pas le fixer direct
                 * ainsi. il est fixer automatiquement lors de la fixation
                 * du Pointofsale
                 */
                if(userBMToUpdate.getBmUsertype().equals(UserBMType.AdminEnterprise)){
                    /***
                     * Alors on peut set le IdEnterprise sans souci
                     */
                    userBMToUpdate.setBmEnterpriseId(userBMDto.getBmEnterpriseId());
                }
            }
        }


        /*
        Tout est deja verifie donc on doit normalement effectue les modification du reste des parametre
         */
        //Le mot de passe doit etre mis a jour dans une methode speciale qui ne s'occupera que de ca
        //userBMToUpdate.setBmPassword(userBMDto.getBmPassword());

        return UserBMDto.fromEntity(userBMRepository.save(userBMToUpdate));
    }

    @Override
    public UserBMDto switchUserBMState(UserBMDto userBMDto) {

        List<String> errors = UserBMValidator.validate(userBMDto);
        if(!errors.isEmpty()){
            log.error("Entity userBM not valid {}", userBMDto);
            throw new InvalidEntityException("Le userBM passé en argument n'est pas valide: "+errors,
                    ErrorCode.USERBM_NOT_VALID, errors);
        }

        /*********************************************************************
         * POSSIBLE CHANGEMENT OF USERSTATE
         * From Activated to Deactivated: possible
         * From Activated to Connected: possible
         * From Activated to Disconnected: Not possible
         *
         * From Deactivated to Activated: Possible
         * From Deactivated to Connected: Not Possible
         * From Deactivated to Disconnected: Not Possible
         *
         * From Connected to Activated: Not Possible
         * From Connected to Deactivated: Not Possible
         * From Connected to Disconnected: Possible
         *
         * From Disconnected to Activated: Not Possible
         * From Disconnected to Deactivated: Possible
         * From Disconnected to Connected: Possible
         */
        if(userBMDto.getId() == null){
            log.error("L'Id du UserBMDto ne saurait etre null sinon on ne peut retrouver l'entity a update");
            throw new InvalidEntityException("L'Id du UserBMDto ne saurait etre null sinon on ne peut retrouver " +
                    "l'entity a update ", ErrorCode.USERBM_NOT_VALID);
        }
        Optional<UserBM> optionalUserBMToUpate = userBMRepository.findUserBMById(userBMDto.getId());
        if(!optionalUserBMToUpate.isPresent()){
            log.error("There is no UserBM with the precise id in the DB");
            throw new InvalidEntityException("Aucun UserBM n'existe avec l'Id precise dans la requete ",
                    ErrorCode.USERBM_NOT_VALID);
        }
        UserBM userBMToUpdate = optionalUserBMToUpate.get();

        if(userBMToUpdate.getBmState().equals(UserBMState.Activated)){
            if(userBMDto.getBmState().equals(UserBMState.Deactivated)){
                userBMToUpdate.setBmState(UserBMState.Deactivated);
            }
            else if(userBMDto.getBmState().equals(UserBMState.Connected)){
                userBMToUpdate.setBmState(UserBMState.Connected);
            }
            else if(userBMDto.getBmState().equals(UserBMState.Disconnected)){
                log.error("It is not possible to move from Activated state to Disconnected state");
                throw new InvalidEntityException("On peut pas passer de l'etat Active a l'etat Deconnecte",
                        ErrorCode.USERBM_NOT_VALID);
            }
        }
        else if(userBMToUpdate.getBmState().equals(UserBMState.Deactivated)){
            if(userBMDto.getBmState().equals(UserBMState.Activated)){
                userBMToUpdate.setBmState(UserBMState.Activated);
            }
            else if(userBMDto.getBmState().equals(UserBMState.Connected)){
                log.error("It is not possible to move from Deactivated state to Connected state");
                throw new InvalidEntityException("On peut pas passer de l'etat Deactivated a l'etat Connected",
                        ErrorCode.USERBM_NOT_VALID);
            }
            else if(userBMDto.getBmState().equals(UserBMState.Disconnected)){
                log.error("It is not possible to move from Deactivated state to Disconnected state");
                throw new InvalidEntityException("On peut pas passer de l'etat Deactivated a l'etat Deconnecte",
                        ErrorCode.USERBM_NOT_VALID);
            }
        } else if(userBMToUpdate.getBmState().equals(UserBMState.Connected)){
            if(userBMDto.getBmState().equals(UserBMState.Activated)){
                log.error("It is not possible to move from Connected state to Activated state");
                throw new InvalidEntityException("On peut pas passe de l'etat Connected a l'etat Activated",
                        ErrorCode.USERBM_NOT_VALID);
            }
            else if(userBMDto.getBmState().equals(UserBMState.Deactivated)){
                log.error("It is not possible to move from Connected state to Deactivated state");
                throw new InvalidEntityException("On peut pas passe de l'etat Connected a l'etat Deactivated",
                        ErrorCode.USERBM_NOT_VALID);
            }
            else if(userBMDto.getBmState().equals(UserBMState.Disconnected)){
                userBMToUpdate.setBmState(UserBMState.Disconnected);
            }
        } else if(userBMToUpdate.getBmState().equals(UserBMState.Disconnected)){
            if(userBMDto.getBmState().equals(UserBMState.Activated)){
                log.error("It is not possible to move from Disconnected state to Activated state");
                throw new InvalidEntityException("On peut pas passe de l'etat Disconnected a l'etat Activated",
                        ErrorCode.USERBM_NOT_VALID);
            }
            else if(userBMDto.getBmState().equals(UserBMState.Deactivated)){
                userBMToUpdate.setBmState(UserBMState.Deactivated);
            }
            else if (userBMDto.getBmState().equals(UserBMState.Connected)) {
                userBMToUpdate.setBmState(UserBMState.Connected);
            }
        }

        log.info("All verification is done and the object can now be registered in the DB");
        return UserBMDto.fromEntity(userBMRepository.save(userBMToUpdate));
    }

    @Override
    public UserBMDto resetPassword(UserBMDto userBMDto) {
        List<String> errors = UserBMValidator.validate(userBMDto);
        if(!errors.isEmpty()){
            log.error("Entity userBM not valid {}", userBMDto);
            throw new InvalidEntityException("Le userBM passé en argument n'est pas valide",
                    ErrorCode.USERBM_NOT_VALID, errors);
        }

        /****************************************************************
         * Il faut recuperer le UserBM dont le Password va etre reset
         */
        if(userBMDto.getId() == null){
            log.error("The Id of the userBM precised is null");
            throw new InvalidEntityException("L'Id du UserBM precise en argument est null ",
                    ErrorCode.USERBM_NOT_VALID);
        }

        Optional<UserBM> optionalUserBM = userBMRepository.findUserBMById(userBMDto.getId());
        if(!optionalUserBM.isPresent()){
            log.error("There is no UserBM with the Id precised in the DB");
            throw new InvalidEntityException("Aucun UserBM n'existe avec l'Id precise donc on ne peut identifier " +
                    "le UserBM a update ", ErrorCode.USERBM_NOT_VALID);
        }
        UserBM userBMToUpdate = optionalUserBM.get();
        /*****************************************************************
         * Il faut encoder le new Password avant de le passer en argument
         */
        Pbkdf2PasswordEncoder p = Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
        String newPasswordEncoded = p.encode(userBMDto.getBmPassword());

        userBMToUpdate.setBmPassword(newPasswordEncoded);

        log.info("All verification is done and the object can now be registered in the DB");
        return UserBMDto.fromEntity(userBMRepository.save(userBMToUpdate));
    }

    @Override
    public Boolean isUserBMUnique(String bmName, String bmSurname, Date bmDob, String bmLogin,
                                  String bmCni, String bmEmail) {
        Boolean isExistWithCni = this.isUserBMExistWithCni(bmCni);
        Boolean isExistWithLogin = this.isUserBMExistWithLogin(bmLogin);
        Boolean isExistWithName = this.isUserBMExistWithNameAndDob(bmName, bmSurname, bmDob);
        Boolean isExistWithEmail = this.isUserBMExistWithEmail(bmEmail);
        if(isExistWithCni){
            return false;
        }
        if(isExistWithLogin){
            return false;
        }
        if(isExistWithName){
            return false;
        }
        if(isExistWithEmail){
            return false;
        }
        return true;
    }

    @Override
    public Boolean isUserBMFullnameUnique(String bmName, String bmSurname, Date bmDob) {
        if(this.isUserBMExistWithNameAndDob(bmName, bmSurname, bmDob)){
            return false;
        }
        return true;
    }

    @Override
    public Boolean isUserBMLoginUnique(String bmLogin) {
        if(this.isUserBMExistWithLogin(bmLogin)){
            return false;
        }
        return true;
    }

    @Override
    public Boolean isUserBMCniUnique(String bmCni) {
        if(this.isUserBMExistWithCni(bmCni)){
            return false;
        }
        return true;
    }

    @Override
    public Boolean isUserBMEmailUnique(String bmEmail) {
        if(this.isUserBMExistWithEmail(bmEmail)){
            return false;
        }
        return true;
    }

    public Boolean isUserBMExistWithCni(String bmCni) {
        if(!StringUtils.hasLength(bmCni)){
            log.error("UserBM bmCni is null");
            throw new NullArgumentException("le bmCni passe en argument de la methode est null");
        }
        Optional<UserBM> optionalUserBM = userBMRepository.findUserBMByBmCni(bmCni);
        return optionalUserBM.isPresent()?true:false;
    }

    @Override
    public UserBMDto findUserBMByLogin(String bmLogin) {
        Optional<UserBM> optionalUserBM = userBMRepository.findUserBMByBmLogin(bmLogin);
        if(!optionalUserBM.isPresent()){
            throw new EntityNotFoundException("Aucun UserBM avec le login "+bmLogin
                    +" n'a été trouve dans la BDD", ErrorCode.USERBM_NOT_FOUND);
        }
        else{
            return UserBMDto.fromEntity(optionalUserBM.get());
        }
    }

    @Override
    public UserBMDto findUserBMByEmail(String bmEmail) {
        Optional<UserBM> optionalUserBM = userBMRepository.findUserBMByBmEmail(bmEmail);
        if(!optionalUserBM.isPresent()){
            throw new EntityNotFoundException("Aucun UserBM avec l'Email "+bmEmail
                    +" n'a été trouve dans la BDD", ErrorCode.USERBM_NOT_FOUND);
        }
        else{
            return UserBMDto.fromEntity(optionalUserBM.get());
        }
    }

    public Boolean isUserBMExistWithLogin(String bmLogin){
        if(!StringUtils.hasLength(bmLogin)){
            log.error("UserBM Login is null");
            throw new NullArgumentException("le bmLogin passe en argument de la methode est null");
        }

        Optional<UserBM> optionalUserBM = userBMRepository.findUserBMByBmLogin(bmLogin);
        return optionalUserBM.isPresent()?true:false;
    }

    public Boolean isUserBMExistWithEmail(String bmEmail){
        if(!StringUtils.hasLength(bmEmail)){
            log.error("UserBM Email is null");
            throw new NullArgumentException("le bmEmail passe en argument de la methode est null");
        }

        Optional<UserBM> optionalUserBM = userBMRepository.findUserBMByBmEmail(bmEmail);
        return optionalUserBM.isPresent()?true:false;
    }

    @Override
    public UserBMDto findUserBMByCni(String bmCni) {
        Optional<UserBM> optionalUserBM = userBMRepository.findUserBMByBmCni(bmCni);
        if(!optionalUserBM.isPresent()){
            throw new EntityNotFoundException("Aucun UserBM avec le Cni "+bmCni
                    +" n'a été trouve dans la BDD", ErrorCode.USERBM_NOT_FOUND);
        }
        else{
            return UserBMDto.fromEntity(optionalUserBM.get());
        }

    }

    @Override
    public UserBMDto findUserBMByFullNameAndDob(String bmName, String bmSurname, Date bmDob) {
        if(!StringUtils.hasLength(bmName)){
            log.error("UserBM name is null");
            throw new NullArgumentException("le bmName passe en argument de la methode est null");
        }

        /*Optional<UserBM> optionalUserBM = userBMRepository.findUserBMByBmNameAndBmSurnameAndBmDob(bmName,
                bmSurname, bmDob);*/
        Optional<UserBM> optionalUserBM = userBMRepository.findUserBMByFullname(bmName,
                bmSurname, bmDob);

        if(!optionalUserBM.isPresent()){
            log.error("UserBM inexistant");
            throw new EntityNotFoundException("UserBM inexistant ", ErrorCode.USERBM_NOT_FOUND);
        }

        return UserBMDto.fromEntity(optionalUserBM.get());
    }

    public Boolean isUserBMExistWithNameAndDob(String bmName, String bmSurname, Date bmDob){
        if(!StringUtils.hasLength(bmName)){
            log.error("UserBM name is null");
            throw new NullArgumentException("le bmName passe en argument de la methode est null");
        }

        Optional<UserBM> optionalUserBM = userBMRepository.findUserBMByBmNameAndBmSurnameAndBmDob(bmName,
                bmSurname, bmDob);

        return optionalUserBM.isPresent()?true:false;
    }

    @Override
    public UserBMDto findUserBMById(Long bmId) {
        if(bmId == null){
            log.error("bmId is null");
            throw new NullArgumentException("le bmId passe en argument de la methode est null");
        }
        Optional<UserBM> optionalUserBM = userBMRepository.findUserBMById(bmId);

        if(!optionalUserBM.isPresent()){
            throw new EntityNotFoundException("Aucun UserBM avec l'id "+bmId
                    +" n'a été trouve dans la BDD", ErrorCode.USERBM_NOT_FOUND);
        }

        return UserBMDto.fromEntity(optionalUserBM.get());
    }

    public Boolean isUserBMExistWithId(Long bmId){
        if(bmId == null){
            log.error("bmId is null");
            throw new NullArgumentException("le bmId passe en argument de la methode est null");
        }
        //log.error("bmId found === "+bmId);
        Optional<UserBM> optionalUserBM = userBMRepository.findUserBMById(bmId);
        return optionalUserBM.isPresent()?true:false;
    }

    @Override
    public List<UserBMDto> findAllByUserBMType(UserBMType bmUsertype) {
        List<UserBM> listofUserBMofType = userBMRepository.findAllByBmUsertype(bmUsertype);
        return listofUserBMofType.stream().map(UserBMDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Boolean isUserBMDeleteable(Long bmId) {
        /*******************************************************************************************
         * Un UserBM est supprimable si:
         *  -Il nest l'admin d'aucune Entreprise
         */
        if(bmId == null){
            log.error("bmId is null");
            throw new NullArgumentException("le bmId passe en argument de la methode est null");
        }
        if(!isUserBMExistWithId(bmId)){
            log.error("There is no UserBM with the Id {} in the DB ", bmId);
            throw new EntityNotFoundException("Aucun UserBm n'existe avec le bmIdn precise: "+bmId,
                    ErrorCode.USERBM_NOT_FOUND);
        }

        //Il faut faire la recherche du UserBM et regarder si il est admin dune entreprise dans le systeme
        Optional<List<Enterprise>> optionalEnterpriseList = entRepository.findAllEnterpriseAdministrateBy(bmId);
        if(optionalEnterpriseList.isPresent()){
            if(optionalEnterpriseList.get().size() != 0) {
                /*
                Si la taille de la liste des entreprises qu'il administre est differente de 0 alors il ne peut etre
                supprime. il faut dabord affecter ces entreprises a un autre admin avant de le supprimer
                 */
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean deleteUserBMByLogin(String bmLogin) {
        if(!StringUtils.hasLength(bmLogin)){
            log.error("bmLogin is null");
            throw new NullArgumentException("Le bmLogin specifie n'a pas de valeur");
        }
        Optional<UserBM> optionalUserBM = Optional.of(UserBMDto.toEntity(this.findUserBMByLogin(bmLogin)));
        if(optionalUserBM.isPresent()){
            if(isUserBMDeleteable(optionalUserBM.get().getId())){
                userBMRepository.delete(optionalUserBM.get());
                return true;
            }
            log.error("The entity {} is not deleteable because it encompasses some other elements ", optionalUserBM.get());
            throw new EntityNotDeleteableException("Ce role ne peut etre supprime ",
                    ErrorCode.USERBM_NOT_DELETEABLE);
        }
        else{
            log.error("There is no UserBM with the login {} in the DB ", bmLogin);
            throw new EntityNotFoundException("Aucun UserBm n'existe avec le bmLogin precise: "+bmLogin,
                    ErrorCode.USERBM_NOT_FOUND);
        }
    }

    @Override
    public Boolean deleteUserBMByCni(String bmCni) {
        if(!StringUtils.hasLength(bmCni)){
            log.error("bmCni is null");
            throw new NullArgumentException("Le bmCni specifie n'a pas de valeur");
        }
        Optional<UserBM> optionalUserBM = Optional.of(UserBMDto.toEntity(this.findUserBMByCni(bmCni)));
        if(optionalUserBM.isPresent()){
            if(isUserBMDeleteable(optionalUserBM.get().getId())){
                userBMRepository.delete(optionalUserBM.get());
                return true;
            }
            log.error("The entity {} is not deleteable because it encompasses some other elements ", optionalUserBM.get());
            throw new EntityNotDeleteableException("Ce role ne peut etre supprime ",
                    ErrorCode.USERBM_NOT_DELETEABLE);
        }
        else{
            log.error("There is no UserBM with the cni number {} in the DB ", bmCni);
            throw new EntityNotFoundException("Aucun UserBm n'existe avec le bmCni precise: "+bmCni,
                    ErrorCode.USERBM_NOT_FOUND);
        }
    }

    @Override
    public Boolean deleteUserBMByFullNameAndDob(String bmName, String bmSurname, Date bmDob) {
        if(!StringUtils.hasLength(bmName) && !StringUtils.hasLength(bmSurname)){
            log.error("bmName or bmSurname is null");
            throw new NullArgumentException("Le bmName ou le bmSurname specifie n'a pas de valeur");
        }

        Optional<UserBM> optionalUserBM = Optional.of(UserBMDto.toEntity(
                this.findUserBMByFullNameAndDob(bmName, bmSurname, bmDob)));
        if(optionalUserBM.isPresent()){
            if(isUserBMDeleteable(optionalUserBM.get().getId())){
                userBMRepository.delete(optionalUserBM.get());
                return true;
            }
            log.error("The entity {} is not deleteable because it encompasses some other elements ", optionalUserBM.get());
            throw new EntityNotDeleteableException("Ce role ne peut etre supprime ",
                    ErrorCode.USERBM_NOT_DELETEABLE);
        }
        else{
            log.error("There is no UserBM with the fullname {} {} born on {} in the DB ", bmName, bmSurname, bmDob);
            throw new EntityNotFoundException("Aucun UserBm n'existe avec le fullname precise: "+bmName+"  "+
                    bmSurname+" born on "+bmDob, ErrorCode.USERBM_NOT_FOUND);
        }
    }

    @Override
    public Boolean deleteUserBMById(Long bmId) {
        if(bmId == null){
            log.error("bmId is null");
            throw new NullArgumentException("le bmId passe en argument de la methode est null");
        }
        Optional<UserBM> optionalUserBM = userBMRepository.findUserBMById(bmId);
        if(optionalUserBM.isPresent()){
            if(isUserBMDeleteable(bmId)){
                userBMRepository.delete(optionalUserBM.get());
                return true;
            }
            log.error("The entity {} is not deleteable because it encompasses some other elements ", optionalUserBM.get());
            throw new EntityNotDeleteableException("Ce role ne peut etre supprime ",
                    ErrorCode.USERBM_NOT_DELETEABLE);
        }
        else{
            log.error("There is no UserBM with the id {} in the DB ", bmId);
            throw new EntityNotFoundException("Aucun UserBm n'existe avec l'ID precise: "+bmId,
                    ErrorCode.USERBM_NOT_FOUND);
        }
    }

    @Override
    public List<UserBMDto> findAllUserBM() {
        List<UserBM> listofUserBM = userBMRepository.findAll();
        return listofUserBM.stream().map(UserBMDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<UserBMDto> findAllUserBMContaining(String sample, int pagenum, int pagesize) {
        if(!StringUtils.hasLength(sample)){
            log.error("sample is null or empty");
            throw new NullArgumentException("le sample passe en argument de la methode est null ou vide");
        }
        Page<UserBM> pageofUserBM = userBMRepository.findAllByBmNameOrBmSurnameContaining(sample,
                PageRequest.of(pagenum, pagesize, Sort.by(Sort.Direction.ASC, "bmName")));
        return pageofUserBM.map(UserBMDto::fromEntity);
    }

    @Override
    public List<UserBMDto> findAllUserBMofPos(Long idPos) {
        if(idPos == null){
            log.error("idPos is null");
            throw new NullArgumentException("le idPos passe en argument de la methode est null");
        }
        List<UserBM> listofUserBM = userBMRepository.findAllByBmPos(idPos);
        return listofUserBM.stream().map(UserBMDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<UserBMDto> findAllUserBMofPos(Long idPos, String sample, int pagenum, int pagesize) {
        if(idPos == null){
            log.error("idPos is null");
            throw new NullArgumentException("le idPos passe en argument de la methode est null");
        }
        if(!StringUtils.hasLength(sample)){
            log.error("sample is null or empty");
            throw new NullArgumentException("le sample passe en argument de la methode est null ou vide");
        }
        Page<UserBM> pageofUserBM = userBMRepository.findAllByBmPosContaining(idPos, sample,
                PageRequest.of(pagenum, pagesize, Sort.by(Sort.Direction.ASC, "bmName")));
        return pageofUserBM.map(UserBMDto::fromEntity);
    }
}
