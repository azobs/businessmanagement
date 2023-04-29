package com.c2psi.businessmanagement.services.contractsImpl.stock.provider;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.pos.pos.OperationDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PosCapsuleOperationDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCapsuleOperationDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.PosCapsuleOperation;
import com.c2psi.businessmanagement.models.ProviderCapsuleOperation;
import com.c2psi.businessmanagement.models.UserBM;
import com.c2psi.businessmanagement.repositories.pos.userbm.UserBMRepository;
import com.c2psi.businessmanagement.repositories.stock.provider.ProviderCapsuleOperationRepository;
import com.c2psi.businessmanagement.services.contracts.stock.provider.ProviderCapsuleOperationService;
import com.c2psi.businessmanagement.validators.stock.provider.ProviderCapsuleOperationValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value="ProviderCapsuleOperationServiceV1")
@Slf4j
@Transactional
public class ProviderCapsuleOperationServiceImpl implements ProviderCapsuleOperationService {

    private ProviderCapsuleOperationRepository providerCapsuleOperationRepository;
    private UserBMRepository userBMRepository;

    @Autowired
    public ProviderCapsuleOperationServiceImpl(ProviderCapsuleOperationRepository providerCapsuleOperationRepository,
                                               UserBMRepository userBMRepository) {
        this.providerCapsuleOperationRepository = providerCapsuleOperationRepository;
        this.userBMRepository = userBMRepository;
    }

    @Override
    public ProviderCapsuleOperationDto findProviderCapsuleOperationById(Long proCapsOpId) {
        if(proCapsOpId == null){
            log.error("The proCapsOpId as argument is null");
            throw new NullArgumentException("L'argument proCapsOpId indique est null");
        }
        Optional<ProviderCapsuleOperation> optionalProviderCapsuleOperation = providerCapsuleOperationRepository.
                findProviderCapsuleOperationById(proCapsOpId);
        if(!optionalProviderCapsuleOperation.isPresent()){
            log.error("There is no ProviderCapsuleOperation in the DB with the id {} passed as argument", proCapsOpId);
            throw new EntityNotFoundException("Aucun ProviderCapsuleOperation n'existe avec l'id "+proCapsOpId+" passe",
                    ErrorCode.PROVIDERCAPSULEOPERATION_NOT_FOUND);
        }
        return ProviderCapsuleOperationDto.fromEntity(optionalProviderCapsuleOperation.get());
    }

    @Override
    public ProviderCapsuleOperationDto updateProviderCapsuleOperation(ProviderCapsuleOperationDto procapopDto) {

        /********************************************************************
         * Il faut d'abord valider le parametre pris en parametre
         */
        List<String> errors = ProviderCapsuleOperationValidator.validate(procapopDto);
        if(!errors.isEmpty()){
            log.error("Entity poscsopDto not valid {}", procapopDto);
            throw new InvalidEntityException("Le procapopDto passé en argument n'est pas valide: "+errors,
                    ErrorCode.PROVIDERCAPSULEOPERATION_NOT_VALID, errors);
        }

        /*****************************************************************
         * Il faut verifier que l'Id du userbm n'est pas null
         * et ensuite qu'il identifie reellement un userbm
         */
        if(procapopDto.getProcsoUserbmDto().getId() == null){
            log.error("The id of the userbm associate with the operation is null and then anything can't be done");
            throw new InvalidEntityException("L'id du userbm associe a l'operation etant null rien ne peut etre fait ",
                    ErrorCode.PROVIDERCAPSULEOPERATION_NOT_VALID);
        }
        //Ici cela veut dire que l'id est la mais est ce que ca identifie alors un PosCashAccountOperation?
        Optional<UserBM> optionalUserBM = userBMRepository.findUserBMById(
                procapopDto.getProcsoUserbmDto().getId());
        if(!optionalUserBM.isPresent()){
            log.error("There is no userbm in the DB with the precised Id");
            throw new EntityNotFoundException("Aucun UserBM n'existe en BD avec l'Id precise ",
                    ErrorCode.USERBM_NOT_FOUND);
        }

        /****************************************************************************
         * Il faut se rassurer que le type d'operation est soit credit soit debit
         * soit changement (permutation)
         */
        if(!procapopDto.getProscoOperationDto().getOpType().equals(OperationType.Withdrawal) &&
                !procapopDto.getProscoOperationDto().getOpType().equals(OperationType.Change) &&
                !procapopDto.getProscoOperationDto().getOpType().equals(OperationType.Credit)){
            log.error("Operation Type not recognize by the system");
            throw new InvalidValueException("La valeur du type d'operation envoye n'est pas reconnu par le system");
        }

        /*******************************************************************
         * Il faut verifier que l'Id du ProviderCapsuleOperation n'est pas null
         * et ensuite qu'il identifie reellement un compte capsule
         */
        if(procapopDto.getId() == null){
            log.error("The id of the operation is null and then anything can't be done");
            throw new InvalidEntityException("L'id de l'operation etant null rien ne peut etre fait ",
                    ErrorCode.PROVIDERCAPSULEOPERATION_NOT_VALID);
        }
        //Ici cela veut dire que l'id est la mais est ce que ca identifie alors un ProviderCapsuleAccountOperation?
        Optional<ProviderCapsuleOperation> optionalProviderCapsuleOperation = providerCapsuleOperationRepository.
                findProviderCapsuleOperationById(procapopDto.getId());
        if(!optionalProviderCapsuleOperation.isPresent()){
            log.error("There is no ProviderCapsuleOperation in the DB with the precised Id");
            throw new EntityNotFoundException("Aucun ProviderCapsuleOperation n'existe en BD avec l'Id precise ",
                    ErrorCode.PROVIDERCAPSULEACCOUNT_NOT_FOUND);
        }

        ///////Ici on peut donc recuperer providercapsuleaccount a modifier
        ProviderCapsuleOperation providerCapsuleOperationToUpdate = optionalProviderCapsuleOperation.get();
        //On fait donc les modif de la quantite en mouvement et de l'operation(dateop, objet, description etc.)
        providerCapsuleOperationToUpdate.setProcsoNumberinmvt(procapopDto.getProcsoNumberinmvt());
        providerCapsuleOperationToUpdate.setProscoOperation(OperationDto.toEntity(procapopDto.getProscoOperationDto()));


        log.info("After all verification, the operation can be updated normally");

        return ProviderCapsuleOperationDto.fromEntity(providerCapsuleOperationRepository.save(
                providerCapsuleOperationToUpdate));
    }

    @Override
    public Boolean isProviderCapsuleOperationDeleteable(Long procapopId) {
        return true;
    }

    @Override
    public Boolean deleteProviderCapsuleOperationById(Long procapopId) {
        if(procapopId == null){
            log.error("procapopId is null");
            throw new NullArgumentException("Le procapopId passe en argument de la methode est null");
        }
        Optional<ProviderCapsuleOperation> optionalProviderCapsuleOperation = providerCapsuleOperationRepository.
                findProviderCapsuleOperationById(procapopId);
        if(optionalProviderCapsuleOperation.isPresent()){
            if(isProviderCapsuleOperationDeleteable(procapopId)){
                providerCapsuleOperationRepository.delete(optionalProviderCapsuleOperation.get());
                return true;
            }
        }
        throw new EntityNotFoundException("Aucune entite n'existe avec l'ID passe en argument ",
                ErrorCode.PROVIDERCAPSULEOPERATION_NOT_FOUND);
    }

    @Override
    public List<ProviderCapsuleOperationDto> findAllProviderCapsuleOperation(Long procapaccId) {
        List<ProviderCapsuleOperation> providerCapsuleOperationList = providerCapsuleOperationRepository.
                findAllProviderCapsuleOperation(procapaccId);
        return providerCapsuleOperationList.stream().map(ProviderCapsuleOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<ProviderCapsuleOperationDto> findPageProviderCapsuleOperation(Long procapaccId, int pagenum,
                                                                              int pagesize) {
        Page<ProviderCapsuleOperation> providerCapsuleOperationPage = providerCapsuleOperationRepository.
                findPageProviderCapsuleOperation(procapaccId, PageRequest.of(pagenum, pagesize));

        return providerCapsuleOperationPage.map(ProviderCapsuleOperationDto::fromEntity);
    }

    @Override
    public List<ProviderCapsuleOperationDto> findAllProviderCapsuleOperationofType(Long procapaccId, OperationType opType) {
        List<ProviderCapsuleOperation> providerCapsuleOperationList = providerCapsuleOperationRepository.findAllProviderCapsuleOperationofType(
                procapaccId, opType);

        return providerCapsuleOperationList.stream().map(ProviderCapsuleOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<ProviderCapsuleOperationDto> findPageProviderCapsuleOperationofType(Long procapaccId, OperationType opType, int pagenum, int pagesize) {
        Page<ProviderCapsuleOperation> providerCapsuleOperationPage = providerCapsuleOperationRepository.
                findPageProviderCapsuleOperationofType(
                procapaccId, opType, PageRequest.of(pagenum, pagesize));

        return providerCapsuleOperationPage.map(ProviderCapsuleOperationDto::fromEntity);
    }

    @Override
    public List<ProviderCapsuleOperationDto> findAllProviderCapsuleOperationBetween(Long procapsaccId,
                                                                                    Instant startDate, Instant endDate) {
        List<ProviderCapsuleOperation> providerCapsuleOperationListBetween = providerCapsuleOperationRepository.
                findAllProviderCapsuleOperationBetween(procapsaccId, startDate, endDate);
        return providerCapsuleOperationListBetween.stream().map(ProviderCapsuleOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<ProviderCapsuleOperationDto> findPageProviderCapsuleOperationBetween(Long procapsaccId, Instant startDate,
                                                                                     Instant endDate,
                                                                                     int pagenum, int pagesize) {
        Page<ProviderCapsuleOperation> providerCapsuleOperationPageBetween = providerCapsuleOperationRepository.
                findPageProviderCapsuleOperationBetween(procapsaccId, startDate, endDate, PageRequest.of(pagenum, pagesize));
        return providerCapsuleOperationPageBetween.map(ProviderCapsuleOperationDto::fromEntity);
    }

    @Override
    public List<ProviderCapsuleOperationDto> findAllProviderCapsuleOperationBetween(Long procapsaccId, OperationType opType,
                                                                                    Instant startDate, Instant endDate) {
        List<ProviderCapsuleOperation> providerCapsuleOperationListoftypeBetween = providerCapsuleOperationRepository.
                findAllProviderCapsuleOperationofTypeBetween(procapsaccId, opType, startDate, endDate);
        return providerCapsuleOperationListoftypeBetween.stream().map(ProviderCapsuleOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<ProviderCapsuleOperationDto> findPageProviderCapsuleOperationBetween(Long procapsaccId, OperationType opType,
                                                                                     Instant startDate, Instant endDate,
                                                                                     int pagenum, int pagesize) {
        Page<ProviderCapsuleOperation> providerCapsuleOperationPageoftypeBetween = providerCapsuleOperationRepository.
                findPageProviderCapsuleOperationofTypeBetween(procapsaccId, opType, startDate, endDate, PageRequest.of(pagenum, pagesize));
        return providerCapsuleOperationPageoftypeBetween.map(ProviderCapsuleOperationDto::fromEntity);
    }
}
