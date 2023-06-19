package com.c2psi.businessmanagement.dtos.client.command;

import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import com.c2psi.businessmanagement.models.BackInDetails;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@Builder
public class BackInDetailsDto {
    Long id;
    @NotNull(message = "The backindetails quantity cannot be null")
    @Positive(message = "The backindetails quantity must be positive")
    BigDecimal bidQuantity;
    String bidComment;
    @NotNull(message = "The backindetails article cannot be null")
    ArticleDto bidArticleDto;
    @NotNull(message = "The backindetails backin cannot be null")
    BackInDto bidbiDto;

    public static BackInDetailsDto fromEntity(BackInDetails backInDetails){
        if(backInDetails == null){
            return null;
        }
        return BackInDetailsDto.builder()
                .id(backInDetails.getId())
                .bidArticleDto(ArticleDto.fromEntity(backInDetails.getBidArticle()))
                .bidbiDto(BackInDto.fromEntity(backInDetails.getBidbi()))
                .bidComment(backInDetails.getBidComment())
                .bidQuantity(backInDetails.getBidQuantity())
                .build();
    }

    public static BackInDetails toEntity(BackInDetailsDto backInDetailsDto){
        if(backInDetailsDto == null){
            return null;
        }
        BackInDetails backInDetails = new BackInDetails();
        backInDetails.setId(backInDetailsDto.getId());
        backInDetails.setBidbi(BackInDto.toEntity(backInDetailsDto.getBidbiDto()));
        backInDetails.setBidArticle(ArticleDto.toEntity(backInDetailsDto.getBidArticleDto()));
        backInDetails.setBidComment(backInDetailsDto.getBidComment());
        backInDetails.setBidQuantity(backInDetailsDto.getBidQuantity());
        return backInDetails;
    }


}
