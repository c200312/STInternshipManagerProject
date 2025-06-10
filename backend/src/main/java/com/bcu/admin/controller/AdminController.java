package com.bcu.admin.controller;

import com.bcu.admin.bean.StudentDetailDTO;
import com.bcu.admin.service.AdminService;
import com.bcu.common.result.Result;

import com.bcu.common.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;


    @PostMapping("/import")
    public Result uploadExcel(@RequestParam("file") MultipartFile file) throws IOException {
        return adminService.importExcel(file);
    }

    @GetMapping("/download/{type}")
    public ResponseEntity<byte[]> downloadTemplate(@PathVariable String type) {
        Map<String, String> template = new HashMap<>();
        template.put("student", "student.xlsx");
        template.put("teacher", "teacher.xlsx");
        template.put("user", "user.xlsx");
        return FileUtil.downloadTemplateFile(template, type);
    }

    @GetMapping("/stinfo")
    public Result getStInfo(){
        return adminService.getStInfo();
    }

    @PostMapping("/allwordoutput")
    public ResponseEntity<byte[]> wordOutput() throws IOException {
        Result result = adminService.getStInfo();
        if (result.getData() instanceof List<?> dataList) {
            for (Object obj : dataList) {
                if (obj instanceof StudentDetailDTO data) {
                    // 检查必要的对象是否为null
                    if (data.getStudent() == null) {
                        continue; // 跳过没有学生信息的记录
                    }

                    Map<String, Object> map = new HashMap<>();
                    map.put("sId", data.getStudent().getS_id());
                    map.put("sName", data.getStudent().getStudent_name());
                    map.put("sNumber", data.getStudent().getStudent_number());
                    map.put("sGender", data.getStudent().getGender());
                    map.put("sClass", data.getStudent().getStu_class());
                    map.put("sMajor", data.getStudent().getMajor());
                    map.put("sDepartment", data.getStudent().getDepartment());
                    map.put("sPhone", data.getStudent().getPhone());
                    map.put("sParentPhone", data.getStudent().getParent_phone());
                    map.put("sEnrollmentYear", data.getStudent().getEnrollment_year());
                    map.put("sCounselor", data.getStudent().getCounselor());
                    map.put("sCounselorPhone", data.getStudent().getCounselor_phone());

                    // 安全地处理学术导师信息
                    if (data.getAcademicAdvisor() != null) {
                        map.put("sAcademicAdvisorName", data.getAcademicAdvisor().getTeacher_name());
                        map.put("sAcademicAdvisorPhone", data.getAcademicAdvisor().getPhone());
                    } else {
                        map.put("sAcademicAdvisorName", "");
                        map.put("sAcademicAdvisorPhone", "");
                    }

                    // 安全地处理行业导师信息
                    if (data.getIndustryAdvisor() != null) {
                        map.put("sIndustryAdvisorName", data.getIndustryAdvisor().getTeacher_name());
                        map.put("sIndustryAdvisorPhone", data.getIndustryAdvisor().getPhone());
                    } else {
                        map.put("sIndustryAdvisorName", "");
                        map.put("sIndustryAdvisorPhone", "");
                    }

                    // 安全地处理实习信息
                    if (data.getInternship() != null) {
                        map.put("sInternshipCompanyName", data.getInternship().getCompany_name());
                        map.put("sInternshipPracticeBaseName", data.getInternship().getPractice_base_name());
                        map.put("sInternshipIsPracticeBase", data.getInternship().getIs_practice_base());
                        map.put("sInternshipCreditCode", data.getInternship().getCredit_code());
                        map.put("sInternshipPracticeRegion", data.getInternship().getPractice_region());
                        map.put("sInternshipApprovalStatus", data.getInternship().getApproval_status());
                        map.put("sInternshipStartDate", data.getInternship().getStart_date());
                        map.put("sInternshipEndDate", data.getInternship().getEnd_date());
                    } else {
                        map.put("sInternshipCompanyName", "");
                        map.put("sInternshipPracticeBaseName", "");
                        map.put("sInternshipIsPracticeBase", "");
                        map.put("sInternshipCreditCode", "");
                        map.put("sInternshipPracticeRegion", "");
                        map.put("sInternshipApprovalStatus", "");
                        map.put("sInternshipStartDate", "");
                        map.put("sInternshipEndDate", "");
                    }

                    // 创建班级文件夹路径
                    String className = data.getStudent().getStu_class();
                    String studentName = data.getStudent().getStudent_name();

                    // 检查班级和姓名是否为空
                    if (className == null || className.trim().isEmpty()) {
                        className = "未分配班级";
                    }
                    if (studentName == null || studentName.trim().isEmpty()) {
                        studentName = "未知学生";
                    }

                    String classDir = "D:/templates/" + className;

                    // 创建班级文件夹
                    java.io.File dir = new java.io.File(classDir);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    // 生成文件路径：班级文件夹/班级+姓名.docx
                    String outputPath = classDir + "/" + className + "+" + studentName + ".docx";
                    WordUtil.generateWord(map, "D:/templates/模板.docx", outputPath);
                }
            }
        }
        return null;
    }
}
