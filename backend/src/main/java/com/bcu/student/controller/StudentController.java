package com.bcu.student.controller;

import com.bcu.common.result.Result;
import com.bcu.student.bean.Student;
import com.bcu.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping("/{id}")
    public Result getStudentById(@PathVariable Integer id) {
        return studentService.getStudentById(id);
    }

    @GetMapping("/getbyusername/{userNumber}")
    public Result getByUsername(@PathVariable Integer userNumber) {
        return studentService.getStudentByUserNumber(userNumber);
    }

    @GetMapping
    public Result getAllStudents() {
        return studentService.getAllStudents();
    }

    @PostMapping
    public Result addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @PutMapping
    public Result updateStudent(@RequestBody Student student) {
        return studentService.updateStudent(student);
    }
    @PutMapping("{sid}/{academicid}/{industryid}")
    public Result updateStudentAcademicAndIndustryTeacher(@PathVariable Integer sid,@PathVariable Integer academicid,@PathVariable Integer industryid){
        return studentService.updateStudentAcademicAndIndustryTeacher(sid,academicid,industryid);
    }

    @DeleteMapping("/{id}")
    public Result deleteStudent(@PathVariable Integer id) {
        return studentService.deleteStudent(id);
    }

    @GetMapping("/checkdocument/{studentNumber}")
    public Result checkWordDocumentExists(@PathVariable String studentNumber) {
        return studentService.checkWordDocumentExists(studentNumber);
    }

    @GetMapping("/download/{studentNumber}")
    public ResponseEntity<Resource> downloadWordDocument(@PathVariable String studentNumber) {
        try {
            return studentService.downloadWordDocument(studentNumber);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
