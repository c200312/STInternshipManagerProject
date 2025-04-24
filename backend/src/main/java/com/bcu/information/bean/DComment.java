package com.bcu.information.bean;

import lombok.Data;

/*
* 教师评语实体类
* */
@Data
public class DComment {
    private String teachername; //教师名称
    private Integer week;   //1-16周周记对应1-8教师评语
    private String content; //教师评语
}
