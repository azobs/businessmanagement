package com.c2psi.businessmanagement.repositories.pos.userbm;

import com.c2psi.businessmanagement.models.Role;
import com.c2psi.businessmanagement.models.UserBM;
import com.c2psi.businessmanagement.models.UserBMRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserBMRoleRepository extends JpaRepository<UserBMRole, Long> {
    /***
     * Tous les Userbmroles associe à un UserBM
     * @param userbm
     * @return
     */
    List<UserBMRole> findAllByUserbmroleUserbm(UserBM userbm);

    /*********
     * Tous les userbmRole associe à un Role
     * @param role
     * @return
     */
    List<UserBMRole> findAllByUserbmroleRole(Role role);

    /***************
     * Retourne le UserBMRole qui correspond à un UserBM et un Role
     * @param userbm
     * @param role
     * @return
     */
    Optional<UserBMRole> findByUserbmroleUserbmAndUserbmroleRole(UserBM userbm, Role role);
    @Query("SELECT ubmr FROM UserBMRole ubmr WHERE ubmr.userbmroleUserbm.id=:userBMId AND ubmr.userbmroleRole.id=:roleId")
    Optional<UserBMRole> findUserBMRoleByUserbmAndRole(Long userBMId, Long roleId);

    /*@Query("SELECT ubmr FROM UserBMRole ubmr  " +
            "WHERE ubmr.userbmroleUserbm.bmPos.posEnterprise.id=:entId " +
            "AND ubmr.userbmroleRole.roleEnt.id=:entId")
    List<UserBMRole> findAllUserBMRoleofEnterprise(@Param("entId") Long entId);*/

    @Query("SELECT ubmr FROM UserBMRole ubmr  " +
            "WHERE ubmr.userbmroleUserbm.bmPosId=:posId ")
    List<UserBMRole> findAllUserBMRoleofPointofsale(@Param("posId") Long posId);

}
