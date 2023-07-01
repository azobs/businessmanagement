package com.c2psi.businessmanagement.services.contractsImpl.stock.provider;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.pos.pos.OperationDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCashOperationDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.ProviderCashOperation;
import com.c2psi.businessmanagement.models.UserBM;
import com.c2psi.businessmanagement.repositories.pos.userbm.UserBMRepository;
import com.c2psi.businessmanagement.repositories.stock.provider.ProviderCashOperationRepository;
import com.c2psi.businessmanagement.services.contracts.stock.provider.ProviderCashOperationService;
import com.c2psi.businessmanagement.validators.stock.provider.ProviderCashOperationValidator;
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

@Service(value="ProviderCashOperationServiceV1")
@Slf4j
@Transactional
public class ProviderCashOperationServiceImpl implements ProviderCashOperationService {

    private ProviderCashOperationRepository providerCashOperationRepository;
    private UserBMRepository userBMRepository;

    @Autowired
    public ProviderCashOperationServiceImpl(ProviderCashOperationRepository providerCashOperationRepository,
                                            UserBMRepository userBMRepository) {
        this.providerCashOperationRepository = providerCashOperationRepository;
        this.userBMRepository = userBMRepository;
    }

    @Override
    public ProviderCashOperationDto updateProviderCashOperation(ProviderCashOperationDto procopDto) {
        /**************************************************
         * Verification de l'argument passe en parametre
         */
        List<String> errors = ProviderCashOperationValidator.validate(procopDto);
        if(!errors.isEmpty()){
            log.error("Entity procopDto not valid {}", procopDto);
            throw new InvalidEntityException("Le procopDto passé en argument n'est pas valide: "+errors,
                    ErrorCode.PROVIDERCASHOPERATION_NOT_VALID, errors);
        }

        /*****************************************************************
         * Il faut verifier que l'Id du userbm n'est pas null
         * et ensuite qu'il identifie reellement un userbm
         */
        if(procopDto.getPcoUserbmDto().getId() == null){
            log.error("The id of the userbm associate with the operation is null and then anything can't be done");
            throw new InvalidEntityException("L'id du userbm associe a l'operation etant null rien ne peut etre fait ",
                    ErrorCode.PROVIDERCASHOPERATION_NOT_VALID);
        }
        //Ici cela veut dire que l'id est la mais est ce que ca identifie alors un UserBM?
        Optional<UserBM> optionalUserBM = userBMRepository.findUserBMById(
                procopDto.getPcoUserbmDto().getId());
        if(!optionalUserBM.isPresent()){
            log.error("There is no userbm in the DB with the precised Id");
            throw new EntityNotFoundException("Aucun UserBM n'existe en BD avec l'Id precise ",
                    ErrorCode.USERBM_NOT_FOUND);
        }

        /****************************************************************************
         * Il faut se rassurer que le type d'operation est soit credit soit debit
         * soit changement (permutation)
         */
        if(!procopDto.getPcoOperationDto().getOpType().equals(OperationType.Withdrawal) ||
                !procopDto.getPcoOperationDto().getOpType().equals(OperationType.Change) ||
                !procopDto.getPcoOperationDto().getOpType().equals(OperationType.Credit)){
            log.error("Operation Type not recognize by the system");
            throw new InvalidValueException("La valeur du type d'operation envoye n'est pas reconnu par le system");
        }

        /*****************************************************************
         * Il faut verifier que l'Id du ProviderCashOperation n'est pas null
         * et ensuite qu'il identifie reellement un compte cash
         */
        if(procopDto.getId() == null){
            log.error("The id of the operation is null and then anything can't be done");
            throw new InvalidEntityException("L'id de l'operation etant null rien ne peut etre fait ",
                    ErrorCode.PROVIDERCASHOPERATION_NOT_VALID);
        }
        //Ici cela veut dire que l'id est la mais est ce que ca identifie alors un ProviderCashAccountOperation?
        Optional<ProviderCashOperation> optionalProviderCashOperation = providerCashOperationRepository.findProviderCashOperationById(
                procopDto.getId());
        if(!optionalProviderCashOperation.isPresent()){
            log.error("There is no ProviderCashAccountOperation in the DB with the precised Id");
            throw new EntityNotFoundException("Aucun ProviderCashAccountOperation n'existe en BD avec l'Id precise ",
                    ErrorCode.PROVIDERCASHOPERATION_NOT_FOUND);
        }

        ///////Ici on peut donc recuperer providerdamageaccount a modifier
        ProviderCashOperation providerCashOperationToUpdate = optionalProviderCashOperation.get();
        //On fait donc les modif de la quantite en mouvement et de l'operation(dateop, objet, description etc.)
        providerCashOperationToUpdate.setPcoAmountinmvt(procopDto.getPcoAmountinmvt());
        providerCashOperationToUpdate.setPcoOperation(OperationDto.toEntity(procopDto.getPcoOperationDto()));

        log.info("After all verification, the operation can be updated normally");

        return ProviderCashOperationDto.fromEntity(providerCashOperationRepository.save(providerCashOperationToUpdate));
    }

    @Override
    public Boolean isProviderCashOperationDeleatable(Long procopDto) {
        return true;
    }

    @Override
    public Boolean deleteProviderCashOperationById(Long procopId) {
        if(procopId == null){
            log.error("procopId is null");
            throw new NullArgumentException("le procopId passe en argument de la methode est null");
        }
        Optional<ProviderCashOperation> optionalProviderCashOperation = providerCashOperationRepository.findProviderCashOperationById(procopId);
        if(optionalProviderCashOperation.isPresent()){
            if(isProviderCashOperationDeleatable(procopId)){
                providerCashOperationRepository.delete(optionalProviderCashOperation.get());
                return true;
            }
        }
        throw new EntityNotFoundException("Aucune entite n'exise avec l'id passe en argument ",
                ErrorCode.PROVIDERCASHOPERATION_NOT_FOUND);
    }

    @Override
    public ProviderCashOperationDto findProviderCashOperationById(Long procopId) {
        if(procopId == null){
            log.error("procopId is null");
            throw new NullArgumentException("le procopId passe en argument de la methode est null");
        }
        Optional<ProviderCashOperation> optionalProviderCashOperation = providerCashOperationRepository.findProviderCashOperationById(procopId);

        if(!optionalProviderCashOperation.isPresent()){
            throw new EntityNotFoundException("Aucune ProviderCashOperation avec l'id "+procopId
                    +" n'a été trouve dans la BDD", ErrorCode.PROVIDERCASHOPERATION_NOT_FOUND);
        }

        return ProviderCashOperationDto.fromEntity(optionalProviderCashOperation.get());

    }

    @Override
    public List<ProviderCashOperationDto> findAllProviderCashOperation(Long procopId) {
        List<ProviderCashOperation> providerCashOperationList = providerCashOperationRepository.
                findAllProviderCashOperation(procopId);
        return providerCashOperationList.stream().map(ProviderCashOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<ProviderCashOperationDto> findPageProviderCashOperation(Long procopId, int pagenum, int pagesize) {
        Page<ProviderCashOperation> providerCashOperationPage = providerCashOperationRepository.
                findAllProviderCashOperation(procopId,
                PageRequest.of(pagenum, pagesize));
        return providerCashOperationPage.map(ProviderCashOperationDto::fromEntity);
    }

    @Override
    public List<ProviderCashOperationDto> findAllProviderCashOperationBetween(Long procopId, Instant startDate,
                                                                              Instant endDate) {
        List<ProviderCashOperation> providerCashOperationListBetween =
                providerCashOperationRepository.findAllProviderCashOperationBetween(procopId, startDate, endDate);
        return providerCashOperationListBetween.stream().map(ProviderCashOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<ProviderCashOperationDto> findPageProviderCashOperationBetween(Long procopId, Instant startDate,
                                                                               Instant endDate, int pagenum,
                                                                               int pagesize) {
        Page<ProviderCashOperation> providerCashOperationPageBetween = providerCashOperationRepository.findPageProviderCashOperationBetween(procopId,
                startDate, endDate, PageRequest.of(pagenum, pagesize));
        return providerCashOperationPageBetween.map(ProviderCashOperationDto::fromEntity);
    }

    @Override
    public List<ProviderCashOperationDto> findAllProviderCashOperationofTypeBetween(Long procopId, OperationType opType,
                                                                                    Instant startDate, Instant endDate) {
        List<ProviderCashOperation> providerCashOperationListBetween =
                providerCashOperationRepository.findAllProviderCashOperationOfTypeBetween(procopId, opType, startDate, endDate);
        return providerCashOperationListBetween.stream().map(ProviderCashOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<ProviderCashOperationDto> findPageProviderCashOperationofTypeBetween(Long procopId, OperationType opType,
                                                                                     Instant startDate, Instant endDate,
                                                                                     int pagenum, int pagesize) {
        Page<ProviderCashOperation> providerCashOperationPageBetween = providerCashOperationRepository.findPageProviderCashOperationOfTypeBetween(procopId,
                opType, startDate, endDate, PageRequest.of(pagenum, pagesize));
        return providerCashOperationPageBetween.map(ProviderCashOperationDto::fromEntity);
    }
}
