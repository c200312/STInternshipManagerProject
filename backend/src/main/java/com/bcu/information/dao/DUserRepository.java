package com.bcu.information.dao;

import com.bcu.information.bean.DUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DUserRepository extends MongoRepository<DUser, String> {

    // 根据 ID（学号）查找（默认支持，无需声明）

    // 判断是否存在某个 ID（学号）（默认支持，无需声明）


    // 删除某个 ID（默认支持 deleteById，无需声明）


}
