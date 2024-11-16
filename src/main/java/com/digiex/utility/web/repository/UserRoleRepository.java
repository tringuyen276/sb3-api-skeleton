package com.digiex.utility.web.repository;

import com.digiex.utility.web.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole,Long> {
}
