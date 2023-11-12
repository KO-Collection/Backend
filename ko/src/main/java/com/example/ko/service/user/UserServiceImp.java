package com.example.ko.service.user;

import com.example.ko.model.Users;
import com.example.ko.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements IUserService {
    @Autowired
    private IUserRepository userRepository;

    @Override
    public Users findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public boolean exitsByUserName(String userName) {
        return userRepository.existsByUserName(userName);
    }

    @Override
    public boolean exitsByUserEmail(String email) {
        return userRepository.existsByUserEmail(email);
    }

    @Override
    public Users saveOrUpdate(Users users) {
        return userRepository.save(users);
    }
}
