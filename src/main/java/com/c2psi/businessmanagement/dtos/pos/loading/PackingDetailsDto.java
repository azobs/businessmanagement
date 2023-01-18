package com.c2psi.businessmanagement.dtos.pos.loading;

import com.c2psi.businessmanagement.dtos.stock.product.PackagingDto;
import com.c2psi.businessmanagement.models.PackingDetails;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.stream.Collectors;


@Data
@Builder
public class PackingDetailsDto {
    Long id;
    @NotNull(message = "The number of package used cannot be null")
    @Positive(message = "The number of package used must be positive")
    Integer pdNumberofpackageused;
    @PositiveOrZero(message = "The number of package return must be positive or null")
    Integer pdNumberofpackagereturn;

    /******************************
     * Relation between entities  *
     * ****************************/
    @NotNull(message = "The packaging associated cannot be null")
    PackagingDto pdPackagingDto;

    @NotNull(message = "The loading associated cannot be null")
    LoadingDto pdLoadingDto;

    /***********************************
     * Mapping method development:   ***
     * method fromEntity and toEntity **
     ***********************************/
    public static PackingDetailsDto fromEntity(PackingDetails pd) {
        if (pd == null) {
            return null;
        }
        return PackingDetailsDto.builder()
                .id(pd.getId())
                .pdNumberofpackageused(pd.getPdNumberofpackageused())
                .pdNumberofpackagereturn(pd.getPdNumberofpackagereturn())
                .pdPackagingDto(PackagingDto.fromEntity(pd.getPdPackaging()))
                .pdLoadingDto(LoadingDto.fromEntity(pd.getPdLoading()))
                .build();
    }

    public static PackingDetails toEntity(PackingDetailsDto pdDto) {
        if (pdDto == null) {
            return null;
        }
        PackingDetails pd = new PackingDetails();
        pd.setId(pdDto.getId());
        pd.setPdNumberofpackageused(pdDto.getPdNumberofpackageused());
        pd.setPdNumberofpackagereturn(pdDto.getPdNumberofpackagereturn());
        pd.setPdPackaging(PackagingDto.toEntity(pdDto.getPdPackagingDto()));
        pd.setPdLoading(LoadingDto.toEntity(pdDto.getPdLoadingDto()));

        return pd;
    }
}
