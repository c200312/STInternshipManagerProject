package com.bcu.student.service;

import com.bcu.common.result.Result;
import com.bcu.student.bean.Student;
import com.bcu.student.bean.StudentExample;
import com.bcu.student.dao.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentMapper studentMapper;


    public Result getStudentById(Integer id) {
        Student student = studentMapper.selectByPrimaryKey(id);
        if (student != null) {
            return Result.success(student, "成功获取学生信息");
        } else {
            return Result.error("未找到对应的学生信息");
        }
    }

    public Result getAllStudents() {
        List<Student> students = studentMapper.selectByExample(null);
        if (students.isEmpty()) {
            return Result.error("未找到任何学生信息");
        } else return Result.success(students, "成功获取所有学生信息");
    }


    public Result addStudent(Student student) {
        if (student == null || student.getStudent_name() == null || student.getStudent_number() == null) {
            return Result.error("学生信息不完整，无法添加");
        } else {
            studentMapper.insertSelective(student);
            return Result.success(student, "学生信息添加成功");
        }
    }


    public Result updateStudent(Student student) {
        if (student == null || student.getS_id() == null) {
            return Result.error("学生ID缺失，无法更新");
        } else {
            studentMapper.updateByPrimaryKeySelective(student);
            return Result.success(student, "学生信息更新成功");
        }
    }


    public Result deleteStudent(Integer id) {
        if (id == null) {
            return Result.error("学生ID缺失，无法删除");
        } else {
            studentMapper.deleteByPrimaryKey(id);
            return Result.success(null, "学生信息删除成功");
        }
    }

    public Result getStudentByUserNumber(Integer userNumber) {
        StudentExample example = new StudentExample();
        example.createCriteria().andStudent_numberEqualTo(String.valueOf(userNumber));
        List<Student> students = studentMapper.selectByExample(example);
        if (students.size() == 1){
            return Result.success(students.getFirst(), "成功获取学生信息");
        }else return Result.error("未找到对应的学生信息");
    }
}