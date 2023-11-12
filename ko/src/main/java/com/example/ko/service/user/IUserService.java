package com.example.ko.service.user;

import com.example.ko.model.Users;

public interface IUserService {
    Users findByUserName(String userName);
    boolean exitsByUserName(String userName);
    boolean exitsByUserEmail(String email);
    Users saveOrUpdate(Users users);
}
