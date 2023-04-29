package com.c2psi.businessmanagement.services.contractsImpl.client.client;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.client.client.ClientPackagingOperationDto;
import com.c2psi.businessmanagement.dtos.pos.pos.OperationDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderPackagingOperationDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.ClientPackagingOperation;
import com.c2psi.businessmanagement.models.ProviderPackagingOperation;
import com.c2psi.businessmanagement.models.UserBM;
import com.c2psi.businessmanagement.repositories.client.client.ClientPackagingOperationRepository;
import com.c2psi.businessmanagement.repositories.pos.userbm.UserBMRepository;
import com.c2psi.businessmanagement.repositories.stock.provider.ProviderPackagingOperationRepository;
import com.c2psi.businessmanagement.services.contracts.client.client.ClientPackagingOperationService;
import com.c2psi.businessmanagement.validators.client.client.ClientPackagingOperationValidator;
import com.c2psi.businessmanagement.validators.stock.provider.ProviderPackagingOperationValidator;
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

@Service(value="ClientPackagingOperationServiceV1")
@Slf4j
@Transactional
public class ClientPackagingOperationServiceImpl implements ClientPackagingOperationService {

    private ClientPackagingOperationRepository clientPackagingOperationRepository;
    private UserBMRepository userBMRepository;

    @Autowired
    public ClientPackagingOperationServiceImpl(ClientPackagingOperationRepository clientPackagingOperationRepository,
                                               UserBMRepository userBMRepository) {
        this.clientPackagingOperationRepository = clientPackagingOperationRepository;
        this.userBMRepository = userBMRepository;
    }

    @Override
    public ClientPackagingOperationDto updateClientPackagingOperation(ClientPackagingOperationDto cltpackopDto) {

        /********************************************************************
         * Il faut d'abord valider le parametre pris en parametre
         */
        List<String> errors = ClientPackagingOperationValidator.validate(cltpackopDto);
        if(!errors.isEmpty()){
            log.error("Entity cltpackopDto not valid {}", cltpackopDto);
            throw new InvalidEntityException("Le cltpackopDto passé en argument n'est pas valide: "+errors,
                    ErrorCode.CLIENTPACKAGINGOPERATION_NOT_VALID, errors);
        }

        /*****************************************************************
         * Il faut verifier que l'Id du userbm n'est pas null
         * et ensuite qu'il identifie reellement un userbm
         */
        if(cltpackopDto.getCltpoUserbmDto().getId() == null){
            log.error("The id of the userbm associate with the operation is null and then anything can't be done");
            throw new InvalidEntityException("L'id du userbm associe a l'operation etant null rien ne peut etre fait ",
                    ErrorCode.CLIENTPACKAGINGOPERATION_NOT_VALID);
        }
        //Ici cela veut dire que l'id est la mais est ce que ca identifie alors un PosCashAccountOperation?
        Optional<UserBM> optionalUserBM = userBMRepository.findUserBMById(
                cltpackopDto.getCltpoUserbmDto().getId());
        if(!optionalUserBM.isPresent()){
            log.error("There is no userbm in the DB with the precised Id");
            throw new EntityNotFoundException("Aucun UserBM n'existe en BD avec l'Id precise ",
                    ErrorCode.USERBM_NOT_FOUND);
        }

        /****************************************************************************
         * Il faut se rassurer que le type d'operation est soit credit soit debit
         * soit changement (permutation)
         */
        if(!cltpackopDto.getCltpoOperationDto().getOpType().equals(OperationType.Withdrawal) &&
                !cltpackopDto.getCltpoOperationDto().getOpType().equals(OperationType.Change) &&
                !cltpackopDto.getCltpoOperationDto().getOpType().equals(OperationType.Credit)){
            log.error("Operation Type not recognize by the system");
            throw new InvalidValueException("La valeur du type d'operation envoye n'est pas reconnu par le system");
        }

        /************************************************************************
         * Il faut verifier que l'Id du ClientPackagingOperation n'est pas null
         * et ensuite qu'il identifie reellement un compte capsule
         */
        if(cltpackopDto.getId() == null){
            log.error("The id of the operation is null and then anything can't be done");
            throw new InvalidEntityException("L'id de l'operation etant null rien ne peut etre fait ",
                    ErrorCode.CLIENTPACKAGINGOPERATION_NOT_VALID);
        }
        //Ici cela veut dire que l'id est la mais est ce que ca identifie alors un PosCapsuleAccountOperation?
        Optional<ClientPackagingOperation> optionalClientPackagingOperation = clientPackagingOperationRepository.findClientPackagingOperationById(
                cltpackopDto.getId());
        if(!optionalClientPackagingOperation.isPresent()){
            log.error("There is no ClientPackagingAccountOperation in the DB with the precised Id");
            throw new EntityNotFoundException("Aucun ClientPackagingAccountOperation n'existe en BD avec l'Id precise ",
                    ErrorCode.CLIENTPACKAGINGACCOUNT_NOT_FOUND);
        }

        ///////Ici on peut donc recuperer cltpackagingaccount a modifier
        ClientPackagingOperation cltPackagingOperationToUpdate = optionalClientPackagingOperation.get();
        //On fait donc les modif de la quantite en mouvement et de l'operation(dateop, objet, description etc.)
        cltPackagingOperationToUpdate.setCltpoNumberinmvt(cltpackopDto.getCltpoNumberinmvt());
        cltPackagingOperationToUpdate.setCltpoOperation(OperationDto.toEntity(cltpackopDto.getCltpoOperationDto()));

        log.info("After all verification, the operation {} can be updated normally", cltpackopDto);

        //PointofsaleDto.fromEntity(posRepository.save(posToUpdate))

        return ClientPackagingOperationDto.fromEntity(clientPackagingOperationRepository.save(cltPackagingOperationToUpdate));
    }

    @Override
    public Boolean isClientPackagingOperationDeleteable(Long cltpackopId) {
        return true;
    }

    @Override
    public Boolean deleteClientPackagingOperationById(Long cltpackopId) {
        if(cltpackopId == null){
            log.error("cltpackopId is null");
            throw new NullArgumentException("Le cltpackopId passe en argument de la methode est null");
        }
        Optional<ClientPackagingOperation> optionalClientPackagingOperation = clientPackagingOperationRepository.
                findClientPackagingOperationById(cltpackopId);
        if(optionalClientPackagingOperation.isPresent()){
            if(isClientPackagingOperationDeleteable(cltpackopId)){
                clientPackagingOperationRepository.delete(optionalClientPackagingOperation.get());
                return true;
            }
        }
        throw new EntityNotFoundException("Aucune entite n'existe avec l'ID passe en argument ",
                ErrorCode.CLIENTPACKAGINGOPERATION_NOT_FOUND);
    }

    @Override
    public List<ClientPackagingOperationDto> findAllClientPackagingOperation(Long cltpackopId) {
        List<ClientPackagingOperation> clientPackagingOperationList = clientPackagingOperationRepository.
                findAllClientPackagingOperation(cltpackopId);
        return clientPackagingOperationList.stream().map(ClientPackagingOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public List<ClientPackagingOperationDto> findAllClientPackagingOperationofType(Long cltpackopId, OperationType opType) {
        List<ClientPackagingOperation> clientPackagingOperationList = clientPackagingOperationRepository.
                findAllClientPackagingOperationOfType(cltpackopId, opType);

        return clientPackagingOperationList.stream().map(ClientPackagingOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<ClientPackagingOperationDto> findPageClientPackagingOperation(Long cltpackopId, int pagenum, int pagesize) {
        Page<ClientPackagingOperation> clientPackagingOperationPage = clientPackagingOperationRepository.findPageClientPackagingOperation(
                cltpackopId, PageRequest.of(pagenum, pagesize));

        return clientPackagingOperationPage.map(ClientPackagingOperationDto::fromEntity);
    }

    @Override
    public Page<ClientPackagingOperationDto> findPageClientPackagingOperationofType(Long cltpackopId, OperationType opType, int pagenum, int pagesize) {
        Page<ClientPackagingOperation> clientPackagingOperationPage = clientPackagingOperationRepository.
                findPageClientPackagingOperationOfType(cltpackopId, opType, PageRequest.of(pagenum, pagesize));

        return clientPackagingOperationPage.map(ClientPackagingOperationDto::fromEntity);
    }

    @Override
    public List<ClientPackagingOperationDto> findAllClientPackagingOperationBetween(Long cltpackopId, Instant startDate,
                                                                                    Instant endDate) {
        List<ClientPackagingOperation> clientPackagingOperationListBetween = clientPackagingOperationRepository.
                findAllClientPackagingOperationBetween(cltpackopId, startDate, endDate);
        return clientPackagingOperationListBetween.stream().map(ClientPackagingOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<ClientPackagingOperationDto> findPageClientPackagingOperationBetween(Long cltpackopId, Instant startDate, Instant endDate, int pagenum, int pagesize) {
        Page<ClientPackagingOperation> clientPackagingOperationPageBetween = clientPackagingOperationRepository.
                findPageClientPackagingOperationBetween(cltpackopId, startDate, endDate, PageRequest.of(pagenum, pagesize));
        return clientPackagingOperationPageBetween.map(ClientPackagingOperationDto::fromEntity);
    }

    @Override
    public List<ClientPackagingOperationDto> findAllClientPackagingOperationBetween(Long cltpackopId, OperationType op_type,
                                                                                    Instant startDate, Instant endDate) {
        List<ClientPackagingOperation> clientPackagingOperationListoftypeBetween = clientPackagingOperationRepository.
                findAllClientPackagingOperationOfTypeBetween(cltpackopId, op_type, startDate, endDate);
        return clientPackagingOperationListoftypeBetween.stream().map(ClientPackagingOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<ClientPackagingOperationDto> findPageClientPackagingOperationBetween(Long cltpackopId, OperationType op_type, Instant startDate, Instant endDate, int pagenum, int pagesize) {
        Page<ClientPackagingOperation> clientPackagingOperationPageoftypeBetween = clientPackagingOperationRepository.
                findPageClientPackagingOperationOfTypeBetween(cltpackopId, op_type, startDate, endDate,
                        PageRequest.of(pagenum, pagesize));
        return clientPackagingOperationPageoftypeBetween.map(ClientPackagingOperationDto::fromEntity);
    }
}
