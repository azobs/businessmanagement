package com.c2psi.businessmanagement.services.contracts.stock.product;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.product.FormatDto;
import com.c2psi.businessmanagement.dtos.stock.product.ProductDto;
import com.c2psi.businessmanagement.dtos.stock.product.ProductFormatedDto;

import java.util.List;

public interface ProductFormatedService {
    ProductFormatedDto saveProductFormated(ProductFormatedDto pfDto);
    ProductFormatedDto findProductFormated(Long pfId);
    ProductFormatedDto findProductFormated(ProductDto prodDto, FormatDto formatDto);
    Boolean isProductFormatedUnique(ProductDto prodDto, FormatDto formatDto);
    List<ProductFormatedDto> findAllProductFormated(PointofsaleDto posDto);
    Boolean deleteProductFormatedById(Long pfId);
    Boolean isProductFormetedUsed(ProductFormatedDto pfDto);
}
