package com.c2psi.businessmanagement.services.contracts.stock.product;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.product.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto saveProduct(ProductDto prodDto);
    ProductDto findProduct(Long prodId);
    ProductDto findProduct(String prodName, PointofsaleDto posDto);
    Boolean isProductUnique(String prodName, PointofsaleDto posDto);
    Boolean deleteProductById(Long prodId);
    Boolean isProductUsed(ProductDto prodDto);
    List<ProductDto> findAllProduct(PointofsaleDto posDto);
}
