package org.example.hrm.repository;

import org.example.hrm.model.Role;
import org.example.hrm.model.enumeration.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleName roleName);

    Set<Role> findAllByIdIn(Set<Long> ids);
}
