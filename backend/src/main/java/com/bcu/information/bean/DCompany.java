package com.bcu.information.bean;

import lombok.Data;


/*
* 实训公司实体类
* */
@Data
public class DCompany {
    private String name;    //公司名称
    private String introduction;    //公司介绍
    // 新增字段：每周的周次和评语内容，用Map表示周次作为键，评语作为值
    private String comments;
}