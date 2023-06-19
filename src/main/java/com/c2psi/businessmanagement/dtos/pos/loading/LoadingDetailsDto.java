package com.c2psi.businessmanagement.dtos.pos.loading;

import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import com.c2psi.businessmanagement.models.LoadingDetails;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@Builder
public class LoadingDetailsDto {
    Long id;
    @NotNull(message = "The quantity taken cannot be null")
    @Positive(message = "The quantity taken must be positive")
    BigDecimal ldQuantitytaken;
    @NotNull(message = "The quantity return cannot be null")
    @PositiveOrZero(message = "The quantity return must be positive or null")
    BigDecimal ldQuantityreturn;

    @NotNull(message = "The article associate with the details loading cannot be null")
    ArticleDto ldArticleDto;
    @NotNull(message = "The loading associate with the details loading cannot be null")
    LoadingDto ldLoadingDto;

    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static LoadingDetailsDto fromEntity(LoadingDetails ld) {
        if (ld == null) {
            return null;
        }
        return LoadingDetailsDto.builder()
                .id(ld.getId())
                .ldQuantitytaken(ld.getLdQuantitytaken())
                .ldQuantityreturn(ld.getLdQuantityreturn())
                .ldArticleDto(ArticleDto.fromEntity(ld.getLdArticle()))
                .ldLoadingDto(LoadingDto.fromEntity(ld.getLdLoading()))
                .build();
    }

    public static LoadingDetails toEntity(LoadingDetailsDto ld_dto) {
        if (ld_dto == null) {
            return null;
        }
        LoadingDetails ld = new LoadingDetails();
        ld.setId(ld_dto.getId());
        ld.setLdQuantityreturn(ld_dto.getLdQuantityreturn());
        ld.setLdQuantitytaken(ld_dto.getLdQuantitytaken());
        ld.setLdArticle(ArticleDto.toEntity(ld_dto.getLdArticleDto()));
        ld.setLdLoading(LoadingDto.toEntity(ld_dto.getLdLoadingDto()));

        return ld;
    }
}
