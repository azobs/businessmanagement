package com.c2psi.businessmanagement.services.contractsImpl.pos.pos;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.pos.pos.OperationDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosDamageOperationDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.PosDamageOperation;
import com.c2psi.businessmanagement.models.UserBM;
import com.c2psi.businessmanagement.repositories.pos.pos.PosDamageOperationRepository;
import com.c2psi.businessmanagement.repositories.pos.userbm.UserBMRepository;
import com.c2psi.businessmanagement.services.contracts.pos.pos.PosDamageOperationService;
import com.c2psi.businessmanagement.validators.pos.pos.PosDamageOperationValidator;
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

@Service(value="PosDamageOperationServiceV1")
@Slf4j
@Transactional
public class PosDamageOperationServiceImpl implements PosDamageOperationService {

    private PosDamageOperationRepository posDamageOperationRepository;
    private UserBMRepository userBMRepository;

    @Autowired
    public PosDamageOperationServiceImpl(PosDamageOperationRepository posDamageOperationRepository,
                                         UserBMRepository userBMRepository) {
        this.posDamageOperationRepository = posDamageOperationRepository;
        this.userBMRepository = userBMRepository;
    }

    @Override
    public PosDamageOperationDto updatePosDamageOperation(PosDamageOperationDto posdamopDto) {
        /********************************************************************
         * Il faut d'abord valider l'argument pris en parametre
         */
        List<String> errors = PosDamageOperationValidator.validate(posdamopDto);
        if(!errors.isEmpty()){
            log.error("Entity posdamopDto not valid {}", posdamopDto);
            throw new InvalidEntityException("Le posdamopDto passé en argument n'est pas valide: "+errors,
                    ErrorCode.POSDAMAGEOPERATION_NOT_VALID, errors);
        }
        //////////////////////
        /********************************************************
         * Il faut verifier que l'Id du userbm n'est pas null
         * et ensuite qu'il identifie reellement un userbm
         */
        if(posdamopDto.getPosdoUserbmDto().getId() == null){
            log.error("The id of the userbm associate with the operation is null and then anything can't be done");
            throw new InvalidEntityException("L'id du userbm associe a l'operation etant null rien ne peut etre fait ",
                    ErrorCode.POSDAMAGEOPERATION_NOT_VALID);
        }
        //Ici cela veut dire que l'id est la mais est ce que ca identifie alors un PosDamageAccountOperation?
        Optional<UserBM> optionalUserBM = userBMRepository.findUserBMById(
                posdamopDto.getPosdoUserbmDto().getId());
        if(!optionalUserBM.isPresent()){
            log.error("There is no userbm in the DB with the precised Id");
            throw new EntityNotFoundException("Aucun UserBM n'existe en BD avec l'Id precise ",
                    ErrorCode.USERBM_NOT_FOUND);
        }
        ////////////////////////
        /****************************************************************************
         * Il faut se rassurer que le type d'operation est soit credit soit debit
         * soit changement (permutation)
         */
        if(!posdamopDto.getPosdoOperationDto().getOpType().equals(OperationType.Withdrawal) &&
                !posdamopDto.getPosdoOperationDto().getOpType().equals(OperationType.Change) &&
                !posdamopDto.getPosdoOperationDto().getOpType().equals(OperationType.Credit)){
            log.error("Operation Type not recognize by the system");
            throw new InvalidValueException("La valeur du type d'operation envoye n'est pas reconnu par le system");
        }
        /////////////////////
        /*******************************************************************
         * Il faut verifier que l'Id du PosDamageOperation n'est pas null
         * et ensuite qu'il identifie reellement un compte capsule
         */
        if(posdamopDto.getId() == null){
            log.error("The id of the operation is null and then anything can't be done");
            throw new InvalidEntityException("L'id de l'operation etant null rien ne peut etre fait ",
                    ErrorCode.POSDAMAGEOPERATION_NOT_VALID);
        }
        //Ici cela veut dire que l'id est la mais est ce que ca identifie alors un PosDamageAccountOperation?
        Optional<PosDamageOperation> optionalPosDamageOperation = posDamageOperationRepository.findPosDamageOperationById(
                posdamopDto.getId());
        if(!optionalPosDamageOperation.isPresent()){
            log.error("There is no PosDamageAccountOperation in the DB with the precised Id");
            throw new EntityNotFoundException("Aucun PosDamageAccountOperation n'existe en BD avec l'Id precise ",
                    ErrorCode.POSDAMAGEACCOUNT_NOT_FOUND);
        }

        ///////Ici on peut donc recuperer poscapsuleaccount a modifier
        PosDamageOperation posDamageOperationToUpdate = optionalPosDamageOperation.get();
        //On fait donc les modif de la quantite en mouvement et de l'operation(dateop, objet, description etc.)
        posDamageOperationToUpdate.setPosdoNumberinmvt(posdamopDto.getPosdoNumberinmvt());
        posDamageOperationToUpdate.setPosdoOperation(OperationDto.toEntity(posdamopDto.getPosdoOperationDto()));

        log.info("After all verification, the operation can be updated normally");

        //PointofsaleDto.fromEntity(posRepository.save(posToUpdate))

        return PosDamageOperationDto.fromEntity(posDamageOperationRepository.save(posDamageOperationToUpdate));
    }

    @Override
    public Boolean isPosDamageOperationDeleteable(Long posdamopId) {
        return true;
    }

    @Override
    public Boolean deletePosDamageOperationById(Long posdamopId) {
        if(posdamopId == null){
            log.error("posdamopId is null");
            throw new NullArgumentException("Le posdamopId passe en argument de la methode est null");
        }
        Optional<PosDamageOperation> optionalPosDamageOperation = posDamageOperationRepository.findPosDamageOperationById(posdamopId);
        if(optionalPosDamageOperation.isPresent()){
            if(isPosDamageOperationDeleteable(posdamopId)){
                posDamageOperationRepository.delete(optionalPosDamageOperation.get());
                return true;
            }
        }
        throw new EntityNotFoundException("Aucune entite n'existe avec l'ID passe en argument ",
                ErrorCode.POSDAMAGEOPERATION_NOT_FOUND);
    }

    @Override
    public List<PosDamageOperationDto> findAllPosDamageOperation(Long posdamopId) {
        List<PosDamageOperation> posDamageOperationList = posDamageOperationRepository.findAllPosDamageOperation(posdamopId);
        return posDamageOperationList.stream().map(PosDamageOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public List<PosDamageOperationDto> findAllPosDamageOperationofType(Long posdamopId, OperationType opType) {
        List<PosDamageOperation> posDamageOperationList = posDamageOperationRepository.findAllPosDamageOperationOfType(
                posdamopId, opType);

        return posDamageOperationList.stream().map(PosDamageOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<PosDamageOperationDto> findPagePosDamageOperation(Long posdamopId, int pagenum, int pagesize) {
        Page<PosDamageOperation> posDamageOperationPage = posDamageOperationRepository.findPagePosDamageOperation(
                posdamopId, PageRequest.of(pagenum, pagesize));

        return posDamageOperationPage.map(PosDamageOperationDto::fromEntity);
    }

    @Override
    public Page<PosDamageOperationDto> findPagePosDamageOperationofType(Long posdamopId, OperationType opType,
                                                                        int pagenum, int pagesize) {
        Page<PosDamageOperation> posDamageOperationPage = posDamageOperationRepository.findPagePosDamageOperationOfType(
                posdamopId, opType, PageRequest.of(pagenum, pagesize));

        return posDamageOperationPage.map(PosDamageOperationDto::fromEntity);
    }

    @Override
    public List<PosDamageOperationDto> findAllPosDamageOperationBetween(Long posdamopId, Instant startDate, Instant endDate) {
        List<PosDamageOperation> posDamageOperationListBetween = posDamageOperationRepository.
                findAllPosDamageOperationBetween(posdamopId, startDate, endDate);
        return posDamageOperationListBetween.stream().map(PosDamageOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<PosDamageOperationDto> findPagePosDamageOperationBetween(Long posdamopId, Instant startDate,
                                                                         Instant endDate, int pagenum, int pagesize) {
        Page<PosDamageOperation> posDamageOperationPageBetween = posDamageOperationRepository.
                findPagePosDamageOperationBetween(posdamopId, startDate, endDate, PageRequest.of(pagenum, pagesize));
        return posDamageOperationPageBetween.map(PosDamageOperationDto::fromEntity);
    }

    @Override
    public List<PosDamageOperationDto> findAllPosDamageOperationofTypeBetween(Long posdamopId, OperationType op_type, Instant startDate, Instant endDate) {
        List<PosDamageOperation> posDamageOperationListoftypeBetween = posDamageOperationRepository.
                findAllPosDamageOperationOfTypeBetween(posdamopId, op_type, startDate, endDate);
        return posDamageOperationListoftypeBetween.stream().map(PosDamageOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<PosDamageOperationDto> findPagePosDamageOperationofTypeBetween(Long posdamopId, OperationType op_type, Instant startDate, Instant endDate, int pagenum, int pagesize) {
        Page<PosDamageOperation> posDamageOperationPageoftypeBetween = posDamageOperationRepository.
                findPagePosDamageOperationOfTypeBetween(posdamopId, op_type, startDate, endDate, PageRequest.of(pagenum, pagesize));
        return posDamageOperationPageoftypeBetween.map(PosDamageOperationDto::fromEntity);
    }
}
