package com.example.ko.repository;

import com.example.ko.model.ERole;
import com.example.ko.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRoleRepository extends JpaRepository<Roles,Long> {
    Optional<Roles> findByRolesName(ERole roleName);
}
