package com.bcu.user.bean;

import lombok.Data;

@Data
public class TUser {
    private String username;

    private String userpassword;

    private String usertype;

    private String newpassword;
}