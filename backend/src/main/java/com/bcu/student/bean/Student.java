package com.bcu.student.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Student {
    @Setter
    private Integer s_id;

    private String student_number;

    private String student_name;

    private String gender;

    private String phone;

    private String parent_phone;

    @Setter
    private Integer enrollment_year;

    private String department;

    private String major;

    private String stu_class;

    private String counselor;

    private String counselor_phone;

    @Setter
    private Integer academic_advisor_id;

    @Setter
    private Integer industry_advisor_id;

    public void setStudent_number(String student_number) {
        this.student_number = student_number == null ? null : student_number.trim();
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name == null ? null : student_name.trim();
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public void setParent_phone(String parent_phone) {
        this.parent_phone = parent_phone == null ? null : parent_phone.trim();
    }

    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    public void setMajor(String major) {
        this.major = major == null ? null : major.trim();
    }

    public void setStu_class(String stu_class) {
        this.stu_class = stu_class == null ? null : stu_class.trim();
    }

    public void setCounselor(String counselor) {
        this.counselor = counselor == null ? null : counselor.trim();
    }

    public void setCounselor_phone(String counselor_phone) {
        this.counselor_phone = counselor_phone == null ? null : counselor_phone.trim();
    }

}