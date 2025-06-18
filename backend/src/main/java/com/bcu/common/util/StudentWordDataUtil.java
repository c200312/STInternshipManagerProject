package com.bcu.common.util;

import com.bcu.admin.bean.StudentDetailDTO;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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


        // 处理学术导师信息
        if (data.getAcademicAdvisor() != null) {
            map.put("sAcademicAdvisorName", data.getAcademicAdvisor().getTeacher_name());
            map.put("sAcademicAdvisorPhone", data.getAcademicAdvisor().getPhone());
            map.put("sAcademicAdvisorGender", data.getAcademicAdvisor().getGender());
            map.put("sAcademicAdvisorAge", data.getAcademicAdvisor().getAge());
            map.put("sAcademicAdvisorEducation", data.getAcademicAdvisor().getEducation());
            map.put("sAcademicAdvisorPosition",data.getAcademicAdvisor().getPosition());
            map.put("sAcademicAdvisorEmail", data.getAcademicAdvisor().getEmail());

        }
        
        // 处理行业导师信息
        if (data.getIndustryAdvisor() != null) {
            map.put("sIndustryAdvisorName", data.getIndustryAdvisor().getTeacher_name());
            map.put("sIndustryAdvisorPhone", data.getIndustryAdvisor().getPhone());
            map.put("sIndustryAdvisorGender", data.getIndustryAdvisor().getGender());
            map.put("sIndustryAdvisorAge", data.getIndustryAdvisor().getAge());
            map.put("sIndustryAdvisorEducation", data.getIndustryAdvisor().getEducation());
            map.put("sIndustryAdvisorPosition",data.getIndustryAdvisor().getPosition());
            map.put("sIndustryAdvisorEmail", data.getIndustryAdvisor().getEmail());
        }
        
        // 处理实习信息
        if (data.getInternship() != null) {
            map.put("sInternshipCompanyName", data.getInternship().getCompany_name());
            map.put("sInternshipPracticeBaseName", data.getInternship().getPractice_base_name());
            map.put("sInternshipIsPracticeBase", data.getInternship().getIs_practice_base());
            map.put("sInternshipCreditCode", data.getInternship().getCredit_code());
            map.put("sInternshipPracticeRegion", data.getInternship().getPractice_region());
            map.put("sInternshipApprovalStatus", data.getInternship().getApproval_status());
            map.put("sInternshipStartDate", formatDateToChinese(data.getInternship().getStart_date()));
            map.put("sInternshipEndDate", formatDateToChinese(data.getInternship().getEnd_date()));
            data.getDuser().getCompany().stream().findFirst().ifPresent(company -> map.put("CompanyIntroduction", company.getIntroduction()));
        }

        // 处理评价信息
        if (data.getAssessment() != null) {
            map.put("sAssessmentAttendanceScore", data.getAssessment().getAttendance_score());
            map.put("sAssessmentCompanyScore", data.getAssessment().getCompany_score());
            map.put("sAssessmentPerformanceScore", data.getAssessment().getPerformance_score());
            map.put("sAssessmentPracticeResultScore", data.getAssessment().getPractice_result_score());
            map.put("sAssessmentSchoolScore", data.getAssessment().getSchool_score());
            map.put("sAssessmentSummaryScore", data.getAssessment().getSummary_score());
            map.put("sAssessmentTotalScore", data.getAssessment().getTotal_score());
            map.put("sAssessmentProfessionalismScore", data.getAssessment().getProfessionalism_score());
            map.put("sAssessmentTaskScore", data.getAssessment().getTask_score());
        }

        // 处理周记信息
        if (data.getDuser() != null) {
            System.out.println(data.getDuser());
            map.put("sPracticeComment", data.getDuser().getPracticeComment());
            map.put("sTeachingUnitComment", data.getDuser().getTeachingUnitComment());
            map.put("sPracticeContent", data.getDuser().getPracticeContent());
            
            // 处理周记列表 - 使用循环处理1-16周
            if (data.getDuser().getDiary() != null && !data.getDuser().getDiary().isEmpty()) {
                for (int week = 1; week <= 16; week++) {
                    final int finalWeek = week;
                    final String weekStr = String.valueOf(week);
                    data.getDuser().getDiary().stream()
                        .filter(diary -> weekStr.equals(diary.getWeek()))
                        .findFirst()
                        .ifPresent(diary -> {
                        
                            map.put("sDiaryContent" + finalWeek, diary.getContent());
                            map.put("sDiaryDate" + finalWeek, formatDateRangeToChinese(diary.getDiaryDate()));
                        });
                }
            }
            
            // 处理教师评语 - 使用循环处理week为1,3,5,7,9,11对应1-6教师评语
            if (data.getDuser().getComment() != null && !data.getDuser().getComment().isEmpty()) {
                // week值和对应的key映射
                Integer[] weekValues = {1, 3, 5, 7, 9, 11};
                String[] keyNames = {"sTeacherComment1", "sTeacherComment2", "sTeacherComment3", 
                                   "sTeacherComment4", "sTeacherComment5", "sTeacherComment6"};
                
                // 循环处理1-6周记教师评语
                for (int i = 0; i < weekValues.length; i++) {
                    final Integer weekValue = weekValues[i];
                    final String keyName = keyNames[i];
                    data.getDuser().getComment().stream()
                        .filter(comment -> weekValue.equals(comment.getWeek()))
                        .findFirst()
                        .ifPresent(comment -> map.put(keyName, comment.getContent()));
                }
                
                // week 13对应总结教师评语
                data.getDuser().getComment().stream()
                    .filter(comment -> Integer.valueOf(13).equals(comment.getWeek()))
                    .findFirst()
                    .ifPresent(comment -> map.put("sSummaryTeacherComment", comment.getContent()));
            }
            
            // 处理总结
            if (data.getDuser().getDiary() != null) {
                data.getDuser().getDiary().stream()
                    .filter(diary -> "achievement".equals(diary.getWeek()))
                    .findFirst()
                    .ifPresent(summary -> {
                        map.put("sAchievementContent", summary.getContent());
                        map.put("sAchievementDate", summary.getDiaryDate());
                    });
                    
                data.getDuser().getDiary().stream()
                    .filter(diary -> "practice".equals(diary.getWeek()))
                    .findFirst()
                    .ifPresent(summary -> {
                        map.put("sPracticeContent", summary.getContent());
                        map.put("sPracticeDate", summary.getDiaryDate());
                    });
            }
        }
        
        return map;
    }
    
    /**
     * 将日期对象转换为中文格式字符串（xxxx年xx月xx日）
     *
     * @param date 日期对象
     * @return 格式化后的中文日期字符串，如果日期为null则返回空字符串
     */
    private static String formatDateToChinese(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        return sdf.format(date);
    }
    
    /**
     * 将日期对象转换为中文格式的日期范围字符串（xxxx年xx月xx日--xxxx年xx月xx+7日）
     *
     * @param startDate 开始日期对象
     * @return 格式化后的中文日期范围字符串，如果日期为null则返回空字符串
     */
    private static String formatDateRangeToChinese(Date startDate) {
        if (startDate == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        
        // 计算结束日期（开始日期+7天）
        long endTime = startDate.getTime() + 7 * 24 * 60 * 60 * 1000L;
        Date endDate = new Date(endTime);
        
        return sdf.format(startDate) + "--" + sdf.format(endDate);
    }
    
    /**
     * 将日期字符串转换为中文格式的日期范围字符串（xxxx年xx月xx日--xxxx年xx月xx+7日）
     *
     * @param dateStr 日期字符串
     * @return 格式化后的中文日期范围字符串，如果日期为null或空则返回空字符串
     */
    private static String formatDateRangeToChinese(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return "";
        }
        try {
            SimpleDateFormat inputSdf = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = inputSdf.parse(dateStr);
            return formatDateRangeToChinese(startDate);
        } catch (Exception e) {
            // 如果解析失败，返回原字符串
            return dateStr;
        }
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
            if (!dir.mkdirs()) {
                throw new RuntimeException("Failed to create directory: " + studentDir);
            }
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