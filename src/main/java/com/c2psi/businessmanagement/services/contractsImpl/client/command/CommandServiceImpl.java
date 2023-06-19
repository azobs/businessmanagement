package com.c2psi.businessmanagement.services.contractsImpl.client.command;

import com.c2psi.businessmanagement.Enumerations.CommandState;
import com.c2psi.businessmanagement.Enumerations.CommandStatus;
import com.c2psi.businessmanagement.Enumerations.CommandType;
import com.c2psi.businessmanagement.Enumerations.DeliveryState;
import com.c2psi.businessmanagement.dtos.client.command.CommandDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.*;
import com.c2psi.businessmanagement.repositories.client.client.ClientRepository;
import com.c2psi.businessmanagement.repositories.client.client.DiversRepository;
import com.c2psi.businessmanagement.repositories.client.command.CommandRepository;
import com.c2psi.businessmanagement.repositories.client.command.SaleInvoiceCapsuleRepository;
import com.c2psi.businessmanagement.repositories.client.command.SaleInvoiceCashRepository;
import com.c2psi.businessmanagement.repositories.client.command.SaleInvoiceDamageRepository;
import com.c2psi.businessmanagement.repositories.client.delivery.DeliveryRepository;
import com.c2psi.businessmanagement.repositories.pos.loading.LoadingRepository;
import com.c2psi.businessmanagement.repositories.pos.pos.PointofsaleRepository;
import com.c2psi.businessmanagement.repositories.pos.userbm.UserBMRepository;
import com.c2psi.businessmanagement.services.contracts.client.command.CommandService;
import com.c2psi.businessmanagement.validators.client.command.CommandValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value="CommandServiceV1")
@Slf4j
@Transactional
public class CommandServiceImpl implements CommandService {

    private DeliveryRepository deliveryRepository;
    private PointofsaleRepository pointofsaleRepository;
    private ClientRepository clientRepository;
    private DiversRepository diversRepository;
    private UserBMRepository userBMRepository;
    private SaleInvoiceCashRepository saleInvoiceCashRepository;
    private SaleInvoiceCapsuleRepository saleInvoiceCapsuleRepository;
    private SaleInvoiceDamageRepository saleInvoiceDamageRepository;
    private LoadingRepository loadingRepository;
    private CommandRepository commandRepository;

    @Autowired
    public CommandServiceImpl(DeliveryRepository deliveryRepository, PointofsaleRepository pointofsaleRepository,
                              ClientRepository clientRepository, UserBMRepository userBMRepository,
                              SaleInvoiceCashRepository saleInvoiceCashRepository,
                              SaleInvoiceCapsuleRepository saleInvoiceCapsuleRepository,
                              SaleInvoiceDamageRepository saleInvoiceDamageRepository, LoadingRepository loadingRepository,
                              CommandRepository commandRepository, DiversRepository diversRepository) {
        this.deliveryRepository = deliveryRepository;
        this.pointofsaleRepository = pointofsaleRepository;
        this.clientRepository = clientRepository;
        this.userBMRepository = userBMRepository;
        this.saleInvoiceCashRepository = saleInvoiceCashRepository;
        this.saleInvoiceCapsuleRepository = saleInvoiceCapsuleRepository;
        this.saleInvoiceDamageRepository = saleInvoiceDamageRepository;
        this.loadingRepository = loadingRepository;
        this.commandRepository = commandRepository;
        this.diversRepository = diversRepository;
    }

    @Override
    public CommandDto saveCommand(CommandDto cmdDto) {

        /*******************************************************************
         * On commence par valider le parametre cmdDto grace au validateur
         */
        List<String> errors = CommandValidator.validate(cmdDto);
        if(!errors.isEmpty()){
            log.error("Entity commandDto not valid {}", cmdDto);
            throw new InvalidEntityException("Le commandDto passe en argument n'est pas valide:  ",
                    ErrorCode.COMMAND_NOT_VALID, errors);
        }

        /********************************************************************
         *On verifie que l'id du Pointofsale associe n'est pas null et si
         * c'est le cas on se rassure qu'il existe vraiment en BD
         */
        if(cmdDto.getCmdPosId() == null){
            log.error("The associate pointofsale id cannot be null");
            throw new InvalidEntityException("L'id du pointofsale associe ne peut etre null ",
                    ErrorCode.COMMAND_NOT_VALID);
        }
        Optional<Pointofsale> optionalPointofsale =  pointofsaleRepository.findPointofsaleById(cmdDto.getCmdPosId());
        if(!optionalPointofsale.isPresent()){
            log.error("The associate pointofsale indicated is not in the DB");
            throw new InvalidEntityException("Le pointofsale associe a la commande est inexistant de la BD",
                    ErrorCode.COMMAND_NOT_VALID);
        }

        /****************************************************************************
         * On se rassure que si l'id du client est null alors celui du divers n'est
         * pas null et vice versa
         */
        if(cmdDto.getCmdClientDto()== null && cmdDto.getCmdDiversDto() == null){
            log.error("The command must belong to a client or  divers that is compulsory for the system");
            throw new InvalidEntityException("Une command appartient toujours soit a un client soit a un divers ",
                    ErrorCode.COMMAND_NOT_VALID);
        }

        /******************************************************************************
         * De meme si la command est a la fois pour un divers et pour un client alors
         * il y a probleme
         */
        if(cmdDto.getCmdClientDto() != null && cmdDto.getCmdDiversDto() != null){
            log.error("The command must belong either to a client or  to a divers not for both");
            throw new InvalidEntityException("Une command appartient toujours soit a un client soit a un divers pas les deux",
                    ErrorCode.COMMAND_NOT_VALID);
        }

        /********************************************************************
         *On verifie que si l'id du Client associe n'est pas null alors
         *  on se rassure qu'il existe vraiment en BD
         */
        if(cmdDto.getCmdClientDto() != null) {
            if (cmdDto.getCmdClientDto().getId() != null) {
                Optional<Client> optionalClient = clientRepository.findClientById(cmdDto.getCmdClientDto().getId());
                if (!optionalClient.isPresent()) {
                    log.error("The associate client indicated is not in the DB");
                    throw new InvalidEntityException("Le client associe a la commande est inexistant de la BD",
                            ErrorCode.COMMAND_NOT_VALID);
                }
                //Le client est non null et existe donc on doit bel et bien fixe le type de la command a standard
                cmdDto.setCmdType(CommandType.Standard);
            }
        }

        /********************************************************************
         *On verifie que si l'id du Divers associe n'est pas null alors
         *  on se rassure qu'il existe vraiment en BD
         */
        if(cmdDto.getCmdDiversDto() != null) {
            if (cmdDto.getCmdDiversDto().getId() != null) {
                Optional<Divers> optionalDivers = diversRepository.findDiversById(cmdDto.getCmdDiversDto().getId());
                if (!optionalDivers.isPresent()) {
                    log.error("The associate divers indicated is not in the DB");
                    throw new InvalidEntityException("Le divers associe a la commande est inexistant de la BD",
                            ErrorCode.COMMAND_NOT_VALID);
                }
                //Le divers est non null et existe donc on doit bel et bien fixe le type de la command a divers
                cmdDto.setCmdType(CommandType.Divers);
            }
        }

        /******************************************************************************
         * On verifie si l'Id du Loading associe n'est pas null et si c'est le cas alors
         * on se rassure qu'il existe vraiment en BD
         */
        if(cmdDto.getCmdLoadingDto() != null){
            if(cmdDto.getCmdLoadingDto().getId() != null){
                Optional<Loading> optionalLoading = loadingRepository.findLoadingById(cmdDto.getCmdLoadingDto().getId());
                if(!optionalLoading.isPresent()){
                    log.error("The associated loading indicated is not in the DB");
                    throw new InvalidEntityException("Le loading precise pour etre associe a la commande n'existe pas " +
                            "en BD ", ErrorCode.COMMAND_NOT_VALID);
                }
            }
        }

        /********************************************************************
         *On verifie que l'id du Userbm associe n'est pas null et si
         * c'est le cas on se rassure qu'il existe vraiment en BD
         */
        if(cmdDto.getCmdUserbmDto().getId() == null){
            log.error("The associate userbm id cannot be null");
            throw new InvalidEntityException("L'id du userbm associe ne peut etre null ",
                    ErrorCode.COMMAND_NOT_VALID);
        }
        Optional<UserBM> optionalUserBM = userBMRepository.findUserBMById(cmdDto.getCmdUserbmDto().getId());
        if(!optionalUserBM.isPresent()){
            log.error("The associate userbm indicated is not in the DB");
            throw new InvalidEntityException("Le userbm associe a la commande est inexistant de la BD",
                    ErrorCode.COMMAND_NOT_VALID);
        }


        /************************************************************************************
         * On doit se rassurer que le userbm est bel et bien un userbm du meme pointofsale
         * que celui de la command
         */
        if(cmdDto.getCmdUserbmDto().getBmPosId() == null){
            log.error("The pointofsale of the userbm can't be null");
            throw new InvalidEntityException("Le pointofsale du userbm ne saurait etre null ",
                    ErrorCode.COMMAND_NOT_VALID);
        }

        if(!cmdDto.getCmdPosId().equals(cmdDto.getCmdUserbmDto().getBmPosId())){
            log.error("The pointofsale of the command must be the same with the one of the command associated");
            throw new InvalidEntityException("Le pointofsale de la commande doit etre le meme que celui de la commande " +
                    "associe", ErrorCode.COMMAND_NOT_VALID);
        }

        /**************************************************************************************************
         * On enregistre pas une commande directement avec la facture.
         * En effet c'est apres l'enregistrement de la commande quon
         * peut provoquer l'edition de la facture
         */
        if(cmdDto.getCmdSaleicashDto() != null || cmdDto.getCmdSaleicapsDto() != null || cmdDto.getCmdSaleidamDto() != null){
            log.error("It is not possible to save a command directly with an invoice even if is capsule, cash or damage");
            throw new InvalidEntityException("Il n'est pas possible d'enregistrer une command directement avec une " +
                    "facture qu'elle soit cash, capsule ou damage", ErrorCode.COMMAND_NOT_VALID);
        }

        /*****************************************************************
         * On se rassure de l'unicite du code de la Command dans le Pos
         */
        if(!isCommandUniqueinPos(cmdDto.getCmdCode(), cmdDto.getCmdPosId())){
            log.error("There is another command with the same code in the same pos register in the DB");
            throw new DuplicateEntityException("Une autre commande existe deja dans le systeme avec le meme code et " +
                    "appartenant au meme pointofsale", ErrorCode.COMMAND_DUPLICATED);
        }

        /****************************************************************************************
         * A la creation d'une command, on est sur que son etat doit etre InEditing
         * On va donc fixer ce champ sans attendre a ce que ce soit la requete qui le face
         */
        cmdDto.setCmdState(CommandState.InEditing);

        log.info("After all verification the record {} can be register in the DB", cmdDto);
        return CommandDto.fromEntity(
                commandRepository.save(
                        CommandDto.toEntity(cmdDto)
                )
        );
    }

    @Override
    public CommandDto updateCommand(CommandDto cmdDto) {

        /*******************************************************************
         * On commence par valider le parametre cmdDto grace au validateur
         */
        List<String> errors = CommandValidator.validate(cmdDto);
        if(!errors.isEmpty()){
            log.error("Entity commandDto not valid {}", cmdDto);
            throw new InvalidEntityException("Le commandDto passe en argument n'est pas valide:  ",
                    ErrorCode.COMMAND_NOT_VALID, errors);
        }

        /****************************************
         * On recherche la command a update
         */
        if(cmdDto.getId() == null){
            log.error("The cmdDtoId can't be null");
            throw new InvalidEntityException("L'id de la commande a update ne peut etre null",
                    ErrorCode.COMMAND_NOT_VALID);
        }
        Optional<Command> optionalCommand = commandRepository.findCommandById(cmdDto.getId());
        if(!optionalCommand.isPresent()){
            log.error("There is no command register with the id {} precised", cmdDto.getId());
            throw new InvalidEntityException("Aucune command n'existe dans le systeme avec l'Id precise ",
                    ErrorCode.COMMAND_NOT_VALID);
        }
        //Ici on est sur de l'existance de la command
        Command cmdToUpdate = optionalCommand.get();

        /****************************************************************************
         * On se rassure que si l'id du client est null alors celui du divers n'est
         * pas null et vice versa
         */
        if(cmdDto.getCmdClientDto()== null && cmdDto.getCmdDiversDto() == null){
            log.error("The command must belong to a client or  divers that is compulsory for the system");
            throw new InvalidEntityException("Une command appartient toujours soit a un client soit a un divers ",
                    ErrorCode.COMMAND_NOT_VALID);
        }

        /******************************************************************************
         * De meme si la command est a la fois pour un divers et pour un client alors
         * il y a probleme
         */
        if(cmdDto.getCmdClientDto() != null && cmdDto.getCmdDiversDto() != null){
            log.error("The command must belong either to a client or  to a divers not for both");
            throw new InvalidEntityException("Une command appartient toujours soit a un client soit a un divers pas les deux",
                    ErrorCode.COMMAND_NOT_VALID);
        }

        /*********************************************************************
         * On se rassure que ce n'est pas le pointofsale quon veut modifier
         * car si c'est le cas on refuse
         */
        if(!cmdDto.getCmdPosId().equals(cmdToUpdate.getCommandPosId())){
            log.error("The pointofsale of the command can't be updated");
            throw new InvalidEntityException("Il n'est pas possible de modifier le pointofsale d'une command",
                    ErrorCode.COMMAND_NOT_VALID);
        }

        /*********************************************************************
         * On se rassure que ce n'est pas le userbm qu'on veut modifier car si
         * cest le cas on refuse
         */
        if(!cmdDto.getCmdUserbmDto().getId().equals(cmdToUpdate.getCmdUserbm().getId())){
            log.error("The Userbm of the command can't be updated");
            throw new InvalidEntityException("Il n'est pas possible de modifier le userbm d'une command",
                    ErrorCode.COMMAND_NOT_VALID);
        }

        /********************************************************************
         * On se rassure que si c'est le client quon veut modifier alors
         * que le nouveau client existe vraiment en BD et qu'il soit bel
         * et bien un client de
         *On arrive ici que si le client est different de null et dans ce cas
         * le divers est null ou alors le divers est non null et le client null
         */
        if(cmdDto.getCmdClientDto() != null){
            //Alors c'est a un client quon veut set la command on va donc set a null le divers qui avait la command
            if(cmdDto.getCmdClientDto().getId() == null){
                log.error("The client id precised can't be null");
                throw new InvalidEntityException("L'id du client precise ne peut etre null ",
                            ErrorCode.COMMAND_NOT_VALID);
            }
            //Ici on est sur que l'id du client nest pas null. on verifie si cest donc ca quon veut modifier
            if(!cmdDto.getCmdClientDto().getId().equals(cmdToUpdate.getCmdClient().getId())){
                //alors cest le client quon veut update
                /*******************************************************************
                 * On va donc verifier si le nouveau client precise existe en BD
                 */
                Optional<Client> optionalClient = clientRepository.findClientById(cmdDto.getCmdClientDto().getId());
                if (!optionalClient.isPresent()) {
                    log.error("The associate client indicated is not in the DB");
                    throw new InvalidEntityException("Le client associe a la commande est inexistant de la BD",
                                ErrorCode.COMMAND_NOT_VALID);
                }
                //A ce niveau on est sur que le nouveau client existe en BD. on prepare donc le update
                cmdToUpdate.setCmdClient(optionalClient.get());
                /*
                Sans oublie quil faut donc set le divers a null au cas ou puisque la command
                ne peut appartenir au client et au divers en meme temps
                 */
                cmdToUpdate.setCmdDivers(null);
                //Il faut pas aussi oublie que dans ce cas la command devient de type standard si elle ne l'etait pas deja
                cmdToUpdate.setCmdType(CommandType.Standard);
            }
            //Ici signifie que ce n'est pas le client quon veut update
        }

        //Maintenant si cest le divers qui est non null
        if(cmdDto.getCmdDiversDto() != null){
            //Alors c'est a un divers quon veut set la command on va donc set a null le client qui avait la command
            if(cmdDto.getCmdDiversDto().getId() == null){
                log.error("The divers id precised can't be null");
                throw new InvalidEntityException("L'id du divers precise ne peut etre null ",
                        ErrorCode.COMMAND_NOT_VALID);
            }
            //Ici on est sur que l'id du divers nest pas null. on verifie si cest donc ca quon veut modifier
            if(!cmdDto.getCmdDiversDto().getId().equals(cmdToUpdate.getCmdDivers().getId())){
                //alors cest le divers quon veut update
                /*******************************************************************
                 * On va donc verifier si le nouveau divers precise existe en BD
                 */
                Optional<Divers> optionalDivers = diversRepository.findDiversById(cmdDto.getCmdDiversDto().getId());
                if (!optionalDivers.isPresent()) {
                    log.error("The associate divers indicated is not in the DB");
                    throw new InvalidEntityException("Le divers associe a la commande est inexistant de la BD",
                            ErrorCode.COMMAND_NOT_VALID);
                }
                //A ce niveau on est sur que le nouveau divers existe en BD. on prepare donc le update
                cmdToUpdate.setCmdDivers(optionalDivers.get());
                /*
                Sans oublie quil faut donc set le client a null au cas ou puisque la command
                ne peut appartenir au client et au divers en meme temps
                 */
                cmdToUpdate.setCmdClient(null);
                //Il faut pas aussi oublie que dans ce cas la command devient de type divers si elle ne l'etait pas deja
                cmdToUpdate.setCmdType(CommandType.Divers);
            }
            //Ici signifie que ce n'est pas le divers quon veut update
        }

        /*************************************************
         * ON verifie si c'est le code quon veut modifier
         * et si cest le cas on se rassure de l'unicite
         */
        if(!cmdDto.getCmdCode().equals(cmdToUpdate.getCmdCode())){
            //System.err.println("isUnique "+isCommandUniqueinPos(cmdToUpdate.getCmdCode(), cmdToUpdate.getCommandPos().getId()));
            if(!isCommandUniqueinPos(cmdDto.getCmdCode(), cmdToUpdate.getCommandPosId())){
                log.error("The new command code enter is already used by another command in DB");
                throw new DuplicateEntityException("le nouveau code est deja utilise par une autre commande en BD ",
                        ErrorCode.COMMAND_DUPLICATED);
            }
            //La on est sur que pas de duplicata on peut donc mettre a jour le code
            cmdToUpdate.setCmdCode(cmdDto.getCmdCode());
        }

        /******************************************************************************
         * On verifie que ce n'est pas le loading qu'on veut modifier car
         * on fait une commande etant dans un id.
         */
        if(cmdDto.getCmdLoadingDto() != null){
            if(cmdToUpdate.getCmdLoading() == null){
                log.error("It is not possible to set the loading of a command");
                throw new InvalidEntityException("Il n'est pas possible de creer une command sans l'associe au loading " +
                        "et l'assigner un loading apres", ErrorCode.COMMAND_NOT_VALID);
            }
            if(!cmdDto.getCmdLoadingDto().getId().equals(cmdToUpdate.getCmdLoading().getId())){
                log.error("It is not possible to modify the loading associe with the command");
                throw new InvalidEntityException("It is not possible to update the loading of a command",
                            ErrorCode.COMMAND_NOT_VALID);
            }
        }

        /*****************************************************************
         * On peut donc mettre a jour le reste des parametres sans souci
         * Mais on ne peut ni modifier le statut ni l'etat de la command
         * ni la livraison
         */
        cmdToUpdate.setCmdDate(cmdDto.getCmdDate());
        cmdToUpdate.setCmdComment(cmdDto.getCmdComment());

        log.info("After all verification, the record {} can be done on the DB", cmdDto);
        return CommandDto.fromEntity(commandRepository.save(cmdToUpdate));
    }


    @Override
    public CommandDto setSaleInvoice(Long cmdId, Long saleInvoiceId, CommandStatus cmdStatus) {
        if(cmdId == null){
            log.error("The command Id precised can't be null in that method");
            throw new NullArgumentException("L'id de la commande passe en argument est null");
        }

        if(saleInvoiceId == null){
            log.error("The saleinvoice Id precised can't be null in that method");
            throw new NullArgumentException("L'id du saleInvoice passe en argument est null");
        }

        if(!isCommandStatusValid(cmdStatus)){
            log.error("The command status precised is not valid for the system");
            throw new InvalidEntityException("Le status precise pour la commande n'est pas valide ",
                    ErrorCode.COMMAND_NOT_VALID);
        }

        /**********************************
         * On va recuperer la cmd a update
         */
        Optional<Command> optionalCommand = commandRepository.findCommandById(cmdId);
        if(!optionalCommand.isPresent()){
            log.error("There is no command in the system with the id {} precised", cmdId);
            throw new InvalidEntityException("Aucune command dans le system n'a l'id precise ",
                    ErrorCode.COMMAND_NOT_VALID);
        }
        //Cmd a update
        Command cmdToUpdate = optionalCommand.get();

        /*************************************************
         *On va recuperer le saleInvoice a set
         */
        if(cmdStatus.equals(CommandStatus.Cash)){
            /*************************************************************
             * Si le statut passe en argument est cash alors il faut que
             * on se rassure que la facture capsule et damage sont null
             * sinon erreur.
             */
            if(cmdToUpdate.getCmdSaleicaps() != null || cmdToUpdate.getCmdSaleidam() != null){
                log.error("The command send to update don't get the status cash");
                throw new InvalidEntityException("La command a update ne peut etre de plusieurs status",
                        ErrorCode.COMMAND_NOT_VALID);
            }

            Optional<SaleInvoiceCash> optionalSaleInvoiceCash = saleInvoiceCashRepository.findSaleInvoiceCashById(saleInvoiceId);
            if(!optionalSaleInvoiceCash.isPresent()){
                log.error("There is no saleinvoicecash in the system with the id {} precised", saleInvoiceId);
                throw new InvalidEntityException("Aucun saleinvoicecash dans le system n'a l'id precise ",
                        ErrorCode.COMMAND_NOT_VALID);
            }
            SaleInvoiceCash saleInvoiceCashToSet = optionalSaleInvoiceCash.get();
            //On le place dans le command
            cmdToUpdate.setCmdSaleicash(saleInvoiceCashToSet);
        }
        else if(cmdStatus.equals(CommandStatus.Capsule)){
            /*************************************************************
             * Si le statut passe en argument est cash alors il faut que
             * on se rassure que la facture capsule et damage sont null
             * sinon erreur.
             */
            if(cmdToUpdate.getCmdSaleicash() != null || cmdToUpdate.getCmdSaleidam() != null){
                log.error("The command send to update don't get the status capsule as precised in the request");
                throw new InvalidEntityException("La command a update ne peut etre de plusieurs status",
                        ErrorCode.COMMAND_NOT_VALID);
            }

            if(cmdToUpdate.getCmdType().equals(CommandType.Divers)){
                log.error("A saleinvoicecapsule can't be assign to a Divers command", saleInvoiceId);
                throw new InvalidEntityException("Aucun divers ne peut changer les capsules. il faut un client ",
                        ErrorCode.COMMAND_NOT_VALID);
            }
            Optional<SaleInvoiceCapsule> optionalSaleInvoiceCapsule = saleInvoiceCapsuleRepository.
                    findSaleInvoiceCapsuleById(saleInvoiceId);
            if(!optionalSaleInvoiceCapsule.isPresent()){
                log.error("There is no saleinvoicecapsule in the system with the id {} precised", saleInvoiceId);
                throw new InvalidEntityException("Aucun saleinvoicecapsule dans le system n'a l'id precise ",
                        ErrorCode.COMMAND_NOT_VALID);
            }
            SaleInvoiceCapsule saleInvoiceCapsuleToSet = optionalSaleInvoiceCapsule.get();
            //On le place dans le command
            cmdToUpdate.setCmdSaleicaps(saleInvoiceCapsuleToSet);
        }
        else if(cmdStatus.equals(CommandStatus.Damage)){
            /*************************************************************
             * Si le statut passe en argument est cash alors il faut que
             * on se rassure que la facture capsule et damage sont null
             * sinon erreur.
             */
            if(cmdToUpdate.getCmdSaleicash() != null || cmdToUpdate.getCmdSaleicaps() != null){
                log.error("The command send to update don't get the status damage as precised in the request");
                throw new InvalidEntityException("La command a update ne peut etre de plusieurs status",
                        ErrorCode.COMMAND_NOT_VALID);
            }
            Optional<SaleInvoiceDamage> optionalSaleInvoiceDamage = saleInvoiceDamageRepository.
                    findSaleInvoiceDamageById(saleInvoiceId);
            if(!optionalSaleInvoiceDamage.isPresent()){
                log.error("There is no saleinvoicedamage in the system with the id {} precised", saleInvoiceId);
                throw new InvalidEntityException("Aucun saleinvoicedamage dans le system n'a l'id precise ",
                        ErrorCode.COMMAND_NOT_VALID);
            }
            SaleInvoiceDamage saleInvoiceDamageToSet = optionalSaleInvoiceDamage.get();
            //On le place dans le command
            cmdToUpdate.setCmdSaleidam(saleInvoiceDamageToSet);
        }
        else{
            log.error("The status send as argument is not recognized by the system but if the code reach there " +
                    "this means there is a problem somewhere because the validity of the status is tested at the beginning");
        }

        log.info("After all verification, the record {} can be done on the DB", CommandDto.fromEntity(cmdToUpdate));
        return CommandDto.fromEntity(commandRepository.save(cmdToUpdate));
    }

    Boolean isCommandStatusValid(CommandStatus cmdStatus){
        if(cmdStatus == null){
            log.error("The cmdStatus precised is null and that is a problem");
            throw new NullArgumentException("L'argument cmdStatus precise est null");
        }
        if(!cmdStatus.equals(CommandStatus.Cash) && !cmdStatus.equals(CommandStatus.Damage) &&
                !cmdStatus.equals(CommandStatus.Capsule)){
            return false;
        }
        return true;
    }

    @Override
    public CommandDto assignCommandToDelivery(Long cmdId, Long deliveryId) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(cmdId == null){
            log.error("The cmd id can't be null in the method");
            throw new NullArgumentException("L'argument cmdId ne peut etre null pour cette methode");
        }

        if(deliveryId == null){
            log.error("The delivery id can't be null in the method");
            throw new NullArgumentException("L'argument deliveryId ne peut etre null pour cette methode");
        }

        /*****************************************************
         *Il faut se rassurer que la Command existe bien en BD
         * et la recuperer
         */
        Optional<Command> optionalCommand = commandRepository.findCommandById(cmdId);
        if(!optionalCommand.isPresent()){
           log.error("There is no command in the DB with the precise id {}", cmdId);
           throw new InvalidEntityException("Il n'existe pas en BD un command avec l'id precise ", ErrorCode.COMMAND_NOT_VALID);
        }
        Command cmdToUpdate = optionalCommand.get();

        /***************************************************************
         * Il faut se rassurer que son etat est EDITED
         */
        if(!cmdToUpdate.getCmdState().equals(CommandState.Edited)){
            log.error("It is not possible to assign a delivery to a command when its state is not EDITED");
            throw new InvalidEntityException("Il n'est pas possible de placer une commande dans une livraison lorsque " +
                    "celle ci n'est pas dans l'etat EDITED", ErrorCode.COMMAND_NOT_VALID);
        }

        /***************************************************************
         * Il faut se rassurer que le delivery indique existe bien en BD
         * et le recuperer
         */
        Optional<Delivery> optionalDelivery = deliveryRepository.findDeliveryById(deliveryId);
        if(!optionalDelivery.isPresent()){
            log.error("There is no Delivery in the DB with the precise id {}", deliveryId);
            throw new InvalidEntityException("Il n'existe pas en BD un delivery avec l'id precise ", ErrorCode.COMMAND_NOT_VALID);
        }
        Delivery deliveryToSet = optionalDelivery.get();

        /**********************************************************************
         * Il faut se rassurer que le pointofsale du deliveryToSet est le meme
         * que celui de la command a update
         */
        if(!deliveryToSet.getDeliveryPosId().equals(cmdToUpdate.getCommandPosId())){
            log.error("The precise delivery must belong to the same pointofsale as the command");
            throw new InvalidEntityException("Le delivery precise doit appartenir au meme pointofsale que la command",
                    ErrorCode.COMMAND_NOT_VALID);
        }

        /**********************************************************************
         * A la creation d'une command ou sa modification, elle a a l'etat
         * INEDITING.Car la creation d'une commande a 3 etatpes (Creer une
         * commande (Inediting),
         * confirmer une commande(edited) et
         * emballer une commande (Packup)).
         * On place une commande dans un delivery lorsque l'etat de cette
         * commande est EDITED. Et apres avoir place une commande dans
         * un delivery, la commande reste a l'etat EDITED. elle passe a
         * Packup lorsque le Delivery en question passe a l'etat
         * OUTOFDELIVERY. En effet lorsquon est encore entrain de creer
         * le delivery, il est a l'etat par defaut PACKUP
         * Un delivery se fera aussi en 3 etapes
         * Creer un delivery: Packup
         * Confirmer un delivery: OutOFDelivery
         * Au retour de la livraison: Delivery et a ce moment la page de saisi du rapport de livraison peur s'ouvrir
         * Une fois le rapport saisi pour chaque command qui etait dans le delivery effectue et confirmer
         * le Delivery passe a l'etat FINISH
         */


        /******************************************************
         * On verifie aue l'etat du delivery est a packup
         */
        if(!deliveryToSet.getDeliveryState().equals(DeliveryState.PackedUp)){
            log.error("It is not possible to assign a delivery in a state different than PackedUp to a command");
            throw new InvalidEntityException("Il n'est pas possible de place une command dans un delivery dont l'etat n'est " +
                    "pas a PackedUp ", ErrorCode.COMMAND_NOT_VALID);
        }
        /*****************************************************************************
         * Il faut donc associe la command a son delivery
         */
        cmdToUpdate.setCmdDelivery(deliveryToSet);

        /*****************************************************************************
         * Il faut automatiquement placer le command associe
         * a l'etat PACKUP car si on est deja dans un delivery c'est quon est deja
         * emballe
         */
        cmdToUpdate.setCmdState(CommandState.PackedUp);

        log.info("After all verification, the record {} can be done on the DB", CommandDto.fromEntity(cmdToUpdate));
        return CommandDto.fromEntity(commandRepository.save(cmdToUpdate));
    }

    @Override
    public CommandDto resetDeliveryofCommand(Long cmdId) {

        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(cmdId == null){
            log.error("The cmd id can't be null in the method");
            throw new NullArgumentException("L'argument cmdId ne peut etre null pour cette methode");
        }

        /*****************************************************
         *Il faut se rassurer que la Command existe bien en BD
         * et la recuperer
         */
        Optional<Command> optionalCommand = commandRepository.findCommandById(cmdId);
        if(!optionalCommand.isPresent()){
            log.error("There is no command in the DB with the precise id {}", cmdId);
            throw new InvalidEntityException("Il n'existe pas en BD un command avec l'id precise ", ErrorCode.COMMAND_NOT_VALID);
        }
        Command cmdToUpdate = optionalCommand.get();

        /***********************************************************
         * Il faut verifier si la Command est deja dans un delivery
         * si c'est le cas alors le Delivery en question doit etre
         * dans letat PackedUP sinon l'operation n'est pas possible
         */
        if(cmdToUpdate.getCmdDelivery() == null){
            log.error("The Command indicated is not yet in a Delivery");
            throw new InvalidEntityException("La command indique n'est pas encore dans un Delivery ",
                    ErrorCode.COMMAND_NOT_VALID);
        }
        //Ici on est sur que le delivery est la
        if(!cmdToUpdate.getCmdDelivery().getDeliveryState().equals(DeliveryState.PackedUp)){
            log.error("It is not possible to reset the Delivery of the indicated command because its state is not yet PackedUp");
            throw new InvalidEntityException("Il n'est pas possible de reinitialiser le delivery de la commande car " +
                    "ce delivery est deja dans un etat different de PackedUp ", ErrorCode.COMMAND_NOT_VALID);
        }
        //Ici on est sur que son etat etait PackedUP on peut donc reset son delivery a null
        cmdToUpdate.setCmdDelivery(null);

        /*****************************************************************************
         * Il faut automatiquement placer le command associe
         * a l'etat EDITED car si on le retire d'un delivery ca veut dire quon va le
         * reemballer
         */
        cmdToUpdate.setCmdState(CommandState.Edited);

        log.info("After all verification, the record {} can be done on the DB", CommandDto.fromEntity(cmdToUpdate));
        return CommandDto.fromEntity(commandRepository.save(cmdToUpdate));
    }

    @Override
    public CommandDto switchCommandStateTo(Long cmdId, CommandState newCmdState) {

        /**********************************************************************
         * Transition possible: InEditing, Edited, Packup
         *
         * InEditing To Edited: Lorsqu'on a fini l'edition de la commande
         * InEditing to Packup: Pas possible
         *
         * Edited to InEditing: Lorsquon veut modifier la command saisie
         * Edited To Packup: Pas possible via cette methode car ca se fait automatiquement quand emballe la command
         *cest a dire le placer dans un delivery
         *
         * Packup To Edited:  Pas possible via cette methode car se fait automatiquement quand on reset le delivery
         *          * d'une commande.
         * Packup a InEditing: Pas possible. On va d'abord devoir reset le delivery
         */

        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(cmdId == null){
            log.error("The cmd id can't be null in the method");
            throw new NullArgumentException("L'argument cmdId ne peut etre null pour cette methode");
        }

        if(newCmdState == null){
            log.error("The argument newCmdState is null ");
            throw new NullArgumentException("L'argument newCmdState de la methode est null");
        }

        if(!isCommandStateValid(newCmdState)){
            log.error("The precised newcmdState is not valid in the system");
            throw  new InvalidEntityException("le nouveau mewcmdState n'est pas reconnu dans le systeme "+newCmdState);
        }

        /*****************************************************
         *Il faut se rassurer que la Command existe bien en BD
         * et la recuperer
         */
        Optional<Command> optionalCommand = commandRepository.findCommandById(cmdId);
        if(!optionalCommand.isPresent()){
            log.error("There is no command in the DB with the precise id {}", cmdId);
            throw new InvalidEntityException("Il n'existe pas en BD un command avec l'id precise ", ErrorCode.COMMAND_NOT_VALID);
        }
        Command cmdToUpdate = optionalCommand.get();

        if(cmdToUpdate.getCmdState().equals(CommandState.InEditing)){
            if(newCmdState.equals(CommandState.Edited)){
                cmdToUpdate.setCmdState(CommandState.Edited);
            }
            else if(newCmdState.equals(CommandState.PackedUp)){
                log.error("It is not possible to switch from InEditing to PackedUp directly");
                throw new InvalidEntityException("Il n'est pas possible de passe de l'etat InEditing a l'etat PackedUp " +
                        "directement ", ErrorCode.COMMAND_NOT_VALID);
            }
        }
        else if(cmdToUpdate.getCmdState().equals(CommandState.Edited)){
            if(newCmdState.equals(CommandState.InEditing)){
                cmdToUpdate.setCmdState(CommandState.InEditing);
            }
            else if(newCmdState.equals(CommandState.PackedUp)){
                log.error("It is not possible to explicitly switch from Edited to PackedUp. It is done implicitly through " +
                        "the methode assignCommandToDelivery");
                throw new InvalidEntityException("Il n'est pas possible de passe explicitement de l'etat Edited " +
                        "a l'etat PackedUp car cela est fait implicitement en placant une command dans une livraison  ",
                        ErrorCode.COMMAND_NOT_VALID);
            }
        }
        else if(cmdToUpdate.getCmdState().equals(CommandState.PackedUp)){
            log.error("The command is already in the last state PackedUP. If there is any problem, reset first the " +
                    "delivery before continue");
            throw new InvalidEntityException("Le Command est deja dans le dernier etat PackedUp. Donc pour tout autre " +
                    "probleme il faut d'abord reset le delivery de la Command pour le manipuler ",
                    ErrorCode.COMMAND_NOT_VALID);
        }

        log.info("After all verification, the record {} can be done on the DB", CommandDto.fromEntity(cmdToUpdate));
        return CommandDto.fromEntity(commandRepository.save(cmdToUpdate));
    }

    Boolean isCommandStateValid(CommandState cmdState){
        if(cmdState == null){
            log.error("The argument cmdState is null ");
            throw new NullArgumentException("L'argument cmdState de la methode est null");
        }
        //System.err.println("le commandState traite est "+cmdState);
        if(!cmdState.equals(CommandState.InEditing) && !cmdState.equals(CommandState.Edited) &&
                !cmdState.equals(CommandState.PackedUp)){
            return false;
        }
        return true;
    }

    @Override
    public CommandDto findCommandById(Long cmdId) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(cmdId == null){
            log.error("The cmd id can't be null in the method");
            throw new NullArgumentException("L'argument cmdId ne peut etre null pour cette methode");
        }

        Optional<Command> optionalCommand = commandRepository.findCommandById(cmdId);
        if(!optionalCommand.isPresent()){
            log.error("There is no command in the DB with the precise id {}", cmdId);
            throw new InvalidEntityException("Il n'existe pas en BD un command avec l'id precise ", ErrorCode.COMMAND_NOT_FOUND);
        }
        Command cmdToUpdate = optionalCommand.get();

        return CommandDto.fromEntity(cmdToUpdate);
    }

    @Override
    public CommandDto findCommandByCodeinPos(String cmdCode, Long posId) {

        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }

        if(!StringUtils.hasLength(cmdCode)){
            log.error("Le code envoye est vierge faut le refaire");
            throw new NullArgumentException("L'argument cmdCode ne peut etre null pour cette methode");
        }

        Optional<Command> optionalCommand = commandRepository.findCommandByCodeinPos(cmdCode, posId);
        if(!optionalCommand.isPresent()){
            log.error("There is no command in the DB with the precise id {} and code {}", posId, cmdCode);
            throw new InvalidEntityException("Il n'existe pas en BD un command avec le code precise dans le " +
                    "pointofsale imdique", ErrorCode.COMMAND_NOT_FOUND);
        }
        Command cmdToUpdate = optionalCommand.get();

        return CommandDto.fromEntity(cmdToUpdate);
    }

    @Override
    public Boolean isCommandUniqueinPos(String cmdCode, Long posId) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }

        if(!StringUtils.hasLength(cmdCode)){
            log.error("Le code envoye est vierge faut le refaire");
            throw new NullArgumentException("L'argument cmdCode ne peut etre null pour cette methode");
        }

        Optional<Command> optionalCommand = commandRepository.findCommandByCodeinPos(cmdCode, posId);
        //System.err.println("optionalCommand.isEmpty() "+cmdCode);
        return optionalCommand.isEmpty();
    }

    @Override
    public Boolean isCommandDeleteable(Long cmdId) {
        return true;
    }

    @Override
    public Boolean deleteCommandById(Long cmdId) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(cmdId == null){
            log.error("The cmd id can't be null in the method");
            throw new NullArgumentException("L'argument cmdId ne peut etre null pour cette methode");
        }

        Optional<Command> optionalCommand = commandRepository.findCommandById(cmdId);
        if(!optionalCommand.isPresent()){
            log.error("There is no command in the DB with the precise id {}", cmdId);
            throw new EntityNotFoundException("Il n'existe pas en BD un command avec l'id precise ",
                    ErrorCode.COMMAND_NOT_FOUND);
        }
        if(!isCommandDeleteable(cmdId)){
            log.error("The Command is not deleteable");
            throw new EntityNotDeleteableException("Il n'est pas possible de supprinmer la command indique ",
                    ErrorCode.COMMAND_NOT_DELETEABLE);
        }
        Command cmdToUpdate = optionalCommand.get();
        commandRepository.delete(cmdToUpdate);
        return true;
    }

    @Override
    public List<CommandDto> findAllCommandinPosBetween(Long posId, Instant startDate, Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        Optional<List<Command>> optionalCommandList = commandRepository.findAllCommandinPosBetween(posId,
                startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosBetween(Long posId, Instant startDate, Instant endDate,
                                                     int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        Optional<Page<Command>> optionalCommandPage = commandRepository.findPageCommandinPosBetween(posId,
                startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofcmdStateBetween(Long posId, CommandState cmdState,
                                                              Instant startDate, Instant endDate) {

        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(cmdState == null){
            log.error("The cmdState can't be null in the method");
            throw new NullArgumentException("L'argument cmdState ne peut etre null pour cette methode");
        }

        Optional<List<Command>> optionalCommandList = commandRepository.findAllCommandinPosofcmdStateBetween(posId,
                cmdState, startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofcmdStateBetween(Long posId, CommandState cmdState,
                                                               Instant startDate, Instant endDate,
                                                               int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(cmdState == null){
            log.error("The cmdState can't be null in the method");
            throw new NullArgumentException("L'argument cmdState ne peut etre null pour cette methode");
        }

        Optional<Page<Command>> optionalCommandPage = commandRepository.findPageCommandinPosofcmdStateBetween(posId,
                cmdState, startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofcmdTypeBetween(Long posId, CommandType cmdType,
                                                             Instant startDate, Instant endDate) {

        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }

        Optional<List<Command>> optionalCommandList = commandRepository.findAllCommandinPosofcmdTypeBetween(posId,
                cmdType, startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofcmdTypeBetween(Long posId, CommandType cmdType,
                                                              Instant startDate, Instant endDate,
                                                              int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }

        Optional<Page<Command>> optionalCommandPage = commandRepository.findPageCommandinPosofcmdTypeBetween(posId,
                cmdType, startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofcmdStatusBetween(Long posId, CommandStatus cmdStatus,
                                                              Instant startDate, Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(cmdStatus == null){
            log.error("The cmdStatus can't be null in the method");
            throw new NullArgumentException("L'argument cmdStatus ne peut etre null pour cette methode");
        }

        Optional<List<Command>> optionalCommandList = commandRepository.findAllCommandinPosofcmdStatusBetween(posId,
                cmdStatus, startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofcmdStatusBetween(Long posId, CommandStatus cmdStatus,
                                                               Instant startDate, Instant endDate,
                                                               int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(cmdStatus == null){
            log.error("The cmdStatus can't be null in the method");
            throw new NullArgumentException("L'argument cmdStatus ne peut etre null pour cette methode");
        }

        Optional<Page<Command>> optionalCommandPage = commandRepository.findPageCommandinPosofcmdStatusBetween(posId,
                cmdStatus, startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofcmdDeliveryStateBetween(Long posId, DeliveryState deliveryState,
                                                                      Instant startDate, Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(deliveryState == null){
            log.error("The deliveryState can't be null in the method");
            throw new NullArgumentException("L'argument cmdStatus ne peut etre null pour cette methode");
        }

        Optional<List<Command>> optionalCommandList = commandRepository.findAllCommandinPosofcmdDeliveryStateBetween(posId,
                deliveryState, startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofcmdDeliveryStateBetween(Long posId, DeliveryState deliveryState,
                                                                       Instant startDate, Instant endDate,
                                                                       int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(deliveryState == null){
            log.error("The deliveryState can't be null in the method");
            throw new NullArgumentException("L'argument deliveryState ne peut etre null pour cette methode");
        }

        Optional<Page<Command>> optionalCommandPage = commandRepository.findPageCommandinPosofcmdDeliveryStateBetween(
                posId, deliveryState, startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofcmdStateAndcmdTypeBetween(Long posId, CommandState cmdState,
                                                                        CommandType cmdType, Instant startDate,
                                                                        Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(cmdState == null){
            log.error("The cmdState can't be null in the method");
            throw new NullArgumentException("L'argument cmdState ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }

        Optional<List<Command>> optionalCommandList = commandRepository.findAllCommandinPosofcmdStateAndcmdTypeBetween(posId,
                cmdState, cmdType, startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofcmdStateAndcmdTypeBetween(Long posId, CommandState cmdState,
                                                                         CommandType cmdType, Instant startDate,
                                                                         Instant endDate, int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(cmdState == null){
            log.error("The cmdState can't be null in the method");
            throw new NullArgumentException("L'argument cmdState ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }

        Optional<Page<Command>> optionalCommandPage = commandRepository.findPageCommandinPosofcmdStateAndcmdTypeBetween(
                posId, cmdState, cmdType, startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofcmdStateAndcmdStatusBetween(Long posId, CommandState cmdState,
                                                                          CommandStatus cmdStatus, Instant startDate,
                                                                          Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(cmdState == null){
            log.error("The cmdState can't be null in the method");
            throw new NullArgumentException("L'argument cmdState ne peut etre null pour cette methode");
        }
        if(cmdStatus == null){
            log.error("The cmdStatus can't be null in the method");
            throw new NullArgumentException("L'argument cmdStatus ne peut etre null pour cette methode");
        }

        Optional<List<Command>> optionalCommandList = commandRepository.findAllCommandinPosofcmdStateAndcmdStatusBetween(posId,
                cmdState, cmdStatus, startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofcmdStateAndcmdStatusBetween(Long posId, CommandState cmdState,
                                                                           CommandStatus cmdStatus, Instant startDate,
                                                                           Instant endDate,
                                                                           int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(cmdState == null){
            log.error("The cmdState can't be null in the method");
            throw new NullArgumentException("L'argument cmdState ne peut etre null pour cette methode");
        }
        if(cmdStatus == null){
            log.error("The cmdStatus can't be null in the method");
            throw new NullArgumentException("L'argument cmdStatus ne peut etre null pour cette methode");
        }

        Optional<Page<Command>> optionalCommandPage = commandRepository.findPageCommandinPosofcmdStateAndcmdStatusBetween(
                posId, cmdState, cmdStatus, startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofcmdStateAndcmdDeliveryStateBetween(Long posId, CommandState cmdState,
                                                                                 DeliveryState deliveryState,
                                                                                 Instant startDate, Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(cmdState == null){
            log.error("The cmdState can't be null in the method");
            throw new NullArgumentException("L'argument cmdState ne peut etre null pour cette methode");
        }
        if(deliveryState == null){
            log.error("The deliveryState can't be null in the method");
            throw new NullArgumentException("L'argument deliveryState ne peut etre null pour cette methode");
        }

        Optional<List<Command>> optionalCommandList = commandRepository.
                findAllCommandinPosofcmdStateAndcmdDeliveryStateBetween(posId, cmdState, deliveryState, startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofcmdStateAndcmdDeliveryStateBetween(Long posId, CommandState cmdState,
                                                                                  DeliveryState deliveryState,
                                                                                  Instant startDate, Instant endDate,
                                                                                  int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(cmdState == null){
            log.error("The cmdState can't be null in the method");
            throw new NullArgumentException("L'argument cmdState ne peut etre null pour cette methode");
        }
        if(deliveryState == null){
            log.error("The deliveryState can't be null in the method");
            throw new NullArgumentException("L'argument deliveryState ne peut etre null pour cette methode");
        }

        Optional<Page<Command>> optionalCommandPage = commandRepository.findPageCommandinPosofcmdStateAndcmdDeliveryStateBetween(
                posId, cmdState, deliveryState, startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofcmdTypeAndcmdStatusBetween(Long posId, CommandType cmdType,
                                                                        CommandStatus cmdStatus, Instant startDate,
                                                                        Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(cmdStatus == null){
            log.error("The cmdStatus can't be null in the method");
            throw new NullArgumentException("L'argument cmdStatus ne peut etre null pour cette methode");
        }

        Optional<List<Command>> optionalCommandList = commandRepository.
                findAllCommandinPosofcmdTypeAndcmdStatusBetween(posId, cmdType, cmdStatus, startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofcmdTypeAndcmdStatusBetween(Long posId, CommandType cmdType,
                                                                         CommandStatus cmdStatus, Instant startDate,
                                                                         Instant endDate,
                                                                         int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(cmdStatus == null){
            log.error("The cmdStatus can't be null in the method");
            throw new NullArgumentException("L'argument cmdStatus ne peut etre null pour cette methode");
        }

        Optional<Page<Command>> optionalCommandPage = commandRepository.findPageCommandinPosofcmdTypeAndcmdStatusBetween(
                posId, cmdType, cmdStatus, startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofcmdTypeAndcmdDeliveryStateBetween(Long posId, CommandType cmdType,
                                                                                DeliveryState deliveryState,
                                                                                Instant startDate, Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(deliveryState == null){
            log.error("The deliveryState can't be null in the method");
            throw new NullArgumentException("L'argument deliveryState ne peut etre null pour cette methode");
        }

        Optional<List<Command>> optionalCommandList = commandRepository.
                findAllCommandinPosofcmdTypeAndcmdDeliveryStateBetween(posId, cmdType, deliveryState, startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofcmdTypeAndcmdDeliveryStateBetween(Long posId, CommandType cmdType,
                                                                                 DeliveryState deliveryState,
                                                                                 Instant startDate, Instant endDate,
                                                                                 int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(deliveryState == null){
            log.error("The deliveryState can't be null in the method");
            throw new NullArgumentException("L'argument deliveryState ne peut etre null pour cette methode");
        }

        Optional<Page<Command>> optionalCommandPage = commandRepository.findPageCommandinPosofcmdTypeAndcmdDeliveryStateBetween(
                posId, cmdType, deliveryState, startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofcmdStateAndcmdTypeAndcmdStatusBetween(
            Long posId, CommandState cmdState,
            CommandType cmdType, CommandStatus cmdStatus,
            Instant startDate, Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(cmdState == null){
            log.error("The cmdState can't be null in the method");
            throw new NullArgumentException("L'argument cmdState ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(cmdStatus == null){
            log.error("The cmdStatus can't be null in the method");
            throw new NullArgumentException("L'argument cmdStatus ne peut etre null pour cette methode");
        }

        Optional<List<Command>> optionalCommandList = commandRepository.
                findAllCommandinPosofcmdStateAndcmdTypeAndcmdStatusBetween(posId, cmdState, cmdType, cmdStatus,
                        startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofcmdStateAndcmdTypeAndcmdStatusBetween(
            Long posId, CommandState cmdState,
            CommandType cmdType, CommandStatus cmdStatus,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(cmdState == null){
            log.error("The cmdState can't be null in the method");
            throw new NullArgumentException("L'argument cmdState ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(cmdStatus == null){
            log.error("The cmdStatus can't be null in the method");
            throw new NullArgumentException("L'argument cmdStatus ne peut etre null pour cette methode");
        }

        Optional<Page<Command>> optionalCommandPage = commandRepository.
                findPageCommandinPosofcmdStateAndcmdTypeAndcmdStatusBetween(posId, cmdState, cmdType, cmdStatus,
                        startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofcmdStateAndcmdTypeAndcmdDeliveryStateBetween(
            Long posId, CommandState cmdState,
            CommandType cmdType, DeliveryState deliveryState,
            Instant startDate, Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(cmdState == null){
            log.error("The cmdState can't be null in the method");
            throw new NullArgumentException("L'argument cmdState ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(deliveryState == null){
            log.error("The deliveryState can't be null in the method");
            throw new NullArgumentException("L'argument deliveryState ne peut etre null pour cette methode");
        }

        Optional<List<Command>> optionalCommandList = commandRepository.
                findAllCommandinPosofcmdStateAndcmdTypeAndcmdDeliveryStateBetween(posId, cmdState, cmdType, deliveryState,
                        startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofcmdStateAndcmdTypeAndcmdDeliveryStateBetween(
            Long posId, CommandState cmdState,
            CommandType cmdType, DeliveryState deliveryState,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(cmdState == null){
            log.error("The cmdState can't be null in the method");
            throw new NullArgumentException("L'argument cmdState ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(deliveryState == null){
            log.error("The deliveryState can't be null in the method");
            throw new NullArgumentException("L'argument deliveryState ne peut etre null pour cette methode");
        }

        Optional<Page<Command>> optionalCommandPage = commandRepository.
                findPageCommandinPosofcmdStateAndcmdTypeAndcmdDeliveryStateBetween(posId, cmdState, cmdType, deliveryState,
                        startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
            Long posId, CommandState cmdState,
            CommandStatus cmdStatus, DeliveryState deliveryState,
            Instant startDate, Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(cmdState == null){
            log.error("The cmdState can't be null in the method");
            throw new NullArgumentException("L'argument cmdState ne peut etre null pour cette methode");
        }
        if(cmdStatus == null){
            log.error("The cmdStatus can't be null in the method");
            throw new NullArgumentException("L'argument cmdStatus ne peut etre null pour cette methode");
        }
        if(deliveryState == null){
            log.error("The deliveryState can't be null in the method");
            throw new NullArgumentException("L'argument deliveryState ne peut etre null pour cette methode");
        }

        Optional<List<Command>> optionalCommandList = commandRepository.
                findAllCommandinPosofcmdStateAndcmdStatusAndcmdDeliveryStateBetween(posId, cmdState, cmdStatus, deliveryState,
                        startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
            Long posId, CommandState cmdState,
            CommandStatus cmdStatus, DeliveryState deliveryState,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(cmdState == null){
            log.error("The cmdState can't be null in the method");
            throw new NullArgumentException("L'argument cmdState ne peut etre null pour cette methode");
        }
        if(cmdStatus == null){
            log.error("The cmdStatus can't be null in the method");
            throw new NullArgumentException("L'argument cmdStatus ne peut etre null pour cette methode");
        }
        if(deliveryState == null){
            log.error("The deliveryState can't be null in the method");
            throw new NullArgumentException("L'argument deliveryState ne peut etre null pour cette methode");
        }

        Optional<Page<Command>> optionalCommandPage = commandRepository.
                findPageCommandinPosofcmdStateAndcmdStatusAndcmdDeliveryStateBetween(posId, cmdState, cmdStatus, deliveryState,
                        startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofcmdStateAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(
            Long posId, CommandState cmdState,
            CommandType cmdType, CommandStatus cmdStatus,
            DeliveryState deliveryState,
            Instant startDate, Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(cmdState == null){
            log.error("The cmdState can't be null in the method");
            throw new NullArgumentException("L'argument cmdState ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(cmdStatus == null){
            log.error("The cmdStatus can't be null in the method");
            throw new NullArgumentException("L'argument cmdStatus ne peut etre null pour cette methode");
        }
        if(deliveryState == null){
            log.error("The deliveryState can't be null in the method");
            throw new NullArgumentException("L'argument deliveryState ne peut etre null pour cette methode");
        }

        Optional<List<Command>> optionalCommandList = commandRepository.
                findAllCommandinPosofcmdStateAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(posId, cmdState, cmdType,
                        cmdStatus, deliveryState, startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofcmdStateAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(
            Long posId, CommandState cmdState,
            CommandType cmdType, CommandStatus cmdStatus,
            DeliveryState deliveryState,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(cmdState == null){
            log.error("The cmdState can't be null in the method");
            throw new NullArgumentException("L'argument cmdState ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(cmdStatus == null){
            log.error("The cmdStatus can't be null in the method");
            throw new NullArgumentException("L'argument cmdStatus ne peut etre null pour cette methode");
        }
        if(deliveryState == null){
            log.error("The deliveryState can't be null in the method");
            throw new NullArgumentException("L'argument deliveryState ne peut etre null pour cette methode");
        }

        Optional<Page<Command>> optionalCommandPage = commandRepository.
                findPageCommandinPosofcmdStateAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(posId, cmdState, cmdType,
                        cmdStatus, deliveryState, startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofClientBetween(Long posId, Long clientId,
                                                            Instant startDate, Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The client id can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        Optional<List<Command>> optionalCommandList = commandRepository.findAllCommandinPosofClientBetween(posId,
                clientId, startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofClientBetween(Long posId, Long clientId,
                                                             Instant startDate, Instant endDate,
                                                             int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The clientId can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        Optional<Page<Command>> optionalCommandPage = commandRepository.
                findPageCommandinPosofClientBetween(posId, clientId,
                        startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofClientAndcmdTypeBetween(Long posId, Long clientId,
                                                                      CommandType cmdType,
                                                                      Instant startDate, Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The client id can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        Optional<List<Command>> optionalCommandList = commandRepository.findAllCommandinPosofClientAndcmdTypeBetween(posId,
                clientId, cmdType, startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofClientAndcmdTypeBetween(Long posId, Long clientId,
                                                                       CommandType cmdType,
                                                                       Instant startDate, Instant endDate,
                                                                       int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The clientId can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        Optional<Page<Command>> optionalCommandPage = commandRepository.
                findPageCommandinPosofClientAndcmdTypeBetween(posId, clientId, cmdType,
                        startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofClientAndcmdStateBetween(Long posId, Long clientId,
                                                                       CommandState cmdState,
                                                                       Instant startDate, Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The client id can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        if(cmdState == null){
            log.error("The cmdState can't be null in the method");
            throw new NullArgumentException("L'argument cmdState ne peut etre null pour cette methode");
        }
        Optional<List<Command>> optionalCommandList = commandRepository.findAllCommandinPosofClientAndcmdStateBetween(posId,
                clientId, cmdState, startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofClientAndcmdStateBetween(Long posId, Long clientId,
                                                                        CommandState cmdState,
                                                                        Instant startDate, Instant endDate,
                                                                        int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The clientId can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        if(cmdState == null){
            log.error("The cmdState can't be null in the method");
            throw new NullArgumentException("L'argument cmdState ne peut etre null pour cette methode");
        }
        Optional<Page<Command>> optionalCommandPage = commandRepository.
                findPageCommandinPosofClientAndcmdStateBetween(posId, clientId, cmdState,
                        startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofClientAndcmdStatusBetween(Long posId, Long clientId,
                                                                        CommandStatus cmdStatus,
                                                                        Instant startDate, Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The client id can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        if(cmdStatus == null){
            log.error("The cmdStatus can't be null in the method");
            throw new NullArgumentException("L'argument cmdStatus ne peut etre null pour cette methode");
        }
        Optional<List<Command>> optionalCommandList = commandRepository.findAllCommandinPosofClientAndcmdStatusBetween(posId,
                clientId, cmdStatus, startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofClientAndcmdStatusBetween(Long posId, Long clientId,
                                                                         CommandStatus cmdStatus,
                                                                         Instant startDate, Instant endDate,
                                                                         int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The clientId can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        if(cmdStatus == null){
            log.error("The cmdStatus can't be null in the method");
            throw new NullArgumentException("L'argument cmdStatus ne peut etre null pour cette methode");
        }
        Optional<Page<Command>> optionalCommandPage = commandRepository.
                findPageCommandinPosofClientAndcmdStatusBetween(posId, clientId, cmdStatus,
                        startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofClientAndcmdDeliveryStateBetween(Long posId, Long clientId,
                                                                               DeliveryState deliveryState,
                                                                               Instant startDate, Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The client id can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        if(deliveryState == null){
            log.error("The deliveryState can't be null in the method");
            throw new NullArgumentException("L'argument deliveryState ne peut etre null pour cette methode");
        }
        Optional<List<Command>> optionalCommandList = commandRepository.
                findAllCommandinPosofClientAndcmdDeliveryStateBetween(posId, clientId, deliveryState, startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofClientAndcmdDeliveryStateBetween(Long posId, Long clientId,
                                                                                DeliveryState deliveryState,
                                                                                Instant startDate, Instant endDate,
                                                                                int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The clientId can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        if(deliveryState == null){
            log.error("The deliveryState can't be null in the method");
            throw new NullArgumentException("L'argument deliveryState ne peut etre null pour cette methode");
        }
        Optional<Page<Command>> optionalCommandPage = commandRepository.
                findPageCommandinPosofClientAndcmdDeliveryStateBetween(posId, clientId, deliveryState,
                        startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofClientAndcmdTypeAndcmdStateBetween(
            Long posId, Long clientId,
            CommandType cmdType, CommandState cmdState,
            Instant startDate, Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The client id can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(cmdState == null){
            log.error("The cmdState can't be null in the method");
            throw new NullArgumentException("L'argument cmdState ne peut etre null pour cette methode");
        }
        Optional<List<Command>> optionalCommandList = commandRepository.
                findAllCommandinPosofClientAndcmdTypeAndcmdStateBetween(posId, clientId, cmdType, cmdState, startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofClientAndcmdTypeAndcmdStateBetween(
            Long posId, Long clientId,
            CommandType cmdType, CommandState cmdState,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The clientId can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(cmdState == null){
            log.error("The cmdState can't be null in the method");
            throw new NullArgumentException("L'argument cmdState ne peut etre null pour cette methode");
        }
        Optional<Page<Command>> optionalCommandPage = commandRepository.
                findPageCommandinPosofClientAndcmdTypeAndcmdStateBetween(posId, clientId, cmdType, cmdState,
                        startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofClientAndcmdTypeAndcmdStatusBetween(
            Long posId, Long clientId,
            CommandType cmdType, CommandStatus cmdStatus,
            Instant startDate, Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The client id can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(cmdStatus == null){
            log.error("The cmdStatus can't be null in the method");
            throw new NullArgumentException("L'argument cmdStatus ne peut etre null pour cette methode");
        }
        Optional<List<Command>> optionalCommandList = commandRepository.
                findAllCommandinPosofClientAndcmdTypeAndcmdStatusBetween(posId, clientId, cmdType, cmdStatus, startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofClientAndcmdTypeAndcmdStatusBetween(
            Long posId, Long clientId,
            CommandType cmdType, CommandStatus cmdStatus,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The clientId can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(cmdStatus == null){
            log.error("The cmdStatus can't be null in the method");
            throw new NullArgumentException("L'argument cmdStatus ne peut etre null pour cette methode");
        }
        Optional<Page<Command>> optionalCommandPage = commandRepository.
                findPageCommandinPosofClientAndcmdTypeAndcmdStatusBetween(posId, clientId, cmdType, cmdStatus,
                        startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofClientAndcmdTypeAndcmdDeliveryStateBetween(
            Long posId, Long clientId,
            CommandType cmdType, DeliveryState deliveryState,
            Instant startDate, Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The client id can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(deliveryState == null){
            log.error("The deliveryState can't be null in the method");
            throw new NullArgumentException("L'argument deliveryState ne peut etre null pour cette methode");
        }
        Optional<List<Command>> optionalCommandList = commandRepository.
                findAllCommandinPosofClientAndcmdTypeAndcmdDeliveryStateBetween(posId, clientId, cmdType, deliveryState, startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofClientAndcmdTypeAndcmdDeliveryStateBetween(
            Long posId, Long clientId,
            CommandType cmdType, DeliveryState deliveryState,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The clientId can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(deliveryState == null){
            log.error("The deliveryState can't be null in the method");
            throw new NullArgumentException("L'argument deliveryState ne peut etre null pour cette methode");
        }
        Optional<Page<Command>> optionalCommandPage = commandRepository.
                findPageCommandinPosofClientAndcmdTypeAndcmdDeliveryStateBetween(posId, clientId, cmdType, deliveryState,
                        startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofClientAndcmdTypeAndcmdStateAndcmdStatusBetween(
            Long posId, Long clientId,
            CommandType cmdType, CommandState cmdState,
            CommandStatus cmdStatus,
            Instant startDate, Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The client id can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(cmdState == null){
            log.error("The cmdState can't be null in the method");
            throw new NullArgumentException("L'argument cmdState ne peut etre null pour cette methode");
        }
        if(cmdStatus == null){
            log.error("The cmdStatus can't be null in the method");
            throw new NullArgumentException("L'argument cmdStatus ne peut etre null pour cette methode");
        }
        Optional<List<Command>> optionalCommandList = commandRepository.
                findAllCommandinPosofClientAndcmdTypeAndcmdStateAndcmdStatusBetween(posId, clientId, cmdType,
                        cmdState, cmdStatus, startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofClientAndcmdTypeAndcmdStateAndcmdStatusBetween(
            Long posId, Long clientId,
            CommandType cmdType, CommandState cmdState,
            CommandStatus cmdStatus,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The clientId can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(cmdState == null){
            log.error("The cmdState can't be null in the method");
            throw new NullArgumentException("L'argument cmdState ne peut etre null pour cette methode");
        }
        if(cmdStatus == null){
            log.error("The cmdStatus can't be null in the method");
            throw new NullArgumentException("L'argument cmdStatus ne peut etre null pour cette methode");
        }
        Optional<Page<Command>> optionalCommandPage = commandRepository.
                findPageCommandinPosofClientAndcmdTypeAndcmdStateAndcmdStatusBetween(posId, clientId, cmdType, cmdState,
                        cmdStatus, startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofClientAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween(
            Long posId, Long clientId,
            CommandType cmdType, CommandState cmdState,
            DeliveryState deliveryState,
            Instant startDate, Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The client id can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(cmdState == null){
            log.error("The cmdState can't be null in the method");
            throw new NullArgumentException("L'argument cmdState ne peut etre null pour cette methode");
        }
        if(deliveryState == null){
            log.error("The deliveryState can't be null in the method");
            throw new NullArgumentException("L'argument deliveryState ne peut etre null pour cette methode");
        }
        Optional<List<Command>> optionalCommandList = commandRepository.
                findAllCommandinPosofClientAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween(posId, clientId, cmdType,
                        cmdState, deliveryState, startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofClientAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween(
            Long posId, Long clientId,
            CommandType cmdType, CommandState cmdState,
            DeliveryState deliveryState,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The clientId can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(cmdState == null){
            log.error("The cmdState can't be null in the method");
            throw new NullArgumentException("L'argument cmdState ne peut etre null pour cette methode");
        }
        if(deliveryState == null){
            log.error("The deliveryState can't be null in the method");
            throw new NullArgumentException("L'argument deliveryState ne peut etre null pour cette methode");
        }
        Optional<Page<Command>> optionalCommandPage = commandRepository.
                findPageCommandinPosofClientAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween(posId, clientId, cmdType, cmdState,
                        deliveryState, startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofClientAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(
            Long posId, Long clientId,
            CommandType cmdType, CommandStatus cmdStatus,
            DeliveryState deliveryState,
            Instant startDate, Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The client id can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(cmdStatus == null){
            log.error("The cmdStatus can't be null in the method");
            throw new NullArgumentException("L'argument cmdStatus ne peut etre null pour cette methode");
        }
        if(deliveryState == null){
            log.error("The deliveryState can't be null in the method");
            throw new NullArgumentException("L'argument deliveryState ne peut etre null pour cette methode");
        }
        Optional<List<Command>> optionalCommandList = commandRepository.
                findAllCommandinPosofClientAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(posId, clientId, cmdType,
                        cmdStatus, deliveryState, startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofClientAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(
            Long posId, Long clientId,
            CommandType cmdType, CommandStatus cmdStatus,
            DeliveryState deliveryState,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The clientId can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(cmdStatus == null){
            log.error("The cmdStatus can't be null in the method");
            throw new NullArgumentException("L'argument cmdStatus ne peut etre null pour cette methode");
        }
        if(deliveryState == null){
            log.error("The deliveryState can't be null in the method");
            throw new NullArgumentException("L'argument deliveryState ne peut etre null pour cette methode");
        }
        Optional<Page<Command>> optionalCommandPage = commandRepository.
                findPageCommandinPosofClientAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(posId, clientId, cmdType,
                        cmdStatus, deliveryState, startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofClientAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
            Long posId, Long clientId,
            CommandType cmdType, CommandState cmdState,
            CommandStatus cmdStatus, DeliveryState deliveryState,
            Instant startDate, Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The client id can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(cmdState == null){
            log.error("The cmdState can't be null in the method");
            throw new NullArgumentException("L'argument cmdState ne peut etre null pour cette methode");
        }
        if(cmdStatus == null){
            log.error("The cmdStatus can't be null in the method");
            throw new NullArgumentException("L'argument cmdStatus ne peut etre null pour cette methode");
        }
        if(deliveryState == null){
            log.error("The deliveryState can't be null in the method");
            throw new NullArgumentException("L'argument deliveryState ne peut etre null pour cette methode");
        }
        Optional<List<Command>> optionalCommandList = commandRepository.
                findAllCommandinPosofClientAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween(posId, clientId,
                        cmdType, cmdState, cmdStatus, deliveryState, startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofClientAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
            Long posId, Long clientId,
            CommandType cmdType, CommandState cmdState,
            CommandStatus cmdStatus, DeliveryState deliveryState,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The clientId can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(cmdState == null){
            log.error("The cmdState can't be null in the method");
            throw new NullArgumentException("L'argument cmdState ne peut etre null pour cette methode");
        }
        if(cmdStatus == null){
            log.error("The cmdStatus can't be null in the method");
            throw new NullArgumentException("L'argument cmdStatus ne peut etre null pour cette methode");
        }
        if(deliveryState == null){
            log.error("The deliveryState can't be null in the method");
            throw new NullArgumentException("L'argument deliveryState ne peut etre null pour cette methode");
        }
        Optional<Page<Command>> optionalCommandPage = commandRepository.
                findPageCommandinPosofClientAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween(posId, clientId,
                        cmdType, cmdState, cmdStatus, deliveryState, startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofUserbmBetween(Long posId, Long userbmId,
                                                            Instant startDate, Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The user id can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        Optional<List<Command>> optionalCommandList = commandRepository.findAllCommandinPosofUserbmBetween(posId,
                userbmId, startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofUserbmBetween(Long posId, Long userbmId,
                                                             Instant startDate, Instant endDate,
                                                             int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The userbmId can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        Optional<Page<Command>> optionalCommandPage = commandRepository.
                findPageCommandinPosofUserbmBetween(posId, userbmId, startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofUserbmAndcmdTypeBetween(Long posId, Long userbmId,
                                                                      CommandType cmdType,
                                                                      Instant startDate, Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The user id can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        Optional<List<Command>> optionalCommandList = commandRepository.findAllCommandinPosofUserbmAndcmdTypeBetween(
                posId, userbmId, cmdType, startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofUserbmAndcmdTypeBetween(Long posId, Long userbmId,
                                                                       CommandType cmdType,
                                                                       Instant startDate, Instant endDate,
                                                                       int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The userbmId can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        Optional<Page<Command>> optionalCommandPage = commandRepository.
                findPageCommandinPosofUserbmAndcmdTypeBetween(posId, userbmId, cmdType, startDate, endDate,
                        PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofUserbmAndcmdStateBetween(Long posId, Long userbmId,
                                                                       CommandState cmdState,
                                                                       Instant startDate, Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The user id can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        if(cmdState == null){
            log.error("The cmdState can't be null in the method");
            throw new NullArgumentException("L'argument cmdState ne peut etre null pour cette methode");
        }
        Optional<List<Command>> optionalCommandList = commandRepository.findAllCommandinPosofUserbmAndcmdStateBetween(
                posId, userbmId, cmdState, startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofUserbmAndcmdStateBetween(Long posId, Long userbmId,
                                                                        CommandState cmdState,
                                                                        Instant startDate, Instant endDate,
                                                                        int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The userbmId can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        if(cmdState == null){
            log.error("The cmdState can't be null in the method");
            throw new NullArgumentException("L'argument cmdState ne peut etre null pour cette methode");
        }
        Optional<Page<Command>> optionalCommandPage = commandRepository.
                findPageCommandinPosofUserbmAndcmdStateBetween(posId, userbmId, cmdState, startDate, endDate,
                        PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofUserbmAndcmdStatusBetween(Long posId, Long userbmId,
                                                                        CommandState cmdStatus,
                                                                        Instant startDate, Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The user id can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        if(cmdStatus == null){
            log.error("The cmdStatus can't be null in the method");
            throw new NullArgumentException("L'argument cmdStatus ne peut etre null pour cette methode");
        }
        Optional<List<Command>> optionalCommandList = commandRepository.findAllCommandinPosofUserbmAndcmdStatusBetween(
                posId, userbmId, cmdStatus, startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofUserbmAndcmdStatusBetween(Long posId, Long userbmId,
                                                                         CommandState cmdStatus,
                                                                         Instant startDate, Instant endDate,
                                                                         int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The userbmId can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        if(cmdStatus == null){
            log.error("The cmdStatus can't be null in the method");
            throw new NullArgumentException("L'argument cmdStatus ne peut etre null pour cette methode");
        }
        Optional<Page<Command>> optionalCommandPage = commandRepository.
                findPageCommandinPosofUserbmAndcmdStatusBetween(posId, userbmId, cmdStatus, startDate, endDate,
                        PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofUserbmAndcmdDeliveryStateBetween(Long posId, Long userbmId,
                                                                               DeliveryState deliveryState,
                                                                               Instant startDate, Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The user id can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        if(deliveryState == null){
            log.error("The deliveryState can't be null in the method");
            throw new NullArgumentException("L'argument deliveryState ne peut etre null pour cette methode");
        }
        Optional<List<Command>> optionalCommandList = commandRepository.findAllCommandinPosofUserbmAndcmdDeliveryStateBetween(
                posId, userbmId, deliveryState, startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofUserbmAndcmdDeliveryStateBetween(Long posId, Long userbmId,
                                                                                DeliveryState deliveryState,
                                                                                Instant startDate, Instant endDate,
                                                                                int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The userbmId can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        if(deliveryState == null){
            log.error("The deliveryState can't be null in the method");
            throw new NullArgumentException("L'argument deliveryState ne peut etre null pour cette methode");
        }
        Optional<Page<Command>> optionalCommandPage = commandRepository.
                findPageCommandinPosofUserbmAndcmdDeliveryStateBetween(posId, userbmId, deliveryState, startDate, endDate,
                        PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofUserbmAndcmdTypeAndcmdStateBetween(
            Long posId, Long userbmId,
            CommandType cmdType, CommandState cmdState,
            Instant startDate, Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The user id can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(cmdState == null){
            log.error("The cmdState can't be null in the method");
            throw new NullArgumentException("L'argument cmdState ne peut etre null pour cette methode");
        }
        Optional<List<Command>> optionalCommandList = commandRepository.
                findAllCommandinPosofUserbmAndcmdTypeAndcmdStateBetween(posId, userbmId, cmdType, cmdState,
                        startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofUserbmAndcmdTypeAndcmdStateBetween(
            Long posId, Long userbmId,
            CommandType cmdType, CommandState cmdState,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The userbmId can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(cmdState == null){
            log.error("The cmdState can't be null in the method");
            throw new NullArgumentException("L'argument cmdState ne peut etre null pour cette methode");
        }
        Optional<Page<Command>> optionalCommandPage = commandRepository.
                findPageCommandinPosofUserbmAndcmdTypeAndcmdStateBetween(posId, userbmId, cmdType, cmdState,
                        startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofUserbmAndcmdTypeAndcmdStatusBetween(
            Long posId, Long userbmId,
            CommandType cmdType, CommandStatus cmdStatus,
            Instant startDate, Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The user id can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(cmdStatus == null){
            log.error("The cmdStatus can't be null in the method");
            throw new NullArgumentException("L'argument cmdStatus ne peut etre null pour cette methode");
        }
        Optional<List<Command>> optionalCommandList = commandRepository.
                findAllCommandinPosofUserbmAndcmdTypeAndcmdStatusBetween(posId, userbmId, cmdType, cmdStatus,
                        startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofUserbmAndcmdTypeAndcmdStatusBetween(
            Long posId, Long userbmId,
            CommandType cmdType, CommandStatus cmdStatus,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The userbmId can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(cmdStatus == null){
            log.error("The cmdStatus can't be null in the method");
            throw new NullArgumentException("L'argument cmdStatus ne peut etre null pour cette methode");
        }
        Optional<Page<Command>> optionalCommandPage = commandRepository.
                findPageCommandinPosofUserbmAndcmdTypeAndcmdStatusBetween(posId, userbmId, cmdType, cmdStatus,
                        startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofUserbmAndcmdTypeAndcmdStatusBetween(
            Long posId, Long userbmId,
            CommandType cmdType, DeliveryState deliveryState,
            Instant startDate, Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The user id can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(deliveryState == null){
            log.error("The deliveryState can't be null in the method");
            throw new NullArgumentException("L'argument deliveryState ne peut etre null pour cette methode");
        }
        Optional<List<Command>> optionalCommandList = commandRepository.
                findAllCommandinPosofUserbmAndcmdTypeAndcmdStatusBetween(posId, userbmId, cmdType, deliveryState,
                        startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofUserbmAndcmdTypeAndcmdStatusBetween(
            Long posId, Long userbmId,
            CommandType cmdType, DeliveryState deliveryState,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The userbmId can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(deliveryState == null){
            log.error("The deliveryState can't be null in the method");
            throw new NullArgumentException("L'argument deliveryState ne peut etre null pour cette methode");
        }
        Optional<Page<Command>> optionalCommandPage = commandRepository.
                findPageCommandinPosofUserbmAndcmdTypeAndcmdStatusBetween(posId, userbmId, cmdType, deliveryState,
                        startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdStatusBetween(
            Long posId, Long userbmId,
            CommandType cmdType, CommandState cmdState,
            CommandStatus cmdStatus,
            Instant startDate, Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The user id can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(cmdState == null){
            log.error("The cmdState can't be null in the method");
            throw new NullArgumentException("L'argument cmdState ne peut etre null pour cette methode");
        }
        if(cmdStatus == null){
            log.error("The cmdStatus can't be null in the method");
            throw new NullArgumentException("L'argument cmdStatus ne peut etre null pour cette methode");
        }
        Optional<List<Command>> optionalCommandList = commandRepository.
                findAllCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdStatusBetween(posId, userbmId, cmdType, cmdState,
                        cmdStatus, startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdStatusBetween(
            Long posId, Long userbmId,
            CommandType cmdType, CommandState cmdState,
            CommandStatus cmdStatus,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The userbmId can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(cmdState == null){
            log.error("The cmdState can't be null in the method");
            throw new NullArgumentException("L'argument cmdState ne peut etre null pour cette methode");
        }
        if(cmdStatus == null){
            log.error("The cmdStatus can't be null in the method");
            throw new NullArgumentException("L'argument cmdStatus ne peut etre null pour cette methode");
        }
        Optional<Page<Command>> optionalCommandPage = commandRepository.
                findPageCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdStatusBetween(posId, userbmId, cmdType, cmdState,
                        cmdStatus, startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween(
            Long posId, Long userbmId,
            CommandType cmdType, CommandState cmdState,
            DeliveryState deliveryState,
            Instant startDate, Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The user id can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(cmdState == null){
            log.error("The cmdState can't be null in the method");
            throw new NullArgumentException("L'argument cmdState ne peut etre null pour cette methode");
        }
        if(deliveryState == null){
            log.error("The deliveryState can't be null in the method");
            throw new NullArgumentException("L'argument deliveryState ne peut etre null pour cette methode");
        }
        Optional<List<Command>> optionalCommandList = commandRepository.
                findAllCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween(posId, userbmId, cmdType, cmdState,
                        deliveryState, startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween(
            Long posId, Long userbmId,
            CommandType cmdType, CommandState cmdState,
            DeliveryState deliveryState,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The userbmId can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(cmdState == null){
            log.error("The cmdState can't be null in the method");
            throw new NullArgumentException("L'argument cmdState ne peut etre null pour cette methode");
        }
        if(deliveryState == null){
            log.error("The deliveryState can't be null in the method");
            throw new NullArgumentException("L'argument deliveryState ne peut etre null pour cette methode");
        }
        Optional<Page<Command>> optionalCommandPage = commandRepository.
                findPageCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween(posId, userbmId, cmdType, cmdState,
                        deliveryState, startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofUserbmAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(
            Long posId, Long userbmId,
            CommandType cmdType, CommandStatus cmdStatus,
            DeliveryState deliveryState,
            Instant startDate, Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The user id can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(cmdStatus == null){
            log.error("The cmdStatus can't be null in the method");
            throw new NullArgumentException("L'argument cmdStatus ne peut etre null pour cette methode");
        }
        if(deliveryState == null){
            log.error("The deliveryState can't be null in the method");
            throw new NullArgumentException("L'argument deliveryState ne peut etre null pour cette methode");
        }
        Optional<List<Command>> optionalCommandList = commandRepository.
                findAllCommandinPosofUserbmAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(posId, userbmId, cmdType,
                        cmdStatus, deliveryState, startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofUserbmAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(
            Long posId, Long userbmId,
            CommandType cmdType, CommandStatus cmdStatus,
            DeliveryState deliveryState,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The userbmId can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(cmdStatus == null){
            log.error("The cmdStatus can't be null in the method");
            throw new NullArgumentException("L'argument cmdStatus ne peut etre null pour cette methode");
        }
        if(deliveryState == null){
            log.error("The deliveryState can't be null in the method");
            throw new NullArgumentException("L'argument deliveryState ne peut etre null pour cette methode");
        }
        Optional<Page<Command>> optionalCommandPage = commandRepository.
                findPageCommandinPosofUserbmAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(posId, userbmId, cmdType,
                        cmdStatus, deliveryState, startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
            Long posId, Long userbmId,
            CommandType cmdType, CommandState cmdState,
            CommandStatus cmdStatus, DeliveryState deliveryState,
            Instant startDate, Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The user id can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(cmdState == null){
            log.error("The cmdState can't be null in the method");
            throw new NullArgumentException("L'argument cmdState ne peut etre null pour cette methode");
        }
        if(cmdStatus == null){
            log.error("The cmdStatus can't be null in the method");
            throw new NullArgumentException("L'argument cmdStatus ne peut etre null pour cette methode");
        }
        if(deliveryState == null){
            log.error("The deliveryState can't be null in the method");
            throw new NullArgumentException("L'argument deliveryState ne peut etre null pour cette methode");
        }
        Optional<List<Command>> optionalCommandList = commandRepository.
                findAllCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween(posId, userbmId,
                        cmdType, cmdState, cmdStatus, deliveryState, startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
            Long posId, Long userbmId,
            CommandType cmdType, CommandState cmdState,
            CommandStatus cmdStatus, DeliveryState deliveryState,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The userbmId can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        if(cmdState == null){
            log.error("The cmdState can't be null in the method");
            throw new NullArgumentException("L'argument cmdState ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(cmdStatus == null){
            log.error("The cmdStatus can't be null in the method");
            throw new NullArgumentException("L'argument cmdStatus ne peut etre null pour cette methode");
        }
        if(deliveryState == null){
            log.error("The deliveryState can't be null in the method");
            throw new NullArgumentException("L'argument deliveryState ne peut etre null pour cette methode");
        }
        Optional<Page<Command>> optionalCommandPage = commandRepository.
                findPageCommandinPosofUserbmAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween(posId, userbmId, cmdType,
                        cmdState, cmdStatus, deliveryState, startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofClientforUserbmBetween(Long posId, Long clientId, Long userbmId,
                                                                        Instant startDate, Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The user id can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The clientId can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        Optional<List<Command>> optionalCommandList = commandRepository.
                findAllCommandinPosofClientforUserbmBetween(posId, clientId, userbmId, startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofUserbmforClientBetween(Long posId, Long userbmId,
                                                                      Long clientId,
                                                                      Instant startDate, Instant endDate,
                                                                      int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The userbmId can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The clientId can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        Optional<Page<Command>> optionalCommandPage = commandRepository.
                findPageCommandinPosofUserbmforClientBetween(posId, userbmId, clientId,
                        startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofClientAndUserbmAndcmdTypeBetween(Long posId, Long clientId,
                                                                               Long userbmId, CommandType cmdType,
                                                                               Instant startDate, Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The user id can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The clientId can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        Optional<List<Command>> optionalCommandList = commandRepository.
                findAllCommandinPosofClientAndUserbmAndcmdTypeBetween(posId, clientId, userbmId, cmdType, startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofClientAndUserbmAndcmdTypeBetween(Long posId, Long clientId,
                                                                                Long userbmId, CommandType cmdType,
                                                                                Instant startDate, Instant endDate,
                                                                                int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The userbmId can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The clientId can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        Optional<Page<Command>> optionalCommandPage = commandRepository.
                findPageCommandinPosofClientAndUserbmAndcmdTypeBetween(posId, userbmId, clientId, cmdType,
                        startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofClientAndUserbmAndcmdStateBetween(Long posId, Long clientId,
                                                                                Long userbmId, CommandState cmdState,
                                                                                Instant startDate, Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The user id can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The clientId can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        if(cmdState == null){
            log.error("The cmdState can't be null in the method");
            throw new NullArgumentException("L'argument cmdState ne peut etre null pour cette methode");
        }
        Optional<List<Command>> optionalCommandList = commandRepository.
                findAllCommandinPosofClientAndUserbmAndcmdStateBetween(posId, clientId, userbmId, cmdState, startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofClientAndUserbmAndcmdStateBetween(Long posId, Long clientId,
                                                                                 Long userbmId, CommandState cmdState,
                                                                                 Instant startDate, Instant endDate,
                                                                                 int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The userbmId can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The clientId can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        if(cmdState == null){
            log.error("The cmdState can't be null in the method");
            throw new NullArgumentException("L'argument cmdState ne peut etre null pour cette methode");
        }
        Optional<Page<Command>> optionalCommandPage = commandRepository.
                findPageCommandinPosofClientAndUserbmAndcmdStateBetween(posId, userbmId, clientId, cmdState,
                        startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofClientAndUserbmAndcmdStateBetween(Long posId, Long clientId,
                                                                                Long userbmId, CommandStatus cmdStatus,
                                                                                Instant startDate, Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The user id can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The clientId can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        if(cmdStatus == null){
            log.error("The cmdStatus can't be null in the method");
            throw new NullArgumentException("L'argument cmdStatus ne peut etre null pour cette methode");
        }
        Optional<List<Command>> optionalCommandList = commandRepository.
                findAllCommandinPosofClientAndUserbmAndcmdStateBetween(posId, clientId, userbmId, cmdStatus, startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofClientAndUserbmAndcmdStateBetween(Long posId, Long clientId,
                                                                                 Long userbmId, CommandStatus cmdStatus,
                                                                                 Instant startDate, Instant endDate,
                                                                                 int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The userbmId can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The clientId can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        if(cmdStatus == null){
            log.error("The cmdStatus can't be null in the method");
            throw new NullArgumentException("L'argument cmdStatus ne peut etre null pour cette methode");
        }
        Optional<Page<Command>> optionalCommandPage = commandRepository.
                findPageCommandinPosofClientAndUserbmAndcmdStateBetween(posId, userbmId, clientId, cmdStatus,
                        startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofClientAndUserbmAndcmdDeliveryStateBetween(
            Long posId, Long clientId,
            Long userbmId, DeliveryState deliveryState,
            Instant startDate, Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The user id can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The clientId can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        if(deliveryState == null){
            log.error("The deliveryState can't be null in the method");
            throw new NullArgumentException("L'argument deliveryState ne peut etre null pour cette methode");
        }
        Optional<List<Command>> optionalCommandList = commandRepository.
                findAllCommandinPosofClientAndUserbmAndcmdDeliveryStateBetween(posId, clientId, userbmId, deliveryState,
                        startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofClientAndUserbmAndcmdDeliveryStateBetween(
            Long posId, Long clientId,
            Long userbmId, DeliveryState deliveryState,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The userbmId can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The clientId can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        if(deliveryState == null){
            log.error("The deliveryState can't be null in the method");
            throw new NullArgumentException("L'argument deliveryState ne peut etre null pour cette methode");
        }
        Optional<Page<Command>> optionalCommandPage = commandRepository.
                findPageCommandinPosofClientAndUserbmAndcmdDeliveryStateBetween(posId, userbmId, clientId, deliveryState,
                        startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateBetween(
            Long posId, Long clientId,
            Long userbmId, CommandType cmdType,
            CommandState cmdState,
            Instant startDate, Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The user id can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The clientId can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(cmdState == null){
            log.error("The cmdState can't be null in the method");
            throw new NullArgumentException("L'argument cmdState ne peut etre null pour cette methode");
        }
        Optional<List<Command>> optionalCommandList = commandRepository.
                findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateBetween(posId, clientId, userbmId, cmdType,
                        cmdState, startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateBetween(
            Long posId, Long clientId,
            Long userbmId, CommandType cmdType,
            CommandState cmdState,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The userbmId can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The clientId can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(cmdState == null){
            log.error("The cmdState can't be null in the method");
            throw new NullArgumentException("L'argument cmdState ne peut etre null pour cette methode");
        }
        Optional<Page<Command>> optionalCommandPage = commandRepository.
                findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateBetween(posId, userbmId, clientId, cmdType,
                        cmdState, startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStatusBetween(
            Long posId, Long clientId,
            Long userbmId, CommandType cmdType,
            CommandStatus cmdStatus,
            Instant startDate, Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The user id can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The clientId can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(cmdStatus == null){
            log.error("The cmdStatus can't be null in the method");
            throw new NullArgumentException("L'argument cmdStatus ne peut etre null pour cette methode");
        }
        Optional<List<Command>> optionalCommandList = commandRepository.
                findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStatusBetween(posId, clientId, userbmId, cmdType,
                        cmdStatus, startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStatusBetween(
            Long posId, Long clientId,
            Long userbmId, CommandType cmdType,
            CommandStatus cmdStatus,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The userbmId can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The clientId can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(cmdStatus == null){
            log.error("The cmdStatus can't be null in the method");
            throw new NullArgumentException("L'argument cmdStatus ne peut etre null pour cette methode");
        }
        Optional<Page<Command>> optionalCommandPage = commandRepository.
                findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStatusBetween(posId, userbmId, clientId, cmdType,
                        cmdStatus, startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStatusBetween(
            Long posId, Long clientId,
            Long userbmId, CommandType cmdType,
            DeliveryState deliveryState,
            Instant startDate, Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The user id can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The clientId can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(deliveryState == null){
            log.error("The deliveryState can't be null in the method");
            throw new NullArgumentException("L'argument deliveryState ne peut etre null pour cette methode");
        }
        Optional<List<Command>> optionalCommandList = commandRepository.
                findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStatusBetween(posId, clientId, userbmId, cmdType,
                        deliveryState, startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStatusBetween(
            Long posId, Long clientId,
            Long userbmId, CommandType cmdType,
            DeliveryState deliveryState,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The userbmId can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The clientId can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(deliveryState == null){
            log.error("The deliveryState can't be null in the method");
            throw new NullArgumentException("L'argument deliveryState ne peut etre null pour cette methode");
        }
        Optional<Page<Command>> optionalCommandPage = commandRepository.
                findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStatusBetween(posId, userbmId, clientId, cmdType,
                        deliveryState, startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdStatusBetween(
            Long posId, Long clientId,
            Long userbmId, CommandType cmdType,
            CommandState cmdState, CommandStatus cmdStatus,
            Instant startDate, Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The user id can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The clientId can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(cmdState == null){
            log.error("The cmdState can't be null in the method");
            throw new NullArgumentException("L'argument cmdState ne peut etre null pour cette methode");
        }
        if(cmdStatus == null){
            log.error("The cmdStatus can't be null in the method");
            throw new NullArgumentException("L'argument cmdStatus ne peut etre null pour cette methode");
        }
        Optional<List<Command>> optionalCommandList = commandRepository.
                findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdStatusBetween(posId, clientId, userbmId, cmdType,
                        cmdState, cmdStatus, startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdStatusBetween(
            Long posId, Long clientId,
            Long userbmId, CommandType cmdType,
            CommandState cmdState, CommandStatus cmdStatus,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The userbmId can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The clientId can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(cmdState == null){
            log.error("The cmdState can't be null in the method");
            throw new NullArgumentException("L'argument cmdState ne peut etre null pour cette methode");
        }
        if(cmdStatus == null){
            log.error("The cmdStatus can't be null in the method");
            throw new NullArgumentException("L'argument cmdStatus ne peut etre null pour cette methode");
        }
        Optional<Page<Command>> optionalCommandPage = commandRepository.
                findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdStatusBetween(posId, userbmId, clientId,
                        cmdType, cmdState, cmdStatus, startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween(
            Long posId, Long clientId,
            Long userbmId, CommandType cmdType,
            CommandState cmdState, DeliveryState deliveryState,
            Instant startDate, Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The user id can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The clientId can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(cmdState == null){
            log.error("The cmdState can't be null in the method");
            throw new NullArgumentException("L'argument cmdState ne peut etre null pour cette methode");
        }
        if(deliveryState == null){
            log.error("The deliveryState can't be null in the method");
            throw new NullArgumentException("L'argument deliveryState ne peut etre null pour cette methode");
        }
        Optional<List<Command>> optionalCommandList = commandRepository.
                findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween(posId, clientId,
                        userbmId, cmdType, cmdState, deliveryState, startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween(
            Long posId, Long clientId,
            Long userbmId, CommandType cmdType,
            CommandState cmdState, DeliveryState deliveryState,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The userbmId can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The clientId can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(cmdState == null){
            log.error("The cmdState can't be null in the method");
            throw new NullArgumentException("L'argument cmdState ne peut etre null pour cette methode");
        }
        if(deliveryState == null){
            log.error("The deliveryState can't be null in the method");
            throw new NullArgumentException("L'argument deliveryState ne peut etre null pour cette methode");
        }
        Optional<Page<Command>> optionalCommandPage = commandRepository.
                findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdDeliveryStateBetween(posId, userbmId, clientId,
                        cmdType, cmdState, deliveryState, startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(
            Long posId, Long clientId,
            Long userbmId, CommandType cmdType,
            CommandStatus cmdStatus, DeliveryState deliveryState,
            Instant startDate, Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The user id can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The clientId can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(cmdStatus == null){
            log.error("The cmdStatus can't be null in the method");
            throw new NullArgumentException("L'argument cmdStatus ne peut etre null pour cette methode");
        }
        if(deliveryState == null){
            log.error("The deliveryState can't be null in the method");
            throw new NullArgumentException("L'argument deliveryState ne peut etre null pour cette methode");
        }
        Optional<List<Command>> optionalCommandList = commandRepository.
                findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(posId, clientId,
                        userbmId, cmdType, cmdStatus, deliveryState, startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(
            Long posId, Long clientId,
            Long userbmId, CommandType cmdType,
            CommandStatus cmdStatus, DeliveryState deliveryState,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The userbmId can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The clientId can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(cmdStatus == null){
            log.error("The cmdStatus can't be null in the method");
            throw new NullArgumentException("L'argument cmdStatus ne peut etre null pour cette methode");
        }
        if(deliveryState == null){
            log.error("The deliveryState can't be null in the method");
            throw new NullArgumentException("L'argument deliveryState ne peut etre null pour cette methode");
        }
        Optional<Page<Command>> optionalCommandPage = commandRepository.
                findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStatusAndcmdDeliveryStateBetween(posId, clientId,
                        userbmId, cmdType, cmdStatus, deliveryState, startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
            Long posId, Long clientId,
            Long userbmId, CommandType cmdType,
            CommandState cmdState, CommandStatus cmdStatus,
            DeliveryState deliveryState,
            Instant startDate, Instant endDate) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The user id can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The clientId can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(cmdState == null){
            log.error("The cmdState can't be null in the method");
            throw new NullArgumentException("L'argument cmdState ne peut etre null pour cette methode");
        }
        if(cmdStatus == null){
            log.error("The cmdStatus can't be null in the method");
            throw new NullArgumentException("L'argument cmdStatus ne peut etre null pour cette methode");
        }
        if(deliveryState == null){
            log.error("The deliveryState can't be null in the method");
            throw new NullArgumentException("L'argument deliveryState ne peut etre null pour cette methode");
        }
        Optional<List<Command>> optionalCommandList = commandRepository.
                findAllCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween(posId,
                        clientId, userbmId, cmdType, cmdState, cmdStatus, deliveryState, startDate, endDate);
        if(!optionalCommandList.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween(
            Long posId, Long clientId,
            Long userbmId, CommandType cmdType,
            CommandState cmdState, CommandStatus cmdStatus,
            DeliveryState deliveryState,
            Instant startDate, Instant endDate,
            int pagenum, int pagesize) {
        /****************************************************************
         * Il faut se rassurer que les arguments envoye ne sont pas null
         */
        if(posId == null){
            log.error("The pos id can't be null in the method");
            throw new NullArgumentException("L'argument posId ne peut etre null pour cette methode");
        }
        if(userbmId == null){
            log.error("The userbmId can't be null in the method");
            throw new NullArgumentException("L'argument userbmId ne peut etre null pour cette methode");
        }
        if(clientId == null){
            log.error("The clientId can't be null in the method");
            throw new NullArgumentException("L'argument clientId ne peut etre null pour cette methode");
        }
        if(cmdType == null){
            log.error("The cmdType can't be null in the method");
            throw new NullArgumentException("L'argument cmdType ne peut etre null pour cette methode");
        }
        if(cmdState == null){
            log.error("The cmdState can't be null in the method");
            throw new NullArgumentException("L'argument cmdState ne peut etre null pour cette methode");
        }
        if(cmdStatus == null){
            log.error("The cmdStatus can't be null in the method");
            throw new NullArgumentException("L'argument cmdStatus ne peut etre null pour cette methode");
        }
        if(deliveryState == null){
            log.error("The deliveryState can't be null in the method");
            throw new NullArgumentException("L'argument deliveryState ne peut etre null pour cette methode");
        }
        Optional<Page<Command>> optionalCommandPage = commandRepository.
                findPageCommandinPosofClientAndUserbmAndcmdTypeAndcmdStateAndcmdStatusAndcmdDeliveryStateBetween(posId, clientId,
                        userbmId, cmdType, cmdState, cmdStatus, deliveryState, startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no pointofsale in DB with the precised id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'id precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }

    @Override
    public List<CommandDto> findAllCommandofLoadinginPos(Long loadingId, Long posId) {
        if(loadingId == null){
            log.error("The loading id precise is null");
            throw new NullArgumentException("L'id du loading precise est null");
        }

        if(posId == null){
            log.error("The pos id precise is null");
            throw new NullArgumentException("L'id du pos precise est null");
        }

        Optional<List<Command>> optionalCommandList = commandRepository.findAllCommandofLoadinginPos(loadingId, posId);
        if(!optionalCommandList.isPresent()){
            log.error("There is no loading with the id {} or pointofsale of the id {} link in the DB", loadingId, posId);
            throw new EntityNotFoundException("Il n'existe pas de loading lie au pointofsale precise en BD",
                    ErrorCode.LOADING_NOT_FOUND);
        }

        return optionalCommandList.get().stream().map(CommandDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CommandDto> findPageCommandofLoadinginPos(Long loadingId, Long posId, int pagenum, int pagesize) {
        if(loadingId == null){
            log.error("The loading id precise is null");
            throw new NullArgumentException("L'id du loading precise est null");
        }

        if(posId == null){
            log.error("The pos id precise is null");
            throw new NullArgumentException("L'id du pos precise est null");
        }

        Optional<Page<Command>> optionalCommandPage = commandRepository.findPageCommandofLoadinginPos(loadingId, posId,
                PageRequest.of(pagenum, pagesize));
        if(!optionalCommandPage.isPresent()){
            log.error("There is no loading with the id {} or pointofsale of the id {} link in the DB", loadingId, posId);
            throw new EntityNotFoundException("Il n'existe pas de loading lie au pointofsale precise en BD",
                    ErrorCode.LOADING_NOT_FOUND);
        }

        return optionalCommandPage.get().map(CommandDto::fromEntity);
    }


}
