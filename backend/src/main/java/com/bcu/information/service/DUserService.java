package com.bcu.information.service;

import com.bcu.information.bean.DComment;
import com.bcu.information.bean.DCompany;
import com.bcu.information.bean.DDiary;
import com.bcu.information.bean.DUser;
import com.bcu.information.dao.DUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DUserService {
    private final DUserRepository repository;

    // 初始化
    public DUser create(DUser user) {
        if (repository.existsById(user.getId())) {
            throw new RuntimeException("该学生 ID 已存在！");
        }
        return repository.save(user);
    }

    // 查询全部信息
    public List<DUser> getAll() {
        return repository.findAll();
    }

    // 按id查询全部信息
    public DUser getById(String id) {
        return repository.findById(id).orElse(null);
    }

    // 按id删除全部信息
    public boolean deleteById(String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    // 按id部分更新(按输入的信息进行覆盖，仅覆盖输入的部分)
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

        // 评语：按 week + teacher 唯一 → 替换或添加
        if (partial.getComment() != null) {
            for (DComment c : partial.getComment()) {
                user.getComment().removeIf(existing ->
                        existing.getWeek().equals(c.getWeek()) &&
                                existing.getTeachername().equals(c.getTeachername())
                );
                user.getComment().add(c);
            }
        }

        return repository.save(user);
    }




}
