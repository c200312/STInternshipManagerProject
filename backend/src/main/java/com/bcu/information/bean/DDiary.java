package com.bcu.information.bean;

import lombok.Data;

/*
 * 周学生记实体类
 * */
@Data
public class DDiary {
    private String week;   //周1-16为周记 17为总结
    private String content; // 周学生记内容
    private String status; // 审核状态：DRAFT(草稿)、SUBMITTED(已提交)、APPROVED(已通过)、REJECTED(已拒绝)
    private String reviewComment; // 审核意见
    private String reviewTime; // 审核时间
    private String reviewer; // 审核人
}
