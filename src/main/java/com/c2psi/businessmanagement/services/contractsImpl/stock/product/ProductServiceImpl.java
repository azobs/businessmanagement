package com.c2psi.businessmanagement.services.contractsImpl.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.CategoryDto;
import com.c2psi.businessmanagement.dtos.stock.product.ProductDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.Category;
import com.c2psi.businessmanagement.models.Pointofsale;
import com.c2psi.businessmanagement.models.Product;
import com.c2psi.businessmanagement.repositories.pos.pos.PointofsaleRepository;
import com.c2psi.businessmanagement.repositories.stock.product.CategoryRepository;
import com.c2psi.businessmanagement.repositories.stock.product.ProductRepository;
import com.c2psi.businessmanagement.services.contracts.stock.product.ProductService;
import com.c2psi.businessmanagement.validators.stock.product.ProductValidator;
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

@Service(value="ProductServiceV1")
@Slf4j
@Transactional
public class ProductServiceImpl implements ProductService {
    private CategoryRepository categoryRepository;
    private PointofsaleRepository posRepository;
    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(CategoryRepository categoryRepository, PointofsaleRepository posRepository,
                              ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.posRepository = posRepository;
        this.productRepository = productRepository;
    }

    @Override
    public ProductDto saveProduct(ProductDto prodDto) {
        /**********************************
         * Validate the entity ProductDto *
         **********************************/
        List<String> errors = ProductValidator.validate(prodDto);
        if(!errors.isEmpty()){
            log.error("Entity productDto not valid {}", prodDto);
            throw new InvalidEntityException("Le productDto passe en argument n'est pas valide: "+errors,
                    ErrorCode.PRODUCT_NOT_VALID, errors);
        }

        /*****************************************
         * Verify the existence of the category  *
         *****************************************/
        Optional<Category> optionalCategory = categoryRepository.findCategoryById(prodDto.getProdCatDto().getId());
        if(optionalCategory.isEmpty()){
            log.error("Entity productDto not valid because the category does not exist{}", prodDto);
            throw new InvalidEntityException("Le productDto passe en argument n'est pas valide puisque sa category " +
                    "n'est pas existant: "+errors, ErrorCode.CATEGORY_NOT_FOUND, errors);
        }

        /***********************************************************************************************************
         * Ensure that the pointofsale of the product is valid and that pointofale of the categery is equal to the
         * one of the pointofsale precise in the pointofsale
         */
        Optional<Pointofsale> optionalPointofsale = posRepository.findPointofsaleById(
                prodDto.getProdPosId());
        if(optionalPointofsale.isEmpty()){
            log.error("Entity productDto not valid because the pointofsale does not exist{}", prodDto);
            throw new InvalidEntityException("Le productDto passe en argument n'est pas valide puisque son pointofsale " +
                    "n'est pas existant: "+errors, ErrorCode.POINTOFSALE_NOT_FOUND, errors);
        }

        /************************************************************************************
         * Ensure that the Category owner indicated is in the same pointofsale as indicated
         */
        if(prodDto.getProdPosId().longValue() !=
                prodDto.getProdCatDto().getCatPosId().longValue()){
            log.error("Entity productDto not valid because the category indicated is not in the same pointofsale " +
                    "as indicated {}", prodDto);
            throw new InvalidEntityException("Le productDto passe en argument n'est pas valide puisque sa category " +
                    "n'est pas dans le meme pointofsale tel qu'indique: "+errors, ErrorCode.PRODUCT_NOT_VALID, errors);
        }

        /********************************************************
         * Ensure the unicity of the product in the pointofsale
         */
        if(!isProductUniqueInPos(prodDto.getProdCode(), prodDto.getProdPosId())){
            log.error("An entity product already exist the same code in the DB {}", prodDto);
            throw new DuplicateEntityException("Un produit existe deja dans la BD avec les memes code ",
                    ErrorCode.PRODUCT_DUPLICATED);
        }

        /***********************************************************************************
         * Tout est bon et on peut simplement faire l'enregistrement dans la base de donnee
         */
        log.info("After all verification, the record {} can be saved on the DB", prodDto);

        return ProductDto.fromEntity(
                productRepository.save(
                        ProductDto.toEntity(prodDto)
                )
        );
    }

    @Override
    public ProductDto updateProduct(ProductDto prodDto) {
        /*******
         * Il faut valider le prodDto passe en parametre cest a dire la saisie de lutilisateur
         */
        List<String> errors = ProductValidator.validate(prodDto);
        if(!errors.isEmpty()){
            log.error("Entity productDto not valid {}", prodDto);
            throw new InvalidEntityException("Le productDto passe en argument n'est pas valide: "+errors,
                    ErrorCode.PRODUCT_NOT_VALID, errors);
        }

        /****
         * On doit verifier lexistence du product quon veut modifier
         * Pour cela on doit verifier dabord que son id nest pas null avant de le chercher dans la BD
         */
        if(prodDto.getId() == null){
            log.error("Entity productDto not valid for update because the id of the product that will be modified is null {}", prodDto);
            throw new InvalidEntityException("Le productDto passe en argument n'est pas valide car son id est null " +
                    "alors quon veut modifier quelque chose qui doit etre en BD: "+errors,
                    ErrorCode.PRODUCT_NOT_VALID, errors);
        }
        /******
         * Il faut verifier que le product a modifier existe vraiment en BD
         */
        if(!isProductExistWithId(prodDto.getId())){
            throw new EntityNotFoundException("Aucun product nexiste en BD avec l'ID precise");
        }

        Product productToUpdate = ProductDto.toEntity(this.findProductById(prodDto.getId()));
        /*****
         * On verifie si cest le code quon veut modifier sur le produit
         * si cest le cas alors on va verifier si ne va pas causer une duplication
         */
        if(!prodDto.getProdCode().equals(productToUpdate.getProdCode())){
            //Comme ils sont pas egaux alors on veut aussi modifier le code
            if(!isProductUniqueInPos(prodDto.getProdCode(), prodDto.getProdPosId())){
                throw new DuplicateEntityException("Le nouveau prodCode identifie deja un product dans le pointofsale en question ",
                        ErrorCode.PRODUCT_DUPLICATED);
            }
            //A ce niveau on est sur que pas de duplication possible donc on peut changer le code sans souci
            productToUpdate.setProdCode(prodDto.getProdCode());
        }

        /*******
         * On verifie si cest plutot la category du product quon veut changer
         */
        if(!prodDto.getProdCatDto().getId().equals(productToUpdate.getProdCat().getId())){
            //Comme ils sont pas egaux alors on veut aussi modifier la category
            //Dans ce cas on va se rassurer que la nouvelle category est dans le meme point de vente
            if(!prodDto.getProdCatDto().getCatPosId().equals(productToUpdate.getProdPosId())){
                throw new EntityNotFoundException("La nouvelle category precise nest pas dans le meme pointofsale ou le " +
                        "product etait");
            }
            //A ce niveau on est sur quil ny a pas de probleme au niveau du pointofsale
            productToUpdate.setProdCat(CategoryDto.toEntity(prodDto.getProdCatDto()));
        }

        log.info("After all verification, the remaining attribute of prodcatToUpdate can be saved on the DB by {}", prodDto);
        productToUpdate.setProdName(prodDto.getProdName());
        productToUpdate.setProdAlias(prodDto.getProdAlias());
        productToUpdate.setProdDescription(prodDto.getProdDescription());
        productToUpdate.setProdPerishable(prodDto.getProdPerishable());

        return ProductDto.fromEntity(productRepository.save(productToUpdate));
    }

    @Override
    public ProductDto findProductById(Long prodId) {
        if(prodId == null){
            log.error("prodId is null");
            throw new NullArgumentException("le prodId passe en argument de la methode est null");
        }
        Optional<Product> optionalProduct = productRepository.findProductById(prodId);
        if(!optionalProduct.isPresent()){
            throw new EntityNotFoundException("Aucun product nexiste en BD avec l'ID precise");
        }
        return ProductDto.fromEntity(optionalProduct.get());
    }

    @Override
    public ProductDto findProductByProductCodeInPos(String prodCode, Long posId) {
        if(!isProductExistWithCodeInPointofsale(prodCode, posId)){
            log.error("Aucun product n'existe dans le pointofsale d'id {} avec le code {}", posId, prodCode);
            throw new EntityNotFoundException("Aucun product nexiste dans le pointofsale d'id "+ posId
                    +" avec le code "+prodCode);
        }
        //Si on est pas entre dans le if cest que ca existe
        Optional<Product> optionalProduct = productRepository.findProductInPointofsaleByCode(prodCode, posId);
        if(optionalProduct.isPresent()){
            return ProductDto.fromEntity(optionalProduct.get());
        }
        return null;
    }

    @Override
    public Boolean isProductUniqueInPos(String prodCode, Long posId) {

        if(!StringUtils.hasLength(prodCode)){
            log.error("Product name is null or empty");
            throw new NullArgumentException("le prodName passe en argument de la methode est null");
        }
        if(posId == null){
            log.error("posId is null");
            throw new NullArgumentException("Le posId passe en argument de la methode est null");
        }
        Optional<Product> optionalProduct = productRepository.findProductInPointofsaleByCode(prodCode, posId);
        return optionalProduct.isPresent()?false:true;
    }

    public Boolean isProductExistWithId(Long prodId) {
        if(prodId == null){
            log.error("prodId is null");
            throw new NullArgumentException("le prodId passe en argument de la methode est null");
        }
        Optional<Product> optionalProduct = productRepository.findProductById(prodId);
        return optionalProduct.isPresent()?true:false;
    }

    public Boolean isProductExistWithCodeInPointofsale(String prodCode, Long posId) {
        if(posId == null){
            log.error("posId is null");
            throw new NullArgumentException("le posId passe en argument de la methode est null");
        }
        if(!StringUtils.hasLength(prodCode)){
            log.error("prodName is null or empty");
            throw new NullArgumentException("le prodName passe en argument de la methode est null ou vide");
        }
        Optional<Product> optionalProduct = productRepository.findProductInPointofsaleByCode(prodCode, posId);
        return optionalProduct.isPresent()?true:false;
    }

    @Override
    public Boolean deleteProductById(Long prodId) {
        if(!isProductExistWithId(prodId)){
            throw new EntityNotFoundException("Aucun product nexiste avec cet id ");
        }
        Optional<Product> optionalProduct = productRepository.findProductById(prodId);
        if(optionalProduct.isPresent()){
            if(isProductDeleteable(prodId)) {
                productRepository.delete(optionalProduct.get());
                return true;
            }
            log.error("The entity {} is not deleteable because it encompasses some other elements ", optionalProduct.get());
            throw new EntityNotDeleteableException("Ce produit ne peut etre supprime car est deja associe a des " +
                    "produits formates en BD. Il faut d'abord supprimer ces produits formates. ", ErrorCode.PRODUCT_NOT_DELETEABLE);
        }
        throw new EntityNotFoundException("Aucun produit n'existe avec l'id  =  "+prodId+" passe en argument");
    }

    @Override
    public Boolean isProductDeleteable(Long prodId) {
        /**********************************************************************************************************
         *Un produit ici est comme la matiere premiere permettant de fabriquer les articles. Car avec un produi,
         * il faut le formater (pour obtenir les ProductFormated) et ensuite les mesurer ou les quantifier en
         * utilisant des unites de mesures (et a ce moment on parle d'article)
         *
         * Un produit peut donc etre supprime si il nest pas encore utilise pour la formation dun ProductFormated
         **********************************************************************************************************/
        return true;
    }

    @Override
    public List<ProductDto> findAllProductInPos(Long posId) {
        if(posId == null){
            log.error("posId is null");
            throw new NullArgumentException("le posId passe en argument de la methode est null");
        }
        Optional<List<Product>> optionalProductList = productRepository.findAllProductInPos(posId);
        if(optionalProductList.isPresent()){
            return optionalProductList.get().stream().map(ProductDto::fromEntity).collect(Collectors.toList());
        }
        throw new EntityNotFoundException("Aucun pointofsale n'existe avec l'ID passe en argument "+posId);
    }

    @Override
    public List<ProductDto> findAllProductOfCategory(Long catId) {
        if(catId == null){
            log.error("catId is null");
            throw new NullArgumentException("le catId passe en argument de la methode est null");
        }
        Optional<List<Product>> optionalProductList = productRepository.findAllProductOfCategory(catId);
        if(optionalProductList.isPresent()){
            return optionalProductList.get().stream().map(ProductDto::fromEntity).collect(Collectors.toList());
        }
        throw new EntityNotFoundException("Aucune category n'existe avec l'id passe en argument "+catId);
    }

    @Override
    public Page<ProductDto> findPageofProductInPos(Long posId, int pagenum, int pagesize) {
        if(posId == null){
            log.error("posId is null");
            throw new NullArgumentException("le posId passe en argument de la methode est null");
        }
        Optional<Page<Product>> optionalProductPage = productRepository.findPageofProductInPos(posId,
                PageRequest.of(pagenum, pagesize, Sort.by(Sort.Direction.ASC, "prodName")));
        if(optionalProductPage.isPresent()){
            return optionalProductPage.get().map(ProductDto::fromEntity);
        }
        throw new EntityNotFoundException("Aucun pointofsale nexiste avec l'id passe en argument "+posId);
    }

    @Override
    public Page<ProductDto> findPageOfProductOfCategory(Long catId, int pagenum, int pagesize) {
        if(catId == null){
            log.error("catId is null");
            throw new NullArgumentException("le catId passe en argument de la methode est null");
        }
        Optional<Page<Product>> optionalProductPage = productRepository.findPageofProductOfCategory(catId,
                PageRequest.of(pagenum, pagesize, Sort.by(Sort.Direction.ASC, "prodName")));
        if(optionalProductPage.isPresent()){
            return optionalProductPage.get().map(ProductDto::fromEntity);
        }
        throw new EntityNotFoundException("Aucune category nexiste avec l'id passe en argument "+catId);
    }

    @Override
    public Page<ProductDto> findProductByProdCodeInPosContaining(Long posId, String sample, int pagenum, int pagesize) {
        if(posId == null){
            log.error("posId is null");
            throw new NullArgumentException("le posId passe en argument de la methode est null");
        }
        if(!StringUtils.hasLength(sample)){
            log.error("sample is null or empty");
            throw new NullArgumentException("le sample passe en argument de la methode est null ou vide");
        }
        Optional<Page<Product>> optionalProductPage = productRepository.findAllProductByProdNameInPosContaining(posId,
                sample, PageRequest.of(pagenum, pagesize, Sort.by(Sort.Direction.ASC, "prodName")));
        if(optionalProductPage.isPresent()){
            return optionalProductPage.get().map(ProductDto::fromEntity);
        }
        throw new EntityNotFoundException("Aucun pointofsale nexiste avec ce posId dans la BD "+posId);
    }
}
