package com.c2psi.businessmanagement.services.contractsImpl.pos.pos;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCashAccountDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCashOperationDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.exceptions.EntityNotFoundException;
import com.c2psi.businessmanagement.exceptions.ErrorCode;
import com.c2psi.businessmanagement.exceptions.InvalidEntityException;
import com.c2psi.businessmanagement.exceptions.NullArgumentException;
import com.c2psi.businessmanagement.models.Operation;
import com.c2psi.businessmanagement.models.PosCashOperation;
import com.c2psi.businessmanagement.models.UserBM;
import com.c2psi.businessmanagement.repositories.pos.pos.PosCashOperationRepository;
import com.c2psi.businessmanagement.services.contracts.pos.pos.PosCashOperationService;
import com.c2psi.businessmanagement.validators.pos.pos.PosCashAccountValidator;
import com.c2psi.businessmanagement.validators.pos.pos.PosCashOperationValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value="PosCashOperationServiceV1")
@Slf4j
@Transactional
public class PosCashOperationServiceImpl implements PosCashOperationService {
    private PosCashOperationRepository posCashOperationRepository;

    @Autowired
    public PosCashOperationServiceImpl(PosCashOperationRepository posCashOperationRepository) {
        this.posCashOperationRepository = posCashOperationRepository;
    }

    @Override
    public PosCashOperationDto savePosCashOperation(PosCashOperationDto pcopDto) {
        List<String> errors = PosCashOperationValidator.validate(pcopDto);
        if(!errors.isEmpty()){
            log.error("Entity pcopDto not valid {}", pcopDto);
            throw new InvalidEntityException("Le pcopDto passé en argument n'est pas valide: "+errors,
                    ErrorCode.POSCASHOPERATION_NOT_VALID, errors);
        }

        return PosCashOperationDto.fromEntity(
                posCashOperationRepository.save(
                        PosCashOperationDto.toEntity(pcopDto)
                )
        );
    }

    @Override
    public Boolean isPosCashOperationDeleteable(Long pcopId) {
        return null;
    }

    @Override
    public Boolean deletePosCashOperationById(Long pcopId) {
        if(pcopId == null){
            log.error("pcopId is null");
            throw new NullArgumentException("le pcopId passe en argument de la methode est null");
        }
        Optional<PosCashOperation> optionalPosCashOperation = posCashOperationRepository.findPosCashOperationById(pcopId);
        if(optionalPosCashOperation.isPresent()){
            posCashOperationRepository.delete(optionalPosCashOperation.get());
            return true;
        }
        return false;
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

        return Optional.of(PosCashOperationDto.fromEntity(optionalPosCashOperation.get())).orElseThrow(()->
                new EntityNotFoundException("Aucune PosCashOperation avec l'id "+pcopId
                        +" n'a été trouve dans la BDD", ErrorCode.POSCASHOPERATION_NOT_FOUND));
    }

    @Override
    public List<PosCashOperationDto> findAllPosCashOperation(Long pcaId) {
        List<PosCashOperation> listofPosCashOperation = posCashOperationRepository.findAllPosCashOperation(pcaId);
        return listofPosCashOperation.stream().map(PosCashOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<PosCashOperationDto> findAllPosCashOperation(Long pcaId, int pagenum, int pagesize) {
        Page<PosCashOperation> pageofPosCashOperation = posCashOperationRepository.findAllPosCashOperation(pcaId,
                PageRequest.of(pagenum, pagesize));
        return pageofPosCashOperation.map(PosCashOperationDto::fromEntity);
    }

    @Override
    public List<PosCashOperationDto> findAllPosCashOperationBetween(Long pcaId, Date startDate, Date endDate) {
        List<PosCashOperation> listofPosCashOperationBetween =
                posCashOperationRepository.findAllPosCashOperationBetween(pcaId, startDate, endDate);
        return listofPosCashOperationBetween.stream().map(PosCashOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<PosCashOperationDto> findAllPosCashOperationBetween(Long pcaId, Date startDate,
                                                                    Date endDate, int pagenum, int pagesize) {
        Page<PosCashOperation> pageofPosCashOperation = posCashOperationRepository.findAllPosCashOperationBetween(pcaId,
                startDate, endDate, PageRequest.of(pagenum, pagesize));
        return pageofPosCashOperation.map(PosCashOperationDto::fromEntity);
    }

    @Override
    public List<PosCashOperationDto> findAllPosCashOperationBetween(Long pcaId, OperationType op_type,
                                                                    Date startDate, Date endDate) {
        List<PosCashOperation> listofPosCashOperationoftypeBetween =
                posCashOperationRepository.findAllPosCashOperationOfTypeBetween(pcaId, op_type, startDate, endDate);
        return listofPosCashOperationoftypeBetween.stream().map(PosCashOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<PosCashOperationDto> findAllPosCashOperationBetween(Long pcaId, OperationType op_type,
                                                                    Date startDate, Date endDate,
                                                                    int pagenum, int pagesize) {
        Page<PosCashOperation> pageofPosCashOperation = posCashOperationRepository.findAllPosCashOperationOfTypeBetween(pcaId,
                op_type, startDate, endDate, PageRequest.of(pagenum, pagesize));
        return pageofPosCashOperation.map(PosCashOperationDto::fromEntity);
    }
}
