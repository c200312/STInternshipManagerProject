package com.bcu.information.bean;

import lombok.Data;

/**
 * 周记审核请求DTO
 */
@Data
public class DiaryReviewRequest {
    private String status; // 审核状态：APPROVED 或 REJECTED
    private String reviewComment; // 审核意见
    private String reviewer; // 审核人
}