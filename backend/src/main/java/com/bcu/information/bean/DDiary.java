package com.bcu.information.bean;

import lombok.Data;

/*
 * 周学生记实体类
 * */
@Data
public class DDiary {
    private Integer week;   //周1-16为周记 17为总结
    private String content; // 周学生记内容
}
