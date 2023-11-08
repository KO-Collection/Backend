package com.example.ko.service;

import com.example.ko.model.ERole;
import com.example.ko.model.Roles;

import java.util.Optional;

public interface IRolesService {
    Optional<Roles> findByRoleName(ERole roleName);
}
