package com.bcu.information.service;

import com.bcu.information.bean.*;
import com.bcu.information.dao.DUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class DUserService {

    @Autowired
    private DUserRepository repository;

    // 初始化
    public DUser create(DUser user) {
        if (repository.existsById(user.getId())) {
            throw new RuntimeException("该学生 ID 已存在！");
        }
        return repository.save(user);
    }

    // 查询全部
    public List<DUser> getAll() {
        return repository.findAll();
    }

    // 按id查询
    public DUser getById(String id) {
        return repository.findById(id).orElse(null);
    }

    // 按id删除
    public boolean deleteById(String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    // 部分更新
    public DUser patchUpdate(String id, DUser partial) {
        Optional<DUser> optionalUser = repository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("学生不存在");
        }

        DUser user = optionalUser.get();

        // 修改姓名
//        if (StringUtils.hasText(partial.getName())) {
//            user.setName(partial.getName());
//        }

        // 公司：按名称去重 → 如果存在就修改介绍，不存在就添加
        if (partial.getCompany() != null) {
            for (DCompany c : partial.getCompany()) {
                boolean updated = false;
                for (DCompany existing : user.getCompany()) {
                    if (existing.getName().equals(c.getName())) {
                        existing.setIntroduction(c.getIntroduction());
                        updated = true;
                        break;
                    }
                }
                if (!updated) {
                    user.getCompany().add(c);
                }
            }
        }

        // 周记：按 week 唯一 → 替换内容或添加
        if (partial.getDiary() != null) {
            for (DDiary d : partial.getDiary()) {
                user.getDiary().removeIf(existing -> existing.getWeek().equals(d.getWeek()));
                user.getDiary().add(d);
            }
        }

        // 评语：按 week + type + teacher 唯一 → 替换或添加
        if (partial.getComment() != null) {
            for (DComment c : partial.getComment()) {
                user.getComment().removeIf(existing ->
                        existing.getWeek().equals(c.getWeek()) &&
                                existing.getType().equals(c.getType()) &&
                                existing.getTeachername().equals(c.getTeachername())
                );
                user.getComment().add(c);
            }
        }

        return repository.save(user);
    }

}
