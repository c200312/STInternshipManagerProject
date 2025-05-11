package com.bcu.student.controller;

import com.bcu.common.result.Result;
import com.bcu.student.bean.Student;
import com.bcu.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping("/{id}")
    public Result getStudentById(@PathVariable Integer id) {

        Student student = studentService.getStudentById(id);
        if (student == null) {
            return Result.error("未找到对应的学生信息");
        } else {
            return Result.success(student, "成功获取学生信息");
        }
    }

    @GetMapping
    public Result getAllStudents() {

        List<Student> students = studentService.getAllStudents();
        if (students.isEmpty()) {
            return Result.error("未找到任何学生信息");
        } else return Result.success(students, "成功获取所有学生信息");
    }

    @PostMapping("/add")
    public Result addStudent(@RequestBody Student student) {

        if (student == null || student.getStudent_name() == null || student.getStudent_number() == null) {
            return Result.error("学生信息不完整，无法添加");
        } else {
            studentService.addStudent(student);
            return Result.success(null, "学生信息添加成功");
        }
    }

    @PostMapping("/update")
    public Result updateStudent(@RequestBody Student student) {
        if (student == null || student.getS_id() == null) {
            return Result.error("学生ID缺失，无法更新");
        } else {
            studentService.updateStudent(student);
            return Result.success(null, "学生信息更新成功");
        }
    }

    @DeleteMapping("/{id}")
    public Result deleteStudent(@PathVariable Integer id) {
        if (id == null) {
            return Result.error("学生ID缺失，无法删除");
        } else {
            studentService.deleteStudent(id);
            return Result.success(null, "学生信息删除成功");
        }
    }
}
