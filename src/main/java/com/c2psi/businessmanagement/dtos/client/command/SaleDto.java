package com.c2psi.businessmanagement.dtos.client.command;

import com.c2psi.businessmanagement.Enumerations.SaleType;
import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import com.c2psi.businessmanagement.models.Sale;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@Builder
public class SaleDto {
    Long id;
    @NotNull(message = "The command code cannot be null")
    @Positive(message = "The sale quantity sale must be positive")
    Double saleQuantity;
    String saleComment;
    @NotNull(message = "The sale final price cannot be null")
    @Positive(message = "The sale final price must be positive")
    BigDecimal saleFinalprice;
    @NotNull(message = "The sale type cannot be null")
    SaleType saleType;

    /******************************
     * Relation between entities  *
     * ****************************/

    @NotNull(message = "The command associated to the sale details cannot be null")
    CommandDto saleCommandDto;

    @NotNull(message = "The article associated to the sale details cannot be null")
    ArticleDto saleArticleDto;

    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static SaleDto fromEntity(Sale sale){
        if(sale == null){
            return null;
        }
        return SaleDto.builder()
                .id(sale.getId())
                .saleQuantity(sale.getSaleQuantity())
                .saleComment(sale.getSaleComment())
                .saleFinalprice(sale.getSaleFinalprice())
                .saleType(sale.getSaleType())
                .saleCommandDto(CommandDto.fromEntity(sale.getSaleCommand()))
                .saleArticleDto(ArticleDto.fromEntity(sale.getSaleArticle()))
                .build();
    }
    public static Sale toEntity(SaleDto saleDto){
        if(saleDto == null){
            return null;
        }
        Sale sale = new Sale();
        sale.setId(saleDto.getId());
        sale.setSaleQuantity(saleDto.getSaleQuantity());
        sale.setSaleComment(saleDto.getSaleComment());
        sale.setSaleFinalprice(saleDto.getSaleFinalprice());
        sale.setSaleType(saleDto.getSaleType());
        sale.setSaleCommand(CommandDto.toEntity(saleDto.getSaleCommandDto()));
        sale.setSaleArticle(ArticleDto.toEntity(saleDto.getSaleArticleDto()));
        return sale;
    }
}
