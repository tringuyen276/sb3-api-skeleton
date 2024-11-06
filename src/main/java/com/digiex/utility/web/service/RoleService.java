package com.digiex.utility.web.service;

import com.digiex.utility.web.model.Permission;
import com.digiex.utility.web.model.Role;
import com.digiex.utility.web.repository.RoleReposity;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
@Service
public class RoleService {

    @Autowired
    private RoleReposity roleReposity;


    public Set<Permission> findPermissionByRoleId(Long roleId){
        Optional< Role> role=roleReposity.findById(roleId);
        return role.map(Role::getPermissions).orElse(Collections.emptySet());
    }
}
