package com.bcu.internship.bean;

import java.util.Date;

public class Internship extends InternshipKey {
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
}