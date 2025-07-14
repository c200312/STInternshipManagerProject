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
import com.bcu.admin.bean.StudentDetailDTO;
import com.bcu.internship.bean.Internship;
import com.bcu.assessment.bean.Assessment;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.ArrayList;
import com.bcu.internship.dao.InternshipMapper;
import com.bcu.assessment.dao.AssessmentMapper;
import com.bcu.internship.bean.InternshipExample;
import com.bcu.assessment.bean.AssessmentExample;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherMapper mapper;
    private final StudentMapper studentMapper;
    private final InternshipMapper internshipMapper;
    private final AssessmentMapper assessmentMapper;

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

    // 工具方法，保证单元格存在
    private Cell getOrCreateCell(Row row, int colIdx) {
        Cell cell = row.getCell(colIdx);
        return cell != null ? cell : row.createCell(colIdx);
    }

    public ResponseEntity<byte[]> exportScoreTable(Integer t_id) {
        try {
            // 获取该教师指导的所有学生
            StudentExample example = new StudentExample();
            example.createCriteria().andAcademic_advisor_idEqualTo(t_id);
            List<Student> students = studentMapper.selectByExample(example);
            if (students.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            // 获取教师姓名
            String teacherName = "";
            Teacher teacher = mapper.selectByPrimaryKey(t_id);
            if (teacher != null) teacherName = teacher.getTeacher_name();
            // 组装学生详细信息
            List<StudentDetailDTO> detailList = new ArrayList<>();
            for (Student student : students) {
                StudentDetailDTO dto = new StudentDetailDTO();
                dto.setStudent(student);
                dto.setAcademicAdvisor(teacher);
                // 获取实习信息
                InternshipExample internshipExample = new InternshipExample();
                internshipExample.createCriteria().andS_idEqualTo(student.getS_id());
                List<Internship> internships = internshipMapper.selectByExample(internshipExample);
                Internship internship = internships != null && !internships.isEmpty() ? internships.get(0) : null;
                dto.setInternship(internship);
                // 获取评分信息
                AssessmentExample assessmentExample = new AssessmentExample();
                assessmentExample.createCriteria().andS_idEqualTo(student.getS_id());
                List<Assessment> assessments = assessmentMapper.selectByExample(assessmentExample);
                Assessment assessment = assessments != null && !assessments.isEmpty() ? assessments.get(0) : null;
                dto.setAssessment(assessment);
                detailList.add(dto);
            }
            // 读取模板
            String templatePath = "D:/templates/评分表模板.xlsx";
            FileInputStream fis = new FileInputStream(templatePath);
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);
            int rowNum = 4; // 第5行（下标从0开始）
            for (StudentDetailDTO dto : detailList) {
                Row row = sheet.getRow(rowNum);
                if (row == null) row = sheet.createRow(rowNum);
                // B5: 学号
                getOrCreateCell(row, 1).setCellValue(dto.getStudent().getStudent_number() == null ? "" : dto.getStudent().getStudent_number());
                // C5: 姓名
                getOrCreateCell(row, 2).setCellValue(dto.getStudent().getStudent_name() == null ? "" : dto.getStudent().getStudent_name());
                // D5: 班级名称
                getOrCreateCell(row, 3).setCellValue(dto.getStudent().getStu_class() == null ? "" : dto.getStudent().getStu_class());
                // E5: 实习单位
                String company = dto.getInternship() != null && dto.getInternship().getCompany_name() != null ? dto.getInternship().getCompany_name() : "";
                getOrCreateCell(row, 4).setCellValue(company);
                // F5: 指导教师
                getOrCreateCell(row, 5).setCellValue(teacherName);
                Assessment a = dto.getAssessment();
                // G5: 出勤率评分
                getOrCreateCell(row, 6).setCellValue(a != null && a.getAttendance_score() != null ? a.getAttendance_score() : 0);
                // H5: 任务完成
                getOrCreateCell(row, 7).setCellValue(a != null && a.getTask_score() != null ? a.getTask_score() : 0);
                // I5: 职业素养
                getOrCreateCell(row, 8).setCellValue(a != null && a.getProfessionalism_score() != null ? a.getProfessionalism_score() : 0);
                // J5: 校外实习单位评分
                getOrCreateCell(row, 9).setCellValue(a != null && a.getCompany_score() != null ? a.getCompany_score() : 0);
                // M5: 实习表现评分
                getOrCreateCell(row, 12).setCellValue(a != null && a.getPerformance_score() != null ? a.getPerformance_score() : 0);
                // R5: 校外总结
                getOrCreateCell(row, 17).setCellValue(a != null && a.getSummary_score() != null ? a.getSummary_score() : 0);
                // W5: 实践结果评分
                getOrCreateCell(row, 22).setCellValue(a != null && a.getPractice_result_score() != null ? a.getPractice_result_score() : 0);
                // X5: 教学单位评分
                getOrCreateCell(row, 23).setCellValue(a != null && a.getSchool_score() != null ? a.getSchool_score() : 0);
                // Y5: 总分
                getOrCreateCell(row, 24).setCellValue(a != null && a.getTotal_score() != null ? a.getTotal_score() : 0);
                rowNum++;
            }
            fis.close();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            workbook.write(bos);
            workbook.close();
            byte[] bytes = bos.toByteArray();
            String fileName = URLEncoder.encode("评分表.xlsx", StandardCharsets.UTF_8.toString());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", fileName);
            headers.setContentLength(bytes.length);
            return ResponseEntity.ok().headers(headers).body(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
