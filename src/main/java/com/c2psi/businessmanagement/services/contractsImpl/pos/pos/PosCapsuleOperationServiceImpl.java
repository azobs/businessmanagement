package com.c2psi.businessmanagement.services.contractsImpl.pos.pos;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.pos.pos.OperationDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCapsuleOperationDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.PosCapsuleOperation;
import com.c2psi.businessmanagement.models.ProviderCapsuleOperation;
import com.c2psi.businessmanagement.models.UserBM;
import com.c2psi.businessmanagement.repositories.pos.pos.PosCapsuleOperationRepository;
import com.c2psi.businessmanagement.repositories.pos.userbm.UserBMRepository;
import com.c2psi.businessmanagement.services.contracts.pos.pos.PosCapsuleOperationService;
import com.c2psi.businessmanagement.validators.pos.pos.PosCapsuleOperationValidator;
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

@Service(value="PosCapsuleOperationServiceV1")
@Slf4j
@Transactional
public class PosCapsuleOperationServiceImpl implements PosCapsuleOperationService {

    private PosCapsuleOperationRepository posCapsuleOperationRepository;
    private UserBMRepository userBMRepository;

    @Autowired
    public PosCapsuleOperationServiceImpl(PosCapsuleOperationRepository posCapsuleOperationRepository,
                                          UserBMRepository userBMRepository) {
        this.posCapsuleOperationRepository = posCapsuleOperationRepository;
        this.userBMRepository = userBMRepository;
    }

    @Override
    public PosCapsuleOperationDto updatePosCapsuleOperation(PosCapsuleOperationDto poscsopDto) {
        /********************************************************************
         * Il faut d'abord valider le parametre pris en parametre
         */
        List<String> errors = PosCapsuleOperationValidator.validate(poscsopDto);
        if(!errors.isEmpty()){
            log.error("Entity poscsopDto not valid {}", poscsopDto);
            throw new InvalidEntityException("Le poscsopDto passé en argument n'est pas valide: "+errors,
                    ErrorCode.POSCAPSULEOPERATION_NOT_VALID, errors);
        }

        /*****************************************************************
         * Il faut verifier que l'Id du userbm n'est pas null
         * et ensuite qu'il identifie reellement un userbm
         */
        if(poscsopDto.getPoscsoUserbmDto().getId() == null){
            log.error("The id of the userbm associate with the operation is null and then anything can't be done");
            throw new InvalidEntityException("L'id du userbm associe a l'operation etant null rien ne peut etre fait ",
                    ErrorCode.POSCAPSULEOPERATION_NOT_VALID);
        }
        //Ici cela veut dire que l'id est la mais est ce que ca identifie alors un PosCashAccountOperation?
        Optional<UserBM> optionalUserBM = userBMRepository.findUserBMById(
                poscsopDto.getPoscsoUserbmDto().getId());
        if(!optionalUserBM.isPresent()){
            log.error("There is no userbm in the DB with the precised Id");
            throw new EntityNotFoundException("Aucun UserBM n'existe en BD avec l'Id precise ",
                    ErrorCode.USERBM_NOT_FOUND);
        }

        /****************************************************************************
         * Il faut se rassurer que le type d'operation est soit credit soit debit
         * soit changement (permutation)
         */
        if(!poscsopDto.getPoscsoOperationDto().getOpType().equals(OperationType.Withdrawal) &&
                !poscsopDto.getPoscsoOperationDto().getOpType().equals(OperationType.Change) &&
                !poscsopDto.getPoscsoOperationDto().getOpType().equals(OperationType.Credit)){
            log.error("Operation Type not recognize by the system");
            throw new InvalidValueException("La valeur du type d'operation envoye n'est pas reconnu par le system");
        }

        /*******************************************************************
         * Il faut verifier que l'Id du PosCapsuleOperation n'est pas null
         * et ensuite qu'il identifie reellement un compte capsule
         */
        if(poscsopDto.getId() == null){
            log.error("The id of the operation is null and then anything can't be done");
            throw new InvalidEntityException("L'id de l'operation etant null rien ne peut etre fait ",
                    ErrorCode.POSCAPSULEOPERATION_NOT_VALID);
        }
        //Ici cela veut dire que l'id est la mais est ce que ca identifie alors un PosCapsuleAccountOperation?
        Optional<PosCapsuleOperation> optionalPosCapsuleOperation = posCapsuleOperationRepository.findPosCapsuleOperationById(
                poscsopDto.getId());
        if(!optionalPosCapsuleOperation.isPresent()){
            log.error("There is no PosCapsuleAccountOperation in the DB with the precised Id");
            throw new EntityNotFoundException("Aucun PosCapsuleAccountOperation n'existe en BD avec l'Id precise ",
                    ErrorCode.POSCAPSULEACCOUNT_NOT_FOUND);
        }

        ///////Ici on peut donc recuperer poscapsuleaccount a modifier
        PosCapsuleOperation posCapsuleOperationToUpdate = optionalPosCapsuleOperation.get();
        //On fait donc les modif de la quantite en mouvement et de l'operation(dateop, objet, description etc.)
        posCapsuleOperationToUpdate.setPoscsoNumberinmvt(poscsopDto.getPoscsoNumberinmvt());
        posCapsuleOperationToUpdate.setPoscsoOperation(OperationDto.toEntity(poscsopDto.getPoscsoOperationDto()));

        log.info("After all verification, the operation can be updated normally");

        //PointofsaleDto.fromEntity(posRepository.save(posToUpdate))

        return PosCapsuleOperationDto.fromEntity(posCapsuleOperationRepository.save(posCapsuleOperationToUpdate));

    }

    @Override
    public Boolean isPosCapsuleOperationDeleteable(Long poscapsopId) {
        return true;
    }

    @Override
    public Boolean deletePosCapsuleOperationById(Long poscsopId) {

        if(poscsopId == null){
            log.error("poscsopId is null");
            throw new NullArgumentException("Le poscsopId passe en argument de la methode est null");
        }
        Optional<PosCapsuleOperation> optionalPosCapsuleOperation = posCapsuleOperationRepository.
                findPosCapsuleOperationById(poscsopId);
        if(optionalPosCapsuleOperation.isPresent()){
            if(isPosCapsuleOperationDeleteable(poscsopId)){
                posCapsuleOperationRepository.delete(optionalPosCapsuleOperation.get());
                return true;
            }
        }
        throw new EntityNotFoundException("Aucune entite n'existe avec l'ID passe en argument ",
                ErrorCode.POSCAPSULEOPERATION_NOT_FOUND);
    }

    @Override
    public List<PosCapsuleOperationDto> findAllPosCapsuleOperation(Long pcapsaccId) {

        List<PosCapsuleOperation> posCapsuleOperationList = posCapsuleOperationRepository.findAllPosCapsuleOperation(pcapsaccId);
        return posCapsuleOperationList.stream().map(PosCapsuleOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public List<PosCapsuleOperationDto> findAllPosCapsuleOperationofType(Long pcapsaccId, OperationType opType) {

        List<PosCapsuleOperation> posCapsuleOperationList = posCapsuleOperationRepository.findAllPosCapsuleOperationOfType(
                pcapsaccId, opType);

        return posCapsuleOperationList.stream().map(PosCapsuleOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<PosCapsuleOperationDto> findPagePosCapsuleOperation(Long pcapsaccId, int pagenum, int pagesize) {
        Page<PosCapsuleOperation> posCapsuleOperationPage = posCapsuleOperationRepository.findPagePosCapsuleOperation(
                pcapsaccId, PageRequest.of(pagenum, pagesize));

        return posCapsuleOperationPage.map(PosCapsuleOperationDto::fromEntity);
    }

    @Override
    public Page<PosCapsuleOperationDto> findPagePosCapsuleOperationofType(Long pcapsaccId, OperationType opType,
                                                                          int pagenum, int pagesize) {
        Page<PosCapsuleOperation> posCapsuleOperationPage = posCapsuleOperationRepository.findPagePosCapsuleOperationOfType(
                pcapsaccId, opType, PageRequest.of(pagenum, pagesize));

        return posCapsuleOperationPage.map(PosCapsuleOperationDto::fromEntity);
    }

    @Override
    public List<PosCapsuleOperationDto> findAllPosCapsuleOperationBetween(Long pcapsaccId, Instant startDate,
                                                                          Instant endDate) {

        List<PosCapsuleOperation> posCapsuleOperationListBetween = posCapsuleOperationRepository.
                findAllPosCapsuleOperationBetween(pcapsaccId, startDate, endDate);
        return posCapsuleOperationListBetween.stream().map(PosCapsuleOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<PosCapsuleOperationDto> findPagePosCapsuleOperationBetween(Long pcapsaccId, Instant startDate,
                                                                           Instant endDate, int pagenum, int pagesize) {
        Page<PosCapsuleOperation> posCapsuleOperationPageBetween = posCapsuleOperationRepository.
                findPagePosCapsuleOperationBetween(pcapsaccId, startDate, endDate, PageRequest.of(pagenum, pagesize));
        return posCapsuleOperationPageBetween.map(PosCapsuleOperationDto::fromEntity);
    }

    @Override
    public List<PosCapsuleOperationDto> findAllPosCapsuleOperationBetween(Long pcapsaccId, OperationType op_type,
                                                                          Instant startDate, Instant endDate) {

        List<PosCapsuleOperation> posCapsuleOperationListoftypeBetween = posCapsuleOperationRepository.
                findAllPosCapsuleOperationOfTypeBetween(pcapsaccId, op_type, startDate, endDate);
        return posCapsuleOperationListoftypeBetween.stream().map(PosCapsuleOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<PosCapsuleOperationDto> findPagePosCapsuleOperationBetween(Long pcapsaccId, OperationType op_type,
                                                                           Instant startDate, Instant endDate,
                                                                           int pagenum, int pagesize) {
        Page<PosCapsuleOperation> posCapsuleOperationPageoftypeBetween = posCapsuleOperationRepository.
                findPagePosCapsuleOperationOfTypeBetween(pcapsaccId, op_type, startDate, endDate, PageRequest.of(pagenum, pagesize));
        return posCapsuleOperationPageoftypeBetween.map(PosCapsuleOperationDto::fromEntity);
    }
}
