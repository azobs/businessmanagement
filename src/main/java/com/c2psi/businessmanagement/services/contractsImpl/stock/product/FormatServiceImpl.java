package com.c2psi.businessmanagement.services.contractsImpl.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.FormatDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.Format;
import com.c2psi.businessmanagement.models.Pointofsale;
import com.c2psi.businessmanagement.repositories.pos.pos.PointofsaleRepository;
import com.c2psi.businessmanagement.repositories.stock.product.FormatRepository;
import com.c2psi.businessmanagement.repositories.stock.product.ProductRepository;
import com.c2psi.businessmanagement.services.contracts.stock.product.FormatService;
import com.c2psi.businessmanagement.validators.stock.product.FormatValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value="FormatServiceV1")
@Slf4j
@Transactional
public class FormatServiceImpl implements FormatService {

    private PointofsaleRepository posRepository;
    private ProductRepository productRepository;
    private FormatRepository formatRepository;

    @Autowired
    public FormatServiceImpl(PointofsaleRepository posRepository, ProductRepository productRepository,
                             FormatRepository formatRepository) {
        this.posRepository = posRepository;
        this.productRepository = productRepository;
        this.formatRepository = formatRepository;
    }

    @Override
    public FormatDto saveFormat(FormatDto formatDto) {
        /*****
         * Il faut valider le formatDto passer en parametre
         */
        List<String> errors = FormatValidator.validate(formatDto);
        if(!errors.isEmpty()){
            log.error("Entity formatDto not valid {}", formatDto);
            throw new InvalidEntityException("Le formatDto passe en argument n'est pas valide: "+errors,
                    ErrorCode.FORMAT_NOT_VALID, errors);
        }

        /*****************
         * Verify the existence of the pointofsale
         ********************************************/
        Optional<Pointofsale> optionalPointofsale = posRepository.findPointofsaleById(formatDto.getFormatPosDto().getId());
        if(!optionalPointofsale.isPresent()){
            log.error("Entity formatDto not valid because the pointofsale does not exist{}", formatDto);
            throw new InvalidEntityException("Le formatDto passe en argument n'est pas valide puisque son pointofsale " +
                    "n'est pas existant: "+errors, ErrorCode.POINTOFSALE_NOT_FOUND, errors);
        }

        /***************************************************
         * Verify the unicity of the format
         */
        if(isFormatExistInPosWith(formatDto.getFormatName(), formatDto.getFormatCapacity(),
                formatDto.getFormatPosDto().getId())){
            log.error("An entity format already exist with the same characteristic in the DB {}", formatDto);
            throw new DuplicateEntityException("Un format existe deja en BD avec les memes caracteristiques ",
                    ErrorCode.FORMAT_DUPLICATED);
        }

        /***********
         * Tout est bon et on peut simplement faire l'enregistrement dans la base de donnee
         */
        log.info("After all verification, the record {} can be saved on the DB", formatDto);

        return FormatDto.fromEntity(
                formatRepository.save(
                        FormatDto.toEntity(formatDto)
                )
        );
    }

    @Override
    public FormatDto updateFormat(FormatDto formatDto) {
        /*****
         * Il faut valider le formatDto passer en parametre
         */
        List<String> errors = FormatValidator.validate(formatDto);
        if(!errors.isEmpty()){
            log.error("Entity formatDto not valid {}", formatDto);
            throw new InvalidEntityException("Le formatDto passe en argument n'est pas valide: "+errors,
                    ErrorCode.FORMAT_NOT_VALID, errors);
        }

        /*****
         * On doit verifier l'existence du format que l'on veut modifier
         */
        if(formatDto.getId() == null){
            log.error("Entity formatDto not valid for update because the id of the format that will be modified is null {}", formatDto);
            throw new InvalidEntityException("Le formatDto passe en argument n'est pas valide car son id est null ",
                    ErrorCode.FORMAT_NOT_VALID);
        }
        /******
         * Il faut verifier que le format a modifier existe vraiment en BD
         */
        if(!isFormatExistWithId(formatDto.getId())){
            log.error("There is no format in DB with the ID precise in the fornatDto sent {}", formatDto);
            throw new EntityNotFoundException("Aucun format n'existe avec l'Id passe dans le formatDto envoye");
        }

        Format formatToUpdate = FormatDto.toEntity(this.findFormatById(formatDto.getId()));
        /************************
         * On verifie d'abord si cest le formatCapacity ou le formatName quon veut modifier
         * On verifie qu'apres modification le format va rester unique dans la BD c'est a dire que le triplet
         * formatName, formatCapacity et posId va rester unique
         */
        if(!formatDto.getFormatName().equals(formatToUpdate.getFormatName()) ||
                formatDto.getFormatCapacity().doubleValue() == formatToUpdate.getFormatCapacity().doubleValue()){
            if(isFormatExistInPosWith(formatDto.getFormatName(), formatDto.getFormatCapacity(),
                    formatDto.getFormatPosDto().getId())){
                log.error("An entity format already exist with the same characteristic in the DB {}", formatDto);
                throw new DuplicateEntityException("Un format existe deja en BD avec les memes caracteristiques ",
                        ErrorCode.FORMAT_DUPLICATED);
            }
            log.info("After all verification, the record {} can be updated on the DB", formatDto);
            formatToUpdate.setFormatCapacity(formatDto.getFormatCapacity());
            formatToUpdate.setFormatName(formatDto.getFormatName());
        }

        return FormatDto.fromEntity(formatRepository.save(formatToUpdate));
    }

    @Override
    public FormatDto findFormatById(Long formatId) {
        if(formatId == null){
            log.error("formatId is null");
            throw new NullArgumentException("Le formatId passe en argument de la methode est null");
        }
        Optional<Format> optionalFormat = formatRepository.findFormatById(formatId);
        if(optionalFormat.isPresent()){
            return FormatDto.fromEntity(optionalFormat.get());
        }
        throw new EntityNotFoundException("Aucun format nexiste dans la BD avec l'Id formatId="+formatId);
    }

    @Override
    public FormatDto findFormatInPointofsaleByFullcharacteristic(String format_name, BigDecimal formatCapacity,
                                                                 Long posId) {
        if(!isFormatExistInPosWith(format_name, formatCapacity, posId)){
            throw new EntityNotFoundException("Aucun format nexiste en BD avec les characteristics precise a savoir " +
                    "format_name= "+format_name+" formatCapacity= "+formatCapacity+" posId= "+posId);
        }
        Optional<Format> optionalFormat = formatRepository.findFormatInPointofsaleByFullcharacteristic(format_name,
                formatCapacity, posId);
        if(optionalFormat.isPresent()){
            return FormatDto.fromEntity(optionalFormat.get());
        }
        return null;
    }

    @Override
    public Boolean isFormatUniqueInPos(String format_name, BigDecimal formatCapacity, Long posId) {
        if(!StringUtils.hasLength(format_name)){
            log.error("Format name is null or empty");
            throw new NullArgumentException("le format_name passe en argument de la methode est null");
        }
        if(formatCapacity == null){
            log.error("formatCapacity is null");
            throw new NullArgumentException("Le formatCapacity passe en argument de la methode est null");
        }
        if(posId == null){
            log.error("posId is null");
            throw new NullArgumentException("Le posId passe en argument de la methode est null");
        }

        Optional<Format> optionalFormat = formatRepository.findFormatInPointofsaleByFullcharacteristic(format_name,
                formatCapacity, posId);

        return optionalFormat.isPresent()?false:true;
    }

    @Override
    public Boolean isFormatExistInPosWith(String format_name, BigDecimal formatCapacity, Long posId) {
        if(!StringUtils.hasLength(format_name)){
            log.error("Format name is null or empty");
            throw new NullArgumentException("le format_name passe en argument de la methode est null");
        }
        if(formatCapacity == null){
            log.error("formatCapacity is null");
            throw new NullArgumentException("Le formatCapacity passe en argument de la methode est null");
        }
        if(posId == null){
            log.error("posId is null");
            throw new NullArgumentException("Le posId passe en argument de la methode est null");
        }

        Optional<Format> optionalFormat = formatRepository.findFormatInPointofsaleByFullcharacteristic(format_name,
                formatCapacity, posId);

        return optionalFormat.isPresent()?true:false;
    }

    @Override
    public Boolean isFormatExistWithId(Long formatId) {
        if(formatId == null){
            log.error("formatId is null");
            throw new NullArgumentException("Le formatId passe en argument de la methode est null");
        }
        Optional<Format> optionalFormat = formatRepository.findFormatById(formatId);
        return optionalFormat.isPresent()?true:false;
    }

    @Override
    public Boolean deleteFormatById(Long formatId) {
        if(formatId == null){
            log.error("formatId is null");
            throw new NullArgumentException("Le formatId passe en argument de la methode est null");
        }
        Optional<Format> optionalFormat = formatRepository.findFormatById(formatId);

        if(optionalFormat.isPresent()){
            if(isFormatDeleteable(formatId)){
                formatRepository.delete(optionalFormat.get());
                return true;
            }
            log.error("The entity {} is not deleteable because it encompasses some other elements ", optionalFormat.get());
            throw new EntityNotDeleteableException("Ce format ne peut etre supprime car est deja associe a des " +
                    "produits formates en BD. Il faut d'abord supprimer ces produits formates. ", ErrorCode.FORMAT_NOT_DELETEABLE);
        }
        throw new EntityNotFoundException("Aucun format n'existe avec l'id passe en argument "+formatId);
    }

    @Override
    public Boolean isFormatDeleteable(Long formatId) {
        return true;
    }

    @Override
    public List<FormatDto> findAllFormatInPos(Long posId) {
        if(posId == null){
            log.error("posId is null");
            throw new NullArgumentException("Le posId passe en argument de la methode est null");
        }
        Optional<List<Format>> optionalFormatList = formatRepository.findAllFormatInPos(posId);
        if(!optionalFormatList.isPresent()){
            throw new EntityNotFoundException("There is no pointofsale associated with the id "+posId);
        }

        return optionalFormatList.get().stream().map(FormatDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<FormatDto> findPageFormatInPos(Long posId, int pagenum, int pagesize) {

        if(posId == null){
            log.error("posId is null");
            throw new NullArgumentException("Le posId passe en argument de la methode est null");
        }
        Optional<Page<Format>> optionalFormatPage = formatRepository.findPageofFormatInPos(posId,
                PageRequest.of(pagenum, pagesize, Sort.by(Sort.Direction.ASC, "formatName", "formatCapacity")));
        if(!optionalFormatPage.isPresent()){
            throw new EntityNotFoundException("There is no pointofsale associated with the id "+posId);
        }

        return optionalFormatPage.get().map(FormatDto::fromEntity);
    }






}
