package com.c2psi.businessmanagement.services.contractsImpl.client.client;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.client.client.ClientCashOperationDto;
import com.c2psi.businessmanagement.dtos.pos.pos.OperationDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.ClientCashOperation;
import com.c2psi.businessmanagement.models.UserBM;
import com.c2psi.businessmanagement.repositories.client.client.ClientCashOperationRepository;
import com.c2psi.businessmanagement.repositories.pos.userbm.UserBMRepository;
import com.c2psi.businessmanagement.services.contracts.client.client.ClientCashOperationService;
import com.c2psi.businessmanagement.validators.client.client.ClientCashOperationValidator;
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

@Service(value="ClientCashOperationServiceV1")
@Slf4j
@Transactional
public class ClientCashOperationServiceImpl implements ClientCashOperationService {

    private ClientCashOperationRepository clientCashOperationRepository;
    private UserBMRepository userBMRepository;

    @Autowired
    public ClientCashOperationServiceImpl(ClientCashOperationRepository clientCashOperationRepository,
                                          UserBMRepository userBMRepository) {
        this.clientCashOperationRepository = clientCashOperationRepository;
        this.userBMRepository = userBMRepository;
    }

    @Override
    public ClientCashOperationDto updateClientCashOperation(ClientCashOperationDto ccaopDto) {

        /**************************************************
         * Verification de l'argument passe en parametre
         */
        List<String> errors = ClientCashOperationValidator.validate(ccaopDto);
        if(!errors.isEmpty()){
            log.error("Entity procopDto not valid {}", ccaopDto);
            throw new InvalidEntityException("Le procopDto passé en argument n'est pas valide: "+errors,
                    ErrorCode.CLIENTCASHOPERATION_NOT_VALID, errors);
        }

        /*****************************************************************
         * Il faut verifier que l'Id du userbm n'est pas null
         * et ensuite qu'il identifie reellement un userbm
         */
        if(ccaopDto.getCcoUserbmDto().getId() == null){
            log.error("The id of the userbm associate with the operation is null and then anything can't be done");
            throw new InvalidEntityException("L'id du userbm associe a l'operation etant null rien ne peut etre fait ",
                    ErrorCode.CLIENTCASHOPERATION_NOT_VALID);
        }
        //Ici cela veut dire que l'id est la mais est ce que ca identifie alors un UserBM?
        Optional<UserBM> optionalUserBM = userBMRepository.findUserBMById(
                ccaopDto.getCcoUserbmDto().getId());
        if(!optionalUserBM.isPresent()){
            log.error("There is no userbm in the DB with the precised Id");
            throw new EntityNotFoundException("Aucun UserBM n'existe en BD avec l'Id precise ",
                    ErrorCode.USERBM_NOT_FOUND);
        }

        /****************************************************************************
         * Il faut se rassurer que le type d'operation est soit credit soit debit
         * soit changement (permutation)
         */
        if(!ccaopDto.getCcoOperationDto().getOpType().equals(OperationType.Withdrawal) ||
                !ccaopDto.getCcoOperationDto().getOpType().equals(OperationType.Change) ||
                !ccaopDto.getCcoOperationDto().getOpType().equals(OperationType.Credit)){
            log.error("Operation Type not recognize by the system");
            throw new InvalidValueException("La valeur du type d'operation envoye n'est pas reconnu par le system");
        }

        /*******************************************************************
         * Il faut verifier que l'Id du ClientCashOperation n'est pas null
         * et ensuite qu'il identifie reellement un compte cash
         */
        if(ccaopDto.getId() == null){
            log.error("The id of the operation is null and then anything can't be done");
            throw new InvalidEntityException("L'id de l'operation etant null rien ne peut etre fait ",
                    ErrorCode.CLIENTCASHOPERATION_NOT_VALID);
        }
        //Ici cela veut dire que l'id est la mais est ce que ca identifie alors un ProviderCashAccountOperation?
        Optional<ClientCashOperation> optionalClientCashOperation = clientCashOperationRepository.findClientCashOperationById(
                ccaopDto.getId());
        if(!optionalClientCashOperation.isPresent()){
            log.error("There is no ClientCashAccountOperation in the DB with the precised Id");
            throw new EntityNotFoundException("Aucun ClientCashAccountOperation n'existe en BD avec l'Id precise ",
                    ErrorCode.CLIENTCASHOPERATION_NOT_FOUND);
        }

        ///////Ici on peut donc recuperer clientcapsuleaccount a modifier
        ClientCashOperation clientCashOperationToUpdate = optionalClientCashOperation.get();
        //On fait donc les modif de la quantite en mouvement et de l'operation(dateop, objet, description etc.)
        clientCashOperationToUpdate.setCcoAmountinmvt(ccaopDto.getCcoAmountinmvt());
        clientCashOperationToUpdate.setCcoOperation(OperationDto.toEntity(ccaopDto.getCcoOperationDto()));

        log.info("After all verification, the operation can be updated normally");

        return ClientCashOperationDto.fromEntity(clientCashOperationRepository.save(clientCashOperationToUpdate));
    }

    @Override
    public Boolean isClientCashOperationDeleatable(Long ccaopDto) {
        return true;
    }

    @Override
    public Boolean deleteClientCashOperationById(Long ccaopId) {
        if(ccaopId == null){
            log.error("ccaopId is null");
            throw new NullArgumentException("le ccaopId passe en argument de la methode est null");
        }
        Optional<ClientCashOperation> optionalClientCashOperation = clientCashOperationRepository.
                findClientCashOperationById(ccaopId);
        if(optionalClientCashOperation.isPresent()){
            if(isClientCashOperationDeleatable(ccaopId)){
                clientCashOperationRepository.delete(optionalClientCashOperation.get());
                return true;
            }
        }
        throw new EntityNotFoundException("Aucune entite n'exise avec l'id passe en argument ",
                ErrorCode.CLIENTCASHOPERATION_NOT_FOUND);
    }

    @Override
    public ClientCashOperationDto findClientCashOperationById(Long ccaopId) {
        if(ccaopId == null){
            log.error("ccaopId is null");
            throw new NullArgumentException("le ccaopId passe en argument de la methode est null");
        }
        Optional<ClientCashOperation> optionalClientCashOperation = clientCashOperationRepository.
                findClientCashOperationById(ccaopId);

        if(!optionalClientCashOperation.isPresent()){
            throw new EntityNotFoundException("Aucune ClientCashOperation avec l'id "+ccaopId
                    +" n'a été trouve dans la BDD", ErrorCode.CLIENTCASHOPERATION_NOT_FOUND);
        }

        return ClientCashOperationDto.fromEntity(optionalClientCashOperation.get());
    }

    @Override
    public List<ClientCashOperationDto> findAllClientCashOperation(Long ccaId) {
        List<ClientCashOperation> clientCashOperationList = clientCashOperationRepository.
                findAllClientCashOperation(ccaId);
        return clientCashOperationList.stream().map(ClientCashOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<ClientCashOperationDto> findPageClientCashOperation(Long ccaId, int pagenum, int pagesize) {
        Page<ClientCashOperation> clientCashOperationPage = clientCashOperationRepository.
                findPageClientCashOperation(ccaId,
                        PageRequest.of(pagenum, pagesize));
        return clientCashOperationPage.map(ClientCashOperationDto::fromEntity);
    }

    @Override
    public List<ClientCashOperationDto> findAllClientCashOperationBetween(Long ccaId, Instant startDate, Instant endDate) {
        List<ClientCashOperation> clientCashOperationListBetween =
                clientCashOperationRepository.findAllClientCashOperationBetween(ccaId, startDate, endDate);
        return clientCashOperationListBetween.stream().map(ClientCashOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<ClientCashOperationDto> findPageClientCashOperationBetween(Long ccaId, Instant startDate,
                                                                           Instant endDate, int pagenum, int pagesize) {
        Page<ClientCashOperation> clientCashOperationPageBetween = clientCashOperationRepository.
                findPageClientCashOperationBetween(ccaId,
                startDate, endDate, PageRequest.of(pagenum, pagesize));
        return clientCashOperationPageBetween.map(ClientCashOperationDto::fromEntity);
    }

    @Override
    public List<ClientCashOperationDto> findAllClientCashOperationofTypeBetween(Long ccaId, OperationType opType,
                                                                          Instant startDate, Instant endDate) {
        List<ClientCashOperation> clientCashOperationListBetween =
                clientCashOperationRepository.findAllClientCashOperationOfTypeBetween(ccaId, opType, startDate, endDate);
        return clientCashOperationListBetween.stream().map(ClientCashOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<ClientCashOperationDto> findPageClientCashOperationofTypeBetween(Long ccaId, OperationType opType,
                                                                           Instant startDate, Instant endDate,
                                                                           int pagenum, int pagesize) {
        Page<ClientCashOperation> clientCashOperationPageBetween = clientCashOperationRepository.
                findPageClientCashOperationOfTypeBetween(ccaId,
                opType, startDate, endDate, PageRequest.of(pagenum, pagesize));
        return clientCashOperationPageBetween.map(ClientCashOperationDto::fromEntity);
    }
}
