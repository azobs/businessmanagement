package com.c2psi.businessmanagement.services.contractsImpl.client.client;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.client.client.ClientDamageOperationDto;
import com.c2psi.businessmanagement.dtos.pos.pos.OperationDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.ClientDamageOperation;
import com.c2psi.businessmanagement.models.UserBM;
import com.c2psi.businessmanagement.repositories.client.client.ClientDamageOperationRepository;
import com.c2psi.businessmanagement.repositories.pos.userbm.UserBMRepository;
import com.c2psi.businessmanagement.services.contracts.client.client.ClientDamageOperationService;
import com.c2psi.businessmanagement.validators.client.client.ClientDamageOperationValidator;
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

@Service(value="ClientDamageOperationServiceV1")
@Slf4j
@Transactional
public class ClientDamageOperationServiceImpl implements ClientDamageOperationService {

    private ClientDamageOperationRepository clientDamageOperationRepository;
    private UserBMRepository userBMRepository;

    @Autowired
    public ClientDamageOperationServiceImpl(ClientDamageOperationRepository clientDamageOperationRepository,
                                            UserBMRepository userBMRepository) {
        this.clientDamageOperationRepository = clientDamageOperationRepository;
        this.userBMRepository = userBMRepository;
    }

    @Override
    public ClientDamageOperationDto updateClientDamageOperation(ClientDamageOperationDto cdamopDto) {
        /********************************************************************
         * Il faut d'abord valider le parametre pris en parametre
         */
        List<String> errors = ClientDamageOperationValidator.validate(cdamopDto);
        if(!errors.isEmpty()){
            log.error("Entity cdamopDto not valid {}", cdamopDto);
            throw new InvalidEntityException("Le cdamopDto passé en argument n'est pas valide: "+errors,
                    ErrorCode.CLIENTDAMAGEOPERATION_NOT_VALID, errors);
        }

        /*****************************************************************
         * Il faut verifier que l'Id du userbm n'est pas null
         * et ensuite qu'il identifie reellement un userbm
         */
        if(cdamopDto.getCltdoUserbmDto().getId() == null){
            log.error("The id of the userbm associate with the operation is null and then anything can't be done");
            throw new InvalidEntityException("L'id du userbm associe a l'operation etant null rien ne peut etre fait ",
                    ErrorCode.CLIENTDAMAGEOPERATION_NOT_VALID);
        }
        //Ici cela veut dire que l'id est la mais est ce que ca identifie alors un PosCashAccountOperation?
        Optional<UserBM> optionalUserBM = userBMRepository.findUserBMById(
                cdamopDto.getCltdoUserbmDto().getId());
        if(!optionalUserBM.isPresent()){
            log.error("There is no userbm in the DB with the precised Id");
            throw new EntityNotFoundException("Aucun UserBM n'existe en BD avec l'Id precise ",
                    ErrorCode.USERBM_NOT_FOUND);
        }

        /****************************************************************************
         * Il faut se rassurer que le type d'operation est soit credit soit debit
         * soit changement (permutation)
         */
        if(!cdamopDto.getCltdoOperationDto().getOpType().equals(OperationType.Withdrawal) &&
                !cdamopDto.getCltdoOperationDto().getOpType().equals(OperationType.Change) &&
                !cdamopDto.getCltdoOperationDto().getOpType().equals(OperationType.Credit)){
            log.error("Operation Type not recognize by the system");
            throw new InvalidValueException("La valeur du type d'operation envoye n'est pas reconnu par le system");
        }

        /*******************************************************************
         * Il faut verifier que l'Id du ProviderDamageOperation n'est pas null
         * et ensuite qu'il identifie reellement un compte Damage
         */
        if(cdamopDto.getId() == null){
            log.error("The id of the operation is null and then anything can't be done");
            throw new InvalidEntityException("L'id de l'operation etant null rien ne peut etre fait ",
                    ErrorCode.CLIENTDAMAGEOPERATION_NOT_VALID);
        }
        //Ici cela veut dire que l'id est la mais est ce que ca identifie alors un ClientDamageAccountOperation?
        Optional<ClientDamageOperation> optionalClientDamageOperation = clientDamageOperationRepository.
                findClientDamageOperationById(cdamopDto.getId());
        if(!optionalClientDamageOperation.isPresent()){
            log.error("There is no ProviderDamageOperation in the DB with the precised Id");
            throw new EntityNotFoundException("Aucun ProviderDamageOperation n'existe en BD avec l'Id precise ",
                    ErrorCode.CLIENTDAMAGEACCOUNT_NOT_FOUND);
        }

        ///////Ici on peut donc recuperer clientDamageaccount a modifier
        ClientDamageOperation clientDamageOperationToUpdate = optionalClientDamageOperation.get();
        //On fait donc les modif de la quantite en mouvement et de l'operation(dateop, objet, description etc.)
        clientDamageOperationToUpdate.setCltdoNumberinmvt(cdamopDto.getCltdoNumberinmvt());
        clientDamageOperationToUpdate.setCltdoOperation(OperationDto.toEntity(cdamopDto.getCltdoOperationDto()));

        log.info("After all verification, the operation can be updated normally");

        return ClientDamageOperationDto.fromEntity(clientDamageOperationRepository.save(
                clientDamageOperationToUpdate));
    }

    @Override
    public Boolean isClientDamageOperationDeleteable(Long cdopId) {
        return true;
    }

    @Override
    public Boolean deleteClientDamageOperationById(Long cdopId) {
        if(cdopId == null){
            log.error("ccopId is null");
            throw new NullArgumentException("Le ccopId passe en argument de la methode est null");
        }
        Optional<ClientDamageOperation> optionalClientDamageOperation = clientDamageOperationRepository.
                findClientDamageOperationById(cdopId);
        if(optionalClientDamageOperation.isPresent()){
            if(isClientDamageOperationDeleteable(cdopId)){
                clientDamageOperationRepository.delete(optionalClientDamageOperation.get());
                return true;
            }
        }
        throw new EntityNotFoundException("Aucune entite n'existe avec l'ID passe en argument ",
                ErrorCode.CLIENTDAMAGEOPERATION_NOT_FOUND);
    }

    @Override
    public List<ClientDamageOperationDto> findAllClientDamageOperation(Long cdaccId) {
        List<ClientDamageOperation> clientDamageOperationList = clientDamageOperationRepository.
                findAllClientDamageOperation(cdaccId);
        return clientDamageOperationList.stream().map(ClientDamageOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<ClientDamageOperationDto> findPageClientDamageOperation(Long cdaccId, int pagenum, int pagesize) {
        Page<ClientDamageOperation> clientDamageOperationPage = clientDamageOperationRepository.
                findPageClientDamageOperation(cdaccId, PageRequest.of(pagenum, pagesize));

        return clientDamageOperationPage.map(ClientDamageOperationDto::fromEntity);
    }

    @Override
    public List<ClientDamageOperationDto> findAllClientDamageOperationofType(Long cdaccId, OperationType opType) {
        List<ClientDamageOperation> clientDamageOperationList = clientDamageOperationRepository.findAllClientDamageOperationofType(
                cdaccId, opType);

        return clientDamageOperationList.stream().map(ClientDamageOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<ClientDamageOperationDto> findPageClientDamageOperationofType(Long cdaccId, OperationType opType,
                                                                              int pagenum, int pagesize) {
        Page<ClientDamageOperation> clientDamageOperationPage = clientDamageOperationRepository.
                findPageClientDamageOperationofType(
                        cdaccId, opType, PageRequest.of(pagenum, pagesize));

        return clientDamageOperationPage.map(ClientDamageOperationDto::fromEntity);
    }

    @Override
    public List<ClientDamageOperationDto> findAllClientDamageOperationBetween(Long cdaccId, Instant startDate,
                                                                              Instant endDate) {
        List<ClientDamageOperation> clientDamageOperationListBetween = clientDamageOperationRepository.
                findAllClientDamageOperationBetween(cdaccId, startDate, endDate);
        return clientDamageOperationListBetween.stream().map(ClientDamageOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<ClientDamageOperationDto> findPageClientDamageOperationBetween(Long cdaccId, Instant startDate,
                                                                               Instant endDate, int pagenum,
                                                                               int pagesize) {
        Page<ClientDamageOperation> clientDamageOperationPageBetween = clientDamageOperationRepository.
                findPageClientDamageOperationBetween(cdaccId, startDate, endDate, PageRequest.of(pagenum, pagesize));
        return clientDamageOperationPageBetween.map(ClientDamageOperationDto::fromEntity);
    }

    @Override
    public List<ClientDamageOperationDto> findAllClientDamageOperationBetween(Long cdaccId, OperationType opType,
                                                                              Instant startDate, Instant endDate) {
        List<ClientDamageOperation> clientDamageOperationListoftypeBetween = clientDamageOperationRepository.
                findAllClientDamageOperationofTypeBetween(cdaccId, opType, startDate, endDate);
        return clientDamageOperationListoftypeBetween.stream().map(ClientDamageOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<ClientDamageOperationDto> findPageClientDamageOperationBetween(Long cdaccId, OperationType opType,
                                                                               Instant startDate, Instant endDate,
                                                                               int pagenum, int pagesize) {
        Page<ClientDamageOperation> clientDamageOperationPageoftypeBetween = clientDamageOperationRepository.
                findPageClientDamageOperationofTypeBetween(cdaccId, opType, startDate, endDate,
                        PageRequest.of(pagenum, pagesize));
        return clientDamageOperationPageoftypeBetween.map(ClientDamageOperationDto::fromEntity);
    }
}
