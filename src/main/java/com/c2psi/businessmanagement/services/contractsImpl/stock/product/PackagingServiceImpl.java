package com.c2psi.businessmanagement.services.contractsImpl.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.FormatDto;
import com.c2psi.businessmanagement.dtos.stock.product.PackagingDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.Packaging;
import com.c2psi.businessmanagement.models.Pointofsale;
import com.c2psi.businessmanagement.models.Provider;
import com.c2psi.businessmanagement.repositories.pos.pos.PointofsaleRepository;
import com.c2psi.businessmanagement.repositories.stock.product.PackagingRepository;
import com.c2psi.businessmanagement.repositories.stock.provider.ProviderRepository;
import com.c2psi.businessmanagement.services.contracts.stock.product.PackagingService;
import com.c2psi.businessmanagement.validators.stock.product.FormatValidator;
import com.c2psi.businessmanagement.validators.stock.product.PackagingValidator;
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

@Service(value="PackagingServiceV1")
@Slf4j
@Transactional
public class PackagingServiceImpl implements PackagingService {

    PackagingRepository packagingRepository;
    ProviderRepository providerRepository;
    PointofsaleRepository pointofsaleRepository;

    @Autowired
    public PackagingServiceImpl(PackagingRepository packagingRepository, ProviderRepository providerRepository,
                                PointofsaleRepository pointofsaleRepository) {
        this.packagingRepository = packagingRepository;
        this.providerRepository = providerRepository;
        this.pointofsaleRepository = pointofsaleRepository;
    }

    @Override
    public PackagingDto savePackaging(PackagingDto packDto) {
        /*********************************************************
         * Il faut d'abord valider le parametre avec le validator
         */
        List<String> errors = PackagingValidator.validate(packDto);
        if(!errors.isEmpty()){
            log.error("Entity packDto not valid {}", packDto);
            throw new InvalidEntityException("Le packDto passe en argument n'est pas valide: "+errors,
                    ErrorCode.PACKAGING_NOT_VALID, errors);
        }

        /***************************************************************************
         * Il faut se rassurer que le provider preciser est vraiment un provider
         */
        Optional<Provider> optionalProvider = providerRepository.findProviderById(packDto.getPackProviderDto().getId());
        if(!optionalProvider.isPresent()){
            log.error("The indicated provider in the packdto sent does not identify a provider");
            throw new InvalidEntityException("Le packDto passe n'est pas valide car le provider envoye n'existe pas en BD ",
                    ErrorCode.PROVIDER_NOT_FOUND, errors);
        }

        /**********************************************************
         * Il faut se rassurer que le pointofsale existe vraiment
         */
        Optional<Pointofsale> optionalPointofsale = pointofsaleRepository.findPointofsaleById(packDto.getPackPosDto().getId());
        if(!optionalPointofsale.isPresent()){
            log.error("The indicated pointofsale in the packDto sent does not identify a pointofsale");
            throw new InvalidEntityException("Le packDto n'est pas valide car le pointofsale envoye n'existe pas en BD ",
                    ErrorCode.POINTOFSALE_NOT_FOUND, errors);
        }

        /**************************************************************************************************
         * Il faut verifier que le provider indiquer est bel et bien un provider du pointofsale indique
         */
        if(optionalProvider.get().getProviderPos().getId() != optionalPointofsale.get().getId()){
            log.error("The provider indicated must be in the indicated pointofsale");
            throw new InvalidEntityException("Le pointofsale indique pour le packaging doit etre le meme que celui " +
                    "du provider indique", ErrorCode.PACKAGING_NOT_VALID);
        }

        /***************************************************************************
         * On va se rassurer que le packaging sera unique en BD pour ce pointofsale
         */
        if(!isPackagingUniqueinPos(packDto.getPackLabel(), packDto.getPackFirstcolor(),
                packDto.getPackProviderDto().getId(), packDto.getPackPosDto().getId())){
            log.error("There exist another packaging in the same pointofsale with the same characteristics");
            throw new DuplicateEntityException("Un packaging existe deja en BD avec les memes caracteristiques ",
                    ErrorCode.PACKAGING_DUPLICATED);
        }


        log.info("After all verification, the record {} can be saved on the DB", packDto);

        return PackagingDto.fromEntity(
                packagingRepository.save(
                        PackagingDto.toEntity(packDto)
                )
        );
    }

    @Override
    public PackagingDto updatePackaging(PackagingDto packDto) {

        /*********************************************************
         * Il faut d'abord valider le parametre avec le validator
         */
        List<String> errors = PackagingValidator.validate(packDto);
        if(!errors.isEmpty()){
            log.error("Entity packDto not valid {}", packDto);
            throw new InvalidEntityException("Le packDto passe en argument n'est pas valide: "+errors,
                    ErrorCode.PACKAGING_NOT_VALID, errors);
        }

        /***************************************************************************
         * Il faut se rassurer que le provider preciser est vraiment un provider
         */
        Optional<Provider> optionalProvider = providerRepository.findProviderById(packDto.getPackProviderDto().getId());
        if(!optionalProvider.isPresent()){
            log.error("The indicated provider in the packdto sent does not identify a provider");
            throw new InvalidEntityException("Le packDto passe n'est pas valide car le provider envoye n'existe pas en BD ",
                    ErrorCode.PROVIDER_NOT_FOUND, errors);
        }

        /**********************************************************
         * Il faut se rassurer que le pointofsale existe vraiment
         */
        Optional<Pointofsale> optionalPointofsale = pointofsaleRepository.findPointofsaleById(packDto.getPackPosDto().getId());
        if(!optionalPointofsale.isPresent()){
            log.error("The indicated pointofsale in the packDto sent does not identify a pointofsale");
            throw new InvalidEntityException("Le packDto n'est pas valide car le pointofsale envoye n'existe pas en BD ",
                    ErrorCode.POINTOFSALE_NOT_FOUND, errors);
        }

        /**************************************************************************************************
         * Il faut verifier que le provider indiquer est bel et bien un provider du pointofsale indique
         */
        if(optionalProvider.get().getProviderPos().getId() != optionalPointofsale.get().getId()){
            log.error("The provider indicated must be in the indicated pointofsale");
            throw new InvalidEntityException("Le pointofsale indique pour le packaging doit etre le meme que celui " +
                    "du provider indique", ErrorCode.PACKAGING_NOT_VALID);
        }

        /******************************************************************************************************
         * On va se rassurer que l'Id du packaging a update a bien ete precise
         */
        if(packDto.getId() == null){
            log.error("l'Id du packaging a update ne peut etre null");
            throw new InvalidEntityException("L'identifiant du packaging a update est null ce qui n'est pas normal",
                    ErrorCode.PACKAGING_NOT_VALID);
        }
        //ici cela signifie que l'id n'est pas nul. on va donc verifier si le packaging en question existe vraiment
        Optional<Packaging> optionalPackaging = packagingRepository.findPackagingById(packDto.getId());
        if(!optionalPackaging.isPresent()){
            log.error("L'Id du packaging a update n'identifie aucun packaging dans la BD");
            throw new EntityNotFoundException("L'id precise pour le packaging a update nest pas retrouve dans la BD ",
                    ErrorCode.PACKAGING_NOT_VALID);
        }

        //Ici ca veut dire quon a effectivement le packaging a modifier
        Packaging packagingToUpdate = optionalPackaging.get();

        /**********************************
         * On va verifier si cest le pointofsale quon veut modifier car si cest le cas on refuse
         */
        if(!packagingToUpdate.getPackPos().getId().equals(packDto.getPackPosDto().getId())){
            log.error("Le pointofsale d'un packaging ne peut etre modifier");
            throw new InvalidEntityException("Le pointofsale d'un Packagaing ne peut etre modifier pendant la " +
                    "mise a jour du packaging", ErrorCode.PACKAGING_NOT_VALID);
        }


        /***************************
         * On va verifier si c'est le label ou la couleur ou alors le provider quon veut modifier
         */
        if(!packagingToUpdate.getPackProvider().getId().equals(packDto.getPackProviderDto().getId()) ||
        !packagingToUpdate.getPackLabel().equals(packDto.getPackLabel()) ||
        !packagingToUpdate.getPackFirstcolor().equals(packDto.getPackFirstcolor())){
            /**
             * On doit donc verifier si ces nouveaux parametre ne vont pas causer des duplicata
             */

            if(!isPackagingUniqueinPos(packDto.getPackLabel(), packDto.getPackFirstcolor(),
                    packDto.getPackProviderDto().getId(), packDto.getPackPosDto().getId())){
                System.err.println("ici6");
                log.error("There exist another packaging in the same pointofsale with the same characteristics");
                throw new DuplicateEntityException("Un packaging existe deja en BD avec les memes caracteristiques ",
                        ErrorCode.PACKAGING_DUPLICATED);
            }
            //ici on est sur qu'aucun duplicata ne sera cree en BD on peut donc faire les modifications
            packagingToUpdate.setPackLabel(packDto.getPackLabel());
            packagingToUpdate.setPackProvider(ProviderDto.toEntity(packDto.getPackProviderDto()));
            packagingToUpdate.setPackFirstcolor(packDto.getPackFirstcolor());

        }

        //Ici on peut update les parametres qui ne cause pas probleme ceux sur lesquelle aucune contrainte nest fixe
        packagingToUpdate.setPackPrice(packDto.getPackPrice());


        return PackagingDto.fromEntity(packagingRepository.save(packagingToUpdate));
    }

    @Override
    public PackagingDto findPackagingById(Long packId) {
        if(packId == null){
            log.error("The argument of the method findPackagingById is null");
            throw new NullArgumentException("L'argument de la methode findPackagingById est null");
        }
        Optional<Packaging> optionalPackaging = packagingRepository.findPackagingById(packId);
        if(!optionalPackaging.isPresent()){
            log.error("The id {} precised does not identify any Packaging in the DB");
            throw new EntityNotFoundException("L'id precise ne correspond a aucun Packaging dans la BD ",
                    ErrorCode.PACKAGING_NOT_FOUND);
        }

        return PackagingDto.fromEntity(optionalPackaging.get());
    }

    @Override
    public PackagingDto findPackagingByAttributes(String packLabel, String packFirstcolor, Long providerId,
                                                  Long posId) {
        if(providerId == null || posId == null){
            log.error("Some arguments are null and does not allow the method to be exceuted");
            throw new NullArgumentException("certains arguments de la methode isPackagingUnique sont null");
        }

        if(!StringUtils.hasLength(packLabel) || !StringUtils.hasLength(packFirstcolor) ){
            log.error("The precised string for label and color cannot be empty");
            throw new NullArgumentException("la couleur ou le label du packaging ne peut etre vide");
        }

        Optional<Packaging> optionalPackaging = packagingRepository.findPackagingByAttribites(packLabel,
                packFirstcolor, providerId, posId);
        if(optionalPackaging.isPresent()){
            return PackagingDto.fromEntity(optionalPackaging.get());
        }
        log.error("There is no Packaging with the precised parameters");
        throw new EntityNotFoundException("Aucun Packaging en BD ne correspond aux attribuent passe ",
                ErrorCode.PACKAGING_NOT_FOUND);
    }

    @Override
    public Boolean isPackagingUniqueinPos(String packLabel, String packFirstcolor, Long providerId, Long posId) {
        if(providerId == null || posId == null){
            log.error("Some arguments are null and does not allow the method to be exceuted");
            throw new NullArgumentException("certains arguments de la methode isPackagingUnique sont null");
        }

        if(!StringUtils.hasLength(packLabel) || !StringUtils.hasLength(packFirstcolor) ){
            log.error("The precised string for label and color cannot be empty");
            throw new NullArgumentException("la couleur ou le label du packaging ne peut etre vide");
        }

        Optional<Packaging> optionalPackaging = packagingRepository.findPackagingByAttribites(packLabel,
                packFirstcolor, providerId, posId);
        if(!optionalPackaging.isPresent()){
            return true;
        }
        return false;
    }

    @Override
    public List<PackagingDto> findAllPackagingofPos(Long posId) {

        if(posId == null){
            log.error("The posId precise is null");
            throw new NullArgumentException("L'argument posId precise est null");
        }

        Optional<List<Packaging>> optionalPackagingList = packagingRepository.findAllPackagingofPos(posId);
        if(!optionalPackagingList.isPresent()){
            log.error("The posId {} does not identify any pointofsale");
            throw new EntityNotFoundException("Aucun pointofsale avec l'Id precise n'existe en BD ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }

        return optionalPackagingList.get().stream().map(PackagingDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<PackagingDto> findPagePackagingofPos(Long posId, int pagenum, int pagesize) {
        if(posId == null){
            log.error("The posId precise is null");
            throw new NullArgumentException("L'argument posId precise est null");
        }

        Optional<Page<Packaging>> optionalPackagingPage = packagingRepository.findPagePackagingofPos(posId,
                PageRequest.of(pagenum, pagesize, Sort.by(Sort.Direction.ASC, "packLabel", "packPrice")));
        if(!optionalPackagingPage.isPresent()){
            log.error("The posId {} does not identify any pointofsale");
            throw new EntityNotFoundException("Aucun pointofsale avec l'Id precise n'existe en BD ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }

        return optionalPackagingPage.get().map(PackagingDto::fromEntity);
    }

    @Override
    public List<PackagingDto> findAllPackagingofProviderinPos(Long providerId, Long posId) {
        if(posId == null){
            log.error("The posId precise is null");
            throw new NullArgumentException("L'argument posId precise est null");
        }

        if(providerId == null){
            log.error("The providerId precise is null");
            throw new NullArgumentException("L'argument providerId precise est null");
        }

        Optional<List<Packaging>> optionalPackagingList = packagingRepository.findAllPackagingofProviderinPos(providerId, posId);
        if(!optionalPackagingList.isPresent()){
            log.error("The posId {} does not identify any pointofsale");
            throw new EntityNotFoundException("Aucun pointofsale avec l'Id precise n'existe en BD ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }

        return optionalPackagingList.get().stream().map(PackagingDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<PackagingDto> findPagePackagingofProviderinPos(Long providerId, Long posId, int pagenum, int pagesize) {
        if(posId == null){
            log.error("The posId precise is null");
            throw new NullArgumentException("L'argument posId precise est null");
        }

        if(providerId == null){
            log.error("The providerId precise is null");
            throw new NullArgumentException("L'argument providerId precise est null");
        }

        Optional<Page<Packaging>> optionalPackagingPage = packagingRepository.findPagePackagingofProviderinPos(providerId, posId,
                PageRequest.of(pagenum, pagesize, Sort.by(Sort.Direction.ASC, "packLabel", "packPrice")));
        if(!optionalPackagingPage.isPresent()){
            log.error("The posId {} does not identify any pointofsale");
            throw new EntityNotFoundException("Aucun pointofsale avec l'Id precise n'existe en BD ",
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }

        return optionalPackagingPage.get().map(PackagingDto::fromEntity);
    }

    @Override
    public Boolean isPackagingDeleteable(Long packId) {
        return true;
    }

    @Override
    public Boolean deletePackagingById(Long packId) {
        if(packId == null){
            log.error("The posId precise is null");
            throw new NullArgumentException("L'argument posId precise est null");
        }

        Optional<Packaging> optionalPackaging = packagingRepository.findPackagingById(packId);
        if(!optionalPackaging.isPresent()){
            log.error("There is no Packaging with the precised id "+packId);
            throw new EntityNotFoundException("Aucun packaging n'existe avec l'id precise ", ErrorCode.PACKAGING_NOT_FOUND);
        }
        if(isPackagingDeleteable(packId)){
            packagingRepository.delete(optionalPackaging.get());
            return true;
        }
        return false;
    }
}
