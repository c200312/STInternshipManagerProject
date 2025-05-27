package com.bcu.admin.bean;

import com.bcu.assessment.bean.Assessment;
import com.bcu.internship.bean.Internship;
import com.bcu.student.bean.Student;
import com.bcu.information.bean.DUser;
import lombok.Data;

@Data
public class StudentDetailDTO {
    private Student student;
    private String academicAdvisorName;
    private String industryAdvisorName;
    private Internship internship;
    private Assessment assessment;
    private DUser duser;
}
