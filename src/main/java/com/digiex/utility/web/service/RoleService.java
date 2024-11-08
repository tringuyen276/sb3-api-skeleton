package com.digiex.utility.web.service;

import com.digiex.utility.web.model.Permission;
import com.digiex.utility.web.model.Role;
import com.digiex.utility.web.model.dto.RoleDTO;
import com.digiex.utility.web.repository.RoleReposity;
import com.digiex.utility.web.service.imp.RoleServiceImp;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoleService implements RoleServiceImp {

    @Autowired
    private RoleReposity roleReposity;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public RoleDTO save(RoleDTO roleDTO) {
        Role role = modelMapper.map(roleDTO, Role.class);
        Role savedRole = roleReposity.save(role);
        return modelMapper.map(savedRole, RoleDTO.class);
    }

    @Override
    public RoleDTO updateRole(long roleId, RoleDTO updateRole) {
        Role existingRole = roleReposity.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role not found with id: " + roleId));

        existingRole.setName(updateRole.getName());
        existingRole.setDescription(updateRole.getDescription());
        existingRole.setPermissions(updateRole.getPermissions());

        Role savedRole = roleReposity.save(existingRole); // Lưu lại và lấy role đã lưu
        return modelMapper.map(savedRole, RoleDTO.class); // Chuyển đổi sang DTO và trả về
    }



    @Override
    public void deleteRole(long id) {

    }

    @Override
    public Optional<RoleDTO> getRoleById(long id) {
        Optional<Role> role = roleReposity.findById(id);
        return role.map(r -> modelMapper.map(r, RoleDTO.class));
    }


    public Set<Permission> findPermissionByRoleId(Long roleId){
        Optional< Role> role=roleReposity.findById(roleId);
        return role.map(Role::getPermissions).orElse(Collections.emptySet());
    }
}
