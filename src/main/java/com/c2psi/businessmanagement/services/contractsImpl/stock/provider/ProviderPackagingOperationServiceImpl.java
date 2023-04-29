package com.c2psi.businessmanagement.services.contractsImpl.stock.provider;

import com.c2psi.businessmanagement.Enumerations.OperationType;
import com.c2psi.businessmanagement.dtos.pos.pos.OperationDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderPackagingOperationDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.ProviderPackagingOperation;
import com.c2psi.businessmanagement.models.UserBM;
import com.c2psi.businessmanagement.repositories.pos.userbm.UserBMRepository;
import com.c2psi.businessmanagement.repositories.stock.provider.ProviderPackagingOperationRepository;
import com.c2psi.businessmanagement.services.contracts.stock.provider.ProviderPackagingOperationService;
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

@Service(value="ProviderPackagingOperationServiceV1")
@Slf4j
@Transactional
public class ProviderPackagingOperationServiceImpl implements ProviderPackagingOperationService {

    private ProviderPackagingOperationRepository providerPackagingOperationRepository;
    private UserBMRepository userBMRepository;

    @Autowired
    public ProviderPackagingOperationServiceImpl(ProviderPackagingOperationRepository providerPackagingOperationRepository,
                                                 UserBMRepository userBMRepository) {
        this.providerPackagingOperationRepository = providerPackagingOperationRepository;
        this.userBMRepository = userBMRepository;
    }

    @Override
    public ProviderPackagingOperationDto updateProviderPackagingOperation(ProviderPackagingOperationDto propackopDto) {

        /********************************************************************
         * Il faut d'abord valider le parametre pris en parametre
         */
        List<String> errors = ProviderPackagingOperationValidator.validate(propackopDto);
        if(!errors.isEmpty()){
            log.error("Entity propackopDto not valid {}", propackopDto);
            throw new InvalidEntityException("Le propackopDto passé en argument n'est pas valide: "+errors,
                    ErrorCode.PROVIDERPACKAGINGOPERATION_NOT_VALID, errors);
        }

        /*****************************************************************
         * Il faut verifier que l'Id du userbm n'est pas null
         * et ensuite qu'il identifie reellement un userbm
         */
        if(propackopDto.getPropoUserbmDto().getId() == null){
            log.error("The id of the userbm associate with the operation is null and then anything can't be done");
            throw new InvalidEntityException("L'id du userbm associe a l'operation etant null rien ne peut etre fait ",
                    ErrorCode.PROVIDERPACKAGINGOPERATION_NOT_VALID);
        }
        //Ici cela veut dire que l'id est la mais est ce que ca identifie alors un PosCashAccountOperation?
        Optional<UserBM> optionalUserBM = userBMRepository.findUserBMById(
                propackopDto.getPropoUserbmDto().getId());
        if(!optionalUserBM.isPresent()){
            log.error("There is no userbm in the DB with the precised Id");
            throw new EntityNotFoundException("Aucun UserBM n'existe en BD avec l'Id precise ",
                    ErrorCode.USERBM_NOT_FOUND);
        }

        /****************************************************************************
         * Il faut se rassurer que le type d'operation est soit credit soit debit
         * soit changement (permutation)
         */
        if(!propackopDto.getPropoOperationDto().getOpType().equals(OperationType.Withdrawal) &&
                !propackopDto.getPropoOperationDto().getOpType().equals(OperationType.Change) &&
                !propackopDto.getPropoOperationDto().getOpType().equals(OperationType.Credit)){
            log.error("Operation Type not recognize by the system");
            throw new InvalidValueException("La valeur du type d'operation envoye n'est pas reconnu par le system");
        }

        /************************************************************************
         * Il faut verifier que l'Id du ProviderPackagingOperation n'est pas null
         * et ensuite qu'il identifie reellement un compte capsule
         */
        if(propackopDto.getId() == null){
            log.error("The id of the operation is null and then anything can't be done");
            throw new InvalidEntityException("L'id de l'operation etant null rien ne peut etre fait ",
                    ErrorCode.PROVIDERPACKAGINGOPERATION_NOT_VALID);
        }
        //Ici cela veut dire que l'id est la mais est ce que ca identifie alors un PosCapsuleAccountOperation?
        Optional<ProviderPackagingOperation> optionalProviderPackagingOperation = providerPackagingOperationRepository.findProviderPackagingOperationById(
                propackopDto.getId());
        if(!optionalProviderPackagingOperation.isPresent()){
            log.error("There is no ProviderPackagingAccountOperation in the DB with the precised Id");
            throw new EntityNotFoundException("Aucun ProviderPackagingAccountOperation n'existe en BD avec l'Id precise ",
                    ErrorCode.PROVIDERPACKAGINGACCOUNT_NOT_FOUND);
        }


        ///////Ici on peut donc recuperer propackagingaccount a modifier
        ProviderPackagingOperation proPackagingOperationToUpdate = optionalProviderPackagingOperation.get();
        //On fait donc les modif de la quantite en mouvement et de l'operation(dateop, objet, description etc.)
        proPackagingOperationToUpdate.setPropoNumberinmvt(propackopDto.getPropoNumberinmvt());
        proPackagingOperationToUpdate.setPropoOperation(OperationDto.toEntity(propackopDto.getPropoOperationDto()));

        log.info("After all verification, the operation can be updated normally");

        //PointofsaleDto.fromEntity(posRepository.save(posToUpdate))

        return ProviderPackagingOperationDto.fromEntity(providerPackagingOperationRepository.save(proPackagingOperationToUpdate));
    }

    @Override
    public Boolean isProviderPackagingOperationDeleteable(Long propackopId) {
        return true;
    }

    @Override
    public Boolean deleteProviderPackagingOperationById(Long propackopId) {
        if(propackopId == null){
            log.error("propackopId is null");
            throw new NullArgumentException("Le propackopId passe en argument de la methode est null");
        }
        Optional<ProviderPackagingOperation> optionalProviderPackagingOperation = providerPackagingOperationRepository.
                findProviderPackagingOperationById(propackopId);
        if(optionalProviderPackagingOperation.isPresent()){
            if(isProviderPackagingOperationDeleteable(propackopId)){
                providerPackagingOperationRepository.delete(optionalProviderPackagingOperation.get());
                return true;
            }
        }
        throw new EntityNotFoundException("Aucune entite n'existe avec l'ID passe en argument ",
                ErrorCode.PROVIDERPACKAGINGOPERATION_NOT_FOUND);
    }

    @Override
    public List<ProviderPackagingOperationDto> findAllProviderPackagingOperation(Long propackopId) {
        List<ProviderPackagingOperation> providerPackagingOperationList = providerPackagingOperationRepository.
                findAllProviderPackagingOperation(propackopId);
        return providerPackagingOperationList.stream().map(ProviderPackagingOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public List<ProviderPackagingOperationDto> findAllProviderPackagingOperationofType(Long propackopId, OperationType opType) {
        List<ProviderPackagingOperation> providerPackagingOperationList = providerPackagingOperationRepository.
                findAllProviderPackagingOperationOfType(propackopId, opType);

        return providerPackagingOperationList.stream().map(ProviderPackagingOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<ProviderPackagingOperationDto> findPageProviderPackagingOperation(Long propackopId, int pagenum,
                                                                                  int pagesize) {
        Page<ProviderPackagingOperation> providerPackagingOperationPage = providerPackagingOperationRepository.findPageProviderPackagingOperation(
                propackopId, PageRequest.of(pagenum, pagesize));

        return providerPackagingOperationPage.map(ProviderPackagingOperationDto::fromEntity);
    }

    @Override
    public Page<ProviderPackagingOperationDto> findPageProviderPackagingOperationofType(Long propackopId,
                                                                                        OperationType opType,
                                                                                        int pagenum, int pagesize) {
        Page<ProviderPackagingOperation> providerPackagingOperationPage = providerPackagingOperationRepository.
                findPageProviderPackagingOperationOfType(propackopId, opType, PageRequest.of(pagenum, pagesize));

        return providerPackagingOperationPage.map(ProviderPackagingOperationDto::fromEntity);
    }

    @Override
    public List<ProviderPackagingOperationDto> findAllProviderPackagingOperationBetween(Long propackopId,
                                                                                        Instant startDate,
                                                                                        Instant endDate) {
        List<ProviderPackagingOperation> providerPackagingOperationListBetween = providerPackagingOperationRepository.
                findAllProviderPackagingOperationBetween(propackopId, startDate, endDate);
        return providerPackagingOperationListBetween.stream().map(ProviderPackagingOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<ProviderPackagingOperationDto> findPageProviderPackagingOperationBetween(Long propackopId,
                                                                                         Instant startDate,
                                                                                         Instant endDate, int pagenum,
                                                                                         int pagesize) {
        Page<ProviderPackagingOperation> providerPackagingOperationPageBetween = providerPackagingOperationRepository.
                findPageProviderPackagingOperationBetween(propackopId, startDate, endDate, PageRequest.of(pagenum, pagesize));
        return providerPackagingOperationPageBetween.map(ProviderPackagingOperationDto::fromEntity);
    }

    @Override
    public List<ProviderPackagingOperationDto> findAllProviderPackagingOperationBetween(Long propackopId, OperationType op_type,
                                                                                        Instant startDate, Instant endDate) {
        List<ProviderPackagingOperation> providerPackagingOperationListoftypeBetween = providerPackagingOperationRepository.
                findAllProviderPackagingOperationOfTypeBetween(propackopId, op_type, startDate, endDate);
        return providerPackagingOperationListoftypeBetween.stream().map(ProviderPackagingOperationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<ProviderPackagingOperationDto> findPageProviderPackagingOperationBetween(Long propackopId, OperationType op_type, Instant startDate, Instant endDate, int pagenum, int pagesize) {
        Page<ProviderPackagingOperation> providerPackagingOperationPageoftypeBetween = providerPackagingOperationRepository.
                findPageProviderPackagingOperationOfTypeBetween(propackopId, op_type, startDate, endDate, PageRequest.of(pagenum, pagesize));
        return providerPackagingOperationPageoftypeBetween.map(ProviderPackagingOperationDto::fromEntity);
    }
}
