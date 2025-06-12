package com.bcu.student.service;

import com.bcu.common.result.Result;
import com.bcu.student.bean.Student;
import com.bcu.student.bean.StudentExample;
import com.bcu.student.dao.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

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
        if (student.getS_id() != null) {
            studentMapper.updateByPrimaryKeySelective(student);
            return Result.success(student, "学生信息更新成功");
        }else return Result.error("信息缺失，无法更新");
        }
    public Result updateStudentAcademicAndIndustryTeacher(Integer sid, Integer academicid, Integer industryid) {
        if(sid != null || academicid != null || industryid != null) {
            Student student = new Student();
            student.setS_id(sid);
            student.setAcademic_advisor_id(academicid);
            student.setIndustry_advisor_id(industryid);
            studentMapper.updateByPrimaryKeySelective(student);
            return Result.success(student, "学生信息更新成功");
        }else return Result.error("信息缺失，无法更新");

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


    public Result checkWordDocumentExists(String studentNumber) {
        try {
            // 构建文件目录路径
            String directoryPath = "D:/templates/" + studentNumber + "/word";
            Path dirPath = Paths.get(directoryPath);
            
            // 检查目录是否存在
            if (!Files.exists(dirPath) || !Files.isDirectory(dirPath)) {
                return Result.error("学生编号 " + studentNumber + " 对应的文档目录不存在");
            }
            
            // 查找目录下的Word文档
            try (Stream<Path> files = Files.list(dirPath)) {
                boolean hasWordFile = files
                    .filter(Files::isRegularFile)
                    .anyMatch(path -> path.toString().toLowerCase().endsWith(".docx") || 
                                     path.toString().toLowerCase().endsWith(".doc"));
                
                if (hasWordFile) {
                    return Result.success(true, "找到学生编号 " + studentNumber + " 对应的Word文档");
                } else {
                    return Result.error("学生编号 " + studentNumber + " 对应的目录下没有Word文档");
                }
            }
        } catch (IOException e) {
            return Result.error("检查文档时发生错误: " + e.getMessage());
        }
    }

    public ResponseEntity<Resource> downloadWordDocument(String studentNumber) {
        try {
            // 构建文件目录路径
            String directoryPath = "D:/templates/" + studentNumber + "/word";
            Path dirPath = Paths.get(directoryPath);
            
            // 检查目录是否存在
            if (!Files.exists(dirPath) || !Files.isDirectory(dirPath)) {
                System.out.println("目录不存在: " + directoryPath);
                return ResponseEntity.notFound().build();
            }
            
            // 查找目录下的Word文档
            try (Stream<Path> files = Files.list(dirPath)) {
                Path wordFile = files
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().toLowerCase().endsWith(".docx") || 
                                   path.toString().toLowerCase().endsWith(".doc"))
                    .findFirst()
                    .orElse(null);
                
                if (wordFile == null) {
                    System.out.println("未找到Word文档在目录: " + directoryPath);
                    return ResponseEntity.notFound().build();
                }
                
                System.out.println("找到文件: " + wordFile.toString());
                
                // 创建资源
                Resource resource = new FileSystemResource(wordFile.toFile());
                
                if (!resource.exists()) {
                    System.out.println("资源不存在: " + wordFile.toString());
                    return ResponseEntity.notFound().build();
                }
                
                // 设置响应头 - 使用UTF-8编码处理中文文件名
                String filename = wordFile.getFileName().toString();
                String encodedFilename = URLEncoder.encode(filename, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
                
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encodedFilename);
                headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
                headers.add("Access-Control-Expose-Headers", "Content-Disposition");
                
                System.out.println("准备下载文件: " + filename + ", 大小: " + resource.contentLength());
                
                return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(resource.contentLength())
                    .body(resource);
                    
            }
        } catch (Exception e) {
            System.err.println("下载文档时发生异常: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

}