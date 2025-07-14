package com.bcu.admin.service;

import com.bcu.admin.bean.StudentDetailDTO;
import com.bcu.assessment.bean.Assessment;
import com.bcu.assessment.dao.AssessmentMapper;
import com.bcu.common.result.Result;
import com.bcu.common.util.ExcelCellUtil;
import com.bcu.common.util.StudentDetailDataUtil;
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

/**
 * 管理员服务类
 * 提供Excel导入、学生信息查询等功能
 * 
 * @author System
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class AdminService {

    private final StudentMapper studentMapper;
    private final TeacherMapper teacherMapper;
    private final UserMapper userMapper;
    private final InternshipMapper internshipMapper;
    private final AssessmentMapper assessmentMapper;
    private final DUserRepository  dUserRepository;

    /**
     * 导入Excel文件
     * 支持导入学生、教师、用户信息
     * 
     * @param file 上传的Excel文件，支持.xlsx和.xls格式
     * @return Result 导入结果，包含成功和失败的记录信息
     * @throws IOException 文件读取异常
     */
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

    /**
     * 通用数据导入方法
     * 使用泛型处理不同类型的数据导入
     * 
     * @param <T> 数据类型
     * @param sheet Excel工作表
     * @param mapper 行数据映射器
     * @param inserter 数据插入器
     * @return Result 导入结果
     */
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

    /**
     * 将Excel行数据映射为User对象
     * 
     * @param row Excel行数据
     * @return User 用户对象
     */
    private User mapUserRow(Row row) {
        User user = new User();
        user.setUsername(ExcelCellUtil.getString(row, 0));
        user.setPassword(ExcelCellUtil.getString(row, 1));
        user.setRole(ExcelCellUtil.getString(row, 2));
        return user;
    }

    /**
     * 将Excel行数据映射为Teacher对象
     * 
     * @param row Excel行数据
     * @return Teacher 教师对象
     */
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

    /**
     * 将Excel行数据映射为Student对象
     * 
     * @param row Excel行数据
     * @return Student 学生对象
     */
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

    /**
     * 检测Excel文件类型
     * 根据表头第一列的内容判断是学生、教师还是用户数据
     * 
     * @param header Excel表头行
     * @return String 文件类型："student"、"teacher"、"user"或"unknown"
     */
    private String detectType(Row header) {
        String firstCell = header.getCell(0).getStringCellValue();
        if (firstCell.contains("学号")) return "student";
        if (firstCell.contains("工号")) return "teacher";
        if (firstCell.contains("用户名")) return "user";
        return "unknown";
    }

    /**
     * 获取并构建数据映射
     * @return 包含所有相关数据的映射对象
     */
    private StudentDetailDataUtil.DataMaps buildDataMaps() {
        List<Teacher> teachers = teacherMapper.selectByExample(null);
        List<Internship> internships = internshipMapper.selectByExample(null);
        List<Assessment> assessments = assessmentMapper.selectByExample(null);
        List<DUser> dUsers = dUserRepository.findAll();

        return StudentDetailDataUtil.buildDataMaps(teachers, internships, assessments, dUsers);
    }

    /**
     * 获取所有学生的详细信息
     * 包含学生基本信息、实习信息、评估信息等
     * 
     * @return Result 包含所有学生详细信息列表的结果
     */
    public Result getAllStInfo(){
        List<Student> students = studentMapper.selectByExample(null);
        StudentDetailDataUtil.DataMaps dataMaps = buildDataMaps();

        List<StudentDetailDTO> result = new ArrayList<>();

        for (Student student : students) {
            // 使用工具类构建StudentDetailDTO
            StudentDetailDTO dto = StudentDetailDataUtil.buildStudentDetailDTO(student, dataMaps);
            result.add(dto);
        }

        return Result.success(result);
    }

    /**
     * 根据学生ID获取单个学生的详细信息
     * 包含学生基本信息、实习信息、评估信息等
     * 
     * @param id 学生ID
     * @return Result 包含学生详细信息的结果，如果学生不存在则返回错误信息
     */
    public Result getStInfo(String id) {
        // 根据学生ID获取学生信息
        Student student = studentMapper.selectByPrimaryKey(Integer.parseInt(id));
        if (student == null) {
            return Result.error("学生不存在");
        }

        StudentDetailDataUtil.DataMaps dataMaps = buildDataMaps();

        // 使用工具类构建StudentDetailDTO
        StudentDetailDTO dto = StudentDetailDataUtil.buildStudentDetailDTO(student, dataMaps);

        return Result.success(dto);
    }

    /**
     * 行数据映射器函数式接口
     * 用于将Excel行数据映射为指定类型的对象
     * 
     * @param <T> 目标对象类型
     */
    @FunctionalInterface
    interface RowMapper<T> {
        /**
         * 将Excel行数据映射为对象
         * 
         * @param row Excel行数据
         * @return T 映射后的对象
         * @throws Exception 映射过程中的异常
         */
        T map(Row row) throws Exception;
    }

    /**
     * 数据插入器函数式接口
     * 用于将对象插入到数据库中
     * 
     * @param <T> 数据对象类型
     */
    @FunctionalInterface
    interface DataInserter<T> {
        /**
         * 插入数据到数据库
         * 
         * @param t 要插入的数据对象
         * @return int 插入影响的行数
         */
        int insert(T t);
    }
}
