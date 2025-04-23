package com.bcu.user.service;

import com.bcu.user.bean.TUser;
import com.bcu.user.bean.TUserExample;
import com.bcu.user.dao.TUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final TUserMapper mapper;

    public TUser findUserByUsernameAndPassword(TUser user) {
        TUserExample example = new TUserExample();
        TUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(user.getUsername());
        criteria.andUserpasswordEqualTo(user.getUserpassword());
        List<TUser> tUsers = mapper.selectByExample(example);
        if (tUsers.size() == 1) {
            return tUsers.get(0);
        } else {
            return null;
        }
    }
}
