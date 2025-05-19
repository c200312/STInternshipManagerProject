package com.bcu.admin.service;

import com.bcu.common.result.Result;
import com.bcu.student.bean.Student;
import com.bcu.student.dao.StudentMapper;
import com.bcu.teacher.bean.Teacher;
import com.bcu.teacher.dao.TeacherMapper;
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

@Service
@RequiredArgsConstructor
public class AdminService {
    private final StudentMapper studentMapper;
    private final TeacherMapper teacherMapper;
    public Result importExcel(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        if (fileName == null || !fileName.endsWith(".xlsx")) {
            return Result.error("请上传正确格式的文件");
        }
        InputStream inputStream = file.getInputStream();
        Workbook workbook = fileName.endsWith(".xlsx") ? new XSSFWorkbook(inputStream) : new HSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0); // 只处理第一个sheet
        Row header = sheet.getRow(0);
        if (header == null){
            return Result.error("文件表头为空请重新上传");
        }
        String type = detectType(header);
        if ("student".equals(type)) {
            return importStudent(sheet);
        } else if ("teacher".equals(type)) {
            return importTeacher(sheet);
        } else return Result.error("请使用系统模板");



    }

    private Result importTeacher(Sheet sheet) {
        List<Teacher> eList = new ArrayList<>();
        List<Teacher> teachers = new ArrayList<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;
            Teacher teacher = new Teacher();
            teacher.setT_number(String.valueOf(row.getCell(0)));
            teacher.setTeacher_name(String.valueOf(row.getCell(1)));
            teacher.setGender(String.valueOf(row.getCell(2)));
            teacher.setAdvisor_type(String.valueOf(row.getCell(3)));
            teacher.setAge(Integer.parseInt(String.valueOf(row.getCell(4))));
            teacher.setEducation(String.valueOf(row.getCell(5)));
            teacher.setPosition(String.valueOf(row.getCell(6)));
            teacher.setPhone(String.valueOf(row.getCell(7)));
            teacher.setEmail(String.valueOf(row.getCell(8)));
            teachers.add(teacher);
        }
        for (Teacher teacher : teachers) {
            int result = teacherMapper.insert(teacher);
            if (result != 1) eList.add(teacher);
        }
        if (eList.isEmpty())
            return Result.success(teachers,"导入成功");
        else return Result.error(eList,"该部分导入失败，其余成功导入");

    }

    private Result importStudent(Sheet sheet) {
        List<Student> eList = new ArrayList<>();
        List<Student> students = new ArrayList<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;
            Student student = new Student();
            student.setStudent_number(String.valueOf(row.getCell(0)));
            student.setStudent_name(String.valueOf(row.getCell(1)));
            student.setGender(String.valueOf(row.getCell(2)));
            student.setPhone(String.valueOf(row.getCell(3)));
            student.setParent_phone(String.valueOf(row.getCell(4)));
            student.setEnrollment_year(Integer.parseInt(String.valueOf(row.getCell(5))));
            student.setDepartment(String.valueOf(row.getCell(6)));
            student.setMajor(String.valueOf(row.getCell(7)));
            student.setStu_class(String.valueOf(row.getCell(8)));
            student.setCounselor(String.valueOf(row.getCell(9)));
            student.setCounselor_phone(String.valueOf(row.getCell(10)));
            students.add(student);

        }
        for (Student student : students) {
            int result = studentMapper.insert(student);
            if (result != 1) eList.add(student);
        }
        if (eList.isEmpty())
            return Result.success(students,"导入成功");
        else return Result.error(eList,"该部分导入失败，其余成功导入");

    }

    private String detectType(Row header) {
        String firstCell = header.getCell(0).getStringCellValue();
        if (firstCell.contains("学号")) return "student";
        if (firstCell.contains("工号")) return "teacher";
        return "unknown";
    }

}
