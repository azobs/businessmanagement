package com.c2psi.businessmanagement.controllers.apiImpl.pos.userbm;


import com.c2psi.businessmanagement.Enumerations.RoleType;
import com.c2psi.businessmanagement.Enumerations.UserBMState;
import com.c2psi.businessmanagement.Enumerations.UserBMType;
import com.c2psi.businessmanagement.dtos.pos.pos.EnterpriseDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.AddressDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.RoleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.services.contracts.pos.pos.EnterpriseService;
import com.c2psi.businessmanagement.services.contracts.pos.userbm.UserBMService;

import java.text.SimpleDateFormat;

public class UserBMDtoConstructor {

    public static AddressDto getAddressDto(String prefix, int num){
        AddressDto addressDto = AddressDto.builder()
                .email(prefix+"_"+num+"@gmail.com")
                .localisation("plan de localisation")
                .numtel1("645789562")
                .numtel2("658962145")
                .numtel3("647852132")
                .pays("Cameroon")
                .ville("Douala")
                .quartier("Babenga")
                .build();
        return addressDto;
    }

    public static UserBMDto getUserBMDtoToSave(String prefix, int num, Long posId){
        /****
         * Le userbmId sert lorsquon veut un userbm pour un update. l'Id du Userbm a
         * update sera donc renseigne a ce niveau
         */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            UserBMDto userBMDto = UserBMDto.builder()
                    .bmAddressDto(getAddressDto(prefix, num))
                    .bmCni("14253626_" + num)
                    .bmState(UserBMState.Activated)
                    .bmDob(sdf.parse("1989-05-15"))
                    .bmLogin(prefix+"login_"+num)
                    .bmName(prefix+"Name_"+num)
                    .bmPassword("password")
                    .bmRepassword("password")
                    .bmSurname("surname")
                    .bmUsertype(prefix.equalsIgnoreCase("adminbm")? UserBMType.AdminBM: prefix.equalsIgnoreCase("adminenterprise")? UserBMType.AdminEnterprise: UserBMType.Employe)
                    .bmPicture("picture")
                    .bmPosId(posId)
                    .build();
            return userBMDto;
        }
        catch (Exception e){
            return null;
        }
    }

    public static UserBMDto getUserBMDtoSaved(String prefix, int num, Long posId, UserBMService userBMService){
        /****
         * Le userbmId sert lorsquon veut un userbm pour un update. l'Id du Userbm a
         * update sera donc renseigne a ce niveau
         */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            UserBMDto userBMDto = UserBMDto.builder()
                    .bmAddressDto(getAddressDto(prefix, num))
                    .bmCni("14253626_" + num)
                    .bmState(UserBMState.Activated)
                    .bmDob(sdf.parse("1989-05-15"))
                    .bmLogin(prefix+"login_"+num)
                    .bmName(prefix+"Name_"+num)
                    .bmPassword("password")
                    .bmRepassword("password")
                    .bmSurname("surname")
                    .bmUsertype(prefix.equalsIgnoreCase("adminbm")? UserBMType.AdminBM: prefix.equalsIgnoreCase("adminenterprise")? UserBMType.AdminEnterprise: UserBMType.Employe)
                    .bmPicture("picture")
                    .bmPosId(posId)
                    .build();
            UserBMDto userBMDtoSaved = userBMService.saveUserBM(userBMDto);
            return userBMDtoSaved;
        }
        catch (Exception e){
            return null;
        }
    }

    public static EnterpriseDto getEnterpriseDtoSaved(int num, EnterpriseService enterpriseService,
                                                      UserBMService userBMService){
        UserBMDto userBMDtoSaved = userBMService.saveUserBM(getUserBMDtoToSave("AdminEnterprise", num+1, null));

        EnterpriseDto enterpriseDto = EnterpriseDto.builder()
                .entRegime("SI")
                .entSocialreason("Raison sociale")
                .entDescription("Description de la future entreprise")
                .entNiu("P025487569"+num)
                .entName("EnterpriseName_"+num)
                .entAcronym("Acronym_"+num)
                .entLogo("Logo.png")
                .entAddressDto(getAddressDto("AdminEnterprise", num+1))
                .entAdminDto(userBMDtoSaved)
                .build();

        EnterpriseDto enterpriseDtoSaved = enterpriseService.saveEnterprise(enterpriseDto);
        return enterpriseDtoSaved;
    }

    public static RoleDto getRoleDtoJsonToSave(int num, EnterpriseService enterpriseService, UserBMService userBMService){

        EnterpriseDto enterpriseDtoSaved = getEnterpriseDtoSaved(num, enterpriseService, userBMService);

        RoleDto roleDto = RoleDto.builder()
                .roleDescription("Description du role vendeur")
                .roleAlias("vendeur")
                .roleName(RoleType.Manager)
                .roleEntDto(enterpriseDtoSaved)
                .build();
        return roleDto;
    }

}
