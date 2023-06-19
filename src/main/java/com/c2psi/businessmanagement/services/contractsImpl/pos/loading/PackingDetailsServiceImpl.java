package com.c2psi.businessmanagement.services.contractsImpl.pos.loading;

import com.c2psi.businessmanagement.dtos.pos.loading.PackingDetailsDto;
import com.c2psi.businessmanagement.exceptions.DuplicateEntityException;
import com.c2psi.businessmanagement.exceptions.ErrorCode;
import com.c2psi.businessmanagement.exceptions.InvalidEntityException;
import com.c2psi.businessmanagement.models.Loading;
import com.c2psi.businessmanagement.models.Packaging;
import com.c2psi.businessmanagement.repositories.pos.loading.LoadingRepository;
import com.c2psi.businessmanagement.repositories.pos.loading.PackingDetailsRepository;
import com.c2psi.businessmanagement.repositories.stock.product.PackagingRepository;
import com.c2psi.businessmanagement.services.contracts.pos.loading.PackingDetailsService;
import com.c2psi.businessmanagement.validators.pos.loading.PackingDetailsValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service(value="PackingDetailsServiceV1")
@Slf4j
@Transactional
public class PackingDetailsServiceImpl implements PackingDetailsService {

    private PackagingRepository packagingRepository;
    private LoadingRepository loadingRepository;
    private PackingDetailsRepository packingDetailsRepository;

    @Autowired
    public PackingDetailsServiceImpl(PackagingRepository packagingRepository, LoadingRepository loadingRepository,
                                     PackingDetailsRepository packingDetailsRepository) {
        this.packagingRepository = packagingRepository;
        this.loadingRepository = loadingRepository;
        this.packingDetailsRepository = packingDetailsRepository;
    }

    @Override
    public PackingDetailsDto savePackingDetails(PackingDetailsDto pdDto) {
        /***************************************************
         * IL faut valider le parametre pris en argument
         */
        List<String> errors = PackingDetailsValidator.validate(pdDto);
        if(!errors.isEmpty()){
            log.error("Entity packingDetailsDto not valid {}", pdDto);
            throw new InvalidEntityException("Le packingDetailsDto passe en argument n'est pas valide:  ",
                    ErrorCode.PACKINGDETAILS_NOT_VALID, errors);
        }

        /*******************************************************
         * On se rassure que le packaging existe bel et bien en BD
         */
        if(pdDto.getPdPackagingDto().getId() == null){
            log.error("The packaging id associated is null");
            throw new InvalidEntityException("L'id du packaging associe au packingDetails est null",
                    ErrorCode.PACKINGDETAILS_NOT_VALID);
        }
        //Ici on est sur que l'id du packaging a ete precise mais est ce que ca existe en BD
        Optional<Packaging> optionalPackaging = packagingRepository.findPackagingById(pdDto.getPdPackagingDto().getId());
        if(!optionalPackaging.isPresent()){
            log.error("There is no packaging with the id precise ");
            throw new InvalidEntityException("Aucun packaging n'existe en BD avec l'id precise ",
                    ErrorCode.PACKINGDETAILS_NOT_VALID);
        }
        //ici on est sur le packaging existe bel et bien dans la BD

        /***********************************************************
         * On se rassure que le loading existe bel et bien en BD
         */
        if(pdDto.getPdLoadingDto().getId() == null){
            log.error("The loading id associated is null");
            throw new InvalidEntityException("L'id du loading associe au LoadingDetails est null",
                    ErrorCode.PACKINGDETAILS_NOT_VALID);
        }
        //Ici on est sur que l'id du loading a ete precise mais est ce que ca existe en BD
        Optional<Loading> optionalLoading = loadingRepository.findLoadingById(pdDto.getPdLoadingDto().getId());
        if(!optionalLoading.isPresent()){
            log.error("There is no loading with the id {} precise ", pdDto.getPdLoadingDto().getId());
            throw new InvalidEntityException("Aucun loading n'existe en BD avec l'id precise ",
                    ErrorCode.PACKINGDETAILS_NOT_VALID);
        }
        //Ici on est sur que le loading existe bel et bien en BD

        /***************************************************
         * On se rassure qu'aucun packingdetails ne concerne
         * le packaging indiaue dans le loading
         */
        if(!isPackingDetailsUniqueinLoading(pdDto.getPdPackagingDto().getId(), pdDto.getPdLoadingDto().getId())){
            log.error("It already exist in the loading precise a details concerned the packaging precised");
            throw new DuplicateEntityException("Il existe deja un details concernant le packaging indique dans le " +
                    "loading indique ", ErrorCode.PACKINGDETAILS_DUPLICATED);
        }

        //On peut donc faire l'enregistrement du details en BD

        log.info("After all verification the record {} can be register in the DB", pdDto);
        return PackingDetailsDto.fromEntity(
                packingDetailsRepository.save(
                        PackingDetailsDto.toEntity(pdDto)
                )
        );
    }

    @Override
    public PackingDetailsDto updatePackingDetails(PackingDetailsDto pdDto) {
        return null;
    }

    @Override
    public PackingDetailsDto findPackingDetailsById(Long pdId) {
        return null;
    }

    @Override
    public PackingDetailsDto findPackingDetailsofArticleinLoading(Long packagingId, Long loadingId) {
        return null;
    }

    @Override
    public List<PackingDetailsDto> findAllPackingDetailsinLoading(Long loadingId) {
        return null;
    }

    @Override
    public Page<PackingDetailsDto> findPagePackingDetailsinLoading(Long loadingId, int pagenum, int pagesize) {
        return null;
    }

    @Override
    public Boolean isPackingDetailsUniqueinLoading(Long packagingId, Long loadingId) {
        return null;
    }

    @Override
    public Boolean isPackingDetailsDeleatable(Long pdId) {
        return null;
    }

    @Override
    public Boolean deletePackingDetailsById(Long pdId) {
        return null;
    }
}
