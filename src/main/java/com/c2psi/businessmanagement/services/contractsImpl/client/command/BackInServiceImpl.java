package com.c2psi.businessmanagement.services.contractsImpl.client.command;

import com.c2psi.businessmanagement.dtos.client.command.BackInDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.BackIn;
import com.c2psi.businessmanagement.models.Command;
import com.c2psi.businessmanagement.models.Pointofsale;
import com.c2psi.businessmanagement.models.UserBM;
import com.c2psi.businessmanagement.repositories.client.command.BackInRepository;
import com.c2psi.businessmanagement.repositories.client.command.CommandRepository;
import com.c2psi.businessmanagement.repositories.pos.pos.PointofsaleRepository;
import com.c2psi.businessmanagement.repositories.pos.userbm.UserBMRepository;
import com.c2psi.businessmanagement.services.contracts.client.command.BackInService;
import com.c2psi.businessmanagement.validators.client.command.BackInValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value="BackInServiceV1")
@Slf4j
@Transactional
public class BackInServiceImpl implements BackInService {
    private CommandRepository commandRepository;
    private PointofsaleRepository pointofsaleRepository;
    private UserBMRepository userBMRepository;
    private BackInRepository backInRepository;

    @Autowired
    public BackInServiceImpl(CommandRepository commandRepository, PointofsaleRepository pointofsaleRepository,
                             UserBMRepository userBMRepository, BackInRepository backInRepository) {
        this.commandRepository = commandRepository;
        this.pointofsaleRepository = pointofsaleRepository;
        this.userBMRepository = userBMRepository;
        this.backInRepository = backInRepository;
    }

    @Override
    public BackInDto saveBackIn(BackInDto backInDto) {
        /********************************************************
         * On effectue la validation du parametre grace au
         * validateur
         */
        List<String> errors = BackInValidator.validate(backInDto);
        if(!errors.isEmpty()){
            log.error("Entity backInDto not valid {}", backInDto);
            throw new InvalidEntityException("Le backInDto passe en argument n'est pas valide:  ",
                    ErrorCode.BACKIN_NOT_VALID, errors);
        }

        /****************************************************
         * On verifie si l'Id du Userbm est non null et si
         * c'est le cas on verifie quil existe en BD
         */
        if(backInDto.getBiUserbmDto().getId() == null){
            log.error("The Id of the UserBM is null");
            throw new InvalidEntityException("L'Id du UserBM indique dans le backin est null ",
                    ErrorCode.BACKIN_NOT_VALID);
        }
        //Ici on est sur que l'id du Userbm n'est pas null
        Optional<UserBM> optionalUserBM = userBMRepository.findUserBMById(backInDto.getBiUserbmDto().getId());
        if(!optionalUserBM.isPresent()){
            log.error("The userbm precised in the backin is not in the DB");
            throw new InvalidEntityException("Le Userbm indique est inexistant dans la BD ",
                    ErrorCode.BACKIN_NOT_VALID);
        }

        /*********************************************************
         * On verifie que l'Id du Pointofsale existe et si c'est
         * le cas on verifie quil existe en BD
         */
        if(backInDto.getBiPosDto().getId() == null){
            log.error("The Id precised for the Pointofsale is null");
            throw new InvalidEntityException("L'Id du Pointofsale indique dans le backin est null",
                    ErrorCode.BACKIN_NOT_VALID);
        }
        //Ici on est sur que l'id du pointofsale n'est pas null
        Optional<Pointofsale> optionalPointofsale = pointofsaleRepository.findPointofsaleById(
                backInDto.getBiPosDto().getId());
        if(!optionalPointofsale.isPresent()){
            log.error("The pointofsale precised in the backin is not in the DB");
            throw new InvalidEntityException("Le Pointofsale indique est inexistant dans la BD ",
                    ErrorCode.BACKIN_NOT_VALID);
        }

        /*************************************************************
         * On verifie que l'id du Command existe et si cest le cas on
         * se rassure qu'il existe en BD
         */
        if(backInDto.getBiCommandDto().getId() == null){
            log.error("The Id precised for the Command is null");
            throw new InvalidEntityException("L'Id de la Command indiaue dans le backin est null",
                    ErrorCode.BACKIN_NOT_VALID);
        }
        //Ici on est sur que l'id du Command existe
        Optional<Command> optionalCommand = commandRepository.findCommandById(backInDto.getBiCommandDto().getId());
        if(!optionalCommand.isPresent()){
            log.error("The Command precised in the backin is not in the DB");
            throw new InvalidEntityException("Le Command indique est inexistant dans la BD ",
                    ErrorCode.BACKIN_NOT_VALID);
        }

        /************************************************************
         * On recupere la command associe a ce BackIn et se rassurer
         * que la date du cmd est anterieure a la date du BackIn
         */
        //Ici on est sur que la command existe bien en BD
        if(optionalCommand.get().getCmdDate().isAfter(backInDto.getBiDate())){
            log.error("The command date can't be after the backinDate");
            throw new InvalidEntityException("La date de la command ne peut etre ulterieure a la date du backin",
                    ErrorCode.BACKIN_NOT_VALID);
        }

        /*************************************************************************
         * Se rassurer qu'il existe aucun BackIn deja enregistre pour le Command *
         *************************************************************************/
        if(!isBackInUniqueforCommand(optionalCommand.get().getId())){
            log.error("There exist a backin already associate with the command");
            throw new DuplicateEntityException("Il existe deja dans la BD un backin associe a la Command",
                    ErrorCode.BACKIN_DUPLICATED);
        }

        /****************************************************
         * Si tout est bon l'enregistrement peut etre fait
         ****************************************************/
        log.info("After all verification the record {} can be register in the DB", backInDto);
        return BackInDto.fromEntity(
                backInRepository.save(
                        BackInDto.toEntity(backInDto)
                )
        );
    }

    @Override
    public Boolean isBackInUniqueforCommand(Long cmdId) {
        if(cmdId == null){
            log.error("The cmdId precised is null");
            throw new NullArgumentException("Le cmdId precise est null ");
        }
        Optional<BackIn> optionalBackIn = backInRepository.findBackInByofCommand(cmdId);
        return optionalBackIn.isEmpty();
    }

    @Override
    public BackInDto updateBackIn(BackInDto backInDto) {
        /******************************************************
         * Il faut valider le parametre grace au validateur
         */
        List<String> errors = BackInValidator.validate(backInDto);
        if(!errors.isEmpty()){
            log.error("Entity backInDto not valid {}", backInDto);
            throw new InvalidEntityException("Le backInDto passe en argument n'est pas valide:  ",
                    ErrorCode.BACKIN_NOT_VALID, errors);
        }

        /******************************************************
         * Il faut retrouver le backin a update apres s'etre
         * rassurer que cet Id n'est pas null
         */
        if(backInDto.getId() == null){
            log.error("The id precised for the backin is null");
            throw new InvalidEntityException("L'Id du backin a update est null", ErrorCode.BACKIN_NOT_VALID);
        }
        Optional<BackIn> optionalBackIn = backInRepository.findBackInById(backInDto.getId());
        if(!optionalBackIn.isPresent()){
            log.error("The BackIn precised does not exist in the DB");
            throw new InvalidEntityException("Le BackIn indique n'existe pas en BD ", ErrorCode.BACKIN_NOT_VALID);
        }
        //Ici on est sur que le BackIn existe bel et bien
        BackIn backInToUpdate = optionalBackIn.get();

        /*******************************************************
         * Il faut se rassurer que ce n'est pas le pointofsale
         * quon veut modifier
         */
        if(!backInToUpdate.getBiPos().getId().equals(backInDto.getBiPosDto().getId())){
            log.error("The pointofsale cannot be modified in the backin");
            throw new InvalidEntityException("Le pointofsale ne peut etre modifie dans le BackIn",
                    ErrorCode.BACKIN_NOT_VALID);
        }

        /********************************************************
         * Il faut se rassurer que ce n'est pas le Command quon
         * veut modifier
         */
        if(!backInToUpdate.getBiCommand().getId().equals(backInDto.getBiCommandDto().getId())){
            log.error("The Command can't be modified in the backin");
            throw new InvalidEntityException("Le Command ne peut etre modifie dans le BackIn",
                    ErrorCode.BACKIN_NOT_VALID);
        }

        /********************************************************
         * Il faut se rassurer que ce n'est pas le UserBm quon
         * veut modifier
         */
        if(!backInToUpdate.getBiUserbm().getId().equals(backInDto.getBiUserbmDto().getId())){
            log.error("The UserBM can't be modified in the backIn");
            throw new InvalidEntityException("Le UserBM ne peut etre modifie dans le BackIn ",
                    ErrorCode.BACKIN_NOT_VALID);
        }

        /*********************************************************
         * On modifie le reste des attributs et on fait le update
         */
        backInToUpdate.setBiDate(backInDto.getBiDate());
        backInToUpdate.setBiComment(backInDto.getBiComment());
        log.info("After all verification, the record can be done on the DB");
        return BackInDto.fromEntity(backInRepository.save(backInToUpdate));
    }

    @Override
    public BackInDto findBackInById(Long backInId) {
        if(backInId == null){
            log.error("The Id precised in the method is null");
            throw new NullArgumentException("L'argument passe en parametre est null");
        }
        Optional<BackIn> optionalBackIn = backInRepository.findBackInById(backInId);
        if(!optionalBackIn.isPresent()){
            log.error("There is no backIn in the DB with the Id {} precised", backInId);
            throw new EntityNotFoundException("Il n'existe aucun BackIn en BD avec l'id precise ",
                    ErrorCode.BACKIN_NOT_FOUND);
        }
        return BackInDto.fromEntity(optionalBackIn.get());
    }

    @Override
    public BackInDto findBackInofCommand(Long cmdId) {
        if(cmdId == null){
            log.error("The cmdId precised is null");
            throw new NullArgumentException("L'argument passe en parametre est null");
        }

        Optional<BackIn> optionalBackIn = backInRepository.findBackInByofCommand(cmdId);
        if(!optionalBackIn.isPresent()){
            log.error("There is no BackIn associate with the cmdId {} precised ", cmdId);
            throw new InvalidEntityException("Aucun BackIn n'est associe a la command precise ",
                    ErrorCode.BACKIN_NOT_FOUND);
        }

        return BackInDto.fromEntity(optionalBackIn.get());
    }

    @Override
    public List<BackInDto> findAllBackIninPosBetween(Long posId, Instant startDate, Instant endDate) {
        if(posId == null){
            log.error("The posId precised is null");
            throw new NullArgumentException("Le posId precise est null");
        }
        Optional<List<BackIn>> optionalBackInList = backInRepository.findAllBackIninPosBetween(posId, startDate,
                endDate);
        if(!optionalBackInList.isPresent()){
            log.error("There is no Pointofsale with the Id precised");
            throw new EntityNotFoundException("Aucun Pointofsale n'existe avec le posId precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalBackInList.get().stream().map(BackInDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<BackInDto> findPageBackIninPosBetween(Long posId, Instant startDate, Instant endDate,
                                                   int pagenum, int pagesize) {
        if(posId == null){
            log.error("The posId precised is null");
            throw new NullArgumentException("Le posId precise est null");
        }
        Optional<Page<BackIn>> optionalBackInPage = backInRepository.findPageBackIninPosBetween(posId, startDate,
                endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalBackInPage.isPresent()){
            log.error("There is no Pointofsale with the Id precised");
            throw new EntityNotFoundException("Aucun Pointofsale n'existe avec le posId precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalBackInPage.get().map(BackInDto::fromEntity);
    }

    @Override
    public List<BackInDto> findAllBackIninPosBetween(Long posId, Long userbmId, Instant startDate, Instant endDate) {
        if(posId == null){
            log.error("The posId precised is null");
            throw new NullArgumentException("Le posId precise est null");
        }

        if(userbmId == null){
            log.error("The userbmId precised is null");
            throw new NullArgumentException("Le userbmId precise est null");
        }

        Optional<List<BackIn>> optionalBackInList = backInRepository.findAllBackIninPosforUserbmBetween(posId, userbmId,
                startDate, endDate);
        if(!optionalBackInList.isPresent()){
            log.error("There is no Pointofsale with the Id precised");
            throw new EntityNotFoundException("Aucun Pointofsale n'existe avec le posId precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalBackInList.get().stream().map(BackInDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<BackInDto> findPageBackIninPosBetween(Long posId, Long userbmId, Instant startDate, Instant endDate,
                                                   int pagenum, int pagesize) {
        if(posId == null){
            log.error("The posId precised is null");
            throw new NullArgumentException("Le posId precise est null");
        }

        if(userbmId == null){
            log.error("The userbmId precised is null");
            throw new NullArgumentException("Le userbmId precise est null");
        }

        Optional<Page<BackIn>> optionalBackInPage = backInRepository.findPageBackIninPosforUserbmBetween(posId, userbmId,
                startDate, endDate, PageRequest.of(pagenum, pagesize));
        if(!optionalBackInPage.isPresent()){
            log.error("There is no Pointofsale with the Id precised");
            throw new EntityNotFoundException("Aucun Pointofsale n'existe avec le posId precise ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalBackInPage.get().map(BackInDto::fromEntity);
    }

    @Override
    public Boolean isBackInByDeleteable(Long backInId) {
        return true;
    }

    @Override
    public Boolean deleteBackInById(Long backInId) {
        if(backInId == null){
            log.error("The backInId precised is null");
            throw new NullArgumentException("Le backInId precise est null");
        }
        Optional<BackIn> optionalBackIn = backInRepository.findBackInById(backInId);
        if(!optionalBackIn.isPresent()){
            log.error("There is no BackIn in the DB with is precised");
            throw new EntityNotFoundException("Aucun BackIn n'existe avec l'id precise ",
                    ErrorCode.BACKIN_NOT_FOUND);
        }
        if(!isBackInByDeleteable(backInId)){
            log.error("The BackIn can't be deleteable");
            throw new EntityNotDeleteableException("Le BackIn precise ne peut etre supprime ",
                    ErrorCode.BACKIN_NOT_DELETEABLE);
        }
        return null;
    }

}
