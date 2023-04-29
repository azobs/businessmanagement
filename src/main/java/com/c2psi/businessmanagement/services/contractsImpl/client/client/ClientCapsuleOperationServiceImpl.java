package com.c2psi.businessmanagement.services.contractsImpl.client.client;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.client.client.ClientCapsuleOperationDto;
import com.c2psi.businessmanagement.dtos.pos.pos.OperationDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderCapsuleOperationDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.ClientCapsuleOperation;
import com.c2psi.businessmanagement.models.ProviderCapsuleOperation;
import com.c2psi.businessmanagement.models.UserBM;
import com.c2psi.businessmanagement.repositories.client.client.ClientCapsuleOperationRepository;
import com.c2psi.businessmanagement.repositories.pos.userbm.UserBMRepository;
import com.c2psi.businessmanagement.services.contracts.client.client.ClientCapsuleOperationService;
import com.c2psi.businessmanagement.validators.client.client.ClientCapsuleOperationValidator;
import com.c2psi.businessmanagement.validators.stock.provider.ProviderCapsuleOperationValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value="ClientCapsuleOperationServiceV1")
@Slf4j
@Transactional
public class ClientCapsuleOperationServiceImpl implements ClientCapsuleOperationService {

    private ClientCapsuleOperationRepository clientCapsuleOperationRepository;
    private UserBMRepository userBMRepository;

    @Autowired
    public ClientCapsuleOperationServiceImpl(ClientCapsuleOperationRepository clientCapsuleOperationRepository,
                                             UserBMRepository userBMRepository) {
        this.clientCapsuleOperationRepository = clientCapsuleOperationRepository;
        this.userBMRepository = userBMRepository;
    }

    @Override
    public ClientCapsuleOperationDto updateClientCapsuleOperation(ClientCapsuleOperationDto ccopDto) {

        /********************************************************************
         * Il faut d'abord valider le parametre pris en parametre
         */
        List<String> errors = ClientCapsuleOperationValidator.validate(ccopDto);
        if(!errors.isEmpty()){
            log.error("Entity poscsopDto not valid {}", ccopDto);
            throw new InvalidEntityException("Le ccopDto passé en argument n'est pas valide: "+errors,
                    ErrorCode.CLIENTCAPSULEOPERATION_NOT_VALID, errors);
        }

        /*****************************************************************
         * Il faut verifier que l'Id du userbm n'est pas null
         * et ensuite qu'il identifie reellement un userbm
         */
        if(ccopDto.getCltcsoUserbmDto().getId() == null){
            log.error("The id of the userbm associate with the operation is null and then anything can't be done");
            throw new InvalidEntityException("L'id du userbm associe a l'operation etant null rien ne peut etre fait ",
                    ErrorCode.CLIENTCAPSULEOPERATION_NOT_VALID);
        }
        //Ici cela veut dire que l'id est la mais est ce que ca identifie alors un PosCashAccountOperation?
        Optional<UserBM> optionalUserBM = userBMRepository.findUserBMById(
                ccopDto.getCltcsoUserbmDto().getId());
        if(!optionalUserBM.isPresent()){
            log.error("There is no userbm in the DB with the precised Id");
            throw new EntityNotFoundException("Aucun UserBM n'existe en BD avec l'Id precise ",
                    ErrorCode.USERBM_NOT_FOUND);
        }

        /****************************************************************************
         * Il faut se rassurer que le type d'operation est soit credit soit debit
         * soit changement (permutation)
         */
        if(!ccopDto.getCltcsoOperationDto().getOpType().equals(OperationType.Withdrawal) &&
                !ccopDto.getCltcsoOperationDto().getOpType().equals(OperationType.Change) &&
                !ccopDto.getCltcsoOperationDto().getOpType().equals(OperationType.Credit)){
            log.error("Operation Type not recognize by the system");
            throw new InvalidValueException("La valeur du type d'operation envoye n'est pas reconnu par le system");
        }

        /*******************************************************************
         * Il faut verifier que l'Id du ProviderCapsuleOperation n'est pas null
         * et ensuite qu'il identifie reellement un compte capsule
         */
        if(ccopDto.getId() == null){
            log.error("The id of the operation is null and then anything can't be done");
            throw new InvalidEntityException("L'id de l'operation etant null rien ne peut etre fait ",
                    ErrorCode.CLIENTCAPSULEOPERATION_NOT_VALID);
        }
        //Ici cela veut dire que l'id est la mais est ce que ca identifie alors un ClientCapsuleAccountOperation?
        Optional<ClientCapsuleOperation> optionalClientCapsuleOperation = clientCapsuleOperationRepository.
                findClientCapsuleOperationById(ccopDto.getId());
        if(!optionalClientCapsuleOperation.isPresent()){
            log.error("There is no ProviderCapsuleOperation in the DB with the precised Id");
            throw new EntityNotFoundException("Aucun ProviderCapsuleOperation n'existe en BD avec l'Id precise ",
                    ErrorCode.CLIENTCAPSULEACCOUNT_NOT_FOUND);
        }

        ///////Ici on peut donc recuperer clientcapsuleaccount a modifier
        ClientCapsuleOperation clientCapsuleOperationToUpdate = optionalClientCapsuleOperation.get();
        //On fait donc les modif de la quantite en mouvement et de l'operation(dateop, objet, description etc.)
        clientCapsuleOperationToUpdate.setCltcsoNumberinmvt(ccopDto.getCltcsoNumberinmvt());
        clientCapsuleOperationToUpdate.setCltcsoOperation(OperationDto.toEntity(ccopDto.getCltcsoOperationDto()));

        log.info("After all verification, the operation can be updated normally");

        return ClientCapsuleOperationDto.fromEntity(clientCapsuleOperationRepository.save(
                clientCapsuleOperationToUpdate));
    }

    @Override
    public Boolean isClientCapsuleOperationDeleteable(Long ccopId) {
        return true;
    }

    @Override
    public Boolean deleteClientCapsuleOperationById(Long ccopId) {
        if(ccopId == null){
            log.error("ccopId is null");
            throw new NullArgumentException("Le ccopId passe en argument de la methode est null");
        }
        Optional<ClientCapsuleOperation> optionalClientCapsuleOperation = clientCapsuleOperationRepository.
                findClientCapsuleOperationById(ccopId);
        if(optionalClientCapsuleOperation.isPresent()){
            if(isClientCapsuleOperationDeleteable(ccopId)){
                clientCapsuleOperationRepository.delete(optionalClientCapsuleOperation.get());
                return true;
            }
        }
        throw new EntityNotFoundException("Aucune entite n'existe avec l'ID passe en argument ",
                ErrorCode.CLIENTCAPSULEOPERATION_NOT_FOUND);
    }

    @Override
    public List<ClientCapsuleOperationDto> findAllClientCapsuleOperation(Long ccaccId) {
        List<ClientCapsuleOperation> clientCapsuleOperationList = clientCapsuleOperationRepository.
                findAllClientCapsuleOperation(ccaccId);
        return clientCapsuleOperationList.stream().map(ClientCapsuleOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<ClientCapsuleOperationDto> findPageClientCapsuleOperation(Long ccaccId, int pagenum, int pagesize) {
        Page<ClientCapsuleOperation> clientCapsuleOperationPage = clientCapsuleOperationRepository.
                findPageClientCapsuleOperation(ccaccId, PageRequest.of(pagenum, pagesize));

        return clientCapsuleOperationPage.map(ClientCapsuleOperationDto::fromEntity);
    }

    @Override
    public List<ClientCapsuleOperationDto> findAllClientCapsuleOperationofType(Long ccaccId, OperationType opType) {
        List<ClientCapsuleOperation> clientCapsuleOperationList = clientCapsuleOperationRepository.findAllClientCapsuleOperationofType(
                ccaccId, opType);

        return clientCapsuleOperationList.stream().map(ClientCapsuleOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<ClientCapsuleOperationDto> findPageClientCapsuleOperationofType(Long ccaccId, OperationType opType,
                                                                                int pagenum, int pagesize) {
        Page<ClientCapsuleOperation> clientCapsuleOperationPage = clientCapsuleOperationRepository.
                findPageClientCapsuleOperationofType(
                        ccaccId, opType, PageRequest.of(pagenum, pagesize));

        return clientCapsuleOperationPage.map(ClientCapsuleOperationDto::fromEntity);
    }

    @Override
    public List<ClientCapsuleOperationDto> findAllClientCapsuleOperationBetween(Long ccaccId, Instant startDate,
                                                                                Instant endDate) {
        List<ClientCapsuleOperation> clientCapsuleOperationListBetween = clientCapsuleOperationRepository.
                findAllClientCapsuleOperationBetween(ccaccId, startDate, endDate);
        return clientCapsuleOperationListBetween.stream().map(ClientCapsuleOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<ClientCapsuleOperationDto> findPageClientCapsuleOperationBetween(Long ccaccId, Instant startDate,
                                                                                 Instant endDate, int pagenum,
                                                                                 int pagesize) {
        Page<ClientCapsuleOperation> clientCapsuleOperationPageBetween = clientCapsuleOperationRepository.
                findPageClientCapsuleOperationBetween(ccaccId, startDate, endDate, PageRequest.of(pagenum, pagesize));
        return clientCapsuleOperationPageBetween.map(ClientCapsuleOperationDto::fromEntity);
    }

    @Override
    public List<ClientCapsuleOperationDto> findAllClientCapsuleOperationBetween(Long ccaccId, OperationType opType, Instant startDate, Instant endDate) {
        List<ClientCapsuleOperation> clientCapsuleOperationListoftypeBetween = clientCapsuleOperationRepository.
                findAllClientCapsuleOperationofTypeBetween(ccaccId, opType, startDate, endDate);
        return clientCapsuleOperationListoftypeBetween.stream().map(ClientCapsuleOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<ClientCapsuleOperationDto> findPageClientCapsuleOperationBetween(Long ccaccId, OperationType opType, Instant startDate, Instant endDate, int pagenum, int pagesize) {
        Page<ClientCapsuleOperation> clientCapsuleOperationPageoftypeBetween = clientCapsuleOperationRepository.
                findPageClientCapsuleOperationofTypeBetween(ccaccId, opType, startDate, endDate,
                        PageRequest.of(pagenum, pagesize));
        return clientCapsuleOperationPageoftypeBetween.map(ClientCapsuleOperationDto::fromEntity);
    }
}
