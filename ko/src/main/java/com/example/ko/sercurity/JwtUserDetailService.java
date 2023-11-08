package com.example.ko.sercurity;

import com.example.ko.model.Users;
import com.example.ko.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailService implements UserDetailsService {
   @Autowired
   private IUserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRepository.findByUserName(username);
        if (users == null){
            throw new UsernameNotFoundException("Không tìm thấy user");
        }
        return JwtUserDetails.mapUserToUserDetail(users);
    }
}
