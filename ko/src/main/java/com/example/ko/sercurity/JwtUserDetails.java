package com.example.ko.sercurity;

import com.example.ko.model.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class JwtUserDetails implements UserDetails {
    private Long userId;
    private String userName;
    @JsonIgnore
    private String userPassWord;
    private String userEmail;
    private String phoneNumber;

    private Boolean flagOnline;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtUserDetails(Long userId, String userName, String userPassWord, String userEmail, String phoneNumber, Boolean flagOnline, Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.userName = userName;
        this.userPassWord = userPassWord;
        this.userEmail = userEmail;
        this.phoneNumber = phoneNumber;
        this.flagOnline = flagOnline;
        this.authorities = authorities;
    }

    @Override
    public String getPassword() {
        return this.userPassWord;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    //    Tu thông tin user chuyen sang thong tin JwtUserdetail
    public static JwtUserDetails mapUserToUserDetail(Users users) {
        List<GrantedAuthority> grantedAuthorityList = users.getListRoles().stream()
                .map(roles -> new SimpleGrantedAuthority(roles.getRolesName().name()))
                .collect(Collectors.toList());
        return new JwtUserDetails(
                users.getUserId(),
                users.getUserName(),
                users.getUserPassWord(),
                users.getUserEmail(),
                users.getPhoneNumber(),
                users.getFlagOnline(),
                grantedAuthorityList);

    }



    @Override
    public boolean isAccountNonExpired() {
        // Xác định logic kiểm tra tài khoản có hết hạn hay không
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Xác định logic kiểm tra tài khoản có bị khóa hay không
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Xác định logic kiểm tra thông tin xác thực (credentials) có hết hạn hay không
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Xác định logic kiểm tra tài khoản có được kích hoạt hay không
        return true;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassWord() {
        return userPassWord;
    }

    public void setUserPassWord(String userPassWord) {
        this.userPassWord = userPassWord;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getFlagOnline() {
        return flagOnline;
    }

    public void setFlagOnline(Boolean flagOnline) {
        this.flagOnline = flagOnline;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}
