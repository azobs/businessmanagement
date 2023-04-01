package com.c2psi.businessmanagement.services.contracts.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.ProductDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    ProductDto saveProduct(ProductDto prodDto);
    ProductDto updateProduct(ProductDto prodDto);
    ProductDto findProductById(Long prodId);
    ProductDto findProductByProductCodeInPos(String prodName, Long posId);
    Boolean isProductUniqueInPos(String prodName, Long posId);
    Boolean deleteProductById(Long prodId);
    Boolean isProductDeleteable(Long prodId);
    List<ProductDto> findAllProductInPos(Long posId);
    List<ProductDto> findAllProductOfCategory(Long catId);
    Page<ProductDto> findPageofProductInPos(Long posId, int pagenum, int pagesize);
    Page<ProductDto> findPageOfProductOfCategory(Long catId, int pagenum, int pagesize);
    Page<ProductDto> findProductByProdCodeInPosContaining(Long posId, String sample, int pagenum, int pagesize);
}
