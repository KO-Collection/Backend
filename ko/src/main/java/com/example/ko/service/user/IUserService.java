package com.example.ko.service.user;

import com.example.ko.model.Users;

public interface IUserService {
    Users findByUserName(String userName);
    boolean exitsByUserName(String userName);
    boolean exitsByUserEmail(String email);
    boolean exitsByUserPhone(String phone);
    Users saveOrUpdate(Users users);
    int updateUser(Users users);
}
