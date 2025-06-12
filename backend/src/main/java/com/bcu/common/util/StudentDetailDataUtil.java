package com.bcu.common.util;

import com.bcu.admin.bean.StudentDetailDTO;
import com.bcu.assessment.bean.Assessment;
import com.bcu.information.bean.DUser;
import com.bcu.internship.bean.Internship;
import com.bcu.student.bean.Student;
import com.bcu.teacher.bean.Teacher;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 学生详细信息数据处理工具类
 * 用于处理StudentDetailDTO的构建和数据映射
 */
public class StudentDetailDataUtil {

    /**
     * 构建各种数据的映射关系
     * @param teachers 教师列表
     * @param internships 实习列表
     * @param assessments 评估列表
     * @param dUsers 用户列表
     * @return 包含所有映射关系的DataMaps对象
     */
    public static DataMaps buildDataMaps(List<Teacher> teachers, List<Internship> internships, 
                                        List<Assessment> assessments, List<DUser> dUsers) {
        Map<Integer, List<Internship>> internshipMap = internships.stream()
                .collect(Collectors.groupingBy(Internship::getS_id));
        Map<Integer, List<Assessment>> assessmentMap = assessments.stream()
                .collect(Collectors.groupingBy(Assessment::getS_id));
        Map<String, List<DUser>> dUserMap = dUsers.stream()
                .collect(Collectors.groupingBy(DUser::getId));
        Map<Integer, List<Teacher>> teacherMap = teachers.stream()
                .collect(Collectors.groupingBy(Teacher::getT_id));
        
        return new DataMaps(internshipMap, assessmentMap, dUserMap, teacherMap);
    }

    /**
     * 为单个学生构建StudentDetailDTO
     * @param student 学生信息
     * @param dataMaps 数据映射关系
     * @return 构建完成的StudentDetailDTO
     */
    public static StudentDetailDTO buildStudentDetailDTO(Student student, DataMaps dataMaps) {
        StudentDetailDTO dto = new StudentDetailDTO();
        dto.setStudent(student);

        // 设置实习信息
        List<Internship> studentInternships = dataMaps.internshipMap.get(student.getS_id());
        if (studentInternships != null && !studentInternships.isEmpty()) {
            dto.setInternship(studentInternships.getFirst());
        }

        // 设置评估信息
        List<Assessment> studentAssessments = dataMaps.assessmentMap.get(student.getS_id());
        if (studentAssessments != null && !studentAssessments.isEmpty()) {
            dto.setAssessment(studentAssessments.getFirst());
        }

        // 设置用户信息
        List<DUser> studentDUsers = dataMaps.dUserMap.get(student.getStudent_number());
        if (studentDUsers != null && !studentDUsers.isEmpty()) {
            dto.setDuser(studentDUsers.getFirst());
        }

        // 设置学术导师
        List<Teacher> academicAdvisors = dataMaps.teacherMap.get(student.getAcademic_advisor_id());
        if (academicAdvisors != null && !academicAdvisors.isEmpty()) {
            dto.setAcademicAdvisor(academicAdvisors.getFirst());
        }

        // 设置行业导师
        List<Teacher> industryAdvisors = dataMaps.teacherMap.get(student.getIndustry_advisor_id());
        if (industryAdvisors != null && !industryAdvisors.isEmpty()) {
            dto.setIndustryAdvisor(industryAdvisors.getFirst());
        }

        return dto;
    }

    /**
     * 数据映射关系的容器类
     */
    public static class DataMaps {
        public final Map<Integer, List<Internship>> internshipMap;
        public final Map<Integer, List<Assessment>> assessmentMap;
        public final Map<String, List<DUser>> dUserMap;
        public final Map<Integer, List<Teacher>> teacherMap;

        public DataMaps(Map<Integer, List<Internship>> internshipMap,
                       Map<Integer, List<Assessment>> assessmentMap,
                       Map<String, List<DUser>> dUserMap,
                       Map<Integer, List<Teacher>> teacherMap) {
            this.internshipMap = internshipMap;
            this.assessmentMap = assessmentMap;
            this.dUserMap = dUserMap;
            this.teacherMap = teacherMap;
        }
    }
}