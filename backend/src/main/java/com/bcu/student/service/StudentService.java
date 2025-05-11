package com.bcu.student.service;

import com.bcu.student.bean.Student;
import com.bcu.student.dao.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentMapper studentMapper;


    public Student getStudentById(Integer id) {
        return studentMapper.selectByPrimaryKey(id);
    }

    public List<Student> getAllStudents() {
        return studentMapper.selectByExample(null);
    }

    public void addStudent(Student student) {
        studentMapper.insertSelective(student);
    }

    public void updateStudent(Student student) {
        studentMapper.updateByPrimaryKeySelective(student);
    }

    public void deleteStudent(Integer id) {
        studentMapper.deleteByPrimaryKey(id);
    }
}