package com.bcu.teacher.controller;

import com.bcu.common.result.Result;
import com.bcu.teacher.bean.Teacher;
import com.bcu.teacher.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("teacher")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService service;

    @GetMapping
    public Result getAllTeachers() {
        List<Teacher> teachers = service.getAllTeachers();
        if (teachers != null){
            return Result.success(teachers,  "查询成功");
        }else return Result.error("未找到该教师");
    }

    @GetMapping("{id}")
    public Result getTeacherById(@PathVariable Integer id) {
        Teacher  teacher = service.selectByPrimaryKey(id);
        if (teacher != null){
            return Result.success(teacher, "查询成功");
        }else return Result.error("未找到该教师");
    }

    @PostMapping("add")
    public Result addTeacher(@RequestBody Teacher teacher) {
        int result =service.insert(teacher);
        if (result != 0){
        return Result.success(teacher, "添加成功");
        }else return Result.error("添加失败");

    }

    @PostMapping("update")
    public Result updateTeacher(@RequestBody Teacher teacher) {
        if (teacher.getT_id() != null){
            int result = service.updateByPrimaryKey(teacher);
            if (result != 0){
                return Result.success(teacher, "更新成功");
            }else return Result.error("更新失败");
        }else return Result.error("缺失教师id");
    }

    @DeleteMapping("{id}")
    public Result deleteTeacher(@PathVariable Integer id) {
        int result =service.deleteByPrimaryKey(id);
        if (result != 0){
            return Result.success(id,  "删除成功");
        }else return Result.error("删除失败");
    }
}
