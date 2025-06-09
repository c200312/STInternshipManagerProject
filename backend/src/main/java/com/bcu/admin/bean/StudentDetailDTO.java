package com.bcu.admin.bean;

import com.bcu.assessment.bean.Assessment;
import com.bcu.internship.bean.Internship;
import com.bcu.student.bean.Student;
import com.bcu.information.bean.DUser;
import com.bcu.teacher.bean.Teacher;
import lombok.*;


@Data
@RequiredArgsConstructor
@Getter
public class StudentDetailDTO {
    private Student student;
    private Teacher academicAdvisor;
    private Teacher industryAdvisor;
    private Internship internship;
    private Assessment assessment;
    private DUser duser;
}
