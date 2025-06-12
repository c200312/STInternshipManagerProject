package com.bcu.teacher.service;

import com.bcu.common.result.Result;
import com.bcu.teacher.dao.TeacherMapper;
import com.bcu.teacher.bean.Teacher;
import com.bcu.teacher.bean.TeacherExample;
import com.bcu.student.dao.StudentMapper;
import com.bcu.student.bean.Student;
import com.bcu.student.bean.StudentExample;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherMapper mapper;
    private final StudentMapper studentMapper;

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

    public Result getStudentsByTeacherId(Integer t_id) {
        try {
            StudentExample example = new StudentExample();
            example.createCriteria().andAcademic_advisor_idEqualTo(t_id);
            List<Student> students = studentMapper.selectByExample(example);
            return Result.success(students);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("查询学生失败");
        }
    }

    public ResponseEntity<byte[]> downloadStudentsReportsZip(Integer t_id) {
        try {
            // 获取该教师指导的所有学生
            StudentExample example = new StudentExample();
            example.createCriteria().andAcademic_advisor_idEqualTo(t_id);
            List<Student> students = studentMapper.selectByExample(example);

            if (students.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            // 创建临时ZIP文件
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ZipOutputStream zos = new ZipOutputStream(baos);

            for (Student student : students) {
                String studentNumber = student.getStudent_number();
                String wordFilePath = "D:\\templates\\" + studentNumber + "\\word\\";
                Path wordDir = Paths.get(wordFilePath);

                if (Files.exists(wordDir) && Files.isDirectory(wordDir)) {
                    // 遍历学生的word目录下的所有文件
                    Files.walk(wordDir)
                            .filter(Files::isRegularFile)
                            .filter(path -> path.toString().toLowerCase().endsWith(".docx"))
                            .forEach(filePath -> {
                                try {
                                    String fileName = filePath.getFileName().toString();
                                    ZipEntry zipEntry = new ZipEntry(fileName);
                                    zos.putNextEntry(zipEntry);
                                    Files.copy(filePath, zos);
                                    zos.closeEntry();
                                } catch (IOException e) {
                                    System.err.println("添加文件到ZIP失败: " + filePath + ", 错误: " + e.getMessage());
                                }
                            });
                }
            }

            zos.close();
            byte[] zipBytes = baos.toByteArray();

            // 设置响应头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "students_reports_" + t_id + ".zip");
            headers.setContentLength(zipBytes.length);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(zipBytes);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
