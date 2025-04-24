package com.bcu.user.controller;

import com.bcu.common.result.Result;
import com.bcu.user.bean.TUser;
import com.bcu.user.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService userService;

    @PostMapping("/login")
    public Result login(@RequestBody TUser user) {
        TUser dbuser = userService.findUserByUsernameAndPassword(user);
        if (dbuser != null) {
            return Result.success(dbuser, "登陆成功！");
        }
        return Result.error("用户名密码错误");
    }
}
