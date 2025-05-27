package com.bcu.information.bean;

import com.bcu.student.bean.Student;
import lombok.Data;
@Data
public class StudentView {
    private Student student;
    private DUser dUser;

        public StudentView() {
        }



        public StudentView(Student student, DUser dUser) {
                this.student = student;
                this.dUser = dUser;
        }
}

