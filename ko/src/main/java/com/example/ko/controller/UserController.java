package com.example.ko.controller;

import com.example.ko.config.JwtTokenProvider;
import com.example.ko.model.ERole;
import com.example.ko.model.Roles;
import com.example.ko.model.Users;
import com.example.ko.payload.request.LoginRequest;
import com.example.ko.payload.request.SignupRequest;
import com.example.ko.payload.response.JwtResponse;
import com.example.ko.payload.response.MessageResponse;
import com.example.ko.sercurity.JwtUserDetails;
import com.example.ko.service.IRolesService;
import com.example.ko.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/auth")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private IUserService userService;
    @Autowired
    private IRolesService rolesService;
    @Autowired
    private PasswordEncoder encoder;
//    Đăng ký tài khoản
    @PostMapping("/sigup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest){
        if (userService.exitsByUserName(signupRequest.getUserName())){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Tên đăng nhập đã được đăng ký"));
        }
        if (userService.exitsByUserEmail(signupRequest.getUserEmail())){
            return ResponseEntity.badRequest().body(new MessageResponse("Error?: Email đã được đăng ký"));
        }
        Users users = new Users();
        users.setUserName(signupRequest.getUserName());
        users.setUserPassWord(encoder.encode(signupRequest.getUserPassWord()));
        users.setUserEmail(signupRequest.getUserEmail());
        users.setUserImg(signupRequest.getUserImg());
        users.setBirthDay(signupRequest.getBirthDay());
        users.setAddress(signupRequest.getAddress());
        users.setCreatedTime(signupRequest.getCreatedTime());
        users.setPhoneNumber(signupRequest.getPhoneNumber());
        users.setFlagOnline(signupRequest.getFlagOnline());
        Set<String> strRoles = signupRequest.getListRoles();
        Set<Roles> listRoles = new HashSet<>();
        if (strRoles == null){
//            User quyền mặc định
            Roles userRole = rolesService.findByRoleName(ERole.ROLE_USER).orElseThrow(()-> new RuntimeException("Error: Role không tồn tại"));
            listRoles.add(userRole);
        }else {
            strRoles.forEach(role -> {
                switch (role){
                    case "admin":
                        Roles adminRole = rolesService.findByRoleName(ERole.ROLE_ADMIN)
                                .orElseThrow(()->new RuntimeException("Error: Role is not found"));
                                listRoles.add(adminRole);
                    case "user":
                        Roles userRole = rolesService.findByRoleName(ERole.ROLE_USER)
                                .orElseThrow(()->new RuntimeException("Error: Role is not found"));
                                listRoles.add(userRole);
                }
            });
        }
        users.setListRoles(listRoles);
        userService.saveOrUpdate(users);
        return ResponseEntity.ok(new MessageResponse("Tài khoản đã được đăng ký thành công"));
    }
//Đăng nhập
    @PostMapping("/signin")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),loginRequest.getUserPassWord())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        JwtUserDetails jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();
        String jwt = jwtTokenProvider.generateToken(jwtUserDetails);
        List<String> listRoles = jwtUserDetails.getAuthorities().stream()
                .map(item->item.getAuthority()).collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt,jwtUserDetails.getUsername(),jwtUserDetails.getUserEmail(),jwtUserDetails.getPhoneNumber(),listRoles));
    }
}
