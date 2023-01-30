package com.c2psi.businessmanagement.dtos.client.command;

import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import com.c2psi.businessmanagement.models.BackIn;
import com.c2psi.businessmanagement.models.BackInDetails;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Builder
public class BackInDetailsDto {
    Long id;
    @NotNull(message = "The backindetails quantity cannot be null")
    @Positive(message = "The backindetails quantity must be positive")
    Double bidQuantity;
    String bidComment;
    @NotNull(message = "The backindetails article cannot be null")
    ArticleDto bidArticle;
    @NotNull(message = "The backindetails backin cannot be null")
    BackInDto bidbi;

    public static BackInDetailsDto fromEntity(BackInDetails backInDetails){
        if(backInDetails == null){
            return null;
        }
        return BackInDetailsDto.builder()
                .id(backInDetails.getId())
                .bidArticle(ArticleDto.fromEntity(backInDetails.getBidArticle()))
                .bidbi(BackInDto.fromEntity(backInDetails.getBidbi()))
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
        backInDetails.setBidbi(BackInDto.toEntity(backInDetailsDto.getBidbi()));
        backInDetails.setBidArticle(ArticleDto.toEntity(backInDetailsDto.getBidArticle()));
        backInDetails.setBidComment(backInDetailsDto.getBidComment());
        backInDetails.setBidQuantity(backInDetailsDto.getBidQuantity());
        return backInDetails;
    }


}
