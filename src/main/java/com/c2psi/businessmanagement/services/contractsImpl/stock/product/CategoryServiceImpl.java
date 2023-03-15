package com.c2psi.businessmanagement.services.contractsImpl.stock.product;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.product.CategoryDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.Category;
import com.c2psi.businessmanagement.models.Pointofsale;
import com.c2psi.businessmanagement.models.Product;
import com.c2psi.businessmanagement.repositories.pos.pos.PointofsaleRepository;
import com.c2psi.businessmanagement.repositories.stock.product.CategoryRepository;
import com.c2psi.businessmanagement.repositories.stock.product.ProductRepository;
import com.c2psi.businessmanagement.services.contracts.pos.pos.PointofsaleService;
import com.c2psi.businessmanagement.services.contracts.stock.product.CategoryService;
import com.c2psi.businessmanagement.validators.stock.product.CategoryValidator;
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

@Service(value="CategoryServiceV1")
@Slf4j
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private PointofsaleRepository posRepository;

    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;

    @Autowired
    public CategoryServiceImpl(PointofsaleRepository posRepository,
                               CategoryRepository categoryRepository,
                               ProductRepository prodRepository) {
        this.posRepository = posRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = prodRepository;
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
            log.error("A category with the same code exist already in tat pointofsale");
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

    /****************************************************************************************************************
     * Cette methode permet de modifier ou de mettre a jour les donnes d'une category de produit d'un pointofsale
     * qui existe deja dans le systeme. Elle lance une exception de type:
     *      +EntityNotFoundException si:
     *          -La categorie a mettre a jour n'existe pas
     *          -Le pointofsale de la categorie n'existe pas
     *      +DuplicateEntityException si:
     *          -Le nouveau code de la categorie identifie deja une categorie dans le meme pointofsale
     *      +InvalidEntityException si:
     *          -La categorie a mettre a jour n'est pas valide
     *          -La Pointofsale de la categorie a mettre a jour n'est pas egale a celui de la mise a jour propose(donc
     *          l'utilisateur essaye de mettre a jour la categorie en changeant le pointofsale).
     *
     * @param catDto
     * @return
     */
    @Override
    public CategoryDto updateCategory(CategoryDto catDto) {
        List<String> errors = CategoryValidator.validate(catDto);
        if(!errors.isEmpty()){
            log.error("Entity Category not valid {}", catDto);
            throw new InvalidEntityException("Le Category passe en argument n'est pas valide:  "+errors,
                    ErrorCode.CATEGORY_NOT_VALID, errors);
        }

        if(catDto.getId() == null){
            log.error("Entity Category not valid {}", catDto);
            throw new InvalidEntityException("Le Category passe en argument n'est pas valide car Id inexistant ",
                    ErrorCode.CATEGORY_NOT_VALID);
        }

        if(!isCategoryExist(catDto.getId())){
            log.error("Entity Category not found {}", catDto);
            throw new EntityNotFoundException("La Category passe en argument n'est pas valide:  "+errors,
                    ErrorCode.CATEGORY_NOT_VALID, errors);
        }

        /*******************************
         * Rechercher la categorie a mettre a jour
         */
        CategoryDto categoryDtoToUpdate = findCategoryById(catDto.getId());

        categoryDtoToUpdate.setCatName(catDto.getCatName());
        categoryDtoToUpdate.setCatDescription(catDto.getCatDescription());
        categoryDtoToUpdate.setCatShortname(catDto.getCatShortname());

        if(!categoryDtoToUpdate.getCatCode().equals(catDto.getCatCode())){
            if(isCatageryUnique(catDto.getCatCode(), catDto.getCatPosDto())){
                categoryDtoToUpdate.setCatCode(catDto.getCatCode());
            }
            else{
                log.error("A category with the same code exist already in tat pointofsale");
                throw new DuplicateEntityException("Une Categorie existe deja dans le Pointofsale precise avec  " +
                        "le meme code :", ErrorCode.CATEGORY_DUPLICATED);
            }
        }

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

    /*******************************************************************************
     * This method is used to verify if the category contain some product or
     * be a parent category of another category. If it is the case, the category
     * will be declare not empty. It is empty in another case
     * @param catDto
     * @return
     */
    @Override
    public Boolean isCatageryEmpty(CategoryDto catDto) {
        if(catDto == null){
            log.error("Category is null");
            throw new NullArgumentException("Le category passe en argument est null");
        }
        if(catDto.getId() == null){
            log.error("Category is null");
            throw new NullArgumentException("Le category passe en argument a un id null");
        }
        List<Product> productList = productRepository.findAllProductOfCategory(catDto.getId());

        return productList.size()==0?true:false;
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
        if(posDto.getId() == null){
            log.error("Pointofsale is null");
            throw new NullArgumentException("Le Pointofsale a laquelle appartient la category n'a pas d'ID");
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

    public Boolean isCategoryExist(Long catId){
        if(catId == null){
            log.error("Le category catId is null");
            throw new NullArgumentException("le catId passe en argument de la methode est null");
        }
        Optional<Category> optionalCategory = categoryRepository.findCategoryById(catId);
        return optionalCategory.isPresent()?true:false;
    }

    @Override
    public List<CategoryDto> findAllCategory() {
        List<Category> categoryDtoList = categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "catName"));
        return categoryDtoList.stream().map(CategoryDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto> findAllCategoryInPointofsale(Long posId) {
        if(posId == null){
            throw  new NullArgumentException("Le posId precise en parametre est null");
        }
        Optional<Pointofsale> optionalPointofsale = posRepository.findPointofsaleById(posId);
        if(optionalPointofsale.isPresent()){
            Optional<List<Category>> optionalCategoryDtoList = categoryRepository.findAllCategoryInPointofsale(posId);
            if(optionalCategoryDtoList.isPresent()){
                return optionalCategoryDtoList.get().stream().map(CategoryDto::fromEntity).collect(Collectors.toList());
            }
        }
        else {
            throw new EntityNotFoundException("Aucun Pointofsale n'existe avec l'ID precise " + posId,
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return null;
    }

    @Override
    public Page<CategoryDto> pageofExistingCategory(int pagenum, int pagesize) {

        Page<Category> categoryPage = categoryRepository.findAll(
                PageRequest.of(pagenum, pagesize, Sort.by(Sort.Direction.ASC, "catName"))
        );
        return categoryPage.map(CategoryDto::fromEntity);
    }

    @Override
    public Page<CategoryDto> pageofCategoryInPos(Long posId, int pagenum, int pagesize) {
        if(posId == null){
            throw  new NullArgumentException("Le posId precise en parametre est null");
        }
        Optional<Pointofsale> optionalPointofsale = posRepository.findPointofsaleById(posId);
        if(optionalPointofsale.isPresent()){
            Page<Category> categoryPage = categoryRepository.findAllCategoryInPointofsale(posId,
                    PageRequest.of(pagenum, pagesize, Sort.by(Sort.Direction.ASC, "catName"))
            );
            return categoryPage.map(CategoryDto::fromEntity);
        }
        throw new EntityNotFoundException("Aucun Pointofsale n'existe avec l'ID precise "+posId,
                ErrorCode.POINTOFSALE_NOT_FOUND);
    }

    @Override
    public Boolean deleteCategoryById(Long catId) {
        if(catId == null){
            log.error("Le category catId is null");
            throw  new NullArgumentException("Le catId precise en parametre est null");
        }
        Optional<Category> optionalCategory = categoryRepository.findCategoryById(catId);
        if(optionalCategory.isPresent()){
            categoryRepository.delete(optionalCategory.get());
            return true;
        }
        else{
            throw new EntityNotFoundException("Aucune category n'existe avec l'ID precise "+catId,
                    ErrorCode.CATEGORY_NOT_FOUND);
        }
    }

    @Override
    public Boolean deleteCategoryByCatCode(String catCode, Long posId) {
        if(!StringUtils.hasLength(catCode)){
            log.error("Le category catCode is vide");
            throw new NullArgumentException("le catCode passe en argument de la methode est vide");
        }

        if(posId == null){
            log.error("Le pointofsale posId is null");
            throw  new NullArgumentException("Le posId precise en parametre est null");
        }

        Optional<Category> optionalCategory = categoryRepository.findCategoryInPointofsaleByCode(catCode, posId);
        if(optionalCategory.isPresent()){
            categoryRepository.delete(optionalCategory.get());
            return true;
        }
        else{
            throw new EntityNotFoundException("Aucune category n'existe avec le code "+catCode +
                    " dans le pointofsale d'ID "+posId, ErrorCode.CATEGORY_NOT_FOUND);
        }
    }


}
