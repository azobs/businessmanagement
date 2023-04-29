package com.c2psi.businessmanagement.services.contractsImpl.pos.pos;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.pos.pos.OperationDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosPackagingOperationDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.PosPackagingOperation;
import com.c2psi.businessmanagement.models.UserBM;
import com.c2psi.businessmanagement.repositories.pos.pos.PosPackagingOperationRepository;
import com.c2psi.businessmanagement.repositories.pos.userbm.UserBMRepository;
import com.c2psi.businessmanagement.services.contracts.pos.pos.PosPackagingOperationService;
import com.c2psi.businessmanagement.validators.pos.pos.PosPackagingOperationValidator;
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

@Service(value="PosPackagingOperationServiceV1")
@Slf4j
@Transactional
public class PosPackagingOperationServiceImpl implements PosPackagingOperationService {

    private PosPackagingOperationRepository posPackagingOperationRepository;
    private UserBMRepository userBMRepository;

    @Autowired
    public PosPackagingOperationServiceImpl(PosPackagingOperationRepository posPackagingOperationRepository,
                                            UserBMRepository userBMRepository) {
        this.posPackagingOperationRepository = posPackagingOperationRepository;
        this.userBMRepository = userBMRepository;
    }

    @Override
    public PosPackagingOperationDto updatePosPackagingOperation(PosPackagingOperationDto ppackopDto) {

        /********************************************************************
         * Il faut d'abord valider le parametre pris en parametre
         */
        List<String> errors = PosPackagingOperationValidator.validate(ppackopDto);
        if(!errors.isEmpty()){
            log.error("Entity ppackopDto not valid {}", ppackopDto);
            throw new InvalidEntityException("Le ppackopDto passé en argument n'est pas valide: "+errors,
                    ErrorCode.POSPACKAGINGOPERATION_NOT_VALID, errors);
        }

        /*****************************************************************
         * Il faut verifier que l'Id du userbm n'est pas null
         * et ensuite qu'il identifie reellement un userbm
         */
        if(ppackopDto.getPospoUserbmDto().getId() == null){
            log.error("The id of the userbm associate with the operation is null and then anything can't be done");
            throw new InvalidEntityException("L'id du userbm associe a l'operation etant null rien ne peut etre fait ",
                    ErrorCode.POSPACKAGINGOPERATION_NOT_VALID);
        }
        //Ici cela veut dire que l'id est la mais est ce que ca identifie alors un PosCashAccountOperation?
        Optional<UserBM> optionalUserBM = userBMRepository.findUserBMById(
                ppackopDto.getPospoUserbmDto().getId());
        if(!optionalUserBM.isPresent()){
            log.error("There is no userbm in the DB with the precised Id");
            throw new EntityNotFoundException("Aucun UserBM n'existe en BD avec l'Id precise ",
                    ErrorCode.USERBM_NOT_FOUND);
        }

        /****************************************************************************
         * Il faut se rassurer que le type d'operation est soit credit soit debit
         * soit changement (permutation)
         */
        if(!ppackopDto.getPospoOperationDto().getOpType().equals(OperationType.Withdrawal) &&
                !ppackopDto.getPospoOperationDto().getOpType().equals(OperationType.Change) &&
                !ppackopDto.getPospoOperationDto().getOpType().equals(OperationType.Credit)){
            log.error("Operation Type not recognize by the system");
            throw new InvalidValueException("La valeur du type d'operation envoye n'est pas reconnu par le system");
        }

        /*******************************************************************
         * Il faut verifier que l'Id du PosPackagingOperation n'est pas null
         * et ensuite qu'il identifie reellement un compte capsule
         */
        if(ppackopDto.getId() == null){
            log.error("The id of the operation is null and then anything can't be done");
            throw new InvalidEntityException("L'id de l'operation etant null rien ne peut etre fait ",
                    ErrorCode.POSPACKAGINGOPERATION_NOT_VALID);
        }
        //Ici cela veut dire que l'id est la mais est ce que ca identifie alors un PosCapsuleAccountOperation?
        Optional<PosPackagingOperation> optionalPosPackagingOperation = posPackagingOperationRepository.findPosPackagingOperationById(
                ppackopDto.getId());
        if(!optionalPosPackagingOperation.isPresent()){
            log.error("There is no PosPackagingAccountOperation in the DB with the precised Id");
            throw new EntityNotFoundException("Aucun PosPackagingAccountOperation n'existe en BD avec l'Id precise ",
                    ErrorCode.POSPACKAGINGACCOUNT_NOT_FOUND);
        }

        ///////Ici on peut donc recuperer pospackagingaccount a modifier
        PosPackagingOperation posPackagingOperationToUpdate = optionalPosPackagingOperation.get();
        //On fait donc les modif de la quantite en mouvement et de l'operation(dateop, objet, description etc.)
        posPackagingOperationToUpdate.setPospoNumberinmvt(ppackopDto.getPospoNumberinmvt());
        posPackagingOperationToUpdate.setPospoOperation(OperationDto.toEntity(ppackopDto.getPospoOperationDto()));

        log.info("After all verification, the operation can be updated normally");

        //PointofsaleDto.fromEntity(posRepository.save(posToUpdate))

        return PosPackagingOperationDto.fromEntity(posPackagingOperationRepository.save(posPackagingOperationToUpdate));
    }

    @Override
    public Boolean isPosPackagingOperationDeleteable(Long ppackopId) {
        return true;
    }

    @Override
    public Boolean deletePosPackagingOperationById(Long ppackopId) {
        if(ppackopId == null){
            log.error("ppackopId is null");
            throw new NullArgumentException("Le ppackopId passe en argument de la methode est null");
        }
        Optional<PosPackagingOperation> optionalPosPackagingOperation = posPackagingOperationRepository.
                findPosPackagingOperationById(ppackopId);
        if(optionalPosPackagingOperation.isPresent()){
            if(isPosPackagingOperationDeleteable(ppackopId)){
                posPackagingOperationRepository.delete(optionalPosPackagingOperation.get());
                return true;
            }
        }
        throw new EntityNotFoundException("Aucune entite n'existe avec l'ID passe en argument ",
                ErrorCode.POSPACKAGINGOPERATION_NOT_FOUND);
    }

    @Override
    public List<PosPackagingOperationDto> findAllPosPackagingOperation(Long ppackopId) {
        List<PosPackagingOperation> posPackagingOperationList = posPackagingOperationRepository.
                findAllPosPackagingOperation(ppackopId);
        return posPackagingOperationList.stream().map(PosPackagingOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public List<PosPackagingOperationDto> findAllPosPackagingOperationofType(Long ppackopId, OperationType opType) {
        List<PosPackagingOperation> posPackagingOperationList = posPackagingOperationRepository.findAllPosPackagingOperationOfType(
                ppackopId, opType);

        return posPackagingOperationList.stream().map(PosPackagingOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<PosPackagingOperationDto> findPagePosPackagingOperation(Long ppackopId, int pagenum, int pagesize) {
        Page<PosPackagingOperation> posPackagingOperationPage = posPackagingOperationRepository.findPagePosPackagingOperation(
                ppackopId, PageRequest.of(pagenum, pagesize));

        return posPackagingOperationPage.map(PosPackagingOperationDto::fromEntity);
    }

    @Override
    public Page<PosPackagingOperationDto> findPagePosPackagingOperationofType(Long ppackopId, OperationType opType,
                                                                              int pagenum, int pagesize) {
        Page<PosPackagingOperation> posPackagingOperationPage = posPackagingOperationRepository.findPagePosPackagingOperationOfType(
                ppackopId, opType, PageRequest.of(pagenum, pagesize));

        return posPackagingOperationPage.map(PosPackagingOperationDto::fromEntity);
    }

    @Override
    public List<PosPackagingOperationDto> findAllPosPackagingOperationBetween(Long ppackopId, Instant startDate,
                                                                              Instant endDate) {
        List<PosPackagingOperation> posPackagingOperationListBetween = posPackagingOperationRepository.
                findAllPosPackagingOperationBetween(ppackopId, startDate, endDate);
        return posPackagingOperationListBetween.stream().map(PosPackagingOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<PosPackagingOperationDto> findPagePosPackagingOperationBetween(Long ppackopId, Instant startDate,
                                                                               Instant endDate, int pagenum,
                                                                               int pagesize) {
        Page<PosPackagingOperation> posPackagingOperationPageBetween = posPackagingOperationRepository.
                findPagePosPackagingOperationBetween(ppackopId, startDate, endDate, PageRequest.of(pagenum, pagesize));
        return posPackagingOperationPageBetween.map(PosPackagingOperationDto::fromEntity);
    }

    @Override
    public List<PosPackagingOperationDto> findAllPosPackagingOperationBetween(Long ppackopId, OperationType op_type,
                                                                              Instant startDate, Instant endDate) {
        List<PosPackagingOperation> posPackagingOperationListoftypeBetween = posPackagingOperationRepository.
                findAllPosPackagingOperationOfTypeBetween(ppackopId, op_type, startDate, endDate);
        return posPackagingOperationListoftypeBetween.stream().map(PosPackagingOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<PosPackagingOperationDto> findPagePosPackagingOperationBetween(Long ppackopId, OperationType op_type,
                                                                               Instant startDate, Instant endDate,
                                                                               int pagenum, int pagesize) {
        Page<PosPackagingOperation> posPackagingOperationPageoftypeBetween = posPackagingOperationRepository.
                findPagePosPackagingOperationOfTypeBetween(ppackopId, op_type, startDate, endDate, PageRequest.of(pagenum, pagesize));
        return posPackagingOperationPageoftypeBetween.map(PosPackagingOperationDto::fromEntity);
    }
}
