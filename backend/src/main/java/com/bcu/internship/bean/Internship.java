package com.bcu.internship.bean;

import java.util.Date;

public class Internship {
    private Integer internship_id;

    private Integer s_id;

    private String company_name;

    private String practice_base_name;

    private Boolean is_practice_base;

    private String credit_code;

    private String practice_region;

    private Byte approval_status;

    private Date start_date;

    private Date end_date;

    private Integer actual_days;

    private Integer company_count;

    private String position;

    private Integer salary;

    private String internship_mode;

    private String internship_type;

    private String company_advisor_name;

    private String company_advisor_position;

    private String address;

    private String contact_person;

    private String company_phone;

    private String base_notes;

    public Integer getInternship_id() {
        return internship_id;
    }

    public void setInternship_id(Integer internship_id) {
        this.internship_id = internship_id;
    }

    public Integer getS_id() {
        return s_id;
    }

    public void setS_id(Integer s_id) {
        this.s_id = s_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name == null ? null : company_name.trim();
    }

    public String getPractice_base_name() {
        return practice_base_name;
    }

    public void setPractice_base_name(String practice_base_name) {
        this.practice_base_name = practice_base_name == null ? null : practice_base_name.trim();
    }

    public Boolean getIs_practice_base() {
        return is_practice_base;
    }

    public void setIs_practice_base(Boolean is_practice_base) {
        this.is_practice_base = is_practice_base;
    }

    public String getCredit_code() {
        return credit_code;
    }

    public void setCredit_code(String credit_code) {
        this.credit_code = credit_code == null ? null : credit_code.trim();
    }

    public String getPractice_region() {
        return practice_region;
    }

    public void setPractice_region(String practice_region) {
        this.practice_region = practice_region == null ? null : practice_region.trim();
    }

    public Byte getApproval_status() {
        return approval_status;
    }

    public void setApproval_status(Byte approval_status) {
        this.approval_status = approval_status;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public Integer getActual_days() {
        return actual_days;
    }

    public void setActual_days(Integer actual_days) {
        this.actual_days = actual_days;
    }

    public Integer getCompany_count() {
        return company_count;
    }

    public void setCompany_count(Integer company_count) {
        this.company_count = company_count;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public String getInternship_mode() {
        return internship_mode;
    }

    public void setInternship_mode(String internship_mode) {
        this.internship_mode = internship_mode == null ? null : internship_mode.trim();
    }

    public String getInternship_type() {
        return internship_type;
    }

    public void setInternship_type(String internship_type) {
        this.internship_type = internship_type == null ? null : internship_type.trim();
    }

    public String getCompany_advisor_name() {
        return company_advisor_name;
    }

    public void setCompany_advisor_name(String company_advisor_name) {
        this.company_advisor_name = company_advisor_name == null ? null : company_advisor_name.trim();
    }

    public String getCompany_advisor_position() {
        return company_advisor_position;
    }

    public void setCompany_advisor_position(String company_advisor_position) {
        this.company_advisor_position = company_advisor_position == null ? null : company_advisor_position.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getContact_person() {
        return contact_person;
    }

    public void setContact_person(String contact_person) {
        this.contact_person = contact_person == null ? null : contact_person.trim();
    }

    public String getCompany_phone() {
        return company_phone;
    }

    public void setCompany_phone(String company_phone) {
        this.company_phone = company_phone == null ? null : company_phone.trim();
    }

    public String getBase_notes() {
        return base_notes;
    }

    public void setBase_notes(String base_notes) {
        this.base_notes = base_notes == null ? null : base_notes.trim();
    }
}