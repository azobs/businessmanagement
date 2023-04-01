package com.c2psi.businessmanagement.services.contractsImpl.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.CategoryDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.Category;
import com.c2psi.businessmanagement.models.Pointofsale;
import com.c2psi.businessmanagement.repositories.pos.pos.PointofsaleRepository;
import com.c2psi.businessmanagement.repositories.stock.product.CategoryRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value="CategoryServiceV1")
@Slf4j
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private PointofsaleRepository posRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, PointofsaleRepository posRepository) {

        this.categoryRepository = categoryRepository;
        this.posRepository = posRepository;
    }

    @Override
    public CategoryDto saveCategory(CategoryDto categoryDto) {
        List<String> errors = CategoryValidator.validate(categoryDto);
        if(!errors.isEmpty()){
            log.error("Entity categoryDto not valid {}", categoryDto);
            throw new InvalidEntityException("Le categoryDto passe en argument n'est pas valide: "+errors,
                    ErrorCode.CATEGORY_NOT_VALID, errors);
        }

        /***
         * Verify the existence of the pointofsale
         */
        Optional<Pointofsale> optionalPointofsale = posRepository.findPointofsaleById(categoryDto.getCatPosDto().getId());
        if(!optionalPointofsale.isPresent()){
            throw new InvalidEntityException("Le pointofsale de la categoryDto passe en argument n'existe pas: ",
                    ErrorCode.POINTOFSALE_NOT_VALID);
        }

        if(!this.isCategoryUniqueInPos(categoryDto.getCatCode(), categoryDto.getCatPosDto().getId())){
            log.error("An entity category already exist the same code in the DB {}", categoryDto);
            throw new DuplicateEntityException("Une category existe deja dans la BD avec les mêmes name ",
                    ErrorCode.CATEGORY_DUPLICATED);
        }


        if(categoryDto.getCategoryParentId() != null){
            /***********
             * On doit verifier que la category parente en question existe d'abord
             */
            if(!isCategoryExistWithId(categoryDto.getCategoryParentId())){
                throw new EntityNotFoundException("Aucune Category avec l'id "+categoryDto.getCategoryParentId()
                        +" n'a été trouve dans la BDD pour etre parente", ErrorCode.CATEGORY_NOT_FOUND);
            }
        }

        log.info("After all verification, the record {} can be done on the DB", categoryDto);
        return CategoryDto.fromEntity(
                categoryRepository.save(
                        CategoryDto.toEntity(categoryDto)
                )
        );
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        /***************************************************************************
         * *Il faut valider le parametre categoryDto. Une fois valide?
         * *Il faut rechercher la category a modifier dans la BD. Si la category est retrouve?
         * *Il regarde si le pointofsale na pas changer. Si cest le cas(pointofsale change), alors on signale une erreur
         * de Dto invalide (InvalidEntityException)
         * *si le pointofsale est le meme alors on regarde si cest le code quon veut modifier. Si cest le cas
         *      *on verifie si le couple (catCode et posId) sera encore unique dans la BD cest a dire verifier si
         *      la category sera encore unique dans le pointofsale
         * *si cest la category parent quon veut modifier?
         *      *On verifie que la nouvelle category parente precise existe
         *      *Si ca existe alors on verifie que les pointofsale du parent en question et du fils quon veut modifier
         *      sont les meme. si cest le cas?
         *
         */
        List<String> errors = CategoryValidator.validate(categoryDto);
        if (!errors.isEmpty()) {
            log.error("Entity categoryDto not valid {}", categoryDto);
            throw new InvalidEntityException("Le categoryDto passe en argument n'est pas valide: " + errors,
                    ErrorCode.CATEGORY_NOT_VALID, errors);
        }

        if (categoryDto.getId() == null) {
            log.error("Entity categoryDto to update not valid {}", categoryDto);
            throw new InvalidEntityException("Le categoryDto a update passe en argument n'est pas valide: " + errors,
                    ErrorCode.CATEGORY_NOT_VALID, errors);
        }

        if (!isCategoryExistWithId(categoryDto.getId())) {
            log.error("Id of Entity categoryDto updated not exist {}", categoryDto);
            throw new EntityNotFoundException("Le categoryDto passe en argument a update n'existe pas: " + errors,
                    ErrorCode.CATEGORY_NOT_FOUND, errors);
        }

        Category catToUpdate = CategoryDto.toEntity(this.findCategoryById(categoryDto.getId()));
        System.out.println("catToUpdate.getId == "+catToUpdate.getId());
        System.out.println("categoryDto.getId == "+categoryDto.getId());

        if (categoryDto.getCatPosDto().getId() != catToUpdate.getCatPos().getId()) {
            log.error("Entity categoryDto to update not valid because pointofsale cannot change {}", categoryDto);
            throw new InvalidEntityException("Le categoryDto a update passe en argument n'est pas valide car " +
                    "le pointofsale ne peut etre modifie: " + errors, ErrorCode.CATEGORY_NOT_VALID, errors);
        }

        if (!categoryDto.getCatCode().equals(catToUpdate.getCatCode())) {
            if (!this.isCategoryUniqueInPos(categoryDto.getCatCode(), categoryDto.getCatPosDto().getId())) {
                log.error("Entity categoryDto will be duplicated in the database{}", categoryDto);
                throw new DuplicateEntityException("Le categoryDto a update passe en argument n'est pas valide car " +
                        "le pointofsale ne peut etre modifie: " + errors, ErrorCode.CATEGORY_DUPLICATED, errors);
            }
            //On veut modifier le code et le nouveau ne va pas creer de duplicata
            catToUpdate.setCatCode(categoryDto.getCatCode());
        }

        if(categoryDto.getCategoryParentId() != null || catToUpdate.getCategoryParentId() != null){
            if (categoryDto.getCategoryParentId().longValue() != catToUpdate.getCategoryParentId().longValue()) {
                //Alors cest la category parente quon veut modifier
                if (!isCategoryExistWithId(categoryDto.getCategoryParentId())) {
                    log.error("Id of Entity categoryDto Parent not exist {}", categoryDto);
                    throw new EntityNotFoundException("Le categoryDto Parente passe en argument a update n'existe pas: " + errors,
                            ErrorCode.CATEGORY_NOT_FOUND, errors);
                }

                CategoryDto newcatDtoParent = this.findCategoryById(categoryDto.getCategoryParentId());

                if (newcatDtoParent.getCatPosDto().getId().longValue() != catToUpdate.getCatPos().getId().longValue()) {
                    log.error("The pointofsale of the new catParent to modify is different than " +
                            "the pointofsale of category modify {}", categoryDto);
                    throw new InvalidEntityException("Le categoryDto a update parente passe en argument n'est pas dans " +
                            "le pointofsale de la modification envoyee " + errors, ErrorCode.CATEGORY_NOT_VALID, errors);
                }
                //On peut donc modifier la category parente et le nouveau parent existe dans le meme point de vente
                catToUpdate.setCategoryParentId(newcatDtoParent.getId());
            }
        }
        else if(categoryDto.getCategoryParentId() == null){
            catToUpdate.setCategoryParentId(null);
        }
        /**
         * Apres toute verification faite on peut donc modifier les autres parametres de la category
         */
        catToUpdate.setCatDescription(categoryDto.getCatDescription());
        catToUpdate.setCatName(categoryDto.getCatName());
        catToUpdate.setCatShortname(categoryDto.getCatShortname());

        return CategoryDto.fromEntity(categoryRepository.save(catToUpdate));
    }

    @Override
    public Boolean isCategoryUniqueInPos(String catCode, Long posId) {
        if(!StringUtils.hasLength(catCode)){
            log.error("Category code is null");
            throw new NullArgumentException("le catCode passe en argument de la methode est null");
        }
        if(posId == null){
            log.error("posId is null");
            throw new NullArgumentException("Le posId passe en argument de la methode est null");
        }
        Optional<Category> optionalCategory = categoryRepository.findCategoryInPointofsaleByCode(catCode, posId);
        return optionalCategory.isPresent()?false:true;
    }

    @Override
    public CategoryDto findCategoryById(Long catId) {
        if(catId == null){
            log.error("catId is null");
            throw new NullArgumentException("le catId passe en argument de la methode est null");
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
    public List<CategoryDto> findAscandantCategoryof(Long catId) {
        /**************  Comment faire la liste des ascendants dune category ***********************
         *          while le parent dune category nest pas null
         *            debut
         *              garder le parent dans la liste
         *              puis passer au parent plus haut
         *            fin
         ********************************************************************************************/
        if(!isCategoryExistWithId(catId)){
            log.error("The category precised dont exist in the DB {}", catId);
            throw new EntityNotFoundException("Aucune category nexiste avec l'ID precise: ",ErrorCode.CATEGORY_NOT_FOUND);
        }
        CategoryDto catToFoundAscandant = this.findCategoryById(catId);
        List<CategoryDto> listofAscandant = new ArrayList<>();
        Long catParentId = catToFoundAscandant.getCategoryParentId();
        while(catParentId != null){
            CategoryDto catParentDto = this.findCategoryById(catParentId);
            listofAscandant.add(catParentDto);
            catParentId = catParentDto.getCategoryParentId();
        }
        return listofAscandant;
    }

    @Override
    public List<CategoryDto> findChildCategoryOf(Long catId) {
        if(catId == null){
            throw new NullArgumentException("le catId passe en argument de la methode est null");
        }
        if(!isCategoryExistWithId(catId)){
            throw new EntityNotFoundException("Aucune category nexiste avec l'ID precise: "+catId,
                    ErrorCode.CATEGORY_NOT_FOUND);
        }
        Optional<List<Category>> optionalCategoryList = categoryRepository.findChildCategoryOf(catId);
        if(optionalCategoryList.isPresent()){
            return optionalCategoryList.get().stream().map(CategoryDto::fromEntity).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<CategoryDto> findAllCategoryInPointofsale(Long posId) {
        if(posId == null){
            log.error("The posId precised in argument is null");
            throw new NullArgumentException("le posId passe en argument de la methode est null");
        }

        Optional<Pointofsale> optionalPointofsale = posRepository.findPointofsaleById(posId);
        if(!optionalPointofsale.isPresent()){
            log.error("There is no pointofsale with the id {} in the DB ", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'ID precise: "+posId,
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }

        Optional<List<Category>> optionalCategoryList = categoryRepository.findAllCategoryInPointofsale(posId);
        if(optionalCategoryList.isPresent()){
            return optionalCategoryList.get().stream().map(CategoryDto::fromEntity).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public Page<CategoryDto> findCategoryInPointofsale(Long posId, int pagenum, int pagesize) {
        if(posId == null){
            log.error("The posId precised in argument is null");
            throw new NullArgumentException("le posId passe en argument de la methode est null");
        }

        Optional<Pointofsale> optionalPointofsale = posRepository.findPointofsaleById(posId);
        if(!optionalPointofsale.isPresent()){
            log.error("There is no pointofsale with the id {} in the DB ", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'ID precise: "+posId,
                    ErrorCode.POINTOFSALE_NOT_FOUND);
        }

        Optional<Page<Category>> optionalCategoryPage = categoryRepository.findAllCategoryInPointofsale(posId,
                PageRequest.of(pagenum, pagesize, Sort.by(Sort.Direction.ASC, "catName")));
        if(optionalCategoryPage.isPresent()){
            return optionalCategoryPage.get().map(CategoryDto::fromEntity);
        }
        return null;
    }

    @Override
    public Page<CategoryDto> findAllByCatNameInPosContaining(Long posId, String sample, int pagenum, int pagesize) {
        /*****************************************************************************************
         *Page<UserBM> pageofUserBM = userBMRepository.findAllByBmNameOrBmSurnameContaining(sample,
         *                 PageRequest.of(pagenum, pagesize, Sort.by(Sort.Direction.ASC, "bmName")));
         *         return pageofUserBM.map(UserBMDto::fromEntity);
         */
        if(posId == null){
            log.error("posId is null");
            throw new NullArgumentException("le posId passe en argument de la methode est null");
        }
        if(!StringUtils.hasLength(sample)){
            log.error("sample is null or empty");
            throw new NullArgumentException("le sample passe en argument de la methode est null ou vide");
        }

        Optional<Page<Category>> optionalCategoryPage = categoryRepository.findAllByCatNameInPosContaining(posId, sample,
                PageRequest.of(pagenum, pagesize, Sort.by(Sort.Direction.ASC, "catName")));
        if(optionalCategoryPage.isPresent()){
            return optionalCategoryPage.get().map(CategoryDto::fromEntity);
        }
        return null;
    }

    @Override
    public Boolean isCategoryDeleteable(Long catId) {
        /***********************************************************************************************
         * Une category peut etre supprimme si et seulement si sa liste des produits est encore vide.
         * si aucun produit ne depend de lui alors on peut la supprimer. sinon on doit signaler la pre-
         * sence des produits et inviter l'admin a deplacer les dits produits vers une autre category
         ***********************************************************************************************/
        return null;
    }

    @Override
    public Boolean deleteCategoryById(Long catId) {
        if(catId == null){
            log.error("catId is null");
            throw new NullArgumentException("le catId passe en argument de la methode est null");
        }
        Optional<Category> optionalCategory = categoryRepository.findCategoryById(catId);
        if(optionalCategory.isPresent()){
            categoryRepository.delete(optionalCategory.get());
            return true;
        }
        else{
            log.error("There is no category with the id {} in the DB ", catId);
            throw new EntityNotFoundException("Aucune category n'existe avec l'ID precise: "+catId,
                    ErrorCode.CATEGORY_NOT_FOUND);
        }
    }

    @Override
    public Boolean deleteCategoryByCatCode(String catCode, Long posId) {
        if(!StringUtils.hasLength(catCode) || posId == null){
            log.error("catCode or posId is null");
            throw new NullArgumentException("le catCode ou le posId passe en argument de la methode est null");
        }
        Optional<Category> optionalCategory = categoryRepository.findCategoryInPointofsaleByCode(catCode, posId);
        if(optionalCategory.isPresent()){
            categoryRepository.delete(optionalCategory.get());
            return true;
        }
        else{
            log.error("There is no category with the catCode {} of pos of ID {} in the DB ", catCode, posId);
            throw new EntityNotFoundException("Aucune category n'existe avec le code "+catCode+ " dans le pos d'ID " +posId+
                    "precise: ", ErrorCode.CATEGORY_NOT_FOUND);
        }
    }

    public Boolean isCategoryExistWithId(Long catId) {
        if(catId == null){
            log.error("catId is null");
            throw new NullArgumentException("le catId passe en argument de la methode est null");
        }
        Optional<Category> optionalCategory = categoryRepository.findCategoryById(catId);
        return optionalCategory.isPresent()?true:false;
    }

    public Boolean isCategoryExistWithCodeInPointofsale(String catCode, Long posId) {
        if(posId == null){
            log.error("posId is null");
            throw new NullArgumentException("le posId passe en argument de la methode est null");
        }
        if(!StringUtils.hasLength(catCode)){
            log.error("catCode is null or empty");
            throw new NullArgumentException("le catCode passe en argument de la methode est null ou vide");
        }
        Optional<Category> optionalCategory = categoryRepository.findCategoryInPointofsaleByCode(catCode, posId);
        return optionalCategory.isPresent()?true:false;
    }
}
