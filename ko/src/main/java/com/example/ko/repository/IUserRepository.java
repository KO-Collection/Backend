package com.example.ko.repository;

import com.example.ko.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface IUserRepository extends JpaRepository<Users,Long> {
    Users findByUserName(String userName);
    Boolean existsByUserName(String userName);
    Boolean existsByUserEmail(String email);

    Boolean existsByPhoneNumber(String phone);
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = " update users set name = :#{#customer.nameCustomer}, user_email = :#{#customer.userEmail}," +
            "address = :#{#customer.address}, phone_number = :#{#customer.phoneNumber}, birth_day = :#{#customer.birthDay} " +
            "where user_name = :#{#customer.userName} ")
    int updateOnlineCustomer(@Param("customer") Users customer);

}
