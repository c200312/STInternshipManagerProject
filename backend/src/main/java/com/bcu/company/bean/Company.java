package com.bcu.company.bean;

public class Company {
    private Integer c_id;

    private String company_name;

    private String practice_base_name;

    private Boolean is_practice_base;

    private String credit_code;

    private String practice_region;

    private Byte approval_status;

    private String base_notes;

    public Integer getC_id() {
        return c_id;
    }

    public void setC_id(Integer c_id) {
        this.c_id = c_id;
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

    public String getBase_notes() {
        return base_notes;
    }

    public void setBase_notes(String base_notes) {
        this.base_notes = base_notes == null ? null : base_notes.trim();
    }
}