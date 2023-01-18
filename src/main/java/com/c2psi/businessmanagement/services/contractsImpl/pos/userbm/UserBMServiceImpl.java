package com.c2psi.businessmanagement.services.contractsImpl.pos.userbm;

import com.c2psi.businessmanagement.Enumerations.UserBMType;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.exceptions.IllegalArgumentException;
import com.c2psi.businessmanagement.models.Pointofsale;
import com.c2psi.businessmanagement.models.UserBM;
import com.c2psi.businessmanagement.repositories.pos.pos.PointofsaleRepository;
import com.c2psi.businessmanagement.repositories.pos.userbm.UserBMRepository;
import com.c2psi.businessmanagement.services.contracts.pos.userbm.UserBMService;
import com.c2psi.businessmanagement.validators.pos.userbm.UserBMValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value="UserBMService1")
@Slf4j
public class UserBMServiceImpl implements UserBMService {

    private UserBMRepository userBMRepository;
    private PointofsaleRepository posRepository;

    @Autowired
    public UserBMServiceImpl(
            UserBMRepository userBMRepository1,
            PointofsaleRepository posRepository1
    )
    {
       this.userBMRepository = userBMRepository1;
       this.posRepository = posRepository1;
    }

    @Override
    public UserBMDto saveUserBM(UserBMDto userBMDto) {
        /******
         * A tester:
         *  -Si le parametre userBMDto n'est pas valide la methode lance l'exception InvalidEntityException.
         *      Ce parametre n'est pas valide si:
         *      -Il est null
         *      -L'objet Address est null
         *      -Le numtel1 n'a pas ete precise dans l'objet Address ou est vide (mais l'espace vide est une valeur
         *      qui marche)
         *      -Le numtel2 n'a pas ete precise dans l'objet Address ou est vide (mais l'espace vide est une valeur
         *       qui marche)
         *      -L'email precise ne respecte pas un format d'adresse email dans l'objet Address
         *  -Si le fullname (name+surname +dob) designe deja un UserBM dans la BD la methode doit lancer un DuplicateEntityException
         *  -Si le login designe deja un UserBM dans la BD la methode doit lancer un DuplicateEntityException
         *  -Si le cniNumber designe deja un UserBM dans la BD la methode doit lancer un DuplicateEntityException
         *  -Si le userBMType n'est pas AdminBM ou AdminEnterprise alors
         *      -Si l'objet bmPos est null la methode doit lancer un InvalidEntityException
         *      -Si l'objet bmPos n'est pas null mais ne designe aucun pointofsale en BD alors la methode doit
         *      lancer un InvalidEntityException
         */
        List<String> errors = UserBMValidator.validate(userBMDto);
        if(!errors.isEmpty()){
            log.error("Entity userBM not valid {}", userBMDto);
            System.out.println("errors == "+errors);
            throw new InvalidEntityException("Le userBM passé en argument n'est pas valide: "+errors,
                    ErrorCode.USERBM_NOT_VALID, errors);
        }

        /*System.out.println("retour de isUnique "+this.isUserBMUnique(userBMDto.getBmName(), userBMDto.getBmSurname(),
                userBMDto.getBmDob(), userBMDto.getBmLogin(), userBMDto.getBmCni(),
                userBMDto.getBmAddressDto().getEmail()));*/
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

        if(!this.isUserBMEmailUnique(userBMDto.getBmAddressDto().getEmail())){
            log.error("An entity userbm already exist with the same email in the DB {}",
                    userBMDto);
            throw new DuplicateEntityException("Un UserBM existe deja dans la BD avec " +
                    "une adresse contenant la meme adresse email ", ErrorCode.USERBM_DUPLICATED);
        }

        /*
        Il faut se rassurer si l'admin qu'on veut ajouter est un employe auquel cas il faut
        se rassurer que le pointde vente est fixé pour cet employe.
         */
        UserBMType userBMType = userBMDto.getBmUsertype();
        /*
         *Si userBMType est different de AdminBM ou AdminEnterprise alors on ne peut ajouter le user
         *que si pos_id n'est pas null
         */
        if(!userBMType.equals(UserBMType.AdminBM) &&
                !userBMType.equals(UserBMType.AdminEnterprise)){
            /*
            * Ceci signifie que le userbm qu'on veut enregistrer est un Employe et par conséquent il faut
            * que son point de vente soit précise.
             */
            if(!Optional.ofNullable(userBMDto.getBmPosDto()).isPresent()){
                log.error("A userbm which is not AdminBM nor AdminEnterprise must be link to a point of sale. " +
                        " he cannot be registered if he is not link with its pointofsale");
                throw new InvalidEntityException("Le USERBM n'est pas valide. Il devrait etre " +
                        "lie a un point de vente existant", ErrorCode.USERBM_NOT_VALID);
            }
            else{
                /*
                *Ici ca veut dire que point of sale a ete precise. Dans ce cas avant d'ajouter il faut se
                * rassurer qu'il (le point de vente precise) existe vraiment dans la base de données auquel
                * cas il faudra signaler une erreur
                 */
                Optional<Pointofsale> optionalPos = posRepository.findPointofsaleById(
                        userBMDto.getBmPosDto().getId());
                if(!optionalPos.isPresent()){
                    log.error("The pointofsale proposed for the userbm must exist in the DB. ");
                    throw new InvalidEntityException("Le Point de vente précisé pour le UserBM n'existe " +
                            "pas dans la BD", ErrorCode.USERBM_NOT_VALID);
                }
                
            }
        }
        log.info("After all verification on the UserBMDto sent in the request, the record can be done: " +
                "{} ", userBMDto);
        return UserBMDto.fromEntity(
                userBMRepository.save(
                        UserBMDto.toEntity(userBMDto)
                )
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
                    ErrorCode.USERBM_NOT_VALID, errors);
        }

        //Tous est bon donc on peut maintenant recuperer le UserBM a modifier
        UserBM userBMToUpdate = UserBMDto.toEntity(this.findUserBMById(userBMDto.getId()));
        //userBMToUpdate.setBmAddress(AddressDto.toEntity(userBMDto.getBmAddressDto()));

        if(!userBMToUpdate.getBmCni().equalsIgnoreCase(userBMDto.getBmCni())){
            if(!this.isUserBMExistWithCni(userBMDto.getBmCni())){
                userBMToUpdate.setBmCni(userBMDto.getBmCni());
            }
            else{
                throw new DuplicateEntityException("Un UserBM existe deja dans la BD avec le meme Cni number "
                        , ErrorCode.USERBM_DUPLICATED);
            }
        }
        if(!userBMToUpdate.getBmLogin().equalsIgnoreCase(userBMDto.getBmLogin())){
            if(!this.isUserBMExistWithLogin(userBMDto.getBmLogin())){
                userBMToUpdate.setBmLogin(userBMDto.getBmLogin());
            }
            else{
                throw new DuplicateEntityException("Un UserBM existe deja dans la BD avec le meme Login "
                        , ErrorCode.USERBM_DUPLICATED);
            }
        }

        if(!userBMToUpdate.getBmAddress().getEmail().equals(userBMDto.getBmAddressDto().getEmail())){
            if(!this.isUserBMExistWithEmail(userBMDto.getBmAddressDto().getEmail())){
                userBMToUpdate.getBmAddress().setEmail(userBMDto.getBmAddressDto().getEmail());
            }
            else{
                throw new DuplicateEntityException("Un UserBM existe deja dans la BD avec le meme Email "
                        , ErrorCode.USERBM_DUPLICATED);
            }
        }

        /*System.out.println("BmName()="+userBMToUpdate.getBmName()+" DtoBmName()="+userBMDto.getBmName());
        System.out.println("BmSurname()="+userBMToUpdate.getBmSurname()+" DtoBmSurname()="+userBMDto.getBmSurname());
        System.out.println("BmDob()="+userBMToUpdate.getBmDob()+" DtoBmDob()="+userBMDto.getBmDob());*/

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

        userBMToUpdate.setBmState(userBMDto.getBmState());
        userBMToUpdate.setBmUsertype(userBMDto.getBmUsertype());
        if(userBMDto.getBmAddressDto() != null){
            userBMToUpdate.getBmAddress().setNumtel1(userBMDto.getBmAddressDto().getNumtel1());
            userBMToUpdate.getBmAddress().setNumtel2(userBMDto.getBmAddressDto().getNumtel2());
            userBMToUpdate.getBmAddress().setNumtel3(userBMDto.getBmAddressDto().getNumtel3());
            userBMToUpdate.getBmAddress().setQuartier(userBMDto.getBmAddressDto().getQuartier());
            userBMToUpdate.getBmAddress().setVille(userBMDto.getBmAddressDto().getVille());
            userBMToUpdate.getBmAddress().setPays(userBMDto.getBmAddressDto().getPays());
            userBMToUpdate.getBmAddress().setLocalisation(userBMDto.getBmAddressDto().getLocalisation());
        }



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
        Optional<UserBM> optionalUserBM = userBMRepository.findUserBMByBmNameAndBmSurnameAndBmDob(bmName,
                bmSurname, bmDob);
        return Optional.of(UserBMDto.fromEntity(optionalUserBM.get())).orElseThrow(()->
                new EntityNotFoundException("Aucun UserBM avec le nom "+bmName+" le prenom "+bmSurname+" et " +
                        "la dob "+bmDob
                        +" n'a été trouve dans la BDD", ErrorCode.USERBM_NOT_FOUND));
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

        return Optional.of(UserBMDto.fromEntity(optionalUserBM.get())).orElseThrow(()->
                new EntityNotFoundException("Aucun UserBM avec l'id "+bmId
                        +" n'a été trouve dans la BDD", ErrorCode.USERBM_NOT_FOUND));
    }

    public Boolean isUserBMExistWithId(Long bmId){
        if(bmId == null){
            log.error("bmId is null");
            throw new NullArgumentException("le bmId passe en argument de la methode est null");
        }
        Optional<UserBM> optionalUserBM = userBMRepository.findUserBMById(bmId);
        return optionalUserBM.isPresent()?true:false;
    }

    @Override
    public List<UserBMDto> findAllByUserBMType(UserBMType bmUsertype) {
        List<UserBM> listofUserBMofType = userBMRepository.findAllByBmUsertype(bmUsertype);
        return listofUserBMofType.stream().map(UserBMDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Boolean deleteUserBMByLogin(String bmLogin) {
        if(!StringUtils.hasLength(bmLogin)){
            log.error("bmLogin is null");
            throw new IllegalArgumentException("Le bmLogin specifie n'a pas de valeur");
        }
        Optional<UserBM> optionalUserBM = Optional.of(UserBMDto.toEntity(this.findUserBMByLogin(bmLogin)));
        if(optionalUserBM.isPresent()){
            userBMRepository.delete(optionalUserBM.get());
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteUserBMByCni(String bmCni) {
        if(!StringUtils.hasLength(bmCni)){
            log.error("bmCni is null");
            throw new IllegalArgumentException("Le bmCni specifie n'a pas de valeur");
        }
        Optional<UserBM> optionalUserBM = Optional.of(UserBMDto.toEntity(this.findUserBMByCni(bmCni)));
        if(optionalUserBM.isPresent()){
            userBMRepository.delete(optionalUserBM.get());
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteUserBMByFullNameAndDob(String bmName, String bmSurname, Date bmDob) {
        if(!StringUtils.hasLength(bmName) && !StringUtils.hasLength(bmSurname)){
            log.error("bmName or bmSurname is null");
            throw new IllegalArgumentException("Le bmName ou le bmSurname specifie n'a pas de valeur");
        }

        Optional<UserBM> optionalUserBM = Optional.of(UserBMDto.toEntity(
                this.findUserBMByFullNameAndDob(bmName, bmSurname, bmDob)));
        if(optionalUserBM.isPresent()){
            userBMRepository.delete(optionalUserBM.get());
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteUserBMById(Long bmId) {
        if(bmId == null){
            log.error("bmId is null");
            throw new NullArgumentException("le bmId passe en argument de la methode est null");
        }
        Optional<UserBM> optionalUserBM = userBMRepository.findUserBMById(bmId);
        if(optionalUserBM.isPresent()){
            userBMRepository.delete(optionalUserBM.get());
            return true;
        }
        return false;
    }

    @Override
    public List<UserBMDto> findAllUserBM() {
        List<UserBM> listofUserBM = userBMRepository.findAll();
        return listofUserBM.stream().map(UserBMDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<UserBMDto> findAllUserBMContaining(String sample, int pagenum, int pagesize) {
        Page<UserBM> pageofUserBM = userBMRepository.findAllByBmNameOrBmSurnameContaining(sample,
                PageRequest.of(pagenum, pagesize, Sort.by(Sort.Direction.ASC, "bmName")));
        return pageofUserBM.map(UserBMDto::fromEntity);
    }

    @Override
    public List<UserBMDto> findAllUserBMofPos(Long idPos) {
        List<UserBM> listofUserBM = userBMRepository.findAllByBmPos(idPos);
        return listofUserBM.stream().map(UserBMDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<UserBMDto> findAllUserBMofPos(Long idPos, String sample, int pagenum, int pagesize) {
        Page<UserBM> pageofUserBM = userBMRepository.findAllByBmPosContaining(idPos, sample,
                PageRequest.of(pagenum, pagesize, Sort.by(Sort.Direction.ASC, "bmName")));
        return pageofUserBM.map(UserBMDto::fromEntity);
    }
}
