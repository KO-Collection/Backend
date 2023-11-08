package com.example.ko.repository;

import com.example.ko.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<Users,Long> {
    Users findByUserName(String userName);
    Boolean existsByUserName(String userName);
    Boolean existsByUserEmail(String email);
}
