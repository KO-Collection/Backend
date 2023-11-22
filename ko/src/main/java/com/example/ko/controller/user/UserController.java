package com.example.ko.controller.user;

import com.example.ko.config.JwtTokenProvider;
import com.example.ko.model.ERole;
import com.example.ko.model.Roles;
import com.example.ko.model.Users;
import com.example.ko.payload.request.LoginRequest;
import com.example.ko.payload.request.SignupRequest;
import com.example.ko.payload.response.JwtResponse;
import com.example.ko.payload.response.MessageResponse;
import com.example.ko.sercurity.JwtUserDetails;
import com.example.ko.service.user.IRolesService;
import com.example.ko.service.user.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
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
        if (!userService.exitsByUserName(loginRequest.getUserName())){
            return ResponseEntity.badRequest().body(new MessageResponse("Tên tài khoản không tồn tại"));
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getUserPassWord())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        JwtUserDetails jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();
        String jwt = jwtTokenProvider.generateToken(jwtUserDetails);
        List<String> listRoles = jwtUserDetails.getAuthorities().stream()
                .map(item -> item.getAuthority()).collect(Collectors.toList());
        Boolean users = userService.exitsByUserName(jwtUserDetails.getUserName());
        return ResponseEntity.ok(new JwtResponse(jwt, jwtUserDetails.getUsername(), jwtUserDetails.getUserEmail(), jwtUserDetails.getPhoneNumber(), listRoles));
    }

    @PatchMapping("/update")
    public ResponseEntity<Object> updateUser(@RequestBody SignupRequest signupRequest, @RequestParam(name = "name_user") String nameUser) {
        Map<String, String> error = new HashMap<>();
        Users userCheck = userService.findByUserName(nameUser);
        String phoneCheck = userCheck.getPhoneNumber();
        String emailCheck = userCheck.getUserEmail();
        if (userService.exitsByUserEmail(signupRequest.getUserEmail()) && (!emailCheck.equals(signupRequest.getUserEmail()))) {
            error.put("userEmail", "Email đã được đăng ký!");
        }
        if (userService.exitsByUserPhone(signupRequest.getPhoneNumber()) && (!phoneCheck.equals(signupRequest.getPhoneNumber()))) {
            error.put("phoneNumber", "Số điện thoại đã được đăng ký!");
        }
        if(error.size() > 0){
            return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
        }
        Users users = new Users();
        users.setUserName(userCheck.getUserName());
        users.setNameCustomer(signupRequest.getNameCustomer());
        users.setUserEmail(signupRequest.getUserEmail());
        users.setBirthDay(signupRequest.getBirthDay());
        users.setAddress(signupRequest.getAddress());
        users.setPhoneNumber(signupRequest.getPhoneNumber());
        userService.updateUser(users);
        return ResponseEntity.ok(new MessageResponse("Tài khoản đã được cập nhật thành công."));
    }

    @GetMapping("/detail/{userName}")
    public ResponseEntity<Object> getUserById(@PathVariable("userName") String userName) {
        Users users = userService.findByUserName(userName);
        if (users == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Tài khoản user không tồn tại"));
        }
        SignupRequest signupRequest = new SignupRequest();
        BeanUtils.copyProperties(users, signupRequest);
        return new ResponseEntity<>(signupRequest, HttpStatus.OK);
    }
}
