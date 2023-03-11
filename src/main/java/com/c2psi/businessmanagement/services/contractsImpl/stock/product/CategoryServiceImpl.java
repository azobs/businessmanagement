package com.c2psi.businessmanagement.services.contractsImpl.stock.product;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.price.CurrencyDto;
import com.c2psi.businessmanagement.dtos.stock.product.CategoryDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.Category;
import com.c2psi.businessmanagement.models.Currency;
import com.c2psi.businessmanagement.models.Pointofsale;
import com.c2psi.businessmanagement.repositories.pos.pos.PointofsaleRepository;
import com.c2psi.businessmanagement.repositories.stock.product.CategoryRepository;
import com.c2psi.businessmanagement.services.contracts.stock.product.CategoryService;
import com.c2psi.businessmanagement.validators.stock.product.CategoryValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service(value="CategoryServiceV1")
@Slf4j
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private PointofsaleRepository posRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(PointofsaleRepository posRepository,
                               CategoryRepository categoryRepository) {
        this.posRepository = posRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDto> findAllCategory(PointofsaleDto posDto) {
        return null;
    }

    @Override
    public CategoryDto saveCategory(CategoryDto catDto) {
        List<String> errors = CategoryValidator.validate(catDto);
        if(!errors.isEmpty()){
            log.error("Entity Category not valid {}", catDto);
            throw new InvalidEntityException("Le Category passe en argument n'est pas valide:  "+errors,
                    ErrorCode.CATEGORY_NOT_VALID, errors);
        }
        //Il faut deja se rassurer que le Pointofsale associe avec le category existe effectivement
        if(Optional.ofNullable(catDto.getCatPosDto()).isPresent()){
            Optional<Pointofsale> optionalPos = posRepository.findPointofsaleById(catDto.getCatPosDto().getId());
            if(!optionalPos.isPresent()){
                log.error("The Pointofsale owner of the category precised does not exist in the system");
                throw new EntityNotFoundException("Le pointofsale associe avec la category n'existe pas en BD"
                        , ErrorCode.POINTOFSALE_NOT_FOUND);
            }
        }
        else{
            /***
             * Ce cadre ne devrait jamais etre execute puisque si le Pointofsale d'une catagorie n'est pas precise
             * ca ne passe meme pas la validation
             */
            log.error("The pointofsale owner of the category has not been precised");
            throw new EntityNotFoundException("Aucun pointofsale n'est associe avec le category"
                    , ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        //IL faut verifier la futur unicite de la catagorie dans le pointofsale
        if(!this.isCatageryUnique(catDto.getCatCode(), catDto.getCatPosDto())){
            //Si c'est true alors la category sera unique dans le Pointofsale donc le non signifie que ca ne va pas etre unique
            throw new DuplicateEntityException("Une Categorie existe deja dans le Pointofsale precise avec  " +
                    "le meme code :", ErrorCode.CATEGORY_DUPLICATED);
        }
        //Il faut verifier si une categorie parent a ete precise.

        log.info("After all verification, the record {} can be saved in the DB", catDto);

        return CategoryDto.fromEntity(
                categoryRepository.save(
                        CategoryDto.toEntity(catDto)
                )
        );
    }

    @Override
    public CategoryDto findCategoryByCode(String catCode, PointofsaleDto posDto) {
        if(!isCatageryExist(catCode, posDto)){
            throw new EntityNotFoundException("Aucune catagorie n'existe avec ce code dans le point de vente specifie"
                    , ErrorCode.CATEGORY_NOT_FOUND);
        }
        Optional<Category> optionalCategory = categoryRepository.findCategoryInPointofsaleByCode(catCode, posDto.getId());
        return CategoryDto.fromEntity(optionalCategory.get());
    }

    @Override
    public CategoryDto findCategoryById(Long catId) {
        if(catId == null){
            log.error("catId is null");
            throw new NullArgumentException("La category ID passe en argument de la methode est null");
        }
        Optional<Category> optionalCategory = categoryRepository.findCategoryById(catId);

        if(optionalCategory.isPresent()){
            return CategoryDto.fromEntity(optionalCategory.get());
        }
        else{
            throw new EntityNotFoundException("Aucune Category avec l'id "+catId
                    +" n'a été trouve dans la BDD", ErrorCode.CATEGORY_NOT_FOUND);
        }

    }

    @Override
    public Boolean categoryIsUnique(String catCode, PointofsaleDto posDto) {
        return null;
    }

    @Override
    public Boolean isCatageryEmpty(CategoryDto catDto) {
        return null;
    }

    @Override
    public Boolean isCatageryUnique(String catCode, PointofsaleDto posDto) {
        if(!StringUtils.hasLength(catCode)){
            log.error("Le category catCode is null");
            throw new NullArgumentException("le catCode passe en argument de la methode est null");
        }
        if(posDto == null){
            log.error("Pointofsale is null");
            throw new NullArgumentException("Le Pointofsale a laquelle appartient la category est null");
        }
        Optional<Category> optionalCategory = categoryRepository.findCategoryInPointofsaleByCode(catCode, posDto.getId());
        return optionalCategory.isPresent()?false:true;
    }

    public Boolean isCatageryExist(String catCode, PointofsaleDto posDto) {
        if(!StringUtils.hasLength(catCode)){
            log.error("Le category catCode is null");
            throw new NullArgumentException("le catCode passe en argument de la methode est null");
        }
        if(posDto == null){
            log.error("Pointofsale is null");
            throw new NullArgumentException("Le Pointofsale a laquelle appartient la category est null");
        }
        Optional<Category> optionalCategory = categoryRepository.findCategoryInPointofsaleByCode(catCode, posDto.getId());
        return optionalCategory.isPresent()?true:false;
    }

    @Override
    public Boolean deleteCategoryById(Long catId) {
        return null;
    }
}
