package com.c2psi.businessmanagement.services.contractsImpl.stock.provider;

import com.c2psi.businessmanagement.dtos.pos.userbm.AddressDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.Pointofsale;
import com.c2psi.businessmanagement.models.Provider;
import com.c2psi.businessmanagement.repositories.pos.pos.PointofsaleRepository;
import com.c2psi.businessmanagement.repositories.pos.userbm.UserBMRepository;
import com.c2psi.businessmanagement.repositories.stock.provider.ProviderCashAccountRepository;
import com.c2psi.businessmanagement.repositories.stock.provider.ProviderCashOperationRepository;
import com.c2psi.businessmanagement.repositories.stock.provider.ProviderRepository;
import com.c2psi.businessmanagement.services.contracts.stock.provider.ProviderService;
import com.c2psi.businessmanagement.validators.stock.provider.ProviderValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value="ProviderServiceV1")
@Slf4j
@Transactional
public class ProviderServiceImpl implements ProviderService {
    private ProviderCashAccountRepository providerCashAccountRepository;
    private UserBMRepository userBMRepository;
    private ProviderCashOperationRepository providerCashOperationRepository;
    private ProviderRepository providerRepository;
    private PointofsaleRepository pointofsaleRepository;

    @Autowired
    public ProviderServiceImpl(ProviderCashAccountRepository providerCashAccountRepository,
                               UserBMRepository userBMRepository,
                               ProviderCashOperationRepository providerCashOperationRepository,
                               ProviderRepository providerRepository, PointofsaleRepository posRepository) {
        this.providerCashAccountRepository = providerCashAccountRepository;
        this.userBMRepository = userBMRepository;
        this.providerCashOperationRepository = providerCashOperationRepository;
        this.providerRepository = providerRepository;
        this.pointofsaleRepository = posRepository;
    }

    @Override
    public ProviderDto saveProvider(ProviderDto providerDto) {
        /********************************************************************************************************
         * Il faut valider le dto pris en parametre pour se rassurer que la saisie des donnees a ete bien faite
         */
        List<String> errors = ProviderValidator.validate(providerDto);
        if(!errors.isEmpty()){
            log.error("Entity Provider not valid {}", providerDto);
            throw new InvalidEntityException("Le provider passe en argument n'est pas valide:  ",
                    ErrorCode.PROVIDER_NOT_VALID, errors);
        }

        /**********************************************************************
         * On se rassure de l'existence du pointofsale associe au provider
         */
        if(providerDto.getProviderPosId() == null){
            log.error("The pointofsale for the provider has not been precised");
            throw new InvalidEntityException("Aucun pointofsale n'est associe avec le provider"
                    , ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        Optional<Pointofsale> optionalPointofsale = pointofsaleRepository.findPointofsaleById
                (providerDto.getProviderPosId());
        if(!optionalPointofsale.isPresent()){
            log.error("The pointofsale precised does not exist in the DB");
            throw new EntityNotFoundException("Le Pointofsale associe avec le provider n'existe pas en BD"
                    , ErrorCode.POINTOFSALE_NOT_FOUND);
        }

        /**********************************************************************************
         * Pour ce qui est du ProviderCashAccount du provider il va etre creer
         * pendant la creation du provider a cause de la relation a sens unique
         * OneToOne. C'est donc a partir du pointofsale quon aura access au ProviderCashAccount
         */

        /*********************************************************************************************
         * Il faut se rassurer qu'il n'y aurait pas de duplicata de provider dans la base de donnee
         */
        if(!isProviderUniqueForPos(providerDto.getProviderName(), providerDto.getProviderAddressDto().getEmail(),
                providerDto.getProviderPosId())){
            log.error("A provider already exist in the DB for the same pointofsale with the same name");
            throw new DuplicateEntityException("Un provider existe deja dans le pointofsale precise avec le " +
                    "meme providerName ", ErrorCode.PROVIDER_DUPLICATED);
        }

        log.info("After all verification, the record {} can be done on the DB", providerDto);

        return ProviderDto.fromEntity(
                providerRepository.save(
                        ProviderDto.toEntity(providerDto)
                )
        );
    }

    @Override
    public ProviderDto updateProvider(ProviderDto providerDto) {
        /********************************************************************************************************
         * Il faut valider le dto pris en parametre pour se rassurer que la saisie des donnees a ete bien faite
         */
        List<String> errors = ProviderValidator.validate(providerDto);
        if(!errors.isEmpty()){
            log.error("Entity Provider not valid {}", providerDto);
            throw new InvalidEntityException("Le provider passe en argument n'est pas valide:  ",
                    ErrorCode.PROVIDER_NOT_VALID, errors);
        }

        /***********************************************************************
         * Il faut se rassurer que l'Id du provider a modifier existe vraiment
         */
        if(providerDto.getId() == null){
            log.error("The id of the provider that must be modified cannot be null");
            throw new InvalidEntityException("L'id du provider a modifier est null ", ErrorCode.PROVIDER_NOT_VALID);
        }

        /*************************************************
         * On essaye de recuperer le provider a modifier
         */
        Optional<Provider> optionalProvider = providerRepository.findProviderById(providerDto.getId());
        if(!optionalProvider.isPresent()){
           log.error("There is no provider in DB for the pointofsale precised with the id passed");
           throw new EntityNotFoundException("Aucun provider n'existe avec le id envoye dans le Dto ",
                   ErrorCode.PROVIDER_NOT_FOUND);
        }
        Provider providerToUpdate = optionalProvider.get();

        /*************************************
         * On verifie que ce n'est pas le pointofsale quon veut modifier car si cest le cas
         * la requete doit etre rejete
         */
        if(!providerToUpdate.getProviderPosId().equals(providerDto.getProviderPosId())){
            log.error("The pointofsale of a provider cannot be modified");
            throw new InvalidEntityException("Le pointofsale d'un provider ne peut etre modifier lors d'une " +
                    "requete de modification du provider ", ErrorCode.PROVIDER_NOT_VALID);
        }

        /**********************************************************
         * On verifie si c'est l'adresse email quon veut modifier
         */
        if(!providerDto.getProviderAddressDto().getEmail().equalsIgnoreCase(providerToUpdate.getProviderAddress().getEmail())){
            /****
             * On verifie donc que la nouvelle adresse n'existe pas encore en BD
             */
            if(isProviderExistWithEmail(providerDto.getProviderAddressDto().getEmail())){
                log.error("The new email precised for the provider already used by another entity");
                throw new DuplicateEntityException("Une autre entite en BD utilise deja cet email ",
                        ErrorCode.PROVIDER_DUPLICATED);
            }
            //Tout est bon au niveau de l'adresse donc on peut mettre a jour
            providerToUpdate.setProviderAddress(AddressDto.toEntity(providerDto.getProviderAddressDto()));
        }


        /*********************************************************************
         * On verifie si c'est le nom qu'on veut modifier et
         * si cest le cas on se rassure quil n'y aura pas de duplicata apres
         */
        if(!providerDto.getProviderName().equalsIgnoreCase(providerToUpdate.getProviderName())){
            //Ils sont different donc on veut modifier le nom
            /***************
             * On se rassure quil y aura pas de duplicata de provider
             */
            if(isProviderExistWithNameinPos(providerDto.getProviderName(), providerDto.getProviderPosId())){
                log.error("The new email precised for the provider already used by another entity");
                throw new DuplicateEntityException("Une autre entite en BD utilise deja cet email ",
                        ErrorCode.PROVIDER_DUPLICATED);
            }
            //Ici on est sur qu'il y aura pas de duplicata donc on peut mettre a jour le providerName
            providerToUpdate.setProviderName(providerDto.getProviderName());
        }

        providerToUpdate.setProviderAcronym(providerDto.getProviderAcronym());
        providerToUpdate.setProviderDescription(providerDto.getProviderDescription());



        log.info("After all verification, the record {} can be done on the DB", providerDto);
        return ProviderDto.fromEntity(providerRepository.save(providerToUpdate));
    }

    @Override
    public ProviderDto findProviderById(Long providerId) {
        if(providerId == null){
            log.info("The argument providerId sent is null");
            throw new NullArgumentException("L'argument envoye est null");
        }
        Optional<Provider> optionalProvider = providerRepository.findProviderById(providerId);
        if(!optionalProvider.isPresent()){
            log.info("There is no provider in the DB with the id precise {}", providerId);
            throw new EntityNotFoundException("Aucun provider n'existe avec le id passe en argument",
                    ErrorCode.PROVIDER_NOT_FOUND);
        }
        return ProviderDto.fromEntity(optionalProvider.get());
    }

    public Boolean isProviderExistWithEmail(String providerEmail) {
        if(!StringUtils.hasLength(providerEmail)){
            log.error("Provider providerEmail is null");
            throw new NullArgumentException("L'Email passe en argument de la methode est null");
        }

        Optional<Provider> optionalProvider = providerRepository.findProviderByProviderEmail(providerEmail);

        return optionalProvider.isPresent()?true:false;
    }

    public Boolean isProviderExistWithNameinPos(String providerName, Long posId) {
        if(!StringUtils.hasLength(providerName)){
            log.error("Provider providerName is null");
            throw new NullArgumentException("Le name passe en argument de la methode est null");
        }

        if(posId == null){
            log.error("Provider posId is null");
            throw new NullArgumentException("Le posId passe en argument de la methode est null");
        }

        Optional<Provider> optionalProvider = providerRepository.findProviderByNameAndPos(providerName, posId);

        return optionalProvider.isPresent()?true:false;
    }

    @Override
    public ProviderDto findProviderByNameofPos(String providerName, Long posId) {
        if(!StringUtils.hasLength(providerName)){
            log.error("Provider providerName is null");
            throw new NullArgumentException("le providerName passe en argument de la methode est null");
        }
        if(posId == null){
            log.error("posId is null");
            throw new NullArgumentException("le posId passe en argument de la methode est null");
        }
        Optional<Provider> optionalProvider = providerRepository.findProviderByNameAndPos(providerName, posId);
        if(!optionalProvider.isPresent()){
            log.error("There is no provider with the name {} in the pointofsale {}",providerName, posId);
            throw new EntityNotFoundException("Aucun provider n'existe avec name indique dans le pointofsale indique");
        }
        return ProviderDto.fromEntity(optionalProvider.get());
    }

    @Override
    public Boolean isProviderUniqueForPos(String providerName, String providerEmail, Long posId) {
        if(!StringUtils.hasLength(providerName)){
            log.error("Provider providerName is null");
            throw new NullArgumentException("le providerName passe en argument de la methode est null");
        }
        if(posId == null){
            log.error("posId is null");
            throw new NullArgumentException("le posId passe en argument de la methode est null");
        }
        Optional<Provider> optionalProvider = providerRepository.findProviderByNameAndPos(providerName, posId);

        //on doit aussi verifier que lemail sera unique dans ladresse
        Optional<Provider> optionalProvider1 = providerRepository.findProviderByProviderEmail(providerEmail);

        return optionalProvider.isEmpty() && optionalProvider1.isEmpty();
    }

    @Override
    public List<ProviderDto> findAllProviderofPos(Long posId) {
        if(posId == null){
            log.error("The posId precised in the method is null");
            throw new NullArgumentException("L'argument de la methode est null");
        }
        Optional<List<Provider>> optionalProviderList = providerRepository.findAllProviderofPos(posId);

        if(!optionalProviderList.isPresent()){
            log.error("There is no pointofsale with the id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'a l'id precise "+posId, ErrorCode.POINTOFSALE_NOT_FOUND);
        }

        return optionalProviderList.get().stream().map(ProviderDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<ProviderDto> findPageProviderofPos(Long posId, int pagenum, int pagesize) {
        if(posId == null){
            log.error("The posId precised in the method is null");
            throw new NullArgumentException("L'argument de la methode est null");
        }
        Optional<Page<Provider>> optionalProviderPage = providerRepository.findPageProviderofPos(posId,
                PageRequest.of(pagenum, pagesize, Sort.Direction.ASC,"providerName"));

        if(!optionalProviderPage.isPresent()){
            log.error("There is no pointofsale with the id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'a l'id precise "+posId, ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalProviderPage.get().map(ProviderDto::fromEntity);
    }

    @Override
    public Boolean isProviderDeleteable(Long providerId) {
        return true;
    }

    @Override
    public Boolean deleteProviderById(Long providerId) {
        if(providerId == null){
            log.error("The argument is null");
            throw new NullArgumentException("L'argument de la methode deleteProvider est null");
        }
        Optional<Provider> optionalProvider = providerRepository.findProviderById(providerId);
        if(!optionalProvider.isPresent()){
            log.error("There is no provider with the precised id {}", providerId);
            throw new EntityNotFoundException("Aucun provider n'existe avec l'id "+providerId, ErrorCode.PROVIDER_NOT_FOUND);
        }
        if(!isProviderDeleteable(providerId)){
            log.error("The provider precised can't be deleted");
            throw new EntityNotDeleteableException("Impossible de supprimer le provider indique ",
                    ErrorCode.PROVIDER_NOT_DELETEABLE);
        }

        providerRepository.delete(optionalProvider.get());
        return true;
    }
}
