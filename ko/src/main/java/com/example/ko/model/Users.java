package com.example.ko.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "user_name",unique = true,nullable = false)
    private String userName;
    @Column(name = "user_password",nullable = false)
    @JsonIgnore
    private String userPassWord;
    @Column(name = "user_email",unique = true,nullable = false)
    private String userEmail;
    @Column(name = "create_time")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date createdTime;
    @Column(name = "phone_number",unique = true)
    private String phoneNumber;
    @Column(name = "birth_day",columnDefinition = "date")
    private String birthDay;
    @Column(name = "user_img",columnDefinition = "longtext")
    private  String userImg;
    @Column(columnDefinition = "text")
    private String address;
    @Column(name = "flag_delete")
    private Boolean flagDelete = true;
    @Column(name = "flag_online")
    private Boolean flagOnline = false;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Roles> listRoles = new HashSet<>();

}
