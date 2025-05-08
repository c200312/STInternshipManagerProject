package com.bcu.teacher.bean;

public class Teacher {
    private Integer t_id;

    private String teacher_name;

    private String gender;

    private String advisor_type;

    private Integer age;

    private String education;

    private String position;

    private String phone;

    private String email;

    private String signature_path;

    public Integer getT_id() {
        return t_id;
    }

    public void setT_id(Integer t_id) {
        this.t_id = t_id;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name == null ? null : teacher_name.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getAdvisor_type() {
        return advisor_type;
    }

    public void setAdvisor_type(String advisor_type) {
        this.advisor_type = advisor_type == null ? null : advisor_type.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education == null ? null : education.trim();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getSignature_path() {
        return signature_path;
    }

    public void setSignature_path(String signature_path) {
        this.signature_path = signature_path == null ? null : signature_path.trim();
    }
}