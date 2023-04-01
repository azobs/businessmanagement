package com.c2psi.businessmanagement.services.contractsImpl.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.ProductDto;
import com.c2psi.businessmanagement.exceptions.DuplicateEntityException;
import com.c2psi.businessmanagement.exceptions.ErrorCode;
import com.c2psi.businessmanagement.exceptions.InvalidEntityException;
import com.c2psi.businessmanagement.exceptions.NullArgumentException;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

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
        /****
         * Validate the entity ProductDto
         */
        List<String> errors = ProductValidator.validate(prodDto);
        if(!errors.isEmpty()){
            log.error("Entity productDto not valid {}", prodDto);
            throw new InvalidEntityException("Le productDto passe en argument n'est pas valide: "+errors,
                    ErrorCode.PRODUCT_NOT_VALID, errors);
        }
        /*****
         * Verify the existence of the category
         */
        Optional<Category> optionalCategory = categoryRepository.findCategoryById(prodDto.getProdCatDto().getId());
        if(optionalCategory.isEmpty()){
            log.error("Entity productDto not valid because the category does not exist{}", prodDto);
            throw new InvalidEntityException("Le productDto passe en argument n'est pas valide puisque sa category " +
                    "n'est pas existant: "+errors, ErrorCode.CATEGORY_NOT_FOUND, errors);
        }
        /*******
         * Ensure that the pointofsale of the product is valid and that pointofale of the categery is equal to the
         * one of the pointofsale precise in the pointofsale
         */
        Optional<Pointofsale> optionalPointofsale = posRepository.findPointofsaleById(
                prodDto.getProdPosDto().getId());
        if(optionalPointofsale.isEmpty()){
            log.error("Entity productDto not valid because the pointofsale does not exist{}", prodDto);
            throw new InvalidEntityException("Le productDto passe en argument n'est pas valide puisque son pointofsale " +
                    "n'est pas existant: "+errors, ErrorCode.POINTOFSALE_NOT_FOUND, errors);
        }
        /*******
         * Ensure that the Category owner indicated is in the same pointofsale as indicated
         */
        if(prodDto.getProdPosDto().getId().longValue() !=
                prodDto.getProdCatDto().getCatPosDto().getId().longValue()){
            log.error("Entity productDto not valid because the category indicated is not in the same pointofsale " +
                    "as indicated {}", prodDto);
            throw new InvalidEntityException("Le productDto passe en argument n'est pas valide puisque sa category " +
                    "n'est pas dans le meme pointofsale tel qu'indique: "+errors, ErrorCode.PRODUCT_NOT_VALID, errors);
        }
        /**********
         * Ensure the unicity of the product in the pointofsale
         */
        if(!isProductUniqueInPos(prodDto.getProdCode(), prodDto.getProdPosDto().getId())){
            log.error("An entity product already exist the same code in the DB {}", prodDto);
            throw new DuplicateEntityException("Un produit existe deja dans la BD avec les memes code ",
                    ErrorCode.PRODUCT_DUPLICATED);
        }
        /***********
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
        return null;
    }

    @Override
    public ProductDto findProductById(Long prodId) {
        return null;
    }

    @Override
    public ProductDto findProductByProductCodeInPos(String prodName, Long posId) {
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
        return null;
    }

    @Override
    public Boolean isProductDeleteable(Long prodId) {
        return null;
    }

    @Override
    public List<ProductDto> findAllProductInPos(Long posId) {
        return null;
    }

    @Override
    public List<ProductDto> findAllProductOfCategory(Long catId) {
        return null;
    }

    @Override
    public Page<ProductDto> findPageofProductInPos(Long posId, int pagenum, int pagesize) {
        return null;
    }

    @Override
    public Page<ProductDto> findPageOfProductOfCategory(Long catId, int pagenum, int pagesize) {
        return null;
    }

    @Override
    public Page<ProductDto> findProductByProdCodeInPosContaining(Long posId, String sample, int pagenum, int pagesize) {
        return null;
    }
}
