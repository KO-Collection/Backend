package com.example.ko.service;

import com.example.ko.model.ERole;
import com.example.ko.model.Roles;
import com.example.ko.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class RoleService implements IRolesService{
    @Autowired
    private IRoleRepository roleRepository;
    @Override
    public Optional<Roles> findByRoleName(ERole roleName) {

        return roleRepository.findByRolesName(roleName);
    }
}
