package com.bcu.user.service;

import com.bcu.user.bean.User;
import com.bcu.user.bean.UserExample;
import com.bcu.user.dao.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper mapper;
    //根据用户名密码查找数据库
    public User findUserByUsernameAndPassword(User user) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(user.getUsername());
        criteria.andPasswordEqualTo(user.getPassword());
        List<User> tUsers = mapper.selectByExample(example);
        if (tUsers.size() == 1) {
            return tUsers.getFirst();
        } else {
            return null;
        }
    }
}
