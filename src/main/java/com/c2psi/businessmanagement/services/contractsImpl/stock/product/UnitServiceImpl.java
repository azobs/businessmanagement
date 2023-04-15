package com.c2psi.businessmanagement.services.contractsImpl.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.UnitDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.Pointofsale;
import com.c2psi.businessmanagement.models.Unit;
import com.c2psi.businessmanagement.repositories.pos.pos.PointofsaleRepository;
import com.c2psi.businessmanagement.repositories.stock.product.UnitRepository;
import com.c2psi.businessmanagement.services.contracts.stock.product.UnitService;
import com.c2psi.businessmanagement.validators.stock.product.UnitValidator;
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

@Service(value="UnitServiceV1")
@Slf4j
@Transactional
public class UnitServiceImpl implements UnitService {

    private PointofsaleRepository posRepository;
    private UnitRepository unitRepository;

    @Autowired
    public UnitServiceImpl(PointofsaleRepository posRepository, UnitRepository unitRepository) {
        this.posRepository = posRepository;
        this.unitRepository = unitRepository;
    }

    @Override
    public UnitDto saveUnit(UnitDto unitDto) {
        /********************************************************
         * Il faut dabord valider le Dto ainsi recu en parametre
         */
        List<String> errors = UnitValidator.validate(unitDto);
        if(!errors.isEmpty()){
            log.error("Entity unitDto not valid {}", unitDto);
            throw new InvalidEntityException("Le unitDto passe en argument n'est pas valide: "+errors,
                    ErrorCode.UNIT_NOT_VALID, errors);
        }

        /*****************
         * Verify the existence of the pointofsale
         ********************************************/
        Optional<Pointofsale> optionalPointofsale = posRepository.findPointofsaleById(unitDto.getUnitPosDto().getId());
        if(!optionalPointofsale.isPresent()){
            log.error("Entity unitDto not valid because the pointofsale does not exist{}", unitDto);
            throw new InvalidEntityException("Le unitDto passe en argument n'est pas valide puisque son pointofsale " +
                    "n'est pas existant: "+errors, ErrorCode.UNIT_NOT_FOUND, errors);
        }

        /***************************************************
         * Verify the unicity of the unit in the pos
         */
        if(!isUnitUniqueInPos(unitDto.getUnitName(), unitDto.getUnitPosDto().getId())){
            log.error("Another unit already exist with the same name {}", unitDto);
            throw new DuplicateEntityException("There is another unit in the same pointofsale with the same name ",
                    ErrorCode.UNIT_DUPLICATED);
        }

        log.info("After all verification we can save the unit {} in DB without any problem ", unitDto);

        return UnitDto.fromEntity(
                unitRepository.save(
                        UnitDto.toEntity(unitDto)
                )
        );
    }

    public Boolean isUnitExistInPosWith(String unitName, Long posId){
        if(!StringUtils.hasLength(unitName)){
            log.error("Unit name is null or empty");
            throw new NullArgumentException("le unitName passe en argument de la methode est null");
        }
        if(posId == null){
            log.error("posId is null");
            throw new NullArgumentException("Le posId passe en argument de la methode est null");
        }
        Optional<Unit> optionalUnit = unitRepository.findUnitByUnitnameAndPos(unitName, posId);

        return optionalUnit.isPresent()?true:false;
    }

    @Override
    public UnitDto updateUnit(UnitDto unitDto) {
        /********************************************************
         * Il faut dabord valider le Dto ainsi recu en parametre
         *
         */
        List<String> errors = UnitValidator.validate(unitDto);
        if(!errors.isEmpty()){
            log.error("Entity unitDto not valid {}", unitDto);
            throw new InvalidEntityException("Le unitDto passe en argument n'est pas valide: "+errors,
                    ErrorCode.UNIT_NOT_VALID, errors);
        }

        /*****************
         * Verify the existence of the pointofsale
         ********************************************/
        Optional<Pointofsale> optionalPointofsale = posRepository.findPointofsaleById(unitDto.getUnitPosDto().getId());
        if(!optionalPointofsale.isPresent()){
            log.error("Entity unitDto not valid because the pointofsale does not exist{}", unitDto);
            throw new InvalidEntityException("Le unitDto passe en argument n'est pas valide puisque son pointofsale " +
                    "n'est pas existant: "+errors, ErrorCode.UNIT_NOT_FOUND, errors);
        }

        /*************************************************************************************
         * Il faut dabord verifier que l'id de l'unit a verifier a bel et bien ete renseigne
         */
        if(unitDto.getId() == null){
            log.error("Entity unitDto not valid for update because the id of the format that will be modified is null {}", unitDto);
            throw new InvalidEntityException("Le unitDto passe en argument n'est pas valide car son id est null ",
                    ErrorCode.UNIT_NOT_VALID);
        }

        /*****************************************************
         * Il faut dabord rechercher l'objet Unit a modifier
         */
        Optional<Unit> optionalUnit = unitRepository.findUnitById(unitDto.getId());
        if(!optionalUnit.isPresent()){
            log.error("The unit to update does not exist in the DB");
            throw new EntityNotFoundException("Le unit a update n'existe pas dans la BD ", ErrorCode.UNIT_NOT_FOUND);
        }
        Unit unitToUpdate = optionalUnit.get();
        /*****
         * Verify if it is the modification of unitName because in that case
         * duplication object can happen
         */
        if(!unitToUpdate.getUnitName().equals(unitDto.getUnitName())){
            /***
             * Alors on va aussi modifier le unitName. Il faut donc verifier que le nouveau ne vas
             * pas creer des duplicata
             */
            if(!isUnitUniqueInPos(unitDto.getUnitName(), unitDto.getUnitPosDto().getId())){
                log.error("Another unit already exist with the same name {}", unitDto);
                throw new DuplicateEntityException("There is another unit in the same pointofsale with the same name ",
                        ErrorCode.UNIT_DUPLICATED);
            }
            unitToUpdate.setUnitName(unitDto.getUnitName());
        }
        log.info("After all verification, the record {} can be updated on the DB", unitDto);
        unitToUpdate.setUnitAbbreviation(unitDto.getUnitAbbreviation());

        return UnitDto.fromEntity(unitRepository.save(unitToUpdate));
    }

    @Override
    public UnitDto findUnitById(Long unitId) {
        if(unitId == null){
            log.error("unitId is null");
            throw new NullArgumentException("Le unitId passe en argument de la methode est null");
        }
        Optional<Unit> optionalUnit = unitRepository.findUnitById(unitId);
        if(!optionalUnit.isPresent()){
            log.error("There is no unit in DB with the precised id {}", unitId);
            throw new EntityNotFoundException("Aucun Unit n'existe avec l'id "+ unitId+" passe en argument ",
                    ErrorCode.UNIT_NOT_FOUND);
        }
        return UnitDto.fromEntity(optionalUnit.get());
    }

    @Override
    public UnitDto findUnitByUnitnameAndPos(String unitName, Long posId) {
        if(!StringUtils.hasLength(unitName)){
            log.error("Unit name is null or empty");
            throw new NullArgumentException("le unitName passe en argument de la methode est null");
        }
        if(posId == null){
            log.error("posId is null");
            throw new NullArgumentException("Le posId passe en argument de la methode est null");
        }
        Optional<Unit> optionalUnit = unitRepository.findUnitByUnitnameAndPos(unitName, posId);
        if(!optionalUnit.isPresent()){
            throw new EntityNotFoundException("Aucun unit nexiste avec le name "+unitName+ " dans le pointofsale d'ID " +
                    " "+posId, ErrorCode.UNIT_NOT_FOUND);
        }
        return UnitDto.fromEntity(optionalUnit.get());
    }

    @Override
    public Boolean isUnitUniqueInPos(String unitName, Long posId) {
        if(!StringUtils.hasLength(unitName)){
            log.error("Unit name is null or empty");
            throw new NullArgumentException("le unitName passe en argument de la methode est null");
        }
        if(posId == null){
            log.error("posId is null");
            throw new NullArgumentException("Le posId passe en argument de la methode est null");
        }
        return isUnitExistInPosWith(unitName, posId)?false:true;
    }

    @Override
    public List<UnitDto> findListofUnitInPos(Long posId) {
        if(posId == null){
            log.error("posId is null");
            throw new NullArgumentException("Le posId passe en argument de la methode est null");
        }
        Optional<List<Unit>> optionalUnitList = unitRepository.findAllUnitInPos(posId);
        if(!optionalUnitList.isPresent()){
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'ID "+posId,
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalUnitList.get().stream().map(UnitDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<UnitDto> findPageofUnitInPos(Long posId, int pagenum, int pagesize) {
        if(posId == null){
            log.error("posId is null");
            throw new NullArgumentException("Le posId passe en argument de la methode est null");
        }

        Optional<Page<Unit>> optionalUnitPage = unitRepository.findPageofUnitInPos(posId,
                PageRequest.of(pagenum, pagesize, Sort.by(Sort.Direction.ASC, "unitName")));
        if(!optionalUnitPage.isPresent()){
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'ID "+posId,
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalUnitPage.get().map(UnitDto::fromEntity);
    }

    @Override
    public Boolean deleteUnitById(Long unitId) {
        if(unitId == null){
            log.error("unitId is null");
            throw new NullArgumentException("Le unitId passe en argument de la methode est null");
        }
        Optional<Unit> optionalUnit = unitRepository.findUnitById(unitId);
        if(!optionalUnit.isPresent()){
            log.error("There is no Unit in DB with the id {}", unitId);
            throw new EntityNotFoundException("Aucun unit n'existe avec l'id "+unitId, ErrorCode.UNIT_NOT_FOUND);
        }

        if(!isUnitDeleteable(unitId)){
            log.error("The unit with id = {} can't be deleteable now due to the fact it is link with another object ", unitId);
            throw new EntityNotDeleteableException("Le unit d'id "+ unitId+" ne peut etre supprime car deja lie a un " +
                    "autre element ", ErrorCode.UNIT_NOT_DELETEABLE);
        }
        unitRepository.delete(optionalUnit.get());
        return true;
    }

    @Override
    public Boolean isUnitDeleteable(Long unitId) {
        return true;
    }
}
