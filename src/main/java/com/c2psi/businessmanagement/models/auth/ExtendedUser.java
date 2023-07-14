package com.c2psi.businessmanagement.models.auth;

import com.c2psi.businessmanagement.Enumerations.UserBMType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class ExtendedUser extends User {
    @Getter
    @Setter
    private UserBMType userBMType;
    @Getter
    @Setter
    private Long idEnterprise;
    @Getter
    @Setter
    private Long idPos;

    public ExtendedUser(String username, String password, Collection<? extends GrantedAuthority> authorities,
                        UserBMType userBMType, Long idEnterprise, Long idPos) {
        super(username, password, authorities);
        this.userBMType = userBMType;
        this.idEnterprise = idEnterprise;
        this.idPos = idPos;
    }
}
