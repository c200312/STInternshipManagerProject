package com.bcu.internship.service;

import com.bcu.common.result.Result;
import com.bcu.internship.bean.Internship;
import com.bcu.internship.bean.InternshipExample;
import com.bcu.internship.bean.InternshipKey;
import com.bcu.internship.dao.InternshipMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InternshipService {
    @Autowired
    private InternshipMapper internshipMapper;

    // 添加实习信息
    public Result addInternship(Internship internship) {
        int result = internshipMapper.insertSelective(internship);
        if (result > 0) {
            return Result.success("添加成功");
        } else {
            return Result.error("添加失败");
        }
    }

    // 删除实习信息
    public Result deleteInternship(InternshipKey key) {
        int result = internshipMapper.deleteByPrimaryKey(key);
        if (result > 0) {
            return Result.success("删除成功");
        } else {
            return Result.error("删除失败");
        }
    }

    // 更新实习信息
    public Result updateInternship(Internship internship) {
        int result = internshipMapper.updateByPrimaryKeySelective(internship);
        if (result > 0) {
            return Result.success("更新成功");
        } else {
            return Result.error("更新失败");
        }
    }

    // 查询所有实习信息
    public Result getAllInternships() {
        InternshipExample example = new InternshipExample();
        List<Internship> internships = internshipMapper.selectByExample(example);
        return Result.success(internships, "查询成功");
    }

    // 根据主键查询实习信息
    public Result getInternshipById(InternshipKey key) {
        Internship internship = internshipMapper.selectByPrimaryKey(key);
        if (internship != null) {
            return Result.success(internship, "查询成功");
        } else {
            return Result.error("未找到相关记录");
        }
    }
}