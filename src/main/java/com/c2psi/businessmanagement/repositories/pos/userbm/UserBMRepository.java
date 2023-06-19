package com.c2psi.businessmanagement.repositories.pos.userbm;

import com.c2psi.businessmanagement.Enumerations.UserBMType;
import com.c2psi.businessmanagement.models.Pointofsale;
import com.c2psi.businessmanagement.models.UserBM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UserBMRepository extends JpaRepository<UserBM, Long> {

    Optional<UserBM> findUserBMById(Long userBMId);
    Optional<UserBM> findUserBMByBmNameAndBmSurnameAndBmDob(String bmName, String bmSurname, Date bmDob);
    @Query("SELECT ub FROM UserBM  ub WHERE ub.bmName=:bmName AND ub.bmSurname=:bmSurname AND ub.bmDob BETWEEN :bmDob AND :bmDob")
    Optional<UserBM> findUserBMByFullname(String bmName, String bmSurname, Date bmDob);//DATE(s.date)
    Optional<UserBM> findUserBMByBmCni(String bmCni);
    Optional<UserBM> findUserBMByBmLogin(String bmLogin);
    @Query("SELECT ub FROM UserBM  ub WHERE ub.bmAddress.email=:bmEmail")
    Optional<UserBM> findUserBMByBmEmail(@Param("bmEmail") String bmEmail);

    @Query("SELECT ub FROM UserBM  ub WHERE ub.bmPosId=:idPos")
    List<UserBM> findAllByBmPos(@Param("idPos") Long idPos);

    @Query("SELECT ub FROM UserBM  ub WHERE ub.bmPosId=:idPos")
    Page<UserBM> findPageByBmPos(@Param("idPos") Long idPos, Pageable pageable);

    Page<UserBM> findAllByBmPosId(Long bmPosId, Pageable pageable);

    @Query("SELECT ub FROM UserBM  ub WHERE ub.bmPosId=:idPos AND (ub.bmName LIKE :sample OR ub.bmSurname LIKE :sample)")
    Page<UserBM> findAllByBmPosContaining(@Param("idPos") Long idPos,
                                          @Param("sample") String sample, Pageable pageable);

    List<UserBM> findAllByBmUsertype(UserBMType userBMType);
    Page<UserBM> findAllByBmUsertype(UserBMType userBMType, Pageable pageable);

    @Query("SELECT ub FROM UserBM  ub WHERE ub.bmName LIKE :sample OR ub.bmSurname LIKE :sample " +
            "ORDER BY ub.bmName ASC")
    Page<UserBM> findAllByBmNameOrBmSurnameContaining(@Param("sample") String sample, Pageable pageable);

}
