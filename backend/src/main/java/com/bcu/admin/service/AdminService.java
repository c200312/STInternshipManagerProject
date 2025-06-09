package com.bcu.admin.service;

import com.bcu.admin.bean.StudentDetailDTO;
import com.bcu.assessment.bean.Assessment;
import com.bcu.assessment.dao.AssessmentMapper;
import com.bcu.common.result.Result;
import com.bcu.common.util.ExcelCellUtil;
import com.bcu.information.bean.DUser;
import com.bcu.information.dao.DUserRepository;
import com.bcu.internship.bean.Internship;
import com.bcu.internship.dao.InternshipMapper;
import com.bcu.student.bean.Student;
import com.bcu.student.dao.StudentMapper;
import com.bcu.teacher.bean.Teacher;
import com.bcu.teacher.dao.TeacherMapper;
import com.bcu.user.bean.User;
import com.bcu.user.dao.UserMapper;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final StudentMapper studentMapper;
    private final TeacherMapper teacherMapper;
    private final UserMapper userMapper;
    private final InternshipMapper internshipMapper;
    private final AssessmentMapper assessmentMapper;
    private final DUserRepository  dUserRepository;

    public Result importExcel(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        if (fileName == null || (!fileName.endsWith(".xlsx") && !fileName.endsWith(".xls"))) {
            return Result.error("请上传正确格式的文件");
        }

        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = fileName.endsWith(".xlsx") ? new XSSFWorkbook(inputStream) : new HSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Row header = sheet.getRow(0);
            if (header == null) {
                return Result.error("文件表头为空请重新上传");
            }

            String type = detectType(header);
            return switch (type) {
                case "student" -> importData(sheet, this::mapStudentRow, studentMapper::insert);
                case "teacher" -> importData(sheet, this::mapTeacherRow, teacherMapper::insert);
                case "user" -> importData(sheet, this::mapUserRow, userMapper::insert);
                default -> Result.error("请使用系统模板");
            };
        }
    }

    private <T> Result importData(Sheet sheet, RowMapper<T> mapper, DataInserter<T> inserter) {
        List<T> successList = new ArrayList<>();
        List<T> errorList = new ArrayList<>();

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;
            try {
                T item = mapper.map(row);
                if (inserter.insert(item) == 1) successList.add(item);
                else errorList.add(item);
            } catch (Exception e) {
                errorList.add(null); // 可选：记录失败
            }
        }

        if (errorList.isEmpty()) return Result.success(successList, "导入成功");
        else return Result.error(errorList, "该部分导入失败请检查后重新导入");
    }

    private User mapUserRow(Row row) {
        User user = new User();
        user.setUsername(ExcelCellUtil.getString(row, 0));
        user.setPassword(ExcelCellUtil.getString(row, 1));
        user.setRole(ExcelCellUtil.getString(row, 2));
        return user;
    }

    private Teacher mapTeacherRow(Row row) {
        Teacher teacher = new Teacher();
        teacher.setT_number(ExcelCellUtil.getString(row, 0));
        teacher.setTeacher_name(ExcelCellUtil.getString(row, 1));
        teacher.setGender(ExcelCellUtil.getString(row, 2));
        teacher.setAdvisor_type(ExcelCellUtil.getString(row, 3));
        teacher.setAge(ExcelCellUtil.getInt(row, 4));
        teacher.setEducation(ExcelCellUtil.getString(row, 5));
        teacher.setPosition(ExcelCellUtil.getString(row, 6));
        teacher.setPhone(ExcelCellUtil.getString(row, 7));
        teacher.setEmail(ExcelCellUtil.getString(row, 8));
        return teacher;
    }

    private Student mapStudentRow(Row row) {
        Student student = new Student();
        student.setStudent_number(ExcelCellUtil.getString(row, 0));
        student.setStudent_name(ExcelCellUtil.getString(row, 1));
        student.setGender(ExcelCellUtil.getString(row, 2));
        student.setPhone(ExcelCellUtil.getString(row, 3));
        student.setParent_phone(ExcelCellUtil.getString(row, 4));
        student.setEnrollment_year(ExcelCellUtil.getInt(row, 5));
        student.setDepartment(ExcelCellUtil.getString(row, 6));
        student.setMajor(ExcelCellUtil.getString(row, 7));
        student.setStu_class(ExcelCellUtil.getString(row, 8));
        student.setCounselor(ExcelCellUtil.getString(row, 9));
        student.setCounselor_phone(ExcelCellUtil.getString(row, 10));
        return student;
    }

    private String detectType(Row header) {
        String firstCell = header.getCell(0).getStringCellValue();
        if (firstCell.contains("学号")) return "student";
        if (firstCell.contains("工号")) return "teacher";
        if (firstCell.contains("用户名")) return "user";
        return "unknown";
    }

    public Result getStInfo(){
        List<Student> students = studentMapper.selectByExample(null);
        List<Teacher> teachers = teacherMapper.selectByExample(null);
        List<Internship> internships = internshipMapper.selectByExample(null);
        List<Assessment> assessments = assessmentMapper.selectByExample(null);
        List<DUser> dUsers = dUserRepository.findAll();


        // 构造：学生ID => 实习记录列表 映射
        Map<Integer, List<Internship>> internshipMap = internships.stream()
                .collect(Collectors.groupingBy(Internship::getS_id));
        Map<Integer, List<Assessment>> assessmentMap = assessments.stream()
                .collect(Collectors.groupingBy(Assessment::getS_id));
        Map<String, List<DUser>> dUserMap = dUsers.stream()
                .collect(Collectors.groupingBy(DUser::getId));
        Map<Integer,List<Teacher>> teacherMap = teachers.stream()
                .collect(Collectors.groupingBy(Teacher::getT_id));


        List<StudentDetailDTO> result = new ArrayList<>();

        for (Student student : students) {
            StudentDetailDTO dto = new StudentDetailDTO();
            dto.setStudent(student);

            // 设置一个实习信息（如有）
            List<Internship> studentInternships = internshipMap.get(student.getS_id());
            if (studentInternships != null && !studentInternships.isEmpty()) {
                dto.setInternship(studentInternships.getFirst());
            }
            List<Assessment> studentAssessments = assessmentMap.get(student.getS_id());
            if (studentAssessments != null && !studentAssessments.isEmpty()) {
                dto.setAssessment(studentAssessments.getFirst());
            }
            List<DUser> studentDUsers = dUserMap.get(student.getStudent_number());
            if (studentDUsers != null && !studentDUsers.isEmpty()) {
                dto.setDuser(studentDUsers.getFirst());
            }
            List<Teacher> academicAdvisors = teacherMap.get(student.getAcademic_advisor_id());
            if (academicAdvisors != null && !academicAdvisors.isEmpty()) {
                dto.setAcademicAdvisor(academicAdvisors.getFirst());
            }
            List<Teacher> industryAdvisors = teacherMap.get(student.getIndustry_advisor_id());
            if (industryAdvisors != null && !industryAdvisors.isEmpty()) {
                dto.setIndustryAdvisor(industryAdvisors.getFirst());
            }

            result.add(dto);
        }

        return Result.success(result);
    }

    @FunctionalInterface
    interface RowMapper<T> {
        T map(Row row) throws Exception;
    }

    @FunctionalInterface
    interface DataInserter<T> {
        int insert(T t);
    }
}
