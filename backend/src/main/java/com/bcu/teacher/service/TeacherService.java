package com.bcu.teacher.service;

import com.bcu.common.result.Result;
import com.bcu.teacher.dao.TeacherMapper;
import com.bcu.teacher.bean.Teacher;
import com.bcu.teacher.bean.TeacherExample;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherMapper mapper;

    public Result getAllTeachers() {
        List<Teacher> teachers = mapper.selectByExample(null);
        if(teachers != null){
            return Result.success(teachers, "查询成功");
        }else return Result.error("未找到该教师");
    }

    public Result getTeacherByPrimaryKey(Integer t_id) {
        Teacher teacher = mapper.selectByPrimaryKey(t_id);
        if (teacher != null){
            return Result.success(teacher, "查询成功");
        }else return Result.error("未找到该教师");
    }

    public Result addTeacher(Teacher teacher) {
        if (teacher.getT_id()!= null){
            mapper.insertSelective(teacher);
            return Result.success(teacher, "教师信息添加成功");
        }else return Result.error("信息不完整，缺失教师id");}

    public Result updateTeacher(Teacher teacher) {
        if (teacher.getT_id() != null){
            int result = mapper.updateByPrimaryKey(teacher);
            if (result != 0){
                return Result.success(teacher, "更新成功");
            }else return Result.error("更新失败");
        }else return Result.error("缺失教师id");
    }

    public Result deleteTeacher(Integer t_id) {
        if (t_id!= null){
             mapper.deleteByPrimaryKey(t_id);
             return Result.success(t_id, "删除成功");
        }else return Result.error("缺失教师id，无法删除");
    }

    public Result getTeacherByNumber(String tNumber) {
        TeacherExample example = new TeacherExample();
        example.createCriteria().andT_numberEqualTo(tNumber);
        List<Teacher> teachers = mapper.selectByExample(example);
        if (!teachers.isEmpty()) {
            return Result.success(teachers.getFirst(), "查询成功");
        }else return Result.error("未找到该教师");
    }
}
