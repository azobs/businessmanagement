package com.c2psi.businessmanagement.services.contractsImpl.client.client;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.client.client.DiversCashOperationDto;
import com.c2psi.businessmanagement.dtos.pos.pos.OperationDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.DiversCashOperation;
import com.c2psi.businessmanagement.models.UserBM;
import com.c2psi.businessmanagement.repositories.client.client.DiversCashOperationRepository;
import com.c2psi.businessmanagement.repositories.pos.userbm.UserBMRepository;
import com.c2psi.businessmanagement.services.contracts.client.client.DiversCashOperationService;
import com.c2psi.businessmanagement.validators.client.client.DiversCashOperationValidator;
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

@Service(value="DiversCashOperationServiceV1")
@Slf4j
@Transactional
public class DiversCashOperationServiceImpl implements DiversCashOperationService {
    private DiversCashOperationRepository diversCashOperationRepository;
    private UserBMRepository userBMRepository;

    @Autowired
    public DiversCashOperationServiceImpl(DiversCashOperationRepository diversCashOperationRepository,
                                          UserBMRepository userBMRepository) {
        this.diversCashOperationRepository = diversCashOperationRepository;
        this.userBMRepository = userBMRepository;
    }

    @Override
    public DiversCashOperationDto updateDiversCashOperation(DiversCashOperationDto dcaopDto) {

        /**************************************************
         * Verification de l'argument passe en parametre
         */
        List<String> errors = DiversCashOperationValidator.validate(dcaopDto);
        if(!errors.isEmpty()){
            log.error("Entity dcaopDto not valid {}", dcaopDto);
            throw new InvalidEntityException("Le dcaopDto passé en argument n'est pas valide: "+errors,
                    ErrorCode.DIVERSCASHOPERATION_NOT_VALID, errors);
        }

        /*****************************************************************
         * Il faut verifier que l'Id du userbm n'est pas null
         * et ensuite qu'il identifie reellement un userbm
         */
        if(dcaopDto.getDcoUserbmDto().getId() == null){
            log.error("The id of the userbm associate with the operation is null and then anything can't be done");
            throw new InvalidEntityException("L'id du userbm associe a l'operation etant null rien ne peut etre fait ",
                    ErrorCode.DIVERSCASHOPERATION_NOT_VALID);
        }
        //Ici cela veut dire que l'id est la mais est ce que ca identifie alors un UserBM?
        Optional<UserBM> optionalUserBM = userBMRepository.findUserBMById(
                dcaopDto.getDcoUserbmDto().getId());
        if(!optionalUserBM.isPresent()){
            log.error("There is no userbm in the DB with the precised Id");
            throw new EntityNotFoundException("Aucun UserBM n'existe en BD avec l'Id precise ",
                    ErrorCode.USERBM_NOT_FOUND);
        }

        /****************************************************************************
         * Il faut se rassurer que le type d'operation est soit credit soit debit
         * soit changement (permutation)
         */
        if(!dcaopDto.getDcoOperationDto().getOpType().equals(OperationType.Withdrawal) ||
                !dcaopDto.getDcoOperationDto().getOpType().equals(OperationType.Change) ||
                !dcaopDto.getDcoOperationDto().getOpType().equals(OperationType.Credit)){
            log.error("Operation Type not recognize by the system");
            throw new InvalidValueException("La valeur du type d'operation envoye n'est pas reconnu par le system");
        }

        /*******************************************************************
         * Il faut verifier que l'Id du DiversCashOperation n'est pas null
         * et ensuite qu'il identifie reellement un compte cash
         */
        if(dcaopDto.getId() == null){
            log.error("The id of the operation is null and then anything can't be done");
            throw new InvalidEntityException("L'id de l'operation etant null rien ne peut etre fait ",
                    ErrorCode.DIVERSCASHOPERATION_NOT_VALID);
        }
        //Ici cela veut dire que l'id est la mais est ce que ca identifie alors un DiversCashAccountOperation?
        Optional<DiversCashOperation> optionalDiversCashOperation = diversCashOperationRepository.findDiversCashOperationById(
                dcaopDto.getId());
        if(!optionalDiversCashOperation.isPresent()){
            log.error("There is no DiversCashAccountOperation in the DB with the precised Id");
            throw new EntityNotFoundException("Aucun DiversCashAccountOperation n'existe en BD avec l'Id precise ",
                    ErrorCode.DIVERSCASHOPERATION_NOT_FOUND);
        }

        ///////Ici on peut donc recuperer diverscapsuleaccount a modifier
        DiversCashOperation diversCashOperationToUpdate = optionalDiversCashOperation.get();
        //On fait donc les modif de la quantite en mouvement et de l'operation(dateop, objet, description etc.)
        diversCashOperationToUpdate.setDcoAmountinmvt(dcaopDto.getDcoAmountinmvt());
        diversCashOperationToUpdate.setDcoOperation(OperationDto.toEntity(dcaopDto.getDcoOperationDto()));

        log.info("After all verification, the operation can be updated normally");

        return DiversCashOperationDto.fromEntity(diversCashOperationRepository.save(diversCashOperationToUpdate));
    }

    @Override
    public Boolean isDiversCashOperationDeleatable(Long dcaopDto) {
        return true;
    }

    @Override
    public Boolean deleteDiversCashOperationById(Long dcaopId) {
        if(dcaopId == null){
            log.error("dcaopId is null");
            throw new NullArgumentException("le dcaopId passe en argument de la methode est null");
        }
        Optional<DiversCashOperation> optionalDiversCashOperation = diversCashOperationRepository.
                findDiversCashOperationById(dcaopId);
        if(optionalDiversCashOperation.isPresent()){
            if(isDiversCashOperationDeleatable(dcaopId)){
                diversCashOperationRepository.delete(optionalDiversCashOperation.get());
                return true;
            }
        }
        throw new EntityNotFoundException("Aucune entite n'exise avec l'id passe en argument ",
                ErrorCode.DIVERSCASHOPERATION_NOT_FOUND);
    }

    @Override
    public DiversCashOperationDto findDiversCashOperationById(Long dcaopId) {
        if(dcaopId == null){
            log.error("dcaopId is null");
            throw new NullArgumentException("le dcaopId passe en argument de la methode est null");
        }
        Optional<DiversCashOperation> optionalDiversCashOperation = diversCashOperationRepository.
                findDiversCashOperationById(dcaopId);

        if(!optionalDiversCashOperation.isPresent()){
            throw new EntityNotFoundException("Aucune DiversCashOperation avec l'id "+dcaopId
                    +" n'a été trouve dans la BDD", ErrorCode.DIVERSCASHOPERATION_NOT_FOUND);
        }

        return DiversCashOperationDto.fromEntity(optionalDiversCashOperation.get());
    }

    @Override
    public List<DiversCashOperationDto> findAllDiversCashOperation(Long dcaopId) {
        List<DiversCashOperation> diversCashOperationList = diversCashOperationRepository.
                findAllDiversCashOperation(dcaopId);
        return diversCashOperationList.stream().map(DiversCashOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<DiversCashOperationDto> findPageDiversCashOperation(Long dcaopId, int pagenum, int pagesize) {
        Page<DiversCashOperation> diversCashOperationPage = diversCashOperationRepository.
                findPageDiversCashOperation(dcaopId,
                        PageRequest.of(pagenum, pagesize));
        return diversCashOperationPage.map(DiversCashOperationDto::fromEntity);
    }

    @Override
    public List<DiversCashOperationDto> findAllDiversCashOperationBetween(Long dcaopId, Instant startDate, Instant endDate) {
        List<DiversCashOperation> diversCashOperationListBetween =
                diversCashOperationRepository.findAllDiversCashOperationBetween(dcaopId, startDate, endDate);
        return diversCashOperationListBetween.stream().map(DiversCashOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<DiversCashOperationDto> findPageDiversCashOperationBetween(Long dcaopId, Instant startDate,
                                                                           Instant endDate, int pagenum, int pagesize) {
        Page<DiversCashOperation> diversCashOperationPageBetween =
                diversCashOperationRepository.findPageDiversCashOperationBetween(dcaopId, startDate, endDate,
                        PageRequest.of(pagenum, pagesize));
        return diversCashOperationPageBetween.map(DiversCashOperationDto::fromEntity);
    }

    @Override
    public List<DiversCashOperationDto> findAllDiversCashOperationofTypeBetween(Long dcaopId, OperationType opType,
                                                                                Instant startDate, Instant endDate) {
        List<DiversCashOperation> diversCashOperationListBetween =
                diversCashOperationRepository.findAllDiversCashOperationOfTypeBetween(dcaopId, opType, startDate, endDate);
        return diversCashOperationListBetween.stream().map(DiversCashOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<DiversCashOperationDto> findPageDiversCashOperationofTypeBetween(Long dcaopId, OperationType opType,
                                                                           Instant startDate, Instant endDate,
                                                                           int pagenum, int pagesize) {
        Page<DiversCashOperation> diversCashOperationPageBetween =
                diversCashOperationRepository.findPageDiversCashOperationOfTypeBetween(dcaopId, opType, startDate,
                        endDate, PageRequest.of(pagenum, pagesize));
        return diversCashOperationPageBetween.map(DiversCashOperationDto::fromEntity);
    }
}
