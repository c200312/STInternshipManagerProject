package com.bcu.common.util;

import com.bcu.admin.bean.StudentDetailDTO;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 学生Word文档数据处理工具类
 * 用于提取学生信息并生成Word文档
 */
public class StudentWordDataUtil {

    /**
     * 将学生详细信息转换为Word模板所需的数据映射
     *
     * @param data 学生详细信息DTO
     * @return 包含所有学生信息的Map，用于Word模板填充
     */
    public static Map<String, Object> buildStudentDataMap(StudentDetailDTO data) {
        Map<String, Object> map = new HashMap<>();
        
        // 基本学生信息
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
            map.put("sAcademicAdvisorGender", data.getAcademicAdvisor().getGender());
            map.put("sAcademicAdvisorAge", data.getAcademicAdvisor().getAge());

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
        
        return map;
    }
    
    /**
     * 生成学生Word文档的输出路径
     *
     * @param data 学生详细信息DTO
     * @return Word文档的完整输出路径
     */
    public static String generateOutputPath(StudentDetailDTO data) {
        String className = data.getStudent().getStu_class();
        String studentName = data.getStudent().getStudent_name();
        String teacherName = data.getAcademicAdvisor() != null ? data.getAcademicAdvisor().getTeacher_name() : "未分配";
        String studentNumber = data.getStudent().getStudent_number();
        
        String studentDir = "D:/templates/" + studentNumber + "/word";
        
        // 创建文件夹
        File dir = new File(studentDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        // 生成文件路径：文件夹/班级+负责教师+姓名+学号+.docx
        return studentDir + "/" + className + "+" + teacherName + "+" + studentName + "+" + studentNumber + "+" + "校外实习实践报告.docx";
    }
    
    /**
     * 生成单个学生的Word文档
     *
     * @param data 学生详细信息DTO
     * @param templatePath 模板文件路径
     * @return 生成的Word文档路径，如果生成失败返回null
     * @throws IOException 文件操作异常
     */
    public static String generateStudentWordDocument(StudentDetailDTO data, String templatePath) throws IOException {
        Map<String, Object> dataMap = buildStudentDataMap(data);
        String outputPath = generateOutputPath(data);
        
        WordUtil.generateWord(dataMap, templatePath, outputPath);
        
        // 检查文件是否生成成功
        File file = new File(outputPath);
        if (file.exists()) {
            return outputPath;
        }
        return null;
    }
}