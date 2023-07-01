package com.c2psi.businessmanagement.services.contractsImpl.pos.pos;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.pos.pos.OperationDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCashOperationDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.PosCashOperation;
import com.c2psi.businessmanagement.models.UserBM;
import com.c2psi.businessmanagement.repositories.pos.pos.PosCashOperationRepository;
import com.c2psi.businessmanagement.repositories.pos.userbm.UserBMRepository;
import com.c2psi.businessmanagement.services.contracts.pos.pos.PosCashOperationService;
import com.c2psi.businessmanagement.validators.pos.pos.PosCashOperationValidator;
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

@Service(value="PosCashOperationServiceV1")
@Slf4j
@Transactional
public class PosCashOperationServiceImpl implements PosCashOperationService {
    private PosCashOperationRepository posCashOperationRepository;
    private UserBMRepository userBMRepository;

    @Autowired
    public PosCashOperationServiceImpl(PosCashOperationRepository posCashOperationRepository,
                                       UserBMRepository userBMRepository) {
        this.posCashOperationRepository = posCashOperationRepository;
        this.userBMRepository = userBMRepository;
    }

    @Override
    public PosCashOperationDto updatePosCashOperation(PosCashOperationDto pcopDto) {
        /************************************************
         * Update car l'enregistrement d'une operation se fait lorsque l'operation est
         * entrain d'etre realise. Donc on peut juste mettre a jour l'operation au cas
         * ou il y a des details a change mais meme les traces de cette mise a jour
         * seront conserve dans le systeme
         * */
        List<String> errors = PosCashOperationValidator.validate(pcopDto);
        if(!errors.isEmpty()){
            log.error("Entity pcopDto not valid {}", pcopDto);
            throw new InvalidEntityException("Le pcopDto passé en argument n'est pas valide: "+errors,
                    ErrorCode.POSCASHOPERATION_NOT_VALID, errors);
        }

        /*****************************************************************
         * Il faut verifier que l'Id du userbm n'est pas null
         * et ensuite qu'il identifie reellement un userbm
         */
        if(pcopDto.getPoscoUserbmDto().getId() == null){
            log.error("The id of the userbm associate with the operation is null and then anything can't be done");
            throw new InvalidEntityException("L'id du userbm associe a l'operation etant null rien ne peut etre fait ",
                    ErrorCode.POSCASHOPERATION_NOT_VALID);
        }
        //Ici cela veut dire que l'id est la mais est ce que ca identifie alors un UserBM?
        Optional<UserBM> optionalUserBM = userBMRepository.findUserBMById(
                pcopDto.getPoscoUserbmDto().getId());
        if(!optionalUserBM.isPresent()){
            log.error("There is no userbm in the DB with the precised Id");
            throw new EntityNotFoundException("Aucun UserBM n'existe en BD avec l'Id precise ",
                    ErrorCode.USERBM_NOT_FOUND);
        }

        /****************************************************************************
         * Il faut se rassurer que le type d'operation est soit credit soit debit
         * soit changement (permutation)
         */
        if(!pcopDto.getPoscoOperationDto().getOpType().equals(OperationType.Withdrawal) &&
                !pcopDto.getPoscoOperationDto().getOpType().equals(OperationType.Change) &&
                !pcopDto.getPoscoOperationDto().getOpType().equals(OperationType.Credit)){
            log.error("Operation Type not recognize by the system");
            throw new InvalidValueException("La valeur du type d'operation envoye n'est pas reconnu par le system " +
                    ""+pcopDto.getPoscoOperationDto().getOpType());
        }

        /*****************************************************************
         * Il faut verifier que l'Id du PosCashOperation n'est pas null
         * et ensuite qu'il identifie reellement un compte cash
         */
        if(pcopDto.getId() == null){
            log.error("The id of the operation is null and then anything can't be done");
            throw new InvalidEntityException("L'id de l'operation etant null rien ne peut etre fait ",
                    ErrorCode.POSCASHOPERATION_NOT_VALID);
        }
        //Ici cela veut dire que l'id est la mais est ce que ca identifie alors un PosCashAccountOperation?
        Optional<PosCashOperation> optionalPosCashOperation = posCashOperationRepository.findPosCashOperationById(
                pcopDto.getId());
        if(!optionalPosCashOperation.isPresent()){
            log.error("There is no PosCashAccountOperation in the DB with the precised Id");
            throw new EntityNotFoundException("Aucun PosCashAccountOperation n'existe en BD avec l'Id precise ",
                    ErrorCode.POSCASHACCOUNT_NOT_FOUND);
        }

        ///////Ici on peut donc recuperer poscapsuleaccount a modifier
        PosCashOperation posCashOperationToUpdate = optionalPosCashOperation.get();
        //On fait donc les modif de la quantite en mouvement et de l'operation(dateop, objet, description etc.)
        posCashOperationToUpdate.setPoscoAmountinmvt(pcopDto.getPoscoAmountinmvt());
        posCashOperationToUpdate.setPoscoOperation(OperationDto.toEntity(pcopDto.getPoscoOperationDto()));

        log.info("After all verification, the operation can be updated normally");

        //PointofsaleDto.fromEntity(posRepository.save(posToUpdate))

        return PosCashOperationDto.fromEntity(posCashOperationRepository.save(posCashOperationToUpdate));
    }

    @Override
    public Boolean isPosCashOperationDeleteable(Long pcopId) {
        return true;
    }

    @Override
    public Boolean deletePosCashOperationById(Long pcopId) {
        if(pcopId == null){
            log.error("pcopId is null");
            throw new NullArgumentException("le pcopId passe en argument de la methode est null");
        }
        Optional<PosCashOperation> optionalPosCashOperation = posCashOperationRepository.findPosCashOperationById(pcopId);
        if(optionalPosCashOperation.isPresent()){
            if(isPosCashOperationDeleteable(pcopId)){
                posCashOperationRepository.delete(optionalPosCashOperation.get());
                return true;
            }
        }
        throw new EntityNotFoundException("Aucune entite n'exise avec l'id passe en argument ",
                ErrorCode.POSCASHOPERATION_NOT_FOUND);
    }

    public Boolean isPosCashOperationExistWithId(Long opId){
        if(opId == null){
            log.error("opId is null");
            throw new NullArgumentException("le opId passe en argument de la methode est null");
        }
        Optional<PosCashOperation> optionalPosCashOperation = posCashOperationRepository.findPosCashOperationById(opId);
        return optionalPosCashOperation.isPresent()?true:false;
    }

    @Override
    public PosCashOperationDto findPosCashOperationById(Long pcopId) {
        if(pcopId == null){
            log.error("pcopId is null");
            throw new NullArgumentException("le pcopId passe en argument de la methode est null");
        }
        Optional<PosCashOperation> optionalPosCashOperation = posCashOperationRepository.findPosCashOperationById(pcopId);

        if(!optionalPosCashOperation.isPresent()){
            throw new EntityNotFoundException("Aucune PosCashOperation avec l'id "+pcopId
                    +" n'a été trouve dans la BDD", ErrorCode.POSCASHOPERATION_NOT_FOUND);
        }

        return PosCashOperationDto.fromEntity(optionalPosCashOperation.get());
    }

    @Override
    public List<PosCashOperationDto> findAllPosCashOperation(Long pcaId) {
        List<PosCashOperation> listofPosCashOperation = posCashOperationRepository.findAllPosCashOperation(pcaId);
        return listofPosCashOperation.stream().map(PosCashOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<PosCashOperationDto> findPagePosCashOperation(Long pcaId, int pagenum, int pagesize) {
        Page<PosCashOperation> pageofPosCashOperation = posCashOperationRepository.findAllPosCashOperation(pcaId,
                PageRequest.of(pagenum, pagesize));
        return pageofPosCashOperation.map(PosCashOperationDto::fromEntity);
    }

    @Override
    public List<PosCashOperationDto> findAllPosCashOperationBetween(Long pcaId, Instant startDate, Instant endDate) {
        List<PosCashOperation> listofPosCashOperationBetween =
                posCashOperationRepository.findAllPosCashOperationBetween(pcaId, startDate, endDate);
        return listofPosCashOperationBetween.stream().map(PosCashOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<PosCashOperationDto> findPagePosCashOperationBetween(Long pcaId, Instant startDate,
                                                                    Instant endDate, int pagenum, int pagesize) {
        Page<PosCashOperation> pageofPosCashOperation = posCashOperationRepository.findAllPosCashOperationBetween(pcaId,
                startDate, endDate, PageRequest.of(pagenum, pagesize));
        return pageofPosCashOperation.map(PosCashOperationDto::fromEntity);
    }

    @Override
    public List<PosCashOperationDto> findAllPosCashOperationofTypeBetween(Long pcaId, OperationType op_type,
                                                                    Instant startDate, Instant endDate) {
        List<PosCashOperation> listofPosCashOperationoftypeBetween =
                posCashOperationRepository.findAllPosCashOperationOfTypeBetween(pcaId, op_type, startDate, endDate);
        return listofPosCashOperationoftypeBetween.stream().map(PosCashOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<PosCashOperationDto> findPagePosCashOperationofTypeBetween(Long pcaId, OperationType op_type,
                                                                    Instant startDate, Instant endDate,
                                                                    int pagenum, int pagesize) {
        Page<PosCashOperation> pageofPosCashOperation = posCashOperationRepository.findAllPosCashOperationOfTypeBetween(pcaId,
                op_type, startDate, endDate, PageRequest.of(pagenum, pagesize));
        return pageofPosCashOperation.map(PosCashOperationDto::fromEntity);
    }
}
