package com.bcu.student.bean;

public class Student {
    private Integer s_id;

    private String student_number;

    private String student_name;

    private String gender;

    private String phone;

    private String parent_phone;

    private Integer enrollment_year;

    private String department;

    private String major;

    private String stu_class;

    private String counselor;

    private String counselor_phone;

    private Integer academic_advisor_id;

    private Integer industry_advisor_id;

    public Integer getS_id() {
        return s_id;
    }

    public void setS_id(Integer s_id) {
        this.s_id = s_id;
    }

    public String getStudent_number() {
        return student_number;
    }

    public void setStudent_number(String student_number) {
        this.student_number = student_number == null ? null : student_number.trim();
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name == null ? null : student_name.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getParent_phone() {
        return parent_phone;
    }

    public void setParent_phone(String parent_phone) {
        this.parent_phone = parent_phone == null ? null : parent_phone.trim();
    }

    public Integer getEnrollment_year() {
        return enrollment_year;
    }

    public void setEnrollment_year(Integer enrollment_year) {
        this.enrollment_year = enrollment_year;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major == null ? null : major.trim();
    }

    public String getStu_class() {
        return stu_class;
    }

    public void setStu_class(String stu_class) {
        this.stu_class = stu_class == null ? null : stu_class.trim();
    }

    public String getCounselor() {
        return counselor;
    }

    public void setCounselor(String counselor) {
        this.counselor = counselor == null ? null : counselor.trim();
    }

    public String getCounselor_phone() {
        return counselor_phone;
    }

    public void setCounselor_phone(String counselor_phone) {
        this.counselor_phone = counselor_phone == null ? null : counselor_phone.trim();
    }

    public Integer getAcademic_advisor_id() {
        return academic_advisor_id;
    }

    public void setAcademic_advisor_id(Integer academic_advisor_id) {
        this.academic_advisor_id = academic_advisor_id;
    }

    public Integer getIndustry_advisor_id() {
        return industry_advisor_id;
    }

    public void setIndustry_advisor_id(Integer industry_advisor_id) {
        this.industry_advisor_id = industry_advisor_id;
    }
}