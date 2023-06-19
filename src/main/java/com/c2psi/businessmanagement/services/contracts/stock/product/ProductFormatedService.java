package com.c2psi.businessmanagement.services.contracts.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.ProductFormatedDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductFormatedService {
    ProductFormatedDto saveProductFormated(ProductFormatedDto pfDto);
    ProductFormatedDto updateProductFormated(ProductFormatedDto pfDto);
    ProductFormatedDto findProductFormatedById(Long pfId);
    ProductFormatedDto findProductFormatedByProductIdAndFormatId(Long prodId, Long formatId);
    Boolean isProductFormatedUnique(Long prodId, Long formatId);
    List<ProductFormatedDto> findAllProductFormatedInPos(Long posId);
    Page<ProductFormatedDto> findPageProductFormatedInPos(Long posId, int pagenum, int pagesize);
    Boolean deleteProductFormatedById(Long pfId);
    Boolean isProductFormatedDeleteable(Long pfId);
}
