package com.c2psi.businessmanagement.services.contractsImpl.stock.product;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.product.FormatDto;
import com.c2psi.businessmanagement.dtos.stock.product.ProductDto;
import com.c2psi.businessmanagement.dtos.stock.product.ProductFormatedDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.Format;
import com.c2psi.businessmanagement.models.Product;
import com.c2psi.businessmanagement.models.ProductFormated;
import com.c2psi.businessmanagement.repositories.pos.pos.PointofsaleRepository;
import com.c2psi.businessmanagement.repositories.stock.product.FormatRepository;
import com.c2psi.businessmanagement.repositories.stock.product.ProductFormatedRepository;
import com.c2psi.businessmanagement.repositories.stock.product.ProductRepository;
import com.c2psi.businessmanagement.services.contracts.stock.product.ProductFormatedService;
import com.c2psi.businessmanagement.validators.stock.product.ProductFormatedValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value="ProductFormatedServiceV1")
@Slf4j
@Transactional
public class ProductFormatedServiceImpl implements ProductFormatedService {

    private PointofsaleRepository posRepository;
    private ProductRepository productRepository;
    private FormatRepository formatRepository;
    private ProductFormatedRepository pfRepository;

    @Autowired
    public ProductFormatedServiceImpl(PointofsaleRepository posRepository, ProductRepository productRepository,
                                      FormatRepository formatRepository, ProductFormatedRepository pfRepository) {
        this.posRepository = posRepository;
        this.productRepository = productRepository;
        this.formatRepository = formatRepository;
        this.pfRepository = pfRepository;
    }

    @Override
    public ProductFormatedDto saveProductFormated(ProductFormatedDto pfDto) {
        /****
         * Validate the entity pfDto
         */
        List<String> errors = ProductFormatedValidator.validate(pfDto);
        if(!errors.isEmpty()){
            log.error("Entity pfDto not valid {}", pfDto);
            throw new InvalidEntityException("Le pfDto passe en argument n'est pas valide: "+errors,
                    ErrorCode.PRODUCTFORMATED_NOT_VALID, errors);
        }

        /*****
         * Verify the existence of the product
         */
        Optional<Product> optionalProduct = productRepository.findProductById(pfDto.getPfProductDto().getId());
        if(!optionalProduct.isPresent()){
           throw new InvalidEntityException("Le productformated nest pas valide puisque le product quil indique " +
                   "n'existe pas ", ErrorCode.PRODUCT_NOT_FOUND);
        }

        /****
         * Verify the existence of the format
         */
        Optional<Format> optionalFormat = formatRepository.findFormatById(pfDto.getPfFormatDto().getId());
        if(!optionalFormat.isPresent()){
            throw new InvalidEntityException("Le productformated nest pas valide puisque le format quil indique " +
                    "n'existe pas ", ErrorCode.FORMAT_NOT_FOUND);
        }

        /*******************************
         * Verify if the format and the product is in the same pointofsale
         */
        if(!optionalProduct.get().getProdPos().getId().equals(optionalFormat.get().getFormatPos().getId())){
            log.error("The format and the product of a productFormated must belong to the same pointofsale");
            throw new InvalidEntityException("Le format et le product devant composer le productFormated doivent etre " +
                    "dans le meme pointofsale", ErrorCode.PRODUCTFORMATED_NOT_VALID);
        }

        /*****************
         * Verify if a productformated is not already register with the same attribute
         */
        if(!isProductFormatedUnique(pfDto.getPfProductDto().getId(), pfDto.getPfFormatDto().getId())){
            log.error("There is another productFormated with the same attribute in the DB");
            throw new DuplicateEntityException("il y a deja un productFormated avec les memes attributs en BD ",
                    ErrorCode.PRODUCTFORMATED_DUPLICATED);
        }

        log.info("After all the verification we can save the productformated {} in the DB", pfDto);

        return ProductFormatedDto.fromEntity(
                pfRepository.save(
                        ProductFormatedDto.toEntity(pfDto)
                )
        );

    }

    @Override
    public ProductFormatedDto updateProductFormated(ProductFormatedDto pfDto) {
        /****
         * Validate the entity pfDto
         */
        List<String> errors = ProductFormatedValidator.validate(pfDto);
        if(!errors.isEmpty()){
            log.error("Entity pfDto not valid {}", pfDto);
            throw new InvalidEntityException("Le pfDto passe en argument n'est pas valide: "+errors,
                    ErrorCode.PRODUCTFORMATED_NOT_VALID, errors);
        }

        /*****
         * Il faut faire la recherche de l'element a modifier
         */
        if(pfDto.getId() == null){
            log.error("Entity pfDto not valid {}", pfDto);
            throw new InvalidEntityException("Le pfDto passe en argument n'est pas valide car son id est null or cest " +
                    "un update qu'on veut lancer ", ErrorCode.PRODUCTFORMATED_NOT_VALID);
        }

        /***
         * On recupere le productformated a modifier
         */
        Optional<ProductFormated> optionalProductFormated = pfRepository.findProductFormatedById(pfDto.getId());
        if(!optionalProductFormated.isPresent()){
            log.error("Entity to modifiy not found in the DB check the id in {}", pfDto);
        }
        ProductFormated pfToUpdate = optionalProductFormated.get();

        /******
         * Verify if is the modification of the format or the product
         */
        if(pfToUpdate.getPfFormat().getId().longValue() != pfDto.getPfFormatDto().getId().longValue() ||
        pfToUpdate.getPfProduct().getId().longValue() != pfDto.getPfProductDto().getId().longValue()){
            /*****
             * On verifie donc que la modification demande ne va pas causer une duplication en BD
             */
            if(!isProductFormatedUnique(pfDto.getPfProductDto().getId(), pfDto.getPfFormatDto().getId())){
                log.error("There is another productFormated with the same attribute in the DB");
                throw new DuplicateEntityException("il y a deja un productFormated avec les memes attributs en BD ",
                        ErrorCode.PRODUCTFORMATED_DUPLICATED);
            }

            /*******************************
             * Verify if the format and the product is in the same pointofsale
             */
            if(!pfDto.getPfProductDto().getProdPosDto().getId().equals(pfDto.getPfFormatDto().getFormatPosDto().getId())){
                log.error("The format and the product of a productFormated must belong to the same pointofsale");
                throw new InvalidEntityException("Le format et le product devant composer le productFormated doivent etre " +
                        "dans le meme pointofsale", ErrorCode.PRODUCTFORMATED_NOT_VALID);
            }

            //Ici on est sur que la modification ne vas pas causer de duplicata de donnee
            pfToUpdate.setPfFormat(FormatDto.toEntity(pfDto.getPfFormatDto()));
            pfToUpdate.setPfProduct(ProductDto.toEntity(pfDto.getPfProductDto()));
        }
        pfToUpdate.setPfPicture(pfDto.getPfPicture());
        return ProductFormatedDto.fromEntity(pfRepository.save(pfToUpdate));
    }

    @Override
    public ProductFormatedDto findProductFormatedById(Long pfId) {
        if(pfId == null){
            log.error("the pfId cannot be null in the function findProductFormatedById");
            throw new NullArgumentException("le pfId est null lors de lappel");
        }
        Optional<ProductFormated> optionalProductFormated = pfRepository.findProductFormatedById(pfId);
        if(!optionalProductFormated.isPresent()){
            throw new EntityNotFoundException("Aucun productFormated n'existe avec le id passe en argument "+pfId,
                    ErrorCode.PRODUCTFORMATED_NOT_FOUND);
        }
        return ProductFormatedDto.fromEntity(optionalProductFormated.get());
    }

    @Override
    public ProductFormatedDto findProductFormatedByProductIdAndFormatId(Long prodId, Long formatId) {
        if(prodId == null){
            log.error("the prodId cannot be null in the function findProductFormatedByProductIdAndFormatId");
            throw new NullArgumentException("le prodId est null lors de lappel");
        }
        if(formatId == null){
            log.error("the formatId cannot be null in the function findProductFormatedByProductIdAndFormatId");
            throw new NullArgumentException("le formatId est null lors de lappel");
        }
        Optional<ProductFormated> optionalProductFormated = pfRepository.findProductFormatedInPosWith(prodId, formatId);
        if(!optionalProductFormated.isPresent()){
            throw new EntityNotFoundException("Aucun productFormated n'existe avec le prodId = "+prodId+" et formatId "+
                    formatId +" passe en argument" + ErrorCode.PRODUCTFORMATED_NOT_FOUND);
        }
        return ProductFormatedDto.fromEntity(optionalProductFormated.get());
    }

    @Override
    public Boolean isProductFormatedUnique(Long prodId, Long formatId) {
        if(prodId == null){
            log.error("the prodId cannot be null in the function isProductFormatedUnique");
            throw new NullArgumentException("le prodId est null lors de lappel");
        }
        if(formatId == null){
            log.error("the formatId cannot be null in the function isProductFormatedUnique");
            throw new NullArgumentException("le formatId est null lors de lappel");
        }

        Optional<ProductFormated> optionalProductFormated = pfRepository.findProductFormatedInPosWith(prodId, formatId);

        return optionalProductFormated.isPresent()?false:true;
    }

    @Override
    public List<ProductFormatedDto> findListofProductFormatedInPos(Long posId) {
        if(posId == null){
            log.error("the posId cannot be null in the function findAllProductFormated");
            throw new NullArgumentException("le prodId est null lors de lappel");
        }
        Optional<List<ProductFormated>> optionalProductFormatedList = pfRepository.findAllProductFormatedInPos(posId);
        if(!optionalProductFormatedList.isPresent()){
            throw new EntityNotFoundException("Aucun pointofsale n'existe dans la BD avec l'id "+posId);
        }

        return optionalProductFormatedList.get().stream().map(ProductFormatedDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<ProductFormatedDto> findPageofProductFormatedInPos(Long posId, int pagenum, int pagesize) {
        if(posId == null){
            log.error("the posId cannot be null in the function findPageofProductFormatedInPos");
            throw new NullArgumentException("le prodId est null lors de lappel");
        }

        Optional<Page<ProductFormated>> optionalProductFormatedPage = pfRepository.findPageofProductFormatedInPos(posId,
                PageRequest.of(pagenum, pagesize));
        if(!optionalProductFormatedPage.isPresent()){
            throw new EntityNotFoundException("There is no pointofsale associated with the id "+posId);
        }
        return optionalProductFormatedPage.get().map(ProductFormatedDto::fromEntity);
    }


    @Override
    public Boolean deleteProductFormatedById(Long pfId) {
        if(pfId == null){
            log.error("the pfId cannot be null in the function findProductFormatedById");
            throw new NullArgumentException("le pfId est null lors de lappel");
        }
        Optional<ProductFormated> optionalProductFormated = pfRepository.findProductFormatedById(pfId);
        if(!optionalProductFormated.isPresent()){
            throw new EntityNotFoundException("Aucun productFormated n'existe avec le id passe en argument "+pfId,
                    ErrorCode.PRODUCTFORMATED_NOT_FOUND);
        }
        if(!isProductFormatedDeleteable(pfId)){
            throw new EntityNotDeleteableException("Le productFormated ne peut etre supprime a linstant car deja " +
                    "deja utilise pour configurer un article ", ErrorCode.PRODUCTFORMATED_NOT_DELETEABLE);
        }
        pfRepository.delete(optionalProductFormated.get());
        return true;
    }

    @Override
    public Boolean isProductFormatedDeleteable(Long pfId) {
        return true;
    }
}
