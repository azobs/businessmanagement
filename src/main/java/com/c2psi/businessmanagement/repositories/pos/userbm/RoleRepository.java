package com.c2psi.businessmanagement.repositories.pos.userbm;

import com.c2psi.businessmanagement.models.Enterprise;
import com.c2psi.businessmanagement.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findRoleByRoleNameAndRoleEnt(String roleName, Enterprise roleEnt);

    List<Role> findAllByRoleEnt(Enterprise roleEnt);

}
