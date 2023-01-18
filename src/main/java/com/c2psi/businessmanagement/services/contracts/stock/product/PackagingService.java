package com.c2psi.businessmanagement.services.contracts.stock.product;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.product.PackagingDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;

import java.util.List;

public interface PackagingService {
    PackagingDto savePackaging(PackagingDto packDto);

    PackagingDto findPackaging(String pack_label, String pack_firstcolor,
                               ProviderDto providerDto, PointofsaleDto posDto);
    Boolean isPackagingUnique(String packLabel, String packFirstcolor,
                              ProviderDto providerDto, PointofsaleDto posDto);
    List<PackagingDto> findAllPackaging(PointofsaleDto posDto);
    Boolean deletePackagingById(Long packId);
}
