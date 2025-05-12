package com.bcu.user.controller;

import com.bcu.common.result.Result;
import com.bcu.user.bean.User;
import com.bcu.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    /*
    * 用户登录
    * @param user 用户名密码
    * @return Result
    * */
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        return userService.findUserByUsernameAndPassword(user);
    }
}
