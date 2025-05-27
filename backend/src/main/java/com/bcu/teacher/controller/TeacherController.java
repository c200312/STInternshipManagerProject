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
        return service.getAllTeachers();
    }

    @GetMapping("{t_number}")
    public Result getTeacherByNumber(@PathVariable String t_number) {
        return service.getTeacherByNumber(t_number);
    }

    @PostMapping
    public Result addTeacher(@RequestBody Teacher teacher) {
        return service.addTeacher(teacher);
    }

    @PutMapping
    public Result updateTeacher(@RequestBody Teacher teacher) {
        return service.updateTeacher(teacher);
    }

    @DeleteMapping("{id}")
    public Result deleteTeacher(@PathVariable Integer id) {
        return service.deleteTeacher(id);
    }
}
