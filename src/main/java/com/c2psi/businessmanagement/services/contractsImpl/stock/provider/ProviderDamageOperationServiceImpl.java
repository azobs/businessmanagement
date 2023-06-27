package com.c2psi.businessmanagement.services.contractsImpl.stock.provider;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.pos.pos.OperationDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDamageOperationDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.ProviderDamageOperation;
import com.c2psi.businessmanagement.models.UserBM;
import com.c2psi.businessmanagement.repositories.pos.userbm.UserBMRepository;
import com.c2psi.businessmanagement.repositories.stock.provider.ProviderDamageOperationRepository;
import com.c2psi.businessmanagement.services.contracts.stock.provider.ProviderDamageOperationService;
import com.c2psi.businessmanagement.validators.stock.provider.ProviderDamageOperationValidator;
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

@Service(value="ProviderDamageOperationServiceV1")
@Slf4j
@Transactional
public class ProviderDamageOperationServiceImpl implements ProviderDamageOperationService {

    private ProviderDamageOperationRepository providerDamageOperationRepository;
    private UserBMRepository userBMRepository;

    @Autowired
    public ProviderDamageOperationServiceImpl(ProviderDamageOperationRepository providerDamageOperationRepository,
                                              UserBMRepository userBMRepository) {
        this.providerDamageOperationRepository = providerDamageOperationRepository;
        this.userBMRepository = userBMRepository;
    }

    @Override
    public ProviderDamageOperationDto updateProviderDamageOperation(ProviderDamageOperationDto prodamopDto) {
        /********************************************************************
         * Il faut d'abord valider le parametre pris en parametre
         */
        List<String> errors = ProviderDamageOperationValidator.validate(prodamopDto);
        if(!errors.isEmpty()){
            log.error("Entity prodamopDto not valid {}", prodamopDto);
            throw new InvalidEntityException("Le prodamopDto passé en argument n'est pas valide: "+errors,
                    ErrorCode.PROVIDERDAMAGEOPERATION_NOT_VALID, errors);
        }

        /*****************************************************************
         * Il faut verifier que l'Id du userbm n'est pas null
         * et ensuite qu'il identifie reellement un userbm
         */
        if(prodamopDto.getProdoUserbmDto().getId() == null){
            log.error("The id of the userbm associate with the operation is null and then anything can't be done");
            throw new InvalidEntityException("L'id du userbm associe a l'operation etant null rien ne peut etre fait ",
                    ErrorCode.POSCAPSULEOPERATION_NOT_VALID);
        }
        //Ici cela veut dire que l'id est la mais est ce que ca identifie alors un PosCashAccountOperation?
        Optional<UserBM> optionalUserBM = userBMRepository.findUserBMById(
                prodamopDto.getProdoUserbmDto().getId());
        if(!optionalUserBM.isPresent()){
            log.error("There is no userbm in the DB with the precised Id");
            throw new EntityNotFoundException("Aucun UserBM n'existe en BD avec l'Id precise ",
                    ErrorCode.USERBM_NOT_FOUND);
        }

        /****************************************************************************
         * Il faut se rassurer que le type d'operation est soit credit soit debit
         * soit changement (permutation)
         */
        if(!prodamopDto.getProdoOperationDto().getOpType().equals(OperationType.Withdrawal) &&
                !prodamopDto.getProdoOperationDto().getOpType().equals(OperationType.Change) &&
                !prodamopDto.getProdoOperationDto().getOpType().equals(OperationType.Credit)){
            log.error("Operation Type not recognize by the system");
            throw new InvalidValueException("La valeur du type d'operation envoye n'est pas reconnu par le system");
        }

        /*******************************************************************
         * Il faut verifier que l'Id du ProviderDamageOperation n'est pas null
         * et ensuite qu'il identifie reellement un compte capsule
         */
        if(prodamopDto.getId() == null){
            log.error("The id of the operation is null and then anything can't be done");
            throw new InvalidEntityException("L'id de l'operation etant null rien ne peut etre fait ",
                    ErrorCode.PROVIDERDAMAGEOPERATION_NOT_VALID);
        }
        //Ici cela veut dire que l'id est la mais est ce que ca identifie alors un ProviderDamageAccountOperation?
        Optional<ProviderDamageOperation> optionalProviderDamageOperation = providerDamageOperationRepository.
                findProviderDamageOperationById(prodamopDto.getId());
        if(!optionalProviderDamageOperation.isPresent()){
            log.error("There is no ProviderDamageOperation in the DB with the precised Id");
            throw new EntityNotFoundException("Aucun ProviderDamageOperation n'existe en BD avec l'Id precise ",
                    ErrorCode.PROVIDERDAMAGEACCOUNT_NOT_FOUND);
        }

        ///////Ici on peut donc recuperer providerdamageaccount a modifier
        ProviderDamageOperation providerDamageOperationToUpdate = optionalProviderDamageOperation.get();
        //On fait donc les modif de la quantite en mouvement et de l'operation(dateop, objet, description etc.)
        providerDamageOperationToUpdate.setProdoNumberinmvt(prodamopDto.getProdoNumberinmvt());
        providerDamageOperationToUpdate.setProdoOperation(OperationDto.toEntity(prodamopDto.getProdoOperationDto()));

        log.info("After all verification, the operation can be updated normally");

        return ProviderDamageOperationDto.fromEntity(providerDamageOperationRepository.save(
                providerDamageOperationToUpdate));
    }

    @Override
    public Boolean isProviderDamageOperationDeleteable(Long prodamopId) {
        return true;
    }

    @Override
    public Boolean deleteProviderDamageOperationById(Long prodamopId) {
        if(prodamopId == null){
            log.error("prodamopId is null");
            throw new NullArgumentException("Le prodamopId passe en argument de la methode est null");
        }
        Optional<ProviderDamageOperation> optionalProviderDamageOperation = providerDamageOperationRepository.
                findProviderDamageOperationById(prodamopId);
        if(optionalProviderDamageOperation.isPresent()){
            if(isProviderDamageOperationDeleteable(prodamopId)){
                providerDamageOperationRepository.delete(optionalProviderDamageOperation.get());
                return true;
            }
        }
        throw new EntityNotFoundException("Aucune entite n'existe avec l'ID passe en argument ",
                ErrorCode.PROVIDERCAPSULEOPERATION_NOT_FOUND);
    }

    @Override
    public List<ProviderDamageOperationDto> findAllProviderDamageOperation(Long prodamaccId) {
        List<ProviderDamageOperation> providerDamageOperationList = providerDamageOperationRepository.
                findAllProviderDamageOperation(prodamaccId);
        return providerDamageOperationList.stream().map(ProviderDamageOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<ProviderDamageOperationDto> findPageProviderDamageOperation(Long prodamaccId, int pagenum, int pagesize) {
        Page<ProviderDamageOperation> providerDamageOperationPage = providerDamageOperationRepository.
                findPageProviderDamageOperation(prodamaccId, PageRequest.of(pagenum, pagesize));

        return providerDamageOperationPage.map(ProviderDamageOperationDto::fromEntity);
    }

    @Override
    public List<ProviderDamageOperationDto> findAllProviderDamageOperationofType(Long prodamaccId, OperationType opType) {
        List<ProviderDamageOperation> providerDamageOperationList = providerDamageOperationRepository.findAllProviderDamageOperationofType(
                prodamaccId, opType);

        return providerDamageOperationList.stream().map(ProviderDamageOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<ProviderDamageOperationDto> findPageProviderDamageOperationofType(Long prodamaccId, OperationType opType,
                                                                                  int pagenum, int pagesize) {
        Page<ProviderDamageOperation> providerDamageOperationPage = providerDamageOperationRepository.
                findPageProviderDamageOperationofType(
                        prodamaccId, opType, PageRequest.of(pagenum, pagesize));

        return providerDamageOperationPage.map(ProviderDamageOperationDto::fromEntity);
    }

    @Override
    public List<ProviderDamageOperationDto> findAllProviderDamageOperationBetween(Long prodamaccId, Instant startDate,
                                                                                  Instant endDate) {
        List<ProviderDamageOperation> providerDamageOperationListBetween = providerDamageOperationRepository.
                findAllProviderDamageOperationBetween(prodamaccId, startDate, endDate);
        return providerDamageOperationListBetween.stream().map(ProviderDamageOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<ProviderDamageOperationDto> findPageProviderDamageOperationBetween(Long prodamaccId, Instant startDate,
                                                                                   Instant endDate, int pagenum, int pagesize) {
        Page<ProviderDamageOperation> providerDamageOperationPageBetween = providerDamageOperationRepository.
                findPageProviderDamageOperationBetween(prodamaccId, startDate, endDate, PageRequest.of(pagenum, pagesize));
        return providerDamageOperationPageBetween.map(ProviderDamageOperationDto::fromEntity);
    }

    @Override
    public List<ProviderDamageOperationDto> findAllProviderDamageOperationofTypeBetween(Long prodamaccId, OperationType opType, Instant startDate, Instant endDate) {
        List<ProviderDamageOperation> providerDamageOperationListoftypeBetween = providerDamageOperationRepository.
                findAllProviderDamageOperationofTypeBetween(prodamaccId, opType, startDate, endDate);
        return providerDamageOperationListoftypeBetween.stream().map(ProviderDamageOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<ProviderDamageOperationDto> findPageProviderDamageOperationofTypeBetween(Long prodamaccId, OperationType opType,
                                                                                   Instant startDate, Instant endDate, int pagenum, int pagesize) {
        Page<ProviderDamageOperation> providerDamageOperationPageoftypeBetween = providerDamageOperationRepository.
                findPageProviderDamageOperationofTypeBetween(prodamaccId, opType, startDate, endDate, PageRequest.of(pagenum, pagesize));
        return providerDamageOperationPageoftypeBetween.map(ProviderDamageOperationDto::fromEntity);
    }
}
