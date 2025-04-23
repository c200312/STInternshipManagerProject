package com.bcu.information.bean.Internship;

import lombok.Data;

/*
* 教师评语实体类
* */
@Data
public class DComment {
    private String teachername;
    private Integer type;
    private Integer week;
    private String content;
}
